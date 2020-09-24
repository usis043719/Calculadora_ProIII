package com.ugb.calculadora;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
        public Button btnCalcular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


                btnCalcular = (Button) findViewById(R.id.BtnCalc);
                btnCalcular.setOnClickListener(new View.OnClickListener()  {
                    @Override
                    public void onClick(View view) {

                        Calcular(view);
            }
        });


    }
    public void Calcular (View view)
    {
        try {
            RadioGroup optOperaciones = (RadioGroup) findViewById(R.id.optOperaciones);
            Spinner CbOperaciones = (Spinner)findViewById(R.id.CbOperaciones);



            TextView tempVal = (TextView) findViewById(R.id.Decimal1);
            double num1 = Double.parseDouble(tempVal.getText().toString());

            tempVal = (TextView) findViewById(R.id.Decimal2);
            double num2 = Double.parseDouble(tempVal.getText().toString());

            double respuesta = 0;

            //este es para el Radiogroups y RadioButtons

            switch (optOperaciones.getCheckedRadioButtonId()) {
                case R.id.RbSuma:

                    respuesta = num1 + num2;

                    break;

                case R.id.RbResta   :

                    respuesta = num1 - num2;

                    break;

                case R.id.RbMult:

                    respuesta = num1 * num2;

                    break;

                case R.id.RbDivic:

                    respuesta = num1 / num2;

                    break;

                case R.id.RbPorcen:

                    respuesta = (num1 * num2) / 100;

                    break;

                case R.id.RbExpo:

                    respuesta = Math.pow(num1,num2);

                    break;

                case R.id.RbMod:

                    respuesta = num1 % num2;
                    break;


                case R.id.RbFactor:

                    int facto = 1;

                    for (int i = 2; i<= num1; i++)

                    {
                        facto = facto * i;

                        respuesta = facto;
                    }
            }

            //Este es para el Spinner----Combobox

            switch (CbOperaciones.getSelectedItemPosition())
            {
                case 1:

                    respuesta = num1 + num2;
                    break;

                case 2:

                    respuesta = num1 - num2;
                    break;

                case 3:

                    respuesta = num1 * num2;
                    break;

                case 4:

                    respuesta = num1 / num2;
                    break;

                case 5:

                    respuesta = (num1 * num2) / 100;
                    break;

                case 6:

                    respuesta = Math.pow(num1,num2);
                    break;

                case 7:

                    respuesta = num1 % num2;
                    break;

                case 8:

                    int Facto = 1;

                    for (int i = 2; i <= num1; i++)

                    {
                        Facto = Facto * i;

                        respuesta = Facto;
                    }
            }

            tempVal = (TextView) findViewById(R.id.LblRespuesta);
            tempVal.setText("La respuesta es: " + respuesta);
        } catch (Exception error) {

            Toast.makeText(getApplicationContext(),"Ingrese los dos numeros", Toast.LENGTH_LONG).show();
        }
    }

}
//prueba