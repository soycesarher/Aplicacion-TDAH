/**
 *Clase: Pantalla de inicio de la aplicación.
 *
 * @author: TDAH Móvil
 */
package com.example.tdah;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth firebaseauth_mauth;
    private FirebaseUser firebaseuser_fuser;
    private DatabaseReference databasereference_databasereference;

    /**
     * Inicializa la actividad (pantalla).
     *
     * @param bundle_savedinstanceState Estado de instancia asociado.
     */
    @Override
    protected void onCreate(Bundle bundle_savedinstanceState)
    {
        super.onCreate(bundle_savedinstanceState);
        setContentView(R.layout.main_activity);
        firebaseauth_mauth = FirebaseAuth.getInstance();

        firebaseuser_fuser = firebaseauth_mauth.getCurrentUser();
        databasereference_databasereference = FirebaseDatabase.getInstance().getReference();
    }

    /**
     * Inicia sesión de conexión de actividades (pantallas).
     *
     * @param view_view Vista asociada.
     */
    public void ir_iniciar_sesion(View view_view)
    {
        Intent ir = new Intent(this,InicioDeSesion.class);
        startActivity(ir);
    }

    /**
     * Abre pantalla de inicio de sesión de psicólogo.
     *
     * @param view_view Vista asociada.
     */
    public void ir_iniciar_sesion_psicologo(View view_view)
    {
        Intent ir = new Intent(this,InicioDeSesionPsicologo.class);
        startActivity(ir);
    }

    /**
     * Inicia procesos de la antividad (pantalla).
     */
    @Override
    protected void onStart()
    {
        super.onStart();
        firebaseuser_fuser = firebaseauth_mauth.getCurrentUser();
        if(firebaseauth_mauth.getCurrentUser()!= null)
        {
            databasereference_databasereference.child("Psicologo").orderByKey().equalTo(firebaseuser_fuser.getUid()).addListenerForSingleValueEvent(new ValueEventListener()
            {

                /**
                 * Se mantiene pendiente de cambios en la actividad (pantalla).
                 *
                 * @param datasnapshot_snapshot DataSnapshot asociado.
                 */
                @Override
                public void onDataChange(@NonNull DataSnapshot datasnapshot_snapshot)
                {
                    if (datasnapshot_snapshot.getChildrenCount()>0)
                    {
                        for (DataSnapshot datasanpshot_datasnapshot : datasnapshot_snapshot.getChildren())
                        {
                            String string_url_pdf =datasanpshot_datasnapshot.child("string_perfilProfesional").getValue().toString();

                            if (string_url_pdf.equalsIgnoreCase("-1"))
                            {
                                startActivity(new Intent(MainActivity.this, CargaPdf.class));
                                finish();
                            } else
                            {
                                startActivity(new Intent(MainActivity.this, PsicologoPrincipal.class));
                                finish();
                            }

                        }
                    }else
                    {
                        startActivity(new Intent(MainActivity.this,UsuarioPrincipal.class));
                        finish();
                    }
                }

                /**
                 * Se mantiene pendiente de cierre de conexión de la base
                 * de datos.
                 * @param databaseerror_error Error de base de datos asociado.
                 */
                @Override
                public void onCancelled(@NonNull DatabaseError databaseerror_error)
                {
                }
            });

        }
    }
}