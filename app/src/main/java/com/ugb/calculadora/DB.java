package com.ugb.calculadora;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import androidx.annotation.Nullable;
public class DB extends SQLiteOpenHelper {
    static String nameDB = "db_tienda";
    static String tblProductos = "CREATE TABLE productos(idProducto integer primary key autoincrement, Producto text, Descripcion text, Caracteristicas text, Precio text)";

    public DB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, nameDB, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(tblProductos);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public Cursor mantenimientoproductos(String accion, String[] data){
        SQLiteDatabase sqLiteDatabaseReadable=getReadableDatabase();
        SQLiteDatabase sqLiteDatabaseWritable=getWritableDatabase();
        Cursor cursor = null;
        switch (accion){
            case "consultar":
                cursor=sqLiteDatabaseReadable.rawQuery("SELECT * FROM productos ORDER BY nombre ASC", null);
                break;
            case "nuevo":
                sqLiteDatabaseWritable.execSQL("INSERT INTO productos (Producto, Descripcion, Caracteristicas, Precio) VALUES('"+ data[1] +"','"+data[2]+"','"+data[3]+"','"+data[4]+"')");
                break;
            case "modificar":
                sqLiteDatabaseWritable.execSQL("UPDATE productos SET Producto='"+ data[1] +"',Descripcion='"+data[2]+"',Caracteristicas='"+data[3]+"',Precio='"+data[4]+"' WHERE idProductos='"+data[0]+"'");
                break;
            case "eliminar":
                sqLiteDatabaseWritable.execSQL("DELETE FROM productos WHERE idProducto='"+ data[0] +"'");
                break;
        }
        return cursor;
    }
}





