---
title: App Basics
sidebar_position: 3
_i18n_hash: 23f93367391ac7cd42c28bf4cd3640ee
---
一旦您在项目中设置了webforJ及其依赖项，您就准备好创建应用程序结构。本文将逐步介绍基本webforJ应用程序的关键元素，特别关注`Application`和`HomeView`类，它们是`webforj-archetype-hello-world`启动项目中的基础类。

## 主应用程序类：`Application.java` {#main-app-class-applicationjava}

`Application`类作为您的webforJ应用程序的入口点，设置基本配置和路由。首先，注意类的声明和注解。

该类扩展了webforJ的核心`App`类，使其被识别为webforJ应用程序。各种注解配置应用程序的主题、标题和路由。

```java
@Routify(packages = "com.samples.views")
@AppTitle("webforJ Hello World")
@StyleSheet("ws://app.css")
public class Application extends App {
}
```

- `@Routify`：指定webforJ应扫描`com.samples.views`包以查找路由组件。
- `@AppTitle`：定义在应用程序浏览器标签上显示的标题。
- `@StyleSheet`：链接外部CSS文件`app.css`，允许为应用程序应用自定义样式。

`Application`类不包含任何额外的方法，因为配置通过注解设置，webforJ处理应用程序初始化。

配置完`Application.java`后，应用程序现在具有指向视图包的标题和路由。接下来，概述`HomeView`类，以了解应用程序运行时显示的内容。

### 发现一个`App` {#discovering-an-app}

webforJ中强制实施一个单一的<JavadocLink type="foundation" location="com/webforj/App" code='true'>App</JavadocLink>限制，所有错误处理责任转移到Java端，并赋予开发人员对错误管理的完全控制。

在webforJ引导过程期间，所有扩展<JavadocLink type="foundation" location="com/webforj/App" code='true'>com.webforj.App</JavadocLink>的类都会被扫描。如果找到多个应用程序，系统会查找<JavadocLink type="foundation" location="com/webforj/annotation/AppEntry" code='true'>com.webforj.annotation.AppEntry</JavadocLink>注解。如果发现的任何类被标记为<JavadocLink type="foundation" location="com/webforj/annotation/AppEntry" code='true'>@AppEntry</JavadocLink>，则第一个遇到的类被视为入口点。

- 如果一个类被标记为`@AppEntry`，该类将被选为入口点。
- 如果多个类被标记为`@AppEntry`，将抛出异常，列出所有发现的类。
- 如果没有类被标记，且只找到一个`App`的子类，则该类将被选为入口点。
- 如果没有类被标记，且找到多个`App`的子类，则将抛出异常，详细说明每个子类。

:::tip 错误处理
有关webforJ中如何处理错误的更多信息，请参见[本文](../advanced/error-handling)。
:::

## 主视图类：`HomeView.java` {#main-view-class-homeviewjava}

`HomeView`类定义了一个简单的视图组件，作为应用程序的主页。它显示一个字段和一个按钮，以便问候用户输入的姓名。

### 类声明和注解 {#class-declaration-and-annotations}

`HomeView`扩展了`Composite<FlexLayout>`，使其能够作为由[`FlexLayout`](../components/flex-layout)组件组成的可重用组件。[`@Route("/")`](../routing/overview)使其成为应用程序的根路由。

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
          Toast.show("Welcome to webforJ Starter " + hello.getValue() + "!", Theme.GRAY));

    self.add(hello, btn);
  }
}
```

### 组件初始化 {#component-initialization}

在类内部，初始化并声明了几个UI元素：

```java
private final FlexLayout self = getBoundComponent();
private TextField hello = new TextField("What is your name?");
private Button btn = new Button("Say Hello");
```

- `self`：使用[`FlexLayout`](../components/flex-layout)的主要布局组件，配置为元素的容器。该元素使用`getBoundComponent()`方法存储类包含的主要`FlexLayout`。
- `hello`：一个标记为`What is your name?`的[`TextField`](../components/fields/textfield)，供用户输入其姓名。
- `btn`：一个标记为`Say Hello`的主样式[`Button`](../components/button)。

### 布局配置 {#layout-configuration}

布局`(self)`配置了一些关键样式属性：

- `FlexDirection.COLUMN`将元素垂直堆叠。
- `setMaxWidth(300)`限制宽度为300像素，以实现紧凑布局。
- `setStyle("margin", "1em auto")`用边距居中布局。

### 向布局添加组件 {#adding-components-to-the-layout}
最后，通过调用`self.add(hello, btn)`将hello文本字段和btn按钮添加到[`FlexLayout`](../components/flex-layout)容器中。此排列定义了视图的结构，使表单既具有互动性又在视觉上居中。

## 样式应用程序 {#styling-the-app}

`styles.css`文件为您的webforJ应用程序提供自定义样式。这个CSS文件通过[`@StyleSheet`](../managing-resources/importing-assets#importing-css-files)注解在Application类中被引用，这使得应用程序能够将样式应用到应用程序内的组件。

该文件位于项目的`resources/static`目录中，并可以使用web服务器URL `ws://app.css`进行引用。
