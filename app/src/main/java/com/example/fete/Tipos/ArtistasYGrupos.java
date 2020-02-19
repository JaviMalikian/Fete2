package com.example.fete.Tipos;

public class ArtistasYGrupos {

    int id_ArtistaGrupo;
    String nombreArtista;
    String DescripcionCorta;
    String DescripcionLarga;
    String Ubicacion;
    double Lat;
    double Longi;
    String Fecha;
    String path;

    public ArtistasYGrupos(int id_ArtistaGrupo, String nombreArtista, String descripcionCorta, String descripcionLarga, String ubicacion, double lat, double longi, String fecha, String path) {
        this.id_ArtistaGrupo = id_ArtistaGrupo;
        this.nombreArtista = nombreArtista;
        DescripcionCorta = descripcionCorta;
        DescripcionLarga = descripcionLarga;
        Ubicacion = ubicacion;
        Lat = lat;
        Longi = longi;
        Fecha = fecha;
        this.path = path;
    }



    public int getId_ArtistaGrupo() { return id_ArtistaGrupo; }

    public void setId_ArtistaGrupo(int id_ArtistaGrupo) { this.id_ArtistaGrupo = id_ArtistaGrupo; }

    public String getNombreArtista() { return nombreArtista; }

    public void setNombreArtista(String nombreArtista) { this.nombreArtista = nombreArtista; }

    public String getDescripcionCorta() { return DescripcionCorta; }

    public void setDescripcionCorta(String descripcionCorta) { DescripcionCorta = descripcionCorta; }

    public String getDescripcionLarga() { return DescripcionLarga; }

    public void setDescripcionLarga(String descripcionLarga) {
        DescripcionLarga = descripcionLarga;
    }

    public String getUbicacion() {
        return Ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        Ubicacion = ubicacion;
    }

    public double getLat() {
        return Lat;
    }

    public void setLat(double lat) {
        Lat = lat;
    }

    public double getLongi() {
        return Longi;
    }

    public void setLongi(double longi) {
        Longi = longi;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }





    /*
                                        consultaJSON.getJSONObject(i).getInt("id_marca"),
                                        consultaJSON.getJSONObject(i).getString("Nombre_evento"),
                                        consultaJSON.getJSONObject(i).getString("desc_corta"),
                                        consultaJSON.getJSONObject(i).getString("desc_larga"),
                                        consultaJSON.getJSONObject(i).getString("Ubicacion"),
                                        consultaJSON.getJSONObject(i).getDouble("Lat"),
                                        consultaJSON.getJSONObject(i).getDouble("Longi"),
                                        consultaJSON.getJSONObject(i).getString("fecha_alta"),
                                        consultaJSON.getJSONObject(i).getDouble("path")
     */


}