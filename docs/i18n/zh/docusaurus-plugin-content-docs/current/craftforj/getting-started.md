---
title: 入门
sidebar_position: 2
description: >-
  Add the devtools dependency, enable craftforJ in your configuration, and open
  craftforJ over a running webforJ app.
_i18n_hash: 81825a3ba8656a8aee4820dee71da732
---
<DocChip chip='since' label='26.02' />

craftforJ 随 webforJ 一起提供，因此无需单独下载。本页面涵盖您的应用在 craftforJ 显示之前所需的内容，以及如何打开它。

:::tip 已在生成的项目中启用
使用 [startforJ](https://docs.webforj.com/startforj) 创建的项目或从 webforJ [原型](/docs/building-ui/archetypes/overview) 创建的项目均已启用 craftforJ。如果您是从其中一个项目开始，请运行您的应用，直接跳到 [打开 craftforJ](#opening-craftforj)。
:::

## 要求 {#requirements}

craftforJ 仅在以下所有条件成立时附加到应用上。如果其中一个条件不满足，则页面上不会显示任何内容。

### 添加依赖 {#add-the-dependency}

如果项目中尚未添加 `webforj-devtools`，请将其添加到项目中：

```xml title="pom.xml"
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-devtools</artifactId>
  <version>${webforj.version}</version>
</dependency>
```

### 调试模式和 craftforJ 标志 {#debug-mode-and-the-craftforj-flag}

将以下属性添加到您的项目中。如果您有一个标准的 webforJ 应用，请在 `webforj.conf` 中设置这些属性。对于使用 [Spring](/docs/integrations/spring/overview) 的 webforJ 项目，请在 `application.properties` 中设置这些属性。

```ini
webforj.debug = true
webforj.devtools.craftforj.enabled = true
```

只有在这两个属性都启用时，craftforJ 才能正常工作。因此，调试模式保持打开的应用程序在生产环境中不会暴露您的源代码树。

### 本地浏览器和开发者许可证 {#a-local-browser-and-a-developer-license}

从运行应用的机器上打开应用，并确保您拥有有效的开发者许可证。要从另一台机器访问 craftforJ，请将其地址添加到 [`hosts-allowed`](/docs/craftforj/configuration#access)。

确保这些设置到位后，重新启动应用并重新加载页面。

## 打开 craftforJ {#opening-craftforj}

当 craftforJ 活动时，您的应用上方会出现一个触发按钮。单击它以打开 craftforJ，或者在应用的任何地方按 <kbd>Alt</kbd> + <kbd>Shift</kbd> + <kbd>D</kbd>。相同的快捷键再次关闭 craftforJ，并且您可以将触发器拖动到适合您的任意角落。

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/getting-started.mp4" type="video/mp4" />
  </video>
</div>

其选项卡涵盖了 [组件树](/docs/craftforj/inspector)、[路由](/docs/craftforj/routes)、[主题](/docs/craftforj/theme) 和 [助手](/docs/craftforj/ai)。设置和应用信息与它们并列。

- **触发器** 是打开和关闭 craftforJ 的按钮。当 craftforJ 关闭时，它会保持不妨碍视线。
- **选项卡条** 沿着靠近应用的边缘运行，并在 craftforJ 显示的内容之间切换。
- **窗口菜单** 包含关于 craftforJ 所在位置的所有信息，详见 [Where craftforJ sits](#where-craftforj-sits)。

:::info macOS 上的快捷方式
craftforJ 根据您所在平台的修饰符编写每个快捷方式，因此 <kbd>Alt</kbd> 显示为 <kbd>⌥</kbd>，<kbd>Ctrl</kbd> 显示为 <kbd>⌘</kbd>。在 craftforJ 中按 <kbd>Shift</kbd> + <kbd>?</kbd> 可以查看当前列表。
:::

## craftforJ 的位置 {#where-craftforj-sits}

默认情况下，craftforJ 在您的应用上浮动。您可以将其拖放到页面上的任意位置，从任意边缘调整大小，当您希望独自使用应用时，将其最小化回触发器。将其拖放到页面边缘会将其停靠在那里，保持全高或全宽，并且每个边缘保持您所设置的大小。将其从边缘拖开，craftforJ 再次浮动。

:::info 停靠覆盖应用，不会重新布局
craftforJ 被绘制在页面上方。您的应用不会调整大小，里面的内容不会移开，因此无论 craftforJ 位于何处，位于其下方的内容都将被隐藏。要查看下面的内容，请将 craftforJ 移动到另一个边缘或将其移出页面。
:::

![craftforJ 停靠在应用页面右侧，覆盖该应用边缘](/img/craftforj/getting-started/docking.png#rounded-border)

要完全不覆盖应用，请将 craftforJ 移出页面，放入自己的浏览器窗口或标签中，这适合第二台显示器。它仍然可以通过打开它的页面检查您的应用，因此请保持该页面打开。如果您导航到其他页面或关闭它，craftforJ 就没有可以检查的内容，直到您再次打开应用。

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/undock-window.mp4" type="video/mp4" />
  </video>
</div>

如果您使用 Chrome 的拆分视图，在 Chrome 中选择选项卡而不是窗口，此时您的应用和 craftforJ 将并排放置，并且只接受真实标签。右键单击应用的选项卡，将其添加到新的拆分视图中，然后选择 craftforJ 选项卡。

:::info 拆分视图是 Chrome 的特性
Chrome 提供并排排列，而不是 craftforJ。其他浏览器没有等效功能，因此在其他浏览器中，craftforJ 将在普通选项卡中打开，您可以切换到它。无论哪种方式，craftforJ 的功能都是相同的。
:::

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/split-view.mp4" type="video/mp4" />
  </video>
</div>

:::tip 在助手撰写时移动
将 craftforJ 移到另一个窗口将结束仍在流式传输的回复。craftforJ 首先会提示，直到那时写下的所有内容都会保留在聊天中。
:::

## 进行第一次更改 {#making-a-first-change}

1. 按 <kbd>Alt</kbd> + <kbd>Shift</kbd> + <kbd>C</kbd> 开始选择一个组件。
2. 将鼠标悬停在应用中的某个元素上并单击它。
3. 组件树选择该组件，侧边栏填充其属性。
4. 更改某个属性。正在运行的应用会立即更新。

该更改仅影响您面前的应用。您的文件保持不变，直到审查该更改并应用它，详细信息请参见 [Writing changes to source](/docs/craftforj/source-changes)。

![craftforJ 在运行的应用旁边打开并选择组件](/img/craftforj/getting-started/first-open.png#rounded-border)

如果什么都没有出现，请通过 [故障排除](/docs/craftforj/troubleshooting) 进行排查。
