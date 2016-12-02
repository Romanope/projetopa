package com.progavancada.appprojeto;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.progavancada.appprojeto.R;

public class CadastroMusicaActivity extends AppCompatActivity {

    private Button btnAdicionarMusica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_musica);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnAdicionarMusica = (Button) findViewById(R.id.btnAdicionarMusica);

        btnAdicionarMusica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // chama a lib
            }
        });

    }

}
