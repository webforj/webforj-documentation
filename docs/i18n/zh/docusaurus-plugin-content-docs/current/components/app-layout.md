---
title: AppLayout
sidebar_position: 5
_i18n_hash: 7bc8b2a8bfc772644cf2107199615515
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-app-layout" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="applayout" location="com/webforj/component/layout/applayout/AppLayout" top='true'/>

`AppLayout` 是一个全面的响应式布局组件，提供了头部、底部、抽屉和内容区。头部和底部是固定的，抽屉在视口内滑动，内容可滚动。

这个组件可以用来构建常见的应用布局，例如仪表盘。

## 特点 {#features}

webforJ 应用布局是一个允许构建常见应用布局的组件。

<ul>
    <li>易于使用和自定义</li>
    <li>响应式设计</li>
    <li>多种布局选项</li>
    <li>支持 webforJ 深色模式</li>
</ul>

它提供了一个头部、底部、抽屉和内容部分，全部构建在一个响应式组件中，能够轻松自定义以快速构建常见的应用布局，如仪表盘。头部和底部是固定的，抽屉在视口内滑动，内容可滚动。

布局的每个部分都是一个 `Div`，可以包含任何有效的 webforJ 控件。为了获得最佳效果，应用程序应包括一个包含 viewport-fit=cover 的视口元标签。该元标签使视口缩放以填充设备显示屏。

```java
@AppMeta(name = "viewport", content = "width=device-width, initial-scale=1.0, viewport-fit=cover, user-scalable=no")
```

## 概述 {#overview}

以下代码示例将生成一个具有可折叠侧边栏的应用，其中包含一个徽标和多种内容选项的标签，以及一个头部。演示使用 dwc-icon-button Web 组件创建抽屉切换按钮。该按钮具有 data-drawer-toggle 属性，用于指示 DwcAppLayout 监听来自该组件的单击事件，以切换抽屉状态。

<!--vale off-->
<AppLayoutViewer path='/webforj/applayout/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/applayout/AppLayoutView.java'
cssURL='/css/applayout/applayout.css'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java']}/>
<!--vale on-->

## 全宽导航栏 {#full-width-navbar}

默认情况下，AppLayout 在离屏模式下渲染头部和底部。离屏模式意味着头部和底部的位置将移位以适应打开的抽屉。禁用此模式将导致头部和底部占用整个可用空间，并将抽屉的顶部和底部位置移动到适应头部和底部。

```java showLineNumbers
AppLayout myApp = new AppLayout();

myApp.setHeaderOffscreen(false);
myApp.setFooterOffscreen(false);
```

<!--vale off-->
<AppLayoutViewer path='/webforj/applayoutfullnavbar/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/fullnavbar/AppLayoutFullNavbarView.java'
cssURL='/css/applayout/applayout.css'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/fullnavbar/AppLayoutFullNavbarContentView.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java']}/>
<!--vale on-->

## 多个工具栏 {#multiple-toolbars}

导航栏对您可以添加的工具栏数量没有限制。`Toolbar` 是一个水平容器组件，用于容纳一组操作按钮、图标或其他控件。要添加一个额外的工具栏，只需使用 `addToHeader()` 方法添加另一个 `Toolbar` 组件。

以下演示展示了如何使用两个工具栏，第一个工具栏容纳抽屉的切换按钮和应用的标题。第二个工具栏容纳一个二级导航菜单。

<!--vale off-->
<AppLayoutViewer path='/webforj/applayoutmultipleheaders/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/multipleheaders/AppLayoutMultipleHeadersView.java'
cssURL='/css/applayout/applayout.css'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/multipleheaders/AppLayoutMultipleHeaderContentView.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java']}/>
<!--vale on-->

## 粘性工具栏 {#sticky-toolbars}

粘性工具栏是一个在用户向下滚动时保持在页面顶部可见的工具栏，但导航栏的高度会收缩，以为页面内容提供更多空间。通常，这种工具栏包含与当前页相关的固定导航菜单。

可以使用 CSS 自定义属性 `--dwc-app-layout-header-collapse-height` 和 `AppLayout.setHeaderReveal()` 选项创建粘性工具栏。

当调用 `AppLayout.setHeaderReveal(true)` 时，头部将在第一次渲染时可见，然后当用户开始向下滚动时隐藏。当用户再次向上滚动时，头部将被显示。

借助 CSS 自定义属性 `--dwc-app-layout-header-collapse-height`，可以控制有多少 NavBar 头部将被隐藏。

<!--vale off-->
<AppLayoutViewer path='/webforj/applayoutstickytoolbar/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/stickytoolbar/AppLayoutStickyToolbarView.java'
cssURL='/css/applayout/applayout.css'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/stickytoolbar/AppLayoutStickyToolbarContentView.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java']}/>
<!--vale on-->

## 移动导航布局 {#mobile-navigation-layout}

底部导航栏可用于提供在应用底部的导航不同版本。这种类型的导航在移动应用中特别流行。

请注意，在以下演示中，抽屉是隐藏的。AppLayout 小部件支持三种抽屉位置：`DrawerPlacement.LEFT`、`DrawerPlacement.RIGHT` 和 `DrawerPlacement.HIDDEN`。

与 `AppLayout.setHeaderReveal()` 类似，支持 `AppLayout.setFooterReveal()`。当调用 `AppLayout.setFooterReveal(true)` 时，底部在首次渲染时可见，然后在用户开始向上滚动时隐藏。一旦用户开始向下滚动，底部将被显示。

默认情况下，当屏幕宽度为 800px 或更小时，抽屉将切换到弹出模式。这被称为断点。弹出模式意味着抽屉将在内容区域上方弹出并具有遮罩层。可以使用 `setDrawerBreakpoint()` 方法配置断点，并且断点必须是有效的 [媒体查询](https://developer.mozilla.org/en-US/docs/Web/CSS/Media_Queries/Using_media_queries)。

<!--vale off-->
<AppLayoutViewer path='/webforj/applayoutmobiledrawer/?' mobile='true'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/mobiledrawer/AppLayoutMobileDrawerView.java'
cssURL='/css/applayout/applayout.css'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/mobiledrawer/AppLayoutMobileDrawerContentView.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java']}/>
<!--vale on-->

## 抽屉工具 {#drawer-utilities}

`AppLayout` 抽屉工具旨在为主应用布局内的集成导航和上下文菜单提供支持，而独立的 [`Drawer`](https://docs.webforj.com/docs/components/drawer) 组件则提供灵活的独立滑动面板，可在您的应用的任何地方用于额外内容、过滤器或通知。本节重点介绍 AppLayout 提供的内置抽屉功能和实用程序。

### 抽屉断点 {#drawer-breakpoint}

默认情况下，当屏幕宽度为 800px 或更小时，抽屉将切换到弹出模式。这被称为断点。弹出模式意味着抽屉将在内容区域上方弹出并具有遮罩层。可以使用 `setDrawerBreakpoint()` 方法配置断点，并且断点必须是有效的媒体查询。

例如，在以下示例中，抽屉断点被配置为 500px 或更小。

```java
AppLayout demo = new AppLayout();
demo.setDrawerBreakpoint("(max-width:500px)");
```

### 抽屉标题 {#drawer-title}

`AppLayout` 组件提供了 `addToDrawerTitle()` 方法，用于定义要在抽屉头部显示的自定义标题。

```java
layout.addToDrawerTitle(new Div("菜单"));
```

### 抽屉操作 {#drawer-actions}

`AppLayout` 组件允许您将自定义组件（如按钮或图标）放置在 **抽屉头部操作区** 中，使用 `addToDrawerHeaderActions()` 方法。

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

抽屉操作出现在抽屉头部的 **右对齐部分**。

<!--vale off-->
<AppLayoutViewer path='/webforj/applayoutdrawerutility/content/Dashboard/?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/applayoutdrawerutility/AppLayoutDrawerUtilityView.java'
cssURL='/css/applayout/applayout.css'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/applayoutdrawerutility/AppLayoutDrawerUtilityContentView.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java']}/>
<!--vale on-->


## `AppDrawerToggle` <DocChip chip='since' label='24.12' /> {#appdrawertoggle-docchip-chipsince-label2412-}

[`AppDrawerToggle`](https://javadoc.io/doc/com.webforj/webforj-applayout/latest/com/webforj/component/layout/applayout/AppDrawerToggle.html) 组件是一个服务器端 webforJ 类，表示用于切换 `AppLayout` 中导航抽屉可见性的按钮。它映射到客户端的 `<dwc-app-drawer-toggle>` 元素，并默认样式设置为像一个传统的汉堡菜单图标，该行为可以自定义。

### 概述 {#overview-1}

`AppDrawerToggle` 继承自 `IconButton`，默认使用 Tabler 图标集中 "menu-2" 图标。它自动应用 `data-drawer-toggle` 属性以集成客户端抽屉行为。

```java
// 无需事件注册：
AppLayout layout = new AppLayout();
layout.addToHeader(new AppDrawerToggle());
// 抽屉切换按钮将开箱即用，无需手动事件监听器。
```
## 样式 {#styling}

<TableBuilder name="AppLayout" />
