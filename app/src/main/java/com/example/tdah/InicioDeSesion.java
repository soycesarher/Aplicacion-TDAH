package com.example.tdah;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class InicioDeSesion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_de_sesion);
    }
    //conexion de activities
    public void ir_a_registro(View view){
        Intent ir = new Intent(this,RegistroUsuario.class);
        startActivity(ir);
    }
    public void ir_a_main(View view){
        Intent ir = new Intent(this,MainActivity.class);
        startActivity(ir);
    }
}