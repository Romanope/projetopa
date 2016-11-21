package br.com.pa.downloaderpa;

import android.content.Context;
import android.widget.ImageView;

/**
 * Created by Romano on 20/11/2016.
 */
public class Download {

    private String url;

    private boolean pendente;

    private boolean emExecucao;

    private boolean finalizado;

    private int position;

    private IListenerDownload listenerDownload;

    private ImageView imageView;

    private Context context;

    public Download(String url, ImageView imageView, Context context) {
        this.url = url;
        this.pendente = true;
        this.emExecucao = false;
        this.finalizado = false;
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

    public boolean isPendente() {
        return pendente;
    }

    public void setPendente(boolean pendente) {
        this.pendente = pendente;
    }

    public boolean isEmExecucao() {
        return emExecucao;
    }

    public void setEmExecucao(boolean emExecucao) {
        this.emExecucao = emExecucao;
    }

    public boolean isFinalizado() {
        return finalizado;
    }

    public void setFinalizado(boolean finalizado) {
        this.finalizado = finalizado;
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
}
