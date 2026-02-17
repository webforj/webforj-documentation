---
sidebar_position: 3
title: Using Components
sidebar_class_name: new-content
---

<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/Component" top='true'/> 

Components are the building blocks of webforJ applications. This guide covers the operations you'll use day-to-day when working with components, whether you're using built-in components like `Button` and `TextField`, or custom components created by others.

## Component properties {#component-properties}

Components have properties that control their content, appearance, and behavior.

### Text content {#text-content}

Set text content with `setText()`:

```java
Button button = new Button();
button.setText("Click Me");

TextField field = new TextField();
field.setText("Initial value");
```

For components that support HTML, use `setHtml()`:

```java
Div container = new Div();
container.setHtml("<strong>Bold text</strong> and <em>italic text</em>");
```

:::warning
`setHtml()` and `setText()` replace any existing content in the component. Be careful when working with components that have child components.
:::

### HTML attributes {#html-attributes}

Set HTML attributes with `setAttribute()`:

```java
Button button = new Button("Submit");
button.setAttribute("data-action", "submit-form");
button.setAttribute("aria-label", "Submit the form");
```

You can add data attributes for JavaScript integration, ARIA attributes for accessibility, or component-specific configuration.

### Component IDs {#component-ids}

Every component can have an ID that identifies it in the HTML DOM. Set it with `setId()`:

```java
Button submitButton = new Button("Submit");
submitButton.setId("submit-btn");

TextField emailField = new TextField("Email");
emailField.setId("email-input");
```

IDs are commonly used for test selectors and CSS targeting in your stylesheets.

:::tip
Unlike CSS classes, IDs should be unique within your application. If you need to target multiple components, use `addClassName()` instead.
:::

### Styling {#styling}

webforJ gives you multiple ways to style components. Use `setStyle()` for quick adjustments or dynamic values, and CSS classes for reusable styles:

```java
Div container = new Div();
container.setStyle("padding", "20px");

if (isHighPriority) {
    container.setStyle("border-left", "4px solid red");
}

Button button = new Button("Toggle");
button.addClassName("primary", "large");

if (isLoading) {
    button.addClassName("loading");
}
```

:::tip
For component-level styling, consider using [`@InlineStyleSheet`](/docs/managing-resources/importing-assets#injecting-css) to keep CSS with your component code.
:::

## Component state {#component-state}

Components have built-in state properties that control their visibility and interactivity:

```java
TextField optionalField = new TextField("Optional field");
Button saveButton = new Button("Save");

optionalField.setVisible(false);
saveButton.setEnabled(false);

CheckBox showOptional = new CheckBox("Show optional field");
showOptional.addValueChangeListener(event -> {
    optionalField.setVisible(event.getValue());
});

TextField nameField = new TextField("Name");
nameField.addValueChangeListener(event -> {
    boolean hasContent = !event.getValue().isEmpty();
    saveButton.setEnabled(hasContent);
});
```

### Conditional state patterns {#conditional-state-patterns}

You can combine these state methods to create dynamic UI behavior:

<ComponentDemo 
path='/webforj/conditionalstate?' 
cssURL='https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/resources/static/usingcomponents/conditionalstate.css'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/usingcomponents/ConditionalStateView.java'
height='400px'
/>

This login form shows the submit button only when both fields have content.

## Working with containers {#working-with-containers}

Components are added to containers to create structured layouts.

### Adding components {#adding-components}

Most containers provide an `add()` method. You can add components one at a time or all at once:

```java
FlexLayout container = new FlexLayout();

container.add(new Button("Click Me"));

TextField nameField = new TextField("Name");
TextField emailField = new TextField("Email");
Button submitButton = new Button("Submit");

container.add(nameField, emailField, submitButton);
```

### Organizing layouts {#organizing-layouts}

webforJ provides layout containers like `FlexLayout` for organizing components. See the [FlexLayout](../components/flex-layout) documentation for layout patterns and responsive design.

### Container management {#container-management}

Remove components from containers when you no longer need them:

```java
FlexLayout container = new FlexLayout();
Button tempButton = new Button("Temporary");

container.add(tempButton);
container.remove(tempButton);

container.removeAll();
```

## Common patterns {#common-patterns}

### Form validation {#form-validation}

You can coordinate multiple components to validate forms before submission. In this contact form, the name field must not be empty, the email must contain an `@` symbol, and the message must be at least 10 characters long. The submit button only enables when all fields meet these criteria:

<ComponentDemo 
path='/webforj/formvalidation?' 
cssURL='https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/resources/static/usingcomponents/formvalidation.css'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/usingcomponents/FormValidationView.java'
height='500px'
/>

### Progressive disclosure {#progressive-disclosure}

Show additional fields based on user input to keep your interface simple:

<ComponentDemo 
path='/webforj/progressivedisclosure?' 
cssURL='https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/resources/static/usingcomponents/progressivedisclosure.css'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/usingcomponents/ProgressiveDisclosureView.java'
height='450px'
/>

This settings panel reveals advanced options only when needed. The "Show advanced settings" button progressively discloses more controls, and the save button tracks when changes have been made.

### Dynamic content updates {#dynamic-content-updates}

Update component content and state based on application events:

```java
Label statusLabel = new Label("Ready");
Button startButton = new Button("Start Process");

startButton.onClick(event -> {
    startButton.setEnabled(false);
    statusLabel.setText("Processing...");
    statusLabel.addClassName("processing");
    
    performTask(() -> {
        statusLabel.setText("Complete");
        statusLabel.removeClassName("processing");
        statusLabel.addClassName("success");
        startButton.setEnabled(true);
    });
});
```

## `ComponentLifecycleObserver` {#componentlifecycleobserver}

The `ComponentLifecycleObserver` interface lets you observe component lifecycle events from outside the component. You can track component instances, coordinate multiple components, or clean up external resources.

### Basic usage {#basic-usage}

Add an observer to watch for lifecycle events:

```java
Button button = new Button("Watch Me");

button.addLifecycleObserver((component, event) -> {
    switch (event) {
        case CREATE:
            System.out.println("Button was created");
            break;
        case DESTROY:
            System.out.println("Button was destroyed");
            break;
    }
});
```

### Pattern: Resource registry {#pattern-resource-registry}

Here's how you might track active components automatically:

```java
public class ResourceRegistry {
    private final Map<String, Component> activeComponents = new ConcurrentHashMap<>();
    
    public void track(Component component, String name) {
        activeComponents.put(name, component);
        
        component.addLifecycleObserver((comp, event) -> {
            if (event == ComponentLifecycleObserver.LifecycleEvent.DESTROY) {
                activeComponents.remove(name);
            }
        });
    }
}
```

### Pattern: Component coordination {#pattern-component-coordination}

You can manage related components together:

```java
public class FormCoordinator {
    private final List<Component> managedComponents = new ArrayList<>();
    
    public void manage(Component component) {
        managedComponents.add(component);
        
        component.addLifecycleObserver((comp, event) -> {
            if (event == ComponentLifecycleObserver.LifecycleEvent.DESTROY) {
                managedComponents.remove(comp);
            }
        });
    }
    
    public void disableAll() {
        managedComponents.forEach(c -> c.setEnabled(false));
    }
}
```

### When to use {#when-to-use}

Use `ComponentLifecycleObserver` for:
- Building component registries
- Implementing logging or monitoring
- Coordinating multiple components
- Cleaning up external resources

For executing code after a component is attached to the DOM, see `whenAttached()` in the [Composite Pattern](/docs/building-ui/composite-components) guide.

## User data {#user-data}

You can attach server-side data to components using `setUserData()` and retrieve it with `getUserData()`. This data stays on the server and is never sent to the client:

```java
Button button = new Button("Process");
button.setUserData(new ProcessingContext(userId, taskId));

button.onClick(event -> {
    ProcessingContext context = (ProcessingContext) button.getUserData();
    processTask(context.getUserId(), context.getTaskId());
});
```

Since the data isn't sent to the client, you can store sensitive information or large objects without affecting network traffic.