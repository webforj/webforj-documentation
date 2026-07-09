---
title: Toolbar
sidebar_position: 145
description: >-
  Lay out action controls with the Toolbar component, placing components into
  Start, Title, Content, and End slots with compact mode.
_i18n_hash: 8dcb4d5bcecce36e656de87218bd3359
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-toolbar" />
<DocChip chip='since' label='24.12' />
<JavadocLink type="toolbar" location="com/webforj/component/layout/toolbar/Toolbar" top='true'/>

工具栏为用户提供核心操作和导航元素的快速访问。webforJ `Toolbar` 组件是一个水平容器，可以容纳一组操作按钮、图标或其他组件。它非常适合管理页面控件并容纳关键功能，如搜索框或通知按钮。

<!-- INTRO_END -->

## 组织工具栏内容 {#organizing-toolbar-content}

`Toolbar` 以易于访问和一致的布局组织基本组件。默认情况下，它占据其父元素的全宽，并提供四个放置区域，即 _slots_，用于组织组件：

- **开始**：通常包含一个 <JavadocLink type="applayout" location="com/webforj/component/layout/applayout/AppDrawerToggle" code='true'>AppDrawerToggle</JavadocLink> 或一个主页按钮。
- **标题**：用于应用名称或徽标。
- **内容**：用于高关注度的操作，如搜索或导航。
- **结束**：不太频繁的操作，如用户资料或帮助。

每个槽都有添加组件的方法：`addToStart()`、`addToTitle()`、`addToContent()` 和 `addToEnd()`。

以下演示显示如何将 `Toolbar` 添加到 [AppLayout](./app-layout) 中并有效利用所有支持的槽。
有关在 `AppLayout` 内实现工具栏的更多信息，请参阅 [粘性工具栏](./app-layout#sticky-toolbars) 和 [移动导航布局](./app-layout#mobile-navigation-layout)。

<ComponentDemo
path='/webforj/toolbarslots'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/toolbar/ToolbarSlotsView.java',
  'src/main/frontend/css/toolbar/toolbar-slots-view.css',
]}
/>

## 紧凑模式 {#compact-mode}

使用 `setCompact(true)` 减少 `Toolbar` 周围的内边距。当您需要在屏幕上放入更多内容时，这非常有用，特别是在具有堆叠工具栏或空间有限的应用程序中。工具栏的行为保持不变—只高度减少。此模式通常用于标题、侧边栏或空间紧张的布局中。

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

`ProgressBar` 作为正在进行的流程的视觉指示器，如加载数据、上传文件或在流程中完成步骤。当放置在 `Toolbar` 内部时，`ProgressBar` 精确地对齐在底边，使其不显眼，同时仍清楚地向用户传达进度。

您可以将其与工具栏中的其他组件结合使用，如按钮或标签，而不会干扰布局。

<ComponentDemo
path='/webforj/toolbarprogressbar'
frame='desktop'
files={['src/main/java/com/webforj/samples/views/toolbar/ToolbarProgressbarView.java']}
/>

## 样式 {#styling}

### 主题 {#themes}

`Toolbar` 组件包括 <JavadocLink type="foundation" location="com/webforj/component/Theme">七个内置主题</JavadocLink> 以快速进行视觉自定义：

<ComponentDemo
path='/webforj/toolbartheme'
files={['src/main/java/com/webforj/samples/views/toolbar/ToolbarThemeView.java']}
height='590px'
/>

<TableBuilder name="Toolbar" />
