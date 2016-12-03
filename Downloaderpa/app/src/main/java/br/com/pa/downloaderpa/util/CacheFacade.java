package br.com.pa.downloaderpa.util;

import android.app.Application;
import android.content.Context;

import br.com.pa.downloaderpa.cache.BaseHashMap;
import br.com.pa.downloaderpa.cache.DataBaseManager;
import br.com.pa.downloaderpa.cache.RepositoryManager;
import br.com.pa.downloaderpa.downloader.Downloaderpa;

/**
 * Created by Romano on 30/11/2016.
 */
public final class CacheFacade extends Application {

    private static CacheFacade facade;

    private static  Context mContext;

    private RepositoryManager manager;

    private CacheFacade() {

    }

    /**
     * Method responsible for add reference of the file downloaded
     * @param midiaName
     * @param midiaPath
     */
    public void addCache(String midiaName, String midiaPath) {

        manager = getRepo(mContext);
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

        manager = getRepo(mContext);
        manager.remove(midiaName);
    }

    public String searchFileDirectory(String midiaName) {

        manager = getRepo(mContext);
        return manager.searchFileDirectory(midiaName);
    }

    private RepositoryManager getRepo(Context context) {
        if (Downloaderpa.useCacheInMemory) {
            return new BaseHashMap();
        } else {
            return new DataBaseManager(context);
        }
    }
}
