---
title: Splitter
sidebar_position: 115
_i18n_hash: 340bcd9862027e6bfb967c0e6a9b5ec1
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-splitter" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="splitter" location="com/webforj/component/layout/splitter/Splitter" top='true'/>

`Splitter` 组件旨在在您的应用程序中分割和调整内容的大小，封装了两个可调整大小的组件：主组件和详细组件。一个分隔符将这些组件分开，允许用户根据自己的喜好动态调整每个组件的大小。

<!-- INTRO_END -->

## 创建分隔器 {#creating-a-splitter}

通过将两个组件传递给其构造函数来创建 `Splitter`。第一个成为主面板，第二个成为详细面板。

<ComponentDemo
path='/webforj/splitterbasic'
files={[
  'src/main/java/com/webforj/samples/views/splitter/SplitterBasicView.java',
  'src/main/java/com/webforj/samples/components/SplitterBox.java',
  'src/main/resources/static/css/splitter-box.css',
]}
height='300px'
/>

## 最小和最大尺寸 {#min-and-max-size}

`Splitter` 组件提供方法来设置面板的最小和最大尺寸，使您能够控制 `Splitter` 内组件的调整大小行为。当用户尝试将面板调整到超出指定的最小或最大尺寸时，分隔器组件会强制执行这些约束，确保面板保持在定义的边界内。

### 设置尺寸 {#setting-sizes}

`setMasterMinSize(String masterMinSize)` 方法指定分隔板主面板的最小尺寸。同样，`setMasterMaxSize(String masterMaxSize)` 方法指定主面板的最大尺寸。

您可以使用任何有效的 CSS 单位来指定尺寸，如下所示：

<ComponentDemo
path='/webforj/splitterminmax'
files={[
  'src/main/java/com/webforj/samples/views/splitter/SplitterMinMaxView.java',
  'src/main/java/com/webforj/samples/components/SplitterBox.java',
  'src/main/resources/static/css/splitter-box.css',
]}
height='300px'
/>

## 方向 {#orientation}

您可以在 `Splitter` 组件中配置方向，允许您根据特定设计要求创建布局。通过指定方向，该组件可以水平或垂直排列面板，为布局设计提供灵活性。

要配置方向，使用支持的方向枚举来指定 `Splitter` 应该水平渲染还是垂直渲染：

<ComponentDemo
path='/webforj/splitterorientation'
files={[
  'src/main/java/com/webforj/samples/views/splitter/SplitterOrientationView.java',
  'src/main/java/com/webforj/samples/components/SplitterBox.java',
  'src/main/resources/static/css/splitter-box.css',
]}
height='300px'
/>

## 相对位置 {#relative-position}

要设置 `Splitter` 组件中分隔条的初始位置，请使用 `setPositionRelative`。此方法接受一个从 `0` 到 `100` 的数值，表示在 `Splitter` 中给定空间的百分比，并显示分隔符位于总宽度的给定百分比处：

<ComponentDemo
path='/webforj/splitterposition'
files={[
  'src/main/java/com/webforj/samples/views/splitter/SplitterPositionView.java',
  'src/main/java/com/webforj/samples/components/SplitterBox.java',
  'src/main/resources/static/css/splitter-box.css',
]}
height='300px'
/>

## 嵌套 {#nesting}

分隔器嵌套允许您创建具有可调整大小面板级别的复杂布局。它使得创建具有细致控制内容排列和调整大小的复杂用户界面成为可能。

要嵌套 Splitter 组件，实例化新的 `Splitter` 实例并将其添加作为现有 `Splitter` 组件的子元素。这种层次结构允许创建具有灵活调整大小能力的多级布局。以下程序演示了这一点：

<ComponentDemo
path='/webforj/splitternested'
files={[
  'src/main/java/com/webforj/samples/views/splitter/SplitterNestedView.java',
  'src/main/java/com/webforj/samples/components/SplitterBox.java',
  'src/main/resources/static/css/splitter-box.css',
]}
height='300px'
/>

## 自动保存 {#auto-save}

`Splitter` 组件包括一个自动保存选项，自动将面板尺寸的状态保存到本地存储，以保持在重新加载间的一致性。

当您设置自动保存配置时，`Splitter` 组件会自动将面板尺寸的状态存储在web浏览器的本地存储中。这确保用户为面板选择的尺寸在页面重新加载或浏览器会话之间保持不变，减少手动调整的需要。

### 清理状态 {#cleaning-the-state}

要以编程方式将 `Splitter` 恢复到默认设置和尺寸，调用 `cleanState()` 方法，从 web 浏览器的本地存储中删除与 `Splitter` 组件相关的任何保存状态数据。

<ComponentDemo
path='/webforj/splitterautosave'
files={[
  'src/main/java/com/webforj/samples/views/splitter/SplitterAutoSaveView.java',
  'src/main/java/com/webforj/samples/components/SplitterBox.java',
  'src/main/resources/static/css/splitter-box.css',
]}
height='400px'
/>

在前面的演示中，每个 Splitter 实例通过调用 `setAutosave` 方法激活自动保存功能。这确保面板尺寸可以自动保存到本地存储。因此，在重新加载浏览器时，这些分隔器的尺寸保持不变。

点击“清除状态”按钮调用 `cleanState()` 方法并刷新浏览器窗口以显示原始尺寸。

## 样式 {#styling}

<TableBuilder name="Splitter" />

## 最佳实践 {#best-practices}

为了确保使用 `Splitter` 组件时获得最佳用户体验，请考虑以下最佳实践：

- **根据内容进行调整**：在决定面板的方向和初始尺寸时，请考虑内容的优先级。例如，在具有导航侧边栏和主内容区域的布局中，侧边栏通常应保持较窄，并设定最小尺寸以便于明确导航。

- **战略嵌套**：嵌套分隔器可以创建灵活的布局，但可能会使用户界面复杂并影响性能。规划您的嵌套布局，以确保它们直观并增强用户体验。

- **记住用户偏好**：使用自动保存功能记住用户在不同会话中的调整，提高用户体验。提供一个选项以允许用户重置为默认设置。
