package red.lisgar.biblioteca.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import red.lisgar.biblioteca.entidades.LibrosDisponibles;
import red.lisgar.biblioteca.entidades.Prestamos;
import red.lisgar.biblioteca.login.SharePreference;

public class DbLibrosPrestados extends DbHelperAdmin{
    Context context;
    DbHelperAdmin dbHelper;
    SharePreference sharePreference;

    public DbLibrosPrestados(@Nullable Context context) {
        super(context);
        this.context = context;
        sharePreference = new SharePreference(context);
    }

    public long nuevoPrestamo(Prestamos prestamos){
        long id= 0;

        try {
            dbHelper = new DbHelperAdmin(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(COLUMN_PRESTAMO_COD_LIBRO, prestamos.getCod_libro());
            values.put(COLUMN_PRESTAMO_COD_USUARIO, prestamos.getCod_usuario());
            values.put(COLUMN_PRESTAMO_FECHA, prestamos.getFecha());


            id = db.insert(TABLE_PRESTAMO, null, values);
        } catch (Exception ex){
            ex.toString();
        }

        return id;
    }

    public ArrayList<LibrosDisponibles> mostrarPrestados(){
        dbHelper = new DbHelperAdmin((context));
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ArrayList<LibrosDisponibles> listaLibros = new ArrayList<>();
        LibrosDisponibles librosDisponibles;
        Cursor cursorLibro;

        cursorLibro = db.rawQuery("SELECT "+ TABLE_LIBRO +".*, " + TABLE_PRESTAMO+"."+ COLUMN_PRESTAMO_FECHA + " FROM " + TABLE_PRESTAMO + " INNER JOIN " + TABLE_LIBRO +" ON "+ TABLE_PRESTAMO + "."+ COLUMN_PRESTAMO_COD_LIBRO +" = "+ TABLE_LIBRO +"."+ COLUMN_LIBRO_ID +" WHERE " +TABLE_PRESTAMO+"."+ COLUMN_PRESTAMO_COD_USUARIO + " = '" + sharePreference.getSharePreference()+"'", null);

        if (cursorLibro.moveToFirst()){
            do {
                librosDisponibles = new LibrosDisponibles();
                librosDisponibles.setId(cursorLibro.getInt(0));
                librosDisponibles.setTitulo(cursorLibro.getString(1));
                librosDisponibles.setAutor(cursorLibro.getString(2));
                librosDisponibles.setFecha(cursorLibro.getString(7));
                librosDisponibles.setImgResource(cursorLibro.getString(5));
                listaLibros.add(librosDisponibles);
            } while (cursorLibro.moveToNext());
        }
        cursorLibro.close();

        return listaLibros;
    }

    public LibrosDisponibles verMiLibro(int id){
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

    public ArrayList<LibrosDisponibles> mostrarPrestadosAdmin(){
        dbHelper = new DbHelperAdmin((context));
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ArrayList<LibrosDisponibles> listaLibros = new ArrayList<>();
        LibrosDisponibles librosDisponibles;
        Cursor cursorLibro;

        cursorLibro = db.rawQuery("SELECT "+ TABLE_LIBRO +".* FROM " + TABLE_PRESTAMO + " INNER JOIN " + TABLE_LIBRO +" ON "+ TABLE_PRESTAMO + "."+ COLUMN_PRESTAMO_COD_LIBRO +" = "+ TABLE_LIBRO +"."+ COLUMN_LIBRO_ID +" GROUP BY " + TABLE_LIBRO + "." + COLUMN_LIBRO_ID, null);

        if (cursorLibro.moveToFirst()){
            do {
                librosDisponibles = new LibrosDisponibles();
                librosDisponibles.setId(cursorLibro.getInt(0));
                librosDisponibles.setTitulo(cursorLibro.getString(1));
                librosDisponibles.setAutor(cursorLibro.getString(2));
                librosDisponibles.setImgResource(cursorLibro.getString(5));
                listaLibros.add(librosDisponibles);
            } while (cursorLibro.moveToNext());
        }
        cursorLibro.close();

        return listaLibros;
    }
}
