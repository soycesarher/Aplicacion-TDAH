package com.example.tdah.audio;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.example.tdah.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Adaptador extends RecyclerView.Adapter<Adaptador.ViewHolder> {

    private String[] localDataSet;
private Context contexto;
    private ListView lv_canciones;
    private DatabaseReference databaseReference;
    private List<AudioModelo> lista_audio_modelo;
    private MediaPlayer mediaPlayer;



    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_titulo, txt_duracion;
        ImageView v_miniatura_layout;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            txt_titulo = view.findViewById(R.id.txt_titulo);
            txt_duracion = view.findViewById(R.id.txt_duracion);
            v_miniatura_layout = view.findViewById(R.id.v_miniatura_layout);

        }


    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    public Adaptador(String[] dataSet) {
        localDataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(contexto)
                .inflate(R.layout.layout_cancion, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        AudioModelo audioModelo = lista_audio_modelo.get(position);
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        Picasso.get().load(audioModelo.getString_url_imagen()).fit().into(viewHolder.v_miniatura_layout);
        viewHolder.txt_titulo.setText(audioModelo.getString_nombre_cancion());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return lista_audio_modelo.size();
    }



}
