---
title: Badge
sidebar_position: 8
_i18n_hash: 1f599f2c8a833e09f2d945ed0ead5447
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-badge" />
<DocChip chip='since' label='25.12' />
<JavadocLink type="badge" location="com/webforj/component/badge/Badge" top='true'/>

一个 `Badge` 是一个紧凑且视觉上独特的标签，用于传达状态、计数或短的上下文信息。无论您需要标记通知计数、将一个项目标记为“新”，还是引起对警告的关注，徽章都可以为您提供一种轻量方式，直接在 UI 中展示该信息。

<!-- INTRO_END -->

:::tip 使用 `Badge`
徽章非常适合用于通知计数、状态标签和版本标签或发布状态等简短元数据。保持徽章文本为一到两个词，使标签一目了然。
:::

## 创建徽章 {#creating-a-badge}

最简单的 `Badge` 接受一个文本字符串。您也可以在构造函数中直接传递 `BadgeTheme` 以立即设置视觉样式。无参数构造函数在您需要动态构建徽章并在创建后进行配置时可用。

```java
Badge badge = new Badge("新");

// 带主题
Badge primary = new Badge("推荐", BadgeTheme.SUCCESS);

// 动态构建
Badge status = new Badge();
status.setLabel("待处理");
status.setTheme(BadgeTheme.WARNING);
```

## 标签 {#label}

您可以随时使用 `setLabel()` 设置或更新徽章的文本内容。`setText()` 方法是相同操作的别名；使用在上下文中更自然的任一方法。如果您需要读取当前值，两个都有对应的 getter，`getLabel()` 和 `getText()`。

```java
Badge badge = new Badge();
badge.setLabel("已更新");

// 等效
badge.setText("已更新");

// 读取值
String current = badge.getLabel();
```

## 图标 {#icons}

有时，在使用 `Badge` 传达信息时，更具视觉效果的方法是有用的。徽章支持插槽图标内容。使用 `Badge(String, Component...)` 构造函数传递一个图标和文本，或者单独传递一个图标以创建一个仅包含图标的徽章。当与文本结合时，图标会显示在标签的左侧。

仅图标徽章在密集布局中的紧凑状态指示器中尤其有效，在这种情况下，使用短词会显得凌乱。当单独的图标可能含义模糊时，将图标与文本配对是一个好的折中方案。状态符号单独使用时广为人知，但添加一个简短的文本标签可以消除首次使用者的猜测。如果您需要组合更丰富的前缀，可以将多个组件传递给构造函数，但在实践中，单个图标是最常见的模式。

<!-- vale off -->
<ComponentDemo
path='/webforj/badgeicons'
files={['src/main/java/com/webforj/samples/views/badge/BadgeIconsView.java']}
height='345px'
/>
<!-- vale on -->

```java
// 带文本的图标
Badge done = new Badge("完成", FeatherIcon.CHECK_CIRCLE.create());
done.setTheme(BadgeTheme.SUCCESS);

// 仅图标
Badge check = new Badge(FeatherIcon.CHECK.create());
check.setTheme(BadgeTheme.SUCCESS);
```

## 在其他组件中的使用 {#usage-in-other-components}

### 按钮 {#buttons}

使用 `setBadge()` 将 `Badge` 附加到 `Button`。徽章出现在按钮的右上角，重叠按钮边缘。这是一种常见的模式，适用于工具栏操作或图标按钮上的通知计数。由于徽章是一个独立的组件，它完全独立于按钮自身的主题和大小。您可以将主按钮与危险徽章配对，也可以将幽灵按钮与成功徽章配对，这样两者的样式不会冲突。稍后更新计数只需调用 `badge.setLabel()` 并传递一个新值即可；按钮无需被触碰。

<!-- vale off -->
<ComponentDemo
path='/webforj/badgebuttons'
files={['src/main/java/com/webforj/samples/views/badge/BadgeButtonsView.java']}
height='290px'
/>
<!-- vale on -->

### 选项卡面板 {#tabbed-pane}

使用 `setSuffixComponent()` 将 `Badge` 添加为 `Tab` 的后缀。这对于收件箱样式的计数或每个标签上的状态指示器非常合适。这种模式常见于电子邮件客户端或任务管理器，其中重要的是要一目了然地信号每个部分的活动。徽章位于标签标签的尾部，在任何前缀内容之后，且无论当前哪个标签处于活动状态，它始终可见。这种持续性是故意的：在非活动标签上隐藏徽章将使得在切换到每个标签之前更难知道哪些部分需要注意。

<!-- vale off -->
<ComponentDemo
path='/webforj/badgetabbedpane'
files={['src/main/java/com/webforj/samples/views/badge/BadgeTabbedPaneView.java']}
height='360px'
/>
<!-- vale on -->

## 样式 {#styling}

徽章支持几个样式维度：主题颜色以传达含义、可控大小的扩展尺度和用于细粒度自定义的 CSS 属性。

### 主题 {#themes}

与 webforJ 中的许多组件一样，`Badge` 共有十四种主题：七种填充和七种轮廓变体。

填充主题使用实心背景，并自动计算满足对比要求的文本颜色。轮廓变体则使用着色背景和有色边框，使其成为在想要徽章补充周围内容而非主导内容时更微妙的选择。

通过 `setTheme()` 或构造函数应用主题。

<!-- vale off -->
<ComponentDemo
path='/webforj/badgethemes'
files={['src/main/java/com/webforj/samples/views/badge/BadgeThemesView.java']}
height='260px'
/>
<!-- vale on -->

### 自定义颜色 {#custom-color}

如果内置主题不匹配您的调色板，请使用 `--dwc-badge-seed` CSS 属性设置自定义种子颜色。通过这个单一值，徽章自动派生背景、文本和边框颜色，因此每种组合都保持可读性，而您无需单独指定每一种颜色。这意味着您可以放心地将徽章品牌化为您设计系统中的任何颜色。色相、饱和度和亮度（HSL）值在这里特别方便；仅交换色相就足以产生完全不同的颜色系列，同时保持对比度不变。

```java
Badge badge = new Badge("自定义");
badge.setStyle("--dwc-badge-seed", "hsl(262, 52%, 47%)");
```

### 尺寸 {#sizing}

使用 `setExpanse()` 控制徽章大小。提供九个可用大小，从 `XXXSMALL` 到 `XXXLARGE`，默认值为 `SMALL`。

<!-- vale off -->
<ComponentDemo
path='/webforj/badgesizes'
files={['src/main/java/com/webforj/samples/views/badge/BadgeSizesView.java']}
height='300px'
/>
<!-- vale on -->

### 部件和 CSS 变量 {#parts-and-css-variables}

<TableBuilder name="Badge" />
