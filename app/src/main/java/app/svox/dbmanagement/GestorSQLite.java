package app.svox.dbmanagement;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

import app.svox.F_Main;

/**
 * Created by Moises on 03/01/2016.
 */
public class GestorSQLite {

    private static final String nombreTablaFrases = F_Main.nombreTablaFrases;

    public static void GuardarFrase(Context context, String contenido) {
        AdminSQLite admin = new AdminSQLite(context, nombreTablaFrases, null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();
        ContentValues registro = new ContentValues();

        //Definimos el registro campo a campo
        registro.put("CONTENIDO", contenido);
        registro.put("FECHA", getFechaActual());

        //Introducimos el registro en la bd
        db.insert(nombreTablaFrases, null, registro);
        db.close();
    }


    public static String getFechaActual(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String ahora = sdf.format(new Date());

        return ahora;
    }


}
