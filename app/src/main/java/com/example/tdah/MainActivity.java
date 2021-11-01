package com.example.tdah;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
    }
    //conexion de activities
    public void ir_iniciar_sesion(View view){
        Intent ir = new Intent(this,InicioDeSesion.class);
        startActivity(ir);
    }
    public void ir_a_registro(View view){
        Intent ir = new Intent(this,RegistroUsuario.class);
        startActivity(ir);
    }


}