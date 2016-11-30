package br.com.pa.downloaderpa.downloader;

import android.app.Activity;
import android.app.Application;
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
    private Map<Integer, ImageView> mViews = new HashMap<Integer, ImageView>();
    private int mDownloadSequence = 0;
    private boolean mDisponivel;
    private static Context mContext;
    private boolean cacheUpdated;

    private static Downloaderpa downloaderpa;

    private Downloaderpa() {

    }

    /**
     * Adiciona à fila um novo download
     * @param url
     * @param imageView
     */
    public synchronized void download(String url, ImageView imageView) {

        validateUrl(url);

        while (mDisponivel) {
            try {
                wait();
            } catch (InterruptedException e) {
                LogWapper.e(e.getMessage());
                return;
            }
        }

        Download download = new Download(url, imageView, mContext);
        download.setId(mDownloadSequence++);
        mDownloadsPendentes.add(download);

        if (imageView != null) {
            mViews.put(download.getId(), imageView);
        }

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
                executor.setObserverDownload(observerDownload);
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
        if (downloaderpa == null) {
            downloaderpa = new Downloaderpa();
        }

        return downloaderpa;
    }

    private void validateUrl(String url) {

        if (url == null) {
            throw new NullPointerException("The url argument can not be null");
        }
        if (url.trim().length() == 0) {
            throw new IllegalArgumentException("url is required");
        }

        if (!Util.urlIsValid(url)) {
            throw new IllegalArgumentException("Url is not valid");
        }
    }

    /**
     * Indica ao downloader o contexto corrente para o download
     * @param c
     * @return instancia do downloadManager
     */
    public static Downloaderpa context(Context c) {

        if (c == null) {
            throw new IllegalArgumentException("Context is required");
        }

        if (downloaderpa == null) {
            downloaderpa = new Downloaderpa();
        }
        mContext = c;

        return downloaderpa;
    }

    private IObserverDownload observerDownload = new IObserverDownload() {
        @Override
        public void downloadFinish(final Download download, final String path) {

        final ImageView v = mViews.get(download.getId());
        if (v != null) {
            Thread thread = new Thread() {
                @Override
                public void run() {

                    Activity a = (Activity) download.getContext();
                    a.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            v.setImageURI(Uri.parse(path));
                        }
                    });
                }
            };
            thread.start();
            mViews.remove(download.getUrl());
        }
        }
    };

    public boolean isCacheUpdated() {
        return cacheUpdated;
    }

    public void setCacheUpdated(boolean cacheUpdated) {
        this.cacheUpdated = cacheUpdated;
    }
}
