package com.progavancada.appprojeto;

import android.content.Intent;
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
import com.progavancada.appprojeto.facade.ContatoFacade;
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
                Intent intent = new Intent(ContatosActivity.this, CadastroContatoActivity.class);
                startActivity(intent);
            }
        });

        mListContatos = (ListView) findViewById(R.id.list_contatos);
        mContatos = new ArrayList<>();

        setupListView();

        registerForContextMenu(mListContatos);

    }

    @Override
    protected void onResume() {
        super.onResume();
        setupListView();
    }

    private void setupListView() {

        ContatoFacade facade = new ContatoFacade(this);
        mContatos = facade.buscaContatos();

        mContatosAdapter = new ContatosAdapter(this, mContatos);
        mListContatos.setAdapter(mContatosAdapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem deletar = menu.add("Apagar");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
                Contato contato = (Contato) mListContatos.getItemAtPosition(info.position);
                ContatoFacade facade = new ContatoFacade(ContatosActivity.this);
                facade.remove(contato);
                facade.fecharConexao();
                Toast.makeText(ContatosActivity.this, "Contato removido com sucesso", Toast.LENGTH_LONG).show();
                setupListView();
                return false;
            }
        });
    }
}
