package com.example.publicapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth auth;
    private EditText edtxtemail, edtxtpwd;
    private Button btnLogar, btnEsqueciSenha;
    private CardView cardView_googleLogin;
    private GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtxtemail = (EditText) findViewById(R.id.edtxt_emaillogin);
        edtxtpwd = (EditText) findViewById(R.id.edtxt_senhalogin);
        btnLogar = (Button) findViewById(R.id.btnlogar);
        btnEsqueciSenha = (Button) findViewById(R.id.btn_esquecisenha);
        cardView_googleLogin = (CardView) findViewById(R.id.cardview_logingoogle);

        auth = FirebaseAuth.getInstance();
        IniciarServicoGoogle();

        btnLogar.setOnClickListener(this);
        btnEsqueciSenha.setOnClickListener(this);
        cardView_googleLogin.setOnClickListener(this);

    }

    private void IniciarServicoGoogle() {
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this,gso);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cardview_logingoogle:
                logarComContaGoogle();
                break;
            case R.id.btnlogar:
                logarUsuario();
                break;
            case R.id.btn_esquecisenha:
                recuperarSenha();
                break;

        }

    }

    private void logarComContaGoogle() {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account == null){

            startActivityForResult(googleSignInClient.getSignInIntent(), 587);

        }else {
            Toast.makeText(getBaseContext(), "Usuario Logado.",Toast.LENGTH_LONG).show();
            startActivity(new Intent(getBaseContext(),homeUser.class));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 587){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                startActivity(new Intent(getBaseContext(),homeUser.class));
            } catch (ApiException e) {
                Toast.makeText(getBaseContext(), "Erro: " + e.toString(),Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
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
