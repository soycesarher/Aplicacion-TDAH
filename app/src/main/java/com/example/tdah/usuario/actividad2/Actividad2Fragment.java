package com.example.tdah.usuario.actividad2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.tdah.R;
import com.example.tdah.UsuarioPrincipal;
import com.example.tdah.modelos.UsuarioPaciente;
import com.example.tdah.modelos.UsuarioPadreTutor;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Actividad2Fragment extends Fragment implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase firebase_database;
    private DatabaseReference databaseReference;
    private UsuarioPaciente usuarioPaciente;
    private UsuarioPadreTutor usuarioPadreTutor;

    private Actividad2ViewModel Actividad2ViewModel;
    private EditText triangulo_nv1_a;
    private EditText triangulo_nv2_a;
    private EditText triangulo_nv2_b;
    private EditText triangulo_nv3_a;
    private EditText triangulo_nv3_b;
    private EditText triangulo_nv3_c;
    private TextView suma_l;
    private TextView suma_r;
    private TextView suma_b;
    private TextView str_puntos;
    private TextView num_puntos;
    private Button comprobar;
    private Button reiniciar;

    UsuarioPrincipal main;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Actividad2ViewModel =
                new ViewModelProvider(this).get(Actividad2ViewModel.class);
        View root = inflater.inflate(R.layout.fragment_actividad2, container, false);
        Componentes(root);
        main = (UsuarioPrincipal) getParentFragment().getActivity();
        Actividad2ViewModel.getText().observe(getViewLifecycleOwner(), s -> {
        });
        return root;
    }

    private void Componentes(View root) {
        Botones(root);
        EditTextComponent(root);
        TextViewComponent(root);
    }

    private void EditTextComponent(View root) {
        triangulo_nv1_a = (EditText) root.findViewById(R.id.triangulo_niv1);
        triangulo_nv2_a = (EditText) root.findViewById(R.id.triangulo_niv2_a);
        triangulo_nv2_b = (EditText) root.findViewById(R.id.triangulo_niv2_b);
        triangulo_nv3_a = (EditText) root.findViewById(R.id.triangulo_niv3_a);
        triangulo_nv3_b = (EditText) root.findViewById(R.id.triangulo_niv3_b);
        triangulo_nv3_c = (EditText) root.findViewById(R.id.triangulo_niv3_c);
    }

    private void TextViewComponent(View root) {
        suma_l = (TextView) root.findViewById(R.id.triangulo_puntos_izq);
        suma_r = (TextView) root.findViewById(R.id.triangulo_puntos_der);
        suma_b = (TextView) root.findViewById(R.id.triangulo_puntos_bas);
        str_puntos = (TextView) root.findViewById(R.id.puntos_actv2);
        num_puntos = (TextView) root.findViewById(R.id.numero_puntos_actv2);
    }

    private void Botones(View root) {
        comprobar = root.findViewById(R.id.btn_comprobar_triangulo);
        comprobar.setOnClickListener(this);
        reiniciar = root.findViewById(R.id.btn_reiniciar_triangulo);
        reiniciar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
       if(comprobar.getId() == v.getId()){
           sumarTriangulo();
       } else{
           triangulo_nv1_a.setText("");
           triangulo_nv2_a.setText("");
           triangulo_nv2_b.setText("");
           triangulo_nv3_a.setText("");
           triangulo_nv3_b.setText("");
           triangulo_nv3_c.setText("");
           suma_l.setText("0");
           suma_r.setText("0");
           suma_b.setText("0");
           num_puntos.setText("0");
       }
    }


    //Este método realiza la suma
    private void sumarTriangulo () {
        try{
            String niv1_a = triangulo_nv1_a.getText().toString();
            int valor_niv1_a = Integer.parseInt(niv1_a);

            String niv2_a = triangulo_nv2_a.getText().toString();
            int valor_niv2_a = Integer.parseInt(niv2_a);

            String niv2_b = triangulo_nv2_b.getText().toString();
            int valor_niv2_b = Integer.parseInt(niv2_b);

            String niv3_a = triangulo_nv3_a.getText().toString();
            int valor_niv3_a = Integer.parseInt(niv3_a);

            String niv3_b = triangulo_nv3_b.getText().toString();
            int valor_niv3_b = Integer.parseInt(niv3_b);

            String niv3_c = triangulo_nv3_c.getText().toString();
            int valor_niv3_c = Integer.parseInt(niv3_c);

            if (valor_niv1_a<7&&valor_niv2_a<7&&valor_niv2_b<7&&valor_niv3_a<7&&valor_niv3_b<7&&valor_niv3_c<7){
                if (valor_niv1_a>0&&valor_niv2_a>0&&valor_niv2_b>0&&valor_niv3_a>0&&valor_niv3_b>0&&valor_niv3_c>0) {
                    if(valor_niv1_a!=valor_niv2_a&&valor_niv1_a!=valor_niv2_b&&valor_niv1_a!=valor_niv3_a&&valor_niv1_a!=valor_niv3_b&&valor_niv1_a!=valor_niv3_c){
                        if(valor_niv2_a!=valor_niv1_a&&valor_niv2_a!=valor_niv2_b&&valor_niv2_a!=valor_niv3_a&&valor_niv2_a!=valor_niv3_b&&valor_niv2_a!=valor_niv3_c){
                            if(valor_niv2_b!=valor_niv1_a&&valor_niv2_b!=valor_niv2_a&&valor_niv2_b!=valor_niv3_a&&valor_niv2_b!=valor_niv3_b&&valor_niv2_b!=valor_niv3_c){
                                if(valor_niv3_a!=valor_niv1_a&&valor_niv3_a!=valor_niv2_a&&valor_niv3_a!=valor_niv2_b&&valor_niv3_a!=valor_niv3_b&&valor_niv3_a!=valor_niv3_c){
                                    if(valor_niv3_b!=valor_niv1_a&&valor_niv3_b!=valor_niv2_a&&valor_niv3_b!=valor_niv2_b&&valor_niv3_b!=valor_niv3_a&&valor_niv3_b!=valor_niv3_c){
                                        if(valor_niv3_c!=valor_niv1_a&&valor_niv3_c!=valor_niv2_a&&valor_niv3_c!=valor_niv2_b&&valor_niv3_c!=valor_niv3_a&&valor_niv3_c!=valor_niv3_b){
                                            int suma_lado_izq = valor_niv3_a+valor_niv2_a+valor_niv1_a;
                                            int suma_lado_der = valor_niv3_c+valor_niv2_b+valor_niv1_a;
                                            int suma_lado_base = valor_niv3_a+valor_niv3_b+valor_niv3_c;
                                            int suma_puntos_total = 0;
                                            suma_l.setText(""+suma_lado_izq);
                                            suma_r.setText(""+suma_lado_der);
                                            suma_b.setText(""+suma_lado_base);
                                            if(suma_lado_izq == 10 && suma_lado_der == 10 && suma_lado_base == 10){
                                                Toast.makeText(main.getApplicationContext(), "Es Correcto. Lo lograste! Felicidades!!", Toast.LENGTH_SHORT).show();
                                                suma_puntos_total = 30;
                                                guardaProgreso(suma_puntos_total);
                                            }else{
                                                if(suma_lado_izq == 10){
                                                    suma_puntos_total+=10;
                                                    guardaProgreso(suma_puntos_total);
                                                    Toast.makeText(main.getApplicationContext(), "El lado izquierdo es Correcto!", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(main.getApplicationContext(), "Te equivocaste en el lado izquierdo!", Toast.LENGTH_SHORT).show();
                                                }
                                                if(suma_lado_der == 10){
                                                    suma_puntos_total+=10;
                                                    guardaProgreso(suma_puntos_total);
                                                    Toast.makeText(main.getApplicationContext(), "El lado derecho es Correcto!", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(main.getApplicationContext(), "Te equivocaste en el lado derecho!", Toast.LENGTH_SHORT).show();
                                                }
                                                if(suma_lado_base == 10){
                                                    suma_puntos_total+=10;
                                                    guardaProgreso(suma_puntos_total);
                                                    Toast.makeText(main.getApplicationContext(), "El lado base es Correcto!", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(main.getApplicationContext(), "Te equivocaste en el lado base!", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                            num_puntos.setText(""+suma_puntos_total);
                                        } else {msm_dato_rep();}
                                    } else {msm_dato_rep();}
                                } else {msm_dato_rep();}
                            } else {msm_dato_rep();}
                        } else {msm_dato_rep();}
                    } else {msm_dato_rep();}
                } else {
                    Toast.makeText(main.getApplicationContext(), "Solo debes ocupar números del 1 al 6", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(main.getApplicationContext(), "Solo debes ocupar números del 1 al 6", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(main.getApplicationContext(), "Primero debes llenar todo el triangulo", Toast.LENGTH_SHORT).show();
        }
    }
    private void msm_dato_rep(){
        Toast.makeText(main.getApplicationContext(), "No se pueden repetir los numeros!", Toast.LENGTH_SHORT).show();
    }


    public void guardaProgreso(int puntuacion) {

        usuarioPadreTutor.setString_id(firebaseUser.getUid());

        usuarioPaciente.setInt_puntuacion_actividad_2(puntuacion);

        databaseReference.child("Usuario").child(usuarioPadreTutor.getString_id()).child("Paciente").child("int_puntuacion_actividad_2").setValue(usuarioPaciente.getInt_puntuacion_actividad_2());

        databaseReference.child("Usuario").child(usuarioPadreTutor.getString_id()).child("Paciente").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    usuarioPaciente.setInt_puntuacion_alta_actividad_2(Integer.parseInt(snapshot.child("int_puntuacion_alta_actividad_2").getValue().toString()));
                    usuarioPaciente.setInt_contador_actividad_2(Integer.parseInt(snapshot.child("int_contador_actividad_2").getValue().toString()));
                    usuarioPaciente.setInt_suma_puntuacion_actividad_2(Integer.parseInt(snapshot.child("int_suma_actividad_2").getValue().toString()));
                    usuarioPaciente.setFloat_promedio_actividad_2(Float.parseFloat(snapshot.child("float_promedio_actividad_2").getValue().toString()));

                    int puntuacion_actual = usuarioPaciente.getInt_puntuacion_actividad_2(), puntuacion_actual_alta = usuarioPaciente.getInt_puntuacion_alta_actividad_2(),
                            contador = usuarioPaciente.getInt_contador_actividad_2(), suma = usuarioPaciente.getInt_suma_puntuacion_actividad_2();

                    float promedio;

                    contador++;
                    suma += usuarioPaciente.getInt_suma_puntuacion_actividad_2();
                    promedio = suma / contador;

                    databaseReference.child("Usuario").child(usuarioPadreTutor.getString_id()).child("Paciente").child("int_contador_actividad_2").setValue(contador);
                    databaseReference.child("Usuario").child(usuarioPadreTutor.getString_id()).child("Paciente").child("int_suma_actividad_2").setValue(suma);
                    databaseReference.child("Usuario").child(usuarioPadreTutor.getString_id()).child("Paciente").child("float_promedio_actividad_2").setValue(promedio);

                    if (puntuacion_actual > puntuacion_actual_alta) {

                        databaseReference.child("Usuario").child(usuarioPadreTutor.getString_id()).child("Paciente").child("int_puntuacion_alta_actividad_2").setValue(puntuacion_actual);

                        Toast.makeText(getContext(), "Nueva puntuación alta: " + puntuacion_actual, Toast.LENGTH_SHORT).show();

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}