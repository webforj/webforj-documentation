---
sidebar_position: 2
title: Routable Apps
description: >-
  Enable webforJ routing with the @Routify annotation to scan packages, manage
  frames, and control browser history.
_i18n_hash: bea0848523a00ddfff8d79265ea699ac
---
在 webforJ 中，路由是一个可选工具。开发人员可以选择使用 webforJ 路由解决方案或使用传统模型进行 `Frame` 操作而不进行深度链接。要启用路由，必须在实现 `App` 的类级别应用 **`@Routify`** 注解。这授予 webforJ 管理浏览器历史记录的权限，响应导航事件，并根据 URL 渲染应用的组件。

:::info
要了解更多有关使用框架、内置组件和自定义组件构建用户界面的信息，请访问 [Building UIs](../building-ui/overview)。
:::

## `@Routify` 注解的目的 {#purpose-of-the-routify-annotation}

**`@Routify`** 使框架能够自动注册路由、管理框架可见性，并定义路由行为，如调试和框架初始化，从而实现应用中的动态和灵活路由。

## `@Routify` 的使用 {#usage-of-routify}

**`@Routify`** 注解应用于主应用类的类级别。它指定要扫描的包集合以寻找路由，并处理其他与路由相关的设置，如框架初始化和可见性管理。

以下是一个基本示例：

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
    // 应用逻辑在这里
  }
}
```

:::tip Routify 默认配置
**`@Routify`** 注解附带合理的默认配置。它假定当前应用定义的包以及所有子包应扫描路由。此外，默认情况下，应用假定只管理一个框架。如果您的应用遵循此结构，则无需为注解提供任何自定义配置。
:::

## `@Routify` 的关键元素 {#key-elements-of-routify}

### 1. **`packages`** {#1-packages}

`packages` 元素定义应扫描哪些包以寻找路由定义。它允许自动发现路由而无需手动注册，从而简化了扩展应用路由系统的过程。

```java
@Routify(packages = {"com.myapp.views"})
```

如果未指定任何包，则使用应用的默认包。

### 2. **`defaultFrameName`** {#2-defaultframename}

此元素指定应用初始化的默认框架名称。框架代表顶级用户界面容器，此设置控制第一个框架的命名和管理方式。

```java
@Routify(defaultFrameName = "MainFrame")
```

默认情况下，如果未明确提供，值设置为 `Routify.DEFAULT_FRAME_NAME`。

### 3. **`initializeFrame`** {#3-initializeframe}

`initializeFrame` 标志决定框架是否应在应用启动时自动初始化第一个框架。将其设置为 `true` 可以简化初始框架的设置。

```java
@Routify(initializeFrame = true)
```

### 4. **`manageFramesVisibility`** {#4-manageframesvisibility}

此元素控制框架在导航期间是否应自动切换可见性。当启用时，匹配的路由会自动显示相应的框架，同时隐藏其他框架，从而确保干净和集中的用户界面。此设置在应用管理多个框架时才相关。

```java
@Routify(manageFramesVisibility = true)
```

### 5. **`debug`** {#5-debug}

`debug` 标志启用或禁用路由调试模式。启用时，路由信息和操作会记录到控制台，以便在开发过程中更轻松地进行调试。

```java
@Routify(debug = true)
```

:::info 路由器调试模式与 webforJ 调试模式
如果路由调试模式设置为 `true` 但 webforJ 调试模式设置为 `false`，则控制台将不会显示调试信息。
:::
