package br.com.pa.downloaderpa.downloader;

import br.com.pa.downloaderpa.util.Constantes;
import br.com.pa.downloaderpa.util.Util;

/**
 * Created by Romano on 30/11/2016.
 *
 * Class is part of the template method
 */
public class PictureDirectory extends DownloadFile {
    @Override
    public String getDirectory(String url) {

        if (Util.isImage(Util.getTypeFile(url))) {
            return Constantes.IMAGE_DIRECTORY;
        }

        return null;
    }
}
