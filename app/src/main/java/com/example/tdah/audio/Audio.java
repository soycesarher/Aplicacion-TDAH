package com.example.tdah.audio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.media.MediaPlayer;
import android.os.Bundle;

import com.example.tdah.R;
import com.google.firebase.storage.FirebaseStorage;

import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Audio extends AppCompatActivity {

    private List<AudioModelo> lista_audio_modelo;
    private MediaPlayer mediaPlayer;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_audio);

        recyclerView = findViewById(R.id.rv_canciones);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        lista_audio_modelo = new ArrayList<>();



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