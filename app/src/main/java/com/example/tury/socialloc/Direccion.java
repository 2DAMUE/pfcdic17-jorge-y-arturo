package com.example.tury.socialloc;

import android.*;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Location;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tury.socialloc.model.Lugar;
import com.example.tury.socialloc.model.LugarDataSource;
import com.google.android.gms.location.LocationServices;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Direccion extends AppCompatActivity {

    private static Address address;
    private static Lugar lugar;
    private TextView ciudad;
    private TextView codigoPostal;
    private EditText descripcion;
    private Spinner spinner;
    private Button guardar;
    private Button borrar;
    private Button mapa;
    private EditText nombre;
    private Button actualizar;
    private ImageButton imagen;
    private int GALLERY = 1, CAMERA = 2;
    LugarDataSource datasource = new LugarDataSource(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direccion);



        ciudad = (TextView) findViewById(R.id.textView8);
        codigoPostal = (TextView) findViewById(R.id.textView9);
        guardar = (Button) findViewById(R.id.button6);
        descripcion = (EditText) findViewById(R.id.editText2);
        spinner = (Spinner) findViewById(R.id.spinner);
        nombre  = (EditText) findViewById(R.id.editText);
        borrar = (Button) findViewById(R.id.button7);
        mapa =(Button) findViewById(R.id.button8);
        actualizar = (Button) findViewById(R.id.button11);
        imagen = (ImageButton) findViewById(R.id.photoButton);

        if (address != null) { // cuando vengo de mapa
            actualizar.setEnabled(false);
            mapa.setEnabled(false);
            borrar.setEnabled(false);//por eso borrar esta inhabilitado
            ciudad.setText(address.getLocality());
            codigoPostal.setText(address .getPostalCode());
            imagen.setEnabled(false);
        } else if (lugar != null) { // cuando selecciono un lugar de la lista de direcciones
            guardar.setEnabled(false);
            ciudad.setText(lugar.getCiudad());
            codigoPostal.setText(lugar.getCodigoPostal());
            descripcion.setText(lugar.getDescripcion());
            nombre.setText(lugar.getNombre());
            spinner.setSelection(((ArrayAdapter<String>)spinner.getAdapter()).getPosition(lugar.getTipo()));
        }
    }

    public static void setAddress(Address address) { // para seleccionar las variables cuando vengo de mapa
        Direccion.address = address;
        Direccion.lugar = null;
    }

    public static void setLugar(Lugar lugar) { // para seleccionar las variables cuando selecciono un lugar de la lista de direcciones
        Direccion.address = null;
        Direccion.lugar = lugar;
    }

    public void guardar (View v){ //324 Caracteres deberia de tener como maximo para que el texto no se superponga a los botones de la pantalla
        Lugar lugar = new Lugar(address.getLocality(), address.getCountryCode(), descripcion.getText().toString(), nombre.getText().toString(), spinner.getSelectedItem().toString(), address.getLatitude(), address.getLongitude());
        datasource.create(lugar);
        Toast.makeText(Direccion.this, R.string.registrok,
                Toast.LENGTH_SHORT).show();
        startActivity(new Intent(Direccion.this, NavActivity.class));
        finish();
    }

    public void borrar (View v){
        datasource.borrar(lugar.getId());
        Toast.makeText(Direccion.this, R.string.borradook,
                Toast.LENGTH_SHORT).show();
        startActivity(new Intent(Direccion.this, NavActivity.class));
        finish();
    }

    public void actualizar (View v){
        //Actualizo en lugar las cosas que he podido cambiar en la pantalla
        lugar.setDescripcion(descripcion.getText().toString());
        lugar.setNombre(nombre.getText().toString());
        lugar.setTipo(spinner.getSelectedItem().toString());
        
        datasource.update(lugar);
        Toast.makeText(Direccion.this, R.string.registrok,
                Toast.LENGTH_SHORT).show();
        startActivity(new Intent(Direccion.this, NavActivity.class));
        finish();
    }

    public void mapa (View v){
        MapsCustomActivity.setLugar(lugar);
        startActivity(new Intent(Direccion.this, MapsCustomActivity.class));
        finish();
    }

    public void foto(View view) {
        showPictureDialog();
    }

    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Escoger foto de la galería",
                "Hacer foto" ,
                "Mostrar fotos"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                            case 2:
                                GalleryActivity.setLugar(lugar);
                                Intent intent = new Intent(Direccion.this, GalleryActivity.class);
                                startActivity(intent);
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent( Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap( this.getContentResolver(), contentURI );
                    saveImage( bitmap );
                    Toast.makeText( Direccion.this, "Image Saved!", Toast.LENGTH_SHORT ).show();
                    //imageview.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText( Direccion.this, "Failed!", Toast.LENGTH_SHORT ).show();
                }
            }
        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");

            if (thumbnail != null) {
                saveImage(thumbnail);
            }

            Toast.makeText(Direccion.this, "Image Saved!", Toast.LENGTH_SHORT).show();
        }
    }

    public void onConnected(Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                ) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    200);
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    200);

            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.CAMERA},
                    200);

            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                    200);

            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    200);

            return;
        }
    }

    public void saveImage(Bitmap myBitmap) {
        lugar.getImagenes().add( myBitmap );
        datasource.create(lugar.getId(),  getBitmapAsByteArray(myBitmap));

    }

    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress( Bitmap.CompressFormat.JPEG, 0, outputStream);
        return outputStream.toByteArray();
    }
}
