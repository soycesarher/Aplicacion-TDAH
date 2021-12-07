package com.example.tdah.psicologo.cuentaPsicologo;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.tdah.PsicologoPrincipal;
import com.example.tdah.R;
import com.example.tdah.databinding.FragmentCuentaPsicologoBinding;
import com.example.tdah.modelos.UsuarioPsicologo;
import com.example.tdah.pdf.Pdf;
import com.github.barteksc.pdfviewer.PDFView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;
import java.util.regex.Pattern;

public class cuentaPsicologoFragment extends Fragment {

    private FirebaseAuth mAuth;
    private FirebaseUser fUser;
    private DatabaseReference databaseReference;

    private EditText txt_nombre_psicologo_cuenta, txt_apellido_paterno_psicologo_cuenta,
                    txt_apellido_materno_psicologo_cuenta, txt_direccion_consultorio_psicologo_cuenta,
                    txt_correo_psicologo_cuenta,txt_telefono_psicologo_cuenta,txt_cedula_psicologo_cuenta;

    private Button btn_editar_psicologo, btn_guardar_psicologo;

    private ProgressBar pb_progreso_carga;
    private PDFView pdf_view_cuenta_psicologo;

    private cuentaPsicologoViewModel cuentaPsicologoViewModel;
    private FragmentCuentaPsicologoBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        cuentaPsicologoViewModel =
                new ViewModelProvider(this).get(cuentaPsicologoViewModel.class);

        binding = FragmentCuentaPsicologoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.txtNombrePsicologo;

        cuentaPsicologoViewModel.getText().observe(getViewLifecycleOwner(), s -> textView.setText(s));

       componentes(root);


        return root;
    }

    private void componentes(View root) {
        txt_nombre_psicologo_cuenta = root.findViewById(R.id.txt_NombrePsicologo);
        txt_apellido_paterno_psicologo_cuenta = root.findViewById(R.id.txt_ApellidoP_Psicologo);
        txt_apellido_materno_psicologo_cuenta = root.findViewById(R.id.txt_ApellidoM_Psicologo);
        txt_cedula_psicologo_cuenta = root.findViewById(R.id.txt_Cedula);
        txt_direccion_consultorio_psicologo_cuenta = root.findViewById(R.id.txt_Direccion_Consultorio);
        txt_correo_psicologo_cuenta = root.findViewById(R.id.txt_CorreoContacto_Psicologo);
        txt_telefono_psicologo_cuenta = root.findViewById(R.id.txt_Telefono_Psicologo);

        pdf_view_cuenta_psicologo = root.findViewById(R.id.pdf_view_cuenta_psicologo);
        pb_progreso_carga = root.findViewById(R.id.pb_progreso_pdf);

        btn_editar_psicologo = root.findViewById(R.id.btn_Editar_Psicologo);
        btn_guardar_psicologo = root.findViewById(R.id.btn_Guardar_Psicologo);
    }

    private void datosUsuario() {
        UsuarioPsicologo p = new UsuarioPsicologo();

        p.setString_id(Objects.requireNonNull(mAuth.getCurrentUser()).getUid());
        p.setString_correo(fUser.getEmail());

        databaseReference.child("Usuario").child(p.getString_id()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {
                    p.setString_nombre(Objects.requireNonNull(snapshot.child("string_nombre").getValue()).toString());
                    p.setString_apellido_paterno(Objects.requireNonNull(snapshot.child("string_apellido_paterno").getValue()).toString());
                    p.setString_apellido_materno(Objects.requireNonNull(snapshot.child("string_apellido_materno").getValue()).toString());
                    p.setInt_cedula(Integer.parseInt(snapshot.child("int_cedula").getValue().toString()));
                    p.setInt_telefono(Integer.parseInt(snapshot.child("int_telefono").getValue().toString()));
                    p.setString_direccion(snapshot.child("string_direccion").getValue().toString());
                    p.setString_perfilProfesional(snapshot.child("string_perfilProfesional").getValue().toString());

                    new Pdf(pdf_view_cuenta_psicologo,pb_progreso_carga).execute(p.getString_perfilProfesional());
                    txt_nombre_psicologo_cuenta.setText(p.getString_nombre());
                    txt_apellido_paterno_psicologo_cuenta.setText(p.getString_apellido_paterno());
                    txt_apellido_materno_psicologo_cuenta.setText(p.getString_apellido_materno());
                    txt_cedula_psicologo_cuenta.setText(String.valueOf(p.getInt_cedula()));
                    txt_telefono_psicologo_cuenta.setText(String.valueOf(p.getInt_telefono()));
                    txt_direccion_consultorio_psicologo_cuenta.setText(p.getString_direccion());



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



    public void actualizaCorreo(String string_correo) {
        Toast.makeText(getContext(), "Actualizando correo", Toast.LENGTH_SHORT).show();
        fUser.updateEmail(string_correo)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        databaseReference.child("Psicologo").child(fUser.getUid()).child("string_correo").setValue(string_correo);
                        Toast.makeText(getContext(), "El correo se actualizó con éxito", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getContext(), PsicologoPrincipal.class));

                    }else{
                        Toast.makeText(getContext(), "ERROR: No se actualizó el correo"+task.getResult().toString(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void actualizaContrasena(String string_contrasena) {

        Toast.makeText(getContext(), "Actualizando contraseña", Toast.LENGTH_SHORT).show();
        fUser.updatePassword(string_contrasena)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getContext(), "La contraseña se actualizó con éxito", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(getContext(), "ERROR: No se actualizó la contraseña"+task.getResult().toString(), Toast.LENGTH_LONG).show();
                    }
                });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    /**
     * Esta funcion retorna verdadero si la contrasena tiene errores y  si es falso no tiene errores
     *
     * @param editText_contrasena EditText contrasena
     * @return boolean_error
     */
    private boolean valida_contrasena(EditText editText_contrasena) {

        editText_contrasena.setError(null);

        String Password = editText_contrasena.getText().toString().trim();

        boolean boolean_contrasena_v = false;

        View focusView = null;

        if (TextUtils.isEmpty(Password)) {
            editText_contrasena.setError(getString(R.string.error_campo_requerido));
            focusView = editText_contrasena;
            boolean_contrasena_v = true;
        }

        if (!Password.matches(".*[!@#$%^&*+=?-].*")) {
            editText_contrasena.setError(getString(R.string.error_caracter_especial_requerido));
            focusView = editText_contrasena;
            boolean_contrasena_v = true;
        }

        if (!Password.matches(".*\\d.*")) {
            editText_contrasena.setError(getString(R.string.error_numero_requerido));
            focusView = editText_contrasena;
            boolean_contrasena_v = true;
        }

        if (!Password.matches(".*[a-z].*")) {
            editText_contrasena.setError(getString(R.string.error_no_se_encontraron_minusculas));
            focusView = editText_contrasena;
            boolean_contrasena_v = true;
        }

        if (!Password.matches(".*[A-Z].*")) {
            editText_contrasena.setError(getString(R.string.error_no_se_encontraron_mayusculas));
            focusView = editText_contrasena;
            boolean_contrasena_v = true;
        }

        if (!Password.matches(".{8,15}")) {
            editText_contrasena.setError(getString(R.string.error_contrasena_muy_corta));
            focusView = editText_contrasena;
            boolean_contrasena_v = true;
        }

        if (Password.matches(".*\\s.*")) {
            editText_contrasena.setError(getString(R.string.error_sin_espacios));
            focusView = editText_contrasena;
            boolean_contrasena_v = true;
        }

        if (boolean_contrasena_v) {

            focusView.requestFocus();

        }
        return boolean_contrasena_v;
    }

    private boolean valida_confirma_contrasena(EditText editText_contrasena, EditText editText_confirma) {

        editText_contrasena.setError(null);

        String Password = editText_contrasena.getText().toString().trim();

        boolean boolean_contrasena_v = false;

        View focusView = null;

        if (!editText_contrasena.getText().toString().equals(editText_confirma.getText().toString())) {
            editText_contrasena.setError(getString(R.string.error_contrasena_diferente));
            focusView = editText_contrasena;
            boolean_contrasena_v = true;
        }

        if (boolean_contrasena_v) {

            focusView.requestFocus();

        }
        return boolean_contrasena_v;
    }

    /**
     * Esta funcion retorna verdadero si el correo tiene errores y falso si el correo no tiene errores
     *
     * @param editText_correo EditText correo
     * @return boolean_error
     */
    private boolean valida_correo(EditText editText_correo) {

        editText_correo.setError(null);

        boolean boolean_correo_v = true;

        View focusView = null;

        Pattern pattern = Patterns.EMAIL_ADDRESS;

        String Email = editText_correo.getText().toString().trim();

        if (TextUtils.isEmpty(Email)) {
            editText_correo.setError(getString(R.string.error_campo_requerido));
            focusView = editText_correo;
            boolean_correo_v = false;
        } else if (!pattern.matcher(Email).matches()) {
            editText_correo.setError(getString(R.string.error_correo_no_valido));
            focusView = editText_correo;
            boolean_correo_v = false;
        }
        if (!boolean_correo_v) {

            focusView.requestFocus();

        }
        return boolean_correo_v;

    }
}