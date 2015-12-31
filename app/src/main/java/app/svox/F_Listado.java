package app.svox;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class F_Listado extends Fragment {

    ListView lvFrases;
    private ArrayList<String> itemFrase;

    public F_Listado() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewFrases = inflater.inflate(R.layout.f_listado_lay, container, false);

        lvFrases = (ListView) viewFrases.findViewById(R.id.livFrases);
        itemFrase = new ArrayList<>();



        return viewFrases;
    }

    /*@Override
    public void onCreate(Bundle savedInstanceState) { }*/




}
