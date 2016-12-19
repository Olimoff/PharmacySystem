package userPanel;

import adminPanel.CloseProject;
import database.DBConnection;
import drugPanel.DrugTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class UserController {


    @FXML
    private Button userSearchButtonClick;
    @FXML
    private Button userClearButtonClick;

    @FXML
    private TextField userTFSerialNumber;
    @FXML
    private TextField userTFDrugName;
    @FXML
    private TextField userTFProducer;
    @FXML
    private ChoiceBox userCbCategory;
    @FXML
    private TextField userTFDescription;


    @FXML
    private Text txtName;
    @FXML
    private Text txtEmail;
    @FXML
    private Text txtNumber;


    @FXML
    TableView<DrugTable> userDrugTableView;
    @FXML
    private TableColumn<DrugTable, String> userCSerialNumber;
    @FXML
    private TableColumn<DrugTable, String> userCDrugName;
    @FXML
    private TableColumn<DrugTable, String> userCProducerName;
    @FXML
    private TableColumn<DrugTable, String> userCCategory;
    @FXML
    private TableColumn<DrugTable, String> userCDrugCost;
    @FXML
    private TableColumn<DrugTable, String> userCDateIssue;
    @FXML
    private TableColumn<DrugTable, String> userCDateExpiry;
    @FXML
    private TableColumn<DrugTable, String> userCDescription;


    private DBConnection dcon;
    private Statement state;
    private Connection con;
    private ResultSet res;
    private String tem;
    private CloseProject closeProject = new CloseProject();
    private TableView<UserTable> adminUserTableView;


    private ObservableList getDataDrugFromSql(String drugQuery){
        ObservableList<DrugTable> userDrugTableData = FXCollections.observableArrayList();
        try {

            con = dcon.getConnection();
            state = con.createStatement();
            res = state.executeQuery(drugQuery);
            while (res.next()){
                userDrugTableData.add(new DrugTable(
                        res.getString("dbSerialNumber"),
                        res.getString("dbDrugName"),
                        res.getString("dbProducerName"),
                        res.getString("dbCategory"),
                        res.getString("dbDrugCost"),
                        res.getString("dbDateIssue"),
                        res.getString("dbDateExpiry"),
                        res.getString("dbDescription")

                ));
            }
            con.close();
            state.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userDrugTableData;
    }


    /*@FXML
    private void setAdminEditButtonClick(Event event){
        DrugTable getSelectedRow = adminTableDrug.getSelectionModel().getSelectedItem();


        String sqlQuery = "select * FROM drug where dbDrugName = '"+getSelectedRow.getDrugName()+"';";

        try {
            connection = dc.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlQuery);
            adminSetAllEnable();
            while(resultSet.next()) {
                adminSerialNumber.setText(resultSet.getString("dbSerialNumber"));
                adminDrugName.setText(resultSet.getString("dbDrugName"));
                adminProducerName.setText(resultSet.getString("dbProducerName"));
                adminCbCategory.setValue(resultSet.getString("dbCategory"));
                adminDrugCost.setText(resultSet.getString("dbDrugCost"));
                adminDescription.setText(resultSet.getString("dbDescription"));
                try {
                    if (!(resultSet.getString("dbDateIssue").isEmpty()) *//*&& (resultSet.getString("dbDateExpiry").isEmpty())*//*) {
                        adminDateIssue.setValue(LocalDate.parse(resultSet.getString("dbDateIssue")));
                        adminDateExpiry.setValue(LocalDate.parse(resultSet.getString("dbDateExpiry")));
                    }
                }
                catch (NullPointerException e){
                    adminDateIssue.setValue(null);
                    adminDateExpiry.setValue(null);
                }

            }

            temp=adminDrugName.getText();
            isSetAdminEditButtonClick = true;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }*/


    public void initialize(){

        dcon = new DBConnection();

        userCSerialNumber.setCellValueFactory(new PropertyValueFactory<DrugTable, String>("serialNumber"));
        userCDrugName.setCellValueFactory(new PropertyValueFactory<DrugTable, String>("drugName"));
        userCProducerName.setCellValueFactory(new PropertyValueFactory<DrugTable, String>("producerName"));
        userCCategory.setCellValueFactory(new PropertyValueFactory<DrugTable, String>("category"));
        userCDrugCost.setCellValueFactory(new PropertyValueFactory<DrugTable, String>("drugCost"));
        userCDateIssue.setCellValueFactory(new PropertyValueFactory<DrugTable, String>("dateIssue"));
        userCDateExpiry.setCellValueFactory(new PropertyValueFactory<DrugTable, String>("dateExpiry"));
        userCDescription.setCellValueFactory(new PropertyValueFactory<DrugTable, String>("description"));

        userDrugTableView.setItems(getDataDrugFromSql("SELECT * FROM drug;"));


    }


    @FXML
    private void setUserChangePassButtonClick(Event event) {
        try {
            Stage stage = new Stage();
            Parent user = FXMLLoader.load(getClass().getResource("../userPanel/ChangePassword.fxml"));
            stage.setMinWidth(180);
            stage.setMinHeight(200);
            stage.setResizable(false);
            stage.setScene(new Scene(user));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node)event.getSource()).getScene().getWindow());
            stage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @FXML
    private void setUserCloseButtonClick(Event event){
        closeProject.close();
    }

}
