# The Composite Pattern

For creating reusable business logic components with fluent interfaces:

**Notice**: This example shows the recommended approach - no lifecycle methods needed! Everything is configured in the constructor.

```java
public class UserForm extends Composite<FlexLayout> {
    // No lifecycle methods required - everything works in constructor!
    private TextField nameField;
    private TextField emailField;
    private Button saveButton;

    // Multiple constructors for flexibility
    public UserForm() {
        this(null, null);
    }
    
    public UserForm(String defaultName) {
        this(defaultName, null);
    }
    
    public UserForm(String defaultName, String defaultEmail) {
        initializeComponents(defaultName, defaultEmail);
        setupLayout();
        setupEventHandlers();
    }

    private void initializeComponents(String defaultName, String defaultEmail) {
        nameField = new TextField("Name")
            .setValue(defaultName != null ? defaultName : "")
            .setPlaceholder("Enter your name")
            .setRequired(true);
            
        emailField = new TextField("Email")
            .setValue(defaultEmail != null ? defaultEmail : "")
            .setPlaceholder("Enter your email")
            .setPattern("[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,}$")
            .setRequired(true);
            
        saveButton = new Button("Save", ButtonTheme.PRIMARY);
        saveButton.setExpanse(ButtonExpanse.XLARGE);
            
    }

    private void setupLayout() {
        // getBoundComponent() works perfectly in constructor - no lifecycle hooks needed!
        FlexLayout layout = getBoundComponent();
        layout.setDirection(FlexDirection.COLUMN)
              .setSpacing("var(--dwc-space-m)")
              .setPadding("var(--dwc-space-l)")
              .add(nameField, emailField, saveButton);
    }

    private void setupEventHandlers() {
        // Multiple event handling patterns
        saveButton.onClick(this::handleSave);
    }

    private void handleSave(ButtonClickEvent event) {
        // Business logic with loading state
        saveButton.setEnabled(false)
                 .setText("Saving...");
        
        try {
            // Perform save operation
            UserData userData = new UserData(
                nameField.getValue(),
                emailField.getValue()
            );
            
            saveUser(userData);
            
            Toast.show("User saved successfully!", Theme.SUCCESS);
                      
            // Fire custom event
            dispatchEvent(new UserSavedEvent(this, userData));
            
        } catch (Exception e) {
            Toast.show("Failed to save: " + e.getMessage(), Theme.DANGER);
        } finally {
            saveButton.setEnabled(true)
                     .setText("Save");
        }
    }
    
    // Fluent API methods
    public UserForm setUserName(String name) {
        nameField.setValue(name);
        return this;
    }
    
    public UserForm setUserEmail(String email) {
        emailField.setValue(email);
        return this;
    }
    
    public String getUserName() {
        return nameField.getValue();
    }
    
    public String getUserEmail() {
        return emailField.getValue();
    }
    
    // Custom event
    public static class UserSavedEvent extends ComponentEvent<UserForm> {
        private final UserData userData;
        
        public UserSavedEvent(UserForm source, UserData userData) {
            super(source);
            this.userData = userData;
        }
        
        public UserData getUserData() {
            return userData;
        }
    }
    
    public ListenerRegistration<UserSavedEvent> onUserSaved(EventListener<UserSavedEvent> listener) {
        return addEventListener(UserSavedEvent.class, listener);
    }
    
    private void saveUser(UserData userData) {
        // Actual save implementation
    }
    
    private static class UserData {
        private final String name;
        private final String email;
        
        public UserData(String name, String email) {
            this.name = name;
            this.email = email;
        }
        
        // Getters...
    }
}
```

## Advanced Composite Patterns

### Theme-aware Composites

```java
public class ThemedPanel extends Composite<FlexLayout> {
    public enum PanelTheme implements ThemeBase {
        @SerializedName("default")
        DEFAULT,
        
        @SerializedName("primary")
        PRIMARY,
        
        @SerializedName("success")
        SUCCESS,
        
        @SerializedName("warning")
        WARNING,
        
        @SerializedName("danger")
        DANGER;
    }
    
    private PanelTheme theme = PanelTheme.DEFAULT;
    
    public ThemedPanel() {
        super();
        applyTheme();
    }
    
    public ThemedPanel(PanelTheme theme) {
        this();
        setTheme(theme);
    }
    
    public ThemedPanel setTheme(PanelTheme theme) {
        // Remove old theme class
        removeClassName("theme-" + this.theme.toString().toLowerCase());
        
        this.theme = theme;
        applyTheme();
        return this;
    }
    
    private void applyTheme() {
        addClassName("themed-panel");
        addClassName("theme-" + theme.toString().toLowerCase());
    }
}
```

### Slot-based Composites

```java
public class SlottedLayout extends Composite<FlexLayout> {
    private FlexLayout header;
    private FlexLayout content;
    private FlexLayout footer;
    
    public SlottedLayout() {
        super();
        initializeSlots();
    }
    
    private void initializeSlots() {
        header = new FlexLayout()
            .setDirection(FlexDirection.ROW)
            .setJustifyContent(FlexJustifyContent.SPACE_BETWEEN)
            .addClassName("slot-header");
            
        content = new FlexLayout()
            .setDirection(FlexDirection.COLUMN)
            .addClassName("slot-content");
            
        footer = new FlexLayout()
            .setDirection(FlexDirection.ROW)
            .setJustifyContent(FlexJustifyContent.END)
            .addClassName("slot-footer");
            
        getBoundComponent()
            .setDirection(FlexDirection.COLUMN)
            .add(header, content, footer);
    }
    
    public SlottedLayout addToHeader(Component... components) {
        header.add(components);
        return this;
    }
    
    public SlottedLayout addToContent(Component... components) {
        content.add(components);
        return this;
    }
    
    public SlottedLayout addToFooter(Component... components) {
        footer.add(components);
        return this;
    }
    
    public SlottedLayout clearHeader() {
        header.removeAll();
        return this;
    }
    
    public SlottedLayout clearContent() {
        content.removeAll();
        return this;
    }
    
    public SlottedLayout clearFooter() {
        footer.removeAll();
        return this;
    }
}
```

---

## Best Practices

1. **Constructor-First**: Do everything possible in the constructor
2. **Fluent API**: Return `this` from setter methods for chaining
3. **Clear Naming**: Use descriptive method names that match business domain
4. **Event Handling**: Provide custom events for important state changes
5. **Encapsulation**: Hide internal components, expose only business methods

## Common Use Cases

- **Forms**: Data entry with validation
- **Dashboards**: Complex layouts with multiple sections
- **Wizards**: Multi-step processes
- **Data Views**: Lists, grids, and detail views
- **Dialogs**: Custom modal interactions

## Navigation

- [← Previous: Component Foundation](02-component-foundation.md)
- [Next: ElementComposite →](04-element-composite.md)
- [Back to Index](00-index.md)