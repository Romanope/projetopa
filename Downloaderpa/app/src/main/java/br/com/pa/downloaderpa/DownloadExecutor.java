package br.com.pa.downloaderpa;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import br.com.pa.downloaderpa.cache.DataBaseManager;

/**
 * Created by Romano on 20/11/2016.
 */
public class DownloadExecutor extends Thread {

    private Download download;

    private boolean deadThread;

    private IObserverDownload observerDownload;

    @Override
    public void run() {

        while (!deadThread) {
            download = DownloadManager.getInstance().getDownload();
            if (download != null) {

                String path = "";

                path = consultarPath(Util.getNameFile(download.getUrl()), download.getContext());
                if (path == null || path.trim().length() == 0) {
                    path = Util.downloaderWithConnector(download.getUrl());
                    addCache(Util.getNameFile(download.getUrl()), path, download.getContext());
                }

                Intent i = new Intent(Util.DOWNLOAD_COMPLETED);
                i.putExtra(Util.EXTRA_PATH_FILE, path);
                download.getContext().sendBroadcast(i);

                if (Util.isImage(Util.getTypeFile(download.getUrl()))) {
                    observerDownload.downloadFinish(download, path);
                }
                Log.i("Downloadpa", this.getName() + " executou o download " + download.getUrl());
            }
        }
    }

    public Download getDownload() {

        return download;
    }

    public void setDownload(Download download) {

        this.download = download;
    }

    public boolean isDeadThread() {

        return deadThread;
    }

    public void setDeadThread(boolean deadThread) {

        this.deadThread = deadThread;
    }

    public IObserverDownload getObserverDownload() {
        return observerDownload;
    }

    public void setObserverDownload(IObserverDownload observerDownload) {
        this.observerDownload = observerDownload;
    }

    private String consultarPath(String midiaName, Context context) {
        String retorno = "";
        if (midiaName != null) {
            DataBaseManager manager = new DataBaseManager(context);
            retorno = manager.consultarPathMidia(midiaName);
        }
        return retorno;
    }

    private void addCache(String midiaName, String midiaPath, Context context) {
        DataBaseManager manager = new DataBaseManager(context);
        manager.addCache(midiaName, midiaPath);
    }
}
