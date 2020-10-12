package it.ermete.mercurio.schedapalestra;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class EserciziPersonaliAdapter extends ArrayAdapter<EsercizioPersonaleDaDb> {

    private final Context context;
    private final List<EsercizioPersonaleDaDb> elenco;

    public EserciziPersonaliAdapter(Context context, int resource, List<EsercizioPersonaleDaDb> elencoEsercizi) {
        super(context, resource, elencoEsercizi);

        this.context = context;
        this.elenco = elencoEsercizi;
    }

    @Override
    public int getCount() {
        return elenco.size();
    }

    @Override
    public EsercizioPersonaleDaDb getItem(int position) {
        return elenco.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            // Non si tratta di una view riutilizzata
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.esercizio_personale_list_item, null);
            // Creo il viewholder
            viewHolder = new ViewHolder();
            viewHolder.textViewNomeGruppoMuscolare = (TextView) convertView.findViewById(R.id.textViewNomeGruppoMuscolare);
            viewHolder.textViewNomeEsercizio = (TextView) convertView.findViewById(R.id.textViewNomeEsercizio);
            // Assegno il viewholder alla view
            convertView.setTag(viewHolder);
        }
        else {
            // Si tratta di una view riutilizzata e ne recupero il viewholder
            viewHolder = (ViewHolder)convertView.getTag();
        }
        // Setto i campi del viewholder
        EsercizioPersonaleDaDb esercizio = elenco.get(position);
        viewHolder.textViewNomeGruppoMuscolare.setText(esercizio.getNomeGruppoMuscolare());
        viewHolder.textViewNomeEsercizio.setText(esercizio.getNome());

        return convertView;
    }

    private static class ViewHolder {
        TextView textViewNomeGruppoMuscolare;
        TextView textViewNomeEsercizio;
    }
}

