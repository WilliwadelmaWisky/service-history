package application.scene.controller;

import api.RequestAPI;
import application.Part;
import application.ServicePart;
import application.ServiceRecord;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import util.fx.scene.DecimalField;
import util.fx.scene.IntegerField;
import util.event.Action;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class ServiceEditWindowController {

    @FXML private TextArea _descriptionTextArea;
    @FXML private TextField _repairerTextField;
    @FXML private IntegerField _mileageIntegerField;
    @FXML private DatePicker _datePicker;
    @FXML private VBox _partListVBox;

    private final DateTimeFormatter _dateFormatter;
    private ServiceRecord _serviceRecord;
    private List<ServicePart> _servicePartList;
    private Action _applyAction, _deleteAction;


    /**
     *
     */
    public ServiceEditWindowController() {
        _dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    }

    /**
     * @param serviceRecord
     * @param onApply
     * @param onDelete
     */
    public void setup(ServiceRecord serviceRecord, Action onApply, Action onDelete) {
        _serviceRecord = serviceRecord;
        _applyAction = onApply;
        _deleteAction = onDelete;
        _servicePartList = new ArrayList<>();

        StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate date) { return (date != null) ? _dateFormatter.format(date) : ""; }

            @Override
            public LocalDate fromString(String s) { return (s != null && !s.isEmpty()) ? LocalDate.parse(s, _dateFormatter) : null; }
        };

        _datePicker.setConverter(converter);

        _descriptionTextArea.setText(_serviceRecord.getDescription());
        _repairerTextField.setText(_serviceRecord.getRepairer());
        _mileageIntegerField.setValue(_serviceRecord.getMileage());
        _datePicker.setValue(_serviceRecord.getDate());

        for (ServicePart servicePart : _serviceRecord){
            _servicePartList.add(servicePart);
            Node entry = createPartEntry(servicePart);
            _partListVBox.getChildren().add(entry);
        }
    }


    /**
     * @param e
     */
    @FXML
    private void onApply(ActionEvent e) {
        _serviceRecord.setDescription(_descriptionTextArea.getText());
        _serviceRecord.setRepairer(_repairerTextField.getText());
        _serviceRecord.setMileage(_mileageIntegerField.getValue());
        _serviceRecord.setDate(_datePicker.getValue());

        _serviceRecord.setServiceParts(_servicePartList);

        _applyAction.invoke();
    }

    /**
     * @param e
     */
    @FXML
    private void onDelete(ActionEvent e) {
        _deleteAction.invoke();
    }


    /**
     * @param servicePart
     * @return
     */
    private Node createPartEntry(final ServicePart servicePart) {
        HBox root = new HBox();
        root.setSpacing(2);

        TextField idTextField = new TextField();
        idTextField.setPromptText("Number...");
        idTextField.setText(servicePart.getPart().getID());
        idTextField.setMinWidth(150);
        idTextField.setMaxWidth(150);
        root.getChildren().add(idTextField);
        idTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            servicePart.getPart().setID(newValue);
        });

        TextField nameTextField = new TextField();
        nameTextField.setPromptText("Name...");
        nameTextField.setText(servicePart.getPart().getName());
        nameTextField.setMaxWidth(1000);
        HBox.setHgrow(nameTextField, Priority.ALWAYS);
        root.getChildren().add(nameTextField);
        nameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            servicePart.getPart().setName(newValue);
        });

        DecimalField quantityTextField = new DecimalField();
        quantityTextField.setMinValue(ServicePart.MIN_QUANTITY);
        quantityTextField.setMaxValue(ServicePart.MAX_QUANTITY);
        quantityTextField.setValue(servicePart.getQuantity());
        quantityTextField.setMinWidth(60);
        quantityTextField.setMaxWidth(60);
        root.getChildren().add(quantityTextField);
        quantityTextField.valueProperty().addListener((observable, oldValue, newValue) -> {
            servicePart.setQuantity(newValue.doubleValue());
        });
        return root;
    }


    /**
     * @param e
     */
    @FXML
    private void onAddPart(ActionEvent e) {
        Part part = new Part("", "");
        ServicePart servicePart = new ServicePart(part, 1);
        _servicePartList.add(servicePart);

        Node entry = createPartEntry(servicePart);
        _partListVBox.getChildren().add(entry);
    }

    /**
     * @param e
     */
    @FXML
    private void onRemovePart(ActionEvent e) {
        if (_servicePartList.isEmpty())
            return;

        _servicePartList.removeLast();
        _partListVBox.getChildren().removeLast();
    }
}
