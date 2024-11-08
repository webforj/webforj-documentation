# First step

## Goals and resources

In this step, you'll setup your project structure, create, and style a simple intro app. The following topics will be covered in this section:

  - [Styling](../../styling/getting-started)
  - [Buttons](../../components/button)
  - [Paragraph](../../building-ui/web-components/html-elements)
  - [Logging](../../advanced/browser-console)

## Project structure

Below is a representation of the file structure used in the demo app, along with explanations for the directories you will be interacting with in this project.

```plaintext
webforj-demo-application/
├── pom.xml
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── webforj/
│   │   │           └── demos/
│   │   │               └── DemoApplication.java
│   │   └── resources/
│   │       └── demoApplication.css
```

- **`pom.xml`**: The Maven Project Object Model file that defines project dependencies, plugins, and configuration settings.
- **`src/main/java`**: Contains the Java source files, including `DemoApplication.java`, which is the main entry point for this app.
- **`src/main/resources`**: Contains resource files like CSS and other configuration assets used by the app.

## Creating a webforJ app

To start, create a file called DemoApplication.java in the demos directory. This class will act as the entry point of the app, and will serve as the primary logic to run this step of the webforJ demo. 

### Extending the `App` class

First, extend the `App` class, which represents a webforJ app and requires the `run()` method to be overriden. This class handles the initialization and lifecycle of the app, configuring core services and components.

```java
import com.webforj.App;

public class DemoApplication extends App {
    
}
```

### Overriding the `run()` method

Next, override the `run()` method. The framework calls this method, which is intended to define the behavior and execution flow of the app, after the app has been fully initialized. For now, add a logging statement, which can be viewed in the browser console, to the method.

Finally, add an `AppTitle()` annotation above the class declaration. This title will be shown in the browser tab. 

```java
import com.webforj.App;
import com.webforj.annotation.AppTitle;
import com.webforj.exceptions.WebforjException;

@AppTitle("webforJ Demo Application")
public class DemoApplication extends App {
    @Override
    public void run() throws WebforjException {
        console().log("Demo Application Running");
    }
}
```

### App `Frame`

Frames are top-level windows that can contain other UI elements and are typically used as the main window of an app. Add a `Frame` to your run method into which other UI elements will be added.

:::info Nesting frames 
These frames cant be nested. 
:::

```java
import com.webforj.App;
import com.webforj.annotation.AppTitle;
import com.webforj.exceptions.WebforjException;

@AppTitle("webforJ Demo Application")
public class DemoApplication extends App {
    @Override
    public void run() throws WebforjException {
        Frame mainFrame = new Frame();
        console().log("Demo Application Running");
    }
}
```

### Adding components 

Now that you have a `Frame` to add components to, add a `Paragraph` and a `Button` element to the `Frame` which will be displayed in the browser when the app is run.

```java
@InlineStyleSheet("context://css/demoApplication.css")
@AppTitle("Demo Step 1")
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

The project includes a CSS file, `demoApplication.css`, which defines the layout and appearance of the app’s UI elements. This CSS file controls various UI aspects such as margins, padding, and the layout grid. Below you can see how you would apply classes from said css to your java components.

```java
    mainFrame.addClassName("mainFrame");
```

### Sample CSS code

Of course you can style your app however you want but here you can see an example of some basic styling.

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

The CSS styles are applied to the main frame, providing structure and aesthetics by arranging components with a grid layout and adding margin, padding, and border styles to make the UI visually organized.


