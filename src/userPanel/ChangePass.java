package userPanel;

import database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;

public class ChangePass implements Initializable {

    @FXML
    private  PasswordField userPFNewPass;
    @FXML
    private TextField userTFEmail;




    private DBConnection dbConnection;
    private Statement statement;
    private Connection connection;
    private ResultSet resultSet;
    private String temp;
    private PreparedStatement pst;


    public void actionClose(Event event){
        Node source = (Node)event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.hide();
    }

    @FXML
    private void setUserSaveClick(Event event) {
        String email = userTFEmail.getText().toString();
        String newPassword = userPFNewPass.getText().toString();
        if (emptyFill()) {
            try {
                connection = dbConnection.getConnection();
                statement = connection.createStatement();
                int rowsAffected = statement.executeUpdate("update user set dbUserPassword = \"" + newPassword +
                        "\" where dbUserEmail = \"" + email + "\"");

                connection.close();
                statement.close();
                actionClose(event);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void setUserCancelClick(Event event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.hide();

    }


    private boolean emptyFill(){
        boolean fillError;
        if(userTFEmail.getText().trim().isEmpty()||userPFNewPass.getText().isEmpty()){

            Alert alrt = new Alert(Alert.AlertType.ERROR);
            alrt.setTitle("Error Dialog");
            alrt.setHeaderText(null);
            alrt.setContentText("Email or password should not empty.");
            alrt.showAndWait();
            fillError = false;
        }
        else fillError = true;
        return fillError;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        dbConnection = new DBConnection();

    }
}
