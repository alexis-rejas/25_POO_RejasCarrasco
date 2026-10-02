package vallegrande.edu.pe.model;

public class Categoria {

    // ATRIBUTOS
    private int id;
    private String nombre;
    private String descripcion;

    //CONSTRUCTOR
    public Categoria(int id, String nombre, String descripcion){
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    //GETTERS
    public int getId(){
        return id;
    }
    public String getNombre(){
        return nombre;
    }
    public String getDescripcion(){
        return descripcion;
    }

    //MOSTRAR CATEGORIA
    public void mostrarCategoria(){
        System.out.println("ID: " + id);
        System.out.println("Nombre: " + nombre);
        System.out.println("Descripcion:" + descripcion);
        System.out.println("------------------------------------");
    }
}
