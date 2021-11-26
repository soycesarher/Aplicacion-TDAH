package com.example.tdah.modelos;

import java.io.Serializable;

public class UsuarioPadreTutor extends Usuario implements Serializable {

    private String string_curp;

    private int int_nip;

    public UsuarioPadreTutor() {
    }

    public String getString_curp() {
        return string_curp;
    }

    public void setString_curp(String string_curp) {
        this.string_curp = string_curp;
    }

    public int getInt_nip() {
        return int_nip;
    }

    public void setInt_nip(int int_nip) {
        this.int_nip = int_nip;
    }

}
