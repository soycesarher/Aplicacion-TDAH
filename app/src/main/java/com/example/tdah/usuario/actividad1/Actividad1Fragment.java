package com.example.tdah.usuario.actividad1;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.tdah.R;
import com.example.tdah.UsuarioPrincipal;
import com.example.tdah.modelos.UsuarioPaciente;
import com.example.tdah.modelos.UsuarioPadreTutor;
import com.example.tdah.usuario.Escenario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Actividad1Fragment extends Fragment implements View.OnClickListener {
    private Actividad1ViewModel Actividad1ViewModel;
    private String[] nombre_animal = {"tucan", "caballo", "perro", "gato", "conejo", "leon", "pato", "rinoceronte"};
    private String[] sombra_animal = {"s_tucan", "s_caballo", "s_perro", "s_gato", "s_conejo", "s_leon", "s_pato", "s_rinoceronte"};
    private int intentos = 3;
    private Button aceptar;
    private TextView mensaje_intentos, mensaje_cuenta, puntuacion_actual_numero, puntuacion_alta;
    private EditText usuario_animal;
    private int numero_generado = 0;
    private ImageView miimagen;
    // puntajes
    private int puntuacion = 0;
    private String score_shadows;
    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase firebase_database;
    private DatabaseReference databaseReference;
    private UsuarioPaciente usuarioPaciente;
    private UsuarioPadreTutor usuarioPadreTutor;

    UsuarioPrincipal main;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Actividad1ViewModel =
                new ViewModelProvider(this).get(Actividad1ViewModel.class);
        View root = inflater.inflate(R.layout.fragment_actividad1, container, false);
        Componentes(root);
        main = (UsuarioPrincipal) getParentFragment().getActivity();
        numero_generado = generaraleatorio();
        establecer_sombra(numero_generado);
        mensaje_intentos.setText("Tiene " + intentos + " intentos");
        usuarioPaciente = new UsuarioPaciente();
        puntuacion_alta = root.findViewById(R.id.puntuacion_alta);
        inicializa_firebase();
        actualizaPuntuacionAlta();

//        final TextView textView = root.findViewById(R.id.text_inicio);
//        Actividad1ViewModel.getText().observe(getViewLifecycleOwner(), s -> textView.setText(s));
        return root;
    }
    public void actualizaPuntuacionAlta(){

        databaseReference.child("Usuario").child(firebaseUser.getUid()).child("Paciente").addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()&& snapshot.hasChildren()){
                    int int_puntuacion_actividad_1 = Integer.parseInt(snapshot.child("int_puntuacion_alta_actividad_1").getValue().toString());
                    usuarioPaciente.setInt_puntuacion_alta_actividad_1(int_puntuacion_actividad_1);
                    puntuacion_alta.setText(""+usuarioPaciente.getInt_puntuacion_alta_actividad_1());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    /**
     * Crea he inicializa las instancias de Firebase Authentication y obtiene la referencia de
     * Firebase Database
     */
    private void inicializa_firebase() {

        mAuth = FirebaseAuth.getInstance();
        firebase_database = FirebaseDatabase.getInstance();
        databaseReference = firebase_database.getReference();
        firebaseUser = mAuth.getCurrentUser();
    }
    private void Componentes(View root) {
        EditTextComponent(root);
        Botones(root);
        TextViewComponent(root);
        ImageViewComponent(root);
    }

    private void EditTextComponent(View root) {
        usuario_animal = root.findViewById(R.id.txtAnimal);
    }

    private void Botones(View root) {
        aceptar = root.findViewById(R.id.btnAceptar);

        aceptar.setOnClickListener(this);
    }

    private void TextViewComponent(View root) {
        mensaje_intentos = root.findViewById(R.id.lblIntentos);
        mensaje_cuenta = root.findViewById(R.id.lblcuenta);
        puntuacion_actual_numero = root.findViewById(R.id.puntuacion_actual_numero);
    }

    private void ImageViewComponent(View root) {
        miimagen = root.findViewById(R.id.IMVanimal);
    }

    @Override
    public void onClick(View v) {


        String nombre = usuario_animal.getText().toString().toLowerCase();
        if (nombre.equals(nombre_animal[numero_generado])) {
            establecer_animal(numero_generado);
            //      Aumentar puntuación en 1 y situarlo en la etiqueta
            puntuacion++;
            puntuacion_actual_numero.setText("" + puntuacion);

            esperar();
        } else {
            Toast.makeText(main.getApplicationContext(), "Ese no es el animal :c", Toast.LENGTH_SHORT).show();
            intentos = intentos - 1;
            mensaje_intentos.setText("Tiene " + intentos + " intentos");
            usuario_animal.setText("");
        }
        if (intentos == 0) {
            // Guardar en firebase
            score_shadows = puntuacion_actual_numero.getText().toString();
            guardaProgreso(Integer.parseInt(score_shadows));

            //main.finish();
        }
    }
    public void guardaProgreso(int puntuacion){

        usuarioPadreTutor.setString_id(firebaseUser.getUid());

        usuarioPaciente.setInt_puntuacion_actividad_1(puntuacion);

        databaseReference.child("Usuario").child(usuarioPadreTutor.getString_id()).child("Paciente").child("int_puntuacion_actividad_1").setValue(usuarioPaciente.getInt_puntuacion_actividad_1());

        databaseReference.child("Usuario").child(usuarioPadreTutor.getString_id()).child("Paciente").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    usuarioPaciente.setInt_puntuacion_alta_actividad_1(Integer.parseInt(snapshot.child("int_puntuacion_alta_actividad_1").getValue().toString()));
                    usuarioPaciente.setInt_contador_actividad_1(Integer.parseInt(snapshot.child("int_contador_actividad_1").getValue().toString()));
                    usuarioPaciente.setInt_suma_puntuacion_actividad_1(Integer.parseInt(snapshot.child("int_suma_actividad_1").getValue().toString()));
                    usuarioPaciente.setFloat_promedio_actividad_1(Float.parseFloat(snapshot.child("float_promedio_actividad_1").getValue().toString()));

                    int puntuacion_actual = usuarioPaciente.getInt_puntuacion_actividad_1(), puntuacion_actual_alta = usuarioPaciente.getInt_puntuacion_alta_actividad_1(),
                            contador = usuarioPaciente.getInt_contador_actividad_1(), suma = usuarioPaciente.getInt_suma_puntuacion_actividad_1();

                    float promedio;

                    contador++;
                    suma+= usuarioPaciente.getInt_suma_puntuacion_actividad_1();
                    promedio = suma/contador;

                    databaseReference.child("Usuario").child(usuarioPadreTutor.getString_id()).child("Paciente").child("int_contador_actividad_1").setValue(contador);
                    databaseReference.child("Usuario").child(usuarioPadreTutor.getString_id()).child("Paciente").child("int_suma_actividad_1").setValue(suma);
                    databaseReference.child("Usuario").child(usuarioPadreTutor.getString_id()).child("Paciente").child("float_promedio_actividad_1").setValue(promedio);

                    if (puntuacion_actual > puntuacion_actual_alta) {

                        databaseReference.child("Usuario").child(usuarioPadreTutor.getString_id()).child("Paciente").child("int_puntuacion_alta_actividad_1").setValue(puntuacion_actual);

                        Toast.makeText(getContext(), "Nueva puntuación alta: "+puntuacion_actual, Toast.LENGTH_SHORT).show();

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void esperar() {
        new CountDownTimer(5000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                mensaje_cuenta.setText("Generando en " + (millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                numero_generado = generaraleatorio();
                establecer_sombra(numero_generado);
                mensaje_cuenta.setText("");
                usuario_animal.setText("");
            }
        }.start();
    }

    private void establecer_sombra(int numero) {
        int id = getResources().getIdentifier(sombra_animal[numero], "drawable", main.getPackageName());
        miimagen.setImageResource(id);
    }

    private void establecer_animal(int numero) {
        int id = getResources().getIdentifier(nombre_animal[numero], "drawable", main.getPackageName());
        miimagen.setImageResource(id);
    }


    private int generaraleatorio() {
        return (int) (Math.random() * nombre_animal.length);
    }
}