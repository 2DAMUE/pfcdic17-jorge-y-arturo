package com.example.tury.socialloc;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.tury.socialloc.model.Lugar;

import java.sql.Blob;

public class GalleryActivity extends AppCompatActivity {

    private static Lugar lugar;

    public static void setLugar(Lugar lugar) {
        GalleryActivity.lugar = lugar;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main2 );

        LinearLayout layout = (LinearLayout)findViewById(R.id.linear);
        for(int i=0;i<lugar.getImagenes().size();i++)
        {
            ImageView image = new ImageView(this);
            image.setImageBitmap((Bitmap) lugar.getImagenes().get( i ));
            image.setLayoutParams(new android.view.ViewGroup.LayoutParams(400,300));
            image.setMaxHeight(20);
            image.setMaxWidth(20);

            // Adds the view to the layout
            layout.addView(image);
        }
    }
}
