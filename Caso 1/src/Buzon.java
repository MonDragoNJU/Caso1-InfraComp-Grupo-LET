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

    public synchronized ResultadoProducto hayProductos(int id) {
        if (!productos.isEmpty()) {
            Producto producto = productos.poll();
            System.out.println("\033[1;32m[PRODUCTOR " + id + "]\033[0m Producto " + producto.getId() + " retirado de reproceso.");
            return new ResultadoProducto(true, producto);
        }
        return new ResultadoProducto(false, null);
    }
    

    public synchronized void ingresarRevision(int id) throws InterruptedException {
        while (productos.size() >= capacidad) {
            wait();
        }
        Producto producto = new Producto();
        productos.add(producto);
        System.out.println("\033[1;34m[PRODUCTOR " + id + "]\033[0m Producto " + producto.getId() + " creado y enviado a revision.");
        notifyAll();
    }

    public synchronized void depositar(int id, Producto producto) {
        while (productos.size() >= capacidad) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        productos.add(producto);
        System.out.println("\033[1;32m[PRODUCTOR " + id + "]\033[0m Producto " + producto.getId() + " depositado.");
    }

}
