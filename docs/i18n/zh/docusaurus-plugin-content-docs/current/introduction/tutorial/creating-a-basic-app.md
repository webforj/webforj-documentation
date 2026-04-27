---
title: Creating a Basic App
sidebar_position: 2
description: Step 1 - Add components to an app.
_i18n_hash: ac74bc5c04bce477a7407c9ff94323a4
---
在[项目设置](/docs/introduction/tutorial/project-setup)中，您生成了一个 webforJ 项目。现在是时候为项目创建主类并使用 webforJ 组件添加交互式界面。 在此步骤中，您将了解：

- 使用 webforJ 和 Spring Boot 的应用程序入口点
- webforJ 和 HTML 元素组件
- 使用 CSS 样式化组件

完成此步骤将创建 [1-creating-a-basic-app](https://github.com/webforj/webforj-tutorial/tree/main/1-creating-a-basic-app) 的版本。

<!-- 在此插入视频 -->

## 运行应用程序 {#running-the-app}

在开发您的应用程序时，您可以将 [1-creating-a-basic-app](https://github.com/webforj/webforj-tutorial/tree/main/1-creating-a-basic-app) 作为比较。要查看应用程序的运行情况：

1. 导航到包含 `pom.xml` 文件的顶级目录，如果您是跟随 GitHub 上的版本，则为 `1-creating-a-basic-app`。

2. 使用以下 Maven 命令在本地运行 Spring Boot 应用程序：
    ```bash
    mvn
    ```

运行应用程序会自动在 `http://localhost:8080` 打开一个新浏览器。

## 入口点 {#entry-point}

每个 webforJ 应用程序包含一个单一的类，该类扩展了 <JavadocLink type="foundation" location="com/webforj/App" code='true'>App</JavadocLink>。对于本教程以及其他已发布的 webforJ 项目，通常称为 `Application`。该类位于一个以您在 [项目设置](/docs/introduction/tutorial/project-setup) 中使用的 `groupId` 命名的包内：

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

在 `Application` 类内部，`SpringApplication.run()` 方法使用配置来启动应用程序。各种注释用于应用程序的配置。

```java title="Application.java"
@SpringBootApplication
@StyleSheet("ws://css/card.css")
@AppTheme("system")
@AppProfile(name = "Customer Application", shortName = "CustomerApp")
public class Application extends App {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
```

### 注释 {#annotations}

[`@SpringBootApplication`](https://docs.spring.io/spring-boot/api/java/org/springframework/boot/autoconfigure/SpringBootApplication.html) 是 Spring Boot 的核心注释。您将此注释放在主类上，以将其标记为应用程序的起始点。

`@StyleSheet`、`@AppTheme` 和 `@AppProfile` 只是您想要明确设置配置时可用的众多 <JavadocLink type="foundation" location="com/webforj/annotation/package-summary">webforJ 注释</JavadocLink> 中的一部分。

- **`@StyleSheet`** 将 CSS 文件嵌入到网页中。有关如何与特定 CSS 文件交互的更多详细信息，请参阅稍后的 [使用 CSS 样式化](#styling-with-css)。

- **`@AppTheme`** 管理应用程序的视觉主题。如果设置为 `system`，则应用程序将自动采用用户的首选主题：`light`、`dark` 或 `dark-pure`。有关创建自定义主题或覆盖默认主题的信息，请参阅 [主题](/docs/styling/themes) 文章。

- **`@AppProfile`** 有助于配置应用程序向用户呈现的方式，作为[可安装的应用程序](/docs/configuration/installable-apps)。至少，这个注释需要一个应用程序的 `name`（全名）和一个 `shortName`（当空间有限时使用）。`shortName` 不应超过 12 个字符。  

## 创建用户界面 {#creating-a-ui}

要创建您的用户界面，您需要添加 [HTML 元素组件](/docs/components/html-elements) 和 [webforJ 组件](/docs/components/overview)。目前，您只有一个单页应用程序，因此您将组件直接添加到 `Application` 类中。 
为此，重写 `App.run()` 方法并创建一个 `Frame` 来添加组件。

```java
@Override
public void run() throws WebforjException {
  Frame mainFrame = new Frame();

  // 创建 UI 组件并添加到框架中

}
```

### 使用 HTML 元素 {#using-html-elements}

您可以使用 [HTML 元素组件](/docs/components/html-elements) 将标准 HTML 元素添加到您的应用程序中。
创建一个组件的新实例，然后使用 `add()` 方法将其添加到 `Frame`：

```java
// 创建 UI 元素的容器
Frame mainFrame = new Frame();

// 创建 HTML 组件
Paragraph tutorial = new Paragraph("Tutorial Application!");

// 将组件添加到容器中
mainFrame.add(tutorial);
```

### 使用 webforJ 组件 {#webforj-components-and-html-elements}

虽然 HTML 元素非常有用于结构、语义和轻量级 UI 需求，但 [webforJ 组件](/docs/components/overview) 提供了更复杂和动态的行为。

下面的代码添加了一个 [Button](/docs/components/button) 组件，使用 `setTheme()` 方法更改其外观，并添加一个事件监听器，在点击该按钮时创建一个 [Message Dialog](/docs/components/option-dialogs/message) 组件。
大多数修改组件的 webforJ 组件方法都返回组件本身，因此您可以链接多个方法以实现更紧凑的代码。

```java
// 创建 UI 元素的容器
Frame mainFrame = new Frame();

// 创建 webforJ 组件
Button btn = new Button("Info");

// 修改 webforJ 组件，并添加事件监听器
btn.setTheme(ButtonTheme.PRIMARY)
  .addClickListener(e -> OptionDialog.showMessageDialog("This is a tutorial!", "Info"));

// 将组件添加到容器中
mainFrame.add(btn);
```

## 使用 CSS 样式化 {#styling-with-css}

大多数 webforJ 组件都有内置的方法，可以进行常见的样式更改，例如大小和主题。

```java
// 使用 CSS 关键字设置 Frame 的宽度
mainFrame.setWidth("fit-content");

// 使用像素设置 Button 的最大宽度
btn.setMaxWidth(200);

// 将 Button 主题设置为 PRIMARY
btn.setTheme(ButtonTheme.PRIMARY);
```

除了这些方法，您还可以使用 CSS 样式化应用程序。任何组件文档页面的 **样式** 部分都有有关相关 CSS 属性的具体细节。

webforJ 还附带了一组设计 CSS 变量，称为 DWC 令牌。有关如何样式化 webforJ 组件以及如何使用令牌的详细信息，请参阅 [样式](/docs/styling/overview) 文档。

### 引用 CSS 文件 {#referencing-a-css-file}

最好有一个单独的 CSS 文件，以保持一切组织和可维护。创建一个名为 `card.css` 的文件，放在 `src/main/resources/static/css` 中，并添加以下 CSS 类定义：

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

然后，通过使用 `@StyleSheet` 注释和 CSS 文件名在 `Application.java` 中引用该文件。对于此步骤，它是 `@StyleSheet("ws://css/card.css")`。

:::tip Web 服务器协议
本教程使用 Web 服务器协议来引用 CSS 文件。要了解更多关于此如何工作的知识，请参阅[管理资源](/docs/managing-resources/overview)。
:::

### 将 CSS 类添加到组件 {#adding-css-classes-to-components}

您可以使用 `addClassName()` 和 `removeClassName()` 方法动态添加或删除组件的类名。在本教程中，只有一个 CSS 类被使用：

```java
mainFrame.addClassName("card");
```

## 完成的 `Application` {#completed-application}

您的 `Application` 类现在应类似于以下内容：

```java title="Application.java"
@SpringBootApplication
@StyleSheet("ws://css/card.css")
@AppTheme("system")
@AppProfile(name = "Customer Application", shortName = "CustomerApp")
public class Application extends App {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Override
  public void run() throws WebforjException {
    Frame mainFrame = new Frame();
    Paragraph tutorial = new Paragraph("Tutorial App!");
    Button btn = new Button("Info");

    btn.setTheme(ButtonTheme.PRIMARY)
        .setMaxWidth(200)
        .addClickListener(e -> OptionDialog.showMessageDialog("This is a tutorial!", "Info"));

    mainFrame.setWidth("fit-content")
        .addClassName("card")
        .add(tutorial, btn);
  }

}
```

:::tip 多个页面
对于一个复杂的应用程序，您可以将 UI 分成多个页面以便更好地组织。此概念将在本教程后面的[路由和组合](/docs/introduction/tutorial/routing-and-composites)中涵盖。
:::

## 下一步 {#next-step}

在创建具有基本用户界面的功能应用后，下一步是添加数据模型并在 [处理数据](/docs/introduction/tutorial/working-with-data) 中的 `Table` 组件中显示结果。
