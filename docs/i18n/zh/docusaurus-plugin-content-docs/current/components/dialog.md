---
title: Dialog
sidebar_position: 30
_i18n_hash: 4896ea2a90b7c5155fe9ef291e69b2ad
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-dialog" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="dialog" location="com/webforj/component/dialog/Dialog" top='true'/>

`Dialog` 组件显示一个弹出窗口，覆盖当前视图，吸引用户注意聚焦内容，如表单、确认或信息消息。

<!-- INTRO_END -->

## `Dialog` 结构 {#dialog-structure}

`Dialog` 被组织为三个部分：头部、内容区域和底部。组件可以使用 `addToHeader()`、`addToContent()` 和 `addToFooter()` 方法添加到每个部分。

<ComponentDemo 
path='/webforj/dialogsections?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogSectionsView.java'
height = '225px'
/>

## 用法 {#usages}

1. **用户反馈和确认**：`Dialog` 组件常用于提供反馈或请求用户确认。它们可以向用户显示各种重要的反馈信息，例如：

  >- 成功消息 
  >- 错误警告
  >- 确认提交

2. **表单输入和编辑**：您可以使用对话框收集用户输入或允许他们以受控和专注的方式编辑信息。例如，对话框可以弹出以编辑用户个人资料详细信息或完成多步骤表单。

3. **上下文信息**：在对话框中显示额外的上下文信息或工具提示可以帮助用户理解复杂的功能或数据。对话框可以提供深入的解释、图表或帮助文档。

4. **图像和媒体预览**：当用户需要查看媒体时，`Dialog` 可以用于显示更大的预览或图库，例如在与以下内容交互时：
  >- 图像
  >- 视频
  >- 其他媒体

## 背景和模糊 {#backdrop-and-blur}

通过启用 webforJ `Dialog` 组件的背景属性，将在 `Dialog` 后面显示一个背景。此外，当启用时，Dialog 的模糊属性会模糊 `Dialog` 的背景。修改这些设置可以通过提供深度、视觉层次结构和上下文来帮助用户，从而为用户提供更清晰的指导。

<ComponentDemo 
path='/webforj/dialogbackdropblur?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogBackdropBlurView.java'
height = '300px'
/>

## 打开和关闭 `Dialog` {#opening-and-closing-the-dialog}

创建新的 `Dialog` 对象后，使用 `open()` 方法显示对话框。然后，`Dialog` 组件可以通过以下任一操作关闭：
- 使用 `close()` 方法
- 按下 <kbd>ESC</kbd> 键
- 点击 `Dialog` 之外的区域

开发人员可以选择哪个交互关闭 `Dialog`，使用 `setCancelOnEscKey()` 和 `setCancelOnOutsideClick()`。此外，`setClosable()` 方法可以防止或允许按下 <kbd>ESC</kbd> 键和点击 `Dialog` 之外的区域以关闭组件。

<ComponentDemo 
path='/webforj/dialogclose?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogCloseView.java'
height = '350px'
/>

## 自动聚焦 {#auto-focus}

启用时，自动聚焦将自动为对话框内可以聚焦的第一个元素给予焦点。这在帮助用户集中注意力方面非常有用，并通过 `setAutoFocus()` 方法进行自定义。

<ComponentDemo 
path='/webforj/dialogautofocus?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogAutoFocusView.java'
height = '350px'
/>

## 可拖动 {#draggable}

`Dialog` 具有内置的可拖动功能，允许用户通过点击和拖动来重新定位 `Dialog` 窗口。`Dialog` 的位置可以通过其内的任何字段进行操作：头部、内容或底部。

### 吸附到边缘 {#snap-to-edge}
还可以校准此行为，使其在屏幕边缘吸附，这意味着 `Dialog` 在拖放后释放时会自动与显示器的边缘对齐。通过 `setSnapToEdge()` 方法可以更改吸附行为。`setSnapThreshold()` 接受像素数量，该数量将设置 `Dialog` 在自动吸附到边缘之前应与屏幕边缘的距离。

<ComponentDemo 
path='/webforj/dialogdraggable?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogDraggableView.java'
height = '350px'
/>

## 定位 {#positioning}

对话框的位置可以使用内置的 `setPosx()` 和 `setPosy()` 方法来操作。这些方法接受一个字符串参数，可以表示任何适用的 CSS 长度单位，例如像素或视图高度/宽度。可以在 [此链接](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units#numbers_lengths_and_percentages) 中找到这些测量的列表。

<ComponentDemo 
path='/webforj/dialogpositioning?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogPositioningView.java'
height = '350px'
/>

### 垂直对齐 {#vertical-alignment}

除了手动分配对话框的 X 和 Y 位置外，还可以使用对话框的内置枚举类来对齐 `Dialog`。有三个可能的值，`TOP`、`CENTER` 和 `BOTTOM`，每个值都可以与 `setAlignment()` 方法一起使用。

<ComponentDemo 
path='/webforj/dialogalignments?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogAlignmentsView.java'
height = '550px'
/>

### 全屏和断点 {#full-screen-and-breakpoints}

`Dialog` 可以设置为进入全屏模式。当全屏启用时，`Dialog` 不能被移动或定位。此模式可以通过 `Dialog` 的断点属性进行操作。断点是一个媒体查询，当 `Dialog` 将自动切换到全屏模式时进行匹配。当查询匹配时，`Dialog` 会更改为全屏 - 否则它会被定位。

## 样式 {#styling}

### 主题 {#themes}

`Dialog` 组件内置 <JavadocLink type="foundation" location="com/webforj/component/dialog/Dialog.Theme.html">7 种独立主题</JavadocLink>，无需使用 CSS 进行快速样式设置。这些主题是可以应用于按钮的预定义样式，以改变其外观和视觉呈现。它们提供了一种快速一致的方法来定制应用程序中按钮的外观。

虽然每种主题都有许多用例，但一些示例用途包括：

  - **危险**：存在严重后果的操作，例如清除已填写的信息或永久删除账户/数据，非常适合使用危险主题的对话框。
  - **默认**：默认主题适合于应用程序中不需要特别关注的通用操作，如切换设置。
  - **主要**：该主题适合作为页面上的主要“行动号召”，例如注册、保存更改或继续到其他页面。
  - **成功**：成功主题的对话框非常适合可视化应用程序中元素的成功完成，如表单提交或注册过程完成。成功主题可以在成功操作完成后以编程方式应用。
  - **警告**：警告对话框用于通知用户他们即将执行可能的风险操作，例如在导航离开包含未保存更改的页面时。这些操作通常不如使用危险主题的操作影响大。
  - **灰色**：适合微妙的操作，例如次要设置或更补充页面的操作，而不是主要功能的一部分。
  - **信息**：信息主题是一个不错的选择，用于在推进时为用户提供清晰的附加信息。

<ComponentDemo 
path='/webforj/dialogthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogThemesView.java'
height = '500px'
/>

<TableBuilder name="Dialog" />
