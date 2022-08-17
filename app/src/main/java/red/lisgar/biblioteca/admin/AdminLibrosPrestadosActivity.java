package red.lisgar.biblioteca.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import red.lisgar.biblioteca.R;
import red.lisgar.biblioteca.adaptadores.ListaLibrosDisponiblesAdapter;
import red.lisgar.biblioteca.db.DbLibros;
import red.lisgar.biblioteca.db.DbLibrosPrestados;
import red.lisgar.biblioteca.entidades.LibrosDisponibles;
import red.lisgar.biblioteca.login.SharePreference;
import red.lisgar.biblioteca.usuario.UsuMisLibrosActivity;

public class AdminLibrosPrestadosActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    RecyclerView recyclerView;
    ListaLibrosDisponiblesAdapter adapter;
    ArrayList<LibrosDisponibles> listPrestadosAdmin;
    DbLibrosPrestados dbLibrosPrestados;

    SearchView buscadorAdminDispon;

    TextView rolToolbar;
    TextView nombreToolbar;
    ImageView btnMas;
    SharePreference sHarePreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_libros_prestados);

        buscadorAdminDispon = findViewById(R.id.buscadorAdminPrestado);

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

        //RECYCLEVIEW
        recyclerView = findViewById(R.id.recyclerAdminPrestados);
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(manager);
        dbLibrosPrestados = new DbLibrosPrestados(AdminLibrosPrestadosActivity.this);
        listPrestadosAdmin = new ArrayList<>();
        String actualizar = "HISTORIAL";
        String ventana = "VERTICAL";
        adapter = new ListaLibrosDisponiblesAdapter(dbLibrosPrestados.mostrarPrestadosAdmin(), AdminLibrosPrestadosActivity.this, actualizar, ventana);
        recyclerView.setAdapter(adapter);
        buscadorAdminDispon.setOnQueryTextListener(this);
    }
    private void verLibrosDisponibles() {
        Intent intent3 = new Intent(this, AdminLibrosDisponiblesActivity.class);
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