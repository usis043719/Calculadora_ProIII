package com.ugb.calculadora;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    Conversor conversor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabHost tbhConversor = (TabHost) findViewById(R.id.tbhCONVERSORES);
        tbhConversor.setup();
        String str = "Universal";
        tbhConversor.addTab(tbhConversor.newTabSpec(str).setIndicator(str, null).setContent(R.id.tabCONVERSORPROPIO));
        String str2 = "Area";
        tbhConversor.addTab(tbhConversor.newTabSpec(str2).setIndicator(str2, null).setContent(R.id.tabAREA));
    }
    public void procesar(View v) {

        TextView temp = (TextView) findViewById(R.id.etUNIDADES);
        String str = "";
        int UNIDADES = temp.getText().toString().equals(str) ? 1 : Integer.parseInt(temp.getText().toString());
        TextView temp2 = (TextView) findViewById(R.id.etCANTIDAD);
        TextView valor = (TextView) findViewById(R.id.etUNIDADES2);
        String str2 = "/";
        if (!temp2.getText().toString().equals(str))
        {
            int CANTIDAD = Integer.parseInt(temp2.getText().toString());
            int CAJAS = CANTIDAD / UNIDADES;
            StringBuilder sb = new StringBuilder();
            sb.append(CAJAS);
            sb.append(str2);
            sb.append(CANTIDAD % UNIDADES);
            valor.setText(sb.toString());

    }
}
}