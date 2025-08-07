---
title: ProgressBar
sidebar_position: 90
_i18n_hash: 3c76010e8bda96d8694bffa5a260b851
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-progressbar" />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/progressbar/ProgressBar" top='true'/>

ProgressBar 是一个可视化显示某项任务进度的组件。随着任务逐渐完成，进度条会显示任务的完成百分比。这个百分比以一个矩形的形式直观呈现，开始时是空的，随着任务的推进而逐渐填充。此外，进度条还可以显示这个百分比的文本表示。

<ComponentDemo 
path='/webforj/progressbarbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/progressbar/ProgressBarBasicView.java'
height='150px'
/>

## Usages {#usages}

`ProgressBar` 组件对于可视化任务的完成状态非常有用。它支持：

- 可配置的最小值和最大值。
- 用于没有明确结束的进行中的任务的不确定模式。
- 可选的文本可见性、动画和条纹设计，以提供更好的视觉反馈。

## Setting values {#setting-values}

ProgressBar 组件允许设置和获取其当前值、最小值和最大值限制。

```java showLineNumbers
ProgressBar bar = new ProgressBar();
bar.setMin(0);
bar.setMax(100);
bar.setValue(50);
```

## Orientation {#orientation}

`ProgressBar` 可以水平或垂直排列。

<ComponentDemo 
path='/webforj/progressbarorientation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/progressbar/ProgressBarOrientationView.java'
height='175px'
/>

## Indeterminate state {#indeterminate-state}

`ProgressBar` 支持对于未知完成时间的任务的不确定状态。

<ComponentDemo 
path='/webforj/progressbardeterminate?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/progressbar/ProgressBarDeterminateView.java'
height='25px'
/>

## Text and text visibility {#text-and-text-visibility}

默认情况下，当创建时，进度条以 `XX%` 格式显示完成百分比。使用 `setText()` 方法，你可以使用占位符 `{{x}}` 获取当前值的百分比。此外，你可以使用占位符 `{{value}}` 获取当前原始值。

```java
ProgressBar bar = new ProgressBar(15, "Downloading: {{x}}%");
```

## Styling {#styling}

### Themes {#themes}

`ProgressBar` 组件内置了 <JavadocLink type="foundation" location="com/webforj/component/Theme"> 主题 </JavadocLink> 以便快速进行无CSS样式的美化。这些主题是预定义的样式，可应用于按钮以更改其外观和视觉展示。
它们提供了一种快速和一致的方式来个性化整个应用中进度条的外观。

<ComponentDemo 
path='/webforj/progressbarthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/progressbar/ProgressBarThemesView.java'
height='320px'
/>

<TableBuilder name="ProgressBar" />

## Best practices {#best-practices}

- **使用适当的最小值和最大值**：设置最小值和最大值以准确反映任务范围。
- **定期更新进度**：持续更新进度值，以向用户提供实时反馈。
- **利用不确定状态表示未知持续时间**：对持续时间不可预测的任务使用不确定状态，以指示正在进行的进度。
- **显示文本以提供更好的用户反馈**：在进度条上显示文本，以提供有关任务进度的额外上下文。
