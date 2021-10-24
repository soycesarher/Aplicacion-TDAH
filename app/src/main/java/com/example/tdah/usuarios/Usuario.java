package com.example.tdah.usuarios;

import java.io.Serializable;

public class Usuario implements Serializable{
    private String string_nombre;
    private String string_apellidoPaterno;
    private String string_apellidoMaterno;
    private String string_fechaDeNacimiento;
    private String string_direccion;
    private String string_contrasena;
    private String string_correo;
    private String string_id;


    public String getString_nombre() {
        return string_nombre;
    }

    public void setString_nombre(String string_nombre) {
        this.string_nombre = string_nombre;
    }

    public String getString_apellidoPaterno() {
        return string_apellidoPaterno;
    }

    public void setString_apellidoPaterno(String string_apellidoPaterno) {
        this.string_apellidoPaterno = string_apellidoPaterno;
    }

    public String getString_apellidoMaterno() {
        return string_apellidoMaterno;
    }

    public void setString_apellidoMaterno(String string_apellidoMaterno) {
        this.string_apellidoMaterno = string_apellidoMaterno;
    }

    public String getString_fechaDeNacimiento() {
        return string_fechaDeNacimiento;
    }

    public void setString_fechaDeNacimiento(String string_fechaDeNacimiento) {
        this.string_fechaDeNacimiento = string_fechaDeNacimiento;
    }

    public String getString_direccion() {
        return string_direccion;
    }

    public void setString_direccion(String string_direccion) {
        this.string_direccion = string_direccion;
    }

    public String getString_contrasena() {
        return string_contrasena;
    }

    public void setString_contrasena(String string_contrasena) {
        this.string_contrasena = string_contrasena;
    }

    public String getString_correo() {
        return string_correo;
    }

    public void setString_correo(String string_correo) {
        this.string_correo = string_correo;
    }

    public String getString_id() {
        return string_id;
    }

    public void setString_id(String string_id) {
        this.string_id = string_id;
    }
}
