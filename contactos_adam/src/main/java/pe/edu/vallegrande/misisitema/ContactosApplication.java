package pe.edu.vallegrande.misisitema;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pe.edu.vallegrande.misisitema.controller.MainController;
import pe.edu.vallegrande.misisitema.view.MainView;

public class ContactosApplication extends Application {
    private final MainController controller = new MainController();
    private final MainView view = new MainView(controller);

    @Override
    public void start(Stage stage) {
        VBox content = new VBox(10, new Label("ADAM - Contactos"), view.crearContenido());
        content.setPadding(new Insets(10));
        content.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 12;");

        stage.setScene(new Scene(content, 1200, 700));
        stage.setTitle("ADAM Contactos");
        stage.setMinWidth(900);
        stage.setMinHeight(650);
        stage.show();
        view.cargarTabla();
    }
}
