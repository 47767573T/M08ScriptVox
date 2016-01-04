package app.svox.dbmanagement;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
        registro.put("FECHA", getFechaActual());
        registro.put("CONTENIDO", contenido);

        //Introducimos el registro en la bd
        db.insert(nombreTablaFrases, null, registro);
        db.close();
    }

    public static void getFraseSegunID(){
    }


    public static String getFechaActual(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String ahora = sdf.format(new Date());

        return ahora;
    }

    public static ArrayList<String> getListFrases(){
        ArrayList<String> lista = new ArrayList<>();

        AdminSQLite admin = new AdminSQLite(null, nombreTablaFrases, null, 1);
        String qAll = "SELECT * FROM "+nombreTablaFrases;
        SQLiteDatabase db = admin.getWritableDatabase();
        Cursor cursor = db.rawQuery(qAll, null);

        while (cursor.moveToNext()) {
            int fecha = cursor.getInt(0);
            String contenido = cursor.getString(1);

            String itemLista = fecha+" - "+contenido;
            lista.add(itemLista);
        }

        /*for (int i = 0; i < lista.size(); i++) {
            String[] listaStr = new String[lista.size()];
            listaStr[i] = lista.get(i);
        }*/
        return lista;

    }
}
