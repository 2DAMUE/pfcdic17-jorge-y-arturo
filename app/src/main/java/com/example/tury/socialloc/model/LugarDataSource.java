package com.example.tury.socialloc.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.BitmapFactory;
import android.provider.BaseColumns;

import java.util.ArrayList;

public class LugarDataSource {
    private Context mContext;
    private LugarSQLiteHelper mLugarSqlLiteHelper;

    public LugarDataSource(Context context) {

        mContext = context;
        mLugarSqlLiteHelper = new LugarSQLiteHelper(context);
    }
    public SQLiteDatabase openWritable(){

        return mLugarSqlLiteHelper.getWritableDatabase();
    }
    public SQLiteDatabase openReadable(){

        return mLugarSqlLiteHelper.getReadableDatabase();
    }
    private void close (SQLiteDatabase database){
        database.close();
    }

    private int getIntFromColumnName (Cursor cursor, String columnName){
        int columnIndex=cursor.getColumnIndex(columnName);

        return cursor.getInt(columnIndex);
    }
    private String getStringFromColumnName (Cursor cursor, String columnName){
        int columnIndex=cursor.getColumnIndex(columnName);
        return cursor.getString(columnIndex);
    }

    private double getDoubleFromColumnName (Cursor cursor, String columnName){
        int columnIndex=cursor.getColumnIndex(columnName);
        return cursor.getDouble(columnIndex);
    }

    private byte[] getbyteFromColumnName (Cursor cursor, String columnName){
        int columnIndex=cursor.getColumnIndex(columnName);
        return cursor.getBlob(columnIndex);
    }

    public void create (Lugar lugar){
        SQLiteDatabase database = openWritable();
        database.beginTransaction();
        ContentValues lugarValues = new ContentValues();
        lugarValues.put(LugarSQLiteHelper.COLUMN_NOMBRE, lugar.getNombre());
        lugarValues.put(LugarSQLiteHelper.COLUMN_CIUDAD, lugar.getCiudad());
        lugarValues.put(LugarSQLiteHelper.COLUMN_CODIGO_POSTAL, lugar.getCodigoPostal());
        lugarValues.put(LugarSQLiteHelper.COLUMN_DESCRIPCION, lugar.getDescripcion());
        lugarValues.put(LugarSQLiteHelper.COLUMN_TIPO, lugar.getTipo());
        lugarValues.put(LugarSQLiteHelper.COLUMN_LATITUDE, lugar.getmLatitude());
        lugarValues.put(LugarSQLiteHelper.COLUMN_LONGITUDE, lugar.getmLongitude());
        database.insert(LugarSQLiteHelper.LUGARES_TABLE, null, lugarValues);
        database.setTransactionSuccessful();
        database.endTransaction();
        close(database);
    }

    public void create( int id, byte[] image) throws SQLiteException {
        SQLiteDatabase database = openWritable();
        database.beginTransaction();
        ContentValues cv = new  ContentValues();
        cv.put(LugarSQLiteHelper.COLUMN_LUGAR,    id);
        cv.put(LugarSQLiteHelper.COLUMN_IMAGEN,   image);
        database.insert( LugarSQLiteHelper.IMAGENES_TABLE, null, cv );
        database.setTransactionSuccessful();
        database.endTransaction();
        close(database);
    }

    public void update (Lugar lugar){
        SQLiteDatabase database = openWritable();
        database.beginTransaction();
        ContentValues lugarValues = new ContentValues();
        lugarValues.put(LugarSQLiteHelper.COLUMN_NOMBRE, lugar.getNombre());
        lugarValues.put(LugarSQLiteHelper.COLUMN_DESCRIPCION, lugar.getDescripcion());
        lugarValues.put(LugarSQLiteHelper.COLUMN_TIPO, lugar.getTipo());

        database.update(LugarSQLiteHelper.LUGARES_TABLE,
                lugarValues,
                String.format("%s=%d",
                                BaseColumns._ID,lugar.getId()), null);

        database.setTransactionSuccessful();
        database.endTransaction();
        close(database);
    }

    public void borrar (int id){
        SQLiteDatabase database = openWritable();
        database.beginTransaction();
        database.delete(LugarSQLiteHelper.LUGARES_TABLE,
                String.format("%s=%d",
                        BaseColumns._ID, id),
                null);
        database.setTransactionSuccessful();
        database.endTransaction();
        close(database);
    }

    public ArrayList<Lugar> buscar(String tipo){
        ArrayList<Lugar> list = new ArrayList<Lugar>();
        SQLiteDatabase database = openReadable();
        Cursor cursor = database.query(LugarSQLiteHelper.LUGARES_TABLE,
                new String[]{BaseColumns._ID,LugarSQLiteHelper.COLUMN_NOMBRE, LugarSQLiteHelper.COLUMN_CIUDAD,LugarSQLiteHelper.COLUMN_CODIGO_POSTAL, LugarSQLiteHelper.COLUMN_DESCRIPCION,LugarSQLiteHelper.COLUMN_DESCRIPCION, LugarSQLiteHelper.COLUMN_TIPO, LugarSQLiteHelper.COLUMN_LATITUDE, LugarSQLiteHelper.COLUMN_LONGITUDE},
                LugarSQLiteHelper.COLUMN_TIPO+" = '"+tipo+"'",
                null,
                null,
                null,
                null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Lugar lugar = new Lugar();
            lugar.setNombre(getStringFromColumnName(cursor, LugarSQLiteHelper.COLUMN_NOMBRE));
            lugar.setCiudad(getStringFromColumnName(cursor, LugarSQLiteHelper.COLUMN_CIUDAD));
            lugar.setCodigoPostal(getStringFromColumnName(cursor, LugarSQLiteHelper.COLUMN_CODIGO_POSTAL));
            lugar.setTipo(getStringFromColumnName(cursor, LugarSQLiteHelper.COLUMN_TIPO));
            lugar.setDescripcion(getStringFromColumnName(cursor, LugarSQLiteHelper.COLUMN_DESCRIPCION));
            lugar.setId(getIntFromColumnName(cursor, BaseColumns._ID));
            lugar.setmLatitude(getDoubleFromColumnName(cursor, LugarSQLiteHelper.COLUMN_LATITUDE));
            lugar.setmLongitude(getDoubleFromColumnName(cursor, LugarSQLiteHelper.COLUMN_LONGITUDE));
            lugar.setImagenes( new ArrayList(  ) );

            Cursor cursor1 = database.query(LugarSQLiteHelper.IMAGENES_TABLE,
                    new String[]{LugarSQLiteHelper.COLUMN_LUGAR, LugarSQLiteHelper.COLUMN_IMAGEN},
                    LugarSQLiteHelper.COLUMN_LUGAR+" = '"+lugar.getId()+"'",
                    null,
                    null,
                    null,
                    null);
            cursor1.moveToFirst();
            while (!cursor1.isAfterLast()) {
                byte[] imgByte = getbyteFromColumnName(cursor1, LugarSQLiteHelper.COLUMN_IMAGEN);
                lugar.getImagenes().add(BitmapFactory.decodeByteArray(imgByte,0, imgByte.length));
                cursor1.moveToNext();
            }

            list.add(lugar);
            cursor.moveToNext();
        }

        cursor.close();
        close(database);
        return list;
    }
}
