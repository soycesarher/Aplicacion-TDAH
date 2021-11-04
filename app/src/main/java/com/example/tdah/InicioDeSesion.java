package com.example.tdah;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;

public class InicioDeSesion extends AppCompatActivity {
private FirebaseAuth mAuth;

private Button btn_iniciar_sesion;
private EditText txt_correo;
private EditText txt_contrasena;
    private String correo="";
    private String contrasena="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_de_sesion);
        mAuth = FirebaseAuth.getInstance();
        txt_correo = (EditText) findViewById(R.id.txt_correo_inicia);
        txt_contrasena = (EditText) findViewById(R.id.txt_contrasena_inicia);

        btn_iniciar_sesion = (Button) findViewById(R.id.btn_ingresar);

        btn_iniciar_sesion.setOnClickListener(v -> {
            correo = txt_correo.getText().toString();
            contrasena = txt_contrasena.getText().toString();
            if(!correo.isEmpty()&&!contrasena.isEmpty()){
                signIn();
            }else{
                Toast.makeText(InicioDeSesion.this,"Llene todos los campos: correo: "+correo+" contraseña: "+contrasena,Toast.LENGTH_LONG).show();
            }
        });


    }

    private void signIn() {
        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(correo, contrasena)
                .addOnCompleteListener( task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        startActivity(new Intent(InicioDeSesion.this,MenuUsuario.class));
                       finish();
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(InicioDeSesion.this, "Error de autentificación",
                                Toast.LENGTH_SHORT).show();

                    }
                });
        // [END sign_in_with_email]
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(mAuth.getCurrentUser()!= null){
            startActivity(new Intent(InicioDeSesion.this,MenuUsuario.class));
            finish();
        }
    }

    //conexion de activities
    public void ir_a_registro(View view){

        startActivity( new Intent(this,RegistroUsuario.class));
    }
    // Este método recarga al menú principal si es que el usuario ya inició sesión


    public void ir_a_main(View view){
       startActivity( new Intent(this,MainActivity.class));

    }

}