package vallegrande.edu.pe.misistema.controller;

import javafx.scene.Node;
import vallegrande.edu.pe.misistema.view.MainView;

public class MainController {

    private MainView view;

    // Guardamos las referencias para conservar los formularios y sus listas en pantalla
    private Node usuariosView;
    private Node productosView;
    private Node reportesView;
    private Node configuracionView;
    private Node citasView;

    public MainController(MainView view) {
        this.view = view;
        initEvents();
    }

    private void initEvents() {
        // INICIO: Recrea la vista en cada clic para refrescar el conteo de las tarjetas
        view.getBtnInicio().setOnAction(e ->
                view.getRootLayout().setCenter(view.createInicioView())
        );

        // USUARIOS: Conserva la vista con su botón de Eliminar y su lista activa
        view.getBtnUsuarios().setOnAction(e -> {
            if (usuariosView == null) usuariosView = view.createUsuariosView();
            view.getRootLayout().setCenter(usuariosView);
        });

        // PRODUCTOS: Conserva la vista con su botón de Eliminar y su lista activa
        view.getBtnProductos().setOnAction(e -> {
            if (productosView == null) productosView = view.createProductosView();
            view.getRootLayout().setCenter(productosView);
        });

        // REPORTES: Conserva la vista con su botón de Eliminar y su lista activa
        view.getBtnReportes().setOnAction(e -> {
            if (reportesView == null) reportesView = view.createReportesView();
            view.getRootLayout().setCenter(reportesView);
        });

        // CONFIGURACIÓN: Conserva la vista
        view.getBtnConfiguracion().setOnAction(e -> {
            if (configuracionView == null) configuracionView = view.createConfiguracionView();
            view.getRootLayout().setCenter(configuracionView);
        });

        // CITAS: Conserva la vista con su botón de Eliminar y su lista activa
        view.getBtnCitas().setOnAction(e -> {
            if (citasView == null) citasView = view.createCitasView();
            view.getRootLayout().setCenter(citasView);
        });

        // Cargar vista de inicio por defecto
        view.getBtnInicio().getOnAction().handle(null);
    }
}