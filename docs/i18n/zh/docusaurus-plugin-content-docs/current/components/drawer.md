---
title: Drawer
sidebar_position: 35
_i18n_hash: 51577f27568214c5d39e43b7e6ce42d0
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-drawer" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="drawer" location="com/webforj/component/drawer/Drawer" top='true'/>

`Drawer` 组件在 webforJ 中创建一个滑动面板，它从屏幕边缘出现，显示额外内容而不离开当前视图。它通常用于侧导航、过滤菜单、用户设置或需要临时出现而不干扰主界面的紧凑通知。

<!-- INTRO_END -->

## 堆叠 {#stacking}

当多个抽屉打开时，抽屉会自动堆叠，使其在空间有限的界面中成为灵活的选择。

下面的例子展示了这种行为在 [`AppLayout`](../components/app-layout) 组件内的表现。通过汉堡菜单触发的导航抽屉内置于 [`AppLayout`](../components/app-layout) 中，而底部的欢迎弹出框使用独立的 `Drawer` 实例。这两者共存并独立堆叠，展示了如何在布局组件中集成抽屉或将其用作独立元素。

<AppLayoutViewer path='/webforj/drawerwelcome?' mobile='true'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerWelcomeView.java'
cssURL='/css/drawer/drawerWelcome.css'
/>

## 自动聚焦

`Drawer` 组件支持自动聚焦，当 `Drawer` 打开时，会自动将焦点设置在第一个可聚焦元素上。这提高了可用性，直接将注意力集中在第一个可操作元素上。

<ComponentDemo
path='/webforj/drawerautofocus?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerAutoFocusView.java'
height='600px'
/>

<!-- 示例 -->

## 标签 {#label}

`setLabel()` 方法可以为 `Drawer` 中的内容提供有意义的描述。当设置标签时，辅助技术如屏幕阅读器可以宣布它，帮助用户理解 `Drawer` 的目的，而无需查看其视觉内容。

```java
Drawer drawer = new Drawer();
drawer.setLabel("任务管理器");
```

:::tip 描述性标签
使用简洁且描述性强的标签来反映 `Drawer` 的目的。当可以使用更具体的名称时，避免使用“菜单”或“面板”等通用术语。
:::

## 尺寸

要控制 `Drawer` 的尺寸，设置 CSS 自定义属性 `--dwc-drawer-size` 的值。这设置了用于左/右放置的 `Drawer` 宽度或用于上/下放置的高度。

您可以使用任何有效的 CSS 单位来定义值，例如百分比、像素或 vw/vh，使用 Java 或 CSS:

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

为了防止 `Drawer` 变得过大，使用 `--dwc-drawer-max-size` 与其配合使用:

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

## 位置 {#placement}

`setPlacement()` 方法控制 `Drawer` 在视口中的出现位置。

可用的放置选项：

<!-- vale off -->
- **顶部**: 将抽屉放置在视口的顶部边缘。
- **顶部中心**: 将抽屉水平居中放置在视口的顶部。
- **底部**: 将抽屉放置在视口的底部。
- **底部中心**: 将抽屉水平居中放置在视口的底部。
- **左侧**: 将抽屉放置在视口的左边缘。
- **右侧**: 将抽屉放置在视口的右边缘。
<!-- vale on -->

<ComponentDemo
path='/webforj/drawerplacement?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerPlacementView.java'
height='600px'
/>

## 事件处理

`Drawer` 组件会发出生命周期事件，可用于在其打开或关闭状态变化时触发应用逻辑。

支持的事件：

- `DrawerOpenEvent`: 当抽屉完全打开时触发。
- `DrawerCloseEvent`: 当抽屉完全关闭时触发。

您可以附加监听器到这些事件，以在 `Drawer` 状态变化时运行逻辑。

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

`Drawer` 组件提供额外内容，而不干扰当前视图。此示例在底部中心放置一个抽屉，包含一个可滚动的联系人列表。

每个联系人显示一个头像、姓名、地点及操作按钮，以便快速访问详细信息或通讯。该方法适用于构建紧凑的工具，如联系人选择器、设置面板或通知。

<ComponentDemo
path='/webforj/drawercontact?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerContactView.java'
cssURL='https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/resources/css/drawer/drawerContact.css'
height='600px'
/>

## 示例：任务管理器

此示例使用 `Drawer` 作为任务管理器。您可以添加任务、将其勾选并清除已完成的任务。`Drawer` 底部包括与任务列表交互的表单控件，当达到 50 个任务时，“添加任务” [`Button`](../components/button) 将禁用。

<ComponentDemo
path='/webforj/drawertask?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerTaskView.java'
cssURL='/css/drawer/drawer-task-view.css'
height='600px'
/>

## 样式 {#styling}

<TableBuilder name="Drawer" />
