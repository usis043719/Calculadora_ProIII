package com.ugb.calculadora;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    JSONArray datosJSON;
    JSONObject JsonObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        obtenerDatostienda objObtenerproductos =new obtenerDatostienda();
        objObtenerproductos.execute();
    }
    private class obtenerDatostienda extends AsyncTask<Void,Void, String>{
        HttpURLConnection urLconection;
        @Override
        protected String doInBackground(Void... voids) {
            StringBuilder result = new StringBuilder();
            try {
                URL url = new URL("Http://10.0.2.2:5984/db_tienda/_design/tienda/_view/mi-tienda");
                urLconection = (HttpURLConnection) url.openConnection();//se conecta al servidor
                urLconection.setRequestMethod("GET");

                InputStream in = new BufferedInputStream(urLconection.getInputStream());//obtener datos
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String linea; //lear los datos
                while ((linea = reader.readLine()) != null) {
                    result.append(linea);
                }
            } catch (Exception ex) {
                //
            }
            return result.toString();
        }

        @Override
        protected void onPostExecute(String s) {//recibr los datos
            super.onPostExecute(s);
            try {
                JsonObject = new JSONObject(s);
                datosJSON = JsonObject.getJSONArray("rows");
                mostrarDatosProductos();
            } catch (Exception ex) {
                Toast.makeText(MainActivity.this, "Error la parsear los datos: " + ex.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }
    private void mostrarDatosProductos(){
        ListView ltsProductosCouchDB = findViewById(R.id.ltsProductosCouchDB);
        try {
            final ArrayList<String>arrayList = new ArrayList<>();
            final ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, arrayList);
            ltsProductosCouchDB.setAdapter(stringArrayAdapter);

            for (int i = 0; i < datosJSON.length(); i++) {
                stringArrayAdapter.add(datosJSON.getJSONObject(i).getJSONObject("value").getString("Nombre"));
            }
            stringArrayAdapter.notifyDataSetChanged(); //reactualizar datos
            registerForContextMenu(ltsProductosCouchDB);
        }catch (Exception ex){
            Toast.makeText(MainActivity.this, "Error al mostrar los datos: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

}
