package com.progavancada.appprojeto.Video;

import java.io.Serializable;

/**
 * Created by gabriel on 19/11/2016.
 */
public class Video implements Serializable {

    private long id;
    private String nome;
    private String descricao;
    private String urlVideo;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {return nome;}

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getUrlVideo() {
        return urlVideo;
    }

    public void setUrlVideo(String urlVideo) {
        this.urlVideo = urlVideo;
    }
}
