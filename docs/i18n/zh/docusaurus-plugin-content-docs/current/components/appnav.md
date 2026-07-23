---
title: AppNav
sidebar_position: 6
sidebar_class_name: new-content
description: >-
  Build hierarchical side navigation menus with AppNav and AppNavItem, linking
  to routes, registered views, or external URLs.
_i18n_hash: 7283cd36346dd18b131a5393db8e8fd3
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-app-nav" />
<DocChip chip="name" label="dwc-app-nav-item" />
<DocChip chip='since' label='24.12' />
<JavadocLink type="appnav" location="com/webforj/component/appnav/AppNav" top='true'/>

`AppNav` 组件通过 `AppNavItem` 条目创建侧边导航菜单。项目可以链接到内部视图或外部资源，可以在父项下嵌套形成分层菜单，并携带图标、徽章或其他组件，以便用户一目了然地获取更多上下文。

<!-- INTRO_END -->

## 添加和嵌套项目 {#adding-and-nesting-items}

`AppNavItem` 实例用于填充 `AppNav` 结构。这些项目可以是简单链接或包含子项的嵌套组头。没有链接的组头充当可展开的容器。

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
导航树中的顶级项目通常是可展开的——而不是可点击的链接。在此类项目上设置 `path` 可能会使用户困惑，因为他们期待这些项目揭示子项而不是导航到其他地方。

如果您希望组头触发自定义操作（例如打开外部文档），请保持组路径为空，而是添加一个交互控件，例如 [`IconButton`](./icon#icon-buttons) 到项目的后缀。这保持了用户体验的一致性和简洁性。
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

每个 `AppNavItem` 可以导航到内部视图或外部链接。您可以使用静态路径或注册视图类来定义此项。

### 静态路径 {#static-paths}

使用字符串路径直接定义链接：

```java
AppNavItem docs = new AppNavItem("文档", "/docs");
AppNavItem help = new AppNavItem("帮助", "https://support.example.com");
```

### 注册视图 {#registered-views}

如果您的视图已通过 [router](../routing/overview) 注册，您可以传递类而不是硬编码的 URL：

```java
AppNavItem settings = new AppNavItem("设置", SettingsView.class);
```

如果您的注释路由支持 [路由参数](../routing/route-patterns#named-parameters)，您还可以传递 `ParametersBag`：

```java
ParametersBag params = ParametersBag.of("id=123");
AppNavItem advanced = new AppNavItem("用户", UserView.class, params);
```

### 带查询参数 {#with-query-parameters}

传递 `ParametersBag` 来包含查询字符串：

```java
ParametersBag params = ParametersBag.of("param1=value1&param2=value2");
AppNavItem advanced = new AppNavItem("高级", SettingsView.class, params);
advanced.setQueryParameters(params);
```

## 目标行为 {#target-behavior}

使用 `setTarget()` 控制链接的打开方式。这在处理外部链接或弹出视图时特别有用。

- **`SELF`**（默认）：在当前视图中打开。
- **`BLANK`**：在新选项卡或窗口中打开。
- **`PARENT`**：在父浏览上下文中打开。
- **`TOP`**：在顶级浏览上下文中打开。

```java
AppNavItem help = new AppNavItem("帮助", "https://support.example.com");
help.setTarget(AppNavItem.NavigationTarget.BLANK);
```

## 前缀和后缀 {#prefix-and-suffix}

`AppNavItem` 支持前缀和后缀组件。使用这些组件通过图标、徽章或按钮提供视觉清晰度。

- **前缀**：出现在标签之前，适用于图标。
- **后缀**：出现在标签之后，非常适合徽章或操作。

```java
AppNavItem notifications = new AppNavItem("警报");
notifications.setPrefixComponent(TablerIcon.create("alert"));
notifications.setSuffixComponent(TablerIcon.create("link"));
```

## 自动打开组 {#auto-opening-groups}

在 `AppNav` 组件上使用 `setAutoOpen(true)` 以在应用刷新时自动展开嵌套组。

```java
nav.setAutoOpen(true);
```

## 固定 <DocChip chip='since' label='26.01' /> {#pinning}

固定允许用户将他们最常使用的项目提升到导航顶部的组中，因此即使是深层菜单仍可在一次点击中快速访问短列表。默认情况下未启用。通过固定配置启用它：

```java
AppNav nav = new AppNav();
nav.getPinning().setEnabled(true);
```

启用后，每个可导航的叶子项目都会显示一个固定切换。切换在悬停和键盘焦点时显示，因此在没有鼠标的情况下仍然可以轻松访问。激活后，项目将移动到导航顶部的固定组中。

有一些规则控制可以固定的内容以及组的行为：

- 只有可导航的叶子项目可以固定。组头（具有子项的项目）不会被固定。
- 固定组仅在固定了某些项目后出现，当最后一个项目被取消固定时将消失。
- 取消固定使项目返回其确切原始位置，包括在组中嵌套若干层的项目。
- 项目是移动的，而不是复制的，因此任何前缀或后缀内容以及任何附加的监听器在项目处于固定组中时仍然有效。

下面的演示启用了固定，带有自定义组标题，并在加载时固定了仪表板。悬停或聚焦一个叶子项目以显示其固定切换。

<ComponentDemo
path='/webforj/appnavpinning/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/appnav/AppNavPinningView.java',
  'src/main/java/com/webforj/samples/views/appnav/AppNavPinningPageView.java',
]}
/>

### 初始固定项目 {#starting-an-item-pinned}

通过设置其固定状态来使项目在固定组中启动。使用 `isPinned()` 来读取当前状态。

```java
AppNavItem reports = new AppNavItem("报告", "/reports");
reports.setPinned(true);
```

:::info 必须启用固定
`setPinned(true)` 仅在通过 `getPinning().setEnabled(true)` 在 `AppNav` 上启用固定时生效。否则，调用不会产生任何效果。
:::

### 固定组标题 {#pinned-group-title}

默认情况下，固定组标记为 `Pinned`。可以更改以适应您的应用：

```java
nav.getPinning().setTitle("收藏");
```

### 固定键 {#pin-keys}

每个可固定项目都有一个关键字，用于持久化和 [固定事件](#reacting-to-pin-changes) 的标识。当您不设置时，关键字将回落到项目的路径，因此 `getPinKey()` 始终返回一个可用值。

```java
AppNavItem reports = new AppNavItem("报告", "/reports");
reports.setPinKey("reports");
```

在路径可能在运行时更改时设置显式键。稳定的关键字确保在重新加载时即使其 URL 移动，也能将固定匹配到右侧项目。

### 自动保存到本地存储 {#autosave}

除非您持久化它们，否则固定仅在当前页面视图中生效。自动保存是最简单的选项：它将固定项目集存储在浏览器的本地存储中，并在重新加载时恢复它们。默认情况下为关闭。它需要组件的稳定 `id`（或名称）作为存储键，`AppNav(String id)` 构造函数是设置一个的便捷方式：

```java
AppNav nav = new AppNav("main-nav"); // 为自动保存提供一个稳定的存储键
nav.getPinning().setAutosave(true);
```

:::info 自动保存需要一个 id
如果组件没有 `id`（或名称），自动保存将悄然无效，因为它没有稳定的键来存储在下。持久性是每个浏览器的，因此固定不会跟随用户到另一个设备或浏览器。
:::

### 自定义持久性 {#custom-persistence}

要控制的持久性，例如每个用户在服务器上，关闭自动保存并通过 [固定事件](#reacting-to-pin-changes) 和 `setPinned` 自行处理：

```java
nav.getPinning().setAutosave(false);

// 每当固定键发生变化时持久化当前固定键集
nav.onPin(event -> savePins(event.getKeys()));

// 加载时，恢复每个保存的键
restoredKeys.forEach(key -> findItem(key).setPinned(true));
```

### 响应固定变化 {#reacting-to-pin-changes}

每当项目被固定或取消固定时，固定事件都会触发。它携带已更改的项目、其键、新的固定状态以及完整有序的固定键集：

```java
nav.onPin(event -> {
  AppNavItem item = event.getItem(); // 更改的项目，如果它不再在导航中则为 null
  boolean pinned = event.isPinned();
  String key = event.getKey();
  List<String> all = event.getKeys(); // 每个固定的键，按固定顺序
});
```

`getItem()` 通过匹配其固定键解析项目，并且当项目不再是导航的一部分时返回 `null`。

### 固定图标 {#pin-icons}

在项目未固定时，切换使用内置的 `dwc:pin` 图标，而固定时使用 `dwc:pinned-off`。通过 `setUnpinnedIcon` 和 `setPinnedIcon` 替换为您自己的图标，这两者接受任何 `IconDefinition`：

```java
nav.getPinning()
   .setUnpinnedIcon(TablerIcon.create("pin"))
   .setPinnedIcon(TablerIcon.create("pinned-off"));
```

### 在触摸屏上的固定切换 {#pin-toggle-on-touchscreens}

触摸屏没有悬停来显示固定，因此默认情况下隐藏切换。通过 `setTouchVisible(true)` 使其在触摸屏上可见并可点击：

```java
nav.getPinning().setTouchVisible(true);
```

## 搜索 <DocChip chip='since' label='26.01' /> {#search}

搜索字段在用户输入时根据项目标签过滤菜单。默认情况下关闭。您可以通过搜索配置显示它并为其提供占位符：

```java
nav.getSearch().setFieldVisible(true);
nav.getSearch().setPlaceholder("搜索");
```

随着用户输入，导航按标签过滤项目，打开包含匹配项的任何组，并在没有匹配项时显示空消息。固定的快捷方式在搜索时保持可见，因此用户的收藏在搜索过程中依然可以轻松访问。

<ComponentDemo
path='/webforj/appnavsearch/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/appnav/AppNavSearchView.java',
  'src/main/java/com/webforj/samples/views/appnav/AppNavSearchPageView.java',
]}
/>

### 空消息 {#search-empty-message}

设置搜索返回无结果时显示的消息。纯文本将作为文本呈现：

```java
nav.getSearch().setEmptyMessage("没有找到项目");
```

### 从自定义字段驱动搜索 {#custom-search-box}

隐藏内置字段并从您自己的输入提供过滤器。通过 `setTerm` 推送当前术语：

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
