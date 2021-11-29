package com.example.tdah.audio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;


import com.example.tdah.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Audio extends AppCompatActivity {

    private List<AudioModelo> lista_audio_modelo;
    private MediaPlayer mediaPlayer;

    private Handler handler;

    private DatabaseReference databaseReference;

    private OnClickInterface onClickInterface;

    private RecyclerView recyclerView;
    private TextView txt_titulo, txt_duracion;
    private ImageButton imgbtn_reproducir,imgbtn_anterior,imgbtn_siguiente;
    private ImageView v_miniatura;
    private SeekBar seekBar;

    private static int solo_una_vez = 0;
    private int int_tiempo_final,int_tiempo_inicial,abc_audio=0;


    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_audio);

        recyclerView = findViewById(R.id.rv_canciones);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        txt_titulo = findViewById(R.id.txt_titulo_r);
        txt_duracion = findViewById(R.id.txt_duracion_r);
        v_miniatura = findViewById(R.id.v_miniatura);

        recupera_canciones();

        mediaPlayer = MediaPlayer.create(this,abc_audio);

        onClickInterface = abc -> {
            if(mediaPlayer.isPlaying()){
                mediaPlayer.stop();
            }

            abc_audio = abc;

            reproducir(abc);



        };

        imgbtn_reproducir.setOnClickListener(v -> {
            if(mediaPlayer.isPlaying()){
                mediaPlayer.pause();
                imgbtn_reproducir.setImageResource(android.R.drawable.ic_media_pause);
            }else{
                mediaPlayer.start();
                imgbtn_reproducir.setImageResource(android.R.drawable.ic_media_play);
            }

        });


    }

    public void recupera_canciones(){
        databaseReference.child("Canciones").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                lista_audio_modelo.clear();
                int int_id_cancion=1;

                for (DataSnapshot f_snapshot: snapshot.getChildren()) {
                    String string_nombre_cancion = f_snapshot.child(String.valueOf(int_id_cancion)).child("string_nombre_cancion").getValue().toString();
                    String string_url_imagen = f_snapshot.child(String.valueOf(int_id_cancion)).child("string_url_imagen").getValue().toString();
                    String string_url_cancion = f_snapshot.child(String.valueOf(int_id_cancion)).child("string_url_cancion").getValue().toString();
                    AudioModelo audioModelo = new AudioModelo(string_nombre_cancion,string_url_cancion,string_url_imagen);
                    lista_audio_modelo.add(audioModelo);

                    int_id_cancion++;
                }
                Adaptador adapter = new Adaptador(Audio.this,lista_audio_modelo,onClickInterface);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    public void reproducir(int indice){

        try{

            mediaPlayer.setDataSource(lista_audio_modelo.get(indice).getString_url_cancion());

            mediaPlayer.setOnPreparedListener(MediaPlayer::start);

        }catch (IOException e){

            e.printStackTrace();

        }

        txt_titulo.setText(lista_audio_modelo
                .get(indice)
                .getString_nombre_cancion());

        Picasso.get()
                .load(lista_audio_modelo
                        .get(indice)
                        .getString_url_imagen())
                .fit()
                .into(v_miniatura);

        int_tiempo_final = mediaPlayer.getDuration() ;

        int_tiempo_inicial = mediaPlayer.getCurrentPosition();

        txt_duracion.setText(String.format("%d:%d" ,
                TimeUnit.MILLISECONDS.toMinutes(int_tiempo_final),
                TimeUnit.MILLISECONDS.toSeconds(int_tiempo_final)-
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(int_tiempo_final))));

        if(solo_una_vez == 0){
            seekBar.setMax(int_tiempo_final);
            solo_una_vez = 1;
        }
        seekBar.setProgress(int_tiempo_inicial);
        handler.postDelayed(actualiza_audio,100);

    }


    private Runnable actualiza_audio = () -> {
        int_tiempo_inicial = mediaPlayer.getCurrentPosition();
        seekBar.setProgress(int_tiempo_inicial);
        handler.postDelayed((Runnable) this,100);
    };


}