package com.example.tdah;

import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.tdah.databinding.ActivityPsicologoPrincipalBinding;
import com.example.tdah.modelos.UsuarioPsicologo;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PsicologoPrincipal extends AppCompatActivity {

    private static final String TAG = "Notificación pago";
    private DatabaseReference databaseReference;

    private FirebaseAuth mAuth;

    private FirebaseUser firebaseUser;

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityPsicologoPrincipalBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_psicologo_principal);
        /*binding = ActivityPsicologoPrincipalBinding.inflate(getLayoutInflater());*/
        Toolbar toolbar = findViewById(R.id.toolbar_psicologo);
        setSupportActionBar(toolbar);

        inicializa_firebase();


        DrawerLayout drawer = findViewById(R.id.drawer_layout_psicologo);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_psicologo_principal);
        assert navHostFragment != null;
        NavController navController = navHostFragment.getNavController();


        NavigationView navigationView = findViewById(R.id.nav_view_psicologo);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                navController.getGraph())
                .setDrawerLayout(drawer)
                .build();
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView,navController);
    }
    private void inicializa_firebase() {

        mAuth = FirebaseAuth.getInstance();

        FirebaseDatabase firebase_database = FirebaseDatabase.getInstance();

        databaseReference = firebase_database.getReference();

        firebaseUser = mAuth.getCurrentUser();

    }
    @Override
    protected void onStart() {
        super.onStart();

        UsuarioPsicologo u = new UsuarioPsicologo();

        u.setString_id(firebaseUser.getUid());

        databaseReference.child("Psicologo").child(u.getString_id()).addValueEventListener(new ValueEventListener() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String string_fecha_termino_suscripcion = snapshot.child("string_fecha_fin_suscripcion").getValue().toString();
                    if(!string_fecha_termino_suscripcion.equalsIgnoreCase("-1")){
                        valida_fecha_suscripcion(string_fecha_termino_suscripcion);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

    }

    /**
     * Regresa el tipo de cuenta
     *
     * @return string_cuenta
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void valida_fecha_suscripcion (String fecha_termino_suscripcion){

        UsuarioPsicologo usuarioPsicologo = new UsuarioPsicologo();

        usuarioPsicologo.setString_id(firebaseUser.getUid());

        DateTimeFormatter dateTimeFormatter_formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime localDate_fecha_actual = LocalDateTime.parse(LocalDateTime.now().format(dateTimeFormatter_formato), dateTimeFormatter_formato);

        LocalDateTime  localDate_fecha_termino_suscripcion = LocalDateTime.parse(fecha_termino_suscripcion, dateTimeFormatter_formato);

        Duration duration_dias_restantes = Duration.between(localDate_fecha_actual, localDate_fecha_termino_suscripcion);

        if(duration_dias_restantes.toDays() < 2){

            Toast.makeText(PsicologoPrincipal.this, "Tu suscripción esta a punto de expirar", Toast.LENGTH_SHORT).show();


        }else if(duration_dias_restantes.isZero()){

            Toast.makeText(PsicologoPrincipal.this, "Tu suscripción ha terminado", Toast.LENGTH_SHORT).show();
            databaseReference.child("Psicologo").child(usuarioPsicologo.getString_id()).child("string_fecha_fin_suscripcion").setValue("-1");

            databaseReference.child("Psicologo").child(usuarioPsicologo.getString_id()).child("string_fecha_pago").setValue("-1");

        }

    }

 /*   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.psicologo_principal, menu);
        return true;
    }*/

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_psicologo_principal);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}