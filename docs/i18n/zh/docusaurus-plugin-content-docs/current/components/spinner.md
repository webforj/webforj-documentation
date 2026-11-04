---
title: Spinner
sidebar_position: 110
description: >-
  Indicate background activity with the Spinner component, configuring theme,
  expanse, rotation speed, and pause or resume.
_i18n_hash: 6a667297edb572a6990b58bee72c8b1b
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-spinner" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="spinner" location="com/webforj/component/spinner/Spinner" top='true'/>

`Spinner` 组件提供了一个视觉指示器，用于表示正在进行的处理或后台加载。它通常用于显示系统正在获取数据或者在处理需要时间完成的操作时。`Spinner` 提供用户反馈，传达系统正在积极工作。

<!-- INTRO_END -->

创建一个 `Spinner` 实例，然后通过 `setTheme()` 和 `setExpanse()` 等方法定义它的外观和行为。

<ComponentDemo
path='/webforj/spinnerdemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerDemoView.java']}
height='225px'
/>

## 管理速度与暂停 {#managing-speed-and-pausing}

可以设定 `Spinner` 的速度（以毫秒为单位）并轻松实现动画的暂停/恢复。

设定速度的用例包括区分加载过程。例如，较快的 `Spinners` 适合较小的任务，而较慢的 `Spinners` 更适合较大的任务。当需要用户操作或确认后再继续流程时，暂停是非常有用的。

### 调整速度 {#adjusting-speed}

可以通过使用 `setSpeed()` 方法调整 `Spinner` 的旋转速度（以毫秒为单位）。较低的值使 `Spinner` 旋转得更快，而较高的值则会减慢旋转速度。

```java
spinner.setSpeed(500); // 旋转更快
```

:::info 默认速度
默认情况下，`Spinner` 完成一次完整旋转需1000毫秒。
:::

### 暂停与恢复 {#pausing-and-resuming}

在程序暂时中止或等待用户输入时，暂停 `Spinner` 是有用的。这可以让用户知道程序处于暂停状态，而不是在积极运行，从而在多步骤过程中增强清晰度。

要暂停和恢复 `Spinner`，请使用 `setPaused()` 方法。这在需要临时停止旋转动画时特别有帮助。

```java
spinner.setPaused(true);  // 暂停spinner
spinner.setPaused(false); // 恢复spinner
```

以下示例展示了如何设置速度以及如何暂停/恢复 `Spinner`：

<ComponentDemo
path='/webforj/spinnerspeeddemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerSpeedDemoView.java']}
height='150px'
/>

## 旋转方向 {#spin-direction}

可以控制 `Spinner` 旋转的方向为 **顺时针** 或 **逆时针**。可以通过使用 `setClockwise()` 方法指定此行为。

```java
spinner.setClockwise(false);  // 逆时针旋转
spinner.setClockwise(true);   // 顺时针旋转
```

此选项可以直观地指示特殊状态或作为独特的设计选择。改变旋转方向可以帮助区分不同类型的过程，例如进度与逆转，或在特定上下文中提供独特的视觉提示。

<ComponentDemo
path='/webforj/spinnerdirectiondemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerDirectionDemoView.java']}
height='150px'
/>

## 样式 {#styling}

### 主题 {#themes}

`Spinner` 组件配备了几种内置主题，使您能够快速应用样式，无需自定义 CSS。这些主题改变了旋转器的外观，使其适用于不同的用例和上下文。使用这些预定义的主题可确保在您的应用中样式的一致性。

虽然旋转器适用于多种情况，以下是不同主题的一些示例用例：

- **主色**：理想用于强调关键用户流程中的加载状态，例如在提交表单或处理重要操作时。

- **成功**：用于表示成功的后台处理，例如当用户提交表单，应用正在执行最终步骤时。

- **危险**：用于风险或高风险操作，比如删除重要数据或进行不可逆的更改，此时需要可视化的紧迫性或注意提示。

- **警告**：用于指示谨慎或不急迫的过程，例如用户等待数据验证，但不需要立即行动。

- **灰色**：适用于微妙的后台处理，例如低优先级或被动加载任务，如提取辅助数据，这些数据并不会直接影响用户体验。

- **信息**：适用于加载场景，在此场景中，您会为用户提供额外的信息或说明，例如在解释正在进行的过程时，展示一个旋转器和一条消息。

您可以通过编程方式将这些主题应用于旋转器，提供与操作的上下文和重要性相符的视觉提示。

您可以使用 `setTheme()` 方法指定此行为。

<ComponentDemo
path='/webforj/spinnerthemedemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerThemeDemoView.java']}
height='100px'
/>

### 尺寸 {#expanses}

可以使用 `setExpanse()` 和 `SpinnerExpanse` 枚举调整旋转器的大小，称为 **expanse**。可用的九种尺寸分别为 `XXXSMALL`、`XXSMALL`、`XSMALL`、`SMALL`、`MEDIUM`、`LARGE`、`XLARGE`、`XXLARGE` 和 `XXXLARGE`。默认值为 `SpinnerExpanse.NONE`。

```java
spinner.setExpanse(SpinnerExpanse.LARGE);
```

<ComponentDemo
path='/webforj/spinnerexpansedemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerExpanseDemoView.java']}
height='100px'
/>

<TableBuilder name="Spinner" />
