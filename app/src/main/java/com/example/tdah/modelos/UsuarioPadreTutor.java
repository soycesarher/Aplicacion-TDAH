package com.example.tdah.modelos;

import java.io.Serializable;

public class UsuarioPadreTutor extends Usuario implements Serializable {

    private String string_curp, string_fecha_pago;
    private String string_fecha_fin_suscripcion;
    private int int_nip;

    public UsuarioPadreTutor() {
    }

    public String getString_curp() {
        return string_curp;
    }

    public void setString_curp(String string_curp) {
        this.string_curp = string_curp;
    }

    public String getString_fecha_pago() {
        return string_fecha_pago;
    }

    public void setString_fecha_pago(String string_fecha_pago) {
        this.string_fecha_pago = string_fecha_pago;
    }

    public int getInt_nip() {
        return int_nip;
    }

    public void setInt_nip(int int_nip) {
        this.int_nip = int_nip;
    }

    public String isString_fecha_fin_suscripcion() {
        return string_fecha_fin_suscripcion;
    }

    public void setString_fecha_fin_suscripcion(String string_fecha_fin_suscripcion) {
        this.string_fecha_fin_suscripcion = string_fecha_fin_suscripcion;
    }
}
