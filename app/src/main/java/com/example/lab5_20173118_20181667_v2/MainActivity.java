package com.example.lab5_20173118_20181667_v2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    Button botonPorMientras;


    EditText txtCorreo2;
    EditText txtPassword2;
    Button botonIngresar2;
    TextView signUpRedirectText;

    // Para usar el Auth
    ImageButton botonGoogleLogIn;
    FirebaseAuth auth;
    FirebaseDatabase database;
    GoogleSignInClient mGoogleSignInClient;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtCorreo2 = findViewById(R.id.txtCorreo2);
        txtPassword2 = findViewById(R.id.txtPassword2);
        botonIngresar2 = findViewById(R.id.botonIngresar2);
        signUpRedirectText = findViewById(R.id.signUpRedirectText);

        botonIngresar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!(validateUsername() && validatePassword())){

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


        // Para usar el Auth
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);

        botonGoogleLogIn = findViewById(R.id.botonGoogleLogIn);
        botonGoogleLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });


    }

    //Metodos para el Authentication
    int RC_SIGN_IN = 40;

    private void signIn(){
        Intent intent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(intent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuth(account.getIdToken());
            } catch (ApiException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void firebaseAuth(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken,null);
        auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = auth.getCurrentUser();

                    Users users = new Users();
                    users.setUserId(user.getUid());
                    users.setName(user.getDisplayName());
                    users.setProfile(user.getPhotoUrl().toString());

                    database.getReference().child("users").child(user.getUid()).setValue(users);
                    Intent intent = new Intent(MainActivity.this, ListadoDoctor.class);
                    startActivity(intent);

                }else{
                    Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //Metodos para el RealtimeDtabase
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

                    String passwordFromDB = null;
                    String nameFromDB = null;
                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                        passwordFromDB = userSnapshot.child("password").getValue(String.class);
                        nameFromDB = userSnapshot.child("name").getValue(String.class);
                    }

                    if(passwordFromDB.equals(password)){
                        txtCorreo2.setError(null);
                        Intent intent = new Intent(MainActivity.this, ListadoDoctor.class);
                        intent.putExtra("name", nameFromDB);
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