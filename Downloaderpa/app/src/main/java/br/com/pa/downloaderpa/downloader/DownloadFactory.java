package br.com.pa.downloaderpa.downloader;

import android.content.Context;
import android.widget.ImageView;

/**
 * Created by Romano on 01/12/2016.
 */
public class DownloadFactory {

    private static DownloadFactory mDownloadFactory;

    private int mIdentifier = 0;

    public Download getDownload(String url, ImageView imageView, IListenerDownloadCompleted listener, Context context) {

        Download download = new Download();
        download.setId(mIdentifier++);
        download.setUrl(url);
        download.setContext(context);
        download.setListener(listener);
        download.setImageView(imageView);

        return download;
    }

    protected class Download {

        private int id;

        private String url;

        private int position;

        private ImageView imageView;

        private Context context;

        private IListenerDownloadCompleted listener;

        private Download() {

        }

        public String getUrl() {

            return url;
        }

        public void setUrl(String url) {

            if (url == null) {
                throw new NullPointerException("The url to download is null");
            }

            if (url.trim().length() == 0) {
                throw new IllegalArgumentException("The url to download is empty");
            }

            this.url = url;
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public ImageView getImageView() {
            return imageView;
        }

        public void setImageView(ImageView imageView) {
            this.imageView = imageView;
        }

        public Context getContext() {
            return context;
        }

        public void setContext(Context context) {

            if (context == null) {
                throw new NullPointerException("The context received is null");
            }
            this.context = context;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public IListenerDownloadCompleted getListener() {
            return listener;
        }

        public void setListener(IListenerDownloadCompleted listener) {
            this.listener = listener;
        }
    }

    public static DownloadFactory getInstance() {
        if (mDownloadFactory == null) {
            mDownloadFactory = new DownloadFactory();
        }

        return mDownloadFactory;
    }
}
