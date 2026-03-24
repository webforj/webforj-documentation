---
title: Splitter
sidebar_position: 115
_i18n_hash: 2f66a9093a3c1f6e339df8fb42048a55
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-splitter" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="splitter" location="com/webforj/component/layout/splitter/Splitter" top='true'/>

`Splitter` 组件旨在划分和调整您应用程序中的内容，封装了两个可调整大小的组件：主组件和细节组件。一个分隔条将这些组件分开，允许用户根据自己的偏好动态调整每个组件的大小。

<!-- INTRO_END -->

## 创建拆分器 {#creating-a-splitter}

通过向其构造函数传递两个组件来创建 `Splitter`。第一个成为主面板，第二个成为细节面板。

<ComponentDemo 
path='/webforj/splitterbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterBasicView.java'
height='300px'
/>

## 最小和最大尺寸 {#min-and-max-size}

`Splitter` 组件提供了设置面板最小和最大尺寸的方法，允许您控制 `Splitter` 中组件的调整大小行为。当用户尝试将面板调整到超出指定最小或最大尺寸时，拆分器组件会强制执行这些约束，确保面板保持在定义的边界内。

### 设置尺寸 {#setting-sizes}

`setMasterMinSize(String masterMinSize)` 方法指定拆分器主面板的最小尺寸。同样，`setMasterMaxSize(String masterMaxSize)` 方法指定主面板的最大尺寸。

您可以使用任何有效的 CSS 单位来指定尺寸，如下所示：

<ComponentDemo 
path='/webforj/splitterminmax?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterMinMaxView.java'
height='300px'
/>

## 方向 {#orientation}

您可以在 `Splitter` 组件中配置方向，允许您创建满足特定设计要求的布局。通过指定方向，组件可以水平或垂直排列面板，从而在布局设计中提供灵活性。

要配置方向，请使用支持的方向枚举来指定 `Splitter` 应该水平或垂直渲染：

<ComponentDemo 
path='/webforj/splitterorientation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterOrientationView.java'
height='300px'
/>

## 相对位置 {#relative-position}

要设置 `Splitter` 组件中分隔条的初始位置，请使用 `setPositionRelative`。此方法接受一个从 `0` 到 `100` 的数值，表示在 `Splitter` 中给定空间的百分比，并在总宽度的给定百分比处显示分隔条：

<ComponentDemo 
path='/webforj/splitterposition?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterPositionView.java'
height='300px'
/>

## 嵌套 {#nesting}

拆分器嵌套允许您创建复杂的布局，包含可调整大小的面板层级。它使创建复杂用户界面成为可能，可对内容的排列和调整大小进行细粒度控制。

要嵌套拆分器组件，请实例化新的 `Splitter` 实例，并将它们作为子组件添加到现有的 `Splitter` 组件中。这种层次结构允许创建具有灵活调整大小功能的多级布局。下面的程序演示了这一点：

<ComponentDemo 
path='/webforj/splitternested?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterNestedView.java'
height='300px'
/>

## 自动保存 {#auto-save}

`Splitter` 组件包括一个自动保存选项，该选项将面板尺寸的状态保存到本地存储，以确保在重载之间保持一致的尺寸。

当您设置自动保存配置时，`Splitter` 组件会自动将面板尺寸的状态存储在网络浏览器的本地存储中。这确保用户为面板选择的尺寸在页面重新加载或浏览器会话之间保持不变，从而减少手动调整的必要性。

### 清理状态 {#cleaning-the-state}

要以编程方式将 `Splitter` 恢复到默认设置和尺寸，请调用 `cleanState()` 方法，以从网络浏览器的本地存储中移除与 `Splitter` 组件相关的任何保存状态数据。

<ComponentDemo 
path='/webforj/splitterautosave?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterAutoSaveView.java'
height='400px'
/>

在上述演示中，每个 Splitter 实例通过调用 `setAutosave` 方法激活自动保存功能。这确保面板尺寸自动保存到本地存储。因此，在重新加载浏览器时，这些拆分器的尺寸保持不变。

单击“清除状态”按钮会调用 `cleanState()` 方法并刷新浏览器窗口以显示原始尺寸。

## 样式 {#styling}

<TableBuilder name="Splitter" />

## 最佳实践 {#best-practices}

为了确保使用 `Splitter` 组件时的最佳用户体验，请考虑以下最佳实践： 

- **根据内容调整**：在决定面板的方向和初始尺寸时，考虑内容的优先级。例如，在具有导航侧边栏和主内容区域的布局中，侧边栏通常应保持较窄，并设定最小尺寸以便于导航。

- **战略性嵌套**：嵌套拆分器可以创建多样化的布局，但可能会使用户界面变得复杂并影响性能。规划您的嵌套布局，以确保它们直观并提高用户体验。

- **记住用户偏好**：使用自动保存功能记住用户在会话中的调整，提高用户体验。提供选项以允许用户重置为默认设置。
