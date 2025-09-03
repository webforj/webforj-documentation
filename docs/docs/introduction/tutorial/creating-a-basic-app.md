---
title: Creating a Basic App
sidebar_position: 2
---


This first step lays the foundation for your customer management app by creating a simple, interactive interface using webforJ with Spring Boot. You’ll set up a minimal Spring Boot project, define your main app class, and build a UI with a button and dialog. This straightforward implementation introduces key components and gives you a feel for how webforJ works.

**Note:** this step uses a single `Application` class that directly hosts the UI content. Routing and separate view classes will be introduced in later steps.

By the end of this step, you’ll have a running app that demonstrates basic interaction and is ready for further extension.

---

## Prerequisites

- Java 17 or 21
- Maven
- A Java IDE (e.g., IntelliJ IDEA, Eclipse, VSCode)
- Web browser

---

## 1. Project setup

You can create your project using [startforJ](https://docs.webforj.com/startforj) (choose the “webforJ + Spring Boot” flavor) or with the Maven archetype:

```bash
mvn -B archetype:generate \
  -DarchetypeGroupId=com.webforj \
  -DarchetypeArtifactId=webforj-archetype-hello-world \
  -DarchetypeVersion=LATEST \
  -DgroupId=org.example \
  -DartifactId=my-app \
  -Dversion=1.0-SNAPSHOT \
  -Dflavor=webforj-spring
```

---

## 2. Main app class

Create a class called `Application.java` that extends `App` and is annotated for Spring Boot and webforJ:

The `@SpringBootApplication` annotation marks this class as the main entry point for a Spring Boot app. It enables auto-configuration, component scanning, and allows Spring Boot to start your app with an embedded server. This means you don't need extra configuration to get your app running. Spring Boot handles it for you.

The `@StyleSheet` annotation loads the style sheet, in this case provided by the [webserver-protocol](../../managing-resources/assets-protocols#the-webserver-protocol).

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

  @Override
  public void run() {
    // UI setup goes here
  }
}
```



---

## 3. Adding components

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

---

## 4. Configuration

- `src/main/resources/application.properties`:
  ```
  spring.application.name=DemoApplication
  server.port=8080
  webforj.entry = com.webforj.demos.Application
  webforj.debug=true
  ```

- Make sure the spring [dependencies](../../integrations/spring/spring-boot#step-2-add-spring-dependencies) are correctly configured in your POM, if not, add them.

- Place your CSS in `src/main/resources/static/app.css` and reference it with `@StyleSheet("ws://app.css")`.

---

## 5. Running the app

From your project directory, run:

```bash
mvn spring-boot:run
```

Then open [http://localhost:8080](http://localhost:8080) in your browser.

---

## Next steps

You now have a working Spring Boot + webforJ app with a simple UI. The next steps will introduce routing, data binding, and more advanced features.
