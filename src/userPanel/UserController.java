package userPanel;

import adminPanel.CloseProject;
import database.DBConnection;
import drugPanel.DrugTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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
    private Button userClearButtonClick;
    @FXML
    private Button logOut;
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
    @FXML
    private TextField userSerialNumber;
    @FXML
    private TextField userDrugName;
    @FXML
    private TextField userProducerName;
    @FXML
    private ChoiceBox userCbCategory;
    @FXML
    private TextField userDrugCost;
    @FXML
    private DatePicker userDateIssue;
    @FXML
    private DatePicker userDateExpiry;
    @FXML
    private TextField userDescription;
    @FXML
    private Button userSaveButton;
    @FXML
    private TextField userTFSearch;


    private DBConnection dcon;
    private Statement state;
    private Connection con;
    private ResultSet res;
    private String tem;
    private CloseProject closeProject = new CloseProject();
    private boolean isSetUserEditButtonClick;


    private ObservableList getDataDrugFromSql(String drugQuery) {
        ObservableList<DrugTable> userDrugTableData = FXCollections.observableArrayList();
        try {

            con = dcon.getConnection();
            state = con.createStatement();
            res = state.executeQuery(drugQuery);
            while (res.next()) {
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


    @FXML
    private void setUserRefreshButtonClick(Event event) {
        userDrugTableView.setItems(getDataDrugFromSql("SELECT * FROM drug;"));
    }

    private void userSetAllEnable() {
        userSerialNumber.setDisable(false);
        userDrugName.setDisable(false);
        userProducerName.setDisable(false);
        userCbCategory.setDisable(false);
        userDrugCost.setDisable(false);
        userDateIssue.setDisable(false);
        userDateExpiry.setDisable(false);
        userDescription.setDisable(false);


        userSaveButton.setDisable(false);
        userClearButtonClick.setDisable(false);

    }

    private void userSetAllClear() {
        userSerialNumber.clear();
        userDrugName.clear();
        userProducerName.clear();
        userDrugCost.clear();
        userDescription.clear();
        userDateIssue.setValue(null);
        userDateExpiry.setValue(null);
        userCbCategory.getSelectionModel().clearSelection();
    }

    private void userSetAllDisable() {
        userSerialNumber.setDisable(true);
        userDrugName.setDisable(true);
        userProducerName.setDisable(true);
        userCbCategory.setDisable(true);
        userDrugCost.setDisable(true);
        userDateIssue.setDisable(true);
        userDateExpiry.setDisable(true);
        userDescription.setDisable(true);


        userSaveButton.setDisable(true);
        userClearButtonClick.setDisable(true);

    }

    public void initialize() {

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

        userSearch();


    }

    @FXML
    private void userSearchButtonClick(Event event) {

    }

    @FXML
    private void setUserLogOut(Event event) {
        Stage userStage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        userStage.hide();
        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        Parent root = null;
        try {
            root = loader.load(getClass().getResource("/loginPanel/MainLogin.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.setTitle("Pharmacy Information System");
        primaryStage.setScene(new Scene(root, 600, 350));
        primaryStage.show();

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
            stage.initOwner(((Node) event.getSource()).getScene().getWindow());
            stage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @FXML
    private void userEditButtonClick(Event event) {
        DrugTable getSelectedRow = userDrugTableView.getSelectionModel().getSelectedItem();


        String sqlQuery = "select * FROM drug where dbDrugName = '" + getSelectedRow.getDrugName() + "';";

        try {
            con = dcon.getConnection();
            state = con.createStatement();
            res = state.executeQuery(sqlQuery);
            userSetAllEnable();
            if (res.next()) {
                userSerialNumber.setText(res.getString("dbSerialNumber"));
                userDrugName.setText(res.getString("dbDrugName"));
                userProducerName.setText(res.getString("dbProducerName"));
                userCbCategory.setValue(res.getString("dbCategory"));
                userDrugCost.setText(res.getString("dbDrugCost"));
                userDescription.setText(res.getString("dbDescription"));
                try {
                    if (!(res.getString("dbDateIssue").isEmpty())) {
                        userDateIssue.setValue(LocalDate.parse(res.getString("dbDateIssue")));
                        userDateExpiry.setValue(LocalDate.parse(res.getString("dbDateExpiry")));
                    }
                } catch (NullPointerException e) {
                    userDateIssue.setValue(null);
                    userDateExpiry.setValue(null);
                }

            }

            tem = userDrugName.getText();
            isSetUserEditButtonClick = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void setUserSearchButtonClick(Event event) {

    }

    @FXML
    private void setUserClearButtonClick(Event event) {
        userSetAllClear();
        userSetAllDisable();
        isSetUserEditButtonClick = false;
    }

    @FXML
    private void setUserSaveButtonClick(Event event) {
        try {
            con = dcon.getConnection();
            state = con.createStatement();
            if (emptyError()) {

                if (isSetUserEditButtonClick) {
                    int rowsAffected = state.executeUpdate("update drug set " +
                            "dbSerialNumber = '" + userSerialNumber.getText() + "'," +
                            "dbDrugName = '" + userDrugName.getText() + "'," +
                            "dbProducerName = '" + userProducerName.getText() + "'," +
                            "dbCategory = '" + userCbCategory.getValue().toString().trim() + "'," +
                            "dbDrugCost = '" + userDrugCost.getText() + "'," +
                            "dbDateIssue = '" + userDateIssue.getValue() + "'," +
                            "dbDateExpiry = '" + userDateExpiry.getValue() + "'," +
                            "dbDescription = '" + userDescription.getText() +


                            "' where dbDrugName = '" +
                            tem + "';");

                }
            }
            con.close();
            state.close();
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        userSetAllClear();
        userSetAllDisable();
        userDrugTableView.setItems(getDataDrugFromSql("SELECT * FROM drug;"));
        isSetUserEditButtonClick = false;
    }

    public void userSearch() {
        FilteredList<DrugTable> filteredList = new FilteredList<>(getDataDrugFromSql("SELECT * FROM drug;"), p -> true);

        userTFSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(person -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();
                if (person.getDrugName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                }
                return false;

            });
        });

        SortedList<DrugTable> sortedList = new SortedList<DrugTable>(filteredList);
        sortedList.comparatorProperty().bind(userDrugTableView.comparatorProperty());
        userDrugTableView.setItems(sortedList);


    }

    private boolean emptyError() {
        boolean fillup;
        if (userSerialNumber.getText().isEmpty() || userDrugName.getText().isEmpty() || userProducerName.getText().isEmpty() ||
                userCbCategory.getItems().isEmpty() || userDrugCost.getText().isEmpty() ||
                userDateIssue.getValue().toString().isEmpty() || userDateExpiry.getValue().toString().isEmpty()
                || userDescription.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Some fields did not filled!!!");

            alert.showAndWait();

            fillup = false;
        } else fillup = true;
        return fillup;
    }


    @FXML
    private void setUserCloseButtonClick(Event event) {
        closeProject.close();
    }


}
