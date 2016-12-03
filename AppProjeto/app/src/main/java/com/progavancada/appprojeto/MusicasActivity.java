package com.progavancada.appprojeto;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.progavancada.appprojeto.adapter.MusicasAdapter;
import com.progavancada.appprojeto.facade.ContatoFacade;
import com.progavancada.appprojeto.model.Contato;
import com.progavancada.appprojeto.model.Musica;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.pa.downloaderpa.util.Constantes;

public class MusicasActivity extends AppCompatActivity {

    private MusicasAdapter mMusicasAdapter;
    private List<Musica> mMusicas;

    private ListView mListMusicas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musicas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MusicasActivity.this, CadastroMusicaActivity.class);
                startActivity(intent);
            }
        });

        mListMusicas = (ListView) findViewById(R.id.list_musicas);

        setupListView();

        registerForContextMenu(mListMusicas);

    }

    private void setupListView() {
        mMusicas = new ArrayList<>();
        mMusicas = carregarDiretorio();

        mMusicasAdapter = new MusicasAdapter(this, mMusicas);
        mListMusicas.setAdapter(mMusicasAdapter);

    }

    private List<Musica> carregarDiretorio() {
        List<Musica> musicas = new ArrayList<>();
        String path = Environment.getExternalStorageDirectory().toString() + "/" + Constantes.AUDIO_DIRECTORY;
        Log.d("Files", "Path: " + path);
        File directory = new File(path);

        if (directory.listFiles() != null) {
            File[] files = directory.listFiles();
            Log.d("Files", "Size: "+ files.length);
            for (int i = 0; i < files.length; i++) {
                Musica musica = new Musica();
                musica.setNome(files[i].getName());
                musica.setUrlMusica(files[i].getAbsolutePath());
                musica.setAutor("ND");
                musicas.add(musica);
                Log.d("Files", "FileName:" + files[i].getName());
            }
        }


        return musicas;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem ouvir = menu.add("Ouvir");
        ouvir.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;

                Musica musica = (Musica) mListMusicas.getItemAtPosition(info.position);


                return false;
            }
        });
    }

}
