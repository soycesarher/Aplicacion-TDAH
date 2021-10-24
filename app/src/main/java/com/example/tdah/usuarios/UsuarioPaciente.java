package com.example.tdah.usuarios;

import java.io.Serializable;

public class UsuarioPaciente extends UsuarioPadreTutor implements Serializable {
    private int int_usuarioPaciente_puntuacion;
    private int int_usuarioPaciente_progreso;

    public int getInt_usuarioPaciente_puntuacion() {
        return int_usuarioPaciente_puntuacion;
    }

    public void setInt_usuarioPaciente_puntuacion(int int_usuarioPaciente_puntuacion) {
        this.int_usuarioPaciente_puntuacion = int_usuarioPaciente_puntuacion;
    }

    public int getInt_usuarioPaciente_progreso() {
        return int_usuarioPaciente_progreso;
    }

    public void setInt_usuarioPaciente_progreso(int int_usuarioPaciente_progreso) {
        this.int_usuarioPaciente_progreso = int_usuarioPaciente_progreso;
    }
}
