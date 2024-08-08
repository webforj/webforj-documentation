---
sidebar_position: 1
displayed_sidebar: documentationSidebar
id: "styling_intro"
pagination_prev: "components/tabbed-pane"
---

# Styling

## Application Theme

webforJ ships three themes by default, as well as with the ability to create your own, custom theme. The default themes are light, dark and dark-pure. 

### Default Themes

To change the theme of your application, you can use the `@AppTheme` annotation or the `App.setTheme()` method. When using this annotation, the theme name should be one of the following: `system`, `light`, `dark`, `dark-pure` or the name of a custom theme.

```java
@AppTheme("dark-pure")
//or
App.setTheme("dark-pure");
```

### System Theme

The `system` theme option allows for a theme to be set based on the operating system user preferences. Operating systems that support a dark mode or dark theme typically have an option to activate it somewhere in the settings. 

- **On Windows 10:** The theme option can be found within the "Colors" section under "Choose your Color". For a more detailed guide, see [this resource.](https://blogs.windows.com/windowsexperience/2016/08/08/windows-10-tip-personalize-your-pc-by-enabling-the-dark-theme/)

- **On macOS:** In the "System Preferences" section under "General", there are options to change the appearance. For more details, see [this resource.](https://support.apple.com/en-us/HT208976)

- **On iOS:** Change the "Appearance" selection within the "Display & Brightness" option in the settings menu.

- **On Android:** Toggle the "Dark Theme" switch under the "Display" option within the settings menu

If the system's themes have been customized and your application is set to use the system theme, it will be necessary to inform your application of the customized theme names. To do this, use the `@AppLightTheme` and `@AppDarkTheme` annotations:

```java
@AppLightTheme("my-light-theme")
@AppDarkTheme("my-dark-theme")
```

## Using Custom CSS

Styling your webforJ application can be accomplished in various ways, which include:

- Using the `setStyle()` method on various elements in the webforJ API

- Using `App` class API methods to apply a stylesheet for your class

- Using annotations

### Using the `setStyle()` method

Components which extend the `AbstractDwcComponent`, including those shipped with the engine, will have access to the `setStyle()` method, which takes two strings: the desired style attribute to be changed, and the desired value. 

```java
Button exampleButton = new Button();
exampleButton.setStyle("opacity","0.75");
```

### Using the `App` class's methods

The webforJ App class contains functionality to allow stylesheets to be applied to the application: `addStyleSheet()` and `addInlineStyleSheet()`.

The `addStyleSheet()` method will inject an external style sheet from a URL into a page, such as stylesheets hosted on CDNs. 

```java
App.getPage().addStyleSheet("https://www.w3schools.com/w3css/4/w3.css")
```

This annotation also supports the `webserver://` protocol, and will by default point to the folder of the running application under jetty's htdocs (`bbx/htdocs/myapp`).

```java
addStyleSheet("webserver://static/css/style.css");
```

The `addInlineStyleSheet()` method is used when a developer wants to include a local stylesheet. By default, the resources directory of a project will be used. For example, a file called `styles.css` located in `src/main/resources/css` would by loaded by the following code:

```java
App.getPage().addInlineStyleSheet("context://css/styles.css");
```

### Using annotations

Similar to the App class's methods, webforJ contains annotations which allow for the use of external and local stylesheets as well. 

:::caution
These annotations are only available to classes which extend the `Component` class, as annotations are processed only when a class is attached to a panel. For classes in which this is not the case, use the previously mentioned `App` class's method.
:::

To annotate the injection of an external CSS file, use the `@StyleSheet()` annotation.

```java
@StyleSheet("https://www.w3schools.com/w3css/4/w3.css")
//or
@StyleSheet(value="https://www.w3schools.com/w3css/4/w3.css")

```

It is also possible to apply local stylesheets with annotations. Use the `@InlineStyleSheet()` annotation to include an external file. As with the App's API method, the root directory used by this annotation will be the resources directory of a project. A file called `styles.css` located in `src/resources/css` would by loaded by the following code:

```java
@InlineStyleSheet("context://css/style.css")
```

These annotations have the required parameter `value`, and optional parameters `id`, `top`, and `once`, and `attributes`.

The required `value` parameter is the CSS content to be injected into the page as a style element. **If no other parameters are given, it is not necessary to add **`value=`** to the annotation.**

The `id` parameter accepts a string which creates a unique resource ID, causing resources with the same ID to be bundled together. This means that the resources will be injected in the same style element in the page.

The `top` parameter accepts a boolean value, is false by default, and specifies that the style should be injected into the top level of the window.

The `once` parameter accepts a boolean value, is false by default, and specifies whether the style should be injected into the page once only. This is useful when creating custom components that come with their own style sheets - multiple instances of the component can be used, but the style sheet will only be injected once. 

:::tip Important!
In order to use the `once` parameter properly, ensure that you have also assigned a unique id using the `id` parameter.
:::

The `attributes` parameter is empty by default, and can be specified either as a string in the `attr=value,attr=value` format, or as a hashMap containing key/value pairs. These attributes are a set of [attributes](https://developer.mozilla.org/en-US/docs/Web/HTML/Element/style) to be added to the style element.

:::info
It is also possible to use Java Text Blocks to create chunks of CSS that can be applied via HTML class attribute to the class itself. This styling can be applied with either the App API method or using annotations. 

```java showLineNumbers
@InlineStyleSheet(value = /* css */ """
   .panel{
        display: flex;
        flex-direction: column;
        align-items: center;
        gap: 1rem;
        padding: 2rem;
        height: inherit;
   } 
""")

public class Demo extends App{
    @Override
    public void run() throws DwcException{
        AppPanel panel = new AppPanel();
        panel.addClassName("panel");
    }
}
```


If you're using VS code, an extension that provides syntax highlighting [can be found here](https://marketplace.visualstudio.com/items?itemName=BEU.vscode-java-html):

<img src='https://github.com/webforj/vscode-java-html/raw/HEAD/docs/demo.png'/>

:::


## Component Theme and Expanse

Many core webforJ components come packaged with various Themes and Expanses for easy styling. More about the specific component Themes and Expanses can be found in the [webforJ Components](/docs/components/home) section.
