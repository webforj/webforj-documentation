---
sidebar_position: 4
title: Composing Components
description: >-
  Combine webforJ components into reusable units by extending Composite,
  configuring the bound component, and overriding initBoundComponent.
_i18n_hash: 96d22d0dc6ba882867ca35edcf1edcca
---
<JavadocLink type="foundation" location="com/webforj/component/Composite" top='true'/>

`Composite` 组件将现有的 webforJ 组件组合成自包含、可重用的组件，具有自定义行为。可以使用它将内部 webforJ 组件包装成可重用的业务逻辑单元，在整个应用程序中重用组件模式，并组合多个组件而不暴露实现细节。

`Composite` 组件与底层绑定组件有着紧密的关联。这使您能够控制用户可以访问的方法和属性，与传统的继承不同，后者将一切暴露出来。

如果您需要集成来自其他来源的网络组件，请使用专业的替代方案：

- [ElementComposite](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/element/ElementComposite.html)：用于具有类型安全属性管理的网络组件
- [ElementCompositeContainer](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/element/ElementCompositeContainer.html)：用于接受插槽内容的网络组件

<AISkillTip skill="webforj-creating-components" />

## 使用 {#usage}

要定义一个 `Composite` 组件，请扩展 `Composite` 类并指定它管理的组件类型。这成为您的绑定组件，它是包含您内部结构的根容器：

```java title="BasicComposite.java"
public class BasicComposite extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public BasicComposite() {
    // 访问绑定组件以进行配置
    self.setDirection(FlexDirection.COLUMN)
      .setSpacing("3px")
      .add(new TextField(), new Button("提交"));
  }
}
```

`getBoundComponent()` 方法提供对您的底层组件的访问，允许您直接配置其属性、添加子组件和管理其行为。

绑定组件可以是任何 [webforJ 组件](/docs/components/overview) 或 [HTML 元素组件](/docs/components/html-elements)。对于灵活的布局，请考虑使用 [`FlexLayout`](/docs/components/flex-layout) 或 [`Div`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/html/elements/Div.html) 作为您的绑定组件。

:::note 组件扩展
切勿直接扩展 `Component` 或 `DwcComponent`。始终使用 `Composite` 组合模式来构建自定义组件。
:::

当您需要在创建和管理绑定组件时获得更大的灵活性时，可以重写 `initBoundComponent()`，例如使用带参数的构造函数而不是默认的无参数构造函数。当绑定组件需要将组件传递给其构造函数而不是事后添加时，请使用此模式。

```java title="CustomFormLayout.java"
public class CustomFormLayout extends Composite<FlexLayout> {
 private TextField nameField;
 private TextField emailField;
 private Button submitButton;

 @Override
 protected FlexLayout initBoundComponent() {
   nameField = new TextField("姓名");
   emailField = new TextField("电子邮件");
   submitButton = new Button("提交");

   FlexLayout layout = new FlexLayout(nameField, emailField, submitButton);
   layout.setDirection(FlexDirection.COLUMN);
   layout.setSpacing("10px");

   return layout;
 }
}
```

## 组件生命周期 {#component-lifecycle}

webforJ 自动处理 `Composite` 组件的所有生命周期管理。通过使用 `getBoundComponent()` 方法，可以在构造函数中处理大多数自定义行为，包括添加子组件、设置属性、基本布局设置和事件注册。

```java
public class UserDashboard extends Composite<FlexLayout> {
 private final FlexLayout self = getBoundComponent();
 private TextField searchField;
 private Button searchButton;
 private Div resultsContainer;

 public UserDashboard() {
   initializeComponents();
   setupLayout();
   configureEvents();
 }

 private void initializeComponents() {
   searchField = new TextField("搜索用户...");
   searchButton = new Button("搜索");
   resultsContainer = new Div();
 }

 private void setupLayout() {
   FlexLayout searchRow = new FlexLayout(searchField, searchButton);
   searchRow.setAlignment(FlexAlignment.CENTER);
   searchRow.setSpacing("8px");

   getBoundComponent()
     .setDirection(FlexDirection.COLUMN)
     .add(searchRow, resultsContainer);
 }

 private void configureEvents() {
   searchButton.onClick(event -> performSearch());
 }

 private void performSearch() {
   // 搜索逻辑在此
 }
}
```

如果您有其他特定的设置或清理要求，您可能需要使用可选生命周期钩子 `onDidCreate()` 和 `onDidDestroy()`：

```java
public class DataVisualizationPanel extends Composite<Div> {
 private Interval refreshInterval;

 @Override
 protected void onDidCreate(Div container) {
   // 初始化需要 DOM 附加的组件
   refreshInterval = new Interval(5.0, event -> updateData());
   refreshInterval.start();
 }

 @Override
 protected void onDidDestroy() {
   // 清理资源
   if (refreshInterval != null) {
     refreshInterval.stop();
   }
 }

 private void updateData() {
   // 数据更新逻辑
 }
}
```

如果您需要在组件附加到 DOM 后执行任何操作，请使用 `whenAttached()` 方法：

```java title="InteractiveMap.java"
public class InteractiveMap extends Composite<Div> {
  public InteractiveMap() {
    setupMapContainer();

    whenAttached().thenAccept(component -> {
      initializeMapLibrary();
      loadMapData();
    });
  }
}
```

## 示例 `Composite` 组件 {#example-composite-component}

以下示例演示了一个待办事项应用程序，其中每个项目都是一个 `Composite` 组件，由一个样式为开关的 [`RadioButton`](../components/radiobutton) 和一个文本的 Div 组成：

<ComponentDemo
path='/webforj/composite'
files={[
  'src/main/java/com/webforj/samples/views/composite/CompositeView.java',
  'src/main/frontend/composite/composite.css',
]}
height='500px'
/>

## 示例：组件分组 {#example-component-grouping}

有时，您可能希望使用 `Composite` 将相关组件组合到一个单元中，即使可重用性不是主要关注点：

<ComponentDemo
path='/webforj/analyticscardcomposite'
files={[
  'src/main/java/com/webforj/samples/views/composite/AnalyticsCardCompositeView.java',
  'src/main/frontend/composite/analyticscomposite.css',
]}
height='550px'
/>
