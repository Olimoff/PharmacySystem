package userPanel;

import database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
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



    /*private ObservableList getUserDataFromSql(String queryUser){
        ObservableList<UserPasswordTable> userTablePassword = FXCollections.observableArrayList();
        try {

            connection = dbConnection.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryUser);
            while(resultSet.next()){
                userTablePassword.add(new UserPasswordTable(
                        resultSet.getString("dbUserPassword")



                ));
            }
            connection.close();
            statement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userTablePassword;

    }*/

    public void actionClose(Event event){
        Node source = (Node)event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.hide();
    }

    @FXML
    private void setUserSaveClick(Event event) {
        String email = userTFEmail.getText().toString();
        String newPassword = userPFNewPass.getText().toString();
        try {
            connection = dbConnection.getConnection();
            statement = connection.createStatement();
            int rowsAffected = statement.executeUpdate("update user set dbUserPassword = \"" + newPassword +
                    "\" where dbUserEmail = \""+ email +"\"");

        connection.close();
        statement.close();
        actionClose(event);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        dbConnection = new DBConnection();

    }
}
