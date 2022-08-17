package red.lisgar.biblioteca.admin;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import red.lisgar.biblioteca.R;
import red.lisgar.biblioteca.db.DbLibros;
import red.lisgar.biblioteca.entidades.LibrosDisponibles;
import red.lisgar.biblioteca.login.SharePreference;

public class AdminActualizarLibroActivity extends AppCompatActivity {

    EditText nombreActualizarLibro;
    EditText autorActualizarLibro;
    EditText cantidadActualizarLibro;
    EditText urlActualizarLibro;
    EditText imagenActualizarLibro;
    EditText descripcionActualizarLibro;
    Button btnActualizarLibro;
    TextView rolToolbar;
    TextView nombreToolbar;
    ImageView btnMas;
    SharePreference sHarePreference;
    DbLibros dbLibros;
    LibrosDisponibles librosDisponibles;
    int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_actualizar_libro);

        nombreActualizarLibro = findViewById(R.id.nombreActualizarLibro);
        autorActualizarLibro = findViewById(R.id.autorActualizarLibro);
        cantidadActualizarLibro = findViewById(R.id.cantidadActualizarLibro);
        urlActualizarLibro = findViewById(R.id.urlActualizarLibro);
        imagenActualizarLibro = findViewById(R.id.imagenActualizarLibro);
        descripcionActualizarLibro = findViewById(R.id.descripcionActualizarLibro);
        btnActualizarLibro = findViewById(R.id.btnActualizarLibro);

        //TOOLBAR
        sHarePreference = new SharePreference(this);
        rolToolbar = findViewById(R.id.rolToolbar);
        btnMas = findViewById(R.id.btnMas);
        nombreToolbar = findViewById(R.id.nombreToolbar);
        rolToolbar.setText("Administrador");
        nombreToolbar.setText(sHarePreference.getSharePreference());

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

        dbLibros = new DbLibros(AdminActualizarLibroActivity.this);
        librosDisponibles = dbLibros.verLibro(id);

        if (librosDisponibles != null){
            nombreActualizarLibro.setText(librosDisponibles.getTitulo());
            autorActualizarLibro.setText(librosDisponibles.getAutor());
            cantidadActualizarLibro.setText(librosDisponibles.getCantidad());
            urlActualizarLibro.setText(librosDisponibles.getUrl());
            imagenActualizarLibro.setText(librosDisponibles.getImgResource());
            descripcionActualizarLibro.setText(librosDisponibles.getDescripcion());
        }

        btnActualizarLibro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Nombre = nombreActualizarLibro.getText().toString().trim();
                String autor = autorActualizarLibro.getText().toString().trim();
                String cantidad = cantidadActualizarLibro.getText().toString().trim();
                String url = urlActualizarLibro.getText().toString().trim();
                String imagen = imagenActualizarLibro.getText().toString().trim();
                String descripcion = descripcionActualizarLibro.getText().toString().trim();
                boolean correcto = false;

                //OBLIGATORIEDAD DE TODOS LOS CAMPOS
                if (!TextUtils.isEmpty(Nombre) && !TextUtils.isEmpty(autor) && !TextUtils.isEmpty(cantidad) && !TextUtils.isEmpty(url) && !TextUtils.isEmpty(imagen) && !TextUtils.isEmpty(descripcion)) {
                    //SE MODIFICA
                    dbLibros = new DbLibros(AdminActualizarLibroActivity.this);
                    correcto = dbLibros.editaLibro(
                    id, Nombre, autor, cantidad, url, imagen, descripcion);
                    if (correcto) {
                        Toast.makeText(AdminActualizarLibroActivity.this, "Libro Actualizado", Toast.LENGTH_LONG).show();
                        verLibrosDisponibles();
                        limpiar();
                    } else {
                        Toast.makeText(AdminActualizarLibroActivity.this, "Error al Actualizar el Libro", Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(AdminActualizarLibroActivity.this, "Rellene todos los campos", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private void verLibrosDisponibles() {
        Intent intent3 = new Intent(this, AdminLibrosDisponiblesActivity.class);
        startActivity(intent3);
    }
    private void limpiar(){
        nombreActualizarLibro.setText("");
        autorActualizarLibro.setText("");
        cantidadActualizarLibro.setText("");
        urlActualizarLibro.setText("");
        imagenActualizarLibro.setText("");
        descripcionActualizarLibro.setText("");
    }
    @Override
    public void onBackPressed() {
        /*super.onBackPressed();*/
    }
}