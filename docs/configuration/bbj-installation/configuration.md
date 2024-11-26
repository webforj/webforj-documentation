---
title: Install Plugin
sidebar_position: 4
displayed_sidebar: documentationSidebar
---

You can configure webforJ using a project's POM file, which is designed to make deploying an app easy. The following sections outline the various options you can change to achieve a desired result.

## Engine exclusion

When running with `BBjServices`, the `webforj-engine` dependency should be excluded, as the features provided by the engine are already available.

```xml
<dependencies>
  <dependency>
    <groupId>com.webforj</groupId>
    <artifactId>webforj</artifactId>
    <version>${webforj.version}</version>
    <exclusions>
      <exclusion>
        <groupId>com.webforj</groupId>
        <artifactId>webforj-engine</artifactId>
      </exclusion>
    </exclusions> 
  </dependency>
</dependencies>
```

## POM file tags

Tags within the `<configuration>` tag can be changed to configure your app. Editing the following lines in the default POM file that comes with the [`HelloWorldJava`](https://github.com/webforj/HelloWorldJava) starting repository will result in these changes:

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

- **`<deployurl>`** This tag is the URL where the webforJ endpoint for the project installation can be reached. For users running their app locally, a default port of 8888 is used. For users running Docker, the port should be changed to the port that was entered when [configuring the Docker container](./docker#2-configuration).

- **`<classname>`** This tag should contain the package and class name of the app you wish to run. This will be the single class in your project that extends the `App` class and runs from the base URL.

- **`<publishname>`** This tag specifies the name of the app in the published URL. Generally, to run your program, you'll navigate to a URL similar to `http://localhost:8888/webapp/<publishname>`, replacing `<publishname>` with the value in the `<publishname>` tag. Then, the program specified by the `<classname>` tag is run.

- **`<debug>`** The debug tag can be set to true or false, and will determine whether or not the browser's console displays error messages thrown by your program. 

## Running a specific program

There are two ways to run a specific program in your app:

1. Place the program within the `run()` method of the class that extends `App`.
2. Utilze [routing](../../routing/overview) in your webforJ app to give the program a dedicated URL.

## How webforJ selects an entry point

The entry point for an app is determined by the `<classname>` specified in the POM file.
If no entry point is specified in the POM file, the system will start an entry point search.

### Entry point search

1. If there is a single class that extends the `App` class, that will become the entry point.
2. If multiple classes extend `App`, the system checks if one has the `com.webforj.annotation.AppEntry` annotation. The single class annotated with `@AppEntry` will become the entry point.
    :::warning
    If multiple classes are annotated with `@AppEntry`, an exception is thrown, listing all the discovered classes.
    :::

If there are multiple classes that extend `App` and none of them are annotated with `@AppEntry`, an exception is thrown, detailing each subclass.

## Debug mode

It's also possible to run your app in debug mode, which allows the console to print comprehensive error messages. 

The first option is to change the `config.bbx` file, found in the `cfg/` directory of your BBj installation. Add the line `SET DEBUG=1` to the file and save your changes.

Additionally, in the Enterprise Manager, you can add the following as a program argument: `DEBUG`

Completing either of these allows the browser console to print error messages.