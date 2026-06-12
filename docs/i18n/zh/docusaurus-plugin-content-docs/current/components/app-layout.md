---
title: AppLayout
sidebar_position: 5
_i18n_hash: 07c685c4fce66e48d5a4e6660b7bc991
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-app-layout" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="applayout" location="com/webforj/component/layout/applayout/AppLayout" top='true'/>

`AppLayout` 组件为您提供了一个准备好的页面结构，配有固定的页眉和页脚、一个可滑出的抽屉以及一个可滚动的内容区域。这些部分共同满足仪表板、管理面板和大多数多部分界面的布局需求。

<!-- INTRO_END -->

## 特性 {#features}

webforJ 应用布局是一个用于构建常见应用布局的组件。

<ul>
    <li>易于使用和自定义</li>
    <li>响应式设计</li>
    <li>多种布局选项</li>
    <li>与 webforJ 深色模式兼容</li>
</ul>

它提供了一个页眉、页脚、抽屉和内容区域，所有这些都内置于一个可响应的组件中，可以轻松自定义，以快速构建常见应用布局，如仪表板。页眉和页脚是固定的，抽屉从视口中滑入和滑出，内容区域可滚动。

布局的每个部分都是一个 `Div` ，可以包含任何有效的 webforJ 控件。为了获得最佳效果，应用程序应包含一个视口元标记，其中包含 viewport-fit=cover。该元标记使视口缩放以填充设备显示屏。

```java
@AppMeta(name = "viewport", content = "width=device-width, initial-scale=1.0, viewport-fit=cover, user-scalable=no")
```

## 概述 {#overview}

以下代码示例将导致一个带有可折叠侧边栏的应用，其中包含一个徽标和多个内容选项的标签，以及一个页眉。演示使用 dwc-icon-button Web 组件来创建一个抽屉切换按钮。该按钮具有 data-drawer-toggle 属性，指示 DwcAppLayout 监听来自该组件的点击事件以切换抽屉状态。

<!--vale off-->
<ComponentDemo
path='/webforj/applayout/content/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/applayout/applayout/AppLayoutView.java',
  'src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
  'src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java',
  'src/main/resources/static/css/applayout/applayout.css',
]}
/>
<!--vale on-->

## 全宽导航栏 {#full-width-navbar}

默认情况下，AppLayout 将页眉和页脚呈现为屏幕外模式。屏幕外模式意味着页眉和页脚位置将被移动以适应打开的抽屉。禁用此模式将导致页眉和页脚占用所有可用空间，并将抽屉的顶部和底部位置移动以适应页眉和页脚。

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
  'src/main/resources/static/css/applayout/applayout.css',
]}
/>
<!--vale on-->

## 多个工具栏 {#multiple-toolbars}

导航栏对您可以添加的工具栏数量没有限制。`Toolbar` 是一个水平容器组件，包含一组操作按钮、图标或其他控件。要添加额外的工具栏，只需使用 `addToHeader()` 方法添加另一个 `Toolbar` 组件。

以下演示展示了如何使用两个工具栏，第一个工具栏放置了抽屉的切换按钮和应用的标题，第二个工具栏放置了一个二级导航菜单。

<!--vale off-->
<ComponentDemo
path='/webforj/applayoutmultipleheaders/content/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/applayout/multipleheaders/AppLayoutMultipleHeadersView.java',
  'src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
  'src/main/java/com/webforj/samples/views/applayout/multipleheaders/AppLayoutMultipleHeaderContentView.java',
  'src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java',
  'src/main/resources/static/css/applayout/applayout.css',
]}
/>
<!--vale on-->

## 粘性工具栏 {#sticky-toolbars}

粘性工具栏是一种在用户向下滚动时保持可见的工具栏，但导航栏高度已缩小，以便为页面内容提供更多空间。通常，这种工具栏包含与当前页面相关的固定导航菜单。

可以使用 CSS 自定义属性 `--dwc-app-layout-header-collapse-height` 和 `AppLayout.setHeaderReveal()` 选项创建粘性工具栏。

当调用 `AppLayout.setHeaderReveal(true)` 时，页眉在首次呈现时可见，并在用户开始向下滚动时隐藏。一旦用户再次开始向上滚动，页眉将重新显现。

借助 CSS 自定义属性 `--dwc-app-layout-header-collapse-height`，可以控制将隐藏多少页眉导航栏。

<!--vale off-->
<ComponentDemo
path='/webforj/applayoutstickytoolbar/content/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/applayout/stickytoolbar/AppLayoutStickyToolbarView.java',
  'src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
  'src/main/java/com/webforj/samples/views/applayout/stickytoolbar/AppLayoutStickyToolbarContentView.java',
  'src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java',
  'src/main/resources/static/css/applayout/applayout.css',
]}
/>
<!--vale on-->

## 移动导航布局 {#mobile-navigation-layout}

底部导航栏可用于在应用底部提供不同版本的导航。这种类型的导航在移动应用中特别受欢迎。

请注意，抽屉在以下演示中是隐藏的。AppLayout 小部件支持三种抽屉位置：`DrawerPlacement.LEFT`、`DrawerPlacement.RIGHT` 和 `DrawerPlacement.HIDDEN`。

与 `AppLayout.setHeaderReveal()` 一样，`AppLayout.setFooterReveal()` 也得到了支持。当调用 `AppLayout.setFooterReveal(true)` 时，页脚在首次呈现时可见，然后在用户开始向上滚动时隐藏。一旦用户再次开始向下滚动，页脚将重新显现。

默认情况下，当屏幕宽度为 800px 或更低时，抽屉将切换到弹出模式。这称为断点。弹出模式意味着抽屉将在内容区域上方弹出，并带有覆盖。可以通过使用 `setDrawerBreakpoint()` 方法配置断点，并且断点必须是有效的 [媒体查询](https://developer.mozilla.org/en-US/docs/Web/CSS/Media_Queries/Using_media_queries)。

<!--vale off-->
<ComponentDemo
path='/webforj/applayoutmobiledrawer/'
frame='mobile'
files={[
  'src/main/java/com/webforj/samples/views/applayout/mobiledrawer/AppLayoutMobileDrawerView.java',
  'src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
  'src/main/java/com/webforj/samples/views/applayout/mobiledrawer/AppLayoutMobileDrawerContentView.java',
  'src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java',
  'src/main/resources/static/css/applayout/applayout.css',
]}
/>
<!--vale on-->

## 抽屉工具 {#drawer-utilities}

`AppLayout` 抽屉工具旨在为主应用布局中的集成导航和上下文菜单提供服务，而独立的 [`Drawer`](https://docs.webforj.com/docs/components/drawer) 组件则提供灵活的、独立的滑动面板，可在应用中的任何地方用于附加内容、过滤器或通知。本节重点介绍 AppLayout 提供的内置抽屉功能和工具。

### 抽屉断点 {#drawer-breakpoint}

默认情况下，当屏幕宽度为 800px 或更低时，抽屉将切换到弹出模式。这称为断点。弹出模式意味着抽屉将在内容区域上方弹出，并带有覆盖。可以通过使用 `setDrawerBreakpoint()` 方法配置断点，并且断点必须是有效的媒体查询。

例如，在以下示例中，抽屉断点被配置为 500px 或更低。

```java
AppLayout demo = new AppLayout();
demo.setDrawerBreakpoint("(max-width:500px)");
```

### 抽屉标题 {#drawer-title}

`AppLayout` 组件提供了一个 `addToDrawerTitle()` 方法，用于定义自定义标题以显示在抽屉页眉中。

```java
layout.addToDrawerTitle(new Div("菜单"));
```

### 抽屉操作 {#drawer-actions}

`AppLayout` 组件允许您使用 `addToDrawerHeaderActions()` 方法将自定义组件，如按钮或图标，放置到 **抽屉页眉操作区域** 中。

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

抽屉操作出现在抽屉页眉的 **右对齐部分**。

<!--vale off-->
<ComponentDemo
path='/webforj/applayoutdrawerutility/content/Dashboard/'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/applayout/applayoutdrawerutility/AppLayoutDrawerUtilityView.java',
  'src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
  'src/main/java/com/webforj/samples/views/applayout/applayoutdrawerutility/AppLayoutDrawerUtilityContentView.java',
  'src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java',
  'src/main/resources/static/css/applayout/applayout.css',
]}
/>
<!--vale on-->


## `AppDrawerToggle` <DocChip chip='since' label='24.12' /> {#appdrawertoggle-docchip-chipsince-label2412-}

[`AppDrawerToggle`](https://javadoc.io/doc/com.webforj/webforj-applayout/latest/com/webforj/component/layout/applayout/AppDrawerToggle.html) 组件是一个服务器端 webforJ 类，表示一个用于切换 `AppLayout` 中导航抽屉可见性的按钮。它映射到客户端的 `<dwc-app-drawer-toggle>` 元素，默认样式类似于传统的汉堡菜单图标，这种行为可以自定义。

### 概述 {#overview-1}

`AppDrawerToggle` 扩展了 `IconButton`，并默认使用 Tabler 图标集中的 "menu-2" 图标。它自动应用 `data-drawer-toggle` 属性，以与客户端抽屉行为集成。

```java
// 不需要事件注册：
AppLayout layout = new AppLayout();
layout.addToHeader(new AppDrawerToggle());
// 抽屉切换将开箱即用，无需手动事件监听器。
```
## 样式 {#styling}

<TableBuilder name="AppLayout" />
