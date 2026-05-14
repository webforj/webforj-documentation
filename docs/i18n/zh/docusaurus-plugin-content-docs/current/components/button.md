---
title: Button
sidebar_position: 15
_i18n_hash: 5e0b4998a50b6c7d935c53c9c11009d6
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-button" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/button/Button" top='true'/>

一个 `Button` 是一个可点击的元素，可以在按下时触发一个动作。它可以显示文本、图标或两者的组合。按钮支持多种视觉主题和大小，并且可以被禁用，以防止在长期操作期间进行交互或在满足某些条件之前进行交互。

<!-- INTRO_END -->

## 用法 {#usages}

`Button` 类是一个多功能组件，通常用于需要触发用户交互和操作的各种场合。以下是一些典型场景，在应用程序中可能需要按钮：

1. **表单提交**：按钮通常用于提交表单数据。例如，在应用程序中，您可以使用：

  > - "提交" 按钮向服务器发送数据
  > - "清除" 按钮以删除表单中已存在的信息


2. **用户操作**：按钮用于允许用户在应用程序中执行特定操作。例如，您可以有一个标记为：

  > - "删除" 的按钮以启动选定项目的删除
  > - "保存" 的按钮以保存对文档或页面所做的更改。

3. **确认对话框**：按钮经常包含在为各种目的构建的 [`Dialog`](../components/dialog) 组件中，以提供选项供用户确认或取消某个操作，或执行您正在使用的 [`Dialog`](../components/dialog) 中构建的任何其他功能。

4. **交互触发器**：按钮可以作为应用程序内交互或事件的触发器。通过点击按钮，用户可以启动复杂的操作或触发动画、刷新内容或更新显示。

5. **导航**：按钮可以用于导航目的，例如在应用程序内不同部分或页面之间移动。导航按钮可以包括：

  > - "下一步" - 将用户带到当前应用程序或页面的下一个页面或部分。
  > - "上一步" - 将用户返回到应用程序的上一页或他们所在部分。
  > - "返回" 将用户带回到他们所在页面的第一部分。
  
以下示例演示了用于表单提交和清除输入的按钮：

<ComponentDemo 
path='/webforj/button?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonView.java'
height='300px'
/>

## 向按钮添加图标 <DocChip chip='since' label='24.11' /> {#adding-icons-to-buttons-docchip-chipsince-label2411-}

将图标纳入按钮可以大大改善应用程序的设计，使用户能够快速识别屏幕上的可操作项。 [`Icon`](./icon.md) 组件提供了广泛的图标选择。

通过利用 `setPrefixComponent()` 和 `setSuffixComponent()` 方法，您可以灵活地确定一个 `Icon` 应该出现在按钮文本之前还是之后。或者，可以使用 `setIcon()` 方法在文本之后添加 `Icon`，但在按钮的 `suffix` 插槽之前。

<!-- Add this back in once Icon has been merged -->
<!-- 有关配置和自定义图标的更多信息，请参阅 [Icon 组件](../components/icon) 页面。 -->

:::tip
默认情况下，`Icon` 继承按钮的主题和空间。
:::

以下是按钮的示例，文本位于左侧和右侧，以及一个仅带图标的按钮：

<ComponentDemo 
path='/webforj/buttonicon?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonIconView.java'
height="200px"
/>

### 名称 {#names}

`Button` 组件利用名称，用于无障碍访问。当名称未明确设置时，将使用 `Button` 的标签。然而，一些图标没有标签，仅显示非文本元素，例如图标。在这种情况下，使用 `setName()` 方法对于确保所创建的 `Button` 组件符合无障碍标准是很方便的。

## 禁用按钮 {#disabling-a-button}

按钮组件，像许多其他组件一样，可以被禁用，以向用户传达某个操作尚不可用或不再可用的信息。禁用按钮会降低按钮的不透明度，并且适用于所有按钮主题和空间。

<ComponentDemo 
path='/webforj/buttondisable?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonDisableView.java'
/>

通过使用 <JavadocLink type="foundation" location="com/webforj/component/HasEnable" code='true'>setEnabled(boolean enabled)</JavadocLink> 函数，可以随时在代码中禁用按钮。为方便起见，使用内置的 <JavadocLink type="foundation" location="com/webforj/component/button/Button" code='true' suffix='#setDisableOnClick(java.lang.Boolean)'>setDisabledOnClick(boolean enabled)</JavadocLink> 函数点击时也可以禁用按钮。

在某些情况下，点击一个按钮会触发一个长时间运行的操作。在应用程序处理该操作之前禁用按钮可以防止用户多次点击该按钮，特别是在高延迟环境中。

:::tip
在点击时禁用不仅有助于优化操作的处理，还可以防止开发人员需要独自实现此行为，因为该方法已优化以减少往返通信。
:::

## 样式 {#styling}

### 主题 {#themes}

`Button` 组件内置了 <JavadocLink type="foundation" location="com/webforj/component/button/ButtonTheme">14种独特的主题</JavadocLink>，可以快速样式化，而无需使用 CSS。这些主题是预定义的样式，可以应用于按钮以更改其外观和视觉呈现。它们提供了一种快速且一致的方式，在整个应用程序中定制按钮的外观。 

尽管对于每种不同的主题有很多用例，但一些典型用途如下：

  - **危险**：最适合具有严重后果的操作，例如清除填写的信息或永久删除账户/数据。
  - **默认**：适用于整个应用程序中不需要特别关注且常规的操作，例如切换设置。
  - **主要**：适合作为页面上的主要 "行动号召"，例如注册、保存更改或继续到另一个页面。
  - **成功**：非常适合可视化应用程序中某个元素的成功完成，例如表单的提交或注册过程的完成。一旦成功的操作完成，成功主题可以通过编程方式应用。
  - **警告**：有助于指示用户即将执行潜在风险的操作，例如在未保存更改的页面上导航。这些操作通常不如使用危险主题的操作影响大。
  - **灰色**：适用于微妙的操作，例如次要设置或对页面更为补充的操作，而不是主要功能的一部分。
  - **信息**：适合向用户提供额外的澄清信息。

下面展示了应用了每种支持主题的按钮示例： <br/>

<ComponentDemo 
path='/webforj/buttonthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonThemesView.java'
height='175px'
/>

### 空间 {#expanses}
以下 <JavadocLink type="foundation" location="com/webforj/component/Expanse">空间值</JavadocLink> 允许快速样式化，而无需使用 CSS。这使得可以在不显式设置任何样式的情况下操作按钮的尺寸。除了简化样式外，它还帮助在您的应用程序中创建和维护一致性。默认 `Button` 空间为 `Expanse.MEDIUM`。

不同的尺寸通常适用于不同的用途：
  - **更大的** 空间值适合那些应该引起注意、强调功能或对应用程序或页面的核心功能至关重要的按钮。
  - **中等** 空间按钮，即默认大小，应为按钮的标准大小。这些按钮的功能不应比类似组件更重要或更不重要。
  - **较小的** 空间值应用于没有核心行为的按钮，且其作用更为补充或实用，而不是在用户交互中发挥重要作用。这包括仅与图标一起用于实用目的的 `Button` 组件。

以下是支持的 `Button` 组件的各种空间： <br/>

<ComponentDemo 
path='/webforj/buttonexpanses?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonExpansesView.java'
height='200px'
/>

<TableBuilder name="Button" />

## 最佳实践 {#best-practices}

为了确保使用 `Button` 组件时获得最佳用户体验，请考虑以下最佳实践：

1. **适当的文本**：使用清晰简洁的文本来指示 `Button` 组件的目的。

2. **适当的视觉样式**：考虑 `Button` 的视觉样式和主题，以确保与应用程序的设计保持一致。例如：
  > - "取消" `Button` 组件应使用适当的主题或 CSS 样式，以确保用户确定他们想要取消操作
  > - "确认" `Button` 的样式应不同于 "取消" 按钮，但同样应突出，以确保用户知道这是一个特殊操作。

3. **高效的事件处理**：高效地处理 `Button` 事件，并为用户提供适当的反馈。请参考 [事件](../building-ui/events) 以查看高效的事件添加行为。

4. **测试和可访问性**：测试按钮在不同场景下的行为，例如在禁用或获得焦点时，以确保顺畅的用户体验。遵循无障碍指南以使 `Button` 可供所有用户使用，包括那些依赖辅助技术的用户。
