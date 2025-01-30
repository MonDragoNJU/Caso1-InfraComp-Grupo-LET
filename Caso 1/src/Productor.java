public class Productor extends Thread{
    private Buzon buzonReproceso;
    private Buzon buzonRevision;
    
    public Productor(Buzon buzonReproceso, Buzon buzonRevision) {
        this.buzonReproceso = buzonReproceso;
        this.buzonRevision = buzonRevision;
    }
    
    public void run() {
        try {
            while (true) {
                Producto producto;
                synchronized (buzonReproceso) {
                    if (!buzonReproceso.getProductos().isEmpty()) {
                        producto = buzonReproceso.retirar("reproceso");
                        System.out.println("[PRODUCTOR] Reprocesó: " + producto);
                    } else {
                        producto = new Producto(false);
                        System.out.println("[PRODUCTOR] Produjo: " + producto);
                    }
                }

                buzonRevision.depositarRevision(producto);
                
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
