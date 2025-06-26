# Component Lifecycle Safety

## Q: How do I prevent "destroyed object" exceptions during navigation?

**A:** Add safety checks in navigation event handlers to prevent accessing destroyed components:

```java
// PROBLEM: This causes WebforjRuntimeException during logout
@Route
public class MainLayout extends Composite<AppLayout> {
    private H1 title = new H1();
    
    public MainLayout() {
        Router.getCurrent().onNavigate(this::onNavigate);
    }
    
    // DANGEROUS - Can cause destroyed object exceptions
    private void onNavigate(NavigateEvent ev) {
        Set<Component> components = ev.getContext().getAllComponents();
        Component view = components.stream()
            .filter(c -> c.getClass().getSimpleName().endsWith("View"))
            .findFirst()
            .orElse(null);

        if (view != null) {
            FrameTitle frameTitle = view.getClass().getAnnotation(FrameTitle.class);
            title.setText(frameTitle != null ? frameTitle.value() : ""); // EXCEPTION HERE!
        }
    }
}
```

**SOLUTION: Add component lifecycle safety checks:**

```java
// SAFE - Prevents destroyed object exceptions
private void onNavigate(NavigateEvent ev) {
    // Safety check: don't update UI if component is destroyed
    if (title == null || !title.isAttached()) {
        return;
    }
    
    Set<Component> components = ev.getContext().getAllComponents();
    Component view = components.stream()
        .filter(c -> c.getClass().getSimpleName().endsWith("View"))
        .findFirst()
        .orElse(null);

    if (view != null) {
        FrameTitle frameTitle = view.getClass().getAnnotation(FrameTitle.class);
        try {
            title.setText(frameTitle != null ? frameTitle.value() : "");
        } catch (Exception e) {
            // Ignore exceptions during component destruction
        }
    }
}
```

## Q: When do "destroyed object" exceptions occur in webforJ?

**A:** These exceptions typically happen during navigation when components are being destroyed:

**Common Scenarios:**
1. **Logout Navigation**: User clicks logout → components destroyed during navigation
2. **Route Changes**: Navigation between views destroys old components
3. **Window Closure**: Browser tab/window closed while operations pending
4. **Component Removal**: Parent component removed while child still has references

**Exception Details:**
```
com.webforj.exceptions.WebforjRuntimeException
com.basis.startup.type.BBjException: Attempting to call a method on a destroyed object
    at com.webforj.component.DwcComponent.doSetText(DwcComponent.java:307)
    at com.webforj.component.DwcComponent.setText(DwcComponent.java:351)
```

## Q: How do I implement safe component cleanup in Composite components?

**A:** Use the optional `onDidDestroy()` lifecycle method for cleanup:

```java
public class SafeDataComponent extends Composite<FlexLayout> {
    private Interval refreshInterval;
    private final List<ListenerRegistration> listeners = new ArrayList<>();
    private TextField statusField;
    
    public SafeDataComponent() {
        statusField = new TextField("Status");
        setupComponents();
    }
    
    @Override
    protected void onDidCreate(FlexLayout layout) {
        // Setup that requires component to be attached
        startPeriodicRefresh();
        registerEventListeners();
    }
    
    @Override
    protected void onDidDestroy() {
        // CRITICAL: Clean up resources to prevent memory leaks
        
        // Stop intervals
        if (refreshInterval != null) {
            refreshInterval.stop();
            refreshInterval = null;
        }
        
        // Remove event listeners
        listeners.forEach(ListenerRegistration::remove);
        listeners.clear();
        
        // Clear any pending operations
        cancelPendingOperations();
    }
    
    private void startPeriodicRefresh() {
        refreshInterval = new Interval(5f, e -> {
            // Safety check before updating UI
            if (statusField != null && statusField.isAttached()) {
                updateStatus();
            }
        });
        refreshInterval.start();
    }
    
    private void updateStatus() {
        try {
            // Safe UI update with exception handling
            statusField.setText("Updated at " + LocalDateTime.now());
        } catch (Exception e) {
            // Component may be destroyed - ignore safely
            System.out.println("Component destroyed during update: " + e.getMessage());
        }
    }
    
    private void registerEventListeners() {
        // Store listener registrations for cleanup
        ListenerRegistration reg = statusField.onFocus(event -> {
            // Event handling with safety checks
            if (statusField.isAttached()) {
                handleFocus();
            }
        });
        listeners.add(reg);
    }
    
    private void cancelPendingOperations() {
        // Cancel any pending async operations
        // Clear caches, close connections, etc.
    }
}
```

## Q: How do I handle navigation events safely in Layout components?

**A:** Implement defensive programming patterns in navigation handlers:

```java
@Route
public class SafeMainLayout extends Composite<AppLayout> implements WillEnterObserver {
    private H1 title = new H1();
    private Toolbar toolbar = new Toolbar();
    private boolean isDestroyed = false;
    
    public SafeMainLayout() {
        configureComponents();
        Router.getCurrent().onNavigate(this::onNavigateWithSafety);
    }
    
    @Override
    protected void onDidDestroy() {
        isDestroyed = true;
        super.onDidDestroy();
    }
    
    @Override
    public void onWillEnter(WillEnterEvent event, ParametersBag parameters) {
        // Quick exit if component is being destroyed
        if (isDestroyed) {
            return;
        }
        
        // Rest of route guard logic...
        AuthService authService = new AuthService();
        if (!authService.isAuthenticated() && !event.getLocation().equals("/login")) {
            event.veto(true);
            event.getRouter().navigate(LoginView.class);
        }
    }
    
    private void onNavigateWithSafety(NavigateEvent ev) {
        // Multiple safety checks
        if (isDestroyed || title == null || !title.isAttached()) {
            return;
        }
        
        try {
            updateTitleSafely(ev);
        } catch (Exception e) {
            // Log but don't crash
            System.err.println("Navigation update failed (component likely destroyed): " + e.getMessage());
        }
    }
    
    private void updateTitleSafely(NavigateEvent ev) {
        Set<Component> components = ev.getContext().getAllComponents();
        Component view = components.stream()
            .filter(c -> c.getClass().getSimpleName().endsWith("View"))
            .findFirst()
            .orElse(null);

        if (view != null && title.isAttached()) {
            FrameTitle frameTitle = view.getClass().getAnnotation(FrameTitle.class);
            
            // Double-check before UI update
            if (!isDestroyed && title.isAttached()) {
                title.setText(frameTitle != null ? frameTitle.value() : "");
            }
        }
    }
}
```

## Q: How do I implement safe periodic updates with component lifecycle?

**A:** Use lifecycle-aware intervals with proper cleanup:

```java
public class PeriodicUpdateComponent extends Composite<FlexLayout> {
    private Interval updateInterval;
    private TextField dataField;
    private Button startButton;
    private Button stopButton;
    private boolean isRunning = false;
    
    public PeriodicUpdateComponent() {
        setupComponents();
        setupLayout();
    }
    
    private void setupComponents() {
        dataField = new TextField("Live Data")
            .setReadOnly(true)
            .setValue("No data");
            
        startButton = new Button("Start Updates", ButtonTheme.SUCCESS);
        stopButton = new Button("Stop Updates", ButtonTheme.DANGER)
            .setEnabled(false);
            
        startButton.onClick(e -> startPeriodicUpdates());
        stopButton.onClick(e -> stopPeriodicUpdates());
    }
    
    private void setupLayout() {
        FlexLayout controls = FlexLayout.create(startButton, stopButton)
            .horizontal()
            .spacing("var(--dwc-space-s)")
            .build();
            
        getBoundComponent()
            .setDirection(FlexDirection.COLUMN)
            .setSpacing("var(--dwc-space-m)")
            .add(dataField, controls);
    }
    
    @Override
    protected void onDidCreate(FlexLayout layout) {
        // Component is now attached and ready for updates
        super.onDidCreate(layout);
    }
    
    @Override
    protected void onDidDestroy() {
        // CRITICAL: Stop all periodic operations
        stopPeriodicUpdates();
        super.onDidDestroy();
    }
    
    private void startPeriodicUpdates() {
        if (isRunning || updateInterval != null) {
            return;
        }
        
        isRunning = true;
        startButton.setEnabled(false);
        stopButton.setEnabled(true);
        
        updateInterval = new Interval(1f, e -> performSafeUpdate());
        updateInterval.start();
    }
    
    private void stopPeriodicUpdates() {
        if (updateInterval != null) {
            updateInterval.stop();
            updateInterval = null;
        }
        
        isRunning = false;
        
        // Safely update UI if component still exists
        if (startButton != null && startButton.isAttached()) {
            startButton.setEnabled(true);
            stopButton.setEnabled(false);
        }
    }
    
    private void performSafeUpdate() {
        // Multiple safety checks
        if (!isRunning || dataField == null || !dataField.isAttached()) {
            stopPeriodicUpdates();
            return;
        }
        
        try {
            String newData = "Updated: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            dataField.setValue(newData);
        } catch (Exception e) {
            // Component likely destroyed - stop updates
            System.out.println("Update failed, stopping periodic updates: " + e.getMessage());
            stopPeriodicUpdates();
        }
    }
}
```

## Critical Rules for Component Safety

1. **Always check `isAttached()`** before updating component properties
2. **Use try-catch blocks** around UI updates in navigation handlers  
3. **Implement `onDidDestroy()`** for components with resources that need cleanup
4. **Store listener registrations** and remove them in `onDidDestroy()`
5. **Stop intervals and timers** in `onDidDestroy()` to prevent memory leaks
6. **Never access UI components** after navigation has started destroying them
7. **Use defensive programming** in all navigation event handlers
8. **Add safety flags** (`isDestroyed`) for complex components
9. **Handle exceptions gracefully** during component destruction
10. **Test logout scenarios** to ensure components clean up properly

---

## Navigation

- [← Previous: Login Components](04-login-components.md)
- [Next: Component Basics →](../components/01-component-basics.md)
- [Back to Cookbook Index](../00-index.md)