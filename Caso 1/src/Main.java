import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        String mensaje = "\n" +
        "\033[1;34m**************************************************\033[0m\n" +
        "\033[1;32m           BIENVENIDO A LA DE SIMULACION\033[0m\n" +
        "\033[1;32m              DE LINEA DE PRODUCCION\033[0m\n" +
        "\033[1;34m**************************************************\033[0m\n" +
        "\033[1;36mIngrese los parámetros requeridos para comenzar.\033[0m\n" +
        "\033[1;34m**************************************************\033[0m\n";

        System.out.println(mensaje);

        //Monitores
        Buzon buzonReproceso = new Buzon();
        Buzon buzonRevision = new Buzon();
        Buzon deposito = new Buzon();
        
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("\033[1;36mIngrese el numero de operadores: \033[0m");
        int numOperadores = scanner.nextInt();
        
        System.out.print("\033[1;36mIngrese el numero total de productos a producir: \033[0m");
        int numProductos = scanner.nextInt();
        
        System.out.print("\033[1;36mIngrese la capacidad maxima del buzon de revision: \033[0m");
        int capacidadBuzon = scanner.nextInt();
        buzonRevision.setCapacidad(capacidadBuzon);

        for (int i = 0; i < 10; i++) {
            buzonReproceso.depositar(new Producto());
        }

        for (int i = 0; i < numProductos; i++) {
            Productor productor = new Productor(buzonReproceso, buzonRevision);
            productor.start();
        }
        
        scanner.close();
    }
}
