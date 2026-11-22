---
title: "JavaFX vs Swing: which Java GUI framework to pick in 2026"
description: "JavaFX vs Swing compared on distribution, UI code, styling, data binding, threading and packaging, with a clear pick for 2026 and a path to the browser."
slug: java-gui-frameworks-compared
date: 2026-09-19
authors: webforJ
tags: [insights, web development, modernization, webswing]
image: https://cdn.webforj.com/webforj-documentation/blogs/2026-09-19-java-gui-frameworks-compared/cover-v3.jpg
hide_table_of_contents: false
---

![cover image](https://cdn.webforj.com/webforj-documentation/blogs/2026-09-19-java-gui-frameworks-compared/cover-v3.jpg)

You are starting a Java desktop app and have to pick a toolkit. Or you own a Swing app and wonder whether JavaFX would have been the better bet. Both toolkits are maintained, both are plain Java, and the choice still shapes years of work.

JavaFX vs Swing in 2026 comes down to this. Swing ships inside the JDK and is the safe choice for a codebase that already uses it. JavaFX is the more modern toolkit, with CSS styling, FXML layouts, observable properties and built-in charts, and it is the stronger pick for a new desktop app. This post compares the two on the points that change the decision, with the same screen written in both. It ends with the case neither toolkit covers: users who need the app in a browser, where Webswing and webforJ take over.

<!-- truncate -->

## JavaFX vs Swing at a glance {#javafx-vs-swing-at-a-glance}

| Question | Swing | JavaFX |
| --- | --- | --- |
| Ships with | The JDK, in the `java.desktop` module | A separate download, OpenJFX |
| Built on | AWT | Its own windowing layer and scene graph |
| A screen is defined in | Java code | Java code or FXML markup |
| Styling | Look and feel classes | CSS style sheets |
| Data binding | Listeners you write | Observable properties and bindings |
| Drawing | Each component paints itself with Java 2D | The toolkit draws a scene graph |
| Animation and 3D | A timer and your own painting code, no 3D classes | Animation classes and 3D shapes included |
| UI thread | Event dispatch thread | FX thread |
| Built in beyond forms | Tables, trees, text components | Tables, trees, charts, media, a web view |
| Targets | Desktop | Desktop, mobile and embedded |
| Releases | With each JDK | Every six months, as OpenJFX |

The sections below take the rows that matter most.

## Where each toolkit comes from {#where-each-comes-from}

Swing is part of the JDK. The `java.desktop` module [defines the AWT and Swing toolkits](https://docs.oracle.com/en/java/javase/21/docs/api/java.desktop/module-summary.html), so a Swing app needs nothing beyond a Java runtime. Every JDK release includes it.

JavaFX used to ship with the Oracle JDK. It left with JDK 11 and is now developed as [OpenJFX](https://openjfx.io/), an open source project with its own releases. You add it through Maven or Gradle modules, or as a standalone SDK. OpenJFX follows the JDK's six-month rhythm: version 26 arrived in March 2026 and version 27 in September 2026.

So neither toolkit is abandoned. Swing is in maintenance: each JDK release brings bug fixes to it, and new features are rare. JavaFX is where new desktop UI features land.

## The toolkit under Swing {#where-awt-fits}

The Abstract Window Toolkit (AWT) is the oldest of the three, and every Swing app still uses it. AWT's own controls are heavyweight, which means [each one is associated with a native window](https://docs.oracle.com/en/java/javase/25/docs/api/java.desktop/java/awt/Component.html) of the operating system. Swing components are lightweight. They're written and painted in Java, so they don't depend on native widgets.

Swing was built on top of AWT and didn't replace it. [`JComponent` extends the AWT class `Container`](https://docs.oracle.com/en/java/javase/25/docs/api/java.desktop/javax/swing/JComponent.html), and the `FlowLayout` and the action listener in the Swing sample below come from AWT packages.

JavaFX controls don't build on AWT. The toolkit has its own windowing layer and its own scene graph. So the choice today is between Swing and JavaFX. Plain AWT matters as the foundation under Swing, not as a third option for a new app.

## The same screen in both {#same-screen}

Here is a small screen in each toolkit: a text field, a button, and a greeting.

Swing:

```java
public class GreetingPanel extends JPanel {
  public GreetingPanel() {
    JTextField name = new JTextField(20);
    JButton greet = new JButton("Greet");
    JLabel message = new JLabel();

    greet.addActionListener(e -> message.setText("Hello, " + name.getText()));

    setLayout(new FlowLayout());
    add(name);
    add(greet);
    add(message);
  }
}
```

JavaFX:

```java
public class GreetingPane extends HBox {
  public GreetingPane() {
    TextField name = new TextField();
    Button greet = new Button("Greet");
    Label message = new Label();

    greet.setOnAction(e -> message.setText("Hello, " + name.getText()));

    setSpacing(8);
    getChildren().addAll(name, greet, message);
  }
}
```

At this size the two are close. You create components, attach a listener, and add them to a container. Swing positions children with a layout manager set on the panel. JavaFX uses layout panes such as `HBox`, `VBox`, and `GridPane`, and every control is a node in a scene graph.

JavaFX also lets you move the layout out of Java. FXML is an XML format that describes the same tree of controls, and Scene Builder is a visual tool that edits FXML files. In Swing the screen lives in Java code.

## Data binding {#data-binding}

This is the largest difference in daily work. Every JavaFX control exposes its state as observable properties, and one property can be bound to another:

```java
greet.disableProperty().bind(name.textProperty().isEmpty());
message.textProperty().bind(Bindings.concat("Hello, ", name.textProperty()));
```

With those two lines the button stays disabled while the field is empty, and the greeting follows the text as the user types. No listener code is involved.

Swing has no binding layer in the JDK. The same behavior takes a `DocumentListener` on the field that updates the button and the label by hand. On a screen with many dependent fields, that listener code is where most Swing bugs hide.

## Styling {#styling}

Swing changes its appearance through look and feel classes. The JDK ships several, including Metal, Nimbus and one that follows the operating system, and `UIManager.setLookAndFeel` switches between them. Changing the look of one button usually means setting colors and fonts in Java.

JavaFX is styled with CSS. Controls carry style classes, and a style sheet sets properties such as `-fx-background-color` and `-fx-text-fill`. A designer can restyle the app without touching Java, and one style sheet can change the whole product.

## Graphics and performance {#graphics-and-performance}

The two toolkits draw in different ways. A Swing component paints itself. The toolkit calls its [`paintComponent` method](https://docs.oracle.com/javase/tutorial/uiswing/painting/closer.html), and custom drawing is Java 2D code you write there. To animate something you start a `javax.swing.Timer`, change some state on each tick and repaint.

JavaFX keeps a scene graph, a tree of nodes that the toolkit draws for you. Its rendering pipeline [uses the graphics hardware when it's available](https://docs.oracle.com/javase/8/javafx/get-started-tutorial/jfx-architecture.htm) and falls back to software rendering when it isn't. Animation is part of the toolkit: the [`javafx.animation` package](https://openjfx.io/javadoc/25/javafx.graphics/javafx/animation/package-summary.html) has timelines and ready-made transitions that fade, move, rotate, and scale a node.

JavaFX also has 3D shapes such as `Box`, `Sphere` and `Cylinder`. [3D is a conditional feature](https://openjfx.io/javadoc/25/javafx.graphics/javafx/scene/shape/Shape3D.html), so check that your target platform supports it. Swing has no 3D classes of its own.

Don't read these differences as Swing being slow. Java 2D, which Swing paints with, has hardware pipelines too, such as [the Metal pipeline on macOS](https://openjdk.org/jeps/382). The practical difference is how much you write yourself. JavaFX includes animation, effects and 3D, and in Swing that work is your own painting code or a separate library.

## Threading {#threading}

Both toolkits have a single UI thread. [Swing component methods must be invoked from the event dispatch thread](https://docs.oracle.com/javase/tutorial/uiswing/concurrency/dispatch.html) unless they are labeled thread safe, and background work reaches the UI through `SwingUtilities.invokeLater`.

JavaFX has the same rule under a different name. The UI belongs to the FX thread, and [`Platform.runLater`](https://openjfx.io/javadoc/21/javafx.graphics/javafx/application/Platform.html) posts a `Runnable` to it. If you know one model, you know the other.

## What comes in the box {#in-the-box}

Both toolkits cover forms, tables, and trees. Swing has `JTable` and `JTree`, and JavaFX has `TableView` and `TreeView`, which read their rows from observable lists.

JavaFX includes more beyond that. Charts are built in, with line, bar, pie, and other chart types. There is a media player for audio and video, and a `WebView` control that renders web pages inside the app. In Swing, charts and media come from third-party libraries.

Swing's advantage is age. Two decades of libraries, components and answers exist for it, and a team that knows it well is productive in it.

## Using both in one app {#using-both}

The choice isn't all or nothing. `JFXPanel` embeds JavaFX content in a Swing app, and `SwingNode` embeds Swing content in a JavaFX app.

That matters for a team with a large Swing codebase. You can add one JavaFX chart or one new JavaFX screen to the existing app without rewriting the rest.

## Packaging and updates {#packaging-and-updates}

Here the two toolkits are the same. Either way the program has to reach each user's machine. The JDK's [`jpackage`](https://docs.oracle.com/en/java/javase/21/jpackage/packaging-overview.html) tool builds native installers for Windows, macOS, and Linux and bundles a runtime. App packages must be built on the target platform, so supporting all three means running the packaging step on each.

Every release is then a rollout. You build installers, publish them and wait for users to take the update. Versions drift, and support has to know which build each person runs.

## Which one to pick {#which-one-to-pick}

**Pick JavaFX** for a new desktop app. Bindings remove a whole class of listener code, CSS makes the UI easier to style, and charts, media and the web view are included.

**Pick Swing** when one of these is true:

- The app must run on a plain JDK with no extra modules.
- You have a working Swing codebase and a team that knows it.
- You depend on Swing libraries that have no JavaFX counterpart.

**Don't rewrite a working Swing app in JavaFX** just to modernize it. A rewrite touches every screen and the result is still a program you install on each machine. Embed JavaFX where you need it instead.

**Pick neither** when your users need the app in a browser. That case has its own path.

## When the app must reach a browser {#browser}

Both toolkits put the UI on the user's machine. If people need to open the app from a URL, on any device, with one deployment instead of many installs, a desktop toolkit is the wrong layer to change.

A team that owns a Swing or JavaFX app doesn't have to start over. [Webswing](/docs/integrations/webswing/overview) runs Swing, JavaFX and SWT apps in a browser without changes to their source, so users get a URL now.

From there the screens move to the web one at a time with webforJ, a full-stack Java framework where the UI is still Java. This is the greeting screen as a webforJ view:

```java
@Route("greeting")
public class GreetingView extends Composite<FlexLayout> {
  public GreetingView() {
    TextField name = new TextField("Name");
    Button greet = new Button("Greet");
    Paragraph message = new Paragraph();

    greet.onClick(e -> message.setText("Hello, " + name.getValue()));

    getBoundComponent().add(name, greet, message);
  }
}
```

The listener is the same kind of lambda, and the class runs on the server, where it calls your existing Java services directly. The `WebswingConnector` component embeds the running desktop app inside a webforJ page, so old and new screens share one browser tab while the old ones retire. Webswing and webforJ are partners on this path, and the [Webswing and webforJ roadmap](/blog/webswing-and-webforj) lays out the stages.

An AI coding assistant can draft each new screen from the Swing or JavaFX original. Connected through the [webforJ AI plugin](/docs/ai-tooling), it looks up the real webforJ API instead of guessing, and it can hand back a test with each screen, so every one arrives with a pass or a fail. That cycle is [the AI verification loop for Java](/blog/ai-verification-loop-java).

The decision guides go further. [Java Swing to web migration](/blog/migrate-swing-app-to-web) compares the paths for a Swing app, and [JavaFX to web](/blog/javafx-to-web) maps stages, scenes and FXML to webforJ.

## Frequently asked questions {#faq}

### Should I use Java Swing or JavaFX? {#faq-swing-or-javafx}

For a new desktop app, JavaFX. It has data binding, CSS styling and built-in charts. Stay with Swing when you already have a Swing codebase, or when the app must run on a plain JDK with no extra modules.

### Is JavaFX dead or still used in 2026? {#faq-javafx-still-used}

It's alive and still used. JavaFX is developed as the open source OpenJFX project and ships a new release every six months. Version 27 arrived in September 2026.

### Is Java Swing still used in 2026? {#faq-swing-still-used}

Yes. Swing ships in the `java.desktop` module of the JDK, and software such as IntelliJ IDEA runs on it: the [IntelliJ Platform builds its UI from Swing components](https://plugins.jetbrains.com/docs/intellij/user-interface-components.html). Swing isn't obsolete. Each JDK release still ships and maintains it.

### Is Java Swing deprecated? {#faq-swing-deprecated}

No. Swing is part of the `java.desktop` module in current JDK releases. JDK 26 removed the Applet API, including `JApplet`, and the rest of Swing stays.

### Should I move a Swing app to JavaFX? {#faq-swing-to-javafx}

Only when the app has to stay a desktop program and Swing is holding it back. The move is a rewrite of every screen. `JFXPanel` lets you add JavaFX content to the Swing app instead. If the goal is to reach users in a browser, Webswing can run the Swing app there now while webforJ replaces its screens one at a time.

### Can a Swing or JavaFX app run in a browser? {#faq-run-in-browser}

Yes. Webswing runs Swing, JavaFX and SWT apps in a browser without changes to their source.

## Plan your move to the browser {#plan-your-move}

To see how the screen-by-screen path works for your own desktop app and talk it through with our team, start on the [Swing modernization page](/modernization).
