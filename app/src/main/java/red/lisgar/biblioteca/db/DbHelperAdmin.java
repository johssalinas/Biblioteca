package red.lisgar.biblioteca.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelperAdmin extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NOMBRE = "Biblioteca.db";
    //COLUMNAS DE LA TABLA ADMIN
    public static final String TABLE_ADMIN = "Tabla_admin";
    public static final String COLUMN_ADMIN_ID = "Id_admin";
    public static final String COLUMN_ADMIN_CORREO = "Correo_admin";
    public static final String COLUMN_ADMIN_NOMBRE = "Nombre_admin";
    public static final String COLUMN_ADMIN_CONTRASENA = "Contrasena_admin";

    //COLUMNAS DE LA TABLA USUARIO
    public static final String TABLE_USUARIO = "Tabla_usuario";
    public static final String COLUMN_USUARIO_ID = "Id_usuario";
    public static final String COLUMN_USUARIO_NOMBRE = "Nombre_usuario";
    public static final String COLUMN_USUARIO_TELEFONO = "Telefono_usuario";
    public static final String COLUMN_USUARIO_CORREO = "Correo_usuario";
    public static final String COLUMN_USUARIO_DIRECCION = "Direccion_usuario";
    public static final String COLUMN_USUARIO_CONTRASENA = "Contrasena_usuario";
    public static final String COLUMN_USUARIO_SP = "Sp_usuario";

    //COLUMNAS DE LA TABLA LIBRO
    public static final String TABLE_LIBRO = "Tabla_libro";
    public static final String COLUMN_LIBRO_ID = "Id_libro";
    public static final String COLUMN_LIBRO_NOMBRE = "Nombre_libro";
    public static final String COLUMN_LIBRO_AUTOR = "Autor_libro";
    public static final String COLUMN_LIBRO_CANTIDAD = "Cantidad_libro";
    public static final String COLUMN_LIBRO_URL = "Url_libro";
    public static final String COLUMN_LIBRO_IMAGEN = "Imagen_libro";
    public static final String COLUMN_LIBRO_DESCRIPCION = "Descripcion_libro";

    //COLUMNAS DE LA TABLA PRESTADOS
    public static final String TABLE_PRESTAMO = "Tabla_Prestado";
    public static final String COLUMN_PRESTAMO_ID = "Id_prestamo";
    public static final String COLUMN_PRESTAMO_COD_LIBRO = "cod_libro";
    public static final String COLUMN_PRESTAMO_COD_USUARIO = "cod_usuario";
    public static final String COLUMN_PRESTAMO_FECHA = "fecha_prestamo";

    public DbHelperAdmin(@Nullable Context context) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //CREACION DE LA TABLA ADMIN
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_ADMIN + " ("+
                COLUMN_ADMIN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ADMIN_CORREO + " TEXT NOT NULL, " +
                COLUMN_ADMIN_NOMBRE + " TEXT NOT NULL, " +
                COLUMN_ADMIN_CONTRASENA + " TEXT NOT NULL) ");

        //CREACION DE LA TABLA USUARIO
        sqLiteDatabase.execSQL(" CREATE TABLE " + TABLE_USUARIO + " ("+
                COLUMN_USUARIO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USUARIO_NOMBRE + " TEXT NOT NULL, " +
                COLUMN_USUARIO_TELEFONO + " TEXT NOT NULL, " +
                COLUMN_USUARIO_CORREO + " TEXT NOT NULL, " +
                COLUMN_USUARIO_DIRECCION + " TEXT NOT NULL, " +
                COLUMN_USUARIO_CONTRASENA + " TEXT NOT NULL, " +
                COLUMN_USUARIO_SP + " TEXT NOT NULL) ");

        //CREACION DE LA TABLA LIBRO
        sqLiteDatabase.execSQL(" CREATE TABLE " + TABLE_LIBRO + " ("+
                COLUMN_LIBRO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_LIBRO_NOMBRE + " TEXT NOT NULL, " +
                COLUMN_LIBRO_AUTOR + " TEXT NOT NULL, " +
                COLUMN_LIBRO_CANTIDAD + " TEXT NOT NULL, " +
                COLUMN_LIBRO_URL + " TEXT NOT NULL, " +
                COLUMN_LIBRO_IMAGEN + " TEXT NOT NULL, " +
                COLUMN_LIBRO_DESCRIPCION + " TEXT NOT NULL)");

        //CREACION DE LA TABLA PRESTAMO
        sqLiteDatabase.execSQL(" CREATE TABLE " + TABLE_PRESTAMO + " ("+
                COLUMN_PRESTAMO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PRESTAMO_COD_LIBRO + " TEXT NOT NULL, " +
                COLUMN_PRESTAMO_COD_USUARIO + " TEXT NOT NULL, " +
                COLUMN_PRESTAMO_FECHA + " TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_ADMIN);
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_USUARIO);
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_LIBRO);
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_PRESTAMO);
        onCreate(sqLiteDatabase);
    }
}
