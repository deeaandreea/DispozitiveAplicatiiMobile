package dam.surdubobandreea1091.proiect3.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dam.surdubobandreea1091.proiect3.Cursa;
import dam.surdubobandreea1091.proiect3.CursaCustomAdapter;
import dam.surdubobandreea1091.proiect3.R;

public class MainFragment extends Fragment {

    public static final String LISTA_CURSE = "LISTA_CURSE";

    private List<Cursa> listaCurse = new ArrayList<>();

    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance(ArrayList<Cursa> listaCurse) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(LISTA_CURSE, listaCurse);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            listaCurse = getArguments().getParcelableArrayList(LISTA_CURSE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        initComponents(view);
        return view;
    }

    private void initComponents(View view) {
        if (getContext() != null) {
            ListView lvCurse = view.findViewById(R.id.lvCurse);

            Cursa cursa1 = new Cursa("Bucuresti", "Constanta",
                    new Date(), "10:30", 3,
                    31, false);
            Cursa cursa2 = new Cursa("Timisoara", "Brasov",
                    new Date(), "19:00", 2,
                    23, true);
            listaCurse.add(cursa1);
            listaCurse.add(cursa2);

            CursaCustomAdapter adapter = new CursaCustomAdapter(getContext().getApplicationContext(),
                    R.layout.lv_item_cursa, listaCurse, getLayoutInflater());
            lvCurse.setAdapter(adapter);
        }
    }
}