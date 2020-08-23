package com.ugb.calculadora;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;

public class MainActivity3 extends AppCompatActivity {
    TabHost tbhConversores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

//////////////////BUTTON
        Button btn = (Button) findViewById(R.id.btnSIGUIENTE);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), MainActivity4.class);
                startActivityForResult(intent, 0);
            }
        });
///////////////////////////////////////////////////////////////////////////////7

        tbhConversores = (TabHost)findViewById(R.id.tbhConversores);
        tbhConversores.setup();
        ///TAG POSICION
        tbhConversores.addTab(tbhConversores.newTabSpec("LONGITUD").setContent(R.id.tabLONGITUD).setIndicator("Longitud"));
        tbhConversores.addTab(tbhConversores.newTabSpec("ALMACENAMIENTO").setContent(R.id.tabALMACENAMIENTO).setIndicator("Almacenamiento"));
        tbhConversores.addTab(tbhConversores.newTabSpec("COMBUSTIBLE").setContent(R.id.tabCOMBUSTIBLE).setIndicator("Combustible"));
    }
    public void CONVERTIR(View v){
        TextView tmpVal = (TextView)findViewById(R.id.etCANTIDAD);
        double CANTIDAD  = Double.parseDouble(tmpVal.getText().toString());
        Spinner SPINNER;

        double VALOR[][] = {

                //LONGITUD
                new double[]{1,100,0.001,1000,1e+6,1e+9,0.000621371,1.09361,3.28084,39.3701,0.000539957},
                //ALMACENAMIENTO
                new double[]{1,0.25,9.77e-4,9.54e-7,9.31e-10,9.09e-13,0.13,1.22e-4,1.19e-7,1.14e-13},
                //CONSUMO DE COMBUSTIBLE
                new double[]{1,2.352,2.825,},
        };
        int DE = 0, A = 0; double RESPUESTA = 0;
        switch (tbhConversores.getCurrentTabTag()){//INDICE
            case "LONGITUD":
                SPINNER = (Spinner)findViewById(R.id.spDLONGITUD);
                DE = SPINNER.getSelectedItemPosition();
                SPINNER = (Spinner)findViewById(R.id.spALONGITUD);
                A = SPINNER.getSelectedItemPosition();
                RESPUESTA = VALOR[0][A] / VALOR[0][DE] * CANTIDAD;
                break;
            case "ALMACENAMIENTO":
                SPINNER = (Spinner)findViewById(R.id.spDALMACENAMIENTO);
                DE = SPINNER.getSelectedItemPosition();
                SPINNER = (Spinner)findViewById(R.id.spAALMACENAMIENTO);
                A = SPINNER.getSelectedItemPosition();
                RESPUESTA = VALOR[1][A] / VALOR[1][DE] * CANTIDAD;
                break;

           case "COMBUSTIBLE":
               SPINNER = (Spinner)findViewById(R.id.spDCOMBUSTIBLE);
               DE = SPINNER.getSelectedItemPosition();
               SPINNER = (Spinner)findViewById(R.id.spACOMBUSTIBLE);
               A = SPINNER.getSelectedItemPosition();
               RESPUESTA = VALOR[2][A] / VALOR[2][DE] * CANTIDAD;
               break;
        }
        tmpVal = (TextView)findViewById(R.id.tvRESPUESTA);
        tmpVal.setText("RESPUESTA: "+ RESPUESTA);
    }
}
