package com.example.tdah.usuario.padres;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.tdah.PadrePrincipal;
import com.example.tdah.R;
import com.example.tdah.UsuarioPrincipal;
import com.example.tdah.modelos.UsuarioPadreTutor;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class PadresFragment extends Fragment{
    private PadresViewModel padresViewModel;
    private Button btn_padres_nip,btn_padres_regresar;
    private EditText txt_nip;

    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private  UsuarioPadreTutor u;
    private boolean boolean_nip;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        padresViewModel =
                new ViewModelProvider(this).get(PadresViewModel.class);
        View root = inflater.inflate(R.layout.fragment_padres, container, false);

        txt_nip = root.findViewById(R.id.editTextNip);
        btn_padres_nip = root.findViewById(R.id.btn_PadresNip);
        btn_padres_regresar = root.findViewById(R.id.btn_PadresCancelar);

        u = new UsuarioPadreTutor();
        inicializa_firebase();

        txt_nip.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean_nip = valida_nip(txt_nip);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        if(!boolean_nip) btn_padres_nip.setEnabled(true);

        btn_padres_nip.setOnClickListener(v -> {
            if(!autentica_nip(txt_nip)){

                    startActivity(new Intent(getContext(), PadrePrincipal.class));

                }else{
                    Toast.makeText(getContext(), "Verifique el nipA", Toast.LENGTH_SHORT).show();
                }


        });

        btn_padres_regresar.setOnClickListener(v -> ir_a_usuario_principal());


        return root;
    }

    private boolean autentica_nip(EditText editText_nip) {

        UsuarioPadreTutor u = new UsuarioPadreTutor();

        editText_nip.setError(null);
        String nip = editText_nip.getText().toString().trim();
        boolean boolean_nip = true;
        View focusView = null;

        u.setString_id(firebaseUser.getUid());

        databaseReference.child("Usuario").child(u.getString_id()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    u.setInt_nip(Integer.parseInt(Objects.requireNonNull(snapshot.child("int_nip").getValue()).toString()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        if (Integer.parseInt(nip) != u.getInt_nip()){
            editText_nip.setError(getString(R.string.error_nip_incorrecto));
            boolean_nip = false;
            focusView = editText_nip;
        }

        if(!boolean_nip)
        focusView.requestFocus();

        return boolean_nip;

    }

    /**
     * Crea he inicializa las instancias de Firebase Authentication y obtiene la referencia de
     * Firebase Database
     */
    private void inicializa_firebase() {
        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase firebase_database = FirebaseDatabase.getInstance();
        databaseReference = firebase_database.getReference();
        firebaseUser = mAuth.getCurrentUser();
    }
    /**
     * Esta funcion retorna verdadero si el nip tiene errores y  si es falso no tiene errores
     *
     * @param editText_nip EditText nip
     * @return boolean_nip_v
     */
    private boolean valida_nip(EditText editText_nip) {

        editText_nip.setError(null);

        String nip = editText_nip.getText().toString().trim();

        boolean boolean_nip_v = true;

        View focusView = null;


        if (nip.isEmpty()) {
            editText_nip.setError(getString(R.string.error_campo_requerido));
            focusView = editText_nip;
            boolean_nip_v = false;
        }

        if (!nip.matches(".{4}")) {
            editText_nip.setError(getString(R.string.error_cuatro_digitos));
            focusView = editText_nip;
            boolean_nip_v = false;
        }

        if (!boolean_nip_v) {

            focusView.requestFocus();

        }

        return boolean_nip_v;
    }
    public void ir_a_usuario_principal(){
        Intent ir = new Intent(getContext(), UsuarioPrincipal.class);
        startActivity(ir);
    }
}
