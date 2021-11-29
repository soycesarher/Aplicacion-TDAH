package com.example.tdah.audio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;


import com.example.tdah.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Audio extends AppCompatActivity {

    private List<AudioModelo> lista_audio_modelo;
    private MediaPlayer mediaPlayer;
    private RecyclerView recyclerView;

    private DatabaseReference databaseReference;
    private OnClickInterface onClickInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_audio);

        recyclerView = findViewById(R.id.rv_canciones);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        recupera_canciones();

        lista_audio_modelo = new ArrayList<>();
        onClickInterface = new OnClickInterface() {
            @Override
            public void setClick(int abc) {
                //aquí podemos hacer lo que queremos con el item list
                lista_audio_modelo.get(abc).getString_url_cancion();
            }
        };


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
                Adaptador adapter = new Adaptador(Audio.this,lista_audio_modelo);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void reproducir(String url){

        try{

            mediaPlayer.setDataSource(url);

            mediaPlayer.setOnPreparedListener(MediaPlayer::start);

        }catch (IOException e){

            e.printStackTrace();

        }

    }


    public  void pausa(String url) {

        if(mediaPlayer.isPlaying()) {

            try {

                mediaPlayer.setDataSource(url);
                mediaPlayer.setOnPreparedListener(MediaPlayer::pause);

            } catch (IOException e) {

                e.printStackTrace();

            }

        }

    }

}