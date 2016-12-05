package com.progavancada.appprojeto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.progavancada.appprojeto.Video.VideosActivity;

public class MainActivity extends AppCompatActivity {

    private Button mButtonContatos;
    private Button mButtonMusicas;
    private Button mButtonVideos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButtonContatos = (Button) findViewById(R.id.btn_contatos);
        mButtonMusicas = (Button) findViewById(R.id.btn_musicas);
        mButtonVideos = (Button) findViewById(R.id.btn_videos);

        mButtonContatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarActivity(ContatosActivity.class);
            }
        });

        mButtonMusicas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarActivity(MusicasActivity.class);
            }
        });

        mButtonVideos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarActivity(VideosActivity.class);
            }
        });

    }

    private void iniciarActivity(Class<?> cls) {
        Intent intent = new Intent(MainActivity.this, cls);
        startActivity(intent);
    }
}
