package com.example.publicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button login = (Button) findViewById(R.id.btnlogin);
        Button cadastro = (Button) findViewById(R.id.btn_cadastrar);

        login.setOnClickListener(this);
        cadastro.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnlogin:
                break;
            case R.id.btn_cadastrar:
                startActivity(new Intent(this, CadastroUser.class));
                break;
        }
    }
}
