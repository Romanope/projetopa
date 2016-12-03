package com.progavancada.appprojeto;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.progavancada.appprojeto.model.Musica;

import java.io.File;

public class MusicaPlayerActivity extends AppCompatActivity {

    private Button mBtnPlay;
    private Button mBtnPause;
    private Button mBtnStop;
    private TextView mTxtNomeMusica;
    private TextView mSrc;
    private Musica mMusica;
    private ProgressBar mProgressoMusica;

    private Handler mHandler;

    private MediaPlayer mMediaPlayer;
    private Uri mUriMusica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musica_player);

        mHandler = new Handler();

        Intent intent = getIntent();
        mMusica = (Musica) intent.getSerializableExtra(MusicasActivity.EXTRA_MUSICA);

        mBtnPlay = (Button) findViewById(R.id.btn_play);
        mBtnPause = (Button) findViewById(R.id.btn_pause);
        mBtnStop = (Button) findViewById(R.id.btn_stop);
        mTxtNomeMusica = (TextView) findViewById(R.id.txt_nome_musica_player);
        mSrc = (TextView) findViewById(R.id.txt_nome_autor_player);

        mTxtNomeMusica.setText(mMusica.getNome());
        mSrc.setText(mMusica.getUrlMusica());

        mUriMusica = Uri.fromFile(new File(mMusica.getUrlMusica()));
        mMediaPlayer = MediaPlayer.create(MusicaPlayerActivity.this, mUriMusica);

        mProgressoMusica = (ProgressBar) findViewById(R.id.progressoMusica);

        if (mMediaPlayer != null) {
            mProgressoMusica.setMax(mMediaPlayer.getDuration());
        }

        mBtnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mMediaPlayer.start();
                } catch (NullPointerException e) {
                    Toast.makeText(MusicaPlayerActivity.this, "O arquivo não existe ou está vazio", Toast.LENGTH_LONG).show();
                }
            }
        });


        mBtnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mMediaPlayer.pause();
                } catch (NullPointerException e) {
                    Toast.makeText(MusicaPlayerActivity.this, "O arquivo não existe ou está vazio", Toast.LENGTH_LONG).show();
                }
            }
        });


        mBtnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mMediaPlayer.stop();
                    mMediaPlayer = MediaPlayer.create(MusicaPlayerActivity.this, mUriMusica);
                } catch (NullPointerException e) {
                    Toast.makeText(MusicaPlayerActivity.this, "O arquivo não existe ou está vazio", Toast.LENGTH_LONG).show();
                }
            }
        });

        this.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                if(mMediaPlayer != null){
                    int mCurrentPosition = mMediaPlayer.getCurrentPosition();
                    mProgressoMusica.setProgress(mCurrentPosition);
                }
                mHandler.postDelayed(this, 200);
            }
        });

    }
}
