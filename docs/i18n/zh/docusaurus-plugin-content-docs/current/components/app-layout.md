---
title: AppLayout
sidebar_position: 5
_i18n_hash: 0aea09dee535e578082dd6df642503d4
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-app-layout" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="applayout" location="com/webforj/component/layout/applayout/AppLayout" top='true'/>

`AppLayout` 组件为您提供了一个现成的页面结构，具有固定的头部和底部，一个滑入滑出的抽屉，以及一个可滚动的内容区域。这些部分共同满足仪表板、管理面板和大多数多部分界面的布局需求。

<!-- INTRO_END -->

## 特性 {#features}

webforJ App Layout 是一个可以构建常见应用布局的组件。

<ul>
    <li>易于使用和定制</li>
    <li>响应式设计</li>
    <li>多种布局选项</li>
    <li>支持 webforJ 黑暗模式</li>
</ul>

它提供了一个头部、底部、抽屉和内容区域，所有这些都构建在一个响应式组件中，可以轻松定制以快速构建常见应用布局，如仪表板。头部和底部是固定的，抽屉在视口中滑入和滑出，内容是可滚动的。

布局的每个部分都是一个 `Div`，可以包含任何有效的 webforJ 控件。为了获得最佳效果，应用程序应包含一个 viewport meta 标签，该标签包含 viewport-fit=cover。该 meta 标签使视口缩放以填满设备显示屏。

```java
@AppMeta(name = "viewport", content = "width=device-width, initial-scale=1.0, viewport-fit=cover, user-scalable=no")
```

## 概述 {#overview}

以下代码示例将生成一个具有可折叠侧边栏的应用程序，该侧边栏包含一个徽标和多个内容选项的标签以及一个头部。演示使用 dwc-icon-button web 组件创建抽屉切换按钮。该按钮具有 data-drawer-toggle 属性，指示 DwcAppLayout 监听来自该组件的单击事件以切换抽屉状态。

<!--vale off-->
<AppLayoutViewer path='/webforj/applayout/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/applayout/AppLayoutView.java'
cssURL='/css/applayout/applayout.css'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java']}/>
<!--vale on-->

## 全宽导航栏 {#full-width-navbar}

默认情况下，AppLayout 在离屏模式下呈现头部和底部。离屏模式意味着头部和底部的位置将被移位，以适应打开的抽屉。禁用此模式将使头部和底部占用全部可用空间，并使抽屉的顶部和底部位置与头部和底部匹配。

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

导航栏可以添加无限数量的工具栏。`Toolbar` 是一个水平容器组件，包含一组操作按钮、图标或其他控件。要添加额外的工具栏，只需使用 `addToHeader()` 方法添加另一个 `Toolbar` 组件。

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

粘性工具栏是指用户向下滚动时仍然可见的工具栏，但导航栏的高度会折叠，以为页面内容提供更多空间。通常，这种工具栏包含与当前页面相关的固定导航菜单。

可以使用 CSS 自定义属性 `--dwc-app-layout-header-collapse-height` 以及 `AppLayout.setHeaderReveal()` 选项创建粘性工具栏。

当 `AppLayout.setHeaderReveal(true)` 被调用时，头部将在首次渲染时可见，然后在用户向下滚动时隐藏。一旦用户再次向上滚动，头部将被显示。

借助 CSS 自定义属性 `--dwc-app-layout-header-collapse-height`，可以控制有多少导航栏的头部将被隐藏。

<!--vale off-->
<AppLayoutViewer path='/webforj/applayoutstickytoolbar/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/stickytoolbar/AppLayoutStickyToolbarView.java'
cssURL='/css/applayout/applayout.css'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/stickytoolbar/AppLayoutStickyToolbarContentView.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java']}/>
<!--vale on-->

## 移动导航布局 {#mobile-navigation-layout}

底部导航栏可以用来提供应用程序底部的不同版本的导航。这种导航在移动应用中特别受欢迎。

请注意，在以下演示中，抽屉是隐藏的。AppLayout 小部件支持三种抽屉位置：`DrawerPlacement.LEFT`、`DrawerPlacement.RIGHT` 和 `DrawerPlacement.HIDDEN`。

与 `AppLayout.setHeaderReveal()` 相同，`AppLayout.setFooterReveal()` 也得到了支持。当调用 `AppLayout.setFooterReveal(true)` 时，底部在首次渲染时可见，然后在用户开始向上滚动时隐藏。一旦用户再次开始向下滚动，底部将被显示。

默认情况下，当屏幕宽度为 800px 或更小时，抽屉将切换到弹出模式。这称为断点。弹出模式意味着抽屉将在内容区域顶部弹出并覆盖。可以使用 `setDrawerBreakpoint()` 方法配置断点，并且断点必须是有效的 [媒体查询](https://developer.mozilla.org/en-US/docs/Web/CSS/Media_Queries/Using_media_queries)。

<!--vale off-->
<AppLayoutViewer path='/webforj/applayoutmobiledrawer/?' mobile='true'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/mobiledrawer/AppLayoutMobileDrawerView.java'
cssURL='/css/applayout/applayout.css'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/mobiledrawer/AppLayoutMobileDrawerContentView.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java']}/>
<!--vale on-->

## 抽屉工具 {#drawer-utilities}

`AppLayout` 抽屉工具旨在集成导航和上下文菜单到主应用布局中，而独立的 [`Drawer`](https://docs.webforj.com/docs/components/drawer) 组件提供灵活的独立滑动面板，可以在应用程序中的任何地方使用，以添加额外的内容、过滤器或通知。本节重点介绍 AppLayout 提供的内置抽屉特性和工具。

### 抽屉断点 {#drawer-breakpoint}

默认情况下，当屏幕宽度为 800px 或更小时，抽屉将切换到弹出模式。这被称为断点。弹出模式意味着抽屉将在内容区域顶部弹出并覆盖。可以使用 `setDrawerBreakpoint()` 方法配置断点，并且断点必须是有效的媒体查询。

例如，在以下示例中，抽屉断点配置为 500px 或更小。

```java
AppLayout demo = new AppLayout();
demo.setDrawerBreakpoint("(max-width:500px)");
```

### 抽屉标题 {#drawer-title}

`AppLayout` 组件提供了一个 `addToDrawerTitle()` 方法，用于定义在抽屉头部显示的自定义标题。

```java
layout.addToDrawerTitle(new Div("菜单"));
```

### 抽屉操作 {#drawer-actions}

`AppLayout` 组件允许您使用 `addToDrawerHeaderActions()` 方法将自定义组件，如按钮或图标，放入 **抽屉头部操作区域**。

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

抽屉操作出现在抽屉头部的 **右对齐区域**。

<!--vale off-->
<AppLayoutViewer path='/webforj/applayoutdrawerutility/content/Dashboard/?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/applayoutdrawerutility/AppLayoutDrawerUtilityView.java'
cssURL='/css/applayout/applayout.css'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/applayoutdrawerutility/AppLayoutDrawerUtilityContentView.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java']}/>
<!--vale on-->

## `AppDrawerToggle` <DocChip chip='since' label='24.12' /> {#appdrawertoggle-docchip-chipsince-label2412-}

[`AppDrawerToggle`](https://javadoc.io/doc/com.webforj/webforj-applayout/latest/com/webforj/component/layout/applayout/AppDrawerToggle.html) 组件是一个服务器端 webforJ 类，代表一个用于切换 `AppLayout` 中导航抽屉可见性的按钮。它映射到客户端的 `<dwc-app-drawer-toggle>` 元素，并默认样式与传统汉堡菜单图标类似，该行为可以自定义。

### 概述 {#overview-1}

`AppDrawerToggle` 扩展了 `IconButton`，并默认使用 Tabler 图标集中的 "menu-2" 图标。它会自动应用 `data-drawer-toggle` 属性以集成客户端的抽屉行为。

```java
// 不需要事件注册：
AppLayout layout = new AppLayout();
layout.addToHeader(new AppDrawerToggle());
// 抽屉切换将工作，开箱即用，无需手动事件监听器。
```
## 样式 {#styling}

<TableBuilder name="AppLayout" />
