---
title: Button
sidebar_position: 15
_i18n_hash: 186724659f1f66cdb6f85e7a1628def8
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-button" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/button/Button" top='true'/>

`Button` 组件是应用程序开发中用于创建交互元素的基本用户界面元素，触发点击或激活时的操作或事件。它作为一个可点击的元素，用户可以与之交互，以在应用程序或网站内执行各种操作。

`Button` 组件的主要目的是为用户提供清晰直观的行动召唤，指导他们执行特定任务，如提交表单、导航到另一页面、触发某个功能或启动某个过程。按钮对于增强用户互动、改善可访问性和创造更具吸引力的用户体验至关重要。

<ComponentDemo 
path='/webforj/button?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonView.java'
height='300px'
/>

## 用法 {#usages}

`Button` 类是一个多功能组件，常用于需要触发用户交互和操作的各种场合。以下是一些典型场景，您可能需要在应用程序中使用按钮：

1. **表单提交**: 按钮通常用于提交表单数据。例如，在应用程序中，您可以使用：

  > - 一个 "提交" 按钮将数据发送到服务器
  > - 一个 "清除" 按钮删除表单中已填写的信息

2. **用户操作**: 按钮用于允许用户在应用程序中执行特定操作。例如，您可以有一个标签为：

  > - "删除" 的按钮以启动删除所选项
  > - "保存" 的按钮以保存对文档或页面所做的更改。

3. **确认对话框**: 按钮通常包含在 [`Dialog`](../components/dialog) 组件中，用于提供用户确认或取消某个操作的选项，或任何其他内置在您当前使用的 [`Dialog`](../components/dialog) 中的功能。

4. **交互触发器**: 按钮可以作为应用程序内交互或事件的触发器。通过点击按钮，用户可以启动复杂操作或触发动画、刷新内容或更新显示。

5. **导航**: 按钮可用于导航目的，如在应用程序内或页面之间移动。用于导航的按钮可以包括：

  > - "下一步" - 将用户带到当前应用程序或页面的下一页或部分。
  > - "上一页" - 将用户返回到应用程序的上一页或他们所在部分。
  > - "返回" - 将用户返回到他们所在的应用程序或页面的第一部分。

## 向按钮添加图标 <DocChip chip='since' label='24.11' /> {#adding-icons-to-buttons-docchip-chipsince-label2411-}

在按钮中添加图标可以极大提升您的应用设计，使用户能够快速识别屏幕上的可操作项目。 [`Icon`](./icon.md) 组件提供了丰富的图标供您选择。

通过使用 `setPrefixComponent()` 和 `setSuffixComponent()` 方法，您可以灵活决定图标应该出现在按钮文本的前面还是后面。或者，`setIcon()` 方法可以用于在文本后添加图标，但在按钮的 `suffix` 插槽之前。

:::tip
默认情况下，图标继承按钮的主题和宽度。
:::

以下是文本在左右两侧的按钮示例，以及仅具有图标的按钮示例：

<ComponentDemo 
path='/webforj/buttonicon?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonIconView.java'
height="200px"
/>

### 名称 {#names}

`Button` 组件利用命名，这用于可访问性。当名称未明确设置时，将使用按钮的标签。不过，有些图标没有标签，仅显示非文本元素，如图标。在这种情况下，使用 `setName()` 方法可以确保创建的 `Button` 组件符合可访问性标准。

## 禁用按钮 {#disabling-a-button}

按钮组件和许多其他组件一样，可以被禁用，以向用户传达某个操作尚未或不再可用。禁用的按钮将降低按钮的透明度，且适用于所有按钮主题和宽度。

<ComponentDemo 
path='/webforj/buttondisable?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonDisableView.java'
/>

<br />

通过在代码中的任何位置使用 <JavadocLink type="foundation" location="com/webforj/component/HasEnable" code='true'>setEnabled(boolean enabled)</JavadocLink> 函数，您可以随时禁用按钮。为了方便，按钮也可以在点击时使用内置的 <JavadocLink type="foundation" location="com/webforj/component/button/Button" code='true' suffix='#setDisableOnClick(java.lang.Boolean)'>setDisabledOnClick(boolean enabled)</JavadocLink> 函数进行禁用。

在一些应用程序中，点击按钮会触发一个长时间运行的操作。在大多数情况下，应用程序可能会想确保仅处理一次单击。在高延迟环境中，当用户在应用程序有机会开始处理结果操作之前多次点击按钮时，这可能会成为一个问题。

:::tip
点击时禁用不仅有助于优化操作处理，还防止开发人员需要自己实现此行为，因为此方法已被优化以减少往返通信。
:::

## 样式 {#styling}

### 主题 {#themes}

`Button` 组件自带 <JavadocLink type="foundation" location="com/webforj/component/button/ButtonTheme">14 种离散主题</JavadocLink>，无需使用 CSS 即可快速样式化。这些主题是预定义的样式，可应用于按钮以改变其外观和视觉表现。它们提供了一个快速且一致的方式来定制应用程序中按钮的外观。

虽然每种主题有许多用例，但以下是一些主题的示例用途：

  - **危险**: 最适合具有严重后果的操作，例如清除已填写的信息或永久删除帐户/数据。
  - **默认**: 适用于整个应用程序中不需要特别注意和通用的操作，例如切换设置。
  - **主要**: 适合作为页面上的主要 "行动按钮"，例如注册、保存更改或继续到另一个页面。
  - **成功**: 非常适合在应用程序中可视化元素成功完成，例如提交表单或完成注册过程。成功主题可以在成功操作完成后通过编程应用。
  - **警告**: 有助于指示用户即将执行潜在风险操作，例如在未保存更改的页面上导航。这些操作通常不如使用危险主题的影响大。
  - **灰色**: 适合微妙的操作，例如小设置或更补充页面的操作，而不是主要功能的一部分。
  - **信息**: 适合向用户提供额外的澄清信息。

以下是每个支持主题应用的示例按钮： <br/>

<ComponentDemo 
path='/webforj/buttonthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonThemesView.java'
cssURL='/css/button/buttonThemes.css'
height='175px'
/>

### 尺寸 {#expanses}
以下 <JavadocLink type="foundation" location="com/webforj/component/Expanse">尺寸值</JavadocLink> 允许快速样式化而无需使用 CSS。这使得可以无需显式设置任何样式，即可操控按钮的尺寸。除了简化样式外，它还帮助创建和维护您应用中的统一性。默认的 `Button` 尺寸是 `Expanse.MEDIUM`。

不同的尺寸通常适合不同的用途：
  - **较大** 的尺寸值适合需要引起注意、强调功能或对应用程序或页面的核心功能至关重要的按钮。
  - **中等** 尺寸按钮，默认大小，适合用作 "标准尺寸"，当按钮的行为与其他类似组件并无上下之分时使用。
  - **较小** 尺寸值应用于没有在应用程序中具备核心行为的按钮，且用于更补充或实用的角色，而不是在用户交互中发挥重要作用。这包括仅用于实用目的的 `Button` 组件。

以下是支持 `Button` 组件的各种尺寸： <br/>

<ComponentDemo 
path='/webforj/buttonexpanses?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonExpansesView.java'
height='200px'
/>

<TableBuilder name="Button" />

## 最佳实践 {#best-practices}

为确保使用 `Button` 组件时的最佳用户体验，请考虑以下最佳实践：

1. **适当的文本**: 为您的 `Button` 组件内的文本使用清晰简洁的描述，以提供其用途的清楚指示。

2. **适当的视觉样式**: 考虑按钮的视觉样式和主题，以确保与您应用程序的设计一致。例如：
  > - 一个 "取消" 的 `Button` 组件应使用适当的主题或 CSS 样式进行样式设置，以确保用户明确他们想要取消操作
  > - 一个 "确认" 的 `Button` 会与 "取消" 按钮有不同的样式，但同样会突出显示，以确保用户知道这是一个特殊操作。

3. **高效的事件处理**: 高效处理 `Button` 事件，并为用户提供适当反馈。请参考 [事件](../building-ui/events) 以查看高效的事件添加行为。

4. **测试和可访问性**: 测试按钮在不同场景下的行为，例如当按钮被禁用或获得焦点时，以确保平滑的用户体验。
遵循可访问性指南，使 `Button` 对所有用户均可用，包括依赖辅助技术的用户。
