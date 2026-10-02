package vallegrande.edu.pe.model;

public class Autor {

    // ATRIBUTOS
    private int id;
    private String nombre;
    private String pais;

    //CONSTRUCTOR
    public Autor(int id, String nombre, String pais){
        this.id = id;
        this.nombre = nombre;
        this.pais = pais;
    }

    //GETTERS
    public int getId(){
        return id;
    }
    public String getNombre(){
        return nombre;
    }
    public String getPais(){
        return pais;
    }

    //MOSTRAR AUTOR
    public void mostrarAutor(){
        System.out.println("ID: " + id);
        System.out.println("Nombre: " + nombre);
        System.out.println("Pais:" + pais);
        System.out.println("------------------------------------");
    }
}
