package com.example.tdah.usuario.libros;

import static java.lang.String.*;

import androidx.fragment.app.Fragment;


import android.media.AudioManager;
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
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tdah.R;
import com.example.tdah.audio.Adaptador;
import com.example.tdah.audio.AudioModelo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;


public class LibrosFragment extends Fragment implements Adaptador.OnClickListener {

    private final ArrayList<AudioModelo> lista_audio_modelo = new ArrayList<>();

    private MediaPlayer mediaPlayer;

    private Adaptador adapter;

    private Handler handler = new Handler();

    private RecyclerView recyclerView;
    private TextView txt_titulo, txt_duracion;
    private ImageButton imgbtn_reproducir, imgbtn_anterior, imgbtn_siguiente;
    private ImageView v_miniatura;

    private ProgressBar progress_bar;
    private SeekBar seekBar;

    private MediaObserver observer = null;
    private MediaController mediaController;

    private static int solo_una_vez = 0;

    private int int_tiempo_final, int_tiempo_inicial, abc_audio = 0;

    private final int[] INTS_CANCIONES = {R.raw.caperucita_roja,
            R.raw.el_gato_con_botas,
            R.raw.la_bella_durmiente,
            R.raw.los_tres_cerditos,
            R.raw.pinocho};

    private final String[] STRINGS_CANCIONES = {"https://firebasestorage.googleapis.com/v0/b/aplicacion-tdah.appspot.com/o/Caperucita%20Roja.mp3?alt=media&token=15874027-7c71-4b52-8cdf-70b469f8bd4a",
            "https://firebasestorage.googleapis.com/v0/b/aplicacion-tdah.appspot.com/o/El%20Gato%20con%20Botas.mp3?alt=media&token=a3bbc748-9163-49bb-bd25-f970a6807170",
            "https://firebasestorage.googleapis.com/v0/b/aplicacion-tdah.appspot.com/o/La%20Bella%20Durmiente.mp3?alt=media&token=4d745267-6a33-4ae5-8d8b-b1bfba912ac5",
            "https://firebasestorage.googleapis.com/v0/b/aplicacion-tdah.appspot.com/o/Los%20Tres%20Cerditos.mp3?alt=media&token=3f0efdfe-6221-4c7c-85e5-9460b21b2540",
            "https://firebasestorage.googleapis.com/v0/b/aplicacion-tdah.appspot.com/o/Pinocho.mp3?alt=media&token=eff49ff0-a629-4df7-a596-df20874ee8b3"
    };

    private final int[] INTS_IMAGENES = {R.drawable.imagen_caperucita_roja,
            R.drawable.imagen_gato_botas,
            R.drawable.imagen_bella_durmiente,
            R.drawable.imagen_tres_cerditos,
            R.drawable.imagen_pinocho};

    private final String[] STRINGS_NOMBRES_CANCION = {"Caperucita roja",
            "El gato con botas",
            "La bella durmiente",
            "Los tres cerditos",
            "Pinocho"};

    private final String[] STRINGS_TIPO = {"Gratis",
            "Gratis",
            "Gratis",
            "Gratis",
            "Paga"};

    private LibrosViewModel librosViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        librosViewModel =
                new ViewModelProvider(this).get(LibrosViewModel.class);
        View root = inflater.inflate(R.layout.fragment_libros, container, false);

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

        seekBar = root.findViewById(R.id.seekBar);
        progress_bar = root.findViewById(R.id.progress_bar);

        cargaListaCanciones();

        mediaPlayer = new MediaPlayer();


        mediaPlayer.setOnBufferingUpdateListener((mp, percent) -> progress_bar.setSecondaryProgress(percent));

        imgbtn_reproducir.setOnClickListener(v -> {

            if (mediaPlayer.isPlaying()) {

                mediaPlayer.pause();

                imgbtn_reproducir.setImageResource(android.R.drawable.ic_media_play);

            } else {

                mediaPlayer.start();

                imgbtn_reproducir.setImageResource(android.R.drawable.ic_media_pause);

            }

        });

        mediaPlayer.setOnCompletionListener(mp -> {

            observer.stop();

            progress_bar.setProgress(mediaPlayer.getCurrentPosition());

            mediaPlayer.stop();

            mediaPlayer.reset();

            imgbtn_reproducir.setImageResource(android.R.drawable.ic_media_play);

        });

        imgbtn_siguiente.setOnClickListener(v -> {

            abc_audio++;

            if (abc_audio >= INTS_IMAGENES.length)
                abc_audio = 0;

            mediaPlayer.stop();

            reproducir(abc_audio);

        });

        imgbtn_anterior.setOnClickListener(v -> {

            abc_audio--;

            if (abc_audio < 0)
                abc_audio = INTS_IMAGENES.length - 1;

            mediaPlayer.stop();

            reproducir(abc_audio);

        });

        return root;
    }

    public void cargaListaCanciones() {


        for (int e = 0; e < INTS_IMAGENES.length; e++) {

            lista_audio_modelo.add(new AudioModelo(INTS_CANCIONES[e], STRINGS_NOMBRES_CANCION[e], INTS_IMAGENES[e], STRINGS_TIPO[e]));

        }

        adapter = new Adaptador(lista_audio_modelo, this);

        recyclerView.setAdapter(adapter);

    }


    public void reproducir(int indice) {

        MediaPlayer mediaPlayer = MediaPlayer.create(getContext(), INTS_CANCIONES[indice]);

        Toast.makeText(getContext(), "Espere un momento", Toast.LENGTH_LONG).show();

        mediaPlayer.setOnPreparedListener(mp -> {

            mp.start();

            imgbtn_reproducir.setEnabled(true);
            imgbtn_anterior.setEnabled(true);
            imgbtn_siguiente.setEnabled(true);

            imgbtn_reproducir.setImageResource(android.R.drawable.ic_media_pause);

        });

        int_tiempo_final = mediaPlayer.getDuration();

        int_tiempo_inicial = mediaPlayer.getCurrentPosition();

        txt_titulo.setText(STRINGS_NOMBRES_CANCION[indice]);

        v_miniatura.setImageResource(INTS_IMAGENES[indice]);

        if (solo_una_vez == 0) {

            seekBar.setMax(int_tiempo_final);

            solo_una_vez = 1;

        }

        seekBar.setProgress(int_tiempo_inicial);

        handler.postDelayed(actualiza_audio, 100);

        observer = new MediaObserver();

        new Thread(observer).start();

    }

    private final Runnable actualiza_audio = new Runnable() {
        @Override
        public void run() {

            int_tiempo_inicial = mediaPlayer.getCurrentPosition();

            txt_duracion.setText(format("%d:%d",
                    TimeUnit.MILLISECONDS.toMinutes(int_tiempo_inicial),
                    TimeUnit.MILLISECONDS.toSeconds(int_tiempo_inicial) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(int_tiempo_inicial))));

            seekBar.setProgress(int_tiempo_inicial);

            handler.postDelayed(this, 100);

        }
    };

    @Override
    public void onDestroy() {

        super.onDestroy();

        mediaPlayer.release();

        mediaPlayer = null;

    }

    @Override
    public void onClick(int position) {

        abc_audio = position;

        if (mediaPlayer.isPlaying()) {

            mediaPlayer.stop();

            reproducir(position);

            imgbtn_reproducir.setImageResource(android.R.drawable.ic_media_pause);

        } else {

            reproducir(position);

            imgbtn_reproducir.setImageResource(android.R.drawable.ic_media_pause);

        }

        adapter.notifyDataSetChanged();

    }

    private class MediaObserver implements Runnable {
        private AtomicBoolean stop = new AtomicBoolean(false);

        public void stop() {

            stop.set(true);

        }

        @Override
        public void run() {

            while (!stop.get()) {

                progress_bar.setProgress((int) ((double) mediaPlayer.getCurrentPosition() / (double) mediaPlayer.getDuration() * 100));

                try {

                    Thread.sleep(100);

                } catch (Exception ex) {

                    ex.printStackTrace();

                }
            }
        }
    }
}
