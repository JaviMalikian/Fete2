package com.example.fete.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.fete.Adaptadores.ArtistasAdapter;
import com.example.fete.Metodos.Preferences;
import com.example.fete.R;
import com.example.fete.Tipos.ArtistasYGrupos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EventosActivity extends AppCompatActivity {

    private List<ArtistasYGrupos> list_ArtGrupos;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProgressBar progressBar;
    ObtenerWebService hiloconexion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventos);
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        Double a = bundle.getDouble("location");
        Double a2 = bundle.getDouble("location2");

        //hiloconexion = new ObtenerWebService();
        //adress = bundle.getString("direccion");
        //location = new LatLng(a,a2);
        //ActionBar actionBar = getActionBar();
        //actionBar.setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        if(!Preferences.obtenerPreferenceBoolean(EventosActivity.this, Preferences.BUTTON_MENSAJE_CONFIG))
            showInfo();

        progressBar = findViewById(R.id.progressBar);
        list_ArtGrupos = new ArrayList<>();
        list_ArtGrupos.clear();
        //list_ArtGrupos= getAllArtistas();
        getAllArtistas1();
        mRecyclerView = findViewById(R.id.recyclerArtistas);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
         DividerItemDecoration itemDecor = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.HORIZONTAL );
        mRecyclerView.addItemDecoration(itemDecor);

        mAdapter= new ArtistasAdapter(list_ArtGrupos, R.layout.item_eventos, EventosActivity.this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void getAllArtistas1(){
       // hiloconexion = new ObtenerWebService();
        //hiloconexion.execute("https://guau.000webhostapp.com/obtener_eventos.php", "1");

        list_ArtGrupos.add(new ArtistasYGrupos(1, "Ara Malikian", "Violinista libanés",
                "Ara Malikian es un virtuoso violinista de origen libanés",
                "Plaza de armas", 22.769381, -102.571888, "5 de febrero de 1988", "https://guau2.000webhostapp.com/imagenes/1.jpg"));

        list_ArtGrupos.add(new ArtistasYGrupos(2, "Paté de Fua", "grupo multidiciplinario",
                "grupo multidiciplinario de origen mexicano, integrado por mexicanos, argentinos etc etc etc",
                "Plaza de armas", 22.769974, -102.575729, "5 de febrero de 1988", "https://guau2.000webhostapp.com/imagenes/2.jpg"));

        list_ArtGrupos.add(new ArtistasYGrupos(3, "Triciclo circus band", "grupo multidiciplinario balcanico",
                "grupo multidiciplinario de origen mexicano, musica balcanica, vals y polkas",
                "Plaza de armas", 22.775999, -102.572156, "5 de febrero de 1988", "https://guau2.000webhostapp.com/imagenes/3.jpg"));

        list_ArtGrupos.add(new ArtistasYGrupos(4, "Blink 182", "grupo Punk-rock",
                "grupo de musica punk que nace en california, conformado por Tom, mark y travis",
                "Plaza de armas", 22.775050, -102.573079, "5 de febrero de 1988", "https://guau2.000webhostapp.com/imagenes/4.jpg"));

        list_ArtGrupos.add(new ArtistasYGrupos(5, "Mon Laferte", "es una cantautora, música, compositora y activista chilena",
                "es una cantautora, música, compositora y activista chilena, implicada especialmente en la defensa del colectivo LGBT, el ecologismo, el aborto libre, los derechos de los animales y la situación política de su país.",
                "Plaza de armas", 22.773877, -102.574200, "5 de febrero de 1988", "https://guau2.000webhostapp.com/imagenes/5.jpg"));

        list_ArtGrupos.add(new ArtistasYGrupos(6, "Kaschauer Klezmer Band", "El objetivo de esta banda es interpretar música klezmer impregnada de tradiciones de música folklórica del este de Europa",
                "El objetivo de esta banda es interpretar música klezmer impregnada de tradiciones de música folklórica del este de Europa, el folklore balcánico, eslovaco y romaní, enriquecida con elementos de música clásica y moderna.",
                "Plaza de armas", 22.773877, -102.574200, "5 de febrero de 1988", "https://guau2.000webhostapp.com/imagenes/6.jpg"));
        progressBar.setVisibility(View.GONE);
    }

    public  void showCargando() {
        new AlertDialog.Builder(EventosActivity.this)
        .setIcon(R.drawable.loading)
                .setMessage("Cargando...")
                .show()
        .dismiss();

    }

    private void showInfo(){
        AlertDialog.Builder builder = new AlertDialog.Builder(EventosActivity.this, R.style.AlertDialogTheme);
        builder.setTitle("Elige una opción");
        builder.setIcon(R.drawable.ic_info_black_24dp);
        builder.setNegativeButton("Aún no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) { }});

        builder.setMessage("Para acceder a la información del evento manten presionada la imagen");
        builder.setPositiveButton("Entendido", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Preferences.savePreferenceBoolean(EventosActivity.this,true,Preferences.BUTTON_MENSAJE_CONFIG);
            }});
        builder.show();
    }

    //web service--------------------------------------------------------
    public class ObtenerWebService extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            //showCargando();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s.equals("1")) {
                progressBar.setVisibility(View.GONE);
                //progressBar.setMaxHeight(0);
                mRecyclerView.setAdapter(mAdapter);

            }
            else
                Toast.makeText(getApplicationContext(),"Error"+s, Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected String doInBackground(String... strings) {
            String cadena =strings[0];
            URL url=null;
            String devuelve="";
            if(Objects.equals(strings[1], "1")){
                try{
                    url = new URL(cadena);
                    HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                    connection.setRequestProperty("User-Agent", "Mozilla/5.0"+
                            "(Linux; Android 1.5; es-ES) Ejemplo HTTP");

                    int respuesta = connection.getResponseCode();
                    StringBuilder result = new StringBuilder();

                    if(respuesta== HttpURLConnection.HTTP_OK){//200 ok 403 para no conexin
                        InputStream in = new BufferedInputStream(connection.getInputStream());
                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                        String line;
                        while ((line = reader.readLine())!= null){
                            result.append(line);
                        }

                        JSONObject respuestaJSON = new JSONObject(result.toString());
                        String resultJSON = respuestaJSON.getString("estado");

                        if(resultJSON.equals("1")){
                            JSONArray consultaJSON = respuestaJSON.getJSONArray("clientes");

                            for (int i=0;i < consultaJSON.length();i++){
                                list_ArtGrupos.add(new ArtistasYGrupos(
                                        consultaJSON.getJSONObject(i).getInt("Id_artista"),
                                        consultaJSON.getJSONObject(i).getString("Nombre_evento"),
                                        consultaJSON.getJSONObject(i).getString("desc_corta"),
                                        consultaJSON.getJSONObject(i).getString("desc_larga"),
                                        consultaJSON.getJSONObject(i).getString("Ubicacion"),
                                        consultaJSON.getJSONObject(i).getDouble("Lat"),
                                        consultaJSON.getJSONObject(i).getDouble("Longi"),
                                        consultaJSON.getJSONObject(i).getString("fecha_alta"),
                                        consultaJSON.getJSONObject(i).getString("path")

                                ));
                            }
                        }
                        devuelve = respuestaJSON.getString("estado");
                    }
                    else
                        devuelve = "Revisa la conexion a internet e inténtalo nuevamente";
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                    devuelve = e.getMessage(); }
            }
            return devuelve;
        }
    }
}
