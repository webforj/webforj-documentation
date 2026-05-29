---
title: Spinner
sidebar_position: 110
_i18n_hash: d93d5704fff2acc975910f1a10e34d0b
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-spinner" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="spinner" location="com/webforj/component/spinner/Spinner" top='true'/>

`Spinner` 组件提供了一个视觉指示器，表示后台正在进行处理或加载。它通常用于显示系统正在获取数据或当一个过程需要时间来完成时。`Spinner` 提供了用户反馈，表明系统正在积极工作。

<!-- INTRO_END -->

## 基础 {#basics}

要创建一个 `Spinner`，您可以指定主题和扩展。基本语法涉及创建一个 `Spinner` 实例，并通过诸如 `setTheme()` 和 `setExpanse()` 的方法定义其外观和行为。

<ComponentDemo
path='/webforj/spinnerdemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerDemoView.java']}
height='225px'
/>

## 管理速度和暂停 {#managing-speed-and-pausing}

可以轻松为 `Spinner` 设置速度（以毫秒为单位）并暂停/恢复动画。

设置速度的用例包括区分加载过程。例如，更快的 `Spinners` 适合较小的任务，而较慢的 `Spinners` 更适合较大的任务。在需要用户操作或确认以继续处理时，暂停功能非常有用。

### 调整速度 {#adjusting-speed}

您可以通过调整其速度（以毫秒为单位）来控制 `Spinner` 的旋转速度，使用 `setSpeed()` 方法。较低的值使 `Spinner` 旋转得更快，而较高的值则会减慢其转动速度。

```java
spinner.setSpeed(500); // 旋转得更快
```

:::info 默认速度
默认情况下，`Spinner` 将花费 1000 毫秒完成一次完整的旋转。
:::

### 暂停和恢复 {#pausing-and-resuming}

在程序暂时被暂停或等待用户输入时，暂停 `Spinner` 是非常有用的。它让用户知道程序处于暂停状态，而不是积极运行，这在多步骤过程中增强了清晰度。

要暂停和恢复 `Spinner`，请使用 `setPaused()` 方法。这在您需要临时停止旋转动画时特别有帮助。

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

可以控制 `Spinner` 旋转的方向为 **顺时针** 或 **逆时针**。您可以使用 `setClockwise()` 方法指定此行为。

```java
spinner.setClockwise(false);  // 逆时针旋转
spinner.setClockwise(true);   // 顺时针旋转
```

这个选项在视觉上指示特殊状态或作为独特的设计选择。改变旋转方向可以帮助区分不同类型的过程，如进度与逆转，或者在特定上下文中提供独特的视觉提示。

<ComponentDemo
path='/webforj/spinnerdirectiondemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerDirectionDemoView.java']}
height='150px'
/>

## 样式 {#styling}

### 主题 {#themes}

`Spinner` 组件具有多个内置主题，可以让您快速应用样式，而无需自定义 CSS。这些主题改变了 Spinner 的视觉外观，使其适合不同的使用场景和上下文。使用这些预定义的主题，可以确保您的应用程序的样式一致性。

虽然 Spinner 适用于各种情况，但以下是不同主题的一些示例用例：

- **主要**：理想用于强调加载状态，这是用户流程的关键部分，例如在提交表单或处理重要操作时。
  
- **成功**：用于表示成功的后台过程，例如用户提交表单时，应用程序正在执行过程的最后步骤。
  
- **危险**：在风险或高风险操作中使用，例如删除重要数据或进行不可逆的更改，需要视觉指示紧急或注意。
  
- **警告**：用于表示一个警示或不太紧急的过程，例如用户在等待数据验证，但不需要立即行动。

- **灰色**：适合于微妙的背景过程，例如低优先级或被动加载任务，比如获取辅助数据，这不会直接影响用户体验。
  
- **信息**：适合于加载场景，其中您正在为用户提供额外的信息或澄清，例如在显示同时解释当前过程的消息的 Spinner。

您可以按程序方式将这些主题应用于 Spinner，提供与操作的上下文和重要性相符的视觉提示。

您可以使用 `setTheme()` 方法指定此行为。

<ComponentDemo
path='/webforj/spinnerthemedemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerThemeDemoView.java']}
height='100px'
/>

### 扩展 {#expanses}

您可以调整 Spinner 的大小，称为 **扩展**，以适应所需的视觉空间。Spinner 支持多种大小，包括 `Expanse.SMALL`、`Expanse.MEDIUM` 和 `Expanse.LARGE`。

<ComponentDemo
path='/webforj/spinnerexpansedemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerExpanseDemoView.java']}
height='100px'
/>

<TableBuilder name="Spinner" />
