package br.com.pa.downloaderpa.util;

import android.util.Log;

/**
 * Created by Romano on 27/11/2016.
 */
public class LogWapper {

    private LogWapper() { // LogWrapper******

    }

    public static void e(String msg) {
        Log.e(Constantes.TAG_LOG, msg);
    }

    public static void i(String msg) {
        Log.i(Constantes.TAG_LOG, msg);
    }

    public static void v(String msg) {
        Log.v(Constantes.TAG_LOG, msg);
    }

}
