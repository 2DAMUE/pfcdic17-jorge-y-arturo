package com.example.tury.socialloc;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.example.tury.socialloc.model.Lugar;
import com.example.tury.socialloc.model.LugarDataSource;

import java.util.ArrayList;

public class Direcciones extends FragmentActivity {

    private static String tipo ;
    LugarDataSource datasource = new LugarDataSource(this);
    static ArrayList<Lugar> list;

    public static void setTipo(String tipo) {
        Direcciones.tipo = tipo;
    }

    public static String[] getList() {
        String[] array = new String[list.size()];

        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i).toString();
        }

        return array;
    }

    public static Lugar getElement(int position) {
        return list.get(position); //selecionamos una posicion desde el fragmento obtenga el lugar
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        list = datasource.buscar(tipo);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direcciones);
    }
}
