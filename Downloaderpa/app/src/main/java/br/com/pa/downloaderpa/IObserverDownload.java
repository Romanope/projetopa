package br.com.pa.downloaderpa;

/**
 * Created by Romano on 22/11/2016.
 */
public interface IObserverDownload {

    public void downloadFinish(Download download, String path);
}
