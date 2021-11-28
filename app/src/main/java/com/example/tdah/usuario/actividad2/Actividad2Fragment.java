package com.example.tdah.usuario.actividad2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.tdah.R;

public class Actividad2Fragment extends Fragment {

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

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Actividad2ViewModel =
                new ViewModelProvider(this).get(Actividad2ViewModel.class);
        View root = inflater.inflate(R.layout.fragment_actividad2, container, false);
        triangulo_nv1_a = (EditText) root.findViewById(R.id.triangulo_niv1);
        triangulo_nv2_a = (EditText) root.findViewById(R.id.triangulo_niv2_a);
        triangulo_nv2_b = (EditText) root.findViewById(R.id.triangulo_niv2_b);
        triangulo_nv3_a = (EditText) root.findViewById(R.id.triangulo_niv3_a);
        triangulo_nv3_b = (EditText) root.findViewById(R.id.triangulo_niv3_b);
        triangulo_nv3_c = (EditText) root.findViewById(R.id.triangulo_niv3_c);
        suma_l = (TextView) root.findViewById(R.id.triangulo_puntos_izq);
        suma_r = (TextView) root.findViewById(R.id.triangulo_puntos_der);
        suma_b = (TextView) root.findViewById(R.id.triangulo_puntos_bas);
        Actividad2ViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;
    }

    //Este método realiza la suma
    public void Sumar (View view) {
        String niv1_a = triangulo_nv1_a.getText ().toString ();
        String niv2_a = triangulo_nv2_a.getText ().toString ();
        String niv2_b = triangulo_nv2_b.getText ().toString ();
        String niv3_a = triangulo_nv3_a.getText ().toString ();
        String niv3_b = triangulo_nv3_b.getText ().toString ();
        String niv3_c = triangulo_nv3_c.getText ().toString ();

        int valor_niv1_a = Integer.parseInt (niv1_a);
        int valor_niv2_a = Integer.parseInt (niv2_a);
        int valor_niv2_b = Integer.parseInt (niv2_b);
        int valor_niv3_a = Integer.parseInt (niv3_a);
        int valor_niv3_b = Integer.parseInt (niv3_b);
        int valor_niv3_c = Integer.parseInt (niv3_c);

        //Suma Lado L del Triangulo:
        int sumaL = valor_niv3_a + valor_niv2_a + valor_niv1_a;

        //Suma Lado R del Triangulo:
        int sumaR = valor_niv3_c + valor_niv2_b + valor_niv1_a;

        //Suma Lado B del Triangulo:
        int sumaB = valor_niv3_a + valor_niv3_b + valor_niv3_c;

        if ((sumaL+sumaR+sumaB)==30){
            Toast.makeText(getActivity(),"Correcto. Lo lograste!",Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getActivity(),"Incorrecto. Intentalo de nuevo!",Toast.LENGTH_LONG).show();
        }
        String result_l = String.valueOf(sumaL);
        String result_r = String.valueOf(sumaR);
        String result_b = String.valueOf(sumaB);
        suma_l.setText(result_l);
        suma_r.setText(result_r);
        suma_b.setText(result_b);
        triangulo_nv1_a.setText(0);
        triangulo_nv2_a.setText(0);
        triangulo_nv2_b.setText(0);
        triangulo_nv3_a.setText(0);
        triangulo_nv3_b.setText(0);
        triangulo_nv3_c.setText(0);
    }
}