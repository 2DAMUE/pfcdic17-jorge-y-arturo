package com.example.tury.socialloc.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class LugarSQLiteHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "lugareseimagenes.db";
    private static final int DB_VERSION = 1;
    public static final String LUGARES_TABLE = "LUGARES";
    public static final String IMAGENES_TABLE = "IMAGENES";
    public static final String COLUMN_CIUDAD="CIUDAD";
    public static final String COLUMN_LUGAR="LUGAR";
    public static final String COLUMN_IMAGEN="IMAGEN";
    public static final String COLUMN_CODIGO_POSTAL="CODIGO_POSTAL";
    public static final String COLUMN_DESCRIPCION="DESCRIPCION";
    public static final String COLUMN_NOMBRE="NOMBRE";
    public static final String COLUMN_TIPO="TIPO";
    public static final String COLUMN_LATITUDE="LATITUDE";
    public static final String COLUMN_LONGITUDE="LONGITUDE";

    public static String CREATE_TABLE =
            "CREATE TABLE "+ LUGARES_TABLE+" ( "+ BaseColumns._ID +" INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_CIUDAD+" TEXT,"+
                    COLUMN_CODIGO_POSTAL+" TEXT,"+
                    COLUMN_DESCRIPCION+" TEXT,"+
                    COLUMN_NOMBRE+" TEXT,"+
                    COLUMN_TIPO+" TEXT," +
                    COLUMN_LATITUDE+" REAL,"+
                    COLUMN_LONGITUDE+" REAL);";

    public static String CREATE_TABLE_I =
            "CREATE TABLE " + IMAGENES_TABLE + "("+
                    COLUMN_LUGAR + " INTEGER," +
                    COLUMN_IMAGEN + " BLOB);";

    //Meme Table Annotations functionality
    public LugarSQLiteHelper (Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        db.execSQL(CREATE_TABLE_I);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}
