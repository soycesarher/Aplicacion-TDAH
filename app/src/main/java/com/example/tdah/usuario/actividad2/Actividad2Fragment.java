/**
 *Clase: Actividad 2 para pacientes. Es sobre
 *resolver triángulos mágicos.
 *
 * @author: TDAH Móvil
 */
package com.example.tdah.usuario.actividad2;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.tdah.R;
import com.example.tdah.UsuarioPrincipal;
import com.example.tdah.modelos.UsuarioPaciente;
import com.example.tdah.modelos.UsuarioPadreTutor;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Actividad2Fragment extends Fragment implements View.OnClickListener {

    private FirebaseAuth firebaseauth_mauth;
    private FirebaseUser firebaseuser_firebaseuser;
    private FirebaseDatabase firebasedatabase_firebasedatabase;
    private DatabaseReference databasereference_databasereference;
    private UsuarioPaciente usuariopaciente_usuariopaciente;
    private UsuarioPadreTutor usuariopadretutor_usuariopadretutor;

    private Actividad2ViewModel actividad2viewmodel_actividad2viewmodel;
    private EditText edittext_triangulo_nv1_a;
    private EditText edittext_triangulo_nv2_a;
    private EditText edittext_triangulo_nv2_b;
    private EditText edittext_triangulo_nv3_a;
    private EditText edittext_triangulo_nv3_b;
    private EditText edittext_triangulo_nv3_c;
    private TextView textview_suma_l;
    private TextView textview_suma_r;
    private TextView textview_suma_b;
    private TextView textview_str_puntos;
    private TextView textview_num_puntos;
    private Button button_comprobar;
    private Button button_reiniciar;

    UsuarioPrincipal usuarioprincipal_main;

    /**
     * Inicializa la actividad (pantalla).
     *
     * @param layoutinflater_inflater Diseño base.
     * @param viewgroup_container Contenedor base.
     * @param bundle_savedInstanceState Instancia activa.
     *
     * @return Vista para la actividad.
     */
    public View onCreateView(@NonNull LayoutInflater layoutinflater_inflater,
                             ViewGroup viewgroup_container, Bundle bundle_savedInstanceState)
    {

        actividad2viewmodel_actividad2viewmodel =
                new ViewModelProvider(this).get(Actividad2ViewModel.class);
        View view_root = layoutinflater_inflater.inflate(R.layout.fragment_actividad2, viewgroup_container, false);
        inicializaFirebase();
        validaCuenta();
        Componentes(view_root);
        usuarioprincipal_main = (UsuarioPrincipal) getParentFragment().getActivity();
        actividad2viewmodel_actividad2viewmodel.getText().observe(getViewLifecycleOwner(), s ->
        {
        });
        return view_root;
    }

    /**
     * Inicializa la instancia de la base de datos.
     */
    private void inicializaFirebase()
    {

        firebaseauth_mauth = FirebaseAuth.getInstance();

        FirebaseDatabase firebasedatabase_firebase_database = FirebaseDatabase.getInstance();

        databasereference_databasereference = firebasedatabase_firebase_database.getReference();

        firebaseuser_firebaseuser = firebaseauth_mauth.getCurrentUser();

    }

    /**
     * Valida el tipo de cuenta que tiene el usuario.
     */
    private void validaCuenta() {
        databasereference_databasereference.child("Usuario").child(firebaseuser_firebaseuser.getUid()).child("string_fecha_pago").addValueEventListener(new ValueEventListener()
        {
            /**
             * Se mantiene pendiente de cambios en la cuenta del usuario.
             *
             * @param datasnapshot_snapshot DataSnapshot asociado a la actividad.
             */
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot_snapshot)
            {
                if (datasnapshot_snapshot.exists())
                {
                    String string_fecha_pago = datasnapshot_snapshot.getValue().toString();
                    if(string_fecha_pago.equals("-1"))
                    {
                        Toast.makeText( getContext(), "Actividad de pago", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getContext(),UsuarioPrincipal.class));

                    }
                }

            }

            /**
             * Se mantiene pendiente ante la desconexión a la base de datos.
             *
             * @param databaseerror_error Error de base de datos asociado.
             */
            @Override
            public void onCancelled(@NonNull DatabaseError databaseerror_error)
            {

            }
        });
    }

    /**
     * Inicializa los componentes gráficos de la actividad.
     *
     * @param view_root Vista de la actividad (pantalla) asociada.
     */
    private void Componentes(View view_root)
    {
        Botones(view_root);
        EditTextComponent(view_root);
        TextViewComponent(view_root);
    }

    /**
     * Asocia los componentes (EditText) a variables.
     *
     * @param view_root Vista de la actividad (pantalla) asociada.
     */
    private void EditTextComponent(View view_root)
    {
        edittext_triangulo_nv1_a = (EditText) view_root.findViewById(R.id.triangulo_niv1);
        edittext_triangulo_nv2_a = (EditText) view_root.findViewById(R.id.triangulo_niv2_a);
        edittext_triangulo_nv2_b = (EditText) view_root.findViewById(R.id.triangulo_niv2_b);
        edittext_triangulo_nv3_a = (EditText) view_root.findViewById(R.id.triangulo_niv3_a);
        edittext_triangulo_nv3_b = (EditText) view_root.findViewById(R.id.triangulo_niv3_b);
        edittext_triangulo_nv3_c = (EditText) view_root.findViewById(R.id.triangulo_niv3_c);
    }

    /**
     * Asocia los componentes (TextView) a variables.
     *
     * @param view_root Vista de la actividad (pantalla) asociada.
     */
    private void TextViewComponent(View view_root)
    {
        textview_suma_l = (TextView) view_root.findViewById(R.id.triangulo_puntos_izq);
        textview_suma_r = (TextView) view_root.findViewById(R.id.triangulo_puntos_der);
        textview_suma_b = (TextView) view_root.findViewById(R.id.triangulo_puntos_bas);
        textview_str_puntos = (TextView) view_root.findViewById(R.id.puntos_actv2);
        textview_num_puntos = (TextView) view_root.findViewById(R.id.numero_puntos_actv2);
    }

    /**
     * Asocia los componentes (Botones) a variables y
     * les agrega un listener.
     *
     * @param view_root Vista de la actividad (pantalla) asociada.
     */
    private void Botones(View view_root)
    {
        button_comprobar = view_root.findViewById(R.id.btn_comprobar_triangulo);
        button_comprobar.setOnClickListener(this);
        button_reiniciar = view_root.findViewById(R.id.btn_reiniciar_triangulo);
        button_reiniciar.setOnClickListener(this);
    }

    /**
     * Restablece los valores por defecto de la pantalla de la actividad.
     *
     * @param view_v Vista de la actividad (pantalla) asociada.
     */
    @Override
    public void onClick(View view_v)
    {
        if(button_comprobar.getId() == view_v.getId())
        {
            sumarTriangulo();
        } else
        {
            edittext_triangulo_nv1_a.setText("");
            edittext_triangulo_nv2_a.setText("");
            edittext_triangulo_nv2_b.setText("");
            edittext_triangulo_nv3_a.setText("");
            edittext_triangulo_nv3_b.setText("");
            edittext_triangulo_nv3_c.setText("");
            textview_suma_l.setText("0");
            textview_suma_r.setText("0");
            textview_suma_b.setText("0");
            textview_num_puntos.setText("0");
        }
    }

    /**
     * Realiza la suma de los lados del triángulo mágico
     * para comprobar si es una solución de este.
     */
    private void sumarTriangulo ()
    {
        try
        {
            /*
             *Obtención y casting de los valores de los EditText
             *comillas iniciales y finales de las cadenas de clave y dato.
             */
            String string_niv1_a = edittext_triangulo_nv1_a.getText().toString();
            int int_valor_niv1_a = Integer.parseInt(string_niv1_a);

            String string_niv2_a = edittext_triangulo_nv2_a.getText().toString();
            int int_valor_niv2_a = Integer.parseInt(string_niv2_a);

            String string_niv2_b = edittext_triangulo_nv2_b.getText().toString();
            int int_valor_niv2_b = Integer.parseInt(string_niv2_b);

            String string_niv3_a = edittext_triangulo_nv3_a.getText().toString();
            int int_valor_niv3_a = Integer.parseInt(string_niv3_a);

            String string_niv3_b = edittext_triangulo_nv3_b.getText().toString();
            int int_valor_niv3_b = Integer.parseInt(string_niv3_b);

            String string_niv3_c = edittext_triangulo_nv3_c.getText().toString();
            int int_valor_niv3_c = Integer.parseInt(string_niv3_c);

            /*
             *Comparaciones lógicas para determinar si el triángulo cumple
             *con la propiedad de ser mágico, es decir, los valores dan
             *solución a este conforme a las reglas.
             */
            if (int_valor_niv1_a<7&&int_valor_niv2_a<7&&int_valor_niv2_b<7&&int_valor_niv3_a<7&&int_valor_niv3_b<7&&int_valor_niv3_c<7)
            {
                if (int_valor_niv1_a>0&&int_valor_niv2_a>0&&int_valor_niv2_b>0&&int_valor_niv3_a>0&&int_valor_niv3_b>0&&int_valor_niv3_c>0)
                {
                    if(int_valor_niv1_a!=int_valor_niv2_a&&int_valor_niv1_a!=int_valor_niv2_b&&int_valor_niv1_a!=int_valor_niv3_a&&int_valor_niv1_a!=int_valor_niv3_b&&int_valor_niv1_a!=int_valor_niv3_c)
                    {
                        if(int_valor_niv2_a!=int_valor_niv1_a&&int_valor_niv2_a!=int_valor_niv2_b&&int_valor_niv2_a!=int_valor_niv3_a&&int_valor_niv2_a!=int_valor_niv3_b&&int_valor_niv2_a!=int_valor_niv3_c)
                        {
                            if(int_valor_niv2_b!=int_valor_niv1_a&&int_valor_niv2_b!=int_valor_niv2_a&&int_valor_niv2_b!=int_valor_niv3_a&&int_valor_niv2_b!=int_valor_niv3_b&&int_valor_niv2_b!=int_valor_niv3_c)
                            {
                                if(int_valor_niv3_a!=int_valor_niv1_a&&int_valor_niv3_a!=int_valor_niv2_a&&int_valor_niv3_a!=int_valor_niv2_b&&int_valor_niv3_a!=int_valor_niv3_b&&int_valor_niv3_a!=int_valor_niv3_c)
                                {
                                    if(int_valor_niv3_b!=int_valor_niv1_a&&int_valor_niv3_b!=int_valor_niv2_a&&int_valor_niv3_b!=int_valor_niv2_b&&int_valor_niv3_b!=int_valor_niv3_a&&int_valor_niv3_b!=int_valor_niv3_c)
                                    {
                                        if(int_valor_niv3_c!=int_valor_niv1_a&&int_valor_niv3_c!=int_valor_niv2_a&&int_valor_niv3_c!=int_valor_niv2_b&&int_valor_niv3_c!=int_valor_niv3_a&&int_valor_niv3_c!=int_valor_niv3_b)
                                        {
                                            int int_suma_lado_izq = int_valor_niv3_a+int_valor_niv2_a+int_valor_niv1_a;
                                            int int_suma_lado_der = int_valor_niv3_c+int_valor_niv2_b+int_valor_niv1_a;
                                            int int_suma_lado_base = int_valor_niv3_a+int_valor_niv3_b+int_valor_niv3_c;
                                            int int_suma_puntos_total = 0;
                                            textview_suma_l.setText(""+int_suma_lado_izq);
                                            textview_suma_r.setText(""+int_suma_lado_der);
                                            textview_suma_b.setText(""+int_suma_lado_base);

                                            //La solución es correcta.
                                            if(int_suma_lado_izq == 10 && int_suma_lado_der == 10 && int_suma_lado_base == 10)
                                            {
                                                Toast.makeText(usuarioprincipal_main.getApplicationContext(), "Es Correcto. Lo lograste! Felicidades!!", Toast.LENGTH_SHORT).show();
                                                int_suma_puntos_total = 30;
                                                guardaProgreso(int_suma_puntos_total);
                                            }else
                                            {
                                                //El lado izquierdo del triángulo es correcto.
                                                if(int_suma_lado_izq == 10)
                                                {
                                                    int_suma_puntos_total+=10;
                                                    guardaProgreso(int_suma_puntos_total);
                                                    Toast.makeText(usuarioprincipal_main.getApplicationContext(), "El lado izquierdo es Correcto!", Toast.LENGTH_SHORT).show();
                                                    //El lado izquierdo del triángulo es incorrecto.
                                                } else
                                                {
                                                    Toast.makeText(usuarioprincipal_main.getApplicationContext(), "Te equivocaste en el lado izquierdo!", Toast.LENGTH_SHORT).show();
                                                }
                                                //El lado derecho del triángulo es correcto.
                                                if(int_suma_lado_der == 10)
                                                {
                                                    int_suma_puntos_total+=10;
                                                    guardaProgreso(int_suma_puntos_total);
                                                    Toast.makeText(usuarioprincipal_main.getApplicationContext(), "El lado derecho es Correcto!", Toast.LENGTH_SHORT).show();
                                                    //El lado derechodel triángulo es incorrecto.
                                                } else
                                                {
                                                    Toast.makeText(usuarioprincipal_main.getApplicationContext(), "Te equivocaste en el lado derecho!", Toast.LENGTH_SHORT).show();
                                                }
                                                //El lado inferior (base) del triángulo es correcto.
                                                if(int_suma_lado_base == 10)
                                                {
                                                    int_suma_puntos_total+=10;
                                                    guardaProgreso(int_suma_puntos_total);
                                                    Toast.makeText(usuarioprincipal_main.getApplicationContext(), "El lado base es Correcto!", Toast.LENGTH_SHORT).show();
                                                    //El lado inferior (base) del triángulo es incorrecto.
                                                } else
                                                {
                                                    Toast.makeText(usuarioprincipal_main.getApplicationContext(), "Te equivocaste en el lado base!", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                            textview_num_puntos.setText(""+int_suma_puntos_total);
                                        } else {msm_dato_rep();
                                        }
                                    } else {msm_dato_rep();
                                    }
                                } else {msm_dato_rep();
                                }
                            } else {msm_dato_rep();
                            }
                        } else {msm_dato_rep();
                        }
                    } else {msm_dato_rep();
                    }
                    //Verificación de restricción de números del 1 al 6.
                } else
                {
                    Toast.makeText(usuarioprincipal_main.getApplicationContext(), "Solo debes ocupar números del 1 al 6", Toast.LENGTH_SHORT).show();
                }
                //Verificación de restricción de números del 1 al 6.
            } else
            {
                Toast.makeText(usuarioprincipal_main.getApplicationContext(), "Solo debes ocupar números del 1 al 6", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e)
        {
            //Verificación de que los EditText han sido llenados.
            //Toast.makeText(usuarioprincipal_main.getApplicationContext(), "Inténtalo de nuevo", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Manda mensaje a usuario para indicar que debe seguir la
     * regla de que no se pueden repetir los números.
     */
    private void msm_dato_rep()
    {
        Toast.makeText(usuarioprincipal_main.getApplicationContext(), "No se pueden repetir los numeros!", Toast.LENGTH_SHORT).show();
    }

    /**
     * Guarda en la base de datos el progreso del usuario en la
     * actividad.
     *
     * @param int_puntuacion Puntuación obtenida por el usuario en la
     * actividad.
     */
    public void guardaProgreso(int int_puntuacion)
    {

        usuariopadretutor_usuariopadretutor.setString_id(firebaseuser_firebaseuser.getUid());

        usuariopaciente_usuariopaciente.setInt_puntuacion_actividad_2(int_puntuacion);

        databasereference_databasereference.child("Usuario").child(usuariopadretutor_usuariopadretutor.getString_id()).child("Paciente").child("int_puntuacion_actividad_2").setValue(usuariopaciente_usuariopaciente.getInt_puntuacion_actividad_2());

        databasereference_databasereference.child("Usuario").child(usuariopadretutor_usuariopadretutor.getString_id()).child("Paciente").addValueEventListener(new ValueEventListener()
        {

            /**
             * Detecta la actividad de los cambios en la puntuación.
             *
             * @param datasnapshot_snapshot Variable de detección de la puntuación.
             */
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot_snapshot)
            {
                if (datasnapshot_snapshot.exists())
                {

                    usuariopaciente_usuariopaciente.setInt_puntuacion_alta_actividad_2(Integer.parseInt(datasnapshot_snapshot.child("int_puntuacion_alta_actividad_2").getValue().toString()));
                    usuariopaciente_usuariopaciente.setInt_contador_actividad_2(Integer.parseInt(datasnapshot_snapshot.child("int_contador_actividad_2").getValue().toString()));
                    usuariopaciente_usuariopaciente.setInt_suma_puntuacion_actividad_2(Integer.parseInt(datasnapshot_snapshot.child("int_suma_actividad_2").getValue().toString()));
                    usuariopaciente_usuariopaciente.setFloat_promedio_actividad_2(Float.parseFloat(datasnapshot_snapshot.child("float_promedio_actividad_2").getValue().toString()));

                    int int_puntuacion_actual = usuariopaciente_usuariopaciente.getInt_puntuacion_actividad_2(), puntuacion_actual_alta = usuariopaciente_usuariopaciente.getInt_puntuacion_alta_actividad_2(),
                            contador = usuariopaciente_usuariopaciente.getInt_contador_actividad_2(), suma = usuariopaciente_usuariopaciente.getInt_suma_puntuacion_actividad_2();

                    float float_promedio;

                    contador++;
                    suma += usuariopaciente_usuariopaciente.getInt_suma_puntuacion_actividad_2();
                    float_promedio = suma / contador;

                    databasereference_databasereference.child("Usuario").child(usuariopadretutor_usuariopadretutor.getString_id()).child("Paciente").child("int_contador_actividad_2").setValue(contador);
                    databasereference_databasereference.child("Usuario").child(usuariopadretutor_usuariopadretutor.getString_id()).child("Paciente").child("int_suma_actividad_2").setValue(suma);
                    databasereference_databasereference.child("Usuario").child(usuariopadretutor_usuariopadretutor.getString_id()).child("Paciente").child("float_promedio_actividad_2").setValue(float_promedio);

                    if (int_puntuacion_actual > puntuacion_actual_alta)
                    {

                        databasereference_databasereference.child("Usuario").child(usuariopadretutor_usuariopadretutor.getString_id()).child("Paciente").child("int_puntuacion_alta_actividad_2").setValue(int_puntuacion_actual);

                        Toast.makeText(getContext(), "Nueva puntuación alta: " + int_puntuacion_actual, Toast.LENGTH_SHORT).show();

                    }
                }

            }

            /**
             * Cancela la actividad.
             *
             * @param databaseerror_error Instancia de base de datos asociada.
             */
            @Override
            public void onCancelled(@NonNull DatabaseError databaseerror_error)
            {

            }
        });
    }
}