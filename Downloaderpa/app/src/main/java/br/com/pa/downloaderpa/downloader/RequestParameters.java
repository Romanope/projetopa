package br.com.pa.downloaderpa.downloader;

import android.content.Context;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import br.com.pa.downloaderpa.util.Constantes;
import br.com.pa.downloaderpa.util.Util;

/**
 * Created by Romano on 02/12/2016.
 */
public class RequestParameters {

    private Map<String, Object> attributes;

    protected RequestParameters() {
        this.attributes = new HashMap<String, Object>();
        Collection<DownloadCompletedListener> listeners = new ArrayList<DownloadCompletedListener>();
        this.putAttribute(Constantes.LISTENERS, listeners);
    }

    protected Map<String, Object> getAttributes() {
        return attributes;
    }

    private RequestParameters putAttribute(String key, Object value) {

        if (this.attributes == null) {
            attributes = new HashMap<String, Object>();
        }

        this.attributes.put(key, value);

        return this;
    }

    public RequestParameters url(String url) {
        this.putAttribute(Constantes.URL, url);
        return this;
    }

    public RequestParameters size(int height, int width) {
        if (height == 0 ||  width == 0) {
            throw new IllegalArgumentException("dimension not equals zero");
        }

        this.putAttribute(Constantes.HEIGHT, height);
        this.putAttribute(Constantes.WIDTH, width);
        return this;
    }

    protected RequestParameters setContext(Context context) {

        if (context == null) {
            throw new NullPointerException("context equals null");
        }

        this.putAttribute(Constantes.CONTEXT, context);

        return this;
    }

    public RequestParameters setImageView(ImageView imageView) {

        if (imageView == null) {
            throw new NullPointerException("imageView equals null");
        }

        this.putAttribute(Constantes.IMAGE_VIEW, imageView);

        return this;
    }

    public RequestParameters registerListener(DownloadCompletedListener listener) {

        if (listener == null) {
            throw new NullPointerException("listener equals null");
        }

        if (this.attributes.get(Constantes.LISTENERS) != null) {
            Collection<DownloadCompletedListener> listeners = (ArrayList<DownloadCompletedListener>)this.attributes.get(Constantes.LISTENERS);
            listeners.add(listener);
            this.putAttribute(Constantes.LISTENERS, listeners);
        }
        return this;
    }

    public void startDownload() {

        Builder builder = new DownloadBuilder();
        FactoryDownload.getInstance(this).toBuild(builder);

        Downloaderpa.getInstance().download(builder.getDownload());
    }
}
