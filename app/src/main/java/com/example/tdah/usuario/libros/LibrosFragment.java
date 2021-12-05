package com.example.tdah.usuario.libros;

import androidx.fragment.app.Fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tdah.R;
import com.example.tdah.audio.Adaptador;
import com.example.tdah.audio.AudioModelo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


public class LibrosFragment extends Fragment implements Adaptador.OnClickListener {


    private final ArrayList<AudioModelo> lista_audio_modelo = new ArrayList<>();
    private Adaptador adapter;

    private MediaPlayer mediaPlayer;


    private Handler handler = new Handler();

    private RecyclerView recyclerView;
    private TextView txt_titulo, txt_duracion;
    private ImageButton imgbtn_reproducir, imgbtn_anterior, imgbtn_siguiente;
    private ImageView v_miniatura;

    private ProgressBar pb_progreso_cancion;

    private MediaController mediaController;

    private DatabaseReference databaseReference;

    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;

    private static int solo_una_vez = 0;
    private double double_tiempo_final = 0, double_tiempo_inicial = 0;
    private int int_indice = 0;

    private final int[] INTS_CANCIONES = {R.raw.caperucita_roja,
            R.raw.el_gato_con_botas,
            R.raw.la_bella_durmiente,
            R.raw.los_tres_cerditos,
            R.raw.pinocho};
    final int[] int_numero_canciones = {3};


    private final int[] INTS_IMAGENES = {R.drawable.imagen_caperucita_roja,
            R.drawable.imagen_gato_botas,
            R.drawable.imagen_bella_durmiente,
            R.drawable.imagen_tres_cerditos,
            R.drawable.imagen_pinocho};

    private final String[] STRINGS_NOMBRES_CANCION = {
            "Caperucita roja",
            "El gato con botas",
            "La bella durmiente",
            "Los tres cerditos",
            "Pinocho"
    };

    private final String[] STRINGS_TIPO = {"Gratis",
            "Gratis",
            "Gratis",
            "Paga",
            "Paga"};

    private LibrosViewModel librosViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        librosViewModel =
                new ViewModelProvider(this).get(LibrosViewModel.class);
        View root = inflater.inflate(R.layout.fragment_libros, container, false);

        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        recyclerView = root.findViewById(R.id.rv_canciones);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        txt_titulo = root.findViewById(R.id.txt_titulo_r);
        txt_duracion = root.findViewById(R.id.txt_duracion_r);
        v_miniatura = root.findViewById(R.id.v_miniatura);

        imgbtn_reproducir = root.findViewById(R.id.btn_reproducir);
        imgbtn_siguiente = root.findViewById(R.id.btn_siguiente);
        imgbtn_anterior = root.findViewById(R.id.btn_anterior);

        imgbtn_anterior.setEnabled(false);
        imgbtn_siguiente.setEnabled(false);
        imgbtn_reproducir.setEnabled(false);

        pb_progreso_cancion = root.findViewById(R.id.pb_progreso_cancion);

        cargaListaCanciones();

        mediaPlayer = new MediaPlayer();


        mediaPlayer.setOnBufferingUpdateListener((mp, percent) -> pb_progreso_cancion.setSecondaryProgress(percent));

        imgbtn_reproducir.setOnClickListener(v -> {

            if (mediaPlayer.isPlaying()) {

                mediaPlayer.pause();

                imgbtn_reproducir.setImageResource(android.R.drawable.ic_media_play);

            } else {

                mediaPlayer.start();

                imgbtn_reproducir.setImageResource(android.R.drawable.ic_media_pause);

            }

        });


        imgbtn_siguiente.setOnClickListener(v -> {

            int_indice++;

            if (int_indice >= int_numero_canciones[0])
                int_indice = 0;

            mediaPlayer.stop();

            cargaValores(int_indice);
            mediaPlayer.start();

        });

        imgbtn_anterior.setOnClickListener(v -> {

            int_indice--;

            if (int_indice < 0)
                int_indice = int_numero_canciones[0]-1;

            mediaPlayer.stop();

            cargaValores(int_indice);
            mediaPlayer.start();

        });

        return root;
    }

    public void cargaListaCanciones() {


        databaseReference.child("Usuario").child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String string_pago = snapshot.child("string_fecha_fin_suscripcion").getValue().toString();

                    if(!string_pago.equalsIgnoreCase("-1")){
                        int_numero_canciones[0] = INTS_CANCIONES.length;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        for (int e = 0; e < int_numero_canciones[0]; e++) {

            lista_audio_modelo.add(new AudioModelo(INTS_CANCIONES[e], STRINGS_NOMBRES_CANCION[e], INTS_IMAGENES[e], STRINGS_TIPO[e]));

        }

        adapter = new Adaptador(lista_audio_modelo, this);

        recyclerView.setAdapter(adapter);

    }

    public void cargaValores(int indice) {

        mediaPlayer = MediaPlayer.create(getContext(), INTS_CANCIONES[indice]);
        Toast.makeText(getContext(), "Espere un momento", Toast.LENGTH_LONG).show();
        double_tiempo_final = mediaPlayer.getDuration();

        double_tiempo_inicial = mediaPlayer.getCurrentPosition();

        imgbtn_reproducir.setEnabled(true);
        imgbtn_anterior.setEnabled(true);
        imgbtn_siguiente.setEnabled(true);

        imgbtn_reproducir.setImageResource(android.R.drawable.ic_media_pause);


        txt_titulo.setText(STRINGS_NOMBRES_CANCION[int_indice]);

        v_miniatura.setImageResource(INTS_IMAGENES[int_indice]);

        if (solo_una_vez == 0) {

            pb_progreso_cancion.setMax((int) double_tiempo_final);

            solo_una_vez = 1;

        }

        pb_progreso_cancion.setProgress((int) double_tiempo_inicial);


        handler.postDelayed(runnable_seekbar, 100);

    }

    @Override
    public void onDestroy() {

        super.onDestroy();

        mediaPlayer.release();

        mediaPlayer = null;

    }

    @Override
    public void onClick(int position) {

        int_indice = position;

        if (mediaPlayer.isPlaying()) {

            mediaPlayer.stop();

            cargaValores(position);

            mediaPlayer.start();

            imgbtn_reproducir.setImageResource(android.R.drawable.ic_media_pause);

        } else {

            cargaValores(position);

            mediaPlayer.start();
            imgbtn_reproducir.setImageResource(android.R.drawable.ic_media_pause);

        }

    }

    private final Runnable runnable_seekbar = new Runnable() {
        @SuppressLint("DefaultLocale")
        @Override
        public void run() {
            if (mediaPlayer != null) {
                double_tiempo_inicial = mediaPlayer.getCurrentPosition();

                txt_duracion.setText(String.format("%d:%d",
                        TimeUnit.MILLISECONDS.toMinutes((long) double_tiempo_inicial),
                        TimeUnit.MILLISECONDS.toSeconds((long) double_tiempo_inicial) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) double_tiempo_inicial))));

                pb_progreso_cancion.setProgress((int) double_tiempo_inicial);

                handler.postDelayed(this, 100);
            }


        }
    };

}
