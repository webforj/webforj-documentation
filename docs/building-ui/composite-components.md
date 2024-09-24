---
sidebar_position: 2
title: Composite Components
draft: false
---

import ComponentDemo from '@site/src/components/DocsTools/ComponentDemo';
import JavadocLink from '@site/src/components/DocsTools/JavadocLink';

<JavadocLink type="foundation" location="com/webforj/component/Composite" top='true'/>

# Composite Components in webforJ

Developers will often wish to create components that contain constituent components for application level use. The `Composite` component gives developers the tools they need to create their own components while maintaining control over what they choose to expose to users. 

It allows developers to manage a specific type of `Component` instance, providing a way to encapsulate its behavior. It requires any extending subclass to specify the type of `Component` it intends to manage, ensuring a subclass of `Composite` is intrinsically linked to its underlying `Component`.

:::tip
It's highly recommended to create custom components by utilizing the `Composite` component, rather than extending the base `Component` component.
:::

To utilize the `Composite` component, start by creating a new Java class that extends the `Composite` component. Specify the type of Component you want to manage as the generic type parameter.

```java
public class ApplicationComponent extends Composite<Div> {
	//Implementation
}
```

## Component Binding

The `Composite` class requires developers to specify the type of `Component` it manages. This strong association ensures that a `Composite` component is intrinsically linked to its underlying Component. This also provides benefits over traditional inheritance, as it allows the developer to decide exactly what functionality to expose to the public API. 

By default, the `Composite` component utilizes the generic type parameter of its subclass to identify and instantiate the bound component type. This is based on the assumption that the component class has a parameter-less constructor. Developers can customize the component initialization process by overriding the `initBoundComponent()` method. This allows for greater flexibility in creating and managing the bound component, including invoking parameterized constructors.

The following snippet overrides the initBoundComponent method to use a parameterized constructor for the [FlexLayout](../components/flex_layouts.md) class:

```java
public static class OverrideComposite extends Composite<FlexLayout> {
	
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

## Lifecycle Management

Unlike with the `Component`, developers do not need to implement the `onCreate()` and `onDestroy()` methods when working with the `Composite` component. The `Composite` component takes care of these aspects for you.

Should you need to access the bound components at the various stages of its lifecycle, the `onDidCreate()` and `onDidDestroy()` hooks allow developers access to these lifecycle stages to perform additional functionality. Utilization of these hooks is optional.

The `onDidCreate()` method is called immediately after the bound component is created and added to a window. Use this method to set up your component, modify any configurations needed, and add child components if applicable. While the `Component` class's `onCreate()` method takes a [Window](#) instance, the `onDidCreate()` method instead takes the bound component, removing the need to call the `getBoundComponent()` method directly. For example:

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

:::tip
This logic can also be implemented in the constructor, by calling `getBoundComponent()`.
:::

Similarly, the `onDidDestroy()` method fires once the bound component has been destroyed, and allows for additional behavior to be fired on destruction should it be desired.

### Example `Composite` Component

In the demo below, a simple ToDo application has been created, where each item added to the list is a `Composite` component, consisting of a [`RadioButton`](../components/radio-button.md) styled as a switch, and a [`Div`](#) with text.

The logic for this component is set up in the constructor, which sets styling and adds constituent components to the bound component using the `getBoundComponent` method, and adds event logic.

:::tip
This could also be implemented in the `onDidCreate()` method, which would give direct access to the bound [`FlexLayout`](../components/flex_layouts.md) component.
:::

This component is then instantiated and utilized in an Application, and allows for its use throughout various locations, making it a powerful tool in the creation of custom components.
 
<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples?class=demos.CompositeDemo' 
cssURL='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/resources/css/compositestyles/styles.css'
javaE='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/demos/CompositeDemo.java'
height='500px'
/>
