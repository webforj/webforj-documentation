---
title: Dialog
sidebar_position: 30
description: >-
  Open modal popups with the Dialog component, including header, content, and
  footer sections, backdrop blur, and configurable close behavior.
_i18n_hash: 3dcdd5a9a66f2b00229064500da2bb79
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-dialog" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="dialog" location="com/webforj/component/dialog/Dialog" top='true'/>

`Dialog` 组件显示一个弹出窗口，覆盖当前视图，吸引用户注意重点内容，如表单、确认或信息消息。

<!-- INTRO_END -->

## `Dialog` 结构 {#dialog-structure}

`Dialog` 被组织成三个部分：头部、内容区域和底部。可以使用 `addToHeader()`、`addToContent()` 和 `addToFooter()` 在每个部分添加组件。

<ComponentDemo
path='/webforj/dialogsections'
files={['src/main/java/com/webforj/samples/views/dialog/DialogSectionsView.java']}
height='225px'
/>

## 用法 {#usages}

1. **用户反馈和确认**：`Dialog` 组件通常用于提供反馈或请求用户确认。它们可以向用户显示各种重要的反馈信息，例如：

  >- 成功消息
  >- 错误提醒
  >- 确认提交

2. **表单输入和编辑**：可以使用对话框来收集用户输入或允许他们以受控和集中的方式编辑信息。例如，可以弹出对话框编辑用户个人资料详细信息或完成多步骤表单。

3. **上下文信息**：在对话框中显示额外的上下文信息或工具提示可以帮助用户理解复杂的功能或数据。对话框可以提供详细的解释、图表或帮助文档。

4. **图像和媒体预览**：当用户需要查看媒体内容时，可以使用 `Dialog` 显示更大的预览或图库，例如在与以下项目交互时：
  >- 图像
  >- 视频
  >- 其他媒体

## 背景和模糊 {#backdrop-and-blur}

通过启用 webforJ `Dialog` 组件的背景属性，`Dialog` 后面将显示一个背景。此外，启用时，`Dialog` 的模糊属性将模糊 `Dialog` 的背景。通过修改这些设置，可以为用户提供深度、视觉层次和上下文，从而为用户提供更清晰的指导。

<ComponentDemo
path='/webforj/dialogbackdropblur'
files={['src/main/java/com/webforj/samples/views/dialog/DialogBackdropBlurView.java']}
height='300px'
/>

## 打开和关闭 `Dialog` {#opening-and-closing-the-dialog}

创建新的 `Dialog` 对象后，使用 `open()` 方法显示对话框。然后，`Dialog` 组件可以通过以下操作关闭：
- 使用 `close()` 方法
- 按下 <kbd>ESC</kbd> 键
- 点击 `Dialog` 外部

开发人员可以选择哪些交互关闭 `Dialog`，使用 `setCancelOnEscKey()` 和 `setCancelOnOutsideClick()`。此外，`setClosable()` 方法可以防止或允许按下 <kbd>ESC</kbd> 键和点击 `Dialog` 外部来关闭组件。

<ComponentDemo
path='/webforj/dialogclose'
files={['src/main/java/com/webforj/samples/views/dialog/DialogCloseView.java']}
height='350px'
/>

## 自动聚焦 {#auto-focus}

启用时，自动聚焦会自动将焦点给对话框中可以聚焦的第一个元素。这有助于引导用户的注意力，并可以通过 `setAutoFocus()` 方法进行自定义。

<ComponentDemo
path='/webforj/dialogautofocus'
files={['src/main/java/com/webforj/samples/views/dialog/DialogAutoFocusView.java']}
height='350px'
/>

## 可拖动 {#draggable}

`Dialog` 内置可拖动的功能，使用户可以通过点击和拖动来重新定位 `Dialog` 窗口。`Dialog` 的位置可以通过它内部的任意字段操纵：头部、内容或底部。

### 吸附到边缘 {#snap-to-edge}
还可以校准此行为，使其吸附到屏幕边缘，这意味着当 `Dialog` 从拖放日期释放时，它会自动与显示器的边缘对齐。通过 `setSnapToEdge()` 方法可以更改吸附。`setSnapThreshold()` 接受一个像素数量，设置 `Dialog` 距离屏幕边缘多远才会自动吸附到边缘。

<ComponentDemo
path='/webforj/dialogdraggable'
files={['src/main/java/com/webforj/samples/views/dialog/DialogDraggableView.java']}
height='350px'
/>

## 定位 {#positioning}

可以使用内置的 `setPosx()` 和 `setPosy()` 方法操纵对话框的位置。这些方法接受一个字符串参数，可以表示任何适用的 CSS 长度单位，例如像素或视口高度/宽度。这些测量的列表 [可以在此链接找到](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units#numbers_lengths_and_percentages)。

<ComponentDemo
path='/webforj/dialogpositioning'
files={['src/main/java/com/webforj/samples/views/dialog/DialogPositioningView.java']}
height='350px'
/>

### 垂直对齐 {#vertical-alignment}

除了手动分配对话框的 X 和 Y 位置外，还可以使用对话框的内置枚举类来对齐 `Dialog`。有三个可能的值，`TOP`、`CENTER` 和 `BOTTOM`，每个值都可以与 `setAlignment()` 方法一起使用。

<ComponentDemo
path='/webforj/dialogalignments'
files={['src/main/java/com/webforj/samples/views/dialog/DialogAlignmentsView.java']}
height='550px'
/>

### 全屏和断点 {#full-screen-and-breakpoints}

`Dialog` 可以设置为进入全屏模式。启用全屏时，`Dialog` 不能移动或定位。可以通过 `Dialog` 的断点属性来控制此模式。断点是一个媒体查询，用于自动触发对话框进入全屏模式。当查询匹配时，`Dialog` 会切换到全屏 - 否则，它将被定位。

### 自动宽度 <DocChip chip='since' label='26.00' /> {#auto-width}

默认情况下，`Dialog` 会拉伸以填充可用的水平空间。当通过 `setAutoWidth(true)` 启用自动宽度时，`Dialog` 将根据其内容宽度自行调整大小。

<ComponentDemo
path='/webforj/dialogautowidth'
files={['src/main/java/com/webforj/samples/views/dialog/DialogAutoWidthView.java']}
height='350px'
/>

## 样式 {#styling}

### 主题 {#themes}

`Dialog` 组件内置了 <JavadocLink type="foundation" location="com/webforj/component/dialog/Dialog.Theme.html">7 种独立主题</JavadocLink>，以快速样式化，而无需使用 CSS。这些主题是预定义的样式，可以应用于按钮以更改其外观和视觉呈现。它们提供了一种快速且一致的方式来定制应用程序中按钮的外观。

虽然每种主题都有许多用例，但一些示例用法包括：

  - **危险**：具有严重后果的操作，例如清除填写的信息或永久删除帐户/数据，代表了使用危险主题对话框的良好用例。
  - **默认**：默认主题适用于不需要特别关注且通用的操作，例如切换设置。
  - **主要**：该主题适合作为页面上的主要“行动呼吁”，例如注册、保存更改或继续到其他页面。
  - **成功**：成功主题的对话框非常适合可视化应用程序中某个元素成功完成的状态，例如提交表单或完成注册过程。一旦成功操作完成，可以程序性应用成功主题。
  - **警告**：警告对话框用于指示用户即将执行潜在风险的操作，例如在离开具有未保存变更的页面时。这些操作通常没有危险主题所使用的操作那么影响深远。
  - **灰色**：适用于细微的操作，例如次要设置或更补充的操作，而不是主要功能的一部分。
  - **信息**：信息主题是为用户提供澄清、额外信息的良好选择。

<ComponentDemo
path='/webforj/dialogthemes'
files={['src/main/java/com/webforj/samples/views/dialog/DialogThemesView.java']}
height='500px'
/>

<TableBuilder name="Dialog" />
