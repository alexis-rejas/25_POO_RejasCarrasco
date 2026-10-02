package vallegrande.edu.pe.crud.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import vallegrande.edu.pe.crud.model.Mascota;

public class VetView extends VBox {

    private TextField txtNombre = new TextField();
    private TextField txtEspecie = new TextField();
    private TextField txtDueño = new TextField();

    private Button btnRegistrar = new Button("Agregar");
    private Button btnEditar = new Button("Actualizar");
    private Button btnEliminar = new Button("Eliminar");

    private TableView<Mascota> tablaMascotas = new TableView<>();
    private Label lblMensaje = new Label();

    public VetView() {
        setSpacing(15);
        setPadding(new Insets(20));
        setAlignment(Pos.CENTER);
        setStyle("-fx-background-color: #F4F6F7;");

        // Título
        Label titleLabel = new Label("🐾 VetCare - CRUD Recepción");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 20));
        titleLabel.setStyle("-fx-text-fill: #2C3E50;");

        // Formulario
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);

        txtNombre.setPromptText("Ej. Firulais");
        txtEspecie.setPromptText("Ej. Perro");
        txtDueño.setPromptText("Ej. Juan Pérez");

        grid.add(new Label("Mascota:"), 0, 0);
        grid.add(txtNombre, 1, 0);
        grid.add(new Label("Especie:"), 0, 1);
        grid.add(txtEspecie, 1, 1);
        grid.add(new Label("Dueño:"), 0, 2);
        grid.add(txtDueño, 1, 2);

        // Botones de acción
        HBox panelBotones = new HBox(10);
        panelBotones.setAlignment(Pos.CENTER);

        btnRegistrar.setStyle("-fx-background-color: #27AE60; -fx-text-fill: white; -fx-font-weight: bold;");
        btnEditar.setStyle("-fx-background-color: #F39C12; -fx-text-fill: white; -fx-font-weight: bold;");
        btnEliminar.setStyle("-fx-background-color: #E74C3C; -fx-text-fill: white; -fx-font-weight: bold;");

        panelBotones.getChildren().addAll(btnRegistrar, btnEditar, btnEliminar);

        // Configuración de la Tabla (Listar)
        TableColumn<Mascota, String> colNombre = new TableColumn<>("Mascota");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colNombre.setPrefWidth(120);

        TableColumn<Mascota, String> colEspecie = new TableColumn<>("Especie");
        colEspecie.setCellValueFactory(new PropertyValueFactory<>("especie"));
        colEspecie.setPrefWidth(120);

        TableColumn<Mascota, String> colDueño = new TableColumn<>("Dueño");
        colDueño.setCellValueFactory(new PropertyValueFactory<>("dueño"));
        colDueño.setPrefWidth(150);

        tablaMascotas.getColumns().addAll(colNombre, colEspecie, colDueño);
        tablaMascotas.setPrefHeight(180);

        lblMensaje.setStyle("-fx-font-weight: bold;");

        getChildren().addAll(titleLabel, grid, panelBotones, tablaMascotas, lblMensaje);
    }

    public TextField getTxtNombre() { return txtNombre; }
    public TextField getTxtEspecie() { return txtEspecie; }
    public TextField getTxtDueño() { return txtDueño; }
    public Button getBtnRegistrar() { return btnRegistrar; }
    public Button getBtnEditar() { return btnEditar; }
    public Button getBtnEliminar() { return btnEliminar; }
    public TableView<Mascota> getTablaMascotas() { return tablaMascotas; }
    public Label getLblMensaje() { return lblMensaje; }
}