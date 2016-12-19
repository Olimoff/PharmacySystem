package adminPanel;

import database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import userPanel.UserTable;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminUserDetailsController implements Initializable {

    @FXML
    private Button adminUserSaveButtonClick;
    @FXML
    private Button adminUserClearButtonClick;


    @FXML
    private TextField adminFirstName;
    @FXML
    private TextField adminLastName;
    @FXML
    private TextField adminEmail;
    @FXML
    private TextField adminPhoneNumber;
    @FXML
    private TextField adminUserSearch;


    @FXML
    private TableView<UserTable> adminUserTableView;
    @FXML
    private TableColumn<UserTable, String> adminUserColumnFName;
    @FXML
    private TableColumn<UserTable, String> adminUserColumnLName;
    @FXML
    private TableColumn<UserTable, String> adminUserColumnEmail;
    @FXML
    private TableColumn<UserTable, String> adminUserColumnPhoneNumber;


    private boolean isSetAdminUserAddNewButtonClick;
    private boolean isSetAdminUserEditButtonClick;
    private DBConnection dbConn;
    private Statement stat;
    private Connection conn;
    private ResultSet rs;
    private String tempp;
    private CloseProject close = new CloseProject();

    private ObservableList getUserDataFromSql(String queryUser){
        ObservableList<UserTable> adminUserTableData = FXCollections.observableArrayList();
        try {

            conn = dbConn.getConnection();
            stat = conn.createStatement();
            rs = stat.executeQuery(queryUser);
            while(rs.next()){
                adminUserTableData.add(new UserTable(
                        rs.getString("dbUserFirstName"),
                        rs.getString("dbUserLastName"),
                        rs.getString("dbUserEmail"),
                        rs.getString("dbUserPhoneNumber")


                        ));
            }
            conn.close();
            stat.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return adminUserTableData;

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        dbConn = new DBConnection();

        adminUserColumnFName.setCellValueFactory(new PropertyValueFactory<UserTable, String>("userFirstName"));
        adminUserColumnLName.setCellValueFactory(new PropertyValueFactory<UserTable, String>("userLastName"));
        adminUserColumnEmail.setCellValueFactory(new PropertyValueFactory<UserTable, String>("userEmail"));
        adminUserColumnPhoneNumber.setCellValueFactory(new PropertyValueFactory<UserTable, String>("userPhoneNumber"));

        adminUserTableView.setItems(getUserDataFromSql("SELECT * FROM user;"));

    }

    @FXML
    private void setAdminUserAddNewButtonClick(Event event){
        adminSetAllEnable();
        isSetAdminUserAddNewButtonClick = true;
    }

    private void adminSetAllEnable(){
        adminFirstName.setDisable(false);
        adminLastName.setDisable(false);
        adminEmail.setDisable(false);
        adminPhoneNumber.setDisable(false);


        adminUserSaveButtonClick.setDisable(false);
        adminUserClearButtonClick.setDisable(false);

    }


    private void adminSetAllDisable(){
        adminFirstName.setDisable(true);
        adminLastName.setDisable(true);
        adminEmail.setDisable(true);
        adminPhoneNumber.setDisable(true);


        adminUserSaveButtonClick.setDisable(true);
        adminUserClearButtonClick.setDisable(true);

    }


    @FXML
    private void setAdminUserSaveButtonClick(Event event) {

        if (userEmptyError()) {
            try {
                conn = dbConn.getConnection();
                stat = conn.createStatement();

                if (isSetAdminUserAddNewButtonClick) {
                    int rowsAffected = stat.executeUpdate("insert into`user` " +
                            "(`dbUserFirstName`,`dbUserLastName`,`dbUserEmail`," +
                            "`dbUserPhoneNumber`" + ") " +
                            "values ('" + adminFirstName.getText() + "','" + adminLastName.getText()
                            + "','" + adminEmail.getText()
                            + "','" + adminPhoneNumber.getText()

                            + "'); ");
                } else if (isSetAdminUserEditButtonClick) {

                    int rowsAffected = stat.executeUpdate("update user set " +
                            "dbUserFirstName = '" + adminFirstName.getText() + "'," +
                            "dbUserLastName = '" + adminLastName.getText() + "'," +
                            "dbUserEmail = '" + adminEmail.getText() + "'," +
                            "dbUserPhoneNumber = '" + adminPhoneNumber.getText() +


                            "' where dbUserEmail = '" +
                            tempp + "';");

                }

                conn.close();
                stat.close();
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            adminSetAllClear();
            adminSetAllDisable();
            adminUserTableView.setItems(getUserDataFromSql("SELECT * FROM user;"));
            isSetAdminUserEditButtonClick = false;
            isSetAdminUserAddNewButtonClick = false;
        }
    }

    private void adminSetAllClear() {
        adminFirstName.clear();
        adminLastName.clear();
        adminEmail.clear();
        adminPhoneNumber.clear();
    }

    @FXML
    private void setAdminUserClearButtonClick(Event event){
        adminSetAllClear();
        adminSetAllDisable();
        isSetAdminUserEditButtonClick=false;
        isSetAdminUserAddNewButtonClick = false;
    }

    @FXML
    private void setAdminUserRefreshButtonClick(Event event){
        adminUserTableView.setItems(getUserDataFromSql("SELECT * FROM user;"));
        adminUserSearch.clear();
    }


    @FXML
    private void setAdminUserSearchButtonClick(Event event){
        String sqlQuery = "select * FROM user where dbUserEmail = '"+adminUserSearch.getText()+"';";
        adminUserTableView.setItems(getUserDataFromSql(sqlQuery));
    }

    @FXML
    private void setAdminUserEditButtonClick(Event event){
        UserTable getSelectedRow = adminUserTableView.getSelectionModel().getSelectedItem();


        String sqlQuery = "select * FROM user where dbUserEmail = '"+getSelectedRow.getUserEmail()+"';";

        try {
            conn = dbConn.getConnection();
            stat = conn.createStatement();
            rs = stat.executeQuery(sqlQuery);
            adminSetAllEnable();
            while(rs.next()) {
                adminFirstName.setText(rs.getString("dbUserFirstName"));
                adminLastName.setText(rs.getString("dbUserLastName"));
                adminEmail.setText(rs.getString("dbUserEmail"));
                adminPhoneNumber.setText(rs.getString("dbUserPhoneNumber"));

            }

            tempp=adminEmail.getText();
            isSetAdminUserEditButtonClick = true;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void setAdminUserDeleteButtonClick(Event event){

        UserTable getSelectedRow = adminUserTableView.getSelectionModel().getSelectedItem();
        String sqlQuery = "delete from user where dbUserEmail = '"+getSelectedRow.getUserEmail()+"';";
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation dialog");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure to delete?");
        Optional<ButtonType> delete = alert.showAndWait();

        if (delete.get() == ButtonType.OK) {
            try {
                conn = dbConn.getConnection();
                stat = conn.createStatement();

                stat.executeUpdate(sqlQuery);
                stat.executeUpdate("delete from user where dbUserEmail ='" + getSelectedRow.getUserEmail() + "';");
                adminUserTableView.setItems(getUserDataFromSql("SELECT * FROM user;"));

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    private boolean userEmptyError(){
        boolean fillup;
        if (adminFirstName.getText().isEmpty() || adminLastName.getText().isEmpty()
                || adminEmail.getText().isEmpty() || adminPhoneNumber.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Some fields did not filled!!");

            alert.showAndWait();

            fillup = false;
        }
        else fillup = true;
        return fillup;
    }


    @FXML
    private void setCloseButtonClick(Event event){
        close.close();
    }

    @FXML
    private void setAdminPanelButtonClick(Event event)throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/adminPanel/AdminPanel.fxml"));
        loader.load();
        Parent pa = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(pa));
        stage.setTitle("Admin Panel");
        stage.show();

    }

}
