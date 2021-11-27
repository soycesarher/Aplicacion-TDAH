package com.example.tdah.usuario.actividad3;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.tdah.MainActivity;
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

        imagenSetup(root);

        return root;
    }


    public void  imagenSetup(View root){
        oro= root.findViewById(R.id.image_oro);
        oro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Oro.class);
                startActivity(intent);
            }
        });
    }

}
