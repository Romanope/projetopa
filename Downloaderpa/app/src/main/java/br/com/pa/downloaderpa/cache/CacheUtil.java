package br.com.pa.downloaderpa.cache;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import java.io.File;

import br.com.pa.downloaderpa.util.Constantes;
import br.com.pa.downloaderpa.util.LogWapper;

/**
 * Created by Romano on 28/11/2016.
 */
public final class CacheUtil extends Application {


    private static final int NUMBER_OF_FILES_TYPE = 3;

    private static String currentPathFile = "";

    /**
     * method responsable for refreshing the cache
     */
    public static void updateCache(Context context) {

        new DataBaseManager(context).remove(null);

        for (int i = 1; i <= NUMBER_OF_FILES_TYPE; i++) {
            String[] files = getFileNamesOfFileSystem(i);
            if (files != null && files.length > 0) {
                for (int j = 0; j < files.length; j++) {
                    String hash = generateHash(files[j]);
                    addCache(hash, currentPathFile + "/" + files[j], context);
                }
            }
        }
    }

    //Argument fileType: 1 - Audio, 2- Video and 3 - image
    private static String[] getFileNamesOfFileSystem(int fileType) {

        File fDirectory = new File(Environment.getExternalStorageDirectory(), getDirectory(fileType));

        String[] names = null;
        if (fDirectory.exists()) {
            names = fDirectory.list();
            currentPathFile = fDirectory.getAbsolutePath();
        }

        return names;
    }

    private static String getDirectory(int fileType) {

        String directory = "";
        if (fileType == Constantes.AUDIO_TYPE) {
            directory += Constantes.AUDIO_DIRECTORY;
        } else if (fileType == Constantes.VIDEO_TYPE) {
            directory += Constantes.VIDEO_DIRECTORY;
        } else if (fileType == Constantes.IMAGE_TYPE) {
            directory += Constantes.IMAGE_DIRECTORY;
        }
        return directory;
    }

    public static String generateHash(String text) {

        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(text.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            LogWapper.e(e.getMessage());
        }

        return null;
    }

    /**
     * Method responsible for add reference of the file downloaded
     * @param midiaName
     * @param midiaPath
     * @param context
     */
    public static void addCache(String midiaName, String midiaPath, Context context) {
        DataBaseManager manager = new DataBaseManager(context);
        manager.addCache(midiaName, midiaPath);
    }
}