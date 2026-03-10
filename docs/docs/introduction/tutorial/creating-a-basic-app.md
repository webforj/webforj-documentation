---
title: Creating a Basic App
sidebar_position: 2
description: Step 1 - Add components to an app.
---

In [Project Setup](/docs/introduction/tutorial/project-setup), you generated a webforJ project. Now it’s time to create the main class for the project and add an interactive interface using webforJ components. In this step, you’ll learn about:

- The entry point for apps using webforJ and Spring Boot
- webforJ and HTML element components
- Using CSS to style components

Completing this step creates a version of [1-creating-a-basic-app](https://github.com/webforj/webforj-tutorial/tree/main/1-creating-a-basic-app).

<!-- Insert video here -->

## Running the app {#running-the-app}

As you develop your app, you can use [1-creating-a-basic-app](https://github.com/webforj/webforj-tutorial/tree/main/1-creating-a-basic-app) as a comparison. To see the app in action:

1. Navigate to the top-level directory containing the `pom.xml` file, this is `1-creating-a-basic-app` if you're following along with the version on GitHub.

2. Use the following Maven command to run the Spring Boot app locally:
    ```bash
    mvn
    ```

Running the app automatically opens a new browser at http://localhost:8080.

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
│   └──com/webforj/tutorial
// highlight-next-line
│       └──Application.java
└───target
```

Inside the `Application` class, the `SpringApplication.run()` method uses the configurations to launch the app. The various annotations are for the app's configurations.

```java title="Application.java"
@SpringBootApplication
@StyleSheet("ws://css/card.css")
@AppTheme("system")
@AppProfile(name = "Customer Application", shortName = "CustomerApp")
public class Application extends App {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
```

### Annotations {#annotations}

The [`@SpringBootApplication`](https://docs.spring.io/spring-boot/api/java/org/springframework/boot/autoconfigure/SpringBootApplication.html) is a core annotation in Spring Boot. You put this annotation on the main class to mark it as the starting point of your app.

`@StyleSheet`, `@AppTheme`, and `@AppProfile` are just a few of the many <JavadocLink type="foundation" location="com/webforj/annotation/package-summary">webforJ annotations</JavadocLink> available when you want to explicitly set configurations.

- **`@StyleSheet`** embeds a CSS file into the web page. Further details on how to interact with a specific CSS file can be found later in [Styling with CSS](#styling-with-css).

- **`@AppTheme`** manages the app's visual theme. If set to `system`, the app automatically adopts the user's preferred theme: `light`, `dark`, or `dark-pure`. For information on creating custom themes or overriding the default themes, refer to the [Themes](/docs/styling/themes) article.

- **`@AppProfile`** helps configure how the app presents to the user as an [installable app](/docs/configuration/installable-apps). At minimum, this annotation needs a `name` for the app’s full name and a `shortName` for use when space is limited. The `shortName` shouldn't exceed 12 characters.  

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
Paragraph tutorial = new Paragraph("Tutorial Application!");

// Add the component to the container
mainFrame.add(tutorial);
```

### Using webforJ components {#webforj-components-and-html-elements}

While HTML elements are useful for structure, semantics, and lightweight UI needs, [webforJ components](/docs/components/overview) provide more complex and dynamic behavior.

The code below adds a [Button](/docs/components/button) component, changes its appearance with the `setTheme()` method, and adds an event listener to create a [Message Dialog](/docs/components/option-dialogs/message) component when the button is clicked.
Most webforJ component methods that modify a component return the component itself, so you can chain multiple methods for more compact code.

```java
// Create the container for the UI elements
Frame mainFrame = new Frame();

// Create the webforJ component
Button btn = new Button("Info");

// Modify the webforJ component, and add an event listener
btn.setTheme(ButtonTheme.PRIMARY)
  .addClickListener(e -> OptionDialog.showMessageDialog("This is a tutorial!", "Info"));

// Add the component to the container
mainFrame.add(btn);
```

## Styling with CSS {#styling-with-css}

Most webforJ components have built-in methods to make common style changes, such as sizing and theming.

```java
//Set the Frame's width using a CSS keyword
mainFrame.setWidth("fit-content");

//Set the Button's max-width using pixels
btn.setMaxWidth(200);

//Set the Button theme to PRIMARY
btn.setTheme(ButtonTheme.PRIMARY);
```

In addition to these methods, you can style your app using CSS. The **Styling** section of any component's documentation page has specific details about the relevant CSS properties.

webforJ also comes with a set of designed CSS variables called DWC tokens. See the [Styling](/docs/styling/overview) documentation for detailed information on how to style webforJ components, and how to use the tokens.

### Referencing a CSS file {#referencing-a-css-file} 

It's best to have a separate CSS file to keep everything organized and maintainable. Create a file named `card.css` inside `src/main/resources/static/css`, with the following CSS class definition:

```css title="card.css"
.card {
  display: grid;
  gap: var(--dwc-space-l);
  padding: var(--dwc-space-l);
  margin: var(--dwc-space-l) auto;
  border: thin solid var(--dwc-color-default);
  border-radius: 16px;
  background-color: var(--dwc-surface-3);
  box-shadow: var(--dwc-shadow-xs);
}
```

Then, reference the file in `Application.java` by using the `@StyleSheet` annotation with the name of the CSS file. For this step, it's `@StyleSheet("ws://css/card.css")`.

:::tip Webserver protocol
This tutorial uses the Webserver protocol to reference the CSS file. To learn more about how this works, see [Managing Resources](/docs/managing-resources/overview).
:::

### Adding CSS classes to components {#adding-css-classes-to-components}

You can dynamically add or remove class names to components using the `addClassName()` and `removeClassName()` methods. For this tutorial, there’s only one CSS class used:

```java
mainFrame.addClassName("card");
```

## Completed `Application` {#completed-application}

Your `Application` class should now look similar to the following:

```java title="Application.java"
@SpringBootApplication
@StyleSheet("ws://css/card.css")
@AppTheme("system")
@AppProfile(name = "Customer Application", shortName = "CustomerApp")
public class Application extends App {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Override
  public void run() throws WebforjException {
    Frame mainFrame = new Frame();
    Paragraph tutorial = new Paragraph("Tutorial App!");
    Button btn = new Button("Info");

    btn.setTheme(ButtonTheme.PRIMARY)
        .setMaxWidth(200)
        .addClickListener(e -> OptionDialog.showMessageDialog("This is a tutorial!", "Info"));

    mainFrame.setWidth("fit-content")
        .addClassName("card")
        .add(tutorial, btn);
  }

}
```

:::tip Multiple pages
For a more complex app, you can divide the UI into multiple pages for better organization. This concept is covered later in this tutorial in [Routing and Composites](/docs/introduction/tutorial/routing-and-composites).
:::

## Next step {#next-step}

After creating a functional app with a basic user interface, the next step is to add a data model and display the results in a `Table` component in [Working with Data](/docs/introduction/tutorial/working-with-data).