---
title: App Basics
sidebar_position: 3
_i18n_hash: eb456b4bb94bf617f33f8aa8425ad97f
---
一旦在您的项目中设置了 webforJ 及其依赖项，您就可以开始创建应用程序结构。本文将介绍基本 webforJ 应用程序的关键元素，特别关注 `Application` 和 `HomeView` 类，这些是 `webforj-archetype-hello-world` 启动项目中的基础类。

## 主应用类：`Application.java` {#main-app-class-applicationjava}

`Application` 类作为您的 webforJ 应用程序的入口点，设置了基本的配置和路由。首先，注意类的声明和注释。

该类扩展了 webforJ 的核心 `App` 类，使其被识别为 webforJ 应用程序。各种注释配置了应用程序的主题、标题和路由。

```java
@Routify(packages = "com.samples.views")
@AppTitle("webforJ Hello World")
@StyleSheet("ws://app.css")
public class Application extends App {
}
```

- `@Routify`：指定 webforJ 应扫描 `com.samples.views` 包中的路由组件。
- `@AppTitle`：定义在应用程序浏览器选项卡上显示的标题。
- `@StyleSheet`：链接一个外部 CSS 文件 `app.css`，允许为应用程序应用自定义样式。

`Application` 类没有包含任何其他方法，因为通过注释设置了配置，webforJ 处理应用程序的初始化。

随着 `Application.java` 的设置，应用程序现在已配置好标题和指向视图包的路由。接下来，概述 `HomeView` 类，以了解在运行应用程序时显示的内容。

### 发现一个 `App` {#discovering-an-app}

webforJ 中实施了单个 <JavadocLink type="foundation" location="com/webforj/App" code='true'>App</JavadocLink> 限制，这将所有错误处理责任转移到 Java 端，并赋予开发人员对错误管理的完全控制。

在 webforJ 引导过程中，所有扩展 <JavadocLink type="foundation" location="com/webforj/App" code='true'>com.webforj.App</JavadocLink> 的类将被扫描。如果找到多个应用程序，系统会查找 <JavadocLink type="foundation" location="com/webforj/annotation/AppEntry" code='true'>com.webforj.annotation.AppEntry</JavadocLink> 注释。如果发现的类中有使用 <JavadocLink type="foundation" location="com/webforj/annotation/AppEntry" code='true' >@AppEntry</JavadocLink> 注释的类，则第一个遇到的类被认为是入口点。

- 如果类使用 `@AppEntry` 注释，则该类被选为入口点。
- 如果多个类使用 `@AppEntry` 注释，将引发异常，并列出所有发现的类。
- 如果没有类被注释并且只发现一个 `App` 的子类，则该类被选为入口点。
- 如果没有类被注释并且发现多个 `App` 的子类，则会引发异常，详细说明每个子类。

:::tip 错误处理
有关 webforJ 中如何处理错误的更多信息，请参见 [这篇文章](../advanced/error-handling)。
:::

## 主视图类：`HomeView.java` {#main-view-class-homeviewjava}

`HomeView` 类定义了一个简单的视图组件，作为应用程序的主页。它显示了一个字段和一个按钮，用于问候用户输入的名称。

### 类声明和注释 {#class-declaration-and-annotations}

`HomeView` 扩展了 `Composite<FlexLayout>`，使其能够作为一个可重用的组件，由 [`FlexLayout`](../components/flex-layout) 组件组成。 [`@Route("/")`](../routing/overview) 使其成为应用程序的根路由。

```java
@Route("/")
public class HelloWorldView extends Composite<FlexLayout> {

  private FlexLayout self = getBoundComponent();
  private TextField hello = new TextField("您叫什么名字？");
  private Button btn = new Button("说你好");

  public HelloWorldView(){
    self.setDirection(FlexDirection.COLUMN);
    self.setMaxWidth(300);
    self.setStyle("margin", "1em auto");

    btn.setTheme(ButtonTheme.PRIMARY)
        .addClickListener(e -> 
          Toast.show("欢迎来到 webforJ 启动 " + hello.getValue() + "!", Theme.GRAY));

    self.add(hello, btn);
  }
}
```

### 组件初始化 {#component-initialization}

在类内部，初始化并声明了几个 UI 元素：

```java
private FlexLayout self = getBoundComponent();
private TextField hello = new TextField("您叫什么名字？");
private Button btn = new Button("说你好");
```

- `self`：使用 [`FlexLayout`](../components/flex-layout) 的主要布局组件，配置为元素的容器。此元素使用 `getBoundComponent()` 方法存储该类包含的主要 `FlexLayout`。
- `hello`：带有标签 `您叫什么名字？` 的 [`TextField`](../components/fields/textfield)，供用户输入他们的名字。
- `btn`：样式为主要样式的 [`Button`](../components/button)，标签为 `说你好`。

### 布局配置 {#layout-configuration}

布局 `(self)` 配置了几个关键样式属性：

- `FlexDirection.COLUMN` 使元素垂直堆叠。
- `setMaxWidth(300)` 将宽度限制为 300 像素，以获得紧凑的布局。
- `setStyle("margin", "1em auto")` 将布局居中，并在周围添加边距。

### 将组件添加到布局 {#adding-components-to-the-layout}

最后，通过调用 `self.add(hello, btn)` 将问候文本框和按钮添加到 [`FlexLayout`](../components/flex-layout) 容器中。此排列定义了视图的结构，使表单既具有交互性又视觉上居中。

## 样式应用程序 {#styling-the-app}

`styles.css` 文件为您的 webforJ 应用程序提供了自定义样式。该 CSS 文件在应用程序类中使用 [`@StyleSheet`](../managing-resources/importing-assets#importing-css-files) 注释进行引用，允许应用程序将样式应用于应用程序内的组件。

该文件位于项目的 `resources/static` 目录中，可以通过 Web 服务器 URL `ws://app.css` 进行引用。
