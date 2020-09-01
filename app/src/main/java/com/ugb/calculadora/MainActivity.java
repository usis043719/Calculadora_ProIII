package com.ugb.calculadora;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    SensorManager sensorManager;
    Sensor sensor;
    SensorEventListener sensorEvenListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
       sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        if(sensor==null){
            finish();
        }
        final TextView lblSensorProximida = (TextView)findViewById(R.id.lblSensorProximida);
        sensorEvenListener = new SensorEventListener() {

            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if( sensorEvent.values[0]>=0 && sensorEvent.values[0]<=4 ){
                    getWindow().getDecorView().setBackgroundColor(Color.RED);
                    lblSensorProximida.setText("Muy Cerca: "+ sensorEvent.values[0]);
                } else if(sensorEvent.values[0]>4 && sensorEvent.values[0]<=8 ){
                    getWindow().getDecorView().setBackgroundColor(Color.GREEN);
                    lblSensorProximida.setText("Lejos: "+ sensorEvent.values[0]);
              //  } else {
              //      getWindow().getDecorView().setBackgroundColor(Color.Gray);
              //      lblSensorProximida.setText("Muy Lejos: "+ sensorEvent.values[0]);
               }
        }
            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {
            }
        };
        iniciar();
    }
    void iniciar(){
        sensorManager.registerListener(sensorEvenListener, sensor, 2000*1000);
    }
    void detener(){
        sensorManager.unregisterListener(sensorEvenListener);
    }
    @Override
    protected void onPause() {
        detener();
        super.onPause();
    }
    @Override
    protected void onResume() {
        iniciar();
        super.onResume();
    }
}