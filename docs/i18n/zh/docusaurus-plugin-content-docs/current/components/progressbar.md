---
title: ProgressBar
sidebar_position: 90
_i18n_hash: 7612411ef90d5344a2bab79b7e221141
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-progressbar" />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/progressbar/ProgressBar" top='true'/>

`ProgressBar` 组件直观地表示操作的完成状态。随着工作进展，矩形逐渐填充以反映当前的百分比。该条形图还可以显示其值的文本表示，并支持已知或未知持续时间的任务的确定状态和不确定状态。

<!-- INTRO_END -->

## 用法 {#usages}

`ProgressBar` 组件用于可视化任务的完成状态。它支持：

- 可配置的最小值和最大值。
- 用于没有明确结束的正在进行任务的无确定模式。
- 文本可见性、动画和条纹设计的选项，以提供更好的视觉反馈。

以下示例展示了一个带有开始、暂停和重置控制的条纹动画进度条：

<ComponentDemo 
path='/webforj/progressbarbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/progressbar/ProgressBarBasicView.java'
height='150px'
/>

## 设置值 {#setting-values}

ProgressBar 组件允许设置和获取其当前值、最小和最大限制。

```java showLineNumbers
ProgressBar bar = new ProgressBar();
bar.setMin(0);
bar.setMax(100);
bar.setValue(50);
```

## 定向 {#orientation}

`ProgressBar` 可以水平或垂直定向。

<ComponentDemo 
path='/webforj/progressbarorientation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/progressbar/ProgressBarOrientationView.java'
height='175px'
/>

## 不确定状态 {#indeterminate-state}

`ProgressBar` 支持不确定状态以处理未知完成时间的任务。

<ComponentDemo 
path='/webforj/progressbardeterminate?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/progressbar/ProgressBarDeterminateView.java'
height='25px'
/>

## 文本和文本可见性 {#text-and-text-visibility}

默认情况下，当创建时，进度条以 `XX%` 的格式显示完成百分比。使用 `setText()` 方法，您可以使用占位符 `{{x}}` 来获取当前值的百分比。此外，您可以使用占位符 `{{value}}` 来获取当前原始值。

```java
ProgressBar bar = new ProgressBar(15, "下载中: {{x}}%");
```

## 样式 {#styling}

### 主题 {#themes}

`ProgressBar` 组件内置了 <JavadocLink type="foundation" location="com/webforj/component/Theme"> 主题 </JavadocLink> ，可以快速进行样式设置，而无需使用 CSS。这些主题是预定义的样式，可以应用于按钮以改变其外观和视觉表现。 
它们提供了一种快速和一致的方式，以自定义整个应用程序中 ProgressBar 的外观。

<ComponentDemo 
path='/webforj/progressbarthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/progressbar/ProgressBarThemesView.java'
height='320px'
/>

<TableBuilder name="ProgressBar" />

## 最佳实践 {#best-practices}

- **使用适当的最小值和最大值**：设置最小值和最大值，以准确反映任务的范围。
- **定期更新进度**：持续更新进度值，以向用户提供实时反馈。
- **利用不确定状态处理未知持续时间**：对持续时间不可预测的任务使用不确定状态，以表示正在进行的进度。
- **展示文本以提供更好的用户反馈**：在进度条上显示文本，以提供关于任务进度的额外上下文。
