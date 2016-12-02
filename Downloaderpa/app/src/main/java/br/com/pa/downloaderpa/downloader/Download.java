package br.com.pa.downloaderpa.downloader;

import android.content.Context;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Romano on 02/12/2016.
 */
public class Download {

    private Long id;

    private String url;

    private Context context;

    private ImageView imageView;

    private Collection<DownloadCompletedListener> listeners;

    private Integer height;

    private Integer width;

    private Long initTime;

    private Long finishTime;

    protected Download() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public Collection<DownloadCompletedListener> getListeners() {
        return listeners;
    }

    public void addListener(Collection<DownloadCompletedListener> listeners) {

        this.listeners = listeners;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public Long getInitTime() {
        return initTime;
    }

    public void setInitTime(Long initTime) {
        this.initTime = initTime;
    }

    public Long getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Long finishTime) {
        this.finishTime = finishTime;
    }
}
