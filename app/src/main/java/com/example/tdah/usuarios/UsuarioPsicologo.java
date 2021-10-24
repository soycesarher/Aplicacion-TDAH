package com.example.tdah.usuarios;

import java.io.Serializable;

public class UsuarioPsicologo extends Usuario implements Serializable {
    private int int_usuarioPsicologo_cedula;
    private double double_usuarioPsicologo_pago;
    private String string_usuarioPsicologo_especialidad;
    private String string_usuarioPsicologo_perfilProfesional;


    public int getInt_usuarioPsicologo_cedula() {
        return int_usuarioPsicologo_cedula;
    }

    public void setInt_usuarioPsicologo_cedula(int int_usuarioPsicologo_cedula) {
        this.int_usuarioPsicologo_cedula = int_usuarioPsicologo_cedula;
    }

    public double getDouble_usuarioPsicologo_pago() {
        return double_usuarioPsicologo_pago;
    }

    public void setDouble_usuarioPsicologo_pago(double double_usuarioPsicologo_pago) {
        this.double_usuarioPsicologo_pago = double_usuarioPsicologo_pago;
    }

    public String getString_usuarioPsicologo_especialidad() {
        return string_usuarioPsicologo_especialidad;
    }

    public void setString_usuarioPsicologo_especialidad(String string_usuarioPsicologo_especialidad) {
        this.string_usuarioPsicologo_especialidad = string_usuarioPsicologo_especialidad;
    }

    public String getString_usuarioPsicologo_perfilProfesional() {
        return string_usuarioPsicologo_perfilProfesional;
    }

    public void setString_usuarioPsicologo_perfilProfesional(String string_usuarioPsicologo_perfilProfesional) {
        this.string_usuarioPsicologo_perfilProfesional = string_usuarioPsicologo_perfilProfesional;
    }
}
