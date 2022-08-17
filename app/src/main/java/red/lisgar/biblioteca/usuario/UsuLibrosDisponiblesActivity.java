package red.lisgar.biblioteca.usuario;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import red.lisgar.biblioteca.R;
import red.lisgar.biblioteca.adaptadores.ListaLibrosDisponiblesAdapter;
import red.lisgar.biblioteca.admin.AdminLibrosDisponiblesActivity;
import red.lisgar.biblioteca.db.DbLibros;
import red.lisgar.biblioteca.db.DbUsuarios;
import red.lisgar.biblioteca.entidades.LibrosDisponibles;
import red.lisgar.biblioteca.entidades.Usuarios;
import red.lisgar.biblioteca.login.SharePreference;

public class UsuLibrosDisponiblesActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    RecyclerView rvLista;
    ListaLibrosDisponiblesAdapter adapter;
    ArrayList<LibrosDisponibles> listItem;
    TextView rolToolbar;
    TextView nombreToolbar;
    ImageView btnMas;
    ImageView imgBarra;
    SearchView buscadorUsuDispon;

    SharePreference sHarePreference;
    DbLibros dbLibros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usu_libros_disponibles);

        buscadorUsuDispon = findViewById(R.id.buscadorUsuDispon);


        //RECYCLEVIEW
        rvLista = findViewById(R.id.recyclerUsuDisponibles);
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        rvLista.setLayoutManager(manager);
        dbLibros = new DbLibros(UsuLibrosDisponiblesActivity.this);
        listItem = new ArrayList<>();
        String verlibro = "VERPRESTARLIBRO";
        String ventana = "VERTICAL";
        adapter = new ListaLibrosDisponiblesAdapter(dbLibros.mostrarLibros(), UsuLibrosDisponiblesActivity.this, verlibro, ventana);
        rvLista.setAdapter(adapter);

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

        buscadorUsuDispon.setOnQueryTextListener(this);

    }
    private void verLibrosDisponibles() {
        Intent intent3 = new Intent(this, UsuMisLibrosActivity.class);
        startActivity(intent3);
    }
    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        adapter.filtrado(s);
        return false;
    }
    @Override
    public void onBackPressed() {
        /*super.onBackPressed();*/
    }
}