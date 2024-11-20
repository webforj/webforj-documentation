---
title: Creating a Basic App
sidebar_position: 2
---

This first step lays the foundation for the customer management app by creating a simple, interactive interface. This demonstrates how to set up a basic webforJ app, with a single button that opens a dialog when clicked. It’s a straightforward implementation that introduces key components and gives you a feel for how webforJ works.

This step leverages the base app class provided by webforJ to define the structure and behavior of the app. Following through to later steps will transition to a more advanced setup using routing to manage multiple screens, introduced in Step 3.

By the end of this step, you’ll have a functioning app that demonstrates basic interaction with components and event handling in webforJ.

<img src={require('@site/static/img/tutorial_images/step1.png').default} alt="Screenshot of first app" className="tutorial-image" />

## Creating a webforJ app

In webforJ, an `App` represents the central hub for defining and managing your project. Every webforJ app starts by creating one class that extends the foundational `App` class, which serves as the core framework to:

- Manage the app lifecycle, including initialization and termination.
- Handle routing and navigation (if enabled).
- Define the app’s theme, locale, and other overall configurations.
- Provide essential utilities for interacting with the environment and components.

### Extending the `App` class

For this step, create a class called DemoApplication.java and extend the `App` class. Do this in the `demos` directory.

```java
public class DemoApplication extends App {

    @Override
    public void run() {
        // Core app logic will go here
    }
}
```

### Configuring your app

Once a foundational `App` class has been created, it's necessary to set this as the entry point for your project. This is done in the `webforj.conf` file in a webforJ project. The `webforj.conf` file is the central configuration file for a webforJ app. It defines key properties that control how the app runs and behaves.

:::tip Learn more
<!-- TODO add link -->
More information on configuring a webforJ project can be found here
:::

For now, focus on the following key configuration properties:

#### App Entry `(webforj.entry)`

This property specifies the main entry point of the app by defining the fully qualified name of the class that extends the `App` class. 

If the `webforj.entry` property isn't set webforJ will automatically scan the classpath for classes extending `App`. If multiple such classes are found, an error occurs. Avoid ambiguity by explicitly setting this property in your configuration.

Set the entry point is set to `com.webforj.demos.DemoApplication`. This ensures the framework knows which class to initialize as the app when it starts.

```hocon
webforj.entry = com.webforj.demos.DemoApplication
```

#### Debug Mode `(webforj.debug)`

Setting `webforj.debug` to true enables debug mode, which provides insights during development:
    - Detailed logs are printed to the console.
    - Exceptions and errors are displayed directly in the browser for easier troubleshooting.

:::info Using debug mode
Debug mode should only be enabled in development environments, as it may expose sensitive information.
:::

Ensure that debug mode is enabled in this project, to ensure that errors are visible while working through the tutorial:

```hocon
webforj.debug = true
```

### Overriding the `run()` method

After ensuring correct configuration for the project, override the `run()` method in your `App` class. 

The run method is the core of your app in webforJ. It defines what happens after the app is initialized and is the main entry point for your app's features. By overriding the `run()` method, you can implement the logic that creates and manages your app's user interface and behavior. 

:::tip Using routing
<!-- TODO link -->
When implementing routing within an app, the `run()` is called after the initial route resolution and `Frame` creation. Otherwise, it's where non-routable app logic can be executed. This tutorial will go further into depth on implementing routing in step 3.
:::

For now, add a logging statement, which can be viewed in the browser console, to the method.

```java
public class DemoApplication extends App {
    @Override
    public void run() throws WebforjException {
        console().log("Demo Application Running");
    }
}
```

### App `Frame`

The `Frame` class in webforJ represents a non-nestable, top-level window in your app. A `Frame` typically acts as the main containers for UI components, making it an essential building block for constructing the user interface. Every app starts with at least one frame, and you can add components such as buttons, dialogs, or forms to these frames.

Create a `Frame` within the `run()` method - later on, components will be added here.

<!-- Any other content here I'm missing? -->

```java
public class DemoApplication extends App {
    @Override
    public void run() throws WebforjException {
        Frame mainFrame = new Frame();
        console().log("Demo Application Running");
    }
}
```

## Adding components 

In webforJ, components are the building blocks of your app’s user interface. These components represent discrete pieces of your app's UI, such as buttons, text fields, dialogs, or tables. 

You can think of a UI as a tree of components, with a Frame serving as the root. Each component added to the frame becomes a branch or leaf in this tree, contributing to the overall structure and behavior of your app.

### Server and client side components

Each of these [server-side component](../../components/overview) created in webforJ has a corresponding [client-side web component](../../client-components/overview). These client-side components are web components, implemented by webforJ's design system called DWC. When viewing information about the various components available in the webforJ documentation, each page will like the relevant information needed to style the client components.

- **Server-Side Logic**: Components like `Button` and `Dialog` define behavior, such as events, and interact with the backend logic.
- **Client-Side Rendering**: Each component has a matching web component on the frontend, like `dwc-button` and `dwc-dialog`. These are implemented using HTML 5 web component standards which ensure maintainability agnostic of a specific web framework.
- **Separation of Concerns**: This separation ensures that your server-side Java logic is easy to maintain, while the client-side remains efficient and lightweight.

:::tip Composite components
<!-- TODO link -->
Alongside the core components provided by webforJ, you can design custom composite components by grouping multiple elements into a single reusable unit. This concept will be covered in this step of the tutorial.
:::

Components need to be added to a container class that implements the `HasComponents` interface. The `Frame` is one such class - for this step, add a `Paragraph` and a `Button` to the `Frame`, which will render in the UI in the browser:

```java
public class DemoApplication extends App {
  Paragraph demo = new Paragraph("Demo Application!");
  Button btn = new Button("Info");

  @Override
  public void run() throws WebforjException {
    Frame mainFrame = new Frame();
    btn.setTheme(ButtonTheme.PRIMARY)
        .addClickListener(e -> showMessageDialog("This is a demo!", "Info"));
    mainFrame.add(demo, btn);
  }
}
```

## Styling with CSS

Styling in webforJ gives you complete flexibility to design your app’s appearance. While the framework supports a cohesive design and style out of the box, it doesn't enforce a specific styling approach, allowing you to apply custom styles that align with your app’s requirements. 

With webforJ, you can dynamically apply class names to components for conditional or interactive styling, use CSS for a consistent and scalable design system, and inject entire inline or external stylesheets.

### Adding CSS classes to components

You can dynamically add or remove class names to components using the `addClassName()` and `removeClassName()` methods. These methods allow you to control the component’s styles based on your app's logic. Add the `mainFrame` class name to the `Frame` created in the previous steps by including the following code in the `run()` method:

```java
mainFrame.addClassName("mainFrame");
```

### Injecting CSS files

To style your app, you can inject CSS files into your project using annotations, along with other methods [outlined here](../../styling/getting-started#using-custom-css). webforJ supports two main annotations for this purpose:

<!-- TODO ask hyyan to review -->
### `@InlineStyleSheet`
Use the `@InlineStyleSheet` annotation to include CSS files located in your project’s context directory. The framework maps the path you specify to the context of your app. When the app runs, the web server serves this file using the `/static` route within the `resources` directory, allowing it to be accessed in the browser.

:::tip Configuring the `/static` directory
Specify the directory from which static files should be served. Use the `<webApp>` in the `pom.xml` configuration to point to your static directory.
:::

The following code would import a stylesheet found in the following directory structure:
```
resources/
  static/
    styles/
      app-styles.css
    images/
```

```java
@InlineStyleSheet("context://styles/styles.css")
public class DemoApplication extends App {
    @Override
    public void run() {
        // App logic here
    }
}
```

### `@StyleSheet`
Use the `@StyleSheet` annotation to include CSS files hosted at external URLs. This is useful for integrating third-party stylesheets. When referencing external URLs, the framework fetches and applies the stylesheet directly from the specified URL. Make sure the resource is accessible and uses the correct protocol (http or https).

```java
@StyleSheet("https://cdn.example.com/styles/library.css")
public class DemoApplication extends App {
    @Override
    public void run() {
        // App logic here
    }
}
```

It's recommended to use this annotation in combination with the `webserver` protocol to inject your custom stylesheets into an app. 

The `webserver` protocol in the context of webforJ refers to the mechanism by which the framework’s built-in or external web server serves static assets and handles requests for app resources like stylesheets, scripts, images, and API endpoints.

### Sample CSS code

Complete the last part of this step by creating a CSS file in your project at `resources > static > css > demoApplication.css` and use the following CSS to get some basic styling applied to the app.

```css
.mainFrame {
    display: inline-grid;
    gap: 20px;
    margin: 20px;
    padding: 20px;
    border: 1px dashed;
    border-radius: 10px;
}
```

Once this is done, the following annotation should be added to your `App` class:

```java
@StyleSheet("ws://css/demoApplication.css")
@AppTitle("Demo Step 1")
public class DemoApplication extends App {
```

The CSS styles are applied to the main `Frame` and provide structure by arranging components with a grid layout, and adding margin, padding, and border styles to make the UI visually organized.