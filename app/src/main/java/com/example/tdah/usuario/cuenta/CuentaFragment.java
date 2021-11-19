package com.example.tdah.usuario.cuenta;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.tdah.R;
import com.example.tdah.modelos.UsuarioPadreTutor;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class CuentaFragment extends Fragment {

    private CuentaViewModel cuentaViewModel;
    private FirebaseAuth mAuth;
    private FirebaseUser fUser;
    private Button btn_editar;
    private EditText txt_nombre;
    private EditText txt_apellido_paterno;
    private EditText txt_apellido_materno;
    private EditText txt_curp;
    private EditText txt_correo;
    private DatabaseReference databaseReference;

    public CuentaFragment(){
        super(R.layout.fragment_cuenta);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        cuentaViewModel =
                new ViewModelProvider(this).get(CuentaViewModel.class);
        View root = inflater.inflate(R.layout.fragment_cuenta, container, false);
        final TextView textView = root.findViewById(R.id.text_cuenta);

        txt_nombre = root.findViewById(R.id.txt_cuenta_nombre);
        txt_apellido_paterno = root.findViewById(R.id.txt_cuenta_apellido_paterno);
        txt_apellido_materno = root.findViewById(R.id.txt_cuenta_apellido_materno);
        txt_curp = root.findViewById(R.id.txt_cuenta_curp);
        txt_correo = root.findViewById(R.id.txt_cuenta_correo);
        btn_editar = (Button) root.findViewById(R.id.btn_cuenta_editar);

        mAuth = FirebaseAuth.getInstance();
        fUser = mAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();


        datosUsuario();
/*        cuentaViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        return root;
    }

    private void datosUsuario() {
        UsuarioPadreTutor u = new UsuarioPadreTutor();

        u.setString_id(Objects.requireNonNull(mAuth.getCurrentUser()).getUid());
        u.setString_correo(fUser.getEmail());
        databaseReference.child("Usuario").child(u.getString_id()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {
                    u.setString_nombre(Objects.requireNonNull(snapshot.child("string_nombre").getValue()).toString());
                    u.setString_apellido_paterno(Objects.requireNonNull(snapshot.child("string_apellido_paterno").getValue()).toString());
                    u.setString_apellido_materno(Objects.requireNonNull(snapshot.child("string_apellido_materno").getValue()).toString());
                    u.setString_curp(Objects.requireNonNull(snapshot.child("string_curp").getValue()).toString());
                    txt_nombre.setText(u.getString_nombre());
                    txt_apellido_paterno.setText(u.getString_apellido_paterno());
                    txt_apellido_materno.setText(u.getString_apellido_materno());
                    txt_correo.setText(u.getString_correo());
                    txt_curp.setText(u.getString_curp());
                    Log.e(TAG, "Datos recuperados");

                } else {
                    Log.e(TAG, "Datos no recuperados");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Log.w(TAG, "loadPost:onCancelled", error.toException());
            }
        });

    }

    public void updateEmail() {
        // [START update_email]
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.updateEmail("user@example.com")
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "User email address updated.");
                    }
                });
        // [END update_email]
    }

    public void updatePassword() {
        // [START update_password]
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String newPassword = "SOME-SECURE-PASSWORD";

        user.updatePassword(newPassword)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "User password updated.");
                    }
                });
        // [END update_password]
    }
}