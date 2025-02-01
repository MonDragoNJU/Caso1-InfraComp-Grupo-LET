public class Productor extends Thread {

    private Buzon buzonReproceso;
    private Buzon buzonRevision;

    public Productor(Buzon buzonReproceso, Buzon buzonRevision) {
        this.buzonReproceso = buzonReproceso;
        this.buzonRevision = buzonRevision;
    }

    @Override
    public void run() {
        boolean reprocesoTerminado = false;
        while (!reprocesoTerminado) {
            synchronized (buzonReproceso) {
                if (buzonReproceso.hayProductos()) {
                    Producto producto = buzonReproceso.retirar();
                    System.out.println("\033[1;34m[PRODUCTOR]\033[0m " + " Producto " + producto.getId() + " reprocesado");
                    buzonRevision.depositar(producto);
                } else {
                reprocesoTerminado = true;
                }
            }
        }

        Producto producto = new Producto();
        System.out.println("\033[1;34m[PRODUCTOR]\033[0m " + " Producto " + producto.getId() + " creado");

        //Espera pasiva
        synchronized (buzonRevision) {
            while (!buzonRevision.hayEspacio()) {
                try {
                    buzonRevision.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            buzonRevision.depositar(producto);
            buzonRevision.notifyAll();
        }
    }

}
