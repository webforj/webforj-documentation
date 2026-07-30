---
title: Dialog
sidebar_position: 30
description: >-
  Open modal popups with the Dialog component, including header, content, and
  footer sections, backdrop blur, and configurable close behavior.
_i18n_hash: 385730b12eeec91287bcbbf77b4e9c77
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-dialog" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="dialog" location="com/webforj/component/dialog/Dialog" top='true'/>

`Dialog` 组件显示一个弹出窗口，覆盖当前视图，吸引注意力到集中内容上，例如表单、确认或信息消息。

<!-- INTRO_END -->

## `Dialog` 结构 {#dialog-structure}

`Dialog` 被组织为三个部分：一个标题、一个内容区域和一个底部。可以使用 `addToHeader()`、`addToContent()` 和 `addToFooter()` 向每个部分添加组件。

<ComponentDemo
path='/webforj/dialogsections'
files={['src/main/java/com/webforj/samples/views/dialog/DialogSectionsView.java']}
height='225px'
/>

## 用法 {#usages}

1. **用户反馈和确认**：`Dialog` 组件通常用于提供反馈或请求用户确认。它们可以向用户显示各种重要的反馈，例如：

  >- 成功消息
  >- 错误警报
  >- 确认提交

2. **表单输入和编辑**：可以使用对话框收集用户输入或允许他们以受控和集中方式编辑信息。例如，可以弹出对话框编辑用户个人资料详细信息或完成多步骤表单。

3. **上下文信息**：在对话框中显示附加的上下文信息或工具提示可以帮助用户理解复杂的功能或数据。对话框可以提供详细的解释、图表或帮助文档。

4. **图像和媒体预览**：当用户需要查看媒体时，可以使用 `Dialog` 显示更大的预览或图库，例如在与以下内容交互时：
  >- 图像
  >- 视频
  >- 其他媒体

## 背景和模糊 {#backdrop-and-blur}

打开的 `Dialog` 组件有一个暗淡的背景，微妙地吸引用户的注意力。使用 `setBackdrop()` 和 `setBlurred()`，可以更改 webforJ 如何显示（或遮罩）`Dialog` 后面的内容。修改这些属性可以通过提供深度和视觉层次来帮助用户。

<ComponentDemo
path='/webforj/dialogbackdropblur'
files={['src/main/java/com/webforj/samples/views/dialog/DialogBackdropBlurView.java']}
height='600px'
/>

## 打开和关闭 `Dialog` {#opening-and-closing-the-dialog}

创建一个新的 `Dialog` 对象后，使用 `open()` 方法显示对话框。然后，可以通过以下操作之一关闭 `Dialog` 组件：
- 使用 `close()` 方法
- 按下 <kbd>ESC</kbd> 键
- 点击 `Dialog` 外部

开发人员可以选择哪些交互关闭 `Dialog`，使用 `setCancelOnEscKey()` 和 `setCancelOnOutsideClick()`。此外，`setClosable()` 方法可以禁止或允许通过按 <kbd>ESC</kbd> 键或点击 `Dialog` 外部来关闭组件。

<ComponentDemo
path='/webforj/dialogclose'
files={['src/main/java/com/webforj/samples/views/dialog/DialogCloseView.java']}
height='350px'
/>

## 自动聚焦 {#auto-focus}

启用时，自动聚焦会自动将焦点给对话框内可以聚焦的第一个元素。这对于帮助引导用户的注意力非常有用，并且可以通过 `setAutoFocus()` 方法自定义。

<ComponentDemo
path='/webforj/dialogautofocus'
files={['src/main/java/com/webforj/samples/views/dialog/DialogAutoFocusView.java']}
height='350px'
/>

## 可拖动 {#draggable}

`Dialog` 具有内置的可拖动功能，允许用户通过单击和拖动来移动 `Dialog` 窗口。可以从对话框内的任何字段：标题、内容或底部分别调整 `Dialog` 的位置。

### 吸附到边缘 {#snap-to-edge}
同样可以校准此行为，使其吸附到屏幕边缘，这意味着当从拖放位置释放时，`Dialog` 将自动与显示边缘对齐。可以通过 `setSnapToEdge()` 方法更改吸附。`setSnapThreshold()` 接受一个像素数，设置 `Dialog` 距离屏幕边缘的距离，超过这个距离后会自动吸附到边缘。

<ComponentDemo
path='/webforj/dialogdraggable'
files={['src/main/java/com/webforj/samples/views/dialog/DialogDraggableView.java']}
height='350px'
/>

## 定位 {#positioning}

对话框的位置可以使用内置的 `setPosx()` 和 `setPosy()` 方法进行调整。这些方法接受一个字符串参数，可以表示任何适用的 CSS 长度单位，例如像素或视口高度/宽度。可以在[此链接](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units#numbers_lengths_and_percentages)找到这些测量单位的列表。

<ComponentDemo
path='/webforj/dialogpositioning'
files={['src/main/java/com/webforj/samples/views/dialog/DialogPositioningView.java']}
height='350px'
/>

### 垂直对齐 {#vertical-alignment}

除了手动指定对话框的 X 和 Y 位置外，还可以使用对话框的内置枚举类来对齐 `Dialog`。可以使用的值有三个：`TOP`、`CENTER` 和 `BOTTOM`，每个值都可以与 `setAlignment()` 方法一起使用。

<ComponentDemo
path='/webforj/dialogalignments'
files={['src/main/java/com/webforj/samples/views/dialog/DialogAlignmentsView.java']}
height='550px'
/>

### 全屏和断点 {#full-screen-and-breakpoints}

`Dialog` 可以设置为进入全屏模式。启用全屏时，`Dialog` 无法移动或定位。可以使用 `Dialog` 的断点属性来调整此模式。当查询匹配时，`Dialog` 自动切换到全屏模式——否则会正常定位。

### 自适应宽度 <DocChip chip='since' label='26.00' /> {#auto-width}

默认情况下，`Dialog` 伸展以填充可用的水平空间。当通过 `setAutoWidth(true)` 启用自适应宽度时，`Dialog` 将根据其内容宽度自行调整大小。

<ComponentDemo
path='/webforj/dialogautowidth'
files={['src/main/java/com/webforj/samples/views/dialog/DialogAutoWidthView.java']}
height='350px'
/>

## 样式 {#styling}

### 主题 {#themes}

`Dialog` 组件自带 <JavadocLink type="foundation" location="com/webforj/component/dialog/Dialog.Theme.html">7种离散主题</JavadocLink>，无需使用 CSS 即可快速样式化。这些主题是可以应用于按钮的预定义样式，以改变其外观和视觉表现。它们提供了一种快速且一致的方式来定制整个应用程序中按钮的外观。

虽然每种主题有很多用例，但一些示例用法包括：

  - **危险**：具有严重后果的操作，例如清除已填写的信息或永久删除帐户/数据，适合使用危险主题的对话框。
  - **默认**：默认主题适合应用程序中不需要特别注意且普通的操作，例如切换设置。
  - **主要**：此主题适合作为页面上的主要“行动号召”，例如注册、保存更改或继续到另一个页面。
  - **成功**：成功主题的对话框非常适合可视化应用程序中元素的成功完成，例如提交表单或完成注册过程。成功主题可以在成功操作完成时程序性地应用。
  - **警告**：警告对话框用于提示用户他们即将进行潜在风险的操作，例如在离开未保存更改的页面时。这些操作通常不会像使用危险主题的操作那样影响深远。
  - **灰色**：适合细微的操作，例如较小的设置或对页的补充操作，而不是主要功能的一部分。
  - **信息**：信息主题是为用户提供澄清或额外信息的不错选择。

<ComponentDemo
path='/webforj/dialogthemes'
files={['src/main/java/com/webforj/samples/views/dialog/DialogThemesView.java']}
height='500px'
/>

<TableBuilder name="Dialog" />
