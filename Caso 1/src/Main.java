import java.util.Scanner;

public class Main {

    public static Buzon buzonReproceso = new Buzon();
    public static Buzon buzonRevision = new Buzon();
    public static Buzon deposito = new Buzon();

    public static void main(String[] args) {

        String mensaje = "\n" +
        "\033[1;34m**************************************************\033[0m\n" +
        "\033[1;32m           BIENVENIDO A LA DE SIMULACION\033[0m\n" +
        "\033[1;32m              DE LINEA DE PRODUCCION\033[0m\n" +
        "\033[1;34m**************************************************\033[0m\n" +
        "\033[1;36mIngrese los parámetros requeridos para comenzar.\033[0m\n" +
        "\033[1;34m**************************************************\033[0m\n";

        System.out.println(mensaje);
        
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("\033[1;36mIngrese el numero de operadores: \033[0m");
        int numOperadores = scanner.nextInt();
        
        System.out.print("\033[1;36mIngrese el numero total de productos a producir: \033[0m");
        int numProductos = scanner.nextInt();
        
        System.out.print("\033[1;36mIngrese la capacidad maxima del buzon de revision: \033[0m");
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
        
        scanner.close();
    }
}
