---
title: Dialog
sidebar_position: 30
description: >-
  Open modal popups with the Dialog component, including header, content, and
  footer sections, backdrop blur, and configurable close behavior.
_i18n_hash: 8f78bdc280006c73955232e7f52ed365
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-dialog" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="dialog" location="com/webforj/component/dialog/Dialog" top='true'/>

`Dialog` 组件显示一个弹出窗口，覆盖当前视图，吸引用户关注诸如表单、确认或信息消息等内容。

<!-- INTRO_END -->

## `Dialog` 结构 {#dialog-structure}

`Dialog` 组织为三个部分：头部、内容区域和底部。可以使用 `addToHeader()`、`addToContent()` 和 `addToFooter()` 向每个部分添加组件。

<ComponentDemo
path='/webforj/dialogsections'
files={['src/main/java/com/webforj/samples/views/dialog/DialogSectionsView.java']}
height='375px'
/>

## 用法 {#usages}

1. **用户反馈和确认**：`Dialog` 组件通常用于提供反馈或请求用户确认。它们可以向用户显示各种重要的反馈信息，例如：

  >- 成功消息
  >- 错误提醒
  >- 确认提交

2. **表单输入和编辑**：您可以使用对话框收集用户输入或让他们以受控和专注的方式编辑信息。例如，可以弹出对话框以编辑用户个人资料详细信息或完成多步骤表单。

3. **上下文信息**：在对话框中显示额外的上下文信息或工具提示可以帮助用户理解复杂的功能或数据。对话框可以提供深入的解释、图表或帮助文档。

4. **图像和媒体预览**：当用户需要查看媒体内容时，可以使用 `Dialog` 显示更大预览或画廊，例如与：

  >- 图像
  >- 视频
  >- 其他媒体

## 背景和模糊 {#backdrop-and-blur}

打开的 `Dialog` 组件具有暗淡的背景，微妙地吸引用户注意其内容。使用 `setBackdrop()` 和 `setBlurred()`，您可以更改 webforJ 如何显示（或遮蔽） `Dialog` 后面的内容。修改这些属性可以帮助用户提供深度和视觉层次。

<ComponentDemo
path='/webforj/dialogbackdropblur'
files={['src/main/java/com/webforj/samples/views/dialog/DialogBackdropBlurView.java']}
height='600px'
/>

## 打开和关闭 `Dialog` {#opening-and-closing-the-dialog}

创建新的 `Dialog` 对象后，使用 `open()` 方法来显示对话框。要以编程方式关闭它，请使用 `close()` 方法。您还可以控制哪些用户交互会关闭对话框：

- **点击外部** 默认情况下关闭 `Dialog`，因为 `cancelOnOutsideClick` 默认为 `true`。使用 `setCancelOnOutsideClick(false)` 可以禁用此行为。
- **按 <kbd>ESC</kbd>** 默认情况下不关闭 `Dialog`，因为 `cancelOnEscKey` 默认为 `false`。使用 `setCancelOnEscKey(true)` 可以启用此行为。

使用 `setCloseable(true)` 来启用这两种交互，或使用 `setCloseable(false)` 来禁用这两种交互。以下演示启用了这两种交互。

<ComponentDemo
path='/webforj/dialogclose'
files={['src/main/java/com/webforj/samples/views/dialog/DialogCloseView.java']}
height='375px'
/>

## 自动聚焦 {#auto-focus}

启用后，自动聚焦将自动将焦点给对话框内第一个可以聚焦的元素。这对于帮助导向用户的注意力非常有用，并可以通过 `setAutoFocus()` 方法进行自定义。

<ComponentDemo
path='/webforj/dialogautofocus'
files={['src/main/java/com/webforj/samples/views/dialog/DialogAutoFocusView.java']}
height='400px'
/>

## 可拖动 {#draggable}

`Dialog` 具有内置的可拖动功能，允许用户通过点击和拖动来移动 `Dialog` 窗口。可以从其内的任何字段（头部、内容或底部）来操控 `Dialog` 的位置。

### 快速对齐到边缘 {#snap-to-edge}
还可以校准此行为，使其快速对齐到屏幕边缘，这意味着 `Dialog` 会在释放后自动与显示器的边缘对齐。通过 `setSnapToEdge()` 方法可以更改对齐方式。`setSnapThreshold()` 接受一个像素值，用于设置 `Dialog` 距离屏幕边缘的距离，达到该距离后会自动对齐到边缘。

<ComponentDemo
path='/webforj/dialogdraggable'
files={['src/main/java/com/webforj/samples/views/dialog/DialogDraggableView.java']}
height='325px'
/>

## 定位 {#positioning}

对话框的位置可以使用内置的 `setPosx()` 和 `setPosy()` 方法进行操控。这些方法接受一个字符串参数，可以表示任何适用的 CSS 长度单位，如像素或视图高度/宽度。您可以在 [这个链接](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units#numbers_lengths_and_percentages) 上找到这些测量的列表。

<ComponentDemo
path='/webforj/dialogpositioning'
files={['src/main/java/com/webforj/samples/views/dialog/DialogPositioningView.java']}
height='400px'
/>

### 垂直对齐 {#vertical-alignment}

除了手动设置对话框的 X 和 Y 位置外，还可以使用对话框的内置枚举类对 `Dialog` 进行对齐。可以使用 `setAlignment()` 方法设置三种可能的值：`TOP`、`CENTER` 和 `BOTTOM`。

<ComponentDemo
path='/webforj/dialogalignments'
files={['src/main/java/com/webforj/samples/views/dialog/DialogAlignmentsView.java']}
height='450px'
/>

### 全屏和断点 {#full-screen-and-breakpoints}

可以设置 `Dialog` 进入全屏模式。当全屏开启时，`Dialog` 不能移动或定位。该模式可以通过 `Dialog` 的断点属性进行操控。断点是媒体查询，根据该查询组件将自动切换到全屏模式。当查询匹配时，`Dialog` 将更改为全屏，否则将处于正常位置。

### 自动宽度 <DocChip chip='since' label='26.00' /> {#auto-width}

默认情况下，`Dialog` 将伸展以填充可用的水平空间。当通过 `setAutoWidth(true)` 启用自动宽度时，`Dialog` 将根据其内容宽度进行调整。

<ComponentDemo
path='/webforj/dialogautowidth'
files={['src/main/java/com/webforj/samples/views/dialog/DialogAutoWidthView.java']}
height='350px'
/>

## 样式 {#styling}

### 主题 {#themes}

`Dialog` 组件内置了 <JavadocLink type="foundation" location="com/webforj/component/dialog/Dialog.Theme.html">7 种独特主题</JavadocLink>，可以快速样式化而无需使用 CSS。这些主题是预定义的样式，可以应用于按钮，以改变其外观和视觉呈现。它们为整个应用程序快速且一致地自定义按钮外观提供了一种方式。

虽然每个主题有许多用例，但一些主题使用示例如下：

  - **危险**：具有严重后果的操作，例如清除填写的信息或永久删除帐户/数据，代表对话框使用危险主题的一个良好用例。
  - **默认**：默认主题适合于整个应用程序中不需要特别关注的通用操作，例如切换设置。
  - **主要**：此主题适合作为页面上的主要“行动呼吁”，例如注册、保存更改或继续到另一个页面。
  - **成功**：成功主题的对话框非常适合可视化应用程序中元素的成功完成，例如表单提交或注册过程的完成。成功主题可以在成功操作完成后以编程方式应用。
  - **警告**：警告对话框可用于指示用户即将执行潜在风险的操作，例如在离开保持未保存更改的页面时。这些操作通常比使用危险主题的操作影响小。
  - **灰色**：适合于细微操作，例如小设置或对页面的辅助操作，而不是主要功能的一部分。
  - **信息**：信息主题是向用户提供澄清的附加信息的良好选择。

<ComponentDemo
path='/webforj/dialogthemes'
files={['src/main/java/com/webforj/samples/views/dialog/DialogThemesView.java']}
height='375px'
/>

<TableBuilder name="Dialog" />
