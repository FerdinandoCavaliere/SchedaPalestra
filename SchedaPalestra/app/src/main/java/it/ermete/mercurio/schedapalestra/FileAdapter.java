package it.ermete.mercurio.schedapalestra;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class FileAdapter extends ArrayAdapter<FileDaFS> {

    final Context context;
    final List<FileDaFS> elenco;

    public FileAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<FileDaFS> elencoFiles) {
        super(context, resource, elencoFiles);

        this.context = context;
        this.elenco = elencoFiles;
    }

    @Override
    public int getCount() {
        return elenco.size();
    }

    @Nullable
    @Override
    public FileDaFS getItem(int position) {
        return elenco.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.file_list_item, null);
            viewHolder.textViewNomeFile = (TextView) convertView.findViewById(R.id.textViewNomeFile);
            viewHolder.textViewDataCreazioneFile = (TextView) convertView.findViewById(R.id.textViewDataCreazioneFile);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.textViewNomeFile.setText(elenco.get(position).getNome());
        viewHolder.textViewDataCreazioneFile.setText( elenco.get(position).getDataCreazione());

        return convertView;
    }

    private static class ViewHolder {
        public TextView textViewNomeFile;
        public TextView textViewDataCreazioneFile;
    }
}
