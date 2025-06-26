# Component Foundation Concepts

## Component Hierarchy

webforJ components follow a sophisticated class hierarchy:

```
Component (abstract base)
├─ DwcComponent<T> (provides control interaction and property handling)
│   ├─ DwcFocusableComponent<T> (for focusable components)
│   │   ├─ DwcButton<T>
│   │   │   └─ Button
│   │   ├─ DwcField<T>
│   │   │   ├─ DwcTextField<T>
│   │   │   │   ├─ TextField
│   │   │   │   └─ MaskedTextField
│   │   │   ├─ DateField
│   │   │   └─ NumberField
│   │   └─ ComboBox
│   └─ ... (other built-in components)
├─ Composite<T> (for composition of existing components)
└─ ElementComposite (for web component integration)
    └─ ElementCompositeContainer (for containers with slots)
```

**Classes for Developers (Use These):**
- **Composite<T>**: For composing existing webforJ components into reusable business logic units
- **ElementComposite**: For integrating web components with type-safe property management
- **ElementCompositeContainer**: For web components that contain other components via slots

**Internal Framework Classes (Never Extend Directly):**
- **Component**: Abstract base (framework internal - DO NOT EXTEND)
- **DwcComponent**: Base for built-in BBj-backed components (framework internal - final classes)

> **IMPORTANT**: Never extend Component or DwcComponent directly. All built-in components are final. Always use composition patterns.

## Component Lifecycle

The webforJ framework automatically manages component lifecycle. You don't need to worry about these internal details:

1. **Creation** - Component instantiation (automatic)
2. **Configuration** - Properties set before attachment (automatic)
3. **Attachment** - Added to a window/container (automatic)
4. **Post-Attachment** - Framework replays configurations (automatic)
5. **Runtime** - Component active and interactive
6. **Destruction** - Component destroyed (automatic cleanup)

**For Developers**: The framework handles all lifecycle management. You only need to use the optional hooks (`onDidCreate`, `onDidDestroy`) if you have specific setup or cleanup requirements.

## Developer Lifecycle Patterns

**RULE**: Never extend Component directly. Always use composition patterns.

**IMPORTANT**: Lifecycle methods are OPTIONAL hooks. You don't need to implement them unless you have specific setup or cleanup needs.

```java
// CORRECT: Simple Composite - no lifecycle methods needed
public class SimpleUserForm extends Composite<FlexLayout> {
    private TextField nameField;
    private Button saveButton;
    
    public SimpleUserForm() {
        // Framework handles everything automatically
        nameField = new TextField("Name");
        saveButton = new Button("Save");
        
        getBoundComponent()
            .setDirection(FlexDirection.COLUMN)
            .add(nameField, saveButton);
    }
}

// CORRECT: Composite with OPTIONAL lifecycle hooks
public class AdvancedUserForm extends Composite<FlexLayout> {
    private TextField nameField;
    private Button saveButton;
    private Interval validationInterval;
    
    public AdvancedUserForm() {
        nameField = new TextField("Name");
        saveButton = new Button("Save");
    }
    
    // OPTIONAL: Only override if you need special setup
    @Override
    protected void onDidCreate(FlexLayout layout) {
        // This is an OPTIONAL hook - not required!
        layout.setDirection(FlexDirection.COLUMN)
              .add(nameField, saveButton);
        
        // Setup something that requires the component to be attached
        startValidationInterval();
    }
    
    // OPTIONAL: Only override if you need cleanup
    @Override
    protected void onDidDestroy() {
        // This is an OPTIONAL hook - not required!
        // Only needed if you have resources to clean up
        if (validationInterval != null) {
            validationInterval.stop();
        }
    }
    
    private void startValidationInterval() {
        // Example of setup that needs component to be attached
    }
}

// CORRECT: ElementComposite without lifecycle methods
@NodeName("simple-counter")
public class SimpleCounter extends ElementComposite {
    private final PropertyDescriptor<Integer> countProp =
        PropertyDescriptor.property("count", 0);
    
    public SimpleCounter() {
        // No lifecycle methods needed - framework handles everything
    }
    
    public int getCount() {
        return get(countProp);
    }
    
    public SimpleCounter setCount(int count) {
        set(countProp, count);
        return this;
    }
}

// CORRECT: ElementComposite with OPTIONAL lifecycle hooks
@NodeName("advanced-chart")
public class AdvancedChart extends ElementComposite {
    private final PropertyDescriptor<String> dataProp =
        PropertyDescriptor.property("data", "");
    
    public AdvancedChart() {
        // No lifecycle methods required in constructor
    }
    
    // OPTIONAL: Only override if you need special initialization
    @Override
    protected void onDidCreate() {
        // This is an OPTIONAL hook - not required!
        // Only needed for special web component setup
        getElement().executeJs("component.initializeChart();");
    }
    
    // OPTIONAL: Only override if you need cleanup
    @Override
    protected void onDidDestroy() {
        // This is an OPTIONAL hook - not required!
        // Only needed if web component needs cleanup
        getElement().executeJs("component.destroy();");
    }
    
    public String getData() {
        return get(dataProp);
    }
    
    public AdvancedChart setData(String data) {
        set(dataProp, data);
        return this;
    }
}

// CORRECT: ElementCompositeContainer for containers with slots
@NodeName("dashboard-layout")
public class DashboardLayout extends ElementCompositeContainer {
    
    @Override
    protected void onDidCreate() {
        // Container setup
        getElement().setAttribute("layout-type", "grid");
    }
    
    public DashboardLayout addToSidebar(Component... components) {
        getElement().add("sidebar", components);
        return this;
    }
}
```

```java
// WRONG: Never do this!
public class MyComponent extends Component {
    // This will cause errors and is not supported
    @Override
    protected void onCreate(Window window) {
        // Don't extend Component directly!
    }
}

// WRONG: Never do this!
public class MyButton extends Button {
    // Button is final - cannot be extended
}

// WRONG: Never do this!
public class MyTextField extends DwcComponent<MyTextField> {
    // DwcComponent is for framework internals only, runtime error will be thrown.
}
```

## When Do You Need Lifecycle Methods?

**Short Answer**: Most of the time, you DON'T need them!

**Use lifecycle methods ONLY when you need:**
- Cleanup resources (intervals, connections, etc.)
- Setup that requires component to be attached to DOM
- JavaScript integration that needs DOM presence

**DON'T use lifecycle methods for:**
- Adding child components (use constructor)
- Setting properties (use constructor) 
- Basic layout setup (use constructor)
- Event registration (use constructor)

```java
// MOST COMMON: No lifecycle methods needed
public class TypicalForm extends Composite<FlexLayout> {
    public TypicalForm() {
        // 99% of components can do everything in constructor
        TextField name = new TextField("Name");
        Button save = new Button("Save");
        
        getBoundComponent()
            .setDirection(FlexDirection.COLUMN)
            .add(name, save);
            
        // Event handling works fine in constructor too
        save.onClick(event -> handleSave());
    }
    
    private void handleSave() {
        // Business logic
    }
}

// RARE CASE: Lifecycle method needed for cleanup
public class FormWithInterval extends Composite<FlexLayout> {
    private Interval autoSaveInterval;
    
    public FormWithInterval() {
        // Basic setup in constructor
        getBoundComponent().add(new TextField("Data"));
        
        // Start interval that needs cleanup
        autoSaveInterval = new Interval(5f, e -> autoSave());
        autoSaveInterval.start();
    }
    
    // OPTIONAL: Only needed because we have an interval to clean up
    @Override
    protected void onDidDestroy() {
        if (autoSaveInterval != null) {
            autoSaveInterval.stop(); // Clean up resource
        }
    }
    
    private void autoSave() {
        // Auto-save logic
    }
}
```

## Using whenAttached() for Deferred Operations

```java
// PREFERRED: Using whenAttached() without lifecycle methods
public class DynamicDataGrid extends Composite<FlexLayout> {
    
    public DynamicDataGrid() {
        // Setup in constructor
        getBoundComponent().setDirection(FlexDirection.COLUMN);
        
        // Load data when attached - no lifecycle method needed!
        whenAttached().then(component -> {
            System.out.println("Component is now attached!");
            loadDataFromServer();
        });
    }
    
    private void loadDataFromServer() {
        // Data loading logic that requires component to be attached
    }
}

// ALTERNATIVE: Using optional lifecycle hook (both approaches work)
public class DynamicDataGridWithHook extends Composite<FlexLayout> {
    
    // OPTIONAL: Use lifecycle hook if you prefer this pattern
    @Override
    protected void onDidCreate(FlexLayout layout) {
        layout.setDirection(FlexDirection.COLUMN);
        loadDataFromServer();
    }
    
    private void loadDataFromServer() {
        // This runs when component is attached
    }
}

// PREFERRED: ElementComposite without lifecycle methods
public class InteractiveChart extends ElementComposite {
    
    public InteractiveChart() {
        // Initialize chart when attached - no lifecycle method needed!
        whenAttached().then(component -> {
            getElement().executeJs("chart.initialize();");
        });
    }
}

// ALTERNATIVE: ElementComposite with optional lifecycle hook
public class ChartWithHook extends ElementComposite {
    
    // OPTIONAL: Use lifecycle hook if you prefer this pattern
    @Override
    protected void onDidCreate() {
        getElement().executeJs("chart.initialize();");
    }
}
```

---

## Key Takeaways

1. **Use Composition**: Always use `Composite<T>` or `ElementComposite`, never extend `Component` directly
2. **Lifecycle is Optional**: Most components don't need lifecycle methods
3. **Constructor First**: Do as much as possible in the constructor
4. **whenAttached()**: Use for deferred operations without needing lifecycle methods
5. **Clean Up Resources**: Only use `onDidDestroy()` if you have resources to clean up

## Navigation

- [← Previous: Introduction](01-introduction.md)
- [Next: Composite Pattern →](03-composite-pattern.md)
- [Back to Index](00-index.md)