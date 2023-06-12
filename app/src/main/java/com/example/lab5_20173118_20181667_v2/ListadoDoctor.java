package com.example.lab5_20173118_20181667_v2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.lab5_20173118_20181667_v2.databinding.ActivityListadoDoctorBinding;
import com.example.lab5_20173118_20181667_v2.model.Doctor;
import com.example.lab5_20173118_20181667_v2.model.DoctorDto;
import com.example.lab5_20173118_20181667_v2.model.DoctorRepository;
import com.example.lab5_20173118_20181667_v2.model.ListaDoctoresAdapter;
import com.example.lab5_20173118_20181667_v2.model.RetrofitBuilder;
import com.example.lab5_20173118_20181667_v2.model.UsuarioDto;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListadoDoctor extends AppCompatActivity {

    ImageButton botonAitel;
    TextView titleName;
    ActivityListadoDoctorBinding binding;

    private static final String msg_test = "dr-list";

    FirebaseDatabase firebaseDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListadoDoctorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        //Para colocar datos del login
        titleName = findViewById(R.id.titleName);


        firebaseDatabase = FirebaseDatabase.getInstance();

        firebaseDatabase.getReference().child("doctors").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<UsuarioDto> usuarioDtoList = new ArrayList<>();
                for (DataSnapshot children:
                     snapshot.getChildren()) {
                    UsuarioDto usuarioDto1 = children.getValue(UsuarioDto.class);
                    Log.d("infoApp",children.getKey()+
                            " | "+usuarioDto1.getNombre()+
                            " | "+usuarioDto1.getApellido());
                    usuarioDtoList.add(usuarioDto1);
                }
                ListaDoctoresAdapter adapter = new ListaDoctoresAdapter();
                adapter.setContext(ListadoDoctor.this);
                adapter.setListaDoctores(usuarioDtoList);
                adapter.notifyDataSetChanged();

                binding.recyclerviewDr.setAdapter(adapter);
                binding.recyclerviewDr.setLayoutManager(new LinearLayoutManager(ListadoDoctor.this));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.button.setOnClickListener(view -> {
            cargarLista();
        });

        String nameUser = showUserData();

        //Para ir al Perfil
        botonAitel = (ImageButton) findViewById(R.id.botonAitel);
        botonAitel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListadoDoctor.this, Perfil.class);
                intent.putExtra("name", nameUser);
                startActivity(intent);
            }
        });

    }


    public String showUserData(){
        Intent intent = getIntent();
        String nameUser = intent.getStringExtra("name");
        titleName.setText(nameUser);
        return nameUser;
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
                    usuarioDto.setFoto(d.getPicture().getMedium());
                    usuarioDto.setEdad(d.getDob().getAge());
                    usuarioDto.setGenero(d.getGender());

                    databaseReference.child("doctors").push().setValue(usuarioDto)
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