package dam.surdubobandreea1091.proiect3.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import dam.surdubobandreea1091.proiect3.CursaCustomAdapter;
import dam.surdubobandreea1091.proiect3.R;
import dam.surdubobandreea1091.proiect3.Utilizator;
import dam.surdubobandreea1091.proiect3.UtilizatorCustomAdapter;
import dam.surdubobandreea1091.proiect3.database.TransportDatabase;
import dam.surdubobandreea1091.proiect3.network.AsyncTaskRunner;
import dam.surdubobandreea1091.proiect3.network.Callback;
import dam.surdubobandreea1091.proiect3.network.HttpManager;

public class UtilizatoriFragment extends Fragment {

    public static final String LISTA_UTILIZATORI = "LISTA_UTILIZATORI";

    private ListView lvUtilizatori;
    private List<Utilizator> listaUtilizatori = new ArrayList<>();

    private AsyncTaskRunner asyncTaskRunner = new AsyncTaskRunner();
    private final static String URL_UTILIZATORI_JSON = "https://pastebin.com/raw/sTUwrmPs";

    private ActivityResultLauncher<Intent> addLauncher;

    private TransportDatabase tranportDatabase = null;

    public UtilizatoriFragment() {
        // Required empty public constructor
    }
    
    public static UtilizatoriFragment newInstance(ArrayList<Utilizator> listaUtilizatori) {
        UtilizatoriFragment fragment = new UtilizatoriFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(LISTA_UTILIZATORI, listaUtilizatori);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            listaUtilizatori = getArguments().getParcelableArrayList(LISTA_UTILIZATORI);
        }

        tranportDatabase = Room.databaseBuilder(getContext().getApplicationContext(),
                        TransportDatabase.class,"TransportDB")
                .allowMainThreadQueries().build();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_utilizatori, container, false);
        initComponents(view);
        return view;
    }

    private void initComponents(View view) {
        if (getContext() != null) {
            Button btnIncarcaDate = view.findViewById(R.id.btnIncarcaUtilizatori);
            btnIncarcaDate.setOnClickListener(incarcaDateUtilizatori());

            lvUtilizatori = view.findViewById(R.id.lvUtilizatori);

            listaUtilizatori = tranportDatabase.getUtilizatorDao().selectAll();
            Toast.makeText(getContext().getApplicationContext(),
                    "Am gasit " + listaUtilizatori.size() + " utilizatori",
                    Toast.LENGTH_LONG).show();

//            ArrayAdapter<Utilizator> adapter = new ArrayAdapter<>(getContext().getApplicationContext(),
//                    android.R.layout.simple_list_item_1, listaUtilizatori);
            UtilizatorCustomAdapter adapter = new UtilizatorCustomAdapter(getContext().getApplicationContext(),
                    R.layout.lv_item_utilizator, listaUtilizatori, getLayoutInflater());
            lvUtilizatori.setAdapter(adapter);
        }
    }

    private View.OnClickListener incarcaDateUtilizatori() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Callable<String> asyncOperation = new HttpManager(URL_UTILIZATORI_JSON);
                Callback<String> mainThreadOperation = mainThreadOperationHttpJson();
                asyncTaskRunner.executeAsync(asyncOperation, mainThreadOperation);
            }
        };
    }

    private Callback<String> mainThreadOperationHttpJson() {
        return new Callback<String>() {
            @Override
            public void runResultOnUiThread(String result) {
                Toast.makeText(getContext().getApplicationContext(), result, Toast.LENGTH_LONG).show();
                listaUtilizatori.clear();
                listaUtilizatori.addAll(incarcaListaUtilizatoriJson(result));
                notifyAdapterUtilizatori();

                salvareDateUtilizatoriInDB();

                Toast.makeText(getContext().getApplicationContext(),
                        "A fost incarcata colectia de tip json cu " + listaUtilizatori.size()
                                + " utilizatori", Toast.LENGTH_LONG).show();
            }
        };
    }

    private void notifyAdapterUtilizatori() {
        ArrayAdapter<Utilizator> adapter = (ArrayAdapter<Utilizator>) lvUtilizatori.getAdapter();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    private List<Utilizator> incarcaListaUtilizatoriJson(String json) {
        List<Utilizator> lista = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String nume = jsonObject.getString("nume");
                String email = jsonObject.getString("email");
                String telefon = jsonObject.getString("telefon");
                String dataN = jsonObject.getString("dataNasterii");
                Date dataNasterii = new SimpleDateFormat("dd/MM/yyyy").parse(dataN);
                boolean esteSofer = jsonObject.getBoolean("esteSofer");

                Utilizator utilizator = new Utilizator(i + 2, nume, email, telefon,
                        dataNasterii, esteSofer);
                lista.add(utilizator);
            }
        } catch (JSONException | ParseException e) {
            e.printStackTrace();
        }
        return lista;
    }

    private void salvareDateUtilizatoriInDB() {
        tranportDatabase.getUtilizatorDao().deleteAll(); // golesc intai tabelul

        for (Utilizator utilizator : listaUtilizatori) {
            tranportDatabase.getUtilizatorDao().insert(utilizator);
        }
    }

    public void sincronizareDateUtilizatori() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.myLooper());

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(URL_UTILIZATORI_JSON);
                    HttpURLConnection http = (HttpURLConnection) url.openConnection();
                    InputStream inputStream = http.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                    BufferedReader reader = new BufferedReader(inputStreamReader);
                    String JSON = reader.lines().collect(Collectors.joining(System.lineSeparator()));

                    listaUtilizatori.clear();
                    listaUtilizatori.addAll(incarcaListaUtilizatoriJson(JSON));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext().getApplicationContext(),
                                "Colectia de tip json a fost incarcata - " + listaUtilizatori.size(),
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}