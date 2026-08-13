---
title: Spinner
sidebar_position: 110
description: >-
  Indicate background activity with the Spinner component, configuring theme,
  expanse, rotation speed, and pause or resume.
_i18n_hash: bd35c3da6c5fc265d0bb249bbde86215
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-spinner" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="spinner" location="com/webforj/component/spinner/Spinner" top='true'/>

`Spinner` 组件提供了一个视觉指示器，用于指示正在进行的处理或后台加载。它通常用于显示系统正在获取数据或处理花费时间的操作。`Spinner` 提供用户反馈，表明系统正在积极工作。

<!-- INTRO_END -->

## 基础 {#basics}

要创建一个 `Spinner`，您可以指定主题和扩展。基本语法涉及创建一个 `Spinner` 实例，并通过方法如 `setTheme()` 和 `setExpanse()` 定义其外观和行为。

<ComponentDemo
path='/webforj/spinnerdemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerDemoView.java']}
height='225px'
/>

## 管理速度和暂停 {#managing-speed-and-pausing}

可以毫不费力地设置 `Spinner` 的速度（以毫秒为单位）并暂停/恢复动画。

设置速度的用例包括区分加载过程。例如，更快的 `Spinners` 适合较小的任务，而较慢的 `Spinners` 更适合较大的任务。当需要用户操作或确认后再继续处理时，暂停功能尤为重要。

### 调整速度 {#adjusting-speed}

您可以通过使用 `setSpeed()` 方法来控制 `Spinner` 旋转的速度（以毫秒为单位）。较低的值使 `Spinner` 旋转得更快，而较高的值则会减慢其速度。

```java
spinner.setSpeed(500); // 旋转更快
```

:::info 默认速度
默认情况下，`Spinner` 完成一整圈的时间为 1000 毫秒。
:::

### 暂停和恢复 {#pausing-and-resuming}

暂停 `Spinner` 在程序暂时停止或等待用户输入时非常有用。它让用户知道程序处于暂停状态，而不是在积极运行，这在多步骤过程中提高了清晰度。

要暂停和恢复 `Spinner`，请使用 `setPaused()` 方法。当您需要临时停止旋转动画时，这特别有用。

```java
spinner.setPaused(true);  // 暂停旋转
spinner.setPaused(false); // 恢复旋转
```

此示例展示了如何设置速度以及如何暂停/恢复 `Spinner`：

<ComponentDemo
path='/webforj/spinnerspeeddemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerSpeedDemoView.java']}
height='150px'
/>

## 旋转方向 {#spin-direction}

`Spinner` 的旋转方向可以控制为 **顺时针** 或 **逆时针**。您可以使用 `setClockwise()` 方法指定此行为。

```java
spinner.setClockwise(false);  // 逆时针旋转
spinner.setClockwise(true);   // 顺时针旋转
```

这个选项视觉上指示了一种特殊状态，或作为独特的设计选择。改变旋转方向可以帮助区分不同类型的过程，例如进展与逆转，或在特定上下文中提供独特的视觉提示。

<ComponentDemo
path='/webforj/spinnerdirectiondemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerDirectionDemoView.java']}
height='150px'
/>

## 样式 {#styling}

### 主题 {#themes}

`Spinner` 组件提供了几种内置主题，使您能够快速应用样式，而无需自定义 CSS。这些主题改变了 spinner 的视觉外观，使其适用于不同的用例和上下文。使用这些预定义主题可确保您应用的样式一致。

虽然 spinner 有各种情况，但以下是不同主题的一些示例用例：

- **主主题**：非常适合强调用户流程中关键的加载状态，例如在提交表单或处理重要操作时。

- **成功主题**：用于表示成功的后台过程，例如用户提交表单时，应用程序正在执行流程的最后步骤。

- **危险主题**：用于高风险或高风险操作，如删除重要数据或进行不可逆的更改，在这种情况下，需要视觉指示来反映紧急或谨慎。

- **警告主题**：用于指示谨慎或不那么紧迫的过程，例如用户等待数据验证，但不需要立即采取行动。

- **灰色主题**：适合用于低优先级或被动加载任务的微妙后台过程，如获取补充数据，这些数据不会直接影响用户体验。

- **信息主题**：适合加载场景，其中您提供额外信息或澄清给用户，例如在显示 spinner 的同时附带解释正在进行的过程的信息。

您可以以编程方式将这些主题应用于 spinner，提供与操作的上下文和重要性相一致的视觉提示。

您可以使用 `setTheme()` 方法指定此行为。

<ComponentDemo
path='/webforj/spinnerthemedemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerThemeDemoView.java']}
height='100px'
/>

### 扩展 {#expanses}

您可以调整 spinner 的大小，称为 **扩展**，以适应所需的视觉空间。spinner 支持多种大小，包括 `Expanse.SMALL`、`Expanse.MEDIUM` 和 `Expanse.LARGE`。

<ComponentDemo
path='/webforj/spinnerexpansedemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerExpanseDemoView.java']}
height='100px'
/>

<TableBuilder name="Spinner" />
