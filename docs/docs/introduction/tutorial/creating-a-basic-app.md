---
title: Creating a Basic App
sidebar_position: 2
description: Step 1 - Learn how to add components to an app.
---

In [Project Setup](/docs/introduction/tutorial/project-setup), you generated a webforJ project. Now it’s time to create the main class for the project and add an interactive interface using webforJ components. By the end of this step, you’ll learn about:

- The entry point for webforJ apps
- webforJ [components](/docs/components/overview)
- Using CSS to style components

<!-- Insert video here -->

## The webforJ entry point {#entry-point}

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


### Application annotations {#application-annotations}

<JavadocLink type="foundation" location="com/webforj/annotation/package-summary">webforJ Annotations</JavadocLink> Gives more options for the main class, like including styling, routing, and more option configurations. This steps adds four annotations to the `Application` class:

The `@SpringBootApplication` annotation marks this class as the main entry point for a Spring Boot app. It enables auto-configuration, component scanning, and allows Spring Boot to start your app with an embedded server. This means you don't need extra configuration to get your app running. Spring Boot handles it for you.

The `@StyleSheet` annotation loads the style sheet, in this case provided by the [webserver-protocol](../../managing-resources/assets-protocols#the-webserver-protocol).

The `@AppTheme` annotation specifies the UI presentation of your app. webforJ components come with different color palettes for both light and dark modes, so components have optimal contrast and are readable in both modes.

Finally, the `@AppTheme` annotation is used to specify various properties of an app, such as its name, display mode, theme color, background color, start URL, and icon sizes. It helps in configuring the app's manifest and how it should be presented to the user.

```java title="Application.java"
package com.webforj.demos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.webforj.App;
import com.webforj.annotation.StyleSheet;
import com.webforj.annotation.AppTheme;
import com.webforj.annotation.AppProfile;

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

## Adding webforJ components

**Note:** this step uses a single `Application` class that directly hosts the UI content. Routing and separate view classes will be introduced in later steps.


Inside the `run()` method, set up your main UI. For example, add a `Frame`, a `Paragraph`, and a `Button`:

```java
@Override
public void run() {
  Frame mainFrame = new Frame();
  Paragraph demo = new Paragraph("Demo Application!");
  Button btn = new Button("Info");
  mainFrame.addClassName("mainFrame");

  btn.setTheme(ButtonTheme.PRIMARY)
     .addClickListener(e -> OptionDialog.showMessageDialog("This is a demo!", "Info"));
  mainFrame.add(demo, btn);
}
```



- Place your CSS in `src/main/resources/static/app.css` and reference it with `@StyleSheet("ws://app.css")`.

## Styling with CSS {#styling-with-css}

Styling in webforJ can be done from component methods or more broadly by using CSS. While the framework supports a cohesive design and style out of the box, it doesn't enforce a specific styling approach, allowing you to apply custom styles that align with your app’s requirements.

With webforJ, you can dynamically apply class names to components for conditional or interactive styling, use CSS for a consistent and scalable design system, and inject entire inline or external stylesheets.

### Adding CSS classes to components {#adding-css-classes-to-components}

You can dynamically add or remove class names to components using the `addClassName()` and `removeClassName()` methods. These methods allow you to control the component’s styles based on your app's logic. Add the `mainFrame` class name to the `Frame` created in the previous steps by including the following code in the `run()` method:

```java
mainFrame.addClassName("mainFrame");
```

### Attaching CSS files {#attaching-css-files}

To style your app, you can include CSS files in your project either by using asset annotations or by utilizing the webforJ <JavadocLink type="foundation" location="com/webforj/Page" >asset API</JavadocLink> at runtime. [See this article](../../managing-resources/importing-assets) for more information. 

For instance, The @StyleSheet annotation is used to include styles from the resources/static directory. It automatically generates a URL for the specified file and injects it into the DOM, ensuring the styles are applied to your app. Note that files outside the static directory aren't accessible.

```java title="DemoApplication.java"
@StyleSheet("ws://styles/library.css")
public class DemoApplication extends App {
  @Override
  public void run() {
    // App logic here
  }
}
```
:::tip Web server URLs
To ensure static files are accessible, they should be placed in the resources/static folder. To include a static file, you can construct its URL using the web server protocol.
:::

### Sample CSS code {#sample-css-code}

A CSS file is used in your project at `resources > static > css > demoApplication.css`, and the following CSS is used to apply some basic styling to the app.

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

```java title="DemoApplication.java"
@StyleSheet("ws://css/demoApplication.css")
@AppTitle("Demo Step 1")
public class DemoApplication extends App {
```

The CSS styles are applied to the main `Frame` and provide structure by arranging components with a [grid layout](https://developer.mozilla.org/en-US/docs/Web/CSS/CSS_grid_layout), and adding margin, padding, and border styles to make the UI visually organized.



## Running the app {#running-the-app}

To see the app in action:

1. Navigate to the top level directory containing the `pom.xml` file, this is `1-creating-a-basic-app` if you're following along with the version on GitHub.

2. Use the following Maven command to run the Spring Boot app locally:
    ```bash
    mvn
    ```

3. Open your browser and go to http://localhost:8080 to view the app.


## Next step

With a functional app that has a basic user interface, the next step is to add data logic and display the results in a `Table` component in the [Working with Data](/docs/introduction/tutorial/working-with-data) step.

