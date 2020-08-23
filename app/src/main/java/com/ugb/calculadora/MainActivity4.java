package com.ugb.calculadora;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

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
                Intent intent = new Intent (v.getContext(), MainActivity2.class);
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
        try {
        TextView tmpVal = (TextView)findViewById(R.id.etCANTIDAD);
        double CANTIDAD  = Double.parseDouble(tmpVal.getText().toString());
        Spinner SPINNER;

        double VALOR[][] = {
                //TIEMPO
                new double[]{1, 0.001,1.6667e-5, 2.7778e-7,1.1574e-8,1.6534e-9,3.8052e-10,3.171e-11,3.171e-12,3.171e-13},
                //TRANSFERENCIA_DATOS
                new double[]{1,0.001,1000000,0.01667,0.00006,60000000,3600,0.0036,360000000,86400,0.0864,864000000},
                //TEMPERATURA
                new double[]{1, 33.8,274.15, 493.47  },
        };
        int DE = 0, A = 0; double RESPUESTA = 0;
        switch (ThbConversores.getCurrentTabTag()){//INDICE
            case "TIEMPO":
                SPINNER = (Spinner)findViewById(R.id.spDETIEMPO);
                DE = SPINNER.getSelectedItemPosition();
                SPINNER = (Spinner)findViewById(R.id.spATIEMPO);
                A = SPINNER.getSelectedItemPosition();
                RESPUESTA = VALOR[0][A] / VALOR[0][DE] * CANTIDAD;
                break;
            case "TRANSFERENCIA_DATOS":
                SPINNER = (Spinner)findViewById(R.id.spDEDATOS);
                DE = SPINNER.getSelectedItemPosition();
                SPINNER = (Spinner)findViewById(R.id.spADATOS);
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
        }catch (Exception err){
            TextView temp = (TextView) findViewById(R.id.tvRESPUESTA);
            temp.setText("POR FAVOR INGRESE UNA CANTIDAD");
            Toast.makeText(getApplicationContext(),"INGRESE UNA CANTIDAD",Toast.LENGTH_LONG).show();
        }
    }
}