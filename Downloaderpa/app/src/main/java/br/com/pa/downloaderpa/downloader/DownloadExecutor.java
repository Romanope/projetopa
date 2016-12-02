package br.com.pa.downloaderpa.downloader;


import android.content.Intent;
import br.com.pa.downloaderpa.cache.CacheUtil;
import br.com.pa.downloaderpa.util.CacheFacade;
import br.com.pa.downloaderpa.util.Constantes;
import br.com.pa.downloaderpa.util.LogWapper;
import br.com.pa.downloaderpa.util.Util;

/**
 * Created by Romano on 20/11/2016.
 */
public class DownloadExecutor implements Runnable {

<<<<<<< HEAD
    private DownloadFactory.Download download;
=======
    private Download download;
>>>>>>> 8a4ee645493e56a93bd34df5a69fc661aaa63565

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

                //Verify if exists in cache
                String path = CacheFacade.getInstance(download.getContext()).searchFileDirectory(CacheUtil.generateHash(Util.getNameFile(download.getUrl())));
                if (Util.isNullOrEmpty(path)) {
                    //case not found in cache, search in the file system
                    path = Util.toSearchFile(download.getUrl());
                    if (Util.isNullOrEmpty(path)) {
                        //Case not found in file system, execute the download
                        path = this.getDownloader(download.getUrl()).downloader(download.getUrl());
                    }

                    //Caches the file reference
                    if (!Util.isNullOrEmpty(path)) {
                        CacheFacade.getInstance(download.getContext()).addCache(CacheUtil.generateHash(Util.getNameFile(download.getUrl())), path);
                    }
                }

                downloadCompleted(path);

                LogWapper.i(mNameRunnable + " performed the download  " + download.getUrl());
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

<<<<<<< HEAD
        if (download.getListener() != null) {
            download.getListener().completed(download.getUrl(), path);
=======
        if (download.getListeners() != null) {
            for (DownloadCompletedListener listener: download.getListeners()) {
                listener.completed(download.getUrl(), path);
            }
>>>>>>> 8a4ee645493e56a93bd34df5a69fc661aaa63565
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
