import java.util.Random;

public class EquipoCalidad extends Thread {
    
    private Buzon buzonRevison;
    private Buzon deposito;
    private Random random = new Random();
    private int meta;
    private static int productosAprobados = 0;

    public EquipoCalidad(Buzon buzonRevison, Buzon deposito, int meta) {
        this.buzonRevison = buzonRevison;
        this.deposito = deposito;
        this.meta = meta;
    }

    @Override
    public void run() {
        while (productosAprobados < meta) {
            boolean revisionTerminada = false;
            while (!revisionTerminada) {
                synchronized (buzonRevison) {
                    if (buzonRevison.hayProductos()) {
                        Producto producto = null;
                        try {
                            producto = buzonRevison.retirarReproceso(1);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        System.out.println("\033[1;35m[EQUIPO CALIDAD]\033[0m " + " Producto " + producto.getId() + " en revision");
                        if (producto.getMensaje().equals("FIN")) {
                            revisionTerminada = true;
                            System.out.println("\033[1;35m[EQUIPO CALIDAD]\033[0m " + " Producto " + producto.getId() + " con mensaje 'FIN' recibido. Terminando revision");
                        } 
                    } else {
                        revisionTerminada = true;
                    }
                }
            }
            //Espera pasiva
            synchronized (buzonRevison) {
                while (!buzonRevison.hayProductos()) {
                    try {
                        buzonRevison.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }


    
}
