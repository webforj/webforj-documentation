---
sidebar_position: 2
displayed_sidebar: documentationSidebar
---

# Creating an Application

Once the DWCJ and all applicable dependencies have been installed and the POM configuration has been completed, it's time to start creating! If you followed the installation guide, a sample "Hello World" program should be included already. However, in this section, a demonstration of how to create **a new application from scratch** will be provided.

:::tip
Using the [HelloWorldJava](./Templates/hello_world.md) program will help expedite the creation of a simple application.
:::

## Start a new Application
Should you decide to create an application from scratch, a few integral steps must be followed in order to get the DWCj application up and running.

### Create a Java Class
 
The first thing that will be required is the creation of a new Java class - do this by creating a file with the .java file extension in your IDE of choice. In this demo, we'll 
create a class called **MyApplication**. 

```java
public class MyApplication {
    
}

```
### Extend the `App` class

In order for the DWCj's engine to recognize that a new application is being created, the class that is created will need to extend the ``App`` class. 

```java
import org.dwcj.App;

public class MyApplication extends App{
    
}
```

### Override the `run()` method

After this is complete, the ``run()`` method must be overridden. This method is where the instructions for the execution of your application will be contained.

```java
import org.dwcj.App;
import org.dwcj.exceptions.DwcjException;

public class MyApplication extends App{

    @Override
    public void run() throws DwcjException { 

    }
}
```

### Create the main `Frame`

Finally, in most instances, within the ```run()``` method or in another function called by the run method, a  ``Frame`` will need to be created as a main container for the contents of the application.

```java
import org.dwcj.App;
import org.dwcj.exceptions.DwcException;
import org.dwcj.component.window.Frame;

public class MyApplication extends App{

    @Override
    public void run() throws DwcjException { 
        Frame mainFrame = new Frame();
    }
}
```

## Customizing your Application

You can then add various components that come with the DWCJ, or custom components, elements, etc to the `Frame`. In the example below, the `Frame` will be created and a button will be added to the panel.

```java
import org.dwcj.App;
import org.dwcj.exceptions.DwcException;
import org.dwcj.component.window.Frame;
import org.dwcj.component.button.Button;

public class ExampleClass extends App{

    @Override
    public void run() throws DwcjException { 
        Frame mainFrame = new Frame();
        Button myButton = new Button("My Button");
        mainFrame.add(myButton);
    }
}
```

Once this has been done, you can use the [installation plugin](./configuration.md) to compile and deploy your application. 

:::tip
Visit [this section](../components/home) to see the various components that can be used in the DWCj. To see how to [create your own components](../ui/home), see this section. You can also explore the [layouts](../category/layouts) designed to help quickly and efficiently create new apps.
:::

### App Data

You can set the application's title, theme and meta tags by using annotations.

```java
@AppTitle("My app title")
@AppTheme("system")
@AppMeta(name = "description", content = "My App")
@AppMeta(name = "keywords", content = "My, App, Java")

public class HelloWorld extends App {
//Implementation
}
```