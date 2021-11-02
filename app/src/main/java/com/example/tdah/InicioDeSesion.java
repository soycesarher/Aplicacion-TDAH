package com.example.tdah;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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

        btn_iniciar_sesion.setOnClickListener(v -> {
            correo = txt_correo.toString();
            contrasena = txt_contrasena.toString();
            if(!correo.isEmpty()&&!contrasena.isEmpty()){
                signIn(correo,contrasena);
            }else{
                Toast.makeText(InicioDeSesion.this,"Llene todos los campos",Toast.LENGTH_LONG).show();
            }
        });


    }

    private void signIn(String email, String password) {
        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information

                        FirebaseUser user = mAuth.getCurrentUser();
                       ir_a_menu_usuario();
                       finish();
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(InicioDeSesion.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();

                    }
                });
        // [END sign_in_with_email]
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser usuarioActual = mAuth.getCurrentUser();
        if(usuarioActual!= null){
            ir_a_menu_usuario();
        }
    }

    //conexion de activities
    public void ir_a_registro(View view){
        Intent ir = new Intent(this,RegistroUsuario.class);
        startActivity(ir);
    }
    // Este método recarga al menú principal si es que el usuario ya inició sesión

    public void ir_a_menu_usuario(){
        Intent ir = new Intent(this,MenuUsuario.class);
        startActivity(ir);
    }
    public void ir_a_main(View view){
        Intent ir = new Intent(this,MainActivity.class);
        startActivity(ir);
    }

}