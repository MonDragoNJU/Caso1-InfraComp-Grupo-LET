import java.util.Random;

public class EquipoCalidad extends Thread{
    private Buzon buzonRevision;
    private Buzon buzonReproceso;
    private Buzon deposito;
    private Random random = new Random();
    
    public EquipoCalidad(Buzon buzonRevision, Buzon buzonReproceso, Buzon deposito) {
        this.buzonRevision = buzonRevision;
        this.buzonReproceso = buzonReproceso;
        this.deposito = deposito;
    }
    
    public void run() {
        try {
            while (true) {
                Producto producto = buzonRevision.retirar("revision");
                int resultado = random.nextInt(100) + 1;
                
                if (resultado % 7 == 0) {
                    System.out.println("[CALIDAD] Producto " + producto.getId() + " fallo y va a reproceso.");
                    buzonReproceso.depositarReproceso(producto);
                } else {
                    System.out.println("[CALIDAD] Producto " + producto.getId() + " aprobado y enviado a deposito.");
                    deposito.depositarDeposito(producto);
                }
                
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
