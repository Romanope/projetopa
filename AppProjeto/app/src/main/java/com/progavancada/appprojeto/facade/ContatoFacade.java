package com.progavancada.appprojeto.facade;

import android.content.Context;

import com.progavancada.appprojeto.dao.ContatoDAO;
import com.progavancada.appprojeto.dao.IContatoDAO;
import com.progavancada.appprojeto.model.Contato;

import java.util.List;

/**
 * Created by gabriel on 21/11/2016.
 */

public class ContatoFacade implements IContatoDAO {

    private IContatoDAO contatoDAO;

    public ContatoFacade(Context context) {
        contatoDAO = new ContatoDAO(context);
    }

    @Override
    public void insere(Contato contato) {
        contatoDAO.insere(contato);
    }

    @Override
    public void remove(Contato contato) {
        contatoDAO.remove(contato);
    }

    @Override
    public void altera(Contato contato) {
        contatoDAO.altera(contato);
    }

    @Override
    public List<Contato> buscaContatos() {
        return contatoDAO.buscaContatos();
    }

    @Override
    public void fecharConexao() {
        contatoDAO.fecharConexao();
    }
}
