package br.com.pa.downloaderpa.cache;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Romano on 24/11/2016.
 */
public class DataBaseManager extends SQLiteOpenHelper {

    private static final String NAME = "Downloaderpa";

    private static final int VERSION = 1;

    private static final String CACHE_TABLE = "CACHE";

    private static final String CD_CACHE = "CD_CACHE";

    private static final String MIDIA_NAME = "MIDIA_NAME";

    private static final String MIDIA_PATH = "MIDIA_PATH";

    public DataBaseManager(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        StringBuilder sql = new StringBuilder();
        sql.append("create table if not exists " + CACHE_TABLE + " ( ")
           .append(CD_CACHE + " Integer primary key autoincrement, ")
           .append(MIDIA_NAME + " text, ")
           .append(MIDIA_PATH + " text ) ");
        
        sqLiteDatabase.execSQL(sql.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CACHE_TABLE);
        onCreate(sqLiteDatabase);
    }

    public void addCache(String midiaName, String midiaPath) {

        validarDados(midiaName, midiaPath);

        SQLiteDatabase writable = getWritableDatabase();
        ContentValues container = new ContentValues();
        container.put(MIDIA_NAME, midiaName);
        container.put(MIDIA_PATH, midiaPath);
        writable.insert(CACHE_TABLE, null, container);
    }

    public String searchFileDirectory(String midiaName) {

        String retorno = "";

        String query = "select midia_path from cache where midia_name = '" + midiaName + "'";
        SQLiteDatabase readable = getReadableDatabase();
        Cursor result = readable.rawQuery(query, null);

        if (result.moveToFirst()) {
            retorno = result.getString(result.getColumnIndex(MIDIA_PATH));
        }
        result.close();
        readable.close();
        this.close();
        return retorno;
    }

    public void remove(String midiaName) {

        SQLiteDatabase write = getWritableDatabase();
        write.delete(CACHE_TABLE, midiaName == null ? null : MIDIA_NAME + " = ?", midiaName == null ? null : new String[] {midiaName});
    }

    private void validarDados(String name, String path) {
        if (name == null || name.trim().length() == 0 || path == null || path.trim().length() == 0) {
            throw new IllegalArgumentException("Dados do cache não pode ser nulos ou vazio");
        }
    }
}
