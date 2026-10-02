package vallegrande.edu.pe.crud.model;

public class Mascota {
    private String nombre;
    private String especie;
    private String dueño;

    public Mascota(String nombre, String especie, String dueño) {
        this.nombre = nombre;
        this.especie = especie;
        this.dueño = dueño;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEspecie() { return especie; }
    public void setEspecie(String especie) { this.especie = especie; }

    public String getDueño() { return dueño; }
    public void setDueño(String dueño) { this.dueño = dueño; }
}