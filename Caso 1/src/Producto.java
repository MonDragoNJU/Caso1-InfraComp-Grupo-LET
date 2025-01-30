public class Producto {
    private static int contador = 0;
    private int id;
    private boolean esReproceso;
    private String mensaje;
    
    public Producto(boolean esReproceso) {
        this.id = ++contador;
        this.esReproceso = esReproceso;
    }
    
    public int getId() { return id; }
    public boolean esReproceso() { return esReproceso; }

    public String getMensaje() { return mensaje; }

}
