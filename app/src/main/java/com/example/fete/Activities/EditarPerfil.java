package com.example.fete.Activities;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fete.Fragments.Perfil;
import com.example.fete.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EditarPerfil.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EditarPerfil#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditarPerfil extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    private EditText nombreUsuario;
    private EditText apelsUsuario;

    private EditText correo;
    private EditText noTelefono;
    private EditText mupo;
    private EditText Estado;

    private Button btnGuardaPerfil;


    public EditarPerfil() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditarPerfil.
     */
    // TODO: Rename and change types and number of parameters
    public static EditarPerfil newInstance(String param1, String param2) {
        EditarPerfil fragment = new EditarPerfil();
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

        /*
        * bundle.putString("nombreUsuario", list_ArtGrupos.get(0));
                bundle.putString("ApelsUsuario", list_ArtGrupos.get(1));
                bundle.putString("correoUsuario", list_ArtGrupos.get(2));
                bundle.putString("noTelefono", list_ArtGrupos.get(3));
                bundle.putString("mupo", list_ArtGrupos.get(4));
                bundle.putString("Estado", list_ArtGrupos.get(5));
        *
        * */
        View v =inflater.inflate(R.layout.fragment_editar_perfil, container, false);

            nombreUsuario = v.findViewById(R.id.nombreUsuario);
            apelsUsuario =  v.findViewById(R.id.apellidosUsuario);
            correo= v.findViewById(R.id.correoUsuario);
            noTelefono = v.findViewById(R.id.noTelefono);
            mupo = v.findViewById(R.id.mupoUsuario);
            Estado = v.findViewById(R.id.estadoUsuario);
            btnGuardaPerfil = v.findViewById(R.id.btnGuardaPerfil);


        btnGuardaPerfil.setOnClickListener( this);


        return v;
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
    public void onStart() {
        super.onStart();
        Bundle args = getArguments();
        if (args != null) {
            //showReceivedData.setText(args.getString(DATA_RECEIVE));
            nombreUsuario.setText(getArguments().getString("nombreUsuario")) ;
            apelsUsuario.setText(getArguments().getString("ApelsUsuario")) ;
            correo.setText(getArguments().getString("correoUsuario")) ;
            noTelefono.setText(getArguments().getString("noTelefono")) ;
            mupo.setText(getArguments().getString("mupo")) ;
            Estado.setText(getArguments().getString("Estado")) ;


        }
    }

    @Override
    public void onClick(View v) {
        if(v == btnGuardaPerfil) {
            Toast.makeText(getActivity(), "Guardado...", Toast.LENGTH_SHORT).show();
            FragmentManager frMan = getActivity().getSupportFragmentManager();
            frMan.beginTransaction().replace(R.id.nav_host_fragment, new Perfil()).commit();
        }
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
}
