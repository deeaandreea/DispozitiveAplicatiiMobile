package dam.surdubobandreea1091.proiect3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class GraphActivity extends AppCompatActivity {

    public static final String GRAFIC_NR = "nr";
    public static final String GRAFIC_PRET = "pret";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        List<Float> listaPreturi = new ArrayList<>();

        Bundle bundle = intent.getExtras();
        int nr = bundle.getInt(GRAFIC_NR);
        for (int i = 0; i < nr; i++)
            listaPreturi.add(bundle.getFloat(GRAFIC_PRET + i));

        setContentView(new BarChartGraph(this, listaPreturi));
    }
}