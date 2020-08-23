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

public class MainActivity2 extends AppCompatActivity {
    TabHost tbhConversores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //////////////////BUTTON
        Button btn = (Button) findViewById(R.id.btnSIGUIENTE);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), MainActivity3.class);
                startActivityForResult(intent, 0);
            }
        });
///////////////////////////////////////////////////////////////////////////////7

        tbhConversores = (TabHost)findViewById(R.id.tbhConversores);
        tbhConversores.setup();
        ///TAG POSICION
        tbhConversores.addTab(tbhConversores.newTabSpec("MONEDAS").setContent(R.id.tabMONEDAS).setIndicator("Monedas"));
        tbhConversores.addTab(tbhConversores.newTabSpec("MASA").setContent(R.id.tabMASA).setIndicator("Masa"));
        tbhConversores.addTab(tbhConversores.newTabSpec("VOLUMEN").setContent(R.id.tabVOLUMEN).setIndicator("Volumen"));
    }
    public void CONVERTIR(View v){
        try {
        TextView tmpVal = (TextView)findViewById(R.id.etCANTIDAD);
        double CANTIDAD  = Double.parseDouble(tmpVal.getText().toString());
        Spinner SPINNER;

        double VALOR[][] = {
                //MONEDAS
                new double[]{1, 0.84,22.13,8.75,6.89,34.79,7.70,3.57,24.65,106.05},
                //MASA
                new double[]{1,1000,1e+6,1e+9,0.000984207,0.00110231,0.157473,2.20462,35.274,0.001},
                //VOLUMEN
                new double[]{1,0.001,1000,0.219969,1.75975,3.51951,61.0237,0.0353147,67.628,0.264172},
                //LONGITUD
        };
        int DE = 0, A = 0; double RESPUESTA = 0;
        switch (tbhConversores.getCurrentTabTag()){//INDICE
            case "MONEDAS":
                SPINNER = (Spinner)findViewById(R.id.spDEMONEY);
                DE = SPINNER.getSelectedItemPosition();
                SPINNER = (Spinner)findViewById(R.id.spAMONEY);
                A = SPINNER.getSelectedItemPosition();
                RESPUESTA = VALOR[0][A] / VALOR[0][DE] * CANTIDAD;
                break;
            case "MASA":
                SPINNER = (Spinner)findViewById(R.id.spDEMASA);
                DE = SPINNER.getSelectedItemPosition();
                SPINNER = (Spinner)findViewById(R.id.spAMASA);
                A = SPINNER.getSelectedItemPosition();
                RESPUESTA = VALOR[1][A] / VALOR[1][DE] * CANTIDAD;
                break;
            case "VOLUMEN":
                SPINNER = (Spinner)findViewById(R.id.spDEVOLUMEN);
                DE = SPINNER.getSelectedItemPosition();
                SPINNER = (Spinner)findViewById(R.id.spAVOLUMEN);
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