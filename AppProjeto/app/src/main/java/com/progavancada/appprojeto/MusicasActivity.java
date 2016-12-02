package com.progavancada.appprojeto;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
                Intent intent = new Intent(MusicasActivity.this, BaixarMusicas.class);
                startActivity(intent);
            }
        });

        mListMusicas = (ListView) findViewById(R.id.list_musicas);

        setupListView();

        registerForContextMenu(mListMusicas);

    }

    private void setupListView() {
        mMusicas = new ArrayList<>();
        criaUmasMusicas(20);


        mMusicasAdapter = new MusicasAdapter(this, mMusicas);
        mListMusicas.setAdapter(mMusicasAdapter);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem ouvir = menu.add("Ouvir");
        ouvir.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
                // usa o MediaPlayer com o endereço da musica

                Musica musica = (Musica) mListMusicas.getItemAtPosition(info.position);

                try {
                    Uri myUri = null; // initialize Uri here
                    MediaPlayer mediaPlayer = new MediaPlayer();
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mediaPlayer.setDataSource(getApplicationContext(), myUri);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (IOException e) {
                    // erro
                }

                return false;
            }
        });
    }


    private void criaUmasMusicas(int qnt) {
        for (int i = 0; i < qnt; i++) {
            Musica m = new Musica();
            m.setNome("Musica No. " + i);
            m.setAutor("Autor qualquer");
            mMusicas.add(m);
        }
    }

}
