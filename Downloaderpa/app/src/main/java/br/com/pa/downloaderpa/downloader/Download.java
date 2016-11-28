package br.com.pa.downloaderpa.downloader;

import android.content.Context;
import android.widget.ImageView;

/**
 * Created by Romano on 20/11/2016.
 */
public class Download {

    private int id;

    private String url;

    private int position;

    private IListenerDownload listenerDownload;

    private ImageView imageView;

    private Context context;

    protected Download(String url, ImageView imageView, Context context) {
        this.url = url;
        this.listenerDownload = null;
        this.imageView = imageView;
        this.context = context;
    }

    public String getUrl() {

        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public IListenerDownload getListenerDownload() {
        return listenerDownload;
    }

    public void setListenerDownload(IListenerDownload listenerDownload) {
        this.listenerDownload = listenerDownload;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
