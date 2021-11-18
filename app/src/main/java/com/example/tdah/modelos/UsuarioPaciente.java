package com.example.tdah.modelos;

import java.io.Serializable;

public class UsuarioPaciente implements Serializable {

    private String string_nombre_paciente;
    private int int_puntuacion_actividad_1=0;
    private int int_puntuacion_actividad_2=0;
    private int int_puntuacion_actividad_3=0;
    private int int_puntuacion_alta_actividad_1=0;
    private int int_puntuacion_alta_actividad_2=0;
    private int int_puntuacion_alta_actividad_3=0;

    public UsuarioPaciente() {
    }

    public int getInt_puntuacion_actividad_1() {
        return int_puntuacion_actividad_1;
    }

    public void setInt_puntuacion_actividad_1(int int_puntuacion_actividad_1) {
        this.int_puntuacion_actividad_1 = int_puntuacion_actividad_1;
    }

    public int getInt_puntuacion_actividad_2() {
        return int_puntuacion_actividad_2;
    }

    public void setInt_puntuacion_actividad_2(int int_puntuacion_actividad_2) {
        this.int_puntuacion_actividad_2 = int_puntuacion_actividad_2;
    }

    public int getInt_puntuacion_actividad_3() {
        return int_puntuacion_actividad_3;
    }

    public void setInt_puntuacion_actividad_3(int int_puntuacion_actividad_3) {
        this.int_puntuacion_actividad_3 = int_puntuacion_actividad_3;
    }

    public int getInt_puntuacion_alta_actividad_1() {
        return int_puntuacion_alta_actividad_1;
    }

    public void setInt_puntuacion_alta_actividad_1(int int_puntuacion_alta_actividad_1) {
        this.int_puntuacion_alta_actividad_1 = int_puntuacion_alta_actividad_1;
    }

    public int getInt_puntuacion_alta_actividad_2() {
        return int_puntuacion_alta_actividad_2;
    }

    public void setInt_puntuacion_alta_actividad_2(int int_puntuacion_alta_actividad_2) {
        this.int_puntuacion_alta_actividad_2 = int_puntuacion_alta_actividad_2;
    }

    public int getInt_puntuacion_alta_actividad_3() {
        return int_puntuacion_alta_actividad_3;
    }

    public void setInt_puntuacion_alta_actividad_3(int int_puntuacion_alta_actividad_3) {
        this.int_puntuacion_alta_actividad_3 = int_puntuacion_alta_actividad_3;
    }

    public String getString_nombre_paciente() {
        return string_nombre_paciente;
    }
    public void setString_nombre_paciente(String string_nombre_paciente) {
        this.string_nombre_paciente = string_nombre_paciente;
    }
}
