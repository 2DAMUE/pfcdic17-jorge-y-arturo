package com.example.tury.socialloc.model;

import android.graphics.Bitmap;

import java.util.List;



public class Lugar {
    private int id;
    private String ciudad;
    private String codigoPostal;
    private String descripcion;
    private String nombre;
    private String tipo;
    private double mLatitude;
    private double mLongitude;
    private List<Bitmap> imagenes;

    public Lugar() {
    }

    public Lugar(String ciudad, String codigoPostal, String descripcion, String nombre, String tipo, double mLatitude, double mLongitude) {
        this.ciudad = ciudad;
        this.codigoPostal = codigoPostal;
        this.descripcion = descripcion;
        this.nombre = nombre;
        this.tipo = tipo;
        this.mLatitude = mLatitude;
        this.mLongitude = mLongitude;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getId() {return id; }

    public void setId(int id) { this.id = id; }

    public double getmLatitude() {
        return mLatitude;
    }

    public void setmLatitude(double mLatitude) {
        this.mLatitude = mLatitude;
    }

    public double getmLongitude() {
        return mLongitude;
    }

    public void setmLongitude(double mLongitude) {
        this.mLongitude = mLongitude;
    }

    public List<Bitmap> getImagenes() {
        return imagenes;
    }

    public void setImagenes(List imagenes) {
        this.imagenes = imagenes;
    }

    @Override
    public String toString() {
        return ciudad + " " + codigoPostal + " " + nombre + " " + tipo;
    }
}
