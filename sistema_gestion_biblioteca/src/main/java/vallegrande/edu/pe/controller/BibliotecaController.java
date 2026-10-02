package vallegrande.edu.pe.controller;

import vallegrande.edu.pe.model.Libro;
import vallegrande.edu.pe.model.Autor;
import vallegrande.edu.pe.model.Categoria;

import java.awt.*;
import java.util.ArrayList;
import java.util.Locale;

public class BibliotecaController {

    //Lista donden almacenaremos nuestros libros
    private ArrayList<Libro> libros;
    //Lista donden almacenaremos nuestros autores
    private ArrayList<Autor> autores;
    //Lista donden almacenaremos nuestras categorias
    private ArrayList<Categoria> categorias;

    //Constructor
    public BibliotecaController(){
        libros = new ArrayList<>();
        autores = new ArrayList<>();
        categorias = new ArrayList<>();
    }

    //Registrar
    public void agregarLibro(Libro libro){
        libros.add(libro);
        System.out.println("Libro registrado correctamente");
    }

    //Listar
    public void listarLibros(){
        if(libros.isEmpty()){
            System.out.println("No hay libros registrados");
            return;
        }
        System.out.println("LISTA DE LIBROS");
        for ( Libro libro: libros){
            libro.mostrarLibro();
        }
    }
    //Buscar
    public void buscarLibro(String criterio){
        boolean encontrado = false;
        String texto = criterio.toLowerCase();
        for ( Libro libro: libros){
            if(libro.getTitulo().toLowerCase().contains(texto) ||
                    libro.getAutor().toLowerCase().contains(texto)) {
                libro.mostrarLibro();
                encontrado = true;
            }
        }
        if (!encontrado){
            System.out.println("No se encontro ningun libro");
        }
    }

    //Registrar Autor
    public void agregarAutor(Autor autor){
        autores.add(autor);
        System.out.println("Autor registrado correctamente");
    }

    //Listar Autores
    public void listarAutores(){
        if(autores.isEmpty()){
            System.out.println("No hay autores registrados");
            return;
        }
        System.out.println("LISTA DE AUTORES");
        for ( Autor autor: autores){
            autor.mostrarAutor();
        }
    }

    //Registrar Categoria
    public void agregarCategoria(Categoria categoria){
        categorias.add(categoria);
        System.out.println("Categoria registrada correctamente");
    }

    //Listar Categorias
    public void listarCategorias(){
        if(categorias.isEmpty()){
            System.out.println("No hay categorias registradas");
            return;
        }
        System.out.println("LISTA DE CATEGORIAS");
        for ( Categoria categoria: categorias){
            categoria.mostrarCategoria();
        }
    }

}