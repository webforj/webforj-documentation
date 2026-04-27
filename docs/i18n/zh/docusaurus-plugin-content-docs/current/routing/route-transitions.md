---
sidebar_position: 12
title: Route Transitions
_i18n_hash: 98050ac6a061f4dc3728af3888aa44b0
---
<JavadocLink type="foundation" location="com/webforj/router/annotation/RouteTransition" top='true'/>

<DocChip chip='since' label='25.11' />
<DocChip chip='experimental' />

路由过渡在路由之间导航时提供声明性的动画过渡。基于 [View Transitions](/docs/advanced/view-transitions) API，将 `@RouteTransition` 注解添加到您的路由组件可以让路由器在导航过程中自动处理动画生命周期。

<ExperimentalWarning />

:::info 编程控制
对于更复杂的过渡场景或编程控制，直接使用 [View Transitions](/docs/advanced/view-transitions) API。
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

| 属性    | 描述                               |
|---------|------------------------------------|
| `enter` | 该视图出现时应用的动画            |
| `exit`  | 该视图离开时应用的动画            |

两个属性都可以接受任何预定义的过渡类型或自定义字符串值：

| 常量                           | 效果                          |
|--------------------------------|-------------------------------|
| `ViewTransition.NONE`          | 无动画                        |
| `ViewTransition.FADE`          | 旧内容和新内容之间的交叉淡出  |
| `ViewTransition.SLIDE_LEFT`    | 内容向左流动（如前进导航）    |
| `ViewTransition.SLIDE_RIGHT`   | 内容向右流动（如后退导航）    |
| `ViewTransition.SLIDE_UP`      | 内容向上流动                  |
| `ViewTransition.SLIDE_DOWN`    | 内容向下流动                  |
| `ViewTransition.ZOOM`          | 旧内容缩小，新内容放大        |
| `ViewTransition.ZOOM_OUT`      | 旧内容放大，新内容缩小        |

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
- 当导航到 `InboxView` 时，组件以缩放动画进入
- 当导航离开 `InboxView` 时，组件以内容向右流动的方式退出

## 导航流 {#navigation-flow}

在两条路由之间导航时，路由器协调过渡序列：

1. 退出组件的 `exit` 动画开始
2. 发生 [DOM](/docs/glossary#dom) 变化（旧视图被移除，新视图被添加）
3. 进入组件的 `enter` 动画播放

如果导航到已经显示的同一视图，则跳过过渡以避免不必要的动画。

:::tip 一致的退出动画
在所有视图中使用相同的退出动画创建方向一致性。例如，配置所有视图以 `SLIDE_RIGHT` 退出，建立统一的“返回”运动模式，使导航行为可预测，无论源视图如何。
:::

## 过渡继承 {#transition-inheritance}

路由从其父路由继承过渡。当路由没有 `@RouteTransition` 时，路由器会向上遍历层次结构以寻找一个。

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
  // 通过 InboxView 从 MainLayout 继承 ZOOM
}
```

所有子路由在不重复注解的情况下继承相同的动画样式。

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
  // 重写为 SLIDE_UP/SLIDE_DOWN
}
```

## 共享组件过渡 {#shared-component-transitions}

您可以将路由过渡与共享组件动画结合起来，以创建相关联的体验。具有匹配 `view-transition-name` 值的组件在视图之间形变。使用可用于实现 <JavadocLink type="foundation" location="com/webforj/concern/HasStyle" code='true'>HasStyle</JavadocLink> 接口的任何组件的 `setViewTransitionName()` 方法。

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

当从列表导航到详细视图时，产品缩略图在其余内容以淡入动画过渡的同时，变形为英雄图像的位置。
