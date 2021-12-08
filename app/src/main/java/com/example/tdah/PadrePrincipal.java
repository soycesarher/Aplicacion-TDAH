package com.example.tdah;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.tdah.databinding.ActivityPadrePrincipalBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class PadrePrincipal extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityPadrePrincipalBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_padre_principal);
        Toolbar toolbar = findViewById(R.id.toolbar_padre);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout_padre);

       NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_padre);
       NavController navController = navHostFragment.getNavController();
        NavigationView navigationView = findViewById(R.id.nav_view_padre);
       //    DrawerLayout drawers = binding.drawerLayout;


        mAppBarConfiguration = new AppBarConfiguration.Builder(
              navController.getGraph())
            .setDrawerLayout(drawer)
         .build();


        //mAppBarConfiguration = new AppBarConfiguration.Builder(
          //      navController.getGraph())
            //    .setDrawerLayout(drawer)
              // .build();
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

    }

  /* @Override
    protected void onStart() {
      super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        startActivity(new Intent(this, PadrePrincipal.class));

        finish();
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_padre, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_padre);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
