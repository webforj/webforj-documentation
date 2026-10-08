---
title: Toolbar
sidebar_position: 145
description: >-
  Lay out action controls with the Toolbar component, placing components into
  Start, Title, Content, and End slots with compact mode.
_i18n_hash: 166b39dabe94c73ecf6310dfdc1ba626
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-toolbar" />
<DocChip chip='since' label='24.12' />
<JavadocLink type="toolbar" location="com/webforj/component/layout/toolbar/Toolbar" top='true'/>

工具栏为用户提供了快速访问核心操作和导航元素的方式。webforJ `Toolbar` 组件是一个水平容器，可以容纳一组操作按钮、图标或其他组件。它非常适合管理页面控件和承载关键功能，如搜索栏或通知按钮。

<!-- INTRO_END -->

## 组织工具栏内容 {#organizing-toolbar-content}

`Toolbar` 以一种易于访问且一致的布局组织基本组件。默认情况下，它占据父元素的全宽，并提供四个放置区域或 _slots_，用于组织组件：

- **开始**：通常包含一个 <JavadocLink type="applayout" location="com/webforj/component/layout/applayout/AppDrawerToggle" code='true'>AppDrawerToggle</JavadocLink> 或一个主页按钮。
- **标题**：用于应用名称或徽标。
- **内容**：用于高关注度的操作，如搜索或导航。
- **结束**：不那么频繁的操作，例如用户档案或帮助。

每个插槽都有添加组件的方法：`addToStart()`、`addToTitle()`、`addToContent()` 和 `addToEnd()`。

以下示例展示了如何将 `Toolbar` 添加到 [AppLayout](./app-layout) 中，并有效利用所有支持的插槽。要了解更多关于在 `AppLayout` 中实现工具栏的信息，请参阅 [粘性工具栏](./app-layout#sticky-toolbars) 和 [移动导航布局](./app-layout#mobile-navigation-layout)。

<ComponentDemo
path='/webforj/toolbarslots'
frame='desktop'
files={['src/main/java/com/webforj/samples/views/toolbar/ToolbarSlotsView.java']}
/>

## 紧凑模式 {#compact-mode}

使用 `setCompact(true)` 来减少 `Toolbar` 周围的填充。这在需要在屏幕上放置更多内容时非常有用，特别是在具有堆叠工具栏或空间有限的应用中。工具栏的行为仍然相同——只有高度被减少。此模式通常用于标题、侧边栏或空间紧张的布局。

```java
Toolbar toolbar = new Toolbar();
toolbar.setCompact(true);
```

<ComponentDemo
path='/webforj/toolbarcompact'
frame='desktop'
files={['src/main/java/com/webforj/samples/views/toolbar/ToolbarCompactView.java']}
/>

## `ProgressBar` 在工具栏中 {#progressbar-in-toolbars}

`ProgressBar` 作为正在进行的过程的可视指示器，例如加载数据、上传文件或完成流程中的步骤。当放在 `Toolbar` 内部时，`ProgressBar` 整齐地对齐在底边，使其不显眼，同时仍然清晰地向用户传达进度。

您可以将其与工具栏中的其他组件如按钮或标签结合使用，而不会干扰布局。

<ComponentDemo
path='/webforj/toolbarprogressbar'
frame='desktop'
files={['src/main/java/com/webforj/samples/views/toolbar/ToolbarProgressbarView.java']}
/>

## 样式 {#styling}

### 主题 {#themes}

`Toolbar` 组件包括 <JavadocLink type="foundation" location="com/webforj/component/Theme">七个内置主题</JavadocLink>，以便快速进行视觉定制：

<ComponentDemo
path='/webforj/toolbartheme'
files={['src/main/java/com/webforj/samples/views/toolbar/ToolbarThemeView.java']}
height='590px'
/>

<TableBuilder name="Toolbar" />
