package vallegrande.edu.pe.crud;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import vallegrande.edu.pe.crud.controller.VetController;
import vallegrande.edu.pe.crud.view.VetView;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        VetView view = new VetView();
        new VetController(view);

        Scene scene = new Scene(view, 450, 480);
        stage.setTitle("VetCare Desktop - Recepción");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}