---
title: Spinner
sidebar_position: 110
_i18n_hash: 8ab95efdcfcc1e42df56c372da27cc81
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-spinner" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="spinner" location="com/webforj/component/spinner/Spinner" top='true'/>

`Spinner` 组件提供了一种视觉指示，表示正在进行的处理或后台加载。它通常用于显示系统正在获取数据或某个过程需要时间才能完成。旋转器提供用户反馈，表明系统正在积极工作。

## 基础 {#basics}

要创建一个 `Spinner`，您可以指定主题和范围。基本语法涉及创建一个 `Spinner` 实例，并通过 `setTheme()` 和 `setExpanse()` 等方法定义其外观和行为。

<ComponentDemo 
path='/webforj/spinnerdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerDemoView.java'
cssURL='/css/spinnerstyles/spinnerdemo.css'
height = '225px'
/>

## 管理速度和暂停 {#managing-speed-and-pausing}

可以轻松设置 `Spinner` 的速度（以毫秒为单位）并暂停/恢复动画。

设置速度的使用案例包括区分加载过程。例如，更快的 `Spinners` 适合较小的任务，而较慢的 `Spinners` 更适合较大的任务。暂停在需要用户操作或确认后继续处理时非常有用。

### 调整速度 {#adjusting-speed}

您可以通过使用 `setSpeed()` 方法调整 `Spinner` 的旋转速度，以毫秒为单位控制旋转速度。较低的值使 `Spinner` 旋转得更快，而较高的值会使其变慢。

```java
spinner.setSpeed(500); // 旋转得更快
```

:::info 默认速度
默认情况下，`Spinner` 将花费 1000 毫秒完成一整圈的旋转。
:::

### 暂停和恢复 {#pausing-and-resuming}

在程序暂时停止或等待用户输入时，暂停 `Spinner` 是有益的。它让用户知道程序处于暂停状态，而不是正在主动运行，这提高了多步骤过程中的清晰度。

要暂停和恢复 Spinner，请使用 `setPaused()` 方法。这在您需要暂时停止旋转动画时特别有用。

```java
spinner.setPaused(true);  // 暂停旋转器
spinner.setPaused(false); // 恢复旋转器
```

这个例子展示了如何设置速度以及如何暂停/恢复 `Spinner`：

<ComponentDemo 
path='/webforj/spinnerspeeddemo?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerSpeedDemoView.java'
cssURL='/css/spinnerstyles/spinnerspeeddemo.css'
height = '150px'
/>

## 旋转方向 {#spin-direction}

`Spinner` 的方向可以控制旋转 **顺时针** 或 **逆时针**。您可以使用 `setClockwise()` 方法指定此行为。

```java
spinner.setClockwise(false);  // 逆时针旋转
spinner.setClockwise(true);   // 顺时针旋转
```

此选项在视觉上指示特殊状态或作为独特的设计选择。改变旋转方向可以帮助区分处理类型，例如进展与逆转，或在特定上下文中提供明显的视觉提示。

<ComponentDemo 
path='/webforj/spinnerdirectiondemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerDirectionDemoView.java'
height = '150px'
/>

## 样式 {#styling}

### 主题 {#themes}

`Spinner` 组件提供了多种内置主题，使您能够快速应用样式，而无需自定义 CSS。这些主题改变旋转器的视觉外观，使其适合不同的用例和上下文。使用这些预定义主题确保了整个应用程序样式的一致性。

虽然旋转器适用于多种情况，但以下是不同主题的一些示例用例：

- **主要**：理想用于强调加载状态，这对用户流程至关重要，例如在提交表单或处理重要操作时。
  
- **成功**：用于表示成功的后台处理，例如当用户提交表单并且应用正在执行过程的最后步骤时。
  
- **危险**：对于风险或高风险操作（如删除重要数据或进行不可逆更改），需要视觉紧迫性或谨慎提示时使用。
  
- **警告**：用于指示注意或不那么紧急的过程，例如当用户等待数据验证时，但不需要立即采取行动。

- **灰色**：适用于微妙的背景过程，例如低优先级或被动加载任务，例如获取补充数据，而不会直接影响用户体验。
  
- **信息**：适用于加载场景，您在此期间向用户提供额外的信息或澄清，例如在解释正在进行的过程时显示旋转器旁边的信息。

您可以以编程方式将这些主题应用于旋转器，为与操作的上下文和重要性相一致的视觉提示提供支持。

您可以使用 `setTheme()` 方法指定此行为。

<ComponentDemo 
path='/webforj/spinnerthemedemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerThemeDemoView.java'
cssURL='/css/spinnerstyles/spinnerthemedemo.css'
height = '100px'
/>

### 范围 {#expanses}

您可以调整旋转器的大小，称为 **范围**，以适应所需的视觉空间。旋转器支持多种大小，包括 `Expanse.SMALL`、`Expanse.MEDIUM` 和 `Expanse.LARGE`。

<ComponentDemo 
path= '/webforj/spinnerexpansedemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerExpanseDemoView.java'
cssURL='/css/spinnerstyles/spinnerexpansedemo.css'
height = '100px'
/>

<TableBuilder name="Spinner" />
