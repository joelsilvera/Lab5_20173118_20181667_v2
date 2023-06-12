package com.example.lab5_20173118_20181667_v2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.lab5_20173118_20181667_v2.databinding.ActivityDetalleDoctorBinding;
import com.example.lab5_20173118_20181667_v2.databinding.ActivityListadoDoctorBinding;
import com.example.lab5_20173118_20181667_v2.model.UsuarioDto;
import com.squareup.picasso.Picasso;

public class DetalleDoctor extends AppCompatActivity {

    ActivityDetalleDoctorBinding binding;
    private static final String msg_test = "dtllDr";
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetalleDoctorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        UsuarioDto usuarioDto = (UsuarioDto) intent.getSerializableExtra("usuario");

        binding.textView7.setText("Dr. "+usuarioDto.getNombre()+" "+usuarioDto.getApellido());
        binding.textView8.setText(usuarioDto.getGenero().equals("female")?"Mujer":"Hombre");
        binding.textView9.setText(usuarioDto.getCorreo());
        binding.textView12.setText(usuarioDto.getUsername());
        binding.textView14.setText(usuarioDto.getPais()+" - "+usuarioDto.getEstado()+" - "+usuarioDto.getCiudad());
        binding.textView16.setText(usuarioDto.getEdad()+" años");
        binding.textView18.setText(usuarioDto.getNumero());
        binding.textView20.setText(usuarioDto.getNacionalidad());
        binding.textView11.setText("S/. "+ 3*usuarioDto.getEdad());

        Picasso
                .get()
                .load(usuarioDto.getFoto())
                .resize(400, 420)
                .into(binding.imageDetailDr);

    }
}