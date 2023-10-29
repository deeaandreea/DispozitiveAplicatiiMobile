package dam.surdubobandreea1091.proiect3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AdaugareCursaActivity extends AppCompatActivity {

    public static final String CURSA = "CURSA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adaugare_cursa);
    }

    public void adaugaCursa(View v) {
        TextInputEditText tietPlecare = findViewById(R.id.tietPlecare);
        String plecare = tietPlecare.getText().toString();
        TextInputEditText tietDestinatie = findViewById(R.id.tietDestinatie);
        String destinatie = tietDestinatie.getText().toString();
        TextInputEditText tietDataPlecare = findViewById(R.id.tietDataPlecare);
        String dataPlecareStr = tietDataPlecare.getText().toString();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date dataPlecare = null;
        try {
            dataPlecare = dateFormat.parse(dataPlecareStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        TextInputEditText tietOraPlecare = findViewById(R.id.tietOraPlecare);
        String oraPlecare = tietOraPlecare.getText().toString();
        RadioGroup rgPasageri = findViewById(R.id.rgNrPasageri);
        int selectedId =rgPasageri.getCheckedRadioButtonId();
        RadioButton rbSelectat = findViewById(selectedId);
        int nrPasageri = Integer.parseInt(rbSelectat.getText().toString());
        TextInputEditText tiePretPerPaager = findViewById(R.id.tietPretPerPasager);
        double pretPerPasager = Double.parseDouble(tiePretPerPaager.getText().toString());
        CheckBox cbAcceptaAnimaleCompanie = findViewById(R.id.cbAcceptaAnimaleCompanie);
        boolean acceptaAnimaleCompanie = cbAcceptaAnimaleCompanie.isChecked();

        Cursa cursa = new Cursa(plecare, destinatie, dataPlecare, oraPlecare,
                nrPasageri, pretPerPasager, acceptaAnimaleCompanie);
        Toast.makeText(this, cursa.toString(),Toast.LENGTH_LONG).show();

        Intent intent = getIntent();
        intent.putExtra(CURSA, cursa);
        setResult(RESULT_OK, intent);

        finish();
    }
}