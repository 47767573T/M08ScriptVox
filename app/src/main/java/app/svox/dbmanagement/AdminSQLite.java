package app.svox.dbmanagement;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Moises on 03/01/2016.
 */
public class AdminSQLite extends SQLiteOpenHelper{


    public AdminSQLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table FRASES"
                    +"(ID int NOT NULL AUTO_INCREMENT,"
                    +"CONTENIDO text,"
                    +"FECHA date,"
                    +"PRIMARY KEY (ID))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
