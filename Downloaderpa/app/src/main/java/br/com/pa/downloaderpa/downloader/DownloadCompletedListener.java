package br.com.pa.downloaderpa.downloader;

/**
 * Created by Romano on 22/11/2016.
 */
public interface DownloadCompletedListener {

    public void completed(String url, String path);
}
