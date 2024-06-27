package application;

import api.RequestAPI;
import application.scene.MainWindow;
import javafx.stage.Stage;

/**
 *
 */
public class Application {

    /**
     * @param stage
     * @param requestAPI
     */
    public Application(Stage stage, RequestAPI requestAPI) {
        MainWindow mainWindow = new MainWindow(stage, requestAPI);
        mainWindow.show();
    }
}
