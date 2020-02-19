package com.example.fete.Fragments;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.fete.Activities.EventosActivity;
import com.example.fete.Metodos.Metodos;
import com.example.fete.R;
import com.example.fete.Tipos.Markers;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static java.util.Objects.requireNonNull;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MapFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment  implements OnMapReadyCallback, LocationListener, View.OnClickListener{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private View rootView;
    private GoogleMap gMap;
    private MapView mapView;
    private List<Address> adresss;
    private Geocoder geocoder;
    private MarkerOptions marker;
    private Marker marker2;
    private LocationManager locationManager;
    private FloatingActionButton fab;
    private FloatingActionButton fabAqui;
    private LatLng LocationGeneral;
    private CameraPosition camera;

    private OnFragmentInteractionListener mListener;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    public MapFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static MapFragment newInstance(String param1, String param2) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView=inflater.inflate(R.layout.fragment_map, container, false);

        if (Metodos.isGPSEnabled(requireNonNull(getActivity()).getApplicationContext()))
            showInfoAlert();

        fab = rootView.findViewById(R.id.fab);
        fabAqui = rootView.findViewById(R.id.fabAqui);
        fab.setOnClickListener(this);
        fabAqui.setOnClickListener(this);

        Button btnConf = rootView.findViewById(R.id.btnVerLita);
        btnConf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                Intent in = new Intent(getContext(), EventosActivity.class);
                in.putExtra("location", Double.valueOf(LocationGeneral.latitude));
                in.putExtra("location2", Double.valueOf(LocationGeneral.longitude));
                in.putExtra("direccion", adresss.get(0).getAddressLine(0).toString());
                startActivity(in);
            }
        });
        // showInfoAlertInternet();


        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapView = rootView.findViewById(R.id.map1);
        if (mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    private boolean isLocationEnabled() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private boolean isGPSEnabled() {
        try {
            int gpsSignal = Settings.Secure.getInt(requireNonNull(getActivity()).getContentResolver(), Settings.Secure.LOCATION_MODE);
            return gpsSignal == 0;
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            return true;
        }
    }

    private void showInfoAlert() {
        new AlertDialog.Builder(requireNonNull(getContext()).getApplicationContext()).setTitle("Señal GPS")
                .setMessage("La señal GPS está deshabilitada, ¿Quieres habilitarla ahora?")
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void createOrUpdateMarkerByLocation(Location location) {
        if (marker == null) {
            marker = new MarkerOptions();
            marker.position(new LatLng(location.getLatitude(), location.getLongitude()));
            marker.title("¡Aquí estoy!");
            marker.draggable(false);
            //marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_person_black_24dp));
            marker.snippet("Dirección");
            gMap.addMarker(marker);
            CreateUpdateMarkers();
            //marker = gMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).draggable(true));
        } else {

            //marker.position(new LatLng(location.getLatitude(), location.getLongitude()));
            gMap.clear();
            marker = new MarkerOptions();
            marker.position(new LatLng(location.getLatitude(), location.getLongitude()));
            marker.title("¡Aquí estoy!");
            marker.draggable(false);
            //marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_person_black_24dp));
            //marker.snippet("Dirección");
            gMap.addMarker(marker);
            CreateUpdateMarkers();

        }
        //marker.setPosition(new LatLng(location.getLatitude(), location.getLongitude()));
    }

    private void CreateUpdateMarkers() {
        List<Markers> _Lista_Markers = getAllDates();

        BitmapDrawable bitmapdraw = (BitmapDrawable) ResourcesCompat.getDrawable(getResources(), R.drawable.mark, null);
        assert bitmapdraw != null;
        Bitmap b = bitmapdraw.getBitmap();
        Bitmap smallMarker = Bitmap.createScaledBitmap(b, 84, 84, false);

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        LatLng _cooMarker ;
        for (int i = 0; i < _Lista_Markers.size(); i++) {
             _cooMarker = new LatLng(_Lista_Markers.get(i).get_latitud(), _Lista_Markers.get(i).get_longitud());
            try {
                adresss = geocoder.getFromLocation(_Lista_Markers.get(i).get_latitud(), _Lista_Markers.get(i).get_longitud(), 1);
            } catch (IOException e) { e.printStackTrace(); }
            //ltn=new LatLng(_Lista_Markers.get(i).get_latitud(), _Lista_Markers.get(i).get_longitud());
            builder.include(_cooMarker);//Para crear un promedio de todos los marcadores
            gMap.addMarker(new MarkerOptions()
                    .title(_Lista_Markers.get(i).get_nombre_evento())
                    .snippet(_Lista_Markers.get(i).getUbicacion())
                    .icon(BitmapDescriptorFactory.fromBitmap(smallMarker))
                    .position(_cooMarker));
        }

        LatLngBounds bounds = builder.build();
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        int padding = (int) (width * 0.20);
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height,padding);
        gMap.animateCamera(cu);
        gMap.moveCamera(cu);


    }

    private List<Markers> getAllDates() {

        //int id_marker, String ubicacion, String _nombre_evento, float _latitud, float _longitud
        return new ArrayList<Markers>() {{
            add(new Markers(1, "Juan escutia 19 fraccionamiento el paraiso", "Evento 1", 22.769381, -102.571888));
            add(new Markers(2, "Pedro de la O 2 Col las Campanas", "Evento 2 largo", 22.769974, -102.575729));
            add(new Markers(3, "Juan escutia 19 fraccionamiento el paraiso", "Evento 3 con algo", 22.775999, -102.572156));
            add(new Markers(4, "Juan escutia 19 fraccionamiento el paraiso", "Evento 4 que es mas largo", 22.775050, -102.573079));
            add(new Markers(5, "Juan escutia 19 fraccionamiento el paraiso", "Evento 5 que es mucho mas largo", 22.773877, -102.574200));
            add(new Markers(6, "Juan escutia 19 fraccionamiento ", "Evento 6 que es mas mucho pero mas largo que todos los demas", 22.774243, -102.573905));
        }};
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
//Aqui boton FAB
        if(v.getId()==R.id.fab) {
            if (isGPSEnabled()) {
                showInfoAlert();
            } else {
                if (ActivityCompat.checkSelfPermission(requireNonNull(getContext()).getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (location == null)
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                //LocationGeneral=(Lalocation;
                if (location != null) {
                    createOrUpdateMarkerByLocation(location);
                    // zoomToLocation(location);
                    CreateUpdateMarkers();
                }
            }
        }
        else if(v.getId()==R.id.fabAqui){
            LatLng miPosicion = new LatLng(locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLatitude(), locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLongitude());
            moveCameraTo(new LatLng(miPosicion.latitude,miPosicion.longitude));
        }
    }

    private void zoomToLocation(Location location){
        camera = new CameraPosition.Builder()
                .target(new LatLng(location.getLatitude(), location.getLongitude()))
                .zoom(18)
                .bearing(0)
                .tilt(15)
                .build();
        gMap.animateCamera(CameraUpdateFactory.newCameraPosition(camera));

    }

    public static boolean compruebaConexion(Context context) {

        boolean connected = false;
        ConnectivityManager connec = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        // Recupera todas las redes (tanto móviles como wifi)
        assert connec != null;
        NetworkInfo[] redes = connec.getAllNetworkInfo();

        for (NetworkInfo rede : redes) {
            // Si alguna red tiene conexión, se devuelve true
            if (rede.getState() == NetworkInfo.State.CONNECTED) {
                connected = true;
            }
        }
        return connected;
    }

    private void showInfoAlertInternet() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext()).setTitle("Señal Internet");

        builder.setMessage("La señal de Internet está deshabilitada, ¿Quieres habilitarla ahora?");
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("No", null);
        AlertDialog alertDialog =  builder.create();
        //builder.show();
        alertDialog.show();
    }

    @Override
    public void onLocationChanged(Location location) {
        //gMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(),location.getLongitude())).draggable(true));
        if (marker2 == null) {
            //marker2= gMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(),location.getLongitude())).draggable(true));
        }
        //else
        //  marker2.setPosition(new LatLng(location.getLatitude(),location.getLongitude()));
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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        gMap.setMinZoomPreference(10);
        gMap.setMaxZoomPreference(20);

        if (isAdded())
            geocoder = new Geocoder(requireNonNull(getActivity()).getApplicationContext(), Locale.getDefault());

        if (ActivityCompat.checkSelfPermission(requireNonNull(getContext()), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        gMap.setMyLocationEnabled(true);
        gMap.getUiSettings().setMyLocationButtonEnabled(true);

        locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);


        if (ActivityCompat.checkSelfPermission(getContext().getApplicationContext(),Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext().getApplicationContext(),Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 3000000, 0, this);
        if (ActivityCompat.checkSelfPermission(getContext().getApplicationContext(),Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext().getApplicationContext(),Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000000, 0, this);

        LatLng miPosicion = new LatLng(locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLatitude(), locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLongitude());
        LocationGeneral = miPosicion;
        //Obtener la direccion en texto de la posición actual
        try {
            adresss = geocoder.getFromLocation(miPosicion.latitude, miPosicion.longitude, 1);

        } catch (IOException e) {
            e.printStackTrace();
        }
       // moveCameraTo(new LatLng(miPosicion.latitude,miPosicion.longitude));
        //LatLng miPosicion = new LatLng(22.7535271, -102.5147896);
        // gMap.addMarker(new MarkerOptions().position(miPosicion).title("¡Aquí estoy!").draggable(true));
        /*camera = new CameraPosition.Builder()
                .target(miPosicion)
                .zoom(18)
                .bearing(0)
                .tilt(15)
                .build();

        gMap.animateCamera(CameraUpdateFactory.newCameraPosition(camera));*/


        marker = new MarkerOptions();
        marker.position(miPosicion);
        marker.title("¡Aquí estoy!");
        marker.draggable(true);
        //marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_person_pin_circle_black_24dp));
        //marker.snippet("Dirección");
        LocationGeneral = new LatLng(miPosicion.latitude, miPosicion.longitude);
        try {
            adresss = geocoder.getFromLocation(miPosicion.latitude, miPosicion.longitude, 1);
            Toast.makeText(getContext(), "Dir: "+adresss.get(0).getAddressLine(0),Toast.LENGTH_LONG).show();
        } catch (IOException e) {

            e.printStackTrace();
        }

        marker.snippet(adresss.get(0).getAddressLine(0));
        gMap.addMarker(marker);

        CreateUpdateMarkers();


        /*gMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Toast.makeText(getContext(), "Click en lat"+latLng.latitude+", long: "+latLng.longitude,Toast.LENGTH_LONG).show();
            }
        });*/

       /* gMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {
                marker.hideInfoWindow();
            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {

                double latitud = marker.getPosition().latitude;
                double longitud = marker.getPosition().longitude;
                try {
                    LocationGeneral = new LatLng(latitud, longitud);
                    adresss = geocoder.getFromLocation(latitud, longitud, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                marker.setSnippet(adresss.get(0).getAddressLine(0));
                marker.showInfoWindow();
                //moveCameraTo(new LatLng(latitud,longitud));

            }
        });*/
    }
    private void moveCameraTo(LatLng location)
    {
        camera = new CameraPosition.Builder()
                .target(new LatLng(location.latitude, location.longitude))
                .zoom(18)
                .bearing(0)
                .tilt(15)
                .build();
        gMap.animateCamera(CameraUpdateFactory.newCameraPosition(camera));
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
