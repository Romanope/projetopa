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
import com.squareup.picasso.Picasso;

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
                Intent intent = new Intent(ContatosActivity.this, CadastroContato.class);
                startActivity(intent);
            }
        });

        mListContatos = (ListView) findViewById(R.id.list_contatos);

        setupListView();

        registerForContextMenu(mListContatos);

    }

    @Override
    protected void onResume() {
        super.onResume();
        setupListView();
    }

    private void setupListView() {

        mContatos = new ArrayList<>();

        ContatoFacade facade = new ContatoFacade(this);
        mContatos = facade.buscaContatos();

        //criaUnsContatos(10);

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
        mContatos.get(1).setUrlFoto("http://i.imgur.com/DvpvklR.png");
        mContatos.get(2).setUrlFoto("https://pbs.twimg.com/profile_images/609439993094770690/MqfzEbtj.jpg");
        mContatos.get(3).setUrlFoto("http://decorandocasas.com.br/wp-content/uploads/2014/03/fachadas-de-casas-bonitas4.jpg");
        mContatos.get(4).setUrlFoto("http://www.fatosdesconhecidos.com.br/wp-content/uploads/2015/05/bart.gif");
        mContatos.get(5).setUrlFoto("http://www.maxiauto.com.br/assets/img/site/home-banner-carro.png");
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem deletar = menu.add("Deletar");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
                Contato contato = (Contato) mListContatos.getItemAtPosition(info.position);
                //mContatos.remove(contato);
                //Toast.makeText(ContatosActivity.this, "Contato deletado", Toast.LENGTH_LONG).show();
                //setupListView();
                return false;
            }
        });
    }
}
