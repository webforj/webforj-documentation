# Event Handling

## Q: How do I handle different types of component events properly?

**A:** Use the specific event methods provided by each component:

```java
// Button clicks
Button button = new Button("Save");
button.onClick(event -> {
    double x = event.getX(); // Mouse X position
    double y = event.getY(); // Mouse Y position
    handleSave();
});

// TextField events
TextField textField = new TextField();
textField.onBlur(event -> {
    String text = event.getText();
    Boolean isValid = event.isClientValidationValid();
    validateField(text, isValid);
});

textField.onFocus(event -> {
    String text = event.getText();
    handleFocus(text);
});

// NumberField value changes
NumberField ageField = new NumberField("Age");
ageField.addValueChangeListener(event -> {
    Double value = event.getValue();
    if (value != null && value > 65) {
        applyDiscount();
    }
});

// ChoiceBox selection
ChoiceBox<String> countryBox = new ChoiceBox<>("Country");
countryBox.onSelectionChange(event -> {
    String selected = event.getSelectedItem();
    updateDependentFields(selected);
});
```

## Q: How do I use intervals for periodic tasks and background operations?

**A:** Use the `Interval` class for timed operations with proper cleanup:

```java
import com.webforj.Interval;

public class LiveDataComponent extends Composite<FlexLayout> {
    private Interval refreshInterval;
    private TextField statusField;
    
    public LiveDataComponent() {
        statusField = new TextField("Status").setReadOnly(true);
        setupPeriodicRefresh();
        
        getBoundComponent().add(statusField);
    }
    
    private void setupPeriodicRefresh() {
        // Refresh every 5 seconds
        refreshInterval = new Interval(5f, e -> refreshData());
        refreshInterval.start();
    }
    
    private void refreshData() {
        try {
            String status = dataService.getCurrentStatus();
            statusField.setValue(status);
        } catch (Exception e) {
            statusField.setValue("Error: " + e.getMessage());
        }
    }
    
    // Clean up when component is destroyed
    @Override
    protected void onDidDestroy() {
        if (refreshInterval != null) {
            refreshInterval.stop();
        }
    }
    
    // Control methods
    public void pauseRefresh() {
        if (refreshInterval != null) {
            refreshInterval.stop();
        }
    }
    
    public void resumeRefresh() {
        if (refreshInterval != null) {
            refreshInterval.start();
        }
    }
    
    public void changeRefreshRate(float seconds) {
        if (refreshInterval != null) {
            refreshInterval.setDelay(seconds);
            refreshInterval.start();
        }
    }
}
```

---

## Navigation

- [← Previous: Data Binding](../data-handling/01-data-binding.md)
- [Next: Custom Components →](../advanced/01-custom-components.md)
- [Back to Cookbook Index](../00-index.md)