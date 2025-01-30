import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Ingrese el numero de operadores: ");
        int numOperadores = scanner.nextInt();
        
        System.out.print("Ingrese el numero total de productos a producir: ");
        int numProductos = scanner.nextInt();
        
        System.out.print("Ingrese la capacidad maxima del buzon de revision: ");
        int capacidadBuzon = scanner.nextInt();
        
        Buzon buzonReproceso = new Buzon();
        Buzon buzonRevision = new Buzon(capacidadBuzon);
        Buzon deposito = new Buzon();
        
        for (int i = 0; i < numOperadores; i++) {
            new Productor(buzonReproceso, buzonRevision).start();
            new EquipoCalidad(buzonRevision, buzonReproceso, deposito, numProductos).start();
        }
        
        scanner.close();
    }
}