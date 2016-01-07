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
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import java.util.zip.Inflater;


public class F_Listado extends Fragment {

    ListView lvFrases;
    String nuevaFrase = "";

    //CONTRUCTOR
    public F_Listado() { }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewFrases = inflater.inflate(R.layout.f_listado_lay, container, false);

        AdminSQLite admin = new AdminSQLite(getActivity().getApplicationContext());
        Vector<String> vector = admin.getFrases();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                getActivity().getApplicationContext(),
                R.layout.item_list_lay,vector);

        lvFrases = (ListView) viewFrases.findViewById(R.id.livFrases);
        lvFrases.setAdapter(arrayAdapter);
        registerForContextMenu(lvFrases);   //registramos el listView para usar el menu contextual


        lvFrases.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String contenido = lvFrases.getItemAtPosition(position).toString();

                Toast.makeText(getActivity().getApplicationContext(), contenido,
                        Toast.LENGTH_LONG).show();
            }
        });

        lvFrases.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                return false;
            }
        });

        registerForContextMenu(lvFrases);   //registramos el listView para usar el menu contextual

        return viewFrases;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater mInflater = getActivity().getMenuInflater();
        mInflater.inflate(R.menu.menu_context_list,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuContextInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        AdminSQLite admin = new AdminSQLite(getActivity().getApplicationContext());

        switch (item.getItemId()){
            case R.id.edit:


                admin.modifyFrase(menuContextInfo.position, nuevaFrase);
                Vector<String> vector = admin.getFrases();

                ArrayAdapter<String> newAdapter = new ArrayAdapter<>(
                        getActivity().getApplicationContext(),
                        R.layout.item_list_lay,vector);

                lvFrases.setAdapter(newAdapter);

                return true;
            case R.id.delete:
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }



}
