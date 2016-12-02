package br.com.pa.downloaderpa.downloader;

import android.content.Context;

/**
 * Created by Romano on 02/12/2016.
 */
public abstract class Builder {

    protected Download download;

    public Download getDownload() {
        return this.download;
    }

    abstract public void buildSize();

    abstract public void buildUrl();

    abstract public void setContext();

    abstract public void setListener();

    abstract public void setGeneralAttributes();

    abstract public void setAttributes(RequestParameters parameters);
}
