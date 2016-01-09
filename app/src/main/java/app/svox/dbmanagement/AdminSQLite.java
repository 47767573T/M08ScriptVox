package app.svox.dbmanagement;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
    private static final String NOMBRE_TABLA_PALABRAS = F_Main.NOMBRE_TABLA_PALABRAS;
    public static final String STRING_TYPE = "text";
    public static final String INT_TYPE = "integer";

    //Script para crear la tablas
    String sqlCreateFrases = "CREATE TABLE "+NOMBRE_TABLA_FRASES+" ("
            +" ID "+INT_TYPE+" PRIMARY KEY AUTOINCREMENT,"
            +" FECHA "+STRING_TYPE+" ,"
            +" CONTENIDO "+STRING_TYPE+" ,"
            +" TAG"+ STRING_TYPE+" NULL)";

    String sqlCreatePalabras = "CREATE TABLE "+NOMBRE_TABLA_PALABRAS+" ("
            +" ID "+INT_TYPE+" PRIMARY KEY AUTOINCREMENT,"
            +" CONTENIDO "+STRING_TYPE+" ,"
            +" TIPO "+ INT_TYPE+" ,"
            +" MENCIONES"+ INT_TYPE+")";

    public AdminSQLite(Context context, int version) {
        super(context, "SVOX1" +
                "_DB", null, version);
    }

    /**
     * Método que automaticamente se ejecuta al principio de instalacion de la app
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(sqlCreateFrases);
        Log.d("creadas tabla", NOMBRE_TABLA_FRASES);
        db.execSQL(sqlCreatePalabras);
        Log.d("creadas tablas", NOMBRE_TABLA_PALABRAS);
    }

    /**
     * Actualizar las tablas con campos añadidos, borrando la version anterior y creando la versionnueva
     * @param db Base de datos a modificar
     * @param oldVersion version a borrar
     * @param newVersion version nueva a crear
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + NOMBRE_TABLA_FRASES);
        Log.d("borrada tabla:", NOMBRE_TABLA_FRASES);
        db.execSQL("DROP TABLE IF EXISTS " + NOMBRE_TABLA_PALABRAS);
        Log.d("creadas tablas:", NOMBRE_TABLA_PALABRAS);
        db.execSQL(sqlCreateFrases);
        Log.d("creadas tablas", NOMBRE_TABLA_PALABRAS);
        db.execSQL(sqlCreatePalabras);
        Log.d("creadas tablas", NOMBRE_TABLA_PALABRAS);
    }

    /**
     * Añadir frases a la tabla FRASES. Solo guarda fecha
     * @param contenido
     */
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
            resultado.add(cursor.getInt(0)+" - "
                    +cursor.getString(2));
        }
        cursor.close();
        return resultado;
    }

    public void modifyFrase(int id, String fraseNueva){
        SQLiteDatabase db = getWritableDatabase();

        db.execSQL("UPDATE " + NOMBRE_TABLA_FRASES + " SET contenido='" + fraseNueva + "' WHERE ID =" + id);
    }

    public void deleteFrase(String fechaSeleccionada){

        SQLiteDatabase db = getWritableDatabase();

        db.execSQL("DELETE FROM " + NOMBRE_TABLA_FRASES + " WHERE FECHA = " + fechaSeleccionada);
        Log.d("script borrar", fechaSeleccionada);

    }

    public static String getFechaActual(){
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM d HH:mm:ss z yyyy");
        String ahora = sdf.format(new Date());
        return ahora;
    }



}
