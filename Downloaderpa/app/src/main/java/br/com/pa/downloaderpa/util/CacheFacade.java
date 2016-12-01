package br.com.pa.downloaderpa.util;

import android.app.Application;
import android.content.Context;

import br.com.pa.downloaderpa.cache.DataBaseManager;

/**
 * Created by Romano on 30/11/2016.
 */
public final class CacheFacade extends Application {

    private static CacheFacade facade;

    private static Context mContext;

    private CacheFacade() {

    }

    /**
     * Method responsible for add reference of the file downloaded
     * @param midiaName
     * @param midiaPath
     */
    public void addCache(String midiaName, String midiaPath) {

        DataBaseManager manager = new DataBaseManager(mContext);
        manager.addCache(midiaName, midiaPath);
    }

    public static CacheFacade getInstance(Context context) {
        if (facade == null) {
            facade = new CacheFacade();
        }
        mContext = context;
        return facade;
    }

    /**
     * Method responsible for remove item of cache.
     * if the param midiaName equals null, all items removed
     * @param midiaName
     */
    public void removeOfCache(String midiaName) {

        DataBaseManager manager = new DataBaseManager(mContext);
        manager.remove(midiaName);
    }

    public String searchFileDirectory(String midiaName) {

        DataBaseManager manager = new DataBaseManager(mContext);
        return manager.searchFileDirectory(midiaName);
    }
}
