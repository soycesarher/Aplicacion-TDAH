package com.example.tdah;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class InicioDeSesion extends AppCompatActivity {
private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_de_sesion);
//        Este método es por si el usuario ya accedió a su cuenta
        FirebaseUser usuarioActual = mAuth.getCurrentUser();
        if(usuarioActual!= null){
            recarga();
        }
    }
//  Este método recarga al menú principal si es que el usuario ya inició sesión
    private void recarga() {
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