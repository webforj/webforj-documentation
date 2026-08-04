---
title: Dialog
sidebar_position: 30
description: >-
  Open modal popups with the Dialog component, including header, content, and
  footer sections, backdrop blur, and configurable close behavior.
_i18n_hash: 901c54134f4c21092deb23457747a29b
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-dialog" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="dialog" location="com/webforj/component/dialog/Dialog" top='true'/>

`Dialog` 组件显示一个弹出窗口，覆盖当前视图，吸引用户注意专注的内容，比如表单、确认或信息消息。

<!-- INTRO_END -->

## `Dialog` 结构 {#dialog-structure}

`Dialog` 被组织为三个部分：一个头部、一个内容区和一个底部。可以通过 `addToHeader()`、`addToContent()` 和 `addToFooter()` 在每个部分添加组件。

<ComponentDemo
path='/webforj/dialogsections'
files={['src/main/java/com/webforj/samples/views/dialog/DialogSectionsView.java']}
height='375px'
/>

## 用法 {#usages}

1. **用户反馈和确认**：`Dialog` 组件通常用于提供反馈或询问用户确认。它们可以向用户显示各种重要的反馈信息，例如：

  >- 成功消息
  >- 错误警报
  >- 提交确认

2. **表单输入与编辑**：您可以使用对话框来收集用户输入或允许他们以受控和集中的方式编辑信息。例如，可以弹出对话框来编辑用户个人资料信息或完成多步骤表单。

3. **上下文信息**：在对话框中显示额外的上下文信息或工具提示可以帮助用户理解复杂的功能或数据。对话框可以提供深入说明、图表或帮助文档。

4. **图像和媒体预览**：当用户需要查看媒体内容时，可以使用 `Dialog` 显示更大的预览或画廊，例如与以下内容交互时：
  >- 图像
  >- 视频
  >- 其他媒体

## 背景和模糊 {#backdrop-and-blur}

打开的 `Dialog` 组件有一个暗淡的背景，微妙地吸引注意其内容。使用 `setBackdrop()` 和 `setBlurred()`，可以更改 webforJ 显示（或遮挡）对话框后面的内容的方式。修改这些属性可以通过提供深度和视觉层次来帮助用户。

<ComponentDemo
path='/webforj/dialogbackdropblur'
files={['src/main/java/com/webforj/samples/views/dialog/DialogBackdropBlurView.java']}
height='600px'
/>

## 打开和关闭 `Dialog` {#opening-and-closing-the-dialog}

创建新的 `Dialog` 对象后，使用 `open()` 方法来显示对话框。然后，`Dialog` 组件可以通过以下操作之一关闭：
- 使用 `close()` 方法
- 按下 <kbd>ESC</kbd> 键
- 在 `Dialog` 外部点击

开发人员可以选择使用 `setCancelOnEscKey()` 和 `setCancelOnOutsideClick()` 来确定哪些交互可以关闭 `Dialog`。此外，`setClosable()` 方法可以防止或允许按下 <kbd>ESC</kbd> 键和在 `Dialog` 外部点击来关闭组件。

<ComponentDemo
path='/webforj/dialogclose'
files={['src/main/java/com/webforj/samples/views/dialog/DialogCloseView.java']}
height='375px'
/>

## 自动聚焦 {#auto-focus}

启用时，自动聚焦将自动将焦点给予对话框内的第一个可聚焦元素。这在帮助引导用户注意力方面非常有用，并可以通过 `setAutoFocus()` 方法进行自定义。

<ComponentDemo
path='/webforj/dialogautofocus'
files={['src/main/java/com/webforj/samples/views/dialog/DialogAutoFocusView.java']}
height='400px'
/>

## 可拖动 {#draggable}

`Dialog` 具有内置的可拖动功能，允许用户通过点击和拖动移动 `Dialog` 窗口。可以从其任何字段（头部、内容或底部）操作 `Dialog` 的位置。

### 磁吸到边缘 {#snap-to-edge}
还可以校准此行为，以磁吸到屏幕边缘，这意味着 `Dialog`将在释放后自动与显示器的边缘对齐。通过 `setSnapToEdge()` 方法可以更改磁吸设置。`setSnapThreshold()` 接受一个像素值，它定义了 `Dialog` 离屏幕边缘的距离，当超过该距离时将自动磁吸到边缘。

<ComponentDemo
path='/webforj/dialogdraggable'
files={['src/main/java/com/webforj/samples/views/dialog/DialogDraggableView.java']}
height='325px'
/>

## 定位 {#positioning}

可以使用内置的 `setPosx()` 和 `setPosy()` 方法来操作对话框的位置。这些方法接受一个字符串参数，可以表示任何适用的 CSS 长度单位，比如像素或视口高度/宽度。有关这些测量的列表 [可以在此链接中找到](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units#numbers_lengths_and_percentages)。

<ComponentDemo
path='/webforj/dialogpositioning'
files={['src/main/java/com/webforj/samples/views/dialog/DialogPositioningView.java']}
height='400px'
/>

### 垂直对齐 {#vertical-alignment}

除了手动指定对话框的 X 和 Y 位置外，使用对话框的内置枚举类来对齐 `Dialog` 也是可能的。有三种可能的值，`TOP`、`CENTER` 和 `BOTTOM`，每个值都可以与 `setAlignment()` 方法一起使用。

<ComponentDemo
path='/webforj/dialogalignments'
files={['src/main/java/com/webforj/samples/views/dialog/DialogAlignmentsView.java']}
height='450px'
/>

### 全屏和断点 {#full-screen-and-breakpoints}

`Dialog` 可以设置为进入全屏模式。当启用全屏时，`Dialog` 不能被移动或定位。此模式可以通过 `Dialog` 的断点属性进行操作。断点是一个媒体查询，当匹配时，`Dialog` 将自动切换到全屏模式。查询匹配时，`Dialog` 改变为全屏 - 否则它将被定位。

### 自动宽度 <DocChip chip='since' label='26.00' /> {#auto-width}

默认情况下，`Dialog` 拉伸以填满可用的水平空间。当通过 `setAutoWidth(true)` 启用自动宽度时，`Dialog` 将根据其内容宽度进行自我调整。

<ComponentDemo
path='/webforj/dialogautowidth'
files={['src/main/java/com/webforj/samples/views/dialog/DialogAutoWidthView.java']}
height='350px'
/>

## 样式 {#styling}

### 主题 {#themes}

`Dialog` 组件附带 <JavadocLink type="foundation" location="com/webforj/component/dialog/Dialog.Theme.html">7 种独特主题</JavadocLink>，可快速进行样式设置，而无需使用 CSS。这些主题是可以应用于按钮的预定义样式，改变其外观和视觉表现。它们提供了一种快速、一致的方式来自定义整个应用程序中的按钮外观。

虽然每种主题都有许多用例，但一些示例用法包括：

  - **危险**：具有严重后果的操作，例如清除填写的信息或永久删除帐户/数据，适合使用危险主题的对话框。
  - **默认**：默认主题适用于应用程序中不需要特别注意和通用的操作，例如切换设置。
  - **主要**：该主题适合作为页面上的主要“行动呼吁”，例如注册、保存更改或继续到另一个页面。
  - **成功**：成功主题的对话框非常适合可视化应用程序中元素的成功完成，例如表单提交或注册过程完成。一旦成功操作完成，成功主题可以通过程序化应用。
  - **警告**：警告对话框用于指示用户即将执行可能的风险操作，例如在离开未保存更改的页面时。这些操作通常不如使用危险主题的操作影响大。
  - **灰色**：适合轻微的操作，例如小的设置或更附属于页面的操作，而不是主要功能的一部分。
  - **信息**：信息主题是一个很好的选择，以向用户提供澄清、额外的信息。

<ComponentDemo
path='/webforj/dialogthemes'
files={['src/main/java/com/webforj/samples/views/dialog/DialogThemesView.java']}
height='375px'
/>

<TableBuilder name="Dialog" />
