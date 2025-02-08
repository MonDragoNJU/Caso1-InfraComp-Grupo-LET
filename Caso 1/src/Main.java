import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        Scanner sc = new Scanner(System.in);

        //Pedimos el numero de operarios
        System.out.println("Introduce el numero de operarios: ");
        int numOperarios = sc.nextInt();

        //Pedimos la capacidad del buzon de revision
        System.out.println("Introduce la capacidad del buzon de revision: ");
        int capacidadRevision = sc.nextInt();

        //Pedimos la meta de productos a producir
        System.out.println("Introduce la meta de productos a producir: ");
        int meta = sc.nextInt();
        
        //Creamos los buffer (buzones)
        Buzon reproceso = new Buzon(Integer.MAX_VALUE, meta);
        Buzon revision = new Buzon(capacidadRevision, meta);
        Buzon deposito = new Buzon(Integer.MAX_VALUE, meta);

        //Lista de operarios
        Productor[] productores = new Productor[numOperarios];
        Calidad[] calidad = new Calidad[numOperarios];


        //Creamos los operarios
        for (int i = 0; i < numOperarios; i++) {
            productores[i] = new Productor(i + 1, reproceso, revision);
            calidad[i] = new Calidad(i + 1, reproceso, revision, deposito, meta);
            productores[i].start();
            calidad[i].start();
        }

        //Esperamos a que todos los operarios terminen
        for (int i = 0; i < numOperarios; i++) {
            productores[i].join();
            calidad[i].join();
        }

        sc.close();

        System.out.println("Proceso terminado.");

    }
}
