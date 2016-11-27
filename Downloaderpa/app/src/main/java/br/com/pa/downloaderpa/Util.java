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
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.widget.ImageView;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by Romano on 20/11/2016.
 */
public class Util {

    private static Map<Long, ImageView> views = new HashMap<Long, ImageView>();

    private static final String[] IMAGE_EXTENSIONS = {"png", "jpg", "jpeg", "bmp"};
    private static final String[] AUDIO_EXTENSIONS = {"mp3", "wma", "aac"};
    private static final String[] VIDEO_EXTENSIONS = {"avi", "mpeg", "mov", "mkv"};
    private static final String VIDEO_DIRECTORY = "downloadpa/videos";
    private static final String IMAGE_DIRECTORY = "downloadpa/pictures";
    private static final String AUDIO_DIRECTORY = "downloadpa/audios";
    public static final String DOWNLOAD_COMPLETED = "downloadCompleted";
    public static final String EXTRA_PATH_FILE = "filePath";

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

    public static String downloaderWithConnector(String url) {

        try {

            URL urlDownload = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlDownload.openConnection();
            connection.setDoInput(true);
            connection.setRequestMethod("GET");
            connection.connect();

            File directory = new File(Environment.getExternalStorageDirectory(), getDirectory(url));
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
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }


    private BroadcastReceiver downloadReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(intent.getAction())) {
                long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);
                //verifyDownloadStatus(id, context);
            }
        }
    };

    public static String getDirectory(String url) {

        String fileExtension = getTypeFile(url);
        if (isAudio(fileExtension)) {
            return AUDIO_DIRECTORY;
        } else if (isImage(fileExtension)) {
            return IMAGE_DIRECTORY;
        } else if (isVideo(fileExtension)) {
            return VIDEO_DIRECTORY;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public static String getTypeFile(String url) {

        return MimeTypeMap.getFileExtensionFromUrl(url);
    }

    public static boolean isImage(String extension) {

        for (int i = 0; i < IMAGE_EXTENSIONS.length; i++) {
            if (IMAGE_EXTENSIONS[i].equalsIgnoreCase(extension)) {
                return true;
            }
        }

        return false;
    }

    public static boolean isAudio(String extension) {

        for (int i = 0; i < AUDIO_EXTENSIONS.length; i++) {
            if (AUDIO_EXTENSIONS[i].equalsIgnoreCase(extension)) {
                return true;
            }
        }

        return false;
    }

    public static boolean isVideo(String extension) {

        for (int i = 0; i < VIDEO_EXTENSIONS.length; i++) {
            if (VIDEO_EXTENSIONS[i].equalsIgnoreCase(extension)) {
                return true;
            }
        }

        return false;
    }

    public static boolean isNullOrEmpty(String str) {
        if (str == null || str.trim().length() == 0) {
            return true;
        }
        return false;
    }

    public static String getNameFile(String url) {
        return URLUtil.guessFileName(url, null, MimeTypeMap.getFileExtensionFromUrl(url));
    }

    public static int getNumberOfCores() {
        if(Build.VERSION.SDK_INT >= 17) {
            return Runtime.getRuntime().availableProcessors();
        }
        else {
            // Use saurabh64's answer
            return getNumCoresOldPhones();
        }
    }

    /**
     * Gets the number of cores available in this device, across all processors.
     * Requires: Ability to peruse the filesystem at "/sys/devices/system/cpu"
     * @return The number of cores, or 1 if failed to get result
     */
    private  static int getNumCoresOldPhones() {
        class CpuFilter implements FileFilter {
            @Override
            public boolean accept(File pathname) {
                if (Pattern.matches("cpu[0-9]+", pathname.getName())) {
                    return true;
                }
                return false;
            }
        }

        try {

            File dir = new File("/sys/devices/system/cpu/");
            File[] files = dir.listFiles(new CpuFilter());
            return files.length;

        } catch (Exception e) {
            return 1;
        }
    }
}
