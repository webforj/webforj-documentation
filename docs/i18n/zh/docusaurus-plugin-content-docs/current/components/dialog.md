---
title: Dialog
sidebar_position: 30
sidebar_class_name: new-content
_i18n_hash: 621dc045e979c7513b41ef04c6cd242a
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-dialog" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="dialog" location="com/webforj/component/dialog/Dialog" top='true'/>

`Dialog` 组件显示一个弹出窗口，覆盖当前视图，吸引用户注意集中内容，如表单、确认或信息消息。

<!-- INTRO_END -->

## `Dialog` 结构 {#dialog-structure}

`Dialog` 被组织为三个部分：标题、内容区域和页脚。可以使用 `addToHeader()`、`addToContent()` 和 `addToFooter()` 在每个部分中添加组件。

<ComponentDemo 
path='/webforj/dialogsections?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogSectionsView.java'
height = '225px'
/>

## 用法 {#usages}

1. **用户反馈和确认**：`Dialog` 组件通常用于提供反馈或请求用户确认。它们可以显示用户的重要反馈，例如：

  >- 成功消息 
  >- 错误警报
  >- 确认提交

2. **表单输入和编辑**：可以使用对话框收集用户输入或允许用户以受控和专注的方式编辑信息。例如，对话框可以弹出以编辑用户个人资料详细信息或完成多步骤表单。

3. **上下文信息**：在对话框中显示额外的上下文信息或工具提示可以帮助用户理解复杂功能或数据。对话框可以提供深入的解释、图表或帮助文档。

4. **图像和媒体预览**：当用户需要查看媒体片段时，可以使用 `Dialog` 显示更大的预览或画廊，例如在与以下内容交互时：
  >- 图像
  >- 视频
  >- 其他媒体

## 背景和模糊 {#backdrop-and-blur}

通过启用 webforJ `Dialog` 组件的背景属性，在 `Dialog` 后面将显示一个背景。此外，当启用时，对话框的模糊属性将模糊 `Dialog` 的背景。修改这些设置可以通过提供深度、视觉层次和上下文帮助用户，从而为用户提供更清晰的指导。

<ComponentDemo 
path='/webforj/dialogbackdropblur?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogBackdropBlurView.java'
height = '300px'
/>

## 打开和关闭 `Dialog` {#opening-and-closing-the-dialog}

创建新的 `Dialog` 对象后，使用 `open()` 方法显示对话框。然后，`Dialog` 组件可以通过以下操作之一关闭：
- 使用 `close()` 方法
- 按下 <kbd>ESC</kbd> 键
- 点击 `Dialog` 之外的区域

开发人员可以使用 `setCancelOnEscKey()` 和 `setCancelOnOutsideClick()` 选择关闭 `Dialog` 的交互方式。此外，`setClosable()` 方法可以防止或允许按下 <kbd>ESC</kbd> 键和点击 `Dialog` 之外的区域来关闭组件。

<ComponentDemo 
path='/webforj/dialogclose?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogCloseView.java'
height = '350px'
/>

## 自动聚焦 {#auto-focus}

启用后，自动聚焦将自动将焦点给对话框中第一个可以聚焦的元素。这在帮助引导用户注意力方面很有用，并且可以通过 `setAutoFocus()` 方法进行自定义。

<ComponentDemo 
path='/webforj/dialogautofocus?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogAutoFocusView.java'
height = '350px'
/>

## 可拖动 {#draggable}

`Dialog` 具有内置的可拖动功能，允许用户通过点击和拖动来重新定位 `Dialog` 窗口。可以从其任何字段操作 `Dialog` 的位置：标题、内容或页脚。

### 吸附到边缘 {#snap-to-edge}
还可以将此行为校准为吸附到屏幕边缘，这意味着 `Dialog` 在从拖放位置释放后会自动对齐到显示器边缘。可以通过 `setSnapToEdge()` 方法更改吸附功能。`setSnapThreshold()` 采用像素数，设置 `Dialog` 应离屏幕边缘多远时才会自动吸附。

<ComponentDemo 
path='/webforj/dialogdraggable?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogDraggableView.java'
height = '350px'
/>

## 定位 {#positioning}

可以使用内置的 `setPosx()` 和 `setPosy()` 方法操控对话框的位置。这些方法接受一个字符串参数，可以表示任何适用的 CSS 长度单位，例如像素或视口高度/宽度。可以在[此链接](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units#numbers_lengths_and_percentages)中找到这些测量值的列表。

<ComponentDemo 
path='/webforj/dialogpositioning?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogPositioningView.java'
height = '350px'
/>

### 垂直对齐 {#vertical-alignment}

除了手动分配对话框的 X 和 Y 位置外，还可以使用对话框内置的枚举类对 `Dialog` 进行对齐。可以使用 `setAlignment()` 方法采用三个可能值：`TOP`、`CENTER` 和 `BOTTOM`。

<ComponentDemo 
path='/webforj/dialogalignments?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogAlignmentsView.java'
height = '550px'
/>

### 全屏和断点 {#full-screen-and-breakpoints}

`Dialog` 可以设置为进入全屏模式。启用全屏时，`Dialog` 无法移动或定位。此模式可以通过 `Dialog` 的断点属性加以操控。断点是一个媒体查询，当 `Dialog` 会自动翻转到全屏模式时。匹配查询时，`Dialog` 变为全屏 - 否则将被定位。

### 自动宽度 <DocChip chip='since' label='26.00' /> {#auto-width}

默认情况下，`Dialog` 会拉伸以填充可用的水平空间。当通过 `setAutoWidth(true)` 启用自动宽度时，`Dialog` 将根据其内容宽度调整自身大小。

<ComponentDemo 
path='/webforj/dialogautowidth?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogAutoWidthView.java'
height = '350px'
/>

## 样式 {#styling}

### 主题 {#themes}

`Dialog` 组件带有 <JavadocLink type="foundation" location="com/webforj/component/dialog/Dialog.Theme.html">7 个独立主题</JavadocLink>，无需使用 CSS 即可快速样式化。这些主题是预定义样式，可以应用于按钮以更改其外观和视觉展示。它们提供了一种快速且一致的方式来定制应用程序中按钮的外观。

虽然每个各种主题有许多用例，但一些示例用途包括：

  - **危险**：具有严重后果的操作，例如清除填写的信息或永久删除帐户/数据，适合使用危险主题的对话框。
  - **默认**：默认主题适合用于应用程序中不需特别关注的通用操作，例如切换设置。
  - **主要**：此主题适合在页面上的主要“行动呼吁”，例如注册、保存更改或继续到另一个页面。
  - **成功**：成功主题的对话框非常适合可视化应用程序中某个元素的成功完成，例如表单的提交或注册过程的完成。成功主题可以在完成成功操作后程序性地应用。
  - **警告**：警告对话框可用于提醒用户将要执行潜在风险的操作，例如在离开未保存更改的页面时。这些操作通常不如将使用危险主题的操作那样影响深远。
  - **灰色**：适合于其它不太突出的操作，例如次要设置或对页面的附加操作，而不是主要功能的一部分。
  - **信息**：信息主题是为用户提供澄清和额外信息的好选择。

<ComponentDemo 
path='/webforj/dialogthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogThemesView.java'
height = '500px'
/>

<TableBuilder name="Dialog" />
