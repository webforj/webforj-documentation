---
title: Toolbar
sidebar_position: 145
_i18n_hash: e3329a54fd8feccd96e15e1b19c3c97d
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-toolbar" />
<DocChip chip='since' label='24.12' />
<JavadocLink type="toolbar" location="com/webforj/component/layout/toolbar/Toolbar" top='true'/>

工具栏为用户提供对核心操作和导航元素的快速访问。webforJ `Toolbar` 组件是一个水平容器，可以容纳一组操作按钮、图标或其他组件。它非常适合管理页面控制和容纳关键功能，例如搜索栏或通知按钮。

<!-- INTRO_END -->

## 组织工具栏内容 {#organizing-toolbar-content}

`Toolbar` 以易于访问和一致的布局组织基本组件。默认情况下，它占据其父元素的整个宽度，并提供四个放置区域或 _插槽_ 用于组织组件：

- **开始**：通常包含一个 <JavadocLink type="applayout" location="com/webforj/component/layout/applayout/AppDrawerToggle" code='true'>AppDrawerToggle</JavadocLink> 或一个主页按钮。
- **标题**：用于应用名称或徽标。
- **内容**：用于高关注度的操作，例如搜索或导航。
- **结束**：较少使用的操作，例如用户个人资料或帮助。

每个插槽都有添加组件的方法：`addToStart()`、`addToTitle()`、`addToContent()` 和 `addToEnd()`。

以下演示显示了如何将 `Toolbar` 添加到 [AppLayout](./app-layout) 中，并有效利用所有支持的插槽。
有关如何在 `AppLayout` 中实现工具栏的更多信息，请参见 [粘性工具栏](./app-layout#sticky-toolbars) 和 [移动导航布局](./app-layout#mobile-navigation-layout)。

<AppLayoutViewer
path='/webforj/toolbarslots?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toolbar/ToolbarSlotsView.java'
height='300px'
/>

## 紧凑模式 {#compact-mode}

使用 `setCompact(true)` 来减少 `Toolbar` 周围的填充。在需要在屏幕上适应更多内容时尤其有用，特别是在具有堆叠工具栏或空间有限的应用中。工具栏的行为保持不变，仅高度减少。此模式通常用于空间紧张的头部、侧边栏或布局中。

```java
Toolbar toolbar = new Toolbar();
toolbar.setCompact(true);
```

<AppLayoutViewer path='/webforj/toolbarcompact?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toolbar/ToolbarCompactView.java'
/>

## `ProgressBar` 在工具栏中 {#progressbar-in-toolbars}

`ProgressBar` 作为进行中过程的视觉指示器，例如加载数据、上传文件或完成流程中的步骤。当放置在 `Toolbar` 中时，`ProgressBar` 整齐地沿着底边对齐，使其不显眼，同时仍清晰地向用户传达进度。

您可以将其与工具栏中的其他组件如按钮或标签结合使用，而不会干扰布局。

<AppLayoutViewer path='/webforj/toolbarprogressbar?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toolbar/ToolbarProgressbarView.java'
/>

## 样式 {#styling}

### 主题 {#themes}

`Toolbar` 组件包括 <JavadocLink type="foundation" location="com/webforj/component/Theme">七种内置主题</JavadocLink> 以快速进行视觉自定义：

<ComponentDemo 
path='/webforj/toolbartheme?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toolbar/ToolbarThemeView.java' 
height = '475px'
/>

<TableBuilder name="Toolbar" />
