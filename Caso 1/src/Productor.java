public class Productor extends Thread{
    private static boolean fin = false;
    
    public void run() {
        try {
            while (!fin) {
                Producto producto;
                synchronized (Main.buzonReproceso) {
                    if (!Main.buzonReproceso.getProductos().isEmpty()) {
                        producto = Main.buzonReproceso.retirar("reproceso");
                        System.out.println("[PRODUCTOR] Reprocesó: " + producto);
                        if (producto.getMensaje().equals("FIN")) {
                            fin = true;
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
