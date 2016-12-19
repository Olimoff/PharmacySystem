package adminPanel;

import database.DBConnection;
import drugPanel.DrugTable;
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
import sun.invoke.empty.Empty;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminController implements Initializable {


    @FXML
    private Button adminSaveButtonClick;
    @FXML
    private Button adminClearButtonClick;


    @FXML
    private TextField adminSerialNumber;
    @FXML
    private TextField adminDrugName;
    @FXML
    private TextField adminProducerName;
    @FXML
    private ChoiceBox adminCbCategory;
    @FXML
    private TextField adminDrugCost;
    @FXML
    private DatePicker adminDateIssue;
    @FXML
    private DatePicker adminDateExpiry;
    @FXML
    private TextField adminDescription;
    @FXML
    private TextField adminDrugSearch;


    @FXML
    private TableView<DrugTable> adminTableDrug;
    @FXML
    private TableColumn<DrugTable, String> adminColumnSerialNumber;
    @FXML
    private TableColumn<DrugTable, String> adminColumnDrugName;
    @FXML
    private TableColumn<DrugTable, String> adminColumnProducer;
    @FXML
    private TableColumn<DrugTable, String> adminColumnCategory;
    @FXML
    private TableColumn<DrugTable, String> adminColumnDrugCost;
    @FXML
    private TableColumn<DrugTable, String> adminColumnDateIssue;
    @FXML
    private TableColumn<DrugTable, String> adminColumnDateExpiry;
    @FXML
    private TableColumn<DrugTable, String> adminColumnDescription;


    private boolean isSetAdminAddNewButtonClick;
    private boolean isSetAdminEditButtonClick;
    private DBConnection dc;
    private Statement statement;
    private Connection connection;
    private ResultSet resultSet;
    private String temp;
    private CloseProject closeProject = new CloseProject();


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        dc = new DBConnection();

        adminColumnSerialNumber.setCellValueFactory(new PropertyValueFactory<DrugTable, String>("serialNumber"));
        adminColumnDrugName.setCellValueFactory(new PropertyValueFactory<DrugTable, String>("drugName"));
        adminColumnProducer.setCellValueFactory(new PropertyValueFactory<DrugTable, String>("producerName"));
        adminColumnCategory.setCellValueFactory(new PropertyValueFactory<DrugTable, String>("category"));
        adminColumnDrugCost.setCellValueFactory(new PropertyValueFactory<DrugTable, String>("drugCost"));
        adminColumnDateIssue.setCellValueFactory(new PropertyValueFactory<DrugTable, String>("dateIssue"));
        adminColumnDateExpiry.setCellValueFactory(new PropertyValueFactory<DrugTable, String>("dateExpiry"));
        adminColumnDescription.setCellValueFactory(new PropertyValueFactory<DrugTable, String>("description"));

        adminTableDrug.setItems(getDataFromSql("SELECT * FROM drug;"));

    }


    private ObservableList getDataFromSql(String query){
        ObservableList<DrugTable> adminTableData = FXCollections.observableArrayList();
        try {

            connection = dc.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                adminTableData.add(new DrugTable(
                        resultSet.getString("dbSerialNumber"),
                        resultSet.getString("dbDrugName"),
                        resultSet.getString("dbProducerName"),
                        resultSet.getString("dbCategory"),
                        resultSet.getString("dbDrugCost"),
                        resultSet.getString("dbDateIssue"),
                        resultSet.getString("dbDateExpiry"),
                        resultSet.getString("dbDescription")

                ));
            }
            connection.close();
            statement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return adminTableData;


    }

    /*@FXML
    private void setAdminLoadButtonClick(ActionEvent actionEvent){
        try {
            Connection connection = dc.Connect();
            data = FXCollections.observableArrayList();
            resultSet = connection.createStatement().executeQuery("SELECT * FROM drug");

            while (resultSet.next()){
                data.add(new DrugTable(resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),
                        resultSet.getString(5),resultSet.getString(6),resultSet.getString(7),
                        resultSet.getString(8),resultSet.getString(9)));
            }

        } catch (SQLException e) {
            System.out.println("Error" + e);
        }

        adminColumnSerialNumber.setCellValueFactory(new PropertyValueFactory<DrugTable, String>("serialNumber"));
        adminColumnDrugName.setCellValueFactory(new PropertyValueFactory<DrugTable, String>("drugName"));
        adminColumnProducer.setCellValueFactory(new PropertyValueFactory<DrugTable, String>("producerName"));
        adminColumnCategory.setCellValueFactory(new PropertyValueFactory<DrugTable, String>("category"));
        adminColumnDrugCost.setCellValueFactory(new PropertyValueFactory<DrugTable, String>("drugCost"));
        adminColumnDateIssue.setCellValueFactory(new PropertyValueFactory<DrugTable, String>("dateIssue"));
        adminColumnDateExpiry.setCellValueFactory(new PropertyValueFactory<DrugTable, String>("dateExpiry"));
        adminColumnDescription.setCellValueFactory(new PropertyValueFactory<DrugTable, String>("description"));

        adminTableDrug.setItems(null);
        adminTableDrug.setItems(data);

    }*/


    @FXML
    private void setAdminAddNewButtonClick(Event event){
        adminSetAllEnable();
        isSetAdminAddNewButtonClick = true;
    }

    private void adminSetAllEnable(){
        adminSerialNumber.setDisable(false);
        adminDrugName.setDisable(false);
        adminProducerName.setDisable(false);
        adminCbCategory.setDisable(false);
        adminDrugCost.setDisable(false);
        adminDateIssue.setDisable(false);
        adminDateExpiry.setDisable(false);
        adminDescription.setDisable(false);


        adminSaveButtonClick.setDisable(false);
        adminClearButtonClick.setDisable(false);

    }


    private void adminSetAllDisable(){
        adminSerialNumber.setDisable(true);
        adminDrugName.setDisable(true);
        adminProducerName.setDisable(true);
        adminCbCategory.setDisable(true);
        adminDrugCost.setDisable(true);
        adminDateIssue.setDisable(true);
        adminDateExpiry.setDisable(true);
        adminDescription.setDisable(true);


        adminSaveButtonClick.setDisable(true);
        adminClearButtonClick.setDisable(true);

    }


    @FXML
    private void setAdminSaveButtonClick(Event event){
        try{
            connection = dc.getConnection();
            statement = connection.createStatement();
            if(emptyError()) {
                if (isSetAdminAddNewButtonClick) {
                    int rowsAffected = statement.executeUpdate("insert into`drug` " +
                            "(`dbSerialNumber`,`dbDrugName`,`dbProducerName`,`dbCategory`," +
                            "`dbDrugCost`,`dbDateIssue`,`dbDateExpiry`,`dbDescription`" + ") " +
                            "values ('" + adminSerialNumber.getText() + "','" + adminDrugName.getText() + "','" + adminProducerName.getText()
                            + "','" + adminCbCategory.getValue().toString().trim()
                            + "','" + adminDrugCost.getText()
                            + "','" + adminDateIssue.getValue()
                            + "','" + adminDateExpiry.getValue()
                            + "','" + adminDescription.getText()

                            + "'); ");
                } else if (isSetAdminEditButtonClick) {
                    int rowsAffected = statement.executeUpdate("update drug set " +
                            "dbSerialNumber = '" + adminSerialNumber.getText() + "'," +
                            "dbDrugName = '" + adminDrugName.getText() + "'," +
                            "dbProducerName = '" + adminProducerName.getText() + "'," +
                            "dbCategory = '" + adminCbCategory.getValue().toString().trim() + "'," +
                            "dbDrugCost = '" + adminDrugCost.getText() + "'," +
                            "dbDateIssue = '" + adminDateIssue.getValue() + "'," +
                            "dbDateExpiry = '" + adminDateExpiry.getValue() + "'," +
                            "dbDescription = '" + adminDescription.getText() +


                            "' where dbDrugName = '" +
                            temp + "';");

                }
            }
            connection.close();
            statement.close();
            resultSet.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        adminSetAllClear();
        adminSetAllDisable();
        adminTableDrug.setItems(getDataFromSql("SELECT * FROM drug;"));
        isSetAdminEditButtonClick=false;
        isSetAdminAddNewButtonClick = false;
    }


    private void adminSetAllClear(){
        adminSerialNumber.clear();
        adminDrugName.clear();
        adminProducerName.clear();
        adminDrugCost.clear();
        adminDescription.clear();
        adminDateIssue.setValue(null);
        adminDateExpiry.setValue(null);
        adminCbCategory.getSelectionModel().clearSelection();
    }

    @FXML
    private void setAdminClearButtonClick(Event event){
        adminSetAllClear();
        adminSetAllDisable();
        isSetAdminEditButtonClick=false;
        isSetAdminAddNewButtonClick = false;
    }

    @FXML
    private void setAdminRefreshButtonClick(Event event){
        adminTableDrug.setItems(getDataFromSql("SELECT * FROM drug;"));
        adminDrugSearch.clear();
    }


    @FXML
    private void setAdminSearchButtonClick(Event event){
        String sqlQuery = "select * FROM drug where dbDrugName = '"+adminDrugSearch.getText()+"';";
        adminTableDrug.setItems(getDataFromSql(sqlQuery));
    }

    @FXML
    private void setAdminEditButtonClick(Event event){
        DrugTable getSelectedRow = adminTableDrug.getSelectionModel().getSelectedItem();


        String sqlQuery = "select * FROM drug where dbDrugName = '"+getSelectedRow.getDrugName()+"';";

        try {
            connection = dc.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlQuery);
            adminSetAllEnable();
            if(resultSet.next()) {
                adminSerialNumber.setText(resultSet.getString("dbSerialNumber"));
                adminDrugName.setText(resultSet.getString("dbDrugName"));
                adminProducerName.setText(resultSet.getString("dbProducerName"));
                adminCbCategory.setValue(resultSet.getString("dbCategory"));
                adminDrugCost.setText(resultSet.getString("dbDrugCost"));
                adminDescription.setText(resultSet.getString("dbDescription"));
                try {
                    if (!(resultSet.getString("dbDateIssue").isEmpty()) /*&& (resultSet.getString("dbDateExpiry").isEmpty())*/) {
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

    }

    @FXML
    private void setAdminDeleteButtonClick(Event event){

        DrugTable getSelectedRow = adminTableDrug.getSelectionModel().getSelectedItem();
        String sqlQuery = "delete from drug where dbSerialNumber = '"+getSelectedRow.getSerialNumber()+"';";
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation dialog");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure to delete?");
        Optional <ButtonType> action = alert.showAndWait();

        if(action.get() == ButtonType.OK){
            try {
                connection = dc.getConnection();
                statement = connection.createStatement();

                statement.executeUpdate(sqlQuery);
                statement.executeUpdate("delete from drug where dbSerialNumber ='"+getSelectedRow.getSerialNumber()+"';");
                adminTableDrug.setItems(getDataFromSql("SELECT * FROM drug;"));

            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }




    }


    private boolean emptyError(){
        boolean fillup;
        if (adminSerialNumber.getText().isEmpty()||adminDrugName.getText().isEmpty()|| adminProducerName.getText().isEmpty() ||
                adminCbCategory.getItems().isEmpty() || adminDrugCost.getText().isEmpty() ||
                adminDateIssue.getValue().toString().isEmpty() || adminDateExpiry.getValue().toString().isEmpty()
                || adminDescription.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Some fields did not filled!!!");

            alert.showAndWait();

            fillup = false;
        }
        else fillup = true;
        return fillup;
    }

    @FXML
    private void setAdminCloseButtonClick(Event event){
        closeProject.close();
    }

    @FXML
    private void setAdminUserPanelClick(Event event)throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/adminPanel/UserDetails.fxml"));
        loader.load();
        Parent pa = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(pa));
        stage.setTitle("Admin Panel");
        stage.show();
    }


}
