package application.scene;

import application.Config;
import application.ServiceRecord;
import application.scene.controller.ServiceEditWindowController;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import util.fx.FXUtil;
import util.event.Action;

import java.util.Objects;

/**
 *
 */
public class ServiceEditWindow implements Window {

    private static final String FXML_PATH = "/fxml/service_edit_window.fxml";
    private static final String CSS_PATH = "/css/service_edit_window.css";

    private final Stage _stage;


    /**
     * @param serviceRecord
     * @param onApply
     */
    public ServiceEditWindow(ServiceRecord serviceRecord, Action onApply, Action onDelete) {
        _stage = new Stage();

        FXUtil.FXMLLoadData loadData = FXUtil.loadFxml(FXML_PATH);
        ServiceEditWindowController controller = (ServiceEditWindowController) loadData.getController();
        controller.setup(serviceRecord, () -> {
            onApply.invoke();
            close();
        }, () -> {
            onDelete.invoke();
            close();
        });

        _stage.setTitle("Edit a single service record");
        _stage.setResizable(false);
        _stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream(Config.LOGO_PATH))));

        Scene scene = new Scene(loadData.getRoot());
        scene.getStylesheets().add(Config.STYLESHEET_PATH);
        scene.getStylesheets().add(CSS_PATH);
        _stage.setScene(scene);
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
