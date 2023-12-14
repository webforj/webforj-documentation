---
sidebar_position: 2
title: Custom Components
slug: composite
draft: false
---

import ComponentDemo from '@site/src/components/DocsTools/ComponentDemo';


# `Composite` Class in DWCj

Developers will often wish to create components that contain constituent components for application level use. The `Composite` class gives developers the tools they need to create their own components while maintaining control over what they choose to expose to users. 

It allows developers to manage a specific type of `Component` instance, providing a way to encapsulate its behavior. It requires any extending subclass to specify the type of `Component` it intends to manage, ensuring a subclass of `Composite` is intrinsically linked to its underlying `Component`.

:::tip
It's highly recommended to create custom components by utilizing the `Composite` class, rather than extending the base `Component` class.
:::

## Component Binding

The `Composite` class requires developers to specify the type of `Component` it manages. This strong association ensures that a `Composite` component is intrinsically linked to its underlying Component. This also provides benefits over traditional inheritance, as it allows the developer to decide exactly what functionality to expose to the public API. 

By default, the `Composite` class utilizes the generic type parameter of its subclass to identify and instantiate the bound component type. This is based on the assumption that the component class has a parameter-less constructor. Developers can customize the component initialization process by overriding the `initBoundComponent()` method. This allows for greater flexibility in creating and managing the bound component, including invoking parameterized constructors.

## Lifecycle Management

Unlike with the `Component` class, developers do not need to implement the `onCreate()` and `onDestroy()` methods when working with the `Composite` class. The `Composite` class takes care of these aspects for you.

Should you need to access the bound components at the various stages of its lifecycle, the `onDidCreate()` and `onDidDestroy()` methods allow developers access to these lifecycle stages to perform additional functionality.

## Creating Application Level Components

The `Composite` class is particularly useful for creating container-like components - or a reusable component that is comprised of other components. Follow these steps to create a container component:

1. **Create a New Composite Class**: Start by creating a new Java class that extends the `Composite` class. Specify the type of Component you want to manage as the generic type parameter.

    ```java
    public class ApplicationComponent extends Composite<Div> {
    }
    ```

2. **Optionally Implement the `onDidCreate()` Method**: Inside your custom `Composite` class, you can implement the `onDidCreate()` method. This method is called immediately after the bound component is created and added to a window. Use this method to set up your component, modify any configurations needed, and add child components if applicable. For example:

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

3. Create additional methods to implement desired functionality.

4. **Use Your `Composite` Component**: Once you've created your composite component, you can use it just like any other component in your application.

### Example `Composite` Component

<ComponentDemo 
path='https://hot.bbx.kitchen/webapp/controlsamples/CompositeDemo' 
cssURL='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/resources/css/compositestyles/styles.css'
javaE='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/java/demos/CompositeDemo.java'
height='500px'
/>
