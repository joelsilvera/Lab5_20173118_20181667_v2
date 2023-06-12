package com.example.lab5_20173118_20181667_v2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.lab5_20173118_20181667_v2.databinding.ActivityCitaAgendadaExitosaBinding;
import com.example.lab5_20173118_20181667_v2.databinding.ActivityListadoDoctorBinding;
import com.example.lab5_20173118_20181667_v2.model.UsuarioDto;

public class CitaAgendadaExitosa extends AppCompatActivity {

    ActivityCitaAgendadaExitosaBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cita_agendada_exitosa);
        binding = ActivityCitaAgendadaExitosaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        binding.drAgendado.setText("Se agendó su cita con el Dr. "+name);


    }
}