---
sidebar_position: 2
title: Assets Protocols
---

webforJ supports custom assets protocols that enable structured and efficient resource access. These protocols abstract the complexities of static and dynamic resource retrieval, ensuring that assets are fetched and integrated seamlessly within the app.

## The webserver protocol

The **`ws://`** protocol allows you to retrieve assets hosted in the static folder of a webforJ app. All files located within the app classpath `src/main/resources/static` are directly accessible from the browser. For example, if you have a file named **css/app.css** inside **resources/static**, it can be accessed at: **`/static/css/app.css`**  

The **ws://** protocol removes the need to hardcode the `static` prefix in your resource URLs, safeguarding against changed prefixes depending on the deployment context. If the web app is deployed under a context other than the root, such as **/mycontext**, the URL for **css/app.css** would be: **`/mycontext/static/css/app.css`**  

:::tip Configuring the static prefix
You can control the `static` prefix using the [webforj configuration](../configuration/properties#configuration-options) option `webforj.assetsDir`. This setting specifies the route name used to serve static files, while **the physical folder remains named `static`**. it's particularly useful if the default static route conflicts with a route in your app, as it allows you to change the route name without renaming the folder.
:::

You can use the <JavadocLink type="foundation" location="com/webforj/utilities/Assets" code='true'>Assets</JavadocLink> utilities class to resolve a given web server URL. This can be useful if you have a custom component that needs to support that protocol.

```java
String url = Assets.resolveWebServerUrl("ws://js/app.js");
```

## The context protocol

The context protocol maps to resources within the app's classpath at `src/main/resources`. Some resource API methods and annotations support this protocol to read the content of a file located in the resources folder and send its content to the browser. For example, the `InlineJavaScript` annotation allows reading the content of a JavaScript file from the resources folder and inlining it on the client side.

For instance:

```java
String content = Assets.contentOf(
  Assets.resolveContextUrl("context://data/customers.json")
);
```

## The icons protocol

The **`icons://`** protocol provides a dynamic approach to icon management, making it efficient and flexible for generating and serving icons in [installable apps](../configuration/installable-apps). This protocol allows you to generate icons on the fly based on the requested filename and parameters, eliminating the need for pre-generated icons in many cases.

```java
Img icon = new Img("icons://icon-192x192.png")
```

### Base icon

When an icon is requested using the `icons://` protocol, a base image is dynamically derived from the requested icon filename. This ensures consistency in icon design and allows for automatic fallback to a default image if no base icon is provided.

- **Example 1:** Request: `/icons/icon-192x192.png` → Base icon: `resources/icons/icon.png`
- **Example 2:** Request: `/icons/icon-different-192x192.png` → Base icon: `resources/icons/icon-different.png`

If a base image doesn’t exist for the requested icon, the default webforJ logo is used as a fallback.

:::tip `webforj.iconsDir`
By default, webforJ serves icons from the `resources/icons/` directory. You can change the endpoint name by setting the `webforj.iconsDir` property in the webforJ configuration file. This only changes the URL endpoint used to access the icons, not the actual folder name. The default endpoint is `icons/`. 
:::

### Overriding Icons

You can override specific icon sizes by placing pre-generated images in the `resources/icons/` directory. This provides greater control over the appearance of icons when certain sizes or styles need to be customized.

- **Example:** If `resources/icons/icon-192x192.png` exists, it will be served directly instead of being dynamically generated.

### Customizing icon appearance

The `icons://` protocol supports additional parameters that allow you to customize the appearance of dynamically generated icons. This includes options for padding and background color.

- **Padding**: The `padding` parameter can be used to control the padding around the icon. Accepted values range from `0`, meaning no padding, to `1`, meaning maximum padding.
  - **Example:** `/icons/icon-192x192.png?padding=0.3`
  
- **Background Color**: The `background` parameter allows you to set the background color of the icon. It supports the following values:
  - **`transparent`**: No background color.
  <!-- vale off -->
  - **Hexadecimal Color Codes**: Custom background colors (e.g., `FFFFFF` for white).
  <!-- vale on -->
  - **`auto`**: Attempts to detect the background color of the icon automatically.

  For instance: 
  
  - **Example 1:** `/icons/icon-192x192.png?background=000000`
  - **Example 2:** `/icons/icon-192x192.png?background=auto`

