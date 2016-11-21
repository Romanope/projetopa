package br.com.pa.downloaderpa;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Romano on 20/11/2016.
 */
public class Util {

    private static Map<Long, ImageView> views = new HashMap<Long, ImageView>();

    private Context context;

    public void downloader(String url, ImageView view, Context context) {
        this.context = context;
        IntentFilter intentFilter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        context.registerReceiver(downloadReceiver, intentFilter);

        String nameFile = URLUtil.guessFileName(url, null, MimeTypeMap.getFileExtensionFromUrl(url));
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setTitle(nameFile);
        request.setDescription("Wait, please.");

        request.allowScanningByMediaScanner();
//        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_ONLY_COMPLETION);

        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, nameFile);
        DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        long id = manager.enqueue(request);
        if (view != null) {
            views.put(id, view);
        }
    }

    private BroadcastReceiver downloadReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(intent.getAction())) {
                long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);
                verifyDownloadStatus(id, context);
            }
        }
    };

    private void verifyDownloadStatus(long idDownload, Context context) {

        if (idDownload == 0) {
            throw new IllegalArgumentException();
        }

        DownloadManager.Query query = new DownloadManager.Query();

        query.setFilterById(idDownload);
        DownloadManager manager = (DownloadManager)context.getSystemService(Context.DOWNLOAD_SERVICE);
        Cursor cursor = manager.query(query);
        if (cursor.moveToFirst()) {

            int columnStatus = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
            if (cursor.getInt(columnStatus) == DownloadManager.STATUS_SUCCESSFUL) {
                String pathFile = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                ImageView view = views.get(idDownload);
                view.setImageURI(Uri.parse(pathFile));
            }
        }
        cursor.close();
    }
}
