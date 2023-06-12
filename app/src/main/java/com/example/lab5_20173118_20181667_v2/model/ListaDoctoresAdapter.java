package com.example.lab5_20173118_20181667_v2.model;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab5_20173118_20181667_v2.DetalleDoctor;
import com.example.lab5_20173118_20181667_v2.ListadoDoctor;
import com.example.lab5_20173118_20181667_v2.MainActivity;
import com.example.lab5_20173118_20181667_v2.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class ListaDoctoresAdapter extends RecyclerView.Adapter<ListaDoctoresAdapter.DoctorViewHolder> {
    private List<UsuarioDto> listaDoctores;
    private Context context;


    public void setListaDoctores(List<UsuarioDto> listaDoctores) {
        this.listaDoctores = listaDoctores;
    }



    public void setContext(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public DoctorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_rv,parent,false);
        return new DoctorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorViewHolder holder, int position) {
        UsuarioDto u = listaDoctores.get(position);
        holder.usuarioDto = u;

        TextView textView1 = holder.itemView.findViewById(R.id.doctorname);
        textView1.setText("Dr. "+u.getApellido());

        TextView textView2 = holder.itemView.findViewById(R.id.locationDoctor);
        textView2.setText(u.getPais()+" - "+u.getEstado()+" - \n"+u.getCiudad());

        ImageView imageView = holder.itemView.findViewById(R.id.imageDr);
        Picasso
                .get()
                .load(u.getFoto())
                .into(imageView);

        // Set click listener on the "verDtllDr" button
        Button btn1 = holder.itemView.findViewById(R.id.verDtllDr);
        btn1.setOnClickListener(view -> {
            Intent myIntent = new Intent(context, DetalleDoctor.class);
            myIntent.putExtra("usuario",u); // Pass the UsuarioDto object as an extra
            context.startActivity(myIntent);
        });

    }

    @Override
    public int getItemCount() {
        return listaDoctores.size();
    }

    public class DoctorViewHolder extends RecyclerView.ViewHolder{
       UsuarioDto usuarioDto;

       public DoctorViewHolder(@NonNull View itemView) {
           super(itemView);
       }
   }
}
