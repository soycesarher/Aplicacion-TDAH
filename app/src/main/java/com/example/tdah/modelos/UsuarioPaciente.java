package com.example.tdah.modelos;

import java.io.Serializable;

public class UsuarioPaciente implements Serializable {
    private String string_nombre_paciente;
    private int int_puntuacion,int_progreso;

    public UsuarioPaciente() {
    }

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

    public String getString_nombre_paciente() {
        return string_nombre_paciente;
    }
    public void setString_nombre_paciente(String string_nombre_paciente) {
        this.string_nombre_paciente = string_nombre_paciente;
    }
}
