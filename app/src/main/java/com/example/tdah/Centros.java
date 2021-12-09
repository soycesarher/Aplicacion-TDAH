package com.example.tdah;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Centros extends Fragment implements OnMapReadyCallback {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    GoogleMap map;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Centros() {
        // Required empty public constructor
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_centros, container, false);
        // Inflate the layout for this fragment
        return v;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.map1);
        Log.e("aaa","SI ENTRO");
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map= googleMap;
        System.out.println("Entre aqui");
        Antut(googleMap);
    }
    public void Antut (GoogleMap googleMap){
        final LatLng Toluca = new LatLng(19.2599261, -99.5902326);
        map.moveCamera(CameraUpdateFactory.newLatLng(Toluca));
        map = googleMap;
        final LatLng Centro1 = new LatLng(19.2962041, -99.6455398);
        map.addMarker(new MarkerOptions().position(Centro1).title("Centro de Salud de Asistecncia Social Municipal de Toluca, San Pedro Totoltepec"));
        final LatLng Centro2 = new LatLng(19.2632041, -99.5306457);
        map.addMarker(new MarkerOptions().position(Centro2).title("DIF Sistema Municipal San Mateo Atenco"));
        final LatLng Centro3 = new LatLng(19.2694363, -99.6536227);
        map.addMarker(new MarkerOptions().position(Centro3).title("DiF"));
        final LatLng Centro4 = new LatLng(19.2694133, -99.5965584);
        map.addMarker(new MarkerOptions().position(Centro4).title("Sistema Municipal de Desarrollo Integral Para la Familia de Metepec Dif"));
        final LatLng Centro5 = new LatLng(19.2615882, -99.6806036);
        map.addMarker(new MarkerOptions().position(Centro5).title("DIF"));
        final LatLng Centro6 = new LatLng(19.2672085, -99.6639399);
        map.addMarker(new MarkerOptions().position(Centro6).title("Cree del DIF"));
        final LatLng Centro7 = new LatLng(19.25338, -99.6616744);
        map.addMarker(new MarkerOptions().position(Centro7).title("Dif del Estado de México"));
        final LatLng Centro8 = new LatLng(19.2652818, -99.6639857);
        map.addMarker(new MarkerOptions().position(Centro8).title("Dif Municipal Toluca"));
        final LatLng Centro9 = new LatLng(19.2769844, -99.6677679);
        map.addMarker(new MarkerOptions().position(Centro9).title("Sistema Municipal DIF de Toluca"));

    }


}