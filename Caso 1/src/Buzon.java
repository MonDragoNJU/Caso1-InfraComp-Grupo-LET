import java.util.LinkedList;
import java.util.Queue;

public class Buzon {

    //Cola para almacenar los productos en el buzon
    private Queue<Producto> productos;

    // Variable de control de produccion
    public boolean continuarProduccion = true;  

    //Capacidad del buzon
    private int capacidad;

    //Meta de productos a producir
    private int meta;

    public Buzon(int capacidad, int meta) {
        this.meta = meta;
        this.capacidad = capacidad;
        productos = new LinkedList<Producto>();
    }

    public synchronized int getMeta() {
        return meta;
    }

    public synchronized void decrementarMeta() {
        meta--;
    }

    // Método para detener la producción
    public synchronized void detenerProduccion() {
        continuarProduccion = false;
        notifyAll();
    }

    // Método para verificar si la producción sigue activa
    public synchronized boolean sigueProduccion() {
        return continuarProduccion;
    }

    //Metodo para remover un producto del buzon de reproceso
    public synchronized Producto removerReproceso() {
        if (productos.isEmpty()) {
            return null;
        }
        Producto producto = productos.poll();
        return producto;
    }

    public synchronized boolean hayProductos() {
        notifyAll();
        return !productos.isEmpty();
    }

    public synchronized void depositarReproceso(Producto producto, int id) {
        productos.add(producto);
        System.out.println("Calidad " + id + " deposita producto " + producto.getId() + " en el buzon de reproceso");
    }

    //Metodo para depositar un producto en el buzon de revision
    public synchronized void depositarRevision(Producto producto, int id) throws InterruptedException {
        while (productos.size() >= capacidad && sigueProduccion()) {
            System.out.println("Productor " + id + " espera espacio en el buzon de revision...");
            wait(); //Espera pasiva sin consumir CPU
        }

        if (sigueProduccion() && productos.size() < capacidad) {
            productos.add(producto);
            System.out.println("Productor " + id + 
                " deposita producto " + producto.getId() + " en el buzon de revision");
        }
        notifyAll(); 
    }

    public synchronized void depositarDeposito(Producto producto, int id) {
        productos.add(producto);
        System.out.println("Calidad " + id + " deposita producto " + producto.getId() + " en el deposito");
    }

    public synchronized Producto retirarRevision() {
        if (productos.isEmpty()) {
            return null;
        }
        Producto producto = productos.poll();
        return producto;
    }
}
