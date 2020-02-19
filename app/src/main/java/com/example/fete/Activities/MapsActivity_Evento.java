package com.example.fete.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import com.example.fete.Metodos.Metodos;
import com.example.fete.Metodos.Preferences;
import com.example.fete.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Locale;
import java.util.Objects;

public class MapsActivity_Evento extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
    LatLng lugar;
    String NombreArt;
    String Descripcion;
    LocationManager locationManager;
    LatLng MarkAquiEstoy;
    private Geocoder geocoder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps__evento);

        if(!Preferences.obtenerPreferenceBoolean( MapsActivity_Evento.this, Preferences.BUTTON_MENSAJE_CONFIG_MAPA))
            showInfo();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        Bundle intent = getIntent().getExtras();
        assert intent != null;
        lugar = new LatLng(intent.getDouble("Lat", 1), intent.getDouble("Long", 1));
        NombreArt = intent.getString("NombreArt");
        Descripcion = intent.getString("Descripcion");
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (Metodos.isGPSEnabled(getApplicationContext())) {
            Metodos.showInfoAlert(getApplicationContext(), "Señal GPS",
                    "La señal GPS está deshabilitada, ¿Quieres habilitarla ahora?");
        } else {
            mMap.setMinZoomPreference(10);
            mMap.setMaxZoomPreference(100);

            geocoder = new Geocoder(Objects.requireNonNull(getApplicationContext()), Locale.getDefault());
            locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 3000000, 0,this);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000000, 0, this);

            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);

            // Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            //MarkAquiEstoy = new LatLng(location.getLatitude(),location.getLongitude());
            //Marker marker1=mMap.addMarker(new MarkerOptions().position(MarkAquiEstoy).title("¡Aquí estoy!"));
            //marker1.showInfoWindow();

            //mMap.moveCamera(CameraUpdateFactory.newLatLng(lugar));


            Marker marker=mMap.addMarker(new MarkerOptions().position(lugar).title(NombreArt).draggable(false).snippet(Descripcion));
            marker.showInfoWindow();


            CameraPosition camera = new CameraPosition.Builder()
                    .target(lugar)
                    .zoom(13)
                    .bearing(0)
                    .tilt(10)
                    .build();
            //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(camera));
        }

    }

    private void showInfo(){
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(MapsActivity_Evento.this, R.style.AlertDialogTheme);
        builder.setTitle("Información");
        builder.setIcon(R.drawable.ic_info_black_24dp);
        builder.setNegativeButton("Aún no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) { }});

        builder.setMessage("Para acceder a la ruta desde tu ubicación, presiona el botón azul que se encuentra em la parte inferior derecha");
        builder.setPositiveButton("Entendido", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Preferences.savePreferenceBoolean(MapsActivity_Evento.this,true,Preferences.BUTTON_MENSAJE_CONFIG_MAPA);
            }});
        builder.show();
    }

}
