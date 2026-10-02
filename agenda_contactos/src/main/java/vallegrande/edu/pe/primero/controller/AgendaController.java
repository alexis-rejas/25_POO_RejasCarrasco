package vallegrande.edu.pe.primero.controller;

import vallegrande.edu.pe.primero.model.Contacto;
import java.util.ArrayList;

public class AgendaController {
    private ArrayList<Contacto> contactos = new ArrayList<>();
    private int proximoId = 1;

    // Registrar contacto
    public void registrarContacto(String nombre, String apellidos, String direccion, String telefono, String correo) {
        Contacto c = new Contacto(proximoId++, nombre, apellidos, direccion, telefono, correo);
        contactos.add(c);
        System.out.println("✓ Contacto registrado correctamente.\n");
    }

    // Listar todos
    public void listarContactos() {
        if (contactos.isEmpty()) {
            System.out.println("No hay contactos registrados.\n");
            return;
        }
        System.out.println("\n--- LISTA DE CONTACTOS ---");
        for (Contacto c : contactos) {
            c.mostrarContacto();
        }
        System.out.println();
    }

    // Buscar por nombre o apellido
    public void buscarContacto(String termino) {
        boolean encontrado = false;
        System.out.println("\n--- RESULTADOS DE BÚSQUEDA ---");
        for (Contacto c : contactos) {
            if (c.getNombre().toLowerCase().contains(termino.toLowerCase()) || 
                c.getApellidos().toLowerCase().contains(termino.toLowerCase())) {
                c.mostrarContacto();
                encontrado = true;
            }
        }
        if (!encontrado) {
            System.out.println("No se encontraron contactos.");
        }
        System.out.println();
    }

    // Eliminar por ID
    public void eliminarContacto(int id) {
        for (int i = 0; i < contactos.size(); i++) {
            if (contactos.get(i).getId() == id) {
                System.out.println("✓ Contacto eliminado.\n");
                contactos.remove(i);
                return;
            }
        }
        System.out.println("Contacto no encontrado.\n");
    }

    // Precargar 5 contactos de muestra
    public void precargaContactos() {
        registrarContacto("Juan", "Pérez García", "Av. Principal 123", "987654321", "juan@email.com");
        registrarContacto("María", "López Martínez", "Calle Secundaria 456", "976543210", "maria@email.com");
        registrarContacto("Carlos", "González Ruiz", "Plaza Central 789", "965432109", "carlos@email.com");
        registrarContacto("Ana", "Rodríguez Sánchez", "Avenida del Sol 101", "954321098", "ana@email.com");
        registrarContacto("Alexis", "Rejas Carrasco", "Calle Tierra 202", "932227359", "oscar@gmail.com");
    }
}
