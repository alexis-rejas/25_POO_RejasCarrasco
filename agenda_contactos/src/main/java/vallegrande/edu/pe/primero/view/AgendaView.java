package vallegrande.edu.pe.primero.view;

import vallegrande.edu.pe.primero.controller.AgendaController;
import java.util.Scanner;

public class AgendaView {
    private AgendaController controller;
    private Scanner scanner;

    public AgendaView() {
        this.controller = new AgendaController();
        this.scanner = new Scanner(System.in);
    }

    public void mostrarMenu() {
        boolean salir = false;
        controller.precargaContactos();
        
        while (!salir) {
            System.out.println("\n==== AGENDA DE CONTACTOS ====");
            System.out.println("1. Registrar contacto");
            System.out.println("2. Listar contactos");
            System.out.println("3. Buscar contacto");
            System.out.println("4. Eliminar contacto");
            System.out.println("5. Salir");
            System.out.print("Seleccione opción: ");
            
            int opcion = scanner.nextInt();
            scanner.nextLine();
            
            switch (opcion) {
                case 1:
                    registrarContacto();
                    break;
                case 2:
                    controller.listarContactos();
                    break;
                case 3:
                    buscarContacto();
                    break;
                case 4:
                    eliminarContacto();
                    break;
                case 5:
                    salir = true;
                    System.out.println("¡Hasta luego!");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
        scanner.close();
    }

    private void registrarContacto() {
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Apellidos: ");
        String apellidos = scanner.nextLine();
        System.out.print("Dirección: ");
        String direccion = scanner.nextLine();
        System.out.print("Teléfono: ");
        String telefono = scanner.nextLine();
        System.out.print("Correo: ");
        String correo = scanner.nextLine();
        
        controller.registrarContacto(nombre, apellidos, direccion, telefono, correo);
    }

    private void buscarContacto() {
        System.out.print("Ingrese nombre o apellido: ");
        String termino = scanner.nextLine();
        controller.buscarContacto(termino);
    }

    private void eliminarContacto() {
        System.out.print("Ingrese ID del contacto a eliminar: ");
        int id = scanner.nextInt();
        controller.eliminarContacto(id);
    }
}
