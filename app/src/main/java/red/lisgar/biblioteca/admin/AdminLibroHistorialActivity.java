package red.lisgar.biblioteca.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import red.lisgar.biblioteca.R;
import red.lisgar.biblioteca.adaptadores.ListaUsuariosAdapter;
import red.lisgar.biblioteca.db.DbAdmin;
import red.lisgar.biblioteca.db.DbLibros;
import red.lisgar.biblioteca.entidades.LibrosDisponibles;
import red.lisgar.biblioteca.login.SharePreference;
import red.lisgar.biblioteca.usuario.UsuPrestarLibroActivity;

public class AdminLibroHistorialActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ListaUsuariosAdapter adapter;
    ArrayList<LibrosDisponibles> listPrestadosAdmin;
    DbAdmin dbAdmin;

    TextView rolToolbar;
    TextView nombreToolbar;
    ImageView btnMas;
    SharePreference sHarePreference;

    DbLibros dbLibros;
    LibrosDisponibles librosDisponibles;
    ImageView imageMiLibroAdmin;
    TextView tituloMiLibroAdmin;
    TextView autorMiLibroAdmin;
    TextView descripcionMiLibroAdmin;

    int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_libro_historial);

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


        dbLibros = new DbLibros(AdminLibroHistorialActivity.this);
        librosDisponibles = dbLibros.mostrarDatos(id);

        imageMiLibroAdmin = findViewById(R.id.imageMiLibroAdmin);
        tituloMiLibroAdmin = findViewById(R.id.tituloMiLibroAdmin);
        autorMiLibroAdmin = findViewById(R.id.autorMiLibroAdmin);
        descripcionMiLibroAdmin = findViewById(R.id.descripcionMiLibroAdmin);

        if (librosDisponibles != null) {
            tituloMiLibroAdmin.setText(librosDisponibles.getTitulo());
            autorMiLibroAdmin.setText(librosDisponibles.getAutor());
            descripcionMiLibroAdmin.setText(librosDisponibles.getDescripcion());
            Glide.with(this)
                    .load(librosDisponibles.getImgResource())
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(imageMiLibroAdmin);
        }


        //RECYCLEVIEW
        recyclerView = findViewById(R.id.recyclerHistorial);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        dbAdmin = new DbAdmin(this);
        listPrestadosAdmin = new ArrayList<>();
        adapter = new ListaUsuariosAdapter(dbAdmin.mostrarDatosHistorial(id), AdminLibroHistorialActivity.this);
        recyclerView.setAdapter(adapter);
    }

        private void verLibrosDisponibles() {
            Intent intent3 = new Intent(this, AdminLibrosPrestadosActivity.class);
            startActivity(intent3);
        }
    @Override
    public void onBackPressed() {
        /*super.onBackPressed();*/
    }
}