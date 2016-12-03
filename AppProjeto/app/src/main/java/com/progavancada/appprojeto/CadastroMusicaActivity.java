package com.progavancada.appprojeto;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.progavancada.appprojeto.R;
import com.progavancada.appprojeto.util.ImageUtil;

public class CadastroMusicaActivity extends AppCompatActivity {

    private EditText txtUrlMusica;
    private Button btnAdicionarMusica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_musica);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtUrlMusica = (EditText) findViewById(R.id.txt_url_musica);
        btnAdicionarMusica = (Button) findViewById(R.id.btnAdicionarMusica);

        btnAdicionarMusica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String musicaUrl = txtUrlMusica.getText().toString();
                ImageUtil.carregarMusica(CadastroMusicaActivity.this, musicaUrl);
            }
        });

    }

}
