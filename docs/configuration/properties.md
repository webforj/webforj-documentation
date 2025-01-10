---
title: Property Configuration
sidebar_position: 1
---

# Configurating webforJ properties

To successfully deploy and run a webforJ app, a couple key configuration files are required: `webforJ.conf` and `web.xml`. Each of these files controls different aspects of the app's behavior, from entry points and debug settings to servlet mappings.

## Configuring `webforj.conf`

The `webforJ.conf` file is a core configuration file in webforJ, specifying app settings like entry points, debug mode, and client-server interaction. The file is written in [HOCON format](https://github.com/lightbend/config/blob/master/HOCON.md), and should be located in the `resources` directory.

### Example `webforj.conf` File

```Ini
# This configuration file is in HOCON format:
# https://github.com/lightbend/config/blob/master/HOCON.md

webforj.entry = com.webforj.samples.Application
webforj.debug = true
webforj.reloadOnServerError = on
webforj.clientHeartbeatRate = 1s
```

### Configuration options

<!-- Fixed many vale issues, but turning it off for the table since these descriptions come directly from comments in the code -->
<!-- vale off -->

| Property                       | Explanation                                                                                                                                                                            | Default         |
|--------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------|
| **`webforj.entry`**            | Defines the app’s entry point by specifying the fully qualified name of the class that extends `webforj.App`. If no entry point is defined, webforJ will automatically scan the classpath for classes that extend `webforj.App`. If multiple classes are found, an error will occur. When a package includes more than one potential entry point, setting this explicitly is required to prevent ambiguity, or alternatively, the `AppEntry` annotation can be used to specify the entry point at runtime. | `null`          |
| **`webforj.debug`**            | Enables debug mode. In debug mode, webforJ will print additional information to the console and show all exceptions in the browser. Debug mode is disabled by default. | `null`          |
| **`webforj.reloadOnServerError`** | When using hot redeploy, the whole WAR file will swapped. If the client tries to send a request to the server while it is restarting, an error occurs. This setting allows the client to attempt a page reload if the server is temporarily unavailable, hoping it will be back online shortly. This only applies to development environments and only handles errors specific to hot redeployment, not other types of errors. | `on`            |
| **`webforj.clientHeartbeatRate`** | Sets the interval at which the client pings the server to see if it's still alive. This helps maintain communication. For development, set this to a shorter interval, for example `8s`, to quickly detect server availability. Don't set this under 50 seconds in production to avoid excessive requests. | `50s`           |
| **`webforj.components`**       | When specified, the base path determines where DWC components are loaded from. By default, components are loaded from the server hosting the app. However, setting a custom base path allows components to be loaded from an alternate server or CDN. For example, to load components from jsdelivr.com, set the base path to: https://cdn.jsdelivr.net/gh/webforj/dwc-dist@1.0.0-${webforj.version} It’s important that the loaded components are compatible with the version of the webforJ framework in use; otherwise, the app may not function as expected. This setting is disregarded when using a standard BBj installation without the engine. For a standard BBj installation, the setting can be managed with the `!COMPONENTS` STBL. | `null`          |
| **`webforj.locale`**           | Defines the locale for the app, determining language, region settings, and formats for dates, times, and numbers. | `null` 
| **`webforj.assetsDir`**           | Specifies the route name used to serve static files, while the physical folder name remains `static`. This configuration is helpful if the default `static` route conflicts with a route defined in your app, allowing you to change the route name without renaming the folder itself. | `static`          |
| **`webforj.stringTable`**      | A map of key-value pairs used to store strings for use in the app. Useful for storing app messages or labels. More information on `StringTable` can be found [here](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/environment/StringTable.html). | `{}`            |
| **`webforj.fileUpload.accept`** | Specifies the allowed file types for file uploads. By default, all file types are allowed. Supported formats include MIME types like `image/*`, `application/pdf`, `text/plain`, or file extensions like `*.txt`. When using a standard BBj installation, this setting is disregarded and managed through `fileupload-accept.txt`. | `[]`            |
| **`webforj.fileUpload.maxSize`** | Defines the maximum file size allowed for file uploads, in bytes. By default, there is no limit. When using a standard BBj installation, this setting is disregarded and managed through `fileupload-accept.txt`. | `null`          |
| **`license.cfg`**              | Configures the directory for the license configuration. By default, it's the same as the webforJ configuration directory, but this can be customized if needed. | `"."`           |

<!-- vale on -->

## Configuring `web.xml`

The web.xml file is an essential configuration file for Java web apps, and in webforJ, it defines important settings like the servlet configuration, URL patterns, welcome pages. This file should be located in the `WEB-INF` directory of your project’s deployment structure.

| Setting                                 | Explanation                                                                                                                                                                                   | Default Value               |
| --------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | --------------------------- |
| **`<display-name>`**                    | Sets the display name for the web app, typically derived from the project name. This name appears in app servers' management consoles.                                                        | `${project.name}`           |
| **`<servlet>` and `<servlet-mapping>`** | Defines the `WebforjServlet`, the core servlet for handling webforJ requests. This servlet is mapped to all URLs (`/*`), making it the main entry point for web requests.                     | `WebforjServlet`            |
| **`<load-on-startup>`**                 | Specifies that `WebforjServlet` should be loaded when the app starts. Setting this to `1` ensures that the servlet loads immediately, which improves initial request handling.                | `1`                         |
<!-- | **`<filter>` and `<filter-mapping>`**   | Configures the `WebforjCacheControlFilter` to control caching for JavaScript files. This filter prevents caching of `.js` files by setting specific HTTP headers, improving development flow. | `WebforjCacheControlFilter` | -->

<!-- ## Configuring `blsclient.conf` -->
