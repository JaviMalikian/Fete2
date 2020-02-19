package com.example.fete.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fete.Metodos.Metodos;
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
import java.net.URL;
import java.util.List;
import java.util.Objects;

public class EventoVisionGeneral extends AppCompatActivity implements View.OnClickListener {

    String NombreArt;
    String pathImage;
    String DescLarga;
    String DescCorta;
    String FechaHora;
    String Lat;
    String Long;
    int IdArt;

    TextView Artista;
    Button btnResenia;
    Button btnUbicacionEv;
    ImageView image;
    TextView descLarga;
    TextView lugar;
    TextView fechaHora;

    ObtenerWebService hiloconexion;
    public List<ArtistasYGrupos> list_ArtGrupos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evento_vision_general);
        Artista = findViewById(R.id.Artista);
        btnResenia = findViewById(R.id.btnResenia);
        btnUbicacionEv = findViewById(R.id.btnUbicacionEv);
        btnResenia.setOnClickListener(this);
        btnUbicacionEv.setOnClickListener(this);

        image = findViewById(R.id.image);
        descLarga = findViewById(R.id.descLarga);
        lugar = findViewById(R.id.lugar);
        fechaHora = findViewById(R.id.fechaHora);


        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        NombreArt = bundle.getString("NombreArt");
        IdArt = bundle.getInt("IdArt");

        Artista.setText("Artista(s): "+NombreArt);
        Artista.setBackgroundColor(Color.argb(80, 255, 255, 255));

        getAllArtistas1();

        btnResenia.setOnClickListener(this);
    }
    public void getAllArtistas1(){
        hiloconexion = new ObtenerWebService();
        hiloconexion.execute("https://guau.000webhostapp.com/obtener_ev.php?id_evento="+IdArt, "1");
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnResenia:
                Intent in = new Intent(getApplicationContext(), ReseniaForm.class);
                //Bundle
                in.putExtra("NombreArt", NombreArt);
                in.putExtra("idArt", IdArt);
                startActivity(in);
                break;
            case R.id.btnUbicacionEv ://
                double lat= Double.parseDouble(Lat);
                double longi= Double.parseDouble(Long);

                Intent i = new Intent(getApplicationContext(), MapsActivity_Evento.class);
                i.putExtra("Lat", lat);
                i.putExtra("Long", longi);
                i.putExtra("IdArt", IdArt);
                i.putExtra("NombreArt", NombreArt);
                i.putExtra("Descripcion", DescCorta);
                startActivity(i);
                break;
        }
    }

    //web service--------------------------------------------------------
    public class ObtenerWebService extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            // progressBar.setVisibility(View.GONE);

            if(s.equals("1")){
                descLarga.setText(DescLarga);
                lugar.setText("Lugar: "+DescCorta);
                fechaHora.setText("Fecha y hora: "+FechaHora);
                Metodos mt=new Metodos();
                mt.imagePicasso(getApplicationContext(),pathImage, image );
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

                    if(respuesta== HttpURLConnection.HTTP_OK){
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

                            DescLarga=consultaJSON.getJSONObject(0).getString("desc_larga");
                            DescCorta=consultaJSON.getJSONObject(0).getString("desc_corta");
                            FechaHora=consultaJSON.getJSONObject(0).getString("fecha_alta");
                            pathImage=consultaJSON.getJSONObject(0).getString("path");
                            Lat =consultaJSON.getJSONObject(0).getString("Lat");
                            Long =consultaJSON.getJSONObject(0).getString("Longi");

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
