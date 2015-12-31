package app.svox;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Fragmento que contiene el menu de de opciones.
 */
public class F_Main extends Fragment {

    public static final int CODIGO_SOLICITUD_RECONOCIMIENTO = 1234;

    private ListView lvFrases;
    private ImageButton btHabla;
    private ImageButton btLista;
    private ImageButton btMap;


    public F_Main() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.f_main_lay, container, false);

        //HABLA Y RECONOCIMIENTO
        //Determinamos el comportamiento del click en el boton de hablar
        btHabla = (ImageButton) rootView.findViewById(R.id.btnHabla);

        btHabla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");

                /* //otra  variante de modelo a testear
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                 */

                try {
                    startActivityForResult(intent, 1);
                    System.out.println("");
                    //TODO: Aqui llamaremos a la base de datos para guardar resultado
                    //txtText.setText("");
                } catch (ActivityNotFoundException a) {
                    Toast t = Toast.makeText(getActivity().getApplicationContext(),
                            "error de compatibilidad",
                            Toast.LENGTH_SHORT);
                    t.show();
                }

            }
        });

        //LISTADO
        //lvFrases = (ListView) rootView.findViewById(R.id.)

        return rootView;
    }


}
