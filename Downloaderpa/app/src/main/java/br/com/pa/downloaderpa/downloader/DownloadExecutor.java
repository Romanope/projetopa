package br.com.pa.downloaderpa.downloader;

import android.content.Context;
import android.content.Intent;

import br.com.pa.downloaderpa.cache.CacheUtil;
import br.com.pa.downloaderpa.cache.DataBaseManager;
import br.com.pa.downloaderpa.util.Constantes;
import br.com.pa.downloaderpa.util.LogWapper;
import br.com.pa.downloaderpa.util.Util;

/**
 * Created by Romano on 20/11/2016.
 */
public class DownloadExecutor implements Runnable {

    private Download download;

    private boolean deadThread;

    private IObserverDownload observerDownload;

    private String mNameRunnable;

    @Override
    public void run() {

        while (!deadThread) {
            download = Downloaderpa.getInstance().getDownload();
            if (download != null) {
                if (!Downloaderpa.getInstance().isCacheUpdated()) {
                    CacheUtil.updateCache(download.getContext());
                    Downloaderpa.getInstance().setCacheUpdated(true);
                }

                String path = consultarPath(CacheUtil.generateHash(Util.getNameFile(download.getUrl())), download.getContext());
                if (Util.isNullOrEmpty(path)) {
                    path = Util.toSearchFile(download.getUrl());
                    if (Util.isNullOrEmpty(path)) {
                        path = Util.downloader(download.getUrl());
                    }
                    if (!Util.isNullOrEmpty(path)) {
                        CacheUtil.addCache(CacheUtil.generateHash(Util.getNameFile(download.getUrl())), path, download.getContext());
                    }
                }

                Intent i = new Intent(Constantes.DOWNLOAD_COMPLETED);
                i.putExtra(Constantes.EXTRA_PATH_FILE, path);
                download.getContext().sendBroadcast(i);

                if (Util.isImage(Util.getTypeFile(download.getUrl()))) {
                    observerDownload.downloadFinish(download, path);
                }
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

    protected IObserverDownload getObserverDownload() {
        return observerDownload;
    }

    protected void setObserverDownload(IObserverDownload observerDownload) {
        this.observerDownload = observerDownload;
    }

    //Verifica se o arquivo está em cache
    private String consultarPath(String midiaName, Context context) {
        String retorno = "";
        if (midiaName != null) {
            DataBaseManager manager = new DataBaseManager(context);
            retorno = manager.consultarPathMidia(midiaName);
        }
        return retorno;
    }

    public void setNameRunnable(String name) {

        this.mNameRunnable = name;
    }

}
