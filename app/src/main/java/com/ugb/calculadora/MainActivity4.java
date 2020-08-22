package com.ugb.calculadora;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;

public class MainActivity4 extends AppCompatActivity {
    TabHost ThbConversores;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
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

        ThbConversores = (TabHost)findViewById(R.id.ThbConversores);
        ThbConversores.setup();
        ///TAG POSICION
        ThbConversores.addTab(ThbConversores.newTabSpec("TIEMPO").setContent(R.id.Tiempo).setIndicator("Tiempo"));
        ThbConversores.addTab(ThbConversores.newTabSpec("TRANSFERENCIA_DATOS").setContent(R.id.Transferncia_datos).setIndicator("Transfrencia de datos"));
        ThbConversores.addTab(ThbConversores.newTabSpec("TEMPERATURA").setContent(R.id.Temperatura).setIndicator("Temperatura"));
    }
    public void CONVERTIR(View v){
        TextView tmpVal = (TextView)findViewById(R.id.etCANTIDAD);
        double CANTIDAD  = Double.parseDouble(tmpVal.getText().toString());
        Spinner SPINNER;

        double VALOR[][] = {
                //TIEMPO
                new double[]{1, 0.001,0.0166667, 0.0166667,0.0416667,0.142857,0.230137,0.0833334,0.1,0.1},
                //TRANSFERENCIA_DATOS
                new double[]{1,0.001,1.25e+8,6e-5,1e-6,1e+12, 6e-5, 1e-6, 1e+12, 2.4e-5, 1e-6, 1e+12},
                //TEMPERATURA
                new double[]{1, 33.8,255.928, 1.8, },
        };
        int DE = 0, A = 0; double RESPUESTA = 0;
        switch (ThbConversores.getCurrentTabTag()){//INDICE
            case "TIEMPO":
                SPINNER = (Spinner)findViewById(R.id.spTIEMPO);
                DE = SPINNER.getSelectedItemPosition();
                SPINNER = (Spinner)findViewById(R.id.spTIEMPO);
                A = SPINNER.getSelectedItemPosition();
                RESPUESTA = VALOR[0][A] / VALOR[0][DE] * CANTIDAD;
                break;
            case "TRANSFERENCIA_DATOS":
                SPINNER = (Spinner)findViewById(R.id.spDATOS);
                DE = SPINNER.getSelectedItemPosition();
                SPINNER = (Spinner)findViewById(R.id.spDATOS);
                A = SPINNER.getSelectedItemPosition();
                RESPUESTA = VALOR[1][A] / VALOR[1][DE] * CANTIDAD;
                break;
            case "TEMPERATURA":
                SPINNER = (Spinner)findViewById(R.id.spDETEMPERATURAS);
                DE = SPINNER.getSelectedItemPosition();
                SPINNER = (Spinner)findViewById(R.id.spATEMPERATURA);
                A = SPINNER.getSelectedItemPosition();
                RESPUESTA = VALOR[2][A] / VALOR[2][DE] * CANTIDAD;
                break;
        }
        tmpVal = (TextView)findViewById(R.id.tvRESPUESTA);
        tmpVal.setText("RESPUESTA: "+ RESPUESTA);
    }
}
