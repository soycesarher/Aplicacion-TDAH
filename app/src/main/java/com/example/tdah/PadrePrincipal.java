package com.example.tdah;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tdah.usuario.padres.PadresFragment;

public class PadrePrincipal extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_padre_principal);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        startActivity(new Intent(this, UsuarioPrincipal.class));

        finish();
    }


}
