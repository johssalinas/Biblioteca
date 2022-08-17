package red.lisgar.biblioteca.usuario;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import red.lisgar.biblioteca.R;
import red.lisgar.biblioteca.admin.AdminActualizarLibroActivity;
import red.lisgar.biblioteca.db.DbLibros;
import red.lisgar.biblioteca.db.DbLibrosPrestados;
import red.lisgar.biblioteca.db.DbUsuarios;
import red.lisgar.biblioteca.entidades.LibrosDisponibles;
import red.lisgar.biblioteca.entidades.Usuarios;
import red.lisgar.biblioteca.login.SharePreference;

public class UsuMiLibroActivity extends AppCompatActivity {

    DbLibros dbLibros;

    TextView rolToolbar;
    TextView nombreToolbar;
    ImageView btnMas;
    ImageView imgBarra;
    SharePreference sHarePreference;

    TextView tituloMiLibro;
    TextView autorMiLibro;
    TextView descripcionMiLibro;
    ImageView imageMiLibro;
    Button btnDevolverLibro;
    Button btnVerLibro;
    LibrosDisponibles librosDisponibles;
    int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usu_mi_libro);

        DbLibrosPrestados dbLibrosPrestados;


        //LAYOUT
        tituloMiLibro = findViewById(R.id.tituloMiLibro);
        autorMiLibro = findViewById(R.id.autorMiLibro);
        descripcionMiLibro = findViewById(R.id.descripcionMiLibro);
        imageMiLibro = findViewById(R.id.imageMiLibro);
        btnDevolverLibro = findViewById(R.id.btnDevolverLibro);
        btnVerLibro = findViewById(R.id.btnVerLibro);


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

        dbLibrosPrestados = new DbLibrosPrestados(UsuMiLibroActivity.this);
        librosDisponibles = dbLibrosPrestados.verMiLibro(id);

        if (librosDisponibles != null){
            tituloMiLibro.setText(librosDisponibles.getTitulo());
            autorMiLibro.setText(librosDisponibles.getAutor());
            descripcionMiLibro.setText(librosDisponibles.getDescripcion());
            Glide.with(this)
                    .load(librosDisponibles.getImgResource())
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(imageMiLibro);
        }

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

        //BOTON VOLVER
        btnMas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verLibrosPrestados();
            }
        });

        //BOTON DEVOLVER
        btnDevolverLibro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean correcto = false;
                dbLibros = new DbLibros(UsuMiLibroActivity.this);
                correcto = dbLibros.regresarLibro(id);
                if (correcto){
                    Toast.makeText(UsuMiLibroActivity.this, "Libro Regresado", Toast.LENGTH_LONG).show();
                    verLibrosPrestados();
                }else {
                    Toast.makeText(UsuMiLibroActivity.this, "Error al Regresar el Libro", Toast.LENGTH_LONG).show();
                }
            }
        });

        //BOTON VER
        btnVerLibro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verLibro();
            }
        });
    }
    private void verLibrosPrestados() {
        Intent intent3 = new Intent(this, UsuMisLibrosActivity.class);
        startActivity(intent3);
    }
    private void verLibro() {

        Uri uri = Uri.parse(librosDisponibles.getUrl());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        /*super.onBackPressed();*/
    }
}