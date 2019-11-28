package com.example.publicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class homeUser extends AppCompatActivity implements View.OnClickListener {

    private Button deslogar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homeuser);

        deslogar = (Button) findViewById(R.id.btndeslogar);

        deslogar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btndeslogar:
                FirebaseAuth.getInstance().signOut();
                finish();
                break;
        }
    }
}
