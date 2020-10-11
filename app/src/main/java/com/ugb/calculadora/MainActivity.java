package com.ugb.calculadora;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
//https://developer.android.com/training/camera/photobasics?hl=es-419#java
public class MainActivity extends AppCompatActivity {
        DB miBD;
        Cursor misProductos;
        Productos Producto;
        ArrayList<Productos> stringArrayList = new ArrayList<Productos>();
        ArrayList<Productos> copyStringArrayList = new ArrayList<Productos>();
    ListView ltsProductos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton btnAgregarProductos = (FloatingActionButton)findViewById(R.id.btnAgregarProductos);
        btnAgregarProductos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agregarProducto("nuevo", new String[]{});
            }
        });
        obtenerDatosproductos();
        buscarproducto();
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.mimenu, menu);

        AdapterView.AdapterContextMenuInfo adapterContextMenuInfo = (AdapterView.AdapterContextMenuInfo)menuInfo;
        misProductos.moveToPosition(adapterContextMenuInfo.position);
        menu.setHeaderTitle(misProductos.getString(1));
    }
    void buscarproducto(){
        final TextView tempVal = (TextView)findViewById(R.id.txtBuscarProducto);
        tempVal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    stringArrayList.clear();
                    if (tempVal.getText().toString().trim().length() < 1) {//no hay texto para buscar
                        stringArrayList.addAll(copyStringArrayList);
                    } else {//hacemos la busqueda
                        for (Productos am : copyStringArrayList) {
                            String nombre = am.getNombre();
                            if (nombre.toLowerCase().contains(tempVal.getText().toString().trim().toLowerCase())) {
                                stringArrayList.add(am);
                            }
                        }
                    }
                    AdaptadorImagenes adaptadorImg = new AdaptadorImagenes(getApplicationContext(), stringArrayList);
                    ltsProductos.setAdapter(adaptadorImg);
                }catch (Exception ex){
                    Toast.makeText(getApplicationContext(), "Error: "+ ex.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.mnxAgregar:
                agregarProducto("nuevo", new String[]{});
                return true;

            case R.id.mnxModificar:
                String[] dataProducto = {
                        misProductos.getString(0),//idAmigo
                        misProductos.getString(1),//nombre
                        misProductos.getString(2),//marca
                        misProductos.getString(3),//categoria
                        misProductos.getString(4), //precio
                        misProductos.getString(5)  //urlImg
                };
                agregarProducto("modificar",dataProducto);
                return true;

            case R.id.mnxEliminar:
                AlertDialog eliminarprdu =  eliminarproducto();
                eliminarprdu.show();
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }
    AlertDialog eliminarproducto(){
        AlertDialog.Builder confirmacion = new AlertDialog.Builder(MainActivity.this);
        confirmacion.setTitle(misProductos.getString(1));
        confirmacion.setMessage("Esta seguro de eliminar el registro?");
        confirmacion.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                miBD.mantenimientoproductos("eliminar",new String[]{misProductos.getString(0)});
                obtenerDatosproductos();
                Toast.makeText(getApplicationContext(), "Amigo eliminado con exito.",Toast.LENGTH_SHORT).show();
                dialogInterface.dismiss();
            }
        });
        confirmacion.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), "Eliminacion cancelada por el usuario.",Toast.LENGTH_SHORT).show();
                dialogInterface.dismiss();
            }
        });
        return confirmacion.create();
    }
    void obtenerDatosproductos(){
        miBD = new DB(getApplicationContext(), "", null, 1);
        misProductos = miBD.mantenimientoproductos("consultar", null);
        if( misProductos.moveToFirst() ){ //hay registro en la BD que mostrar
            mostrarDatosproductos();
        } else{ //No tengo registro que mostrar.
            Toast.makeText(getApplicationContext(), "No hay registros de amigos que mostrar",Toast.LENGTH_LONG).show();
            agregarProducto("nuevo", new String[]{});
        }
    }
    void agregarProducto(String accion, String[] dataProducto){
        Bundle enviarParametros = new Bundle();
        enviarParametros.putString("accion",accion);
        enviarParametros.putStringArray("dataProducto",dataProducto);
        Intent agregarProductos = new Intent(MainActivity.this, agregar_Productos.class);
        agregarProductos.putExtras(enviarParametros);
        startActivity(agregarProductos);
    }
    void mostrarDatosproductos(){
        stringArrayList.clear();
        ltsProductos = (ListView)findViewById(R.id.ltsProductos);
        do {
            Producto = new Productos(misProductos.getString(0),misProductos.getString(1), misProductos.getString(2), misProductos.getString(3), misProductos.getString(4), misProductos.getString(5));
            stringArrayList.add(Producto);
        }while(misProductos.moveToNext());
        AdaptadorImagenes adaptadorImg = new AdaptadorImagenes(getApplicationContext(), stringArrayList);
        ltsProductos.setAdapter(adaptadorImg);

        copyStringArrayList.clear();//limpiamos la lista de amigos
        copyStringArrayList.addAll(stringArrayList);//creamos la copia de la lista de amigos...
        registerForContextMenu(ltsProductos);
    }
}
class Productos{
    String id;
    String nombre;
    String marca;
    String categoria;
    String precio;
    String urlImg;

    public Productos(String id, String nombre, String marca, String categoria, String precio, String urlImg) {
        this.id = id;
        this.nombre = nombre;
        this.marca  = marca;
        this.categoria = categoria;
        this.precio = precio;
        this.urlImg = urlImg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() { return nombre; }

    public void setNombre(String codigo) { this.nombre = nombre;}

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }
}