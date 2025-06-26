# Event Handling Architecture

**Required Imports for Event Handling:**
```java
import com.webforj.component.event.BlurEvent;
import com.webforj.component.event.FocusEvent;
import com.webforj.component.button.event.ButtonClickEvent;
import com.webforj.component.element.ElementEventOptions;
import com.webforj.Interval;
import com.webforj.dispatcher.EventListener;
```

webforJ's event system bridges JavaScript events with Java's type safety. Understanding how event payload works is crucial for effective event handling.

## Correct Event Listener Methods

```java
// Button clicks - use onClick method
Button button = new Button("Save");
button.onClick(event -> {
    double x = event.getX(); // Mouse X position
    double y = event.getY(); // Mouse Y position
    handleSave();
});

// TextField blur events - use onBlur method
TextField textField = new TextField();
textField.onBlur(event -> {
    String text = event.getText();
    Boolean isValid = event.isClientValidationValid();
    validateField(text, isValid);
});

// TextField focus events - use onFocus method
textField.onFocus(event -> {
    String text = event.getText();
    handleFocus(text);
});

// TextField value changes - use addEventListener with payload
ElementEventOptions options = new ElementEventOptions();
options.addData("value", "event.target.value");
options.setDebounce(300); // Optional debouncing

textField.addEventListener("input", event -> {
    String value = (String) event.getData().get("value");
    handleValueChange(value);
}, options);
```

## Understanding Event Payload

**IMPORTANT**: Events do **NOT** contain payload data by default. Event payload is only available when explicitly configured through `ElementEventOptions`.

```java
public class EventPayloadDemo extends ElementComposite {
    
    @Override
    protected void onDidCreate() {
        setupBasicEvents();
        setupEventsWithPayload();
    }
    
    /**
     * Basic events without payload - no additional data available
     */
    private void setupBasicEvents() {
        // Basic click event - no payload data
        addEventListener("click", event -> {
            // event.getData() will be empty or contain only default browser data
            System.out.println("Basic click - no custom payload");
        });
    }
    
    /**
     * Events with configured payload using ElementEventOptions
     */
    private void setupEventsWithPayload() {
        // Configure event options to include payload data
        ElementEventOptions options = new ElementEventOptions();
        options.addData("clientX", "event.clientX");
        options.addData("clientY", "event.clientY");
        options.addData("timestamp", "Date.now()");
        options.addData("componentValue", "component.value || ''");
        
        // Now the event will have payload data
        addEventListener("click", event -> {
            Map<String, Object> data = event.getData();
            int x = (Integer) data.get("clientX");
            int y = (Integer) data.get("clientY");
            long timestamp = ((Number) data.get("timestamp")).longValue();
            String value = (String) data.get("componentValue");
            
            System.out.println("Click at (" + x + ", " + y + ") with value: " + value);
        }, options);
    }
}
```

## Built-in Event Classes with Predefined Methods

Some webforJ event classes have built-in methods that provide data without requiring payload configuration:

```java
public class BuiltInEventDemo {
    public void demonstrateBuiltInEvents() {
        Button button = new Button("Click me");
        
        // Button click uses built-in event handling
        button.onClick(event -> {
            double x = event.getX();
            double y = event.getY();
            System.out.println("Button clicked at: " + x + ", " + y);
        });
        
        TextField textField = new TextField();
        textField.onBlur(event -> {
            String text = event.getText();
            System.out.println("Field lost focus with text: " + text);
        });
    }
}
```

## Custom Event Classes with Configured Payload

Define custom events that explicitly configure their payload data:

```java
@EventName("my-custom-event")
@EventOptions(data = {
    @EventOptions.EventData(key = "value", exp = "event.detail.value"),
    @EventOptions.EventData(key = "timestamp", exp = "event.timeStamp"),
    @EventOptions.EventData(key = "elementId", exp = "event.target.id")
})
public static class CustomEvent extends ComponentEvent<MyComponent> {
    public CustomEvent(MyComponent component, Map<String, Object> eventData) {
        super(component, eventData);
    }

    public String getValue() {
        return (String) getData().get("value");
    }

    public Long getTimestamp() {
        return ((Number) getData().get("timestamp")).longValue();
    }
    
    public String getElementId() {
        return (String) getData().get("elementId");
    }
}
```

## Event Handler Registration Patterns

```java
public class EventfulComponent extends ElementComposite {
    
    @Override
    protected void onDidCreate() {
        setupEventHandlers();
    }
    
    private void setupEventHandlers() {
        // Basic event handler (no payload)
        addEventListener("input", this::handleInput);
        
        // Event handler with configured payload
        ElementEventOptions inputOptions = new ElementEventOptions();
        inputOptions.addData("value", "event.target.value");
        inputOptions.addData("valid", "event.target.validity.valid");
        
        addEventListener("input", this::handleInputWithPayload, inputOptions);
    }
    
    private void handleInput(ComponentEvent event) {
        // No payload data available
        System.out.println("Input changed - no additional data");
    }
    
    private void handleInputWithPayload(ComponentEvent event) {
        // Payload data available as configured
        String value = (String) event.getData().get("value");
        Boolean valid = (Boolean) event.getData().get("valid");
        System.out.println("Input: " + value + ", Valid: " + valid);
    }
    
    // Convenience methods for event registration
    public ListenerRegistration<CustomEvent> onCustomEvent(EventListener<CustomEvent> listener) {
        return addEventListener(CustomEvent.class, listener);
    }
}
```

## Comprehensive ElementEventOptions Configuration

`ElementEventOptions` is a powerful tool that provides complete control over event behavior. Here's the full API coverage:

```java
public class ComprehensiveElementEventOptions extends ElementComposite {
    
    /**
     * Complete ElementEventOptions API demonstration
     */
    public void demonstrateAllEventOptions() {
        ElementEventOptions options = new ElementEventOptions();
        
        // 1. EVENT DATA CONFIGURATION
        // Add custom data to event payload
        options.addData("value", "event.target.value");
        options.addData("length", "event.target.value.length");
        options.addData("timestamp", "Date.now()");
        options.addData("elementId", "event.target.id");
        options.addData("coordinates", "{x: event.clientX, y: event.clientY}");
        
        // 2. DEBOUNCING CONFIGURATION
        // Basic debounce - delay execution by 300ms
        options.setDebounce(300);
        
        // 3. THROTTLING CONFIGURATION  
        // Limit event frequency to once per 100ms
        options.setThrottle(100);
        
        // 4. EVENT FILTERING
        // Only fire event if condition is met
        options.setFilter("event.target.value.length > 2");
        
        // 5. PRE-EXECUTION JAVASCRIPT
        // Execute JavaScript before event is fired
        options.setCode("""
            event.target.classList.add('processing');
            console.log('Event being processed for:', event.target.id);
            """);
        
        addEventListener("input", event -> {
            Map<String, Object> data = event.getData();
            String value = (String) data.get("value");
            Integer length = (Integer) data.get("length");
            Long timestamp = ((Number) data.get("timestamp")).longValue();
            
            System.out.println("Processed input: " + value + " (length: " + length + ") at " + timestamp);
        }, options);
    }
}
```

## Debouncing Deep Dive

Debouncing delays event execution until after the specified time has passed since the last trigger:

```java
public class DebouncingExamples extends ElementComposite {
    
    /**
     * Search input with debouncing
     */
    public void setupSearchWithDebouncing() {
        ElementEventOptions searchOptions = new ElementEventOptions();
        
        // Delay search by 500ms after user stops typing
        searchOptions.setDebounce(500);
        searchOptions.addData("query", "event.target.value");
        searchOptions.addData("timestamp", "Date.now()");
        
        // Only search if query is not empty
        searchOptions.setFilter("event.target.value.trim().length > 0");
        
        addEventListener("input", event -> {
            String query = (String) event.getData().get("query");
            Long timestamp = ((Number) event.getData().get("timestamp")).longValue();
            
            System.out.println("Searching for: " + query + " at " + timestamp);
            performSearch(query);
        }, searchOptions);
    }
    
    /**
     * Form validation with debouncing
     */
    public void setupValidationWithDebouncing() {
        ElementEventOptions validationOptions = new ElementEventOptions();
        
        // Validate 200ms after user stops typing
        validationOptions.setDebounce(200);
        validationOptions.addData("value", "event.target.value");
        validationOptions.addData("fieldName", "event.target.name");
        validationOptions.addData("isValid", "event.target.validity.valid");
        
        addEventListener("input", event -> {
            String value = (String) event.getData().get("value");
            String fieldName = (String) event.getData().get("fieldName");
            Boolean isValid = (Boolean) event.getData().get("isValid");
            
            validateField(fieldName, value, isValid);
        }, validationOptions);
    }
    
    /**
     * Auto-save with debouncing
     */
    public void setupAutoSaveWithDebouncing() {
        ElementEventOptions autoSaveOptions = new ElementEventOptions();
        
        // Auto-save 1 second after user stops typing
        autoSaveOptions.setDebounce(1000);
        autoSaveOptions.addData("content", "event.target.value");
        autoSaveOptions.addData("fieldId", "event.target.id");
        
        // Show saving indicator immediately (no debounce)
        addEventListener("input", event -> showSavingIndicator());
        
        // Save after debounce period
        addEventListener("input", event -> {
            String content = (String) event.getData().get("content");
            String fieldId = (String) event.getData().get("fieldId");
            
            autoSave(fieldId, content);
            hideSavingIndicator();
        }, autoSaveOptions);
    }
}
```

## Throttling Deep Dive

Throttling limits the frequency of event execution to a maximum rate:

```java
public class ThrottlingExamples extends ElementComposite {
    
    /**
     * Mouse movement tracking with throttling
     */
    public void setupMouseTrackingWithThrottling() {
        ElementEventOptions mouseOptions = new ElementEventOptions();
        
        // Limit mouse events to once per 50ms
        mouseOptions.setThrottle(50);
        mouseOptions.addData("x", "event.clientX");
        mouseOptions.addData("y", "event.clientY");
        mouseOptions.addData("timestamp", "Date.now()");
        
        addEventListener("mousemove", event -> {
            Integer x = (Integer) event.getData().get("x");
            Integer y = (Integer) event.getData().get("y");
            Long timestamp = ((Number) event.getData().get("timestamp")).longValue();
            
            updateMousePosition(x, y, timestamp);
        }, mouseOptions);
    }
    
    /**
     * Scroll position tracking with throttling
     */
    public void setupScrollTrackingWithThrottling() {
        ElementEventOptions scrollOptions = new ElementEventOptions();
        
        // Limit scroll events to once per 100ms
        scrollOptions.setThrottle(100);
        scrollOptions.addData("scrollTop", "event.target.scrollTop");
        scrollOptions.addData("scrollHeight", "event.target.scrollHeight");
        scrollOptions.addData("clientHeight", "event.target.clientHeight");
        
        addEventListener("scroll", event -> {
            Integer scrollTop = (Integer) event.getData().get("scrollTop");
            Integer scrollHeight = (Integer) event.getData().get("scrollHeight");
            Integer clientHeight = (Integer) event.getData().get("clientHeight");
            
            updateScrollIndicator(scrollTop, scrollHeight, clientHeight);
        }, scrollOptions);
    }
    
    /**
     * Resize handler with throttling
     */
    public void setupResizeHandlerWithThrottling() {
        ElementEventOptions resizeOptions = new ElementEventOptions();
        
        // Limit resize events to once per 200ms
        resizeOptions.setThrottle(200);
        resizeOptions.addData("width", "window.innerWidth");
        resizeOptions.addData("height", "window.innerHeight");
        resizeOptions.addData("orientation", "window.orientation || 0");
        
        // Note: For window events, you might need to attach to the window object
        getElement().executeJs("""
            window.addEventListener('resize', (event) => {
                const customEvent = new CustomEvent('window-resize', {
                    detail: {
                        width: window.innerWidth,
                        height: window.innerHeight,
                        orientation: window.orientation || 0
                    }
                });
                component.dispatchEvent(customEvent);
            });
        """);
        
        addEventListener("window-resize", event -> {
            Integer width = (Integer) event.getData().get("width");
            Integer height = (Integer) event.getData().get("height");
            Integer orientation = (Integer) event.getData().get("orientation");
            
            handleResize(width, height, orientation);
        }, resizeOptions);
    }
}
```

## Event Filtering and Pre-execution

Use filtering to conditionally fire events and pre-execution code for setup:

```java
public class FilteringAndPreExecutionExamples extends ElementComposite {
    
    /**
     * Conditional event firing based on input value
     */
    public void setupConditionalEvents() {
        // Only fire event for valid email addresses
        ElementEventOptions emailOptions = new ElementEventOptions();
        emailOptions.addData("email", "event.target.value");
        emailOptions.setFilter("event.target.value.match(/^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$/)");
        
        addEventListener("change", event -> {
            String email = (String) event.getData().get("email");
            handleValidEmail(email);
        }, emailOptions);
        
        // Only fire for numeric input
        ElementEventOptions numericOptions = new ElementEventOptions();
        numericOptions.addData("number", "parseInt(event.target.value)");
        numericOptions.setFilter("!isNaN(parseInt(event.target.value))");
        
        addEventListener("input", event -> {
            Integer number = (Integer) event.getData().get("number");
            handleNumericInput(number);
        }, numericOptions);
    }
    
    /**
     * Pre-execution code for event setup
     */
    public void setupPreExecutionCode() {
        ElementEventOptions options = new ElementEventOptions();
        
        // Execute setup code before event fires
        options.setCode("""
            // Add processing class
            event.target.classList.add('processing');
            
            // Store original value
            event.target.dataset.originalValue = event.target.dataset.originalValue || event.target.value;
            
            // Log event
            console.log('Processing event for:', event.target.id, 'at', new Date());
            
            // Set custom property
            event.customTimestamp = Date.now();
        """);
        
        options.addData("value", "event.target.value");
        options.addData("originalValue", "event.target.dataset.originalValue");
        options.addData("customTimestamp", "event.customTimestamp");
        
        addEventListener("change", event -> {
            String value = (String) event.getData().get("value");
            String originalValue = (String) event.getData().get("originalValue");
            Long timestamp = ((Number) event.getData().get("customTimestamp")).longValue();
            
            handleChange(value, originalValue, timestamp);
            
            // Clean up
            getElement().executeJs("event.target.classList.remove('processing');");
        }, options);
    }
}
```

## ElementEventOptions Merging and Advanced Patterns

```java
public class AdvancedEventPatterns extends ElementComposite {
    
    /**
     * Merging multiple event configurations
     */
    public void setupMergedEventOptions() {
        // Base options for all input events
        ElementEventOptions baseOptions = new ElementEventOptions();
        baseOptions.addData("fieldId", "event.target.id");
        baseOptions.addData("fieldName", "event.target.name");
        baseOptions.addData("timestamp", "Date.now()");
        
        // Specific options for validation
        ElementEventOptions validationOptions = new ElementEventOptions();
        validationOptions.setDebounce(300);
        validationOptions.addData("value", "event.target.value");
        validationOptions.addData("isValid", "event.target.validity.valid");
        validationOptions.setFilter("event.target.value.length > 0");
        
        // Merge options (note: in practice, you'd need to implement merging logic)
        ElementEventOptions mergedOptions = mergeOptions(baseOptions, validationOptions);
        
        addEventListener("input", event -> handleValidation(event), mergedOptions);
    }
    
    /**
     * Dynamic event configuration based on component state
     */
    public void setupDynamicEventConfiguration() {
        // Configure events differently based on mode
        boolean isEditMode = true;
        
        ElementEventOptions options = new ElementEventOptions();
        options.addData("value", "event.target.value");
        
        if (isEditMode) {
            options.setDebounce(500);  // Longer debounce in edit mode
            options.addData("isDirty", "true");
            options.setCode("event.target.classList.add('edited');");
        } else {
            options.setFilter("false");  // Disable events in read-only mode
        }
        
        addEventListener("input", event -> handleInput(event), options);
    }
}
```

## Performance Considerations and Best Practices

### Event Payload Performance Best Practices

```java
public class EventPerformanceBestPractices {
    
    // AVOID - querying component state in event handlers
    private void inefficientEventHandler(ComponentEvent event) {
        MyComponent comp = (MyComponent) event.getComponent();
        String value = comp.getValue();    // Unnecessary round-trip to client!
        boolean enabled = comp.isEnabled(); // Another round-trip!
    }
    
    // GOOD - use built-in event methods when available
    private void efficientBuiltInEventHandler(BlurEvent event) {
        String text = event.getText();    // Built-in method, no round-trip
        Boolean valid = event.isClientValidationValid(); // Built-in method
    }
    
    // GOOD - configure payload for custom data needs
    private void setupEfficientCustomEvent() {
        ElementEventOptions options = new ElementEventOptions();
        options.addData("value", "component.value");
        options.addData("enabled", "!component.disabled");
        options.addData("customState", "component.customProperty");
        
        addEventListener("my-event", event -> {
            // All data from payload - no round-trips
            String value = (String) event.getData().get("value");
            Boolean enabled = (Boolean) event.getData().get("enabled");
            Object customState = event.getData().get("customState");
        }, options);
    }
    
    // GOOD - maintain server-side state when possible
    private String currentValue;
    private boolean isEnabled = true;
    
    private void maintainServerState() {
        ElementEventOptions options = new ElementEventOptions();
        options.addData("value", "event.target.value");
        
        addEventListener("input", event -> {
            currentValue = (String) event.getData().get("value");
            // Use cached server-side state instead of querying
            if (isEnabled) {
                processValue(currentValue);
            }
        }, options);
    }
}
```

## Key Takeaways

1. **Events have no payload by default** - Always configure ElementEventOptions for data
2. **Use built-in event methods** - Button.onClick(), TextField.onBlur() when available
3. **Configure payload explicitly** - Use addData() to specify what data you need
4. **Debounce for delayed actions** - Search, validation, auto-save
5. **Throttle for rate limiting** - Mouse movement, scrolling, resizing
6. **Filter for conditional events** - Only fire when conditions are met
7. **Pre-execution for setup** - Run JavaScript before event processing

## Navigation

- [← Previous: Property Management](05-property-management.md)
- [Next: Third-Party Events →](07-third-party-events.md)
- [Back to Index](00-index.md)