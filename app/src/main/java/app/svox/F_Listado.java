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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


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

        AdminSQLite admin = new AdminSQLite(getActivity().getApplicationContext());
        Vector<String> vector = admin.extraerFrases();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                getActivity().getApplicationContext(),
                R.layout.item_list_lay,vector);

        lvFrases = (ListView) viewFrases.findViewById(R.id.livFrases);
        lvFrases.setAdapter(arrayAdapter);

        lvFrases.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String contenido = lvFrases.getItemAtPosition(position).toString();

                Toast.makeText(getActivity().getApplicationContext(), contenido,
                        Toast.LENGTH_LONG).show();
            }
        });

        return viewFrases;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }



}
