package com.example.tdah.usuario.actividad1;

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
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.tdah.MainActivity;
import com.example.tdah.R;
import com.example.tdah.UsuarioPrincipal;
import com.example.tdah.usuario.inicio.InicioViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Actividad1Fragment extends Fragment implements View.OnClickListener{
    private Actividad1ViewModel Actividad1ViewModel;
    private String[] nombre_animal={"tucan","caballo","perro","gato","conejo","leon","pato","rinoceronte"};
    private String[] sombra_animal={"s_tucan","s_caballo","s_perro","s_gato","s_conejo","s_leon","s_pato","s_rinoceronte"};
    private int intentos=3;
    private Button aceptar;
    private TextView mensaje_intentos,mensaje_cuenta, puntuacion_actual_numero, puntuacion_alta;
    private EditText usuario_animal;
    private int numero_generado=0;
    private ImageView miimagen;
    // puntajes
    private int puntuacion = 0;
    private String score_shadows, userid;

    UsuarioPrincipal main;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Actividad1ViewModel =
                new ViewModelProvider(this).get(Actividad1ViewModel.class);
        View root = inflater.inflate(R.layout.fragment_actividad1, container, false);
        Componentes(root);
        main = (UsuarioPrincipal) getParentFragment().getActivity();
        numero_generado=generaraleatorio();
        establecer_sombra(numero_generado);
        mensaje_intentos.setText("Tiene " + intentos + " intentos");
        userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        puntuacion_alta = root.findViewById(R.id.puntuacion_alta);


        final TextView textView = root.findViewById(R.id.text_inicio);
        Actividad1ViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    private void Componentes(View root){
        EditTextComponent(root);
        Botones(root);
        TextViewComponent(root);
        ImageViewComponent(root);
    }
    private  void  EditTextComponent(View root){
        usuario_animal = root.findViewById(R.id.txtAnimal);
    }
    private void Botones(View root){
        aceptar = root.findViewById(R.id.btnAceptar);

        aceptar.setOnClickListener(this);
    }
    private void TextViewComponent(View root){
        mensaje_intentos = root.findViewById(R.id.lblIntentos);
        mensaje_cuenta = root.findViewById(R.id.lblcuenta);
        puntuacion_actual_numero = root.findViewById(R.id.puntuacion_actual_numero);
    }
    private void ImageViewComponent(View root){
        miimagen = root.findViewById(R.id.IMVanimal);
    }

    @Override
    public void onClick(View v) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myref = database.getReference();
        String nombre=usuario_animal.getText().toString().toLowerCase();
        if(nombre.equals(nombre_animal[numero_generado])){
            establecer_animal(numero_generado);
            //      Aumentar puntuación en 1 y situarlo en la etiqueta
            puntuacion++;
            puntuacion_actual_numero.setText( "" + puntuacion);

            esperar();
        }else {
            Toast.makeText(main.getApplicationContext(),"Ese no es el animal :c", Toast.LENGTH_SHORT).show();
            intentos=intentos-1;
            mensaje_intentos.setText("Tiene " + intentos + " intentos");
            usuario_animal.setText("");
        }if(intentos==0){
            //      Guardar en firebase
            score_shadows = puntuacion_actual_numero.getText().toString();
            //score.setScore_shadows(score_shadows);
            //score.setUserid(userid);
            //score.update(GalleryFragment.this.getContext());
            myref.child("scores").child(userid).child("score_shadows").setValue(score_shadows);

            //main.finish();
        }
    }

    public void esperar(){
        new CountDownTimer(5000,1000){

            @Override
            public void onTick(long millisUntilFinished) {
                mensaje_cuenta.setText("Generando en " + (millisUntilFinished/1000));
            }

            @Override
            public void onFinish() {
                numero_generado=generaraleatorio();
                establecer_sombra(numero_generado);
                mensaje_cuenta.setText("");
                usuario_animal.setText("");
            }
        }.start();
    }

    private void establecer_sombra(int numero){
        int id = getResources().getIdentifier(sombra_animal[numero], "drawable", main.getPackageName());
        miimagen.setImageResource(id);
    }

    private void establecer_animal(int numero){
        int id = getResources().getIdentifier(nombre_animal[numero], "drawable", main.getPackageName());
        miimagen.setImageResource(id);
    }


    private int generaraleatorio(){
        return (int)(Math.random()*nombre_animal.length);
    }
}