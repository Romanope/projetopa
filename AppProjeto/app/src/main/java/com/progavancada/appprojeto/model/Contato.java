package com.progavancada.appprojeto.model;

/**
 * Created by gabriel on 19/11/2016.
 */

public class Contato {

    private long id;
    private String nome;
    private String email;
    private String urlFoto;

    public Contato(String nome, String email, String urlFoto) {
        this.nome = nome;
        this.email = email;
        this.urlFoto = urlFoto;
    }

    public Contato() {}

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    @Override
    public String toString() {
        return this.urlFoto;
    }
}
