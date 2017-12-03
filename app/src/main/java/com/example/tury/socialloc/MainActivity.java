package com.example.tury.socialloc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    public static FirebaseUser loggedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //FirebaseAuth.getInstance().signOut(); al quitar esto aqui y en loguin ya nos recuerda
    }

    public void mapas (View view){
        Intent i = new Intent(MainActivity.this, MapsActivity.class);
        startActivity(i);
    }

    public void restaurantes (View view){
        Direcciones.setTipo("Restaurante");
        Intent i = new Intent(MainActivity.this, Direcciones.class);
        startActivity(i);
    }

    public void hoteles (View view){
        Direcciones.setTipo("Hotel");
        Intent i = new Intent(MainActivity.this, Direcciones.class);
        startActivity(i);
    }
    public void otros (View view){
        Direcciones.setTipo("Otros");
        Intent i = new Intent(MainActivity.this, Direcciones.class );
        startActivity(i);

    }

    public void cerrar (View view){
        finish();
    }

    public void salir (View view){
        FirebaseAuth.getInstance().signOut();
        finish();
    }
}
