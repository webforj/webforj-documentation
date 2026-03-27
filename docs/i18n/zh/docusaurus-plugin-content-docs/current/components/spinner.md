---
title: Spinner
sidebar_position: 110
_i18n_hash: c60e7d3c3604a39de7f659f169d973a6
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-spinner" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="spinner" location="com/webforj/component/spinner/Spinner" top='true'/>

`Spinner` 组件提供了一个视觉指示器，表示后台正在进行处理或加载。它通常用于显示系统正在提取数据或当一个过程需要时间才能完成时。`Spinner` 提供了用户反馈，表明系统正在积极工作。

<!-- INTRO_END -->

## 基础 {#basics}

要创建一个 `Spinner`，您可以指定主题和大小。基本语法涉及创建一个 `Spinner` 实例，并通过方法如 `setTheme()` 和 `setExpanse()` 定义其外观和行为。

<ComponentDemo 
path='/webforj/spinnerdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerDemoView.java'
cssURL='/css/spinnerstyles/spinnerdemo.css'
height = '225px'
/>

## 管理速度和暂停 {#managing-speed-and-pausing}

可以以毫秒为单位设置 `Spinner` 的速度，并轻松暂停/恢复动画。

设置速度的用例包括区分加载过程。例如，更快的 `Spinners` 适合较小的任务，而较慢的 `Spinners` 更适合较大的任务。暂停在需要用户操作或确认时是有用的，以便在继续过程之前进行确认。

### 调整速度 {#adjusting-speed}

您可以使用 `setSpeed()` 方法控制 `Spinner` 旋转的快慢。较低的值使 `Spinner` 旋转更快，而较高的值会减慢它的速度。

```java
spinner.setSpeed(500); // 旋转得更快
```

:::info 默认速度
默认情况下，`Spinner` 完成一次完整旋转需要 1000 毫秒。
:::

### 暂停和恢复 {#pausing-and-resuming}

当程序暂时停止或等待用户输入时，暂停 `Spinner` 是有用的。它让用户知道程序处于暂停状态，而不是处于主动运行状态，这在多步骤过程中增强了清晰度。

要暂停和恢复 `Spinner`，请使用 `setPaused()` 方法。这在您需要临时停止旋转动画时特别有用。

```java
spinner.setPaused(true);  // 暂停旋转器
spinner.setPaused(false); // 恢复旋转器
```

这个示例展示了如何设置速度以及如何暂停/恢复 `Spinner`：

<ComponentDemo 
path='/webforj/spinnerspeeddemo?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerSpeedDemoView.java'
cssURL='/css/spinnerstyles/spinnerspeeddemo.css'
height = '150px'
/>

## 旋转方向 {#spin-direction}

可以控制 `Spinner` 的旋转方向为 **顺时针** 或 **逆时针**。您可以使用 `setClockwise()` 方法指定这种行为。

```java
spinner.setClockwise(false);  // 逆时针旋转
spinner.setClockwise(true);   // 顺时针旋转
```

这个选项可视表示特殊状态或作为独特的设计选择。改变旋转方向可以帮助区分过程类型，例如进度与反向，或在特定上下文中提供独特的视觉提示。

<ComponentDemo 
path='/webforj/spinnerdirectiondemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerDirectionDemoView.java'
height = '150px'
/>

## 样式 {#styling}

### 主题 {#themes}

`Spinner` 组件带有多个内置主题，可以快速应用样式，而无需自定义 CSS。这些主题改变旋转器的视觉外观，使其适合不同的使用场景和上下文。使用这些预定义的主题确保您应用中的样式一致性。

虽然 `Spinner` 用于各种情况，但以下是不同主题的一些示例用例：

- **主要**: 适合强调加载状态，这对用户流程至关重要，例如在提交表单或处理重要操作时。
  
- **成功**: 有助于表示成功的后台过程，例如用户提交表单并且应用程序正在执行处理的最后步骤时。
  
- **危险**: 用于风险或高风险操作，例如删除重要数据或进行不可逆的更改，当需要视觉提示紧急性或谨慎时。
  
- **警告**: 用于指示警示或不那么紧急的过程，例如当用户等待数据验证，但不需要立即采取措施时。

- **灰色**: 非常适合用于微妙的后台过程，例如低优先级或被动加载任务，例如提取补充数据，这不会直接影响用户体验。
  
- **信息**: 适合于提供额外信息或阐明的加载场景，例如与解释正在进行的过程的消息一起显示旋转器。

您可以以编程方式将这些主题应用于旋转器，提供符合操作上下文和重要性的视觉提示。

您可以使用 `setTheme()` 方法指定这种行为。

<ComponentDemo 
path='/webforj/spinnerthemedemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerThemeDemoView.java'
cssURL='/css/spinnerstyles/spinnerthemedemo.css'
height = '100px'
/>

### 大小 {#expanses}

您可以调整旋转器的大小，称为 **大小** ，以适应您需要的视觉空间。旋转器支持不同的大小，包括 `Expanse.SMALL`，`Expanse.MEDIUM` 和 `Expanse.LARGE`。

<ComponentDemo 
path= '/webforj/spinnerexpansedemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerExpanseDemoView.java'
cssURL='/css/spinnerstyles/spinnerexpansedemo.css'
height = '100px'
/>

<TableBuilder name="Spinner" />
