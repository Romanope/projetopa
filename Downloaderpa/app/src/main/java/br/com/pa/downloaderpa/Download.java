package br.com.pa.downloaderpa;

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

    public Download(String url, IListenerDownload listener) {
        this.url = url;
        this.pendente = true;
        this.emExecucao = false;
        this.finalizado = false;
        this.listenerDownload = listener;
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
}
