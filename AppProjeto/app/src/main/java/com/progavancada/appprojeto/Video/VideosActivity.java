package com.progavancada.appprojeto.Video;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.VideoView;

import com.progavancada.appprojeto.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import br.com.pa.downloaderpa.util.Constantes;

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
                Intent intent = new Intent(VideosActivity.this,BaixarVideosActivity.class);
                startActivity(intent);
            }
        });


        mListVideos = (ListView) findViewById(R.id.list_videos);

        setupListView();

        registerForContextMenu(mListVideos);
    }

    private void setupListView() {
        mVideos = new ArrayList<>();
        mVideos= pegarDiretorio();
        mVideosAdapter = new VideosAdapter(this, mVideos);
        mListVideos.setAdapter(mVideosAdapter);
    }



    private List<Video> pegarDiretorio(){

        List<Video> videos = new ArrayList<>();
        String path= Environment.getExternalStorageDirectory().toString()+"/"+Constantes.VIDEO_DIRECTORY;
        File directory = new File(path);


        if(directory.listFiles()!=null){
            File[] files = directory.listFiles();
            for(int i=0;i<files.length;i++){
                int idVideo=videos.size();

                Video video = new Video();
                video.setId(idVideo);
                video.setNome(files[i].getName());
                video.setUrlVideo(files[i].getAbsolutePath());
                videos.add(video);
            }


        }
        return videos;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem assistir= menu.add("Assistir");
        assistir.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;

                Video video = (Video) mListVideos.getItemAtPosition(info.position);

                Intent intent = new Intent(VideosActivity.this, VideoPlayerActivity.class);

                intent.putExtra("video",video);
                startActivity(intent);

                return false;
            }
        });


    }
}
