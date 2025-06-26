# Complete Implementation Examples & Best Practices

## Example 1: Simple Web Component Integration

```java
/**
 * Integration of a simple rating component.
 */
@JavaScript(
    value = "https://cdn.jsdelivr.net/npm/simple-rating@1.0.0/rating.js",
    attributes = {@Attribute(name = "type", value = "module")}
)
@NodeName("simple-rating")
public final class Rating extends ElementComposite
    implements HasComponents, HasHtml<Rating>, HasStyle<Rating> {

    // Properties
    private final PropertyDescriptor<Integer> valueProp =
        PropertyDescriptor.property("value", 0);
    private final PropertyDescriptor<Integer> maxProp =
        PropertyDescriptor.property("max", 5);
    private final PropertyDescriptor<Boolean> readonlyProp =
        PropertyDescriptor.property("readonly", false);

    // Events
    @EventName("rating-changed")
    @EventOptions(data = {
        @EventOptions.EventData(key = "value", exp = "event.detail.value"),
        @EventOptions.EventData(key = "oldValue", exp = "event.detail.oldValue")
    })
    public static class RatingChangedEvent extends ComponentEvent<Rating> {
        public RatingChangedEvent(Rating component, Map<String, Object> eventData) {
            super(component, eventData);
        }

        public Integer getValue() {
            return ((Number) getData().get("value")).intValue();
        }

        public Integer getOldValue() {
            return ((Number) getData().get("oldValue")).intValue();
        }
    }

    // Constructors
    public Rating() {
        super();
    }

    public Rating(int initialValue) {
        this();
        setValue(initialValue);
    }

    // Property methods
    public Integer getValue() {
        return get(valueProp);
    }

    public Rating setValue(Integer value) {
        if (value < 0 || value > getMax()) {
            throw new IllegalArgumentException("Value must be between 0 and " + getMax());
        }
        set(valueProp, value);
        return this;
    }

    public Integer getMax() {
        return get(maxProp);
    }

    public Rating setMax(Integer max) {
        if (max < 1) {
            throw new IllegalArgumentException("Max must be at least 1");
        }
        set(maxProp, max);
        return this;
    }

    public Boolean isReadonly() {
        return get(readonlyProp);
    }

    public Rating setReadonly(Boolean readonly) {
        set(readonlyProp, readonly);
        return this;
    }

    // Event handlers
    public ListenerRegistration<RatingChangedEvent> onRatingChanged(EventListener<RatingChangedEvent> listener) {
        return addEventListener(RatingChangedEvent.class, listener);
    }

    // Convenience methods
    public Rating reset() {
        setValue(0);
        return this;
    }

    public boolean isMaxRating() {
        return getValue().equals(getMax());
    }
}
```

## Example 2: Composite with Business Logic

```java
/**
 * A complete order management form using composition.
 */
public final class OrderManagementPanel extends Composite<FlexLayout> {
    
    private TextField customerNameField;
    private ComboBox<String> productComboBox;
    private NumberField quantityField;
    private TextField notesField;
    private Button submitButton;
    private Button cancelButton;
    
    public OrderManagementPanel() {
        initializeComponents();
        setupLayout();
        setupEventHandlers();
    }
    
    private void initializeComponents() {
        customerNameField = new TextField("Customer Name")
            .setRequired(true)
            .setPlaceholder("Enter customer name");
            
        productComboBox = new ComboBox<String>("Product")
            .setRequired(true)
            .setItems("Product A", "Product B", "Product C");
            
        quantityField = new NumberField("Quantity")
            .setRequired(true)
            .setMin(1)
            .setValue(1);
            
        notesField = new TextField("Notes")
            .setPlaceholder("Optional order notes");
            
        submitButton = new Button("Submit Order", ButtonTheme.PRIMARY);
        cancelButton = new Button("Cancel", ButtonTheme.DEFAULT);
    }
    
    private void setupLayout() {
        FlexLayout layout = getBoundComponent();
        layout.setDirection(FlexDirection.COLUMN)
              .setSpacing("var(--dwc-space-m)")
              .setPadding("var(--dwc-space-l)");
              
        FlexLayout buttonRow = new FlexLayout(submitButton, cancelButton)
            .setDirection(FlexDirection.ROW)
            .setSpacing("var(--dwc-space-s)");
            
        layout.add(
            customerNameField,
            productComboBox, 
            quantityField,
            notesField,
            buttonRow
        );
    }
    
    private void setupEventHandlers() {
        submitButton.onClick(this::handleSubmit);
        cancelButton.onClick(this::handleCancel);
        
        // Form validation using blur events
        EventListener<BlurEvent> validationHandler = event -> {
            boolean isValid = !customerNameField.getValue().isEmpty() && 
                            productComboBox.getValue() != null &&
                            quantityField.getValue() > 0;
            submitButton.setEnabled(isValid);
        };
        
        customerNameField.onBlur(validationHandler);
        quantityField.onBlur(validationHandler);
    }
    
    private void handleSubmit(ButtonClickEvent event) {
        try {
            OrderData order = new OrderData(
                customerNameField.getValue(),
                productComboBox.getValue(),
                quantityField.getValue().intValue(),
                notesField.getValue()
            );
            
            submitOrder(order);
            
            Toast.show("Order submitted successfully!", Theme.SUCCESS);
                      
            clearForm();
            
        } catch (Exception e) {
            Toast.show("Failed to submit order: " + e.getMessage(), Theme.DANGER);
        }
    }
    
    private void handleCancel(ButtonClickEvent event) {
        clearForm();
    }
    
    private void clearForm() {
        customerNameField.setValue("");
        productComboBox.setValue(null);
        quantityField.setValue(1);
        notesField.setValue("");
    }
    
    private void submitOrder(OrderData order) {
        // Business logic for order submission
    }
    
    public static class OrderData {
        private final String customerName;
        private final String product;
        private final int quantity;
        private final String notes;
        
        public OrderData(String customerName, String product, int quantity, String notes) {
            this.customerName = customerName;
            this.product = product;
            this.quantity = quantity;
            this.notes = notes;
        }
        
        // Getters...
    }
}
```

---

## Best Practices Summary

### Component Design

1. **Always Use Composition (Never Inheritance)**

   - Never extend Component or DwcComponent directly
   - Use Composite<T> for business logic components
   - Use ElementComposite for web component integration
   - All built-in components (Button, TextField, etc.) are final

2. **Lifecycle Methods Are Optional Hooks**

   - Most components don't need onDidCreate() or onDidDestroy()
   - Do everything in the constructor when possible
   - Only use lifecycle hooks for cleanup or DOM-dependent operations
   - getBoundComponent() works perfectly in constructors

3. **Follow Single Responsibility Principle**

   - Each component should have one clear purpose
   - Separate business logic from presentation logic
   - Compose multiple simple components into complex ones

4. **Implement Fluent Interfaces**

   - All setters should return `this`
   - Enable method chaining for better usability
   - Provide meaningful method names

5. **Use Type-Safe Properties**
   - Always use PropertyDescriptor for component state
   - Distinguish between properties and attributes
   - Validate input parameters

### Performance

1. **Minimize Client-Server Round Trips**

   - Use event payload data instead of component queries
   - Cache frequently accessed properties
   - Batch multiple property changes

2. **Manage Resources Properly**

   - Only implement lifecycle hooks when you have resources to clean up
   - Clean up intervals, connections, and external resources in onDidDestroy()
   - Event listeners are automatically cleaned up by the framework
   - Use component pooling for expensive components

3. **Optimize Event Handling**
   - Use specific event classes, not generic ones
   - Avoid excessive event handler registration
   - Consider event delegation for large collections

### Testing

1. **Use PropertyDescriptorTester**

   - Automatically validates PropertyDescriptor implementations
   - Catches common property implementation bugs
   - Ensures consistency across components

2. **Integration Testing**
   - Test components in real application context
   - Verify styling and layout
   - Test event handling end-to-end

### Code Organization

1. **Consistent Naming Conventions**

   - Use camelCase for PropertyDescriptor fields
   - Use descriptive method names
   - Follow Java naming conventions

2. **Comprehensive Documentation**

   - Document all public methods with JavaDoc
   - Include usage examples
   - Document event behavior and payload

3. **Error Handling**
   - Validate input parameters
   - Provide meaningful error messages
   - Implement graceful degradation

### Integration

1. **Library-Agnostic Patterns**

   - Use patterns that work with any web component library
   - Don't couple code to specific library features
   - Design for extensibility

2. **CSS Integration**

   - Use CSS custom properties for theming
   - Implement responsive design patterns
   - Support multiple themes

3. **Event System**
   - Create specific event classes for each event type
   - Include relevant data in event payload
   - Use meaningful event names

---

## Final Guidelines

1. **Start Simple** - Begin with basic components and evolve complexity
2. **Test Early** - Use PropertyDescriptorTester from the beginning
3. **Document Everything** - Clear documentation prevents confusion
4. **Performance Matters** - Consider client-server optimization from the start
5. **Stay Consistent** - Follow established patterns throughout your codebase

## Navigation

- [← Previous: Anti-Patterns](12-antipatterns.md)
- [Back to Index](00-index.md)