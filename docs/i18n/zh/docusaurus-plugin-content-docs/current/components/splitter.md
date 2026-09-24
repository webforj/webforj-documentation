---
title: Splitter
sidebar_position: 115
description: >-
  Divide a layout into resizable master and detail panels with the Splitter
  component, with min and max sizes and orientation control.
_i18n_hash: c700d01058105b5b752ecfa560224fb5
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-splitter" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="splitter" location="com/webforj/component/layout/splitter/Splitter" top='true'/>

`Splitter` 组件旨在划分和调整应用程序中的内容，封装了两个可调整大小的组件：主组件和详细组件。一个分隔器将这些组件分开，允许用户根据自己的偏好动态调整每个组件的大小。

<!-- INTRO_END -->

## 创建分割器 {#creating-a-splitter}

通过将两个组件传递给其构造函数来创建 `Splitter`。第一个成为主面板，第二个成为详细面板。

<ComponentDemo
path='/webforj/splitterbasic'
files={[
  'src/main/java/com/webforj/samples/views/splitter/SplitterBasicView.java',
  'src/main/java/com/webforj/samples/components/SplitterBox.java',
  'src/main/frontend/css/splitter/splitter-box.css',
]}
height='300px'
/>

## 最小和最大大小 {#min-and-max-size}

`Splitter` 组件提供了设置其面板的最小和最大大小的方法，使您能够控制 `Splitter` 中组件的调整大小行为。当用户尝试将面板调整到超出指定的最小或最大大小时，分割器组件会执行这些约束，确保面板保持在定义的边界内。

### 设置大小 {#setting-sizes}

`setMasterMinSize(String masterMinSize)` 方法指定分割器主面板的最小大小。同样，`setMasterMaxSize(String masterMaxSize)` 方法指定主面板的最大大小。

您可以使用任何有效的 CSS 单位来指定大小，如下所示：

<ComponentDemo
path='/webforj/splitterminmax'
files={[
  'src/main/java/com/webforj/samples/views/splitter/SplitterMinMaxView.java',
  'src/main/java/com/webforj/samples/components/SplitterBox.java',
  'src/main/frontend/css/splitter/splitter-box.css',
]}
height='300px'
/>

## 方向 {#orientation}

您可以在 `Splitter` 组件中配置方向，使您能够创建满足特定设计需求的布局。通过指定方向，组件可以水平或垂直排列面板，为布局设计提供灵活性。

要配置方向，请使用支持的方向枚举来指定 `Splitter` 应该水平或垂直渲染：

<ComponentDemo
path='/webforj/splitterorientation'
files={[
  'src/main/java/com/webforj/samples/views/splitter/SplitterOrientationView.java',
  'src/main/java/com/webforj/samples/components/SplitterBox.java',
  'src/main/frontend/css/splitter/splitter-box.css',
]}
height='300px'
/>

## 相对位置 {#relative-position}

要设置 `Splitter` 组件中分隔条的初始位置，可以使用 `setPositionRelative`。该方法接受一个从 `0` 到 `100` 的数值，表示在 `Splitter` 中给定空间的百分比，并在给定的总宽度百分比处显示分隔符：

<ComponentDemo
path='/webforj/splitterposition'
files={[
  'src/main/java/com/webforj/samples/views/splitter/SplitterPositionView.java',
  'src/main/java/com/webforj/samples/components/SplitterBox.java',
  'src/main/frontend/css/splitter/splitter-box.css',
]}
height='300px'
/>

## 嵌套 {#nesting}

分割器嵌套允许您创建具有可调整大小面板的复杂布局。它能够创建复杂的用户界面，粒度控制内容的排列和调整大小。

要嵌套分割器组件，请实例化新的 `Splitter` 实例，并将它们作为子项添加到现有的 `Splitter` 组件中。这种层次结构允许创建具有灵活调整大小能力的多级布局。下面的程序演示了这一点：

<ComponentDemo
path='/webforj/splitternested'
files={[
  'src/main/java/com/webforj/samples/views/splitter/SplitterNestedView.java',
  'src/main/java/com/webforj/samples/components/SplitterBox.java',
  'src/main/frontend/css/splitter/splitter-box.css',
]}
height='300px'
/>

## 自动保存 {#auto-save}

`Splitter` 组件包括一个自动保存选项，该选项将面板大小的状态保存到本地存储，以保持在重新加载之间的一致性。

当您设置自动保存配置时，`Splitter` 组件会自动将面板大小的状态存储在网页浏览器的本地存储中。这确保了用户选择的面板大小在页面重新加载或浏览器会话之间持续存在，减少了手动调整的需要。

### 清理状态 {#cleaning-the-state}

要以程序方式将 `Splitter` 恢复到默认设置和尺寸，可以调用 `cleanState()` 方法，以从网页浏览器的本地存储中删除与 `Splitter` 组件相关的任何保存状态数据。

<ComponentDemo
path='/webforj/splitterautosave'
files={[
  'src/main/java/com/webforj/samples/views/splitter/SplitterAutoSaveView.java',
  'src/main/java/com/webforj/samples/components/SplitterBox.java',
  'src/main/frontend/css/splitter/splitter-box.css',
]}
height='400px'
/>

在前面的演示中，每个分割器实例通过调用 `setAutosave` 方法激活自动保存功能。这确保面板的大小会自动保存到本地存储。因此，重新加载浏览器时，这些分割器的大小保持不变。

单击“清除状态”按钮会调用 `cleanState()` 方法并刷新浏览器窗口以显示原始尺寸。

## 样式 {#styling}

<TableBuilder name="Splitter" />

## 最佳实践 {#best-practices}

为了确保在使用 `Splitter` 组件时提供最佳用户体验，请考虑以下最佳实践：

- **基于内容调整**：在决定面板的方向和初始大小时，考虑内容的优先级。例如，在具有导航侧边栏和主内容区域的布局中，侧边栏通常应保持较窄，并设置最小大小以便进行清晰导航。

- **战略嵌套**：嵌套分割器可以创建多功能布局，但可能会使 UI 复杂并影响性能。规划嵌套布局以确保它们直观并增强用户体验。

- **记住用户偏好**：使用自动保存功能记住用户在会话中的调整，提升用户体验。提供选项，让用户可以重置为默认设置。
