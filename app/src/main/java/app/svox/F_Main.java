package app.svox;

import app.svox.dbmanagement.*;

import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.speech.RecognizerIntent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import app.svox.dbmanagement.AdminSQLite;

/**
 * Fragmento que contiene el menu de de opciones.
 */
public class F_Main extends Fragment {

    public static final String nombreTablaFrases = "FRASES";
    public static final int CODIGO_SOLICITUD_RECONOCIMIENTO = 1;


    private ListView lvFrases;
    private ImageButton btHabla;
    private ImageButton btLista;
    private ImageButton btMap;

    private TextView tvUltimaFrase;
    private TextView tvSugerenciaFrase;

    private ArrayList <String> SesionActualfrases = new ArrayList<>();


    public F_Main() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.f_main_lay, container, false);

        //PARA HABLA Y RECONOCIMIENTO:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
        //Determinamos el comportamiento del click en el boton de hablar
        btHabla = (ImageButton) rootView.findViewById(R.id.btnHabla);
        tvUltimaFrase = (TextView) rootView.findViewById(R.id.txvUltimaFrase);
        tvSugerenciaFrase = (TextView) rootView.findViewById(R.id.txvFraseSugerencia);

        btHabla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

                //otra  variante de modelo a testear y mensajes
                //intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");


                try {
                    //Aqui realiza la actividad de reconocimiento y recoge los datos
                    startActivityForResult(intent, CODIGO_SOLICITUD_RECONOCIMIENTO);
                    tvUltimaFrase.setText("");
                    tvSugerenciaFrase.setText("");

                } catch (ActivityNotFoundException a) {
                    Toast t = Toast.makeText(getActivity().getApplicationContext(),
                            "error en el reconocimiento de voz de su aparato",
                            Toast.LENGTH_SHORT);
                    t.show();
                }
            }
        });

        tvUltimaFrase.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String frase = (String) tvUltimaFrase.getText();
                SesionActualfrases.add(frase);
                GestorSQLite.GuardarFrase(getContext(), frase);    //Llama al metodo que guarda la frase en la BBDD

                Toast.makeText(getContext(), "Frase guardada",
                        Toast.LENGTH_SHORT).show();

                tvUltimaFrase.setText("");
                tvSugerenciaFrase.setText("");

                return false;
            }
        });

        tvSugerenciaFrase.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String frase = (String) tvSugerenciaFrase.getText();
                SesionActualfrases.add(frase);
                GestorSQLite.GuardarFrase(getContext(), frase);    //Llama al metodo que guarda la frase en la BBDD

                Toast.makeText(getContext(), "Frase sugerida guardada",
                        Toast.LENGTH_SHORT).show();

                tvUltimaFrase.setText("");
                tvSugerenciaFrase.setText("");

                return false;
            }
        });

        //PARA LISTADO:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
        btLista = (ImageButton) rootView.findViewById(R.id.btnLista);

        btLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent listadoFrases = new Intent(getContext(), A_List.class);
                startActivity(listadoFrases);
            }
        });



        //PARA GEOCALIZACION
        btMap = (ImageButton) rootView.findViewById(R.id.btnMap);

        btLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent Mapa = new Intent(getContext(), XXXX.class);
                //startActivity(Mapa);
            }
        });
        return rootView;
    }

    @Override
    public void onActivityResult(int codigoSolicitud, int resultCode, Intent intent) {
        super.onActivityResult(codigoSolicitud, resultCode, intent);

        //Validamos si el codigo de solicitud es el mismo y si la voz captada no da error
        switch (codigoSolicitud) {
            case CODIGO_SOLICITUD_RECONOCIMIENTO: {
                if (resultCode == A_Main.RESULT_OK && null != intent) {

                    ArrayList<String> frases = intent.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    tvUltimaFrase.setText(frases.get(0));   //Aqui escribe el resultado en el textView indicado

                    tvSugerenciaFrase.setText(frases.get(1));
                }
                break;
            }
        }
    }

}


