package red.lisgar.biblioteca.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import red.lisgar.biblioteca.entidades.LibrosDisponibles;
import red.lisgar.biblioteca.entidades.Usuarios;
import red.lisgar.biblioteca.login.SharePreference;

public class DbLibros extends DbHelperAdmin{
    Context context;
    SharePreference sHarePreference;
    DbHelperAdmin dbHelper;

    public DbLibros(@Nullable Context context) {
        super(context);
        this.context = context;
        sHarePreference = new SharePreference(context);
    }
    public long insertaLibro(LibrosDisponibles librosDisponibles) {

        long id= 0;

        try {
            dbHelper = new DbHelperAdmin(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();


            ContentValues values = new ContentValues();
            values.put(COLUMN_LIBRO_NOMBRE, librosDisponibles.getTitulo());
            values.put(COLUMN_LIBRO_AUTOR, librosDisponibles.getAutor());
            values.put(COLUMN_LIBRO_CANTIDAD, librosDisponibles.getCantidad());
            values.put(COLUMN_LIBRO_URL, librosDisponibles.getUrl());
            values.put(COLUMN_LIBRO_IMAGEN, librosDisponibles.getImgResource());
            values.put(COLUMN_LIBRO_DESCRIPCION, librosDisponibles.getDescripcion());


            id = db.insert(TABLE_LIBRO, null, values);
        } catch (Exception ex){
            ex.toString();
        }

        return id;
    }

    public ArrayList<LibrosDisponibles> mostrarLibros(){
        dbHelper = new DbHelperAdmin((context));
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ArrayList<LibrosDisponibles> listaLibros = new ArrayList<>();
        LibrosDisponibles librosDisponibles;
        Cursor cursorLibro;

        cursorLibro = db.rawQuery("SELECT * FROM " + TABLE_LIBRO + " WHERE " +  COLUMN_LIBRO_CANTIDAD + " > 0", null);

        if (cursorLibro.moveToFirst()){
            do {
                librosDisponibles = new LibrosDisponibles();
                librosDisponibles.setId(cursorLibro.getInt(0));
                librosDisponibles.setTitulo(cursorLibro.getString(1));
                librosDisponibles.setAutor(cursorLibro.getString(2));
                librosDisponibles.setCantidad(cursorLibro.getString(3));
                librosDisponibles.setUrl(cursorLibro.getString(4));
                librosDisponibles.setImgResource(cursorLibro.getString(5));
                librosDisponibles.setDescripcion(cursorLibro.getString(6));
                listaLibros.add(librosDisponibles);
            } while (cursorLibro.moveToNext());
        }
        cursorLibro.close();

        return listaLibros;
    }

    public LibrosDisponibles verLibro(int id){
        dbHelper = new DbHelperAdmin((context));
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        LibrosDisponibles librosDisponibles = null;
        Cursor cursorLibro;

        cursorLibro = db.rawQuery("SELECT * FROM " + TABLE_LIBRO + " WHERE " + COLUMN_LIBRO_ID + " = '" + id+"'", null);

        if (cursorLibro.moveToFirst()){
                librosDisponibles = new LibrosDisponibles();
                librosDisponibles.setId(cursorLibro.getInt(0));
                librosDisponibles.setTitulo(cursorLibro.getString(1));
                librosDisponibles.setAutor(cursorLibro.getString(2));
                librosDisponibles.setCantidad(cursorLibro.getString(3));
                librosDisponibles.setUrl(cursorLibro.getString(4));
                librosDisponibles.setImgResource(cursorLibro.getString(5));
                librosDisponibles.setDescripcion(cursorLibro.getString(6));
        }
        cursorLibro.close();

        return librosDisponibles;
    }

    public boolean editaLibro(int id, String nombre, String autor, String cantidad, String url, String imagen, String descripcion) {

        boolean correcto = false;
        dbHelper = new DbHelperAdmin(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("UPDATE " + TABLE_LIBRO + " SET " +
                    COLUMN_LIBRO_NOMBRE + " = '"+nombre+"', " +
                    COLUMN_LIBRO_AUTOR + " = '"+autor+"', " +
                    COLUMN_LIBRO_CANTIDAD + " = '"+cantidad+"', " +
                    COLUMN_LIBRO_URL + " = '"+url+"', " +
                    COLUMN_LIBRO_IMAGEN + " = '"+imagen+"', " +
                    COLUMN_LIBRO_DESCRIPCION + " = '"+descripcion+"'" +
                    " WHERE " + COLUMN_LIBRO_ID + " = '"+id+"'");
            correcto = true;
        } catch (Exception ex){
            ex.toString();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }

    public LibrosDisponibles mostrarDatos(int id){
        dbHelper = new DbHelperAdmin((context));
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        LibrosDisponibles librosDisponibles = null;
        Cursor cursorLibro;

        cursorLibro = db.rawQuery("SELECT * FROM " + TABLE_LIBRO + " WHERE " + COLUMN_LIBRO_ID + " = '" + id+"'", null);

        if (cursorLibro.moveToFirst()){
            librosDisponibles = new LibrosDisponibles();
            librosDisponibles.setId(cursorLibro.getInt(0));
            librosDisponibles.setImgResource(cursorLibro.getString(5));
            librosDisponibles.setTitulo(cursorLibro.getString(1));
            librosDisponibles.setAutor(cursorLibro.getString(2));
            librosDisponibles.setDescripcion(cursorLibro.getString(6));
        }
        cursorLibro.close();
        return librosDisponibles;
    }

    public boolean prestarLibro(int id) {

        dbHelper = new DbHelperAdmin(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        boolean correcto = false;
        try {
            db.execSQL("UPDATE " + TABLE_LIBRO + " SET " + COLUMN_LIBRO_CANTIDAD + " = "+COLUMN_LIBRO_CANTIDAD+ " -1 " + " WHERE " + COLUMN_LIBRO_ID + " = '"+id+"'");
            correcto = true;
        } catch (Exception ex){
            ex.toString();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }
    public boolean regresarLibro(int id) {

        dbHelper = new DbHelperAdmin(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        boolean correcto = false;
        try {
            db.execSQL("UPDATE " + TABLE_LIBRO + " SET " + COLUMN_LIBRO_CANTIDAD + " = "+COLUMN_LIBRO_CANTIDAD+ " +1 " + " WHERE " + COLUMN_LIBRO_ID + " = '"+id+"'");
            db.execSQL("DELETE FROM " + TABLE_PRESTAMO + " WHERE " + COLUMN_PRESTAMO_COD_LIBRO + " = '"+id+"'");
            correcto = true;
        } catch (Exception ex){
            ex.toString();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }
}
