---
title: Toolbar
sidebar_position: 145
description: >-
  Lay out action controls with the Toolbar component, placing components into
  Start, Title, Content, and End slots with compact mode.
_i18n_hash: 99def78151a30c5c7fef7106b2efcb5b
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-toolbar" />
<DocChip chip='since' label='24.12' />
<JavadocLink type="toolbar" location="com/webforj/component/layout/toolbar/Toolbar" top='true'/>

工具栏为用户提供对核心操作和导航元素的快速访问。 webforJ `Toolbar` 组件是一个水平容器，可以容纳一组操作按钮、图标或其他组件。 它非常适合管理页面控件并容纳诸如搜索栏或通知按钮之类的关键功能。

<!-- INTRO_END -->

## 组织工具栏内容 {#organizing-toolbar-content}

`Toolbar` 以易于访问和一致的布局组织必要组件。 默认情况下，它占据其父元素的整个宽度，并提供四个放置区域或 _插槽_ 以组织组件：

- **开始**：通常包含一个 <JavadocLink type="applayout" location="com/webforj/component/layout/applayout/AppDrawerToggle" code='true'>AppDrawerToggle</JavadocLink> 或主页按钮。
- **标题**：用于应用程序名称或徽标。
- **内容**：用于高度关注的操作，如搜索或导航。
- **结束**：较少使用的操作，例如用户资料或帮助。

每个插槽都有一个添加组件的方法：`addToStart()`、`addToTitle()`、`addToContent()` 和 `addToEnd()`。

以下演示展示了如何将 `Toolbar` 添加到 [AppLayout](./app-layout) 中，并有效利用所有支持的插槽。
有关在 `AppLayout` 中实现工具栏的更多信息，请参阅 [Sticky toolbars](./app-layout#sticky-toolbars) 和 [Mobile navigation layout](./app-layout#mobile-navigation-layout)。

<ComponentDemo
path='/webforj/toolbarslots'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/toolbar/ToolbarSlotsView.java',
  'src/main/resources/static/css/toolbar/toolbar-slots-view.css',
]}
/>

## 紧凑模式 {#compact-mode}

使用 `setCompact(true)` 来减少 `Toolbar` 周围的填充。这在您需要在屏幕上放置更多内容时特别有用，尤其是在具有堆叠工具栏或空间有限的应用程序中。 工具栏的行为仍然相同，只有高度被减少。 此模式通常用于标题、侧边栏或空间紧张的布局中。

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

`ProgressBar` 作为持续过程的视觉指示器，例如加载数据、上传文件或完成流程中的步骤。当放置在 `Toolbar` 中时，`ProgressBar` 整齐地对齐在底部边缘，使其在不干扰的情况下清晰地向用户传达进度。

您可以将其与按钮或标签等其他组件组合在工具栏中，而不会干扰布局。

<ComponentDemo
path='/webforj/toolbarprogressbar'
frame='desktop'
files={['src/main/java/com/webforj/samples/views/toolbar/ToolbarProgressbarView.java']}
/>

## 样式 {#styling}

### 主题 {#themes}

`Toolbar` 组件包括 <JavadocLink type="foundation" location="com/webforj/component/Theme">七个内置主题</JavadocLink> 用于快速视觉自定义：

<ComponentDemo
path='/webforj/toolbartheme'
files={['src/main/java/com/webforj/samples/views/toolbar/ToolbarThemeView.java']}
height='590px'
/>

<TableBuilder name="Toolbar" />
