package com.example.tdah.modelos;

import java.io.Serializable;

public class UsuarioPsicologo extends Usuario implements Serializable {
    private int int_cedula;

    public long getLong_telefono() {
        return long_telefono;
    }

    public void setLong_telefono(long long_telefono) {
        this.long_telefono = long_telefono;
    }

    private long long_telefono;
    private String string_especialidad,string_perfilProfesional;
    private boolean boolean_validado;

    public UsuarioPsicologo() {
    }

    public int getInt_cedula() {
        return int_cedula;
    }

    public void setInt_cedula(int int_cedula) {
        this.int_cedula = int_cedula;
    }

    public String getString_especialidad() {
        return string_especialidad;
    }

    public void setString_especialidad(String string_especialidad) {
        this.string_especialidad = string_especialidad;
    }

    public String getString_perfilProfesional() {
        return string_perfilProfesional;
    }

    public void setString_perfilProfesional(String string_perfilProfesional) {
        this.string_perfilProfesional = string_perfilProfesional;
    }



    public boolean isBoolean_validado() {
        return boolean_validado;
    }

    public void setBoolean_validado(boolean boolean_validado) {
        this.boolean_validado = boolean_validado;
    }
}
