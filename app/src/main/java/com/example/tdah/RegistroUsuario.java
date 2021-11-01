package com.example.tdah;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tdah.modelos.UsuarioPaciente;
import com.example.tdah.modelos.UsuarioPadreTutor;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.tdah.validaciones.DatosDeCurp;

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
    RequestQueue rq;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);
        inicializa_firebase();
        txt_curp=findViewById(R.id.txt_curp);
        rq = Volley.newRequestQueue(this);
    }

    private void inicializa_firebase() {
        FirebaseApp.initializeApp(this);
        firebase_database = FirebaseDatabase.getInstance();
        usuario = firebase_database.getReference();
    }

    public void ingresa_base_datos(View view){
        UsuarioPadreTutor u = new UsuarioPadreTutor();
        UsuarioPaciente p = new UsuarioPaciente();
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
            u.setString_id(UUID.randomUUID().toString());
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

    public void valida_datos_curp(String renapo){

        DatosDeCurp validar = new DatosDeCurp(renapo);
        String nombre = validar.getString_nombre();
        String apellido_paterno = validar.getString_apellido_paterno();
        String apellido_materno = validar.getString_apellido_materno();
        String fecha_nacimiento = validar.getString_fecha_nacimiento();
        txt_nombre_padre_tutor=findViewById(R.id.txt_nombre_padre_tutor);
        txt_nombre_padre_tutor.setText(nombre);
        txt_apellido_paterno=findViewById(R.id.txt_apellido_paterno);
        txt_apellido_paterno.setText(apellido_paterno);
        txt_apellido_materno=findViewById(R.id.txt_apellido_materno);
        txt_apellido_materno.setText(apellido_materno);
        /*boolean datos_correctos = false;
        if(datos_correctos)
            ir_inicio_de_sesion();*/
    }
    public void recuperar(View v){
        txt_curp = findViewById(R.id.txt_curp);
        StringRequest requerimiento = new StringRequest(Request.Method.GET,
                "https://us-west4-arsus-production.cloudfunctions.net/curp?curp="+txt_curp.getText().toString()+"&apiKey=WgrtpPpMT6gCrKmawXDipiEzQQv2",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        valida_datos_curp(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegistroUsuario.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
        rq.add(requerimiento);
    }
}