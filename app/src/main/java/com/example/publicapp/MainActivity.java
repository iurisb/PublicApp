package com.example.publicapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private FirebaseAuth auth;   //autenticacao
    private FirebaseUser user;   //usuario
    private FirebaseAuth.AuthStateListener authStateListener; //estado da autenticacao

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();

        Button login = (Button) findViewById(R.id.btnlogar);
        Button cadastro = (Button) findViewById(R.id.btn_cadastrar);

        EstadodaAutenticacao();

        login.setOnClickListener(this);
        cadastro.setOnClickListener(this);

    }


    private void EstadodaAutenticacao() {
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser usuario = firebaseAuth.getCurrentUser();
                if (usuario!=null){
                    Toast.makeText(getBaseContext(),usuario.getEmail(),Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getBaseContext(),"Usuário não logado.",Toast.LENGTH_LONG).show();
                }
            }
        };
    }

    @Override
    public void onClick(View view) {
        user = auth.getCurrentUser();

        switch (view.getId()){
            case R.id.btnlogar:
                if (user!=null){
                    startActivity(new Intent(this, homeUser.class));
                }else {
                    startActivity(new Intent(this, Login.class));
                }
                break;
            case R.id.btn_cadastrar:
                if (user!=null){
                    Toast.makeText(getBaseContext(),"Usuário Logado.",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(this, homeUser.class));
                }else {
                    startActivity(new Intent(this, CadastroUser.class));
                }
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        // adicionando um ouvinte a variavel auth
        auth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // removendo um ouvinte a variavel auth
        if (authStateListener!=null)auth.removeAuthStateListener(authStateListener);
    }
}
