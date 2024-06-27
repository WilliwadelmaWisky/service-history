package util.fx.scene;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import util.MathUtil;

/**
 * A text field for decimal numbers
 */
public class DecimalField extends TextField {

    private final DoubleProperty _valueProperty;
    private double _minValue, _maxValue;


    /**
     *
     */
    public DecimalField() {
        super();
        _valueProperty = new SimpleDoubleProperty(0);
        _maxValue = Double.MAX_VALUE;
        _minValue = 0;
        setText(String.valueOf(getValue()));

        addEventFilter(KeyEvent.KEY_TYPED, e -> {
            if (!"0123456789.".contains(e.getCharacter()) || (!"-".equals(e.getCharacter()) && _minValue < 0))
                e.consume();
        });

        _valueProperty.addListener((observable, oldValue, newValue) -> {
            if (getText().isEmpty() || getText().endsWith(".") || getText().equals(newValue.toString()))
                return;

            String text = MathUtil.isInteger(newValue.doubleValue())
                    ? String.valueOf((int)newValue.doubleValue())
                    : String.valueOf(newValue.doubleValue());
            setText(text);
        });

        textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty() || (_minValue < 0 && "-".equals(newValue))) {
                setValue(0);
                return;
            }

            final double doubleValue = Double.parseDouble(newValue);
            if (_minValue > doubleValue) {
                setText(String.valueOf(_minValue));
                return;
            }

            if (doubleValue > _maxValue) {
                setText(String.valueOf(_maxValue));
                return;
            }

            setValue(doubleValue);
        });
    }


    public double getValue() { return _valueProperty.getValue(); }
    public double getMinValue() { return _minValue; }
    public double getMaxValue() { return _maxValue; }
    public DoubleProperty valueProperty() { return _valueProperty; }


    public void setValue(double value) { _valueProperty.setValue(Math.clamp(value, _minValue, _maxValue)); }
    public void setMinValue(double minValue) { _minValue = minValue; }
    public void setMaxValue(double maxValue) { _maxValue = maxValue; }
}
