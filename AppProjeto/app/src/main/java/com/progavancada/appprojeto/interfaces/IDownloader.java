package com.progavancada.appprojeto.interfaces;

import com.progavancada.appprojeto.model.Musica;
import com.progavancada.appprojeto.model.Video;

/**
 * Created by Lucas on 20/11/2016.
 */

public interface IDownloader {

    public Musica baixarMusica ();
    public Video baixarVideo ();

}
