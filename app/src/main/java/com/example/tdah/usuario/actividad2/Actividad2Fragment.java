package com.example.tdah.usuario.actividad2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.tdah.R;
import com.example.tdah.UsuarioPrincipal;

public class Actividad2Fragment extends Fragment implements View.OnClickListener {

    private Actividad2ViewModel Actividad2ViewModel;
    private EditText triangulo_nv1_a;
    private EditText triangulo_nv2_a;
    private EditText triangulo_nv2_b;
    private EditText triangulo_nv3_a;
    private EditText triangulo_nv3_b;
    private EditText triangulo_nv3_c;
    private TextView suma_l;
    private TextView suma_r;
    private TextView suma_b;
    private Button comprobar;

    UsuarioPrincipal main;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Actividad2ViewModel =
                new ViewModelProvider(this).get(Actividad2ViewModel.class);
        View root = inflater.inflate(R.layout.fragment_actividad2, container, false);
        Componentes(root);
        main = (UsuarioPrincipal) getParentFragment().getActivity();
        Actividad2ViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });
        return root;
    }

    private void Componentes(View root) {
        Botones(root);
        EditTextComponent(root);
        TextViewComponent(root);
    }

    private void EditTextComponent(View root) {
        triangulo_nv1_a = (EditText) root.findViewById(R.id.triangulo_niv1);
        triangulo_nv2_a = (EditText) root.findViewById(R.id.triangulo_niv2_a);
        triangulo_nv2_b = (EditText) root.findViewById(R.id.triangulo_niv2_b);
        triangulo_nv3_a = (EditText) root.findViewById(R.id.triangulo_niv3_a);
        triangulo_nv3_b = (EditText) root.findViewById(R.id.triangulo_niv3_b);
        triangulo_nv3_c = (EditText) root.findViewById(R.id.triangulo_niv3_c);
    }

    private void TextViewComponent(View root) {
        suma_l = (TextView) root.findViewById(R.id.triangulo_puntos_izq);
        suma_r = (TextView) root.findViewById(R.id.triangulo_puntos_der);
        suma_b = (TextView) root.findViewById(R.id.triangulo_puntos_bas);
    }

    private void Botones(View root) {
        comprobar = root.findViewById(R.id.btn_comprobar_triangulo);
        comprobar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        sumarTriangulo();
    }

    //Este método realiza la suma
    private void sumarTriangulo () {
        //Toast.makeText(main.getApplicationContext(), "En reparacion ...", Toast.LENGTH_SHORT).show();
        try{
            String niv1_a = triangulo_nv1_a.getText().toString();
            int valor_niv1_a = Integer.parseInt(niv1_a);

            String niv2_a = triangulo_nv2_a.getText().toString();
            int valor_niv2_a = Integer.parseInt(niv2_a);

            String niv2_b = triangulo_nv2_b.getText().toString();
            int valor_niv2_b = Integer.parseInt(niv2_b);

            String niv3_a = triangulo_nv3_a.getText().toString();
            int valor_niv3_a = Integer.parseInt(niv3_a);

            String niv3_b = triangulo_nv3_b.getText().toString();
            int valor_niv3_b = Integer.parseInt(niv3_b);

            String niv3_c = triangulo_nv3_c.getText().toString();
            int valor_niv3_c = Integer.parseInt(niv3_c);

            if (valor_niv1_a<7&&valor_niv2_a<7&&valor_niv2_b<7&&valor_niv3_a<7&&valor_niv3_b<7&&valor_niv3_c<7){
                if (valor_niv1_a>0&&valor_niv2_a>0&&valor_niv2_b>0&&valor_niv3_a>0&&valor_niv3_b>0&&valor_niv3_c>0) {

                    int suma_lado_izq = valor_niv3_a+valor_niv2_a+valor_niv1_a;
                    int suma_lado_der = valor_niv3_c+valor_niv2_b+valor_niv1_a;
                    int suma_lado_base = valor_niv3_a+valor_niv3_b+valor_niv3_c;

                    if(suma_lado_izq == 10){
                        Toast.makeText(main.getApplicationContext(), "El lado izquierdo es Correcto!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(main.getApplicationContext(), "Te equivocaste en el lado izquierdo!", Toast.LENGTH_SHORT).show();
                    }
                    if(suma_lado_der == 10){
                        Toast.makeText(main.getApplicationContext(), "El lado derecho es Correcto!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(main.getApplicationContext(), "Te equivocaste en el lado derecho!", Toast.LENGTH_SHORT).show();
                    }
                    if(suma_lado_base == 10){
                        Toast.makeText(main.getApplicationContext(), "El lado base es Correcto!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(main.getApplicationContext(), "Te equivocaste en el lado base!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(main.getApplicationContext(), "Solo debes ocupar números del 1 al 6", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(main.getApplicationContext(), "Solo debes ocupar números del 1 al 6", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(main.getApplicationContext(), "Primero debes llenar todo el triangulo", Toast.LENGTH_SHORT).show();
        }
    }
}