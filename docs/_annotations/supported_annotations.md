---
sidebar_position: 1
---

# Annotations

Webforj supports the use of various annotations for the implementation of various behaviors. The following annotations are currently supported:

| Annotation | Description |
|------------|-------------|
|`@AppTitle`| Set the application title. This annotation can only be used in the App class or extending classes.|
|`@AppTheme`| Sets the application's theme. This annotation can only be used in the App class or extending classes.|
|`@AppMeta`| Sets one or more meta tags. This annotation can only be used in the App class or extending classes.|
|`@AppDarkTheme`| Sets the name of the dark theme to be used by the application when the current theme is system. Can only be used in the App class or extending classes.|
|`@AppLightTheme`| Sets the name of the light theme to be used by the application when the current theme is system. Can only be used in the App class or extending classes.|
|`@AppAttribute`| Sets one or more attributes to the application, either to the html tag, or to a different target by using a selector.|
|`@StyleSheet`| Sets one or more stylesheets to be loaded by the application. This annotation is for use with external stylesheets. Duplication can be avoided by providing an ID.|
|`@InlineStyleSheet`| Inlines one or more stylesheets to be loaded by the application. This annotation accepts a local path to a file as a string, or a string with the desired styles. Bundling can be achieved by providing an ID.|
|`@JavaScript`| Sets one or more JavaScript scripts to be loaded by the application. This annotation is for use with external JavaScript. Duplications can be avoided by providing an ID.|
|`@InlineJavaScript`| Inlines one or more JavaScript scripts for the application. The annotation accepts a local path to a file, or a string with the content of the JavaScript. |
|`@Link`| Sets one or more links to be injected into the application, such as font links. |

<br/>
Annotations should be placed before class, method and field declarations. The following code snippet is an example of how to use the above annotations in ebforj:

```java
@AppTitle("My app title")
@AppTheme("system")
@AppDarkTheme("dark-pure")
public class HelloWorld extends App {

  @Override
  public void run() throws DwcException {
    msgbox("Hello, World!");
  }
}

```