package app.svox;

import java.util.ArrayList;

import app.svox.dbmanagement.*;

import android.annotation.TargetApi;
import android.app.Activity;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class F_Listado extends Fragment {

    ListView lvFrases;
    private ArrayList<String> frases;
    private ArrayAdapter adapter;


    //CONTRUCTOR
    public F_Listado() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewFrases = inflater.inflate(R.layout.f_listado_lay, container, false);

        //frases = GestorSQLite.getListFrases();
        frases.add("esto");
        frases.add("es");
        frases.add("una");
        frases.add("prueba");
        lvFrases = (ListView) viewFrases.findViewById(R.id.livFrases);

        adapter = new ArrayAdapter<>(
                getActivity().getApplicationContext()
                , 0
                , frases);

        lvFrases.setAdapter(adapter);

        return viewFrases;
    }

    /*@Override
    public void onCreate(Bundle savedInstanceState) { }*/




}
