---
sidebar_position: 2
title: Routable Apps
_i18n_hash: 889bb5d90fac8315d6b7b1cf766fadea
---
在 webforJ 中，路由是一个可选工具。开发人员可以选择使用 webforJ 路由解决方案或传统模型，后者通过 `Frame` 操作并且不支持深度链接。要启用路由，必须在实现 `App` 的类层级上应用 **`@Routify`** 注解。这使 webforJ 有权管理浏览器历史记录、响应导航事件，并根据 URL 渲染应用程序的组件。

:::info
要了解更多关于使用框架、内置和自定义组件构建用户界面的信息，请访问 [Building UIs](../building-ui/basics) 部分。
:::

## `@Routify` 注解的目的 {#purpose-of-the-routify-annotation}

**`@Routify`** 使框架能够自动注册路由、管理框架可见性，并定义路由行为，例如调试和框架初始化，从而实现应用的动态和灵活路由。

## `@Routify` 的使用 {#usage-of-routify}

**`@Routify`** 注解应用于主应用类的类级别。它指定要扫描路由的包的集合，并处理其他与路由相关的设置，例如框架初始化和可见性管理。

下面是一个基本示例：

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
    // 应用程序逻辑在这里
  }
}
```

:::tip Routify 默认配置
**`@Routify`** 注解提供了合理的默认配置。它假设当前定义应用的包及其所有子包应被扫描以查找路由。此外，默认情况下假设应用仅管理一个框架。如果您的应用遵循此结构，则不需要为注解提供任何自定义配置。
:::

## `@Routify` 的关键元素 {#key-elements-of-routify}

### 1. **`packages`** {#1-packages}

`packages` 元素定义应扫描哪些包以获取路由定义。它使得路由的自动发现成为可能，无需手动注册，从而简化了扩展应用路由系统的过程。

```java
@Routify(packages = {"com.myapp.views"})
```

如果未指定包，则使用应用的默认包。

### 2. **`defaultFrameName`** {#2-defaultframename}

此元素指定应用初始化的默认框架的名称。框架表示顶层 UI 容器，此设置控制第一个框架的命名和管理方式。

```java
@Routify(defaultFrameName = "MainFrame")
```

如果未显式提供，默认情况下，该值设置为 `Routify.DEFAULT_FRAME_NAME`。

### 3. **`initializeFrame`** {#3-initializeframe}

`initializeFrame` 标志决定框架是否应在应用启动时自动初始化第一个框架。将其设置为 `true` 可以简化初始框架设置。

```java
@Routify(initializeFrame = true)
```

### 4. **`manageFramesVisibility`** {#4-manageframesvisibility}

此元素控制框架在导航过程中是否应自动切换框架的可见性。当启用时，匹配的路由将自动显示相应的框架，同时隐藏其他框架，从而确保清晰集中用户界面。此设置仅在您的应用管理多个框架时相关。

```java
@Routify(manageFramesVisibility = true)
```

### 5. **`debug`** {#5-debug}

`debug` 标志启用或禁用路由调试模式。当启用时，路由信息和操作将记录到控制台，以便在开发期间更容易调试。

```java
@Routify(debug = true)
```

:::info 路由器调试模式和 webforJ 调试模式  
如果路由器调试模式设置为 `true`，但 webforJ 调试模式设置为 `false`，则在控制台中不会显示调试信息。  
:::
