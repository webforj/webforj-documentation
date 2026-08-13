---
title: App Basics
description: >-
  Walk through the Application and HomeView classes of the hello-world archetype
  to see how routing, annotations, and views shape a webforJ app.
sidebar_position: 3
_i18n_hash: 2ebddfe300802013e4376681bc2ccf04
---
一旦在您的项目中设置了webforJ及其依赖项，您就可以开始创建应用程序结构。本文将介绍基本webforJ应用程序的关键元素，特别关注`Application`和`HomeView`类，这是`webforj-archetype-hello-world`起始项目中的基础类。

## 主要应用程序类: `Application.java` {#main-app-class-applicationjava}

`Application`类作为您的webforJ应用程序的入口点，设置基本的配置和路由。首先，请注意该类的声明和注解。

此类扩展了webforJ的核心`App`类，使其被识别为webforJ应用程序。各种注解用于配置应用程序的主题、标题和路由。

```java
@Routify(packages = "com.samples.views")
@AppTitle("webforJ Hello World")
@StyleSheet("ws://app.css")
public class Application extends App {
}
```

- `@Routify`：指定webforJ应扫描`com.samples.views`包中的路由组件。
- `@AppTitle`：定义在应用程序浏览器标签上显示的标题。
- `@StyleSheet`：链接外部CSS文件`app.css`，允许应用程序自定义样式。

`Application`类不包含任何额外的方法，因为配置是通过注解设置的，webforJ负责应用程序的初始化。

配置好`Application.java`后，应用程序现在配置了一个指向视图包的标题和路由。接下来，`HomeView`类的概述提供了在运行应用程序时显示内容的洞察。

### 发现一个`App` {#discovering-an-app}

在webforJ中，强制执行单个<JavadocLink type="foundation" location="com/webforj/App" code='true'>App</JavadocLink>限制，这将所有错误处理职责转移到Java端，并赋予开发人员对错误管理的完全控制权。

在webforJ引导过程中，所有扩展<JavadocLink type="foundation" location="com/webforj/App" code='true'>com.webforj.App</JavadocLink>的类都将被扫描。如果发现多个应用程序，系统将寻找<JavadocLink type="foundation" location="com/webforj/annotation/AppEntry" code='true'>com.webforj.annotation.AppEntry</JavadocLink>注解。如果任何发现的类使用<JavadocLink type="foundation" location="com/webforj/annotation/AppEntry" code='true'>@AppEntry</JavadocLink>注解，则第一个遇到的类被视为入口点。

- 如果类被标注为`@AppEntry`，则该类被选为入口点。
- 如果多个类都标注为`@AppEntry`，则会抛出异常，列出所有发现的类。
- 如果没有类被标注，并且只找到了一个`App`的子类，则该类被选为入口点。
- 如果没有类被标注，并且发现了多个`App`的子类，则会抛出异常，详细说明每个子类。

:::tip 错误处理
有关webforJ中如何处理错误的更多信息，请参见[本文](../advanced/error-handling)。
:::

## 主要视图类: `HomeView.java` {#main-view-class-homeviewjava}

`HomeView`类定义了一个简单的视图组件，作为该应用程序的主页。它显示一个文本框和一个按钮，以问候用户输入的名字。

### 类声明和注释 {#class-declaration-and-annotations}

`HomeView`扩展了`Composite<FlexLayout>`，这使得它可以作为一个可重用组件，由[`FlexLayout`](../components/flex-layout)组件组成。[`@Route("/")`](../routing/overview)使其成为应用程序的根路由。

```java
@Route("/")
public class HelloWorldView extends Composite<FlexLayout> {

  private final FlexLayout self = getBoundComponent();
  private TextField hello = new TextField("What is your name?");
  private Button btn = new Button("Say Hello");

  public HelloWorldView(){
    self.setDirection(FlexDirection.COLUMN);
    self.setMaxWidth(300);
    self.setStyle("margin", "1em auto");

    btn.setTheme(ButtonTheme.PRIMARY)
        .addClickListener(e ->
          Toast.show("欢迎来到webforJ Starter " + hello.getValue() + "!", Theme.GRAY));

    self.add(hello, btn);
  }
}
```

### 组件初始化 {#component-initialization}

在类内部，几个UI元素被初始化和声明：

```java
private final FlexLayout self = getBoundComponent();
private TextField hello = new TextField("What is your name?");
private Button btn = new Button("Say Hello");
```

- `self`：使用[`FlexLayout`](../components/flex-layout)作为主要布局组件，配置为元素的容器。该元素使用`getBoundComponent()`方法来存储该类包含的主要`FlexLayout`。
- `hello`：一个标记为`What is your name?`的[`TextField`](../components/fields/textfield)，供用户输入他们的名字。
- `btn`：一个以主样式标记的[`Button`](../components/button)，标记为`Say Hello`。

### 布局配置 {#layout-configuration}

布局`(self)`配置了几个关键样式属性：

- `FlexDirection.COLUMN`将元素垂直堆叠。
- `setMaxWidth(300)`将宽度限制为300像素，以实现紧凑的布局。
- `setStyle("margin", "1em auto")`在周围居中布局。

### 将组件添加到布局 {#adding-components-to-the-layout}
最后，欢迎文本框和按钮通过调用`self.add(hello, btn)`添加到[`FlexLayout`](../components/flex-layout)容器中。此排列定义了视图的结构，使表单既具互动性又美观居中。

## 样式设置应用程序 {#styling-the-app}

`styles.css`文件为您的webforJ应用程序提供自定义样式。该CSS文件在Application类中使用[`@StyleSheet`](../managing-resources/importing-assets#importing-css-files)注解引用，使应用程序能够将样式应用于应用程序中的组件。

此文件位于项目的`resources/static`目录中，可以通过web服务器URL`ws://app.css`进行引用。
