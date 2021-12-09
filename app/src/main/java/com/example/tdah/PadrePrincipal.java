package com.example.tdah;

import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.tdah.databinding.ActivityPadrePrincipalBinding;
import com.google.android.material.navigation.NavigationView;

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
