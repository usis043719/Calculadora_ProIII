package com.ugb.calculadora;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Donante_Activity extends AppCompatActivity {
    ImageView imgFoto;
    Intent takePictureIntent;
    String urlCompletaImg;
    String urlCompletaImgFirestore;
    DatabaseReference mDatabse;
    MyFirebaseInstanceIdService myFirebaseInstanceIdService = new MyFirebaseInstanceIdService();
    String miToken;

    //No tocar login
    public static final String user="names";
        TextView txtUser;

        @Override
        protected void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_donante_);

            ///Codigo del login msgbox
            txtUser =(TextView)findViewById(R.id.textser);
            //String user = getIntent().getStringExtra("names");
           // txtUser.setText("¡Bienvenid@ " + user +"!");
            //No tocar

            imgFoto = findViewById(R.id.imgFoto);
            tomarFoto();
            try {
                mDatabse = FirebaseDatabase.getInstance().getReference("usuarios");
                miToken = myFirebaseInstanceIdService.miToken;
                Button btnGuardarRegistro = findViewById(R.id.btnGuardarDonante);
                btnGuardarRegistro.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            subirFileFirestore();
                        } catch (Exception ex) {
                            Toast.makeText(getApplicationContext(), "Erro al intentar guardar el registro: " + ex.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }catch (Exception ex){
                Toast.makeText(getApplicationContext(), "Erro: "+ ex.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    private void subirFileFirestore(){
        Toast.makeText(getApplicationContext(), "Te informaremos cuando la foto se suba a firestoire",Toast.LENGTH_SHORT).show();
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        Uri file = Uri.fromFile(new File(urlCompletaImg));
        final StorageReference riversRef = storageRef.child("images/"+file.getLastPathSegment());

        final UploadTask uploadTask = riversRef.putFile(file);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Fallo el intento de subir la foto a firestore: "+ e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getApplicationContext(), "Listo se subio la foto a firestore",Toast.LENGTH_SHORT).show();
                Task<Uri> downloadUri = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        return riversRef.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful()) {
                            urlCompletaImgFirestore = task.getResult().toString();
                            guardarDatosFirebase();
                        }
                    }
                });
            }
        });
    }
    private void guardarDatosFirebase(){
        try {
            TextView tempVal = findViewById(R.id.txtNombreDonante);
            String Nombre = tempVal.getText().toString();

            TextView tempVal5 = findViewById(R.id.txtCorreo);
            String correo = tempVal5.getText().toString();

            TextView tempVal1 = findViewById(R.id.txtEdad);
            String edad = tempVal1.getText().toString();

            TextView tempVal3 = findViewById(R.id.txtTelefono);
            String telefono = tempVal3.getText().toString();

            TextView tempVal2 = findViewById(R.id.txtDireccion);
            String direccion = tempVal2.getText().toString();

            TextView tempVal4 = findViewById(R.id.txtTipo);
            String tipoSangre = tempVal4.getText().toString(),

                    id = mDatabse.push().getKey();
            donantes user = new donantes(Nombre,correo, urlCompletaImg, urlCompletaImgFirestore, miToken ,edad ,telefono,direccion,tipoSangre);
            if (id != null) {
                mDatabse.child(id).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(), "Registro Guardado con exito", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), lista_donantes.class);
                        startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Erro al intentar crear el registro en la base de datos: "+e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            } else {
                Toast.makeText(getApplicationContext(), "Erro al intentar crear el registro en la base de datos", Toast.LENGTH_LONG).show();
            }
        }catch (Exception ex){

        }
    }
    void tomarFoto(){
        imgFoto.setOnClickListener(new View.OnClickListener() {
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
                            Uri photoURI = FileProvider.getUriForFile(Donante_Activity.this, "com.ugb.calculadora.fileprovider", photoFile);
                            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                            startActivityForResult(takePictureIntent, 1);
                        }catch (Exception ex){
                            Toast.makeText(getApplicationContext(), "Erro Toma Foto: "+ ex.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == 1 && resultCode == RESULT_OK) {
                Bitmap imageBitmap = BitmapFactory.decodeFile(urlCompletaImg);
                imgFoto.setImageBitmap(imageBitmap);
            }
        }catch (Exception ex){
            Toast.makeText(getApplicationContext(), "Erro: "+ ex.getMessage(), Toast.LENGTH_LONG).show();
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
        urlCompletaImg = image.getAbsolutePath();
        return image;
    }
}























































