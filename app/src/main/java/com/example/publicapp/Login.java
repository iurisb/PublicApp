package com.example.publicapp;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity implements View.OnClickListener {

    FirebaseAuth auth;
    EditText edtxtemail, edtxtpwd;
    Button btnLogar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtxtemail = (EditText) findViewById(R.id.edtxt_emaillogin);
        edtxtpwd = (EditText) findViewById(R.id.edtxt_senhalogin);
        btnLogar = (Button) findViewById(R.id.btnlogar);

        auth = FirebaseAuth.getInstance();
        btnLogar.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnlogar:
                logarUsuario();
                break;
        }

    }

    private void logarUsuario() {
        String email = edtxtemail.getText().toString().trim();
        String pwd = edtxtpwd.getText().toString().trim();

        if (email.isEmpty() || pwd.isEmpty()){
            Toast.makeText(getBaseContext(), "Preencha os campos obrigatórios!",Toast.LENGTH_LONG).show();
        }else {
            if (verificarInternet()){
                auth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            startActivity(new Intent(getBaseContext(),homeUser.class));
                            finish();
                         }else {
                            String erro = task.getException().toString();
                            //teste
                            opcoesdeerros(erro);
                        }
                    }
                });
            }else {
                Toast.makeText(getBaseContext(), "Seu Aparelho esta sem conexão com a Internet",Toast.LENGTH_LONG).show();
            }
        }
    }

    private boolean verificarInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()){
            return true;
        }else{
            return false;
        }
    }

    private void opcoesdeerros(String resp) {
        if (resp.contains("password is invalid")){
            Toast.makeText(getBaseContext(), "Senha Inválida.",Toast.LENGTH_LONG).show();
        }else if(resp.contains("address is badly")){
            Toast.makeText(getBaseContext(), "E-mail Inválido.",Toast.LENGTH_LONG).show();
        }else if (resp.contains("There is no user")){
            Toast.makeText(getBaseContext(), "Usuario não cadastrado.",Toast.LENGTH_LONG).show();
        }else if (resp.contains("interrupted connection")){
            Toast.makeText(getBaseContext(), "Sem Conexão, Verifique as Configurações de seu Molde de Internet.",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(getBaseContext(), resp,Toast.LENGTH_LONG).show();
        }
    }
}