package app.svox;

import java.util.ArrayList;

import app.svox.dbmanagement.*;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
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
import java.util.List;


public class F_Listado extends Fragment {

    ListView lvFrases;
    private ArrayList<String> frases;
    private ArrayAdapter adapter;

    public View view;

    //CONTRUCTOR
    public F_Listado() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewFrases = inflater.inflate(R.layout.f_listado_lay, container, false);

        //frases = GestorSQLite.getListFrases();

        //Testeando con arraylist de prueba
        ArrayList<String> listArray = new ArrayList<>();
        listArray.add("esto");
        listArray.add("es");
        listArray.add("una");
        listArray.add("prueba");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                getActivity().getApplicationContext(),
                R.layout.item_list_lay,listArray);

        lvFrases = (ListView) viewFrases.findViewById(R.id.livFrases);
        lvFrases.setAdapter(arrayAdapter);
        return viewFrases;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }



}
