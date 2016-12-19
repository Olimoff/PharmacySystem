package loginPanel;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainLogin extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("MainLogin.fxml"));
        primaryStage.setTitle("Pharmacy Information System");
        primaryStage.setScene(new Scene(root, 600, 350));
        primaryStage.show();
    }



}
