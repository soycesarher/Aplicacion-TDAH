package com.example.tdah;


import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

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

import com.example.tdah.modelos.UsuarioPadreTutor;
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

public class UsuarioPrincipal extends AppCompatActivity {

    private static final String TAG = "Notificación pago";
    private DatabaseReference databaseReference;

    private FirebaseAuth mAuth;

    private FirebaseUser firebaseUser;

    AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_principal);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        inicializaFirebase();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();


        NavigationView navigationView = findViewById(R.id.nav_view);

         mAppBarConfiguration = new AppBarConfiguration.Builder(
                navController.getGraph())
                .setDrawerLayout(drawer)
                .build();
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView,navController);
    }

    @Override
    protected void onStart() {
        super.onStart();

        UsuarioPadreTutor u = new UsuarioPadreTutor();

        u.setString_id(firebaseUser.getUid());

        databaseReference.child("Usuario").child(u.getString_id()).addValueEventListener(new ValueEventListener() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String string_fecha_termino_suscripcion = snapshot.child("string_fecha_fin_suscripcion").getValue().toString();
                    if(!string_fecha_termino_suscripcion.equalsIgnoreCase("-1"))
                    validaFechaSuscripcion(string_fecha_termino_suscripcion);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

    }

    /**
     * Crea he inicializa las instancias de Firebase Authentication y obtiene la referencia de
     * Firebase Database
     */
    private void inicializaFirebase() {

        mAuth = FirebaseAuth.getInstance();

        FirebaseDatabase firebase_database = FirebaseDatabase.getInstance();

        databaseReference = firebase_database.getReference();

        firebaseUser = mAuth.getCurrentUser();

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_usuario, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    /**
     * Regresa el tipo de cuenta
     *
     * @return string_cuenta
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void validaFechaSuscripcion(String fecha_termino_suscripcion){

        UsuarioPadreTutor u = new UsuarioPadreTutor();

        u.setString_id(firebaseUser.getUid());

        DateTimeFormatter dateTimeFormatter_formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime  localDate_fecha_actual = LocalDateTime.parse(LocalDateTime.now().format(dateTimeFormatter_formato), dateTimeFormatter_formato);

        LocalDateTime  localDate_fecha_termino_suscripcion = LocalDateTime.parse(fecha_termino_suscripcion, dateTimeFormatter_formato);

        Duration duration_dias = Duration.between(localDate_fecha_actual, localDate_fecha_termino_suscripcion);

        if(duration_dias.toDays() == 2){

            Log.e(TAG,"LE QUEDAN: "+duration_dias.toDays()+" DE SUSCRIPCION");

        }else if(duration_dias.isZero()){

            Log.e(TAG,"SU SUSCRIPCIÓN HA TERMINADO");

            databaseReference.child("Usuario").child(u.getString_id()).child("string_fecha_fin_suscripcion").setValue("-1");

            databaseReference.child("Usuario").child(u.getString_id()).child("string_fecha_pago").setValue("-1");

        }

    }

}