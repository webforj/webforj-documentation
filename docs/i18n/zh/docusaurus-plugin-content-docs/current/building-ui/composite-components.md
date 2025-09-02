---
sidebar_position: 2
title: Composite Components
sidebar_class_name: updated-content
_i18n_hash: 997bb40968c4f4ede5eccb00c27e5305
---
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/Composite" top='true'/>

`Composite` 组件将现有的 webforJ 组件组合成自包含的、可重用的组件，具有自定义行为。使用它将内部 webforJ 组件封装成可重用的业务逻辑单元，在整个应用程序中重用组件模式，并组合多个组件而不暴露实现细节。

`Composite` 组件与底层绑定组件有着紧密的关联。这使您可以控制用户可以访问哪些方法和属性，而与传统的继承不同，后者将所有内容暴露出来。

如果您需要集成来自其他来源的 Web 组件，请使用专门的替代方案：

- [ElementComposite](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/element/ElementComposite.html)：用于具有类型安全属性管理的 Web 组件
- [ElementCompositeContainer](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/element/ElementCompositeContainer.html)：用于接受插槽内容的 Web 组件

## Usage {#usage}

要定义一个 `Composite` 组件，请扩展 `Composite` 类并指定其管理的组件类型。这将成为您的绑定组件，即包含内部结构的根容器：

```java title="BasicComposite.java"
public class BasicComposite extends Composite<FlexLayout> {

  public BasicComposite() {
    // 访问绑定组件以进行配置
    getBoundComponent()
      .setDirection(FlexDirection.COLUMN)
      .setSpacing("3px")
      .add(new TextField(), new Button("Submit"));
  }
}
```

`getBoundComponent()` 方法提供对您底层组件的访问，使您能够直接配置其属性、添加子组件并管理其行为。

绑定组件可以是任何 [webforJ 组件](../components/overview) 或 [HTML 元素组件](/docs/building-ui/web-components/html-elements)。对于灵活布局，请考虑使用 [`FlexLayout`](../components/flex-layout) 或 [`Div`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/html/elements/Div.html) 作为您的绑定组件。

:::note 组件扩展
切勿直接扩展 `Component` 或 `DwcComponent`。始终使用 `Composite` 的组合模式构建自定义组件。
:::

当您需要更灵活地创建和管理绑定组件时，可以覆盖 `initBoundComponent()`，例如使用带参数的构造函数，而不是默认的无参数构造函数。当绑定组件需要将组件传递给其构造函数而不是在之后添加时，请使用此模式。

```java title="CustomFormLayout.java"
public class CustomFormLayout extends Composite<FlexLayout> {
 private TextField nameField;
 private TextField emailField;
 private Button submitButton;

 @Override
 protected FlexLayout initBoundComponent() {
   nameField = new TextField("Name");
   emailField = new TextField("Email");
   submitButton = new Button("Submit");

   FlexLayout layout = new FlexLayout(nameField, emailField, submitButton);
   layout.setDirection(FlexDirection.COLUMN);
   layout.setSpacing("10px");

   return layout;
 }
}
```

## Component lifecycle {#component-lifecycle}

webforJ 自动处理 `Composite` 组件的所有生命周期管理。通过使用 `getBoundComponent()` 方法，可以在构造函数中处理大多数自定义行为，包括添加子组件、设置属性、基本布局设置和事件注册。

```java
public class UserDashboard extends Composite<FlexLayout> {
 private TextField searchField;
 private Button searchButton;
 private Div resultsContainer;

 public UserDashboard() {
   initializeComponents();
   setupLayout();
   configureEvents();
 }

 private void initializeComponents() {
   searchField = new TextField("Search users...");
   searchButton = new Button("Search");
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
   // 搜索逻辑在这里
 }
}
```

如果您有额外的特定设置或清理要求，您可能需要使用可选的生命周期钩子 `onDidCreate()` 和 `onDidDestroy()`：

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

以下示例演示了一个 Todo 应用，其中每个项都是一个 `Composite` 组件，由一个样式化为开关的 [`RadioButton`](../components/radiobutton) 和一个带文本的 Div 组成：

<ComponentDemo 
path='/webforj/composite?' 
cssURL='https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/resources/static/composite/composite.css'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/composite/CompositeView.java'
height='500px'
/>

## 示例：组件分组 {#example-component-grouping}

有时，您可能希望使用 `Composite` 将相关组件分组到一个单元中，即使可重用性不是主要考虑因素：

<ComponentDemo
path='/webforj/analyticscardcomposite?'
cssURL='https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/resources/static/composite/analyticscomposite.css'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/composite/AnalyticsCardCompositeView.java'
height='500px'
/>
