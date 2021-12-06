package com.example.tdah;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tdah.modelos.UsuarioPsicologo;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.paypal.checkout.PayPalCheckout;
import com.paypal.checkout.config.CheckoutConfig;
import com.paypal.checkout.config.Environment;
import com.paypal.checkout.config.SettingsConfig;
import com.paypal.checkout.createorder.CreateOrderActions;
import com.paypal.checkout.createorder.CurrencyCode;
import com.paypal.checkout.createorder.OrderIntent;
import com.paypal.checkout.createorder.UserAction;
import com.paypal.checkout.order.Amount;
import com.paypal.checkout.order.AppContext;
import com.paypal.checkout.order.Order;
import com.paypal.checkout.order.PurchaseUnit;
import com.paypal.checkout.paymentbutton.PaymentButton;

import java.util.ArrayList;
import java.util.regex.Pattern;


public class RegistroPsicologo extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    String estado, municipio, localidad;

    private Spinner sp_estado;
    private Spinner sp_municipio;
    private Spinner sp_localidad;

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
    FirebaseAuth mAuth;
    FirebaseUser usuario_actual;

    private boolean boolean_error_texto;
    private boolean boolean_error_contrasena;
    private boolean boolean_error_numero_exterior;
    private boolean boolean_error_correo;
    private boolean boolean_error_telefono;
    private boolean boolean_error_cedula;
    private boolean boolean_error_cp;
    private boolean boolean_pago;

    private static final String ID_CLIENT_PAYPAL = "ATWfD62z3TUeMswLbKbXRRwC0tzFiIak2A0ptBlaSjL7LOcQuunPoibBONshrWXck4KcqIgPiXHHiQRr";

    private PaymentButton payPalButton;

    private Button btn_registrarse;

    private final String string_url_curriculum = "-1";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_registro_psicologo);

        btn_registrarse = findViewById(R.id.btn_registrarse);


        //Paypal
        payPalButton = findViewById(R.id.payPalButton_psicologo);
        configuraPaypal();

        payPalButton.setup(
                createOrderActions -> {
                    ArrayList purchaseUnits = new ArrayList<>();
                    purchaseUnits.add(
                            new PurchaseUnit.Builder()
                                    .amount(
                                            new Amount.Builder()
                                                    .currencyCode(CurrencyCode.USD)
                                                    .value("5.00")
                                                    .build()
                                    )
                                    .build()
                    );
                    Order order = new Order(OrderIntent.CAPTURE,
                            new AppContext.Builder()
                                    .userAction(UserAction.PAY_NOW)
                                    .build(),
                            purchaseUnits);

                    createOrderActions.create(order, (CreateOrderActions.OnOrderCreated) null);
                },
                approval -> approval.getOrderActions().capture(result -> {
                    Toast.makeText(RegistroPsicologo.this, "Compra exitosa", Toast.LENGTH_LONG).show();
                    boolean_pago = false;
                }),
                () -> Toast.makeText(RegistroPsicologo.this, "Compra cancelada", Toast.LENGTH_LONG).show()
        );


        // Fin PayPal

        inicializa_firebase();
        mAuth = FirebaseAuth.getInstance();
        usuario_actual = mAuth.getCurrentUser();

        txt_nombre_psicologo = findViewById(R.id.txt_nombre_psicologo);
        txt_apellido_paterno = findViewById(R.id.txt_apellido_paterno);
        txt_apellido_materno = findViewById(R.id.txt_apellido_materno);
        txt_calle = findViewById(R.id.txt_calle);
        txt_num_exterior = findViewById(R.id.txt_num_exterior);
        txt_cp = findViewById(R.id.txt_cp);

//        txt_municipio = findViewById(R.id.txt_municipio);
        txt_telefono = findViewById(R.id.txt_telefono);
        txt_correo = findViewById(R.id.txt_correo_psicologo);
        txt_contrasena = findViewById(R.id.txt_contrasena_psicologo);
        txt_celdula = findViewById(R.id.txt_cedula_psicologo);

        // After Clicking on this we will be
        // redirected to choose pdf


        ArrayAdapter<CharSequence> estadoAdapter, municipioAdapter, localidadAdapter;

        estadoAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.estado, android.R.layout.simple_spinner_item);
        municipioAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.municipio, android.R.layout.simple_spinner_item);
        localidadAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.localidad, android.R.layout.simple_spinner_item);

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


        if (boolean_error_cedula || boolean_error_correo || !boolean_error_contrasena
                || boolean_error_texto || boolean_error_numero_exterior || boolean_error_cp || boolean_error_telefono || boolean_pago) {

          btn_registrarse.setEnabled(false);

        } else {
            btn_registrarse.setEnabled(true);

        }
        btn_registrarse.setOnClickListener(v->ingresa_base_datos());

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

        String string_telefono = txt_telefono.getText().toString();
        String string_correo = txt_correo.getText().toString();
        String string_contrasena = txt_contrasena.getText().toString();
        String string_cedula = txt_celdula.getText().toString();

        mAuth.createUserWithEmailAndPassword(string_correo, string_contrasena).addOnCompleteListener(task -> {

            UsuarioPsicologo usuarioPsicologo = new UsuarioPsicologo();

            if (task.isSuccessful()) {


                assert usuario_actual != null;

                usuarioPsicologo.setString_id(usuario_actual.getUid());
                usuarioPsicologo.setString_nombre(string_nombre);
                usuarioPsicologo.setString_apellido_paterno(string_apellido_paterno);
                usuarioPsicologo.setString_apellido_materno(string_apellido_materno);
                usuarioPsicologo.setString_direccion(string_calle + "," + string_num_ext + "," + string_cp +
                        "," + localidad + "," + municipio + "," + estado);
                usuarioPsicologo.setInt_telefono(Integer.parseInt(string_telefono));
                usuarioPsicologo.setInt_cedula(Integer.parseInt(string_cedula));
                usuarioPsicologo.setString_especialidad("Por verificar");
                usuarioPsicologo.setString_perfilProfesional(string_url_curriculum);

                usuario_actual.sendEmailVerification().addOnCompleteListener(task1 -> {

                    if (task1.isSuccessful()) {

                        Toast.makeText(RegistroPsicologo.this, "Mensaje enviado", Toast.LENGTH_LONG).show();

                        databaseReference.child("Psicologo").child(usuarioPsicologo.getString_id()).setValue(usuarioPsicologo).addOnCompleteListener(task2 -> {

                            if (task2.isSuccessful()) {
                                startActivity(new Intent(this, CargaPdf.class));

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

    public void Terminos(View view) {
        Intent termino = new Intent(this, TerminosyCondiciones.class);
        startActivity(termino);
    }

    /**
     * Este método asigna el monto a pagar por la suscripción
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void configuraPaypal() {
        CheckoutConfig config;
        config = new CheckoutConfig(
                getApplication(),
                ID_CLIENT_PAYPAL,
                Environment.SANDBOX,
                String.format("%s://paypalpay", BuildConfig.APPLICATION_ID),
                CurrencyCode.MXN,
                UserAction.PAY_NOW,
                new SettingsConfig(
                        true,
                        false
                )
        );
        PayPalCheckout.setConfig(config);


    }

    /**
     *
     */
//    @Override
//    protected void onDestroy() {
//        stopService(new Intent(this, PayPalService.class));
//        super.onDestroy();
//    }
    @Override
    public void onClick(View view) {

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

    /**
     * Crea he inicializa las instancias de Firebase Authentication y obtiene la referencia de
     * Firebase Database
     */
    private void inicializa_firebase() {
        FirebaseApp.initializeApp(this);

        FirebaseDatabase firebase_database = FirebaseDatabase.getInstance();
        databaseReference = firebase_database.getReference();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}
