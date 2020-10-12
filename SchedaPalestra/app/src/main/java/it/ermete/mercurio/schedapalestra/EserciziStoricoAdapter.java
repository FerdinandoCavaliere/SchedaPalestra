package it.ermete.mercurio.schedapalestra;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class EserciziStoricoAdapter extends ArrayAdapter<EsercizioDaDb> {

    private final Context context;
    private final List<EsercizioDaDb> elenco;

    public EserciziStoricoAdapter(Context context, int resource, ArrayList<EsercizioDaDb> elencoDiEsercizi) {
        super(context, resource, elencoDiEsercizi);

        this.context = context;
        this.elenco = elencoDiEsercizi;
    }

    @Override
    public int getCount() {
        return elenco.size();
    }

    @Override
    public EsercizioDaDb getItem(int position) {
        return elenco.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            // Non si tratta di una view riutilizzata
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.esercizio_storico_list_item, null);
            // Creo il viewholder
            viewHolder = new ViewHolder();
            viewHolder.textViewNomeGruppoMuscolare = (TextView) convertView.findViewById(R.id.textViewNomeGruppoMuscolare);
            viewHolder.textViewNomeEsercizio = (TextView) convertView.findViewById(R.id.textViewNomeEsercizio);
            viewHolder.textViewSerie = (TextView) convertView.findViewById(R.id.textViewSerie);
            viewHolder.textViewX = (TextView ) convertView.findViewById(R.id.textViewX);
            viewHolder.linearLayoutContenitoreItemDinamici = (LinearLayout) convertView.findViewById(R.id.linearLayoutContenitoreItemDinamici);
            viewHolder.textViewNote = (TextView) convertView.findViewById(R.id.textViewNote);
            viewHolder.imageViewSuperSet = (ImageView) convertView.findViewById(R.id.imageViewSuperSet);
            // Assegno il viewholder alla view
            convertView.setTag(viewHolder);
        }
        else {
            // Si tratta di una view riutilizzata e ne recupero il viewholder
            viewHolder = (ViewHolder)convertView.getTag();
        }
        // Setto i campi del viewholder
        EsercizioDaDb esercizio = getItem(position);
        if (esercizio.getSuperSet() == null) {
            viewHolder.imageViewSuperSet.setVisibility(View.INVISIBLE);
        }
        else {
            viewHolder.imageViewSuperSet.setVisibility(View.VISIBLE);
            if (esercizio.getSuperSet().getIsPrimo() == 1) {
                if (esercizio.getIsPiramidale() == 1)
                    viewHolder.imageViewSuperSet.setImageResource(R.drawable.ssprimop);
                else
                    viewHolder.imageViewSuperSet.setImageResource(R.drawable.ssprimo);
            }
            else if (esercizio.getSuperSet().getIsUltimo() == 1) {
                if (esercizio.getIsPiramidale() == 1)
                    viewHolder.imageViewSuperSet.setImageResource(R.drawable.ssultimop);
                else
                    viewHolder.imageViewSuperSet.setImageResource(R.drawable.ssultimo);
            }
            else {
                if (esercizio.getIsPiramidale() == 1)
                    viewHolder.imageViewSuperSet.setImageResource(R.drawable.ssintermediop);
                else
                    viewHolder.imageViewSuperSet.setImageResource(R.drawable.ssintermedio);
            }
        }
        viewHolder.textViewNomeGruppoMuscolare.setText(esercizio.getNomeGruppoMuscolare());
        viewHolder.textViewNomeEsercizio.setText(esercizio.getNomeEsercizio());
        viewHolder.textViewSerie.setText(esercizio.getSerie() != 0 ? Integer.toString(esercizio.getSerie()) : "-");
        viewHolder.linearLayoutContenitoreItemDinamici.removeAllViews(); // Rimuovo ripetizioni e pesi per poi riaggiungerli dinamicamente
        for (RipetizioniPesoDaDb singolo : esercizio.getRipetizioniPeso()) {
            LayoutInflater inflaterRipetizioniPesi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View viewRpetizioniPeso = inflaterRipetizioniPesi.inflate(R.layout.ripetizioni_peso_per_esercizio_list_item, null);
            TextView textViewRipetizioni = (TextView) viewRpetizioniPeso.findViewById(R.id.textViewRipetizioni);
            textViewRipetizioni.setText(singolo.getRipetizioni());
            TextView textViewPeso = (TextView) viewRpetizioniPeso.findViewById(R.id.textViewPeso);
            textViewPeso.setText(singolo.getPeso() != 0.0 ? Utility.FormatFloat(singolo.getPeso()) : "-");
            viewHolder.linearLayoutContenitoreItemDinamici.addView(viewRpetizioniPeso);
        }
        viewHolder.textViewNote.setText(esercizio.getNote());

        if (esercizio.getIsPiramidale() == 0) {
            // Non è un piramidale
            viewHolder.textViewX.setText("X");
        }
        else {
            // E' un piramidale
            viewHolder.textViewX.setText(":");
        }

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private static class ViewHolder {
        TextView textViewNomeGruppoMuscolare;
        TextView textViewNomeEsercizio;
        TextView textViewSerie;
        TextView textViewX;
        TextView textViewNote;
        LinearLayout linearLayoutContenitoreItemDinamici;
        ImageView imageViewSuperSet;
    }
}
