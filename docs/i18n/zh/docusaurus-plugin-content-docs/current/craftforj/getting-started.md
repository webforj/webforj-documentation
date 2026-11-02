---
title: 开始使用
sidebar_position: 2
description: >-
  Add the devtools dependency, enable craftforJ in your configuration, and open
  craftforJ over a running webforJ app.
_i18n_hash: fb5860ba7349ef5523c2dfb6bc425d9b
---
<DocChip chip='since' label='26.02' />

craftforJ 随 webforJ 一起发布，因此无需单独下载。本页面介绍了在 craftforJ 出现之前您的应用所需的内容，以及如何打开它。

:::tip 已在生成的项目中启用
使用 [startforJ](https://docs.webforj.com/startforj) 创建的项目，或从 webforJ [原型](/docs/building-ui/archetypes/overview) 创建的项目都已启用 craftforJ。如果您是从中开始的，请运行您的应用并跳到 [打开 craftforJ](#opening-craftforj)。
:::

## 要求 {#requirements}

仅在以下所有条件都满足时，craftforJ 才会附加到应用程序上。如果其中一个条件不满足，则页面上不会出现任何内容。

### 添加依赖项 {#add-the-dependency}

如果您的项目中还没有 `webforj-devtools`，请将其添加到您的项目中：

```xml title="pom.xml"
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-devtools</artifactId>
  <version>${webforj.version}</version>
</dependency>
```

### 调试模式和 craftforJ 标志 {#debug-mode-and-the-craftforj-flag}

将以下属性添加到您的项目中。如果您有一个标准的 webforJ 应用，请在 `webforj.conf` 中设置属性。对于使用 [Spring](/docs/integrations/spring/overview) 的 webforJ 项目，请在 `application.properties` 中设置属性。

```ini
webforj.debug = true
webforj.devtools.craftforj.enabled = true
```

只有在启用这两个属性时，craftforJ 才能正常工作；因此，处于调试模式的应用在生产时不会暴露您的源树。

### 本地浏览器和开发者许可证 {#a-local-browser-and-a-developer-license}

从运行应用的机器打开应用，并确保您拥有有效的开发者许可证。要从另一台机器访问 craftforJ，请将其地址添加到 [`hosts-allowed`](/docs/craftforj/configuration#access)。

这些准备就绪后，重启应用并重新加载页面。

## 打开 craftforJ {#opening-craftforj}

当 craftforJ 激活时，触发按钮会出现在您的应用上。点击它以打开 craftforJ，或在应用中的任何地方按 <kbd>Alt</kbd> + <kbd>Shift</kbd> + <kbd>D</kbd>。相同的快捷键会关闭 craftforJ，您可以将触发器拖到适合您的任意角落。

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/getting-started.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

其选项卡涵盖 [组件树](/docs/craftforj/inspector)、[路由](/docs/craftforj/routes)、[主题](/docs/craftforj/theme) 和 [助手](/docs/craftforj/ai)。设置和应用信息与之并排。

- **触发器** 是打开和关闭 craftforJ 的按钮。当 craftforJ 关闭时，它会保持不显眼。
- **选项卡条** 沿着最靠近应用的边缘运行，并在 craftforJ 显示的内容之间切换。
- **窗口菜单** 包含有关 craftforJ 位置的所有信息，涵盖在 [Where craftforJ sits](#where-craftforj-sits) 中。

:::info macOS 快捷键
craftforJ 使用您所在平台的修饰符编写每个快捷键，因此 <kbd>Alt</kbd> 显示为 <kbd>⌥</kbd>，<kbd>Ctrl</kbd> 显示为 <kbd>⌘</kbd>。在 craftforJ 中按 <kbd>Shift</kbd> + <kbd>?</kbd> 可查看当前列表。
:::

## craftforJ 的位置 {#where-craftforj-sits}

craftforJ 默认悬浮在您的应用上。您可以将其拖动到页面上的任何位置，从任何边缘调整大小，并在想要使用应用时将其最小化回触发器。将其拖到页面的边缘会将其停靠在该处，保持全高或全宽，每个边缘保持您给定的大小。将其从边缘拖开后，它会再次悬浮。

:::info 停靠覆盖应用，而不是重新布局它
craftforJ 是在页面上方绘制的。您的应用不会调整大小，且其中的内容不会移出视野，因此 craftforJ 下面的任何内容在其存在时都会被隐藏。要查看其下方的内容，请将 craftforJ 移动到另一个边缘或将其退出页面。
:::

![craftforJ 停靠在应用页面右侧，覆盖该边缘的应用](/img/craftforj/getting-started/docking.png#rounded-border)

要完全停止覆盖应用，请将 craftforJ 移出页面，放到单独的浏览器窗口或标签中，这适合第二个显示器。它仍然通过打开它的页面检查您的应用，因此请保持该页面打开。如果您导航离开或关闭它，craftforJ 将不再有内容进行检查，直到您再次打开应用。

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/undock-window.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

如果您使用 Chrome 的拆分视图，在其中您的应用和 craftforJ 并排显示并只接受真实标签，则选择选项卡而不是窗口。右键单击应用的选项卡，将其添加到新的拆分视图中，然后选择 craftforJ 选项卡。

:::info 拆分视图是 Chrome 功能
Chrome 提供并排的排列，而不是 craftforJ。其他浏览器没有等效项，因此 craftforJ 在其他浏览器中打开为普通选项卡，您切换到它。craftforJ 本身在两种方式下的工作原理相同。
:::

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/split-view.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

:::tip 在助手写作时移动
将 craftforJ 移入另一个窗口会结束仍在流式传输的回复。craftforJ 首先会询问，一切前写的内容将保留在聊天中。
:::

## 做第一次更改 {#making-a-first-change}

1. 按 <kbd>Alt</kbd> + <kbd>Shift</kbd> + <kbd>C</kbd> 开始选择一个组件。
2. 将鼠标悬停在应用中的某个元素上并单击它。
3. 组件树选择该组件，侧边栏填充了它的属性。
4. 更改一个属性。正在运行的应用会立即更新。

更改仅影响您面前的应用。您的文件保持不变，直到您审查更改并应用它，如 [将更改写入源](/docs/craftforj/source-changes) 中所述。

![craftforJ 在运行的应用旁边打开，选中一个组件](/img/craftforj/getting-started/first-open.png#rounded-border)

如果什么都没有出现，请查阅 [故障排除](/docs/craftforj/troubleshooting)。
