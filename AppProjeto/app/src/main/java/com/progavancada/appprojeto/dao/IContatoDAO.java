package com.progavancada.appprojeto.dao;

import com.progavancada.appprojeto.model.Contato;

import java.util.List;

/**
 * Created by gabriel on 21/11/2016.
 */

public interface IContatoDAO {

    void insere(Contato contato);
    void remove(Contato contato);
    void altera(Contato contato);
    List<Contato> buscaContatos();

}
