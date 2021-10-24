package com.example.tdah.usuarios;

import java.io.Serializable;

public class Usuario implements Serializable{
    private String string_usuario_nombre;
    private String string_usuario_apellidoPaterno;
    private String string_usuario_apellidoMaterno;
    private String string_usuario_fechaDeNacimiento;
    private String string_usuario_direccion;
    private String string_usuario_contrasena;
    private String string_usuario_correo;
    private String string_usuario_idUsuario;

    public String getString_usuario_nombre() {
        return string_usuario_nombre;
    }

    public void setString_usuario_nombre(String string_usuario_nombre) {
        this.string_usuario_nombre = string_usuario_nombre;
    }

    public String getString_usuario_apellidoPaterno() {
        return string_usuario_apellidoPaterno;
    }

    public void setString_usuario_apellidoPaterno(String string_usuario_apellidoPaterno) {
        this.string_usuario_apellidoPaterno = string_usuario_apellidoPaterno;
    }

    public String getString_usuario_apellidoMaterno() {
        return string_usuario_apellidoMaterno;
    }

    public void setString_usuario_apellidoMaterno(String string_usuario_apellidoMaterno) {
        this.string_usuario_apellidoMaterno = string_usuario_apellidoMaterno;
    }

    public String getString_usuario_fechaDeNacimiento() {
        return string_usuario_fechaDeNacimiento;
    }

    public void setString_usuario_fechaDeNacimiento(String string_usuario_fechaDeNacimiento) {
        this.string_usuario_fechaDeNacimiento = string_usuario_fechaDeNacimiento;
    }

    public String getString_usuario_direccion() {
        return string_usuario_direccion;
    }

    public void setString_usuario_direccion(String string_usuario_direccion) {
        this.string_usuario_direccion = string_usuario_direccion;
    }

    public String getString_usuario_contrasena() {
        return string_usuario_contrasena;
    }

    public void setString_usuario_contrasena(String string_usuario_contrasena) {
        this.string_usuario_contrasena = string_usuario_contrasena;
    }

    public String getString_usuario_correo() {
        return string_usuario_correo;
    }

    public void setString_usuario_correo(String string_usuario_correo) {
        this.string_usuario_correo = string_usuario_correo;
    }

    public String getString_usuario_idUsuario() {
        return string_usuario_idUsuario;
    }

    public void setString_usuario_idUsuario(String string_usuario_idUsuario) {
        this.string_usuario_idUsuario = string_usuario_idUsuario;
    }
}
