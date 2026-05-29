---
title: Dialog
sidebar_position: 30
sidebar_class_name: new-content
_i18n_hash: 750f3d1f7c1c905274eac22a90b270de
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-dialog" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="dialog" location="com/webforj/component/dialog/Dialog" top='true'/>

`Dialog` 组件显示一个弹出窗口，覆盖当前视图，吸引注意力到专注内容，如表单、确认或信息消息。

<!-- INTRO_END -->

## `Dialog` 结构 {#dialog-structure}

`Dialog` 被组织为三个部分：一个头部、一个内容区域和一个底部。可以使用 `addToHeader()`、`addToContent()` 和 `addToFooter()` 向每个部分添加组件。

<ComponentDemo
path='/webforj/dialogsections'
files={['src/main/java/com/webforj/samples/views/dialog/DialogSectionsView.java']}
height='225px'
/>

## 用法 {#usages}

1. **用户反馈和确认**：`Dialog` 组件常用于提供反馈或请求用户确认。它们可以向用户显示各种重要的反馈信息，例如：

  >- 成功消息 
  >- 错误警报
  >- 确认提交

2. **表单输入和编辑**：您可以使用对话框收集用户输入或允许用户以受控和专注的方式编辑信息。例如，可以弹出对话框以编辑用户个人资料详细信息或完成多步骤表单。

3. **上下文信息**：在对话框中显示额外的上下文信息或工具提示可以帮助用户理解复杂功能或数据。对话框可以提供深入的解释、图表或帮助文档。

4. **图像和媒体预览**：当用户需要查看媒体时，可以使用 `Dialog` 显示更大的预览或画廊，例如与以下内容交互时：
  >- 图像
  >- 视频
  >- 其他媒体

## 背景和模糊 {#backdrop-and-blur}

通过启用 webforJ `Dialog` 组件的背景属性，将在 `Dialog` 后面显示一个背景。此外，当启用时，Dialog 的模糊属性将模糊 `Dialog` 的背景。修改这些设置可以通过提供深度、视觉层次和上下文来帮助用户，从而为用户提供更清晰的指导。

<ComponentDemo
path='/webforj/dialogbackdropblur'
files={['src/main/java/com/webforj/samples/views/dialog/DialogBackdropBlurView.java']}
height='300px'
/>

## 打开和关闭 `Dialog` {#opening-and-closing-the-dialog}

创建新的 `Dialog` 对象后，使用 `open()` 方法显示对话框。然后，`Dialog` 组件可以通过以下操作之一关闭：
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

启用时，自动聚焦将自动将焦点给对话框内的第一个可聚焦元素。这在帮助引导用户注意力时是有用的，可以通过 `setAutoFocus()` 方法进行自定义。

<ComponentDemo
path='/webforj/dialogautofocus'
files={['src/main/java/com/webforj/samples/views/dialog/DialogAutoFocusView.java']}
height='350px'
/>

## 可拖动 {#draggable}

`Dialog` 具有内置的可拖动功能，允许用户通过点击和拖动来重新定位 `Dialog` 窗口。`Dialog` 的位置可以通过其内部的任意字段操作：头部、内容或底部。

### 吸附到边缘 {#snap-to-edge}
还可以校准此行为，以吸附到屏幕边缘，这意味着 `Dialog` 将在释放后自动与显示器的边缘对齐。通过 `setSnapToEdge()` 方法可以更改吸附。`setSnapThreshold()` 接受一个像素数，用于设置 `Dialog` 与屏幕边缘之间的距离，在该距离内将自动吸附到边缘。

<ComponentDemo
path='/webforj/dialogdraggable'
files={['src/main/java/com/webforj/samples/views/dialog/DialogDraggableView.java']}
height='350px'
/>

## 定位 {#positioning}

对话框的位置可以使用内置的 `setPosx()` 和 `setPosy()` 方法进行调整。这些方法接受一个字符串参数，可以表示任何适用的 CSS 长度单位，例如像素或视口高度/宽度。有关这些度量单位的列表 [可以在此链接中找到](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units#numbers_lengths_and_percentages)。

<ComponentDemo
path='/webforj/dialogpositioning'
files={['src/main/java/com/webforj/samples/views/dialog/DialogPositioningView.java']}
height='350px'
/>

### 垂直对齐 {#vertical-alignment}

除了手动分配对话框的 X 和 Y 位置外，还可以使用对话框内置的枚举类对 `Dialog` 进行对齐。有三个可能的值，`TOP`、`CENTER` 和 `BOTTOM`，每个值都可以与 `setAlignment()` 方法结合使用。

<ComponentDemo
path='/webforj/dialogalignments'
files={['src/main/java/com/webforj/samples/views/dialog/DialogAlignmentsView.java']}
height='550px'
/>

### 全屏和断点 {#full-screen-and-breakpoints}

`Dialog` 可以设置为进入全屏模式。当启用全屏时，`Dialog` 不能被移动或定位。这个模式可以通过 `Dialog` 的断点属性进行调整。断点是一个媒体查询，当 `Dialog` 将自动切换到全屏模式时。查询匹配时，`Dialog` 切换到全屏 - 否则它的位置保持不变。

### 自动宽度 <DocChip chip='since' label='26.00' /> {#auto-width}

默认情况下，`Dialog` 会扩展以填满可用的水平空间。当通过 `setAutoWidth(true)` 启用自动宽度时，`Dialog` 会根据其内容的宽度进行调整。

<ComponentDemo
path='/webforj/dialogautowidth'
files={['src/main/java/com/webforj/samples/views/dialog/DialogAutoWidthView.java']}
height='350px'
/>

## 样式 {#styling}

### 主题 {#themes}

`Dialog` 组件带有 <JavadocLink type="foundation" location="com/webforj/component/dialog/Dialog.Theme.html">7 种离散主题 </JavadocLink>，可快速进行样式调整，无需使用 CSS。这些主题是预定义的样式，可以应用于按钮，以改变其外观和视觉呈现。它们提供了一种快速且一致的方式来定制应用程序中按钮的外观。

虽然每种主题都有许多用例，但一些示例用法包括：

  - **危险**：具有严重后果的操作，例如清除填写的信息或永久删除帐户/数据，适合使用危险主题的对话框。
  - **默认**：默认主题适用于不需要特别关注且通用的应用程序内操作，如切换设置。
  - **主要**：此主题适合作为页面上的主要“行动呼吁”，如注册、保存更改或继续到下一页。
  - **成功**：成功主题的对话框非常适合可视化应用程序中元素的成功完成，如提交表单或完成注册过程。成功主题可以在成功操作完成后程序化应用。
  - **警告**：警告对话框用于指示用户即将执行可能的风险操作，例如导航离开包含未保存更改的页面。这些操作通常不如使用危险主题的操作影响大。
  - **灰色**：适合细微的操作，例如次要设置或对页面的补充操作，而不是主要功能的一部分。
  - **信息**：信息主题是向用户提供澄清或补充信息的好选择。

<ComponentDemo
path='/webforj/dialogthemes'
files={['src/main/java/com/webforj/samples/views/dialog/DialogThemesView.java']}
height='500px'
/>

<TableBuilder name="Dialog" />
