package com.example.fete.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fete.Activities.EditarPerfil;
import com.example.fete.Activities.EventosActivity;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Perfil.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Perfil#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Perfil extends Fragment implements EditarPerfil.OnFragmentInteractionListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private List<String> list_ArtGrupos;

    private TextView nombreUsuario;
    private TextView miembroDesde;
    private TextView correo;
    private TextView noTelefono;
    private TextView mupoEstado;
    private TextView ArtistaRes;
    private TextView reseniaTv;
    private TextView ArtistaRes2;
    private TextView reseniaTv2;
    private TextView ArtistaRes3;
    private TextView reseniaTv3;

    private ProgressBar progressBar;
    ObtenerWebService hiloconexion;

    private Button btnmodificar;


    public Perfil() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Perfil.
     */
    // TODO: Rename and change types and number of parameters
    public static Perfil newInstance(String param1, String param2) {
        Perfil fragment = new Perfil();
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
        View v = inflater.inflate(R.layout.fragment_perfil, container, false);

        nombreUsuario=v.findViewById(R.id.nombreUsuario);
        miembroDesde = v.findViewById(R.id.miembroDesde);
        correo=v.findViewById(R.id.correo);
        noTelefono = v.findViewById(R.id.noTelefono);
        mupoEstado = v.findViewById(R.id.mupoEstado);

        ArtistaRes = v.findViewById(R.id.ArtistaRes);
        reseniaTv = v.findViewById(R.id.reseniaTv);

        ArtistaRes2 = v.findViewById(R.id.ArtistaRes2);
        reseniaTv2 = v.findViewById(R.id.reseniaTv2);

        ArtistaRes3 = v.findViewById(R.id.ArtistaRes3);
        reseniaTv3 =v.findViewById(R.id.reseniaTv3);

        progressBar = v.findViewById(R.id.progressBar);

        btnmodificar = v.findViewById(R.id.btnmodificar);

        list_ArtGrupos = new ArrayList<>();

        getAllArtistas2();

        if(list_ArtGrupos.size()>0) {
            nombreUsuario.setText(list_ArtGrupos.get(0));
            miembroDesde.setText(list_ArtGrupos.get(6));
            correo.setText(list_ArtGrupos.get(2));
            noTelefono.setText(list_ArtGrupos.get(3));
            mupoEstado.setText(list_ArtGrupos.get(4)+", "+list_ArtGrupos.get(5));
            ArtistaRes.setText(list_ArtGrupos.get(0));
            reseniaTv.setText(list_ArtGrupos.get(7));
          //  ArtistaRes2.setText(list_ArtGrupos.get(0));
            //reseniaTv2.setText(list_ArtGrupos.get(0));
            //ArtistaRes3.setText(list_ArtGrupos.get(0));
            //reseniaTv3.setText(list_ArtGrupos.get(0));
        }

        btnmodificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle=new Bundle();
                bundle.putString("nombreUsuario", list_ArtGrupos.get(0));
                bundle.putString("ApelsUsuario", list_ArtGrupos.get(1));
                bundle.putString("correoUsuario", list_ArtGrupos.get(2));
                bundle.putString("noTelefono", list_ArtGrupos.get(3));
                bundle.putString("mupo", list_ArtGrupos.get(4));
                bundle.putString("Estado", list_ArtGrupos.get(5));

                //set Fragmentclass Arguments
                EditarPerfil fragobj=new EditarPerfil();
                fragobj.setArguments(bundle);

                FragmentManager frMan = getActivity().getSupportFragmentManager();
                frMan.beginTransaction().replace(R.id.nav_host_fragment, new EditarPerfil()).commit();
            }
        });



        return v;
    }

    private void getAllArtistas1(){
        hiloconexion = new ObtenerWebService();
        hiloconexion.execute("https://guau.000webhostapp.com/fete_obtener_cliente_by_id.php?id_usuario=1", "1");
    }

    private void getAllArtistas2(){
        list_ArtGrupos.add("Javier");
        list_ArtGrupos.add("Castro Cortes");
        list_ArtGrupos.add( "javier.bodom@gmail.com");
        list_ArtGrupos.add( "4921593054");
        list_ArtGrupos.add( "Guadalupe");
        list_ArtGrupos.add("Zacatecas");
        list_ArtGrupos.add("05/02/2019");
        list_ArtGrupos.add("Resenia del usuario que va a ir aqui y que puede ser mas largo que esto pero así se puso");
        progressBar.setVisibility(View.GONE);

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
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
    public void onFragmentInteraction(Uri uri) {

    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
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
            if(s.equals("1"))
                progressBar.setVisibility(View.GONE);
           // else
             //   Toast.makeText(getApplicationContext(),"Error"+s, Toast.LENGTH_SHORT).show();
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
                        while ((line = reader.readLine())!= null){ result.append(line); }

                        JSONObject respuestaJSON = new JSONObject(result.toString());
                        String resultJSON = respuestaJSON.getString("status");
                        if(resultJSON.equals("1")){
                            JSONArray consultaJSON = respuestaJSON.getJSONArray("JsonResults");
                            //for (int i=0;i < consultaJSON.length();i++){
                                list_ArtGrupos.add(consultaJSON.getJSONObject(0).getString("nombre_usuario"));
                                list_ArtGrupos.add(consultaJSON.getJSONObject(0).getString("apels_usuario"));
                                list_ArtGrupos.add( consultaJSON.getJSONObject(0).getString("email"));
                                list_ArtGrupos.add( consultaJSON.getJSONObject(0).getString("telefono"));
                                list_ArtGrupos.add( consultaJSON.getJSONObject(0).getString("municipio"));
                                list_ArtGrupos.add(consultaJSON.getJSONObject(0).getString("estado"));
                                list_ArtGrupos.add(consultaJSON.getJSONObject(0).getString("fecha_alta"));
                            list_ArtGrupos.add(consultaJSON.getJSONObject(0).getString("resenia"));

                           // }
                        }
                        devuelve = respuestaJSON.getString("status");
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
