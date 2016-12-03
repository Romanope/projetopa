package br.com.pa.downloaderpa.cache;

/**
 * Created by Romano on 03/12/2016.
 */
public interface RepositoryManager {

    public void remove(String midiaName);

    public String searchFileDirectory(String midiaName);

    public void addCache(String midiaName, String midiaPath);
}
