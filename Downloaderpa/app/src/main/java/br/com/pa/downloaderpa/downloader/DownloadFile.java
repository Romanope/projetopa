package br.com.pa.downloaderpa.downloader;

import android.os.Environment;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import br.com.pa.downloaderpa.util.LogWrapper;
import br.com.pa.downloaderpa.util.Util;

/**
 * Created by Romano on 29/11/2016.
 */
public abstract class DownloadFile {

    public abstract String getDirectory(String url);

    /**
     * Método responsável por realizar o download do arquivo
     * @param url
     * @return diretório onde o arquivo baixado foi salvo
     */
    public String downloader(String url) {

        try {

            URL urlDownload = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlDownload.openConnection();
            connection.setDoInput(true);
            connection.setRequestMethod("GET");
            connection.connect();

            String subDir = getDirectory(url);
            if (Util.isNullOrEmpty(subDir)) {
                throw new NullPointerException("sub directory not found");
            }
            File directory = new File(Environment.getExternalStorageDirectory(), subDir);
            if (!directory.exists()) {
                directory.mkdir();
                
            }

            //some times the directory not is created using the method .mkdirs
            if (!directory.exists()) {
                directory.mkdir();
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

            return fileDownloaded.getPath();

        } catch (MalformedURLException e) {

            LogWrapper.e(e.getMessage());
        } catch (IOException e) {
            LogWrapper.e(e.getMessage());
        }

        return null;
    }
}
