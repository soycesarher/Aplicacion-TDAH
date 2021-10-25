package com.example.tdah.modelos;

import java.io.Serializable;

public class UsuarioPaciente extends UsuarioPadreTutor implements Serializable {
    private int int_puntuacion;
    private int int_progreso;

    public int getInt_puntuacion() {
        return int_puntuacion;
    }

    public void setInt_puntuacion(int int_puntuacion) {
        this.int_puntuacion = int_puntuacion;
    }

    public int getInt_progreso() {
        return int_progreso;
    }

    public void setInt_progreso(int int_progreso) {
        this.int_progreso = int_progreso;
    }
}
