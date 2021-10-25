package com.example.tdah.modelos;

import java.io.Serializable;

public class UsuarioPadreTutor extends Usuario implements Serializable {

    private String string_curp,string_tipo_cuenta;
    private double double_pago;
    private int int_nip;

    public UsuarioPadreTutor() {
    }

    public String getString_curp() {
        return string_curp;
    }

    public void setString_curp(String string_curp) {
        this.string_curp = string_curp;
    }

    public String getString_tipo_cuenta() {
        return string_tipo_cuenta;
    }

    public void setString_tipo_cuenta(String string_tipo_cuenta) {
        this.string_tipo_cuenta = string_tipo_cuenta;
    }

    public double getDouble_pago() {
        return double_pago;
    }

    public void setDouble_pago(double double_pago) {
        this.double_pago = double_pago;
    }

    public int getInt_nip() {
        return int_nip;
    }

    public void setInt_nip(int int_nip) {
        this.int_nip = int_nip;
    }

}
