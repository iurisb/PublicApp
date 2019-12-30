package com.example.publicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class index extends AppCompatActivity implements Runnable{

    private ProgressBar progressBar;
    private Handler handler;
    private Thread thread;
    private int i;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);


        auth = FirebaseAuth.getInstance();
        progressBar =(ProgressBar) findViewById(R.id.progressBarPublic);

        handler = new Handler();
        thread = new Thread(this);
        thread.start();


    }

    @Override
    public void run() {
        i=1;
        try {
            while (i<=100){
                Thread.sleep(15);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        i++;
                        progressBar.setProgress(i);
                    }
                });
            }

            FirebaseUser user = auth.getCurrentUser();
            if (user==null){
                finish();
                startActivity(new Intent(getBaseContext(),MainActivity.class));
            }else {
                finish();
                startActivity(new Intent(getBaseContext(),homeUser.class));
            }

        }catch (InterruptedException e){

        }
    }
}
