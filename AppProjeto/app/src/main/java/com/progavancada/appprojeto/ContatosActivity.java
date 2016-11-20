package com.progavancada.appprojeto;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.progavancada.appprojeto.adapter.ContatosAdapter;
import com.progavancada.appprojeto.model.Contato;

import java.util.ArrayList;
import java.util.List;

public class ContatosActivity extends AppCompatActivity {

    private ContatosAdapter mContatosAdapter;
    private List<Contato> mContatos;

    private ListView mListContatos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contatos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Botao para adicionar um novo contato
            }
        });

        mListContatos = (ListView) findViewById(R.id.list_contatos);

        setupListView();

        registerForContextMenu(mListContatos);

    }

    private void setupListView() {
        mContatos = new ArrayList<>();
        criaUnsContatos(10);

        mContatosAdapter = new ContatosAdapter(this, mContatos);
        mListContatos.setAdapter(mContatosAdapter);
    }

    private void criaUnsContatos(int qnt) {
        for (int i = 0; i < qnt; i++) {
            Contato c = new Contato();
            c.setNome("Nome Qualquer " + i);
            c.setEmail("emailqualquer@email.com.br");
            mContatos.add(c);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem deletar = menu.add("Deletar");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
                Contato contato = (Contato) mListContatos.getItemAtPosition(info.position);
                mContatos.remove(contato);
                Toast.makeText(ContatosActivity.this, "Contato deletado", Toast.LENGTH_LONG);
                setupListView();
                return false;
            }
        });
    }
}
