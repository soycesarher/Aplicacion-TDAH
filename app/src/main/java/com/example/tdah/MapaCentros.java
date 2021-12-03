package com.example.tdah;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.tdah.databinding.ActivityMapaCentrosBinding;

public class MapaCentros extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapaCentrosBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapaCentrosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        Antut(googleMap);
    }
    public void Antut (GoogleMap googleMap){
        mMap = googleMap;
        //Camara de google maps a donde se va a dirigir
        final LatLng Toluca = new LatLng(19.2599261, -99.5902326);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Toluca));
        //Ubicaciones
        final LatLng Centro1 = new LatLng(19.2962041, -99.6455398);
        mMap.addMarker(new MarkerOptions().position(Centro1).title("Centro de Salud de Asistecncia Social Municipal de Toluca, San Pedro Totoltepec"));
        final LatLng Centro2 = new LatLng(19.2632041, -99.5306457);
        mMap.addMarker(new MarkerOptions().position(Centro2).title("DIF Sistema Municipal San Mateo Atenco"));
        final LatLng Centro3 = new LatLng(19.2694363, -99.6536227);
        mMap.addMarker(new MarkerOptions().position(Centro3).title("DiF"));
        final LatLng Centro4 = new LatLng(19.2694133, -99.5965584);
        mMap.addMarker(new MarkerOptions().position(Centro4).title("Sistema Municipal de Desarrollo Integral Para la Familia de Metepec Dif"));
        final LatLng Centro5 = new LatLng(19.2615882, -99.6806036);
        mMap.addMarker(new MarkerOptions().position(Centro5).title("DIF"));
        final LatLng Centro6 = new LatLng(19.2672085, -99.6639399);
        mMap.addMarker(new MarkerOptions().position(Centro6).title("Cree del DIF"));
        final LatLng Centro7 = new LatLng(19.25338, -99.6616744);
        mMap.addMarker(new MarkerOptions().position(Centro7).title("Dif del Estado de México"));
        final LatLng Centro8 = new LatLng(19.2652818, -99.6639857);
        mMap.addMarker(new MarkerOptions().position(Centro8).title("Dif Municipal Toluca"));
        final LatLng Centro9 = new LatLng(19.2769844, -99.6677679);
        mMap.addMarker(new MarkerOptions().position(Centro9).title("Sistema Municipal DIF de Toluca"));

    }
}