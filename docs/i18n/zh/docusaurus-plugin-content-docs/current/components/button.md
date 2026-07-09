---
title: Button
sidebar_position: 15
description: >-
  Trigger click actions in webforJ with the Button component, including themes,
  expanses, prefix and suffix icons, and disabled state.
_i18n_hash: 31fa93b60126cba6b26198da5a5c15b5
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-button" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/button/Button" top='true'/>

一个 `Button` 是一个可点击的元素，当按下时会触发一个动作。它可以显示文本、图标或两者的组合。按钮支持多种视觉主题和尺寸，并可以被禁用以防止在长时间运行的操作过程中交互，或者在某些条件未满足时阻止交互。

<!-- INTRO_END -->

## 用法 {#usages}

`Button` 类是一个多功能组件，通常用于需要触发用户交互和动作的各种场合。以下是一些在应用程序中可能需要按钮的典型场景：

1. **表单提交**：按钮通常用于提交表单数据。例如，在应用程序中，您可以使用：

  > - 一个 "提交" 按钮将数据发送到服务器
  > - 一个 "清除" 按钮删除表单中已经存在的信息


2. **用户操作**：按钮用于允许用户在应用程序内执行特定操作。例如，您可以有一个标记为：

  > - "删除" 以启动选定项目的删除
  > - "保存" 以保存对文档或页面进行的更改。

3. **确认对话框**：按钮通常包含在 [`Dialog`](../components/dialog) 组件中，旨在为用户提供确认或取消操作的选项，以及您正在使用的 [`Dialog`](../components/dialog) 中内置的任何其他功能。

4. **交互触发器**：按钮可以作为应用程序内交互或事件的触发器。通过单击按钮，用户可以启动复杂的操作或触发动画、刷新内容或更新显示。

5. **导航**：按钮可以用于导航目的，例如在应用程序内不同部分或页面之间移动。导航按钮可以包括：

  > - "下一步" - 将用户带到当前应用程序或页面的下一页或部分。
  > - "上一步" - 将用户返回到应用程序或所在部分的上一页。
  > - "返回" 将用户返回到应用程序或页面的第一部分。

以下示例演示了用于表单提交和清除输入的按钮：

<ComponentDemo
path='/webforj/button'
files={['src/main/java/com/webforj/samples/views/button/ButtonView.java']}
height='300px'
/>

## 向按钮添加图标 <DocChip chip='since' label='24.11' /> {#adding-icons-to-buttons-docchip-chipsince-label2411-}

将图标嵌入按钮中可以极大改善您应用程序的设计，使用户能够快速识别屏幕上的可操作项目。[`Icon`](./icon.md) 组件提供了广泛的图标供选择。

通过利用 `setPrefixComponent()` 和 `setSuffixComponent()` 方法，您可以灵活地确定图标应该出现在按钮文本之前还是之后。或者，`setIcon()` 方法可以用于在文本之后添加图标，但在按钮的 `suffix` 插槽之前。

<!-- Add this back in once Icon has been merged -->
<!-- Refer to the [Icon component](../components/icon) page for more information on configuring and customizing icons. -->

:::tip
默认情况下，图标继承按钮的主题和面积。
:::

以下是图标位于左侧和右侧的按钮示例，以及只有图标的按钮：

<ComponentDemo
path='/webforj/buttonicon'
files={['src/main/java/com/webforj/samples/views/button/ButtonIconView.java']}
height='200px'
/>

### 名称 {#names}

`Button` 组件利用命名，供无障碍访问使用。当没有明确设置名称时，`Button` 的标签将被用作名称。然而，一些图标没有标签，只显示非文本元素，例如图标。在这种情况下，使用 `setName()` 方法确保创建的 `Button` 组件符合无障碍标准是很方便的。

## 禁用按钮 {#disabling-a-button}

按钮组件，如同许多其他组件，可以被禁用，以向用户传达某个操作尚不可用或不再可用的状态。一个禁用的按钮会降低按钮的透明度，并适用于所有按钮主题和面积。

<ComponentDemo
path='/webforj/buttondisable'
files={['src/main/java/com/webforj/samples/views/button/ButtonDisableView.java']}
/>

通过使用 <JavadocLink type="foundation" location="com/webforj/component/HasEnable" code='true'>setEnabled(boolean enabled)</JavadocLink> 函数，您可以随时在代码中禁用按钮。为了更方便，当单击时也可以使用内置的 <JavadocLink type="foundation" location="com/webforj/component/button/Button" code='true' suffix='#setDisableOnClick(java.lang.Boolean)'>setDisabledOnClick(boolean enabled)</JavadocLink> 函数禁用按钮。

在某些情况下，单击按钮会触发一个长时间运行的操作。禁用按钮直到您的应用处理完成此操作，可以防止用户多次点击按钮，尤其是在高延迟环境中。

:::tip
单击时禁用不仅有助于优化操作的处理，还防止开发人员需要自行实现此行为，因为此方法已优化以减少往返通信。
:::

## 样式 {#styling}

### 主题 {#themes}

`Button` 组件内置了 <JavadocLink type="foundation" location="com/webforj/component/button/ButtonTheme">14种离散主题</JavadocLink>，以便快速样式而无需使用 CSS。这些主题是预定义的样式，可以应用于按钮以改变它们的外观和视觉表现。它们提供了一种快速且一致的方法来定制应用程序中按钮的外观。

虽然每种不同主题都有许多用例，但一些示例用法包括：

  - **危险**：最适合具有严重后果的操作，例如清除已填写的信息或永久删除帐户/数据。
  - **默认**：适合应用程序中不需要特殊关注且更为一般性的操作，例如切换设置。
  - **主要**：适合作为页面上的主要 "号召性用语"，例如注册、保存更改或继续到另一个页面。
  - **成功**：非常适合在应用程序中可视化成功完成的元素，例如表单提交或注册过程的完成。成功主题可以在成功操作完成后程序matically应用。
  - **警告**：有助于指示用户即将执行可能具有风险的操作，例如离开未保存更改的页面。这些操作的影响通常小于使用危险主题的操作。
  - **灰色**：适合于细微操作，例如次要设置或作为页面的补充操作，而不是应用程序的主要功能的一部分。
  - **信息**：适合于向用户提供额外的澄清信息。

以下是每个支持主题应用的按钮示例： <br/>

<ComponentDemo
path='/webforj/buttonthemes'
files={['src/main/java/com/webforj/samples/views/button/ButtonThemesView.java']}
height='175px'
/>

### 面积 {#expanses}
以下 <JavadocLink type="foundation" location="com/webforj/component/Expanse">面积值</JavadocLink> 允许快速样式，而无需使用 CSS。这允许在不明确设置样式的情况下操作按钮的尺寸。除了简化样式外，它还有助于在您的应用程序中创建和维护一致性。默认的 `Button` 面积是 `Expanse.MEDIUM`。

不同的尺寸通常适用于不同的用途：
  - **较大** 的面积值适合于应吸引注意、强调功能或对应用程序或页面的核心功能至关重要的按钮。
  - **中等** 面积按钮，默认大小，应为按钮的标准大小。这些按钮的功能不应比类似组件更重要或更不重要。
  - **较小** 的面积值应适用于在应用程序中没有重要行为的按钮，并充当更补充或实用的角色，而不是在用户交互中发挥重要作用。这包括仅用于图标的 `Button` 组件，以实现实用目的。

以下是 `Button` 组件支持的各种面积： <br/>

<ComponentDemo
path='/webforj/buttonexpanses'
files={['src/main/java/com/webforj/samples/views/button/ButtonExpansesView.java']}
height='200px'
/>

<TableBuilder name="Button" />

## 最佳实践 {#best-practices}

为了确保在使用 `Button` 组件时获得最佳用户体验，请考虑以下最佳实践：

1. **正确的文本**：使用明确和简洁的文本在您的 `Button` 组件中提供其目的的清晰指示。

2. **适当的视觉样式**：考虑按钮的视觉样式和主题，以确保与您应用程序的设计一致。例如：
  > - 一个 "取消" `Button` 组件应使用适当的主题或 CSS 样式进行样式处理，以确保用户明确其想要取消操作
  > - 一个 "确认" `Button` 将与 "取消" 按钮样式不同，但也会明显突出，以确保用户知道这是一个特殊操作。

3. **高效的事件处理**：高效处理 `Button` 事件并向用户提供适当的反馈。请参阅 [事件](../building-ui/events) 以查看高效事件添加行为。

4. **测试和可及性**：在不同场景中测试按钮的行为，例如在禁用或获得焦点时，以确保顺畅的用户体验。
遵循可及性指南，使 `Button` 可供所有用户使用，包括依赖辅助技术的用户。
