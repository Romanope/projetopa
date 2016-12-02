package br.com.pa.downloaderpa.downloader;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import br.com.pa.downloaderpa.util.LogWapper;
import br.com.pa.downloaderpa.util.Util;

/**
 * Created by Romano on 20/11/2016.
 */
public class Downloaderpa {

    private ArrayList<Download> mDownloadsPendentes = new ArrayList<Download>();
    private boolean mPoolStarted;
    private boolean mDisponivel;
    private boolean mCacheUpdated;
    private static RequestParameters requestParameters;


    private static Downloaderpa mDownloaderpa;

    private Downloaderpa() {

    }

    /**    * Adiciona à fila um novo download
     */
    protected synchronized void download(Download download) {

        while (mDisponivel) {
            try {
                wait();
            } catch (InterruptedException e) {
                LogWapper.e(e.getMessage());
                return;
            }
        }

        mDownloadsPendentes.add(download);

        if (!mPoolStarted) {
            startPoolThreads();
        }
        mDisponivel = true;
        notifyAll();
    }

    //Método responsável por instanciar o pool de threads
    private void startPoolThreads() {
        int numbersOfThreads = Util.getNumberOfCores();
        if (numbersOfThreads > 0) {
            for (int i = 0; i < numbersOfThreads; i++) {
                DownloadExecutor executor = new DownloadExecutor();
                executor.setNameRunnable("Thread " + i);
                Thread t = new Thread(executor);
                t.start();
            }
            mPoolStarted = true;
        }
    }

    public synchronized Download getDownload() {

        Download download = null;
        while (!mDisponivel) {
            try {
                wait();
            } catch (InterruptedException e) {
                LogWapper.e(e.getMessage());
            }
        }
        if (mDownloadsPendentes.size() > 0) {
            download = mDownloadsPendentes.iterator().next();
            mDownloadsPendentes.remove(download);
        }
        mDisponivel = false;
        notifyAll();
        return download;
    }

    protected static Downloaderpa getInstance() {
        if (mDownloaderpa == null) {
            mDownloaderpa = new Downloaderpa();
        }

        return mDownloaderpa;
    }

    /**
     * Indica ao downloader o contexto corrente para o download
     * @param c
     * @return instancia do downloadManager
     */
    public static RequestParameters context(Context c) {

        if (c == null) {
            throw new IllegalArgumentException("Context is required");
        }

        if (mDownloaderpa == null) {
            mDownloaderpa = new Downloaderpa();
        }

        requestParameters = new RequestParameters();
        requestParameters.setContext(c);

        return requestParameters;
    }

    protected void refreshingView(final Download download, final String path) {

        if (download.getImageView() != null) {
            Thread thread = new Thread() {
                @Override
                public void run() {

                    Activity a = (Activity) download.getContext();
                    a.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            download.getImageView().setImageURI(Uri.parse(path));
                        }
                    });
                }
            };
            thread.start();
        }
    }

    public boolean isCacheUpdated() {
        return mCacheUpdated;
    }

    public void setCacheUpdated(boolean cacheUpdated) {
        this.mCacheUpdated = cacheUpdated;
    }
}
