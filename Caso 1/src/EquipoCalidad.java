import java.util.Random;

public class EquipoCalidad extends Thread {
    private int fallos = 0;
    private Random random = new Random();
    private static int productosDepositados = 0;
    private static int numProductos;
    private static boolean fin = false;
    
    public EquipoCalidad(int numProductos) {
        EquipoCalidad.numProductos = numProductos;
    }
    
    private synchronized boolean verificarLimite(Producto producto) throws InterruptedException {
        if (productosDepositados >= numProductos) {
            System.out.println("[CALIDAD] Capacidad máxima del depósito alcanzada. Enviando mensaje FIN.");
            producto.setMensaje("FIN");
            Main.buzonReproceso.depositarReproceso(producto);
            fin = true;
            return true;
        }
        return false;
    }
    
    public void run() {
        try {
            while (!fin) {
                Producto producto = Main.buzonRevision.retirar("revision");
                
                if (verificarLimite(producto)) {
                    break;
                }
                
                int resultado = random.nextInt(100) + 1;
                
                if (resultado % 7 == 0 && fallos < Math.floor(0.1 * numProductos)) {
                    System.out.println("[CALIDAD] Producto " + producto.getId() + " falló y va a reproceso.");
                    Main.buzonReproceso.depositarReproceso(producto);
                    fallos++;
                } else {
                    synchronized (this) {
                        productosDepositados++;
                        if (productosDepositados >= numProductos) {
                            producto.setEsReproceso(true);
                            producto.setMensaje("FIN");
                            Main.buzonReproceso.depositarReproceso(producto);
                            fin = true;
                            break;
                        }
                    }
                    System.out.println("[CALIDAD] Producto " + producto.getId() + " aprobado y enviado a depósito.");
                    Main.deposito.depositarDeposito(producto);
                }
                
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
