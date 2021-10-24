package com.example.tdah.usuarios;

import java.io.Serializable;

public class UsuarioPadreTutor extends Usuario implements Serializable {
    private String string_curp;
    private double double_pago;
    private int int_nip;
    private String string_tipoCuenta;

    public String getString_curp() {
        return string_curp;
    }

    public void setString_curp(String string_curp) {
        this.string_curp = string_curp;
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

    public String getString_tipoCuenta() {
        return string_tipoCuenta;
    }

    public void setString_tipoCuenta(String string_tipoCuenta) {
        this.string_tipoCuenta = string_tipoCuenta;
    }
}
