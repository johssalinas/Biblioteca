package red.lisgar.biblioteca.entidades;

public class Prestamos{
    private String cod_prestamos;
    private String cod_libro;
    private String cod_usuario;
    private String fecha;

    public String getCod_prestamos() {
        return cod_prestamos;
    }

    public void setCod_prestamos(String cod_prestamos) {
        this.cod_prestamos = cod_prestamos;
    }

    public String getCod_libro() {
        return cod_libro;
    }

    public void setCod_libro(String cod_libro) {
        this.cod_libro = cod_libro;
    }

    public String getCod_usuario() {
        return cod_usuario;
    }

    public void setCod_usuario(String cod_usuario) {
        this.cod_usuario = cod_usuario;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
