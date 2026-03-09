---
title: Drawer
sidebar_position: 35
_i18n_hash: 1ac0b2efc50e748c9fd18f92de8d0e6e
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-drawer" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="drawer" location="com/webforj/component/drawer/Drawer" top='true'/>

`Drawer` 组件在 webforJ 中创建一个从屏幕边缘滑出的面板，展示额外的内容而不离开当前视图。它通常用于侧导航、筛选菜单、用户设置，或需要临时出现而不干扰主界面的紧凑通知。

当多个 `Drawers` 被打开时，它们会自动堆叠，使其成为空间有限界面的灵活选择。

下面的示例展示了这一行为在 [`AppLayout`](../components/app-layout) 组件中的体现。由汉堡菜单触发的导航抽屉是内置于 [`AppLayout`](../components/app-layout) 中，而底部的欢迎弹出窗口则使用一个独立的 `Drawer` 实例。两者共存并独立堆叠，展示了 `Drawers` 如何集成在布局组件中或作为独立元素使用。

<AppLayoutViewer path='/webforj/drawerwelcome?' mobile='true'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerWelcomeView.java'
cssURL='/css/drawer/drawerWelcome.css'
/>

## Autofocus

`Drawer` 组件支持自动聚焦，当 `Drawer` 打开时自动将焦点设置到第一个可聚焦的元素上。这提高了可用性，使用户直接关注第一个可操作的元素。

<ComponentDemo
path='/webforj/drawerautofocus?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerAutoFocusView.java'
height='600px'
/>

<!-- 示例 -->

## Label {#label}

`setLabel()` 方法可以提供 `Drawer` 内部内容的有意义描述。当设置标签时，辅助技术如屏幕阅读器可以宣布它，帮助用户理解 `Drawer` 的目的而不需要查看其视觉内容。

```java
Drawer drawer = new Drawer();
drawer.setLabel("任务管理器");
```

:::tip 描述性标签
使用简明且描述性的标签，反映 `Drawer` 的目的。在可以使用更具体名称时，避免使用 “菜单” 或 “面板” 这样的通用术语。
:::

## Size

要控制 `Drawer` 的大小，请为 CSS 自定义属性 `--dwc-drawer-size` 设置一个值。这设置了 `Drawer` 的宽度（用于左右放置）或高度（用于上下放置）。

您可以使用任何有效的 CSS 单位来定义该值，例如百分比、像素或 vw/vh，使用 Java 或 CSS：

```java
// Java
drawer.setStyle("--dwc-drawer-size", "40%");
```

```css
/* CSS */
dwc-drawer {
  --dwc-drawer-size: 40%;
}
```

为了防止 `Drawer` 变得太大，请同时使用 `--dwc-drawer-max-size`：

```java
// Java
drawer.setStyle("--dwc-drawer-size", "40%");
drawer.setStyle("--dwc-drawer-max-size", "800px");
```

```css
/* CSS */
dwc-drawer {
  --dwc-drawer-size: 40%;
  --dwc-drawer-max-size: 800px;
}
```

## Placement {#placement}

`setPlacement()` 方法控制 `Drawer` 在视口中出现的位置。

可用的放置选项：

<!-- vale off -->
- **TOP**: 将抽屉放置在视口的顶部边缘。
- **TOP_CENTER**: 将抽屉水平居中放置在视口的顶部。
- **BOTTOM**: 将抽屉放置在视口的底部。
- **BOTTOM_CENTER**: 将抽屉水平居中放置在视口的底部。
- **LEFT**: 将抽屉放置在视口的左边缘。
- **RIGHT**: 将抽屉放置在视口的右边缘。
<!-- vale on -->

<ComponentDemo
path='/webforj/drawerplacement?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerPlacementView.java'
height='600px'
/>

## 事件处理

`Drawer` 组件发出生命周期事件，可用于根据其打开或关闭状态的变化触发应用逻辑。

支持的事件：

- `DrawerOpenEvent`: 在抽屉完全打开时触发。
- `DrawerCloseEvent`: 在抽屉完全关闭时触发。

您可以将监听器附加到这些事件上，以便在 `Drawer` 状态变化时运行逻辑。

```java
Drawer drawer = new Drawer();

drawer.addOpenListener(e -> {
  // 处理抽屉打开事件
});

drawer.addCloseListener(e -> {
  // 处理抽屉关闭事件
});
```

## 示例：联系人选择器

`Drawer` 组件在不干扰当前视图的情况下展示额外内容。此示例在底部中心放置一个抽屉，包含一个可滚动的联系人列表。

每个联系人都有头像、姓名、位置和快速访问详细信息或沟通的操作按钮。这种方法非常适合构建紧凑工具，如联系人选择器、设置面板或通知。

<ComponentDemo
path='/webforj/drawercontact?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerContactView.java'
cssURL='https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/resources/css/drawer/drawerContact.css'
height='600px'
/>

## 示例：任务管理器

此示例使用 `Drawer` 作为任务管理器。您可以添加任务，检查完成的任务，并清除已完成的任务。`Drawer` 底部包含表单控件以与任务列表进行交互，当达到 50 个任务时，“添加任务” [`Button`](../components/button) 会禁用自身。

<ComponentDemo
path='/webforj/drawertask?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerTaskView.java'
height='600px'
/>

## Styling {#styling}

<TableBuilder name="Drawer" />
