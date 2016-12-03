package com.progavancada.appprojeto.model;

import java.io.Serializable;

/**
 * Created by gabriel on 19/11/2016.
 */

public class Musica implements Serializable {

    private long id;
    private String nome;
    private String autor;
    private String urlMusica;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getUrlMusica() {
        return urlMusica;
    }

    public void setUrlMusica(String urlMusica) {
        this.urlMusica = urlMusica;
    }
}
