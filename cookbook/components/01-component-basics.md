# Component Basics

## Q: What's the correct way to create custom components in webforJ?

**A:** Always use the `Composite` pattern - never extend `Component` directly:

```java
import com.webforj.component.Composite;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.field.TextField;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;

public class UserForm extends Composite<FlexLayout> {
    private TextField nameField;
    private TextField emailField;
    private Button saveButton;

    public UserForm() {
        initializeComponents();
        setupLayout();
        setupEventHandlers();
    }

    private void initializeComponents() {
        nameField = new TextField("Name")
            .setInvalidMessage("Name is required");
        emailField = new TextField("Email");
        saveButton = new Button("Save", ButtonTheme.PRIMARY);
    }

    private void setupLayout() {
        getBoundComponent()
            .setDirection(FlexDirection.COLUMN)
            .setSpacing("var(--dwc-space-m)")
            .add(nameField, emailField, saveButton);
    }
    
    private void setupEventHandlers() {
        saveButton.onClick(this::handleSave);
    }
    
    private void handleSave(ButtonClickEvent event) {
        // Save logic here
    }
}
```

## Q: How do I handle component lifecycle and cleanup properly?

**A:** Use the optional lifecycle methods `onDidCreate()` and `onDidDestroy()`:

```java
public class LifecycleAwareComponent extends Composite<FlexLayout> {
    private Interval refreshInterval;
    private final List<ListenerRegistration> listeners = new ArrayList<>();
    
    public LifecycleAwareComponent() {
        // Constructor logic - component setup
        initializeComponents();
    }
    
    @Override
    protected void onDidCreate(FlexLayout layout) {
        // Called after component is created and added to DOM
        startPeriodicRefresh();
        registerEventListeners();
    }
    
    @Override
    protected void onDidDestroy() {
        // Clean up resources
        if (refreshInterval != null) {
            refreshInterval.stop();
        }
        
        listeners.forEach(ListenerRegistration::remove);
        listeners.clear();
    }
    
    private void startPeriodicRefresh() {
        refreshInterval = new Interval(5f, e -> refreshData());
        refreshInterval.start();
    }
    
    private void registerEventListeners() {
        listeners.add(someButton.onClick(this::handleClick));
        listeners.add(someField.onBlur(this::handleBlur));
    }
}
```

## Q: How do I make components visible/hidden and enable/disable them?

**A:** Use the `setVisible()` and `setEnabled()` methods available on all components:

```java
Button button = new Button("Toggle me");

// Visibility control
button.setVisible(false); // Hide
button.setVisible(true);  // Show

// Check visibility
if (button.isVisible()) {
    // Component is visible
}

// Enable/disable control
button.setEnabled(false); // Disable
button.setEnabled(true);  // Enable

// Check enabled state
if (button.isEnabled()) {
    // Component is enabled
}

// Conditional enabling based on form validation
nameField.onBlur(e -> {
    boolean isValid = !e.getText().isEmpty();
    saveButton.setEnabled(isValid);
});
```

---

## Navigation

- [← Previous: Basic App](../app-architecture/01-basic-app.md)
- [Next: Form Controls →](02-form-controls.md)
- [Back to Cookbook Index](../00-index.md)