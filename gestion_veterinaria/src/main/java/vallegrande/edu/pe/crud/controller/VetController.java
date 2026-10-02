package vallegrande.edu.pe.crud.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import vallegrande.edu.pe.crud.model.Mascota;
import vallegrande.edu.pe.crud.view.VetView;

public class VetController {

    private VetView view;
    private ObservableList<Mascota> listaMascotas = FXCollections.observableArrayList();

    public VetController(VetView view) {
        this.view = view;

        // Asignar lista a la tabla
        this.view.getTablaMascotas().setItems(listaMascotas);

        // Eventos
        this.view.getBtnRegistrar().setOnAction(e -> agregarMascota());
        this.view.getBtnEditar().setOnAction(e -> editarMascota());
        this.view.getBtnEliminar().setOnAction(e -> eliminarMascota());

        // Evento para cargar datos en las cajas al seleccionar una fila de la tabla
        this.view.getTablaMascotas().getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> seleccionarFila(newSelection)
        );
    }

    private void agregarMascota() {
        String nombre = view.getTxtNombre().getText();
        String especie = view.getTxtEspecie().getText();
        String dueño = view.getTxtDueño().getText();

        if (nombre.isEmpty() || especie.isEmpty() || dueño.isEmpty()) {
            mostrarMensaje("Por favor, completa todos los campos.", "#E74C3C");
            return;
        }

        Mascota nueva = new Mascota(nombre, especie, dueño);
        listaMascotas.add(nueva);
        mostrarMensaje("Mascota agregada correctamente.", "#27AE60");
        limpiarCampos();
    }

    private void seleccionarFila(Mascota mascota) {
        if (mascota != null) {
            view.getTxtNombre().setText(mascota.getNombre());
            view.getTxtEspecie().setText(mascota.getEspecie());
            view.getTxtDueño().setText(mascota.getDueño());
        }
    }

    private void editarMascota() {
        Mascota seleccionada = view.getTablaMascotas().getSelectionModel().getSelectedItem();

        if (seleccionada == null) {
            mostrarMensaje("Selecciona una mascota de la tabla para editar.", "#E74C3C");
            return;
        }

        seleccionada.setNombre(view.getTxtNombre().getText());
        seleccionada.setEspecie(view.getTxtEspecie().getText());
        seleccionada.setDueño(view.getTxtDueño().getText());

        view.getTablaMascotas().refresh();
        mostrarMensaje("Mascota actualizada correctamente.", "#F39C12");
        limpiarCampos();
    }

    private void eliminarMascota() {
        Mascota seleccionada = view.getTablaMascotas().getSelectionModel().getSelectedItem();

        if (seleccionada == null) {
            mostrarMensaje("Selecciona una mascota de la tabla para eliminar.", "#E74C3C");
            return;
        }

        listaMascotas.remove(seleccionada);
        mostrarMensaje("Mascota eliminada.", "#E74C3C");
        limpiarCampos();
    }

    private void limpiarCampos() {
        view.getTxtNombre().clear();
        view.getTxtEspecie().clear();
        view.getTxtDueño().clear();
        view.getTablaMascotas().getSelectionModel().clearSelection();
    }

    private void mostrarMensaje(String texto, String colorHex) {
        view.getLblMensaje().setStyle("-fx-text-fill: " + colorHex + "; -fx-font-weight: bold;");
        view.getLblMensaje().setText(texto);
    }
}