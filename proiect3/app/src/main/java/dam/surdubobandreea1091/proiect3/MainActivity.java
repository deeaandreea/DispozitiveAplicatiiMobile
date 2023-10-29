package dam.surdubobandreea1091.proiect3;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import dam.surdubobandreea1091.proiect3.fragments.MainFragment;
import dam.surdubobandreea1091.proiect3.fragments.ProfilUtilizatorFragment;
import dam.surdubobandreea1091.proiect3.fragments.UtilizatoriFragment;
import dam.surdubobandreea1091.proiect3.database.TransportDatabase;

public class MainActivity extends AppCompatActivity {

    // meniu navigabil
    DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Fragment fragmentSelectat;

    private Utilizator utilizatorLogat = new Utilizator();
    private List<Cursa> listaCurse = new ArrayList<>();
    private List<Utilizator> listaUtilizatori = new ArrayList<>();

    private ActivityResultLauncher<Intent> addLauncher;

    private TransportDatabase tranportDatabase = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();

        addLauncher = registerAddLauncher();

        if (savedInstanceState == null) {
            fragmentSelectat = MainFragment.newInstance((ArrayList<Cursa>) listaCurse);
            openFragment(fragmentSelectat);
            navigationView.setCheckedItem(R.id.nav_main);
        }

        tranportDatabase = Room.databaseBuilder(getApplicationContext(), TransportDatabase.class,"TransportDB")
                .allowMainThreadQueries().build();
    }

    private void configurareNavigare() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener((actionBarDrawerToggle));
        actionBarDrawerToggle.syncState();
    }

    private void initComponents() {
        configurareNavigare();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.nav_main) {
                    Log.i("MainActivityDrawerMain", "A fost aleasa optiunea Main");
                    fragmentSelectat = MainFragment.newInstance((ArrayList<Cursa>) listaCurse);
                }
                if (item.getItemId() == R.id.nav_profil_utilizator) {
                    Log.i("MainActivityDrawerProfil", "A fost aleasa optiunea Profil Utilizator");
                    fragmentSelectat = ProfilUtilizatorFragment.newInstance(utilizatorLogat);
                }
                if (item.getItemId() == R.id.nav_incarca_date_utilizatori) {
                    Log.i("MainActivityDrawerIncarcareUtilizatori", "A fost aleasa optiunea Incarcare date utilizatori");
                    fragmentSelectat = UtilizatoriFragment.newInstance((ArrayList<Utilizator>) listaUtilizatori);
                }
                if (item.getItemId() == R.id.nav_incarca_date_curse) {
                    Log.i("MainActivityDrawerIncarcareCurse", "A fost aleasa optiunea Incarcare date curse");
                    listaCurse = incarcaListaCurseJson();
                    fragmentSelectat = MainFragment.newInstance((ArrayList<Cursa>) listaCurse);
                    Toast.makeText(getApplicationContext(),
                            "A fost incarcata colectia de tip json cu " + listaCurse.size()
                                    + " curse", Toast.LENGTH_LONG).show();
                }
                if (item.getItemId() == R.id.nav_raport_curse) {
                    Log.i("MainActivityDrawerRaportCurse", "A fost aleasa optiunea Raport curse");
                    grafic();
                }
                openFragment(fragmentSelectat);
//                Toast.makeText(getApplicationContext(),
//                        getString(R.string.select_option, item.getTitle()),
//                        Toast.LENGTH_LONG).show();
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        FloatingActionButton fabAdaugCursa = findViewById(R.id.fabAdd);
        fabAdaugCursa.setOnClickListener(adaugaCursaClick());

        FloatingActionButton fabSalvezInDB = findViewById(R.id.fabSaveDB);
        fabSalvezInDB.setOnClickListener(salveazaListaCurseInDB());
    }

    private View.OnClickListener salveazaListaCurseInDB() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Filtrare curse cu plecare din Bucuresti
                List<Cursa> listaCurse2 = listaCurse.stream()
                        .filter(e -> e.getPlecare().equals("Bucuresti"))
                        .collect(Collectors.toList());

                if (listaCurse2.size() == 0) {
                    Toast.makeText(getApplicationContext(),
                            "Operatia de filtrare nu a reusit",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(getApplicationContext(),
                        "Salvare in DB curse cu plecare din Bucuresti: " + listaCurse2.size() + " curse",
                        Toast.LENGTH_SHORT).show();

                // Filtrarea s-a realizat cu succes
                tranportDatabase.getCursaDao().deleteAll(); // daca vreau sa golesc intai tabelul

                for (Cursa cursa : listaCurse2) {
                    cursa.setIdSofer(utilizatorLogat.getId());
                    tranportDatabase.getCursaDao().insert(cursa);
                }
            }
        };
    }

    private ActivityResultLauncher<Intent> registerAddLauncher() {
        ActivityResultCallback<ActivityResult> callback = getAdaugareCursaResultCallback();
        return registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), callback);
    }

    private ActivityResultCallback<ActivityResult> getAdaugareCursaResultCallback() {
        return new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Cursa cursa = result.getData().getParcelableExtra(AdaugareCursaActivity.CURSA);
                    listaCurse.add(cursa);
                    notifyAdapterCurse();
                }
            }
        };
    }

    private void notifyAdapterCurse() {
        ListView lvCurse = findViewById(R.id.lvCurse);
        CursaCustomAdapter adapter = (CursaCustomAdapter) lvCurse.getAdapter();
        adapter.notifyDataSetChanged();
    }

    private View.OnClickListener adaugaCursaClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getApplicationContext();
                Toast.makeText(context, "Publicare cursa noua", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), AdaugareCursaActivity.class);
                addLauncher.launch(intent);
            }
        };
    }

    private void openFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_frame_container, fragment)
                .commit();
    }

    public List<Cursa> incarcaListaCurseJson() {
        List<Cursa> rezultat = new ArrayList<>();
        InputStream inputStream;
        try {
            inputStream = getResources().openRawResource(R.raw.curse);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            String jsonString = stringBuilder.toString();
            JSONArray jsonArray = new JSONArray(jsonString);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject joCursa = jsonArray.getJSONObject(i);

                String plecare = joCursa.getString("plecare");
                String destinatie = joCursa.getString("destinatie");
                String dataPl = joCursa.getString("dataPlecare");
                Date dataPlecare = new SimpleDateFormat("dd/MM/yyyy").parse(dataPl);
                String oraPlecare = joCursa.getString("oraPlecare");
                int nrPasageri = joCursa.getInt("numarPasageri");
                double pretPerPasager = joCursa.getDouble("pretPerPasager");
                boolean acceptaAnimaleCompanie = joCursa.getBoolean("acceptaAnimaleCompanie");

                JSONObject joSofer = joCursa.getJSONObject("sofer");
                String nume = joSofer.getString("nume");
                String email = joSofer.getString("email");
                int rating = joSofer.getInt("rating");
                JSONObject joMasina = joSofer.getJSONObject("masina");
                String marca = joMasina.getString("marca");
                String culoare = joMasina.getString("culoare");
                String nrInmatriculare = joMasina.getString("numarInmatriculare");

                JSONArray jaPasageri = joCursa.getJSONArray("pasageri");
                List<Utilizator> pasageri = new ArrayList<>();
                for (int j = 0; j < jaPasageri.length(); j++) {
                    JSONObject joPasager = jaPasageri.getJSONObject(j);
                    nume = joPasager.getString("nume");
                    email = joPasager.getString("email");
                    rating = joPasager.getInt("rating");
                    Utilizator pasager = new Utilizator();
                    pasager.setNume(nume);
                    pasager.setEmail(email);
                    pasager.setRating(rating);
                    pasageri.add(pasager);
                }

                Cursa cursa = new Cursa(plecare, destinatie, dataPlecare, oraPlecare,
                        nrPasageri, pretPerPasager, acceptaAnimaleCompanie);
                rezultat.add(cursa);
            }
        } catch (IOException | JSONException | ParseException e) {
            e.printStackTrace();
        }
        return rezultat;
    }

    public void grafic() {
        Intent intent = new Intent( this, GraphActivity.class);
        List<Float> listaPreturi = new ArrayList<>();
        for (Cursa cursa : listaCurse) {
            listaPreturi.add((float)cursa.getPretPerPasager());
        }

        Bundle bundle = new Bundle();
        bundle.putInt("nr", listaPreturi.size());
        for (int i = 0; i < listaPreturi.size(); i++)
            bundle.putFloat("pret" + i, listaPreturi.get(i));

        intent.putExtras(bundle);

        startActivity(intent);
    }
}