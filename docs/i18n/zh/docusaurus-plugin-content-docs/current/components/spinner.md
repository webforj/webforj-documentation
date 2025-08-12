---
title: Spinner
sidebar_position: 110
_i18n_hash: b1137c43133bce5c5a16df51c0aa82e3
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-spinner" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="spinner" location="com/webforj/component/spinner/Spinner" top='true'/>

`Spinner` 组件提供了一个视觉指示器，指示后台正在进行处理或加载。它通常用于显示系统正在获取数据或当一个过程需要时间来完成时。旋转器提供用户反馈，表明系统正在积极工作。

## 基础 {#basics}

要创建一个 `Spinner`，您可以指定主题和大小。基本语法包括创建一个 `Spinner` 实例，并通过 `setTheme()` 和 `setExpanse()` 等方法定义其外观和行为。

<ComponentDemo 
path='/webforj/spinnerdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerDemoView.java'
cssURL='/css/spinnerstyles/spinnerdemo.css'
height = '225px'
/>

## 管理速度和暂停 {#managing-speed-and-pausing}

可以轻松设置 `Spinner` 的速度（以毫秒为单位）并暂停/恢复动画。

设置速度的用例包括区分加载过程。例如，较快的 `Spinners` 适合较小的任务，而较慢的 `Spinners` 更适合较大的任务。当用户需要在继续过程之前进行操作或确认时，暂停是非常有用的。

### 调整速度 {#adjusting-speed}

您可以通过使用 `setSpeed()` 方法调整 `Spinner` 旋转的速度（以毫秒为单位）。较低的值使 `Spinner` 旋转得更快，而较高的值会减慢其速度。

```java
spinner.setSpeed(500); // 旋转更快
```

:::info 默认速度
默认情况下，`Spinner` 将花费 1000 毫秒完成一次完整的旋转。
:::

### 暂停和恢复 {#pausing-and-resuming}

在程序暂时停止或等待用户输入时，暂停 `Spinner` 是非常有用的。它让用户知道程序处于暂停状态，而不是正在积极运行，从而在多步骤过程中增强了清晰度。

要暂停和恢复 Spinner，请使用 `setPaused()` 方法。当需要暂时停止旋转动画时，这尤其有用。

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

这个选项在视觉上指示一种特殊状态，或作为一种独特的设计选择。改变旋转方向可以帮助区分不同类型的过程，例如进度与回退，或者在特定上下文中提供独特的视觉提示。

<ComponentDemo 
path='/webforj/spinnerdirectiondemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerDirectionDemoView.java'
height = '150px'
/>

## 样式 {#styling}

### 主题 {#themes}

`Spinner` 组件带有几种内置主题，允许您快速应用样式而无需自定义 CSS。这些主题改变旋转器的视觉外观，使其适用于不同的用例和上下文。使用这些预定义的主题可以确保在整个应用程序中的样式一致性。

虽然旋转器适用于各种情况，但以下是不同主题的一些示例用例：

- **主要**：理想用于强调用户流程中关键的加载状态，例如在提交表单或处理重要操作时。
  
- **成功**：用于表示成功的后台进程，例如当用户提交表单，应用程序正在执行最终步骤时。
  
- **危险**：用于风险或高风险的操作，例如删除重要数据或进行不可逆更改，需要视觉指示紧急性或警惕性。
  
- **警告**：用于指示一种警告或不那么紧急的过程，例如当用户等待数据验证，但不需要立即行动。

- **灰色**：适用于微妙的后台进程，例如低优先级或被动加载任务，例如获取不直接影响用户体验的补充数据。
  
- **信息**：适合于提供额外信息或澄清给用户的加载场景，例如在显示说明正在进行的过程的信息时，显示旋转器。

您可以以编程方式将这些主题应用于旋转器，提供与操作的上下文和重要性相匹配的视觉提示。

您可以使用 `setTheme()` 方法指定这种行为。

<ComponentDemo 
path='/webforj/spinnerthemedemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerThemeDemoView.java'
cssURL='/css/spinnerstyles/spinnerthemedemo.css'
height = '100px'
/>

### 尺寸 {#expanses}

您可以调整旋转器的大小，称为 **大小**，以适应所需的视觉空间。旋转器支持多种尺寸，包括 `Expanse.SMALL`、`Expanse.MEDIUM` 和 `Expanse.LARGE`。

<ComponentDemo 
path= '/webforj/spinnerexpansedemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerExpanseDemoView.java'
cssURL='/css/spinnerstyles/spinnerexpansedemo.css'
height = '100px'
/>

<TableBuilder name="Spinner" />
