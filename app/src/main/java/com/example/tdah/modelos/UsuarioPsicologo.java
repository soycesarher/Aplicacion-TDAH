package com.example.tdah.modelos;

import java.io.Serializable;

public class UsuarioPsicologo extends Usuario implements Serializable {
    private int int_cedula;
    private int int_telefono;
    private double double_pago;
    private String string_especialidad,string_perfilProfesional;

    public UsuarioPsicologo() {
    }

    public int getInt_cedula() {
        return int_cedula;
    }

    public void setInt_cedula(int int_cedula) {
        this.int_cedula = int_cedula;
    }

    public double getDouble_pago() {
        return double_pago;
    }

    public void setDouble_pago(double double_pago) {
        this.double_pago = double_pago;
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

    public int getInt_telefono() {
        return int_telefono;
    }

    public void setInt_telefono(int int_telefono) {
        this.int_telefono = int_telefono;
    }
}
