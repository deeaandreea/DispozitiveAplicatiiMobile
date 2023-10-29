package dam.surdubobandreea1091.proiect3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class UtilizatorCustomAdapter extends ArrayAdapter<Utilizator> {

    private final int resource;
    private final List<Utilizator> listaUtilizatori;
    private final LayoutInflater inflater;

    public UtilizatorCustomAdapter(@NonNull Context context, int resource,
                                   @NonNull List<Utilizator> objects, LayoutInflater inflater) {
        super(context, resource, objects);
        this.resource = resource;
        this.listaUtilizatori = objects;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = inflater.inflate(resource, parent, false);

        Utilizator utilizator = listaUtilizatori.get(position);
        if (utilizator == null) {
            return view;
        }
        TextView tvNume = view.findViewById(R.id.tv_nume_utilizator);
        String valoare = utilizator.getNume();
        if (valoare != null && !valoare.trim().isEmpty()) {
            tvNume.setText(valoare);
        } else {
            tvNume.setText(R.string.valoare_lipsa);
        }

        return view;
    }
}
