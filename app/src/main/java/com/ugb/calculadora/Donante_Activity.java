package com.ugb.calculadora;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Donante_Activity extends AppCompatActivity {
    public static final String user="names";
        TextView txtUser;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_donante_);


            txtUser =(TextView)findViewById(R.id.textser);
            String user = getIntent().getStringExtra("names");
            txtUser.setText("¡Bienvenido "+ user +"!");


        }
    }
