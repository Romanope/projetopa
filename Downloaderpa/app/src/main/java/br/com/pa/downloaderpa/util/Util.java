package br.com.pa.downloaderpa.util;

import android.os.Build;
import android.os.Environment;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Pattern;

import br.com.pa.downloaderpa.downloader.AudioDirectory;
import br.com.pa.downloaderpa.downloader.DownloadFile;
import br.com.pa.downloaderpa.downloader.PictureDirectory;
import br.com.pa.downloaderpa.downloader.VideoDirectory;

/**
 * Created by Romano on 20/11/2016.
 */
public class Util {

    /**
     * Método responsável por realizar o download do arquivo
     * @param url
     * @return diretório onde o arquivo baixado foi salvo
     */
    public static String downloader(String url) {

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
            LogWapper.e(e.getMessage());
        } catch (IOException e) {
            LogWapper.e(e.getMessage());
        }

        return null;
    }

    /**
     * Método responsável por obter o diretório
     * onde o arquivo será salvo.
     * @param url
     * @return diretório onde o arquivo será salvo
     */
    public static String getDirectory(String url) {

        String fileExtension = getTypeFile(url);
        if (isAudio(fileExtension)) {
            return Constantes.AUDIO_DIRECTORY;
        } else if (isImage(fileExtension)) {
            return Constantes.IMAGE_DIRECTORY;
        } else if (isVideo(fileExtension)) {
            return Constantes.VIDEO_DIRECTORY;
        } else {
            throw new IllegalArgumentException("not mapped extension file");
        }
    }

    /**
     * Método responsável por obter a extensão do arquivo
     * @param url
     * @return extensão do arquivo
     */
    public static String getTypeFile(String url) {

        return url.substring(url.lastIndexOf('.')+1);
    }

    /**
     * Método responsável verificar se o arquivo é uma imagem
     * @param extension
     * @return Verdadeiro, caso seja uma imagem, do contrário false.
     */
    public static boolean isImage(String extension) {

        for (int i = 0; i < Constantes.IMAGE_EXTENSIONS.length; i++) {
            if (Constantes.IMAGE_EXTENSIONS[i].equalsIgnoreCase(extension)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Verifica se o arquivo é um áudio
     * @param extension
     * @return verdadeiro, caso o arquivo seja um áudio
     */
    public static boolean isAudio(String extension) {

        for (int i = 0; i < Constantes.AUDIO_EXTENSIONS.length; i++) {
            if (Constantes.AUDIO_EXTENSIONS[i].equalsIgnoreCase(extension)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Método responsável por verificar se o arquivo é vídeo
     * @param extension
     * @return verdadeiro, caso arquivo seja vídeo
     */
    public static boolean isVideo(String extension) {

        for (int i = 0; i < Constantes.VIDEO_EXTENSIONS.length; i++) {
            if (Constantes.VIDEO_EXTENSIONS[i].equalsIgnoreCase(extension)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Método responsável por verificar se a
     * string é vazia ou nula
     * @param str
     * @return verdadeiro, caso a string seja vazia ou nula, caso não,
     * retorna falso
     */
    public static boolean isNullOrEmpty(String str) {
        if (str == null || str.trim().length() == 0) {
            return true;
        }
        return false;
    }

    /**
     * Método responsável por obter o nome do arquivo
     * @param url
     * @return nome do arquivo
     */
    public static String getNameFile(String url) {
        return url.substring(url.lastIndexOf("/")+1);
    }

    /**
     * Método responsável por obter o número de núcleos do processador
     * @return número de núcloes do processador
     */
    public static int getNumberOfCores() {
        if(Build.VERSION.SDK_INT >= 17) {
            return Runtime.getRuntime().availableProcessors();
        }
        else {
            // Use saurabh64's answer
            return getNumCoresOldPhones();
        }
    }

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

    /**
     * Método responsável por verificar se a url
     * é uma url válida para realizar o download
     * @param url
     * @return retorna verdadeiro, caso a url seja válida, caso não,
     * retorna falso.
     */
    public static boolean urlIsValid(String url) {

        boolean retorno = false;

        String name = getNameFile(url);

        if (name == null) {
            throw new IllegalArgumentException("Url is not valid");
        }

        if (name.indexOf('.') == 0) {
            throw new IllegalArgumentException("Url is not valid");
        }

        String extension = "";
        if (name.indexOf('.') > 0) {
            extension = name.substring(name.lastIndexOf('.') + 1);
        }

        if (isImage(extension))
            retorno = true;
        else if (isVideo(extension))
            retorno = true;
        else if (isAudio(extension))
            retorno = true;

        return retorno;
    }

    /**
     * Method responsible for to search file in FileSystem.
     * @param url
     * @return path of file, case exists.
     */
    public static String toSearchFile(String url) {

        String directory = Environment.getExternalStorageDirectory() + "/" + Util.getDirectory(url);
        File file = new File(directory);
        String fileName = Util.getNameFile(url);
        if (file.exists()) {
            String[] names = file.list();
            if (names != null) {
                for (int i = 0; i < names.length; i++) {
                    if (names[i].equalsIgnoreCase(fileName)) {
                        return directory + "/" + fileName;
                    }
                }
            }
        }

        return null;
    }

    public static DownloadFile[] getDownloaders() {

        DownloadFile[] downloadFiles = new DownloadFile[3];

        downloadFiles[0] = new PictureDirectory();
        downloadFiles[1] = new AudioDirectory();
        downloadFiles[2] = new VideoDirectory();

        return downloadFiles;
    }
}
