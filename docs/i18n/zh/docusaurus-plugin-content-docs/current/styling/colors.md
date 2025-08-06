---
sidebar_position: 3
title: Colors
_i18n_hash: 257b725aa7a7992bc6cedd91de9c9ca5
---
import ColorPalette from '@site/src/components/DWCTheme/ColorPalette/ColorPalette';

webforJ 提供了一种基于 CSS 自定义属性的颜色系统。这些颜色变量可以确保您的应用程序在视觉样式上的一致性，同时让您完全控制根据设计需求自定义调色板。

您可以使用 `--dwc-color-{palette}-{shade}` 语法引用任何颜色，其中 `{palette}` 是颜色组的名称（如 `primary`、`danger` 等），`{shade}` 是从 `5` 到 `95` 的数字，以 `5` 为增量，表示颜色的明度。

```css
.element {
  background-color: var(--dwc-color-primary-40);
  color: var(--dwc-color-primary-text-40);
}
```

:::tip 色调值范围
色调值范围从 `5`（最暗）到 `95`（最亮），以 `5` 为步骤递增。
:::

## 颜色调色板 {#color-palettes}

有几种内置的颜色调色板，每种调色板都是为了语义用途而设计，例如品牌、成功消息、警告等。每种调色板都是根据三个关键属性动态生成的调子和色调：`hue`、`saturation` 和 `contrast-threshold`。

### 可用的调色板 {#available-palettes}

- **default**：一种中性色灰调色板，使用主要颜色进行着色，用于大多数组件。
- **primary**：通常表示您品牌的主要颜色。
- **success**, **warning**, **danger**：用于适当状态指示器的语义调色板。
- **info**：一种可选的补充调色板，用于第二强调。
- **gray**：真正的灰度调色板，未着色。
- **black/white**：静态颜色值

<ColorPalette></ColorPalette>

<br/>

:::tip DWC HueCraft
为了简化为您的 webforJ 应用程序生成符合 WCAG 标准的调色板的过程，您可以使用 [DWC HueCraft](https://webforj.github.io/huecraft/) 工具。它支持基于品牌颜色或徽标创建调色板，并允许快速 CSS 导出。
:::

### 黑暗模式行为 {#dark-mode-behavior}

webforJ 支持在黑暗模式中使用反转颜色策略。它不是使用完全独立的颜色调色板，而是反转明度值的解释方式。

这意味着在 **黑暗主题** 中，较低的色调值（例如 `--dwc-color-primary-5`）变得明亮，而较高的值（例如 `--dwc-color-primary-95`）变得黑暗。逻辑被反转，以确保在背景上具有最佳对比度和可读性。

无论主题如何，您的组件代码保持不变。例如：

```css
.button {
  background-color: var(--dwc-color-primary-40);
  color: var(--dwc-color-primary-text-40);
}
```

在明亮模式下，这提供了中等色调的背景。在黑暗模式下，它仍然提供中等色调，但在视觉上反转以适应黑暗表面。这种方法避免了重复，使您的样式保持一致，并在在明亮与黑暗主题之间切换时保持视觉过渡平滑。

### 调色板配置变量 {#palette-configuration-variables}

每个调色板是基于以下变量生成的：

| 变量                         | 描述                                                                 |
|------------------------------|----------------------------------------------------------------------|
| `hue`                        | 颜色轮上的角度（以度为单位）。无单位的值被解释为度。                        |
| `saturation`                 | 表示颜色强度的百分比。`100%` 是完全饱和；`0%` 为灰度。                   |
| `contrast-threshold`         | 一个介于 `0` 和 `100` 之间的值，决定文本在背景明度基础上是浅色还是深色。 |

您可以通过在根样式中重新定义这些变量来调整调色板。例如，要修改主调色板：

```css
:root {
  --dwc-color-primary-h: 225;
  --dwc-color-primary-s: 100%;
  --dwc-color-primary-c: 60;
}
```

## 使用抽象变量主题化组件 {#theming-components-with-abstract-variables}

为了简化样式和跨组件的一致性，webforJ 引入了对基本颜色调色板的抽象层。该层建立在 **抽象主题变量** 之上——引用颜色调色板中特定色调的 CSS 自定义属性。

这些变量使得在所有组件上应用主题变得更容易，而无需直接引用原始颜色值或色板。您可以将其视为反映您应用程序意图的 *语义样式快捷方式*，而不是其实现细节。

### 变量组 {#variable-groups}

抽象主题变量组织成四组：

1. [正常](#normal-state) 用于默认外观，例如背景和未激活组件上的文本。
2. [较暗](#darker-variant) 用于活动或已选择状态。
3. [较亮](#lighter-variant) 用于悬停和焦点状态。
4. [替代](#alt-variant) 用于二次强调，例如键盘焦点或细微的 UI 亮点。

每组定义：

- 背景颜色
- 前景（文本）颜色
- 边框颜色（用于聚焦/悬停/活动状态）
- 聚焦环（在组件获得可见焦点样式时使用）

下面的每个选项卡显示为特定调色板（`primary`、`success`、`danger`等）定义的抽象变量。这些变量从基础调色板中拉取值（例如 `--dwc-color-primary-40`），并在您的应用中可重用。

例如，您可以应用 `--dwc-color-primary`，而不是直接使用 `--dwc-color-primary-40`，这抽象了该颜色作为主要风格组件的 *默认背景* 的角色。

在一个地方更改调色板值将更新所有依赖于这些抽象变量的组件的外观。

### 正常状态 {#normal-state}

用于组件的基本、中性外观——当它处于闲置状态且未被交互时。

| 变量                             | 描述                                                               |
|----------------------------------|--------------------------------------------------------------------|
| `--dwc-color-${name}`            | 默认背景颜色。也用于许多组件的边框。                                  |
| `--dwc-color-on-${name}-text`    | 显示在默认背景上的文本颜色。                                          |
| `--dwc-color-${name}-text`       | 当组件置于表面背景上时的文本颜色。                                   |
| `--dwc-border-color-${name}`     | 边框颜色，主要用于悬停、聚焦和活动状态。                              |
| `--dwc-focus-ring-${name}`       | 当组件获得可见焦点样式时的聚焦环阴影。                               |

---

### 较暗变体 {#darker-variant}

用于被选择或活动状态——通常为更深的色调以获取更强的对比度和强调。

| 变量                                | 描述                                                                |
|-------------------------------------|---------------------------------------------------------------------|
| `--dwc-color-${name}-dark`          | 基本颜色的较暗版本，通常用于按下或选择的状态。                           |
| `--dwc-color-on-${name}-text-dark`  | 在深色背景上使用时的文本颜色。                                       |
| `--dwc-color-${name}-text-dark`     | 显示在表面上的稍微暗一点的文本替代方案。                               |

---

### 较亮变体 {#lighter-variant}

用于悬停、聚焦和较不主导的视觉提示。这些是为细微的交互反馈设计的柔和色调。

| 变量                                 | 描述                                                               |
|--------------------------------------|--------------------------------------------------------------------|
| `--dwc-color-${name}-light`          | 基本颜色的较亮版本。通常用于悬停/聚焦背景。                           |
| `--dwc-color-on-${name}-text-light`  | 在浅色背景上显示时的文本颜色。                                     |
| `--dwc-color-${name}-text-light`     | 在较不显著状态下使用的较亮文本色调。                               |

---

### 替代变体 {#alt-variant}

用于二次强调或 UI 高亮——例如键盘导航焦点轮廓或辅助指示。

| 变量                               | 描述                                                                |
|------------------------------------|---------------------------------------------------------------------|
| `--dwc-color-${name}-alt`          | 该颜色的非常浅版本，主要用于高亮或背景发光。                         |
| `--dwc-color-on-${name}-text-alt`  | 背景为替代（`alt`）颜色时的文本颜色。                               |

<Tabs>

<TabItem value="Default / Tone">

```css
--dwc-color-default-dark: var(--dwc-color-default-85);
--dwc-color-on-default-text-dark: var(--dwc-color-default-text-85);
--dwc-color-default-text-dark: var(--dwc-color-default-35);

--dwc-color-default: var(--dwc-color-default-90);
--dwc-color-on-default-text: var(--dwc-color-default-text-90);
--dwc-color-default-text: var(--dwc-color-default-40);

--dwc-color-default-light: var(--dwc-color-default-95);
--dwc-color-on-default-text-light: var(--dwc-color-default-text-95);
--dwc-color-default-text-light: var(--dwc-color-default-45);

--dwc-color-default-alt: var(--dwc-color-primary-alt);
--dwc-color-on-default-text-alt: var(--dwc-color-on-primary-text-alt);

--dwc-border-color-default: var(--dwc-border-color-primary);
--dwc-focus-ring-default: var(--dwc-focus-ring-primary);
```

</TabItem>

<TabItem value="Primary">

```css
--dwc-color-primary-dark: var(--dwc-color-primary-35);
--dwc-color-on-primary-text-dark: var(--dwc-color-primary-text-35);
--dwc-color-primary-text-dark: var(--dwc-color-primary-30);

--dwc-color-primary: var(--dwc-color-primary-40);
--dwc-color-on-primary-text: var(--dwc-color-primary-text-40);
--dwc-color-primary-text: var(--dwc-color-primary-35);

--dwc-color-primary-light: var(--dwc-color-primary-45);
--dwc-color-on-primary-text-light: var(--dwc-color-primary-text-45);
--dwc-color-primary-text-light: var(--dwc-color-primary-40);

--dwc-color-primary-alt: var(--dwc-color-primary-95);
--dwc-color-on-primary-text-alt: var(--dwc-color-primary-text-95);

--dwc-border-color-primary: var(--dwc-color-primary);
--dwc-focus-ring-primary: 0 0 0 var(--dwc-focus-ring-width) hsla(
    var(--dwc-color-primary-h),
    var(--dwc-color-primary-s),
    var(--dwc-focus-ring-l),
    var(--dwc-color-primary-a)
  );
```

</TabItem>

<TabItem value="Success">

```css
--dwc-color-success-dark: var(--dwc-color-success-20);
--dwc-color-on-success-text-dark: var(--dwc-color-success-text-20);
--dwc-color-success-text-dark: var(--dwc-color-success-15);

--dwc-color-success: var(--dwc-color-success-25);
--dwc-color-on-success-text: var(--dwc-color-success-text-25);
--dwc-color-success-text: var(--dwc-color-success-20);

--dwc-color-success-light: var(--dwc-color-success-30);
--dwc-color-on-success-text-light: var(--dwc-color-success-text-30);
--dwc-color-success-text-light: var(--dwc-color-success-25);

--dwc-color-success-alt: var(--dwc-color-success-95);
--dwc-color-on-success-text-alt: var(--dwc-color-success-text-95);

--dwc-border-color-success: var(--dwc-color-success);
--dwc-focus-ring-success: 0 0 0 var(--dwc-focus-ring-width) hsla(
    var(--dwc-color-success-h),
    var(--dwc-color-success-s),
    var(--dwc-focus-ring-l),
    var(--dwc-color-success-a)
  );
```

</TabItem>

<TabItem value="Warning">

```css
--dwc-color-warning-dark: var(--dwc-color-warning-35);
--dwc-color-on-warning-text-dark: var(--dwc-color-warning-text-35);
--dwc-color-warning-text-dark: var(--dwc-color-warning-15);

--dwc-color-warning: var(--dwc-color-warning-40);
--dwc-color-on-warning-text: var(--dwc-color-warning-text-40);
--dwc-color-warning-text: var(--dwc-color-warning-20);

--dwc-color-warning-light: var(--dwc-color-warning-45);
--dwc-color-on-warning-text-light: var(--dwc-color-warning-text-45);
--dwc-color-warning-text-light: var(--dwc-color-warning-25);

--dwc-color-warning-alt: var(--dwc-color-warning-95);
--dwc-color-on-warning-text-alt: var(--dwc-color-warning-text-95);

--dwc-border-color-warning: var(--dwc-color-warning);
--dwc-focus-ring-warning: 0 0 0 var(--dwc-focus-ring-width) hsla(
    var(--dwc-color-warning-h),
    var(--dwc-color-warning-s),
    var(--dwc-focus-ring-l),
    var(--dwc-color-warning-a)
  );
```

</TabItem>

<TabItem value="Danger">

```css
--dwc-color-danger-dark: var(--dwc-color-danger-35);
--dwc-color-on-danger-text-dark: var(--dwc-color-danger-text-35);
--dwc-color-danger-text-dark: var(--dwc-color-danger-30);

--dwc-color-danger: var(--dwc-color-danger-40);
--dwc-color-on-danger-text: var(--dwc-color-danger-text-40);
--dwc-color-danger-text: var(--dwc-color-danger-35);

--dwc-color-danger-light: var(--dwc-color-danger-45);
--dwc-color-on-danger-text-light: var(--dwc-color-danger-text-45);
--dwc-color-danger-text-light: var(--dwc-color-danger-40);

--dwc-color-danger-alt: var(--dwc-color-danger-95);
--dwc-color-on-danger-text-alt: var(--dwc-color-danger-text-95);

--dwc-border-color-danger: var(--dwc-color-danger);
--dwc-focus-ring-danger: 0 0 0 var(--dwc-focus-ring-width) hsla(
    var(--dwc-color-danger-h),
    var(--dwc-color-danger-s),
    var(--dwc-focus-ring-l),
    var(--dwc-color-danger-a)
  );
```

</TabItem>

<TabItem value="Info">

```css
--dwc-color-info-dark: var(--dwc-color-info-35);
--dwc-color-on-info-text-dark: var(--dwc-color-info-text-35);
--dwc-color-info-text-dark: var(--dwc-color-info-35);

--dwc-color-info: var(--dwc-color-info-40);
--dwc-color-on-info-text: var(--dwc-color-info-text-40);
--dwc-color-info-text: var(--dwc-color-info-40);

--dwc-color-info-light: var(--dwc-color-info-45);
--dwc-color-on-info-text-light: var(--dwc-color-info-text-45);
--dwc-color-info-text-light: var(--dwc-color-info-45);

--dwc-color-info-alt: var(--dwc-color-info-95);
--dwc-color-on-info-text-alt: var(--dwc-color-info-text-95);

--dwc-border-color-info: var(--dwc-color-info);
--dwc-focus-ring-info: 0 0 0 var(--dwc-focus-ring-width) hsla(
    var(--dwc-color-info-h),
    var(--dwc-color-info-s),
    var(--dwc-focus-ring-l),
    var(--dwc-color-info-a)
  );
```

</TabItem>

<TabItem value="Gray">

```css
--dwc-color-gray-dark: var(--dwc-color-gray-10);
--dwc-color-on-gray-text-dark: var(--dwc-color-gray-text-10);
--dwc-color-gray-text-dark: var(--dwc-color-gray-15);

--dwc-color-gray: var(--dwc-color-gray-15);
--dwc-color-on-gray-text: var(--dwc-color-gray-text-15);
--dwc-color-gray-text: var(--dwc-color-gray-20);

--dwc-color-gray-light: var(--dwc-color-gray-20);
--dwc-color-on-gray-text-light: var(--dwc-color-gray-text-20);
--dwc-color-gray-text-light: var(--dwc-color-gray-25);

--dwc-color-gray-alt: var(--dwc-color-gray-95);
--dwc-color-on-gray-text-alt: var(--dwc-color-gray-text-95);

--dwc-border-color-gray: var(--dwc-color-gray);
--dwc-focus-ring-gray: 0 0 0 var(--dwc-focus-ring-width) hsla(
    var(--dwc-color-gray-h),
    var(--dwc-color-gray-s),
    var(--dwc-focus-ring-l),
    var(--dwc-color-gray-a)
  );
```
</TabItem>

</Tabs>
