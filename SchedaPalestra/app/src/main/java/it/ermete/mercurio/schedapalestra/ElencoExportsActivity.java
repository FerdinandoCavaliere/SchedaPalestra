package it.ermete.mercurio.schedapalestra;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class ElencoExportsActivity extends AppCompatActivity implements DialogConfermaEliminazioneFile.NoticeDialogEliminazioneFileListener {

    ArrayList<FileDaFS> elencoSchede;
    private int PROVENIENZA = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elenco_exports);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.SettaListenersPerListElencoExport();

        PROVENIENZA = getIntent().getExtras().getInt("PROVENIENZA");
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.CaricaElencoSchede();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuItem itemGuida = menu.add(0, 2, 2, R.string.VoceMenuAiuto);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 2:
                DialogAiutoElencoExport aiuto = DialogAiutoElencoExport.getInstance();
                aiuto.show(getTransacion(), "Aiuto");
                break;

            // Respond to the action bar's Up/Home button
            case android.R.id.home:

                switch (PROVENIENZA) {
                    case 0:
                        Intent backMain = new Intent(ElencoExportsActivity.this, MainActivity.class);
                        NavUtils.navigateUpTo(this, backMain);
                        break;
                    case 1:
                        Intent backStorico = new Intent(ElencoExportsActivity.this, StoricoActivity.class);
                        NavUtils.navigateUpTo(this, backStorico);
                        break;
                }
                //NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void CaricaElencoSchede() {
        try {
            FileController fileController = new FileController();
            elencoSchede = fileController.GetElencoExportsPdf();

            FileAdapter adapter = new FileAdapter(ElencoExportsActivity.this,
                    R.layout.file_list_item,
                    elencoSchede);

            ListView listViewElencoExport = (ListView) findViewById(R.id.listViewElencoExport);
            listViewElencoExport.setAdapter(adapter);

            if (elencoSchede == null || elencoSchede.size() == 0) {
                Toast.makeText(ElencoExportsActivity.this,
                        R.string.testoNessunaExportTrovato,
                        Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception ex)
        {
            Log.d("ElencoExport", ex.getMessage());
        }
    }

    private void SettaListenersPerListElencoExport() {
        try {
            ListView listViewElencoExport = (ListView) findViewById(R.id.listViewElencoExport);
            listViewElencoExport.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    FileDaFS fileSelezionato = elencoSchede.get(position);
                    File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) +
                                    "/" + fileSelezionato.getNome());
                    Uri targetUri = FileProvider.getUriForFile(ElencoExportsActivity.this, "it.ermete.mercurio.schedapalestra.fileProvider", file);
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setDataAndType(targetUri,"application/pdf");
                    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(ElencoExportsActivity.this,
                                R.string.testoNessunaAppPerPDF,
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
            listViewElencoExport.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    FileDaFS fileSelezionato = elencoSchede.get(position);
                    DialogConfermaEliminazioneFile dialog = DialogConfermaEliminazioneFile.getInstance(fileSelezionato.getNome());
                    dialog.show(getTransacion(), "EliminaFile");
                    return true;
                }
            });
        }
        catch (Exception ex) {
            Log.d("ElencoExport", ex.getMessage());
        }
    }

    @Override
    public void onDialogEliminazioneFilePositiveClick(String esito) {
        if (TextUtils.isEmpty(esito)) {
            this.CaricaElencoSchede();
        }
        else {
            Toast.makeText(ElencoExportsActivity.this,
                    R.string.testoNonPossibileEliminareExport,
                    Toast.LENGTH_SHORT).show();
        }
    }

    private FragmentTransaction getTransacion() {
        FragmentTransaction tr = getSupportFragmentManager().beginTransaction();
        tr.addToBackStack("null");
        return tr;
    }
}
