package red.lisgar.biblioteca.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import red.lisgar.biblioteca.entidades.Admin;
import red.lisgar.biblioteca.entidades.Usuarios;
import red.lisgar.biblioteca.login.SharePreference;

public class DbAdmin extends DbHelperAdmin{
    Context context;
    SharePreference sharePreference;

    public DbAdmin(@Nullable Context context){
        super(context);
        this.context = context;
        sharePreference = new SharePreference(context);
    }

    public long insertarAdmin(Admin admin) {

        long id= 0;

        try {
            DbHelperAdmin dbHelper = new DbHelperAdmin((context));
            SQLiteDatabase db = dbHelper.getWritableDatabase();


            ContentValues values = new ContentValues();
            values.put(COLUMN_ADMIN_CORREO, admin.getCorreo());
            values.put(COLUMN_ADMIN_NOMBRE, admin.getNombre());
            values.put(COLUMN_ADMIN_CONTRASENA, admin.getPass());

            id = db.insert(TABLE_ADMIN, null, values);
        } catch (Exception ex){
            ex.toString();

        }
        return id;
    }
    public boolean validarAdmin(String correo_electronico, String contrasena) {
        DbHelperAdmin dbHelper = new DbHelperAdmin(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursoradmin = db.rawQuery("SELECT * FROM " + TABLE_ADMIN + " WHERE " + COLUMN_ADMIN_CORREO + " =? AND " + COLUMN_ADMIN_CONTRASENA + " =?",new String[] {correo_electronico,contrasena});
        if (cursoradmin.getCount()>0)
            return true;
        else
            return false;
    }
    public boolean validarAdminSignin(String correo_electronico) {
        DbHelperAdmin dbHelper = new DbHelperAdmin(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursoradmin = db.rawQuery("SELECT * FROM " + TABLE_ADMIN + " WHERE " + COLUMN_ADMIN_CORREO + " =? ",new String[] {correo_electronico});
        if (cursoradmin.getCount()>0)
            return true;
        else
            return false;

    }
    public ArrayList<Usuarios> mostrarDatosHistorial(int id){
        DbHelperAdmin dbHelper = new DbHelperAdmin(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ArrayList<Usuarios> listaUsuarios = new ArrayList<>();
        Usuarios usuarios;
        Cursor cursorLibro;

        cursorLibro = db.rawQuery("SELECT " + TABLE_USUARIO+ ".* " +
                "FROM " +TABLE_PRESTAMO+
                " INNER JOIN " + TABLE_USUARIO +
                " ON " + TABLE_PRESTAMO + "." + COLUMN_PRESTAMO_COD_USUARIO + " = " + TABLE_USUARIO + "." + COLUMN_USUARIO_CORREO +
                " INNER JOIN "+ TABLE_LIBRO +
                " ON "+ TABLE_PRESTAMO + "." + COLUMN_PRESTAMO_COD_LIBRO + " = " + TABLE_LIBRO + "." + COLUMN_LIBRO_ID +
                " WHERE " + TABLE_LIBRO + "." + COLUMN_LIBRO_ID + " = " + id, null);

        if (cursorLibro.moveToFirst()){
            do {
                usuarios = new Usuarios();
                usuarios.setId(cursorLibro.getInt(0));
                usuarios.setNombre(cursorLibro.getString(1));
                usuarios.setTelefono(cursorLibro.getString(2));
                usuarios.setCorreo(cursorLibro.getString(3));
                listaUsuarios.add(usuarios);
            } while (cursorLibro.moveToNext());
        }
        cursorLibro.close();

        return listaUsuarios;
    }
}