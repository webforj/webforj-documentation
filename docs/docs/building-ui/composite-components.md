---
sidebar_position: 2
title: Composite Components
draft: false
---

<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/Composite" top='true'/>

Developers often need to build custom components that encapsulate multiple UI elements into a single reusable unit. The `Composite` component in webforJ provides a clean and structured way to create such components, giving you full control over what functionality is exposed while encouraging encapsulation and reuse.

A `Composite` manages a single, strongly-typed child component that serves as its internal content. This ensures a clear separation between internal logic and external behavior.

:::tip
It's highly recommended to create custom components by utilizing the `Composite` component, rather than extending the base `Component` component.
:::

To utilize the `Composite` component, start by creating a new Java class that extends the `Composite` component. Specify the type of Component you want to manage as the generic type parameter.

```java
public class ApplicationComponent extends Composite<Div> {
	//Implementation
}
```

## Component binding {#component-binding}

The `Composite` class requires the specification of a managed component type. This provides a tight link between your composite and its underlying component, making the component structure clear and consistent.

By default, the `Composite` uses the default (parameter-less) constructor of the specified component type to create its bound component. You can override this behavior by implementing the `initBoundComponent()` method.

The following snippet overrides the `initBoundComponent()` method to use a parameterized constructor for the [FlexLayout](../components/flex-layout.md) class:

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

## Lifecycle management {#lifecycle-management}

Unlike the `Component` class, the `Composite` component manages its own lifecycle. You don’t need to implement `onCreate()` or `onDestroy()`. Instead, webforJ offers two lifecycle methods you can optionally override:

- `onDidCreate(T component)` – called immediately after the bound component is instantiated and added to the view.
- `onDidDestroy(T component)` – called after the component is removed and cleaned up.

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
Alternatively, you can use `getBoundComponent()` inside the constructor for setup, which is a common pattern.
:::

Similarly, the `onDidDestroy()` method fires once the bound component has been destroyed, and allows for additional behavior to be fired on destruction should it be desired.

### Example `Composite` component {#example-composite-component}

In the following example, a custom analytics card is created using the `Composite` component. This card is designed for use in a business dashboard and showcases how to encapsulate layout, styling, and behavior into a single reusable component.

All logic and component composition are handled inside the constructor using `getBoundComponent()`. This approach works well for components whose layout and content are known at initialization time and don’t require dynamic setup post-render.

:::tip When to Use `onDidCreate()`
You can still choose to use `onDidCreate()` if your logic needs to respond after rendering or if you prefer separating setup from construction.
:::
 
<ComponentDemo 
path='/webforj/composite?' 
cssURL='/css/composite.css'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/CompositeView.java'
height='550px'
/>

This shows how `Composite` can be used to group multiple webforJ components into one encapsulated unit, making it easier to reuse and maintain across larger applications.
