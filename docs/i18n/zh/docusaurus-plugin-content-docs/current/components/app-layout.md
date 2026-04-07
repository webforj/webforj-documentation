---
title: AppLayout
sidebar_position: 5
_i18n_hash: 8b9351e865e2651e84f0ae16ef5efc21
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-app-layout" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="applayout" location="com/webforj/component/layout/applayout/AppLayout" top='true'/>

`AppLayout` 组件为您提供了一个现成的页面结构，包括固定的头部和底部、一个可滑入和滑出的抽屉，以及一个可滚动的内容区域。结合在一起，这些部分满足了仪表板、管理面板和大多数多部分界面的布局需求。

<!-- INTRO_END -->

## 特性 {#features}

webforJ App Layout 是一个允许构建常见应用程序布局的组件。

<ul>
    <li>易于使用和自定义</li>
    <li>响应式设计</li>
    <li>多种布局选项</li>
    <li>支持 webforJ 暗黑模式</li>
</ul>

它提供了一个头部、一个底部、一个抽屉和一个内容区域，所有这些都构建在一个响应式组件中，可以轻松定制，以快速构建常见应用程序布局，例如仪表板。头部和底部是固定的，抽屉在视口内滑入和滑出，而内容是可滚动的。

布局的每个部分都是一个 `Div`，可以包含任何有效的 webforJ 控件。为了获得最佳效果，应用程序应包括一个包含 viewport-fit=cover 的视口元标签。该元标签使视口的比例缩放以填满设备显示器。

```java
@AppMeta(name = "viewport", content = "width=device-width, initial-scale=1.0, viewport-fit=cover, user-scalable=no")
```

## 概述 {#overview}

以下代码示例将生成一个具有可折叠侧边栏的应用程序，侧边栏中包含 logo 和用于各种内容选项的选项卡及一个头部。该演示使用了 dwc-icon-button web 组件来创建一个抽屉切换按钮。该按钮具有 data-drawer-toggle 属性，指示 DwcAppLayout 监听来自该组件的点击事件以切换抽屉状态。

<!--vale off-->
<AppLayoutViewer path='/webforj/applayout/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/applayout/AppLayoutView.java'
cssURL='/css/applayout/applayout.css'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java']}/>
<!--vale on-->

## 全宽导航栏 {#full-width-navbar}

默认情况下，AppLayout 在不在屏幕模式下呈现头部和底部。离屏模式意味着头部和底部的位置将移位以适应打开的抽屉。禁用此模式将导致头部和底部占用可用的全部空间，并将抽屉的上下位置移位以适应头部和底部。

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

导航栏可以添加的工具栏数量没有限制。`Toolbar` 是一个水平容器组件，包含一组操作按钮、图标或其他控件。要添加一个额外的工具栏，只需使用 `addToHeader()` 方法添加另一个 `Toolbar` 组件。

以下演示展示了如何使用两个工具栏，第一个工具栏放置抽屉的切换按钮和应用程序的标题。第二个工具栏包含一个辅助导航菜单。

<!--vale off-->
<AppLayoutViewer path='/webforj/applayoutmultipleheaders/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/multipleheaders/AppLayoutMultipleHeadersView.java'
cssURL='/css/applayout/applayout.css'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/multipleheaders/AppLayoutMultipleHeaderContentView.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java']}/>
<!--vale on-->

## 悬浮工具栏 {#sticky-toolbars}

悬浮工具栏是在用户向下滚动时仍然可见的工具栏，但导航栏的高度被折叠，以便为页面内容提供更多空间。通常，这种工具栏包含与当前页面相关的固定导航菜单。

可以使用 CSS 自定义属性 `--dwc-app-layout-header-collapse-height` 以及 `AppLayout.setHeaderReveal()` 选项创建悬浮工具栏。

当调用 `AppLayout.setHeaderReveal(true)` 时，头部将在首次渲染时可见，然后在用户开始向下滚动时隐藏。当用户再次开始向上滚动时，头部将重新显现。

借助 CSS 自定义属性 `--dwc-app-layout-header-collapse-height`，可以控制头部导航栏将隐藏的高度。

<!--vale off-->
<AppLayoutViewer path='/webforj/applayoutstickytoolbar/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/stickytoolbar/AppLayoutStickyToolbarView.java'
cssURL='/css/applayout/applayout.css'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/stickytoolbar/AppLayoutStickyToolbarContentView.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java']}/>
<!--vale on-->

## 移动导航布局 {#mobile-navigation-layout}

底部导航栏可用于提供应用底部导航的不同版本。这种类型的导航在移动应用中尤其流行。

请注意，在以下演示中，抽屉被隐藏。AppLayout 小部件支持三种抽屉位置：`DrawerPlacement.LEFT`、`DrawerPlacement.RIGHT` 和 `DrawerPlacement.HIDDEN`。

与 `AppLayout.setHeaderReveal()` 一样，`AppLayout.setFooterReveal()` 也受支持。当调用 `AppLayout.setFooterReveal(true)` 时，底部将在首次渲染时可见，然后在用户开始向上滚动时隐藏。当用户再次开始向下滚动时，底部将重新显现。

默认情况下，当屏幕宽度为 800px 或更少时，抽屉将切换到弹出模式。这称为断点。弹出模式意味着抽屉将在内容区域上方弹出并带有覆盖层。可以使用 `setDrawerBreakpoint()` 方法配置断点，断点必须是有效的 [媒体查询](https://developer.mozilla.org/en-US/docs/Web/CSS/Media_Queries/Using_media_queries)。

<!--vale off-->
<AppLayoutViewer path='/webforj/applayoutmobiledrawer/?' mobile='true'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/mobiledrawer/AppLayoutMobileDrawerView.java'
cssURL='/css/applayout/applayout.css'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/mobiledrawer/AppLayoutMobileDrawerContentView.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java']}/>
<!--vale on-->

## 抽屉工具 {#drawer-utilities}

`AppLayout` 抽屉工具设计用于在主应用程序布局中提供集成导航和上下文菜单，而独立的 [`Drawer`](https://docs.webforj.com/docs/components/drawer) 组件提供灵活的、独立的滑动面板，可以在应用程序中的任何地方使用，以便添加内容、过滤器或通知。 本节重点介绍 AppLayout 提供的内置抽屉功能和工具。

### 抽屉断点 {#drawer-breakpoint}

默认情况下，当屏幕宽度为 800px 或更少时，抽屉将切换到弹出模式。这称为断点。弹出模式意味着抽屉将在内容区域上方弹出并带有覆盖层。可以使用 `setDrawerBreakpoint()` 方法配置断点，断点必须是有效的媒体查询。

例如，在以下示例中，抽屉断点配置为 500px 或更少。

```java
AppLayout demo = new AppLayout();
demo.setDrawerBreakpoint("(max-width:500px)");
```

### 抽屉标题 {#drawer-title}

`AppLayout` 组件提供了 `addToDrawerTitle()` 方法，用于定义要显示在抽屉头部的自定义标题。

```java
layout.addToDrawerTitle(new Div("菜单"));
```

### 抽屉操作 {#drawer-actions}

`AppLayout` 组件允许您通过 `addToDrawerHeaderActions()` 方法将自定义组件（例如按钮或图标）放置到 **抽屉头部操作区域**。

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

[`AppDrawerToggle`](https://javadoc.io/doc/com.webforj/webforj-applayout/latest/com/webforj/component/layout/applayout/AppDrawerToggle.html) 组件是一个服务端的 webforJ 类，表示一个用于切换 `AppLayout` 中导航抽屉可见性的按钮。它映射到客户端的 `<dwc-app-drawer-toggle>` 元素，并默认样式使其行为像传统的汉堡菜单图标，该行为可以自定义。

### 概述 {#overview-1}

`AppDrawerToggle` 扩展了 `IconButton`，默认使用 Tabler 图标集中的 "menu-2" 图标。它会自动应用 `data-drawer-toggle` 属性以与客户端抽屉行为集成。

```java
// 无需事件注册：
AppLayout layout = new AppLayout();
layout.addToHeader(new AppDrawerToggle());
// 抽屉切换将即刻生效—无需手动事件监听器。
```
## 样式 {#styling}

<TableBuilder name="AppLayout" />
