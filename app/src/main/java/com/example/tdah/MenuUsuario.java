package com.example.tdah;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tdah.modelos.UsuarioPaciente;
import com.example.tdah.modelos.UsuarioPadreTutor;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MenuUsuario extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Button btn_cerrar_sesion;
    private TextView txt_nombre_paciente;
    private TextView txt_puntuacion_paciente;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_usuario);
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        txt_nombre_paciente=(TextView) findViewById(R.id.txt_v_datos_usuario);
        txt_puntuacion_paciente=(TextView) findViewById(R.id.txt_v_puntuacion);
        btn_cerrar_sesion = (Button) findViewById(R.id.btn_cerrar_sesion);
        btn_cerrar_sesion.setOnClickListener(v -> {
            mAuth.signOut();
            ir_a_main();
        });
        datosUsuario();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    public void ir_a_main(){

        startActivity(new Intent(this,MainActivity.class));
        finish();
    }

    /**
     * @deprecated Este metodo regresa datos del usuario, solo es de prueba
     */
    private void datosUsuario() {
        UsuarioPadreTutor u = new UsuarioPadreTutor();
        UsuarioPaciente p = new UsuarioPaciente();
         u.setString_id( mAuth.getCurrentUser().getUid());
        databaseReference.child("Usuario").child(u.getString_id()).child("Paciente").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    p.setString_nombre_paciente(snapshot.child("string_nombre_paciente").getValue().toString());
                    p.setInt_puntuacion(Integer.valueOf(snapshot.child("int_puntuacion").getValue().toString()));
                    txt_nombre_paciente.setText(p.getString_nombre_paciente());
                    txt_puntuacion_paciente.setText(String.valueOf(p.getInt_puntuacion()));
                }else{
                    Toast.makeText(MenuUsuario.this,"Los datos no existen ",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}