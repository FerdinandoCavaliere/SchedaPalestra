package it.ermete.mercurio.schedapalestra;

import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.support.v4.util.Pair;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.woxthebox.draglistview.DragItemAdapter;
import java.util.ArrayList;

public class EsercizioDragAdapter extends DragItemAdapter<Pair<Long, EsercizioDaDb>, EsercizioDragAdapter.ViewHolder> {

    private final int mLayoutId;
    private final int mGrabHandleId;
    private final Context mContext;
    private boolean mDragOnLongPress;

    public EsercizioDragAdapter(ArrayList<Pair<Long, EsercizioDaDb>> list,
                                int layoutId,
                                int grabHandleId,
                                boolean dragOnLongPress,
                                Context _contenitore) {
        mContext = _contenitore;
        mLayoutId = layoutId;
        mGrabHandleId = grabHandleId;
        mDragOnLongPress = dragOnLongPress;
        //setHasStableIds(true);
        setItemList(list);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(mLayoutId, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        EsercizioDaDb esercizio = mItemList.get(position).second;

        holder.linearLayoutContenitoreEsercizio.setBackgroundResource(Utility.getBgColor(esercizio.getBgColor()));

        // Visualizzazione dell'eventuale immagine per SuperSet
        if (esercizio.getSuperSet() == null) {
            holder.imageViewSuperSet.setVisibility(View.INVISIBLE);
        }
        else {
            holder.imageViewSuperSet.setVisibility(View.VISIBLE);
            if (esercizio.getSuperSet().getIsPrimo() == 1) {
                if (esercizio.getIsPiramidale() == 1 || esercizio.getIdGruppoMuscolare() == 1000)
                    holder.imageViewSuperSet.setImageResource(R.drawable.ssprimop);
                else
                    holder.imageViewSuperSet.setImageResource(R.drawable.ssprimo);
            }
            else if (esercizio.getSuperSet().getIsUltimo() == 1) {
                if (esercizio.getIsPiramidale() == 1 || esercizio.getIdGruppoMuscolare() == 1000)
                    holder.imageViewSuperSet.setImageResource(R.drawable.ssultimop);
                else
                    holder.imageViewSuperSet.setImageResource(R.drawable.ssultimo);
            }
            else {
                if (esercizio.getIsPiramidale() == 1 || esercizio.getIdGruppoMuscolare() == 1000)
                    holder.imageViewSuperSet.setImageResource(R.drawable.ssintermediop);
                else
                    holder.imageViewSuperSet.setImageResource(R.drawable.ssintermedio);
            }
        }
        // Fine visualizzazione dell'eventuale immagine per SuperSet

        // Set campi in comune ad esercizi cardio e non cardio
        holder.textViewNomeGruppoMuscolare.setText(esercizio.getNomeGruppoMuscolare());
        holder.textViewNomeEsercizio.setText(esercizio.getNomeEsercizio());
        holder.textViewNote.setText(esercizio.getNote());
        holder.textViewGiorno.setText(esercizio.getGiorno());
        if (!TextUtils.isEmpty(esercizio.getRoutine())) {
            holder.textViewRoutine.setText(mContext.getResources().getString(R.string.testoLabelRoutine) + " " + esercizio.getRoutine());
        } else {
            holder.textViewRoutine.setVisibility(View.INVISIBLE);
            holder.textViewRoutine.setWidth(0);
            holder.textViewRoutine.setHeight(0);
        }
        // Fine set campi in comune ad esercizi cardio e non cardio

        // Ripulisco tutti i containers dei dati (Cardio e non cardio)
        holder.linearLayoutContenitoreItemDinamiciCardio.removeAllViews();
        holder.linearLayoutContenitoreItemDinamiciNoCardio.removeAllViews();

        // Verifica se si tratta di un esercizio cardio oppure no
        if (esercizio.getIdGruppoMuscolare() == 1000) {
            holder.textViewMassimaleLabel.setVisibility(View.INVISIBLE);
            holder.textViewMassimaleLabel.setWidth(0);
            holder.textViewMassimaleLabel.setHeight(0);
            holder.textViewMassimale.setVisibility(View.INVISIBLE);
            holder.textViewMassimale.setWidth(0);
            holder.textViewMassimale.setHeight(0);
            SetValoriEsercizioCardio(holder, esercizio);
        }
        else {
            if (esercizio.getMassimale() != 0.0) {
                holder.textViewMassimaleLabel.setVisibility(View.VISIBLE);
                holder.textViewMassimale.setVisibility(View.VISIBLE);
                holder.textViewMassimale.setText( Utility.FormatFloat(esercizio.getMassimale()));
            } else {
                holder.textViewMassimaleLabel.setVisibility(View.INVISIBLE);
                holder.textViewMassimaleLabel.setWidth(0);
                holder.textViewMassimaleLabel.setHeight(0);
                holder.textViewMassimale.setVisibility(View.INVISIBLE);
                holder.textViewMassimale.setWidth(0);
                holder.textViewMassimale.setHeight(0);
            }
            SetValoriEsercizioNonCardio(holder, esercizio);
        }
        // Fine verifica se si tratta di un esercizio cardio oppure no

        holder.itemView.setTag(esercizio);
    }

    private void SetValoriEsercizioNonCardio(ViewHolder holder, EsercizioDaDb esercizio) {
        // Esercizio non cardio
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewDatiEsercizio = inflater.inflate(R.layout.dati_per_esercizio_list_item, null);

        // Set ripetizioni/peso/recupero a seconda di esercizio normale o pirabidale
        if (esercizio.getIsPiramidale() == 1)
            holder.image.setImageResource(R.drawable.ordinap);
        else
            holder.image.setImageResource(R.drawable.ordina);

        TextView textViewSerie = (TextView) viewDatiEsercizio.findViewById(R.id.textViewSerie);
        textViewSerie.setText(esercizio.getSerie() != 0 ? Integer.toString(esercizio.getSerie()) : "-");

        LinearLayout linearLayoutContenitoreItemDinamici = (LinearLayout) viewDatiEsercizio.findViewById(R.id.linearLayoutContenitoreItemDinamici);
        linearLayoutContenitoreItemDinamici.removeAllViews(); // Rimuovo ripetizioni e pesi per poi riaggiungerli dinamicamente
        for (RipetizioniPesoDaDb singolo : esercizio.getRipetizioniPeso()) {
            LayoutInflater inflaterRipetizioniPesi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

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
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewDatiEsercizio = inflater.inflate(R.layout.dati_cardio_per_esercizio_list_item, null);

        LinearLayout linearLayoutProgramma = (LinearLayout) viewDatiEsercizio.findViewById(R.id.linearLayoutProgramma);
        LinearLayout linearLayoutIntensita = (LinearLayout) viewDatiEsercizio.findViewById(R.id.linearLayoutIntensita);
        LinearLayout linearLayoutPendenza = (LinearLayout) viewDatiEsercizio.findViewById(R.id.linearLayoutPendenza);

        TextView textViewProgramma = (TextView) viewDatiEsercizio.findViewById(R.id.textViewProgramma);
        TextView textViewIntensita = (TextView) viewDatiEsercizio.findViewById(R.id.textViewIntensita);
        TextView textViewPendenza = (TextView) viewDatiEsercizio.findViewById(R.id.textViewPendenza);
        TextView textViewVelocita = (TextView) viewDatiEsercizio.findViewById(R.id.textViewVelocita);
        TextView textViewTempo = (TextView) viewDatiEsercizio.findViewById(R.id.textViewTempo);

        // Set dati esercizio cardio
        holder.image.setImageResource(R.drawable.ordinap);

        DatiEsercizioCardioDaDb datiEsercizioCardioDaDb = esercizio.getDatiEsercizioCardio();
        textViewProgramma.setText(datiEsercizioCardioDaDb.getProgramma() == "" ? "-" : datiEsercizioCardioDaDb.getProgramma());
        textViewIntensita.setText(datiEsercizioCardioDaDb.getIntensita() == "" ? "-" : datiEsercizioCardioDaDb.getIntensita());
        textViewPendenza.setText(datiEsercizioCardioDaDb.getPendenza() == "" ? "-" : datiEsercizioCardioDaDb.getPendenza());

        textViewVelocita.setText(datiEsercizioCardioDaDb.getVelocita() != 0 ? Float.toString(datiEsercizioCardioDaDb.getVelocita()) : "-");
        textViewTempo.setText(datiEsercizioCardioDaDb.getTempo() != 0 ? Float.toString(datiEsercizioCardioDaDb.getTempo()) : "-");

        holder.linearLayoutContenitoreItemDinamiciCardio.addView(viewDatiEsercizio);
    }

    @Override
    public long getUniqueItemId(int position) {
        return mItemList.get(position).first;
    }

    public class ViewHolder extends DragItemAdapter.ViewHolder {

        final LinearLayout linearLayoutContenitoreEsercizio;
        final LinearLayout linearLayoutContenitoreItemDinamiciCardio;
        final LinearLayout linearLayoutContenitoreItemDinamiciNoCardio;

        final ImageView image;
        final TextView textViewNomeGruppoMuscolare;
        final TextView textViewNomeEsercizio;
        final TextView textViewGiorno;
        final TextView textViewRoutine;
        final TextView textViewNote;
        final ImageView imageViewSuperSet;
        final TextView textViewMassimaleLabel;
        final TextView textViewMassimale;

        public ViewHolder(final View itemView) {
            super(itemView, mGrabHandleId, mDragOnLongPress);

            linearLayoutContenitoreEsercizio = (LinearLayout) itemView.findViewById(R.id.linearLayoutContenitoreEsercizio);
            linearLayoutContenitoreItemDinamiciCardio = (LinearLayout) itemView.findViewById(R.id.linearLayoutContenitoreItemDinamiciCardio);
            linearLayoutContenitoreItemDinamiciNoCardio = (LinearLayout) itemView.findViewById(R.id.linearLayoutContenitoreItemDinamiciNoCardio);

            image = (ImageView) itemView.findViewById(R.id.image);
            textViewNomeGruppoMuscolare = (TextView) itemView.findViewById(R.id.textViewNomeGruppoMuscolare);
            textViewNomeEsercizio = (TextView) itemView.findViewById(R.id.textViewNomeEsercizio);
            textViewNote = (TextView) itemView.findViewById(R.id.textViewNote);
            textViewGiorno = (TextView) itemView.findViewById(R.id.textViewGiorno);
            textViewRoutine = (TextView) itemView.findViewById(R.id.textViewRoutine);
            imageViewSuperSet = (ImageView) itemView.findViewById(R.id.imageViewSuperSet);
            textViewMassimaleLabel = (TextView) itemView.findViewById(R.id.textViewMassimaleLabel);
            textViewMassimale = (TextView) itemView.findViewById(R.id.textViewMassimale);
        }

        @Override
        public void onItemClicked(View view) {
            EsercizioDaDb esercizioSelezionato = (EsercizioDaDb) view.getTag();
            MainActivity activity = (MainActivity) mContext;

            if (!activity.getBottoniSpecificiVisibility()) {
                activity.getPosizioneScrollbar(); // Per ricostruire la giusta vista del recycleviw dopo l'operazione
                if (esercizioSelezionato.getIdGruppoMuscolare() == 1000) {
                    // Esercizio cardio
                    DialogModificaEsercizioCardio dialog = DialogModificaEsercizioCardio.getInstance(esercizioSelezionato,
                            activity.getIdUltimaScheda());
                    FragmentTransaction tr = activity.getSupportFragmentManager().beginTransaction();
                    tr.addToBackStack(null);
                    dialog.show(tr, "ModificaEsercizioCardio");
                }
                else {
                    // Esercizio non cardio
                    if (esercizioSelezionato.getIsPiramidale() == 0) {
                        // Non piramidale
                        DialogModificaEsercizio dialog = DialogModificaEsercizio.getInstance(esercizioSelezionato,
                                activity.getIdUltimaScheda());
                        FragmentTransaction tr = activity.getSupportFragmentManager().beginTransaction();
                        tr.addToBackStack(null);
                        dialog.show(tr, "ModificaEsercizio");
                    } else {
                        // Piramidale
                        DialogModificaEsercizioPiramidale dialog = DialogModificaEsercizioPiramidale.getInstance(esercizioSelezionato,
                                activity.getIdUltimaScheda());
                        FragmentTransaction tr = activity.getSupportFragmentManager().beginTransaction();
                        tr.addToBackStack(null);
                        dialog.show(tr, "ModificaEsercizioPiramidale");
                    }
                }
            }
        }

        @Override
        public boolean onItemLongClicked(View view) {
            EsercizioDaDb esercizioSelezionato = (EsercizioDaDb) view.getTag();
            MainActivity activity = (MainActivity) mContext;

            if (!activity.getBottoniSpecificiVisibility()) {
                activity.getPosizioneScrollbar(); // Per ricostruire la giusta vista del recycleviw dopo l'operazione
                DialogConfermaEliminazione dialog = DialogConfermaEliminazione.getInstance(esercizioSelezionato);
                FragmentTransaction tr = activity.getSupportFragmentManager().beginTransaction();
                tr.addToBackStack(null);
                dialog.show(tr, "ModificaEsercizioPiramidale");
            }
            return true;
        }
    }
}
