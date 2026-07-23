---
title: Splitter
sidebar_position: 115
description: >-
  Divide a layout into resizable master and detail panels with the Splitter
  component, with min and max sizes and orientation control.
_i18n_hash: 0683e5c7589bbf3fa42b8ea137c4f809
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-splitter" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="splitter" location="com/webforj/component/layout/splitter/Splitter" top='true'/>

`Splitter` 组件旨在划分和调整应用内的内容大小，封装了两个可调整大小的组件：主面板和详细面板。一个分隔条分隔这些组件，使用户能够根据自己的偏好动态调整每个组件的大小。

<!-- INTRO_END -->

## 创建分隔器 {#creating-a-splitter}

通过将两个组件传递给构造函数来创建 `Splitter`。第一个成为主面板，第二个成为详细面板。

<ComponentDemo
path='/webforj/splitterbasic'
files={[
  'src/main/java/com/webforj/samples/views/splitter/SplitterBasicView.java',
  'src/main/java/com/webforj/samples/components/SplitterBox.java',
  'src/main/frontend/css/splitter-box.css',
]}
height='300px'
/>

## 最小和最大大小 {#min-and-max-size}

`Splitter` 组件提供方法来设置面板的最小和最大大小，让您可以控制 `Splitter` 内组件的调整大小行为。当用户试图将面板调整到超出指定的最小或最大大小时，分隔器组件会强制执行这些限制，确保面板保持在定义的边界内。

### 设置大小 {#setting-sizes}

`setMasterMinSize(String masterMinSize)` 方法指定分隔器主面板的最小大小。同样，`setMasterMaxSize(String masterMaxSize)` 方法指定主面板的最大大小。

您可以使用任何有效的 CSS 单位指定大小，如下所示：

<ComponentDemo
path='/webforj/splitterminmax'
files={[
  'src/main/java/com/webforj/samples/views/splitter/SplitterMinMaxView.java',
  'src/main/java/com/webforj/samples/components/SplitterBox.java',
  'src/main/frontend/css/splitter-box.css',
]}
height='300px'
/>

## 方向 {#orientation}

您可以在 `Splitter` 组件中配置方向，使您能够创建符合特定设计要求的布局。通过指定方向，组件可以水平或垂直排列面板，提供布局设计的灵活性。

要配置方向，请使用支持的方向枚举指定 `Splitter` 应该水平还是垂直渲染：

<ComponentDemo
path='/webforj/splitterorientation'
files={[
  'src/main/java/com/webforj/samples/views/splitter/SplitterOrientationView.java',
  'src/main/java/com/webforj/samples/components/SplitterBox.java',
  'src/main/frontend/css/splitter-box.css',
]}
height='300px'
/>

## 相对位置 {#relative-position}

要设置 `Splitter` 组件中分隔条的初始位置，请使用 `setPositionRelative`。此方法接收从 `0` 到 `100` 的数值，表示在 `Splitter` 给定空间的百分比，并在总宽度的给定百分比处显示分隔条：

<ComponentDemo
path='/webforj/splitterposition'
files={[
  'src/main/java/com/webforj/samples/views/splitter/SplitterPositionView.java',
  'src/main/java/com/webforj/samples/components/SplitterBox.java',
  'src/main/frontend/css/splitter-box.css',
]}
height='300px'
/>

## 嵌套 {#nesting}

分隔器嵌套允许您创建具有可调整大小面板的复杂布局。它支持创建精细控制内容排列和调整大小的复杂用户界面。

要嵌套分隔器组件，请实例化新的 `Splitter` 实例并将其作为子组件添加到现有 `Splitter` 组件中。这种层次结构允许创建具有灵活调整大小能力的多级布局。下面的程序演示了这一点：

<ComponentDemo
path='/webforj/splitternested'
files={[
  'src/main/java/com/webforj/samples/views/splitter/SplitterNestedView.java',
  'src/main/java/com/webforj/samples/components/SplitterBox.java',
  'src/main/frontend/css/splitter-box.css',
]}
height='300px'
/>

## 自动保存 {#auto-save}

`Splitter` 组件包含一个自动保存选项，能够将面板大小的状态保存到本地存储，以保持在重新加载时尺寸一致。

当您设置自动保存配置时，`Splitter` 组件会自动将面板大小的状态存储在网页浏览器的本地存储中。这确保用户为面板选择的大小在页面重新加载或浏览器会话之间持续存在，减少了手动调整的需要。

### 清理状态 {#cleaning-the-state}

要以编程方式将 `Splitter` 恢复为默认设置和尺寸，请调用 `cleanState()` 方法，从网页浏览器的本地存储中删除与 `Splitter` 组件相关的任何已保存状态数据。

<ComponentDemo
path='/webforj/splitterautosave'
files={[
  'src/main/java/com/webforj/samples/views/splitter/SplitterAutoSaveView.java',
  'src/main/java/com/webforj/samples/components/SplitterBox.java',
  'src/main/frontend/css/splitter-box.css',
]}
height='400px'
/>

在前面的演示中，每个 `Splitter` 实例通过调用 `setAutosave` 方法激活自动保存功能。这确保面板大小自动保存到本地存储。因此，在重新加载浏览器时，这些分隔器的大小保持不变。

单击“清除状态”按钮调用 `cleanState()` 方法并刷新浏览器窗口以显示原始尺寸。

## 样式 {#styling}

<TableBuilder name="Splitter" />

## 最佳实践 {#best-practices}

为了确保使用 `Splitter` 组件时获得最佳用户体验，请考虑以下最佳实践：

- **根据内容调整**：在决定面板的方向和初始大小时，考虑内容的优先级。例如，在带有导航侧边栏和主内容区域的布局中，侧边栏通常应保持更窄，并设置最小大小以确保导航清晰。

- **战略性嵌套**：嵌套分隔器可以创建多样化的布局，但可能会使用户界面复杂并影响性能。规划您的嵌套布局以确保它们直观并提升用户体验。

- **记住用户偏好**：使用自动保存功能记住用户在会话之间的调整，提高用户体验。提供选项让用户恢复到默认设置。
