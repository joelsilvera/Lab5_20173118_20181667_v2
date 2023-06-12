package com.example.lab5_20173118_20181667_v2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class Perfil extends AppCompatActivity {

    TextView titleName2;
    TextView txtCerrarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        titleName2 = (TextView) findViewById(R.id.titleName2);
        mostrarData();

        //Para Cerrar sesion
        txtCerrarSesion = (TextView) findViewById(R.id.txtCerrarSesion);
        txtCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Perfil.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Borra todas las actividades de la pila
                startActivity(intent);
                finish();
            }
        });




    }
    public void mostrarData(){
        Intent intent = getIntent();
        String nameUser = intent.getStringExtra("name");
        titleName2.setText(nameUser);
    }

}