---
sidebar_position: 2
title: Composite Components
sidebar_class_name: updated-content
---

<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/Composite" top='true'/>

The `Composite` component in webforJ lets you create custom, self-contained components by wrapping internal components into a single reusable unit. You can encapsulate logic, layout, and styling while controlling exactly what methods and data is exposed.

Use a `Composite` when you want to:
	-	Reuse component patterns throughout your application
	-	Encapsulate layout and behavior into a single, maintainable unit
	-	Compose multiple components without exposing implementation details

It's highly recommended to create custom components by utilizing the `Composite` component, rather than extending the base `Component` component.

## Defining a `Composite`

To define a `Composite` component, extend the `Composite` class and specify the type of component it manages. This becomes your bound component, which is the root container that holds your internal structure. The bound component can be any [webforJ component](../components/overview) or [HTML element component](/docs/building-ui/web-components/html-elements).

```java
public class ApplicationComponent extends Composite<Div> {
	//Implementation
}
```

## Constructor setup (Recommended)

The recommended approach is to configure everything in the constructor. Use `getBoundComponent()` to build your internal layout and logic directly.

<ComponentDemo 
path='/webforj/composite?' 
cssURL='https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/resources/css/composite.css'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/composite/CompositeView.java'
height='400px'
/>

`getBoundComponent()` works perfectly in the constructor - no lifecycle methods needed for most use cases.

## Component binding {#component-binding}

The `Composite` class enforces a strong link between the custom component and its underlying component. By default, it instantiates the bound component using its no-argument constructor. If you need more control, you can override this behavior.

To create the bound component manually with custom parameters or default children, override the `initBoundComponent()` method.

```java
public class OverrideComposite extends Composite<FlexLayout> {
    TextField nameField;
    Button submit;

    @Override
    protected FlexLayout initBoundComponent() {
        nameField = new TextField();
        submit = new Button("Submit");
        return new FlexLayout(nameField, submit);
    }
}
```

This override lets you pass parameters to the constructor, add default children, or set up custom layout behavior.

## Fluent API design

A fluent API lets you chain method calls together for cleaner, more readable code. Setter methods return `this` instead of `void`, so you can link multiple method calls in a single statement.

### Creating fluent methods

To make your `Composite` components fluent, follow this pattern:

```java
public class UserForm extends Composite<FlexLayout> {
    private TextField nameField;
    private TextField emailField;
    
    // ... constructor and setup code ...

    // Fluent setter methods - return 'this' for chaining
    public UserForm setUserName(String name) {
        nameField.setValue(name);
        return this; // ← This enables chaining
    }
    
    public UserForm setUserEmail(String email) {
        emailField.setValue(email);
        return this; // ← This too
    }
    
    public UserForm setRequired(boolean required) {
        nameField.setRequired(required);
        emailField.setRequired(required);
        return this; // ← And this
    }
}
```

### Using fluent APIs

With fluent methods, you can configure components like this:

```java
// Instead of this verbose approach:
UserForm form = new UserForm();
form.setUserName("John Doe");
form.setUserEmail("john@example.com");
form.setRequired(true);

// You can write this concise, chainable version:
UserForm form = new UserForm()
    .setUserName("John Doe")
    .setUserEmail("john@example.com")
    .setRequired(true);
```

The demo below shows a working example of a `Composite` component with fluent API methods. Notice how the form is configured using method chaining when it's instantiated.

<ComponentDemo 
path='/webforj/compositefluentapi?' 
cssURL='https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/resources/css/composite.css'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/CompositeFluentAPIView.java'
height='350px'
/>

## Custom events

Create domain-specific events for important state changes in your `Composite` components. Custom events allow your components to communicate important actions or data changes to parent components or listeners.

Custom events extend `ComponentEvent` and can carry specific data relevant to your business logic:

<ComponentDemo 
path='/webforj/compositecustomevent?' 
cssURL='https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/resources/css/composite.css'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/CompositeCustomEventView.java'
height='350px'
/>

## Lifecycle management {#lifecycle-management}

Unlike the `Component` class, the `Composite` component manages its own lifecycle. You don’t need to implement `onCreate()` or `onDestroy()`. Instead, webforJ offers two lifecycle methods you can optionally override:

- `onDidCreate(T component)` – Called immediately after the bound component is instantiated and added to the view.
- `onDidDestroy(T component)` – Called after the component is removed and cleaned up.

```java
public class ApplicationComponent extends Composite<Div> {
	@Override
	protected void onDidCreate(Div container) {
		// Add child components to the container
		container.add(new CheckBox());
		container.add(new Paragraph());
		// ...
	}
}
```

:::tip Using `getBoundComponent()`
You can use `getBoundComponent()` inside the constructor for setup, which is the preferred pattern. Your component will be fully configured and ready to use immediately when created, without needing lifecycle methods or additional setup calls.
:::

Similarly, the `onDidDestroy()` method fires once the bound component has been destroyed, and allows for additional behavior to be fired on destruction should it be desired.

## Component grouping

Sometimes, you may want to use a `Composite` not for reuse, but simply to group related components together into a presentable unit. The following analytics card brings together a title, value, progress indicator, and chart into one visually consistent UI.

Use this when you want to organize components together without worrying about reusing them elsewhere.

<ComponentDemo
path='/webforj/analyticscardcomposite?'
cssURL='https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/resources/css/analyticscomposite.css'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/AnalyticsCardCompositeView.java'
height='550px'
/>

## Advanced patterns

### Theme-aware composites

Create components that can switch between different visual themes. For example, a panel that can be styled as primary, secondary, success, or danger depending on its purpose.

```java
public class ThemedPanel extends Composite<FlexLayout> {
    public enum PanelTheme {
        DEFAULT,
        PRIMARY,
        SUCCESS
    }
    
    private PanelTheme theme = PanelTheme.DEFAULT;
    
    public ThemedPanel() {
        // Apply initial theme
        applyTheme();
    }
    
    public ThemedPanel setTheme(PanelTheme theme) {
        // Remove old theme CSS class
        getBoundComponent().removeClassName("theme-" + this.theme.toString().toLowerCase());
        
        this.theme = theme;
        
        // Add new theme CSS class
        getBoundComponent().addClassName("theme-" + theme.toString().toLowerCase());
        return this;
    }
    
    private void applyTheme() {
        getBoundComponent().addClassName("themed-panel");
        getBoundComponent().addClassName("theme-" + theme.toString().toLowerCase());
    }
}
```

:::note Styling Integration
Theme-aware `Composites` work well with webforJ's built-in styling system. See the [Styling overview](../styling/overview) and [Colors](../styling/colors) documentation to learn how to create themes that match webforJ components and automatically support dark mode.
:::

### Slot-based layouts

Create flexible layout components with predefined areas (slots) where other components can be placed. This pattern is common in app shells, card layouts, and dashboard widgets.

```java
public class SlottedLayout extends Composite<FlexLayout> {
    private FlexLayout header;
    private FlexLayout content;
    private FlexLayout footer;
    
    public SlottedLayout() {
        initializeSlots();
    }
    
    private void initializeSlots() {
        header = new FlexLayout().addClassName("slot-header");
        content = new FlexLayout().addClassName("slot-content");
        footer = new FlexLayout().addClassName("slot-footer");
            
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
}
```

Once defined, you can use the slot methods to organize content into specific areas:

```java
SlottedLayout layout = new SlottedLayout();
layout.addToHeader(new H1("Dedicated place for a header"));
layout.addToFooter(new Button("This button will always be at the bottom"));
```
