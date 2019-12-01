package com.example.publicapp;

import android.content.Intent;
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
    Button btnLogar, btnEsqueciSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtxtemail = (EditText) findViewById(R.id.edtxt_emaillogin);
        edtxtpwd = (EditText) findViewById(R.id.edtxt_senhalogin);
        btnLogar = (Button) findViewById(R.id.btnlogar);
        btnEsqueciSenha = (Button) findViewById(R.id.btn_esquecisenha);

        auth = FirebaseAuth.getInstance();
        btnLogar.setOnClickListener(this);
        btnEsqueciSenha.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnlogar:
                logarUsuario();
                break;
            case R.id.btn_esquecisenha:
                recuperarSenha();
                break;

        }

    }

    private void recuperarSenha() {
        final String email = edtxtemail.getText().toString().trim();
        if (email.isEmpty()){
            Toast.makeText(getBaseContext(), "Preencha o campo de E-mail",Toast.LENGTH_LONG).show();
        }else {
            if(Util.verificarInternet(getBaseContext())){
                auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(getBaseContext(), "Instruções enviadas para o email: " + email + ".",Toast.LENGTH_LONG).show();
                        }else {
                            Util.opcoesdeerros(getBaseContext(), task.getException().toString());
                        }
                    }
                });
            }
        }

    }


    private void logarUsuario() {
        String email = edtxtemail.getText().toString().trim();
        String pwd = edtxtpwd.getText().toString().trim();

        if (email.isEmpty() || pwd.isEmpty()){
            Toast.makeText(getBaseContext(), "Preencha os campos obrigatórios!",Toast.LENGTH_LONG).show();
        }else {
            if (Util.verificarInternet(this)){
                auth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            startActivity(new Intent(getBaseContext(),homeUser.class));
                            finish();
                         }else {
                            String erro = task.getException().toString();
                            Util.opcoesdeerros(getBaseContext(),erro);
                        }
                    }
                });
            }else {
                Toast.makeText(getBaseContext(), "Seu Aparelho esta sem conexão com a Internet",Toast.LENGTH_LONG).show();
            }
        }
    }
}
