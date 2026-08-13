---
title: AppLayout
sidebar_position: 5
description: >-
  Build dashboards and admin shells with the AppLayout component, providing a
  fixed header, footer, sliding drawer, and scrollable content area.
_i18n_hash: 559d0c63a8e61e2e3d79086aa08922c1
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-app-layout" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="applayout" location="com/webforj/component/layout/applayout/AppLayout" top='true'/>

`AppLayout` 组件为您提供一个现成的页面结构，具有固定的头部和底部、一个可以滑入和滑出的抽屉，和一个可滚动的内容区域。这些部分共同满足了仪表板、管理面板和大多数多部分界面的布局需求。

<!-- INTRO_END -->

## 特性 {#features}

webforJ App Layout 是一个组件，允许构建常见的应用布局。

<ul>
    <li>易于使用和自定义</li>
    <li>响应式设计</li>
    <li>多种布局选项</li>
    <li>与 webforJ Dark Mode 兼容</li>
</ul>

它提供了一个头部、底部、抽屉和内容部分，所有这些都构建在一个可响应的组件中，可以轻松自定义，以快速构建常见的应用布局，如仪表板。头部和底部是固定的，抽屉在视口中滑入和滑出，内容是可滚动的。

布局的每一部分都是一个 `Div`，可以包含任何有效的 webforJ 控件。为了获得最佳效果，应用程序应包括一个视口元标签，包含 viewport-fit=cover。这个元标签会使视口缩放以填充设备显示屏。

```java
@AppMeta(name = "viewport", content = "width=device-width, initial-scale=1.0, viewport-fit=cover, user-scalable=no")
```

## 概述 {#overview}

以下代码示例将生成一个具有可折叠侧边栏的应用，其中包含一个徽标和多个内容选项的标签，以及一个头部。演示使用 dwc-icon-button 组件来创建抽屉切换按钮。该按钮具有 data-drawer-toggle 属性，指示 DwcAppLayout 监听来自该组件的点击事件以切换抽屉状态。

<!--vale off-->
<ComponentDemo
path='/webforj/applayout/content/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/applayout/applayout/AppLayoutView.java',
  'src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
  'src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java',
  'src/main/frontend/css/applayout/applayout.css',
]}
/>
<!--vale on-->

## 全宽导航栏 {#full-width-navbar}

默认情况下，AppLayout 会将头部和底部渲染为屏幕外模式。屏幕外模式意味着头部和底部的位置将偏移，以适应打开的抽屉。禁用此模式将导致头部和底部占据整个可用空间，并使抽屉的顶部和底部位置适应头部和底部。

```java showLineNumbers
AppLayout myApp = new AppLayout();

myApp.setHeaderOffscreen(false);
myApp.setFooterOffscreen(false);
```

<!--vale off-->
<ComponentDemo
path='/webforj/applayoutfullnavbar/content/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/applayout/fullnavbar/AppLayoutFullNavbarView.java',
  'src/main/java/com/webforj/samples/views/applayout/fullnavbar/AppLayoutFullNavbarContentView.java',
  'src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java',
  'src/main/frontend/css/applayout/applayout.css',
]}
/>
<!--vale on-->

## 多个工具栏 {#multiple-toolbars}

导航栏没有限制您可以添加的工具栏数量。`Toolbar` 是一个水平容器组件，包含一组操作按钮、图标或其他控件。要添加一个额外的工具栏，只需使用 `addToHeader()` 方法添加另一个 `Toolbar` 组件。

以下演示显示如何使用两个工具栏，第一个工具栏包含抽屉的切换按钮和应用的标题。第二个工具栏包含一个二级导航菜单。

<!--vale off-->
<ComponentDemo
path='/webforj/applayoutmultipleheaders/content/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/applayout/multipleheaders/AppLayoutMultipleHeadersView.java',
  'src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
  'src/main/java/com/webforj/samples/views/applayout/multipleheaders/AppLayoutMultipleHeaderContentView.java',
  'src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java',
  'src/main/frontend/css/applayout/applayout.css',
]}
/>
<!--vale on-->

## 粘性工具栏 {#sticky-toolbars}

粘性工具栏是在用户向下滚动时仍然可见的工具栏，但导航栏高度收缩以为页面内容提供更多空间。通常，这种工具栏包含与当前页面相关的固定导航菜单。

可以使用 CSS 自定义属性 `--dwc-app-layout-header-collapse-height` 和 `AppLayout.setHeaderReveal()` 选项创建粘性工具栏。

当调用 `AppLayout.setHeaderReveal(true)` 时，头部在首次渲染时将可见，然后在用户开始向下滚动时隐藏。一旦用户开始向上滚动，头部将被显现出来。

借助 CSS 自定义属性 `--dwc-app-layout-header-collapse-height`，可以控制头部导航栏会隐藏多少。

<!--vale off-->
<ComponentDemo
path='/webforj/applayoutstickytoolbar/content/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/applayout/stickytoolbar/AppLayoutStickyToolbarView.java',
  'src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
  'src/main/java/com/webforj/samples/views/applayout/stickytoolbar/AppLayoutStickyToolbarContentView.java',
  'src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java',
  'src/main/frontend/css/applayout/applayout.css',
]}
/>
<!--vale on-->

## 移动导航布局 {#mobile-navigation-layout}

底部导航栏可用于提供应用程序底部的不同版本导航。这种类型的导航在移动应用中尤为流行。

请注意，以下演示中隐藏了抽屉。AppLayout 小部件支持三种抽屉位置： `DrawerPlacement.LEFT`、 `DrawerPlacement.RIGHT` 和 `DrawerPlacement.HIDDEN`。

与 `AppLayout.setHeaderReveal()` 类似，支持 `AppLayout.setFooterReveal()`。当调用 `AppLayout.setFooterReveal(true)` 时，底部在首次渲染时可见，然后在用户开始向上滚动时隐藏。一旦用户开始向下滚动，底部将被显现出来。

默认情况下，当屏幕宽度为 800px 或更小时，抽屉将切换为弹出模式。这称为断点。弹出模式意味着抽屉将覆盖内容区域并带有覆盖层。可以通过使用 `setDrawerBreakpoint()` 方法来配置断点，断点必须是有效的 [媒体查询](https://developer.mozilla.org/en-US/docs/Web/CSS/Media_Queries/Using_media_queries)。

<!--vale off-->
<ComponentDemo
path='/webforj/applayoutmobiledrawer/'
frame='mobile'
files={[
  'src/main/java/com/webforj/samples/views/applayout/mobiledrawer/AppLayoutMobileDrawerView.java',
  'src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
  'src/main/java/com/webforj/samples/views/applayout/mobiledrawer/AppLayoutMobileDrawerContentView.java',
  'src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java',
  'src/main/frontend/css/applayout/applayout.css',
]}
/>
<!--vale on-->

## 抽屉工具 {#drawer-utilities}

`AppLayout` 抽屉工具旨在为主应用布局中的集成导航和上下文菜单提供便利，而独立的 [`Drawer`](https://docs.webforj.com/docs/components/drawer) 组件则提供灵活的、独立的滑动面板，可用于应用中的任何地方，用于附加内容、过滤器或通知。本节重点介绍 AppLayout 提供的内置抽屉功能和工具。

### 抽屉断点 {#drawer-breakpoint}

默认情况下，当屏幕宽度为 800px 或更小时，抽屉将切换为弹出模式。这称为断点。弹出模式意味着抽屉将覆盖内容区域并带有覆盖层。可以通过使用 `setDrawerBreakpoint()` 方法来配置断点，断点必须是有效的媒体查询。

例如，在以下示例中，抽屉断点被配置为 500px 或更小。

```java
AppLayout demo = new AppLayout();
demo.setDrawerBreakpoint("(max-width:500px)");
```

### 抽屉标题 {#drawer-title}

`AppLayout` 组件提供一个 `addToDrawerTitle()` 方法，用于定义要在抽屉头部显示的自定义标题。

```java
layout.addToDrawerTitle(new Div("菜单"));
```

### 抽屉操作 {#drawer-actions}

`AppLayout` 组件允许您使用 `addToDrawerHeaderActions()` 方法将自定义组件（如按钮或图标）放入 **抽屉头部操作区域**。

```java
layout.addToDrawerHeaderActions(
  new IconButton(TablerIcon.create("bell")),
);
```

可以传递多个组件作为参数：

```java
layout.addToDrawerHeaderActions(
  new IconButton(TablerIcon.create("bell")),
  new Button("个人资料")
);
```

抽屉操作出现在 **抽屉头部的右对齐部分**。

<!--vale off-->
<ComponentDemo
path='/webforj/applayoutdrawerutility/content/Dashboard/'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/applayout/applayoutdrawerutility/AppLayoutDrawerUtilityView.java',
  'src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
  'src/main/java/com/webforj/samples/views/applayout/applayoutdrawerutility/AppLayoutDrawerUtilityContentView.java',
  'src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java',
  'src/main/frontend/css/applayout/applayout.css',
]}
/>
<!--vale on-->

## `AppDrawerToggle` <DocChip chip='since' label='24.12' /> {#appdrawertoggle-docchip-chipsince-label2412-}

[`AppDrawerToggle`](https://javadoc.io/doc/com.webforj/webforj-applayout/latest/com/webforj/component/layout/applayout/AppDrawerToggle.html) 组件是一个服务端的 webforJ 类，表示一个用于切换 `AppLayout` 中导航抽屉可见性的按钮。它映射到客户端的 `<dwc-app-drawer-toggle>` 元素，并默认样式行为类似于传统的汉堡菜单图标，这种行为可以自定义。

### 概述 {#overview-1}

`AppDrawerToggle` 扩展了 `IconButton`，默认使用 Tabler 图标集中的 "menu-2" 图标。它自动应用 `data-drawer-toggle` 属性以与客户端抽屉行为集成。

```java
// 无需事件注册：
AppLayout layout = new AppLayout();
layout.addToHeader(new AppDrawerToggle());
// 抽屉切换将即插即用，无需手动事件监听器。
```
## 样式 {#styling}

<TableBuilder name="AppLayout" />
