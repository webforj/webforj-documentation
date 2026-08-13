---
title: ProgressBar
sidebar_position: 90
description: >-
  Visualize task completion with the ProgressBar component, supporting
  determinate and indeterminate modes, orientation, and themes.
_i18n_hash: 47c51276d2b1da6c6bef337f76403515
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-progressbar" />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/progressbar/ProgressBar" top='true'/>

`ProgressBar` 组件直观地表示操作的完成状态。随着工作进展，矩形逐渐填充以反映当前百分比。该条形图还可以显示其值的文本表示，并支持已知或未知持续时间的确定和不确定状态任务。

<!-- INTRO_END -->

## 用法 {#usages}

`ProgressBar` 组件便于可视化任务的完成状态。它支持：

- 可配置的最小值和最大值。
- 不确定模式，适用于没有确定结束的持续任务。
- 文本可见性、动画和条纹设计的选项，以获得更好的视觉反馈。

以下示例展示了一个带有开始、暂停和重置控件的条纹动画进度条：

<ComponentDemo
path='/webforj/progressbarbasic'
files={['src/main/java/com/webforj/samples/views/progressbar/ProgressBarBasicView.java']}
height='150px'
/>

## 设置值 {#setting-values}

ProgressBar 组件允许设置和获取其当前值、最小值和最大值。

```java showLineNumbers
ProgressBar bar = new ProgressBar();
bar.setMin(0);
bar.setMax(100);
bar.setValue(50);
```

## 方向 {#orientation}

`ProgressBar` 可以水平或垂直定向。

<ComponentDemo
path='/webforj/progressbarorientation'
files={['src/main/java/com/webforj/samples/views/progressbar/ProgressBarOrientationView.java']}
height='175px'
/>

## 不确定状态 {#indeterminate-state}

`ProgressBar` 支持任务未知完成时间的不确定状态。

<ComponentDemo
path='/webforj/progressbardeterminate'
files={['src/main/java/com/webforj/samples/views/progressbar/ProgressBarDeterminateView.java']}
height='25px'
/>

## 文本和文本可见性 {#text-and-text-visibility}

默认情况下，当创建时，进度条以 `XX%` 格式显示完成百分比。使用 `setText()` 方法，可以使用占位符 `{{x}}` 获取当前值作为百分比。此外，还可以使用占位符 `{{value}}` 获取原始当前值。

```java
ProgressBar bar = new ProgressBar(15, "下载中: {{x}}%");
```

## 样式 {#styling}

### 主题 {#themes}

`ProgressBar` 组件内置了 <JavadocLink type="foundation" location="com/webforj/component/Theme"> 主题 </JavadocLink>，可快速样式而无需使用 CSS。这些主题是可应用于按钮的预定义样式，用于改变其外观和视觉呈现。它们提供了一种快速一致的方式来定制应用中 ProgressBars 的外观。

<ComponentDemo
path='/webforj/progressbarthemes'
files={['src/main/java/com/webforj/samples/views/progressbar/ProgressBarThemesView.java']}
height='320px'
/>

<TableBuilder name="ProgressBar" />

## 最佳实践 {#best-practices}

- **使用适当的最小值和最大值**：设置最小值和最大值以准确反映任务范围。
- **定期更新进度**：持续更新进度值以向用户提供实时反馈。
- **利用不确定状态处理未知持续时间**：对持续时间不可预测的任务使用不确定状态，以指示正在进行的进度。
- **显示文本以获取更好的用户反馈**：在进度条上显示文本，以提供有关任务进展的额外上下文。
