---
title: Badge
sidebar_position: 8
sidebar_class_name: new-content
_i18n_hash: 112f61dea5c6c0d434267a25ccc61b9e
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-badge" />
<DocChip chip='since' label='25.12' />
<JavadocLink type="badge" location="com/webforj/component/badge/Badge" top='true'/>

一个 `Badge` 是一个紧凑且视觉上独特的标签，用于传达状态、计数或简短的上下文信息。无论您是需要标记通知计数、将某项标记为“新”还是呼叫注意警告，徽章为您提供了一种轻量级的方式直接在 UI 中显示该信息。

<!-- INTRO_END -->

:::tip 使用 `Badge`
徽章适用于通知计数、状态标签和短小的元数据，如版本标签或发布状态。徽章文本应保持在一到两个单词之内，以便标签一目了然。
:::

## 创建徽章 {#creating-a-badge}

最简单的 `Badge` 只需一个文本字符串。您也可以在构造函数中直接传递一个 `BadgeTheme` 来立即设置视觉样式。当您需要动态构建徽章并在创建后配置它时，可以使用无参数构造函数。

```java
Badge badge = new Badge("New");

// 使用主题
Badge primary = new Badge("Featured", BadgeTheme.SUCCESS);

// 动态构建
Badge status = new Badge();
status.setLabel("Pending");
status.setTheme(BadgeTheme.WARNING);
```

## 标签 {#label}

您可以随时使用 `setLabel()` 设置或更新徽章的文本内容。`setText()` 方法是相同操作的别名；根据上下文使用更自然的那个。两者都有对应的 getter，`getLabel()` 和 `getText()`，如果您需要读取当前值。

```java
Badge badge = new Badge();
badge.setLabel("Updated");

// 等效
badge.setText("Updated");

// 读取当前值
String current = badge.getLabel();
```

## 图标 {#icons}

在使用 `Badge` 传达信息时，有时使用更视觉化的方式会更有用。徽章支持插槽图标内容。使用 `Badge(String, Component...)` 构造函数传递一个图标和文本，或者单独传递一个图标以创建一个仅图标徽章。当与文本结合时，图标将渲染在标签的左侧。

仅图标徽章在密集布局中的紧凑状态指示器中效果尤为突出，短单词会显得拥挤。将图标与文本搭配使用是一个不错的折中方案，因为单独图标可能会含糊不清。状态符号通常被广泛理解，但添加一个短文本标签可以消除首次用户的猜测。如果您需要组合更丰富的前缀，可以向构造函数传递多个组件，不过实际上单个图标是最常见的模式。

<!-- vale off -->
<ComponentDemo
path='/webforj/badgeicons?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/badge/BadgeIconsView.java'
height='320px'
/>
<!-- vale on -->

```java
// 图标与文本
Badge done = new Badge("Done", FeatherIcon.CHECK_CIRCLE.create());
done.setTheme(BadgeTheme.SUCCESS);

// 仅图标
Badge check = new Badge(FeatherIcon.CHECK.create());
check.setTheme(BadgeTheme.SUCCESS);
```

## 在其他组件中的使用 {#usage-in-other-components}

### 按钮 {#buttons}

使用 `setBadge()` 将 `Badge` 附加到 `Button`。徽章出现在按钮的右上角，重叠按钮边缘。这是工具栏操作或图标按钮上的通知计数的常见模式。由于徽章是一个独立组件，它与按钮自己的主题和大小完全独立。您可以将主要按钮与危险徽章配对，或将幽灵按钮与成功徽章配对，并且组合的每一边都会自我样式化，而不冲突。稍后更新计数就像调用 `badge.setLabel()` 传递新值一样简单；按钮无需触碰。

<!-- vale off -->
<ComponentDemo
path='/webforj/badgebuttons?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/badge/BadgeButtonsView.java'
height='250px'
/>
<!-- vale on -->

### 选项卡面板 {#tabbed-pane}

使用 `setSuffixComponent()` 将 `Badge` 添加为 `Tab` 的后缀。这是收件箱样式计数或每个选项卡状态指示器的自然选择。这种模式在电子邮件客户端或任务管理器中很常见，能够一目了然地信号每个部分的活动尤为重要。徽章位于选项卡标签的尾部，在任何前缀内容之后，并且无论当前活动哪个选项卡都保持可见。这种持久性是有意的：隐藏非活动选项卡上的徽章会使得难以知道哪些部分需要注意，而不必切换到每一部分。

<!-- vale off -->
<ComponentDemo
path='/webforj/badgetabbedpane?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/badge/BadgeTabbedPaneView.java'
height='325px'
/>
<!-- vale on -->

## 样式 {#styling}

徽章支持几种样式维度：主题颜色传达含义、扩展规模控制大小，以及 CSS 属性进行细粒度自定义。

### 主题 {#themes}

与 webforJ 中的许多组件一样，`Badge` 提供了十四种主题：七种填充和七种轮廓变体。

填充主题使用实心背景，并自动计算符合对比要求的文本颜色。轮廓变体则使用有色边框的着色背景，使其成为一种更微妙的选项，当您希望徽章补充周围内容而非主导时。

通过 `setTheme()` 或构造函数应用主题。

<!-- vale off -->
<ComponentDemo
path='/webforj/badgethemes?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/badge/BadgeThemesView.java'
height='260px'
/>
<!-- vale on -->

### 自定义颜色 {#custom-color}

如果内置主题不符合您的调色板，请使用 `--dwc-badge-seed` CSS 属性设置自定义种子颜色。根据这个单一值，徽章会自动推导背景、文本和边框颜色，因此每种组合保持可读，而无需您逐一指定。这意味着您可以自信地将徽章品牌设计成您设计系统中的任何颜色。色调、饱和度和亮度 (HSL) 值在这里特别方便；仅交换色调就足以产生完全不同的色彩系列，同时保持对比度不变。

```java
Badge badge = new Badge("Custom");
badge.setStyle("--dwc-badge-seed", "hsl(262, 52%, 47%)");
```

### 尺寸 {#sizing}

使用 `setExpanse()` 来控制徽章大小。提供九种尺寸，从 `XXXSMALL` 到 `XXXLARGE`，默认大小为 `SMALL`。

<!-- vale off -->
<ComponentDemo
path='/webforj/badgesizes?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/badge/BadgeSizesView.java'
height='260px'
/>
<!-- vale on -->

### 部件和 CSS 变量 {#parts-and-css-variables}

<TableBuilder name="Badge" />
