public class Productor extends Thread {
    private static boolean fin = false;

    public static synchronized void detenerProduccion() {
        fin = true;
    }

    public static synchronized boolean produccionFinalizada() {
        return fin;
    }

    public void run() {
        try {
            while (!produccionFinalizada()) {
                Producto producto;
                synchronized (Main.buzonReproceso) {
                    if (!Main.buzonReproceso.getProductos().isEmpty()) {
                        producto = Main.buzonReproceso.retirar("reproceso");
                        System.out.println("[PRODUCTOR] Reprocesó: " + producto);

                        if ("FIN".equals(producto.getMensaje())) {
                            detenerProduccion();
                            System.out.println("[PRODUCTOR] Se recibió el mensaje FIN. Terminando producción.");
                            break;
                        } else {
                            Main.buzonRevision.depositarRevision(producto);
                        }
                    } else {
                        producto = new Producto(false);
                        System.out.println("[PRODUCTOR] Produjo: " + producto);
                        Main.buzonRevision.depositarRevision(producto);
                    }
                }
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
