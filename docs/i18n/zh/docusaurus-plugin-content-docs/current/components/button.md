---
title: Button
sidebar_position: 15
_i18n_hash: 4f84dd4c618dafe32cbeb47c7dcbbe36
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-button" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/button/Button" top='true'/>

一个 `Button` 是一个可点击的元素，当按下时触发一个动作。它可以显示文本、图标或两者的组合。按钮支持多种视觉主题和尺寸，并可以被禁用，以防止在长时间运行的操作中或当某些条件未满足时与其交互。

<!-- INTRO_END -->

## 用法 {#usages}

`Button` 类是一个多功能组件，通常用于各种需要触发用户交互和操作的场合。以下是应用程序中可能需要按钮的典型场景：

1. **表单提交**：按钮通常用于提交表单数据。例如，在应用程序中，您可以使用：

  > - 一个“提交”按钮将数据发送到服务器
  > - 一个“清除”按钮来删除表单中已经存在的信息


2. **用户操作**：按钮用于允许用户在应用程序中执行特定操作。例如，您可以有一个标记为：

  > - “删除”的按钮以启动删除选定项
  > - “保存”的按钮以保存对文档或页面所做的更改。

3. **确认对话框**：按钮通常包含在旨在提供用户确认或取消某个操作的 [`Dialog`](../components/dialog) 组件中，或者在您使用的 [`Dialog`](../components/dialog) 中构建的任何其他功能。

4. **交互触发器**：按钮可以作为应用程序中交互或事件的触发器。通过点击按钮，用户可以启动复杂的操作、触发动画、刷新内容或更新显示。

5. **导航**：按钮可以用于导航目的，例如在应用程序中在不同部分或页面之间移动。导航按钮可以包括：

  > - “下一步” - 将用户带到当前应用程序或页面的下一页或部分。
  > - “上一步” - 将用户返回到应用程序或他们所在部分的上一页。
  > - “返回” 将用户带回他们所在的应用程序或页面的第一部分。
  
以下示例演示了用于表单提交和清除输入的按钮：

<ComponentDemo
path='/webforj/button'
files={['src/main/java/com/webforj/samples/views/button/ButtonView.java']}
height='300px'
/>

## 向按钮添加图标 <DocChip chip='since' label='24.11' /> {#adding-icons-to-buttons-docchip-chipsince-label2411-}

在按钮中加入图标可以大大改善您的应用程序设计，使用户能够快速识别屏幕上的可操作项目。[`Icon`](./icon.md) 组件提供了广泛的图标供您选择。

通过利用 `setPrefixComponent()` 和 `setSuffixComponent()` 方法，您可以灵活地决定 `Icon` 应该在按钮文本的前面还是后面出现。或者，`setIcon()` 方法可用于在文本后添加 `Icon`，但在按钮的 `suffix` 插槽之前。

<!-- Add this back in once Icon has been merged -->
<!-- 请参阅 [Icon 组件](../components/icon) 页面以获取有关配置和自定义图标的更多信息。 -->

:::tip
默认情况下，`Icon` 继承按钮的主题和扩展。
:::

下面是文本在左侧和右侧的按钮示例，以及只有图标的按钮：

<ComponentDemo
path='/webforj/buttonicon'
files={['src/main/java/com/webforj/samples/views/button/ButtonIconView.java']}
height='200px'
/>

### 名称 {#names}

`Button` 组件利用命名，供无障碍访问使用。当未明确设置名称时，`Button` 的标签将用于此。然而，有些图标没有标签，仅显示非文本元素，例如图标。在这种情况下，使用 `setName()` 方法可以确保创建的 `Button` 组件符合无障碍标准。

## 禁用按钮 {#disabling-a-button}

按钮组件，像许多其他组件一样，可以被禁用，以向用户传达某个操作尚不可用或不再可用。禁用的按钮会降低按钮的透明度，且适用于所有按钮主题和扩展。

<ComponentDemo
path='/webforj/buttondisable'
files={['src/main/java/com/webforj/samples/views/button/ButtonDisableView.java']}
/>

可以随时在代码中使用 <JavadocLink type="foundation" location="com/webforj/component/HasEnable" code='true'>setEnabled(boolean enabled)</JavadocLink> 函数来禁用按钮。为了方便起见，按钮还可以在点击时使用内置的 <JavadocLink type="foundation" location="com/webforj/component/button/Button" code='true' suffix='#setDisableOnClick(java.lang.Boolean)'>setDisabledOnClick(boolean enabled)</JavadocLink> 函数禁用。

在某些情况下，点击按钮会触发长时间运行的操作。禁用按钮直到您的应用程序处理该操作，防止用户多次点击按钮，特别是在高延迟环境中。

:::tip
在点击时禁用不仅有助于优化操作的处理，还防止开发者需要自行实现此行为，因为该方法经过优化以减少往返通信。
:::

## 样式 {#styling}

### 主题 {#themes}

`Button` 组件内置 <JavadocLink type="foundation" location="com/webforj/component/button/ButtonTheme">14 种离散主题</JavadocLink>，可快速样式化而不使用 CSS。这些主题是可以应用于按钮的预定义样式，以改变其外观和视觉表现。它们提供了一种快速而一致的方式来定制应用程序中按钮的外观。

虽然各种主题有很多用例，但一些示例用法包括：

  - **危险**：最适合具有严重后果的操作，例如清除填写的信息或永久删除账户/数据。
  - **默认**：适用于整个应用程序中不需要特别关注且是普通的操作，例如切换设置。
  - **主要**：适合用于页面的主要“行动呼吁”，例如注册、保存更改或继续访问另一个页面。
  - **成功**：极好地可视化应用程序中元素的成功完成，例如表单提交或注册过程的完成。成功主题可以在成功操作完成后以编程方式应用。
  - **警告**：用于指示用户即将执行潜在风险的操作，例如在没有保存更改的情况下离开页面。这些操作通常不如使用危险主题的操作具有影响力。
  - **灰色**：适合细微操作，例如次要设置或补充页面的操作，而不是主要功能的一部分。
  - **信息**：适合向用户提供额外的澄清信息。

下面是应用了每个支持主题的按钮示例： <br/>

<ComponentDemo
path='/webforj/buttonthemes'
files={['src/main/java/com/webforj/samples/views/button/ButtonThemesView.java']}
height='175px'
/>

### 扩展值 {#expanses}
以下 <JavadocLink type="foundation" location="com/webforj/component/Expanse">扩展值</JavadocLink> 允许快速样式化而不使用 CSS。这使得在不必明确设置样式的情况下操作按钮的尺寸变得容易。此外，这还有助于创建和维护应用程序的统一性。默认的 `Button` 扩展值是 `Expanse.MEDIUM`。

不同的尺寸通常适合不同的用途：
  - **较大**的扩展值适合于应引起注意、强调功能或是应用程序或页面核心功能的重要组成部分的按钮。
  - **中等**扩展按钮（默认大小）应为按钮的标准大小。这些按钮的功能应与类似组件的功能不多也不少。
  - **较小**的扩展值应用于没有在应用程序中具有重要行为的按钮，担任更补充或实用的角色，而不是在用户交互中发挥重要作用。这包括仅与图标一起使用的 `Button` 组件，用于实用目的。

以下是为 `Button` 组件支持的各种扩展示例： <br/>

<ComponentDemo
path='/webforj/buttonexpanses'
files={['src/main/java/com/webforj/samples/views/button/ButtonExpansesView.java']}
height='200px'
/>

<TableBuilder name="Button" />

## 最佳实践 {#best-practices}

为了确保使用 `Button` 组件时获得最佳用户体验，请考虑以下最佳实践：

1. **适当的文本**：为您的 `Button` 组件中的文本使用清晰简洁的文字，以明确其目的。

2. **适当的视觉样式**：考虑按钮的视觉样式和主题，以确保与应用程序的设计一致。例如：
  > - 一个“取消”的 `Button` 组件应该使用适当的主题或 CSS 样式，以确保用户在取消操作时感到确信。
  > - 一个“确认” 的 `Button` 将与“取消”按钮有不同的样式，但同样会显眼，以确保用户知道这是一个特殊的操作。

3. **高效的事件处理**：高效地处理 `Button` 事件，并提供适当的反馈给用户。请参考 [事件](../building-ui/events) 来审查高效的事件添加行为。

4. **测试和无障碍性**：在不同情况下测试按钮的行为，例如在禁用或获得焦点时，以确保顺畅的用户体验。遵循无障碍指南，以确保按钮可供所有用户使用，包括依赖辅助技术的用户。
