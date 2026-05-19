---
title: Drawer
sidebar_position: 35
_i18n_hash: 7edd08525f20625cb8d891316111ebb3
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-drawer" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="drawer" location="com/webforj/component/drawer/Drawer" top='true'/>

`Drawer` 组件在 webforJ 中创建一个从屏幕边缘滑出的面板，显示额外内容而不离开当前视图。它通常用于侧边导航、筛选菜单、用户设置或临时出现的紧凑通知，而不会干扰主界面。

<!-- INTRO_END -->

## 堆叠 {#stacking}

当多个抽屉打开时，抽屉会自动堆叠，这使它们成为空间受限界面的灵活选择。

下面的示例展示了这种行为在 [`AppLayout`](../components/app-layout) 组件中的表现。由汉堡菜单触发的导航抽屉内置于 [`AppLayout`](../components/app-layout) 中，而底部的欢迎弹出窗口使用独立的 `Drawer` 实例。两者共存并独立堆叠，演示了抽屉如何在布局组件中集成或用作独立元素。

<ComponentDemo
path='/webforj/drawerwelcome'
frame='mobile'
files={[
  'src/main/java/com/webforj/samples/views/drawer/DrawerWelcomeView.java',
  'src/main/resources/static/css/drawer/drawerWelcome.css',
]}
/>

## 自动聚焦

`Drawer` 组件支持自动聚焦，当 `Drawer` 打开时，自动将焦点设置在第一个可聚焦元素上。这通过直接将注意力引导到第一个可操作元素来提高可用性。

<ComponentDemo
path='/webforj/drawerautofocus'
files={['src/main/java/com/webforj/samples/views/drawer/DrawerAutoFocusView.java']}
height='600px'
/>

<!-- 示例 -->

## 标签 {#label}

`setLabel()` 方法可以提供对 `Drawer` 内容的有意义描述。设置标签后，辅助技术（如屏幕阅读器）可以宣布它，帮助用户理解 `Drawer` 的目的，而不必查看其视觉内容。

```java
Drawer drawer = new Drawer();
drawer.setLabel("任务管理器");
```

:::tip 描述性标签
使用简洁且描述性的标签，以反映 `Drawer` 的目的。当可以使用更具体的名称时，避免使用诸如“菜单”或“面板”等通用术语。
:::

## 尺寸

要控制 `Drawer` 的大小，请为 CSS 自定义属性 `--dwc-drawer-size` 设置一个值。此设置用于左/右放置的宽度或上/下放置的高度。

您可以使用任何有效的 CSS 单位（如百分比、像素或 vw/vh）来定义该值，使用 Java 或 CSS：

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

为了防止 `Drawer` 变得过大，请与之一起使用 `--dwc-drawer-max-size`：

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

## 放置 {#placement}

`setPlacement()` 方法控制 `Drawer` 在视口中的出现位置。

可用的放置选项：

<!-- vale off -->
- **顶部**: 将抽屉放置在视口的顶部边缘。
- **顶部中心**: 将抽屉在视口顶部水平居中对齐。
- **底部**: 将抽屉放置在视口的底部。
- **底部中心**: 将抽屉在视口底部水平居中对齐。
- **左侧**: 将抽屉放置在视口的左侧边缘。
- **右侧**: 将抽屉放置在视口的右侧边缘。
<!-- vale on -->

<ComponentDemo
path='/webforj/drawerplacement'
files={['src/main/java/com/webforj/samples/views/drawer/DrawerPlacementView.java']}
height='600px'
/>

## 事件处理

`Drawer` 组件会发出生命周期事件，可以用于在其打开或关闭状态发生变化时触发应用逻辑。

支持的事件：

- `DrawerOpenEvent`: 在抽屉完全打开时触发。
- `DrawerCloseEvent`: 在抽屉完全关闭时触发。

您可以附加监听器以在 `Drawer` 状态改变时运行逻辑。

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

`Drawer` 组件在不干扰当前视图的情况下显示额外内容。此示例将抽屉放置在底部中心，包含一个可滚动的联系人列表。

每个联系人显示一个头像、姓名、位置和操作按钮，以便快速访问详细信息或进行沟通。这种方法很适合构建紧凑的工具，如联系人选择器、设置面板或通知。

<ComponentDemo
path='/webforj/drawercontact'
files={[
  'src/main/java/com/webforj/samples/views/drawer/DrawerContactView.java',
  'src/main/resources/css/drawer/drawerContact.css',
]}
height='600px'
/>

## 示例：任务管理器

此示例使用 `Drawer` 作为任务管理器。您可以添加任务、完成任务并清除已完成的任务。`Drawer` 页脚包含与任务列表交互的表单控件，当达到 50 个任务时，“添加任务” [`Button`](../components/button) 会禁用自己。

<ComponentDemo
path='/webforj/drawertask'
files={[
  'src/main/java/com/webforj/samples/views/drawer/DrawerTaskView.java',
  'src/main/resources/static/css/drawer/drawer-task-view.css',
]}
height='600px'
/>

## 样式 {#styling}

<TableBuilder name="Drawer" />
