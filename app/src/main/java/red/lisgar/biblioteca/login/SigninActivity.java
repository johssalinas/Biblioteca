package red.lisgar.biblioteca.login;

import android.content.Intent;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import red.lisgar.biblioteca.R;
import red.lisgar.biblioteca.db.DbAdmin;
import red.lisgar.biblioteca.db.DbUsuarios;
import red.lisgar.biblioteca.entidades.Usuarios;
import red.lisgar.biblioteca.usuario.UsuMisLibrosActivity;

public class SigninActivity extends AppCompatActivity {

    EditText txtNombre;
    EditText txtCorreosignin;
    EditText txtTelefono;
    EditText txtDireccion;
    EditText txtContrasena;
    Button btnRegistrarse;
    Button btnIniciarSesion;
    Usuarios usuarios;
    SharePreference sHarePreference;
    DbAdmin dbAdmin;
    DbUsuarios dbUsuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        txtNombre = findViewById(R.id.txtNombre);
        txtCorreosignin = findViewById(R.id.txtCorreosignin);
        txtTelefono = findViewById(R.id.txtTelefono);
        txtDireccion = findViewById(R.id.txtDireccion);
        txtContrasena = findViewById(R.id.txtContrasena);
        btnRegistrarse = findViewById(R.id.btnRegistrarse);
        btnIniciarSesion = findViewById(R.id.btnIniciarSesion);
        sHarePreference = new SharePreference(this);
        usuarios = new Usuarios();
        dbAdmin = new DbAdmin(this);
        dbUsuarios = new DbUsuarios(this);

        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Nombre = txtNombre.getText().toString().trim();
                String Correo = txtCorreosignin.getText().toString().trim().toLowerCase();
                String Telefono = txtTelefono.getText().toString().trim().toLowerCase();
                String Direccion = txtDireccion.getText().toString().trim().toLowerCase();
                String Contrasena = txtContrasena.getText().toString().trim();

                //OBLIGATORIEDAD DE TODOS LOS CAMPOS
                if (!TextUtils.isEmpty(Correo) && !TextUtils.isEmpty(Telefono) && !TextUtils.isEmpty(Direccion) && !TextUtils.isEmpty(Contrasena)) {
                    //QUE NO ESTÉ CREADO
                    boolean checkadmin = dbAdmin.validarAdminSignin(Correo);
                    boolean checkCorreopass = dbUsuarios.entrarUsuarioContrasenaSignin(Correo);
                    boolean validEmail = dbUsuarios.validarEmail(Correo);
                    boolean validPass = dbUsuarios.validarPass(txtContrasena.getText().toString().trim());
                    //VALIDA DISPONIBILIDAD DE CORREO
                    if (checkadmin || checkCorreopass){
                        Toast.makeText(SigninActivity.this, "Correo no disponible", Toast.LENGTH_LONG).show();
                    } else {
                        //EL TELEFONO DEBE TENER 10 CARACTERES
                        if (Telefono.length()==10) {
                            //DEBE TENER FORMAT O DE CORREO
                            if (validEmail){
                                //VALIDA LA CONTRASEÑA
                                if (validPass){
                                    //SE REGISTRA
                                    dbUsuarios = new DbUsuarios(SigninActivity.this);
                                    usuarios.setNombre(Nombre);
                                    usuarios.setCorreo(Correo);
                                    usuarios.setTelefono(Telefono);
                                    usuarios.setDireccion(Direccion);
                                    usuarios.setContrasena(Contrasena);
                                    sHarePreference.setSharedPreferences(Correo);
                                    limpiar();
                                    long id = dbUsuarios.insertarUsuario(usuarios);
                                    if (id > 0) {
                                        Toast.makeText(SigninActivity.this, "Registro guardado", Toast.LENGTH_LONG).show();
                                        verMain();
                                    } else {
                                        Toast.makeText(SigninActivity.this, "Error al guardar el registro", Toast.LENGTH_LONG).show();
                                    }
                                }else {
                                    Toast.makeText(SigninActivity.this, "Debe ser alfanumérica, incluir caracteres especiales, un rango de mínimo 8 y máximo 15", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(SigninActivity.this, "Debe tener formato de correo", Toast.LENGTH_LONG).show();
                            }
                            } else{
                            Toast.makeText(SigninActivity.this, "El telefono debe tener 10 caracteres", Toast.LENGTH_LONG).show();

                        }
                    }
                } else {
                    Toast.makeText(SigninActivity.this, "Rellene todos lo campos", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verMain();
                limpiar();
            }
        });
    }

    private void verMain() {
        Intent intent3 = new Intent(this, MainActivity.class);
        startActivity(intent3);
    }
    private void limpiar(){
        txtNombre.setText("");
        txtCorreosignin.setText("");
        txtDireccion.setText("");
        txtContrasena.setText("");
        txtTelefono.setText("");
    }
    @Override
    public void onBackPressed() {
        /*super.onBackPressed();*/
    }
}