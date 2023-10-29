package dam.surdubobandreea1091.proiect3.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import dam.surdubobandreea1091.proiect3.R;
import dam.surdubobandreea1091.proiect3.Utilizator;

public class ProfilUtilizatorFragment extends Fragment {

    public static final String ID = "Id";
    public static final String NUME = "Nume";
    public static final String EMAIL = "Email";
    public static final String TELEFON = "Telefon";
    public static final String DATA_NASTERII = "Data nasterii";
    public static final String ESTE_SOFER = "Este sofer";
    public static final String UTILIZATOR_LOGAT = "UTILIZATOR_LOGAT";

    private TextInputEditText tietId;
    private TextInputEditText tietNume;
    private EditText etEmail;
    private EditText etTelefon;
    private EditText etDataNasterii;
    private SwitchCompat switchSofer;

    SharedPreferences preferences;
    Utilizator utilizatorLogat;

    public ProfilUtilizatorFragment() {
        // Required empty public constructor
    }

    public static ProfilUtilizatorFragment newInstance(Utilizator utilizator) {
        ProfilUtilizatorFragment fragment = new ProfilUtilizatorFragment();
        Bundle args = new Bundle();
        args.putParcelable(UTILIZATOR_LOGAT, utilizator);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            utilizatorLogat = getArguments().getParcelable(UTILIZATOR_LOGAT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profil_utilizator, container, false);
        initComponents(view);
        incarcaDinPreferinte();
        return view;
    }

    private void initComponents(View view) {
        tietId = view.findViewById(R.id.tietId);
        tietNume = view.findViewById(R.id.tietNume);
        etEmail = view.findViewById(R.id.etEmail);
        etTelefon = view.findViewById(R.id.etTelefon);
        etDataNasterii = view.findViewById(R.id.etDataNasterii);
        switchSofer = view.findViewById(R.id.switchSofer);
        Button btnSalveazaProfil = view.findViewById(R.id.btnSalveazaProfil);

        btnSalveazaProfil.setOnClickListener(salveazaProfil());

        preferences = this.getActivity().getSharedPreferences("loginSharedPref", MODE_PRIVATE);
    }

    private void incarcaDinPreferinte() {
        int id = preferences.getInt(ID, 1);
        String nume = preferences.getString(NUME, "necunoscut");
        String email = preferences.getString(EMAIL, "");
        String telefon = preferences.getString(TELEFON, "");
        String dataNasteriiStr = preferences.getString(DATA_NASTERII, "");
        boolean esteSofer = preferences.getBoolean(ESTE_SOFER, false);

        tietId.setText(String.valueOf(id));
        tietNume.setText(nume);
        etEmail.setText(email);
        etTelefon.setText(telefon);
        etDataNasterii.setText(dataNasteriiStr);
        switchSofer.setChecked(esteSofer);
    }

    private void salveazaInPreferinte() {
        int id = Integer.parseInt(tietId.getText().toString());
        String nume = tietNume.getText().toString();
        String email = etEmail.getText().toString();
        String telefon = etTelefon.getText().toString();
        String dataNasteriiStr = etDataNasterii.getText().toString();
        boolean esteSofer = switchSofer.isChecked();

        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(ID, id);
        editor.putString(NUME, nume);
        editor.putString(EMAIL, email);
        editor.putString(TELEFON, telefon);
        editor.putString(DATA_NASTERII, dataNasteriiStr);
        editor.putBoolean(ESTE_SOFER, esteSofer);
        editor.apply();
    }

    private View.OnClickListener salveazaProfil() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salveazaInPreferinte();
                Toast.makeText(getContext().getApplicationContext(),
                        "Am salvat profilul pentru " + tietNume.getText().toString(),
                        Toast.LENGTH_LONG).show();
            }
        };
    }
}