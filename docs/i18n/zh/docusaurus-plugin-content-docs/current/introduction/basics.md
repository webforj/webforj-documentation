---
title: App Basics
sidebar_position: 3
_i18n_hash: ad73702df52f27ebff7e226bb75e3a6a
---
一旦在您的项目中设置好 webforJ 及其依赖项，您就可以创建应用程序结构。本文将介绍基本 webforJ 应用程序的关键元素，特别关注`Application`和`HomeView`类，这些是`webforj-archetype-hello-world`启动项目中的基础类。

## 主要应用程序类: `Application.java` {#main-app-class-applicationjava}

`Application` 类作为您的 webforJ 应用程序的入口点，设置基本配置和路由。首先注意类的声明和注解。

该类扩展了 webforJ 的核心 `App` 类，使其被识别为 webforJ 应用程序。各种注解配置应用程序的主题、标题和路由。

```java
@Routify(packages = "com.samples.views")
@AppTitle("webforJ Hello World")
@StyleSheet("ws://app.css")
public class Application extends App {
}
```

- `@Routify`: 指定 webforJ 扫描 `com.samples.views` 包以查找路由组件。
- `@AppTitle`: 定义在应用程序浏览器标签上显示的标题。
- `@StyleSheet`: 链接外部 CSS 文件 `app.css`，允许为应用程序提供自定义样式。

`Application` 类不包含任何额外方法，因为配置是通过注解设置的，webforJ 处理应用程序初始化。

设置好 `Application.java` 后，应用程序现在配置了标题和指向视图包的路由。接下来，`HomeView` 类的概述将提供有关应用程序运行时显示内容的洞察。

### 发现一个 `App` {#discovering-an-app}

在 webforJ 中，强制限制一个单一的 <JavadocLink type="foundation" location="com/webforj/App" code='true'>App</JavadocLink>，这将所有错误处理责任转移到 Java 端，使开发人员对错误管理拥有完全控制权。

在 webforJ 引导过程中，所有扩展 <JavadocLink type="foundation" location="com/webforj/App" code='true'>com.webforj.App</JavadocLink> 的类都会被扫描。如果发现多个应用程序，系统会查找 <JavadocLink type="foundation" location="com/webforj/annotation/AppEntry" code='true'>com.webforj.annotation.AppEntry</JavadocLink> 注解。如果发现的类中有任何被注解为 <JavadocLink type="foundation" location="com/webforj/annotation/AppEntry" code='true'>@AppEntry</JavadocLink>，则第一个遇到的被视为入口点。

- 如果一个类被注解为 `@AppEntry`，那么该类被选为入口点。
- 如果多个类被注解为 `@AppEntry`，则会抛出异常，并列出所有发现的类。
- 如果没有类被注解且仅找到一个 `App` 的子类，则选定该类为入口点。
- 如果没有类被注解且找到多个 `App` 的子类，则会抛出异常，详细列出每个子类。

:::tip 错误处理
有关 webforJ 中错误处理的更多信息，请参见 [这篇文章](../advanced/error-handling)。
:::

## 主要视图类: `HomeView.java` {#main-view-class-homeviewjava}

`HomeView` 类定义了一个简单的视图组件，作为应用程序的主页。它显示一个字段和一个按钮，用于问候用户输入的名称。

### 类声明和注解 {#class-declaration-and-annotations}

`HomeView` 扩展 `Composite<FlexLayout>`，使其能够作为一个可重用组件，由 [`FlexLayout`](../components/flex-layout) 组件组成。 [`@Route("/")`](../routing/overview) 使其成为应用程序的根路由。

```java
@Route("/")
public class HelloWorldView extends Composite<FlexLayout> {

  private FlexLayout self = getBoundComponent();
  private TextField hello = new TextField("What is your name?");
  private Button btn = new Button("Say Hello");

  public HelloWorldView(){
    self.setDirection(FlexDirection.COLUMN);
    self.setMaxWidth(300);
    self.setStyle("margin", "1em auto");

    btn.setTheme(ButtonTheme.PRIMARY)
        .addClickListener(e -> 
          Toast.show("Welcome to webforJ Starter " + hello.getValue() + "!", Theme.GRAY));

    self.add(hello, btn);
  }
}
```

### 组件初始化 {#component-initialization}

在类内部，初始化和声明了多个 UI 元素：

```java
private FlexLayout self = getBoundComponent();
private TextField hello = new TextField("What is your name?");
private Button btn = new Button("Say Hello");
```

- `self`: 使用 [`FlexLayout`](../components/flex-layout) 的主要布局组件，配置为元素的容器。该元素使用 `getBoundComponent()` 方法来存储类包含的主要 `FlexLayout`。
- `hello`: 一个标签为 `What is your name?` 的 [`TextField`](../components/fields/textfield)，供用户输入其名称。
- `btn`: 一个主要样式的 [`Button`](../components/button)，标签为 `Say Hello`。

### 布局配置 {#layout-configuration}

布局 `(self)` 被配置了一些关键样式属性：

- `FlexDirection.COLUMN` 将元素垂直堆叠。
- `setMaxWidth(300)` 将宽度限制为 300 像素，以获得紧凑布局。
- `setStyle("margin", "1em auto")` 使布局居中，并在周围添加边距。

### 向布局添加组件 {#adding-components-to-the-layout}
最后，hello 文本字段和 btn 按钮通过调用 `self.add(hello, btn)` 添加到 [`FlexLayout`](../components/flex-layout) 容器中。这个排列定义了视图的结构，使表单既具有交互性又在视觉上居中。

## 美化应用程序 {#styling-the-app}

`styles.css` 文件为您的 webforJ 应用程序提供了自定义样式。该 CSS 文件在 Application 类中通过 [`@StyleSheet`](../managing-resources/importing-assets#importing-css-files) 注解引用，允许应用程序将样式应用于应用程序内的组件。

该文件位于项目的 `resources/static` 目录中，可以通过 web 服务器 URL `ws://app.css` 进行引用。
