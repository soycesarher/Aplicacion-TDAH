package com.example.tdah.usuario.libros;

import androidx.fragment.app.Fragment;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import com.example.tdah.audio.OnClickInterface;
import com.google.firebase.database.DatabaseReference;

import java.util.List;
import java.util.concurrent.TimeUnit;


public class LibrosFragment extends Fragment {

    private List<AudioModelo> lista_audio_modelo;

    private MediaPlayer mediaPlayer;

    private Handler handler;

    private OnClickInterface onClickInterface;

    private RecyclerView recyclerView;
    private TextView txt_titulo, txt_duracion;
    private ImageButton imgbtn_reproducir, imgbtn_anterior, imgbtn_siguiente;
    private ImageView v_miniatura;
    private SeekBar seekBar;

    private static int solo_una_vez = 0;

    private int int_tiempo_final, int_tiempo_inicial, abc_audio = 0;

    public final int[] INTS_CANCIONES = {R.raw.caperucita_roja,
            R.raw.el_gato_con_botas,
            R.raw.la_bella_durmiente,
            R.raw.los_tres_cerditos,
            R.raw.pinocho};

    public final int[] INTS_IMAGENES = {R.drawable.imagen_caperucita_roja,
            R.drawable.imagen_gato_botas,
            R.drawable.imagen_bella_durmiente,
            R.drawable.imagen_tres_cerditos,
            R.drawable.imagen_pinocho};

    public final String[] STRINGS_NOMBRES_CANCION = {"Caperucita roja",
            "El gato con botas",
            "La bella durmiente",
            "Los tres cerditos",
            "Pinocho"};

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

        carga();

        mediaPlayer = new MediaPlayer();

        onClickInterface = abc -> {

            if (mediaPlayer == null) {
                reproducir(abc);
            } else {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    reproducir(abc);
                }

            }

            abc_audio = abc;

        };

        imgbtn_reproducir.setOnClickListener(v -> {

            if (mediaPlayer == null) {
                reproducir(abc_audio);
            } else {

                if (mediaPlayer.isPlaying()) {

                    mediaPlayer.pause();

                    imgbtn_reproducir.setImageResource(android.R.drawable.ic_media_pause);

                } else {

                    mediaPlayer.start();

                    imgbtn_reproducir.setImageResource(android.R.drawable.ic_media_play);

                }


            }


        });

        imgbtn_siguiente.setOnClickListener(v -> {
            abc_audio++;
            if(abc_audio>= INTS_IMAGENES.length){
                abc_audio=0;
            }else {

                mediaPlayer.stop();
                reproducir(abc_audio);
            }

        });
        imgbtn_anterior.setOnClickListener(v -> {
            abc_audio--;
            if(abc_audio<0){
                abc_audio= INTS_IMAGENES.length-1;
            }else {

                mediaPlayer.stop();
                reproducir(abc_audio);
            }

        });


        return root;
    }

    public void carga() {

        AudioModelo audioModelo = new AudioModelo();


        for (int e = 0; e < INTS_IMAGENES.length; e++) {

            Toast.makeText(getContext(), "Nombre cancion: "+STRINGS_NOMBRES_CANCION[e], Toast.LENGTH_SHORT).show();

            lista_audio_modelo.add(new AudioModelo(INTS_CANCIONES[e],STRINGS_NOMBRES_CANCION[e],INTS_IMAGENES[e]));

        }

        Adaptador adapter = new Adaptador(getContext(), lista_audio_modelo, onClickInterface);

        recyclerView.setAdapter(adapter);

    }


    public void reproducir(int indice) {

        MediaPlayer mediaPlayer = MediaPlayer.create(getContext(), INTS_CANCIONES[indice]);
        mediaPlayer.start(); // no need to call prepare(); create() does that for you

        int_tiempo_final = mediaPlayer.getDuration();

        int_tiempo_inicial = mediaPlayer.getCurrentPosition();


        txt_titulo.setText(STRINGS_NOMBRES_CANCION[indice]);

        v_miniatura.setImageResource(INTS_IMAGENES[indice]);


        txt_duracion.setText(String.format("%d:%d",
                TimeUnit.MILLISECONDS.toMinutes(int_tiempo_final),
                TimeUnit.MILLISECONDS.toSeconds(int_tiempo_final) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(int_tiempo_final))));

        if (solo_una_vez == 0) {
            seekBar.setMax(int_tiempo_final);
            solo_una_vez = 1;
        }

        seekBar.setProgress(int_tiempo_inicial);
        handler.postDelayed(actualiza_audio, 100);

    }


    private final Runnable actualiza_audio = () -> {
        int_tiempo_inicial = mediaPlayer.getCurrentPosition();
        seekBar.setProgress(int_tiempo_inicial);
        handler.postDelayed((Runnable) this, 100);
    };


}
