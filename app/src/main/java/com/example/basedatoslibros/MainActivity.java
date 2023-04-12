package com.example.basedatoslibros;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText et1,et2,et3,et4;
    private String NombreBaseDatos = "administrar";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et1 =(EditText)findViewById(R.id.et1);
        et2 =(EditText)findViewById(R.id.et2);
        et3 = (EditText)findViewById(R.id.et3);
        et4 =(EditText)findViewById(R.id.et4);
    }
    //Metodo guardar
    public void guardar (View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, NombreBaseDatos , null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String isbn = et1.getText().toString();
        String nombre = et3.getText().toString();
        String autor = et2.getText().toString();
        String precio = et4.getText().toString();
        
        if (!isbn.isEmpty() && !nombre.isEmpty() && !autor.isEmpty()){
            ContentValues registro = new ContentValues();

            registro.put("codigo",isbn);
            registro.put("nombre",nombre);
            registro.put("autor",autor);
            registro.put("precio",precio);

            BaseDeDatos.insert("libros",null,registro);

            BaseDeDatos.close();

            et1.setText("");
            et2.setText("");
            et3.setText("");

            Toast.makeText(this, "El libro se ha guardado correctamente", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Rellene los campos ISBN,Autor y Nombre", Toast.LENGTH_SHORT).show();
        }
    }
    //Metodo buscar
    public void buscar (View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, NombreBaseDatos , null , 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String isbm = et1.getText().toString();

        if(!isbm.isEmpty()){
            Cursor fila = BaseDeDatos.rawQuery("select nombre, autor,precio from libros where codigo="+isbm,null);
            if(fila.moveToFirst()){
                et3.setText(fila.getString(0));
                et2.setText(fila.getString(1));
                et4.setText(fila.getString(2));
            }else{
                et4.setText("");
                et1.setText("");
                et2.setText("");
                et3.setText("");
                Toast.makeText(this, "No se ha encontrado el articulo", Toast.LENGTH_SHORT).show();
                BaseDeDatos.close();
            }
        }else{
            Toast.makeText(this, "Rellene el campo IBN", Toast.LENGTH_SHORT).show();
        }
    }
    public void eliminar (View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,NombreBaseDatos, null,1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();
        String isbn = et1.getText().toString();
        if(!isbn.isEmpty()){
            int cantidad = BaseDeDatos.delete("libros","codigo="+ isbn,null);
            BaseDeDatos.close();

            if (cantidad==1){
                Toast.makeText(this, "El articulo ha sido eliminado", Toast.LENGTH_SHORT).show();
                et1.setText("");
                et2.setText("");
                et3.setText("");
                et4.setText("");
            }else{
                Toast.makeText(this, "El articulo no existe", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Debes introducir el ISBN del producto", Toast.LENGTH_SHORT).show();
        }
    }
    public void modificar (View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, NombreBaseDatos,null,1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();
        String isbn = et1.getText().toString();
        String autor = et2.getText().toString();
        String nombre = et3.getText().toString();
        String precio = et4.getText().toString();

        if (!isbn.isEmpty() && !autor.isEmpty() && !nombre.isEmpty()){
            ContentValues registro = new ContentValues();
            registro.put("codigo",isbn);
            registro.put("autor",autor);
            registro.put("nombre",nombre);
            registro.put("precio",precio);

            int cantidad = BaseDeDatos.update("libros", registro, "codigo="+isbn, null);
            BaseDeDatos.close();

            if (cantidad==1){
                Toast.makeText(this, "El articulo ha sido modificado", Toast.LENGTH_SHORT).show();
                et1.setText("");
                et2.setText("");
                et3.setText("");
                et4.setText("");
            }else{
                Toast.makeText(this, "El articuo no existe", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Debes rellenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }
}