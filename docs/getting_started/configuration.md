---
sidebar_position: 1
displayed_sidebar: documentationSidebar
---

# Configuration

webforJ can be configured using a project's POM file and a plugin designed to make deploying an application easy. The following sections outline the various options that can be changed in order to achieve the desired result, both with and without the provided webforJ installation plugin.

## Using the Install Plugin

:::success Tip! 
This is the best option for most users, especially those who have followed one of the installation guides on this site.
:::

When using the installation plugin, the tags within the `<configuration>` tag can be changed to configure your application. Editing the following lines in the default POM file that comes with the [`HelloWorldJava`](https://github.com/webforj/HelloWorldJava) starting repository will result in these changes:

```xml {13-16} showLineNumbers
<plugin>
    <groupId>com.webforj</groupId>
    <artifactId>webforj-install-maven-plugin</artifactId>
    <version>${webforj.version}</version>
    <executions>
        <execution>
            <goals>
                <goal>install</goal>
            </goals>
    </execution>
    </executions>
    <configuration>
        <deployurl>http://localhost:8888/webforj-install</deployurl>
        <classname>samples.HelloWorldApp</classname>
        <publishname>hello-world</publishname>
        <debug>true</debug>
    </configuration>
</plugin>
```

- **`<deployurl>`** This tag is the URL under which the webforJ endpoint for the project installation can be reached. For users running their application locally, a default port 8888 is used. For users running Docker, the port should be changed to the port that was entered when [configuring the Docker container](../installation/docker_user.md#2-configuration).

- **`<classname>`** This tag should contain the name of package and class name of the application you wish to run. This is helpful for projects which may contain multiple classes that extend the `App` class, and allows you to choose which program should run when the base URL is navigated to.

- **`<publishname>`** This tag specifies what name the program will take in the final URL. Generally, to run your program, you'll enter a URL similar to `http://localhost:8888/webapp/<publishname>`, where the value within the `<publishname>` tag is entered. When this is done, the class extending application specified in the `<classname>` tag is run.

- **`<debug>`** The debug tag can be set to true or false, and will determine whether or not error messages thrown by your program will be displayed in the browser's console or not. 

### Running a Specific Application


It is possible to specify which class that extends App within your project is displayed by modifying the URL in one of the following ways: 

**1. Modify the URL Path** - If your application is running on `localhost:8888`, the publish name is `MyProgram` and the Java class that extends the `App` class you want to run is named `MyApp`, the URL would look as follows:

`http://localhost:8888/webapp/MyProgram/MyApp`

**2. Using Query Parameters** - The same result can be achieved using query parameters. Taking the same example, with `localhost:8888` as the port, `MyProgram` as the publish name and `apps.MyApp` as the full name of the class to run, the URL would be:

`http://localhost:8888/webapp/yourAppName?class=apps.MyApp`

:::info
When using the query parameter method, it is important to include the full name of the desired class, including package names.
:::


## Without the Install Plugin

:::warning
This is not the recommended method of configuring your application, and should be used only if needed.
:::

### Default Class

It is possible to configure the webforJ to automatically load an application from the list of available applications that extend the `App` class. 

#### Editing the BBj config file
The second option is to open your config.bbx file, and set the classname within the file itself. This file is found in the cfg directory of your BBj installation, `C:\bbx\cfg\config.bbx` for example. To do so, add the following line and replace <b>your.class.name.here</b> with the full classname as it appears on the list of classes:

`SET DWCJCLASSNAME=your.class.name.here`

#### Using the Enterprise Manager

Finally, you can set the default class within the Enterprise Manager by adding the following line as a program argument within your Application:

`class=your.class.name.here`

Replace <b>your.class.name.here</b> with the full class name of your App (implementing [org.dwcj.engine.App](https://javadoc.io/doc/org.dwcj/dwcj-engine/latest/com/webforj/App.html)), as it appears on the list of classes on the welcome page.

Once any of these options have been completed, the specified class will always load instead of displaying a list of available classes.

<br />

### Debug Mode

It's also possible to run your application in debug mode, which will allow comprehensive error messages to be printed to the console. 

The first option is to change the config.bbx file, found in the cfg directory of your BBj installation, `C:\bbx\cfg\config.bbx` for example. Add the line `SET DEBUG=1` to the file and save your changes.

Additionally, in the Enterprise Manager, you can add the following as a program argument:

`DEBUG`

Completing either of these will allow error messages to be printed to the browser console.