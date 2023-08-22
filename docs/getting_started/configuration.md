---
sidebar_position: 2
displayed_sidebar: documentationSidebar
---

# Configuration

Options exist to configure the DWCJ in order to load a default class or enable debug mode.

## Using the Install Plugin

:::success Tip! 
For most users, especially those who have followed one of the installation guides on this site, this will be the best option.
:::

In a project using the install plugin, there are various tags that can be changed which will help configure your application's URL. The following lines in the default POM file that comes with the `HelloWorldJava` starting repository can be configured:

```xml {14,17,18}
<plugin>
    <groupId>org.dwcj</groupId>
    <artifactId>dwcj-install-maven-plugin</artifactId>
    <version>0.1.0</version>
    <executions>
        <execution>
            <goals>
                <goal>install</goal>
            </goals>
        </execution>
    </executions>
    <configuration>
        <deployurl>http://localhost:8888/dwcj-install</deployurl>
        <classname>samples.HelloWorldJava</classname>
        <username>admin</username>
        <password>admin123</password>
        <publishname>hworld</publishname>
        <debug>true</debug>
    </configuration>
</plugin>
```

1. The `<classname>` tag should contain the name of package and classname of the application you wish to run. This is helpful for projects which may contain multiple classes which extend the App class, and allows you to choose which program should run when the URL is navigated to.

2. The `<publishname>` tag specifies what name the program will take in the final URL. Generally, to run your program, you'll enter a URL similar to `http://localhost:8888/webapp/<publishname>`, where the value within the `<publishname>` tag is entered. When this is done, the class extending application specified in the `<classname>` tag is run.

3. The `<debug>` tag can be set to true or false, and will determine whether or not error messages thrown by your program will be displayed in the browser's console or not. 

It is also possible to start a different class which extends App within your project by modifying the URL in the following format - replace <b>your.class.name.here</b> with the full classname:

`http://localhost:8888/webapp/yourAppName?class=your.class.name.here`

## Without the Install Plugin

### Default Class

It is possible to configure the DWCJ to automatically load an application from the list of available applications that extend the `App` class. 

#### Editing the BBj config file
The second option is to open your config.bbx file, and set the classname within the file itself. This file is found in the cfg directory of your BBj installation, `C:\bbx\cfg\config.bbx` for example. To do so, add the following line and replace <b>your.class.name.here</b> with the full classname as it appears on the list of classes:

`SET DWCJCLASSNAME=your.class.name.here`

#### Using the Enterprise Manager

Finally, you can set the default class within the Enterprise Manager by adding the following line as a program argument within your Application:

`class=your.class.name.here`

Replace <b>your.class.name.here</b> with the full class name of your App (implementing [org.dwcj.engine.App](https://javadoc.io/doc/org.dwcj/dwcj-engine/latest/org/dwcj/App.html)), as it appears on the list of classes on the welcome page.

Once any of these options have been completed, the specified class will always load instead of displaying a list of available classes.

<br />

### Debug Mode

It's also possible to run your application in debug mode, which will allow comprehensive error messages to be printed to the console. 

The first option is to change the config.bbx file, found in the cfg directory of your BBj installation, `C:\bbx\cfg\config.bbx` for example. Add the line `SET DEBUG=1` to the file and save your changes.

Additionally, in the Enterprise Manager, you can add the following as a program argument:

`DEBUG`

Completing either of these will allow error messages to be printed to the browser console.