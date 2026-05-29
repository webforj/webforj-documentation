---
sidebar_position: 3
title: Colors
_i18n_hash: cc233e97e4b7333262eb47b14bfe572a
---
webforJ 提供了一个基于 CSS 自定义属性的颜色系统。这些颜色变量能够保持应用程序的视觉风格一致，同时让您能够根据设计需求完全控制调色板的自定义。

您可以使用 `--dwc-color-{palette}-{step}` 语法引用任何颜色，其中 `{palette}` 是颜色组的名称（例如，`primary`、`danger` 等），`{step}` 是从 `5` 到 `95` 的数字，步长为 `5`，表示颜色的明亮程度。

```css
.element {
  background-color: var(--dwc-color-primary-50);
  color: var(--dwc-color-on-primary-text-50);
}
```

:::tip 阶段步进比例
步进值的范围从 `5`（最深）到 `95`（最浅），以 `5` 为步长递增。第 5 步始终是最深的，第 95 步始终是最浅的，无论是亮模式还是暗模式。
:::

## 颜色调色板 {#color-palettes}

DWC 配置了七种调色板以及 `black/white` 调色板，每种调色板都是一种语义颜色的变化（阴影和色调）的集合。

### 可用的调色板 {#available-palettes}

- **default**: 一种中性色调板，掺入了主要色调，适用于大多数组件的背景、边框和中性色彩元素。
- **primary**: 通常代表您品牌的主要颜色。
- **success**、**warning**、**danger**: 用于适当状态指示的语义调色板。
- **info**: 用于次要强调的补充调色板。
- **gray**: 一种纯灰度调色板，未掺色。
- **black/white**: 动态模式感知颜色，能够自动适应当前主题。在亮模式下几乎是黑色，在暗模式下变为几乎白色，反之亦然。

<dwc-doc-palettes></dwc-doc-palettes>

### 调色板种子 {#palette-seeds}

每种调色板是从两个种子变量生成的：`hue` 和 `saturation`。设置这两个值可以自动生成所有 19 个步骤。

| 种子变量 | 描述 |
|---|---|
| `--dwc-color-{name}-h` | 种子颜色的色相角度（0-360）。 |
| `--dwc-color-{name}-s` | 饱和度百分比。`100%` 是完全饱和，`0%` 是完全无饱和（灰色）。 |

您可以通过在根样式中重新定义这些变量来调整调色板。例如，要修改主要调色板：

```css
:root {
  --dwc-color-primary-h: 225;
  --dwc-color-primary-s: 100%;
}
```

<Tabs>

<TabItem value="Primary">

| 变量 | 默认值 |
|---|---|
| `--dwc-color-primary-h` | 223 |
| `--dwc-color-primary-s` | 91% |

</TabItem>

<TabItem value="Success">

| 变量 | 默认值 |
|---|---|
| `--dwc-color-success-h` | 153 |
| `--dwc-color-success-s` | 60% |

</TabItem>

<TabItem value="Warning">

| 变量 | 默认值 |
|---|---|
| `--dwc-color-warning-h` | 35 |
| `--dwc-color-warning-s` | 90% |

</TabItem>

<TabItem value="Danger">

| 变量 | 默认值 |
|---|---|
| `--dwc-color-danger-h` | 4 |
| `--dwc-color-danger-s` | 90% |

</TabItem>

<TabItem value="Info">

| 变量 | 默认值 |
|---|---|
| `--dwc-color-info-h` | 262 |
| `--dwc-color-info-s` | 65% |

</TabItem>

<TabItem value="Gray">

| 变量 | 默认值 |
|---|---|
| `--dwc-color-gray-h` | 0 |
| `--dwc-color-gray-s` | 0% |

</TabItem>

<TabItem value="Default / Tone">

| 变量 | 默认值 |
|---|---|
| `--dwc-color-default-h` | var(--dwc-color-primary-h) |
| `--dwc-color-default-s` | 3% |

</TabItem>

</Tabs>

### 直接种子覆盖 {#direct-seed-override}

每种调色板还暴露了一个 `--dwc-color-{name}-seed` 变量。默认情况下，它是由色相和饱和度值构成的，但您可以用任何有效的 CSS 颜色直接覆盖它，以完全绕过色相/饱和度系统。

```css
:root {
  --dwc-color-primary-seed: #6366f1;
}
```

### 色相旋转 {#hue-rotation}

调色板生成器在步骤之间应用细微的色相旋转，以创建更自然的调色板。更深的阴影会略微偏暖，而更浅的阴影则会略微偏冷。这模仿了真实颜料的行为，并防止调色板显得平坦或合成。

| 变量 | 默认值 | 描述 |
|---|---|---|
| `--dwc-color-hue-rotate` | 3 | 调色板中色相偏移的度数。设置为 0 可以禁用。 |

<dwc-doc-hue-rotate name="primary"></dwc-doc-hue-rotate>

### 每一步生成的变量 {#generated-variables-per-step}

每种调色板生成 19 个步骤（5 到 95）。对于每个步骤，产生以下变量：

| 变量模式 | 描述 |
|---|---|
| `--dwc-color-{name}-{step}` | 该步骤的调色板阴影。 |
| `--dwc-color-{name}-text-{step}` | 从该步骤派生的安全文本颜色（符合 WCAG AA 标准）。 |
| `--dwc-color-on-{name}-text-{step}` | 用于该阴影作为背景的文本颜色（自动翻转光/暗）。 |

:::tip 可访问性
所有生成的文本颜色自动满足 WCAG AA 对比要求。您无需自己计算对比比率。
:::

顶部行显示了阴影及其 `on-text` 颜色（用于直接放置在该阴影上的文本）。底部行显示在表面背景上的 `text` 颜色：

<dwc-doc-step-vars name="primary"></dwc-doc-step-vars>

### 其他生成的变量 {#additional-generated-variables}

| 变量模式 | 描述 |
|---|---|
| `--dwc-color-{name}-tint` | 种子颜色在 12% 不透明度下，用于细微高亮背景。 |
| `--dwc-border-color-{name}` | 感知模式的边框颜色，全色调的边框颜色。 |
| `--dwc-border-color-{name}-emphasis` | 用于悬停、聚焦和活动状态的更强的感知模式边框颜色。 |
| `--dwc-focus-ring-{name}` | 调色板的焦点环阴影。 |

## 全局颜色 {#global-colors}

这些是模式感知颜色，能够自动适应明暗主题。

| 变量 | 描述 |
|---|---|
| `--dwc-color-black` | 在亮模式中接近黑色，在暗模式中接近白色。 |
| `--dwc-color-white` | 在亮模式中接近白色，在暗模式中接近黑色。 |
| `--dwc-color-body-text` | 默认的主体文本颜色（使用 `--dwc-color-black`）。 |

## 组件主题 {#theming-components-with-abstract-variables}

DWC 将可用调色板的使用抽象化为一组更高级的语义变化变量。组件使用这些变化而不是原始的步骤数字，因为变化能够自动适应亮暗模式。

这些变化分为三组：`normal`、`dark` 和 `light`。

1. `normal` 变量是基础颜色，用于背景和主要用户界面元素。
2. `dark` 变量主要用于 `active/pressed` 状态。
3. `light` 变量主要用于 `hover/focus` 状态。

<Tabs>

<TabItem value="Primary">

<dwc-doc-variations name="primary"></dwc-doc-variations>

```css
--dwc-color-primary-dark: var(--dwc-color-primary-45)
--dwc-color-primary: var(--dwc-color-primary-50)
--dwc-color-primary-light: var(--dwc-color-primary-55)
--dwc-color-primary-alt: var(--dwc-color-primary-tint)

--dwc-color-primary-text-dark: var(--dwc-color-primary-text-40)
--dwc-color-primary-text: var(--dwc-color-primary-text-45)
--dwc-color-primary-text-light: var(--dwc-color-primary-text-50)

--dwc-color-on-primary-text-dark: var(--dwc-color-on-primary-text-45)
--dwc-color-on-primary-text: var(--dwc-color-on-primary-text-50)
--dwc-color-on-primary-text-light: var(--dwc-color-on-primary-text-55)
--dwc-color-on-primary-text-alt: var(--dwc-color-primary-text)
```

</TabItem>

<TabItem value="Success">

<dwc-doc-variations name="success"></dwc-doc-variations>

```css
--dwc-color-success-dark: var(--dwc-color-success-45)
--dwc-color-success: var(--dwc-color-success-50)
--dwc-color-success-light: var(--dwc-color-success-55)
--dwc-color-success-alt: var(--dwc-color-success-tint)

--dwc-color-success-text-dark: var(--dwc-color-success-text-40)
--dwc-color-success-text: var(--dwc-color-success-text-45)
--dwc-color-success-text-light: var(--dwc-color-success-text-50)

--dwc-color-on-success-text-dark: var(--dwc-color-on-success-text-45)
--dwc-color-on-success-text: var(--dwc-color-on-success-text-50)
--dwc-color-on-success-text-light: var(--dwc-color-on-success-text-55)
--dwc-color-on-success-text-alt: var(--dwc-color-success-text)
```

</TabItem>

<TabItem value="Warning">

<dwc-doc-variations name="warning"></dwc-doc-variations>

```css
--dwc-color-warning-dark: var(--dwc-color-warning-45)
--dwc-color-warning: var(--dwc-color-warning-50)
--dwc-color-warning-light: var(--dwc-color-warning-55)
--dwc-color-warning-alt: var(--dwc-color-warning-tint)

--dwc-color-warning-text-dark: var(--dwc-color-warning-text-40)
--dwc-color-warning-text: var(--dwc-color-warning-text-45)
--dwc-color-warning-text-light: var(--dwc-color-warning-text-50)

--dwc-color-on-warning-text-dark: var(--dwc-color-on-warning-text-45)
--dwc-color-on-warning-text: var(--dwc-color-on-warning-text-50)
--dwc-color-on-warning-text-light: var(--dwc-color-on-warning-text-55)
--dwc-color-on-warning-text-alt: var(--dwc-color-warning-text)
```

</TabItem>

<TabItem value="Danger">

<dwc-doc-variations name="danger"></dwc-doc-variations>

```css
--dwc-color-danger-dark: var(--dwc-color-danger-45)
--dwc-color-danger: var(--dwc-color-danger-50)
--dwc-color-danger-light: var(--dwc-color-danger-55)
--dwc-color-danger-alt: var(--dwc-color-danger-tint)

--dwc-color-danger-text-dark: var(--dwc-color-danger-text-40)
--dwc-color-danger-text: var(--dwc-color-danger-text-45)
--dwc-color-danger-text-light: var(--dwc-color-danger-text-50)

--dwc-color-on-danger-text-dark: var(--dwc-color-on-danger-text-45)
--dwc-color-on-danger-text: var(--dwc-color-on-danger-text-50)
--dwc-color-on-danger-text-light: var(--dwc-color-on-danger-text-55)
--dwc-color-on-danger-text-alt: var(--dwc-color-danger-text)
```

</TabItem>

<TabItem value="Info">

<dwc-doc-variations name="info"></dwc-doc-variations>

```css
--dwc-color-info-dark: var(--dwc-color-info-45)
--dwc-color-info: var(--dwc-color-info-50)
--dwc-color-info-light: var(--dwc-color-info-55)
--dwc-color-info-alt: var(--dwc-color-info-tint)

--dwc-color-info-text-dark: var(--dwc-color-info-text-40)
--dwc-color-info-text: var(--dwc-color-info-text-45)
--dwc-color-info-text-light: var(--dwc-color-info-text-50)

--dwc-color-on-info-text-dark: var(--dwc-color-on-info-text-45)
--dwc-color-on-info-text: var(--dwc-color-on-info-text-50)
--dwc-color-on-info-text-light: var(--dwc-color-on-info-text-55)
--dwc-color-on-info-text-alt: var(--dwc-color-info-text)
```

</TabItem>

<TabItem value="Default / Tone">

<dwc-doc-variations name="default"></dwc-doc-variations>

默认变化用于中性用户界面元素，如组件背景和边框。它继承其色相于主要调色板并具有非常低的饱和度。与色彩调色板不同，默认使用自己的 OKLCH 明度计算，而不是调色板步骤。

```css
--dwc-color-default-dark
--dwc-color-default
--dwc-color-default-light
--dwc-color-default-alt: var(--dwc-color-primary-alt)

--dwc-color-default-text-dark: var(--dwc-color-default-text-40)
--dwc-color-default-text: var(--dwc-color-default-text-45)
--dwc-color-default-text-light: var(--dwc-color-default-text-50)

--dwc-color-on-default-text-dark
--dwc-color-on-default-text
--dwc-color-on-default-text-light
--dwc-color-on-default-text-alt: var(--dwc-color-primary-text)

--dwc-focus-ring-default: var(--dwc-focus-ring-primary)
```

</TabItem>

<TabItem value="Gray">

<dwc-doc-variations name="gray"></dwc-doc-variations>

灰色变化使用纯灰色调，能够感知模式，在亮模式下选择深色步骤，在暗模式下选择浅色步骤。

```css
--dwc-color-gray-dark
--dwc-color-gray
--dwc-color-gray-light
--dwc-color-gray-alt: var(--dwc-color-gray-tint)

--dwc-color-gray-text-dark: var(--dwc-color-gray-text-40)
--dwc-color-gray-text: var(--dwc-color-gray-text-45)
--dwc-color-gray-text-light: var(--dwc-color-gray-text-50)

--dwc-color-on-gray-text-dark
--dwc-color-on-gray-text
--dwc-color-on-gray-text-light
--dwc-color-on-gray-text-alt: var(--dwc-color-gray-text)
```

</TabItem>

</Tabs>

### 变化参考 {#variation-reference}

| 变量 | 描述 |
|---|---|
| `--dwc-color-{name}` | 基础颜色。用于背景、填充和边框。 |
| `--dwc-color-{name}-dark` | 更深的版本。用于活动/按下状态。 |
| `--dwc-color-{name}-light` | 更浅的版本。用于悬停/聚焦状态。 |
| `--dwc-color-{name}-alt` | 种子颜色在 12% 不透明度下。用于细微高亮状态。 |
| `--dwc-color-{name}-text` | 在应用表面上安全的文本颜色（WCAG AA）。 |
| `--dwc-color-{name}-text-dark` | 更深的文本变化。 |
| `--dwc-color-{name}-text-light` | 更浅的文本变化。 |
| `--dwc-color-on-{name}-text` | 用于 `--dwc-color-{name}` 作为背景的文本颜色。 |
| `--dwc-color-on-{name}-text-dark` | 用于 `--dwc-color-{name}-dark` 的文本颜色。 |
| `--dwc-color-on-{name}-text-light` | 用于 `--dwc-color-{name}-light` 的文本颜色。 |
| `--dwc-color-on-{name}-text-alt` | 用于 `--dwc-color-{name}-alt` 的文本颜色。 |
| `--dwc-border-color-{name}` | 能够感知模式的边框颜色。 |
| `--dwc-border-color-{name}-emphasis` | 更强的模式感知边框颜色。 |
| `--dwc-focus-ring-{name}` | 焦点环阴影。 |
