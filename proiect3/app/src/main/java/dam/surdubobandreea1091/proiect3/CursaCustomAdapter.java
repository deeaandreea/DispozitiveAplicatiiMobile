package dam.surdubobandreea1091.proiect3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.List;

public class CursaCustomAdapter extends ArrayAdapter<Cursa> {

    private final int resource;
    private final List<Cursa> listaCurse;
    private final LayoutInflater inflater;

    public CursaCustomAdapter(@NonNull Context context, int resource, @NonNull List<Cursa> objects,
                              LayoutInflater inflater) {
        super(context, resource, objects);
        this.resource = resource;
        this.listaCurse = objects;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = inflater.inflate(resource, parent, false);
        Cursa cursa = listaCurse.get(position);
        if (cursa == null) {
            return view;
        }
        setTextPlecare(view, cursa.getPlecare());
        setTextDestinatie(view, cursa.getDestinatie());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dataPlecare = dateFormat.format(cursa.getDataPlecare());
        setTextDataPlecare(view, dataPlecare);

        ImageView ivAnimaleCompanie = view.findViewById(R.id.iv_accepta_animale_companie);
        if (cursa.isAcceptaAnimaleCompanie()) {
            ivAnimaleCompanie.setVisibility(View.VISIBLE);
        } else {
            ivAnimaleCompanie.setVisibility(View.GONE);
        }

        return view;
    }

    private void setTextPlecare(View view, String plecare) {
        TextView textView = view.findViewById(R.id.tv_plecare);
        setValoareTextView(textView, plecare);
    }

    private void setTextDestinatie(View view, String destinatie) {
        TextView textView = view.findViewById(R.id.tv_destinatie);
        setValoareTextView(textView, destinatie);
    }

    private void setTextDataPlecare(View view, String dataPlecare) {
        TextView textView = view.findViewById(R.id.tv_data_plecare);
        setValoareTextView(textView, dataPlecare);
    }

    private void setValoareTextView(TextView textView, String valoare) {
        if (valoare != null && !valoare.trim().isEmpty()) {
            textView.setText(valoare);
        } else {
            textView.setText(R.string.valoare_lipsa);
        }
    }
}
