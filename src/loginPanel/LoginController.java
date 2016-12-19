package loginPanel;

import database.DBConnection;
import drugPanel.DrugTable;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import userPanel.UserController;
import userPanel.UserTable;


import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginController {

    @FXML
    private TextField tfEmailID;
    @FXML
    private PasswordField pfPassword;
    @FXML
    private ChoiceBox cbUser;
    @FXML
    private Text txtError;


    @FXML
    private void loginButtonClick(Event event) throws SQLException {
        if (isAllFieldFillup()) {
            String userName = tfEmailID.getText().trim();
            String password = pfPassword.getText();
            String userType = cbUser.getValue().toString().trim();

            switch (userType){
                case "Admin":
                    if (isValidCredentials(userType,userName,password,"Email")){
                        try {
                            Parent adminParent = FXMLLoader.load(getClass().getResource("/adminPanel/AdminPanel.fxml"));
                            Scene adminScene = new Scene(adminParent);
                            Stage adminStage = (Stage) ((Node) event.getSource()) .getScene().getWindow();
                            adminStage.hide();
                            adminStage.setScene(adminScene);
                            adminStage.setTitle("Admin Panel");
                            adminStage.show();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                    break;
                case "Guardian":
                    System.out.println("I am now on Guardian");
                    break;
                /*case "User":
                    if (isValidCredentials(userType,userName,password,"Email")){
                        try {
                            UserController userController = new UserController();
                            userController.setEmail(userName);
                            Parent userParent = FXMLLoader.load(getClass().getResource("/userPanel/UserPanel.fxml"));
                            Scene userScene = new Scene(userParent);
                            Stage userStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            userStage.hide();
                            userStage.setScene(userScene);
                            userStage.setTitle("User Panel");
                            userStage.show();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                    break;

*/
                case "User":
                    if (isValidCredentials(userType,userName,password,"Email")){
                        try {
                            Parent userParent = FXMLLoader.load(getClass().getResource("/userPanel/UserPanel.fxml"));
                            Scene userScene = new Scene(userParent);
                            Stage userStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            userStage.hide();
                            userStage.setScene(userScene);
                            userStage.setTitle("user Panel");
                            userStage.show();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                    break;

            }


        }

    }


    private boolean isValidCredentials(String userType, String userName, String password,String loginType) throws SQLException {
        boolean userPassOk = false;

        DBConnection database = new DBConnection();
        Connection connection = database.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from "+userType.toLowerCase()+" where db"+userType
                +loginType+" = '"+userName+"' and db"+userType+"Password = '"+password+"';");


        while (resultSet.next()){

            if(resultSet.getString("db"+userType+loginType)!=null && resultSet.getString("db"+userType+"Password")!=null){
                userPassOk = true;

                //UserTable getSelectedRow = adminUserTableData.getSelectionModel().getSelectedItem();
            }

        }

        if(!userPassOk) {

            txtError.setText("Incorrect email or password");

            tfEmailID.clear();
            pfPassword.clear();

            userPassOk = false;

        }


        return userPassOk;
    }



    private boolean isAllFieldFillup(){
        boolean fillError;
        if(tfEmailID.getText().trim().isEmpty()||pfPassword.getText().isEmpty()){

            Alert alrt = new Alert(Alert.AlertType.ERROR);
            alrt.setTitle("Attention!!!");
            alrt.setHeaderText(null);
            alrt.setContentText("Email or password should not empty.");
            alrt.showAndWait();
            fillError = false;
        }
        else fillError = true;
        return fillError;
    }

    }