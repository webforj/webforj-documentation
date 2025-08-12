---
title: ProgressBar
sidebar_position: 90
_i18n_hash: 9b2f9ec23124d60ab5f8fca18e561acb
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-progressbar" />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/progressbar/ProgressBar" top='true'/>

ProgressBar 是一个可视化显示某个任务进度的组件。当任务逐步走向完成时，进度条显示任务的完成百分比。这个百分比通过一个矩形来表示，开始时是空的，随着任务进展逐渐填充。此外，进度条可以显示这个百分比的文本表示。

<ComponentDemo 
path='/webforj/progressbarbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/progressbar/ProgressBarBasicView.java'
height='150px'
/>

## Usages {#usages}

`ProgressBar` 组件对于可视化任务的完成状态非常有用。它支持：

- 可配置的最小和最大值。
- 用于没有明确结束时间的进行中任务的无确定模式。
- 可见性、动画和条纹设计的文本选项，以提供更好的视觉反馈。

## Setting values {#setting-values}

ProgressBar 组件允许设置和获取其当前值、最小值和最大值。

```java showLineNumbers
ProgressBar bar = new ProgressBar();
bar.setMin(0);
bar.setMax(100);
bar.setValue(50);
```

## Orientation {#orientation}

`ProgressBar` 可以水平或垂直放置。

<ComponentDemo 
path='/webforj/progressbarorientation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/progressbar/ProgressBarOrientationView.java'
height='175px'
/>

## Indeterminate state {#indeterminate-state}

`ProgressBar` 支持不确定状态，适用于完成时间未知的任务。

<ComponentDemo 
path='/webforj/progressbardeterminate?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/progressbar/ProgressBarDeterminateView.java'
height='25px'
/>

## Text and text visibility {#text-and-text-visibility}

默认情况下，创建时进度条以 `XX%` 的格式显示完成百分比。使用 `setText()` 方法，您可以使用占位符 `{{x}}` 来获取当前值的百分比。此外，您可以使用占位符 `{{value}}` 来获取原始当前值。

```java
ProgressBar bar = new ProgressBar(15, "Downloading: {{x}}%");
```

## Styling {#styling}

### Themes {#themes}

`ProgressBar` 组件内置了 <JavadocLink type="foundation" location="com/webforj/component/Theme">主题</JavadocLink>，可快速进行样式设置而无需使用 CSS。这些主题是可应用于按钮的预定义样式，以改变它们的外观和视觉呈现。它们为自定义应用程序中 ProgressBars 的外观提供了一种快速和一致的方法。

<ComponentDemo 
path='/webforj/progressbarthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/progressbar/ProgressBarThemesView.java'
height='320px'
/>

<TableBuilder name="ProgressBar" />

## Best practices {#best-practices}

- **使用合适的最小和最大值**：设置最小和最大值，以准确反映任务的范围。
- **定期更新进度**：持续更新进度值，以向用户提供实时反馈。
- **利用不确定状态处理未知持续时间**：对于持续时间不可预测的任务，使用不确定状态来指示正在进行的进度。
- **显示文本以便于用户反馈**：在进度条上显示文本，以提供有关任务进度的额外上下文。
