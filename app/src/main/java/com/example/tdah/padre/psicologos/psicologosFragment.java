package com.example.tdah.padre.psicologos;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tdah.R;
import com.example.tdah.modelos.UsuarioPsicologo;
import com.example.tdah.pdf.AdaptadorPdf;
import com.example.tdah.pdf.PerfilPsicologo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class psicologosFragment extends Fragment implements AdaptadorPdf.OnClickListener{
    private psicologosViewModel psicologos_view_model;

    private final ArrayList<UsuarioPsicologo> lista_psicologo_modelo = new ArrayList<>();
    private AdaptadorPdf adaptadorPdf;
    private DatabaseReference databaseReference;
    private RecyclerView rv_psicologos;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        psicologos_view_model =
                new ViewModelProvider(this).get(psicologosViewModel.class);
        View root = inflater.inflate(R.layout.fragment_psicologos, container, false);
        rv_psicologos = root.findViewById(R.id.rv_psicologos);
        cargaListaPsicologos();
        return super.onCreateView(inflater, container, savedInstanceState);

    }

    public void cargaListaPsicologos() {


        databaseReference.child("Psicologo").orderByKey().orderByChild("boolean_validado").equalTo(true).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                lista_psicologo_modelo.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    UsuarioPsicologo usuarioPsicologo = dataSnapshot.getValue(UsuarioPsicologo.class);

                    lista_psicologo_modelo.add(usuarioPsicologo);

                    adaptadorPdf = new AdaptadorPdf(lista_psicologo_modelo, psicologosFragment.this);

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
                        getContext(),
                        PerfilPsicologo.class).
                        putExtra(
                                "Psicologo",
                                lista_psicologo_modelo
                        ).putExtra("Index",position)
        );
    }

}
