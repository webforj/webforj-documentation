---
sidebar_position: 2
displayed_sidebar: documentationSidebar
title: Creating an Application
---

Once webforJ and all applicable dependencies have been installed and the POM configuration has been completed, it's time to start creating! If you followed the installation guide, a sample "Hello World" program should be included already. However, in this section, we will demonstrate how to create **a new application from scratch**.

:::tip
Using the [HelloWorldJava](./templates/hello-world) program will help expedite the creation of a simple application.
:::

## Start a new application
Should you decide to create an application from scratch, you must follow a few integral steps to get the webforJ application up and running.

### Create a Java class
 
First, you need to create a new Java class. Do this by creating a `.java` file in your IDE of choice. In this demo, we'll 
create a class called **MyApplication**. 

```java
public class MyApplication {
    
}

```
### Extend the `App` class

Your new class must extend the `App` class so that webforJ can recognize it as a webforJ application.

```java
import com.webforj.App;

public class MyApplication extends App{
    
}
```

### Override the `run()` method

Next, you must override the  `run()` method. This method is where you will write the instructions for the execution of your application.

```java
import com.webforj.App;
import com.webforj.exceptions.webforJException;

public class MyApplication extends App{

    @Override
    public void run() throws webforJException { 

    }
}
```

### Create the main `Frame`

Finally, in most instances, you will need to create a `Frame` within the `run()` method or in another function called by the run method. This `Frame` is the main container for the contents of your application.

```java
import com.webforj.App;
import com.webforj.exceptions.webforJException;
import com.webforj.component.window.Frame;

public class MyApplication extends App{

    @Override
    public void run() throws webforJException { 
        Frame mainFrame = new Frame();
    }
}
```

## Customizing your application

You can then add various components that come with webforJ, custom components, elements, etc., to the `Frame`. In the example below, after the `Frame` is created, a button is added to it.

```java
import com.webforj.App;
import com.webforj.exceptions.webforJException;
import com.webforj.component.window.Frame;
import com.webforj.component.button.Button;

public class ExampleClass extends App{
    
    Button myButton = new Button("My Button");

    @Override
    public void run() throws webforJException { 
        Frame mainFrame = new Frame();
        mainFrame.add(myButton);
    }
}
```

Once you have completed these steps, you can use the [installation plugin](./configuration.md) to compile and deploy your application. 

:::tip
Visit [this section](../components/overview) to see the various components that you can use in webforJ. To see how to [create your own components](../building-ui/basics), see this section.
:::

### App data

You can set the application's title, theme, and meta tags by using annotations.

```java
@AppTitle("My app title")
@AppTheme("system")
@AppMeta(name = "description", content = "My App")
@AppMeta(name = "keywords", content = "My, App, Java")

public class HelloWorld extends App {
//Implementation
}
```