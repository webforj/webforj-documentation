# Performance Optimization

## Q: How do I manage component memory and prevent leaks?

**A:** Properly clean up listeners and resources:

```java
public class ResourceManagedComponent extends Composite<FlexLayout> {
    private final List<ListenerRegistration> listeners = new ArrayList<>();
    private Interval refreshInterval;
    
    public ResourceManagedComponent() {
        setupComponent();
        registerEventListeners();
    }
    
    private void registerEventListeners() {
        // Store listener registrations for cleanup
        listeners.add(saveButton.onClick(this::handleSave));
        listeners.add(cancelButton.onClick(this::handleCancel));
        listeners.add(nameField.onBlur(this::validateName));
    }
    
    @Override
    protected void onDidCreate(FlexLayout layout) {
        // Start resource-intensive operations only after attachment
        startPeriodicRefresh();
    }
    
    @Override
    protected void onDidDestroy() {
        // Clean up all resources
        listeners.forEach(ListenerRegistration::remove);
        listeners.clear();
        
        if (refreshInterval != null) {
            refreshInterval.stop();
        }
    }
    
    private void startPeriodicRefresh() {
        refreshInterval = new Interval(10f, e -> refreshData());
        refreshInterval.start();
    }
}
```

---

## Navigation

- [← Previous: Testing](02-testing.md)
- [Back to Cookbook Index](../00-index.md)