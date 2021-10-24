package com.example.tdah.usuarios;

import java.io.Serializable;

public class UsuarioPadreTutor extends Usuario implements Serializable {
    String string_usuarioPadreTutor_curp;
    double double_usuarioPadreTutor_pago;
    int int_usuarioPadreTutor_nip;

    public String getString_usuarioPadreTutor_curp() {
        return string_usuarioPadreTutor_curp;
    }

    public void setString_usuarioPadreTutor_curp(String string_usuarioPadreTutor_curp) {
        this.string_usuarioPadreTutor_curp = string_usuarioPadreTutor_curp;
    }

    public double getDouble_usuarioPadreTutor_pago() {
        return double_usuarioPadreTutor_pago;
    }

    public void setDouble_usuarioPadreTutor_pago(double double_usuarioPadreTutor_pago) {
        this.double_usuarioPadreTutor_pago = double_usuarioPadreTutor_pago;
    }

    public int getInt_usuarioPadreTutor_nip() {
        return int_usuarioPadreTutor_nip;
    }

    public void setInt_usuarioPadreTutor_nip(int int_usuarioPadreTutor_nip) {
        this.int_usuarioPadreTutor_nip = int_usuarioPadreTutor_nip;
    }

    public String getString_usuarioPadreTutor_tipoCuenta() {
        return string_usuarioPadreTutor_tipoCuenta;
    }

    public void setString_usuarioPadreTutor_tipoCuenta(String string_usuarioPadreTutor_tipoCuenta) {
        this.string_usuarioPadreTutor_tipoCuenta = string_usuarioPadreTutor_tipoCuenta;
    }

    String string_usuarioPadreTutor_tipoCuenta;

}
