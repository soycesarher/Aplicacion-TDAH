package com.example.tdah;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tdah.modelos.UsuarioPaciente;
import com.example.tdah.modelos.UsuarioPadreTutor;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class RegistroUsuario extends AppCompatActivity {
    private DatabaseReference usuario;
    Button btn_registrar, btn_cancelar;
    EditText txt_nombre_padre_tutor, txt_nombre_paciente, txt_apellido_materno,
    txt_apellido_paterno, txt_correo, txt_curp, txt_nip, txt_contrasena;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);
        usuario = FirebaseDatabase.getInstance().getReference();
        btn_registrar.setOnClickListener(v -> ingresaBaseDatos());
    }
    public void ingresaBaseDatos(){
        UsuarioPadreTutor u = new UsuarioPadreTutor();
        UsuarioPaciente p = new UsuarioPaciente();
        if(!TextUtils.isEmpty(txt_nombre_padre_tutor.getText().toString())||!TextUtils.isEmpty(txt_nombre_paciente.getText().toString())||
                !TextUtils.isEmpty(txt_apellido_materno.getText().toString())||!TextUtils.isEmpty(txt_apellido_paterno.getText().toString())||
                !TextUtils.isEmpty(txt_correo.getText().toString())||!TextUtils.isEmpty(txt_curp.getText().toString())||
                !TextUtils.isEmpty(txt_contrasena.getText().toString())||!TextUtils.isEmpty(txt_nip.getText().toString()))
        {
            u.setString_id(UUID.randomUUID().toString());
            u.setString_nombre(txt_nombre_padre_tutor.getText().toString());
            u.setInt_nip(Integer.parseInt(txt_nombre_padre_tutor.getText().toString()));
            u.setString_curp(txt_curp.getText().toString());
            u.setString_tipo_cuenta("Libre");
            u.setString_apellido_materno(txt_apellido_materno.getText().toString());
            u.setString_apellido_paterno(txt_apellido_paterno.getText().toString());
            u.setString_contrasena(txt_nombre_padre_tutor.getText().toString());
            u.setString_fecha_nacimiento("0/0/2021");
            u.setDouble_pago(30.5);
            p.setString_nombre_paciente(txt_nombre_paciente.getText().toString());
            p.setInt_progreso(0);
            p.setInt_puntuacion(0);
            usuario.child("Usuario").child(u.getString_id()).setValue(u);
            usuario.child("Usuario").child(u.getString_id()).child("Paciente").setValue(p);
            Toast.makeText(this,"Usuario registrado",Toast.LENGTH_LONG).show();
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