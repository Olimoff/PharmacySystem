package userPanel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UserTable {

    private StringProperty userFirstName;
    private StringProperty userLastName;
    private StringProperty userEmail;
    private StringProperty userPhoneNumber;

    public  UserTable(String userFirstName, String userLastName,
                      String userEmail, String userPhoneNumber){

        this.userFirstName = new SimpleStringProperty(userFirstName);
        this.userLastName = new SimpleStringProperty(userLastName);
        this.userEmail = new SimpleStringProperty(userEmail);
        this.userPhoneNumber = new SimpleStringProperty(userPhoneNumber);

    }


    public String getUserFirstName() {
        return userFirstName.get();
    }

    public StringProperty userFirstNameProperty() {
        return userFirstName;
    }

    public String getUserLastName() {
        return userLastName.get();
    }

    public StringProperty userLastNameProperty() {
        return userLastName;
    }

    public String getUserEmail() {
        return userEmail.get();
    }

    public StringProperty userEmailProperty() {
        return userEmail;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber.get();
    }

    public StringProperty userPhoneNumberProperty() {
        return userPhoneNumber;
    }



    public void setUserFirstName(String value) {
        userFirstName.set(value);
    }

    public void setUserLastName(String value) {
        userLastName.set(value);
    }

    public void setUserEmail(String value) {
        userEmail.set(value);
    }

    public void setUserPhoneNumber(String value) {
        userPhoneNumber.set(value);
    }
}
