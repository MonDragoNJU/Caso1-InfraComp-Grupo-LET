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
        if (productos.size() > 0) {
            return true;
        }
        return false;
    }

    public synchronized boolean depositar(Producto producto) {
        if (productos.size() < capacidad) {
            productos.add(producto);
            return true;
        }
        return false;
    }

    public synchronized Producto retirar() {
        return productos.poll();
    }
    
}
