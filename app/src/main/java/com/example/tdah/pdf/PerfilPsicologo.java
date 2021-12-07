package com.example.tdah.pdf;

import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tdah.R;
import com.example.tdah.modelos.UsuarioPsicologo;
import com.github.barteksc.pdfviewer.PDFView;

import java.util.ArrayList;

public class PerfilPsicologo extends AppCompatActivity  {

    private TextView txt_nombre_psicologo_perfil,txt_direccion_consultorio,txt_especialidad;
    private ProgressBar pb_progreso_carga;
    private PDFView visor_pdf;

    private ArrayList<UsuarioPsicologo> lista_usuario_psicologo = new ArrayList<>();
    private int indice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_psicologo);

        pb_progreso_carga = findViewById(R.id.pb_progreso_carga);
        visor_pdf = findViewById(R.id.visor_pdf);
        txt_nombre_psicologo_perfil = findViewById(R.id.txt_nombre_psicologo_perfil);
        txt_especialidad = findViewById(R.id.txt_especialidad_psicologo_perfil);
        txt_direccion_consultorio = findViewById(R.id.txt_direccion_consultorio_psicologo_perfil);

        if (savedInstanceState == null) {
            Bundle bundle_extras = getIntent().getExtras();
            if(bundle_extras == null) {
                Toast.makeText(this,"No se cargaron los datos correctamente",Toast.LENGTH_LONG);
            } else {
                indice = (int) bundle_extras.getInt("Index");
                lista_usuario_psicologo = (ArrayList<UsuarioPsicologo>) bundle_extras.getSerializable("Psicologo");
            }
        } else {
            lista_usuario_psicologo = (ArrayList<UsuarioPsicologo>) savedInstanceState.getSerializable("Psicologo");
            indice = (int) savedInstanceState.getInt("Index");
        }

        cargaDatos(lista_usuario_psicologo, indice);
    }

    private void cargaDatos(ArrayList<UsuarioPsicologo> lista_usuario_psicologo, int indice) {

        UsuarioPsicologo usuarioPsicologo = lista_usuario_psicologo.get(indice);
        txt_nombre_psicologo_perfil.setText(usuarioPsicologo.getString_nombre());
        txt_especialidad.setText(usuarioPsicologo.getString_especialidad());
        txt_direccion_consultorio.setText(usuarioPsicologo.getString_direccion());
        new Pdf(visor_pdf,pb_progreso_carga).execute(usuarioPsicologo.getString_perfilProfesional());
    }


}