package br.com.pa.downloaderpa;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Created by Romano on 20/11/2016.
 */
public class DownloadExecutor extends Thread {

    private Download download;

    private boolean deadThread;

    private boolean downloadTermited;

    @Override
    public void run() {

        while (!deadThread) {
            if (!downloadTermited) {
                new Util().downloader(download.getUrl(), download.getImageView(), download.getContext());
                downloadTermited = true;
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

    public boolean isDownloadTermited() {
        return downloadTermited;
    }

    public void setDownloadTermited(boolean downloadTermited) {
        this.downloadTermited = downloadTermited;
    }
}
