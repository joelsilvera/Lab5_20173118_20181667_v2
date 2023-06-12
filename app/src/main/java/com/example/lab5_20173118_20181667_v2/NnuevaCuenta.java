package com.example.lab5_20173118_20181667_v2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NnuevaCuenta extends AppCompatActivity {

    EditText txtNombre;
    EditText txtPassword;
    EditText txtCorreo;
    EditText txtTelefono;
    TextView loginRedirectText;
    Button botonIngresar;
    FirebaseDatabase database;
    DatabaseReference reference;
    ImageButton botonGoogle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nnueva_cuenta);


        txtNombre = findViewById(R.id.txtNombre);
        txtPassword = findViewById(R.id.txtPassword);
        txtCorreo = findViewById(R.id.txtCorreo);
        txtTelefono = findViewById(R.id.txtTelefono);
        loginRedirectText = findViewById(R.id.botonFacebook);
        botonIngresar = findViewById(R.id.botonIngresar2);
        botonGoogle = findViewById(R.id.botonGoogle);


        botonIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database = FirebaseDatabase.getInstance();
                reference = database.getReference("users");

                String email = txtCorreo.getText().toString();
                String name = txtNombre.getText().toString();
                String password = txtPassword.getText().toString();
                String telefono = txtTelefono.getText().toString();

                Usuario usuario = new Usuario(email, name, password, telefono);
                reference.child(name).setValue(usuario);

                Toast.makeText(NnuevaCuenta.this, "Tu cuenta se creó exitosamente", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(NnuevaCuenta.this, MainActivity.class);
                startActivity(intent);
            }
        });


        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NnuevaCuenta.this, MainActivity.class);
                startActivity(intent);
            }
        });




    }
}