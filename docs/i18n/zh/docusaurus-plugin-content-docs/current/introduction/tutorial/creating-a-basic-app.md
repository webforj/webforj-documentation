---
title: Creating a Basic App
sidebar_position: 2
description: Step 1 - Add components to an app.
_i18n_hash: d7385c22706cf76508b7e1971186f88d
---
在 [项目设置](/docs/introduction/tutorial/project-setup) 中，您生成了一个 webforJ 项目。现在是时候为该项目创建主类，并使用 webforJ 组件添加交互式界面。在此步骤中，您将了解：

- 使用 webforJ 和 Spring Boot 的应用程序入口点
- webforJ 和 HTML 元素组件
- 使用 CSS 来样式化组件

完成此步骤会创建 [1-creating-a-basic-app](https://github.com/webforj/webforj-tutorial/tree/main/1-creating-a-basic-app) 的一个版本。

<!-- 在此处插入视频 -->

## 运行应用程序 {#running-the-app}

在您开发应用程序时，可以使用 [1-creating-a-basic-app](https://github.com/webforj/webforj-tutorial/tree/main/1-creating-a-basic-app) 进行比较。要查看应用程序的运行：

1. 导航到包含 `pom.xml` 文件的顶级目录，如果您正在按照 GitHub 上的版本进行操作，该目录为 `1-creating-a-basic-app`。

2. 使用以下 Maven 命令在本地运行 Spring Boot 应用程序：
    ```bash
    mvn
    ```

运行应用程序会自动在 `http://localhost:8080` 打开一个新浏览器。

## 入口点 {#entry-point}

每个 webforJ 应用程序包含一个扩展了 <JavadocLink type="foundation" location="com/webforj/App" code='true'>App</JavadocLink> 的类。本教程及其他发布的 webforJ 项目通常称为 `Application`。该类位于一个以您在 [项目设置](/docs/introduction/tutorial/project-setup) 中使用的 `groupId` 命名的包内：

```
1-creating-a-basic-app
│   .editorconfig
│   .gitignore
│   pom.xml
│   README.md
│
├───.vscode
├───src/main/java
// highlight-next-line
│   └──com/webforj/tutorial
// highlight-next-line
│       └──Application.java
└───target
```

在 `Application` 类中，`SpringApplication.run()` 方法使用配置启动应用程序。各种注解用于应用程序的配置。

```java title="Application.java"
@SpringBootApplication
@BundleEntry("css/card.css")
@AppTheme("system")
@AppProfile(name = "客户应用程序", shortName = "CustomerApp")
public class Application extends App {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
```

### 注解 {#annotations}

[`@SpringBootApplication`](https://docs.spring.io/spring-boot/api/java/org/springframework/boot/autoconfigure/SpringBootApplication.html) 是 Spring Boot 中的核心注解。您将此注解放在主类上，以将其标记为应用程序的起始点。

`@BundleEntry`、`@AppTheme` 和 `@AppProfile` 仅是 <JavadocLink type="foundation" location="com/webforj/annotation/package-summary">webforJ 注解</JavadocLink> 中的一部分，您可以在需要明确设置配置时使用这些注解。

- **`@BundleEntry`** 将文件从 `src/main/frontend` 添加到应用程序的前端捆绑包。在此步骤中，它加载您将在 [使用 CSS 进行样式化](#styling-with-css) 中创建的 CSS 文件。

- **`@AppTheme`** 管理应用程序的视觉主题。如果设置为 `system`，应用程序将自动采用用户首选的主题：`light`、`dark` 或 `dark-pure`。有关创建自定义主题或覆盖默认主题的信息，请参阅 [主题](/docs/styling/themes) 文章。

- **`@AppProfile`** 有助于配置应用程序如何作为 [可安装应用程序](/docs/configuration/installable-apps) 向用户展示。此注解至少需要应用程序的全名 `name` 和在空间有限时使用的 `shortName`。`shortName` 不应超过 12 个字符。

## 创建用户界面 {#creating-a-ui}

要创建用户界面，您需要添加 [HTML 元素组件](/docs/components/html-elements) 和 [webforJ 组件](/docs/components/overview)。现在，您只有一个单页应用程序，因此您将直接将组件添加到 `Application` 类中。
为此，重写 `App.run()` 方法并创建一个 `Frame` 来添加组件。

```java
@Override
public void run() throws WebforjException {
  Frame mainFrame = new Frame();

  // 创建 UI 组件并添加到框架中

}
```

### 使用 HTML 元素 {#using-html-elements}

您可以使用 [HTML 元素组件](/docs/components/html-elements) 向应用程序添加标准 HTML 元素。
创建组件的新实例，然后使用 `add()` 方法将其添加到 `Frame`：

```java
// 创建 UI 元素的容器
Frame mainFrame = new Frame();

// 创建 HTML 组件
Paragraph tutorial = new Paragraph("教程应用程序！");

// 将组件添加到容器中
mainFrame.add(tutorial);
```

### 使用 webforJ 组件 {#webforj-components-and-html-elements}

虽然 HTML 元素对于结构、语义和轻量级 UI 需求很有用，但 [webforJ 组件](/docs/components/overview) 提供了更复杂和动态的行为。

下面的代码添加了一个 [Button](/docs/components/button) 组件，通过 `setTheme()` 方法更改其外观，并添加事件监听器，点击按钮时创建一个 [Message Dialog](/docs/components/option-dialogs/message) 组件。
大多数 webforJ 组件方法修改组件后返回组件本身，因此您可以链式调用多个方法以获得更简洁的代码。

```java
// 创建 UI 元素的容器
Frame mainFrame = new Frame();

// 创建 webforJ 组件
Button btn = new Button("信息");

// 修改 webforJ 组件，并添加事件监听器
btn.setTheme(ButtonTheme.PRIMARY)
  .addClickListener(e -> OptionDialog.showMessageDialog("这是一个教程！", "信息"));

// 将组件添加到容器中
mainFrame.add(btn);
```

## 使用 CSS 进行样式化 {#styling-with-css}

大多数 webforJ 组件都有内置方法来进行常见的样式更改，例如调整大小和主题。

```java
// 使用 CSS 关键字设置框架的宽度
mainFrame.setWidth("fit-content");

// 使用像素设置按钮的最大宽度
btn.setMaxWidth(200);

// 将按钮主题设置为 PRIMARY
btn.setTheme(ButtonTheme.PRIMARY);
```

除了这些方法，您还可以使用 CSS 来样式化您的应用程序。任何组件文档页面的 **样式** 部分提供了有关相关 CSS 属性的具体细节。

webforJ 还配备了一组设计好的 CSS 变量，称为 DWC 令牌。有关如何样式化 webforJ 组件以及如何使用令牌的详细信息，请参阅 [样式](/docs/styling/overview) 文档。

### 将 CSS 添加到前端捆绑包 {#referencing-a-css-file}

最好使用单独的 CSS 文件以保持一切组织良好且易于维护。在 `src/main/frontend/css` 中创建一个名为 `card.css` 的文件，包含以下 CSS 类定义：

```css title="card.css"
.card {
  display: grid;
  gap: var(--dwc-space-l);
  padding: var(--dwc-space-l);
  margin: var(--dwc-space-l) auto;
  border: thin solid var(--dwc-color-default);
  border-radius: 16px;
  background-color: var(--dwc-surface-3);
  box-shadow: var(--dwc-shadow-xs);
}
```

然后，通过在 `Application.java` 中使用 `@BundleEntry("css/card.css")` 将文件添加到前端捆绑包。该路径相对于 `src/main/frontend`。

:::tip 前端捆绑器
教程项目的 Maven 配置在您使用 `mvn` 启动应用程序时运行 webforJ 前端观察者，因此在开发过程中 `src/main/frontend` 下的更改会被重建。要了解更多信息，请参见 [前端捆绑器](/docs/managing-resources/bundler/overview)。
:::

### 为组件添加 CSS 类 {#adding-css-classes-to-components}

您可以使用 `addClassName()` 和 `removeClassName()` 方法动态添加或删除组件的类名。对于本教程，仅使用了一个 CSS 类：

```java
mainFrame.addClassName("card");
```

## 完成的 `Application` {#completed-application}

您的 `Application` 类现在应该看起来类似于以下内容：

```java title="Application.java"
@SpringBootApplication
@BundleEntry("css/card.css")
@AppTheme("system")
@AppProfile(name = "客户应用程序", shortName = "CustomerApp")
public class Application extends App {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Override
  public void run() throws WebforjException {
    Frame mainFrame = new Frame();
    Paragraph tutorial = new Paragraph("教程应用程序！");
    Button btn = new Button("信息");

    btn
      .setTheme(ButtonTheme.PRIMARY)
      .setMaxWidth(200)
      .addClickListener(e ->
        OptionDialog.showMessageDialog("这是一个教程！", "信息")
      );

    mainFrame.setWidth("fit-content").addClassName("card").add(tutorial, btn);
  }
}
```

:::tip 多个页面
对于更复杂的应用程序，您可以将用户界面分成多个页面以更好地组织。此概念将在本教程稍后涵盖的 [路由和组合](/docs/introduction/tutorial/routing-and-composites) 中进行说明。
:::

## 下一步 {#next-step}

在创建一个具有基本用户界面的功能应用程序后，下一步是添加数据模型并在 [处理数据](/docs/introduction/tutorial/working-with-data) 的 `Table` 组件中显示结果。
