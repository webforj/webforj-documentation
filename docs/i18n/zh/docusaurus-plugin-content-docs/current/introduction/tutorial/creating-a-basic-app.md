---
title: Creating a Basic App
sidebar_position: 2
description: Step 1 - Add components to an app.
_i18n_hash: 7c98bf3851e1db10d5e0dd68045ea22d
---
在[项目设置](/docs/introduction/tutorial/project-setup)中，您生成了一个webforJ项目。现在是时候为项目创建主类，并使用webforJ组件添加一个交互式界面。在此步骤中，您将了解：

- 使用webforJ和Spring Boot的应用程序入口点
- webforJ和HTML元素组件
- 使用CSS为组件设置样式

完成此步骤后，将创建一个[1-创建基本应用程序](https://github.com/webforj/webforj-tutorial/tree/main/1-creating-a-basic-app)的版本。

<!-- 在此插入视频 -->

## 运行应用程序 {#running-the-app}

在开发您的应用程序时，您可以使用[1-创建基本应用程序](https://github.com/webforj/webforj-tutorial/tree/main/1-creating-a-basic-app)作为对比。要查看应用程序的运行情况：

1. 导航到包含`pom.xml`文件的顶级目录，如果您按照GitHub上的版本操作，则为`1-creating-a-basic-app`。

2. 使用以下Maven命令在本地运行Spring Boot应用程序：
    ```bash
    mvn
    ```

运行应用程序会自动在`http://localhost:8080`打开一个新浏览器。

## 入口点 {#entry-point}

每个webforJ应用程序都包含一个扩展自<JavadocLink type="foundation" location="com/webforj/App" code='true'>App</JavadocLink>的单一类。在本教程以及其他发布的webforJ项目中，该类通常称为`Application`。此类位于一个以您在[项目设置](/docs/introduction/tutorial/project-setup)中使用的`groupId`命名的包内：

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

在`Application`类内部，`SpringApplication.run()`方法使用配置启动应用程序。各种注释用于应用程序的配置。

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

[`@SpringBootApplication`](https://docs.spring.io/spring-boot/api/java/org/springframework/boot/autoconfigure/SpringBootApplication.html)是Spring Boot中的核心注释。您将此注释放在主类上，以将其标记为应用程序的起点。

`@StyleSheet`、`@AppTheme`和`@AppProfile`只是您想要显式设置配置时可用的众多<JavadocLink type="foundation" location="com/webforj/annotation/package-summary">webforJ注释</JavadocLink>中的一部分。

- **`@StyleSheet`**将CSS文件嵌入网页中。有关如何与特定CSS文件互动的更多细节，请稍后在[使用CSS进行样式设置](#styling-with-css)中查看。

- **`@AppTheme`**管理应用程序的视觉主题。如果设置为`system`，应用程序将自动采用用户首选的主题：`light`、`dark`或`dark-pure`。有关创建自定义主题或覆盖默认主题的信息，请参阅[主题](/docs/styling/themes)文章。

- **`@AppProfile`**有助于配置应用程序如何作为[可安装应用](/docs/configuration/installable-apps)向用户呈现。至少，此注释需要应用程序的全名`name`和在空间有限时使用的`shortName`。`shortName`不应超过12个字符。

## 创建用户界面 {#creating-a-ui}

要创建用户界面，您需要添加[HTML元素组件](/docs/components/html-elements)和[webforJ组件](/docs/components/overview)。目前，您只有一个单页面应用程序，因此您将直接将组件添加到`Application`类中。
为此，请重写`App.run()`方法，并创建一个`Frame`来添加组件。

```java
@Override
public void run() throws WebforjException {
  Frame mainFrame = new Frame();

  // 创建UI组件并添加到框架中

}
```

### 使用HTML元素 {#using-html-elements}

您可以使用[HTML元素组件](/docs/components/html-elements)向您的应用程序添加标准HTML元素。
创建组件的新实例，然后使用`add()`方法将其添加到`Frame`：

```java
// 创建UI元素的容器
Frame mainFrame = new Frame();

// 创建HTML组件
Paragraph tutorial = new Paragraph("教程应用程序！");

// 将组件添加到容器
mainFrame.add(tutorial);
```

### 使用webforJ组件 {#webforj-components-and-html-elements}

虽然HTML元素对结构、语义和轻量级UI需求很有用，但[webforJ组件](/docs/components/overview)提供了更复杂和动态的行为。

下面的代码添加了一个[按钮](/docs/components/button)组件，使用`setTheme()`方法更改其外观，并添加一个事件监听器，以便在单击按钮时创建一个[消息对话框](/docs/components/option-dialogs/message)组件。
大多数webforJ组件方法会修改组件并返回组件本身，因此您可以链式调用多个方法以实现更紧凑的代码。

```java
// 创建UI元素的容器
Frame mainFrame = new Frame();

// 创建webforJ组件
Button btn = new Button("信息");

// 修改webforJ组件并添加事件监听器
btn.setTheme(ButtonTheme.PRIMARY)
  .addClickListener(e -> OptionDialog.showMessageDialog("这是一个教程！", "信息"));

// 将组件添加到容器
mainFrame.add(btn);
```

## 使用CSS进行样式设置 {#styling-with-css}

大多数webforJ组件都有内置方法来进行常见的样式更改，例如调整大小和主题。

```java
// 使用CSS关键字设置Frame的宽度
mainFrame.setWidth("fit-content");

// 使用像素设置按钮的最大宽度
btn.setMaxWidth(200);

// 将按钮主题设置为PRIMARY
btn.setTheme(ButtonTheme.PRIMARY);
```

除了这些方法，您还可以使用CSS为应用程序设置样式。任何组件文档页面的**样式设置**部分都有有关相关CSS属性的具体详细信息。

webforJ还带有一组设计好的CSS变量，称为DWC令牌。有关如何为webforJ组件设置样式以及如何使用令牌的详细信息，请参阅[样式设置](/docs/styling/overview)文档。

### 引用CSS文件 {#referencing-a-css-file}

最好有一个单独的CSS文件，以保持一切组织和便于维护。在`src/main/resources/static/css`中创建一个名为`card.css`的文件，包含以下CSS类定义：

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

然后，通过使用`@StyleSheet`注释引用`Application.java`中的文件，并指定CSS文件的名称。对于此步骤，它是`@StyleSheet("ws://css/card.css")`。

:::tip Web服务器协议
本教程使用Web服务器协议来引用CSS文件。要了解更多有关此如何工作的内容，请参阅[管理资源](/docs/managing-resources/overview)。
:::

### 添加CSS类到组件 {#adding-css-classes-to-components}

您可以使用`addClassName()`和`removeClassName()`方法动态地向组件添加或删除类名。对于本教程，只使用一个CSS类：

```java
mainFrame.addClassName("card");
```

## 完成的`Application` {#completed-application}

您的`Application`类现在应类似于以下内容：

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
    Paragraph tutorial = new Paragraph("教程应用程序！");
    Button btn = new Button("信息");

    btn.setTheme(ButtonTheme.PRIMARY)
        .setMaxWidth(200)
        .addClickListener(e -> OptionDialog.showMessageDialog("这是一个教程！", "信息"));

    mainFrame.setWidth("fit-content")
        .addClassName("card")
        .add(tutorial, btn);
  }

}
```

:::tip 多页面
对于更复杂的应用程序，您可以将用户界面划分为多个页面，以便于组织。该概念将在本教程后面在[路由和复合](/docs/introduction/tutorial/routing-and-composites)中覆盖。
:::

## 下一步 {#next-step}

在创建具有基本用户界面的功能性应用程序之后，下一步是添加数据模型并在[处理数据](/docs/introduction/tutorial/working-with-data)中的`Table`组件中显示结果。
