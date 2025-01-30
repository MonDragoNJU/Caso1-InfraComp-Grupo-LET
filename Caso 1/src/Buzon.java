import java.util.LinkedList;
import java.util.Queue;

public class Buzon {
    private Queue<Producto> productos = new LinkedList<>();
    private int capacidad;
    
    public Buzon(int capacidad) {
        this.capacidad = capacidad;
    }

    public Buzon() {
        this.capacidad = Integer.MAX_VALUE;
    }

    public Queue<Producto> getProductos() {
        return productos;
    }
    
    //Distincion en donde se va a depositar.

    //Depositar en el buzon de revision
    public synchronized void depositarRevision(Producto producto) throws InterruptedException {
        while (productos.size() >= capacidad) {
            wait();
        }
        productos.add(producto);
        System.out.println("[BUZON REVISION] Producto en revision: " + producto);
        notifyAll();
    }

    //Depositar en el deposito
    public synchronized void depositarDeposito(Producto producto) throws InterruptedException {
        productos.add(producto);
        System.out.println("[DEPOSITO] Producto depositado: " + producto);
    }

    //Depositar en el buzon de reproceso
    public synchronized void depositarReproceso(Producto producto) throws InterruptedException {
        productos.add(producto);
        System.out.println("[BUZON REPROCESO] Producto en reproceso: " + producto);
    }
    
    public synchronized Producto retirar(String tipo) throws InterruptedException {
        while (productos.isEmpty()) {
            wait();
        }
        Producto producto = productos.poll();
        if (tipo.equals("revision")) {
            System.out.println("[BUZON REVISION] Producto retirado: " + producto);
        } else {
            System.out.println("[BUZON REPROCESO] Producto retirado: " + producto);
        }
        notifyAll();
        return producto;
    }
}
