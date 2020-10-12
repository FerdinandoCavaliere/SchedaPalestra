package it.ermete.mercurio.schedapalestra;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SchedeAdapter extends ArrayAdapter<SchedaDaDb> {

    final Context context;
    final List<SchedaDaDb> elenco;

    public SchedeAdapter(Context context, int resource, ArrayList<SchedaDaDb> elencoSchede) {
        super(context, resource, elencoSchede);

        this.elenco = elencoSchede;
        this.context = context;
    }

    @Override
    public int getCount() {
        return elenco.size();
    }

    @Override
    public SchedaDaDb getItem(int position) {
        return elenco.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            // Non si tratta di una view riutilizzata
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.scheda_list_item, null);
            // Creo il viewholder
            viewHolder = new ViewHolder();
            viewHolder.ChkSeleziona = (CheckBox) convertView.findViewById(R.id.ChkSeleziona);
            viewHolder.LayoutDatiScheda = (ConstraintLayout) convertView.findViewById(R.id.LayoutDatiScheda);
            viewHolder.textViewDataInserimentoScheda = (TextView) convertView.findViewById(R.id.textViewDataInserimentoScheda);
            viewHolder.textViewNoteScheda = (TextView) convertView.findViewById(R.id.textViewNoteScheda);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        SchedaDaDb scheda = getItem(position);
        viewHolder.textViewDataInserimentoScheda.setText(Utility.GetStringDaData(scheda.getData()));
        viewHolder.textViewNoteScheda.setText(scheda.getNote());
        viewHolder.ChkSeleziona.setTag(Integer.toString(scheda.getId()));
        viewHolder.ChkSeleziona.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    ((StoricoActivity) context).AggiungiIdEsercizio(Integer.valueOf(buttonView.getTag().toString()));
                else
                    ((StoricoActivity) context).RimuoviIdEsercizio(Integer.valueOf(buttonView.getTag().toString()));
            }
        });
        viewHolder.LayoutDatiScheda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((StoricoActivity) context).CaricaSchedaSelezionata(position);
            }
        });
        viewHolder.LayoutDatiScheda.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ((StoricoActivity) context).EliminaSchedaSelezionata(position);
                return true;
            }
        });

        return convertView;
    }

    private static class ViewHolder {
        public CheckBox ChkSeleziona;
        public ConstraintLayout LayoutDatiScheda;
        public TextView textViewDataInserimentoScheda;
        public TextView textViewNoteScheda;
    }
}
