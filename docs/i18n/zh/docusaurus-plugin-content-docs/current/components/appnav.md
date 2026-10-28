---
title: AppNav
sidebar_position: 6
sidebar_class_name: new-content
description: >-
  Build hierarchical side navigation menus with AppNav and AppNavItem, linking
  to routes, registered views, or external URLs.
_i18n_hash: 4f5eaa3e9738a7f959de22544589d240
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-app-nav" />
<DocChip chip="name" label="dwc-app-nav-item" />
<DocChip chip="name" label="dwc-app-nav-label" />
<DocChip chip='since' label='24.12' />
<JavadocLink type="appnav" location="com/webforj/component/layout/appnav/AppNav" top='true'/>

`AppNav` 组件从 `AppNavItem` 条目创建一个侧边导航菜单。项目可以链接到内部视图或外部资源，可以在父项下嵌套以形成层次菜单，并可以携带图标、徽章或其他组件，以便用户一目了然地获得更多上下文。

<!-- INTRO_END -->

## 添加和嵌套项目 {#adding-and-nesting-items}

`AppNavItem` 实例用于填充 `AppNav` 结构。这些项目可以是简单链接或包含子项的嵌套组标题。没有链接的组标题作为可展开的容器。

使用 `addItem()` 在导航中添加项目：

```java
AppNavItem dashboard = new AppNavItem("仪表板", "/dashboard");
AppNavItem admin = new AppNavItem("管理员");
admin.addItem(new AppNavItem("用户", "/admin/users"));
admin.addItem(new AppNavItem("设置", "/admin/settings"));

AppNav nav = new AppNav();
nav.addItem(dashboard);
nav.addItem(admin);
```

:::tip 链接组项目
导航树中的顶层项目通常旨在可展开，而不是可点击的链接。在这样的项目上设置 `path` 可能会让用户感到困惑，因为他们期望它们能够显示子项，而不是导航到其他地方。

如果您希望组标题触发自定义操作（例如打开外部文档），请保持组路径为空，而不是在项目的后缀中添加交互式控件，如 [`IconButton`](./icon#icon-buttons)。这样可保持用户体验一致且简洁。
:::

<!--vale off-->
<ComponentDemo
path='/webforj/appnav/Social'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/appnav/AppNavView.java',
  'src/main/java/com/webforj/samples/views/appnav/AppNavPageView.java',
]}
/>
<!--vale on-->

## 链接项目 {#linking-items}

每个 `AppNavItem` 可以导航到内部视图或外部链接。您可以使用静态路径或注册的视图类来定义此链接。

### 静态路径 {#static-paths}

使用字符串路径直接定义链接：

```java
AppNavItem docs = new AppNavItem("文档", "/docs");
AppNavItem help = new AppNavItem("帮助", "https://support.example.com");
```

### 注册的视图 {#registered-views}

如果您的视图已在 [路由器](../routing/overview) 中注册，您可以传递类而不是硬编码的 URL：

```java
AppNavItem settings = new AppNavItem("设置", SettingsView.class);
```

如果您的注解路由支持 [路由参数](../routing/route-patterns#named-parameters)，您也可以传递 `ParametersBag`：

```java
ParametersBag params = ParametersBag.of("id=123");
AppNavItem advanced = new AppNavItem("用户", UserView.class, params);
```

### 带查询参数 {#with-query-parameters}

传递 `ParametersBag` 以包含查询字符串：

```java
ParametersBag params = ParametersBag.of("param1=value1&param2=value2");
AppNavItem advanced = new AppNavItem("高级", SettingsView.class, params);
advanced.setQueryParameters(params);
```

## 目标行为 {#target-behavior}

使用 `setTarget()` 控制链接的打开方式。这对于外部链接或弹出视图尤其有用。

- **`SELF`**（默认）：在当前视图中打开。
- **`BLANK`**：在新标签或新窗口中打开。
- **`PARENT`**：在父浏览上下文中打开。
- **`TOP`**：在顶级浏览上下文中打开。

```java
AppNavItem help = new AppNavItem("帮助", "https://support.example.com");
help.setTarget(AppNavItem.NavigationTarget.BLANK);
```

## 前缀和后缀 {#prefix-and-suffix}

`AppNavItem` 支持前缀和后缀组件。使用这些组件提供视觉清晰度，例如图标、徽章或按钮。

- **前缀**：出现在标签之前，适用于图标。
- **后缀**：出现在标签之后，非常适合徽章或操作。

```java
AppNavItem notifications = new AppNavItem("警报");
notifications.setPrefixComponent(TablerIcon.create("alert"));
notifications.setSuffixComponent(TablerIcon.create("link"));
```

## 自动打开组 {#auto-opening-groups}

在 `AppNav` 组件上使用 `setAutoOpen(true)`，在应用程序刷新时自动展开嵌套组。

```java
nav.setAutoOpen(true);
```

## 部分标签 <DocChip chip='since' label='26.02' /> {#section-labels}

`AppNavLabel` 是一个非交互式标题，用于给一系列项目命名。标签适用于后面跟随的每个项目，到下一个标签或菜单结束为止，这使得一长串顶级项目看起来像几个命名组而不必嵌套它们。

通过 `add()` 而不是 `addItem()` 添加标签，调用的顺序定义部分：

```java
AppNav nav = new AppNav();
nav.addItem(new AppNavItem("仪表板", DashboardView.class, TablerIcon.create("layout-dashboard")));

nav.add(new AppNavLabel("分析"));
nav.addItem(new AppNavItem("概述", OverviewView.class));
nav.addItem(new AppNavItem("报告", ReportsView.class));

nav.add(new AppNavLabel("其他"));
nav.addItem(new AppNavItem("设置", SettingsView.class));
```

当其部分没有可见项目时，导航会自动隐藏标签，因此当 [搜索](#search) 将其项目过滤掉或当所有项目都被 [固定](#pinning) 到菜单顶部时，标签会消失。

### 标签前缀和后缀 {#label-prefix-and-suffix}

与 `AppNavItem` 类似，标签也支持前缀和后缀组件。将前缀传递给构造函数，或事后设置任何一个：

```java
AppNavLabel analytics = new AppNavLabel("分析", TablerIcon.create("chart-pie"));
analytics.setSuffixComponent(new Badge().setText("2").setTheme(BadgeTheme.WARNING));

nav.add(analytics);
```

下面的例子将菜单分为三个标签，第一项携带 [`Icon`](./icon) 前缀和 [`Badge`](./badge) 后缀。仪表板位于第一个标签之上，因此它不属于任何部分。

<ComponentDemo
path='/webforj/appnavlabel/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/appnav/AppNavLabelView.java',
  'src/main/java/com/webforj/samples/views/appnav/AppNavLabelPageView.java',
]}
/>

## 固定 <DocChip chip='since' label='26.01' /> {#pinning}

固定功能允许用户将他们最常访问的项目提升到导航顶部的组中，这样即使深层菜单仍然可以在一次点击下保留一短列表的收藏。默认情况下是关闭的。通过固定配置打开它：

```java
AppNav nav = new AppNav();
nav.getPinning().setEnabled(true);
```

启用后，每个可导航的叶子项目都会显示一个固定切换功能。切换功能在悬停和键盘焦点时显示，因此在没有鼠标的情况下也可到达。激活它将项目移入导航顶部的固定组。

有一些规则规定了可以固定的内容及其组的行为：

- 只有可导航的叶子项目可以被固定。组标题（具有子项的项目）永远无法固定。
- 只有在某个项目被固定后，固定组才会出现。当最后一个项目未被固定时，该组会消失。
- 取消固定将项目返回到其确切的原始位置，包括嵌套在多个级别深的组中的项目。
- 项目是移动的，而不是复制的，因此任何前缀或后缀内容以及附加的监听器在其位于固定组时仍然可以正常工作。

下面的演示启用了固定功能，带有自定义组标题，仪表板在加载时固定。悬停或聚焦于叶子项目以显示其固定切换功能。

<ComponentDemo
path='/webforj/appnavpinning/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/appnav/AppNavPinningView.java',
  'src/main/java/com/webforj/samples/views/appnav/AppNavPinningPageView.java',
]}
/>

### 开始时固定项目 {#starting-an-item-pinned}

通过设置其固定状态来让项目在固定组中开始。使用 `isPinned()` 读取当前状态。

```java
AppNavItem reports = new AppNavItem("报告", "/reports");
reports.setPinned(true);
```

:::info 固定必须启用
`setPinned(true)` 仅在通过 `getPinning().setEnabled(true)` 在 `AppNav` 上启用固定时生效。没有它，该调用无效。
:::

### 固定组标题 {#pinned-group-title}

固定组默认标记为 `Pinned`。将其更改为适合您的应用程序：

```java
nav.getPinning().setTitle("收藏夹");
```

### 固定键 {#pin-keys}

每个可固定的项目都有一个键，用于识别它以便持久化和 [固定事件](#reacting-to-pin-changes)。当您不设置一个时，键将回退到项目的路径，因此 `getPinKey()` 总是返回一个可用的值。

```java
AppNavItem reports = new AppNavItem("报告", "/reports");
reports.setPinKey("reports");
```

在路径可以在运行时更改时设置显式键。稳定的键确保即使在 URL 移动的情况下，固定也保持与正确项目匹配。

### 自动保存到本地存储 {#autosave}

除非您持久化它们，否则固定仅在当前页面视图中有效。自动保存是最简单的选项：它将固定项目的集合存储在浏览器的本地存储中，并在重新加载时恢复它们。它默认关闭。它需要在组件上有一个稳定的 `id`（或名称）作为存储键，而 `AppNav(String id)` 构造函数是设置一个的方便方法：

```java
AppNav nav = new AppNav("main-nav"); // 为自动保存提供一个稳定的存储键
nav.getPinning().setAutosave(true);
```

:::info 自动保存需要一个 id
如果组件上没有 `id`（或名称），则自动保存默默无闻地无效，因为它没有稳定的键可供存储。因此持久性是每个浏览器的，因此固定项目不会在用户的其他设备或浏览器中进行跟随。
:::

### 自定义持久性 {#custom-persistence}

对于您控制的持久性，例如基于用户的服务器持久性，关闭自动保存，并通过 [固定事件](#reacting-to-pin-changes) 和 `setPinned` 自行管理：

```java
nav.getPinning().setAutosave(false);

// 每当更改时持久化当前的固定键集
nav.onPin(event -> savePins(event.getKeys()));

// 在加载时，恢复每个保存的键
restoredKeys.forEach(key -> findItem(key).setPinned(true));
```

### 响应固定变化 {#reacting-to-pin-changes}

每当项目被固定或取消固定时，固定事件都会触发。它携带改变的项目、其键、新的固定状态以及所有固定键的完整有序集合：

```java
nav.onPin(event -> {
  AppNavItem item = event.getItem(); // 改变的项目，如果不再在导航中则为 null
  boolean pinned = event.isPinned();
  String key = event.getKey();
  List<String> all = event.getKeys(); // 每个固定键，按固定顺序
});
```

`getItem()` 通过匹配其固定键解析项目，当项目不再是导航的一部分时返回 `null`。

### 固定图标 {#pin-icons}

在项目未固定时，切换使用内置的 `dwc:pin` 图标，而在项目固定时使用 `dwc:pinned-off` 图标。通过 `setUnpinnedIcon` 和 `setPinnedIcon` 更换为您自己的图标，这些方法接收任何 `IconDefinition`：

```java
nav.getPinning()
   .setUnpinnedIcon(TablerIcon.create("pin"))
   .setPinnedIcon(TablerIcon.create("pinned-off"));
```

### 触摸屏上的固定切换 {#pin-toggle-on-touchscreens}

触摸屏没有悬停来显示固定，因此切换在触摸屏上默认隐藏。通过 `setTouchVisible(true)` 使其在触摸屏上可见且可点击：

```java
nav.getPinning().setTouchVisible(true);
```

## 搜索 <DocChip chip='since' label='26.01' /> {#search}

搜索字段通过项目标签在用户输入时过滤菜单。默认情况下关闭。您可以通过搜索配置显示它并为其提供占位符：

```java
nav.getSearch().setFieldVisible(true);
nav.getSearch().setPlaceholder("搜索");
```

当用户输入时，导航根据标签过滤项目，打开任何包含匹配项的组，并在没有匹配时显示空消息。固定的快速访问链接在搜索时保持可见，因此用户的收藏在过滤中仍然只需一次点击即可到达。

<ComponentDemo
path='/webforj/appnavsearch/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/appnav/AppNavSearchView.java',
  'src/main/java/com/webforj/samples/views/appnav/AppNavSearchPageView.java',
]}
/>

### 空消息 {#search-empty-message}

设置在搜索没有结果时显示的消息。纯文本将作为文本呈现：

```java
nav.getSearch().setEmptyMessage("没有找到项目");
```

### 从您自己的字段驱动搜索 {#custom-search-box}

隐藏内置字段，从您自己的输入源提供过滤。通过 `setTerm` 推送当前术语：

```java
nav.getSearch().setFieldVisible(false);

myField.onModify(event -> nav.getSearch().setTerm(event.getText()));
```

要响应用户在内置字段中输入的内容，请监听搜索事件：

```java
nav.onSearch(event -> log(event.getTerm()));
```

## 样式化 `AppNavItem` {#styling-appnavitem}

<TableBuilder name="AppNavItem" />
