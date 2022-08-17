package red.lisgar.biblioteca.usuario;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import red.lisgar.biblioteca.R;
import red.lisgar.biblioteca.db.DbLibros;
import red.lisgar.biblioteca.db.DbLibrosPrestados;
import red.lisgar.biblioteca.db.DbUsuarios;
import red.lisgar.biblioteca.entidades.LibrosDisponibles;
import red.lisgar.biblioteca.entidades.Prestamos;
import red.lisgar.biblioteca.entidades.Usuarios;
import red.lisgar.biblioteca.login.SharePreference;

public class UsuPrestarLibroActivity extends AppCompatActivity {

    TextView rolToolbar;
    TextView nombreToolbar;
    ImageView btnMas;
    ImageView imgBarra;
    SharePreference sHarePreference;
    int id = 0;
    DbLibros dbLibros;
    LibrosDisponibles librosDisponibles;
    DbLibrosPrestados dbLibrosPrestados;

    TextView tituloPrestar;
    TextView autorPrestar;
    TextView descripcionPrestar;
    ImageView imagePrestar;
    Button btnPrestarLibro;
    Prestamos prestamos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usu_prestar_libro);

        tituloPrestar = findViewById(R.id.tituloPrestar);
        autorPrestar = findViewById(R.id.autorPrestar);
        descripcionPrestar = findViewById(R.id.descripcionPrestar);
        imagePrestar = findViewById(R.id.imagePrestar);
        btnPrestarLibro = findViewById(R.id.btnPrestarLibro);

        //TOOLBAR
        sHarePreference = new SharePreference(this);
        rolToolbar = findViewById(R.id.rolToolbar);
        btnMas = findViewById(R.id.btnMas);
        imgBarra = findViewById(R.id.imgBarra);
        nombreToolbar = findViewById(R.id.nombreToolbar);
        imgBarra.setImageResource(R.drawable.user);
        DbUsuarios dbUsuarios = new DbUsuarios(this);
        Usuarios usuarios = new Usuarios();
        rolToolbar.setText("Usuario");
        usuarios = dbUsuarios.mostrarNombre();
        if (usuarios != null) {
            nombreToolbar.setText(usuarios.getNombre());
        }

        btnMas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verLibrosDisponibles();
            }
        });

        if (savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if (extras == null){
                id= Integer.parseInt(null);
            } else{
                id= extras.getInt("ID");
            }
        }else{
            id=(int) savedInstanceState.getSerializable("ID");
        }
        dbLibros = new DbLibros(UsuPrestarLibroActivity.this);
        librosDisponibles = dbLibros.mostrarDatos(id);

        if (librosDisponibles != null){
             tituloPrestar.setText(librosDisponibles.getTitulo());
            autorPrestar.setText(librosDisponibles.getAutor());
            descripcionPrestar.setText(librosDisponibles.getDescripcion());
            Glide.with(this)
                    .load(librosDisponibles.getImgResource())
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(imagePrestar);
        }

        btnPrestarLibro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //VALIDA SI EL LIBRO YA ESTÁ CREADO
                String idval = String.valueOf(librosDisponibles.getId());
                boolean checkTitulo = dbUsuarios.validarTitulo(idval);
                if (checkTitulo) {
                    Toast.makeText(UsuPrestarLibroActivity.this, "Libro no disponible", Toast.LENGTH_LONG).show();
                } else {

                    //PRESTA EL LIBRO
                    long agregado;
                    String id_prestamo = String.valueOf(id);
                    String cod_usuario = sHarePreference.getSharePreference();
                    String fecha = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());

                    dbLibrosPrestados = new DbLibrosPrestados(UsuPrestarLibroActivity.this);
                    prestamos = new Prestamos();
                    prestamos.setCod_libro(id_prestamo);
                    prestamos.setCod_usuario(cod_usuario);
                    prestamos.setFecha(fecha);
                    agregado = dbLibrosPrestados.nuevoPrestamo(prestamos);

                    if (agregado > 0) {
                        boolean correcto = false;
                        dbLibros = new DbLibros(UsuPrestarLibroActivity.this);
                        correcto = dbLibros.prestarLibro(id);
                        if (correcto) {
                            Toast.makeText(UsuPrestarLibroActivity.this, "Libro Prestado", Toast.LENGTH_LONG).show();
                            verMisLibros();
                        }
                    } else {
                        Toast.makeText(UsuPrestarLibroActivity.this, "Error al Prestar el Libro", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }
    private void verLibrosDisponibles() {
        Intent intent3 = new Intent(this, UsuLibrosDisponiblesActivity.class);
        startActivity(intent3);
    }
    private void verMisLibros() {
        Intent intent3 = new Intent(this, UsuMisLibrosActivity.class);
        startActivity(intent3);
    }
    @Override
    public void onBackPressed() {
        /*super.onBackPressed();*/
    }
}