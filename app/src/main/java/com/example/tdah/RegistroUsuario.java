package com.example.tdah;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tdah.modelos.UsuarioPaciente;
import com.example.tdah.modelos.UsuarioPadreTutor;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseUser;


import java.util.UUID;

public class RegistroUsuario extends AppCompatActivity {
    DatabaseReference usuario;
    EditText txt_nombre_padre_tutor;
    EditText txt_nombre_paciente;
    EditText txt_apellido_materno;
    EditText txt_apellido_paterno;
    EditText txt_correo;
    EditText txt_curp;
    EditText txt_nip;
    EditText txt_contrasena;
    FirebaseDatabase firebase_database;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);
        inicializa_firebase();
    }

    private void inicializa_firebase() {
        FirebaseApp.initializeApp(this);
        mAuth= FirebaseAuth.getInstance();
        firebase_database = FirebaseDatabase.getInstance();
        usuario = firebase_database.getReference();
    }

    public void ingresa_base_datos(View view){


        txt_curp=findViewById(R.id.txt_curp);
        txt_apellido_paterno=findViewById(R.id.txt_apellido_paterno);
        txt_nip=findViewById(R.id.txt_nip);
        txt_contrasena=findViewById(R.id.txt_contrasena);
        txt_correo=findViewById(R.id.txt_correo);
        txt_apellido_materno=findViewById(R.id.txt_apellido_materno);
        txt_nombre_paciente=findViewById(R.id.txt_nombre_paciente);
        txt_nombre_padre_tutor=findViewById(R.id.txt_nombre_padre_tutor);
        String curp = txt_curp.getText().toString();
        String nombre = txt_nombre_padre_tutor.getText().toString();
        String apellido_paterno=txt_apellido_paterno.getText().toString();
        String apellido_materno=txt_apellido_materno.getText().toString();
        String correo=txt_correo.getText().toString();
        String nombre_paciente=txt_nombre_paciente.getText().toString();
        String contrasena=txt_contrasena.getText().toString();
        String nip=txt_nip.getText().toString();

        if(!TextUtils.isEmpty(nombre)||!TextUtils.isEmpty(curp)||
                !TextUtils.isEmpty(apellido_materno)||!TextUtils.isEmpty(apellido_paterno)||
                !TextUtils.isEmpty(correo)||!TextUtils.isEmpty(nombre_paciente)||
                !TextUtils.isEmpty(nip)||!TextUtils.isEmpty(contrasena))
        {
            mAuth.createUserWithEmailAndPassword(correo,contrasena).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    UsuarioPadreTutor u = new UsuarioPadreTutor();
                    UsuarioPaciente p = new UsuarioPaciente();
               if(task.isSuccessful()){
                   FirebaseUser usuario_actual = mAuth.getCurrentUser();
                   u.setString_id(usuario_actual.getUid());
                   u.setString_nombre(nombre);
                   u.setInt_nip(Integer.parseInt(nip));
                   u.setString_curp(curp);
                   u.setString_tipo_cuenta("Libre");
                   u.setString_apellido_materno(apellido_materno);
                   u.setString_apellido_paterno(apellido_paterno);
                   u.setString_contrasena(contrasena);
                   u.setString_fecha_nacimiento("0/0/2021");
                   u.setDouble_pago(30.5);
                   p.setString_nombre_paciente(nombre_paciente);
                   p.setInt_progreso(0);
                   p.setInt_puntuacion(0);
                   usuario.child("Usuario").child(u.getString_id()).setValue(u);
                   usuario.child("Usuario").child(u.getString_id()).child("Paciente").setValue(p);
                   Toast.makeText(RegistroUsuario.this, "Usuario registrado", Toast.LENGTH_SHORT).show();
               }else {
                   Toast.makeText(RegistroUsuario.this, "Fallo de autenticación", Toast.LENGTH_SHORT).show();
               }
                }
            });

        }else {
            Toast.makeText(this,"Debe ingresar todos los parametros",Toast.LENGTH_LONG).show();
        }
    }

    //conexion de activities
    public void ir_main(View view){
        Intent ir = new Intent(this,MainActivity.class);
        startActivity(ir);
    }
    public void ir_inicio_de_sesion(View view){
        Intent ir = new Intent(this,InicioDeSesion.class);
        startActivity(ir);
    }
}