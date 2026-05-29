---
title: Upgrading to the v26 Design System
description: >-
  Reference for the design-system updates in DWC 26 - color engine, dark mode,
  surfaces, shadows, typography, radius, focus ring, and interaction feedback.
sidebar_position: 2
_i18n_hash: 8a36bc047ecfc90874412da4d39643fb
---
DWC 26 引入了全新的设计系统。此次更新是增量式的，而非完全重写：大多数 v25 的 CSS 变量仍然可用，主题引擎的公共 API 保持不变，现有的自定义设置无需更改即可继续使用。

本指南记录了变化内容、视觉输出的不同之处，以及当应用依赖于特定 v25 行为时所需的升级步骤。

## 快速评判 {#quick-verdict}

| 场景 | 预期效果 |
| --- | --- |
| 使用默认样式 | 视觉更新。默认调色板色相已经重新调整（例如，主色从 `h: 211 / s: 100%` 移动到 `h: 223 / s: 91%`），阴影看起来更分层，组件感觉更圆润。无需代码更改，但品牌默认颜色会有所变化。 |
| 重写 `--dwc-color-{name}-h` 和 `-s` | 仍然有效。HSL 种子路径保持不变。 |
| 单个调色板步骤重写（例如 `--dwc-color-primary-40`） | 数字可能解析为不同的颜色。请参阅 [颜色调色板](#color-palette-step-5-is-always-darkest)。 |
| 依赖于 `--dwc-color-{name}-c` | 删除。现在根据每个阴影自动计算浅/深文本翻转。 |
| 引用命名字体大小标记（`--dwc-font-size-m`、`-l` 等） | 尺度向下移动了一档。`m` 现在是 14px，而不是 16px。请参阅 [排版](#typography)。 |
| 使用 `--dwc-font-weight-semibold` 获取 500 权重 | `semibold` 现在是 600。切换到新的 `--dwc-font-weight-medium` 以获取 500。 |
| 使用 `--dwc-focus-ring-width` 来保留可聚焦元素周围的填充 | 现在环有一个间隙。将 `--dwc-focus-ring-gap` 添加到该填充，否则环会溢出。请参阅 [焦点环](#focus-ring)。 |
| 自定义按钮悬停/涟漪效果 | 涟漪效果已经消失。按压反馈现为小范围缩小。 |

如果上述情况都不适用，您可以在此停止阅读。您的升级已经完成。

## 新功能一览 {#whats-new-at-a-glance}

- **现代色彩引擎。** 调色板使用 OKLCH 代替 HSL 生成。亮度级别是感知均匀的（相邻步骤看起来像相邻步骤），暗模式不再翻转调色板。
- **通过一个变量实现暗模式。** `--dwc-dark-mode: 1` 翻转整个用户界面。模式适应发生在变体层，而不是通过重新映射每个步骤。
- **自动 `on-text` 颜色。** 每个调色板步骤都有一个 `--dwc-color-on-{name}-text-{step}` 伴随值，符合该阴影的 WCAG AA 对比度要求。您无需手动计算对比度。
- **直接种子重写。** 将任何 CSS 颜色（十六进制、`rgb()`、`oklch()`、`lab()` 等）传入 `--dwc-color-{name}-seed`，整个调色板将从中重新生成。
- **重新调整的阴影。** 仍然有六个级别（`xs` 到 `2xl`），现在具有逼真的层次衰减，并通过 `--dwc-shadow-strength` 自动增强暗模式强度。
- **表面和 `default` 使用自己的亮度曲线。** 现在两者都通过 `--dwc-dark-mode` 和一个小主色调进行适应，而不是在暗主题中重新定义表面并将 `default` 别名为调色板步骤。
- **缩小按压反馈。** 涟漪由按压时的小范围缩小替代。标记：`--dwc-scale-press`、`--dwc-scale-press-deep`。
- **带间隙的焦点环。** 环现在有一个小的表面颜色间隙（`--dwc-focus-ring-gap`），在有色阴影之前保持可见，以便在实心按钮和紧凑布局中保持可见。
- **边框半径被种子化。** 改变 `--dwc-border-radius-seed`，则 `s` 到 `4xl` 步骤将成比例缩放（`2xs` 和 `xs` 保持在固定像素值）。新增 `3xl` 和 `4xl` 步骤。

## 颜色系统 {#the-color-system}

这是内部最大的变化。您应该熟悉看到的行为，内部结构有所不同。

### 定义颜色的两种方式 {#two-ways-to-define-a-color}

您可以像以前一样继续使用色相 + 饱和度，或直接重写种子为任何 CSS 颜色。

```css
/* 色相 + 饱和度（仍为默认路径） */
:root {
  --dwc-color-primary-h: 223;
  --dwc-color-primary-s: 91%;
}

/* 直接种子 - 任何 CSS 颜色格式 */
:root {
  --dwc-color-primary-seed: #6366f1;
}
```

如果您已经在使用 `-h` 和 `-s`，则无需进行任何操作。种子重写是直接品牌颜色的新路径。

### 颜色调色板：第 5 步始终是最暗的 {#color-palette-step-5-is-always-darkest}

在 v25 中，调色板在暗模式下翻转（在光线中第 5 步最暗，在暗中最亮）。在 v26 中，第 5 步始终是最暗的阴影，第 95 步始终是最亮的，无论模式如何。模式适应现在发生在一个层次的顶部，在变体标记中：

```css
/* v26 - 变体指向固定步骤 */
--dwc-color-primary-dark:  var(--dwc-color-primary-45);
--dwc-color-primary:       var(--dwc-color-primary-50);
--dwc-color-primary-light: var(--dwc-color-primary-55);
```

| 场景 | 更改内容 |
| --- | --- |
| 使用 `--dwc-color-primary`（或 `-dark`、`-light`、`-text`） | 无变化。变体在各模式间仍表现相同。 |
| 硬编码步骤如 `--dwc-color-primary-40` | 该步骤现在在两种模式下解析为相同的 OKLCH 亮度。您在暗模式中看到的颜色来自不同的步骤。如果希望支持模式迁移，请切换到变体标记。 |
| 直接编写 `hsl(var(--dwc-color-primary-h), ...)` | 仍然有效。HSL 种子仍然由 h + s 构造。 |

### 颜色是派生的，而不是承诺的 {#colors-are-derived-not-promised}

:::info 注意
您设置的色相是一个 **种子**，而不是目标。您通过 `--dwc-color-{name}-h` / `-s`（或 `-seed`）传递的颜色不一定会出现在第 50 步。
:::

由于调色板使用绝对 OKLCH 亮度来表示每一步，您的种子落在哪一步取决于其自然亮度。明亮的色相（青色、黄色）具有高 OKLCH 亮度，通常在第 80-85 步附近。较暗的色相（蓝色）碰巧位于第 50 步附近。

如果您需要确切的颜色在确切的步骤下，请显式设置该步骤：

```css
:root {
  --dwc-color-primary-50: #1d4ed8;
}
```

### `--dwc-color-{name}-c` 已消失 {#dwc-color-name-c-is-gone}

在 v25 中，`-c` 是对比阈值：伴随文字颜色从白色翻转为黑色的背景亮度阈值。值为 `50` 表示，在 50% 更暗的背景上文字是白色，而在 50% 更亮的背景上文字是黑色。

在 v26 中，您不再选择翻转点。每个步骤都有一个经过自动计算的着色的 `on-text` 颜色。这样得到的结果始终达到 WCAG AA 仅安全，同时保留搭配色调的暗示，而不是落入纯黑或纯白。

如果您有任何 `--dwc-color-{name}-c` 的重写，可以将其删除，因为没有效果。

### 文字和 `on-text` 颜色 {#text-and-on-text-colors}

v25 有每个步骤一个文字标记，`--dwc-color-{name}-text-{step}`，该标记是从 `-c` 阈值计算的纯黑或白色，旨在用作该步骤作为背景里的文字。

v26 保留相同的标记名称，但其含义发生变化，并增加第二个每步的标记：

| 标记 | v25 含义 | v26 含义 |
| --- | --- | --- |
| `--dwc-color-{name}-text-{step}` | 纯黑/白，旨在作为该阴影里的背景文字 | 着色的 **表面安全** 文字，在中性页面背景上可读 |
| `--dwc-color-on-{name}-text-{step}` | （未作为每步标记存在） | 着色的文字用于 **在** 那一步做背景 |

v26 两个标记都根据其预期背景的 WCAG AA 对比度进行限制。如果您将 `--dwc-color-{name}-text-{step}` 作为有色背景上的前景，请切换到 `--dwc-color-on-{name}-text-{step}`（新的 `on-text` 标记）以保持该语义。

### 着色和边框 {#tints-and-borders}

生成器现在为每个调色板发出三个标记，一个真正的新标记，一个新变体，以及一个移动了源的标记：

| 标记 | v25 | v26 |
| --- | --- | --- |
| `--dwc-color-{name}-tint` | （未存在） | 种子透明度 12%，用于替代背景 |
| `--dwc-border-color-{name}-emphasis` | （未存在） | 更强的模式感知边框，用于悬停/聚焦/激活 |
| `--dwc-border-color-{name}` | 在每个变体中设置为 `var(--dwc-color-{name})`（饱和色调） | 在生成器中计算：模式感知的轻度种子色调 |

如果您的 CSS 读取 `--dwc-border-color-primary` 期待饱和的主色，视觉效果现在变成了细微的分隔线色调。如果您特意想要饱和的外观，请直接切换到 `--dwc-color-primary`。

## 暗模式 {#dark-mode}

暗模式由一个变量 `--dwc-dark-mode` 控制。设置为 `1` 为暗模式，`0` 为亮模式：

```css
html[data-app-theme='my-dark-theme'] {
  --dwc-dark-mode: 1;
  color-scheme: dark;
}
```

它参与整个系统中的 `calc()` 表达式，它是模式适应传播到表面、阴影、边框和文本颜色的方式。

在 v25 中，内置的 `dark` 和 `dark-pure` 主题必须手动重新定义表面、阴影和许多调色板变体。在 v26 中，所有这些内容都从 `--dwc-dark-mode` 和种子颜色派生。典型的自定义暗主题，过去需要 20 行的重写，现在能简化为：

```css
html[data-app-theme='my-dark-theme'] {
  --dwc-dark-mode: 1;
  --dwc-color-primary-h: 280;
  color-scheme: dark;
}
```

如果您有从 v25 结构复制的自定义暗主题，通常可以删除大部分内部块，只保留种子和暗模式标志。

## 表面和 `default` {#surfaces-and-default}

在 v25 中，表面被定义了两次，一次在 `:root` 中用于亮模式（`hsl(default-h, default-s, 96%)` 等），一次在暗主题中（`hsl(default-h, default-s, 8%)` 等）。`default` 变体指向调色板步骤，并且同样需要暗主题的重写。

在 v26 中，两个都只计算一次，并在亮度计算中融合了 `--dwc-dark-mode` 术语，这确保了：

- 表面总是稍微低于 `default`，因此卡片在页面上视觉上浮动。
- 在两种模式下都对主色调应用了一点轻微的色调。
- `dark-pure` 主题设置 `--dwc-color-default-s: 0%`，自动将色调降低至零。

如果您的应用重写了 `--dwc-surface-1`（或其他任何表面）为固定颜色，它仍然有效；您只是选择退出了自动模式适应。

`--dwc-color-{name}-alt` 标记的源也发生变化：

| 标记 | v25 | v26 |
| --- | --- | --- |
| `--dwc-color-{name}-alt` | 调色板步骤 95（接近白色背景） | 种子透明度 12%（半透明色调） |

如果您将 `-alt` 用作实心的接近白色背景，它现在将读取为半透明着色的叠加。您可以选择特定的步骤（`--dwc-color-{name}-95`）或围绕半透明语义设计。

## 阴影 {#shadows}

六级缩放（`xs`、`s`、`m`、`l`、`xl`、`2xl`）的名称和数量保持不变，但层偏移已重新构建，以实现更真实的衰减，且暗模式阴影现在通过 `--dwc-shadow-strength` 自动增强 5 倍，因为暗表面需要更多对比度来传达深度。

如果您只使用 `var(--dwc-shadow)`，则获取的是重新调整后的中等阴影，其他内容没有变化。`--dwc-shadow-color` 变量仍然会被生成，但其值格式已更改：

| | v25 | v26 |
| --- | --- | --- |
| `--dwc-shadow-color` | HSL 三元组（`h, s%, l%`） | 完整的 OKLCH 颜色 |

如果您的 CSS 使用了旧三元组形式，比如 `hsla(var(--dwc-shadow-color), 0.07)`，请切换到完整的阴影标记（`var(--dwc-shadow-m)`）或用 `oklch(from var(--dwc-shadow-color) l c h / 0.07)` 进行重写。

## 排版 {#typography}

组件大小标记（`--dwc-size-*`）保持不变。字体比例重新调整，以将 `m` 级固定在其他 DWC 标记中使用的相同轻体大小，因此名称级别向下移动了一步：

| 标记 | v25 | v26 |
| --- | --- | --- |
| `--dwc-font-size-3xs` | 10px | 10px |
| `--dwc-font-size-2xs` | 12px | 11px |
| `--dwc-font-size-xs` | 13px | 12px |
| `--dwc-font-size-s` | 14px | 13px |
| `--dwc-font-size-m` | 16px | 14px |
| `--dwc-font-size-l` | 18px | 16px |
| `--dwc-font-size-xl` | 22px | 20px |
| `--dwc-font-size-2xl` | 28px | 26px |
| `--dwc-font-size-3xl` | 36px | 34px |

默认的 `--dwc-font-size` 仍解析为 **14px**，只是通过 `--dwc-font-size-m`（v26）而不是 `--dwc-font-size-s`（v25）到达。

如果您的 CSS 通过名称引用字体大小标记（例如 `font-size: var(--dwc-font-size-l)`），则 v26 中可见结果将较小。请向上跳一档以保持 v25 尺寸。

字体权重添加了三个标记（`thin`、`medium`、`black`），一个现有标记发生了变化：

| 标记 | v25 | v26 |
| --- | --- | --- |
| `--dwc-font-weight-semibold` | 500 | 600 |
| `--dwc-font-weight-medium` | （未存在） | 500 |

如果您使用 `--dwc-font-weight-semibold` 获取 500 权重文本，请切换到新的 `--dwc-font-weight-medium`。

行高级别与字体大小同样向下移动；默认的 `--dwc-font-line-height` 仍解析为 1.25。

`--dwc-font-family-sans` 和 `--dwc-font-family-mono` 已现代化地使用 `system-ui` 和 `ui-monospace` 堆栈。如果您从旧堆栈中针对特定命名字体（如 `Dank Mono`、`Operator Mono`、`Roboto` 等）想要恢复，设置 `--dwc-font-family` 为您控制的堆栈。

## 间距 {#spacing}

空间比例从 `xs` 开始保持不变。只有最小的两个标记被四舍五入为整像素值：

| 标记 | v25 | v26 |
| --- | --- | --- |
| `--dwc-space-3xs` | 1.2px | 1px |
| `--dwc-space-2xs` | 2.4px | 2px |

几乎没有任何应用需要采取行动。

## 边框半径 {#border-radius}

边框半径现在被种子化。更改 `--dwc-border-radius-seed`，并且每个步骤（`s`、`m`、`l`、`xl`、`2xl`、`3xl`、`4xl`）都会成比例缩放。`2xs` 和 `xs` 步骤仍保持在固定的像素值（太小而无法有效派生）。

变化有三个：

| | v25 | v26 |
| --- | --- | --- |
| 单位 | `em`（与父字体大小一起缩放） | `rem`（与根字体大小一起缩放） |
| 默认 `--dwc-border-radius` | `--dwc-border-radius-s`（4px） | `--dwc-border-radius-seed`（8px） |
| 可用步骤 | 最高 `2xl` | 添加 `3xl`、`4xl` |

组件感觉在默认情况下更圆润。如果一个嵌套在较大文本中的组件以前通过 `em` 继承更大的半径，现在不再发生缩放，半径现在固定在根级别。如果要恢复 v25 的默认大小，请将种子减半：

```css
:root {
  --dwc-border-radius-seed: 0.25rem; /* 4px，使整个比例减半 */
}
```

## 缓和 {#easings}

缓和目录基本保持不变，并为常见情况提供了新的快捷标记：`--dwc-ease`、`--dwc-ease-in`、`--dwc-ease-out`、`--dwc-ease-outGlide`。请参阅 [过渡和缓和](/docs/styling/transitions-easing) 页面以获取完整列表。

## 过渡 {#transitions}

过渡持续时间经过重新平衡以获得更快的感觉：

| 变量 | v25 | v26 |
| --- | --- | --- |
| `--dwc-transition-slow` | 500&nbsp;ms | 300&nbsp;ms |
| `--dwc-transition-medium` | 250&nbsp;ms | 250&nbsp;ms |
| `--dwc-transition-fast` | 150&nbsp;ms | 150&nbsp;ms |
| `--dwc-transition-x-fast` | 50&nbsp;ms | 100&nbsp;ms |

如果您依赖特定持续时间，请在 `:root` 中覆盖它。

## 焦点环 {#focus-ring}

焦点环现在使用双环模式：一个小的表面颜色间隙，然后是彩色环。这使得在实心按钮和密集布局上保持环可读。

| 变量 | v25 | v26 |
| --- | --- | --- |
| `--dwc-focus-ring-width` | 3px | 2px |
| `--dwc-focus-ring-a` | 0.4 | 0.75 |
| `--dwc-focus-ring-gap` | （无） | 2px |
| `--dwc-focus-ring-l` | 45% | （删除，亮度根据模式计算） |

如果您用 `padding: var(--dwc-focus-ring-width)` 保留可聚焦元素周围的空间，请将间隙添加到该填充中，以便新的环有空间呈现：

```css
/* v25 */
dwc-button { padding: var(--dwc-focus-ring-width); }

/* v26 */
dwc-button {
  padding: calc(var(--dwc-focus-ring-width) + var(--dwc-focus-ring-gap));
}
```

## 互动反馈 {#interaction-feedback}

不再使用 DWC 组件的素材风格涟漪效果。任何可点击元素的新反馈是微小的缩小：

```css
--dwc-scale-press: 0.97;      /* 标准的 3% 缩小 */
--dwc-scale-press-deep: 0.93; /* 按钮的深层 7% 缩小 */
```

`ripple` SCSS 混合和 `--dwc-ripple-color` CSS 变量仍然存在于构建中，但默认情况下没有任何导入。如果您的组件选择了混合，请切换到按压缩放标记以匹配新的感觉。

## 浏览器支持 {#browser-support}

新系统使用两种 CSS 特性，其浏览器兼容性表可在 MDN 查看：

- [OKLCH 颜色空间](https://developer.mozilla.org/en-US/docs/Web/CSS/color_value/oklch#browser_compatibility)，包括相对颜色语法 (`oklch(from ...)`)
- [`color-mix()`](https://developer.mozilla.org/en-US/docs/Web/CSS/color_value/color-mix#browser_compatibility)

两者均已在常青版本的 Chrome、Edge、Firefox 和 Safari 中发布。

## 实用的升级检查清单 {#a-pragmatic-upgrade-checklist}

1. 搜索 `--dwc-color-*-c` 并删除这些声明。
2. 搜索 `hsla(var(--dwc-shadow-color)` 并替换为阴影标记（`var(--dwc-shadow-m)`）或以 `oklch(from ...)` 进行重写。
3. 搜索直接调色板步骤引用（`--dwc-color-{name}-{number}`）。如果任何内容提供专门针对暗模式的样式，请切换到变体标记（`--dwc-color-{name}`、`-dark`、`-light`）。
4. 搜索命名字体大小引用（`--dwc-font-size-m`、`-l` 等）。如果您想要 v25 大小，请向上移动一档。
5. 搜索 `--dwc-font-weight-semibold`。如果您想要 500，请切换到 `--dwc-font-weight-medium`。
6. 如果您使用 `--dwc-focus-ring-width` 保留可聚焦元素周围的空间，请将 `--dwc-focus-ring-gap` 添加到填充中。
7. 打开应用，四处点击。大多数应用不需要其他步骤。
