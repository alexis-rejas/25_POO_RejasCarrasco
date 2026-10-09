package pe.edu.vallegrande.misisitema.view;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pe.edu.vallegrande.misisitema.controller.MainController;
import pe.edu.vallegrande.misisitema.model.Usuario;

import java.sql.SQLException;
import java.util.List;

public class MainView {
    private final MainController controller;
    private final TableView<Usuario> tabla = new TableView<>();

    public MainView(MainController controller) {
        this.controller = controller;
    }

    public VBox crearFormulario() {
        TextField nombre = new TextField();
        TextField email = new TextField();
        TextField telefono = new TextField();
        TextField empresa = new TextField();
        TextField asunto = new TextField();
        TextArea mensaje = new TextArea();

        nombre.setPromptText("Tu nombre");
        email.setPromptText("tuemail@ejemplo.com");
        telefono.setPromptText("Teléfono");
        empresa.setPromptText("Empresa");
        asunto.setPromptText("Asunto");
        mensaje.setPromptText("Mensaje (mínimo 10 caracteres)");
        mensaje.setPrefHeight(120);
        mensaje.setWrapText(true);

        Button guardar = new Button("Guardar contacto");
        guardar.setStyle(
                "-fx-padding: 10; -fx-font-size: 13; "
                        + "-fx-background-color: #CC6600; -fx-text-fill: white;"
        );
        guardar.setMaxWidth(Double.MAX_VALUE);
        guardar.setOnAction(event -> {
            Usuario contacto = new Usuario(
                    nombre.getText().trim(),
                    email.getText().trim(),
                    telefono.getText().trim(),
                    empresa.getText().trim(),
                    asunto.getText().trim(),
                    mensaje.getText().trim()
            );
            try {
                if (controller.crearContacto(contacto)) {
                    mostrarAlerta("Contacto guardado", "El contacto se guardó correctamente.");
                    nombre.clear();
                    email.clear();
                    telefono.clear();
                    empresa.clear();
                    asunto.clear();
                    mensaje.clear();
                    cargarTabla();
                } else {
                    mostrarError("No se insertó el contacto en la tabla.");
                }
            } catch (IllegalArgumentException error) {
                mostrarError(error.getMessage());
            } catch (SQLException error) {
                mostrarError("No se pudo guardar el contacto en la base de datos.", error);
            }
        });

        VBox form = new VBox(
                8,
                new Label("Nombre *"), nombre,
                new Label("Correo *"), email,
                new Label("Teléfono"), telefono,
                new Label("Empresa"), empresa,
                new Label("Asunto"), asunto,
                new Label("Mensaje *"), mensaje,
                guardar
        );
        form.setPadding(new Insets(15));
        form.setStyle("-fx-border-color: #CC6600; -fx-border-radius: 5; -fx-border-width: 2;");
        form.setPrefWidth(350);
        form.setMinWidth(300);
        return form;
    }

    public VBox crearTabla() {
        TableColumn<Usuario, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getValue().getId()));
        colId.setPrefWidth(55);

        TableColumn<Usuario, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(
                cell -> new ReadOnlyStringWrapper(cell.getValue().getNombre())
        );
        colNombre.setPrefWidth(150);

        TableColumn<Usuario, String> colEmail = new TableColumn<>("Correo");
        colEmail.setCellValueFactory(
                cell -> new ReadOnlyStringWrapper(cell.getValue().getEmail())
        );
        colEmail.setPrefWidth(190);

        TableColumn<Usuario, String> colEstado = new TableColumn<>("Estado");
        colEstado.setCellValueFactory(
                cell -> new ReadOnlyStringWrapper(cell.getValue().getEstado())
        );
        colEstado.setPrefWidth(100);

        TableColumn<Usuario, Void> colAcciones = new TableColumn<>("Acciones");
        colAcciones.setPrefWidth(90);
        colAcciones.setCellFactory(column -> new TableCell<>() {
            private final Button acciones = new Button("Acciones");

            {
                acciones.setOnAction(event -> {
                    Usuario contacto = getTableRow().getItem();
                    if (contacto != null) {
                        mostrarMenuAcciones(contacto);
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty || getTableRow().getItem() == null ? null : acciones);
            }
        });

        tabla.getColumns().setAll(List.of(colId, colNombre, colEmail, colEstado, colAcciones));
        tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        tabla.setPlaceholder(new Label("No hay contactos registrados."));
        tabla.setPrefHeight(600);

        Button actualizar = new Button("Actualizar lista");
        actualizar.setOnAction(event -> cargarTabla());
        VBox box = new VBox(8, new HBox(10, new Label("Contactos"), actualizar), tabla);
        box.setPadding(new Insets(15));
        box.setStyle("-fx-border-color: #CCCCCC; -fx-border-radius: 5;");
        VBox.setVgrow(tabla, javafx.scene.layout.Priority.ALWAYS);
        return box;
    }

    private void mostrarMenuAcciones(Usuario usuario) {
        ContextMenu menu = new ContextMenu();
        MenuItem editar = new MenuItem("Editar");
        MenuItem eliminar = new MenuItem("Eliminar");

        editar.setOnAction(event -> abrirEdicion(usuario));
        eliminar.setOnAction(event -> {
            if (!confirmar("¿Eliminar el contacto de " + usuario.getNombre() + "?")) {
                return;
            }
            try {
                if (controller.eliminarContacto(usuario.getId())) {
                    cargarTabla();
                    mostrarAlerta("Contacto eliminado", "El contacto se eliminó correctamente.");
                } else {
                    mostrarError("No se encontró el contacto para eliminar.");
                }
            } catch (SQLException error) {
                mostrarError("No se pudo eliminar el contacto de la base de datos.", error);
            }
        });

        menu.getItems().setAll(editar, eliminar);
        menu.show(tabla, Side.RIGHT, 0, 0);
    }

    private void abrirEdicion(Usuario original) {
        Stage ventana = new Stage();
        ventana.setTitle("Editar contacto");

        TextField nombre = new TextField(original.getNombre());
        TextField email = new TextField(original.getEmail());
        TextField telefono = new TextField(original.getTelefono());
        TextField empresa = new TextField(original.getEmpresa());
        TextField asunto = new TextField(original.getAsunto());
        TextArea mensaje = new TextArea(original.getMensaje());
        mensaje.setPrefHeight(100);
        mensaje.setWrapText(true);

        Button guardar = new Button("Guardar cambios");
        guardar.setStyle(
                "-fx-padding: 10; -fx-font-size: 12; "
                        + "-fx-background-color: #CC6600; -fx-text-fill: white;"
        );
        guardar.setMaxWidth(Double.MAX_VALUE);
        guardar.setOnAction(event -> {
            Usuario actualizado = new Usuario(
                    original.getId(),
                    nombre.getText().trim(),
                    email.getText().trim(),
                    telefono.getText().trim(),
                    empresa.getText().trim(),
                    asunto.getText().trim(),
                    mensaje.getText().trim(),
                    original.getEstado(),
                    original.getFechaCreacion(),
                    original.getFechaRespuesta()
            );
            try {
                if (controller.actualizarContacto(actualizado)) {
                    ventana.close();
                    cargarTabla();
                    mostrarAlerta("Contacto actualizado", "Los cambios se guardaron correctamente.");
                } else {
                    mostrarError("No se encontró el contacto para actualizar.");
                }
            } catch (IllegalArgumentException error) {
                mostrarError(error.getMessage());
            } catch (SQLException error) {
                mostrarError("No se pudo actualizar el contacto en la base de datos.", error);
            }
        });

        VBox form = new VBox(
                8,
                new Label("Nombre *"), nombre,
                new Label("Correo *"), email,
                new Label("Teléfono"), telefono,
                new Label("Empresa"), empresa,
                new Label("Asunto"), asunto,
                new Label("Mensaje *"), mensaje,
                guardar
        );
        form.setPadding(new Insets(15));
        ventana.setScene(new Scene(form, 420, 620));
        ventana.showAndWait();
    }

    public void cargarTabla() {
        try {
            tabla.getItems().setAll(controller.obtenerTodos());
        } catch (SQLException error) {
            mostrarError("No se pudo cargar la lista de contactos.", error);
        }
    }

    public HBox crearContenido() {
        HBox contenido = new HBox(20, crearFormulario(), crearTabla());
        contenido.setPadding(new Insets(10));
        HBox.setHgrow(contenido.getChildren().get(1), javafx.scene.layout.Priority.ALWAYS);
        return contenido;
    }

    public void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    private void mostrarError(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Error");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    private void mostrarError(String mensaje, Exception error) {
        error.printStackTrace();
        mostrarError(mensaje + "\n\nDetalle: " + error.getMessage());
    }

    private boolean confirmar(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Confirmar");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        return alerta.showAndWait().orElse(ButtonType.NO) == ButtonType.OK;
    }
}
