public class Productor extends Thread {
    
    private int id;
    private Buzon reproceso;
    private Buzon revision;

    public Productor(int id, Buzon reproceso, Buzon revision) {
        this.id = id;
        this.reproceso = reproceso;
        this.revision = revision;
    }
    @Override
    public void run() {
        while (revision.sigueProduccion()) {  // Verificar si la producción sigue activa

            Producto producto = null;

            synchronized (reproceso) {
                if (!revision.sigueProduccion()) break;  // Verificar nuevamente antes de operar
                
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                
                producto = reproceso.removerReproceso();

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (producto != null) {
                    System.out.println("Productor " + id +
                            " reprocesó producto " + producto.getId());

                    // Si recibe FIN, detener producción global
                    if ("FIN".equals(producto.getMensaje())) {
                        System.out.println("Productor " + id +
                                " recibió FIN. Deteniendo producción.");
                        revision.detenerProduccion();
                        break;
                    }
                } else {

                    if (!revision.sigueProduccion()) break;  // Verificar nuevamente antes de crear

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (!revision.sigueProduccion()) break;

                    producto = new Producto();
                    System.out.println("Productor " + id +
                            " creó producto " + producto.getId());
                }
            }

            if (!revision.sigueProduccion()) break;  // Verificar antes de depositar

            try {
                revision.depositarRevision(producto, id);
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

    }
}
