package com.example.fete.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.fete.Fragments.MapFragment;
import com.example.fete.Fragments.Perfil;
import com.example.fete.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import static androidx.navigation.Navigation.findNavController;

public class MainActivity extends AppCompatActivity implements MapFragment.OnFragmentInteractionListener, Perfil.OnFragmentInteractionListener, EditarPerfil.OnFragmentInteractionListener{
//implements MapFragment.OnFragmentInteractionListener, OnMapReadyCallback

    private GoogleMap mMap;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

                BottomNavigationView navView = findViewById(R.id.nav_view);
                // Passing each menu ID as a set of Ids because each
                // menu should be considered as top level destinations.
                AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                        R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                        .build();

                NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
                NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
                NavigationUI.setupWithNavController(navView, navController);



                navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        FragmentManager frMan = getSupportFragmentManager();
                        switch (item.getItemId()) {
                            case R.id.navigation_notifications:
                                frMan.beginTransaction().replace(R.id.nav_host_fragment, new Perfil()).commit();
                                break;
                            case R.id.navigation_home:
                                frMan.beginTransaction().replace(R.id.nav_host_fragment, new MapFragment()).commit();
                                break;
                            case R.id.navigation_dashboard:
                                Intent in = new Intent(getApplicationContext(), EventosActivity.class);
                                in.putExtra("location", Double.valueOf(1.1));
                                in.putExtra("location2", Double.valueOf(1.1));
                                startActivity(in);
                                break;
                        }
                        return false;
                    }
                });

                // SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                //       .findFragmentById(R.id.);
                //mapFragment.getMapAsync( this);
                FragmentManager frMan = getSupportFragmentManager();
                frMan.beginTransaction().replace(R.id.nav_host_fragment, new MapFragment()).commit();
    }

    @Override
   public void onFragmentInteraction(Uri uri) { }

  /*  @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }*/


}
