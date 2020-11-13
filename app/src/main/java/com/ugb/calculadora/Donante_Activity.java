package com.ugb.calculadora;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Donante_Activity extends AppCompatActivity {

    //No tocar
    public static final String user="names";
        TextView txtUser;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_donante_);

            ///Codigo del login msgbox
            txtUser =(TextView)findViewById(R.id.textser);
            //String user = getIntent().getStringExtra("names");
           // txtUser.setText("¡Bienvenid@ " + user +"!");
            //No tocar


            

        }

}
















































