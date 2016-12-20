package drugPanel;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class DrugTable {

    private final StringProperty serialNumber;
    private final StringProperty drugName;
    private final StringProperty producerName;
    private final StringProperty category;
    private final StringProperty drugCost;
    private final StringProperty dateIssue;
    private final StringProperty dateExpiry;
    private final StringProperty description;

    public DrugTable(String serialNumber, String drugName, String producerName, String category, String drugCost,
                     String dateIssue, String dateExpiry, String description) {
        this.serialNumber = new SimpleStringProperty(serialNumber);
        this.drugName = new SimpleStringProperty(drugName);
        this.producerName = new SimpleStringProperty(producerName);
        this.category = new SimpleStringProperty(category);
        this.drugCost = new SimpleStringProperty(drugCost);
        this.dateIssue = new SimpleStringProperty(dateIssue);
        this.dateExpiry = new SimpleStringProperty(dateExpiry);
        this.description = new SimpleStringProperty(description);
    }


    public String getSerialNumber() {
        return serialNumber.get();
    }

    public StringProperty serialNumberProperty() {
        return serialNumber;
    }

    public String getDrugName() {
        return drugName.get();
    }

    public StringProperty drugNameProperty() {
        return drugName;
    }

    public String getProducerName() {
        return producerName.get();
    }

    public StringProperty producerNameProperty() {
        return producerName;
    }

    public String getCategory() {
        return category.get();
    }

    public StringProperty categoryProperty() {
        return category;
    }

    public String getDrugCost() {
        return drugCost.get();
    }

    public StringProperty drugCostProperty() {
        return drugCost;
    }

    public String getDateIssue() {
        return dateIssue.get();
    }

    public StringProperty dateIssueProperty() {
        return dateIssue;
    }

    public String getDateExpiry() {
        return dateExpiry.get();
    }

    public StringProperty dateExpiryProperty() {
        return dateExpiry;
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }


    public void setSerialNumber(String value) {
        serialNumber.set(value);
    }

    public void setDrugName(String value) {
        drugName.set(value);
    }

    public void setProducerName(String value) {
        producerName.set(value);
    }

    public void setCategory(String value) {
        category.set(value);
    }

    public void setDrugCost(String value) {
        drugCost.set(value);
    }

    public void setDateIssue(String value) {
        dateIssue.set(value);
    }

    public void setDateExpiry(String value) {
        dateExpiry.set(value);
    }

    public void setDescription(String value) {
        description.set(value);
    }
}
