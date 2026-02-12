---
sidebar_position: 12
title: Route Transitions
sidebar_class_name: new-content
_i18n_hash: 5991e12089a2044ef0fd6b15cae1fb13
---
<JavadocLink type="foundation" location="com/webforj/router/annotation/RouteTransition" top='true'/>

<DocChip chip='since' label='25.11' />
<DocChip chip='experimental' />

路由过渡提供在路由之间导航时的声明性动画过渡。基于 [View Transitions](/docs/advanced/view-transitions) API，向你的路由组件添加 `@RouteTransition` 注解使路由器能够在导航期间自动处理动画生命周期。

:::warning 实验性 API
此 API 自 25.11 起被标记为实验性，可能会在未来的版本中发生更改。API 签名、行为和性能特征可能会修改。
:::

:::info 编程控制
对于更复杂的过渡场景或编程控制，请直接使用 [View Transitions](/docs/advanced/view-transitions) API。
:::

## `@RouteTransition` 注解 {#the-routetransition-annotation}

`@RouteTransition` 注解定义了路由组件在进入或退出视图时的动画方式：

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

两个属性都可以接受任意预定义的过渡类型或自定义字符串值：

| 常量 | 效果 |
|------|------|
| `ViewTransition.NONE` | 无动画 |
| `ViewTransition.FADE` | 旧内容与新内容之间的交叉淡化 |
| `ViewTransition.SLIDE_LEFT` | 内容向左流动（类似前进导航） |
| `ViewTransition.SLIDE_RIGHT` | 内容向右流动（类似后退导航） |
| `ViewTransition.SLIDE_UP` | 内容向上流动 |
| `ViewTransition.SLIDE_DOWN` | 内容向下流动 |
| `ViewTransition.ZOOM` | 旧内容缩小，新内容放大 |
| `ViewTransition.ZOOM_OUT` | 旧内容放大，新内容缩小 |

## 基本用法 {#basic-usage}

将注解添加到任何路由组件以启用过渡：

```java title="InboxView.java"
@Route(value = "inbox", outlet = MainLayout.class)
@RouteTransition(enter = ViewTransition.ZOOM, exit = ViewTransition.SLIDE_RIGHT)
@FrameTitle("收件箱")
public class InboxView extends Composite<FlexLayout> {

  public InboxView() {
    getBoundComponent().add(new H1("收件箱"));
    // ...
  }
}
```

在此示例中：
- 当导航到 `InboxView` 时，组件以缩放动画进入
- 当离开 `InboxView` 时，组件以内容向右流动的方式退出

## 导航流 {#navigation-flow}

在两个路由之间导航时，路由器协调过渡顺序：

1. 离开的组件的 `exit` 动画开始
2. [DOM](/docs/glossary#dom) 变化发生（旧视图被移除，新视图被添加）
3. 进入的组件的 `enter` 动画播放

如果导航到已经显示的相同视图，过渡会被跳过以避免不必要的动画。

:::tip 一致的退出动画
在所有视图中使用相同的退出动画会创建方向一致性。例如，配置所有视图使用 `SLIDE_RIGHT` 退出建立一个统一的“返回”运动模式，使导航行为在任何来源视图中都是可预测的。
:::

## 过渡继承 {#transition-inheritance}

路由从其父路由继承过渡。当一个路由没有 `@RouteTransition` 时，路由器会向上遍历层次结构查找。

```java
@Route
@RouteTransition(enter = ViewTransition.ZOOM)
public class MainLayout extends Composite<AppLayout> {
  // 带有过渡的父布局
}

@Route(value = "/inbox", outlet = MainLayout.class)
public class InboxView extends Composite<FlexLayout> {
  // 从 MainLayout 继承 ZOOM
}

@Route(value = "/sub", outlet = InboxView.class)
public class SubView extends Composite<FlexLayout> {
  // 从 MainLayout（通过 InboxView）继承 ZOOM
}
```

所有子路由在不重复注解的情况下继承相同的动画样式。

### 重写继承的过渡 {#overriding-inherited-transitions}

子路由可以通过定义自己的 `@RouteTransition` 重写继承的过渡：

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
  // 使用 SLIDE_UP/SLIDE_DOWN 重写
}
```

## 共享组件过渡 {#shared-component-transitions}

您可以将路由过渡与共享组件动画结合使用，以创建连接的体验。具有匹配 `view-transition-name` 值的组件在视图之间变形。使用 `setViewTransitionName()` 方法，适用于任何实现了 <JavadocLink type="foundation" location="com/webforj/concern/HasStyle" code='true'>HasStyle</JavadocLink> 接口的组件。

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

当从列表导航到详细视图时，产品缩略图在英雄图像位置变形，同时其余内容通过淡入动画过渡。
