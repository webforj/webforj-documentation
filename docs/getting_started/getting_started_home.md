---
sidebar_position: 2
displayed_sidebar: documentationSidebar
---

# Creating an Application

Once the DWCJ and all applicable dependencies have been installed, it's time to start creating! If you followed the [**Users**](../installation/users.md) installation guide, 
a sample "Hello World" program should be included already. However, in this section,
a demonstration of how to create a new application from scratch will be provided.

## Create a New Java Class

The first thing that will be required is the creation of a new Java class - do this by creating a file with the .java file extension in your IDE of choice. In this demo, we'll 
create a class called **ExampleClass** with a package name of **examplepackage**. 

```java
package examplepackage;

public class ExampleClass {
    
}

```

In order for the DWCJ's engine to recognize that a new application is being created,
the class that is created will need to extend the ```app``` class. 

Most IDEs will import this automatically, but the snippet below shows the import as well.

```java
package examplepackage;

import org.dwcj.App;

public class ExampleClass extends App{
    
}
```

After this is complete, the ```run()``` method will need to be overridden. This method is
where the instructions for the execution of your application will be contained.

```java
package examplepackage;

import org.dwcj.App;
import org.dwcj.exceptions.DwcAppInitializeException;

public class ExampleClass extends App{

    @Override
    public void run() throws DwcAppInitializeException { 

    }
}
```

Finally, in most instances, within the ```run()``` method or in another function called by
the run method, an ```AppPanel``` will need to be created as a container for the contents
of the application or webpage.

To this panel, you can add the various controls that come with the DWCJ, or custom
controls and components, elements, etc. In the example below, the AppPanel will be created
and a button will be added to the panel.

```java
package examplepackage;

import org.dwcj.App;
import org.dwcj.exceptions.DwcAppInitializeException;
import org.dwcj.controls.panels.AppPanel;
import org.dwcj.controls.button.Button;

public class ExampleClass extends App{

    @Override
    public void run() throws DwcAppInitializeException { 
        AppPanel panel = new AppPanel();
        Button myButton = new Button();
        panel.add(myButton);
    }
}
```
Once this has been done, compile or package your program using Maven, and then navigate to
the appropriate URL. If done correctly, you should see the entry in your list of available
applications.