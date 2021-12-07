package com.example.tdah;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.tdah.databinding.ActivityPsicologoPrincipalBinding;
import com.example.tdah.modelos.UsuarioPsicologo;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.time.Period;
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

        binding = ActivityPsicologoPrincipalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarPsicologoPrincipal.toolbar);
        binding.appBarPsicologoPrincipal.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_menu_ayuda, R.id.nav_menu_cuenta, R.id.nav_menu_configuracion)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_psicologo_principal);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
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
                    }else{

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

        DateTimeFormatter dateTimeFormatter_formato = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

        LocalDate localDate_fecha_actual = LocalDate.parse(LocalDate.now().format(dateTimeFormatter_formato), dateTimeFormatter_formato);

        LocalDate  localDate_fecha_termino_suscripcion = LocalDate.parse(fecha_termino_suscripcion, dateTimeFormatter_formato);

        Period period_edad = Period.between(localDate_fecha_actual, localDate_fecha_termino_suscripcion);

        if(period_edad.getDays() == 2){

            Log.e(TAG,"LE QUEDAN: "+period_edad.getDays()+" DE SUSCRIPCION");

        }else if(period_edad.isZero()){

            Log.e(TAG,"SU SUSCRIPCIÓN HA TERMINADO");

            databaseReference.child("Psicologo").child(usuarioPsicologo.getString_id()).child("string_fecha_fin_suscripcion").setValue("-1");

            databaseReference.child("Psicologo").child(usuarioPsicologo.getString_id()).child("string_fecha_pago").setValue("-1");

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.psicologo_principal, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_psicologo_principal);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}