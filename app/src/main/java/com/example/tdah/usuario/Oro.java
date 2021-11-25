package com.example.tdah.usuario;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tdah.R;
import com.example.tdah.usuario.actividad3.Actividad3Fragment;

public class Oro extends AppCompatActivity {

    TextView txtPuntuacion, txtMenu;
    Button btnJugar, btnPuntuaciones, btnAcerdaDe, btnSalir;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oro);


        //UBICACION
        String ubicacion = "fuentes/letra.TTF";
        Typeface Tf = Typeface.createFromAsset(Oro.this.getAssets(),ubicacion);

        txtMenu = findViewById(R.id.txt_menu);
        txtPuntuacion = findViewById(R.id.txt_puntuacion);

        btnSalir = findViewById(R.id.btnSalir);
        btnJugar = findViewById(R.id.btnJugar);
        btnPuntuaciones = findViewById(R.id.btnPuntuaciones);
        btnAcerdaDe = findViewById(R.id.btnAcercaDe);

        //CAMBIO DE LETRA
        txtPuntuacion.setTypeface(Tf);
        txtMenu.setTypeface(Tf);
        btnAcerdaDe.setTypeface(Tf);
        btnPuntuaciones.setTypeface(Tf);
        btnJugar.setTypeface(Tf);
        btnSalir.setTypeface(Tf);


        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Oro.this, "SALIR", Toast.LENGTH_SHORT).show();
            }
        });

        btnJugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Oro.this, "JUGAR", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Oro.this, Escenario.class);
                startActivity(intent);
            }
        });

        btnPuntuaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Oro.this, "PUNTUACIONES", Toast.LENGTH_SHORT).show();
            }
        });

        btnAcerdaDe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Oro.this, "ACERCA DE", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
