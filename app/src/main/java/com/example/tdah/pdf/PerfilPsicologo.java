package com.example.tdah.pdf;

import android.os.Bundle;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tdah.R;
import com.github.barteksc.pdfviewer.PDFView;

public class PerfilPsicologo extends AppCompatActivity {

    private ProgressBar pb_progreso_carga;
    private PDFView visor_pdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_psicologo);
        pb_progreso_carga = findViewById(R.id.pb_progreso_carga);

        visor_pdf = findViewById(R.id.visor_pdf);

    }
}