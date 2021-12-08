package com.example.tdah;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tdah.modelos.UsuarioPsicologo;
import com.example.tdah.pdf.AdaptadorPdf;
import com.example.tdah.pdf.PerfilPsicologo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListaPsicologos extends AppCompatActivity implements AdaptadorPdf.OnClickListener {

    private final ArrayList<UsuarioPsicologo> lista_psicologo_modelo = new ArrayList<>();
    private AdaptadorPdf adaptadorPdf;
    private DatabaseReference databaseReference;
    private RecyclerView rv_psicologos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_psicologos);
        rv_psicologos = findViewById(R.id.rv_psicologos);
        cargaListaPsicologos();

    }

    public void cargaListaPsicologos() {


        databaseReference.child("Psicologo").orderByKey().orderByChild("boolean_validado").equalTo(true).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                lista_psicologo_modelo.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    UsuarioPsicologo usuarioPsicologo = dataSnapshot.getValue(UsuarioPsicologo.class);

                    lista_psicologo_modelo.add(usuarioPsicologo);

                    adaptadorPdf = new AdaptadorPdf(lista_psicologo_modelo, ListaPsicologos.this);

                    rv_psicologos.setAdapter(adaptadorPdf);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onClick(int position) {
        startActivity(
                new Intent(
                        ListaPsicologos.this,
                        PerfilPsicologo.class).
                        putExtra(
                                "Psicologo",
                                lista_psicologo_modelo
                        ).putExtra("Index",position)
        );
    }
}