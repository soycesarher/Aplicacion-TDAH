package com.example.tdah.pdf;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tdah.R;
import com.example.tdah.modelos.UsuarioPsicologo;

import java.util.ArrayList;

public class AdaptadorPdf extends RecyclerView.Adapter<AdaptadorPdf.ViewHolder> {

    private ArrayList<UsuarioPsicologo> lista_psicologo_modelo;
    private final AdaptadorPdf.OnClickListener mOnClickListener;

    public interface OnClickListener{
        /**
         * Regresa la posición de la tarjeta
         * @param position posición de la tarjeta seleccionada
         */
        void onClick(int position);
    }

    public AdaptadorPdf(ArrayList<UsuarioPsicologo> lista_psicologo_modelo, AdaptadorPdf.OnClickListener onClickListener) {

        this.lista_psicologo_modelo = lista_psicologo_modelo;
        this.mOnClickListener = onClickListener;

    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txt_nombre_psicologo_layout;
        TextView txt_especialidad_psicologo_layout;
        TextView txt_direccion_psicologo_layout;

        public AdaptadorPdf.OnClickListener onClickListener;

        public ViewHolder(View view, AdaptadorPdf.OnClickListener onClickListener) {
            super(view);
            this.onClickListener = onClickListener;
            // Define click listener for the ViewHolder's View

            txt_nombre_psicologo_layout = view.findViewById(R.id.txt_nombre_psicologo_layout);
            txt_especialidad_psicologo_layout = view.findViewById(R.id.txt_especialidad_psicologo_layout);
            txt_direccion_psicologo_layout = view.findViewById(R.id.txt_direccion_psicologo_layout);

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
    public AdaptadorPdf.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_perfil_psicologo, viewGroup, false);

        return new AdaptadorPdf.ViewHolder(view, mOnClickListener);
    }

    /**
     * Este método es usuado para llenar los datos en el recyclerview
     * @param viewHolder vista que regresa el método onCreateViewHolder
     * @param position posicion en la lista del elemento UsuarioPsicologo
     */

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(AdaptadorPdf.ViewHolder viewHolder, final int position) {

        UsuarioPsicologo usuarioPsicologo = lista_psicologo_modelo.get(position);

        viewHolder.txt_direccion_psicologo_layout.setText(usuarioPsicologo.getString_direccion());

        viewHolder.txt_nombre_psicologo_layout.setText(usuarioPsicologo.getString_nombre()+" "
                +usuarioPsicologo.getString_apellido_paterno()+" "
                +usuarioPsicologo.getString_apellido_materno());

        viewHolder.txt_especialidad_psicologo_layout.setText(usuarioPsicologo.getString_especialidad());

    }

    /**
     * Regresa el tamanio de la lista_psicologo_modelo
     * @return tamanio de la lista_psicologo_modelo
     */
    @Override
    public int getItemCount() {
        return lista_psicologo_modelo.size();
    }

}
