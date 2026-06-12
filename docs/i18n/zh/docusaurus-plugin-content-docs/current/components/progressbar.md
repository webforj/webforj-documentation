---
title: ProgressBar
sidebar_position: 90
_i18n_hash: 6acac582ce905eb255ee09e499fd561f
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-progressbar" />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/progressbar/ProgressBar" top='true'/>

`ProgressBar` 组件可视化地表示操作的完成状态。在工作进展时，一个矩形逐渐填充以反映当前的百分比。该条形图还可以显示其值的文本表示，并支持已知或未知持续时间任务的确定和不确定状态。

<!-- INTRO_END -->

## 用法 {#usages}

`ProgressBar` 组件对于可视化任务的完成状态非常有用。它支持：

- 可配置的最小值和最大值。
- 对于没有确定结束的进行中任务的非确定模式。
- 用于更好视觉反馈的文本可见性、动画和条纹设计选项。

以下示例展示了带有开始、暂停和重置控件的条纹动画进度条：

<ComponentDemo
path='/webforj/progressbarbasic'
files={['src/main/java/com/webforj/samples/views/progressbar/ProgressBarBasicView.java']}
height='150px'
/>

## 设置值 {#setting-values}

ProgressBar 组件允许设置和获取其当前值、最小值和最大值限制。

```java showLineNumbers
ProgressBar bar = new ProgressBar();
bar.setMin(0);
bar.setMax(100);
bar.setValue(50);
```

## 定向 {#orientation}

`ProgressBar` 可以水平或垂直定向。

<ComponentDemo
path='/webforj/progressbarorientation'
files={['src/main/java/com/webforj/samples/views/progressbar/ProgressBarOrientationView.java']}
height='175px'
/>

## 不确定状态 {#indeterminate-state}

`ProgressBar` 支持对于未知完成时间的任务的不确定状态。

<ComponentDemo
path='/webforj/progressbardeterminate'
files={['src/main/java/com/webforj/samples/views/progressbar/ProgressBarDeterminateView.java']}
height='25px'
/>

## 文本和文本可见性 {#text-and-text-visibility}

默认情况下，创建时，进度条以 `XX%` 格式显示完成百分比。使用 `setText()` 方法，您可以使用占位符 `{{x}}` 来获取当前值作为百分比。此外，您可以使用占位符 `{{value}}` 来获取原始当前值。

```java
ProgressBar bar = new ProgressBar(15, "正在下载: {{x}}%");
```

## 样式 {#styling}

### 主题 {#themes}

`ProgressBar` 组件内置了 <JavadocLink type="foundation" location="com/webforj/component/Theme"> 主题 </JavadocLink>，以便快速样式化，而无需使用 CSS。这些主题是预定义的样式，可应用于按钮以改变其外观和视觉效果。它们提供了一种快速且一致的方式来定制应用程序中 ProgressBars 的外观。

<ComponentDemo
path='/webforj/progressbarthemes'
files={['src/main/java/com/webforj/samples/views/progressbar/ProgressBarThemesView.java']}
height='320px'
/>

<TableBuilder name="ProgressBar" />

## 最佳实践 {#best-practices}

- **使用适当的最小值和最大值**：设置最小值和最大值以准确反映任务范围。
- **定期更新进度**：持续更新进度值，以向用户提供实时反馈。
- **对未知时长任务使用不确定状态**：对于持续时间不可预测的任务，使用不确定状态以指示正在进行的进度。
- **显示文本以提供更好的用户反馈**：在进度条上显示文本，以提供关于任务进度的额外上下文。
