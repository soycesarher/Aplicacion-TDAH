package com.example.tdah.psicologo.cuentaPsicologo;

import static android.content.ContentValues.TAG;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
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

public class cuentaPsicologoFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener{

    private FirebaseAuth mAuth;
    private FirebaseUser fUser;
    private DatabaseReference databaseReference;

    private EditText txt_nombre_psicologo_cuenta;
    private EditText txt_apellido_paterno_psicologo_cuenta;
    private EditText txt_apellido_materno_psicologo_cuenta;
    private EditText txt_direccion_consultorio_psicologo_cuenta;
    private EditText txt_correo_psicologo_cuenta;
    private EditText txt_telefono_psicologo_cuenta;
    private EditText txt_cedula_psicologo_cuenta;
    private EditText txt_contrasena;
    private EditText txt_confirma_contrasena;
    private EditText txt_calle;
    private EditText txt_num_exterior;
    private EditText txt_cp;

    String estado, municipio, localidad;

    private Spinner sp_estado;
    private Spinner sp_municipio;
    private Spinner sp_localidad;
    private Button btn_editar_psicologo, btn_guardar_psicologo;

    private ProgressBar pb_progreso_carga;
    private PDFView pdf_view_cuenta_psicologo;

    private cuentaPsicologoViewModel cuentaPsicologoViewModel;
    private FragmentCuentaPsicologoBinding binding;

    private boolean boolean_correo;
    private boolean boolean_contrasena=false,boolean_verificado=false;
    private boolean boolean_error_numero_exterior;
    private boolean boolean_error_texto;
    private boolean boolean_error_telefono;
    private boolean boolean_error_cedula;
    private boolean boolean_error_cp;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        cuentaPsicologoViewModel =
                new ViewModelProvider(this).get(cuentaPsicologoViewModel.class);

        binding = FragmentCuentaPsicologoBinding.inflate(inflater, container, false);
        View root = inflater.inflate(R.layout.fragment_cuenta_psicologo, container,false);

        final TextView textView = binding.txtNombrePsicologo;

        cuentaPsicologoViewModel.getText().observe(getViewLifecycleOwner(), s -> textView.setText(s));

       componentes(root);
       datosUsuario();
        txt_contrasena.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean_contrasena = true;
                boolean_contrasena = valida_contrasena(txt_contrasena);
                txt_confirma_contrasena.setEnabled(true);
                txt_confirma_contrasena.setVisibility(View.VISIBLE);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        txt_confirma_contrasena.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean_contrasena = true;
                boolean_contrasena = valida_confirma_contrasena(txt_confirma_contrasena, txt_contrasena);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        txt_correo_psicologo_cuenta.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean_correo = false;
                boolean_correo = valida_correo(txt_correo_psicologo_cuenta);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        txt_num_exterior.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean_error_numero_exterior = valida_numero_exterior(txt_num_exterior);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        txt_cp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean_error_cp = valida_cp(txt_cp);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        txt_calle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean_error_texto = valida_texto(txt_calle);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        txt_telefono_psicologo_cuenta.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean_error_telefono = valida_telefono(txt_telefono_psicologo_cuenta);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        txt_cedula_psicologo_cuenta.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean_error_cedula = valida_cedula(txt_cedula_psicologo_cuenta);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        txt_nombre_psicologo_cuenta.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean_error_texto = valida_texto(txt_nombre_psicologo_cuenta);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        txt_apellido_paterno_psicologo_cuenta.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean_error_texto = valida_texto(txt_apellido_paterno_psicologo_cuenta);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        txt_apellido_materno_psicologo_cuenta.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean_error_texto = valida_texto(txt_apellido_materno_psicologo_cuenta);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        btn_editar_psicologo.setOnClickListener(v -> {
            if(!boolean_verificado){

                txt_cedula_psicologo_cuenta.setEnabled(true);

            }
            sp_localidad.setVisibility(View.VISIBLE);
            sp_estado.setVisibility(View.VISIBLE);
            sp_municipio.setVisibility(View.VISIBLE);
            txt_calle.setVisibility(View.VISIBLE);
            txt_cp.setVisibility(View.VISIBLE);
            txt_num_exterior.setVisibility(View.VISIBLE);
            txt_telefono_psicologo_cuenta.setEnabled(true);
            txt_contrasena.setEnabled(true);
            txt_confirma_contrasena.setVisibility(View.VISIBLE);
            txt_confirma_contrasena.setEnabled(true);
            txt_correo_psicologo_cuenta.setEnabled(true);
        });

        if (!boolean_correo || boolean_contrasena||!boolean_error_cedula
                || !boolean_error_texto || !boolean_error_numero_exterior || !boolean_error_cp || !boolean_error_telefono) {
            btn_guardar_psicologo.setEnabled(true);
        }

        btn_guardar_psicologo.setOnClickListener(v -> {

            if (boolean_correo)
                actualizaCorreo(txt_correo_psicologo_cuenta.getText().toString());

            if (!boolean_contrasena && !txt_confirma_contrasena.getText().toString().isEmpty())
                actualizaContrasena(txt_confirma_contrasena.getText().toString());
            if(boolean_error_cedula) actualizaCedula(txt_cedula_psicologo_cuenta);
            if(boolean_error_texto&&boolean_error_cp&&boolean_error_numero_exterior) actualizaDireccion(txt_calle,txt_cp,txt_num_exterior,localidad,municipio,estado);
            if(boolean_error_telefono) actualizaTelefono(txt_telefono_psicologo_cuenta);

        });



        return root;
    }

    private void actualizaTelefono(EditText txt_telefono_psicologo_cuenta) {
        String string_telefono = txt_telefono_psicologo_cuenta.getText().toString();
        databaseReference.child("Psicologo").child(fUser.getUid()).child("long_telefono").setValue(string_telefono);
        Toast.makeText(getContext(), "El telefono se actualizó con éxito", Toast.LENGTH_LONG).show();
        startActivity(new Intent(getContext(), PsicologoPrincipal.class));
    }

    private void actualizaDireccion(EditText txt_calle, EditText txt_cp, EditText txt_num_exterior, String localidad, String municipio, String estado) {
        String string_calle = txt_calle.getText().toString();
        String string_cp = txt_cp.getText().toString();
        String string_num_ext = txt_num_exterior.getText().toString();

        databaseReference.child("Psicologo").child(fUser.getUid()).child("string_direccion").setValue(string_calle + "," + string_num_ext + "," + string_cp +
                "," + localidad + "," + municipio + "," + estado);
        Toast.makeText(getContext(), "La dirección se actualizó con éxito", Toast.LENGTH_LONG).show();
        startActivity(new Intent(getContext(), PsicologoPrincipal.class));
    }

    private void actualizaCedula(EditText txt_cedula_psicologo_cuenta) {
        String string_cedula = txt_cedula_psicologo_cuenta.getText().toString();
        databaseReference.child("Psicologo").child(fUser.getUid()).child("int_cedula").setValue(string_cedula);
        Toast.makeText(getContext(), "La cédula se actualizó con éxito", Toast.LENGTH_LONG).show();
        startActivity(new Intent(getContext(), PsicologoPrincipal.class));
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        switch (parent.getId()) {
            case R.id.sp_estado:
                if (position != 0) {
                    estado = parent.getItemAtPosition(position).toString();
                } else {
                    estado = "";
                }
                break;
            case R.id.sp_municipio:
                if (position != 0) {
                    municipio = parent.getItemAtPosition(position).toString();
                } else {
                    municipio = "";
                }
                break;
            case R.id.sp_localidad:
                if (position != 0) {
                    localidad = parent.getItemAtPosition(position).toString();
                } else {
                    localidad = "";
                }
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void componentes(View root) {
        txt_nombre_psicologo_cuenta = root.findViewById(R.id.txt_NombrePsicologo);
        txt_apellido_paterno_psicologo_cuenta = root.findViewById(R.id.txt_ApellidoP_Psicologo);
        txt_apellido_materno_psicologo_cuenta = root.findViewById(R.id.txt_ApellidoM_Psicologo);
        txt_cedula_psicologo_cuenta = root.findViewById(R.id.txt_Cedula);
        txt_direccion_consultorio_psicologo_cuenta = root.findViewById(R.id.txt_Direccion_Consultorio);
        txt_correo_psicologo_cuenta = root.findViewById(R.id.txt_CorreoContacto_Psicologo);
        txt_telefono_psicologo_cuenta = root.findViewById(R.id.txt_Telefono_Psicologo);
        txt_contrasena = root.findViewById(R.id.txt_Contrasena_Psicologo);
        txt_confirma_contrasena = root.findViewById(R.id.txt_ContrasenaConfirmacion_Psicologo);

        txt_calle = root.findViewById(R.id.txt_calle_cuenta);
        txt_num_exterior = root.findViewById(R.id.txt_num_exterior_cuenta);
        txt_cp = root.findViewById(R.id.txt_cp_cuenta);

        pdf_view_cuenta_psicologo = root.findViewById(R.id.pdf_view_cuenta_psicologo);
        pb_progreso_carga = root.findViewById(R.id.pb_progreso_pdf);

        btn_editar_psicologo = root.findViewById(R.id.btn_Editar_Psicologo);
        btn_guardar_psicologo = root.findViewById(R.id.btn_Guardar_Psicologo);

        ArrayAdapter<CharSequence> estadoAdapter, municipioAdapter, localidadAdapter;

        estadoAdapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(), R.array.estado, android.R.layout.simple_spinner_item);
        municipioAdapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(), R.array.municipio, android.R.layout.simple_spinner_item);
        localidadAdapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(), R.array.localidad, android.R.layout.simple_spinner_item);

        sp_estado = root.findViewById(R.id.sp_estado_cuenta);
        sp_estado.setAdapter(estadoAdapter);

        sp_municipio = root.findViewById(R.id.sp_municipio_cuenta);
        sp_municipio.setAdapter(municipioAdapter);

        sp_localidad = root.findViewById(R.id.sp_localidad_cuenta);
        sp_localidad.setAdapter(localidadAdapter);

        sp_estado.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        sp_municipio.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        sp_localidad.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
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
                    p.setLong_telefono(Integer.parseInt(snapshot.child("int_telefono").getValue().toString()));
                    p.setString_direccion(snapshot.child("string_direccion").getValue().toString());
                    p.setString_perfilProfesional(snapshot.child("string_perfilProfesional").getValue().toString());
                    p.setString_fecha_fin_suscripcion(snapshot.child("string_fecha_fin_suscripcion").getValue().toString());
                    if(!p.getString_fecha_fin_suscripcion().equals("-1"))boolean_verificado=true;

                    new Pdf(pdf_view_cuenta_psicologo,pb_progreso_carga).execute(p.getString_perfilProfesional());
                    txt_nombre_psicologo_cuenta.setText(p.getString_nombre());
                    txt_apellido_paterno_psicologo_cuenta.setText(p.getString_apellido_paterno());
                    txt_apellido_materno_psicologo_cuenta.setText(p.getString_apellido_materno());
                    txt_cedula_psicologo_cuenta.setText(String.valueOf(p.getInt_cedula()));
                    txt_telefono_psicologo_cuenta.setText(String.valueOf(p.getLong_telefono()));
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
    /**
     * Esta funcion retorna verdadero si el correo tiene errores y falso si el correo no tiene errores
     *
     * @param editText_telefono EditText correo
     * @return boolean_error
     */
    private boolean valida_telefono(EditText editText_telefono) {

        editText_telefono.setError(null);

        boolean boolean_error = true;

        View focusView = null;

        Pattern pattern = Patterns.PHONE;

        String telefono = editText_telefono.getText().toString().trim();

        if (TextUtils.isEmpty(telefono)) {
            editText_telefono.setError(getString(R.string.error_campo_requerido));
            focusView = editText_telefono;
            boolean_error = false;
        } else if (!telefono.matches(".{10}")) {
            editText_telefono.setError(getString(R.string.error_telefono_invalido));
            focusView = editText_telefono;
            boolean_error = false;
        }
        if (!boolean_error) {

            focusView.requestFocus();

        }
        return boolean_error;

    }


    private boolean valida_cedula(EditText editText_cedula) {

        editText_cedula.setError(null);

        String string_cedula = editText_cedula.getText().toString().trim();

        boolean boolean_error = true;

        View focusView = null;

        if (string_cedula.isEmpty()) {
            editText_cedula.setError(getString(R.string.error_campo_requerido));
            focusView = editText_cedula;
            boolean_error = false;
        }

        if (!string_cedula.matches(".{7,8}")) {
            editText_cedula.setError(getString(R.string.error_formato_no_valido));
            focusView = editText_cedula;
            boolean_error = false;
        }

        if (!boolean_error) {

            focusView.requestFocus();

        }

        return boolean_error;

    }

    private boolean valida_numero_exterior(EditText editText_num_exterior) {

        editText_num_exterior.setError(null);

        String string_num_ext = editText_num_exterior.getText().toString().trim();

        boolean boolean_error = true;

        View focusView = null;

        if (string_num_ext.isEmpty()) {
            editText_num_exterior.setError(getString(R.string.error_campo_requerido));
            focusView = editText_num_exterior;
            boolean_error = false;
        }

        if (!string_num_ext.matches(".{2,10}")) {
            editText_num_exterior.setError(getString(R.string.error_numero_exterior));
            focusView = editText_num_exterior;
            boolean_error = false;
        }

        if (!boolean_error) {

            focusView.requestFocus();

        }

        return boolean_error;
    }

    private boolean valida_cp(EditText editText_cp) {

        editText_cp.setError(null);

        String string_cp = editText_cp.getText().toString().trim();

        boolean boolean_error = true;

        View focusView = null;

        if (string_cp.isEmpty()) {
            editText_cp.setError(getString(R.string.error_campo_requerido));
            focusView = editText_cp;
            boolean_error = false;
        }

        if (!string_cp.matches(".{5}")) {
            editText_cp.setError(getString(R.string.error_formato_no_valido));
            focusView = editText_cp;
            boolean_error = false;
        }

        if (!boolean_error) {

            focusView.requestFocus();

        }

        return boolean_error;
    }

    /**
     * Valida el formato de editText_texto
     *
     * @param editText_texto EditText que contiente el paciente
     * @return boolean_nombre_paciente_v Regresa el booleano false si no es correcto el formato y true es formato
     */
    private boolean valida_texto(EditText editText_texto) {

        editText_texto.setError(null);

        String sting_texto = editText_texto.getText().toString().trim();

        boolean boolean_error = true;

        View focusView = null;

        if (TextUtils.isEmpty(sting_texto)) {
            editText_texto.setError(getString((R.string.error_campo_requerido)));
            focusView = editText_texto;
            boolean_error = false;
        }

        if (!sting_texto.matches(".{2,20}")) {
            editText_texto.setError(getString(R.string.error_formato_no_valido));
            focusView = editText_texto;
            boolean_error = false;
        }

        if (sting_texto.matches(".*\\s.*")) {
            editText_texto.setError(getString(R.string.error_sin_espacios));
            focusView = editText_texto;
            boolean_error = true;
        }

        if (!boolean_error) {

            focusView.requestFocus();

        }

        return boolean_error;
    }
    @Override
    public void onClick(View view) {

    }
}