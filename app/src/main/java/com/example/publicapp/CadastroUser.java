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

public class CadastroUser extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth auth;
    private EditText edtxt_email, edtxt_pwd, edtxt_cpwd;
    private Button btn_voltar, btn_cadastrarF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrouser);

        edtxt_email = (EditText) findViewById(R.id.edtxt_emailCadastro);
        edtxt_pwd = (EditText) findViewById(R.id.edtxt_pwdCadastro);
        edtxt_cpwd = (EditText) findViewById(R.id.edtxt_pwdConfirmacaocadastro);
        btn_voltar = (Button) findViewById(R.id.btn_voltar);
        btn_cadastrarF = (Button) findViewById(R.id.btn_novocadastrodeusuario);

        auth = FirebaseAuth.getInstance();

        btn_voltar.setOnClickListener(this);
        btn_cadastrarF.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_voltar:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.btn_novocadastrodeusuario:
                criarUsuario();
                break;
        }

    }

    private void criarUsuario() {
        String email = edtxt_email.getText().toString().trim();
        String pwd = edtxt_pwd.getText().toString().trim();
        String cpwd = edtxt_cpwd.getText().toString().trim();

        if (email.isEmpty() || pwd.isEmpty() || cpwd.isEmpty()){
            Toast.makeText(this, "Preencha todos os Campos! Obrigado.",Toast.LENGTH_LONG).show();
        }else if (!pwd.equals(cpwd)){
            Toast.makeText(this, "Senhas precisam ser iguais.",Toast.LENGTH_LONG).show();
        }else {
            auth.createUserWithEmailAndPassword(email,pwd).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(getBaseContext(), "Cadastro realizado com sucesso",Toast.LENGTH_LONG).show();
                    }else {
                        String resposta = task.getException().toString();
                        opcoesdeerros(resposta);
                    }
                }
            });
        }

    }

    private void opcoesdeerros(String resp) {
        if (resp.contains("least 6 characters")){
            Toast.makeText(getBaseContext(), "Senha Inválida.",Toast.LENGTH_LONG).show();
        }else if(resp.contains("address is badly")){
            Toast.makeText(getBaseContext(), "E-mail Inválido.",Toast.LENGTH_LONG).show();
        }else if (resp.contains("address is already")){
            Toast.makeText(getBaseContext(), "E-mail já Cadastrado.",Toast.LENGTH_LONG).show();
        }else if (resp.contains("interrupted connection")){
            Toast.makeText(getBaseContext(), "Sem Conexão, Verifique as Configurações de seu Molde de Internet.",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(getBaseContext(), resp,Toast.LENGTH_LONG).show();
        }
    }


}
