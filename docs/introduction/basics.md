---
title: App Basics
sidebar_position: 3
pagination_next: 
---

Once webforJ and its dependencies are set up in your project, you're ready to create the app structure. This article will walk through the key elements of a basic webforJ app, specifically focusing on the `Application` and `HomeView` classes, which are the foundational classes in the `webforj-archetype-hello-world` starter project.

## Main app class: `Application.java`

The `Application` class serves as the entry point for your webforJ app, setting up essential configurations and routes. To start, notice the class's declaration and annotations. 

This class extends the core `App` class from webforJ, making it recognizable as a webforJ app. Various annotations configure the app's theme, title, and routing.

```java
@Routify(packages = "com.samples.views")
@AppTitle("webforJ Hello World")
@StyleSheet("ws://app.css")
public class Application extends App {
}
```

- `@Routify`: Specifies that webforJ should scan the `com.samples.views` package for route components.
- `@AppTitle`: Defines the title displayed on the app's browser tab.
- `@StyleSheet`: Links an external CSS file, `app.css`, allowing custom styling for the app.

The Application class doesn't contain any additional methods because the configurations are set through annotations, and webforJ handles the app initialization.

With `Application.java` set up, the app is now configured with a title and routes pointing to the views package. Next, an overview of the `HomeView` class gives insight into what's displayed when the app is run.

## Main view class: `HomeView.java`

The `HomeView` class defines a simple view component that serves as the homepage for the app. It displays a field and a button that to greet the user's typed name.

### Class declaration and annotations

`HomeView` extends `Composite<FlexLayout>`, which allows it to act as a reusable component composed of a [`FlexLayout`](../components/flex-layout) component. The [`@Route("/")`](../routing/overview) makes this the root route of the app.

```java
@Route("/")
public class HelloWorldView extends Composite<FlexLayout> {

  private FlexLayout self = getBoundComponent();
  private TextField hello = new TextField("What is your name?");
  private Button btn = new Button("Say Hello");

  public HelloWorldView(){
    self.setDirection(FlexDirection.COLUMN);
    self.setMaxWidth(300);
    self.setStyle("margin", "1em auto");

    btn.setTheme(ButtonTheme.PRIMARY)
        .addClickListener(e -> 
          Toast.show("Welcome to webforJ Starter " + hello.getValue() + "!", Theme.GRAY));

    self.add(hello, btn);
  }
}
```

<!-- TODO update/reinstate this section once hello world is overhauled -->

<!-- ### Component initialization

Inside the class, several UI elements are initialized and declared:

```java
private FlexLayout self = getBoundComponent();
private Img image = new Img("context://images/logo.svg");
private H1 title = new H1("Welcome to webforJ");
private Button counter = new Button("Count is 0");
private Anchor docs = new Anchor("https://documentation.webforj.com/", "Check out the docs to learn more");
private int count = 0;
```

- `self`: The main layout component using [`FlexLayout`](../components/flex-layout), configured as a container for the elements. This element uses the `getBoundComponent()` method to store the main `FlexLayout` the class contains.
- `image`: Displays an SVG logo.
- `title`: Shows a welcome message.
- `counter`: A button labeled `Count is 0`, which updates each time it's clicked.
- `docs`: A link to the webforJ documentation.

### Constructor and layout configuration

The constructor sets up the layout and component interactions:

```java
public HomeView() {
    self.addClassName("home-view");
    self.setDirection(FlexDirection.COLUMN)
        .setJustifyContent(FlexJustifyContent.CENTER)
        .setAlignment(FlexAlignment.CENTER)
        .setSpacing("3em")
        .setStyle("height", "100dvh");

    counter.setTheme(ButtonTheme.PRIMARY);
    counter.onClick(e -> counter.setText("Current Count: " + (++count)));

    self.add(image, title, counter, docs);
}
```

- Layout Settings: The [`FlexLayout`](../components/flex-layout) is configured to stack items vertically, using `FlexDirection.COLUMN`, center them using `FlexJustifyContent.CENTER and FlexAlignment.CENTER`, and add spacing by calling `setSpacing("3em")`.
- Button Action: The counter button uses a click event listener, `onClick`, to increment the count and update its label whenever it’s clicked.
- Component Addition: Finally, all elements are added to the layout via `self.add(image, title, counter, and docs)`. -->

### Component initialization

Inside the class, several UI elements are initialized and declared:

```java
private FlexLayout self = getBoundComponent();
private TextField hello = new TextField("What is your name?");
private Button btn = new Button("Say Hello");
```

- `self`: The main layout component using [`FlexLayout`](../components/flex-layout), configured as a container for the elements. This element uses the `getBoundComponent()` method to store the main `FlexLayout` the class contains.
- `hello`: A [`TextField`](../components/fields/textfield) labeled `What is your name?` for users to input their name.
- `btn`: A primary-styled [`Button`](../components/button) labeled `Say Hello`.

### Layout configuration

The layout `(self)` is configured with a few key style properties:

- `FlexDirection.COLUMN` stacks the elements vertically.
- `setMaxWidth(300)` restricts the width to 300 pixels for a compact layout.
- `setStyle("margin", "1em auto")` centers the layout with a margin around it.

### Adding components to the layout
Finally, the hello text field and btn button are added to the [`FlexLayout`](../components/flex-layout) container by calling `self.add(hello, btn)`. This arrangement defines the view’s structure, making the form both interactive and visually centered.

## Styling the app

The `styles.css` file provides custom styling for your webforJ app. This CSS file is referenced in the Application class using the [`@StyleSheet`](../styling/getting-started#using-annotations) annotation, which allows the app to apply styles to components within the app.

This file is located in the `resources/static` directory of the project, and can be referenced using the web server URL `ws://app.css`.