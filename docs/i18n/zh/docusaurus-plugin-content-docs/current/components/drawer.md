---
title: Drawer
sidebar_position: 35
sidebar_class_name: updated-content
_i18n_hash: a19d1b8c8e0b74cecee529e86649d449
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-drawer" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="drawer" location="com/webforj/component/drawer/Drawer" top='true'/>

`Drawer` 组件在 webforJ 中创建一个从屏幕边缘滑出的面板，显示额外内容而不离开当前视图。它通常用于侧边导航、过滤菜单、用户设置或需要临时出现的紧凑通知，而不会干扰主界面。

当多个 `Drawers` 打开时，它们会自动堆叠，使其成为空间受限界面的灵活选择。

下面的示例展示了这种行为在 [`AppLayout`](../components/app-layout) 组件中的效果。通过汉堡菜单触发的导航抽屉集成在 [`AppLayout`](../components/app-layout) 中，而底部的欢迎弹出窗口使用独立的 `Drawer` 实例。两者共存并独立堆叠，展示了 `Drawers` 如何在布局组件中集成或作为独立元素使用。

<AppLayoutViewer path='/webforj/drawerwelcome?' mobile='true'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerWelcomeView.java'
cssURL='/css/drawer/drawerWelcome.css'
/>

## Autofocus

`Drawer` 组件支持自动聚焦，当 `Drawer` 打开时，会自动将焦点设置到第一个可聚焦元素。这通过直接将注意力集中在第一个可操作元素上，提高了可用性。

<ComponentDemo
path='/webforj/drawerautofocus?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerAutoFocusView.java'
height='600px'
/>

<!-- 示例 -->

## Label {#label}

`setLabel()` 方法可以提供对 `Drawer` 内部内容的有意义描述。当设置了标签后，辅助技术如屏幕阅读器可以宣读它，帮助用户在未查看其视觉内容的情况下理解 `Drawer` 的用途。

```java
Drawer drawer = new Drawer();
drawer.setLabel("任务管理器");
```

:::tip 描述性标签
使用简洁且描述性强的标签来反映 `Drawer` 的用途。当更具体的名称可以使用时，避免使用“菜单”或“面板”等通用术语。
:::

## Size

要控制 `Drawer` 的大小，请为 CSS 自定义属性 `--dwc-drawer-size` 设置值。这将设置 `Drawer` 左/右放置的宽度或上下放置的高度。

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

要防止 `Drawer` 变得过大，请结合使用 `--dwc-drawer-max-size`：

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

## Placement

`setPlacement()` 方法控制 `Drawer` 在视口中的出现位置。

可用的放置选项：

<!-- vale off -->
- **顶部**: 将抽屉放置在视口的顶部边缘。
- **顶部中心**: 将抽屉水平居中对齐于视口的顶部。
- **底部**: 将抽屉放置在视口的底部。
- **底部中心**: 将抽屉水平居中于视口底部。
- **左侧**: 将抽屉沿视口的左边缘放置。
- **右侧**: 将抽屉沿视口的右边缘放置。
<!-- vale on -->

<ComponentDemo
path='/webforj/drawerplacement?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerPlacementView.java'
height='600px'
/>

## 事件处理

`Drawer` 组件会发出生命周期事件，可用于在其开启或关闭状态变化时触发应用逻辑。

支持的事件：

- `DrawerOpenEvent`: 在抽屉完全打开时触发。
- `DrawerCloseEvent`: 在抽屉完全关闭时触发。

您可以向这些事件附加监听器，以在 `Drawer` 状态变化时运行逻辑。

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

`Drawer` 组件在不干扰当前视图的情况下提供额外内容。此示例在底部中心放置一个抽屉，包含一个可滚动的联系人列表。

每个联系人显示一个头像、姓名、位置和用于快速访问详细信息或通信的操作按钮。这种方法非常适合构建紧凑的工具，如联系人选择器、设置面板或通知。

<ComponentDemo
path='/webforj/drawercontact?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerContactView.java'
cssURL='https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/resources/css/drawer/drawerContact.css'
height='600px'
/>

## 示例：任务管理器

此示例使用 `Drawer` 作为任务管理器。您可以添加任务、检查它们，并清除已完成的任务。`Drawer` 底部包括与任务列表交互的表单控件，且“添加任务” [`Button`](../components/button) 在达到 50 个任务时会禁用自己。

<ComponentDemo
path='/webforj/drawertask?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerTaskView.java'
height='600px'
/>

## 样式 {#styling}

<TableBuilder name="Drawer" />
