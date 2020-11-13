package com.ugb.calculadora;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //defining view objects
    private EditText TextEmail;
    private EditText TextPassword;
    private Button btnRegistrar, btnLogin;
    private ProgressDialog progressDialog;


    //Declaramos un objeto firebaseAuth
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //inicializamos el objeto firebaseAuth
        firebaseAuth = FirebaseAuth.getInstance();

        //Referenciamos los views
        TextEmail = (EditText) findViewById(R.id.TxtEmail);
        TextPassword = (EditText) findViewById(R.id.TxtPassword);

        btnRegistrar = (Button) findViewById(R.id.botonRegistrar);
        btnLogin = (Button) findViewById(R.id.botonLogin);


        progressDialog = new ProgressDialog(this);

        //attaching listener to button
        btnRegistrar.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
    }

    private void registrarUsuario(){

        //Obtenemos el email y la contraseña desde las cajas de texto
        String email = TextEmail.getText().toString().trim();
        String password  = TextPassword.getText().toString().trim();

        //Verificamos que las cajas de texto no esten vacías
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Se debe ingresar un correo de Gmail",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Falta ingresar la contraseña",Toast.LENGTH_LONG).show();
            return;
        }


        progressDialog.setMessage("Realizando registro en linea...");
        progressDialog.show();

        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if(task.isSuccessful()){

                            Toast.makeText(MainActivity.this,"Se ha registrado el usuario con el email: " + TextEmail.getText(),Toast.LENGTH_LONG).show();
                        }else{
                           if (task.getException() instanceof FirebaseAuthUserCollisionException){//si se presenta una colision
                              Toast.makeText(MainActivity.this, "Este usuario ya existe", Toast.LENGTH_SHORT).show();
                           }else{
                            Toast.makeText(MainActivity.this,"No se pudo registrar el usuario ingrese la @,el dominio y una contraseña con 6 o mas caracteres",Toast.LENGTH_LONG).show();
                           }
                        }
                        progressDialog.dismiss();
                    }
                });

    }


    //PARA INICIAR SESION





private void  loguearUsuario(){
    //Obtenemos el email y la contraseña desde las cajas de texto
    final String email = TextEmail.getText().toString().trim();
    String password  = TextPassword.getText().toString().trim();

    //Verificamos que las cajas de texto no esten vacías
    if(TextUtils.isEmpty(email)){
        Toast.makeText(this,"Se debe ingresar un correo de Gmail",Toast.LENGTH_LONG).show();
        return;
    }

    if(TextUtils.isEmpty(password)){
        Toast.makeText(this,"Falta ingresar la contraseña",Toast.LENGTH_LONG).show();
        return;
    }


    progressDialog.setMessage("Iniciando sesion...");
    progressDialog.show();

    //INICIO DE SESION
    firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    //checking if success
                    if(task.isSuccessful()){

//detalles
                        Toast.makeText(MainActivity.this,"Inicio Sesion " + TextEmail.getText(),Toast.LENGTH_LONG).show();
                        Intent intencion = new Intent (getApplication(), Donante_Activity.class);
                        intencion.putExtra(Donante_Activity.user, email);
                        startActivity(intencion);

                    }else{
                        if (task.getException() instanceof FirebaseAuthUserCollisionException){//si se presenta una colision
                            Toast.makeText(MainActivity.this, "Este usuario ya existe", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(MainActivity.this,"Usuario o contraseña incorrecto",Toast.LENGTH_LONG).show();
                        }
                    }
                    progressDialog.dismiss();
                }
            });

}

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.botonRegistrar:
        //Invocamos al método:
              registrarUsuario();
              break;
            case R.id.botonLogin:
                loguearUsuario();
                break;

        }
    }
}