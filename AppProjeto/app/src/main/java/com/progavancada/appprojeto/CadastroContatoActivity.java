package com.progavancada.appprojeto;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.progavancada.appprojeto.facade.ContatoFacade;
import com.progavancada.appprojeto.model.Contato;
import com.progavancada.appprojeto.util.ImageUtil;
import com.progavancada.appprojeto.util.Utils;

import java.io.File;

public class CadastroContatoActivity extends AppCompatActivity {

    private EditText mCampoNome;
    private EditText mCampoEmail;
    private EditText mCampoURLFoto;
    private ImageView mPreviewFoto;
    private Button mButtonSalvar;
    private Button mButtonCarregarFoto;
    private Button mButtonTirarFoto;

    private String mCaminhoFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_contato);

        mCampoNome = (EditText) findViewById(R.id.edt_nome_contato);
        mCampoEmail = (EditText) findViewById(R.id.edt_email_contato);
        mCampoURLFoto = (EditText) findViewById(R.id.edt_url_foto);
        mPreviewFoto = (ImageView) findViewById(R.id.preview_foto);
        mButtonSalvar = (Button) findViewById(R.id.btn_salvar_contato);
        mButtonCarregarFoto = (Button) findViewById(R.id.btn_carregar_foto);
        mButtonTirarFoto = (Button) findViewById(R.id.btn_tirar_foto);

        mButtonSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomeContato = mCampoNome.getText().toString();
                String emailContato = mCampoEmail.getText().toString();
                String urlFotoContato = (String) mPreviewFoto.getTag();

                Contato contato = new Contato(nomeContato, emailContato, urlFotoContato);

                ContatoFacade contatoFacade = new ContatoFacade(CadastroContatoActivity.this);
                contatoFacade.insere(contato);
                contatoFacade.fecharConexao();

                Toast.makeText(CadastroContatoActivity.this, "Contato salvo!", Toast.LENGTH_LONG).show();

            }
        });

        mButtonTirarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                mCaminhoFoto = getExternalFilesDir(null) + "/" + System.currentTimeMillis() + ".jpg";
                File arquivoFoto = new File(mCaminhoFoto);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(arquivoFoto));
                startActivityForResult(intent, Utils.CODIGO_CAMERA);
            }
        });

        mButtonCarregarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = mCampoURLFoto.getText().toString();
                try{
                    ImageUtil.carregarImagem(CadastroContatoActivity.this, url, 64, 64, mPreviewFoto);
                    mPreviewFoto.setTag(url);
                } catch (IllegalArgumentException e) {
                    Toast.makeText(CadastroContatoActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                } catch (NullPointerException e) {
                    Toast.makeText(CadastroContatoActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Utils.CODIGO_CAMERA && resultCode == Activity.RESULT_OK) {
            Bitmap bitmap = BitmapFactory.decodeFile(mCaminhoFoto);
            Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 64, 64, true);
            mPreviewFoto.setImageBitmap(bitmapReduzido);
            mPreviewFoto.setTag(mCaminhoFoto);
        }
    }
}
