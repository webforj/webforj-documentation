---
title: 入门
sidebar_position: 2
description: >-
  Add the devtools dependency, enable craftforJ in your configuration, and open
  craftforJ over a running webforJ app.
_i18n_hash: 6cb150ca0da866a142787024af9e2bd1
---
<!-- vale off -->
# 开始使用 <DocChip chip='since' label='26.02' />
<!-- vale on -->

craftforJ 随 webforJ 一同提供，因此无需单独下载。本页介绍 craftforJ 显示前应用需要满足的条件，以及如何打开它。

:::tip 在生成的项目中已启用
使用 [startforJ](https://docs.webforj.com/startforj) 或 webforJ [原型](/docs/building-ui/archetypes/overview)创建的项目已启用 craftforJ。如果你使用这类项目，请运行应用，然后跳转到[打开 craftforJ](#opening-craftforj)。
:::

## 要求 {#requirements}

只有满足以下所有条件时，craftforJ 才会连接到应用。如果未满足其中任一条件，页面上就不会显示任何内容。

### 添加依赖项 {#add-the-dependency}

如果项目中尚未包含 `webforj-devtools`，请添加它：

```xml title="pom.xml"
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-devtools</artifactId>
  <version>${webforj.version}</version>
</dependency>
```

### 调试模式和 craftforJ 标志 {#debug-mode-and-the-craftforj-flag}

将以下属性添加到项目中。如果你使用标准 webforJ 应用，请在 `webforj.conf` 中设置这些属性。如果你的 webforJ 项目使用 [Spring](/docs/integrations/spring/overview)，请在 `application.properties` 中设置这些属性。

```ini
webforj.debug = true
webforj.devtools.craftforj.enabled = true
```

只有同时启用这两个属性，craftforJ 才能正常工作；因此，即使应用在部署到生产环境时仍开启调试模式，也不会暴露源代码树。

### 本地浏览器和开发者许可证 {#a-local-browser-and-a-developer-license}

请在运行应用的机器上打开应用，并确保你拥有有效的开发者许可证。若要从另一台机器访问 craftforJ，请将其地址添加到 [`hosts-allowed`](/docs/craftforj/configuration#access)。

完成以上设置后，重启应用并重新加载页面。

## 打开 craftforJ {#opening-craftforj}

craftforJ 激活后，应用上会出现一个触发按钮。点击它即可打开 craftforJ，也可以在应用的任意位置按 <kbd>Alt</kbd> + <kbd>Shift</kbd> + <kbd>D</kbd>。使用相同的快捷键可再次关闭 craftforJ，并且你可以将触发按钮拖到任意角落。

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/getting-started.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

其选项卡包含[组件树](/docs/craftforj/inspector)、[路由](/docs/craftforj/routes)、[主题](/docs/craftforj/theme)和[助手](/docs/craftforj/ai)。设置和应用信息也位于其中。


- **触发按钮**用于打开和关闭 craftforJ。craftforJ 关闭时，它会停留在不碍事的位置。
- **选项卡栏**位于靠近应用的一侧，可在 craftforJ 展示的内容之间切换。
- **窗口菜单**包含有关 craftforJ 位置的所有选项，详见[craftforJ 的位置](#where-craftforj-sits)。

:::info macOS 上的快捷键
craftforJ 会根据你当前使用的平台显示各快捷键的修饰键，因此 <kbd>Alt</kbd> 会显示为 <kbd>⌥</kbd>，<kbd>Ctrl</kbd> 会显示为 <kbd>⌘</kbd>。在 craftforJ 中按 <kbd>Shift</kbd> + <kbd>?</kbd> 可查看当前快捷键列表。
:::

## craftforJ 的位置 {#where-craftforj-sits}

默认情况下，craftforJ 会悬浮在应用上方。你可以将它拖到页面上的任意位置，从任意边缘调整大小，并在需要使用应用时将它最小化为触发按钮。将它拖到页面边缘即可停靠在该处，并占据全高或全宽；每条边缘都会保留你设置的尺寸。将它拖离边缘后，它会再次悬浮。

:::info 停靠会覆盖应用，不会调整应用布局
craftforJ 会绘制在页面上方。应用不会调整大小，其中的内容也不会移开，因此 craftforJ 覆盖的区域会被遮挡。若要查看下方的内容，请将 craftforJ 移到另一侧边缘，或将它移出页面。
:::

![craftforJ 停靠在应用页面右侧，覆盖了应用的该侧区域](/img/craftforj/getting-started/docking.png#rounded-border)

若要完全避免覆盖应用，请将 craftforJ 移出页面，放入独立的浏览器窗口或标签页中，这种方式适合搭配第二台显示器使用。craftforJ 仍会通过打开它的页面检查应用，因此请保持该页面打开。如果你离开该页面或将其关闭，在再次打开应用之前，craftforJ 就没有可供检查的内容。

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/undock-window.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

如果你使用 Chrome 的分屏视图，请选择标签页而不是窗口。分屏视图会将应用和 craftforJ 并排显示，并且只接受真正的标签页。右键点击应用的标签页，将其添加到新的分屏视图中，然后选择 craftforJ 标签页。

:::info 分屏视图是 Chrome 的功能
并排排列由 Chrome 提供，而非 craftforJ。其他浏览器没有对应功能，因此在其他浏览器中，craftforJ 会在普通标签页中打开，你需要切换到该标签页。无论采用哪种方式，craftforJ 本身的工作方式都相同。
:::

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/split-view.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

:::tip 助手正在生成回复时移动
将 craftforJ 移到另一个窗口会中断仍在生成的回复。craftforJ 会先询问你；到那时为止已生成的内容会保留在聊天中。
:::

## 进行首次更改 {#making-a-first-change}

1. 按 <kbd>Alt</kbd> + <kbd>Shift</kbd> + <kbd>C</kbd> 开始选择组件。
2. 将鼠标悬停在应用中的某个元素上，然后点击它。
3. 组件树会选中该组件，侧边栏则会显示它的属性。
4. 更改某个属性，运行中的应用会立即更新。

更改只会影响当前显示的应用。只有在检查并应用更改后，文件才会被修改。相关内容请参阅[将更改写入源代码](/docs/craftforj/source-changes)。

![craftforJ 在运行中的应用旁打开，并选中了一个组件](/img/craftforj/getting-started/first-open.png#rounded-border)

如果完全没有显示任何内容，请按[故障排除](/docs/craftforj/troubleshooting)中的步骤逐一检查。
