package com.example.tdah.usuario.cuenta;

import static android.content.ContentValues.TAG;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.tdah.R;
import com.example.tdah.modelos.UsuarioPaciente;
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

    private FirebaseAuth mAuth;
    private FirebaseUser fUser;
    private Button btn_cerrar_sesion;
    private TextView txt_nombre;
    private TextView txt_apellido_paterno;
    private TextView txt_apellido_materno;
    private TextView txt_curp;
    private TextView txt_correo;
    private DatabaseReference databaseReference;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CuentaViewModel cuentaViewModel = new ViewModelProvider(this).get(CuentaViewModel.class);
        View root = inflater.inflate(R.layout.fragment_cuenta, container, false);
        final TextView textView = root.findViewById(R.id.text_cuenta);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        txt_nombre = (TextView) textView.findViewById(R.id.txt_cuenta_nombre);
        txt_apellido_paterno = (TextView) textView.findViewById(R.id.txt_cuenta_apellido_paterno);
        txt_apellido_materno = (TextView) textView.findViewById(R.id.txt_cuenta_apellido_materno);
        txt_curp = (TextView) textView.findViewById(R.id.txt_cuenta_curp);
        txt_correo = (TextView) textView.findViewById(R.id.txt_cuenta_correo);
        btn_cerrar_sesion = (Button) btn_cerrar_sesion.findViewById(R.id.btn_cuenta_editar);

        cuentaViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        datosUsuario();

        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
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
}