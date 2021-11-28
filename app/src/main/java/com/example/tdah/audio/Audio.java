package com.example.tdah.audio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;

import com.example.tdah.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.SuccessContinuation;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;

public class Audio extends AppCompatActivity {

    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_audio);

    }

    public void reproducir(String url){

        try{

            mediaPlayer.setDataSource(url);

            mediaPlayer.setOnPreparedListener(MediaPlayer::start);

        }catch (IOException e){

            e.printStackTrace();

        }

    }

    public void listAllFiles() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        // [START storage_list_all]
        StorageReference listRef = storage.getReference().child("files/uid");

        listRef.listAll()
                .addOnSuccessListener(listResult -> {
                    for (StorageReference prefix : listResult.getPrefixes()) {
                        // All the prefixes under listRef.
                        // You may call listAll() recursively on them.

                    }
                    for (StorageReference item : listResult.getItems()) {
                        // All the items under listRef.

                    }
                })
                .addOnFailureListener(e -> {
                    // Uh-oh, an error occurred!
                });
        // [END storage_list_all]
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