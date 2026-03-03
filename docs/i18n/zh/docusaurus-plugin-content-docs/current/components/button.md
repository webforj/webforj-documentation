---
title: Button
sidebar_position: 15
_i18n_hash: 6c3425f6d7138e710c5222d2baf84644
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-button" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/button/Button" top='true'/>

一个 `Button` 是一个可点击的元素，按下时会触发一个动作。它可以显示文本、图标或两者的组合。按钮支持多种视觉主题和大小，并且可以禁用以防止在长时间运行的操作期间进行交互，或者在某些条件未满足时进行交互。

<!-- INTRO_END -->

## 使用 {#usages}

`Button` 类是一个多用途的组件，通常用于需要触发用户交互和动作的各种场景。以下是一些您在应用程序中可能需要按钮的典型场景：

1. **表单提交**：按钮通常用于提交表单数据。例如，在一个应用程序中，您可以使用：

  > - 一个“提交”按钮将数据发送到服务器
  > - 一个“清除”按钮以移除表单中已经存在的信息


2. **用户操作**：按钮用于允许用户在应用程序中执行特定操作。例如，您可以有一个标记为：

  > - “删除”的按钮以启动删除选定项的操作
  > - “保存”的按钮以保存对文档或页面所做的更改。

3. **确认对话框**：按钮通常包含在针对各种目的构建的 [`Dialog`](../components/dialog) 组件中，为用户提供确认或取消操作的选项，或任何其他功能。

4. **交互触发器**：按钮可以作为应用程序内交互或事件的触发器。通过点击按钮，用户可以启动复杂操作、触发动画、刷新内容或更新显示。

5. **导航**：按钮可以用于导航目的，例如在应用程序内移动不同的部分或页面。导航用的按钮可能包括：

  > - “下一步” - 带用户到达当前应用程序或页面的下一页或部分。
  > - “上一步” - 返回用户到达应用程序或当前部分的上一页。
  > - “返回” 返回用户到达当前页面的第一部分。
  
以下示例演示了用于表单提交和清除输入的按钮：

<ComponentDemo 
path='/webforj/button?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonView.java'
height='300px'
/>

## 为按钮添加图标 <DocChip chip='since' label='24.11' /> {#adding-icons-to-buttons-docchip-chipsince-label2411-}

在按钮中融入图标可以极大改善您的应用程序设计，让用户快速识别屏幕上的可操作项目。 [`Icon`](./icon.md) 组件提供了广泛的图标供选择。

通过利用 `setPrefixComponent()` 和 `setSuffixComponent()` 方法，您可以灵活决定 `Icon` 是否应出现在按钮文本的前面或后面。或者，您也可以使用 `setIcon()` 方法在文本后但在按钮的 `suffix` 插槽之前添加一个 `Icon`。

<!-- 一旦 Icon 合并，请将此内容添加回 -->
<!-- 有关配置和自定义图标的更多信息，请参考 [Icon 组件](../components/icon) 页面。 -->

:::tip
默认情况下，`Icon` 继承按钮的主题和扩展。
:::

下面是文本分别位于左侧和右侧的按钮示例，以及仅有图标的按钮示例：

<ComponentDemo 
path='/webforj/buttonicon?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonIconView.java'
height="200px"
/>

### 名称 {#names}

`Button` 组件使用命名，这对于可访问性是必需的。当名称没有被明确设置时，将使用 `Button` 的标签。然而，有些图标没有标签，只显示非文本元素，例如图标。在这种情况下，使用 `setName()` 方法确保创建的 `Button` 组件符合可访问性标准是很有必要的。

## 禁用按钮 {#disabling-a-button}

按钮组件，如许多其他组件，可以被禁用，以向用户传达某个操作尚不可用或不再可用。禁用按钮会降低按钮的透明度，并且适用于所有按钮主题和扩展。

<ComponentDemo 
path='/webforj/buttondisable?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonDisableView.java'
/>

<br />

按钮在代码中的任何时刻都可以通过使用 <JavadocLink type="foundation" location="com/webforj/component/HasEnable" code='true'>setEnabled(boolean enabled)</JavadocLink> 函数进行禁用。为了方便起见，按钮在被点击时也可以使用内置的 <JavadocLink type="foundation" location="com/webforj/component/button/Button" code='true' suffix='#setDisableOnClick(java.lang.Boolean)'>setDisabledOnClick(boolean enabled)</JavadocLink> 函数进行禁用。

在某些应用程序中，点击按钮会触发一个长时间运行的操作。在大多数情况下，应用程序可能希望确保仅处理一次点击。这在高延迟环境中可能会是一个问题，用户在应用程序有机会开始处理相应的操作之前多次点击按钮。

:::tip
点击禁用不仅有助于优化操作处理，还可以防止开发人员自行实现该行为，因为此方法经过优化可减少往返通信。
:::

## 样式 {#styling}

### 主题 {#themes}

`Button` 组件内置了 <JavadocLink type="foundation" location="com/webforj/component/button/ButtonTheme">14 种离散主题</JavadocLink>，可快速进行样式设置，而无需使用CSS。这些主题是预定义样式，可以应用于按钮以改变其外观和视觉呈现。它们提供了一种快速且一致的方法，可以在整个应用程序中自定义按钮的外观。

虽然各种主题的使用场景众多，但有些示例用法包括：

  - **危险**：最佳用于后果严重的操作，例如清除已填充的信息或永久删除帐户/数据。
  - **默认**：适用于整个应用程序中的操作，无需特殊注意且较为通用，例如切换设置。
  - **主要**：适用于页面上的主要“行动呼吁”，例如注册、保存更改或继续到另一个页面。
  - **成功**：非常适合在应用程序中可视化成功完成的元素，例如表单提交或注册过程完成。成功主题在成功操作完成后可以程序性地应用。
  - **警告**：用于指示用户即将执行潜在风险的操作，例如在未保存更改的情况下离开页面。这些操作通常不如使用危险主题的操作具有影响力。
  - **灰色**：适合于细微操作，例如次要设置或更为补充页面的操作，而非主要功能的一部分。
  - **信息**：适合于向用户提供额外的说明信息。

下面是应用了每个支持主题的示例按钮： <br/>

<ComponentDemo 
path='/webforj/buttonthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonThemesView.java'
cssURL='/css/button/buttonThemes.css'
height='175px'
/>

### 扩展 {#expanses}
以下 <JavadocLink type="foundation" location="com/webforj/component/Expanse">扩展值</JavadocLink> 允许未经CSS的快速样式设置。这使得可以在不显式设置任何样式的情况下操作按钮的尺寸。除了简化样式外，它还帮助创建和维护应用程序的一致性。默认的 `Button` 扩展是 `Expanse.MEDIUM`。

不同的大小通常适合不同的用途：
  - **更大** 的扩展值适合应该引起注意、强调功能或对应用程序或页面的核心功能至关重要的按钮。
  - **中等** 扩展按钮，默认尺寸，应被用作“标准大小”，当按钮的行为与其他类似组件没有更多或更少的重要性时。
  - **较小** 扩展值应用于在应用程序中没有核心行为的按钮，而是承担补充或实用角色，而非在用户交互中扮演重要角色。这包括仅用于图标的 `Button` 组件。

下面是 `Button` 组件支持的各种扩展： <br/>

<ComponentDemo 
path='/webforj/buttonexpanses?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonExpansesView.java'
height='200px'
/>

<TableBuilder name="Button" />

## 最佳实践 {#best-practices}

为确保在使用 `Button` 组件时获得最佳用户体验，请考虑以下最佳实践：

1. **合适的文本**：使用清晰简洁的文本为 `Button` 组件内的文本提供其目的的明确指示。

2. **适当的视觉样式**：考虑 `Button` 的视觉样式和主题，以确保与应用程序的设计一致。例如：
  > - 一个“取消” `Button` 组件应使用适当的主题或CSS样式进行样式设置，以确保用户确定他们要取消操作
  > - 一个“确认” `Button` 的样式与“取消”按钮不同，但同样会脱颖而出以确保用户知道这是一个特殊操作。

3. **高效的事件处理**：高效地处理 `Button` 事件并向用户提供适当的反馈。请参考[事件](../building-ui/events)以回顾高效的事件添加行为。

4. **测试和可访问性**：在不同场景中测试按钮的行为，例如它被禁用或获得焦点时，以确保流畅的用户体验。
遵循可访问性指南，使 `Button` 可供所有用户使用，包括依赖辅助技术的用户。
