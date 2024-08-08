---
sidebar_position: 1
displayed_sidebar: documentationSidebar
---

# Configuration

webforJ can be configured using a project's POM file and an installation plugin designed to make deploying an application easy. The following sections outline the various options that can be changed to achieve the desired result, both with and without the provided webforJ installation plugin.

## Using the installation plugin

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

- **`<deployurl>`** This tag is the URL where the webforJ endpoint for the project installation can be reached. For users running their application locally, a default port of 8888 is used. For users running Docker, the port should be changed to the port that was entered when [configuring the Docker container](../installation/docker_user.md#2-configuration).

- **`<classname>`** This tag should contain the package and class name of the application you wish to run. This is helpful for projects that contain multiple classes that extend the `App` class, and allows you to choose which program to run from the base URL.

- **`<publishname>`** This tag specifies the name of the application in the published URL. Generally, to run your program, you'll navigate to a URL similar to `http://localhost:8888/webapp/<publishname>`, replacing `<publishname>` with the value in the `<publishname>` tag. Then, the program specified by the `<classname>` tag is run.

- **`<debug>`** The debug tag can be set to true or false, and will determine whether or not the browser's console displays error messages thrown by your program. 

### Running a specific application


It is possible to specify which class that extends `App` within your project is displayed by modifying the URL in one of the following ways: 

**1. Modify the URL Path.** For example, if your application is running on `localhost:8888`, the publish name is `MyProgram`, and you want to run the Java class `MyApp` that extends the `App` class, the URL would look as follows:

`http://localhost:8888/webapp/MyProgram/MyApp`

**2. Use Query Parameters.** The same result can be achieved using query parameters. Taking the same example, with `localhost:8888` as the port, `MyProgram` as the publish name and `apps.MyApp` as the full name of the class to run, the URL would be:

`http://localhost:8888/webapp/MyProgram?class=apps.MyApp`

:::info
When using the query parameter method, it is important to include the full name of the desired class, including package names.
:::


## Without the installation plugin

:::warning
This is not the recommended method of configuring your application, and should be used only if needed.
:::

### Default class

It is possible to configure webforJ to automatically load an application from the list of available applications that extend the `App` class. 

#### Editing the BBj config file
One option is to set the classname within your `config.bbx` file, located in the `cfg/` directory of your BBj installation. To do so, add the following line, replacing `your.class.name.here` with the full classname as it appears on the list of classes:

`SET DWCJCLASSNAME=your.class.name.here`

#### Using the Enterprise Manager

Another option is to set the default class within the Enterprise Manager by adding the following line as a program argument within your Application:

`class=your.class.name.here`

Replace `your.class.name.here` with the full class name of your App as it appears on the list of classes on the welcome page.

Once either of these options have been completed, the specified class will always load instead of displaying a list of available classes.

### Debug mode

It's also possible to run your application in debug mode, which allows the console to print comprehensive error messages. 

The first option is to change the `config.bbx` file, found in the `cfg/` directory of your BBj installation. Add the line `SET DEBUG=1` to the file and save your changes.

Additionally, in the Enterprise Manager, you can add the following as a program argument:

`DEBUG`

Completing either of these allows the browser console to print error messages.