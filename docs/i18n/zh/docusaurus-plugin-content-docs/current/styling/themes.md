---
sidebar_position: 2
title: Themes
sidebar_class_name: updated-content
description: >-
  Apply built-in light, dark, and dark-pure themes with @AppTheme or define
  custom themes through data-app-theme selectors.
_i18n_hash: 91e1a18f11aadea66df804dbaa4917d9
---
在 webforJ 中，主题是一组命名的 CSS 自定义属性（设计令牌），用于控制每个组件的外观。切换主题会瞬间重新计算整个应用中的颜色、阴影、表面和边框，而无需重建。

## 内置主题 {#built-in-themes}

webforJ 默认提供三种应用主题：

| 主题         | 背景             | 色调                          |
|--------------|------------------|-------------------------------|
| `light`      | 明亮（默认）     | 微妙的主色调色调               |
| `dark`       | 暗色             | 微妙的主色调色调               |
| `dark-pure`  | 暗色             | 无（纯中性色调）               |

任何应用都可以在运行时在它们之间切换，并且可以在现有主题的基础上定义额外的自定义主题。

## 应用主题 {#applying-a-theme}

使用 `@AppTheme` 注解声明性设置活动主题，或使用 `App.setTheme()` 以编程方式设置。主题名称必须是 `system`、`light`、`dark`、`dark-pure` 或自定义主题的名称之一。

```java
@AppTheme("dark-pure")
class MyApp extends App {
  // 应用代码
}

// 或者以编程方式
App.setTheme("dark-pure");
```

随时再次调用 `App.setTheme()` 将应用切换到不同的主题。

## 颜色方案 {#color-scheme}

`color-scheme` CSS 声明告诉浏览器如何渲染其内置表面，例如原生滚动条、表单控件小部件、自动填充高亮和默认页面背景，CSS 加载之前。内置的 `dark` 和 `dark-pure` 主题已经为您设置了 `color-scheme: dark`，因此浏览器界面会自动与暗色表面融合。

当定义您自己的自定义暗主题时，您才需要考虑这个问题。在这种情况下，请在主题选择器上包含 `color-scheme: dark`：

```css
html[data-app-theme="brand-dark"] {
  --dwc-dark-mode: 1;
  color-scheme: dark;
}
```

如果您跳过它，滚动条和自动填充矩形默认为亮色模式，并且在暗色表面上看起来不协调。亮主题不需要该声明，浏览器默认使用亮色模式。

## 跟随用户的偏好 {#following-the-users-preference}

大多数操作系统允许用户选择全局的亮色或暗色外观。webforJ 可以尊重该偏好并自动选择正确的主题。

通过 `@AppLightTheme` 和 `@AppDarkTheme`（或 `App.setLightTheme()` 和 `App.setDarkTheme()`）注册每个外观状态要应用的主题，然后将保留关键字 `"system"` 传递给 `App.setTheme()`（或 `@AppTheme("system")`），让 webforJ 根据用户的操作系统偏好在它们之间进行选择。

```java
@AppTheme("system")
@AppLightTheme("light")
@AppDarkTheme("dark")
class MyApp extends App {
  // 应用代码
}
```

等效的编程形式：

```java
App.setLightTheme("light");
App.setDarkTheme("dark");
App.setTheme("system");
```

`"system"` 是一个保留关键字。webforJ 在运行时将其解析为注册的亮色或暗色主题，并在操作系统偏好更改时自动重新解析。一旦解析，页面上的实际 `data-app-theme` 属性为 `light` 或 `dark`，因此任何 CSS 覆盖应针对这些名称，而不是 `"system"`。

:::info 操作系统级外观设置
用户启用系统范围的外观设置的位置因平台而异：

- **Windows 10/11**：设置 > 个性化 > 颜色 > 选择您的颜色
- **macOS**：系统设置 > 外观
- **iOS**：设置 > 显示与亮度 > 外观
- **Android**：设置 > 显示 > 暗色主题
:::

## 覆盖默认主题 {#overriding-default-themes}

大多数品牌工作是通过**覆盖现有主题**而不是创建新主题来完成的。重新调整内置的 `light`、`dark` 和 `dark-pure` 主题的种子颜色（或任何其他令牌），每个组件都会自动采用新的外观。

您可以通过在 `:root` 选择器中重新定义 CSS 自定义属性来覆盖 **light** 主题。

:::info `:root` 伪类
`:root` CSS 伪类定位文档的根元素。在 HTML 中，它表示 `<html>` 元素，并且具有比普通 `html` 选择器更高的特异性。
:::

```css
:root {
  --dwc-color-primary-h: 215;
  --dwc-color-primary-s: 100%;
  --dwc-font-size: var(--dwc-font-size-l);
}
```

要覆盖 **dark** 或 **dark-pure** 主题，请对 `<html>` 元素使用属性选择器：

```css
html[data-app-theme="dark"] {
  --dwc-color-primary-seed: #a855f7;
}

html[data-app-theme="dark-pure"] {
  --dwc-color-primary-seed: #a855f7;
}
```

使用 `App.setTheme("dark")` 切换主题将激活重新品牌的暗主题，无需新的主题名称。

## 创建自定义主题 {#creating-custom-themes}

仅在需要与内置主题共存的全新主题时创建 (例如，高对比度变体或特定客户的皮肤)。选择一个唯一的名称，并在其自己的 `html[data-app-theme='THEME_NAME']` 选择器下定义它：

```css
html[data-app-theme="new-theme"] {
  --dwc-color-primary-h: 280;
  --dwc-color-primary-s: 100%;
}
```

要使自定义主题暗色，请设置 `--dwc-dark-mode: 1` 和 `color-scheme: dark`：

```css
html[data-app-theme="new-dark-theme"] {
  --dwc-dark-mode: 1;
  --dwc-color-primary-h: 280;
  --dwc-color-primary-s: 100%;
  color-scheme: dark;
}
```

然后在您的应用中：

```java
@AppTheme("new-theme")
class MyApp extends App {
  // 应用代码
}

// 或者以编程方式
App.setTheme("new-theme");
```

## 使用 DWC 令牌 {#working-with-dwc-tokens}

一些习惯可以使自定义 CSS 与设计系统保持一致，并防止其在暗模式或未来版本中漂移。

### 始终使用 `var(...)` 引用令牌 {#always-reference-tokens-with-var}

硬编码颜色字面量（`#3b82f6`、`rgb(59 130 246)`、`oklch(0.6 0.18 250)`）不能适应暗模式，并且无法跟踪调色板的变化。请使用令牌。

```css
/* 避免 */
.my-panel {
  background: #ffffff;
  color: #1f2937;
  border: 1px solid #e5e7eb;
}

/* 优选 */
.my-panel {
  background: var(--dwc-surface-3);
  color: var(--dwc-color-body-text);
  border: 1px solid var(--dwc-border-color);
}
```

### 优先使用变体令牌而不是原始步长 {#prefer-variation-tokens-over-raw-step-numbers}

变体令牌（`--dwc-color-primary`、`-dark`、`-light`、`-text`、`-alt`）在亮色和暗色模式下会自动解析为不同的步长。原始步长数字（`--dwc-color-primary-50`）则不会。

```css
/* 避免 - 在两种模式下固定为步长 50 */
.badge {
  background: var(--dwc-color-primary-50);
}

/* 优选 - 在暗模式下移步 */
.badge {
  background: var(--dwc-color-primary);
}
```

### 使用与角色匹配的后缀 {#use-the-suffix-that-matches-the-role}

| 后缀                          | 角色                                                              |
|-----------------------------|-------------------------------------------------------------------|
| `--dwc-color-{name}`       | 全强度的实心填充（按钮、徽章、横幅）                                |
| `--dwc-color-{name}-dark`  | 激活/按下状态                                                    |
| `--dwc-color-{name}-light` | 悬停/聚焦背景                                                  |
| `--dwc-color-{name}-alt`   | 用于呼叫和替代行的微妙色调背景                                   |
| `--dwc-color-{name}-text`  | 在中性色表面上的彩色文本                                       |
| `--dwc-color-on-{name}-text`| 在有色阴影作为背景的情况下放置的文本（自动对比）               |
| `--dwc-border-color-{name}`| 边框和分隔线                                                    |

### 为其角色保留表面和边框 {#reserve-surfaces-and-borders-for-their-roles}

表面（`--dwc-surface-1` / `-2` / `-3`）构建页面层次结构。边框（`--dwc-border-color`、`--dwc-border-color-*`）用于绘制分隔线。重用这些角色的调色板步长在视觉上是有效的，但失去了专用令牌所携带的自动模式适应能力。

### 在自定义主题的种子级别覆盖 {#override-at-the-seed-level-in-custom-themes}

在构建自定义主题时，设置种子（`--dwc-color-{name}-h`、`-s` 或 `-seed`），而不是覆盖单独的步长。生成器将围绕种子重建完整的 19 步调色板，使整个色调范围保持一致。覆盖单独的步长会导致调色板的其余部分与您的品牌颜色不符。

```css
/* 避免 - 使其他步长不一致 */
html[data-app-theme="brand"] {
  --dwc-color-primary-50: #6366f1;
}

/* 优选 - 重新生成整个调色板 */
html[data-app-theme="brand"] {
  --dwc-color-primary-seed: #6366f1;
}
```

### 使用令牌进行间距、大小、圆角和过渡 {#use-tokens-for-spacing-sizing-radius-and-transitions}

同样的规则适用于设计系统的其他部分：引用令牌，而不是魔法数字。

```css
/* 避免 */
.my-panel {
  padding: 16px;
  border-radius: 8px;
  transition: background-color 250ms;
}

/* 优选 */
.my-panel {
  padding: var(--dwc-space-m);
  border-radius: var(--dwc-border-radius);
  transition: background-color var(--dwc-transition);
}
```

硬编码值绕过用户偏好字体大小缩放，锁定您进入固定的形状语言，并跳过设计系统的缓和时间曲线。

### 使用 `::part(...)` 访问组件 {#use-part-to-reach-into-components}

webforJ 组件是 Shadow DOM。它们的内部标记对外部选择器是隐藏的，因此像 `.dwc-button-label { ... }` 这样的规则将不会匹配任何内容。要样式化内部部分，请定位公开的部分：

```css
/* 样式化每个主按钮内部的标签 */
dwc-button[theme="primary"]::part(label) {
  letter-spacing: 0.02em;
}
```

查看 [Shadow Parts](./shadow-parts) 以获取完整机制，以及每个组件的 **样式 → Shadow Parts** 部分以获取暴露的部分。

### 使用包装选择器作用域令牌覆盖 {#scope-token-overrides-with-a-wrapper-selector}

CSS 自定义属性具有级联效果。在包装元素上设置令牌会重新调整其内部的所有内容，而不影响应用程序的其余部分。

```css
.danger-section {
  --dwc-color-primary-seed: #ef4444;
}
```

`.danger-section` 内部的每个组件（按钮、链接、聚焦环）现在都使用危险红色调，而全局主题保持不变。

### 在亮色和暗色模式下进行测试 {#test-in-both-light-and-dark-mode}

在发布任何自定义 CSS 之前，将主题切换到 `dark` 和 `dark-pure`，并遍历屏幕。最常见的回归是硬编码颜色值在一种模式下看起来很好，而在另一种模式下则无法辨认或超出调色板。

### 不要使用 `!important` {#dont-reach-for-important}

它会逃避级联并使未来的每个覆盖变得更加困难。如果一个规则没有生效，原因几乎总是和特异性不匹配有关，使用更简洁的修复：定位框架使用的相同选择器，或添加父级限定符。将 `!important` 保留用于确实没有其他方法可以击败的第三方样式。

## 组件主题 {#component-themes}

除了应用级主题，webforJ 组件还支持一组基于默认调色板的 **组件主题**：`default`、`primary`、`success`、`warning`、`danger`、`info` 和 `gray`。这与当前活动的应用主题无关。

每个组件在 **样式 → 主题** 部分中文档化其支持的主题。
