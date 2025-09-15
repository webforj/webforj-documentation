---
title: AppLayout
sidebar_position: 5
_i18n_hash: e6da714fff4ce713ceb5b486b8ab0026
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-app-layout" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="applayout" location="com/webforj/component/layout/applayout/AppLayout" top='true'/>

`AppLayout` 是一个综合性的响应式布局组件，提供了页眉、页脚、抽屉和内容区域。 页眉和页脚是固定的，抽屉在视口中滑入和滑出，内容是可滚动的。

该组件可用于构建常见的应用布局，例如仪表盘。

## 特性 {#features}

webforJ 应用布局是一个允许构建常见应用布局的组件。

<ul>
    <li>易于使用和定制</li>
    <li>响应式设计</li>
    <li>多种布局选项</li>
    <li>支持 webforJ 黑暗模式</li>
</ul>

它提供了一个页眉、页脚、抽屉和内容区域，所有这些都构建在一个响应式组件中，用户可以轻松定制，以快速构建常见的应用布局，比如仪表盘。 页眉和页脚是固定的，抽屉在视口中滑入和滑出，内容是可滚动的。

布局的每个部分都是一个 `Div`，可以包含任何有效的 webforJ 控件。为了获得最佳效果，应用应包含一个包含 viewport-fit=cover 的视口元标签。该元标签使得视口缩放以填充设备显示。

```java
@AppMeta(name = "viewport", content = "width=device-width, initial-scale=1.0, viewport-fit=cover, user-scalable=no")
```

## 概述 {#overview}

以下代码示例将创建一个具有可折叠侧边栏的应用，该侧边栏包含一个徽标和各种内容选项的标签以及一个页眉。演示使用 dwc-icon-button web 组件创建抽屉切换按钮。该按钮具有 data-drawer-toggle 属性，指示 DwcAppLayout 监听来自该组件的点击事件以切换抽屉状态。

<AppLayoutViewer path='/webforj/applayout/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/applayout/AppLayoutView.java'
cssURL='/css/applayout/applayout.css'
/>

## 全宽导航栏 {#full-width-navbar}

默认情况下，AppLayout 会以离屏模式渲染页眉和页脚。离屏模式意味着页眉和页脚的位置将被移动以适应打开的抽屉。禁用此模式将使页眉和页脚占用全部可用空间，并将抽屉的顶部和底部位置移动以适应页眉和页脚。

```java showLineNumbers
AppLayout myApp = new AppLayout();

myApp.setHeaderOffscreen(false);
myApp.setFooterOffscreen(false);
```

<AppLayoutViewer path='/webforj/applayoutfullnavbar/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/fullnavbar/AppLayoutFullNavbarView.java'
cssURL='/css/applayout/applayout.css'/>

## 多个工具栏 {#multiple-toolbars}

导航栏可以添加无限数量的工具栏。`Toolbar` 是一个水平容器组件，可以容纳一组操作按钮、图标或其他控件。要添加一个额外的工具栏，只需使用 `addToHeader()` 方法添加另一个 `Toolbar` 组件。

以下演示展示了如何使用两个工具栏，第一个工具栏包含抽屉的切换按钮和应用的标题。第二个工具栏包含一个二级导航菜单。

<AppLayoutViewer path='/webforj/applayoutmultipleheaders/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/multipleheaders/AppLayoutMultipleHeadersView.java'
cssURL='/css/applayout/applayout.css'/>

## 固定工具栏 {#sticky-toolbars}

固定工具栏是指当用户向下滚动时，仍然在页面顶部可见的工具栏，但导航栏高度被折叠，以为页面内容提供更多空间。通常这种工具栏包含与当前页面相关的固定导航菜单。

可以使用 CSS 自定义属性 `--dwc-app-layout-header-collapse-height` 和 `AppLayout.setHeaderReveal()` 选项创建固定工具栏。

当调用 `AppLayout.setHeaderReveal(true)` 时，页眉将在首次呈现时可见，然后在用户开始向下滚动时隐藏。一旦用户开始向上滚动，页眉将再次显示。

借助 CSS 自定义属性 `--dwc-app-layout-header-collapse-height`，可以控制页眉导航栏的隐藏程度。

<AppLayoutViewer path='/webforj/applayoutstickytoolbar/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/stickytoolbar/AppLayoutStickyToolbarView.java'
cssURL='/css/applayout/applayout.css'/>

## 移动导航布局 {#mobile-navigation-layout}

底部导航栏可用于在应用底部提供不同版本的导航。这种类型的导航在移动应用中尤其流行。

请注意，在以下演示中，抽屉被隐藏。AppLayout 组件支持三种抽屉位置：`DrawerPlacement.LEFT`、`DrawerPlacement.RIGHT` 和 `DrawerPlacement.HIDDEN`。

与 `AppLayout.setHeaderReveal()` 相同，`AppLayout.setFooterReveal()` 也被支持。当调用 `AppLayout.setFooterReveal(true)` 时，页脚将在首次呈现时可见，随后在用户开始向上滚动时隐藏。一旦用户再次向下滚动，页脚将被显示。

默认情况下，当屏幕宽度为 800px 或更小时，抽屉会切换到弹出模式。这称为断点。弹出模式意味着抽屉将覆盖在内容区域之上并带有叠加层。可通过使用 `setDrawerBreakpoint()` 方法配置断点，且断点必须是有效的 [媒体查询](https://developer.mozilla.org/en-US/docs/Web/CSS/Media_Queries/Using_media_queries)。

<AppLayoutViewer path='/webforj/applayoutmobiledrawer/?' mobile='true'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/mobiledrawer/AppLayoutMobileDrawerView.java'
cssURL='/css/applayout/applayout.css'
/>

## 抽屉工具 {#drawer-utilities}

`AppLayout` 抽屉工具的设计用于在主应用布局中集成导航和上下文菜单，同时独立的 [`Drawer`](https://docs.webforj.com/docs/components/drawer) 组件提供灵活的独立滑动面板，可以在应用的任何位置用于额外的内容、过滤器或通知。本节专注于 AppLayout 提供的内置抽屉功能和工具。

### 抽屉断点 {#drawer-breakpoint}

默认情况下，当屏幕宽度为 800px 或更小时，抽屉会切换到弹出模式。这称为断点。弹出模式意味着抽屉将覆盖在内容区域之上并带有叠加层。可通过使用 `setDrawerBreakpoint()` 方法配置断点，且断点必须是有效的媒体查询。

例如，在以下示例中，抽屉断点被配置为 500px 或更小。

```java
AppLayout demo = new AppLayout();
demo.setDrawerBreakpoint("(max-width:500px)");
```

### 抽屉标题 {#drawer-title}

`AppLayout` 组件提供了 `addToDrawerTitle()` 方法用于定义在抽屉标题中显示的自定义标题。

```java
layout.addToDrawerTitle(new Div("菜单"));
```

### 抽屉操作 {#drawer-actions}

`AppLayout` 组件允许您将自定义组件（如按钮或图标）放置在 **抽屉头部操作区**，使用 `addToDrawerHeaderActions()` 方法。

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

<AppLayoutViewer path='/webforj/applayoutdrawerutility/content/Dashboard/?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/applayoutdrawerutility/AppLayoutDrawerUtilityView.java'
cssURL='/css/applayout/applayout.css'
/>

## `AppDrawerToggle` <DocChip chip='since' label='24.12' /> {#appdrawertoggle-docchip-chipsince-label2412-}

[`AppDrawerToggle`](https://javadoc.io/doc/com.webforj/webforj-applayout/latest/com/webforj/component/layout/applayout/AppDrawerToggle.html) 组件是一个服务器端的 webforJ 类，表示一个用于切换 `AppLayout` 中导航抽屉可见性的按钮。它映射到客户端的 `<dwc-app-drawer-toggle>` 元素，并默认样式以像传统的汉堡菜单图标一样行为，此行为可以自定义。

### 概述 {#overview-1}

`AppDrawerToggle` 继承自 `IconButton`，并默认使用 Tabler 图标集中的 "menu-2" 图标。它会自动应用 `data-drawer-toggle` 属性以集成客户端的抽屉行为。

```java
// 不需要事件注册：
AppLayout layout = new AppLayout();
layout.addToHeader(new AppDrawerToggle());
// 抽屉切换将开箱即用，无需手动事件监听器。
```
## 样式 {#styling}

<TableBuilder name="AppLayout" />
