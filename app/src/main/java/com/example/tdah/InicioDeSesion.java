package com.example.tdah;


import androidx.annotation.NonNull;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

public class InicioDeSesion extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser fUser;
    private DatabaseReference databaseReference;

    private boolean boolean_correo = false, boolean_contrasena = false;

    private Button btn_iniciar_sesion;

    private EditText txt_correo;
    private EditText txt_contrasena;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_de_sesion);

        mAuth = FirebaseAuth.getInstance();

        fUser = mAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        txt_correo = findViewById(R.id.txt_correo_inicia);
        txt_contrasena = findViewById(R.id.txt_contrasena_inicia);

        btn_iniciar_sesion = findViewById(R.id.btn_ingresar);

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

        txt_contrasena.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean_contrasena = contrasenaValida(txt_contrasena);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        btn_iniciar_sesion.setOnClickListener(v -> {
            if(TextUtils.isEmpty(txt_correo.getText())||TextUtils.isEmpty(txt_contrasena.getText())){
                Toast.makeText(InicioDeSesion.this, "No se pudo iniciar sesión" , Toast.LENGTH_SHORT).show();
            }else {
                if (boolean_correo && boolean_contrasena) {
                    Toast.makeText(InicioDeSesion.this, "Datos incorrectos", Toast.LENGTH_LONG).show();
                } else {
                    inicio_sesion_firebase(txt_correo.getText().toString(),txt_contrasena.getText().toString());

                }
            }
        });

    }

    /**
     * Esta funcion retorna verdadero si la contrasena tiene errores y  si es falso no tiene errores
     *
     * @param editText_contrasena EditText contrasena
     * @return boolean_error
     */
    private boolean contrasenaValida(EditText editText_contrasena) {

        editText_contrasena.setError(null);

        String Password = editText_contrasena.getText().toString().trim();

        boolean boolean_error = false;

        View focusView = null;

        if (TextUtils.isEmpty(Password)) {
            editText_contrasena.setError(getString(R.string.error_campo_requerido));
            focusView = editText_contrasena;
            boolean_error = true;
        }

        if (!Password.matches(".*[!@#$%^&*+=?-].*")) {
            editText_contrasena.setError(getString(R.string.error_caracter_especial_requerido));
            focusView = editText_contrasena;
            boolean_error = true;
        }

        if (!Password.matches(".*\\d.*")) {
            editText_contrasena.setError(getString(R.string.error_numero_requerido));
            focusView = editText_contrasena;
            boolean_error = true;
        }

        if (!Password.matches(".*[a-z].*")) {
            editText_contrasena.setError(getString(R.string.error_no_se_encontraron_minusculas));
            focusView = editText_contrasena;
            boolean_error = true;
        }

        if (!Password.matches(".*[A-Z].*")) {
            editText_contrasena.setError(getString(R.string.error_no_se_encontraron_mayusculas));
            focusView = editText_contrasena;
            boolean_error = true;
        }

        if (!Password.matches(".{8,15}")) {
            editText_contrasena.setError(getString(R.string.error_contrasena_muy_corta));
            focusView = editText_contrasena;
            boolean_error = true;
        }

        if (Password.matches(".*\\s.*")) {
            editText_contrasena.setError(getString(R.string.error_sin_espacios));

            focusView = editText_contrasena;
            boolean_error = true;
        }

        if (boolean_error) {

            focusView.requestFocus();

        } else {

            boolean_error = false;

        }
        return boolean_error;
    }

    /**
     * Esta funcion retorna verdadero si el correo tiene errores y falso si el correo no tiene errores
     *
     * @param editText_correo EditText correo
     * @return boolean_error
     */
    private boolean valida_correo(EditText editText_correo) {

        editText_correo.setError(null);

        String correo = editText_correo.getText().toString().trim();

        boolean boolean_error = false;

        View focusView = null;

        Pattern pattern = Patterns.EMAIL_ADDRESS;

        if (TextUtils.isEmpty(correo)) {
            editText_correo.setError(getString(R.string.error_campo_requerido));
            focusView = editText_correo;
            boolean_error = true;
        }
        if (!pattern.matcher(correo).matches()) {
            editText_correo.setError(getString(R.string.error_correo_no_valido));
            focusView = editText_correo;
            boolean_error = true;
        }
        if (boolean_error) {

            focusView.requestFocus();

        }
        return boolean_error;
    }

    /**
     * Valida si el correo y contraseña existen en la base de datos
     */
    private void inicio_sesion_firebase(String correo, String contrasena) {

        mAuth.signInWithEmailAndPassword(correo, contrasena)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        startActivity(new Intent(InicioDeSesion.this, UsuarioPrincipal.class));
                        finish();
                    } else {

                        Toast.makeText(InicioDeSesion.this, "Error de autentificación",
                                Toast.LENGTH_SHORT).show();

                    }
                });

    }

    @Override
    protected void onStart() {
        super.onStart();

            if(mAuth.getCurrentUser()!= null){
                databaseReference.child("Psicologo").orderByKey().equalTo(fUser.getUid()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            String string_url_pdf = snapshot.child("string_perfilProfesional").getValue().toString();
                            if(string_url_pdf.equalsIgnoreCase("-1")){
                                startActivity(new Intent(InicioDeSesion.this, CargaPdf.class));

                            }else {
                                startActivity(new Intent(InicioDeSesion.this, PsicologoPrincipal.class));
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        startActivity(new Intent(InicioDeSesion.this, UsuarioPrincipal.class));


                    }
                });


                finish();
            }

    }

    //conexion de activities
    public void ir_a_registro(View view) {

        startActivity(new Intent(this, RegistroUsuario.class));
    }
    // Este método recarga al menú principal si es que el usuario ya inició sesión


    public void ir_a_main(View view) {
        startActivity(new Intent(this, MainActivity.class));

    }

}