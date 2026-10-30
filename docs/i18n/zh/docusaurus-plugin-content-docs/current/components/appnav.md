---
title: AppNav
sidebar_position: 6
sidebar_class_name: new-content
description: >-
  Build hierarchical side navigation menus with AppNav and AppNavItem, linking
  to routes, registered views, or external URLs.
_i18n_hash: c4ad786639b189058a38c808bd99ec8b
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-app-nav" />
<DocChip chip='since' label='24.12' />
<JavadocLink type="appnav" location="com/webforj/component/layout/appnav/AppNav" top='true'/>

`AppNav` 组件根据 `AppNavItem` 条目创建侧边导航菜单。条目可以链接到内部视图或外部资源，嵌套在父项下形成分层菜单，并携带图标、徽章或其他组件，以便用户可以一目了然地获得更多上下文信息。

<!-- INTRO_END -->

## 添加和嵌套条目 <DocChip chip="name" label="dwc-app-nav-item" /> {#adding-and-nesting-items}

`AppNavItem` 实例用于填充 `AppNav` 结构。这些条目可以是简单的链接或包含子项的嵌套组头。没有链接的组头充当可扩展的容器。

使用 `addItem()` 将条目包含在导航中：

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
导航树中的顶级项目通常意味着可扩展 - 而不是可点击链接。在此类项目上设置 `path` 可能会使用户感到困惑，他们期望它们显示子项而不是导航到其他位置。

如果您希望组头触发自定义操作（例如打开外部文档），请保持组路径为空，而在条目的后缀中添加一个交互式控件，比如 [`IconButton`](./icon#icon-buttons)。这可以保持用户体验的一致性和整洁性。
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

## 链接条目 {#linking-items}

每个 `AppNavItem` 都可以导航到内部视图或外部链接。您可以使用静态路径或注册的视图类来定义这一点。

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

传递一个 `ParametersBag` 以包含查询字符串：

```java
ParametersBag params = ParametersBag.of("param1=value1&param2=value2");
AppNavItem advanced = new AppNavItem("高级", SettingsView.class, params);
advanced.setQueryParameters(params);
```

## 目标行为 {#target-behavior}

使用 `setTarget()` 控制链接的打开方式。对于外部链接或弹出视图尤其有用。

- **`SELF`**（默认）：在当前视图中打开。
- **`BLANK`**：在新标签或窗口中打开。
- **`PARENT`**：在父浏览上下文中打开。
- **`TOP`**：在顶级浏览上下文中打开。

```java
AppNavItem help = new AppNavItem("帮助", "https://support.example.com");
help.setTarget(AppNavItem.NavigationTarget.BLANK);
```

## 前缀和后缀 {#prefix-and-suffix}

`AppNavItem` 支持前缀和后缀组件。使用这些组件提供视觉清晰度，如图标、徽章或按钮。

- **前缀**：在标签之前出现，适用于图标。
- **后缀**：在标签之后出现，适用于徽章或操作。

```java
AppNavItem notifications = new AppNavItem("警报");
notifications.setPrefixComponent(TablerIcon.create("alert"));
notifications.setSuffixComponent(TablerIcon.create("link"));
```

## 自动打开组 {#auto-opening-groups}

在 `AppNav` 组件上使用 `setAutoOpen(true)` 可以在应用刷新时自动展开嵌套组。

```java
nav.setAutoOpen(true);
```

## 部分标签 <DocChip chip='since' label='26.02' /> <DocChip chip="name" label="dwc-app-nav-label" /> {#section-labels}

`AppNavLabel` 是一个非交互式标题，命名一系列条目。标签适用于它之后的每一项，直到下一个标签或菜单结束，这使得一长串顶级项目可以被视为几个命名组，而没有嵌套它们。

标签通过 `add()` 而不是 `addItem()` 添加，调用的顺序定义了部分：

```java
AppNav nav = new AppNav();
nav.addItem(new AppNavItem("仪表板", DashboardView.class, TablerIcon.create("layout-dashboard")));

nav.add(new AppNavLabel("分析"));
nav.addItem(new AppNavItem("概述", OverviewView.class));
nav.addItem(new AppNavItem("报告", ReportsView.class));

nav.add(new AppNavLabel("其他"));
nav.addItem(new AppNavItem("设置", SettingsView.class));
```

当某个部分没有可见条目时，导航会自动隐藏标签，因此当 [搜索](#search) 筛选掉其条目时，或当它们全部 [固定](#pinning) 在菜单顶部时，标签会消失。

### 标签前缀和后缀 {#label-prefix-and-suffix}

与 `AppNavItem` 一样，标签支持前缀和后缀组件。将前缀传递给构造函数，或之后设置任何一个：

```java
AppNavLabel analytics = new AppNavLabel("分析", TablerIcon.create("chart-pie"));
analytics.setSuffixComponent(new Badge().setText("2").setTheme(BadgeTheme.WARNING));

nav.add(analytics);
```

下面的示例将一个菜单分为三个标签，首个标签携带一个 [`Icon`](./icon) 前缀和一个 [`Badge`](./badge) 后缀。仪表板位于第一个标签的上方，因此它不属于任何部分。

<ComponentDemo
path='/webforj/appnavlabel/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/appnav/AppNavLabelView.java',
  'src/main/java/com/webforj/samples/views/appnav/AppNavLabelPageView.java',
]}
/>

## 固定 <DocChip chip='since' label='26.01' /> {#pinning}

固定功能允许用户将他们最常访问的条目提到导航顶部的一个组中，这样深层菜单仍能在一键之内保持简短的收藏列表，默认情况下是关闭的。通过固定配置将其开启：

```java
AppNav nav = new AppNav();
nav.getPinning().setEnabled(true);
```

启用后，每个可导航的叶子条目都会显示一个固定切换。切换在悬停和键盘聚焦时显现，因此它在没有鼠标的情况下仍可访问。激活后，该条目会移入导航顶部的固定组中。

几个规则决定了可以固定的内容及组的行为：

- 只有可导航的叶子条目可以固定。组头（带有子项的条目）从不可以固定。
- 只有在有内容被固定时，固定组才会显示，并在最后一个条目取消固定时消失。
- 取消固定会将条目返回到其确切的原始位置，包括在组中嵌套多个层级的条目。
- 该条目为移动而非复制，因此任何前缀或后缀内容以及附加的监听器在固定组中工作时仍然有效。

下面的演示启用了固定功能，并设置了自定义组标题，并在加载时固定了仪表板。悬停或聚焦叶子条目可揭示其固定切换。

<ComponentDemo
path='/webforj/appnavpinning/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/appnav/AppNavPinningView.java',
  'src/main/java/com/webforj/samples/views/appnav/AppNavPinningPageView.java',
]}
/>

### 将条目设置为固定 {#starting-an-item-pinned}

通过设置条目的固定状态来使其在固定组中启动。使用 `isPinned()` 读取当前状态。

```java
AppNavItem reports = new AppNavItem("报告", "/reports");
reports.setPinned(true);
```

:::info 固定功能必须启用
`setPinned(true)` 仅在通过 `getPinning().setEnabled(true)` 启用固定功能时生效。否则，此调用将无效。
:::

### 固定组标题 {#pinned-group-title}

固定组默认为 `Pinned`。更改以适应您的应用程序：

```java
nav.getPinning().setTitle("收藏");
```

### 固定键 {#pin-keys}

每个可固定的条目都有一个键，用于持久化和 [固定事件](#reacting-to-pin-changes) 的标识。当您不设置其中一个时，键会回落到条目的路径，因此 `getPinKey()` 始终返回一个可用值。

```java
AppNavItem reports = new AppNavItem("报告", "/reports");
reports.setPinKey("reports");
```

当路径可以在运行时变化时，设置显式键。稳定的键在重新加载时保持固定与正确条目的匹配，即使其 URL 移动。

### 自动保存到本地存储 {#autosave}

钉住的条目仅在当前页面视图中存在，除非您持久化它们。自动保存是最简单的选项：它将固定条目的集合存储在浏览器的本地存储中，并在重新加载时恢复。默认情况下关闭。它需要组件的稳定 `id`（或名称）作为存储键，并且 `AppNav(String id)` 构造函数是设置一个的便利方法：

```java
AppNav nav = new AppNav("main-nav"); // 为自动保存提供稳定的存储键
nav.getPinning().setAutosave(true);
```

:::info 自动保存需要一个 id
没有组件的 `id`（或名称），自动保存默默无闻，因为没有稳定的键进行存储。持久化是按浏览器进行的，因此固定条目不会跟随用户到其他设备或浏览器。
:::

### 自定义持久化 {#custom-persistence}

对于您控制的持久性，例如每个用户在服务器上的持久性，将自动保存设置为关闭，并通过 [固定事件](#reacting-to-pin-changes) 和 `setPinned` 自行驱动：

```java
nav.getPinning().setAutosave(false);

// 每次改变固定键时持久化当前固定键集合
nav.onPin(event -> savePins(event.getKeys()));

// 在加载时，恢复每个保存的键
restoredKeys.forEach(key -> findItem(key).setPinned(true));
```

### 响应固定变化 {#reacting-to-pin-changes}

当条目被固定或取消固定时，固定事件会触发。它携带已更改的条目、其键、新的固定状态和所有固定键的完整有序集合：

```java
nav.onPin(event -> {
  AppNavItem item = event.getItem(); // 更改的条目，如果不再在导航中则为 null
  boolean pinned = event.isPinned();
  String key = event.getKey();
  List<String> all = event.getKeys(); // 所有固定键，按照固定顺序
});
```

`getItem()` 通过匹配其固定键来解析该条目，如果条目不再是导航的一部分则返回 `null`。

### 固定图标 {#pin-icons}

当条目未固定时，切换使用内置的 `dwc:pin` 图标，固定时使用 `dwc:pinned-off`。可以通过 `setUnpinnedIcon` 和 `setPinnedIcon` 替换成您自己的，这接受任何 `IconDefinition`：

```java
nav.getPinning()
   .setUnpinnedIcon(TablerIcon.create("pin"))
   .setPinnedIcon(TablerIcon.create("pinned-off"));
```

### 触摸屏上的固定切换 {#pin-toggle-on-touchscreens}

触摸屏没有悬停来揭示固定，因此切换默认隐藏。使用 `setTouchVisible(true)` 使其在触摸屏上可见且可点击：

```java
nav.getPinning().setTouchVisible(true);
```

## 搜索 <DocChip chip='since' label='26.01' /> {#search}

搜索字段在用户输入时根据条目标签过滤菜单。默认情况下关闭。您可以通过搜索配置显示它并给它一个占位符：

```java
nav.getSearch().setFieldVisible(true);
nav.getSearch().setPlaceholder("搜索");
```

当用户输入时，导航按标签过滤条目，打开包含匹配的任何组，并在没有匹配时显示空消息。固定的快捷方式在搜索时保持可见，因此用户的收藏即使在过滤中也能保持一键可达。

<ComponentDemo
path='/webforj/appnavsearch/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/appnav/AppNavSearchView.java',
  'src/main/java/com/webforj/samples/views/appnav/AppNavSearchPageView.java',
]}
/>

### 空消息 {#search-empty-message}

设置搜索返回无结果时显示的消息。普通文本将呈现为文本：

```java
nav.getSearch().setEmptyMessage("没有找到条目");
```

### 从您自己的字段驱动搜索 {#custom-search-box}

隐藏内置字段并通过自己的输入提供过滤。在 `setTerm` 中推送当前术语：

```java
nav.getSearch().setFieldVisible(false);

myField.onModify(event -> nav.getSearch().setTerm(event.getText()));
```

要响应用户在内置字段中输入的内容，请监听搜索事件：

```java
nav.onSearch(event -> log(event.getTerm()));
```

## 样式 `AppNavItem` {#styling-appnavitem}

<TableBuilder name="AppNavItem" />
