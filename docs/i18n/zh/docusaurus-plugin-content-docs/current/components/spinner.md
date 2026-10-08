---
title: Spinner
sidebar_position: 110
description: >-
  Indicate background activity with the Spinner component, configuring theme,
  expanse, rotation speed, and pause or resume.
_i18n_hash: 22812c9195f148410b746c3547a0f118
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-spinner" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="spinner" location="com/webforj/component/spinner/Spinner" top='true'/>

`Spinner` 组件提供了一种视觉指示器，用于表示后台正在进行的处理或加载。它通常用于显示系统正在获取数据或某个过程需要时间来完成。`Spinner` 提供用户反馈，表明系统正在积极工作。

<!-- INTRO_END -->

创建一个 `Spinner` 实例，然后使用 `setTheme()` 和 `setExpanse()` 等方法定义其外观和行为。

<ComponentDemo
path='/webforj/spinnerdemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerDemoView.java']}
height='225px'
/>

## 管理速度和暂停 {#managing-speed-and-pausing}

可以轻松设置 `Spinner` 的速度（毫秒），并暂停/恢复动画。

设置速度的使用案例包括区分加载过程。例如，较快的 `Spinners` 适合较小的任务，而较慢的 `Spinners` 更适合较大的任务。当需要用户操作或确认才能继续处理时，暂停功能是非常有用的。

### 调整速度 {#adjusting-speed}

可以使用 `setSpeed()` 方法调整 `Spinner` 的转速（单位为毫秒）。较低的值会让 `Spinner` 转动得更快，而较高的值则会减慢其转速。

```java
spinner.setSpeed(500); // 转动更快
```

:::info 默认速度
默认情况下，`Spinner` 完成一次完整旋转需要 1000 毫秒。
:::

### 暂停和恢复 {#pausing-and-resuming}

当程序暂时停止或等待用户输入时，暂停 `Spinner` 是非常有用的。它让用户知道程序处于待命状态，而不是正在积极运行，这在多步骤过程中增强了清晰度。

要暂停和恢复 Spinner，请使用 `setPaused()` 方法。当您需要临时停止旋转动画时，这特别有用。

```java
spinner.setPaused(true);  // 暂停 spinner
spinner.setPaused(false); // 恢复 spinner
```

下面的示例展示了如何设置速度以及如何暂停/恢复 `Spinner`：

<ComponentDemo
path='/webforj/spinnerspeeddemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerSpeedDemoView.java']}
height='150px'
/>

## 旋转方向 {#spin-direction}

可以控制 `Spinner` 的旋转方向为 **顺时针** 或 **逆时针**。您可以使用 `setClockwise()` 方法指定该行为。

```java
spinner.setClockwise(false);  // 逆时针旋转
spinner.setClockwise(true);   // 顺时针旋转
```

这一选项在视觉上指示了特殊状态，或作为独特的设计选择。改变旋转方向可以帮助区分不同类型的过程，例如进度与逆转，或在特定上下文中提供明显的视觉提示。

<ComponentDemo
path='/webforj/spinnerdirectiondemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerDirectionDemoView.java']}
height='150px'
/>

## 样式 {#styling}

### 主题 {#themes}

`Spinner` 组件配备了几种内置主题，允许您快速应用样式，而无需自定义 CSS。这些主题改变了 spinner 的视觉外观，使其适用于不同的用例和上下文。使用这些预定义主题可以确保整个应用程序的样式一致性。

尽管 spinners 可用于多种情况，以下是不同主题的一些示例用例：

- **主要**：适合强调加载状态，这在用户流中是关键部分，例如在提交表单或处理重要操作时。

- **成功**：用于表示成功的后台处理，例如当用户提交表单而应用程序正在执行过程的最后一步时。

- **危险**：在风险或高风险操作中使用，例如删除重要数据或进行不可逆更改，并且需要紧急性或谨慎性的视觉指示。

- **警告**：用于指示小心或不那么紧急的过程，例如当用户等待数据验证时，但不需要立即采取行动。

- **灰色**：适合轻微的后台处理，例如低优先级或被动加载任务，例如在获取对用户体验没有直接影响的补充数据时。

- **信息**：适用于加载场景，其中您正在向用户提供附加信息或说明，例如在消息旁显示 spinner，从而解释正在进行的过程。

您可以通过程序化方式将这些主题应用于 spinner，提供与操作的上下文和重要性相匹配的视觉提示。

您可以使用 `setTheme()` 方法指定该行为。

<ComponentDemo
path='/webforj/spinnerthemedemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerThemeDemoView.java']}
height='100px'
/>

### 扩展 {#expanses}

您可以调整 spinner 的大小，称为 **expanse**，以适应您需要的视觉空间。spinner 支持多个尺寸，包括 `Expanse.SMALL`、`Expanse.MEDIUM` 和 `Expanse.LARGE`。

<ComponentDemo
path='/webforj/spinnerexpansedemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerExpanseDemoView.java']}
height='100px'
/>

<TableBuilder name="Spinner" />
