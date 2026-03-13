---
sidebar_position: 12
title: Route Transitions
sidebar_class_name: new-content
_i18n_hash: 34159c78405282a71774c6148a31f18a
---
<JavadocLink type="foundation" location="com/webforj/router/annotation/RouteTransition" top='true'/>

<DocChip chip='since' label='25.11' />
<DocChip chip='experimental' />

路由转换提供了在不同路由之间导航时的声明式动画效果。基于 [View Transitions](/docs/advanced/view-transitions) API，通过为你的路由组件添加 `@RouteTransition` 注解，路由器可以自动处理导航过程中的动画生命周期。

:::warning 实验性 API
此 API 自 25.11 起被标记为实验性，未来版本可能会发生变化。API 签名、行为和性能特征可能会被修改。
:::

:::info 编程控制
对于更复杂的过渡场景或编程控制，直接使用 [View Transitions](/docs/advanced/view-transitions) API。
:::

## `@RouteTransition` 注解 {#the-routetransition-annotation}

`@RouteTransition` 注解定义了路由组件在进入或退出视图时的动画效果：

```java
@Route
@RouteTransition(enter = ViewTransition.ZOOM, exit = ViewTransition.FADE)
public class DashboardView extends Composite<Div> {
  // 视图实现
}
```

该注解接受以下属性：

| 属性 | 描述 |
|------|------|
| `enter` | 此视图出现时应用的动画 |
| `exit` | 此视图离开时应用的动画 |

两个属性都可以接受预定义的任何过渡类型或自定义字符串值：

| 常量 | 效果 |
|------|------|
| `ViewTransition.NONE` | 无动画 |
| `ViewTransition.FADE` | 旧内容与新内容之间的交叉淡化 |
| `ViewTransition.SLIDE_LEFT` | 内容向左滑动（如前进导航） |
| `ViewTransition.SLIDE_RIGHT` | 内容向右滑动（如后退导航） |
| `ViewTransition.SLIDE_UP` | 内容向上滑动 |
| `ViewTransition.SLIDE_DOWN` | 内容向下滑动 |
| `ViewTransition.ZOOM` | 旧内容缩小，新内容放大 |
| `ViewTransition.ZOOM_OUT` | 旧内容放大，新内容缩小 |

## 基本用法 {#basic-usage}

将注解添加到任何路由组件以启用过渡：

```java title="InboxView.java"
@Route(value = "inbox", outlet = MainLayout.class)
@RouteTransition(enter = ViewTransition.ZOOM, exit = ViewTransition.SLIDE_RIGHT)
@FrameTitle("Inbox")
public class InboxView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public InboxView() {
    self.add(new H1("Inbox"));
    // ...
  }
}
```

在这个例子中：
- 当导航到 `InboxView` 时，组件以缩放动画进入。
- 当离开 `InboxView` 时，组件以内容向右流动的方式退出。

## 导航流程 {#navigation-flow}

在两个路由之间导航时，路由器协调过渡顺序：

1. 正在退出的组件的 `exit` 动画开始。
2. 发生 [DOM](/docs/glossary#dom) 变化（旧视图被移除，新视图被添加）。
3. 正在进入的组件的 `enter` 动画播放。

如果导航到已经显示的相同视图，则跳过过渡，以避免不必要的动画。

:::tip 一致的退出动画
在所有视图中使用相同的退出动画来创建方向一致性。例如，将所有视图配置为以 `SLIDE_RIGHT` 退出，建立统一的“后退”动作模式，使导航行为在任何起始视图中都可预测。
:::

## 过渡继承 {#transition-inheritance}

路由从其父路由继承过渡。当路由没有 `@RouteTransition` 时，路由器沿着层级向上查找以找到一个。

```java
@Route
@RouteTransition(enter = ViewTransition.ZOOM)
public class MainLayout extends Composite<AppLayout> {
  // 带过渡的父布局
}

@Route(value = "/inbox", outlet = MainLayout.class)
public class InboxView extends Composite<FlexLayout> {
  // 从 MainLayout 继承 ZOOM
}

@Route(value = "/sub", outlet = InboxView.class)
public class SubView extends Composite<FlexLayout> {
  // 通过 InboxView 从 MainLayout 继承 ZOOM
}
```

所有子路由共享相同的动画风格，而无需重复注解。

### 重写继承的过渡 {#overriding-inherited-transitions}

子路由可以通过定义自己的 `@RouteTransition` 来重写继承的过渡：

```java
@Route
@RouteTransition(enter = ViewTransition.ZOOM)
public class MainLayout extends Composite<AppLayout> {}

@Route(value = "/inbox", outlet = MainLayout.class)
public class InboxView extends Composite<FlexLayout> {
  // 继承 ZOOM
}

@Route(value = "/settings", outlet = MainLayout.class)
@RouteTransition(enter = ViewTransition.SLIDE_UP, exit = ViewTransition.SLIDE_DOWN)
public class SettingsView extends Composite<FlexLayout> {
  // 用 SLIDE_UP/SLIDE_DOWN 重写
}
```

## 共享组件过渡 {#shared-component-transitions}

你可以将路由过渡与共享组件动画结合起来，以创建相互连接的体验。具有相同 `view-transition-name` 值的组件在视图之间变形。使用 `setViewTransitionName()` 方法，该方法可用于任何实现了 <JavadocLink type="foundation" location="com/webforj/concern/HasStyle" code='true'>HasStyle</JavadocLink> 接口的组件。

```java title="ProductListView.java"
@Route(value = "products", outlet = MainLayout.class)
@RouteTransition(enter = ViewTransition.FADE)
public class ProductListView extends Composite<FlexLayout> {

  private void buildProductCard(Product product) {
      Img thumbnail = new Img(product.getImageUrl());
      thumbnail.setViewTransitionName("product-image-" + product.getId());
      // ...
  }
}
```

```java title="ProductDetailView.java"
@Route(value = "products/:id", outlet = MainLayout.class)
@RouteTransition(enter = ViewTransition.FADE)
public class ProductDetailView extends Composite<FlexLayout> implements DidEnterObserver {

  private Img heroImage = new Img();

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
      String id = parameters.get("id").orElse("");
      heroImage.setViewTransitionName("product-image-" + id);
      // ...
  }
}
```

在从列表导航到详细视图时，产品缩略图会变形到主图像位置，同时其余内容以淡入动画过渡。
