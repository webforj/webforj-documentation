---
title: AppNav
sidebar_position: 6
sidebar_class_name: new-content
description: >-
  Build hierarchical side navigation menus with AppNav and AppNavItem, linking
  to routes, registered views, or external URLs.
_i18n_hash: afb61d8d44c3f5dcb03f533954baafc1
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-app-nav" />
<DocChip chip="name" label="dwc-app-nav-item" />
<DocChip chip="name" label="dwc-app-nav-label" />
<DocChip chip='since' label='24.12' />
<JavadocLink type="appnav" location="com/webforj/component/appnav/AppNav" top='true'/>

`AppNav` 组件通过 `AppNavItem` 条目创建侧边导航菜单。这些项目可以链接到内部视图或外部资源，可以依附于父项以形成层次菜单，并携带图标、徽章或其他组件，以便用户一目了然。

<!-- INTRO_END -->

## 添加和嵌套项目 {#adding-and-nesting-items}

`AppNavItem` 实例用于填充 `AppNav` 结构。这些项目可以是简单的链接或包含子项的嵌套组标题。没有链接的组标题充当可展开的容器。

使用 `addItem()` 将项目包含在导航中：

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
导航树中的顶层项目通常旨在可展开，而不是可点击链接。在此类项目上设置 `path` 可能会让用户感到困惑，他们期待这些项目显示子项而不是导航到其他位置。

如果您希望组标题触发自定义操作（例如打开外部文档），请保持组路径为空，而是在项目的后缀中添加交互控件，例如 [`IconButton`](./icon#icon-buttons)。这使用户体验保持一致和干净。
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

每个 `AppNavItem` 都可以导航到内部视图或外部链接。您可以使用静态路径或注册的视图类来定义此功能。

### 静态路径 {#static-paths}

使用字符串路径直接定义链接：

```java
AppNavItem docs = new AppNavItem("文档", "/docs");
AppNavItem help = new AppNavItem("帮助", "https://support.example.com");
```

### 注册视图 {#registered-views}

如果您的视图已在 [路由器](../routing/overview) 中注册，您可以传递类而不是硬编码的 URL：

```java
AppNavItem settings = new AppNavItem("设置", SettingsView.class);
```

如果您的注释路由支持 [路由参数](../routing/route-patterns#named-parameters)，您还可以传递 `ParametersBag`：

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

使用 `setTarget()` 控制链接的打开方式。这对于外部链接或弹出视图特别有用。

- **`SELF`**（默认）：在当前视图中打开。
- **`BLANK`**：在新标签页或窗口中打开。
- **`PARENT`**：在父浏览上下文中打开。
- **`TOP`**：在顶级浏览上下文中打开。

```java
AppNavItem help = new AppNavItem("帮助", "https://support.example.com");
help.setTarget(AppNavItem.NavigationTarget.BLANK);
```

## 前缀和后缀 {#prefix-and-suffix}

`AppNavItem` 支持前缀和后缀组件。使用它们提供图标、徽章或按钮的视觉清晰度。

- **前缀**：出现在标签之前，适用于图标。
- **后缀**：出现在标签之后，适合徽章或操作。

```java
AppNavItem notifications = new AppNavItem("警报");
notifications.setPrefixComponent(TablerIcon.create("alert"));
notifications.setSuffixComponent(TablerIcon.create("link"));
```

## 自动打开组 {#auto-opening-groups}

在 `AppNav` 组件上使用 `setAutoOpen(true)` 以在应用程序刷新时自动展开嵌套组。

```java
nav.setAutoOpen(true);
```

## 节标题 <DocChip chip='since' label='26.02' /> {#section-labels}

`AppNavLabel` 是一个非交互式标题，标识一组项目的标题。标题适用于紧随其后的每个项目，直到下一个标题或菜单的末尾，从而使长列表的顶层项目能够以几个命名组的形式呈现，而无需将它们嵌套。

使用 `add()` 而不是 `addItem()` 来添加标签，调用顺序定义了部分：

```java
AppNav nav = new AppNav();
nav.addItem(new AppNavItem("仪表板", DashboardView.class, TablerIcon.create("layout-dashboard")));

nav.add(new AppNavLabel("分析"));
nav.addItem(new AppNavItem("概述", OverviewView.class));
nav.addItem(new AppNavItem("报告", ReportsView.class));

nav.add(new AppNavLabel("其他"));
nav.addItem(new AppNavItem("设置", SettingsView.class));
```

当其部分没有可见项目时，导航会自动隐藏标签，因此当 [搜索](#search) 过滤掉其项目或当所有项目都被 [固定](#pinning) 到菜单顶部时，标签会消失。

### 标签前缀和后缀 {#label-prefix-and-suffix}

与 `AppNavItem` 一样，标签支持前缀和后缀组件。将前缀传递给构造函数，或在之后设置任一：

```java
AppNavLabel analytics = new AppNavLabel("分析", TablerIcon.create("chart-pie"));
analytics.setSuffixComponent(new Badge().setText("2").setTheme(BadgeTheme.WARNING));

nav.add(analytics);
```

下面的示例在三个标签下对菜单进行分组，首个标签带有 [`Icon`](./icon) 前缀和 [`Badge`](./badge) 后缀。仪表板位于第一个标签之前，因此不属于任何部分。

<ComponentDemo
path='/webforj/appnavlabel/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/appnav/AppNavLabelView.java',
  'src/main/java/com/webforj/samples/views/appnav/AppNavLabelPageView.java',
]}
/>

## 固定 <DocChip chip='since' label='26.01' /> {#pinning}

固定允许用户将他们最常用的项目提升到导航顶部的组中，因此即使是深层菜单仍能在一次点击内保留一小部分收藏。默认情况下是关闭的。通过固定配置打开：

```java
AppNav nav = new AppNav();
nav.getPinning().setEnabled(true);
```

一旦启用，每个可导航的叶子项目都会显示一个固定切换。在悬停和键盘聚焦时会显示切换，因此即使没有鼠标也可以访问。激活它将项目移入导航顶部的固定组。

一些规则控制哪些项目可以被固定以及组的行为：

- 只有可导航的叶子项目可以被固定。组标题（带有子项的项目）永远不可固定。
- 仅当某些内容被固定后，固定组才会出现；当最后一个项目解除固定时，它也会消失。
- 解除固定将项目返回到其确切的原始位置，包括嵌套在多个层级深的组内的项目。
- 项目被移动，而不是复制，因此它所附带的任何前缀或后缀内容以及附加的任何监听器在固定组中工作时仍然有效。

下面的演示启用了固定功能，带有自定义组标题，并在加载时固定了仪表板。悬停或聚焦于叶子项目以显示其固定切换。

<ComponentDemo
path='/webforj/appnavpinning/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/appnav/AppNavPinningView.java',
  'src/main/java/com/webforj/samples/views/appnav/AppNavPinningPageView.java',
]}
/>

### 以固定状态启动项目 {#starting-an-item-pinned}

通过设置其固定状态，使项目在固定组中开始。使用 `isPinned()` 读取当前状态。

```java
AppNavItem reports = new AppNavItem("报告", "/reports");
reports.setPinned(true);
```

:::info 必须启用固定
`setPinned(true)` 仅在通过 `getPinning().setEnabled(true)` 在 `AppNav` 上启用固定时生效。否则，该调用无效。
:::

### 固定组标题 {#pinned-group-title}

固定组默认标记为 `Pinned`。可以更改为适合您的应用：

```java
nav.getPinning().setTitle("收藏");
```

### 固定键 {#pin-keys}

每个可固定的项目都有一个唯一键，用于持久化和 [固定事件](#reacting-to-pin-changes)。当您不设置时，该键将回退到项目的路径，因此 `getPinKey()` 始终返回可用的值。

```java
AppNavItem reports = new AppNavItem("报告", "/reports");
reports.setPinKey("reports");
```

当路径在运行时可能会更改时，请设置一个明确的键。稳定的键在重新加载时将固定匹配到正确的项目，即使其 URL 移动。

### 自动保存到本地存储 {#autosave}

如果不持久化，固定项仅在当前页面视图中存在。自动保存是最简单的选择：它将固定项的集合存储在浏览器的本地存储中，并在重新加载时恢复。默认情况下关闭。它需要在组件上设置一个稳定的 `id`（或名称）作为存储键，而 `AppNav(String id)` 构造函数是设置一个的方便方法：

```java
AppNav nav = new AppNav("main-nav"); // 为自动保存提供稳定的存储键
nav.getPinning().setAutosave(true);
```

:::info 自动保存需要一个 id
没有组件的 `id`（或名称），自动保存将安静地无所作为，因为它没有稳定的键来进行存储。持久性是每个浏览器的，因此固定项不会随用户移到其他设备或浏览器。
:::

### 自定义持久化 {#custom-persistence}

对于持久性您控制的情况，例如每个用户在服务器上，可以关闭自动保存并通过 [固定事件](#reacting-to-pin-changes) 和 `setPinned` 自行处理：

```java
nav.getPinning().setAutosave(false);

// 每当修改时持久化当前固定键的集合
nav.onPin(event -> savePins(event.getKeys()));

// 在加载时恢复每个保存的键
restoredKeys.forEach(key -> findItem(key).setPinned(true));
```

### 响应固定变化 {#reacting-to-pin-changes}

每当项目被固定或解除固定时，固定事件会触发。它包含更改的项目、其键、新的固定状态以及所有固定键的完整有序集合：

```java
nav.onPin(event -> {
  AppNavItem item = event.getItem(); // 更改的项目，如果它不再在导航中则为 null
  boolean pinned = event.isPinned();
  String key = event.getKey();
  List<String> all = event.getKeys(); // 所有固定键，按固定顺序
});
```

`getItem()` 通过匹配其固定键解析项目，当项目不再属于导航时返回 `null`。

### 固定位图标 {#pin-icons}

在项目未固定时切换使用内置的 `dwc:pin` 图标，而在固定时使用 `dwc:pinned-off`。通过 `setUnpinnedIcon` 和 `setPinnedIcon` 插入自己的图标，这些方法接受任何 `IconDefinition`：

```java
nav.getPinning()
   .setUnpinnedIcon(TablerIcon.create("pin"))
   .setPinnedIcon(TablerIcon.create("pinned-off"));
```

### 触摸屏上的固定切换 {#pin-toggle-on-touchscreens}

触摸屏没有悬停以显示固定项，因此切换默认情况下是隐藏的。通过 `setTouchVisible(true)` 使其在触摸屏上可见并可以点击：

```java
nav.getPinning().setTouchVisible(true);
```

## 搜索 <DocChip chip='since' label='26.01' /> {#search}

搜索字段在用户输入时通过项目标签过滤菜单。默认情况下关闭。您可以通过搜索配置显示它并给它一个占位符：

```java
nav.getSearch().setFieldVisible(true);
nav.getSearch().setPlaceholder("搜索");
```

当用户输入时，导航会按标签过滤项目，打开包含匹配项的任何组，并在没有匹配项时显示空消息。固定快捷方式在搜索时会保持可见，因此用户的收藏在过滤过程中仍然保持可点击。

<ComponentDemo
path='/webforj/appnavsearch/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/appnav/AppNavSearchView.java',
  'src/main/java/com/webforj/samples/views/appnav/AppNavSearchPageView.java',
]}
/>

### 空消息 {#search-empty-message}

设置搜索返回无结果时显示的消息。普通文本将作为文本呈现：

```java
nav.getSearch().setEmptyMessage("未找到项目");
```

### 从您自己的字段驱动搜索 {#custom-search-box}

隐藏内置字段，并从您自己的输入中提供过滤。通过 `setTerm` 将当前术语推入：

```java
nav.getSearch().setFieldVisible(false);

myField.onModify(event -> nav.getSearch().setTerm(event.getText()));
```

要对用户在内置字段中输入的内容做出反应，请监听搜索事件：

```java
nav.onSearch(event -> log(event.getTerm()));
```

## 样式化 `AppNavItem` {#styling-appnavitem}

<TableBuilder name="AppNavItem" />
