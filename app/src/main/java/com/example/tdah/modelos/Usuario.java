package com.example.tdah.modelos;

import java.io.Serializable;

public class Usuario implements Serializable{
    private String string_nombre;
    private String string_apellido_paterno;
    private String string_apellido_materno;
    private String string_fecha_nacimiento;
    private String string_direccion;
    private String string_contrasena;
    private String string_correo;
    private String string_id;

    public Usuario() {

    }

    public String getString_nombre() {
        return string_nombre;
    }

    public void setString_nombre(String string_nombre) {
        this.string_nombre = string_nombre;
    }

    public String getString_apellido_paterno() {
        return string_apellido_paterno;
    }

    public void setString_apellido_paterno(String string_apellido_paterno) {
        this.string_apellido_paterno = string_apellido_paterno;
    }

    public String getString_apellido_materno() {
        return string_apellido_materno;
    }

    public void setString_apellido_materno(String string_apellido_materno) {
        this.string_apellido_materno = string_apellido_materno;
    }

    public String getString_fecha_nacimiento() {
        return string_fecha_nacimiento;
    }

    public void setString_fecha_nacimiento(String string_fecha_nacimiento) {
        this.string_fecha_nacimiento = string_fecha_nacimiento;
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
