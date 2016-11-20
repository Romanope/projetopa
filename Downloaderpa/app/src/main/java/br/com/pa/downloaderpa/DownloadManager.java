package br.com.pa.downloaderpa;

import java.util.ArrayList;

/**
 * Created by Romano on 20/11/2016.
 */
public class DownloadManager {

    private static ArrayList<Download> downloadsPendentes = new ArrayList<Download>();

    private static ArrayList<Download> downloadsEmExecucao = new ArrayList<Download>();

    private static ArrayList<Download> downloadsFinalizados = new ArrayList<Download>();

    private static ArrayList<DownloadExecutor> executors = new ArrayList<DownloadExecutor>();

    private static boolean poolStarted;

    private static boolean deadDownloadManager;

    private static Download currentDownload;

    private static boolean isAtivo = false;

    public static void downloadImage(String url, IListenerDownload listenerDownload) {
        if (!isAtivo) {
            executeDownloads();
            isAtivo = true;
        }
        Download download = new Download(url, listenerDownload);
        downloadsPendentes.add(download);
    }

    public static void executeDownloads() {

        while (!isDeadDownloadManager()) {
            //Start pool of threads case first of execution
            if (!poolStarted) {
                startPoolThreads(3);
            }

            currentDownload = getNextDownload();
            if (currentDownload != null && currentDownload.isPendente() && !currentDownload.isEmExecucao() && !currentDownload.isFinalizado()) {
                for (DownloadExecutor executor: executors) {
                    if (executor.isDownloadTermited()) {
                        if (executor.getDownload() != null) {
                            Download dFinish = executor.getDownload();
                            dFinish.setEmExecucao(false);
                            dFinish.setFinalizado(true);
                            executor.setDownload(null);
                            downloadsFinalizados.add(dFinish);
                        }
                        executor.setDownloadTermited(false);
                        currentDownload.setPendente(false);
                        currentDownload.setEmExecucao(true);
                        executor.setDownload(currentDownload);
                        break;
                    }
                }
            } else {
                verifyDownloads();
            }

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void startPoolThreads(int numberThreads) {
        if (numberThreads > 0) {
            for (int i = 0; i < numberThreads; i++) {
                DownloadExecutor executor = new DownloadExecutor();
                executor.setName("Thread " + i);
                executor.setDownloadTermited(true);
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

    private static Download getNextDownload() {

        Download down = null;
        if (downloadsPendentes.size() > 0) {
            down = downloadsPendentes.iterator().next();
            downloadsPendentes.remove(down);
        }
        return down;
    }

    private static void verifyDownloads() {

        for (DownloadExecutor e: executors) {
            if (e != null) {
                if (e.isDownloadTermited()) {
                    if (e.getDownload() != null) {
                        Download d = e.getDownload();
                        d.setFinalizado(true);
                        d.setEmExecucao(false);
                        downloadsFinalizados.add(d);
                        e.setDownload(null);
                    }
                }
            }
        }
    }
}
