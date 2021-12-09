/**
 *Clase: Se encarga del registro de los usuarios.
 *
 * @author: TDAH Móvil
 */
package com.example.tdah;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tdah.modelos.UsuarioPaciente;
import com.example.tdah.modelos.UsuarioPadreTutor;
import com.example.tdah.validaciones.DatosDeCurp;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.paypal.checkout.paymentbutton.PaymentButton;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Pattern;


public class RegistroUsuario extends AppCompatActivity {

    private DatabaseReference databasereference_databasereference;

    private EditText edittext_nombre_padre_tutor;
    private EditText edittext_nombre_paciente;
    private EditText edittext_apellido_materno;
    private EditText edittext_apellido_paterno;
    private EditText edittext_correo;
    private EditText edittext_curp;
    private EditText edittext_nip;
    private EditText edittext_contrasena;

    private Button button_registrarse;

    private String string_fecha_nacimiento;
    private String string_direccion;

    private static final String STRING_ID_CLIENT_PAYPAL = "ATWfD62z3TUeMswLbKbXRRwC0tzFiIak2A0ptBlaSjL7LOcQuunPoibBONshrWXck4KcqIgPiXHHiQRr";

    private boolean boolean_contrasena=true;
    private boolean boolean_nombre_paciente=false;
    private boolean boolean_correo=false;
    private boolean boolean_curp=false;
    private boolean boolean_nip=false;
    private boolean boolean_edad = false;
    private boolean boolean_pago=true;


    private FirebaseAuth firebaseauth_mauth;
    private FirebaseUser firebaseuser_fuser;

    private PaymentButton paymentbutton_paypalbutton;

    RequestQueue requestqueue_rq;

    /**
     * Inicializa la actividad (pantalla).
     *
     * @param bundle_savedInstanceState Estado de instancia asociado.
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle bundle_savedInstanceState)
    {
        super.onCreate(bundle_savedInstanceState);

        setContentView(R.layout.activity_registro_usuario);

        //Paypal


        // Fin PayPal

        inicializa_firebase();

        edittext_curp = findViewById(R.id.txt_curp);

        requestqueue_rq = Volley.newRequestQueue(this);

        edittext_nombre_paciente = findViewById(R.id.txt_nombre_paciente);
        edittext_nip = findViewById(R.id.txt_nip);
        edittext_contrasena = findViewById(R.id.txt_contrasena);
        edittext_apellido_paterno = findViewById(R.id.txt_apellido_paterno);
        edittext_correo = findViewById(R.id.txt_correo);

        button_registrarse = findViewById(R.id.btn_registrarse);
        Button btn_verifica_curp = findViewById(R.id.btn_verifica_curp);
        button_registrarse.setEnabled(false);

        edittext_nombre_paciente.addTextChangedListener(new TextWatcher()
        {

            /**
             * Se mantiene pendiente antes de que ocurra cambio en EditText.
             *
             * @param s Secuencia de caracteres.
             * @param start Número de inicio.
             * @param count Número contador.
             * @param after Número sucesor.
             */
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
            }

            /**
             * Se mantiene pendiente a que ocurra cambio en EditText.
             *
             * @param s Secuencia de caracteres.
             * @param start Número de inicio.
             * @param count Número contador.
             * @param before Número antecesor..
             */
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                boolean_nombre_paciente = valida_nombre_paciente(edittext_nombre_paciente);
            }

            /**
             * Se mantiene pendiente después de que ocurra cambio en EditText.
             *
             * @param s Propiedad editable
             */
            @Override
            public void afterTextChanged(Editable s)
            {
            }
        });

        edittext_curp.addTextChangedListener(new TextWatcher()
        {

            /**
             * Se mantiene pendiente antes de que ocurra cambio en EditText.
             *
             * @param s Secuencia de caracteres.
             * @param start Número de inicio.
             * @param count Número contador.
             * @param after Número sucesor.
             */
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
            }

            /**
             * Se mantiene pendiente a que ocurra cambio en EditText.
             *
             * @param s Secuencia de caracteres.
             * @param start Número de inicio.
             * @param count Número contador.
             * @param before Número antecesor..
             */
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                boolean_curp = valida_curp(edittext_curp);
            }

            /**
             * Se mantiene pendiente después de que ocurra cambio en EditText.
             *
             * @param s Propiedad editable
             */
            @Override
            public void afterTextChanged(Editable s)
            {
            }
        });

        edittext_nip.addTextChangedListener(new TextWatcher()
        {
            /**
             * Se mantiene pendiente antes de que ocurra cambio en EditText.
             *
             * @param s Secuencia de caracteres.
             * @param start Número de inicio.
             * @param count Número contador.
             * @param after Número sucesor.
             */
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
            }

            /**
             * Se mantiene pendiente después de que ocurra cambio en EditText.
             *
             * @param s Propiedad editable
             */
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                boolean_nip = valida_nip(edittext_nip);
            }

            /**
             * Se mantiene pendiente después de que ocurra cambio en EditText.
             *
             * @param s Propiedad editable
             */
            @Override
            public void afterTextChanged(Editable s)
            {
            }
        });

        edittext_contrasena.addTextChangedListener(new TextWatcher()
        {
            /**
             * Se mantiene pendiente antes de que ocurra cambio en EditText.
             *
             * @param s Secuencia de caracteres.
             * @param start Número de inicio.
             * @param count Número contador.
             * @param after Número sucesor.
             */
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
            }

            /**
             * Se mantiene pendiente después de que ocurra cambio en EditText.
             *
             * @param s Propiedad editable
             */
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                boolean_contrasena = valida_contrasena(edittext_contrasena);
            }

            /**
             * Se mantiene pendiente después de que ocurra cambio en EditText.
             *
             * @param s Propiedad editable
             */
            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });

        edittext_correo.addTextChangedListener(new TextWatcher()
        {
            /**
             * Se mantiene pendiente antes de que ocurra cambio en EditText.
             *
             * @param s Secuencia de caracteres.
             * @param start Número de inicio.
             * @param count Número contador.
             * @param after Número sucesor.
             */
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
            }

            /**
             * Se mantiene pendiente después de que ocurra cambio en EditText.
             *
             * @param s Propiedad editable
             */
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                boolean_correo = valida_correo(edittext_correo);
            }

            /**
             * Se mantiene pendiente después de que ocurra cambio en EditText.
             *
             * @param s Propiedad editable
             */
            @Override
            public void afterTextChanged(Editable s)
            {
            }
        });

        btn_verifica_curp.setOnClickListener(v ->
        {
            try
            {
                recuperar(edittext_curp);

            } catch (NullPointerException e)
            {
                Toast.makeText(RegistroUsuario.this, "La CURP no fue encontrada", Toast.LENGTH_LONG).show();
            }
        });

        button_registrarse.setOnClickListener(v ->
        {
            if (boolean_contrasena || !boolean_correo || !boolean_curp || !boolean_nip || !boolean_nombre_paciente)
            {
                Toast.makeText(RegistroUsuario.this, "Faltan datos", Toast.LENGTH_SHORT).show();
            } else
            {
                ingresa_base_datos();
            }
        });

    }

    /**
     * Muestra términos y condiciones de la aplicación.
     *
     * @param view_view Vista asociada.
     */
    public void Terminos(View view_view)
    {
        Intent termino = new Intent(this, TerminosyCondiciones.class);
        startActivity(termino);
    }




    /**
     * Crea he inicializa las instancias de Firebase Authentication y obtiene la referencia de
     * Firebase Database
     */
    private void inicializa_firebase()
    {
        FirebaseApp.initializeApp(this);
        firebaseauth_mauth = FirebaseAuth.getInstance();
        FirebaseDatabase firebase_database = FirebaseDatabase.getInstance();
        databasereference_databasereference = firebase_database.getReference();
    }

    /**
     * Inicia la actividad (pantalla).
     */
    @Override
    protected void onStart()
    {
        super.onStart();
    }

    /**
     * Recupera los valores obtenidos de los botones, autentica el correo y contrasenia he ingresa
     * la información a la base de datos.
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void ingresa_base_datos()
    {
        edittext_curp = findViewById(R.id.txt_curp);
        edittext_apellido_paterno = findViewById(R.id.txt_apellido_paterno);
        edittext_nip = findViewById(R.id.txt_nip);
        edittext_contrasena = findViewById(R.id.txt_contrasena);
        edittext_correo = findViewById(R.id.txt_correo);
        edittext_apellido_materno = findViewById(R.id.txt_apellido_materno);
        edittext_nombre_paciente = findViewById(R.id.txt_nombre_paciente);
        edittext_nombre_padre_tutor = findViewById(R.id.txt_nombre_padre_tutor);

        String string_curp = edittext_curp.getText().toString();
        String string_nombre = edittext_nombre_padre_tutor.getText().toString();
        String string_apellido_paterno = edittext_apellido_paterno.getText().toString();
        String string_apellido_materno = edittext_apellido_materno.getText().toString();
        String string_correo = edittext_correo.getText().toString();
        String string_nombre_paciente = edittext_nombre_paciente.getText().toString();
        String string_contrasena = edittext_contrasena.getText().toString();
        String string_nip = edittext_nip.getText().toString();


        firebaseauth_mauth.createUserWithEmailAndPassword(string_correo, string_contrasena).addOnCompleteListener(task ->
        {
            UsuarioPadreTutor usuariopadretutor_usuariopadretutor = new UsuarioPadreTutor();
            UsuarioPaciente usuariopaciente_usuariopaciente = new UsuarioPaciente();
            if (task.isSuccessful())
            {

                FirebaseUser firebaseuser_usuario_actual = firebaseauth_mauth.getCurrentUser();

                assert firebaseuser_usuario_actual != null;
                usuariopadretutor_usuariopadretutor.setString_id(firebaseuser_usuario_actual.getUid());
                usuariopadretutor_usuariopadretutor.setInt_nip(Integer.parseInt(string_nip));
                usuariopadretutor_usuariopadretutor.setString_curp(string_curp);
                usuariopadretutor_usuariopadretutor.setString_nombre(string_nombre);
                usuariopadretutor_usuariopadretutor.setString_apellido_materno(string_apellido_materno);
                usuariopadretutor_usuariopadretutor.setString_apellido_paterno(string_apellido_paterno);
                usuariopadretutor_usuariopadretutor.setString_correo(string_correo);
                usuariopadretutor_usuariopadretutor.setString_direccion(string_direccion);
                usuariopadretutor_usuariopadretutor.setString_fecha_nacimiento(string_fecha_nacimiento);



                usuariopadretutor_usuariopadretutor.setString_fecha_pago("-1");
                usuariopadretutor_usuariopadretutor.setString_fecha_fin_suscripcion("-1");


                usuariopaciente_usuariopaciente.setString_nombre_paciente(string_nombre_paciente);
                usuariopaciente_usuariopaciente.setInt_puntuacion_alta_actividad_1(0);
                usuariopaciente_usuariopaciente.setInt_puntuacion_alta_actividad_2(0);
                usuariopaciente_usuariopaciente.setInt_puntuacion_alta_actividad_3(0);
                usuariopaciente_usuariopaciente.setInt_puntuacion_actividad_1(0);
                usuariopaciente_usuariopaciente.setInt_puntuacion_actividad_2(0);
                usuariopaciente_usuariopaciente.setInt_puntuacion_actividad_3(0);


                firebaseuser_usuario_actual.sendEmailVerification().addOnCompleteListener(task1 ->
                {
                    if (task1.isSuccessful())
                    {
                        Toast.makeText(RegistroUsuario.this, "Mensaje enviado", Toast.LENGTH_SHORT).show();
                        databasereference_databasereference.child("Usuario").child(usuariopadretutor_usuariopadretutor.getString_id()).setValue(usuariopadretutor_usuariopadretutor).addOnCompleteListener(task2 ->
                        {
                            if (task2.isSuccessful())
                            {
                                databasereference_databasereference.child("Usuario").child(usuariopadretutor_usuariopadretutor.getString_id()).child("Paciente").setValue(usuariopaciente_usuariopaciente).addOnCompleteListener(task3 ->
                                {
                                    if (task3.isSuccessful())
                                    {
                                        Toast.makeText(RegistroUsuario.this, "Usuario registrado", Toast.LENGTH_LONG).show();

                                        startActivity(new Intent(RegistroUsuario.this, UsuarioPrincipal.class));
                                        finish();

                                    }
                                });

                            } else
                            {
                                Toast.makeText(RegistroUsuario.this, "No se pudo realizar el registro", Toast.LENGTH_LONG).show();
                            }

                        });
                    } else
                    {
                        Toast.makeText(RegistroUsuario.this, "Mensaje no recibido", Toast.LENGTH_SHORT).show();
                    }

                });

            } else
            {
                Toast.makeText(RegistroUsuario.this, "Fallo de autenticación", Toast.LENGTH_LONG).show();
            }
        });

    }



    /**
     * Abre main_activity
     *
     * @param view_view vista a la que mandará el método
     */
    public void ir_main(View view_view)
    {
        Intent intent_ir = new Intent(this, MainActivity.class);
        startActivity(intent_ir);
    }

    /**
     * Abre activity_inicio_de_sesion
     *
     * @param view_view vista a la que mandará el método
     */
    public void ir_inicio_de_sesion(View view_view)
    {
        Intent intent_ir = new Intent(this, InicioDeSesion.class);
        startActivity(intent_ir);
    }

    /**
     * Valida datos relacionados a la CURP.
     *
     * @param string_renapo Contiene los datos de renapo
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void valida_datos_curp(String string_renapo)
    {
        DatosDeCurp datosdecurp_validar = new DatosDeCurp(string_renapo);
        String string_nombre = datosdecurp_validar.getString_nombre();
        String string_apellido_paterno = datosdecurp_validar.getString_apellido_paterno();
        String string_apellido_materno = datosdecurp_validar.getString_apellido_materno();
        string_direccion = datosdecurp_validar.getString_estado_nacimiento();

        edittext_nombre_padre_tutor = findViewById(R.id.txt_nombre_padre_tutor);
        edittext_nombre_padre_tutor.setText(string_nombre);
        edittext_apellido_paterno = findViewById(R.id.txt_apellido_paterno);
        edittext_apellido_paterno.setText(string_apellido_paterno);
        edittext_apellido_materno = findViewById(R.id.txt_apellido_materno);
        edittext_apellido_materno.setText(string_apellido_materno);
        String string_fecha_nacimiento_v = datosdecurp_validar.getString_fecha_nacimiento();
        boolean_edad = valida_edad(string_fecha_nacimiento_v);
    }

    /**
     * Valida la edad del usuario y activa el botón de registro
     * @param string_fecha_nacimiento_v String que contiene la fecha del usuario
     *
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean valida_edad(String string_fecha_nacimiento_v)
    {
        boolean boolean_edad = false;
        String[] string_fecha = string_fecha_nacimiento_v.split("-");
        string_fecha_nacimiento = string_fecha[0] + "-" + string_fecha[1] + "-" + string_fecha[2].substring(0, 2);
        int int_anio = Integer.parseInt(string_fecha[0]);
        int int_mes = Integer.parseInt(string_fecha[1]);
        int int_dia = Integer.parseInt(string_fecha[2].substring(0, 2));
        LocalDate localdate_fecha_actual = LocalDate.now();
        LocalDate localdate_fecha_nacimiento = LocalDate.of(int_anio, int_mes, int_dia);
        Period period_edad = Period.between(localdate_fecha_nacimiento, localdate_fecha_actual);
        if (period_edad.getYears() >= 18)
        {
            button_registrarse.setEnabled(true);
        }else
        {
            Toast.makeText(RegistroUsuario.this, "El padre o tutor no es mayor de edad", Toast.LENGTH_LONG).show();
        }
        return boolean_edad;
    }

    /**
     * Recupera los datos de la API
     * @param edittext_curp curp para buscarlo en la api
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void recuperar(EditText edittext_curp)
    {
        StringRequest stringrequest_requerimiento = new StringRequest(Request.Method.GET,
                "https://us-west4-arsus-production.cloudfunctions.net/curp?curp=" + edittext_curp.getText().toString() + "&apiKey=WgrtpPpMT6gCrKmawXDipiEzQQv2",
                this::valida_datos_curp,
                error -> Toast.makeText(RegistroUsuario.this, "INTENTE DE NUEVO.", Toast.LENGTH_LONG).show());
        requestqueue_rq.add(stringrequest_requerimiento);
    }

    /**
     * Valida el formato de editText_nombre_paciente
     * @param edittext_nombre_paciente EditText que contiente el paciente
     * @return boolean_nombre_paciente_v Regresa el booleano false si no es correcto el formato y true es formato
     */
    private boolean valida_nombre_paciente(EditText edittext_nombre_paciente)
    {

        edittext_nombre_paciente.setError(null);

        String string_nombre_paciente = edittext_nombre_paciente.getText().toString().trim();

        boolean boolean_nombre_paciente_v = true;

        View view_focusview = null;

        if (TextUtils.isEmpty(string_nombre_paciente))
        {
            edittext_nombre_paciente.setError(getString((R.string.error_campo_requerido)));
            view_focusview = edittext_nombre_paciente;
            boolean_nombre_paciente_v = false;
        }

        if (!string_nombre_paciente.matches(".{2,20}"))
        {
            edittext_nombre_paciente.setError(getString(R.string.error_formato_no_valido));
            view_focusview = edittext_nombre_paciente;
            boolean_nombre_paciente_v = false;
        }

        if (!boolean_nombre_paciente_v)
        {
            view_focusview.requestFocus();
        }

        return boolean_nombre_paciente_v;
    }

    /**
     * Valida si una CURP es correcta en un EditText.
     *
     * @param edittext_curp EditText asociado.
     * @return Verdadero si la CURP es correcta, falso en caso contrario.
     */
    private boolean valida_curp(EditText edittext_curp)
    {

        edittext_curp.setError(null);

        String string_curp = edittext_curp.getText().toString().trim();

        boolean boolean_curp_v = true;

        View view_focusview = null;

        if (TextUtils.isEmpty(string_curp))
        {
            edittext_curp.setError(getString(R.string.error_campo_requerido));
            view_focusview = edittext_curp;
            boolean_curp_v = false;
        }

        if (!string_curp.matches("[A-Z]{1}[AEIOU]{1}[A-Z]{2}[0-9]{2}" +
                "(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1])" +
                "[HM]{1}" +
                "(AS|BC|BS|CC|CS|CH|CL|CM|DF|DG|GT|GR|HG|JC|MC|MN|MS|NT|NL|OC|PL|QT|QR|SP|SL|SR|TC|TS|TL|VZ|YN|ZS|NE)" +
                "[B-DF-HJ-NP-TV-Z]{3}" +
                "[0-9A-Z]{1}[0-9]{1}$"))
        {
            edittext_curp.setError(getString(R.string.error_formato_no_valido));
            view_focusview = edittext_curp;
            boolean_curp_v = false;
        }

        if (!boolean_curp_v)
        {

            view_focusview.requestFocus();

        }

        return boolean_curp_v;
    }
    /**
     * Esta funcion retorna verdadero si el nip tiene errores y  si es falso no tiene errores
     *
     * @param edittext_nip EditText nip
     * @return boolean_nip_v
     */
    private boolean valida_nip(EditText edittext_nip)
    {

        edittext_nip.setError(null);

        String string_nip = edittext_nip.getText().toString().trim();

        boolean boolean_nip_v = true;

        View view_focusview = null;

        if (string_nip.isEmpty())
        {
            edittext_nip.setError(getString(R.string.error_campo_requerido));
            view_focusview = edittext_nip;
            boolean_nip_v = false;
        }

        if (!string_nip.matches(".{4}"))
        {
            edittext_nip.setError(getString(R.string.error_cuatro_digitos));
            view_focusview = edittext_nip;
            boolean_nip_v = false;
        }

        if (!boolean_nip_v)
        {

            view_focusview.requestFocus();

        }

        return boolean_nip_v;
    }

    /**
     * Esta funcion retorna verdadero si la contrasena tiene errores y  si es falso no tiene errores
     *
     * @param edittext_contrasena EditText contrasena
     * @return boolean_contrasena_v
     */
    private boolean valida_contrasena(EditText edittext_contrasena)
    {

        edittext_contrasena.setError(null);

        String string_password = edittext_contrasena.getText().toString().trim();

        boolean boolean_contrasena_v = false;

        View view_focusview = null;

        if (TextUtils.isEmpty(string_password))
        {
            edittext_contrasena.setError(getString(R.string.error_campo_requerido));
            view_focusview = edittext_contrasena;
            boolean_contrasena_v = true;
        }

        if (!string_password.matches(".*[!@#$%^&*+=?-].*"))
        {
            edittext_contrasena.setError(getString(R.string.error_caracter_especial_requerido));
            view_focusview = edittext_contrasena;
            boolean_contrasena_v = true;
        }

        if (!string_password.matches(".*\\d.*"))
        {
            edittext_contrasena.setError(getString(R.string.error_numero_requerido));
            view_focusview = edittext_contrasena;
            boolean_contrasena_v = true;
        }

        if (!string_password.matches(".*[a-z].*"))
        {
            edittext_contrasena.setError(getString(R.string.error_no_se_encontraron_minusculas));
            view_focusview = edittext_contrasena;
            boolean_contrasena_v = true;
        }

        if (!string_password.matches(".*[A-Z].*"))
        {
            edittext_contrasena.setError(getString(R.string.error_no_se_encontraron_mayusculas));
            view_focusview = edittext_contrasena;
            boolean_contrasena_v = true;
        }

        if (!string_password.matches(".{8,15}"))
        {
            edittext_contrasena.setError(getString(R.string.error_contrasena_muy_corta));
            view_focusview = edittext_contrasena;
            boolean_contrasena_v = true;
        }

        if (string_password.matches(".*\\s.*"))
        {
            edittext_contrasena.setError(getString(R.string.error_sin_espacios));
            view_focusview = edittext_contrasena;
            boolean_contrasena_v = true;
        }

        if (boolean_contrasena_v)
        {

            view_focusview.requestFocus();

        }
        return boolean_contrasena_v;
    }

    /**
     * Esta funcion retorna verdadero si el correo tiene errores y falso si el correo no tiene errores
     *
     * @param edittext_correo EditText correo
     * @return boolean_error
     */
    private boolean valida_correo(EditText edittext_correo)
    {

        edittext_correo.setError(null);

        boolean boolean_correo_v = true;

        View view_focusview = null;

        Pattern pattern_pattern = Patterns.EMAIL_ADDRESS;

        String string_email = edittext_correo.getText().toString().trim();

        if (TextUtils.isEmpty(string_email))
        {
            edittext_correo.setError(getString(R.string.error_campo_requerido));
            view_focusview = edittext_correo;
            boolean_correo_v = false;
        } else if (!pattern_pattern.matcher(string_email).matches())
        {
            edittext_correo.setError(getString(R.string.error_correo_no_valido));
            view_focusview = edittext_correo;
            boolean_correo_v = false;
        }
        if (!boolean_correo_v)
        {

            view_focusview.requestFocus();

        }
        return boolean_correo_v;

    }

}