package application.scene.controller;

import api.Request;
import api.RequestAPI;
import api.RequestType;
import api.StatusCode;
import application.*;
import application.io.DocumentPrinter;
import application.io.ServiceDocument;
import application.scene.CarEditWindow;
import application.scene.OpenWindow;
import application.scene.ServiceEditWindow;
import application.scene.Window;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import util.Util;
import util.net.NetUtil;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class MainWindowController {

    @FXML private VBox _serviceRecordVBox;
    @FXML private TextField _searchTextField;
    @FXML private Label _vinLabel, _makeLabel, _modelLabel, _manufactureDateLabel;
    @FXML private Label _licenseLabel, _registrationDateLabel;
    @FXML private ComboBox<String> _mileageTypeComboBox;

    private final DateTimeFormatter _dateFormatter;
    private final List<Window> _subWindowList;
    private RequestAPI _requestAPI;
    private Car _car;
    private ServiceHistory _serviceHistory;


    /**
     *
     */
    public MainWindowController() {
        _dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        _subWindowList = new ArrayList<>();
    }

    /**
     * @param requestAPI
     */
    public void setup(RequestAPI requestAPI) {
        _requestAPI = requestAPI;
        ObservableList<String> items = FXCollections.observableList(Util.select(MileageType.values(), Enum::name));
        _mileageTypeComboBox.setItems(items);
        _mileageTypeComboBox.setValue(MileageType.KILOMETER.name());

        createNew();
    }

    /**
     *
     */
    public void onDestroy() {
        for (Window window : _subWindowList) {
            window.close();
        }
    }


    /**
     *
     */
    private void createNew() {
        Car car = Car.empty();
        setCar(car);

        ServiceHistory serviceHistory = new ServiceHistory(car);
        setServiceHistory(serviceHistory);
    }


    /**
     * @param car
     */
    public void setCar(Car car) {
        _car = car;
        refreshCarInfo();
    }

    /**
     * @param serviceHistory
     */
    public void setServiceHistory(ServiceHistory serviceHistory) {
        _serviceHistory = serviceHistory;
        refreshServiceInfo();
    }

    /**
     *
     */
    private void refreshCarInfo() {
        _vinLabel.setText(_car.getVIN());
        _licenseLabel.setText(_car.getLicense());
        _makeLabel.setText(_car.getMake());
        _modelLabel.setText(_car.getModel());
        _manufactureDateLabel.setText(_car.getManufactureDate().format(_dateFormatter));
        _registrationDateLabel.setText(_car.getRegistrationDate().format(_dateFormatter));
    }

    /**
     *
     */
    private void refreshServiceInfo() {
        _serviceRecordVBox.getChildren().clear();
        _serviceHistory.sort();
        for (ServiceRecord serviceRecord : _serviceHistory) {
            Node entry = createServiceEntry(serviceRecord);
            _serviceRecordVBox.getChildren().add(entry);
        }
    }


    /**
     * @param serviceRecord
     * @return
     */
    private Node createServiceEntry(final ServiceRecord serviceRecord) {
        HBox root = new HBox();
        root.getStyleClass().add("content-box");
        root.setPadding(new Insets(10));
        root.setOnMouseClicked(e -> {
            if (!e.getButton().equals(MouseButton.SECONDARY))
                return;

            ServiceEditWindow serviceEditWindow = new ServiceEditWindow(serviceRecord, this::refreshServiceInfo, () -> {
                _serviceHistory.delete(serviceRecord);
                refreshServiceInfo();
            });
            _subWindowList.add(serviceEditWindow);
            serviceEditWindow.show();
        });

        Label dateLabel = new Label();
        dateLabel.setText(serviceRecord.getDate().format(_dateFormatter));
        dateLabel.setMinWidth(200);
        dateLabel.setMaxWidth(200);
        root.getChildren().add(dateLabel);

        Label descriptionLabel = new Label();
        descriptionLabel.setText(serviceRecord.getDescription());
        descriptionLabel.setMinWidth(400);
        descriptionLabel.setMaxWidth(400);
        root.getChildren().add(descriptionLabel);

        Label repairerLabel = new Label();
        repairerLabel.setText(serviceRecord.getRepairer());
        root.getChildren().add(repairerLabel);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        spacer.setMaxWidth(1000);
        root.getChildren().add(spacer);

        Label mileageLabel = new Label();
        MileageType mileageType = MileageType.valueOf(_mileageTypeComboBox.getValue());
        mileageLabel.setText(String.valueOf(mileageType.calculate(serviceRecord.getMileage())));
        root.getChildren().add(mileageLabel);
        return root;
    }


    /**
     * @param e
     */
    @FXML
    private void onEditCar(MouseEvent e) {
        if (!e.getButton().equals(MouseButton.SECONDARY))
            return;

        CarEditWindow carEditWindow = new CarEditWindow(_car, this::refreshCarInfo, () -> {
            String url = "/" + _car.getID() + ".txt";
            Request carRequest = new Request(RequestType.DELETE, url, null);
            _requestAPI.sendRequest(Config.CAR_SERVER_HOST, carRequest, carResponse -> {
                if (!StatusCode.OK.equals(carResponse.getStatus()))
                    return;

                Request serviceRequest = new Request(RequestType.DELETE, url, null);
                _requestAPI.sendRequest(Config.SERVICE_SERVER_HOST, serviceRequest, serviceResponse -> {
                    if (StatusCode.OK.equals(serviceResponse.getStatus()))
                        System.out.println("Data successfully deleted!");
                });
            });
        });
        _subWindowList.add(carEditWindow);
        carEditWindow.show();
    }

    /**
     * @param e
     */
    @FXML
    private void onSearch(ActionEvent e) {
        _serviceRecordVBox.getChildren().clear();
        for (ServiceRecord serviceRecord : _serviceHistory) {
            if (!_searchTextField.getText().isEmpty() && !serviceRecord.getDescription().toLowerCase().contains(_searchTextField.getText().toLowerCase()))
                continue;

            Node entry = createServiceEntry(serviceRecord);
            _serviceRecordVBox.getChildren().add(entry);
        }
    }

    /**
     * @param e
     */
    @FXML
    private void onNew(ActionEvent e) { createNew(); }

    /**
     * @param e
     */
    @FXML
    private void onOpen(ActionEvent e) {
        OpenWindow openWindow = new OpenWindow(_requestAPI, (car, serviceHistory) -> {
            setCar(car);
            if (serviceHistory == null)
                serviceHistory = new ServiceHistory(car);
            setServiceHistory(serviceHistory);
        });
        _subWindowList.add(openWindow);
        openWindow.show();
    }

    /**
     * @param e
     */
    @FXML
    private void onSave(ActionEvent e) {
        String url = "/" + _car.getID() + ".txt";
        Request carRequest = new Request(RequestType.POST, url, _car.serialize());
        _requestAPI.sendRequest(Config.CAR_SERVER_HOST, carRequest, carResponse -> {
            if (!StatusCode.OK.equals(carResponse.getStatus()))
                return;

            Request serviceRequest = new Request(RequestType.POST, url, _serviceHistory.serialize());
            _requestAPI.sendRequest(Config.SERVICE_SERVER_HOST, serviceRequest, serviceResponse -> {
                if (StatusCode.OK.equals(serviceResponse.getStatus()))
                    System.out.println("Data successfully saved!");
            });
        });
    }


    /**
     * @param e
     */
    @FXML
    private void onAdd(ActionEvent e) {
        ServiceRecord serviceRecord = ServiceRecord.empty();
        ServiceEditWindow serviceEditWindow = new ServiceEditWindow(serviceRecord, () -> {
            _serviceHistory.add(serviceRecord);
            refreshServiceInfo();
        }, () -> { });
        _subWindowList.add(serviceEditWindow);
        serviceEditWindow.show();
    }

    /**
     * @param e
     */
    @FXML
    private void onClear(ActionEvent e) {
        _serviceHistory.clear();
        _serviceRecordVBox.getChildren().clear();
    }

    /**
     * @param e
     */
    @FXML
    private void onDelete(ActionEvent e) {
        String url = "/" + _car.getID() + ".txt";
        Request carRequest = new Request(RequestType.DELETE, url, null);
        _requestAPI.sendRequest(Config.CAR_SERVER_HOST, carRequest, carResponse -> {
            if (!StatusCode.OK.equals(carResponse.getStatus()))
                return;

            Request serviceRequest = new Request(RequestType.DELETE, url, null);
            _requestAPI.sendRequest(Config.SERVICE_SERVER_HOST, serviceRequest, serviceResponse -> {
                if (StatusCode.OK.equals(serviceResponse.getStatus()))
                    System.out.println("Data successfully deleted!");
            });
        });
    }

    /**
     * @param e
     */
    @FXML
    private void onPrint(ActionEvent e) {
        ServiceDocument serviceDocument = new ServiceDocument(_car, _serviceHistory);
        DocumentPrinter documentPrinter = new DocumentPrinter(serviceDocument);
        documentPrinter.print();
    }

    /**
     * @param e
     */
    @FXML
    private void onOpenUserManual(ActionEvent e) {
        NetUtil.browse("https://github.com/WilliwadelmaWisky/service-history");
    }
}
