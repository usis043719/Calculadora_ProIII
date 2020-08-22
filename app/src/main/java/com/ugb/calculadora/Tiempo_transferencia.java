package com.ugb.calculadora;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;

public class Tiempo_transferencia extends AppCompatActivity {
    TabHost ThbConversores;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiempo_transferencia);
        //////////////////BUTTON
        Button btn = (Button) findViewById(R.id.btnSIGUIENTE);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), Tiempo_transferencia.class);
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
                new double[]{1, 0.84,22.13,8.75,6.89,34.79,7.70,3.57,24.65,106.05},
                //TRANSFERENCIA_DATOS
                new double[]{1,1000,1e+6,1e+9,0.000984207,0.00110231,0.157473,2.20462,35.274,0.001},
                //TEMPERATURA
                new double[]{1,0.001,1000,0.219969,1.75975,3.51951,61.0237,0.0353147,67.628,0.264172},
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
}