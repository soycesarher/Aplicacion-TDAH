package com.example.tdah.audio;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;

import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tdah.R;

import java.util.ArrayList;


public class Adaptador extends RecyclerView.Adapter<Adaptador.ViewHolder>{



    private ArrayList<AudioModelo> lista_audio_modelo;
    private OnClickListener mOnClickListener;


    public interface OnClickListener{
        /**
         * Regresa la posición de la tarjeta
         * @param position posición de la tarjeta seleccionada
         */
        void onClick(int position);
    }

    public Adaptador( ArrayList<AudioModelo> lista_audio_modelo, OnClickListener onClickListener) {

        this.lista_audio_modelo = lista_audio_modelo;
        this.mOnClickListener = onClickListener;

    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView txt_titulo, txt_tipo;
        public ImageView v_miniatura_layout;
        public OnClickListener onClickListener;

        public ViewHolder(View view, OnClickListener onClickListener) {
            super(view);
            this.onClickListener = onClickListener;
            // Define click listener for the ViewHolder's View
            txt_titulo = view.findViewById(R.id.txt_titulo);
            txt_tipo = view.findViewById(R.id.txt_tipo);
            v_miniatura_layout = view.findViewById(R.id.v_miniatura_layout);

            view.setClickable(true);
            view.setOnClickListener(this);

        }
                @Override
        public void onClick(View v) {
            onClickListener.onClick(getAdapterPosition());
        }
    }


    /**
     * Crea una nueva vista y tipo de vista
     * @param viewGroup vista que contiene las demas vistas
     * @param viewType entero que sirve para saber el tipo de vista
     * @return un objeto ViewHolder
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_cancion, viewGroup, false);

        return new ViewHolder(view, mOnClickListener);
    }

    /**
     * Este método es usuado para llenar los datos en el recyclerview
     * @param viewHolder vista que regresa el método onCreateViewHolder
     * @param position posicion en la lista del elemento AudioModelo
     */

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        AudioModelo audioModelo = lista_audio_modelo.get(position);

        viewHolder.v_miniatura_layout.setImageResource(audioModelo.getInt_imagen());

        viewHolder.txt_titulo.setText(audioModelo.getString_nombre_cancion());

        viewHolder.txt_tipo.setText(audioModelo.getString_tipo());


    }

    /**
     * Regresa el tamanio de la lista_audio_modelo
     * @return tamanio de la lista_audio_modelo
     */
    @Override
    public int getItemCount() {
        return lista_audio_modelo.size();
    }

}
