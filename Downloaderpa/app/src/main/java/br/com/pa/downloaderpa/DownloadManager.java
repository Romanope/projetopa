package br.com.pa.downloaderpa;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import br.com.pa.downloaderpa.cache.DataBaseManager;

/**
 * Created by Romano on 20/11/2016.
 */
public class DownloadManager {

    protected static ArrayList<Download> downloadsPendentes = new ArrayList<Download>();
    private static ArrayList<DownloadExecutor> executors = new ArrayList<DownloadExecutor>();
    private static boolean poolStarted;
    private static boolean deadDownloadManager;
    private static Map<Integer, ImageView> views = new HashMap<Integer, ImageView>();
    private static int downloadSequence = 0;
    private static boolean disponivel;

    private static DownloadManager downloadManager;

    private static IObserverDownload observerDownload = new IObserverDownload() {
        @Override
        public void downloadFinish(final Download download, final String path) {

            final ImageView v = views.get(download.getId());
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
                views.remove(download.getUrl());
            }
        }
    };

    public synchronized void downloadImage(String url, ImageView imageView, Context context) {

        if (downloadManager == null) {
            downloadManager = new DownloadManager();
        }

        while (disponivel) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (url == null || url.trim().length() == 0) {
            throw new IllegalArgumentException("url is required");
        }

        Download download = new Download(url, imageView, context);
        download.setId(downloadSequence++);
        downloadsPendentes.add(download);

        if (imageView != null) {
            views.put(download.getId(), imageView);
        }

        if (!poolStarted) {
            startPoolThreads();
        }
        disponivel = true;
        notifyAll();
    }

    private static void startPoolThreads() {
        int numbersOfThreads = Util.getNumberOfCores();
        if (numbersOfThreads > 0) {
            for (int i = 0; i < numbersOfThreads; i++) {
                DownloadExecutor executor = new DownloadExecutor();
                executor.setName("Thread " + i);
                executor.setObserverDownload(observerDownload);
                executor.start();
                executors.add(executor);
            }

            poolStarted = true;
        }
    }

    public static boolean isDeadDownloadManager() {

        return deadDownloadManager;
    }

    public static void setDeadDownloadManager(boolean deadDownloadManager) {
        DownloadManager.deadDownloadManager = deadDownloadManager;
    }

    public synchronized Download getDownload() {

        Download download = null;
        while (!disponivel) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (downloadsPendentes.size() > 0) {
            download = downloadsPendentes.iterator().next();
            downloadsPendentes.remove(download);
        }
        disponivel = false;
        notifyAll();
        return download;
    }

    public static DownloadManager getInstance() {
        if (downloadManager == null) {
            downloadManager = new DownloadManager();
        }

        return downloadManager;
    }
}
