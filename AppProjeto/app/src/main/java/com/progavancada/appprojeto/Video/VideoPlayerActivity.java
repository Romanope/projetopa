package com.progavancada.appprojeto.Video;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.MediaController;
import android.widget.VideoView;

import com.progavancada.appprojeto.R;

import java.io.File;

/**
 * Created by Joao on 03/12/2016.
 */

public class VideoPlayerActivity extends AppCompatActivity{
    private Uri mUriVideo;
    private Video mVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        Intent intent= getIntent();
        mVideo= (Video) intent.getSerializableExtra("video");
        VideoView vid= (VideoView) findViewById(R.id.videoView);
        vid.setVideoPath(mVideo.getUrlVideo());
        vid.setMediaController(new MediaController(this));
        vid.start();
        vid.requestFocus();

    }
}
