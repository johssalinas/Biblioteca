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
import red.lisgar.biblioteca.db.DbUsuarios;
import red.lisgar.biblioteca.entidades.LibrosDisponibles;
import red.lisgar.biblioteca.login.MainActivity;
import red.lisgar.biblioteca.login.SharePreference;
import red.lisgar.biblioteca.login.SigninActivity;

public class AdminAgregarLibroActivity extends AppCompatActivity {

    //LAYOUT
    EditText nombreAgregarLibro;
    EditText autorAgregarLibro;
    EditText cantidadAgregarLibro;
    EditText urlAgregarLibro;
    EditText imagenAgregarLibro;
    EditText descripcionAgregarLibro;
    Button btnAgregarLibro;
    TextView rolToolbar;
    TextView nombreToolbar;
    ImageView btnMas;

    //LLAMAR CLASES
    DbLibros dbLibros;
    LibrosDisponibles librosDisponibles;
    SharePreference sHarePreference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_agregar_libro);

        //FINDVIEWBYID
        nombreAgregarLibro = findViewById(R.id.nombreAgregarLibro);
        autorAgregarLibro = findViewById(R.id.autorAgregarLibro);
        cantidadAgregarLibro = findViewById(R.id.cantidadAgregarLibro);
        urlAgregarLibro = findViewById(R.id.urlAgregarLibro);
        imagenAgregarLibro = findViewById(R.id.imagenAgregarLibro);
        descripcionAgregarLibro = findViewById(R.id.descripcionAgregarLibro);

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

        //AGREGAR LIBRO
        btnAgregarLibro = findViewById(R.id.btnAgregarLibro);
        dbLibros = new DbLibros(this);
        librosDisponibles = new LibrosDisponibles();

        btnAgregarLibro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Nombre = nombreAgregarLibro.getText().toString().trim();
                String autor = autorAgregarLibro.getText().toString().trim();
                String cantidad = cantidadAgregarLibro.getText().toString().trim();
                String url = urlAgregarLibro.getText().toString().trim();
                String imagen = imagenAgregarLibro.getText().toString().trim();
                String descripcion = descripcionAgregarLibro.getText().toString().trim();

                //OBLIGATORIEDAD DE TODOS LOS CAMPOS
                if (!TextUtils.isEmpty(Nombre) && !TextUtils.isEmpty(autor) && !TextUtils.isEmpty(cantidad) && !TextUtils.isEmpty(url) && !TextUtils.isEmpty(imagen) && !TextUtils.isEmpty(descripcion)) {
                    //SE REGISTRA
                    dbLibros = new DbLibros(AdminAgregarLibroActivity.this);
                    librosDisponibles.setTitulo(Nombre);
                    librosDisponibles.setAutor(autor);
                    librosDisponibles.setCantidad(cantidad);
                    librosDisponibles.setUrl(url);
                    librosDisponibles.setImgResource(imagen);
                    librosDisponibles.setDescripcion(descripcion);
                    limpiar();
                    long id = dbLibros.insertaLibro(librosDisponibles);
                    if (id > 0) {
                        Toast.makeText(AdminAgregarLibroActivity.this, "LIBRO GUARDADO", Toast.LENGTH_LONG).show();
                        verLibrosDisponibles();
                    } else {
                        Toast.makeText(AdminAgregarLibroActivity.this, "ERROR AL GUARDAR EL LIBRO", Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(AdminAgregarLibroActivity.this, "RELLENE TODOS LO CAMPOS", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private void verLibrosDisponibles() {
        Intent intent3 = new Intent(this, AdminLibrosDisponiblesActivity.class);
        startActivity(intent3);
    }
    private void limpiar(){
        nombreAgregarLibro.setText("");
        autorAgregarLibro.setText("");
        cantidadAgregarLibro.setText("");
        urlAgregarLibro.setText("");
        imagenAgregarLibro.setText("");
        descripcionAgregarLibro.setText("");
    }
    @Override
    public void onBackPressed() {
        /*super.onBackPressed();*/
    }
}