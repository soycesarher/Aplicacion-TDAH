package com.example.tdah.usuario.libros;

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
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tdah.R;
import com.example.tdah.audio.Adaptador;
import com.example.tdah.audio.Audio;
import com.example.tdah.audio.AudioModelo;
import com.example.tdah.audio.OnClickInterface;
import com.example.tdah.usuario.cuenta.CuentaViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


public class LibrosFragment extends Fragment {


    private MediaPlayer mediaPlayer;

    private Handler handler;

    private DatabaseReference databaseReference;

    private OnClickInterface onClickInterface;

    private RecyclerView recyclerView;
    private TextView txt_titulo, txt_duracion;
    private ImageButton imgbtn_reproducir, imgbtn_anterior, imgbtn_siguiente;
    private ImageView v_miniatura;
    private SeekBar seekBar;

    private static int solo_una_vez = 0;

    private int int_tiempo_final, int_tiempo_inicial, abc_audio = 0;

    private int[] ints_canciones = {R.raw.Caperucita_Roja,
            R.raw.El_Gato_con_Botas,
            R.raw.La_Bella_Durmiente,
            R.raw.Los_Tres_Cerditos,
            R.raw.Pinocho};

    private int[] ints_imagenes = {R.drawable.imagen_caperucita_roja,
            R.drawable.imagen_gato_botas,
            R.drawable.imagen_bella_durmiente,
            R.drawable.imagen_tres_cerditos,
            R.drawable.imagen_pinocho};

    private String[] string_nombre_cancion = {"Caperucita roja",
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

        mediaPlayer = new MediaPlayer();

        onClickInterface = abc -> {

            if(mediaPlayer == null){
                reproducir(abc);
            }else{
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
            }

            abc_audio = abc;

        };

        imgbtn_reproducir.setOnClickListener(v -> {

            if(mediaPlayer == null){
                reproducir(abc_audio);
            }else{

                if (mediaPlayer.isPlaying()) {

                    mediaPlayer.pause();

                    imgbtn_reproducir.setImageResource(android.R.drawable.ic_media_pause);

                } else {

                    mediaPlayer.start();

                    imgbtn_reproducir.setImageResource(android.R.drawable.ic_media_play);

                }


            }


        });


        return root;
    }



    public void reproducir(int indice) {

        MediaPlayer mediaPlayer = MediaPlayer.create(getContext(),ints_canciones[indice]);
        mediaPlayer.start(); // no need to call prepare(); create() does that for you

                int_tiempo_final = mediaPlayer.getDuration();

                int_tiempo_inicial = mediaPlayer.getCurrentPosition();




            imgbtn_reproducir.setEnabled(true);




        txt_titulo.setText(string_nombre_cancion[indice]);

        v_miniatura.setImageResource(ints_imagenes[indice]);


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
