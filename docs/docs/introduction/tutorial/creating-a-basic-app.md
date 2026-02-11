---
title: Creating a Basic App
sidebar_position: 2
description: Step 1 - Learn how to add components to an app.
---

In [Project Setup](/docs/introduction/tutorial/project-setup), you generated a webforJ project. Now it’s time to create the main class for the project and add an interactive interface using webforJ components. In this step, you’ll learn about:

- The entry point for apps using webforJ and Spring Boot
- webforJ and HTML element components
- Using CSS to style components

Completing this step creates a version of [1-creating-a-basic-app](https://github.com/webforj/webforj-demo-application/tree/main/1-creating-a-basic-app).

<!-- Insert video here -->

## The entry point {#entry-point}

Every webforJ app contains a single class that extends <JavadocLink type="foundation" location="com/webforj/App" code='true'>App</JavadocLink>. For this tutorial, and other published webforJ projects, it's commonly called `Application`. This class is inside a package that's named after the `groupId` you used in [Project Setup](/docs/introduction/tutorial/project-setup):

```
1-creating-a-basic-app 
│   .editorconfig
│   .gitignore
│   pom.xml
│   README.md
│
├───.vscode
├───src/main/java
// highlight-next-line
│   └──com/webforj/demos
// highlight-next-line
│       └──Application.java
└───target
```

Inside the `Application` class, the `SpringApplication.run()` method uses the configurations to launch the app. The various annotations are for the app's configurations.

```java title="Application.java"
@SpringBootApplication
@StyleSheet("ws://css/app.css")
@AppTheme("system")
@AppProfile(name = "DemoApplication", shortName = "DemoApplication")
public class Application extends App {
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
```

### Annotations {#main-class-annotations}

The [`@SpringBootApplication`](https://docs.spring.io/spring-boot/api/java/org/springframework/boot/autoconfigure/SpringBootApplication.html) annotation auto-configures parts of your Spring Boot app. This helps Spring determine when you’re using parts of its framework.

`@StyleSheet`, `@AppTheme`, and `@AppProfile` are just a few of the many <JavadocLink type="foundation" location="com/webforj/annotation/package-summary">webforJ annotations</JavadocLink> available when you want to explicitly set configurations or embed JavaScript and CSS resources. `@StyleSheet` will be discussed in [Styling with CSS](#styling-with-css).

## Creating a user interface {#creating-a-ui}

To create your UI, you’ll need to add [HTML element components](/docs/building-ui/web-components/html-elements) and [webforJ components](/docs/components/overview). For now, you only have a single-page app, so you'll add components directly to the `Application` class. 
To do this, override the `App.run()` method and create a `Frame` to add components to. 

```java
@Override
public void run() throws WebforjException {
  Frame mainFrame = new Frame();
  // Create UI components and add to the frame
}
```

### Using HTML elements {#using-html-elements}

You can add standard HTML elements to your app with [HTML element components](/docs/building-ui/web-components/html-elements).
Create a new instance of the component, then use the `add()` method to add it to the `Frame`:

```java
// Create the container for the UI elements
Frame mainFrame = new Frame();
// Create the HTML component
Paragraph demo = new Paragraph("Demo Application!");
// Add the component to the container
mainFrame.add(demo);
```

### Using webforJ components {#webforj-components-and-html-elements}

While HTML elements are suitable for displaying static information, [webforJ components](/docs/components/overview) provide more complex and dynamic behavior.

The code below adds a [Button](/docs/components/button) component, changes its appearance with the `setTheme()` method, and adds an event listener to create a [Message Dialog](/docs/components/option-dialogs/message) component when the button is clicked.
Most webforJ component methods that modify a component return the component itself, so you can chain multiple methods for more compact code.

```java
// Create the container for the UI elements
Frame mainFrame = new Frame();
// Create the webforJ component
Button btn = new Button("Info");
// Modify the webforJ component, and add an event listener
btn.setTheme(ButtonTheme.PRIMARY).addClickListener(e -> OptionDialog.showMessageDialog("This is a demo!", "Info"));
// Add the component to the container
mainFrame.add(btn);
```

## Styling with CSS {#styling-with-css}

Most webforJ components have built-in methods to make common style changes, such as sizing and theming. In addition to these methods, you can style your app using CSS. 
See the [Styling](/docs/styling/overview) documentation for detailed information on how to style webforJ components, and see the **Styling** section of any component's documentation page for specific details about its styling.

### Referencing a CSS file {#refrencing-a-css-file} 

It's best to have a separate CSS file to keep everything organized and maintainable. Create a file named `app.css` inside `src/main/resources/static/css`, with the following CSS class definition:

```css title="app.css"
.frame--border {
  display: inline-grid;
  gap: 20px;
  margin: 20px;
  padding: 20px;
  border: 1px dashed;
  border-radius: 10px;
}
```

Then, reference the file in `Application.java` by using the `@StyleSheet` annotation with the name of the CSS file. For this step, it's `@StyleSheet("ws://css/app.css")`.

:::tip Webserver protocol
This tutorial uses a webserver to reference the CSS file. To learn more about how this works, see [Managing Resources](/docs/managing-resources/overview).
:::

### Adding CSS classes to components {#adding-css-classes-to-components}

You can dynamically add or remove class names to components using the `addClassName()` and `removeClassName()` methods. For this tutorial, there’s only one CSS class used:

```java
mainFrame.addClassName("frame--border");
```

## Completed `Application` {#completed-application}

Your `Application` class should now look similar to the following:

```java title="Application.java"
@SpringBootApplication
@StyleSheet("ws://css/app.css")
@AppTheme("system")
@AppProfile(name = "DemoApplication", shortName = "DemoApplication")
public class Application extends App {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Override
  public void run() throws WebforjException {
    Frame mainFrame = new Frame();
    Paragraph demo = new Paragraph("Demo Application!");
    Button btn = new Button("Info");
    mainFrame.addClassName("frame--border");

    btn.setTheme(ButtonTheme.PRIMARY).addClickListener(e -> OptionDialog.showMessageDialog("This is a demo!", "Info"));
    mainFrame.add(demo, btn);
  }
}
```

:::tip Multiple pages
For a more complex app, you can divide the UI into multiple pages for better organization. This concept is covered later in this tutorial in [Scaling with Routing and Composites](/docs/introduction/tutorial/scaling-with-routing-and-composites).
:::

## Running the app {#running-the-app}

When you’ve finished this step, you can compare it to [1-creating-a-basic-app](https://github.com/webforj/webforj-demo-application/tree/main/1-creating-a-basic-app) on GitHub. To see the app in action:

1. Navigate to the top-level directory containing the `pom.xml` file, this is `1-creating-a-basic-app` if you're following along with the version on GitHub.

2. Use the following Maven command to run the Spring Boot app locally:
    ```bash
    mvn
    ```

3. Open your browser and go to http://localhost:8080 to view the app.

## Next step {#next-step}

After creating a functional app with a basic user interface, the next step is to add a data model and display the results in a `Table` component in [Working with Data](/docs/introduction/tutorial/working-with-data).