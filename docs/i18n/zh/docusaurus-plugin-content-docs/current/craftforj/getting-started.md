---
title: 入门
sidebar_position: 2
description: >-
  Add the devtools dependency, enable craftforJ in your configuration, and open
  craftforJ over a running webforJ app.
_i18n_hash: 5d30f5dd80df008a05be5d4617c40369
---
<!-- vale off -->
# 开始使用 <DocChip chip='since' label='26.02' />
<!-- vale on -->

craftforJ 随 webforJ 一同提供，因此无需单独下载。本页介绍了 craftforJ 显示前应用需要满足的条件，以及如何打开 craftforJ。

:::tip 已在生成的项目中启用
通过 [startforJ](https://docs.webforj.com/startforj) 或 webforJ [原型](/docs/building-ui/archetypes/overview)创建的项目已启用 craftforJ。如果你是从这类项目开始的，只需运行应用，然后跳至[打开 craftforJ](#opening-craftforj)。
:::

## 要求 {#requirements}

只有在满足以下所有条件时，craftforJ 才会连接到应用。如果其中任何一项未满足，页面上就不会显示任何内容。

### 添加依赖项 {#add-the-dependency}

如果项目中尚未添加 `webforj-devtools`，请将它添加到项目中：

```xml title="pom.xml"
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-devtools</artifactId>
  <version>${webforj.version}</version>
</dependency>
```

### 调试模式和 craftforJ 标志 {#debug-mode-and-the-craftforj-flag}

将以下属性添加到项目中。对于标准 webforJ 应用，请在 `webforj.conf` 中设置这些属性。对于使用 [Spring](/docs/integrations/spring/overview) 的 webforJ 项目，请在 `application.properties` 中设置这些属性。

```ini
webforj.debug = true
webforj.devtools.craftforj.enabled = true
```

只有同时启用这两个属性，craftforJ 才能正常工作；因此，即使应用在发布到生产环境时仍开启了调试模式，也不会暴露源代码树。

### 本地浏览器和开发者许可证 {#a-local-browser-and-a-developer-license}

请在运行应用的机器上打开应用，并确保你持有有效的开发者许可证。若要从另一台机器访问 craftforJ，请将该机器的地址添加到 [`hosts-allowed`](/docs/craftforj/configuration#access)。

完成这些设置后，重启应用并重新加载页面。

## 打开 craftforJ {#opening-craftforj}

craftforJ 激活后，应用上方会出现一个触发按钮。点击该按钮即可打开 craftforJ，也可以在应用的任意位置按 <kbd>Alt</kbd> + <kbd>Shift</kbd> + <kbd>D</kbd>。再次按下相同的快捷键即可关闭 craftforJ；你也可以将触发按钮拖到合适的角落。

<VideoPlayer video="craftforj/getting-started" width={1920} height={984} title="Getting started" />

其中的选项卡包括[组件树](/docs/craftforj/inspector)、[路由](/docs/craftforj/routes)、[主题](/docs/craftforj/theme)和[助手](/docs/craftforj/ai)。设置和应用信息也在其中。


- **触发按钮**用于打开和关闭 craftforJ。craftforJ 关闭时，它不会妨碍你使用应用。
- **选项卡栏**沿着靠近应用的一侧排列，可在 craftforJ 显示的内容之间切换。
- **窗口菜单**包含 craftforJ 位置相关的所有设置，详见 [craftforJ 的位置](#where-craftforj-sits)。

:::info macOS 上的快捷键
craftforJ 会根据你所用平台显示快捷键修饰键，因此 <kbd>Alt</kbd> 会显示为 <kbd>⌥</kbd>，而 <kbd>Ctrl</kbd> 会显示为 <kbd>⌘</kbd>。在 craftforJ 中按 <kbd>Shift</kbd> + <kbd>?</kbd> 可查看当前快捷键列表。
:::

## craftforJ 的位置 {#where-craftforj-sits}

默认情况下，craftforJ 会浮在应用上方。你可以将它拖到页面上的任意位置，从任意边缘调整大小，也可以在想要单独使用应用时将它最小化回触发按钮。将它拖到页面边缘后，它会停靠在那里，铺满页面高度或宽度；每个边缘都会记住你设置的尺寸。将它从边缘拖开后，它便会再次浮动。

:::info 停靠会覆盖应用，但不会重新排布应用内容
craftforJ 绘制在页面上方。应用不会调整大小，页面中的内容也不会移开，因此 craftforJ 下方的内容会被遮挡。若要查看下方内容，请将 craftforJ 移到另一个边缘，或将它移出页面。
:::

![craftforJ 停靠在应用页面右侧，覆盖了应用的该侧区域](/img/craftforj/getting-started/docking.png#rounded-border)

若要完全避免遮挡应用，请将 craftforJ 移出页面，放入单独的浏览器窗口或标签页中，这种方式适合搭配第二台显示器使用。它仍会通过打开它的页面检查应用，因此请保持该页面处于打开状态。如果你离开该页面或将其关闭，craftforJ 就无法继续检查任何内容，直到你再次打开应用。

<VideoPlayer video="craftforj/undock-window" width={1920} height={1048} title="Undock window" />

如果你使用 Chrome 的分屏视图，请选择标签页而不是窗口。分屏视图会将应用和 craftforJ 并排显示，并且只接受实际的标签页。右键点击应用的标签页，将其添加到新的分屏视图，然后选择 craftforJ 标签页。

:::info 分屏视图是 Chrome 的功能
并排布局由 Chrome 提供，而不是 craftforJ。其他浏览器没有对应功能，因此在其他浏览器中，craftforJ 会在普通标签页中打开，你需要切换到该标签页。无论采用哪种方式，craftforJ 本身的工作方式都相同。
:::

<VideoPlayer video="craftforj/split-view" width={1920} height={1080} title="Split view" />

:::tip 助手正在生成内容时移动 craftforJ
将 craftforJ 移到另一个窗口会中止仍在流式生成的回复。craftforJ 会先询问你；截至中止时已生成的所有内容都会保留在聊天中。
:::

## 进行首次更改 {#making-a-first-change}

1. 按 <kbd>Alt</kbd> + <kbd>Shift</kbd> + <kbd>C</kbd> 开始选择组件。
2. 将鼠标悬停在应用中的某个元素上并点击它。
3. 组件树会选中该组件，侧边栏则会显示它的属性。
4. 更改某个属性。运行中的应用会立即更新。

更改只会影响你当前看到的应用。在你检查并应用更改之前，文件不会受到影响；相关内容请参见[将更改写入源代码](/docs/craftforj/source-changes)。

![craftforJ 在运行中的应用旁打开，并选中了一个组件](/img/craftforj/getting-started/first-open.png#rounded-border)

如果完全没有显示任何内容，请参阅[故障排查](/docs/craftforj/troubleshooting)。
