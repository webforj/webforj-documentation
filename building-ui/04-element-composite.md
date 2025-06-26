# ElementComposite: The Integration Core

ElementComposite is the foundation for integrating ANY web component into webforJ. It provides a type-safe, Java-friendly wrapper around HTML Custom Elements.

## Basic ElementComposite Structure

```java
@NodeName("my-component")
public final class MyComponent extends ElementComposite
    implements HasClassName<MyComponent>, HasStyle<MyComponent> {

    // Property descriptors
    private final PropertyDescriptor<String> titleProp =
        PropertyDescriptor.property("title", "");
    
    private final PropertyDescriptor<Boolean> enabledProp =
        PropertyDescriptor.property("enabled", true);
    
    private final PropertyDescriptor<Integer> countProp =
        PropertyDescriptor.property("count", 0);

    // Constructors
    public MyComponent() {
        super();
    }

    public MyComponent(String title) {
        this();
        setTitle(title);
    }

    // Property methods with fluent interface
    public String getTitle() {
        return get(titleProp);
    }

    public MyComponent setTitle(String title) {
        set(titleProp, title);
        return this;
    }
    
    public Boolean isEnabled() {
        // Use get(prop, true, Boolean.class) to fetch from client if needed
        return get(enabledProp);
    }
    
    public MyComponent setEnabled(Boolean enabled) {
        set(enabledProp, enabled);
        return this;
    }
    
    public Integer getCount() {
        return get(countProp);
    }
    
    public MyComponent setCount(Integer count) {
        if (count < 0) {
            throw new IllegalArgumentException("Count cannot be negative");
        }
        set(countProp, count);
        return this;
    }
}
```

## ElementCompositeContainer for Components with Slots

```java
@NodeName("my-container")
public final class MyContainer extends ElementCompositeContainer
    implements HasClassName<MyContainer>, HasStyle<MyContainer> {
    
    // Define slot names as constants
    private static final String HEADER_SLOT = "header";
    private static final String CONTENT_SLOT = "content";
    private static final String FOOTER_SLOT = "footer";
    
    public MyContainer() {
        super();
    }
    
    // Slot management methods
    public MyContainer addToHeader(Component... components) {
        getElement().add(HEADER_SLOT, components);
        return this;
    }
    
    public MyContainer addToContent(Component... components) {
        // Default slot (no slot name)
        add(components);
        return this;
    }
    
    public MyContainer addToFooter(Component... components) {
        getElement().add(FOOTER_SLOT, components);
        return this;
    }
}
```

## Key Annotations

### @NodeName

Defines the HTML custom element tag (required):

```java
@NodeName("dwc-my-element") // Creates <dwc-my-element>
```

### Loading External Resources

While the guide mentions @JavaScript and @StyleSheet annotations, these should be used through proper webforJ mechanisms. For loading external resources, consult the webforJ documentation on resource loading patterns.

## Interfaces for Capabilities (Concern Interfaces)

Components gain capabilities through interface implementation:

```java
// CSS class management
implements HasClassName<MyComponent>

// CSS styling
implements HasStyle<MyComponent>

// Visibility control
implements HasVisibility<MyComponent>

// Text content (for components with text)
implements HasText<MyComponent>

// Enable/disable state
implements HasEnablement<MyComponent>
```

These concern interfaces provide default implementations for their methods, making them easy to use without overriding.

## Real Component Example from webforJ

Here's how the actual Toast component is implemented:

```java
@NodeName("dwc-toast")
public class Toast extends ElementCompositeContainer
    implements HasClassName<Toast>, HasStyle<Toast>, HasText<Toast>, HasHtml<Toast> {

    // Enums for property values
    public enum Placement {
        @SerializedName("bottom")
        BOTTOM("bottom"),
        @SerializedName("top")
        TOP("top"),
        @SerializedName("center")
        CENTER("center");
        
        private final String value;
        
        Placement(String value) {
            this.value = value;
        }
        
        public String getValue() {
            return value;
        }
    }

    // Property descriptors using @PropertyMethods annotation
    @PropertyMethods(setter = "setText", getter = "getText")
    private final PropertyDescriptor<String> messageProp = 
        PropertyDescriptor.property("message", "");
    private final PropertyDescriptor<Integer> durationProp =
        PropertyDescriptor.property("duration", 3000);
    @PropertyExclude  // Exclude from PropertyDescriptorTester
    private final PropertyDescriptor<Boolean> openedProp =
        PropertyDescriptor.property("opened", false);
    private final PropertyDescriptor<Placement> placementProp =
        PropertyDescriptor.property("placement", Placement.BOTTOM);

    // Multiple constructors for convenience
    public Toast() {
        this("");
    }
    
    public Toast(String text) {
        this(text, DEFAULT_DURATION);
    }
    
    public Toast(String text, int duration) {
        this(text, duration, DEFAULT_THEME);
    }
    
    public Toast(String text, int duration, Theme theme) {
        setText(text);
        setDuration(duration);
        setTheme(theme);
    }

    // Property methods
    @Override
    public Toast setText(String text) {
        setHtml(sanitizeHtml(text));
        return this;
    }

    @Override
    public String getText() {
        return sanitizeHtml(getHtml());
    }

    @Override
    public Toast setHtml(String html) {
        set(messageProp, html);
        return this;
    }

    @Override
    public String getHtml() {
        return get(messageProp);
    }
    
    public Toast setDuration(int duration) {
        set(durationProp, duration);
        return this;
    }
    
    public int getDuration() {
        return get(durationProp);
    }
    
    public Toast setPlacement(Placement placement) {
        set(placementProp, placement);
        return this;
    }
    
    public Placement getPlacement() {
        return get(placementProp);
    }
}
```

## Element Access

Access the underlying Element through the protected getElement() method:

```java
public class MyComponent extends ElementComposite {
    
    @Override
    protected void onDidCreate() {
        super.onDidCreate();
        
        // Access element after it's created
        Element element = getElement();
        
        // Set attributes
        element.setAttribute("data-custom", "value");
        
        // Execute JavaScript on the element
        element.executeJs("component.initialize();");
    }
    
    @Override
    protected void onDidDestroy() {
        super.onDidDestroy();
        
        // Cleanup when component is destroyed
        getElement().executeJs("component.cleanup();");
    }
    
    // Get the node name defined by @NodeName
    protected String getNodeName() {
        return super.getNodeName();
    }
}
```

---

## Key Takeaways

1. **@NodeName**: Required annotation that defines the custom element tag
2. **PropertyDescriptor**: Type-safe property management
3. **Concern Interfaces**: Add capabilities through interface implementation
4. **ElementCompositeContainer**: Use for components with slots
5. **Element Access**: Use getElement() for direct DOM manipulation

## Navigation

- [← Previous: Composite Pattern](03-composite-pattern.md)
- [Next: Property Management →](05-property-management.md)
- [Back to Index](00-index.md)