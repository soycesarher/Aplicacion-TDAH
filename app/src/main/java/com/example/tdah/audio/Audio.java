package com.example.tdah.audio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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


import com.example.tdah.R;
import com.example.tdah.usuario.libros.LibrosViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Audio extends AppCompatActivity {

/*

        private List<AudioModelo> lista_audio_modelo;
        private MediaPlayer mediaPlayer;

        private Handler handler;

        private DatabaseReference databaseReference;

        private OnClickInterface onClickInterface;

        private RecyclerView recyclerView;
        private TextView txt_titulo, txt_duracion;
        private ImageButton imgbtn_reproducir, imgbtn_anterior, imgbtn_siguiente;
        private ImageView v_miniatura;
        private SeekBar pb_progreso_cancion;

        private static int solo_una_vez = 0;
        private int int_tiempo_final, int_tiempo_inicial, abc_audio = 0;


        private LibrosViewModel librosViewModel;

        public View onCreateView(@NonNull LayoutInflater inflater,
                                 ViewGroup container, Bundle savedInstanceState) {
            librosViewModel =
                    new ViewModelProvider(this).get(LibrosViewModel.class);
            View root = inflater.inflate(R.layout.fragment_libros, container, false);
            recyclerView = root.findViewById(R.id.rv_canciones);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(Audio.this));
            txt_titulo = root.findViewById(R.id.txt_titulo_r);
            txt_duracion = root.findViewById(R.id.txt_duracion_r);
            v_miniatura = root.findViewById(R.id.v_miniatura);
            imgbtn_reproducir = root.findViewById(R.id.btn_reproducir);
            databaseReference = FirebaseDatabase.getInstance().getReference();
            mediaPlayer = new MediaPlayer();
            lista_audio_modelo = new ArrayList<>();
            recupera_canciones();

//        mediaPlayer = MediaPlayer.create(getContext());

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

        public void recupera_canciones() {
            databaseReference.child("Canciones").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {


                    if (snapshot.exists()) {

                        int int_id_cancion = 1;

                        for (DataSnapshot f_snapshot : snapshot.getChildren()) {

                            String string_nombre_cancion = f_snapshot.child("string_nombre_cancion").getValue(String.class);
                            String string_url_imagen = f_snapshot.child("string_url_imagen").getValue(String.class);
                            String string_url_cancion = f_snapshot.child("string_url_cancion").getValue(String.class);

                            Toast.makeText(Audio.this, "Nombre cancion: "+string_nombre_cancion+" indice: "+ int_id_cancion, Toast.LENGTH_LONG).show();

                            AudioModelo audioModelo = new AudioModelo(string_nombre_cancion,string_url_imagen,string_url_cancion);
                            lista_audio_modelo.add(audioModelo);

                            int_id_cancion++;

                        }

                        Adaptador adapter = new Adaptador(Audio.this, lista_audio_modelo, onClickInterface);

                        recyclerView.setAdapter(adapter);

                    } else {

                        Toast.makeText(Audio.this, "No se recuperaron datos", Toast.LENGTH_SHORT).show();

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                    Toast.makeText(Audio.this, "No se recuperaron datos, error base de datos", Toast.LENGTH_SHORT).show();

                }

            });

        }


        public void reproducir(int indice) {

            try {

                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.setDataSource(lista_audio_modelo.get(indice).getString_url_cancion());
                Toast.makeText(Audio.this,"Url: "+lista_audio_modelo.get(indice).getString_url_cancion(),Toast.LENGTH_LONG).show();

                mediaPlayer.prepare(); // might take long! (for buffering, etc)

                mediaPlayer.setOnPreparedListener(mp -> {

                    int_tiempo_final = mediaPlayer.getDuration();

                    int_tiempo_inicial = mediaPlayer.getCurrentPosition();

                });

                mediaPlayer.start();

                imgbtn_reproducir.setEnabled(true);


            } catch (IOException e) {

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
                    .error(android.R.drawable.ic_menu_gallery)
                    .into(v_miniatura);

            txt_duracion.setText(String.format("%d:%d",
                    TimeUnit.MILLISECONDS.toMinutes(int_tiempo_final),
                    TimeUnit.MILLISECONDS.toSeconds(int_tiempo_final) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(int_tiempo_final))));

            if (solo_una_vez == 0) {
                pb_progreso_cancion.setMax(int_tiempo_final);
                solo_una_vez = 1;
            }

            pb_progreso_cancion.setProgress(int_tiempo_inicial);
            handler.postDelayed(actualiza_audio, 100);

        }


        private final Runnable actualiza_audio = () -> {
            int_tiempo_inicial = mediaPlayer.getCurrentPosition();
            pb_progreso_cancion.setProgress(int_tiempo_inicial);
            handler.postDelayed((Runnable) this, 100);
        };
*/



    }