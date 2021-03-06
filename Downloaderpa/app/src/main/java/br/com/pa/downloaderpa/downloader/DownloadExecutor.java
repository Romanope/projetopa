package br.com.pa.downloaderpa.downloader;


import android.content.Intent;
import br.com.pa.downloaderpa.cache.CacheUtil;
import br.com.pa.downloaderpa.util.CacheFacade;
import br.com.pa.downloaderpa.util.Constantes;
import br.com.pa.downloaderpa.util.LogWrapper;
import br.com.pa.downloaderpa.util.Util;

/**
 * Created by Romano on 20/11/2016.
 */
public class DownloadExecutor implements Runnable {

    private Download download;

    private boolean deadThread;

    private String mNameRunnable;

    private static DownloadFile[] downloaders;

    @Override
    public void run() {

        while (!deadThread) {
            download = Downloaderpa.getInstance().getDownload();
            if (download != null) {
                if (!Downloaderpa.getInstance().isCacheUpdated()) {
                    CacheUtil.updateCache(download.getContext());
                    Downloaderpa.getInstance().setCacheUpdated(true);
                }

                if (downloaders == null) {
                    setDownloaders();
                }

                //Verify if exists in cache or in system file
                String path = CacheUtil.getFileDirectory(download);
                if (Util.isNullOrEmpty(path)) {
                    path = this.getDownloader(download.getUrl()).downloader(download.getUrl());
                }

                //Caches the file reference
                if (!Util.isNullOrEmpty(path)) {
                    CacheFacade.getInstance(download.getContext()).addCache(CacheUtil.generateHash(Util.getNameFile(download.getUrl())), path);
                }

                downloadCompleted(path);

                LogWrapper.i(mNameRunnable + " performed the download  " + download.getUrl());
            }
        }
    }

    public boolean isDeadThread() {

        return deadThread;
    }

    public void setDeadThread(boolean deadThread) {

        this.deadThread = deadThread;
    }

    public void setNameRunnable(String name) {

        this.mNameRunnable = name;
    }

    private void downloadCompleted(String path) {

        Intent i = new Intent(Constantes.DOWNLOAD_COMPLETED);
        i.putExtra(Constantes.EXTRA_PATH_FILE, path);
        download.getContext().sendBroadcast(i);

        if (Util.isImage(Util.getTypeFile(download.getUrl()))) {
            Downloaderpa.getInstance().refreshingView(download, path);
        }

        if (download.getListeners() != null) {
            for (DownloadCompletedListener listener: download.getListeners()) {
                listener.completed(download.getUrl(), path);
            }
        }
    }

    private void setDownloaders() {

        downloaders = Util.getDownloaders();
    }

    private DownloadFile getDownloader(String url) {

        DownloadFile downloader = null;

        String fileExtension = Util.getTypeFile(url);
        if (Util.isAudio(fileExtension)) {
            downloader = downloaders[1];
        } else if (Util.isImage(fileExtension)) {
            downloader = downloaders[0];
        } else if (Util.isVideo(fileExtension)) {
            downloader = downloaders[2];
        } else {
            throw new IllegalArgumentException("not mapped extension file");
        }

        return downloader;
    }
}
