# Advanced Techniques

## Performance Optimization

webforJ applications can be optimized through careful component design and usage patterns.

### Client-Server Optimization

#### Minimize Round Trips

```java
// Avoid multiple client queries
private void inefficientMethod(ComponentEvent event) {
    Component comp = event.getComponent();
    String value = comp.getValue();    // Round-trip
    boolean enabled = comp.isEnabled(); // Round-trip
    int size = comp.getSize();         // Round-trip
}

// Use event payload when possible
private void efficientMethod(MyCustomEvent event) {
    String value = event.getValue();    // From payload
    boolean enabled = event.isEnabled(); // From payload
    int size = event.getSize();         // From payload
}
```

### Lazy Loading

```java
public class LazyComponent extends Composite<FlexLayout> {
    private ExpensiveComponent expensiveChild;
    private boolean childLoaded = false;

    public void showExpensiveFeature() {
        if (!childLoaded) {
            expensiveChild = new ExpensiveComponent();
            getBoundComponent().add(expensiveChild);
            childLoaded = true;
        }
        expensiveChild.setVisible(true);
    }
}
```

### Memory Management

```java
public class ManagedComponent extends ElementComposite {
    private final List<ListenerRegistration<?>> listeners = new ArrayList<>();

    @Override
    protected void onDidCreate() {
        // Register listeners and store registrations
        listeners.add(addEventListener("click", this::handleClick));
        listeners.add(onCustomEvent(this::handleCustom));
    }

    @Override
    protected void onDidDestroy() {
        super.onDidDestroy();

        // Clean up all listeners
        listeners.forEach(ListenerRegistration::remove);
        listeners.clear();
    }

    private void handleClick(ClickEvent event) {
        // Handle click
    }

    private void handleCustom(CustomEvent event) {
        // Handle custom event
    }
}
```

## Advanced Integration Techniques

### Slot Management

Many web components use slots for content projection:

```java
public class SlottedComponent extends ElementCompositeContainer {
    /**
     * Adds content to the header slot.
     */
    public SlottedComponent addToHeader(Component component) {
        add("header", component);
        return this;
    }

    /**
     * Adds content to the footer slot.
     */
    public SlottedComponent addToFooter(Component component) {
        add("footer", component);
        return this;
    }

    /**
     * Adds content to the default slot.
     */
    public SlottedComponent addToContent(Component component) {
        add(component); // Default slot
        return this;
    }
}
```

---

## Best Practices for Advanced Components

1. **Minimize Round Trips** - Use event payload instead of querying component state
2. **Lazy Loading** - Load expensive components only when needed
3. **Memory Management** - Clean up resources in onDidDestroy()
4. **Slot Management** - Proper content projection for container components
5. **Event Optimization** - Use specific event classes with payload data

## Common Optimization Patterns

### Component Pooling

```java
public class ComponentPool<T extends Component> {
    private final Queue<T> pool = new LinkedList<>();
    private final Supplier<T> factory;
    
    public ComponentPool(Supplier<T> factory) {
        this.factory = factory;
    }
    
    public T acquire() {
        T component = pool.poll();
        if (component == null) {
            component = factory.get();
        }
        return component;
    }
    
    public void release(T component) {
        // Reset component state
        component.setVisible(false);
        pool.offer(component);
    }
}
```

### Efficient Event Handling

```java
public class EfficientEventComponent extends ElementComposite {
    
    @Override
    protected void onDidCreate() {
        // Use event delegation for better performance
        ElementEventOptions options = new ElementEventOptions();
        options.addData("target", "event.target.dataset.action");
        options.setFilter("event.target.dataset.action");
        
        addEventListener("click", this::handleDelegatedClick, options);
    }
    
    private void handleDelegatedClick(ComponentEvent event) {
        String action = (String) event.getData().get("target");
        
        switch (action) {
            case "save" -> handleSave();
            case "cancel" -> handleCancel();
            case "delete" -> handleDelete();
        }
    }
}
```

## Navigation

- [← Previous: JavaScript Execution](10-javascript-execution.md)
- [Next: Anti-Patterns →](12-antipatterns.md)
- [Back to Index](00-index.md)