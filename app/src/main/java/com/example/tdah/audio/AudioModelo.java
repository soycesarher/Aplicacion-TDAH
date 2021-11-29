package com.example.tdah.audio;

public class AudioModelo {
    private String string_url_cancion;
    private String string_nombre_cancion;
    private String string_url_imagen;

    public AudioModelo() {
    }

    public AudioModelo( String string_nombre_cancion,String string_url_cancion, String string_url_imagen) {
        this.string_url_cancion = string_url_cancion;
        this.string_nombre_cancion = string_nombre_cancion;
        this.string_url_imagen = string_url_imagen;
    }

    public String getString_url_cancion() {
        return string_url_cancion;
    }

    public void setString_url_cancion(String string_url_cancion) {
        this.string_url_cancion = string_url_cancion;
    }

    public String getString_nombre_cancion() {
        return string_nombre_cancion;
    }

    public void setString_nombre_cancion(String string_nombre_cancion) {
        this.string_nombre_cancion = string_nombre_cancion;
    }

    public String getString_url_imagen() {
        return string_url_imagen;
    }

    public void setString_url_imagen(String string_url_imagen) {
        this.string_url_imagen = string_url_imagen;
    }
}
