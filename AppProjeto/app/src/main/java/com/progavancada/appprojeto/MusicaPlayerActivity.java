package com.progavancada.appprojeto;

import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class MusicaPlayerActivity extends AppCompatActivity {

    private Button mBtnPlay;
    private Button mBtnPause;
    private Button mBtnStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musica_player);

        mBtnPlay = (Button) findViewById(R.id.btn_play);
        mBtnPause = (Button) findViewById(R.id.btn_pause);
        mBtnStop = (Button) findViewById(R.id.btn_stop);

        MediaPlayer mediaPlayer = MediaPlayer.create(this, Uri.parse("http://www.myinstants.com/media/sounds/tindeck_1.mp3"));
        mediaPlayer.start();

    }
}
