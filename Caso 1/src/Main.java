import java.util.Scanner;

public class Main {

    public static Buzon buzonReproceso = new Buzon();
    public static Buzon buzonRevision = new Buzon();
    public static Buzon deposito = new Buzon();

    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Ingrese el numero de operadores: ");
        int numOperadores = scanner.nextInt();
        
        System.out.print("Ingrese el numero total de productos a producir: ");
        int numProductos = scanner.nextInt();
        
        System.out.print("Ingrese la capacidad maxima del buzon de revision: ");
        int capacidadBuzon = scanner.nextInt();
        buzonRevision.setCapacidad(capacidadBuzon);

        Thread[] productores = new Thread[numOperadores];
        Thread[] equiposCalidad = new Thread[numOperadores];
        
        for (int i = 0; i < numOperadores; i++) {
            productores[i] = new Productor();
            equiposCalidad[i] = new EquipoCalidad(numProductos);
            productores[i].start();
            equiposCalidad[i].start();
        }

        // Esperar a que todos los Threads terminen
        try {
            for (int i = 0; i < numOperadores; i++) {
                productores[i].join();
                equiposCalidad[i].join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Simulacion finalizada.");
        
        scanner.close();
    }
}
