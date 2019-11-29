package com.example.publicapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import static android.content.Context.CONNECTIVITY_SERVICE;

// Classe onde são criados métodos que serão usados em mais de uma Activity



public class Util {

    public static boolean verificarInternet(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()){
            return true;
        }else{
            return false;
        }
    }

    public static void opcoesdeerros(Context context, String resp) {
        if (resp.contains("least 6 characters")){
            Toast.makeText(context, "Senha Inválida.",Toast.LENGTH_LONG).show();
        }else if(resp.contains("address is badly")){
            Toast.makeText(context, "E-mail Inválido.",Toast.LENGTH_LONG).show();
        }else if (resp.contains("address is already")){
            Toast.makeText(context, "E-mail já Cadastrado.",Toast.LENGTH_LONG).show();
        }else if (resp.contains("interrupted connection")){
            Toast.makeText(context, "Sem Conexão, Verifique as Configurações de seu Molde de Internet.",Toast.LENGTH_LONG).show();
        }else if (resp.contains("There is no user")){
            Toast.makeText(context, "Usuario não cadastrado.",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(context, resp,Toast.LENGTH_LONG).show();
        }
    }
}
