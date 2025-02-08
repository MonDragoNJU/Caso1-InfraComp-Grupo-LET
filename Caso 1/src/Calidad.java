import java.util.Random;

public class Calidad extends Thread{
    
    private int id;
    private Buzon reproceso;
    private Buzon revision;
    private Buzon deposito;
    private int meta;
    private Random random = new Random();

    public Calidad(int id, Buzon reproceso, Buzon revision, Buzon deposito, int meta) {
        this.id = id;
        this.reproceso = reproceso;
        this.revision = revision;
        this.deposito = deposito;
        this.meta = meta;
    }

    @Override
    public void run() {

        int fallos = (int) Math.floor(meta * 0.1); //Calcula el 10% de la meta de productos

        while (revision.sigueProduccion() || revision.hayProductos()) { //Verifica que la produccion siga activa o que haya productos en el buzon de revision
            
            while (!revision.hayProductos() && revision.sigueProduccion()) {
                Thread.yield(); //Espera semi-activa
            }

            Producto producto = null;

            synchronized(revision) {
                producto = revision.retirarRevision();
                if (producto == null) {
                    continue;
                } else {
                    System.out.println("Calidad " + id + " revisa producto " + producto.getId());
                }
            }

            //Revisar si hay fallo o no
            if (revision.getMeta() > 0) {
                if ((random.nextInt(100) + 1) % 7 == 0 && fallos > 0) {
                    System.out.println("Calidad " + id + " detectó falla en producto " + producto.getId());
                    reproceso.depositarReproceso(producto, id);
                    fallos--;
                } else {
                    System.out.println("Calidad " + id + " aprobó producto " + producto.getId());
                    deposito.depositarDeposito(producto, id);
                    revision.decrementarMeta();
                }
            } else {
                synchronized (reproceso) {
                    Producto fin = new Producto();
                    fin.setMensaje("FIN");
                    reproceso.depositarReproceso(fin, id);
                }
            }

            //Simular tiempo de procesamiento
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    

}
