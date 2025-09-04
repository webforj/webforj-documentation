---
title: Dialog
sidebar_position: 30
_i18n_hash: e0d440fddf7ad6be7a78958ae1ddde1a
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-dialog" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="dialog" location="com/webforj/component/dialog/Dialog" top='true'/>

webforJ 对话框组件旨在允许开发者快速且轻松地在其应用程序中显示对话框，例如登录菜单或信息框。

该组件由三个部分构成，每个部分都是 `Panel` 组件：**头部**、**内容**和**底部**。

<ComponentDemo 
path='/webforj/dialogsections?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogSectionsView.java'
height = '225px'
/>

## 用法 {#usages}

1. **用户反馈和确认**：`Dialog` 组件常用于提供反馈或询问用户确认。它们可以向用户展示各种重要的反馈信息，例如：

  >- 成功消息
  >- 错误警报
  >- 确认提交

2. **表单输入和编辑**：您可以使用对话框收集用户输入或允许他们以受控和专注的方式编辑信息。例如，可以弹出对话框以编辑用户个人资料的详细信息或完成多步骤表单。

3. **上下文信息**：在对话框中显示额外的上下文信息或工具提示可以帮助用户理解复杂的功能或数据。对话框可以提供深入的解释、图表或帮助文档。

4. **图像和媒体预览**：当用户需要查看媒体内容时，可以使用 `Dialog` 显示更大的预览或图库，例如在与下列内容互动时：
  >- 图像
  >- 视频
  >- 其他媒体

## 背景和模糊 {#backdrop-and-blur}

通过启用 webforJ `Dialog` 组件的背景属性，将在 `Dialog` 后显示背景。此外，当启用时，Dialog 的模糊属性将模糊 `Dialog` 的背景。修改这些设置可以通过提供深度、视觉层次和上下文来帮助用户，从而为用户提供更清晰的指导。

<ComponentDemo 
path='/webforj/dialogbackdropblur?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogBackdropBlurView.java'
height = '300px'
/>

## 打开和关闭 `Dialog` {#opening-and-closing-the-dialog}

创建新的 `Dialog` 对象后，使用 `open()` 方法显示对话框。然后，`Dialog` 组件可以通过以下任一方式关闭：
- 使用 `close()` 方法
- 按下 <kbd>ESC</kbd> 键
- 点击 `Dialog` 外部

开发者可以选择哪些交互可以关闭 `Dialog`，使用 `setCancelOnEscKey()` 和 `setCancelOnOutsideClick()`。此外，`setClosable()` 方法可以防止或允许按下 <kbd>ESC</kbd> 键和点击 `Dialog` 外部来关闭该组件。

<ComponentDemo 
path='/webforj/dialogclose?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogCloseView.java'
height = '350px'
/>

## 自动聚焦 {#auto-focus}

启用时，自动聚焦将自动将焦点给予对话框内第一个可以聚焦的元素。这对于帮助引导用户的注意力非常有用，并可以通过 `setAutoFocus()` 方法进行自定义。

<ComponentDemo 
path='/webforj/dialogautofocus?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogAutoFocusView.java'
height = '350px'
/>

## 可拖动 {#draggable}

`Dialog` 具备内置的可拖动功能，允许用户通过点击并拖动来移动 `Dialog` 窗口。`Dialog` 的位置可以从其中任何区域进行操作：头部、内容或底部。

### 向边缘吸附 {#snap-to-edge}

还可以校准此行为，使其向屏幕边缘吸附，这意味着 `Dialog` 将在拖放释放时自动与显示器的边缘对齐。通过 `setSnapToEdge()` 方法可以更改吸附设置。`setSnapThreshold()` 接受一个像素数值，用于设置 `Dialog` 在自动吸附到边缘之前应离屏幕边缘的距离。

<ComponentDemo 
path='/webforj/dialogdraggable?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogDraggableView.java'
height = '350px'
/>

## 定位 {#positioning}

可以使用内置的 `setPosx()` 和 `setPosy()` 方法操作对话框的位置。这些方法接受一个字符串参数，可以表示任何适用的 CSS 长度单位，例如像素或视图高度/宽度。有关这些测量的列表[请查看此链接](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units#numbers_lengths_and_percentages)。

<ComponentDemo 
path='/webforj/dialogpositioning?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogPositioningView.java'
height = '350px'
/>

### 垂直对齐 {#vertical-alignment}

除了手动设置对话框的 X 和 Y 位置外，还可以使用对话框内置的枚举类对 `Dialog` 进行对齐。有三种可能的值，`TOP`、`CENTER` 和 `BOTTOM`，每个值都可以与 `setAlignment()` 方法一起使用。

<ComponentDemo 
path='/webforj/dialogalignments?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogAlignmentsView.java'
height = '550px'
/>

### 全屏和断点 {#full-screen-and-breakpoints}

可以设置 `Dialog` 进入全屏模式。当启用全屏时，`Dialog` 不能被移动或定位。此模式可以通过 `Dialog` 的断点属性进行操作。断点是一个媒体查询，当满足条件时，`Dialog` 将自动切换到全屏模式。当查询匹配时，`Dialog` 变为全屏，否则将保持定位。

## 样式 {#styling}

### 主题 {#themes}

`Dialog` 组件内置了 <JavadocLink type="foundation" location="com/webforj/component/dialog/Dialog.Theme.html">7 种独特主题</JavadocLink>，可快速样式化而无需使用 CSS。这些主题是预定义的样式，可以应用于按钮以更改其外观和视觉表现。它们提供了一种快速而一致的方式来定制应用程序中按钮的外观。

尽管各种主题有许多用例，但一些例子包括：

  - **危险**：具有严重后果的操作，例如清除已填写的信息或永久删除账户/数据，代表了适合使用危险主题对话框的良好用例。
  - **默认**：默认主题适用于应用程序中不需要特别关注且通用的操作，例如切换设置。
  - **主要**：此主题适合作为页面上的主要“号召性用语”，例如注册、保存更改或继续到另一个页面。
  - **成功**：成功主题的对话框非常适合可视化应用程序中元素的成功完成，例如表单提交或注册过程的完成。成功主题可以在成功操作完成后以编程方式应用。
  - **警告**：警告对话框用于指示用户即将执行可能存在风险的操作，例如在离开未保存更改的页面时。这些操作通常不如使用危险主题的操作影响大。
  - **灰色**：适用于微小操作，例如次要设置或更补充页面的操作，而不是主要功能的一部分。
  - **信息**：信息主题是向用户提供澄清或附加信息的良好选择。

<ComponentDemo 
path='/webforj/dialogthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogThemesView.java'
height = '500px'
/>

<TableBuilder name="Dialog" />
