public class Producto {

    private static int contador = 1;
    private int id;
    private String mensaje = "";
    
    public Producto() {
        this.id = contador++;
    }
    
    public int getId() { return id; }
    public String getMensaje() { return mensaje; }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

}
