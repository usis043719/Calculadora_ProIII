package com.ugb.calculadora;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    String [][] moneda = {
            {"Seleccione Conversor"},
            {"Bitcoin","Colon salvadoreño", "Euro", },
            {"", "San Jorge", "San Rafael"},
            {"SRL","La Union","Anamoros","El Carmen"},
            {""}
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabHost tbhConversores = (TabHost)findViewById(R.id.tabconversores);
        tbhConversores.setup();

        tbhConversores.addTab(tbhConversores.newTabSpec("Direccion").setContent(R.id.tbsMasa).setIndicator("DIRECCION", null));
        tbhConversores.addTab(tbhConversores.newTabSpec("Pais").setContent(R.id.tbsVolumen).setIndicator("PAIS", null));

        final Spinner spnMun = (Spinner)findViewById(R.id.spnconv);

            }
    }

