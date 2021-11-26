package com.example.tdah.usuario;

import android.app.Dialog;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.PatternMatcher;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tdah.R;

import java.util.Random;

public class Escenario extends AppCompatActivity {

    TextView txtContador, txtTiempo, Anchotv, Altotv;
    ImageView imgMoneda;

    int AnchoPantallla;
    int AltoPantalla;

    Random aleatorio;
    boolean GameOver = false;
    Dialog miDialog;


    int contador=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escenario);
        imgMoneda = findViewById(R.id.imgMoneda);

        txtContador = findViewById(R.id.txtContador);
        txtTiempo = findViewById(R.id.txtTiempo);

        miDialog = new Dialog(Escenario.this);


        Anchotv = findViewById(R.id.Anchotv);
        Altotv = findViewById(R.id.Altotv);

        Pantalla();
        CuentaAtras();

        //AL HACER CLIC EN LA IMAGEN
        imgMoneda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!GameOver) {
                    contador++;
                    txtContador.setText(String.valueOf(contador));
                    imgMoneda.setImageResource(R.drawable.monedas);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            imgMoneda.setImageResource(R.drawable.moneda);
                            Movimiento();
                        }
                    }, 500);
                }
            }
        });
    }

    // METODO PARA OBTENER EL TAMAÑO DE LA PANTALLA
    private void Pantalla(){
        Display display = getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        AnchoPantallla = point.x;
        AltoPantalla = point.y;

        String ANCHO = String.valueOf(AnchoPantallla);
        String ALTO = String.valueOf(AltoPantalla);

        Anchotv.setText(ANCHO);
        Altotv.setText(ALTO);

        aleatorio = new Random();
    }

    private void Movimiento(){
        int min = 0;
        int MaximoX = AnchoPantallla - imgMoneda.getWidth();
        int MaximoY = AltoPantalla - imgMoneda.getHeight();

        int randomX = aleatorio.nextInt((MaximoX - min)+1)+min;
        int randomY = aleatorio.nextInt((MaximoY - min)+1)+min;

        imgMoneda.setX(randomX);
        imgMoneda.setY(randomY);
    }

    private void CuentaAtras(){
        new CountDownTimer(30000, 1000){
            public void onTick(long millisUntilFinished){
                long segundosRestantes = millisUntilFinished/1000;
                txtTiempo.setText(segundosRestantes+"S");

            }

            @Override
            public void onFinish() {
                txtTiempo.setText("0S");
                GameOver = true;
                MensajeGameOver();
            }
        }.start();
    }

    public void MensajeGameOver() {
        String ubicacion = "fuentes/letra.TTF";
        Typeface typeface = Typeface.createFromAsset(Escenario.this.getAssets(), ubicacion);
        TextView SeAcaboTxt, RecolectadoTxt, NumeroTxt;
        Button Jugardenuevo;
        miDialog.setContentView(R.layout.gameover);

        SeAcaboTxt = miDialog.findViewById(R.id.SeAcaboTxt);
        RecolectadoTxt = miDialog.findViewById(R.id.RecolectadoTxt);
        NumeroTxt = miDialog.findViewById(R.id.NumeroTxt);

        Jugardenuevo = miDialog.findViewById(R.id.Jugardenuevo);

        String moneda = String.valueOf(contador);
        NumeroTxt.setText(moneda);

        SeAcaboTxt.setTypeface(typeface);
        RecolectadoTxt.setTypeface(typeface);
        NumeroTxt.setTypeface(typeface);

        Jugardenuevo.setTypeface(typeface);

        Jugardenuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contador = 0;
                miDialog.dismiss();
                txtContador.setText("0");
                GameOver = false;
                CuentaAtras();
                Movimiento();
            }
        });

        miDialog.show();
    }
}
