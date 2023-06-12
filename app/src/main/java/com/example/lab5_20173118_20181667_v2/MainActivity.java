package com.example.lab5_20173118_20181667_v2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ktx.Firebase;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    Button botonPorMientras;

    EditText txtCorreo2;
    EditText txtPassword2;
    Button botonIngresar2;
    TextView signUpRedirectText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Para ir al listado de Doctores directamente
        botonPorMientras = (Button)findViewById(R.id.botonPorMientras);
        botonPorMientras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this, ListadoDoctor.class);
                startActivity(myIntent);
            }
        });


        txtCorreo2 = findViewById(R.id.txtCorreo2);
        txtPassword2 = findViewById(R.id.txtPassword2);
        botonIngresar2 = findViewById(R.id.botonIngresar2);
        signUpRedirectText = findViewById(R.id.signUpRedirectText);

        botonIngresar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!validateUsername() | !validatePassword()){

                }else{
                    checkUser();
                }
            }
        });

        signUpRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NnuevaCuenta.class);
                startActivity(intent);
            }
        });

    }

    public Boolean validateUsername(){
        String val = txtCorreo2.getText().toString();
        if (val.isEmpty()){
            txtCorreo2.setError("Debe ingresar un correo");
            return false;
        }else{
            txtCorreo2.setError(null);
            return true;
        }
    }

    public Boolean validatePassword(){
        String val = txtPassword2.getText().toString();
        if (val.isEmpty()){
            txtPassword2.setError("Debe ingresar una contraseña");
            return false;
        }else{
            txtPassword2.setError(null);
            return true;
        }
    }

    public void checkUser(){
        String correo = txtCorreo2.getText().toString().trim();
        String password = txtPassword2.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUserDatabase = reference.orderByChild("email").equalTo(correo);

        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){
                    txtCorreo2.setError(null);
                    String passwordFromDB = snapshot.child(correo).child("password").getValue(String.class);

                    if(!Objects.equals(passwordFromDB, password)){
                        txtCorreo2.setError(null);
                        Intent intent = new Intent(MainActivity.this, ListadoDoctor.class);
                        startActivity(intent);
                    }else{
                        txtPassword2.setError("Contraseña incorrecta");
                        txtPassword2.requestFocus();
                    }
                }else{
                    txtCorreo2.setError("Este correo no está registrado");
                    txtCorreo2.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }



}