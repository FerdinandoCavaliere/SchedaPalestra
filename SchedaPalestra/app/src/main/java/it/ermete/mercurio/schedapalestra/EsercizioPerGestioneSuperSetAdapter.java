package it.ermete.mercurio.schedapalestra;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class EsercizioPerGestioneSuperSetAdapter  extends ArrayAdapter<EsercizioDaDb> {

    private final Context context;
    private final List<EsercizioDaDb> elenco;

    public EsercizioPerGestioneSuperSetAdapter(Context context, int resource, ArrayList<EsercizioDaDb> elencoDiEsercizi) {
        super(context, resource, elencoDiEsercizi);

        this.context = context;
        this.elenco = elencoDiEsercizi;
    }

    @Override
    public int getCount() {
        if (this.elenco != null) {
            return elenco.size();
        } else {
            return 0;
        }
    }

    @Override
    public EsercizioDaDb getItem(int position) {
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
            convertView = inflater.inflate(R.layout.esercizio_gestionesuperset_list_item, null);
            // Creo il viewholder
            viewHolder = new ViewHolder();
            viewHolder.linearLayoutContenitoreEsercizio = (LinearLayout) convertView.findViewById(R.id.linearLayoutContenitoreEsercizio);
            viewHolder.checkBoxSeleziona = (CheckBox) convertView.findViewById(R.id.checkBoxSeleziona);
            viewHolder.linearLayoutContenitoreItemDinamiciCardio = (LinearLayout) convertView.findViewById(R.id.linearLayoutContenitoreItemDinamiciCardio);
            viewHolder.linearLayoutContenitoreItemDinamiciNoCardio = (LinearLayout) convertView.findViewById(R.id.linearLayoutContenitoreItemDinamiciNoCardio);
            viewHolder.textViewNomeGruppoMuscolare = (TextView) convertView.findViewById(R.id.textViewNomeGruppoMuscolare);
            viewHolder.textViewNomeEsercizio = (TextView) convertView.findViewById(R.id.textViewNomeEsercizio);
            viewHolder.textViewGiorno = (TextView) convertView.findViewById(R.id.textViewGiorno);
            viewHolder.textViewRoutine = (TextView) convertView.findViewById(R.id.textViewRoutine);
            viewHolder.imageViewSuperSet = (ImageView) convertView.findViewById(R.id.imageViewSuperSet);
            viewHolder.textViewMassimaleLabel = (TextView) convertView.findViewById(R.id.textViewMassimaleLabel);
            viewHolder.textViewMassimale = (TextView) convertView.findViewById(R.id.textViewMassimale);
            // Assegno il viewholder alla view
            convertView.setTag(viewHolder);
        }
        else {
            // Si tratta di una view riutilizzata e ne recupero il viewholder
            viewHolder = (ViewHolder)convertView.getTag();
        }

        // Ripulisco tutti i containers dei dati (Cardio e non cardio)
        viewHolder.linearLayoutContenitoreItemDinamiciCardio.removeAllViews();
        viewHolder.linearLayoutContenitoreItemDinamiciNoCardio.removeAllViews();

        // Setto i campi del viewholder
        EsercizioDaDb esercizio = getItem(position);
        viewHolder.linearLayoutContenitoreEsercizio.setBackgroundResource(Utility.getBgColor(esercizio.getBgColor()));

        if (esercizio.getSuperSet() == null) {
            viewHolder.imageViewSuperSet.setVisibility(View.INVISIBLE);
            viewHolder.checkBoxSeleziona.setVisibility(View.VISIBLE);
            viewHolder.checkBoxSeleziona.setTag(Integer.toString(esercizio.getId()));
            viewHolder.checkBoxSeleziona.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked)
                        ((GestioneSuperSetActivity) context).AggiungiIdEsercizio(Integer.valueOf(buttonView.getTag().toString()));
                    else
                        ((GestioneSuperSetActivity) context).RimuoviIdEsercizio(Integer.valueOf(buttonView.getTag().toString()));
                }
            });
        }
        else {
            viewHolder.imageViewSuperSet.setVisibility(View.VISIBLE);
            viewHolder.checkBoxSeleziona.setVisibility(View.INVISIBLE);
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
        viewHolder.textViewGiorno.setText(esercizio.getGiorno());
        if (!TextUtils.isEmpty(esercizio.getRoutine())) {
            viewHolder.textViewRoutine.setText(context.getResources().getString(R.string.testoLabelRoutine) + " " + esercizio.getRoutine());
        } else {
            viewHolder.textViewRoutine.setVisibility(View.INVISIBLE);
            viewHolder.textViewRoutine.setWidth(0);
            viewHolder.textViewRoutine.setHeight(0);
        }
        // Verifica se si tratta di un esercizio cardio oppure no
        if (esercizio.getIdGruppoMuscolare() == 1000) {
            viewHolder.textViewMassimaleLabel.setVisibility(View.INVISIBLE);
            viewHolder.textViewMassimaleLabel.setWidth(0);
            viewHolder.textViewMassimaleLabel.setHeight(0);
            viewHolder.textViewMassimale.setVisibility(View.INVISIBLE);
            viewHolder.textViewMassimale.setWidth(0);
            viewHolder.textViewMassimale.setHeight(0);
            SetValoriEsercizioCardio(viewHolder, esercizio);
        }
        else {
            if (esercizio.getMassimale() != 0.0) {
                viewHolder.textViewMassimaleLabel.setVisibility(View.VISIBLE);
                viewHolder.textViewMassimale.setVisibility(View.VISIBLE);
                viewHolder.textViewMassimale.setText( Utility.FormatFloat(esercizio.getMassimale()));
            } else {
                viewHolder.textViewMassimaleLabel.setVisibility(View.INVISIBLE);
                viewHolder.textViewMassimaleLabel.setWidth(0);
                viewHolder.textViewMassimaleLabel.setHeight(0);
                viewHolder.textViewMassimale.setVisibility(View.INVISIBLE);
                viewHolder.textViewMassimale.setWidth(0);
                viewHolder.textViewMassimale.setHeight(0);
            }
            SetValoriEsercizioNonCardio(viewHolder, esercizio);
        }
        // Fine verifica se si tratta di un esercizio cardio oppure no

        return convertView;
    }

    private void SetValoriEsercizioNonCardio(ViewHolder holder, EsercizioDaDb esercizio ) {
        // Esercizio non cardio
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewDatiEsercizio = inflater.inflate(R.layout.dati_per_esercizio_list_item, null);

        TextView textViewSerie = (TextView) viewDatiEsercizio.findViewById(R.id.textViewSerie);
        textViewSerie.setText(esercizio.getSerie() != 0 ? Integer.toString(esercizio.getSerie()) : "-");

        LinearLayout linearLayoutContenitoreItemDinamici = (LinearLayout) viewDatiEsercizio.findViewById(R.id.linearLayoutContenitoreItemDinamici);
        linearLayoutContenitoreItemDinamici.removeAllViews(); // Rimuovo ripetizioni e pesi per poi riaggiungerli dinamicamente
        for (RipetizioniPesoDaDb singolo : esercizio.getRipetizioniPeso()) {
            LayoutInflater inflaterRipetizioniPesi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View viewRpetizioniPeso = inflaterRipetizioniPesi.inflate(R.layout.ripetizioni_peso_per_esercizio_list_item, null);
            TextView textViewRipetizioni = (TextView) viewRpetizioniPeso.findViewById(R.id.textViewRipetizioni);
            textViewRipetizioni.setText(singolo.getRipetizioni());
            TextView textViewPeso = (TextView) viewRpetizioniPeso.findViewById(R.id.textViewPeso);
            textViewPeso.setText(singolo.getPeso() != 0.0 ? Utility.FormatFloat(singolo.getPeso()) : "-");
            TextView textViewUnitaDiMisura = (TextView) viewRpetizioniPeso.findViewById(R.id.textViewUnitaDiMisura);
            textViewUnitaDiMisura.setText(esercizio.getUnitaDiMisura());

            SetValoreConVisibilitaPercentuale(viewRpetizioniPeso, singolo);

            SetValoreConVisibilitaRPE(viewRpetizioniPeso, singolo);

            SetVisibilitaRecupero(viewRpetizioniPeso, esercizio, singolo);

            linearLayoutContenitoreItemDinamici.addView(viewRpetizioniPeso);
        }

        TextView textViewX = (TextView) viewDatiEsercizio.findViewById(R.id.textViewX);
        TextView textViewR = (TextView) viewDatiEsercizio.findViewById(R.id.textViewR);
        TextView textViewRecupero = (TextView) viewDatiEsercizio.findViewById(R.id.textViewRecupero);
        TextView textViewMin = (TextView) viewDatiEsercizio.findViewById(R.id.textViewMin);
        if (esercizio.getIsPiramidale() == 1) {
            // E' piramidale quindi nascondo i campi di recupero degli esercizi non piramidali
            textViewX.setText(":");
            textViewR.setVisibility(View.INVISIBLE);
            textViewRecupero.setVisibility(View.INVISIBLE);
            textViewMin.setVisibility(View.INVISIBLE);
        }
        else {
            // Non è un piramidale quindi setto e visualizzo i campi di recupero degli esercizi non piramidali
            textViewX.setText("X");
            textViewR.setVisibility(View.VISIBLE);
            textViewRecupero.setText(esercizio.getRecupero() != 0.0 ? Utility.FormatFloat(esercizio.getRecupero()) : "-");
            textViewRecupero.setVisibility(View.VISIBLE);
            textViewMin.setVisibility(View.VISIBLE);
        }
        // Fine set ripetizioni/peso a seconda di esercizio normale o pirabidale

        holder.linearLayoutContenitoreItemDinamiciNoCardio.addView(viewDatiEsercizio);
    }

    private void SetVisibilitaRecupero(View viewRpetizioniPeso,
                                       EsercizioDaDb esercizio,
                                       RipetizioniPesoDaDb singolo) {
        TextView textViewRecPiramidale = (TextView) viewRpetizioniPeso.findViewById(R.id.textViewRecPiramidale);
        TextView textViewRecuperoPiramidale = (TextView) viewRpetizioniPeso.findViewById(R.id.textViewRecuperoPiramidale);
        TextView textViewMinPiramidale = (TextView) viewRpetizioniPeso.findViewById(R.id.textViewMinPiramidale);
        if (esercizio.getIsPiramidale() == 1) {
            // E' piramidale quindi setto e visualizzo i campi di recupero degli esercizi piramidali
            textViewRecPiramidale.setVisibility(View.VISIBLE);
            textViewRecuperoPiramidale.setText(singolo.getRecupero() != 0.0 ? Utility.FormatFloat(singolo.getRecupero()) : "-");
            textViewRecuperoPiramidale.setVisibility(View.VISIBLE);
            textViewMinPiramidale.setVisibility(View.VISIBLE);
        }
        else {
            // Non è un piramidale e quindi nascondo i campi di recupero del layout visto che ci saranno quelli globali per esercizi non piramidali
            textViewRecPiramidale.setVisibility(View.INVISIBLE);
            textViewRecPiramidale.setWidth(0);
            textViewRecPiramidale.setHeight(0);
            textViewRecuperoPiramidale.setVisibility(View.INVISIBLE);
            textViewRecuperoPiramidale.setWidth(0);
            textViewRecuperoPiramidale.setHeight(0);
            textViewMinPiramidale.setVisibility(View.INVISIBLE);
            textViewMinPiramidale.setWidth(0);
            textViewMinPiramidale.setHeight(0);
        }
    }

    private void SetValoreConVisibilitaPercentuale(View viewRpetizioniPeso,
                                                   RipetizioniPesoDaDb singolo) {
        TextView textViewPercentuale = (TextView) viewRpetizioniPeso.findViewById(R.id.textViewPercentuale);
        TextView textViewPercentualeAbbreviato = (TextView) viewRpetizioniPeso.findViewById(R.id.textViewPercentualeAbbreviato);
        if (singolo.getPercentuale() != 0.0) {
            // C'è la percentuale
            textViewPercentuale.setVisibility(View.VISIBLE);
            textViewPercentualeAbbreviato.setVisibility(View.VISIBLE);
            textViewPercentuale.setText(Utility.FormatFloat(singolo.getPercentuale()));
        }
        else {
            // Non c'è la percentuale
            textViewPercentuale.setVisibility(View.INVISIBLE);
            textViewPercentuale.setWidth(0);
            textViewPercentuale.setHeight(0);
            textViewPercentualeAbbreviato.setVisibility(View.INVISIBLE);
            textViewPercentualeAbbreviato.setWidth(0);
            textViewPercentualeAbbreviato.setHeight(0);
        }
    }

    private void SetValoreConVisibilitaRPE(View viewRpetizioniPeso,
                                           RipetizioniPesoDaDb singolo) {
        TextView textViewRPELabel = (TextView) viewRpetizioniPeso.findViewById(R.id.textViewRPELabel);
        TextView textViewRPE = (TextView) viewRpetizioniPeso.findViewById(R.id.textViewRPE);
        if (singolo.getRpe() != 0.0) {
            // C'è l'RPE
            textViewRPELabel.setVisibility(View.VISIBLE);
            textViewRPE.setVisibility(View.VISIBLE);
            textViewRPE.setText(Integer.toString(singolo.getRpe()));
        }
        else {
            // Non c'è l'RPE
            textViewRPELabel.setVisibility(View.INVISIBLE);
            textViewRPELabel.setWidth(0);
            textViewRPELabel.setHeight(0);
            textViewRPE.setVisibility(View.INVISIBLE);
            textViewRPE.setWidth(0);
            textViewRPE.setHeight(0);
        }
    }

    private void SetValoriEsercizioCardio(ViewHolder holder, EsercizioDaDb esercizio ) {
        // Esercizio cardio
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewDatiEsercizio = inflater.inflate(R.layout.dati_cardio_per_esercizio_list_item, null);

        TextView textViewProgramma = (TextView) viewDatiEsercizio.findViewById(R.id.textViewProgramma);
        TextView textViewIntensita = (TextView) viewDatiEsercizio.findViewById(R.id.textViewIntensita);
        TextView textViewPendenza = (TextView) viewDatiEsercizio.findViewById(R.id.textViewPendenza);
        TextView textViewVelocita = (TextView) viewDatiEsercizio.findViewById(R.id.textViewVelocita);
        TextView textViewTempo = (TextView) viewDatiEsercizio.findViewById(R.id.textViewTempo);

        DatiEsercizioCardioDaDb datiEsercizioCardioDaDb = esercizio.getDatiEsercizioCardio();
        textViewProgramma.setText(datiEsercizioCardioDaDb.getProgramma() == "" ? "-" : datiEsercizioCardioDaDb.getProgramma());
        textViewIntensita.setText(datiEsercizioCardioDaDb.getIntensita() == "" ? "-" : datiEsercizioCardioDaDb.getIntensita());
        textViewPendenza.setText(datiEsercizioCardioDaDb.getPendenza() == "" ? "-" : datiEsercizioCardioDaDb.getPendenza());

        textViewVelocita.setText(datiEsercizioCardioDaDb.getVelocita() != 0 ? Float.toString(datiEsercizioCardioDaDb.getVelocita()) : "-");
        textViewTempo.setText(datiEsercizioCardioDaDb.getTempo() != 0 ? Float.toString(datiEsercizioCardioDaDb.getTempo()) : "-");

        holder.linearLayoutContenitoreItemDinamiciCardio.addView(viewDatiEsercizio);
    }

    private static class ViewHolder {
        CheckBox checkBoxSeleziona;

        LinearLayout linearLayoutContenitoreEsercizio;
        LinearLayout linearLayoutContenitoreItemDinamiciCardio;
        LinearLayout linearLayoutContenitoreItemDinamiciNoCardio;

        TextView textViewNomeGruppoMuscolare;
        TextView textViewNomeEsercizio;
        TextView textViewGiorno;
        TextView textViewRoutine;
        ImageView imageViewSuperSet;
        TextView textViewMassimaleLabel;
        TextView textViewMassimale;
    }
}
