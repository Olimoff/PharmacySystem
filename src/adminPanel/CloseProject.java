package adminPanel;

import javafx.application.Platform;

public class CloseProject {
    public void close(){
        Platform.exit();
        System.exit(0);
    }
}
