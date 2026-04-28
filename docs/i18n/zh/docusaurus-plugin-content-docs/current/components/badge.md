---
title: Badge
sidebar_position: 8
sidebar_class_name: new-content
_i18n_hash: 83dfb4c5ec1d554fc78e7e860128fb46
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-badge" />
<DocChip chip='since' label='25.12' />
<JavadocLink type="badge" location="com/webforj/component/badge/Badge" top='true'/>

一个 `Badge` 是一种紧凑且视觉上独特的标签，用于传达状态、计数或简短的上下文信息。无论您是需要标记通知计数、将某个项目标记为“新”，还是引起对某个警告的注意，徽章都可以轻松地直接在 UI 中呈现这些信息。

<!-- INTRO_END -->

:::tip 使用 `Badge`
徽章非常适合用于通知计数、状态标签和版本标签或发布状态等短元数据。保持徽章文本在一到两个单词之间，以便标签一眼能读懂。
:::

## 创建徽章 {#creating-a-badge}

最简单的 `Badge` 只需一个文本字符串。您还可以在构造函数中直接传递 `BadgeTheme` 来立即设置视觉样式。当您需要动态构建徽章并在创建后进行配置时，可以使用无参数的构造函数。

```java
Badge badge = new Badge("新");

// 有主题
Badge primary = new Badge("推荐", BadgeTheme.SUCCESS);

// 动态构建
Badge status = new Badge();
status.setLabel("待处理");
status.setTheme(BadgeTheme.WARNING);
```

## 标签 {#label}

您可以随时通过 `setLabel()` 设置或更新徽章的文本内容。`setText()` 方法是同一操作的别名；可以根据上下文使用更自然的名称。两者都有对应的 getter，`getLabel()` 和 `getText()`，如果您需要读取当前值。

```java
Badge badge = new Badge();
badge.setLabel("已更新");

// 等效
badge.setText("已更新");

// 读取当前值
String current = badge.getLabel();
```

## 图标 {#icons}

有时，使用 `Badge` 传达信息时，更具视觉效果的方法是有用的。徽章支持插槽图标内容。使用 `Badge(String, Component...)` 构造函数在文本旁边传递图标，或者单独传递图标以创建仅有图标的徽章。当与文本组合时，图标会渲染在标签的左侧。

仅有图标的徽章在密集布局中的紧凑状态指示器中特别好用，在那种情况下，短单词会显得杂乱。将图标与文本配对是一种很好的中间方案，因为单独的图标可能会产生歧义。状态符号通常可以被广泛理解，但添加简短的文本标签可以消除首次使用者的猜测。如果需要组合更丰富的前缀，可以向构造函数传递多个组件，但实际上，单个图标是最常见的模式。

<!-- vale off -->
<ComponentDemo
path='/webforj/badgeicons?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/badge/BadgeIconsView.java'
height='345px'
/>
<!-- vale on -->

```java
// 图标与文本
Badge done = new Badge("已完成", FeatherIcon.CHECK_CIRCLE.create());
done.setTheme(BadgeTheme.SUCCESS);

// 仅图标
Badge check = new Badge(FeatherIcon.CHECK.create());
check.setTheme(BadgeTheme.SUCCESS);
```

## 在其他组件中的使用 {#usage-in-other-components}

### 按钮 {#buttons}

使用 `setBadge()` 将 `Badge` 附加到 `Button`。徽章出现在按钮的右上角，覆盖按钮边缘。这是工具栏操作或图标按钮中通知计数的常见模式。由于徽章是独立组件，因此它与按钮自己的主题和大小完全独立。您可以将主按钮与危险徽章配对，或将幽灵按钮与成功徽章配对，并且组合的每一侧都能独立样式化而不发生冲突。稍后更新计数只需调用 `badge.setLabel()` 并传入新值；按钮无需进行更改。

<!-- vale off -->
<ComponentDemo
path='/webforj/badgebuttons?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/badge/BadgeButtonsView.java'
height='290px'
/>
<!-- vale on -->

### 标签页窗格 {#tabbed-pane}

使用 `setSuffixComponent()` 在 `Tab` 上添加 `Badge` 作为后缀。这非常适合在每个选项卡上添加收件箱式计数或状态指示器。这是您在电子邮件客户端或任务管理器中看到的模式，重要的是可以一眼看到每个部分的活动。徽章位于选项卡标签的尾部，在任何前缀内容之后，即使当前活动选项卡也保持可见。这种持久性是有意的：在非活动选项卡上隐藏徽章会使得不切换至每个选项卡的情况下更难知道哪些部分需要关注。

<!-- vale off -->
<ComponentDemo
path='/webforj/badgetabbedpane?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/badge/BadgeTabbedPaneView.java'
height='360px'
/>
<!-- vale on -->

## 样式 {#styling}

徽章支持多个样式维度：主题颜色以传达意义、面积尺度以控制大小，以及 CSS 属性以进行细粒度自定义。

### 主题 {#themes}

与 webforJ 中的许多组件一样，`Badge` 有十四种主题：七种填充和七种轮廓变体。

填充主题使用实心背景，并自动计算符合对比度要求的文本颜色。而轮廓变体则使用带有彩色边框的着色背景，当您希望徽章与周围内容互补而不是主导时，它们是更微妙的选择。

使用 `setTheme()` 或通过构造函数应用主题。

<!-- vale off -->
<ComponentDemo
path='/webforj/badgethemes?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/badge/BadgeThemesView.java'
height='260px'
/>
<!-- vale on -->

### 自定义颜色 {#custom-color}

如果内置主题不符合您的调色板，请使用 `--dwc-badge-seed` CSS 属性设置自定义种子颜色。通过这个单一的值，徽章自动推导出背景、文本和边框颜色，因此每种组合都能保持可读性，而无需您单独指定每种颜色。这意味着您可以自信地将徽章品牌化为您设计系统中的任何颜色。色调、饱和度和亮度 (HSL) 值在这里尤其方便；仅交换色调就足以产生完全不同的颜色系列，同时保持对比度不变。

```java
Badge badge = new Badge("自定义");
badge.setStyle("--dwc-badge-seed", "hsl(262, 52%, 47%)");
```

### 尺寸 {#sizing}

使用 `setExpanse()` 来控制徽章大小。提供九种尺寸，从 `XXXSMALL` 到 `XXXLARGE`，默认是 `SMALL`。

<!-- vale off -->
<ComponentDemo
path='/webforj/badgesizes?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/badge/BadgeSizesView.java'
height='300px'
/>
<!-- vale on -->

### 部件和 CSS 变量 {#parts-and-css-variables}

<TableBuilder name="Badge" />
