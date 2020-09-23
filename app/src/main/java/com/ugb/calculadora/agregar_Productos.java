package com.ugb.calculadora;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class agregar_Productos extends AppCompatActivity {
    DB miDB;
    String accion = "nuevo";
    String idProducto = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar__productos);

        Button btnGuardarProducto = (Button)findViewById(R.id.btnGuardarProducto);
        btnGuardarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView tempVal = (TextView)findViewById(R.id.txtNombreProducto);
                String nombre = tempVal.getText().toString();

                tempVal = (TextView)findViewById(R.id.txtMarcaproducto);
                String marca = tempVal.getText().toString();

                tempVal = (TextView)findViewById(R.id.txtCategoriaProducto);
                String categoria = tempVal.getText().toString();

                tempVal = (TextView)findViewById(R.id.txtPrecioProducto);
                String precio = tempVal.getText().toString();

                String[] data = {idProducto,nombre,marca,categoria,precio};

               // miDB = new DB(getApplicationContext(),"", null, 1);
               // miDB.mantenimientoProductos(accion, data);

                Toast.makeText(getApplicationContext(),"Registro de producto insertado con exito", Toast.LENGTH_LONG).show();
                Intent mostrarProductos = new Intent(agregar_Productos.this, MainActivity.class);
                startActivity(mostrarProductos);
            }
        });
        mostrarDatosProducto();
    }
    void mostrarDatosProducto(){
        try {
            Bundle recibirParametros = getIntent().getExtras();
            accion = recibirParametros.getString("accion");
            if (accion.equals("modificar")){
                String[] dataProducto = recibirParametros.getStringArray("dataProducto");

                idProducto = dataProducto[0];

                TextView tempVal = (TextView)findViewById(R.id.txtNombreProducto);
                tempVal.setText(dataProducto[1]);

                tempVal = (TextView)findViewById(R.id.txtMarcaproducto);
                tempVal.setText(dataProducto[2]);

                tempVal = (TextView)findViewById(R.id.txtCategoriaProducto);
                tempVal.setText(dataProducto[3]);

                tempVal = (TextView)findViewById(R.id.txtPrecioProducto);
                tempVal.setText(dataProducto[4]);
            }
        }catch (Exception ex){
            ///
        }
    }
}