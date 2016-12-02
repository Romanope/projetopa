package br.com.pa.downloaderpa.downloader;

import android.content.Context;
import android.widget.ImageView;

import java.util.Calendar;
import java.util.Collection;
import java.util.Map;

import br.com.pa.downloaderpa.util.Constantes;
import br.com.pa.downloaderpa.util.Util;

/**
 * Created by Romano on 02/12/2016.
 */
public class DownloadBuilder extends Builder {

    private static int id = 0;

    private Map<String, Object> attributes;

    protected DownloadBuilder() {
        this.download = new Download();
    }

    @Override
    public void buildSize() {

        if (this.attributes.get(Constantes.HEIGHT) != null && ((Integer)this.attributes.get(Constantes.HEIGHT)).intValue() > 0) {
            if (this.attributes.get(Constantes.WIDTH) != null && ((Integer)this.attributes.get(Constantes.WIDTH)).intValue() > 0) {
                //execute algorithm of manipulation properties of image
                //after set result in object download
                download.setHeight(((Integer)this.attributes.get(Constantes.HEIGHT)));
                download.setWidth(((Integer)this.attributes.get(Constantes.WIDTH)));
            }
        }
    }

    @Override
    public void buildUrl() {
        if (!Util.isNullOrEmpty((String)this.attributes.get(Constantes.URL))) {
            validateUrl((String)this.attributes.get(Constantes.URL));
            download.setUrl((String)this.attributes.get(Constantes.URL));
        }
    }

    @Override
    public void setContext() {
        if (this.attributes.get(Constantes.CONTEXT) != null) {
            download.setContext((Context)this.attributes.get(Constantes.CONTEXT));
        }
    }

    @Override
    public void setListener() {
        if (this.attributes.get(Constantes.LISTENERS) != null) {
            download.addListener((Collection<DownloadCompletedListener>) this.attributes.get(Constantes.LISTENERS));
        }
    }

    @Override
    public void setGeneralAttributes() {
        this.download.setId(id++);
        if (this.attributes.get(Constantes.IMAGE_VIEW) != null) {
            download.setImageView((ImageView) this.attributes.get(Constantes.IMAGE_VIEW));
        }
        download.setInitTime(Calendar.getInstance().getTimeInMillis());
    }

    @Override
    public void setAttributes(RequestParameters parameters) {
        this.attributes = parameters.getAttributes();
    }

    private void validateUrl(String url) {

        if (url == null) {
            throw new NullPointerException("The url argument can not be null");
        }
        if (url.trim().length() == 0) {
            throw new IllegalArgumentException("url is required");
        }

        if (!Util.urlIsValid(url)) {
            throw new IllegalArgumentException("Url is not valid");
        }
    }
}
