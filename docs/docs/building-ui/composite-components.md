---
sidebar_position: 2
title: Composite Components
draft: false
---

<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/Composite" top='true'/>

The `Composite` component in webforJ lets developers create custom, self-contained components by wrapping internal components into a single reusable unit. This approach provides flexibility to encapsulate logic, layout, and styling while maintaining precise control over the component's public interface.

Use a `Composite` when you want to:
	-	Reuse component patterns throughout your application
	-	Encapsulate layout and behavior into a single, maintainable unit
	-	Compose multiple components without exposing implementation details

:::tip
It's highly recommended to create custom components by utilizing the `Composite` component, rather than extending the base `Component` component.
:::

## Defining a `Composite`

To define a `Composite` component, extend the `Composite` class and specify the type of component it manages. This becomes your bound component, which is the root container that holds your internal structure.

```java
public class ApplicationComponent extends Composite<Div> {
	//Implementation
}
```

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
Alternatively, you can use `getBoundComponent()` inside the constructor for setup, which is a common pattern.
:::

Similarly, the `onDidDestroy()` method fires once the bound component has been destroyed, and allows for additional behavior to be fired on destruction should it be desired.

### Example `Composite` component {#example-composite-component}

### Example 1: Analytics Card — Grouped UI

Sometimes, you may want to use a `Composite` not for reuse, but simply to group related components together into a presentable unit. The following analytics card brings together a title, value, progress indicator, and chart into one visually consistent UI.

This is useful when you want layout encapsulation, but don’t necessarily need the component to be reused in other contexts.

<ComponentDemo 
path='/webforj/analyticscardcomposite?' 
cssURL='https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/resources/css/composite.css'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/CompositeView.java'
height='550px'
/>

### Example 2: To-do List — Reusable Custom Component

The true power of `Composite` shines when building reusable UI components. In this example, a single to-do item is modeled as a `Composite<FlexLayout>`. It includes a toggle and label, and can be reused in any task list, dashboard, or form.

<ComponentDemo 
path='/webforj/composite?' 
cssURL='https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/resources/css/composite.css'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/CompositeView.java'
height='550px'
/>

This shows how `Composite` can be used to group multiple webforJ components into one encapsulated unit, making it easier to reuse and maintain across larger applications.
