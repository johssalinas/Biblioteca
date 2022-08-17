package red.lisgar.biblioteca.usuario;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import red.lisgar.biblioteca.R;
import red.lisgar.biblioteca.adaptadores.ListaLibrosDisponiblesAdapter;
import red.lisgar.biblioteca.db.DbLibrosPrestados;
import red.lisgar.biblioteca.db.DbUsuarios;
import red.lisgar.biblioteca.entidades.LibrosDisponibles;
import red.lisgar.biblioteca.entidades.Usuarios;
import red.lisgar.biblioteca.login.MainActivity;
import red.lisgar.biblioteca.login.SharePreference;

public class UsuMisLibrosActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    RecyclerView recyclerView;
    ListaLibrosDisponiblesAdapter adapter;
    ArrayList<LibrosDisponibles> listPrestados;
    DbLibrosPrestados dbLibrosPrestados;

    //LAYOUT
    TextView rolToolbar;
    TextView nombreToolbar;
    ImageView btnMas;
    ImageView imgBarra;
    SearchView buscadorUsuPrestados;

    SharePreference sHarePreference;
    PopupMenu popupMenu;
    Button btnPrestarLibro;
    DbUsuarios dbUsuarios;
    Usuarios usuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usu_mis_libros);

        btnPrestarLibro = findViewById(R.id.btnPrestarLibro);
        buscadorUsuPrestados = findViewById(R.id.buscadorUsuPrestados);

        //RECYCLEVIEW
        recyclerView = findViewById(R.id.recyclerPrestados);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        dbLibrosPrestados = new DbLibrosPrestados(UsuMisLibrosActivity.this);
        listPrestados = new ArrayList<>();
        String actualizar = "VERLIBRO";
        String ventana = "HORIZONTAL";
        adapter = new ListaLibrosDisponiblesAdapter(dbLibrosPrestados.mostrarPrestados(), UsuMisLibrosActivity.this, actualizar, ventana);
        recyclerView.setAdapter(adapter);

        //TOOLBAR
        sHarePreference = new SharePreference(this);
        btnMas = findViewById(R.id.btnMas);
        rolToolbar = findViewById(R.id.rolToolbar);
        nombreToolbar = findViewById(R.id.nombreToolbar);
        imgBarra = findViewById(R.id.imgBarra);
        btnMas.setImageResource(R.drawable.ic_mas);
        imgBarra.setImageResource(R.drawable.user);
        dbUsuarios = new DbUsuarios(this);
        usuarios = new Usuarios();
        rolToolbar.setText("Usuario");
        usuarios = dbUsuarios.mostrarNombre();
        if (usuarios != null) {
            nombreToolbar.setText(usuarios.getNombre());
        }

        //MENU POPUP
        btnMas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupMenu = new PopupMenu(UsuMisLibrosActivity.this, view);
                popupMenu.getMenuInflater().inflate(R.menu.menu_usuario, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.salirMenu:
                                salir();
                                break;
                            default:
                                return UsuMisLibrosActivity.super.onOptionsItemSelected(menuItem);
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });

        btnPrestarLibro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verLibrosDisponibles();
            }
        });

        buscadorUsuPrestados.setOnQueryTextListener(this);

    }
    private void verLibrosDisponibles() {
        Intent intent2 = new Intent(this, UsuLibrosDisponiblesActivity.class);
        startActivity(intent2);
    }
    private void salir() {
        Intent intent3 = new Intent(this, MainActivity.class);
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