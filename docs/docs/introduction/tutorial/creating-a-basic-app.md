---
title: Creating a Basic App
sidebar_position: 2
description: Step 1 - Learn how to add components to an app.
---

In [Project Setup](/docs/introduction/tutorial/project-setup), you generated a webforJ project. Now it’s time to create the main class for the project and add an interactive interface using webforJ components. By the end of this step, you’ll learn about:

- The entry point for apps using webforJ and Spring Boot
- webforJ and HTML element components
- Using CSS to style components

<!-- Insert video here -->

## The entry point {#entry-point}

Every webforJ app contains a single class that extends <JavadocLink type="foundation" location="com/webforj/App" code='true'>App</JavadocLink>. For this tutorial, and other published webforJ projects, it's commonly called `Application`. This class is inside a package that's named after the given `groupId`:

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

Inside the `Application` class, the `SpringApplication.run()` method uses the configurations to launch the app. The configurations for the app are derived from the various annotations.

```java
@SpringBootApplication
@StyleSheet("ws://app.css")
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

`@StyleSheet`, `@AppTheme`, and `@AppTheme` are just a few of the many <JavadocLink type="foundation" location="com/webforj/annotation/package-summary">webforJ annotations</JavadocLink> available when you want to explicitly set configurations or embed JavaScript and CSS resources. `@StyleSheet` will be discussed again later in this step in [Styling with CSS](#styling-with-css).

## Adding components

To create your UI, you’ll need to add components. For now, you only have a single-page app, so you'll add components directly to the `Application` class. This is achieved by overriding the `App.run()` method and adding components to a `Frame`.


```java
@Override
public void run() throws WebforjException {
  Frame mainFrame = new Frame();
  Paragraph demo = new Paragraph("Demo Application!");
  Button btn = new Button("Info");
  mainFrame.addClassName("mainFrame");

  btn.setTheme(ButtonTheme.PRIMARY).addClickListener(e -> OptionDialog.showMessageDialog("This is a demo!", "Info"));
  mainFrame.add(demo, btn);
}
```

:::tip Multiple pages
For a more complex app, you’d want to divide the UI into multiple pages for better organization. This concept is taught in a future step in the tutorial, [Scaling with Routing and Composites](/docs/introduction/tutorial/scaling-with-routing-and-composites).
:::

### Using webforJ components and HTML elements {#webforj-components-and-html-elements}

Using [webforJ components](/docs/components/overview) makes your app interactive. This step allows the user to take an action with the [Button](/docs/components/button) component, and display a message using the [Message Dialog](/docs/components/option-dialogs/message) component.

In the next step, [Working with Data](/docs/introduction/tutorial/working-with-data), you’ll learn how to use the powerful [Table](/docs/components/table/overview) component to visualize data.

Additionally, the webforJ framework allows you to easily use standard HTML elements with
[HTML element components](/docs/building-ui/web-components/html-elements).
This allows you to add HTML elements to your app by creating Java objects:

```java
Paragraph demo = new Paragraph("Demo Application!");
```

## Styling with CSS {#styling-with-css}

Using the webforJ framework allows you to add visually appealing components that also come with built-in methods to change styling, like sizing and theming. Additionally, the framework allows you to style using CSS.

### Referencing a CSS file {#refrencing-a-css-file} 

It's best to have a separate CSS file to keep everything organized and maintainable. Create a file named `app.css` inside `src/main/resources/static`:

```css title="app.css"
.mainFrame {
  display: inline-grid;
  gap: 20px;
  margin: 20px;
  padding: 20px;
  border: 1px dashed;
  border-radius: 10px;
}
```

Then, reference the file in `Application.java`. You’ll do that by using the `@StyleSheet` annotation with the name of the CSS file. For this step, it's `@StyleSheet("ws://app.css")`.

:::tip Webserver protocol
This tutorial uses a webserver to reference the CSS file. To learn more about how this works, see the [Managing Resources](/docs/managing-resources/overview) section of the documentation.
:::

### Adding CSS classes to components {#adding-css-classes-to-components}

You can dynamically add or remove class names to components using the `addClassName()` and `removeClassName()` methods. For this tutorial, there’s only one CSS class used:

```java
mainFrame.addClassName("mainFrame");
```

## Running the app {#running-the-app}

When you’ve finished this step, you can compare it to [1-creating-a-basic-app](https://github.com/webforj/webforj-demo-application/tree/main/1-creating-a-basic-app) on GitHub. To see the app in action:

1. Navigate to the top level directory containing the `pom.xml` file, this is `1-creating-a-basic-app` if you're following along with the version on GitHub.

2. Use the following Maven command to run the Spring Boot app locally:
    ```bash
    mvn
    ```

3. Open your browser and go to http://localhost:8080 to view the app.

## Next step {#next-step}

After creating a functional app with a basic user interface, the next step is to add a data model and display the results in a `Table` component in [Working with Data](/docs/introduction/tutorial/working-with-data).