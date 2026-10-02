package vallegrande.edu.pe.misistema.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class MainView {

    private BorderPane rootLayout;
    private Button btnInicio, btnUsuarios, btnProductos, btnReportes, btnConfiguracion, btnCitas;

    // Listas globales que mantienen los datos
    private ObservableList<String> listaUsuarios = FXCollections.observableArrayList();
    private ObservableList<String> listaProductos = FXCollections.observableArrayList();
    private ObservableList<String> listaReportes = FXCollections.observableArrayList();
    private ObservableList<String> listaCitas = FXCollections.observableArrayList();

    public MainView() {
        rootLayout = new BorderPane();
        initMenu();
    }

    private void initMenu() {
        VBox sideMenu = new VBox(10);
        sideMenu.setPadding(new Insets(15));
        sideMenu.setStyle("-fx-background-color: #1e293b; -fx-pref-width: 180px;");

        Label title = new Label("SISTEMA");
        title.setStyle("-fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;");
        sideMenu.getChildren().add(title);

        btnInicio = createMenuBtn("Inicio");
        btnUsuarios = createMenuBtn("Usuarios");
        btnProductos = createMenuBtn("Productos");
        btnReportes = createMenuBtn("Reportes");
        btnConfiguracion = createMenuBtn("Configuración");
        btnCitas = createMenuBtn("Citas");

        sideMenu.getChildren().addAll(btnInicio, btnUsuarios, btnProductos, btnReportes, btnConfiguracion, btnCitas);
        rootLayout.setLeft(sideMenu);
    }

    private Button createMenuBtn(String text) {
        Button btn = new Button(text);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setStyle("-fx-background-color: transparent; -fx-text-fill: #ecf0f1; -fx-alignment: CENTER-LEFT;");
        btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: #334155; -fx-text-fill: white; -fx-alignment: CENTER-LEFT;"));
        btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: transparent; -fx-text-fill: #ecf0f1; -fx-alignment: CENTER-LEFT;"));
        return btn;
    }

    // --- VISTA DE INICIO CON TARJETAS DE RESUMEN ---
    public Node createInicioView() {
        VBox container = new VBox(20);
        container.setPadding(new Insets(20));

        Label title = new Label("Resumen General del Sistema");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #1e293b;");

        // Contenedor horizontal para las tarjetas
        FlowPane grid = new FlowPane(15, 15);

        // Tarjetas dinámicas conectadas al tamaño de las listas
        grid.getChildren().addAll(
                createCard("Usuarios Registrados", String.valueOf(listaUsuarios.size()), "#2563eb"),
                createCard("Productos en Stock", String.valueOf(listaProductos.size()), "#059669"),
                createCard("Reportes Generados", String.valueOf(listaReportes.size()), "#d97706"),
                createCard("Citas Agendadas", String.valueOf(listaCitas.size()), "#7c3aed")
        );

        container.getChildren().addAll(title, grid);
        return container;
    }

    // Método auxiliar para construir tarjetas de diseño
    private VBox createCard(String titulo, String valor, String colorHex) {
        VBox card = new VBox(8);
        card.setPadding(new Insets(15));
        card.setPrefSize(160, 100);
        card.setStyle("-fx-background-color: white; -fx-background-radius: 8px; "
                + "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 8, 0, 0, 2); "
                + "-fx-border-color: " + colorHex + "; -fx-border-width: 0 0 0 5px;");

        Label lblTitle = new Label(titulo);
        lblTitle.setStyle("-fx-font-size: 12px; -fx-text-fill: #64748b; -fx-font-weight: bold;");

        Label lblValue = new Label(valor);
        lblValue.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: " + colorHex + ";");

        card.getChildren().addAll(lblTitle, lblValue);
        return card;
    }

    // --- MÓDULOS DE REGISTRO ---

    public Node createUsuariosView() {
        VBox box = new VBox(10); box.setPadding(new Insets(20));
        TextField txtUser = new TextField(); txtUser.setPromptText("Nombre de Usuario");
        TextField txtRol = new TextField(); txtRol.setPromptText("Rol");
        Button btnAdd = new Button("Agregar"); Button btnDel = new Button("Eliminar");
        ListView<String> listView = new ListView<>(listaUsuarios);

        btnAdd.setOnAction(e -> { if(!txtUser.getText().isEmpty()) { listaUsuarios.add(txtUser.getText() + " (" + txtRol.getText() + ")"); txtUser.clear(); txtRol.clear(); } });
        btnDel.setOnAction(e -> { if(listView.getSelectionModel().getSelectedItem() != null) listaUsuarios.remove(listView.getSelectionModel().getSelectedItem()); });

        box.getChildren().addAll(new Label("Usuarios"), txtUser, txtRol, new HBox(10, btnAdd, btnDel), listView);
        return box;
    }

    public Node createProductosView() {
        VBox box = new VBox(10); box.setPadding(new Insets(20));
        TextField txtProd = new TextField(); txtProd.setPromptText("Producto");
        TextField txtPrecio = new TextField(); txtPrecio.setPromptText("Precio");
        Button btnAdd = new Button("Agregar"); Button btnDel = new Button("Eliminar");
        ListView<String> listView = new ListView<>(listaProductos);

        btnAdd.setOnAction(e -> { if(!txtProd.getText().isEmpty()) { listaProductos.add(txtProd.getText() + " - S/. " + txtPrecio.getText()); txtProd.clear(); txtPrecio.clear(); } });
        btnDel.setOnAction(e -> { if(listView.getSelectionModel().getSelectedItem() != null) listaProductos.remove(listView.getSelectionModel().getSelectedItem()); });

        box.getChildren().addAll(new Label("Productos"), txtProd, txtPrecio, new HBox(10, btnAdd, btnDel), listView);
        return box;
    }

    public Node createReportesView() {
        VBox box = new VBox(10); box.setPadding(new Insets(20));
        TextArea txtReporte = new TextArea(); txtReporte.setPromptText("Detalle del reporte"); txtReporte.setPrefRowCount(3);
        Button btnAdd = new Button("Generar"); Button btnDel = new Button("Eliminar");
        ListView<String> listView = new ListView<>(listaReportes);

        btnAdd.setOnAction(e -> { if(!txtReporte.getText().isEmpty()) { listaReportes.add("Reporte: " + txtReporte.getText()); txtReporte.clear(); } });
        btnDel.setOnAction(e -> { if(listView.getSelectionModel().getSelectedItem() != null) listaReportes.remove(listView.getSelectionModel().getSelectedItem()); });

        box.getChildren().addAll(new Label("Reportes"), txtReporte, new HBox(10, btnAdd, btnDel), listView);
        return box;
    }

    public Node createConfiguracionView() {
        VBox box = new VBox(10); box.setPadding(new Insets(20));
        box.getChildren().addAll(new Label("Configuración"), new TextField("Mi Sistema JavaFX"), new Button("Guardar"));
        return box;
    }

    public Node createCitasView() {
        VBox box = new VBox(10); box.setPadding(new Insets(20));
        DatePicker date = new DatePicker();
        TextField txtCliente = new TextField(); txtCliente.setPromptText("Cliente");
        Button btnAdd = new Button("Agendar"); Button btnDel = new Button("Eliminar");
        ListView<String> listView = new ListView<>(listaCitas);

        btnAdd.setOnAction(e -> { if(date.getValue() != null && !txtCliente.getText().isEmpty()) { listaCitas.add("Cita: " + txtCliente.getText() + " - " + date.getValue()); txtCliente.clear(); } });
        btnDel.setOnAction(e -> { if(listView.getSelectionModel().getSelectedItem() != null) listaCitas.remove(listView.getSelectionModel().getSelectedItem()); });

        box.getChildren().addAll(new Label("Citas"), txtCliente, date, new HBox(10, btnAdd, btnDel), listView);
        return box;
    }

    // Getters
    public BorderPane getRootLayout() { return rootLayout; }
    public Button getBtnInicio() { return btnInicio; }
    public Button getBtnUsuarios() { return btnUsuarios; }
    public Button getBtnProductos() { return btnProductos; }
    public Button getBtnReportes() { return btnReportes; }
    public Button getBtnConfiguracion() { return btnConfiguracion; }
    public Button getBtnCitas() { return btnCitas; }
}