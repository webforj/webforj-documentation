---
sidebar_position: 3
title: Using Components
description: Configure webforJ components in Java by setting text, attributes, IDs, inline styles, and CSS classes that drive appearance and behavior.
sidebar_class_name: new-content
---

<JavadocLink type="foundation" location="com/webforj/component/Component" top='true'/> 

Components are the building blocks of webforJ applications. Whether you're using built-in components like `Button` and `TextField`, or working with custom components provided by your team, the way you interact with them follows the same consistent model: you configure properties, manage state, and compose components into layouts.

This guide focuses on those day-to-day operations: not the internals of how components work, but how to get things done with them in practice.

## Component properties {#component-properties}

Every component exposes properties that control its content, appearance, and behavior. Most of these have dedicated, typed Java methods (`setText()`, `setTheme()`, `setExpanse()`, and so on), which is the primary way you'll configure components in webforJ. The sections below cover the properties and methods that apply broadly across component types.

### Text content {#text-content}

The `setText()` method sets a component's visible text as literal characters, such as the caption on a `Button` or the content of a `Label`. For input components like `TextField`, use `setValue()` instead to set the field's current value.

```java
Button button = new Button();
button.setText("Click Me");

Label label = new Label();
label.setText("Status: ready");

TextField field = new TextField();
field.setValue("Initial value");
```

Markup written with `setText()` appears as those characters and is never run, which keeps text that comes from user input or external data from being interpreted as live markup.

```java
// Shown as the literal characters "<b>Status: ready</b>"
component.setText("<b>Status: ready</b>");
```

:::note Using the `<html>` tag
Earlier versions of webforJ treated a value wrapped in `<html>` and passed to `setText()` as HTML. This behavior is deprecated and will be removed in webforJ 27.00.

The first time an `<html>` wrapped value reaches `setText()`, a warning is logged that names the component and the call site, so the call can be moved to `setHtml()`.

To adopt the webforJ 27.00 default ahead of time, set `webforj.legacyHtmlInText` to `false`. In a Spring app, the same value is set through `webforj.legacy-html-in-text`.

```java
// webforj.legacyHtmlInText = true (default)
component.setText("<html><b>Status: ready</b></html>"); // renders bold

// webforj.legacyHtmlInText = false
component.setText("<html><b>Status: ready</b></html>"); // shows the characters <b>Status: ready</b>
```
:::

### Rendering HTML {#rendering-html}

Some components also support `setHtml()` for cases where you need to render inline HTML markup in the content:

```java
Div container = new Div();
container.setHtml("<strong>Bold text</strong> and <em>italic text</em>");
```

:::danger Cross-site Scripting (XSS)
As a precaution against [cross-site scripting (XSS) attacks](/docs/security/application-security/common-threats#cross-site-scripting-xss), only use `setHtml()` with content you directly control.
:::

### HTML attributes {#html-attributes}

Most configuration in webforJ is done through typed Java methods rather than raw HTML attributes. However, `setAttribute()` is useful for passing accessibility attributes that don't have a dedicated API:

```java
Button button = new Button("Submit");
button.setAttribute("aria-label", "Submit the form");
button.setAttribute("aria-describedby", "form-hint");
```

:::note Check component support
Not all components support arbitrary attributes. This depends on the underlying component implementation.
:::

### Component IDs {#component-ids}

You can assign an ID to a component's HTML element using `setAttribute()`:

```java
Button submitButton = new Button("Submit");
submitButton.setAttribute("id", "submit-btn");

TextField emailField = new TextField("Email");
emailField.setAttribute("id", "email-input");
```

DOM IDs are commonly used for test selectors and CSS targeting in your stylesheets.

:::tip Prefer classes for multi-component targeting
Unlike CSS classes, IDs should be unique within your app. If you need to target multiple components, use `addClassName()` instead.
:::

:::info Framework-managed IDs
webforJ also assigns automatic identifiers to components internally. The server-side ID (accessed via `getComponentId()`) is used for framework tracking, while the client-side ID (accessed via `getClientComponentId()`) is used for client-server communication. These are separate from the DOM `id` attribute you set with `setAttribute()`.
:::

### Styling {#styling}

Three methods cover most styling needs: `setStyle()` for individual CSS property values, and `addClassName()` and `removeClassName()` to apply or remove CSS classes defined in your stylesheets. 
Use `setStyle()` for minor or one-off styling adjustments, and use CSS classes to apply larger or reusable styling.

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

## Component state {#component-state}

Beyond content and appearance, components have state properties that determine whether they're visible and whether they respond to user interaction. The two most commonly used are `setVisible()` and `setEnabled()`.

`setVisible()` controls whether the component is rendered in the UI at all. `setEnabled()` controls whether it accepts input or interaction while remaining visible. In most cases, disabling is preferable to hiding: a disabled button still communicates that an action exists but isn't available yet, which is less disorienting than having it appear and disappear.

```java
// Reveal an additional field when a checkbox is checked
TextField advancedField = new TextField("Advanced setting");
advancedField.setVisible(false);

CheckBox enableAdvanced = new CheckBox("Show advanced settings");
enableAdvanced.addValueChangeListener(e -> advancedField.setVisible(e.getValue()));

// Enable a button only when the required field has a value
Button submitButton = new Button("Submit");
submitButton.setEnabled(false);

TextField nameField = new TextField("Name");
nameField.addValueChangeListener(e -> submitButton.setEnabled(!e.getValue().isBlank()));
```

:::warning Disabled and hidden aren't security
`setVisible(false)` and `setEnabled(false)` affect the UI only. They don't stop a determined user from invoking the underlying action through the browser or a crafted request, so never rely on them to protect sensitive operations. Always enforce access control on the server. See [Disabled and hidden aren't security](/docs/security/application-security/production-hardening#disabled-and-hidden-arent-security) for more details.
:::

The following login form demonstrates `setEnabled()` in practice. The sign-in button stays disabled until both fields have content, making it clear to the user that input is required before proceeding:

<ComponentDemo
path='/webforj/conditionalstate'
files={[
  'src/main/java/com/webforj/samples/views/usingcomponents/ConditionalStateView.java',
  'src/main/frontend/usingcomponents/conditionalstate.css',
]}
height='450px'
/>

## Working with containers {#working-with-containers}

In webforJ, layout is handled by containers, which are components that hold other components and control how they're arranged. You don't position child components manually; instead, you add them to a container and configure that container's layout properties.

### Adding components {#adding-components}

All containers provide an `add()` method. You can pass components one at a time or all at once:

```java
FlexLayout container = new FlexLayout();

container.add(new Button("Click Me"));

TextField nameField = new TextField("Name");
TextField emailField = new TextField("Email");
Button submitButton = new Button("Submit");

container.add(nameField, emailField, submitButton);
```

### Layout options {#layout-options}

`FlexLayout` is the primary layout container in webforJ and covers the majority of use cases: rows, columns, alignment, spacing, and wrapping. For more complex arrangements such as CSS Grid or custom positioning, you can apply CSS directly via `setStyle()` or `addClassName()` on any container component. See the [FlexLayout](/docs/components/flex-layout) documentation for the full range of layout options.

### Showing and hiding sections {#showing-hiding-sections}

A common use of `setVisible()` in containers is revealing additional UI only when it's relevant. This keeps the interface focused and reduces visual clutter. Rather than navigating to a new view, you can show a section of the current layout in direct response to user input.

The following settings panel demonstrates this: basic notification preferences are always visible, and a section of advanced options only appears when the user asks for it. The save button activates as soon as any setting is changed:

<ComponentDemo
path='/webforj/progressivedisclosure'
files={[
  'src/main/java/com/webforj/samples/views/usingcomponents/ProgressiveDisclosureView.java',
  'src/main/frontend/usingcomponents/progressivedisclosure.css',
]}
height='450px'
/>

### Container management {#container-management}

Use `remove()` and `removeAll()` to take components out of a container at runtime:

```java
FlexLayout container = new FlexLayout();
Button tempButton = new Button("Temporary");

container.add(tempButton);
container.remove(tempButton);

container.removeAll();
```

This is useful when you need to replace content entirely, such as swapping a loading indicator for the loaded data.

## Form validation {#form-validation}

Coordinating multiple components to gate a submit action is a common pattern in webforJ UIs. The basic idea is that each input field registers a listener, and whenever a value changes, the form re-evaluates whether all criteria are met and updates the submit button accordingly.
 
The example below wires this up manually so you can see how component state and event listeners work together. It isn't the recommended approach for real forms: manual listener logic becomes hard to maintain as forms grow, and it doesn't connect your components to an underlying data model.
 
:::tip Use data binding for form validation
For production forms, use [data binding](/docs/data-binding/overview). It covers validation, two-way synchronization between components and your model, and value transformation through `BindingContext`. The manual pattern shown here is for illustration only.
:::
 
In this contact form, the name field must not be empty, the email must contain an `@` symbol, and the message must be at least 10 characters long:

<ComponentDemo
path='/webforj/formvalidation'
files={[
  'src/main/java/com/webforj/samples/views/usingcomponents/FormValidationView.java',
  'src/main/frontend/usingcomponents/formvalidation.css',
]}
height='500px'
/>

## Dynamic content updates {#dynamic-content-updates}

Components don't have to stay in a fixed state after they're created. You can update text, swap CSS classes, and toggle enabled state at any point in response to app events. A common example is providing feedback during a long-running task:

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

Disabling the button while the task runs prevents duplicate submissions, and updating the label keeps the user informed about what's happening.

## `ComponentLifecycleObserver` {#componentlifecycleobserver}

The `ComponentLifecycleObserver` interface lets you observe component lifecycle events from outside the component itself. This is useful when you need to react to a component being created or destroyed without modifying its implementation. For example, you might use it to maintain a registry of active components or release external resources when a component is removed.

### Basic usage {#basic-usage}

Call `addLifecycleObserver()` on any component to register a callback. The callback receives the component and the lifecycle event:

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

The DESTROY event is particularly useful for keeping a registry automatically in sync. Rather than manually removing components when they're no longer needed, you let the component notify the registry itself:

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

A coordinator class that manages a set of related components can use the same approach to keep its internal list accurate:

```java
public class FormCoordinator {
  private final List<DwcComponent<?>> managedComponents = new ArrayList<>();

  public void manage(DwcComponent<?> component) {
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

For executing code after a component is attached to the DOM, see `whenAttached()` in the [Composing Components](/docs/building-ui/composing-components) guide.

## User data {#user-data}

Components can carry arbitrary server-side data via `setUserData()` and `getUserData()`. Both methods take a key to identify the data. This is useful when you need to associate domain objects or context with a component without managing a separate lookup structure.

```java
Button button = new Button("Process");
button.setUserData("context", new ProcessingContext(userId, taskId));

button.onClick(event -> {
  ProcessingContext context = (ProcessingContext) button.getUserData("context");
  processTask(context.getUserId(), context.getTaskId());
});
```

Since user data is never sent to the client, you can safely store sensitive information or large objects without affecting network traffic.
