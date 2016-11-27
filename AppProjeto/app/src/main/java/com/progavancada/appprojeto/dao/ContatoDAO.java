package com.progavancada.appprojeto.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.progavancada.appprojeto.model.Contato;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gabriel on 21/11/2016.
 */

public class ContatoDAO extends SQLiteOpenHelper implements IContatoDAO {

    public ContatoDAO(Context context) {
        super(context, "Contatos", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table contato (" +
                "id integer primary key, " +
                "nome text not null, " +
                "email text, " +
                "url_foto text);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "drop table if exists contato;";
        db.execSQL(sql);
        onCreate(db);
    }


    @Override
    public void insere(Contato contato) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nome", contato.getNome());
        contentValues.put("email", contato.getEmail());
        contentValues.put("url_foto", contato.getUrlFoto());
        writableDatabase.insert("contato", null, contentValues);
    }

    @Override
    public void remove(Contato contato) {
        String sql = "delete from contato where id=" + contato.getId();
        SQLiteDatabase writableDatabase = getWritableDatabase();
        writableDatabase.execSQL(sql);
    }

    @Override
    public void altera(Contato contato) {

    }

    @Override
    public List<Contato> buscaContatos() {

        List<Contato> contatos = new ArrayList<>();

        String sql = "select * from contato;";
        SQLiteDatabase readableDatabase = getReadableDatabase();
        Cursor cursor = readableDatabase.rawQuery(sql, null);

        while (cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndex("id"));
            String nome = cursor.getString(cursor.getColumnIndex("nome"));
            String email = cursor.getString(cursor.getColumnIndex("email"));
            String urlFoto = cursor.getString(cursor.getColumnIndex("url_foto"));
            Contato contato = new Contato(nome, email, urlFoto);
            contato.setId(id);
            contatos.add(contato);
        }

        cursor.close();

        Log.i("CONTATOS", contatos.toString());

        return contatos;
    }

    @Override
    public void fecharConexao() {
        close();
    }
}
