package com.example.tdah;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        mAuth= FirebaseAuth.getInstance();
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

    @Override
    protected void onStart() {
        super.onStart();
            if(mAuth.getCurrentUser()!= null){
                startActivity(new Intent(MainActivity.this, DatosUsuario.class));
                finish();
            }
    }
}