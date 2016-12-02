package br.com.pa.downloaderpa.util;

/**
 * Created by Romano on 27/11/2016.
 */
public final class Constantes {

    private Constantes() {

    }

    public static final String   TAG_LOG = "downloaderpa";
    public static final String[] IMAGE_EXTENSIONS = {"png", "jpg", "jpeg", "bmp"};
    public static final String[] AUDIO_EXTENSIONS = {"mp3", "wma", "aac"};
    public static final String[] VIDEO_EXTENSIONS = {"avi", "mpeg", "mov", "mkv"};
    public static final String   VIDEO_DIRECTORY = "downloadpa/videos";
    public static final String   IMAGE_DIRECTORY = "downloadpa/pictures";
    public static final String   AUDIO_DIRECTORY = "downloadpa/audios";
    public static final String   DOWNLOAD_COMPLETED = "downloadCompleted";
    public static final String   EXTRA_PATH_FILE = "filePath";
    public static final int      AUDIO_TYPE = 1;
    public static final int      VIDEO_TYPE = 2;
    public static final int      IMAGE_TYPE = 3;
}
