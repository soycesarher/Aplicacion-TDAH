package com.example.tdah;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.tdah.modelos.UsuarioPsicologo;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;
import io.card.payment.i18n.locales.LocalizedStringsEN_GB;

public class RegistroPsicologo extends Activity {

public class RegistroPsicologo extends Activity implements View.OnClickListener, AdapterView.OnItemSelectedListener{

    String estado, municipio, localidad;

    private EditText txt_nombre_psicologo;
    private EditText txt_apellido_paterno;
    private EditText txt_apellido_materno;
    private EditText txt_calle;
    private EditText txt_num_exterior;
    private EditText txt_cp;
    private EditText txt_localidad;
    private EditText txt_municipio;
    private EditText txt_telefono;
    private EditText txt_correo;
    private EditText txt_contrasena;
    private EditText txt_celdula;

    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;

    private boolean boolean_error_texto;
    private boolean boolean_error_contrasena;
    private boolean boolean_error_numero_exterior;
    private boolean boolean_error_correo;
    private boolean boolean_error_telefono;
    private boolean boolean_error_cedula;
    private boolean boolean_error_cp;
        private boolean boolean_pago;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_registro_psicologo);

        inicializa_firebase();

        txt_nombre_psicologo = findViewById(R.id.txt_nombre_psicologo);
        txt_apellido_paterno = findViewById(R.id.txt_apellido_paterno);
        txt_apellido_materno = findViewById(R.id.txt_apellido_materno);
        txt_calle = findViewById(R.id.txt_calle);
        txt_num_exterior = findViewById(R.id.txt_num_exterior);
        txt_cp = findViewById(R.id.txt_cp);
        txt_localidad = findViewById(R.id.txt_localidad);
        txt_municipio = findViewById(R.id.txt_municipio);
        txt_telefono = findViewById(R.id.txt_telefono);
        txt_correo = findViewById(R.id.txt_correo_psicologo);
        txt_contrasena = findViewById(R.id.txt_contrasena_psicologo);
        txt_celdula = findViewById(R.id.txt_cedula_psicologo);


        btn_curriculum = findViewById(R.id.btn_curriculum);

        // After Clicking on this we will be
        // redirected to choose pdf
        btn_curriculum.setOnClickListener(v -> {
            Intent galleryIntent = new Intent();
            galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

            // We will be redirected to choose pdf
            galleryIntent.setType("application/pdf");
            startActivityForResult(galleryIntent, 1);
        });



        ArrayAdapter<CharSequence> estadoAdapter, municipioAdapter, localidadAdapter;

        estadoAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.estado, android.R.layout.simple_spinner_item);
        municipioAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.municipio, android.R.layout.simple_spinner_item);
        localidadAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.localidad, android. R.layout.simple_spinner_item);

        sp_estado = findViewById(R.id.sp_estado);
        sp_estado.setAdapter(estadoAdapter);

        sp_municipio = findViewById(R.id.sp_municipio);
        sp_municipio.setAdapter(municipioAdapter);

        sp_localidad = findViewById(R.id.sp_localidad);
        sp_localidad.setAdapter(localidadAdapter);

        sp_estado.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        sp_municipio.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        sp_localidad.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);


        txt_nombre_psicologo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean_error_texto = valida_texto(txt_nombre_psicologo);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        txt_apellido_paterno.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean_error_texto = valida_texto(txt_apellido_paterno);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        txt_apellido_materno.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean_error_texto = valida_texto(txt_apellido_materno);
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

        txt_localidad.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean_error_texto = valida_texto(txt_localidad);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        txt_correo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                boolean_error_correo = valida_correo(txt_correo);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        txt_contrasena.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean_error_contrasena = valida_contrasena(txt_contrasena);
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

        txt_municipio.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                boolean_error_texto = valida_texto(txt_municipio);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        txt_telefono.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean_error_telefono = valida_telefono(txt_telefono);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        txt_celdula.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean_error_cedula = valida_cedula(txt_celdula);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        if(boolean_error_cedula||boolean_error_correo||!boolean_error_contrasena
                ||boolean_error_texto||boolean_error_numero_exterior||boolean_error_cp||boolean_error_telefono||boolean_pago){

            Toast.makeText(RegistroPsicologo.this, "Llene correctamente los campos", Toast.LENGTH_LONG).show();

        }else{
            ingresa_base_datos();
        }


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

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(RegistroPsicologo.this, PsicologoPrincipal.class));
            finish();
        }
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

    /**
     * Esta funcion retorna verdadero si la contrasena tiene errores y  si es falso no tiene errores
     *
     * @param editText_contrasena EditText contrasena
     * @return boolean_contrasena_v
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
        } else if (!pattern.matcher(telefono).matches()) {
            editText_telefono.setError(getString(R.string.error_telefono_invalido));
            focusView = editText_telefono;
            boolean_error = false;
        }
        if (!boolean_error) {

            focusView.requestFocus();

        }
        return boolean_error;

    }

    /**
     * Crea he inicializa las instancias de Firebase Authentication y obtiene la referencia de
     * Firebase Database
     */
    private void inicializa_firebase() {
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase firebase_database = FirebaseDatabase.getInstance();
        databaseReference = firebase_database.getReference();
    }

    /**
     * Recupera los valores obtenidos de los botones, autentica el correo y contrasenia he ingresa
     * la información a la base de datos.
     */
    @RequiresApi(api = Build.VERSION_CODES.O)

    public void ingresa_base_datos() {

        String string_nombre = txt_nombre_psicologo.getText().toString();
        String string_apellido_paterno = txt_apellido_paterno.getText().toString();
        String string_apellido_materno = txt_apellido_materno.getText().toString();
        String string_calle = txt_calle.getText().toString();
        String string_num_ext = txt_num_exterior.getText().toString();
        String string_cp = txt_cp.getText().toString();
        String string_localidad = txt_localidad.getText().toString();
        String string_municipio = txt_municipio.getText().toString();
        String string_telefono = txt_telefono.getText().toString();
        String string_correo = txt_correo.getText().toString();
        String string_contrasena = txt_contrasena.getText().toString();
        String string_cedula = txt_celdula.getText().toString();

        mAuth.createUserWithEmailAndPassword(string_correo, string_contrasena).addOnCompleteListener(task -> {

            UsuarioPsicologo usuarioPsicologo = new UsuarioPsicologo();

            if (task.isSuccessful()) {

                FirebaseUser usuario_actual = mAuth.getCurrentUser();

                assert usuario_actual != null;

                usuarioPsicologo.setString_id(usuario_actual.getUid());
                usuarioPsicologo.setString_nombre(string_nombre);
                usuarioPsicologo.setString_apellido_paterno(string_apellido_paterno);
                usuarioPsicologo.setString_apellido_materno(string_apellido_materno);
                usuarioPsicologo.setString_direccion(string_calle + "," + string_num_ext + "," + string_cp +
                        "," + string_localidad + "," + string_municipio);
                usuarioPsicologo.setInt_telefono(Integer.parseInt(string_telefono));
                usuarioPsicologo.setInt_cedula(Integer.parseInt(string_cedula));
                usuarioPsicologo.setString_especialidad("Por verificar");
                usuarioPsicologo.setString_perfilProfesional("Por verificar");

                usuario_actual.sendEmailVerification().addOnCompleteListener(task1 -> {

                    if (task1.isSuccessful()) {

                        Toast.makeText(RegistroPsicologo.this, "Mensaje enviado", Toast.LENGTH_LONG).show();

                        databaseReference.child("Psicologo").child("NoValidado").child(usuarioPsicologo.getString_id()).setValue(usuarioPsicologo).addOnCompleteListener(task2 -> {

                            if (task2.isSuccessful()) {


                            } else {

                                Toast.makeText(RegistroPsicologo.this, "No se pudo realizar el registro", Toast.LENGTH_LONG).show();

                            }

                        });

                    } else {

                        Toast.makeText(RegistroPsicologo.this, "Mensaje no recibido", Toast.LENGTH_LONG).show();

                    }

                });

            } else {

                Toast.makeText(RegistroPsicologo.this, "Fallo de autenticación", Toast.LENGTH_LONG).show();

            }

        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {


            pd_dialogo = new ProgressDialog(this);

            pd_dialogo.setMessage("Cargando archivo");

            pd_dialogo.show();

            uri_pdf = data.getData();

            StorageReference storageReference = FirebaseStorage.getInstance().getReference();

            usuario_actual = mAuth.getCurrentUser();

            Toast.makeText(RegistroPsicologo.this, uri_pdf.toString(), Toast.LENGTH_SHORT).show();

            // Here we are uploading the pdf in firebase storage with the name of current time
            final StorageReference filepath = storageReference.child(usuario_actual.getUid() + "." + "pdf");

            Toast.makeText(RegistroPsicologo.this, filepath.getName(), Toast.LENGTH_SHORT).show();

            filepath.putFile(uri_pdf).continueWithTask((Continuation) task -> {

                if (!task.isSuccessful()) {

                    throw task.getException();

                }

                return filepath.getDownloadUrl();

            }).addOnCompleteListener((OnCompleteListener<Uri>) task -> {

                if (task.isSuccessful()) {

                    pd_dialogo.dismiss();

                    Uri uri = task.getResult();

                    string_url_curriculum = uri.toString();

                    Toast.makeText(RegistroPsicologo.this, "Cargado exitosamente", Toast.LENGTH_SHORT).show();

                    boolean_pdf = true;

                } else {

                    pd_dialogo.dismiss();

                    Toast.makeText(RegistroPsicologo.this, "Error al cargar archivo", Toast.LENGTH_SHORT).show();

                }

            });
        }

    }


    @Override
    public void onClick(View view) {

    }
}
