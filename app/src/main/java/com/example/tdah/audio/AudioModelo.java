package com.example.tdah.audio;

import java.io.Serializable;

public class AudioModelo implements Serializable {
    private int int_cancion;
    private String string_nombre_cancion;
    private int int_imagen;
    private String string_tipo;

    public AudioModelo() {
    }

    /**
     *
     * @param int_cancion cancion:
     * @param string_nombre_cancion nombre:
     * @param int_imagen imagen:
     */
    public AudioModelo(int int_cancion, String string_nombre_cancion, int int_imagen, String string_tipo) {
        this.int_cancion = int_cancion;
        this.string_nombre_cancion = string_nombre_cancion;
        this.int_imagen = int_imagen;
        this.string_tipo = string_tipo;
    }

    public int getInt_cancion() {
        return int_cancion;
    }

    public void setInt_cancion(int int_cancion) {
        this.int_cancion = int_cancion;
    }

    public String getString_nombre_cancion() {
        return string_nombre_cancion;
    }

    public void setString_nombre_cancion(String string_nombre_cancion) {
        this.string_nombre_cancion = string_nombre_cancion;
    }

    public int getInt_imagen() {
        return int_imagen;
    }

    public void setInt_imagen(int int_imagen) {
        this.int_imagen = int_imagen;
    }

    public String getString_tipo() {
        return string_tipo;
    }

    public void setString_tipo(String string_tipo) {
        this.string_tipo = string_tipo;
    }
}
