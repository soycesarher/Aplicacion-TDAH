package com.example.tdah;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tdah.modelos.UsuarioPaciente;
import com.example.tdah.modelos.UsuarioPadreTutor;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseUser;
import com.example.tdah.validaciones.DatosDeCurp;

import java.util.regex.Pattern;


public class RegistroUsuario extends AppCompatActivity {

    private DatabaseReference usuario;

    private EditText txt_nombre_padre_tutor;
    private EditText txt_nombre_paciente;
    private EditText txt_apellido_materno;
    private EditText txt_apellido_paterno;
    private EditText txt_correo;
    private EditText txt_curp;
    private EditText txt_nip;
    private EditText txt_contrasena;

    private Button btn_registrarse;
    private Button btn_verifica_curp;

    private String fecha_nacimiento;
    private String direccion;

    private boolean boolean_contrasena;
    private boolean boolean_nombre_paciente;
    private boolean boolean_correo;
    private boolean boolean_curp;
    private boolean boolean_nip;

    private FirebaseDatabase firebase_database;

    private FirebaseAuth mAuth;

    RequestQueue rq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_registro_usuario);

        inicializa_firebase();

        txt_curp = findViewById(R.id.txt_curp);

        rq = Volley.newRequestQueue(this);

        txt_nombre_paciente = findViewById(R.id.txt_nombre_paciente);
        txt_nip = findViewById(R.id.txt_nip);
        txt_contrasena = findViewById(R.id.txt_contrasena);
        txt_apellido_paterno = findViewById(R.id.txt_apellido_paterno);
        txt_correo = findViewById(R.id.txt_correo);

        btn_registrarse = findViewById(R.id.btn_registrarse);
        btn_verifica_curp = findViewById(R.id.btn_verifica_curp);
        btn_registrarse.setEnabled(false);

        txt_nombre_paciente.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean_nombre_paciente = valida_nombre_paciente(txt_nombre_paciente);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        txt_curp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean_curp = valida_curp(txt_curp);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

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

        txt_contrasena.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean_contrasena = valida_contrasena(txt_contrasena);
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
                boolean_correo = valida_correo(txt_correo);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        btn_verifica_curp.setOnClickListener(v -> {
            try {
                recuperar(txt_curp);
                btn_registrarse.setEnabled(true);
            } catch (NullPointerException e) {
                Toast.makeText(RegistroUsuario.this, "La CURP no fue encontrada", Toast.LENGTH_LONG).show();
            }
        });
        btn_registrarse.setOnClickListener(v -> {
            if (boolean_contrasena || !boolean_correo || !boolean_curp || !boolean_nip || !boolean_nombre_paciente) {
                Toast.makeText(RegistroUsuario.this, "Faltan datos", Toast.LENGTH_SHORT).show();
            } else {
                ingresa_base_datos();
            }
        });
    }

    private boolean valida_nombre_paciente(EditText editText_nombre_paciente) {

        editText_nombre_paciente.setError(null);

        String nombre_paciente = editText_nombre_paciente.getText().toString().trim();

        boolean boolean_nombre_paciente_v = true;

        View focusView = null;

        if (TextUtils.isEmpty(nombre_paciente)) {
            editText_nombre_paciente.setError(getString((R.string.error_campo_requerido)));
            focusView = editText_nombre_paciente;
            boolean_nombre_paciente_v = false;
        }

        if (!nombre_paciente.matches(".{2,20}")) {
            editText_nombre_paciente.setError(getString(R.string.error_formato_no_valido));
            focusView = editText_nombre_paciente;
            boolean_nombre_paciente_v = false;
        }

        if (!boolean_nombre_paciente_v) {

            focusView.requestFocus();

        }

        return boolean_nombre_paciente_v;
    }

    private boolean valida_curp(EditText editText_curp) {

        editText_curp.setError(null);

        String curp = editText_curp.getText().toString().trim();

        boolean boolean_curp_v = true;

        View focusView = null;

        if (TextUtils.isEmpty(curp)) {
            editText_curp.setError(getString(R.string.error_campo_requerido));
            focusView = editText_curp;
            boolean_curp_v = false;
        }

        if (!curp.matches("[A-Z]{1}[AEIOU]{1}[A-Z]{2}[0-9]{2}" +
                "(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1])" +
                "[HM]{1}" +
                "(AS|BC|BS|CC|CS|CH|CL|CM|DF|DG|GT|GR|HG|JC|MC|MN|MS|NT|NL|OC|PL|QT|QR|SP|SL|SR|TC|TS|TL|VZ|YN|ZS|NE)" +
                "[B-DF-HJ-NP-TV-Z]{3}" +
                "[0-9A-Z]{1}[0-9]{1}$")) {
            editText_curp.setError(getString(R.string.error_formato_no_valido));
            focusView = editText_curp;
            boolean_curp_v = false;
        }

        if (!boolean_curp_v) {

            focusView.requestFocus();

        }

        return boolean_curp_v;
    }

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
     * Crea he inicializa las instancias de Firebase Authentication y obtiene la referencia de
     * Firebase Database
     */
    private void inicializa_firebase() {
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        firebase_database = FirebaseDatabase.getInstance();
        usuario = firebase_database.getReference();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(RegistroUsuario.this, UsuarioPrincipal.class));
            finish();
        }
    }

    /**
     * Recupera los valores obtenidos de los botones, autentica el correo y contrasenia he ingresa
     * la información a la base de datos.
     */
    public void ingresa_base_datos() {

        txt_curp = findViewById(R.id.txt_curp);
        txt_apellido_paterno = findViewById(R.id.txt_apellido_paterno);
        txt_nip = findViewById(R.id.txt_nip);
        txt_contrasena = findViewById(R.id.txt_contrasena);
        txt_correo = findViewById(R.id.txt_correo);
        txt_apellido_materno = findViewById(R.id.txt_apellido_materno);
        txt_nombre_paciente = findViewById(R.id.txt_nombre_paciente);
        txt_nombre_padre_tutor = findViewById(R.id.txt_nombre_padre_tutor);

        String curp = txt_curp.getText().toString();
        String nombre = txt_nombre_padre_tutor.getText().toString();
        String apellido_paterno = txt_apellido_paterno.getText().toString();
        String apellido_materno = txt_apellido_materno.getText().toString();
        String correo = txt_correo.getText().toString();
        String nombre_paciente = txt_nombre_paciente.getText().toString();
        String contrasena = txt_contrasena.getText().toString();
        String nip = txt_nip.getText().toString();

        mAuth.createUserWithEmailAndPassword(correo, contrasena).addOnCompleteListener(task -> {
            UsuarioPadreTutor usuarioPadreTutor = new UsuarioPadreTutor();
            UsuarioPaciente usuarioPaciente = new UsuarioPaciente();
            if (task.isSuccessful()) {
                FirebaseUser usuario_actual = mAuth.getCurrentUser();
                usuarioPadreTutor.setString_id(usuario_actual.getUid());
                usuarioPadreTutor.setInt_nip(Integer.parseInt(nip));
                usuarioPadreTutor.setString_curp(curp);
                usuarioPadreTutor.setString_tipo_cuenta(cuenta());
                usuarioPadreTutor.setString_nombre(nombre);
                usuarioPadreTutor.setString_apellido_materno(apellido_materno);
                usuarioPadreTutor.setString_apellido_paterno(apellido_paterno);

                usuarioPadreTutor.setString_correo(correo);
                usuarioPadreTutor.setString_direccion(direccion);
                usuarioPadreTutor.setString_fecha_nacimiento(fecha_nacimiento);
                usuarioPadreTutor.setBoolean_pago(pago());

                usuarioPaciente.setString_nombre_paciente(nombre_paciente);


                usuario_actual.sendEmailVerification().addOnCompleteListener(task1 -> {
                    if (task1.isSuccessful()) {
                        Toast.makeText(RegistroUsuario.this, "Mensaje enviado", Toast.LENGTH_SHORT).show();
                        usuario.child("Usuario").child(usuarioPadreTutor.getString_id()).setValue(usuarioPadreTutor).addOnCompleteListener(task2 -> {
                            if (task2.isSuccessful()) {

                                usuario.child("Usuario").child(usuarioPadreTutor.getString_id()).child("Paciente").setValue(usuarioPaciente).addOnCompleteListener(task3 -> {
                                    if (task3.isSuccessful()) {
                                        Toast.makeText(RegistroUsuario.this, "Usuario registrado", Toast.LENGTH_LONG).show();

                                        startActivity(new Intent(RegistroUsuario.this, UsuarioPrincipal.class));
                                        finish();

                                    }
                                });

                            } else {
                                Toast.makeText(RegistroUsuario.this, "No se pudo realizar el registro", Toast.LENGTH_LONG).show();
                            }

                        });
                    } else {
                        Toast.makeText(RegistroUsuario.this, "Mensaje no recibido", Toast.LENGTH_SHORT).show();
                    }


                });

            } else {

                Toast.makeText(RegistroUsuario.this, "Fallo de autenticación", Toast.LENGTH_LONG).show();
            }
        });

    }

    /**
     * Regresa el tipo de cuenta
     *
     * @return string_cuenta
     */
    private String cuenta() {
        String string_cuenta = "Gratuita";
        if (pago()) {
            string_cuenta = "Pago procesado";
        } else {

            Toast.makeText(RegistroUsuario.this, "Cuenta gratuita", Toast.LENGTH_SHORT).show();
        }
        return string_cuenta;
    }

    /**
     * Regresa si el pago se aplico correctamente
     *
     * @return boolean_pago
     */
    private Boolean pago() {
        Boolean boolean_pago = false;

        return boolean_pago;
    }


    /**
     * Abre main_activity
     *
     * @param view
     */
    public void ir_main(View view) {
        Intent ir = new Intent(this, MainActivity.class);
        startActivity(ir);
    }

    /**
     * Abre activity_inicio_de_sesion
     *
     * @param view
     */
    public void ir_inicio_de_sesion(View view) {
        Intent ir = new Intent(this, InicioDeSesion.class);
        startActivity(ir);
    }

    /**
     * @param renapo
     */
    public void valida_datos_curp(String renapo) {

        DatosDeCurp validar = new DatosDeCurp(renapo);
        String nombre = validar.getString_nombre();
        String apellido_paterno = validar.getString_apellido_paterno();
        String apellido_materno = validar.getString_apellido_materno();
        fecha_nacimiento = validar.getString_fecha_nacimiento();
        direccion = validar.getString_estado_nacimiento();

        txt_nombre_padre_tutor = findViewById(R.id.txt_nombre_padre_tutor);
        txt_nombre_padre_tutor.setText(nombre);
        txt_apellido_paterno = findViewById(R.id.txt_apellido_paterno);
        txt_apellido_paterno.setText(apellido_paterno);
        txt_apellido_materno = findViewById(R.id.txt_apellido_materno);
        txt_apellido_materno.setText(apellido_materno);
        fecha_nacimiento = validar.getString_fecha_nacimiento();

    }

    /**
     * @param
     */
    public void recuperar(EditText txt_curp) {

        StringRequest requerimiento = new StringRequest(Request.Method.GET,
                "https://us-west4-arsus-production.cloudfunctions.net/curp?curp=" + txt_curp.getText().toString() + "&apiKey=WgrtpPpMT6gCrKmawXDipiEzQQv2",
                response -> valida_datos_curp(response),
                error -> Toast.makeText(RegistroUsuario.this, "ERROR: " + error.getMessage() + " INTENTE DE NUEVO.", Toast.LENGTH_SHORT).show());
        rq.add(requerimiento);
    }

}