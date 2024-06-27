package application.scene;

import api.RequestAPI;
import application.Config;
import application.scene.controller.MainWindowController;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import util.fx.FXUtil;

import java.util.Objects;

/**
 *
 */
public class MainWindow implements Window {

    private static final String FXML_PATH = "/fxml/main_window.fxml";
    private static final String CSS_PATH = "/css/main_window.css";


    private final Stage _stage;


    /**
     * @param stage
     * @param requestAPI
     */
    public MainWindow(Stage stage, RequestAPI requestAPI) {
        _stage = stage;

        FXUtil.FXMLLoadData loadData = FXUtil.loadFxml(FXML_PATH);
        MainWindowController controller = (MainWindowController)loadData.getController();
        controller.setup(requestAPI);

        _stage.setTitle("Service history");
        _stage.setResizable(false);
        _stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream(Config.LOGO_PATH))));

        Scene scene = new Scene(loadData.getRoot());
        scene.getStylesheets().add(Config.STYLESHEET_PATH);
        scene.getStylesheets().add(CSS_PATH);
        _stage.setScene(scene);

        _stage.setOnCloseRequest(e -> {
            controller.onDestroy();
        });
    }


    /**
     *
     */
    @Override
    public void show() { _stage.show(); }

    /**
     *
     */
    @Override
    public void close() { _stage.close(); }
}
