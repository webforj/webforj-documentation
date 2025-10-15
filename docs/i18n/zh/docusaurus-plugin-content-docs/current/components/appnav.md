---
title: AppNav
sidebar_position: 6
_i18n_hash: faa14d827865b1697b369a9787315dcd
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-app-nav" />
<DocChip chip="name" label="dwc-app-nav-item" />
<DocChip chip='since' label='24.12' />
<JavadocLink type="appnav" location="com/webforj/component/appnav/AppNav" top='true'/> 

`AppNav` 组件在 webforJ 中提供了灵活且有组织的侧导航菜单，支持扁平和层次结构。每个条目是一个 `AppNavItem`，可以表示一个简单的链接或一个包含子项的组。项可以链接到内部视图或外部资源，并增强了图标、徽章或其他组件。

## 添加和嵌套项 {#adding-and-nesting-items}

`AppNavItem` 实例用于填充 `AppNav` 结构。这些项可以是简单链接或包含子项的嵌套组标题。没有链接的组标题充当可扩展容器。

使用 `addItem()` 在导航中添加项：

```java
AppNavItem dashboard = new AppNavItem("仪表板", "/dashboard");
AppNavItem admin = new AppNavItem("管理");
admin.addItem(new AppNavItem("用户", "/admin/users"));
admin.addItem(new AppNavItem("设置", "/admin/settings"));

AppNav nav = new AppNav();
nav.addItem(dashboard);
nav.addItem(admin);
```

:::tip 链接组项
导航树中的顶级项通常意味着是可扩展的，而不是可点击的链接。为这样的项设置 `path` 可能会让用户困惑，因为他们期望它们能够展开子项而不是导航到其他地方。

如果你想让组标题触发自定义操作（例如打开外部文档），请保持组路径为空，而是添加一个交互控件，例如[`IconButton`](./icon#icon-buttons)，到项的后缀。这可以保持用户体验的一致性和清晰性。
:::

<!--vale off-->
<AppLayoutViewer 
path='/webforj/appnav/Social?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/appnav/AppNavView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/appnav/AppNavPageView.java']}
/>
<!--vale on-->

## 链接项 {#linking-items}

每个 `AppNavItem` 可以导航到内部视图或外部链接。你可以使用静态路径或注册的视图类来定义这一点。

### 静态路径 {#static-paths}

使用字符串路径直接定义链接：

```java
AppNavItem docs = new AppNavItem("文档", "/docs");
AppNavItem help = new AppNavItem("帮助", "https://support.example.com");
```

### 注册视图 {#registered-views}

如果你的视图在 [router](../routing/overview) 中注册过，你可以传递类而不是硬编码的 URL：

```java
AppNavItem settings = new AppNavItem("设置", SettingsView.class);
```

如果你的注释路由支持 [路由参数](../routing/route-patterns#named-parameters)，你也可以传递 `ParametersBag`：

```java
ParametersBag params = ParametersBag.of("id=123");
AppNavItem advanced = new AppNavItem("用户", UserView.class, params);
```

### 带查询参数 {#with-query-parameters}

传递 `ParametersBag` 以包括查询字符串：

```java
ParametersBag params = ParametersBag.of("param1=value1&param2=value2");
AppNavItem advanced = new AppNavItem("高级", SettingsView.class, params);
advanced.setQueryParameters(params);
```

## 目标行为 {#target-behavior}

使用 `setTarget()` 控制链接的打开方式。这对于外部链接或弹出视图尤为重要。

- **`SELF`**（默认）：在当前视图中打开。
- **`BLANK`**：在新标签或窗口中打开。
- **`PARENT`**：在父浏览上下文中打开。
- **`TOP`**：在顶层浏览上下文中打开。

```java
AppNavItem help = new AppNavItem("帮助", "https://support.example.com");
help.setTarget(AppNavItem.NavigationTarget.BLANK);
```

## 前缀和后缀 {#prefix-and-suffix}

`AppNavItem` 支持前缀和后缀组件。使用这些来通过图标、徽章或按钮提供视觉清晰度。

- **前缀**：出现在标签之前，适合用作图标。
- **后缀**：出现在标签之后，非常适合徽章或操作。

```java
AppNavItem notifications = new AppNavItem("警报");
notifications.setPrefixComponent(TablerIcon.create("alert"));
notifications.setSuffixComponent(TablerIcon.create("link"));
```

## 自动打开的组 {#auto-opening-groups}

在 `AppNav` 组件上使用 `setAutoOpen(true)` 以在应用刷新时自动展开嵌套组。

```java
nav.setAutoOpen(true);
```

## 样式 `AppNavItem` {#styling-appnavitem}

<TableBuilder name="AppNavItem" />
