---
sidebar_position: 2  
title: Routable Apps
---

Routing in webforJ is an optional tool. Developers can choose between the webforJ routing solution or a traditional model with `Frame` manipulation and without deep linking. To enable routing, the **`@Routify`** annotation must be applied at the level of a class implementing `App`. This grants webforJ the authority to manage browser history, respond to navigation events, and render the app’s components based on the URL.

:::info
To learn more about building UIs using frames, built-in, and custom components, visit the [Building UIs](../building-ui/basics) section.
:::

## Purpose of the `@Routify` Annotation

**`@Routify`** enables the framework to automatically register routes, manage frame visibility, and define routing behaviors such as debugging and frame initialization, allowing for dynamic, flexible routing in the app.

## Usage of `@Routify`

The **`@Routify`** annotation is applied at the class level of the main app class. It specifies the set of packages to scan for routes and handles other routing-related settings such as frame initialization and visibility management.

Here’s a basic example:

```java
@Routify(
  packages = {"com.myapp.views"},
  defaultFrameName = "MainFrame",
  initializeFrame = true,
  manageFramesVisibility = false,
  debug = true
)
public class MyApp extends App {

  @Override
  public void run() {
    // Application logic goes here
  }
}
```

:::tip Routify Default Configurations
The **`@Routify`** annotation comes with reasonable default configurations. It assumes that the current package where the app is defined, along with all its subpackages, should be scanned for routes. Additionally, it assumes the app manages only one frame by default. If your app follows this structure, there's no need to provide any custom configurations to the annotation.
:::

## Key elements of `@Routify`

### 1. **`packages`**

The `packages` element defines which packages should be scanned for route definitions. It enables automatic discovery of routes without manual registration, streamlining the process of expanding the app routing system.

```java
@Routify(packages = {"com.myapp.views"})
```

If no packages are specified, the default package of the app is used.

### 2. **`defaultFrameName`**

This element specifies the name of the default frame that the app initializes. Frames represent top-level UI containers, and this setting controls how the first frame is named and managed.

```java
@Routify(defaultFrameName = "MainFrame")
```

By default, if not explicitly provided, the value is set to `Routify.DEFAULT_FRAME_NAME`.

### 3. **`initializeFrame`**

The `initializeFrame` flag determines whether the framework should automatically initialize the first frame when the app starts. Setting this to `true` simplifies the initial frame setup.

```java
@Routify(initializeFrame = true)
```

### 4. **`manageFramesVisibility`**

This element controls whether the framework should automatically toggle the visibility of frames during navigation. When enabled, the matched route automatically shows the corresponding frame while hiding others, ensuring a clean and focused UI. This setting is only relevant when your app is managing multiple frames.

```java
@Routify(manageFramesVisibility = true)
```

### 5. **`debug`**

The `debug` flag enables or disables routing debug mode. When enabled, routing information and actions are logged to the console for easier debugging during development. 

```java
@Routify(debug = true)
```

:::info Router Debug Mode and webforJ Debug Mode  
If router debug mode is set to `true` but webforJ debug mode is set to `false`, no debugging information will be displayed in the console.  
:::

