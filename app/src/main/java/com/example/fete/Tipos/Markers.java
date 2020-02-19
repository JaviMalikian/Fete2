package com.example.fete.Tipos;
public class Markers {

    public Markers(int id_marker, String ubicacion, String _nombre_evento, double _latitud, double _longitud) {
        this.id_marker = id_marker;
        Ubicacion = ubicacion;
        this._nombre_evento = _nombre_evento;
        this._latitud = _latitud;
        this._longitud = _longitud;
    }

    private int id_marker;
    private String Ubicacion;
    private String _nombre_evento;
    private double _latitud;
    private double _longitud;

    public int getId_marker() {
        return id_marker;
    }

    public void setId_marker(int id_marker) {
        this.id_marker = id_marker;
    }

    public String getUbicacion() {
        return Ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        Ubicacion = ubicacion;
    }

    public String get_nombre_evento() {
        return _nombre_evento;
    }

    public void set_nombre_evento(String _nombre_evento) {
        this._nombre_evento = _nombre_evento;
    }

    public double get_latitud() {
        return _latitud;
    }

    public void set_latitud(float _latitud) {
        this._latitud = _latitud;
    }

    public double get_longitud() {
        return _longitud;
    }

    public void set_longitud(float _longitud) {
        this._longitud = _longitud;
    }



}

