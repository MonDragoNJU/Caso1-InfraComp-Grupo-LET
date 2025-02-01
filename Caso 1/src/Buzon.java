import java.util.LinkedList;
import java.util.Queue;

public class Buzon {
    private Queue<Producto> productos = new LinkedList<>();
    private int capacidad = Integer.MAX_VALUE;

    public Queue<Producto> getProductos() {
        return productos;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public boolean hayProductos() {
        return !productos.isEmpty();
    }

    public boolean hayEspacio() {
        return productos.size() < capacidad;
    }

    public synchronized void depositar(Producto producto) {
        if (productos.size() >= capacidad) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
        }
        productos.add(producto);
        notifyAll();
    }

    public synchronized Producto retirar() {
        return productos.poll();
    }
    
}
