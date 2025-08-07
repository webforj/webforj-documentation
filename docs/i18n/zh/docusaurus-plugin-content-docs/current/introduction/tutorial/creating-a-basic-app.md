---
title: Creating a Basic App
sidebar_position: 2
_i18n_hash: 9bd91b6d733198a2c16c9377029e8162
---
这第一步为客户管理应用程序奠定了基础，通过创建一个简单的交互式界面。这个过程演示了如何设置一个基本的 webforJ 应用程序，只有一个按钮，点击时会打开一个对话框。这是一个直接的实现，介绍了关键组件，并让你感受到 webforJ 的工作方式。

此步骤利用 webforJ 提供的基础应用程序类来定义应用程序的结构和行为。继续学习后面的步骤，将过渡到使用路由来管理多个屏幕的更高级的设置，这些内容在 [Scaling with Routing and Composites](./scaling-with-routing-and-composites) 中介绍。

完成此步骤后，你将拥有一个功能齐全的应用程序，演示了与组件和事件处理的基本交互。在运行应用程序之前：

- 转到 `1-creating-a-basic-app` 目录
- 运行 `mvn jetty:run` 命令

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/tutorials/creating-a-basic-app.mp4" type="video/mp4"/>
  </video>
</div>

## 创建一个 webforJ 应用程序 {#creating-a-webforj-app}

在 webforJ 中，`App` 代表定义和管理项目的中心枢纽。每个 webforJ 应用程序开始时会创建一个扩展基础 `App` 类的类，该类充当核心框架，用于：

- 管理应用程序生命周期，包括初始化和终止。
- 处理路由和导航（如果启用）。
- 定义应用程序的主题、区域设置和其他整体配置。
- 提供与环境和组件交互的基本实用工具。

### 扩展 `App` 类 {#extending-the-app-class}

在此步骤中，创建一个名为 `DemoApplication.java` 的类，并扩展 `App` 类。

```java title="DemoApplication.java"
public class DemoApplication extends App {
  @Override
  public void run() {
    // 核心应用程序逻辑将在此处
  }
}
```

:::tip 关键配置属性

在此演示应用程序中，`webforj.conf` 文件配置了以下两个基本属性：

- **`webforj.entry`**：指定充当项目主入口点的扩展 `App` 的类的完全限定名称。在本教程中，将其设置为 `com.webforj.demos.DemoApplication` 以避免初始化时的歧义。
  ```hocon
  webforj.entry = com.webforj.demos.DemoApplication
  ```
- **`webforj.debug`**：启用调试模式，以便在开发过程中查看详细日志和错误。确保在进行此教程时将其设置为 `true`：
  ```hocon
  webforj.debug = true
  ```

有关其他配置选项的更多详细信息，请参见 [Configuration Guide](../../configuration/overview)。
:::

### 重写 `run()` 方法 {#overriding-the-run-method}

确保项目的正确配置后，重写 `App` 类中的 `run()` 方法。

`run()` 方法是 webforJ 中应用程序的核心。它定义了应用程序初始化后发生的事情，是应用程序功能的主要入口点。通过重写 `run()` 方法，可以实现创建和管理应用程序用户界面及其行为的逻辑。

:::tip 使用路由
在应用程序中实现路由时，无需重写 `run()` 方法，因为框架会自动处理路由的初始化和初始 `Frame` 的创建。在基本路由解析后调用 `run()` 方法，确保在执行任何逻辑之前，应用程序的导航系统已完全初始化。本教程将在 [步骤 3](scaling-with-routing-and-composites) 中更深入地探讨路由的实现。更多信息也可在 [Routing Article](../../routing/overview) 中找到。
:::

```java title="DemoApplication.java"
public class DemoApplication extends App {
  @Override
  public void run() throws WebforjException {
    // 应用程序逻辑
  }
}
```

## 添加组件 {#adding-components}

在 webforJ 中，组件是应用程序用户界面的构建块。这些组件代表应用程序 UI 的独立部分，例如按钮、文本字段、对话框或表格。

你可以将 UI 视为一个组件树，其中 `Frame` 作为根节点。添加到 `Frame` 的每个组件都成为这棵树中的一个分支或叶子，为应用程序的整体结构和行为做出贡献。

:::tip 组件目录
请参见 [此页面](../../components/overview)，了解在 webforJ 中提供的各种组件列表。
:::

### 应用程序 `Frame` {#app-frame}

webforJ 中的 `Frame` 类表示应用程序中的一个非嵌套的顶级窗口。`Frame` 通常充当 UI 组件的主要容器，是构建用户界面的基本构建块。每个应用程序至少以一个 `Frame` 开始，你可以向这些框架添加组件，例如按钮、对话框或表单。

在此步骤中，在 `run()` 方法中创建一个 `Frame` - 之后将向此处添加组件。

```java title="DemoApplication.java"
public class DemoApplication extends App {
  @Override
  public void run() throws WebforjException {
    Frame mainFrame = new Frame();
  }
}
```

### 服务器和客户端组件 {#server-and-client-side-components}

webforJ 中的每个服务器端组件都有一个匹配的客户端 Web 组件。服务器端组件处理逻辑和后端交互，而客户端组件例如 `dwc-button` 和 `dwc-dialog` 管理前端渲染和样式。

:::tip 复合组件

除了 webforJ 提供的核心组件之外，你还可以通过将多个元素组合成一个可重用单元来设计自定义复合组件。该概念将在本教程的此步骤中讨论。有关更多信息，请参见 [Composite Article](../../building-ui/composite-components)。
:::

组件需要添加到实现 <JavadocLink type="foundation" location="com/webforj/concern/HasComponents" code='true' >HasComponents</JavadocLink> 接口的容器类中。`Frame` 是其中一个类 - 在此步骤中，向 `Frame` 添加 `Paragraph` 和 `Button`，这些组件将在浏览器的 UI 中渲染：

```java title="DemoApplication.java"
public class DemoApplication extends App {
  Paragraph demo = new Paragraph("演示应用程序！");
  Button btn = new Button("信息");

  @Override
  public void run() throws WebforjException {
    Frame mainFrame = new Frame();
    btn.setTheme(ButtonTheme.PRIMARY)
        .addClickListener(e -> showMessageDialog("这是一个演示！", "信息"));
    mainFrame.add(demo, btn);
  }
}
```

运行此代码应会显示一个简单的样式按钮，使消息弹出，内容为 "这是一个演示！"

## 使用 CSS 进行样式 {#styling-with-css}

在 webforJ 中，样式设置为你提供完全的灵活性，以设计应用程序的外观。尽管框架内置支持一致的设计和样式，但并不强制特定的样式方法，允许你应用符合应用程序要求的自定义样式。

在 webforJ 中，你可以动态地为组件应用类名以实现条件或交互式样式，使用 CSS 实现一致且可扩展的设计系统，以及注入整个内联或外部样式表。

### 向组件添加 CSS 类 {#adding-css-classes-to-components}

你可以使用 `addClassName()` 和 `removeClassName()` 方法动态地向组件添加或删除类名。这些方法允许你根据应用程序的逻辑控制组件的样式。通过在 `run()` 方法中包含以下代码，为之前创建的 `Frame` 添加 `mainFrame` 类名：

```java
mainFrame.addClassName("mainFrame");
```

### 附加 CSS 文件 {#attaching-css-files}

要为应用程序样式，您可以通过使用资产注释或利用 webforJ <JavadocLink type="foundation" location="com/webforj/Page">资产 API</JavadocLink> 在运行时包含 CSS 文件。[查看这篇文章](../../managing-resources/importing-assets) 以获取更多信息。

例如，@StyleSheet 注释用于包含来自 resources/static 目录的样式。它会自动为指定的文件生成一个 URL，并将其注入到 DOM 中，确保样式应用于你的应用程序。请注意，static 目录外的文件不可访问。

```java title="DemoApplication.java"
@StyleSheet("ws://styles/library.css")
public class DemoApplication extends App {
  @Override
  public void run() {
    // 此处应用程序逻辑
  }
}
```
:::tip Web 服务器 URL
为确保静态文件可访问，它们应放置在 resources/static 文件夹中。要包含静态文件，可以使用 Web 服务器协议构造其 URL。
:::

### 示例 CSS 代码 {#sample-css-code}

在项目中的 `resources > static > css > demoApplication.css` 使用一个 CSS 文件，使用以下 CSS 为应用程序应用一些基本样式。

```css
.mainFrame {
  display: inline-grid;
  gap: 20px;
  margin: 20px;
  padding: 20px;
  border: 1px dashed;
  border-radius: 10px;
}
```

完成后，应将以下注释添加到你的 `App` 类中：

```java title="DemoApplication.java"
@StyleSheet("ws://css/demoApplication.css")
@AppTitle("演示步骤 1")
public class DemoApplication extends App {
```

CSS 样式应用于主 `Frame`，通过以 [网格布局](https://developer.mozilla.org/en-US/docs/Web/CSS/CSS_grid_layout) 的形式排列组件，使界面在视觉上更加有组织，并增加了边距、填充和边框样式。
