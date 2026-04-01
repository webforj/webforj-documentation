---
title: AppNav
sidebar_position: 6
_i18n_hash: d4756db6bed23bc005fbcd2be222b4ea
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-app-nav" />
<DocChip chip="name" label="dwc-app-nav-item" />
<DocChip chip='since' label='24.12' />
<JavadocLink type="appnav" location="com/webforj/component/appnav/AppNav" top='true'/> 

`AppNav` 组件通过 `AppNavItem` 条目创建侧边导航菜单。项目可以链接到内部视图或外部资源，可以嵌套在父项下以形成层次菜单，并可以携带图标、徽章或其他组件，以便用户一目了然地获得更多上下文。

<!-- INTRO_END -->

## 添加和嵌套项目 {#adding-and-nesting-items}

`AppNavItem` 实例用于填充 `AppNav` 结构。这些条目可以是简单链接或包含子项的嵌套组标题。没有链接的组标题用作可扩展的容器。

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
导航树中的顶级项目通常意味着可扩展—而不是可单击的链接。在这类项目上设置 `path` 可能会使用户感到困惑，他们期待这些项目可以展开子项，而不是导航到其他地方。

如果您希望组标题触发自定义操作（如打开外部文档），则保持组路径为空，并在项目的后缀中添加交互控件，如 [`IconButton`](./icon#icon-buttons)。这可以保持用户体验一致且干净。
:::

<!--vale off-->
<AppLayoutViewer 
path='/webforj/appnav/Social?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/appnav/AppNavView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/appnav/AppNavPageView.java']}
/>
<!--vale on-->

## 链接项目 {#linking-items}

每个 `AppNavItem` 可以导航到内部视图或外部链接。您可以使用静态路径或注册的视图类来定义它。

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

如果您的注释路由支持 [路由参数](../routing/route-patterns#named-parameters)，您也可以传递 `ParametersBag`：

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

使用 `setTarget()` 控制链接的打开方式。这对外部链接或弹出视图特别有用。

- **`SELF`** (默认): 在当前视图中打开。
- **`BLANK`**: 在新标签或窗口中打开。
- **`PARENT`**: 在父浏览上下文中打开。
- **`TOP`**: 在顶级浏览上下文中打开。

```java
AppNavItem help = new AppNavItem("帮助", "https://support.example.com");
help.setTarget(AppNavItem.NavigationTarget.BLANK);
```

## 前缀和后缀 {#prefix-and-suffix}

`AppNavItem` 支持前缀和后缀组件。使用这些组件提供视觉清晰度，配合图标、徽章或按钮。

- **前缀**: 出现在标签之前，适用于图标。
- **后缀**: 出现在标签之后，适合徽章或操作。

```java
AppNavItem notifications = new AppNavItem("提醒");
notifications.setPrefixComponent(TablerIcon.create("警报"));
notifications.setSuffixComponent(TablerIcon.create("链接"));
```

## 自动打开组 {#auto-opening-groups}

在 `AppNav` 组件上使用 `setAutoOpen(true)` 以在应用程序刷新时自动展开嵌套组。

```java
nav.setAutoOpen(true);
```

## 样式 `AppNavItem` {#styling-appnavitem}

<TableBuilder name="AppNavItem" />
