---
title: Splitter
sidebar_position: 115
_i18n_hash: 9eb7b2aa3890f16f8fe8a2d4c303b227
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-splitter" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="splitter" location="com/webforj/component/layout/splitter/Splitter" top='true'/>

`Splitter` 组件旨在分割和调整您应用程序中的内容大小，封装了两个可调整大小的组件：主组件和细节组件。一个分隔条将这些组件分开，允许用户根据自己的喜好动态调整每个组件的大小。

<!-- INTRO_END -->

## 创建分割器 {#creating-a-splitter}

通过将两个组件传递给其构造函数来创建 `Splitter`。第一个成为主面板，第二个成为细节面板。

<ComponentDemo 
path='/webforj/splitterbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterBasicView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/components/SplitterBox.java',]}
cssURL='/css/splitter-box.css'
height='300px'
/>

## 最小和最大大小 {#min-and-max-size}

`Splitter` 组件提供方法设置其面板的最小和最大大小，使您能够控制在 `Splitter` 中组件的调整行为。当用户尝试将面板调整到指定的最小或最大大小之外时，分割器组件强制执行这些约束，确保面板保持在定义的边界内。

### 设置大小 {#setting-sizes}

`setMasterMinSize(String masterMinSize)` 方法指定分割器主面板的最小大小。同样，`setMasterMaxSize(String masterMaxSize)` 方法指定主面板的最大大小。

您可以使用任何有效的 CSS 单位来指定大小，如下所示：

<ComponentDemo 
path='/webforj/splitterminmax?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterMinMaxView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/components/SplitterBox.java',]}
cssURL='/css/splitter-box.css'
height='300px'
/>

## 方向 {#orientation}

您可以在 `Splitter` 组件中配置方向，允许您创建根据特定设计要求量身定制的布局。通过指定方向，组件可以水平或垂直排列面板，为布局设计提供灵活性。

要配置方向，请使用支持的方向枚举来指定 `Splitter` 是否应水平或垂直呈现：

<ComponentDemo 
path='/webforj/splitterorientation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterOrientationView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/components/SplitterBox.java',]}
cssURL='/css/splitter-box.css'
height='300px'
/>

## 相对位置 {#relative-position}

要设置 `Splitter` 组件中分隔条的初始位置，请使用 `setPositionRelative`。此方法接受一个从 `0` 到 `100` 的数值，表示 `Splitter` 中给定空间的百分比，并在给定的总宽度百分比处显示分隔条：

<ComponentDemo 
path='/webforj/splitterposition?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterPositionView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/components/SplitterBox.java',]}
cssURL='/css/splitter-box.css'
height='300px'
/>

## 嵌套 {#nesting}

分割器嵌套允许您创建具有可调整大小面板的复杂布局。它使得创建复杂的用户界面成为可能，并提供对内容安排和调整的细致控制。

要嵌套分割器组件，请实例化新的 `Splitter` 实例并将其作为子组件添加到现有的 `Splitter` 组件中。这种层次结构允许创建具有灵活调整能力的多级布局。下面的程序演示了这一点：

<ComponentDemo 
path='/webforj/splitternested?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterNestedView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/components/SplitterBox.java',]}
cssURL='/css/splitter-box.css'
height='300px'
/>

## 自动保存 {#auto-save}

`Splitter` 组件包括自动保存选项，该选项将面板大小的状态保存到本地存储中，以保持在重新加载之间的一致性。

当您设置自动保存配置时，`Splitter` 组件会自动将面板大小的状态存储在Web浏览器的本地存储中。这确保用户选择的面板大小在页面重新加载或浏览器会话之间持续存在，减少了手动调整的需要。

### 清除状态 {#cleaning-the-state}

要以编程方式将 `Splitter` 还原为默认设置和尺寸，请调用 `cleanState()` 方法，从Web浏览器的本地存储中删除与 `Splitter` 组件相关的任何保存的状态数据。

<ComponentDemo 
path='/webforj/splitterautosave?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterAutoSaveView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/components/SplitterBox.java',]}
cssURL='/css/splitter-box.css'
height='400px'
/>

在之前的演示中，每个 Splitter 实例通过调用 `setAutosave` 方法激活自动保存功能。这确保面板大小会自动保存到本地存储中。因此，重新加载浏览器时，这些分割器的大小保持不变。

点击“清除状态”按钮调用 `cleanState()` 方法，并刷新浏览器窗口以显示原始尺寸。

## 样式 {#styling}

<TableBuilder name="Splitter" />

## 最佳实践 {#best-practices}

为了确保使用 `Splitter` 组件时的最佳用户体验，请考虑以下最佳实践：

- **根据内容进行调整**：在决定面板的方向和初始大小时，请考虑内容的优先级。例如，在具有导航侧边栏和主要内容区域的布局中，侧边栏通常应保持较窄，并设置最小大小以便于导航。

- **战略嵌套**：嵌套分割器可以创建多功能布局，但可能会使用户界面复杂并影响性能。规划您的嵌套布局以确保它们直观并增强用户体验。

- **记住用户偏好**：使用自动保存功能记住用户在会话中的调整，提升用户体验。提供一个选项，让用户能够重置为默认设置。
