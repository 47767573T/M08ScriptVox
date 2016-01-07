package app.svox.dbmanagement;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import app.svox.F_Main;

/**
 * Created by Moises on 03/01/2016.
 */
public class AdminSQLite extends SQLiteOpenHelper{

    //Definimos valores finales como nombre de tablas y tipos de campos
    private static final String NOMBRE_TABLA_FRASES = F_Main.NOMBRE_TABLA_FRASES;
    public static final String STRING_TYPE = "text";
    public static final String INT_TYPE = "integer";

    //Script para crear la tabla de frases
    String sqlCreate = "CREATE TABLE "+NOMBRE_TABLA_FRASES+" ("
            +"_ID "+INT_TYPE+" PRIMARY KEY AUTOINCREMENT,"
            +" FECHA "+STRING_TYPE+" ,"
            +" CONTENIDO "+STRING_TYPE+")";

    public AdminSQLite(Context context) {
        super(context, "SVOX1" +
                "_DB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+NOMBRE_TABLA_FRASES);
        db.execSQL(sqlCreate);
    }

    public void addFrase(String contenido){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("FECHA", getFechaActual());
        values.put("CONTENIDO", contenido);

        db.insert(NOMBRE_TABLA_FRASES, null, values);
    }

    public Vector<String> getFrases(){
        Vector<String> resultado = new Vector <>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + NOMBRE_TABLA_FRASES, null);

        while (cursor.moveToNext()){
            resultado.add(cursor.getInt(1)+" - "
                    +cursor.getString(2));
        }
        cursor.close();
        return resultado;
    }

    public void modifyFrase(int id, String fraseNueva){
        SQLiteDatabase db = getWritableDatabase();

        db.execSQL("UPDATE "+NOMBRE_TABLA_FRASES+" SET contenido='"+fraseNueva+"' WHERE ID="+ id);
    }

    public static String getFechaActual(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String ahora = sdf.format(new Date());

        return ahora;
    }
}
