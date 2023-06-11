package com.example.lab5_20173118_20181667_v2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button botonPorMientras;

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



    }
}