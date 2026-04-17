---
title: Spinner
sidebar_position: 110
_i18n_hash: bb61c6f2d3cf7073ca2d1c6fc6e1c0c4
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-spinner" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="spinner" location="com/webforj/component/spinner/Spinner" top='true'/>

`Spinner` 组件提供了一个视觉指示器，用于指示后台正在进行处理或加载。它通常用于显示系统正在获取数据或者某个过程需要时间才能完成。`Spinner` 提供了用户反馈，表明系统正在积极工作。

<!-- INTRO_END -->

## 基础 {#basics}

要创建 `Spinner`，可以指定主题和尺寸。基本语法涉及创建一个 `Spinner` 实例，并通过 `setTheme()` 和 `setExpanse()` 等方法定义其外观和行为。

<ComponentDemo 
path='/webforj/spinnerdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerDemoView.java'
height = '225px'
/>

## 管理速度和暂停 {#managing-speed-and-pausing}

可以轻松地设置 `Spinner` 的速度（以毫秒为单位）并暂停/恢复动画。

设置速度的用例包括区分加载过程。例如，较快的 `Spinners` 适合较小的任务，而较慢的 `Spinners` 更适合较大的任务。在需要用户操作或确认才能继续的情况下，暂停是非常有用的。

### 调整速度 {#adjusting-speed}

您可以使用 `setSpeed()` 方法控制 `Spinner` 的旋转速度，以毫秒为单位进行调整。较低的值使 `Spinner` 旋转得更快，而较高的值则会减慢速度。

```java
spinner.setSpeed(500); // 旋转得更快
```

:::info 默认速度
默认情况下，`Spinner` 将需要 1000 毫秒完成一次完整旋转。
:::

### 暂停和恢复 {#pausing-and-resuming}

在程序暂时停止或等待用户输入时，暂停 `Spinner` 是很有用的。它让用户知道程序正在暂停，而不是在积极运行，这在多步骤过程中增强了清晰度。

要暂停和恢复 `Spinner`，请使用 `setPaused()` 方法。当需要暂时停止旋转动画时，这尤其有帮助。      

```java
spinner.setPaused(true);  // 暂停旋转器
spinner.setPaused(false); // 恢复旋转器
```

以下示例展示了如何设置速度以及如何暂停/恢复 `Spinner`：

<ComponentDemo 
path='/webforj/spinnerspeeddemo?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerSpeedDemoView.java'
height = '150px'
/>

## 旋转方向 {#spin-direction}

可以控制 `Spinner` 的旋转方向，旋转方式为 **顺时针** 或 **逆时针**。可以使用 `setClockwise()` 方法指定此行为。

```java
spinner.setClockwise(false);  // 逆时针旋转
spinner.setClockwise(true);   // 顺时针旋转
```

这个选项可视性地指示特殊状态，或作为一种独特的设计选择。改变旋转方向可以帮助区分流程类型，例如进度与倒退，或者在特定上下文中提供独特的视觉提示。

<ComponentDemo 
path='/webforj/spinnerdirectiondemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerDirectionDemoView.java'
height = '150px'
/>

## 样式 {#styling}

### 主题 {#themes}

`Spinner` 组件包含几种内置主题，允许您快速应用样式而无需自定义 CSS。这些主题改变了旋转器的视觉外观，使其适合不同的使用场景和上下文。使用这些预定义主题可以确保您应用中的样式一致性。

尽管旋转器服务于各种情况，以下是不同主题的一些示例用例：

- **主要**: 适合强调关键用户流中加载状态，比如提交表单或处理重要操作时。
  
- **成功**: 能够表示成功的后台处理，例如用户提交表单后，应用程序正在执行处理的最后步骤。
  
- **危险**: 用于风险或高风险操作，例如删除重要数据或进行不可逆的更改，在这些情况下需要一个紧急或警告的视觉指示。

- **警告**: 用于表示谨慎或不那么紧急的过程，例如用户等待数据验证，但不需要立即采取行动。

- **灰色**: 非常适合于细微的背景进程，例如低优先级或被动加载任务，比如获取补充数据，而这不会直接影响用户体验。
  
- **信息**: 适用于加载场景，其中您为用户提供额外的信息或说明，例如在展示加载器的同时显示一条说明正在进行的过程的消息。

您可以通过编程方式将这些主题应用于旋转器，提供与操作的上下文和重要性相符的视觉提示。

您可以使用 `setTheme()` 方法指定此行为。

<ComponentDemo 
path='/webforj/spinnerthemedemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerThemeDemoView.java'
height = '100px'
/>

### 尺寸 {#expanses}

您可以调整旋转器的大小，称为 **尺寸**，以适应所需的视觉空间。旋转器支持多种大小，包括 `Expanse.SMALL`、`Expanse.MEDIUM` 和 `Expanse.LARGE`。

<ComponentDemo 
path= '/webforj/spinnerexpansedemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerExpanseDemoView.java'
height = '100px'
/>

<TableBuilder name="Spinner" />
