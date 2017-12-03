package com.example.tury.socialloc.model;

import android.content.Context;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LugarDataSourceFirebase {
    private Context mContext;
    private DatabaseReference mDatabase;
    static ArrayList<Lugar> list  =new ArrayList<>();


    public LugarDataSourceFirebase(Context context) {
        mDatabase = FirebaseDatabase.getInstance().getReference("lugares");
        mContext = context;
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                list.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting artist
                    Lugar artist = postSnapshot.getValue(Lugar.class);
                    //adding artist to the list
                    list.add(artist);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void create (Lugar lugar){
        mDatabase.child(String.valueOf(lugar.getId())).setValue(lugar);
    }
    public void update (Lugar lugar){
        mDatabase.child(String.valueOf(lugar.getId())).setValue(lugar);
    }

    public void borrar (String id){
        mDatabase.child(String.valueOf(id)).removeValue();
    }

    public ArrayList<Lugar> buscar(String tipo){
        //ArrayList<Lugar> list =new ArrayList<>();
        return list;
    }
}
