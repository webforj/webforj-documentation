---
title: Button
sidebar_position: 15
_i18n_hash: 0282098a1b80b4d494409d4f416caa5d
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-button" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/button/Button" top='true'/>

`Button` 组件是应用程序开发中的基本用户界面元素，用于创建可交互的元素，当用户单击或激活时触发动作或事件。它作为一个可点击的元素，用户可以与之交互以在应用程序或网站内执行各种操作。

`Button` 组件的主要目的是为用户提供清晰且直观的行动呼吁，指导他们执行特定任务，例如提交表单、导航到另一个页面、触发一个功能或启动一个过程。按钮对增强用户交互、改善可访问性和创建更具吸引力的用户体验至关重要。

<ComponentDemo 
path='/webforj/button?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonView.java'
height='300px'
/>

<!-- tabs={['ButtonDemo.java', 'demo_styles.css']} -->

## 用法 {#usages}

`Button` 类是一个多功能组件，通常在需要触发用户交互和动作的各种情况下使用。以下是一些您可能在应用程序中需要按钮的典型场景：

1. **表单提交**：按钮通常用于提交表单数据。例如，在一个应用程序中，您可以使用：

  > - “提交”按钮将数据发送到服务器
  > - “清除”按钮以删除表单中已存在的任何信息

2. **用户操作**：按钮用于允许用户在应用程序中执行特定操作。例如，您可以拥有一个标签为：

  > - “删除”以启动选定项目的删除
  > - “保存”以保存对文档或页面所做的更改。

3. **确认对话框**：按钮通常包含在为各种目的而构建的 [`Dialog`](../components/dialog) 组件中，提供供用户确认或取消一个操作的选项，或在您使用的 [`Dialog`](../components/dialog) 中内置的任何其他功能。

4. **交互触发器**：按钮可以作为应用程序内交互或事件的触发器。用户通过单击按钮，可以启动复杂的操作或触发动画、刷新内容或更新显示。

5. **导航**：按钮可以用于导航目的，例如在应用程序中不同部分或页面之间移动。导航按钮可以包括：

  > - “下一个” - 将用户带到当前应用程序或页面的下一个页面或部分。
  > - “上一个” - 将用户返回到他们所在应用程序或部分的上一个页面。
  > - “返回” 将用户带回到他们所在的应用程序或页面的第一部分。

## 向按钮添加图标 <DocChip chip='since' label='24.11' /> {#adding-icons-to-buttons-docchip-chipsince-label2411-}

将图标合并到按钮中可以大大改善您应用程序的设计，使用户能够快速识别屏幕上可操作的项目。 [`Icon`](./icon.md) 组件提供了丰富的图标选择供您选择。

通过使用 `setPrefixComponent()` 和 `setSuffixComponent()` 方法，您可以灵活地确定 `Icon` 应该出现在按钮文本之前或之后。另外，`setIcon()` 方法可用于在文本后但在按钮的 `suffix` 插槽之前添加 `Icon`。

<!-- Add this back in once Icon has been merged -->
<!-- Refer to the [Icon component](../components/icon) page for more information on configuring and customizing icons. -->

:::tip
默认情况下，`Icon` 继承按钮的主题和扩展。
:::

以下是按钮的示例，文本位于左侧和右侧，以及仅包含图标的按钮：

<ComponentDemo 
path='/webforj/buttonicon?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonIconView.java'
height="200px"
/>

### 名称 {#names}

`Button` 组件利用命名，用于可访问性。当没有明确设置名称时，将使用 `Button` 的标签。然而，一些图标没有标签，只显示非文本元素，例如图标。在这种情况下，使用 `setName()` 方法，确保创建的 `Button` 组件符合可访问性标准将是明智之举。

## 禁用按钮 {#disabling-a-button}

按钮组件，像许多其他组件一样，可以被禁用，以向用户传达某个操作尚不可用或不再可用。禁用按钮将降低按钮的透明度，并适用于所有按钮主题和扩展。

<ComponentDemo 
path='/webforj/buttondisable?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonDisableView.java'
/>

<br />

可以在代码中的任何时候通过使用 <JavadocLink type="foundation" location="com/webforj/component/HasEnable" code='true'>setEnabled(boolean enabled)</JavadocLink> 函数来禁用按钮。为了方便起见，按钮还可以在单击时被禁用，使用内置的 <JavadocLink type="foundation" location="com/webforj/component/button/Button" code='true' suffix='#setDisableOnClick(java.lang.Boolean)'>setDisabledOnClick(boolean enabled)</JavadocLink> 函数。

在某些应用程序中，单击按钮会触发长时间运行的操作。在大多数情况下，应用程序可能希望确保只处理单个单击。这在高延迟环境中可能会出现问题，因为用户在应用程序有机会开始处理响应操作之前，可能会多次单击按钮。

:::tip
单击时禁用按钮不仅有助于优化操作的处理，还防止开发人员需要自己实现此行为，因为该方法已被优化以减少往返通信。
:::

## 样式 {#styling}

### 主题 {#themes}

`Button` 组件提供了 <JavadocLink type="foundation" location="com/webforj/component/button/ButtonTheme">14种离散主题</JavadocLink>，无须使用 CSS 便可快速样式化。这些主题是预定义的样式，可以应用于按钮，以改变其外观和视觉呈现。它们为整个应用程序中的按钮提供了一种快速一致的自定义外观的方法。

虽然各种主题都有许多用例，但一些示例用法为：

  - **危险**：最适合严重后果的操作，例如清除已填写的信息或永久删除帐户/数据。
  - **默认**：适合整个应用程序中不需要特别注意和普遍的操作，例如切换设置。
  - **主要**：适合作为页面上的主要“行动呼吁”，例如注册、保存更改或继续到另一个页面。
  - **成功**：非常适合可视化应用程序中元素成功完成的状态，例如表单的提交或注册过程的完成。成功主题可以在成功完成操作后以编程方式应用。
  - **警告**：指示用户即将执行潜在风险操作的情况，例如导航离开带有未保存更改的页面。这些操作通常没有使用危险主题的影响那么大。
  - **灰色**：适合于微妙的操作，例如次要设置或补充到页面的操作，而不是主要功能的一部分。
  - **信息**：适合为用户提供额外的澄清信息。

下面展示的是应用了每一个支持主题的示例按钮：<br/>

<ComponentDemo 
path='/webforj/buttonthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonThemesView.java'
cssURL='/css/button/buttonThemes.css'
height='175px'
/>

### 扩展 {#expanses}
以下 <JavadocLink type="foundation" location="com/webforj/component/Expanse">扩展值</JavadocLink> 允许快速样式化而无需使用 CSS。这允许操控按钮的尺寸，而无需通过任何样式进行明确设置。除了简化样式，它还帮助在应用程序中创建和维护统一性。默认的 `Button` 扩展为 `Expanse.MEDIUM`。

不同的尺寸往往适合不同的用途：
  - **较大** 的扩展值适合于那些应引起注意的按钮，强调功能或是应用程序或页面的核心功能。
  - **中等** 扩展按钮，即默认大小，应作为“标准大小”使用，当按钮的行为不比其他类似组件更重要或不重要时。
  - **较小** 的扩展值应用于在应用程序中没有核心行为的按钮，并且承担更补充或工具角色，而不是在用户交互中发挥重要作用。这包括仅用于工具目的的图标 `Button` 组件。

以下是 `Button` 组件支持的各种扩展：<br/>

<ComponentDemo 
path='/webforj/buttonexpanses?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonExpansesView.java'
height='200px'
/>

<TableBuilder name="Button" />

## 最佳实践 {#best-practices}

为了确保在使用 `Button` 组件时获得最佳用户体验，请考虑以下最佳实践：

1. **适当的文本**：为 `Button` 组件内部的文本使用清晰简明的文本，以提供其目的的清晰指示。

2. **适当的视觉样式**：考虑 `Button` 的视觉样式和主题，以确保与应用程序设计的一致性。例如：
  > - “取消” `Button` 组件的样式应使用适当的主题或 CSS 样式，以确保用户能确认他们想要取消操作。
  > - “确认” `Button` 的样式应与“取消”按钮不同，但同样需要突出，以确保用户知道这是一个特殊操作。

3. **高效的事件处理**：高效处理 `Button` 事件，并向用户提供适当的反馈。请参阅 [事件](../building-ui/events) 以回顾有效的事件添加行为。

4. **测试和可访问性**：在不同场景下测试按钮的行为，例如在按钮被禁用或获得焦点时，确保用户体验顺畅。
遵循可访问性指南，使所有用户均能使用 `Button`，包括依赖辅助技术的用户。
