package com.example.tdah.psicologo.cuentaPsicologo;

import static android.content.ContentValues.TAG;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Switch;
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
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;
import java.util.regex.Pattern;

public class CuentaPsicologoFragment extends Fragment
{

    private FirebaseAuth mAuth;
    private FirebaseUser fUser;
    private DatabaseReference databaseReference;

    private EditText txt_nombre_psicologo_cuenta;
    private EditText txt_apellido_paterno_psicologo_cuenta;
    private EditText txt_apellido_materno_psicologo_cuenta;
    private EditText txt_direccion_consultorio_psicologo_cuenta;
    private EditText txt_telefono_psicologo_cuenta;
    private EditText txt_cedula_psicologo_cuenta;


    private EditText txt_calle;
    private EditText txt_num_exterior;
    private EditText txt_cp;

    private EditText txt_contrasena_nueva;
    private EditText txt_correo;


    String estado, municipio, localidad;

    private Spinner sp_estado;
    private Spinner sp_municipio;
    private Spinner sp_localidad;

    private Button btn_guardar_cedula;

    private Button btn_guardar_correo, btn_guardar_contrasena;

    private Switch sw_correo;
    private Switch sw_contrasena;
    private Switch sw_cedula;
    private Switch sw_datos;

    private ProgressBar pb_progreso_carga;
    private PDFView pdf_view_cuenta_psicologo;

    private CuentaPsicologoViewModel cuentaPsicologoViewModel;
    private FragmentCuentaPsicologoBinding binding;

    private boolean boolean_correo;
    private boolean boolean_contrasena = false, boolean_verificado = false;
    private boolean boolean_error_cedula;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {

        cuentaPsicologoViewModel =
                new ViewModelProvider(this).get(CuentaPsicologoViewModel.class);

        binding = FragmentCuentaPsicologoBinding.inflate(inflater, container, false);
        View root = inflater.inflate(R.layout.fragment_cuenta_psicologo, container, false);

        inicializa_firebase();
        componentes(root);
        datosUsuario();
        sw_correo.setOnCheckedChangeListener((compoundButton, b) ->
        {
            if (b)
            {
                btn_guardar_correo.setVisibility(View.VISIBLE);
                txt_correo.setVisibility(View.VISIBLE);
                txt_correo.setEnabled(true);
                txt_correo.addTextChangedListener(new TextWatcher()
                {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after)
                    {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count)
                    {

                        valida_correo(txt_correo);
                    }

                    @Override
                    public void afterTextChanged(Editable s)
                    {

                    }
                });

            } else
            {
                txt_correo.setText("");
                txt_correo.setVisibility(View.GONE);
                btn_guardar_correo.setVisibility(View.GONE);

            }
        });

        sw_contrasena.setOnCheckedChangeListener((compoundButton, b) ->
        {
            if (b)
            {
                btn_guardar_contrasena.setVisibility(View.VISIBLE);
                txt_contrasena_nueva.setVisibility(View.VISIBLE);
                txt_contrasena_nueva.setEnabled(true);
                txt_contrasena_nueva.addTextChangedListener(new TextWatcher()
                {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after)
                    {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count)
                    {
                        valida_contrasena(txt_contrasena_nueva);
                    }

                    @Override
                    public void afterTextChanged(Editable s)
                    {

                    }
                });

            } else
            {
                txt_contrasena_nueva.setText("");
                btn_guardar_contrasena.setVisibility(View.GONE);
                txt_contrasena_nueva.setVisibility(View.GONE);

            }
        });


        txt_cedula_psicologo_cuenta.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                boolean_error_cedula = valida_cedula(txt_cedula_psicologo_cuenta);
            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });


        btn_guardar_cedula.setOnClickListener(v ->
        {

            if (sw_datos.isChecked()) actualizaCedula(txt_cedula_psicologo_cuenta);

        });

        btn_guardar_correo.setOnClickListener(view -> mostrar(root));

        btn_guardar_contrasena.setOnClickListener(view -> mostrar(root));

        return root;
    }

    private void inicializa_firebase() {

        mAuth = FirebaseAuth.getInstance();

        FirebaseDatabase firebase_database = FirebaseDatabase.getInstance();

        databaseReference = firebase_database.getReference();

        fUser = mAuth.getCurrentUser();

    }
    private void actualizaCedula(EditText txt_cedula_psicologo_cuenta)
    {
        String string_cedula = txt_cedula_psicologo_cuenta.getText().toString();
        databaseReference.child("Psicologo").child(fUser.getUid()).child("int_cedula").setValue(string_cedula);
        Toast.makeText(getContext(), "La cédula se actualizó con éxito", Toast.LENGTH_LONG).show();
        startActivity(new Intent(getContext(), PsicologoPrincipal.class));
    }


    private void componentes(View root)
    {
        txt_nombre_psicologo_cuenta = root.findViewById(R.id.txt_NombrePsicologo);
        txt_apellido_paterno_psicologo_cuenta = root.findViewById(R.id.txt_ApellidoP_Psicologo);
        txt_apellido_materno_psicologo_cuenta = root.findViewById(R.id.txt_ApellidoM_Psicologo);
        txt_cedula_psicologo_cuenta = root.findViewById(R.id.txt_Cedula);
        txt_direccion_consultorio_psicologo_cuenta = root.findViewById(R.id.txt_direccion_consultorio);
        txt_correo = root.findViewById(R.id.txt_cuenta_correo);
        txt_telefono_psicologo_cuenta = root.findViewById(R.id.txt_telefono_psicologo);
        txt_contrasena_nueva = root.findViewById(R.id.txt_cuenta_contrasenia_nueva);


        btn_guardar_correo = root.findViewById(R.id.btn_cuenta_guardar_co);
        btn_guardar_contrasena = root.findViewById(R.id.btn_cuenta_guardar_cc);
        btn_guardar_cedula = root.findViewById(R.id.btn_cuenta_guardar_ce);

        sw_correo = root.findViewById(R.id.sw_correo_usuario);
        sw_contrasena = root.findViewById(R.id.sw_contrasena_usuario);
        sw_datos = root.findViewById(R.id.sw_actualiza_cedula);


        pdf_view_cuenta_psicologo = root.findViewById(R.id.pdf_view_cuenta_psicologo);
        pb_progreso_carga = root.findViewById(R.id.pb_progreso_pdf);


    }

    private void datosUsuario()
    {
        UsuarioPsicologo p = new UsuarioPsicologo();

        p.setString_id(Objects.requireNonNull(mAuth.getCurrentUser()).getUid());
        p.setString_correo(fUser.getEmail());

        databaseReference.child("Usuario").child(p.getString_id()).addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {

                if (snapshot.exists())
                {
                    p.setString_nombre(Objects.requireNonNull(snapshot.child("string_nombre").getValue()).toString());
                    p.setString_apellido_paterno(Objects.requireNonNull(snapshot.child("string_apellido_paterno").getValue()).toString());
                    p.setString_apellido_materno(Objects.requireNonNull(snapshot.child("string_apellido_materno").getValue()).toString());
                    p.setInt_cedula(Integer.parseInt(Objects.requireNonNull(snapshot.child("int_cedula").getValue()).toString()));
                    p.setLong_telefono(Integer.parseInt(Objects.requireNonNull(snapshot.child("int_telefono").getValue()).toString()));
                    p.setString_direccion(Objects.requireNonNull(snapshot.child("string_direccion").getValue()).toString());
                    p.setString_perfilProfesional(Objects.requireNonNull(snapshot.child("string_perfilProfesional").getValue()).toString());
                    p.setString_fecha_fin_suscripcion(Objects.requireNonNull(snapshot.child("string_fecha_fin_suscripcion").getValue()).toString());

                    if (!p.getString_fecha_fin_suscripcion().equals("-1"))
                        boolean_verificado = true;

                    new Pdf(pdf_view_cuenta_psicologo, pb_progreso_carga).execute(p.getString_perfilProfesional());
                    txt_nombre_psicologo_cuenta.setText(p.getString_nombre());
                    txt_apellido_paterno_psicologo_cuenta.setText(p.getString_apellido_paterno());
                    txt_apellido_materno_psicologo_cuenta.setText(p.getString_apellido_materno());
                    txt_cedula_psicologo_cuenta.setText(String.valueOf(p.getInt_cedula()));
                    txt_direccion_consultorio_psicologo_cuenta.setText(p.getString_direccion());
                    txt_telefono_psicologo_cuenta.setText(String.valueOf(p.getLong_telefono()));

                    Log.e(TAG, "Datos recuperados");

                } else
                {
                    Log.e(TAG, "Datos no recuperados");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

                Log.w(TAG, "loadPost:onCancelled", error.toException());
            }
        });

    }

    Dialog customDialog = null;

    public void mostrar(View view)
    {

        customDialog = new Dialog(getContext(), R.style.Theme_AppCompat_Dialog);

        customDialog.setCancelable(false);

        customDialog.setContentView(R.layout.layout_reautentica);

        String string_correo_dialog = ((EditText) customDialog.findViewById(R.id.username)).getText().toString();


        String string_contrasena_dialog = ((EditText) customDialog.findViewById(R.id.password)).getText().toString();

        ((Button) customDialog.findViewById(R.id.btn_iniciar_dialog)).setOnClickListener(view1 ->
                {
                    reauthenticate(string_correo_dialog, string_contrasena_dialog);
                    customDialog.dismiss();
                }
        );

        ((Button) customDialog.findViewById(R.id.btn_cancelar_dialog)).setOnClickListener(view12 ->
        {
            sw_contrasena.setChecked(false);
            sw_correo.setChecked(false);
            customDialog.dismiss();
        });

        customDialog.show();
    }

    private void reauthenticate(String string_correo_dialog, String string_contrasena_dialog)
    {

        AuthCredential credential = EmailAuthProvider.getCredential(string_correo_dialog, string_contrasena_dialog);

        fUser.reauthenticate(credential).addOnCompleteListener(task ->
        {
            if (task.isSuccessful())
            {
                if (task.isComplete())
                {
                    if (sw_contrasena.isChecked()) actualizaContrasena(txt_contrasena_nueva.getText().toString());

                    if (sw_correo.isChecked()) actualizaCorreo(txt_correo.getText().toString());
                }
            } else
            {
                Toast.makeText(getContext(), "Error de autenticacion", Toast.LENGTH_LONG).show();
            }

        });
    }

    public void actualizaCorreo(String string_correo)
    {
        Toast.makeText(getContext(), "Actualizando correo", Toast.LENGTH_SHORT).show();
        fUser.updateEmail(string_correo)
                .addOnCompleteListener(task ->
                {
                    if (task.isSuccessful())
                    {
                        databaseReference.child("Psicologo").child(fUser.getUid()).child("string_correo").setValue(string_correo);
                        Toast.makeText(getContext(), "El correo se actualizó con éxito", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getContext(), PsicologoPrincipal.class));

                    } else
                    {
                        Toast.makeText(getContext(), "ERROR: No se actualizó el correo" + task.getResult().toString(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void actualizaContrasena(String string_contrasena)
    {

        Toast.makeText(getContext(), "Actualizando contraseña", Toast.LENGTH_SHORT).show();
        fUser.updatePassword(string_contrasena)
                .addOnCompleteListener(task ->
                {
                    if (task.isSuccessful())
                    {
                        Toast.makeText(getContext(), "La contraseña se actualizó con éxito", Toast.LENGTH_LONG).show();
                    } else
                    {
                        Toast.makeText(getContext(), "ERROR: No se actualizó la contraseña" + task.getResult().toString(), Toast.LENGTH_LONG).show();
                    }
                });

    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        binding = null;
    }

    private void valida_contrasena(EditText editText_contrasena)
    {

        editText_contrasena.setError(null);

        String Password = editText_contrasena.getText().toString().trim();

        boolean boolean_contrasena_v = false;

        View focusView = null;

        if (TextUtils.isEmpty(Password))
        {
            editText_contrasena.setError(getString(R.string.error_campo_requerido));
            focusView = editText_contrasena;
            boolean_contrasena_v = true;
        }

        if (!Password.matches(".*[!@#$%^&*+=?-].*"))
        {
            editText_contrasena.setError(getString(R.string.error_caracter_especial_requerido));
            focusView = editText_contrasena;
            boolean_contrasena_v = true;
        }

        if (!Password.matches(".*\\d.*"))
        {
            editText_contrasena.setError(getString(R.string.error_numero_requerido));
            focusView = editText_contrasena;
            boolean_contrasena_v = true;
        }

        if (!Password.matches(".*[a-z].*"))
        {
            editText_contrasena.setError(getString(R.string.error_no_se_encontraron_minusculas));
            focusView = editText_contrasena;
            boolean_contrasena_v = true;
        }

        if (!Password.matches(".*[A-Z].*"))
        {
            editText_contrasena.setError(getString(R.string.error_no_se_encontraron_mayusculas));
            focusView = editText_contrasena;
            boolean_contrasena_v = true;
        }

        if (!Password.matches(".{8,15}"))
        {
            editText_contrasena.setError(getString(R.string.error_contrasena_muy_corta));
            focusView = editText_contrasena;
            boolean_contrasena_v = true;
        }

        if (Password.matches(".*\\s.*"))
        {
            editText_contrasena.setError(getString(R.string.error_sin_espacios));
            focusView = editText_contrasena;
            boolean_contrasena_v = true;
        }

        if (boolean_contrasena_v)
        {

            focusView.requestFocus();


        }

    }


    private void valida_correo(EditText editText_correo)
    {

        editText_correo.setError(null);

        boolean boolean_correo_v = true;

        View focusView = null;

        Pattern pattern = Patterns.EMAIL_ADDRESS;

        String Email = editText_correo.getText().toString().trim();

        if (TextUtils.isEmpty(Email))
        {
            editText_correo.setError(getString(R.string.error_campo_requerido));
            focusView = editText_correo;
            boolean_correo_v = false;
        } else if (!pattern.matcher(Email).matches())
        {
            editText_correo.setError(getString(R.string.error_correo_no_valido));
            focusView = editText_correo;
            boolean_correo_v = false;
        }
        if (!boolean_correo_v)
        {

            focusView.requestFocus();
            btn_guardar_correo.setEnabled(false);
        } else
        {
            btn_guardar_correo.setEnabled(true);
        }

    }


    private boolean valida_cedula(EditText editText_cedula)
    {

        editText_cedula.setError(null);

        String string_cedula = editText_cedula.getText().toString().trim();

        boolean boolean_error = true;

        View focusView = null;

        if (string_cedula.isEmpty())
        {
            editText_cedula.setError(getString(R.string.error_campo_requerido));
            focusView = editText_cedula;
            boolean_error = false;
        }

        if (!string_cedula.matches(".{7,8}"))
        {
            editText_cedula.setError(getString(R.string.error_formato_no_valido));
            focusView = editText_cedula;
            boolean_error = false;
        }

        if (!boolean_error)
        {

            focusView.requestFocus();

        }

        return boolean_error;

    }


}