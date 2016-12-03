package com.progavancada.appprojeto.Video;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.progavancada.appprojeto.R;

import br.com.pa.downloaderpa.downloader.Downloaderpa;

/**
 * Created by Joao on 03/12/2016.
 */

public class BaixarVideosActivity extends AppCompatActivity{
        private EditText editText;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baixar_videos);
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button baixar = (Button) findViewById(R.id.baixar);
        editText = (EditText) findViewById(R.id.editText2);

        baixar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {
                    Downloaderpa.context(BaixarVideosActivity.this).url(editText.getText().toString()).startDownload();
                    Toast.makeText(BaixarVideosActivity.this, "Baixando", Toast.LENGTH_LONG).show();
                }catch(IllegalArgumentException e){
                    Toast.makeText(BaixarVideosActivity.this,"URL invalida", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
