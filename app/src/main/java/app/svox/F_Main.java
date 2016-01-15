package app.svox;

import app.svox.dbmanagement.*;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.location.LocationManager;
import android.speech.RecognizerIntent;
//import android.media.MediaPlayer.OnCompletionListener;
//import android.media.MediaPlayer;
//import android.media.MediaRecorder;

import android.location.LocationListener;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.File;
import java.util.ArrayList;


/**
 * Fragmento que contiene el menu Inicial.
 */
public class F_Main extends Fragment implements View.OnClickListener, View.OnLongClickListener {

    //Variables que definen la BBDD
    public AdminSQLite admin;
    public static final String NOMBRE_TABLA_FRASES = "FRASES";
    public static final String NOMBRE_TABLA_PALABRAS = "PALABRAS";
    public static final int CODIGO_SOLICITUD_RECONOCIMIENTO = 1;

    //Elementos de la interfaz grafica
    private ToggleButton tbGrabar;

    private ListView lvFrases;
    private ImageButton btHabla;

    private ImageButton btLista;
    private ImageButton btMap;

    private TextView tvUltimaFrase;
    private TextView tvSugerenciaFrase;
    private EditText etFraseCaptada;
    private EditText etFraseSugerida;

    //Elementos pasivos auxiliares
    private ArrayList <String> SesionActualfrases = new ArrayList<>();

    //CONSTRUCTOR
    public F_Main() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.f_main_lay, container, false);

        //PARA HABLA Y RECONOCIMIENTO:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
        //Determinamos el comportamiento del click en el boton de hablar
        btHabla = (ImageButton) rootView.findViewById(R.id.btnHabla);
        btHabla.setOnClickListener(this);
        btHabla.setOnLongClickListener(this);
        etFraseCaptada = (EditText) rootView.findViewById(R.id.etxFraseCaptada);
        etFraseCaptada.setOnClickListener(this);
        etFraseCaptada.setOnLongClickListener(this);
        etFraseSugerida = (EditText) rootView.findViewById(R.id.etxFraseSugerida);
        etFraseSugerida.setOnClickListener(this);
        etFraseSugerida.setOnLongClickListener(this);


        //tvUltimaFrase = (TextView) rootView.findViewById(R.id.txvUltimaFrase);
        //tvUltimaFrase.setOnClickListener(this);
        //tvUltimaFrase.setOnLongClickListener(this);
        //tvSugerenciaFrase = (TextView) rootView.findViewById(R.id.txvFraseSugerencia);
        //tvSugerenciaFrase.setOnClickListener(this);
        //tvSugerenciaFrase.setOnLongClickListener(this);
        tbGrabar = (ToggleButton) rootView.findViewById(R.id.tobGrabar);

        /*tvSugerenciaFrase.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String frase = (String) etFraseSugerida.getText();
                SesionActualfrases.add(frase);
                admin = new AdminSQLite(getContext(), 2);
                admin.addFrase(frase);

                Toast.makeText(getContext(), "Frase sugerida guardada",
                        Toast.LENGTH_SHORT).show();

                tvUltimaFrase.setText("");
                tvSugerenciaFrase.setText("");

                return false;
            }
        });*/

        //PARA LISTADO:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
        btLista = (ImageButton) rootView.findViewById(R.id.btnLista);
        btLista.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent listadoFrases = new Intent(getActivity().getApplication(), A_List.class);
                startActivity(listadoFrases);
                return false;
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

                    ArrayList<String> afrases = intent.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    etFraseCaptada.setText(afrases.get(0));   //Aqui escribe el resultado en el textView indicado
                    etFraseSugerida.setText(afrases.get(8));
                }
                break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //BOTON DE LLAMADA AL METODO DE RECONOCIMIENTO DE VOZ
            case R.id.btnHabla:
                Intent intHabla = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intHabla.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

                try {
                    startActivityForResult(intHabla, CODIGO_SOLICITUD_RECONOCIMIENTO);
                    etFraseCaptada.setText("");
                    etFraseSugerida.setText("");

                } catch (ActivityNotFoundException a) {
                    toastMsg (2,"reconocimiento de voz");
                }
                break;

            //EDITAR EL TEXTO
            case R.id.etxFraseCaptada:
                break;

            case R.id.etxFraseSugerida:
                break;

            case R.id.btnLista:
                Intent listadoFrases = new Intent(getActivity().getApplication(), A_List.class);
                startActivity(listadoFrases);

                break;
        }
    }

    @Override
    public boolean onLongClick(View v) {
        admin = new AdminSQLite(getContext(), 2);

        switch (v.getId()) {
            case R.id.btnHabla:
                break;

            case R.id.etxFraseCaptada:       //Guardar la frase del TextView
                SesionActualfrases.add(etFraseCaptada.getText().toString());
                admin.addFrase(etFraseCaptada.getText().toString());

                toastMsg(1, "Frase");
                etFraseCaptada.setText("");
                etFraseSugerida.setText("");

                break;

            case R.id.etxFraseSugerida:   //Guardar la frase sugerida del TextView
                SesionActualfrases.add(etFraseSugerida.getText().toString());
                admin.addFrase(etFraseSugerida.getText().toString());

                toastMsg(1, "Frase Sugerida");
                etFraseCaptada.setText("");
                etFraseSugerida.setText("");

                break;

            case R.id.btnLista:
                Intent listadoFrases = new Intent(getActivity().getApplication(), A_List.class);
                startActivity(listadoFrases);

                break;
        }
        return false;
    }

    public void toastMsg (int option, String msgAux){
        switch (option){
            case 1: //GUARDAR
                Toast.makeText(getContext(), msgAux+" guardada",
                        Toast.LENGTH_SHORT).show();
                break;

            case 2: //ERROR
                Toast.makeText(getContext(), "ERROR en "+msgAux,
                        Toast.LENGTH_LONG).show();
                break;
        }
    }


}


