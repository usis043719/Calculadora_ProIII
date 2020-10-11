package com.ugb.calculadora;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class agregar_Productos extends AppCompatActivity {
    DB miDB;
    String accion = "nuevo";
    String idProducto = "0";
    ImageView imgFotoProduc;
    String urlCompletaImg;
    Button btnProductos;
    Intent takePictureIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar__productos);

        imgFotoProduc = findViewById(R.id.imgFotoProduc);

        btnProductos = findViewById(R.id.btnMostrarProducto);
        btnProductos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarListaproducto();
            }
        });
        guardarDatosproducto();
        mostrarDatosproducto();
        tomarFotoproducto();
    }
    void tomarFotoproducto(){
        imgFotoProduc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    //guardando la imagen
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    }catch (Exception ex){}
                    if (photoFile != null) {
                        try {
                            Uri photoURI = FileProvider.getUriForFile(agregar_Productos.this, "com.example.Calculadora.fileprovider", photoFile);
                            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                            startActivityForResult(takePictureIntent, 1);
                        }catch (Exception ex){
                            Toast.makeText(getApplicationContext(), "Error Toma Foto: "+ ex.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == 1 && resultCode == RESULT_OK) {
                Bitmap imageBitmap = BitmapFactory.decodeFile(urlCompletaImg);
                imgFotoProduc.setImageBitmap(imageBitmap);
            }
        }catch (Exception ex){
            Toast.makeText(getApplicationContext(), "Error: "+ ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "imagen_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if( storageDir.exists()==false ){
            storageDir.mkdirs();
        }
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        urlCompletaImg = image.getAbsolutePath();
        return image;
    }
    void guardarDatosproducto(){
        btnProductos = findViewById(R.id.btnGuardarProducto);
        btnProductos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView tempVal = findViewById(R.id.txtNombreProducto);
                String nombre = tempVal.getText().toString();

                tempVal = findViewById(R.id.txtMarcaproducto);
                String marca = tempVal.getText().toString();

                tempVal = findViewById(R.id.txtCategoriaProducto);
                String categoria= tempVal.getText().toString();

                tempVal = findViewById(R.id.txtPrecioProducto   );
                String precio = tempVal.getText().toString();

                if(!nombre.isEmpty() && !marca.isEmpty() && !categoria.isEmpty() && !precio.isEmpty()){

                String[] data = {idProducto,nombre,marca,categoria,precio,urlCompletaImg};

                miDB = new DB(getApplicationContext(),"", null, 1);
                miDB.mantenimientoproductos(accion, data);

                Toast.makeText(getApplicationContext(),"Registro de producto insertado con exito", Toast.LENGTH_LONG).show();
                mostrarListaproducto();
            }
                else {
                    Toast.makeText(getApplicationContext(), "ERROR: Ingrese los datos", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnProductos = (Button)findViewById(R.id.btnMostrarProducto );
        btnProductos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarListaproducto();
            }
        });
        mostrarDatosproducto();
    }
    void mostrarListaproducto(){
        Intent mostrarproducto = new Intent(agregar_Productos.this, MainActivity.class);
        startActivity(mostrarproducto);
    }
    void mostrarDatosproducto(){
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

                urlCompletaImg = dataProducto[5];
                Bitmap imageBitmap = BitmapFactory.decodeFile(urlCompletaImg);
                imgFotoProduc.setImageBitmap(imageBitmap);
            }
        }catch (Exception ex){
            ///
        }
    }
}