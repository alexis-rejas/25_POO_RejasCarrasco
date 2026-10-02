package pe.edu.vallegrande.misisitema;

import pe.edu.vallegrande.misisitema.controller.MainController;
import pe.edu.vallegrande.misisitema.model.Conexion;
import pe.edu.vallegrande.misisitema.view.MainView;
import javafx.application.Application;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {
    private MainController ctrl = new MainController();
    private MainView view = new MainView(ctrl);
    
    @Override
    public void start(Stage stage) throws Exception {
        if (Conexion.getConexion() == null) {
            alerta("Error", "No se conecto a BD");
            System.exit(1);
        }
        
        VBox content = new VBox(10, new Label("ADAM - Contactos"), view.crearContenido());
        content.setPadding(new Insets(10));
        content.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 12;");
        
        stage.setScene(new Scene(content, 1200, 700));
        stage.setTitle("ADAM Contactos");
        stage.show();
        view.cargarTabla();
        
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), e -> view.cargarTabla()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
    
    private void alerta(String titulo, String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(titulo);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
    
    @Override
    public void stop() {
        Conexion.cerrarConexion();
    }
    
    public static void main(String[] args) {
        launch();
    }
}
