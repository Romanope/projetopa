package br.com.pa.downloaderpa.downloader;

/**
 * Created by Romano on 02/12/2016.
 */
public class FactoryDownload {

    private static RequestParameters requestParameters;

    private static FactoryDownload factoryDownload;

    public static FactoryDownload getInstance(RequestParameters request) {
        if (factoryDownload == null) {
            factoryDownload = new FactoryDownload();
        }
        requestParameters = request;
        return factoryDownload;
    }

    public void toBuild(Builder builder) {
        builder.setAttributes(requestParameters);
        builder.buildSize();
        builder.buildUrl();
        builder.setContext();
        builder.setListener();
        builder.setGeneralAttributes();
    }
}
