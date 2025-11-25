---
title: 创建一个基本应用
sidebar_position: 2
_i18n_hash: c59ff0def84230ed79877cba3d5e5aa4
---
这第一步为客户管理应用程序奠定了基础，通过创建一个简单的交互界面。 这演示了如何设置一个基本的webforJ应用程序，该应用程序具有一个单一按钮，单击后会打开一个对话框。 这是一个简单明了的实现，介绍了关键组件，并让您感受到webforJ的工作方式。

这一步骤利用了webforJ提供的基础应用程序类来定义应用程序的结构和行为。 持续进行后面的步骤将转向使用路由管理多个屏幕的更高级设置，具体介绍见[使用路由和复合组件进行扩展](./scaling-with-routing-and-composites)。

到这一步结束时，您将拥有一个正常工作的应用程序，演示了与组件和事件处理的基本交互。 要运行应用程序：

- 转到`1-creating-a-basic-app`目录
- 运行`mvn jetty:run`命令

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/tutorials/creating-a-basic-app.mp4" type="video/mp4"/>
  </video>
</div>

## 创建一个webforJ应用 {#creating-a-webforj-app}

在webforJ中，`App`代表了定义和管理您项目的中心枢纽。每个webforJ应用程序都始于创建一个类，该类扩展了基础的`App`类，后者作为核心框架来：

- 管理应用程序生命周期，包括初始化和终止。
- 处理路由和导航（如果启用）。
- 定义应用程序的主题、区域设置和其他整体配置。
- 提供与环境和组件交互的基本实用工具。

### 扩展`App`类 {#extending-the-app-class}

在此步骤中，创建一个名为`DemoApplication.java`的类，并扩展`App`类。

```java title="DemoApplication.java"
public class DemoApplication extends App {
  @Override
  public void run() {
    // 核心应用程序逻辑将在此处进行
  }
}
```

:::tip 关键配置属性

在此演示应用程序中，`webforj.conf`文件配置了以下两个基本属性：

- **`webforj.entry`**：指定扩展`App`的类的完全限定名称，该类充当您项目的主要入口点。 对于本教程，将其设置为`com.webforj.demos.DemoApplication`以避免初始化时的歧义。
  ```hocon
  webforj.entry = com.webforj.demos.DemoApplication
  ```
- **`webforj.debug`**：启用调试模式，以便在开发过程中获得详细日志和错误可见性。 在进行此教程时，请确保将其设置为`true`：
  ```hocon
  webforj.debug = true
  ```

有关其他配置选项的更多详细信息，请参见[配置指南](../../configuration/overview)。
:::

### 重写`run()`方法 {#overriding-the-run-method}

在确保项目的正确配置后，重写`App`类中的`run()`方法。

`run()`方法是webforJ中应用程序的核心。 它定义了应用程序初始化后发生的事情，并且是应用程序功能的主要入口点。 通过重写`run()`方法，您可以实现创建和管理应用程序用户界面及其行为的逻辑。

:::tip 使用路由
在应用程序中实现路由时，不需要重写`run()`方法，因为框架会自动处理路由的初始化以及初始`Frame`的创建。 在解析基础路由后调用`run()`方法，确保在执行任何逻辑之前，应用程序的导航系统已完全初始化。 本教程将在[第3步](scaling-with-routing-and-composites)中进一步深入探讨实现路由。 [路由文章](../../routing/overview)中也提供了更多信息。
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

在webforJ中，组件是应用程序用户界面的构建模块。 这些组件代表应用程序UI的离散部分，例如按钮、文本字段、对话框或表格。

您可以将UI视为组件树，`Frame`作为根。 每个添加到`Frame`的组件都成为这棵树中的一个分支或叶子，构成了应用程序的整体结构和行为。

:::tip 组件目录
请参见[此页面](../../components/overview)，获取webforJ中提供的各种组件的列表。
:::

### 应用程序`Frame` {#app-frame}

webforJ中的`Frame`类表示应用程序中的一个不可嵌套的顶层窗口。`Frame`通常充当UI组件的主要容器，使其成为构建用户界面的基本构件。每个应用程序至少以一个`Frame`开始，您可以向这些框架添加按钮、对话框或表单等组件。

在此步骤中，在`run()`方法中创建了一个`Frame`——稍后将在这里添加组件。

```java title="DemoApplication.java"
public class DemoApplication extends App {
  @Override
  public void run() throws WebforjException {
    Frame mainFrame = new Frame();
  }
}
```

### 服务器端和客户端组件 {#server-and-client-side-components}

在webforJ中，每个服务器端组件都有一个匹配的客户端网页组件。 服务器端组件处理逻辑和后端交互，而客户端组件如`dwc-button`和`dwc-dialog`则管理前端渲染和样式。

:::tip 复合组件
除了webforJ提供的核心组件外，您还可以通过将多个元素组合成一个可重用的单元来设计自定义复合组件。 本步骤中的教程将涵盖这个概念。 有关更多信息，请参见[复合文章](../../building-ui/composite-components)。
:::

组件需要添加到实现了<JavadocLink type="foundation" location="com/webforj/concern/HasComponents" code='true' >HasComponents</JavadocLink>接口的容器类。`Frame`就是一个这样的类——在此步骤中，向`Frame`添加一个`Paragraph`和一个`Button`，它们将在浏览器的UI中渲染：

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

运行此代码，您应该会看到一个简单的样式按钮，弹出消息说“这是一个演示！”。

## 使用CSS进行样式设计 {#styling-with-css}

在webforJ中，样式设计为您提供了完全的灵活性，以设计应用程序的外观。 虽然框架支持开箱即用的一致设计和样式，但并没有强制特定的样式方法，允许您应用符合应用程序需求的自定义样式。

使用webforJ，您可以动态地为组件应用类名，实现条件或交互样式，使用CSS创建一致且可扩展的设计系统，注入整个内联或外部样式表。

### 向组件添加CSS类 {#adding-css-classes-to-components}

您可以使用`addClassName()`和`removeClassName()`方法动态添加或移除组件的类名。 这些方法允许您根据应用程序逻辑控制组件的样式。 通过在`run()`方法中包括以下代码，将`mainFrame`类名添加到先前创建的`Frame`中：

```java
mainFrame.addClassName("mainFrame");
```

### 附加CSS文件 {#attaching-css-files}

要为应用程序添加样式，您可以通过使用资产注释或在运行时利用webforJ的<JavadocLink type="foundation" location="com/webforj/Page">资产API</JavadocLink>来在项目中包含CSS文件。 [参见此文章](../../managing-resources/importing-assets)以获取更多信息。

例如，@StyleSheet注解用于从resources/static目录包含样式。 它会自动生成指定文件的URL并将其注入到DOM中，确保样式应用于您的应用程序。 请注意，静态目录外的文件无法访问。

```java title="DemoApplication.java"
@StyleSheet("ws://styles/library.css")
public class DemoApplication extends App {
  @Override
  public void run() {
    // 应用程序逻辑在此处执行
  }
}
```
:::tip Web服务器URLs
要确保静态文件可以访问，它们应放在resources/static文件夹中。 要包含静态文件，您可以使用Web服务器协议构造其URL。
:::

### 示例CSS代码 {#sample-css-code}

在您的项目中使用CSS文件位于`resources > static > css > demoApplication.css`，以下CSS用于为应用程序应用一些基本样式。

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

完成后，应将以下注解添加到您的`App`类中：

```java title="DemoApplication.java"
@StyleSheet("ws://css/demoApplication.css")
@AppTitle("演示步骤 1")
public class DemoApplication extends App {
```

CSS样式应用于主`Frame`，通过使用[网格布局](https://developer.mozilla.org/en-US/docs/Web/CSS/CSS_grid_layout)对组件进行安排，并添加边距、内边距及边框样式，以使UI视觉上井然有序。
