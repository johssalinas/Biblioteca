package red.lisgar.biblioteca.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import red.lisgar.biblioteca.R;
import red.lisgar.biblioteca.admin.AdminActualizarLibroActivity;
import red.lisgar.biblioteca.admin.AdminLibroHistorialActivity;
import red.lisgar.biblioteca.entidades.LibrosDisponibles;
import red.lisgar.biblioteca.entidades.Usuarios;

public class ListaUsuariosAdapter extends RecyclerView.Adapter<ListaUsuariosAdapter.UsuariosViewHolder> {

    ArrayList<Usuarios> listUsuario;
    Context context;

    public ListaUsuariosAdapter(ArrayList<Usuarios> listUsuario, Context context) {
        this.listUsuario = listUsuario;
        this.context = context;
    }

    @NonNull
    @Override
    public ListaUsuariosAdapter.UsuariosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_usuarios_prestados, parent, false);
        return new UsuariosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaUsuariosAdapter.UsuariosViewHolder holder, int position) {
        final Usuarios item = listUsuario.get(position);

        holder.nombreUsuariosPrestados.setText(item.getNombre());
        holder.telefonoUsuariosPrestados.setText(item.getTelefono());
        holder.correoUsuariosPrestados.setText(item.getCorreo());
    }

    @Override
    public int getItemCount() {
        return listUsuario.size();
    }

    public class UsuariosViewHolder extends RecyclerView.ViewHolder {

        TextView nombreUsuariosPrestados;
        TextView telefonoUsuariosPrestados;
        TextView correoUsuariosPrestados;

        public UsuariosViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreUsuariosPrestados = itemView.findViewById(R.id.nombreUsuariosPrestados);
            telefonoUsuariosPrestados = itemView.findViewById(R.id.telefonoUsuariosPrestados);
            correoUsuariosPrestados = itemView.findViewById(R.id.correoUsuariosPrestados);
        }
    }
}
