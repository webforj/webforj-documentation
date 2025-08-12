---
title: Toolbar
sidebar_position: 145
_i18n_hash: 446d71b3e376810254bbbf6ffee43aa9
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-toolbar" />
<DocChip chip='since' label='24.12' />
<JavadocLink type="toolbar" location="com/webforj/component/layout/toolbar/Toolbar" top='true'/>

工具栏为用户提供了快速访问核心操作和导航元素的方式。webforJ `Toolbar` 组件是一个水平容器，可以容纳一组操作按钮、图标或其他组件。它非常适合管理页面控件以及容纳关键功能，如搜索栏或通知按钮。

## 组织工具栏内容 {#organizing-toolbar-content}

`Toolbar` 以易于访问和一致的布局组织基本组件。默认情况下，它占据其父元素的整个宽度，并提供四个放置区域，或称为 _slots_，用于组织组件：

- **开始**：通常包含一个 <JavadocLink type="applayout" location="com/webforj/component/layout/applayout/AppDrawerToggle" code='true'>AppDrawerToggle</JavadocLink> 或主按钮。
- **标题**：用于应用程序名称或标志。
- **内容**：用于高关注度的操作，如搜索或导航。
- **结束**：较不频繁的操作，如用户个人资料或帮助。

每个插槽都有一种添加组件的方法：`addToStart()`、`addToTitle()`、`addToContent()` 和 `addToEnd()`。

以下演示展示了如何将 `Toolbar` 添加到 [AppLayout](./app-layout) 中，并有效利用所有支持的插槽。要了解更多关于在 `AppLayout` 中实现工具栏的内容，请参见 [粘性工具栏](./app-layout#sticky-toolbars) 和 [移动导航布局](./app-layout#mobile-navigation-layout)。

<AppLayoutViewer
path='/webforj/toolbarslots?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toolbar/ToolbarSlotsView.java'
height='300px'
/>

## 紧凑模式 {#compact-mode}

使用 `setCompact(true)` 来减少 `Toolbar` 周围的填充。这在需要在屏幕上适应更多内容时很有帮助，尤其是在具有堆叠工具栏或空间有限的应用程序中。工具栏的行为仍然相同，只是高度减少。这种模式常用于标题、侧边栏或空间紧张的布局中。

```java
Toolbar toolbar = new Toolbar();
toolbar.setCompact(true);
```

<AppLayoutViewer path='/webforj/toolbarcompact?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toolbar/ToolbarCompactView.java'
/>

## `ProgressBar` 在工具栏中 {#progressbar-in-toolbars}

`ProgressBar` 是持续进程的视觉指示器，如加载数据、上传文件或在流程中完成步骤。当放置在 `Toolbar` 内部时，`ProgressBar` 整齐地对齐在底边，使其不显眼，同时清晰地向用户传达进度。

您可以将其与工具栏中的其他组件（如按钮或标签）结合使用，而不会干扰布局。

<AppLayoutViewer path='/webforj/toolbarprogressbar?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toolbar/ToolbarProgressbarView.java'
/>

## 样式 {#styling}

### 主题 {#themes}

`Toolbar` 组件包含 <JavadocLink type="foundation" location="com/webforj/component/Theme">七个内置主题</JavadocLink> 以快速进行视觉定制：

<ComponentDemo 
path='/webforj/toolbartheme?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toolbar/ToolbarThemeView.java' 
height = '475px'
/>

<TableBuilder name="Toolbar" />
