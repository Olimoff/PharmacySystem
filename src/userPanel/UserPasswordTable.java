package userPanel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UserPasswordTable {

    private StringProperty userPassword;

    public UserPasswordTable(String userPassword) {
        this.userPassword = new SimpleStringProperty(userPassword);
    }

    public String getUserPassword() {
        return userPassword.get();
    }

    public StringProperty userPasswordProperty() {
        return userPassword;
    }

    public void setUserPassword(String value) {
        userPassword.set(value);
    }
}
