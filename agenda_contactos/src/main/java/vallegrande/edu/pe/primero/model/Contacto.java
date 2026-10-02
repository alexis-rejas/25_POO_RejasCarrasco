package vallegrande.edu.pe.primero.model;

public class Contacto {
    private int id;
    private String nombre;
    private String apellidos;
    private String direccion;
    private String telefono;
    private String correo;

    public Contacto(int id, String nombre, String apellidos, String direccion, String telefono, String correo) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.telefono = telefono;
        this.correo = correo;
    }

    // Getters
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getApellidos() { return apellidos; }
    public String getDireccion() { return direccion; }
    public String getTelefono() { return telefono; }
    public String getCorreo() { return correo; }

    // Mostrar contacto
    public void mostrarContacto() {
        System.out.println("ID: " + id + " | " + nombre + " " + apellidos + " | " + direccion + " | Tel: " + telefono + " | " + correo);
    }
}
