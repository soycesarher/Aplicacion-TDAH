package com.example.tdah.usuario.actividad3;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.tdah.R;
import com.example.tdah.UsuarioPrincipal;
import com.example.tdah.modelos.UsuarioPaciente;
import com.example.tdah.modelos.UsuarioPadreTutor;
import com.example.tdah.usuario.Oro;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Actividad3Fragment extends Fragment{
    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase firebase_database;
    private DatabaseReference databaseReference;
    private UsuarioPaciente usuarioPaciente;
    private UsuarioPadreTutor usuarioPadreTutor;


    FragmentTransaction transaction;
    Fragment fragmentGO;

    private static ImageView oro;
    private Actividad3ViewModel Actividad3ViewModel;
    UsuarioPrincipal main;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Actividad3ViewModel =
                new ViewModelProvider(this).get(Actividad3ViewModel.class);
        View root = inflater.inflate(R.layout.fragment_actividad3, container, false);
        main = (UsuarioPrincipal) getParentFragment().getActivity();
        inicializaFirebase();
        validaCuenta();

        imagenSetup(root);

        return root;
    }
    private void inicializaFirebase() {

        mAuth = FirebaseAuth.getInstance();

        FirebaseDatabase firebase_database = FirebaseDatabase.getInstance();

        databaseReference = firebase_database.getReference();

        firebaseUser = mAuth.getCurrentUser();

    }
    private void validaCuenta() {
        databaseReference.child("Usuario").child(firebaseUser.getUid()).child("string_fecha_pago").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String string_fecha_pago = snapshot.getValue().toString();
                    if(string_fecha_pago.equals("-1")){
                        Toast.makeText( getContext(), "Actividad de pago", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getContext(),UsuarioPrincipal.class));

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void  imagenSetup(View root){
        oro= root.findViewById(R.id.image_oro);
        oro.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), Oro.class);
            startActivity(intent);
        });
    }

}
