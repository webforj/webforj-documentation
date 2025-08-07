---
title: Dialog
sidebar_position: 30
_i18n_hash: d0087974ac244db9b082133be7966a3e
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-dialog" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="dialog" location="com/webforj/component/dialog/Dialog" top='true'/>

webforJ对话框组件旨在让开发者能够快速轻松地在其应用程序中显示对话框，例如登录菜单或信息框。

该组件由三个部分组成，每个部分都是`Panel`组件：**标题**、**内容**和**页脚**。

<ComponentDemo 
path='/webforj/dialogsections?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogSectionsView.java'
height = '225px'
/>

## 用途 {#usages}

1. **用户反馈和确认**：`Dialog`组件通常用于提供反馈或请求用户确认。它们可以向用户显示各种重要反馈信息，例如：

  >- 成功消息 
  >- 错误警报
  >- 确认提交

2. **表单输入和编辑**：您可以使用对话框收集用户输入或允许他们以受控和专注的方式编辑信息。例如，可以弹出对话框以编辑用户配置文件详细信息或完成多步骤表单。

3. **上下文信息**：在对话框中显示附加的上下文信息或工具提示可以帮助用户理解复杂的功能或数据。对话框可以提供深入的解释、图表或帮助文档。

4. **图像和媒体预览**：当用户需要查看媒体时，可以使用`Dialog`显示更大预览或图库，例如与以下内容互动时：
  >- 图像
  >- 视频
  >- 其他媒体

## 背景和模糊效果 {#backdrop-and-blur}

通过启用webforJ `Dialog`组件的背景属性，将在`Dialog`后面显示一个背景。此外，当启用时，对话框的模糊属性将模糊`Dialog`的背景。修改这些设置可以通过提供深度、视觉层次和上下文来帮助用户，从而为用户提供更清晰的指导。

<ComponentDemo 
path='/webforj/dialogbackdropblur?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogBackdropBlurView.java'
height = '300px'
/>

## 打开和关闭`Dialog` {#opening-and-closing-the-dialog}

创建新的`Dialog`对象后，使用`open()`方法显示对话框。然后，`Dialog`组件可以通过以下一种行动关闭：
- 使用`close()`方法
- 按下<kbd>ESC</kbd>键
- 点击`Dialog`外部

开发者可以选择通过`setCancelOnEscKey()`和`setCancelOnOutsideClick()`来确定哪些交互可以关闭`Dialog`。此外，`setClosable()`方法可以防止或允许按下<kbd>ESC</kbd>键和点击`Dialog`外部以关闭组件。

<ComponentDemo 
path='/webforj/dialogclose?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogCloseView.java'
height = '350px'
/>

## 自动聚焦 {#auto-focus}

启用时，自动聚焦将自动使对话框内第一个可聚焦的元素获得焦点。这有助于引导用户的注意力，并可以通过`setAutoFocus()`方法进行自定义。

<ComponentDemo 
path='/webforj/dialogautofocus?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogAutoFocusView.java'
height = '350px'
/>

## 可拖动 {#draggable}

`Dialog`具有内置的可拖动功能，允许用户通过点击和拖动来重新定位`Dialog`窗口。可以通过其内的任一字段修改`Dialog`的位置：标题、内容或页脚。

### 向边缘对齐 {#snap-to-edge}
还可以校准此行为，以向屏幕边缘对齐，这意味着`Dialog`将在其拖放结束时自动与显示器的边缘对齐。可以通过`setSnapToEdge()`方法改变此对齐方式。`setSnapThreshold()`接受一个像素值，该值设置`Dialog`距离屏幕边缘多少像素时将自动对齐。

<ComponentDemo 
path='/webforj/dialogdraggable?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogDraggableView.java'
height = '350px'
/>

## 定位 {#positioning}

对话框的位置可以使用内置的`setPosx()`和`setPosy()`方法进行调整。这些方法接受一个字符串参数，该参数可以表示任何适用的CSS长度单位，例如像素或视口高度/宽度。有关这些测量单位的列表[可以在此链接中找到](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units#numbers_lengths_and_percentages)。

<ComponentDemo 
path='/webforj/dialogpositioning?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogPositioningView.java'
height = '350px'
/>

### 垂直对齐 {#vertical-alignment}

除了手动分配对话框的X和Y位置外，还可以使用对话框内置的枚举类对`Dialog`进行对齐。有三个可能的值，`TOP`、`CENTER`和`BOTTOM`，每一个都可以与`setAlignment()`方法一起使用。

<ComponentDemo 
path='/webforj/dialogalignments?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogAlignmentsView.java'
height = '550px'
/>

### 全屏和断点 {#full-screen-and-breakpoints}

可以将`Dialog`设置为进入全屏模式。启用全屏时，`Dialog`无法移动或定位。可以通过`Dialog`的断点属性来操作此模式。断点是一个媒体查询，当`Dialog`将自动切换到全屏模式时将适用。当查询匹配时，`Dialog`将切换到全屏状态，否则保持其位置。

## 样式 {#styling}

### 主题 {#themes}

`Dialog`组件附带<JavadocLink type="foundation" location="com/webforj/component/dialog/Dialog.Theme.html">7种离散主题</JavadocLink>，无需使用CSS即可快速样式化。这些主题是可以应用于按钮的预定义样式，以改变它们的外观和视觉呈现。它们提供了一种快速且一致的方式来定制应用程序中按钮的外观。

虽然每种不同主题有很多用例，但一些示例用途包括：

  - **危险**：具有严重后果的操作，例如清除填写过的信息或永久删除帐户/数据，是使用危险主题对话框的好用例。
  - **默认**：默认主题适用于应用程序中不需要特殊关注且通用的操作，例如切换设置。
  - **主要**：该主题适合用作页面上的主要“号召行动”，例如注册、保存更改或继续到另一个页面。
  - **成功**：成功主题的对话框很好地可视化了应用程序中元素的成功完成，例如提交表单或完成注册流程。一旦成功执行操作，可以通过编程方式应用成功主题。
  - **警告**：警告对话框有助于向用户表示他们即将执行潜在的风险操作，例如在离开具有未保存更改的页面时。这些操作通常小于会使用危险主题的操作。
  - **灰色**：适用于微小操作，例如次要设置或更辅助手的操作而不属于主要功能。
  - **信息**：信息主题是提供澄清的额外信息给用户的好选择。

<ComponentDemo 
path='/webforj/dialogthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogThemesView.java'
height = '500px'
/>

<TableBuilder name="Dialog" />
