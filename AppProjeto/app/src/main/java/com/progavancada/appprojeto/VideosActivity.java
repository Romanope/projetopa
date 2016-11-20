package com.progavancada.appprojeto;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.progavancada.appprojeto.adapter.VideosAdapter;
import com.progavancada.appprojeto.model.Video;

import java.util.ArrayList;
import java.util.List;

public class VideosActivity extends AppCompatActivity {

    private List<Video> mVideos;
    private VideosAdapter mVideosAdapter;

    private ListView mListVideos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        mListVideos = (ListView) findViewById(R.id.list_videos);

        setupListView();

        registerForContextMenu(mListVideos);
    }

    private void setupListView() {
        mVideos = new ArrayList<>();
        criaUnsVideos(20);

        mVideosAdapter = new VideosAdapter(this, mVideos);
        mListVideos.setAdapter(mVideosAdapter);
    }

    private void criaUnsVideos(int qnt) {
        for (int i = 0; i < qnt; i++) {
            Video v = new Video();
            v.setNome("Nome de um video " + i);
            v.setDescricao("Descricao do video");
            mVideos.add(v);
        }
    }

}
