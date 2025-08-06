---
title: Toolbar
sidebar_position: 145
_i18n_hash: 171c46f92903112a08194d130d89f2c7
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-toolbar" />
<DocChip chip='since' label='24.12' />
<JavadocLink type="toolbar" location="com/webforj/component/layout/toolbar/Toolbar" top='true'/>

工具栏为用户提供了快速访问核心操作和导航元素的途径。webforJ `Toolbar` 组件是一个水平容器，可以容纳一组操作按钮、图标或其他组件。它非常适合管理页面控件和包含关键功能，如搜索栏或通知按钮。

## 组织工具栏内容 {#organizing-toolbar-content}

`Toolbar` 以易于访问和一致的布局组织基本组件。默认情况下，它占用父元素的全部宽度，并提供了四个放置区域或 _插槽_，用于组织组件：

- **开始**：通常包含 <JavadocLink type="applayout" location="com/webforj/component/layout/applayout/AppDrawerToggle" code='true'>AppDrawerToggle</JavadocLink> 或主页按钮。
- **标题**：用于应用程序名称或徽标。
- **内容**：用于高关注度的操作，如搜索或导航。
- **结束**：不太频繁的操作，例如用户资料或帮助。

每个插槽都有添加组件的方法：`addToStart()`、`addToTitle()`、`addToContent()` 和 `addToEnd()`。

以下演示展示了如何将 `Toolbar` 添加到 [AppLayout](./app-layout) 并有效利用所有支持的插槽。
要了解更多关于在 `AppLayout` 中实现工具栏的信息，请参阅 [粘性工具栏](./app-layout#sticky-toolbars) 和 [移动导航布局](./app-layout#mobile-navigation-layout)。

<AppLayoutViewer
path='/webforj/toolbarslots?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toolbar/ToolbarSlotsView.java'
height='300px'
/>

## 紧凑模式 {#compact-mode}

使用 `setCompact(true)` 可以减少 `Toolbar` 周围的填充。这在需要在屏幕上放入更多内容时非常有帮助，特别是在具有堆叠工具栏或空间有限的应用程序中。工具栏的行为保持不变，仅高度减少。此模式通常用于空间紧张的头部、边栏或布局中。

```java
Toolbar toolbar = new Toolbar();
toolbar.setCompact(true);
```

<AppLayoutViewer path='/webforj/toolbarcompact?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toolbar/ToolbarCompactView.java'
/>

## `ProgressBar` 在工具栏中 {#progressbar-in-toolbars}

`ProgressBar` 作为正在进行的过程的视觉指示器，例如加载数据、上传文件或完成流程中的步骤。当放置在 `Toolbar` 中时，`ProgressBar` 整齐地沿底边对齐，使其不显眼，同时仍然清晰地向用户传达进度。

您可以将其与工具栏中的其他组件（例如按钮或标签）结合使用，而不会干扰布局。

<AppLayoutViewer path='/webforj/toolbarprogressbar?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toolbar/ToolbarProgressbarView.java'
/>

## 样式 {#styling}

### 主题 {#themes}

`Toolbar` 组件包括 <JavadocLink type="foundation" location="com/webforj/component/Theme">七个内置主题</JavadocLink> 供快速视觉定制：

<ComponentDemo 
path='/webforj/toolbartheme?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toolbar/ToolbarThemeView.java' 
height = '475px'
/>

<TableBuilder name="Toolbar" />
