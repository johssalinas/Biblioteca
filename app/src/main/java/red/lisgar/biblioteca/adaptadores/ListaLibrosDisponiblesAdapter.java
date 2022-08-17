package red.lisgar.biblioteca.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import red.lisgar.biblioteca.R;
import red.lisgar.biblioteca.admin.AdminActualizarLibroActivity;
import red.lisgar.biblioteca.admin.AdminLibroHistorialActivity;
import red.lisgar.biblioteca.entidades.LibrosDisponibles;
import red.lisgar.biblioteca.entidades.Prestamos;
import red.lisgar.biblioteca.usuario.UsuMiLibroActivity;
import red.lisgar.biblioteca.usuario.UsuPrestarLibroActivity;

public class ListaLibrosDisponiblesAdapter extends RecyclerView.Adapter<ListaLibrosDisponiblesAdapter.LibrosDisponiblesViewHolder> {

    ArrayList<LibrosDisponibles> listaOriginal;
    ArrayList<LibrosDisponibles> listItem;
    Context context;
    String destino;
    String ventana;

    public ListaLibrosDisponiblesAdapter(ArrayList<LibrosDisponibles> listItem, Context context, String destino, String ventana) {
        this.listItem = listItem;
        this.context = context;
        this.destino = destino;
        this.ventana = ventana;
        listaOriginal = new ArrayList<>();
        listaOriginal.addAll(listItem);
    }

    @NonNull
    @Override
    public ListaLibrosDisponiblesAdapter.LibrosDisponiblesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        switch (ventana){
            case "VERTICAL":
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_libros_disponibles, parent, false);
                return new LibrosDisponiblesViewHolder(view);
            case "HORIZONTAL":
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_libros_prestados, parent, false);
                return new LibrosDisponiblesViewHolder(view);
            default:
                throw new IllegalStateException("Unexpected value: " + ventana);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ListaLibrosDisponiblesAdapter.LibrosDisponiblesViewHolder holder, int position) {
        final LibrosDisponibles item = listItem.get(position);


        switch (ventana){
            case "VERTICAL":
                Glide.with(context)
                        .load(item.getImgResource())
                        .placeholder(R.drawable.ic_launcher_background)
                        .into(holder.imagenLibrosDisponibles);
                holder.txtItemTituloLibro.setText(item.getTitulo());
                holder.txtItemDescripcion.setText(item.getAutor());
                break;
            case "HORIZONTAL":
                Glide.with(context)
                        .load(item.getImgResource())
                        .placeholder(R.drawable.ic_launcher_background)
                        .into(holder.imagenLibrosPrestados);
                holder.txtItemTituloPrestado.setText(item.getTitulo());
                holder.txtItemAutorPrestado.setText(item.getAutor());
                holder.txtItemDescripcionPrestados.setText(item.getFecha());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    public class LibrosDisponiblesViewHolder extends RecyclerView.ViewHolder {

        ImageView imagenLibrosDisponibles;
        TextView txtItemTituloLibro;
        TextView txtItemDescripcion;

        ImageView imagenLibrosPrestados;
        TextView txtItemTituloPrestado;
        TextView txtItemAutorPrestado;
        TextView txtItemDescripcionPrestados;


        public LibrosDisponiblesViewHolder(@NonNull View itemView) {
            super(itemView);
            imagenLibrosDisponibles = itemView.findViewById(R.id.imagenLibrosDisponibles);
            txtItemTituloLibro = itemView.findViewById(R.id.txtItemTituloLibro);
            txtItemDescripcion = itemView.findViewById(R.id.txtItemDescripcion);

            imagenLibrosPrestados = itemView.findViewById(R.id.imagenLibrosPrestados);
            txtItemTituloPrestado = itemView.findViewById(R.id.txtItemTituloPrestado);
            txtItemAutorPrestado = itemView.findViewById(R.id.txtItemAutorPrestado);
            txtItemDescripcionPrestados = itemView.findViewById(R.id.txtItemDescripcionPrestados);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent intent;
                    switch (destino){
                        case "ACTUALIZAR":
                             intent = new Intent(context, AdminActualizarLibroActivity.class);
                            intent.putExtra("ID", listItem.get(getAdapterPosition()).getId());
                            context.startActivity(intent);
                            break;
                        case "VERPRESTARLIBRO":
                            intent = new Intent(context, UsuPrestarLibroActivity.class);
                            intent.putExtra("ID", listItem.get(getAdapterPosition()).getId());
                            context.startActivity(intent);
                            break;
                        case "VERLIBRO":
                            intent = new Intent(context, UsuMiLibroActivity.class);
                            intent.putExtra("ID", listItem.get(getAdapterPosition()).getId());
                            context.startActivity(intent);
                            break;
                        case "HISTORIAL":
                            intent = new Intent(context, AdminLibroHistorialActivity.class);
                            intent.putExtra("ID", listItem.get(getAdapterPosition()).getId());
                            context.startActivity(intent);
                            break;
                    }
                }
            });
        }
    }

    public void filtrado(String txtBuscar){
        int longitud = txtBuscar.length();
        if (longitud == 0){
            listItem.clear();
            listItem.addAll(listaOriginal);
        }else{
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N){
                List<LibrosDisponibles> collection = listItem.stream().filter(i -> i.getTitulo().toLowerCase().contains(txtBuscar.toLowerCase())).collect(Collectors.toList());
                listItem.clear();
                listItem.addAll(collection);
            }else {
                for (LibrosDisponibles l: listaOriginal){
                    if (l.getTitulo().toLowerCase().contains(txtBuscar.toLowerCase())){
                        listItem.add(l);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }
}
