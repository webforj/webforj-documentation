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

- **`<deployurl>`** This tag is the URL where the webforJ endpoint for the project installation can be reached. For users running their application locally, a default port of 8888 is used. For users running Docker, the port should be changed to the port that was entered when [configuring the Docker container](../installation/docker.md#2-configuration).

- **`<classname>`** This tag should contain the package and class name of the application you wish to run. This will be the single class in your project that extends the `App` class and runs from the base URL.

- **`<publishname>`** This tag specifies the name of the application in the published URL. Generally, to run your program, you'll navigate to a URL similar to `http://localhost:8888/webapp/<publishname>`, replacing `<publishname>` with the value in the `<publishname>` tag. Then, the program specified by the `<classname>` tag is run.

- **`<debug>`** The debug tag can be set to true or false, and will determine whether or not the browser's console displays error messages thrown by your program. 

### Running a specific program

There are two ways to run a specific program in your app:

1. Place the program within the `run()` method of the class that extends `App`.
2. Utilze [routing](../../docs/routing/overview) in your webforJ app to give the program a dedicated URL.

## Without the installation plugin

:::warning
This is not the recommended method of configuring your application, and should be used only if needed.
:::

### Default class

The default class in an application will be the single class that extends the `App` class. If multiple classes extend `App`, the system looks for the `com.webforj.annotation.AppEntry` annotation. If any of the discovered classes are annotated with @AppEntry, the first one encountered is considered the entry point.

- If a class is annotated with @AppEntry, that class is selected as the entry point.
- If multiple classes are annotated with @AppEntry, an exception is thrown, listing all the discovered classes.
- If no class is annotated and only one subclass of App is found, that class is selected as the entry point.
- If no class is annotated and multiple subclasses of App are found, an exception is thrown, detailing each subclass.

### Debug mode

It's also possible to run your application in debug mode, which allows the console to print comprehensive error messages. 

The first option is to change the `config.bbx` file, found in the `cfg/` directory of your BBj installation. Add the line `SET DEBUG=1` to the file and save your changes.

Additionally, in the Enterprise Manager, you can add the following as a program argument:

`DEBUG`

Completing either of these allows the browser console to print error messages.