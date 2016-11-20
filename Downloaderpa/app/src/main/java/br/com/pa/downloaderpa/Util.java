package br.com.pa.downloaderpa;

import android.app.DownloadManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Romano on 20/11/2016.
 */
public class Util extends AppCompatActivity {

    public static Bitmap downloader(String url) {

        Bitmap image = null;
        try {
            URL urlDownload = new URL(url);

            HttpURLConnection connection = (HttpURLConnection) urlDownload.openConnection();
            connection.setDoInput(true);
            connection.setRequestMethod("GET");
            connection.connect();

            File directory = new File(Environment.DIRECTORY_DOWNLOADS, "profiles");
            if (!directory.exists()) {
                directory.mkdirs();
            }

            String fileName = URLUtil.guessFileName(url, null, MimeTypeMap.getFileExtensionFromUrl(url));

            File fileDownloaded = new File(directory, fileName);
            fileDownloaded.createNewFile();

            InputStream inputStream = connection.getInputStream();

            FileOutputStream outputStream = new FileOutputStream(fileDownloaded);

            byte[] buffer = new byte[1024];
            int count = 0;

            while ((count = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, count);
            }

            outputStream.close();

            String pathDownload = directory.getAbsolutePath() + fileName;

            image = BitmapFactory.decodeFile(pathDownload);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }
}
