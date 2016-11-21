package com.progavancada.appprojeto;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.progavancada.appprojeto.dao.ContatoDAO;
import com.progavancada.appprojeto.facade.ContatoFacade;
import com.progavancada.appprojeto.model.Contato;

public class CadastroContato extends AppCompatActivity {

    private EditText mCampoNome;
    private EditText mCampoEmail;
    private EditText mCampoURLFoto;
    private Button mButtonSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_contato);

        mCampoNome = (EditText) findViewById(R.id.edt_nome_contato);
        mCampoEmail = (EditText) findViewById(R.id.edt_email_contato);
        mCampoURLFoto = (EditText) findViewById(R.id.edt_url_foto);
        mButtonSalvar = (Button) findViewById(R.id.btn_salvar_contato);

        mButtonSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomeContato = mCampoNome.getText().toString();
                String emailContato = mCampoEmail.getText().toString();
                String urlFotoContato = mCampoURLFoto.getText().toString();

                Contato contato = new Contato(nomeContato, emailContato, urlFotoContato);

                ContatoFacade contatoFacade = new ContatoFacade(CadastroContato.this);
                contatoFacade.insere(contato);
                Toast.makeText(CadastroContato.this, "Contato salvo!", Toast.LENGTH_LONG).show();

            }
        });

    }

}
