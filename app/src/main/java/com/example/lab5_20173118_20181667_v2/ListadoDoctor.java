package com.example.lab5_20173118_20181667_v2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.lab5_20173118_20181667_v2.databinding.ActivityListadoDoctorBinding;
import com.example.lab5_20173118_20181667_v2.model.Doctor;
import com.example.lab5_20173118_20181667_v2.model.DoctorDto;
import com.example.lab5_20173118_20181667_v2.model.DoctorRepository;
import com.example.lab5_20173118_20181667_v2.model.RetrofitBuilder;
import com.example.lab5_20173118_20181667_v2.model.UsuarioDto;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListadoDoctor extends AppCompatActivity {

    ActivityListadoDoctorBinding binding;

    private static final String msg_test = "dr-list";

    FirebaseDatabase firebaseDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListadoDoctorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseDatabase = FirebaseDatabase.getInstance();

        firebaseDatabase.getReference().child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot children:
                     snapshot.getChildren()) {
                    UsuarioDto usuarioDto1 = children.getValue(UsuarioDto.class);
                    Log.d("infoApp",children.getKey()+
                            " | "+usuarioDto1.getNombre()+
                            " | "+usuarioDto1.getApellido());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.button.setOnClickListener(view -> {
            cargarLista();
        });


    }


    public void cargarLista() {
        DoctorRepository doctorRepository = RetrofitBuilder.createRepo("https://randomuser.me");

        doctorRepository.obtenerDoctor().enqueue(new Callback<DoctorDto>() {
            @Override
            public void onResponse(Call<DoctorDto> call, Response<DoctorDto> response) {
                if (response.isSuccessful()) {
                    DoctorDto doctorDto = response.body();
                    Doctor d = doctorDto.getResults()[0];
                    DatabaseReference databaseReference = firebaseDatabase.getReference();
                    UsuarioDto usuarioDto = new UsuarioDto();
                    usuarioDto.setNombre(d.getName().getFirst());
                    usuarioDto.setApellido(d.getName().getLast());
                    usuarioDto.setPais(d.getLocation().getCountry());
                    usuarioDto.setCiudad(d.getLocation().getCity());
                    usuarioDto.setEstado(d.getLocation().getState());
                    usuarioDto.setNumero(d.getPhone());
                    usuarioDto.setUsername(d.getLogin().getUsername());
                    usuarioDto.setCorreo(d.getEmail());
                    usuarioDto.setNacionalidad(d.getNat());
                    usuarioDto.setFoto(d.getPicture().getThumbnail());
                    usuarioDto.setEdad(d.getDob().getAge());


                    databaseReference.child("users").push().setValue(usuarioDto)
                            .addOnSuccessListener(aVoid ->{
                                Log.d(msg_test,"Data guardada exitosamente");
                            })
                            .addOnFailureListener(e ->{
                                Log.d(msg_test,e.getMessage());
                            });

                } else {
                    Log.d(msg_test, "Algo pasó :/");
                }


            }

            @Override
            public void onFailure(Call<DoctorDto> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }
}