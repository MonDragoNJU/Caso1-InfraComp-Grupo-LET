import java.util.Random;

public class EquipoCalidad extends Thread {
    private Buzon buzonRevision;
    private Buzon buzonReproceso;
    private Buzon deposito;
    private Random random = new Random();
    private static int productosDepositados = 0;
    private static int numProductos;
    private static boolean fin = false;
    
    public EquipoCalidad(Buzon buzonRevision, Buzon buzonReproceso, Buzon deposito, int numProductos) {
        this.buzonRevision = buzonRevision;
        this.buzonReproceso = buzonReproceso;
        this.deposito = deposito;
        EquipoCalidad.numProductos = numProductos;
    }
    
    private synchronized boolean verificarLimite(Producto producto) throws InterruptedException {
        if (productosDepositados >= numProductos) {
            producto.setMensaje("FIN");
            buzonReproceso.depositarReproceso(producto);
            fin = true;
            return true;
        }
        return false;
    }
    
    public void run() {
        try {
            while (!fin) {
                Producto producto = buzonRevision.retirar("revision");
                
                if (verificarLimite(producto)) {
                    continue;
                }
                
                int resultado = random.nextInt(100) + 1;
                
                if (resultado % 7 == 0) {
                    System.out.println("[CALIDAD] Producto " + producto.getId() + " falló y va a reproceso.");
                    buzonReproceso.depositarReproceso(producto);
                } else {
                    System.out.println("[CALIDAD] Producto " + producto.getId() + " aprobado y enviado a depósito.");
                    deposito.depositarDeposito(producto);
                    
                    synchronized (this) {
                        productosDepositados++;
                    }
                }
                
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
