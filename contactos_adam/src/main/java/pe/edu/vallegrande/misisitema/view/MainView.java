package pe.edu.vallegrande.misisitema.view;

import pe.edu.vallegrande.misisitema.controller.MainController;
import pe.edu.vallegrande.misisitema.model.Usuario;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainView {
    
    private MainController ctrl;
    private TableView<Usuario> tabla = new TableView<>();
    
    public MainView(MainController ctrl) {
        this.ctrl = ctrl;
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
        telefono.setPromptText("Telefono");
        empresa.setPromptText("Empresa");
        asunto.setPromptText("Asunto");
        mensaje.setPromptText("Mensaje");
        mensaje.setPrefHeight(120);
        
        Button btnGuardar = new Button("Guardar");
        btnGuardar.setStyle("-fx-padding: 10; -fx-font-size: 13; -fx-background-color: #CC6600; -fx-text-fill: white;");
        btnGuardar.setMaxWidth(Double.MAX_VALUE);
        btnGuardar.setOnAction(e -> {
            if (nombre.getText().isEmpty() || email.getText().isEmpty()) {
                mostrarAlerta("Error", "Campos requeridos");
                return;
            }
            Usuario c = new Usuario(nombre.getText(), email.getText(), telefono.getText(), empresa.getText(), asunto.getText(), mensaje.getText());
            if (ctrl.crearContacto(c)) {
                mostrarAlerta("Exito", "Contacto creado");
                nombre.clear();
                email.clear();
                telefono.clear();
                empresa.clear();
                asunto.clear();
                mensaje.clear();
                cargarTabla();
            }
        });
        
        VBox form = new VBox(12, new Label("Nombre"), nombre, new Label("Correo"), email, new Label("Telefono"), telefono, new Label("Empresa"), empresa, new Label("Asunto"), asunto, new Label("Mensaje"), mensaje, btnGuardar);
        form.setPadding(new Insets(15));
        form.setStyle("-fx-border-color: #CC6600; -fx-border-radius: 5; -fx-border-width: 2;");
        form.setPrefWidth(350);
        return form;
    }
    
    public VBox crearTabla() {
        TableColumn<Usuario, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(cv -> new javafx.beans.property.SimpleObjectProperty<>(cv.getValue().getId()));
        TableColumn<Usuario, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(cv -> new javafx.beans.property.SimpleObjectProperty<>(cv.getValue().getNombre()));
        TableColumn<Usuario, String> colEmail = new TableColumn<>("Email");
        colEmail.setCellValueFactory(cv -> new javafx.beans.property.SimpleObjectProperty<>(cv.getValue().getEmail()));
        TableColumn<Usuario, String> colEstado = new TableColumn<>("Estado");
        colEstado.setCellValueFactory(cv -> new javafx.beans.property.SimpleObjectProperty<>(cv.getValue().getEstado()));
        
        TableColumn<Usuario, Void> colAcciones = new TableColumn<>("Acciones");
        colAcciones.setCellFactory(param -> new TableCell<Usuario, Void>() {
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableRow().getItem() == null) {
                    setGraphic(null);
                } else {
                    Usuario c = getTableRow().getItem();
                    Button acciones = new Button("⋮");
                    acciones.setStyle("-fx-font-size: 14; -fx-padding: 5 10; -fx-min-width: 40;");
                    acciones.setOnAction(e -> mostrarMenuAcciones(c));
                    setGraphic(acciones);
                }
            }
        });
        
        tabla.getColumns().addAll(colId, colNombre, colEmail, colEstado, colAcciones);
        tabla.setPrefHeight(600);
        VBox box = new VBox(5, new Label("Contactos:"), tabla);
        box.setPadding(new Insets(15));
        box.setStyle("-fx-border-color: #CCCCCC; -fx-border-radius: 5;");
        return box;
    }
    
    private void mostrarMenuAcciones(Usuario usuario) {
        ContextMenu menu = new ContextMenu();
        MenuItem editar = new MenuItem("Editar");
        MenuItem eliminar = new MenuItem("Eliminar");
        
        editar.setOnAction(e -> abrirEdicion(usuario));
        eliminar.setOnAction(e -> {
            if (confirmar("¿Eliminar contacto?")) {
                if (ctrl.eliminarContacto(usuario.getId())) {
                    mostrarAlerta("Éxito", "Contacto eliminado");
                    cargarTabla();
                }
            }
        });
        
        menu.getItems().addAll(editar, eliminar);
        menu.show(tabla, javafx.geometry.Side.RIGHT, 0, 0);
    }
    
    private void abrirEdicion(Usuario usuario) {
        Stage ventana = new Stage();
        ventana.setTitle("Editar Contacto");
        
        TextField nombre = new TextField(usuario.getNombre());
        TextField email = new TextField(usuario.getEmail());
        TextField telefono = new TextField(usuario.getTelefono());
        TextField empresa = new TextField(usuario.getEmpresa());
        TextField asunto = new TextField(usuario.getAsunto());
        TextArea mensaje = new TextArea(usuario.getMensaje());
        mensaje.setPrefHeight(100);
        
        Button guardar = new Button("Guardar cambios");
        guardar.setStyle("-fx-padding: 10; -fx-font-size: 12; -fx-background-color: #CC6600; -fx-text-fill: white;");
        guardar.setMaxWidth(Double.MAX_VALUE);
        guardar.setOnAction(e -> {
            if (nombre.getText().isEmpty() || email.getText().isEmpty()) {
                mostrarAlerta("Error", "Campos requeridos");
                return;
            }
            usuario.setNombre(nombre.getText());
            usuario.setEmail(email.getText());
            usuario.setTelefono(telefono.getText());
            usuario.setEmpresa(empresa.getText());
            usuario.setAsunto(asunto.getText());
            usuario.setMensaje(mensaje.getText());
            
            if (ctrl.actualizarContacto(usuario)) {
                mostrarAlerta("Éxito", "Contacto actualizado");
                cargarTabla();
                ventana.close();
            }
        });
        
        VBox form = new VBox(10, 
            new Label("Nombre"), nombre, 
            new Label("Email"), email, 
            new Label("Teléfono"), telefono, 
            new Label("Empresa"), empresa, 
            new Label("Asunto"), asunto, 
            new Label("Mensaje"), mensaje, 
            guardar
        );
        form.setPadding(new Insets(15));
        
        ventana.setScene(new Scene(form, 400, 500));
        ventana.showAndWait();
    }
    
    public void cargarTabla() {
        tabla.getItems().setAll(ctrl.obtenerTodos());
    }
    
    public HBox crearContenido() {
        return new HBox(20, crearFormulario(), crearTabla());
    }
    
    public void mostrarAlerta(String titulo, String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(titulo);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
    
    public boolean confirmar(String msg) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle("Confirmar");
        a.setHeaderText(null);
        a.setContentText(msg);
        return a.showAndWait().orElse(ButtonType.NO) == ButtonType.OK;
    }
}
