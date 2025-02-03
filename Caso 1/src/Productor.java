import java.util.Random;

public class Productor extends Thread {

    private int id;
    private Buzon buzonReproceso;
    private Buzon buzonRevision;
    private Random random = new Random();
    private static boolean revisionTerminada = false;


    public Productor(int id, Buzon buzonReproceso, Buzon buzonRevision) {
        this.id = id;
        this.buzonReproceso = buzonReproceso;
        this.buzonRevision = buzonRevision;
    }

    public static synchronized void terminarProduccion() {
        revisionTerminada = true;
    }

    public static synchronized boolean isProduccionTerminada() {
        return revisionTerminada;
    }

    @Override
    public void run() {
        while (!isProduccionTerminada()) {
            //Reprocesar
            ResultadoProducto resultado;
            while ((resultado = buzonReproceso.hayProductos(id)).isExito()) {
                try {
                    buzonRevision.depositar(id, resultado.getProducto());
                    Thread.sleep(random.nextInt(2000) + 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            //Depositar en revision
            try {
                Thread.sleep(random.nextInt(2000) + 1000); //Tiempo de trabajo random
                buzonRevision.ingresarRevision(id); //Espera Pasiva
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

}
