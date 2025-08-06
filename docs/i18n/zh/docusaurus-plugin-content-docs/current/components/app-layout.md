---
title: AppLayout
sidebar_position: 5
sidebar_class_name: updated-content
_i18n_hash: 46ea0f38e27d84ef944e7a26fd0c5666
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-app-layout" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="applayout" location="com/webforj/component/layout/applayout/AppLayout" top='true'/>

`AppLayout` 是一个全面的响应式布局组件，提供了一个头部、一个底部、一个侧边栏和内容区域。头部和底部是固定的，侧边栏在视口中滑入和滑出，内容是可滚动的。

该组件可用于构建常见的应用布局，例如仪表盘。

## 功能 {#features}

webforJ 应用布局是一个允许构建常见应用布局的组件。

<ul>
    <li>易于使用和自定义</li>
    <li>响应式设计</li>
    <li>多种布局选项</li>
    <li>支持 webforJ 黑暗模式</li>
</ul>

它提供了一个头部、底部、侧边栏和内容区域，所有这些都构建为一个响应式组件， 可以轻松自定义，以快速构建常见的应用布局，例如仪表盘。头部和底部是固定的，侧边栏在视口中滑入和滑出，内容是可滚动的。

布局的每个部分都是一个 `Div`，可以包含任何有效的 webforJ 控件。为了获得最佳效果，应用程序应包含一个视口元标签，其中包含 viewport-fit=cover。元标签使视口的大小适应设备显示。

```java
@AppMeta(name = "viewport", content = "width=device-width, initial-scale=1.0, viewport-fit=cover, user-scalable=no")
```

## 概述 {#overview}

以下代码示例将生成一个带有可折叠侧边栏的应用，其中包含一个徽标和多个内容选项的标签以及一个头部。演示使用 dwc-icon-button 网页组件创建一个侧边栏切换按钮。该按钮具有 data-drawer-toggle 属性，指示 DwcAppLayout 监听来自该组件的点击事件以切换侧边栏状态。

<AppLayoutViewer path='/webforj/applayout/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/applayout/AppLayoutView.java'
cssURL='/css/applayout/applayout.css'
/>

## 全宽导航栏 {#full-width-navbar}

默认情况下，AppLayout 在离屏模式下呈现头部和底部。离屏模式意味着头部和底部的位置将移动，以适应打开的侧边栏。禁用此模式将使头部和底部占用整个可用空间，并将侧边栏的顶部和底部位置调整，以适应头部和底部。

```java showLineNumbers
AppLayout myApp = new AppLayout();

myApp.setHeaderOffscreen(false);
myApp.setFooterOffscreen(false);
```

<AppLayoutViewer path='/webforj/applayoutfullnavbar/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/fullnavbar/AppLayoutFullNavbarView.java'
cssURL='/css/applayout/applayout.css'/>

## 多个工具栏 {#multiple-toolbars}

导航栏添加工具栏的数量没有限制。一个 `Toolbar` 是一个水平容器组件，可以容纳一组操作按钮、图标或其他控件。要添加一个额外的工具栏，只需使用 `addToHeader()` 方法添加另一个 `Toolbar` 组件。

以下演示展示了如何使用两个工具栏，第一个工具栏容纳侧边栏切换按钮和应用的标题。第二个工具栏容纳一个次级导航菜单。

<AppLayoutViewer path='/webforj/applayoutmultipleheaders/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/multipleheaders/AppLayoutMultipleHeadersView.java'
cssURL='/css/applayout/applayout.css'/>

## 粘性工具栏 {#sticky-toolbars}

粘性工具栏是在用户向下滚动时仍然可见的工具栏，但导航栏高度折叠以提高页面内容的可用空间。通常，这种工具栏包含与当前页面相关的固定导航菜单。

可以使用 CSS 自定义属性 `--dwc-app-layout-header-collapse-height` 和 `AppLayout.setHeaderReveal()` 选项创建粘性工具栏。

当 `AppLayout.setHeaderReveal(true)` 被调用时，头部在首次渲染时将可见，然后在用户开始向下滚动时隐藏。一旦用户再次开始向上滚动，头部将被显示。

借助 CSS 自定义属性 `--dwc-app-layout-header-collapse-height`，可以控制头部导航栏将隐藏多少。

<AppLayoutViewer path='/webforj/applayoutstickytoolbar/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/stickytoolbar/AppLayoutStickyToolbarView.java'
cssURL='/css/applayout/applayout.css'/>

## 移动导航布局 {#mobile-navigation-layout}

底部导航栏可以用于提供应用底部的不同版本导航。这种类型的导航在移动应用中尤其受欢迎。

请注意，以下演示中侧边栏是隐藏的。AppLayout 小部件支持三个侧边栏位置： `DrawerPlacement.LEFT`、 `DrawerPlacement.RIGHT` 和 `DrawerPlacement.HIDDEN`。

与 `AppLayout.setHeaderReveal()` 相同，支持 `AppLayout.setFooterReveal()`。当调用 `AppLayout.setFooterReveal(true)` 时，底部将可见，在首次渲染时，然后在用户开始向上滚动时隐藏。一旦用户再次开始向下滚动，底部将被显示。

默认情况下，当屏幕宽度为 800px 或更小时，侧边栏将切换到弹出模式。这被称为断点。弹出模式意味着侧边栏将在内容区域上方弹出并覆盖。可以通过使用 `setDrawerBreakpoint()` 方法配置断点，断点必须是有效的 [媒体查询](https://developer.mozilla.org/en-US/docs/Web/CSS/Media_Queries/Using_media_queries)。

<AppLayoutViewer path='/webforj/applayoutmobiledrawer/?' mobile='true'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/mobiledrawer/AppLayoutMobileDrawerView.java'
cssURL='/css/applayout/applayoutMobile.css'
/>

## 侧边栏实用程序 {#drawer-utilities}

`AppLayout` 侧边栏实用程序旨在为主应用布局内的集成导航和上下文菜单提供支持，而独立的 [`Drawer`](https://docs.webforj.com/docs/components/drawer) 组件提供灵活的、独立的滑动面板，可以在应用的任何地方用于附加内容、过滤器或通知。本节重点介绍 AppLayout 提供的内置侧边栏功能和实用程序。

### 侧边栏断点 {#drawer-breakpoint}

默认情况下，当屏幕宽度为 800px 或更小时，侧边栏将切换到弹出模式。这被称为断点。弹出模式意味着侧边栏将在内容区域上方弹出并覆盖。可以通过使用 `setDrawerBreakpoint()` 方法配置断点，断点必须是有效的媒体查询。

例如，在以下示例中，侧边栏断点被配置为 500px 或更小。

```java
AppLayout demo = new AppLayout();
demo.setDrawerBreakpoint("(max-width:500px)");
```

### 侧边栏标题 {#drawer-title}

`AppLayout` 组件提供了一个 `addToDrawerTitle()` 方法，用于定义在侧边栏头部显示的自定义标题。

```java
layout.addToDrawerTitle(new Div("菜单"));
```

### 侧边栏操作 {#drawer-actions}

`AppLayout` 组件允许您使用 `addToDrawerHeaderActions()` 方法将自定义组件（如按钮或图标）放置到**侧边栏头部操作区域**中。

```java
layout.addToDrawerHeaderActions(
    new IconButton(TablerIcon.create("bell")),
);
```

可以将多个组件作为参数传递：

```java
layout.addToDrawerHeaderActions(
    new IconButton(TablerIcon.create("bell")),
    new Button("个人资料")
);
```

侧边栏操作出现在**侧边栏头部的右对齐部分**。

<AppLayoutViewer path='/webforj/applayoutdrawerutility/content/Dashboard/?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/applayoutdrawerutility/AppLayoutDrawerUtilityView.java'
cssURL='/css/applayout/applayout.css'
/>

## `AppDrawerToggle` <DocChip chip='since' label='24.12' /> {#appdrawertoggle-docchip-chipsince-label2412-}

[`AppDrawerToggle`](https://javadoc.io/doc/com.webforj/webforj-applayout/latest/com/webforj/component/layout/applayout/AppDrawerToggle.html) 组件是一个服务器端的 webforJ 类，表示用于切换 `AppLayout` 中导航侧边栏可见性的按钮。它映射到客户端的 `<dwc-app-drawer-toggle>` 元素，并默认样式化，使其像传统的汉堡菜单图标，这一行为可以自定义。

### 概述 {#overview-1}

`AppDrawerToggle` 扩展了 `IconButton`，默认使用来自 Tabler 图标集的 "menu-2" 图标。它自动应用 `data-drawer-toggle` 属性，以与客户端侧边栏行为集成。

```java
// 不需要事件注册：
AppLayout layout = new AppLayout();
layout.addToHeader(new AppDrawerToggle());
// 侧边栏切换将开箱即用，无需手动事件监听器。
```
## 样式 {#styling}

<TableBuilder name="AppLayout" />
