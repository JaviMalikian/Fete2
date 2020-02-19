package com.example.fete.Adaptadores;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fete.Activities.EventoVisionGeneral;
import com.example.fete.Activities.MapsActivity_Evento;
import com.example.fete.Activities.ReseniaForm;
import com.example.fete.Metodos.Metodos;
import com.example.fete.R;
import com.example.fete.Tipos.ArtistasYGrupos;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;

import java.util.List;

import static android.content.Context.CLIPBOARD_SERVICE;

public class ArtistasAdapter extends RecyclerView.Adapter<ArtistasAdapter.ViewHolder> {

    private List<ArtistasYGrupos> artistas;
    private int layout;
    public Context context;

    private TextView Artista;
    private ImageView image2;
    /*
    private TextView descripcionCorta;
    private TextView fecha;
    private TextView hora;
    private TextView lugar;
    private ImageButton btnUbicacion;
    private ImageButton btnDetalles;
    private ImageButton btnResenia;*/


    public ArtistasAdapter(List<ArtistasYGrupos> list_artGrupos, int item_eventos, Context applicationContext) {
        this.artistas = list_artGrupos;
        this.layout = item_eventos;
        this.context = applicationContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(layout, parent, false);
        Artista = v.findViewById(R.id.Artista);
        image2 = v.findViewById(R.id.image2);
        // FacebookSdk.sdkInitialize(context);
        //AppEventsLogger.activateApp(context);

        return new ViewHolder(v);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        return artistas.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int i) {
        // holder.bind(artistas.get(i),itemClickListener);

        Artista.setText(artistas.get(i).getNombreArtista());
        Artista.setBackgroundColor(Color.argb(90, 255, 255, 255));

        Metodos mt=new Metodos();
        mt.imagePicasso(context,artistas.get(i).getPath(), holder.image2 );

        image2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                try {
                    Thread.sleep(100);
                    showOptions(artistas.get(i).getId_ArtistaGrupo(),i);
                } catch (InterruptedException e) { e.printStackTrace(); }
                return true;
            }
        });
    }

    private void showOptions(final int id_art, final int position){
        final CharSequence[] option = {"Compartir en Facebook","Compartir en Whatsapp","Ver detalles del evento",
                "Ver ubicación de el evento","Copiar enlace", "Dejar reseña"};

        final AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogTheme);
        builder.setTitle("Elige una opción");

        builder.setItems(option, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(which){
                    case 0://compartir en facebok

                        compartir_facebook();

                        break;
                    case 1://Compartir en whatsapp
                        CompartirWhats();
                        break;
                    case 2://Detalles del evento
                        Intent iE = new Intent(context, EventoVisionGeneral.class);
                        iE.putExtra("Lat", artistas.get(position).getLat());
                        iE.putExtra("Long", artistas.get(position).getLongi());
                        iE.putExtra("IdArt", artistas.get(position).getId_ArtistaGrupo());
                        iE.putExtra("NombreArt", artistas.get(position).getNombreArtista());
                        iE.putExtra("Descripcion", artistas.get(position).getDescripcionCorta());
                        context.startActivity(iE);

                        break;
                    case 3://Ver ubicacion en mapa
                        Intent i = new Intent(context, MapsActivity_Evento.class);
                        i.putExtra("Lat", artistas.get(position).getLat());
                        i.putExtra("Long", artistas.get(position).getLongi());
                        i.putExtra("IdArt", artistas.get(position).getId_ArtistaGrupo());
                        i.putExtra("NombreArt", artistas.get(position).getNombreArtista());
                        i.putExtra("Descripcion", artistas.get(position).getDescripcionCorta());
                        context.startActivity(i);

                        break;
                    case 4://Copiar enlace
                        ClipboardManager myClipboard;
                        myClipboard = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
                        ClipData myClip;
                        String text = "http://guau.000webhostapp.com/Mascotas/id_mascota=";
                        myClip = ClipData.newPlainText("text", text);
                        assert myClipboard != null;
                        myClipboard.setPrimaryClip(myClip);
                        Toast.makeText(context, "Enlace copiado...", Toast.LENGTH_SHORT).show();
                        break;
                    case 5:
                        Intent in = new Intent(context, ReseniaForm.class);
                        //Bundle
                        in.putExtra("NombreArt", artistas.get(position).getNombreArtista());
                        in.putExtra("idArt", artistas.get(position).getId_ArtistaGrupo());

                        //in.putExtra("location2", Double.valueOf(LocationGeneral.longitude));
                        //in.putExtra("direccion", String.valueOf(adresss.get(0).getAddressLine(0).toString()));

                        context.startActivity(in);
                        break;
                    default:
                        dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void compartir_facebook() {

        image2.buildDrawingCache();
        Bitmap imgtoShare=image2.getDrawingCache();

        SharePhoto photo = new SharePhoto.Builder()
                .setBitmap(imgtoShare)
                .build();
        final SharePhotoContent content = new SharePhotoContent.Builder()
                .addPhoto(photo)
                .setRef("Compartido desde Festivales")
                .build();

        final ProgressDialog progresRing = ProgressDialog.show(context, "Compartiendo", "Por favor espere...", true);
        progresRing.setCancelable(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                ShareDialog.show((Activity) context,content);
            }
        }).start();
        progresRing.show();
    }

    private void CompartirWhats(){
        Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
        whatsappIntent.setType("text/plain");
        whatsappIntent.setPackage("com.whatsapp");
        whatsappIntent.putExtra(Intent.EXTRA_TEXT, "Enviado desde GuauApp http://guau.000webhostapp.com/verfoto.php?id=332");
        try {
            context.startActivity(whatsappIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(context,"WhatsApp no esta instalado",Toast.LENGTH_SHORT).show();
        }
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView Artista;
        private TextView descripcionCorta;
        private TextView fecha;
        private TextView hora;
        private TextView lugar;
        private ImageButton btnUbicacion;
        private ImageButton btnDetalles;
        private ImageButton btnResenia;
        private ImageView image2;

        private ViewHolder(View itemView)  {
            super(itemView);
            this.image2 = itemView.findViewById(R.id.image2);
            this.Artista = itemView.findViewById(R.id.Artista);
            //descripcionCorta = itemView.findViewById(R.id.descripcionCorta);
            //btnUbicacion = itemView.findViewById(R.id.btnUbicacion);
            //btnDetalles = itemView.findViewById(R.id.btnDetalles);
            //btnResenia= itemView.findViewById(R.id.btnResenia);

            //fecha=itemView.findViewById(R.id.fecha);
            //hora= itemView.findViewById(R.id.hora);
            //lugar= itemView.findViewById(R.id.lugar);
            //  cardV=itemView.findViewById(R.id.cardV);
        }
    }
}