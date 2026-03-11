---
title: Button
sidebar_position: 15
_i18n_hash: 7df385d72b74249e5689c31575568ae8
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-button" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/button/Button" top='true'/>

一个 `Button` 是一个可点击的元素，当按下时会触发一个动作。它可以显示文本、图标或两者的组合。按钮支持多种视觉主题和尺寸，并且可以被禁用，以防止在长时间运行的操作中进行交互或在某些条件未满足时进行交互。

<!-- INTRO_END -->

## 用法 {#usages}

`Button` 类是一个多功能组件，通常在需要触发用户交互和操作的各种情况下使用。以下是一些您可能在应用程序中需要按钮的典型场景：

1. **表单提交**：按钮通常用于提交表单数据。例如，在应用程序中，您可以使用：

  > - “提交”按钮将数据发送到服务器
  > - “清除”按钮以移除表单中已输入的信息


2. **用户操作**：按钮用于允许用户在应用程序内执行特定操作。例如，您可以有一个标签为：

  > - “删除”的按钮，用于开始删除选定的项目
  > - “保存”的按钮，保存对文档或页面所做的更改。

3. **确认对话框**：按钮通常包含在 [Dialog](../components/dialog) 组件中，为用户提供确认或取消某个操作的选项，或者提供您使用的 [`Dialog`](../components/dialog) 中的任何其他功能。

4. **交互触发器**：按钮可以作为应用程序内交互或事件的触发器。用户通过单击按钮可以启动复杂的操作或触发动画、刷新内容或更新显示。

5. **导航**：按钮可用于导航目的，例如在应用程序内的不同部分或页面之间移动。用于导航的按钮可能包括：

  > - “下一步” - 带用户进入当前应用程序或页面的下一页或部分。
  > - “上一步” - 将用户返回到应用程序或所处部分的上一页。
  > - “后退” - 返回用户到他们所处应用程序或页面的第一部分。
  
以下示例演示用于表单提交和清除输入的按钮：

<ComponentDemo 
path='/webforj/button?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonView.java'
height='300px'
/>

## 向按钮添加图标 <DocChip chip='since' label='24.11' /> {#adding-icons-to-buttons-docchip-chipsince-label2411-}

在按钮中集成图标可以大大改善您应用程序的设计，使用户能够快速识别屏幕上的可操作项。[`Icon`](./icon.md) 组件提供了广泛的图标可供选择。

通过使用 `setPrefixComponent()` 和 `setSuffixComponent()` 方法，您可以灵活地确定 `Icon` 应该在按钮文本之前还是之后出现。或者，您可以使用 `setIcon()` 方法在文本之后但在按钮的 `suffix` 槽之前添加 `Icon`。

<!-- 在 Icon 合并后再添加此内容 -->
<!-- 有关配置和自定义图标的更多信息，请参阅 [Icon 组件](../components/icon) 页。 -->

:::tip
默认情况下，`Icon` 继承按钮的主题和大小。
:::

下面是按钮左侧和右侧带文本的例子，以及只有图标的按钮：

<ComponentDemo 
path='/webforj/buttonicon?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonIconView.java'
height="200px"
/>

### 名称 {#names}

`Button` 组件使用命名，以用于可访问性。当名称未明确设置时，将使用 `Button` 的标签。 然而，某些图标没有标签，仅显示非文本元素，例如图标。在这种情况下，使用 `setName()` 方法确保创建的 `Button` 组件符合可访问性标准是很方便的。

## 禁用按钮 {#disabling-a-button}

按钮组件（和许多其他组件一样）可以被禁用，以向用户传达某个操作当前不可用或者不再可用。禁用的按钮将降低按钮的透明度，并且适用于所有按钮主题和大小。

<ComponentDemo 
path='/webforj/buttondisable?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonDisableView.java'
/>

可以通过使用 <JavadocLink type="foundation" location="com/webforj/component/HasEnable" code='true'>setEnabled(boolean enabled)</JavadocLink> 函数在代码中的任何时候禁用按钮。为了方便起见，按钮在单击时也可以使用内置的 <JavadocLink type="foundation" location="com/webforj/component/button/Button" code='true' suffix='#setDisableOnClick(java.lang.Boolean)'>setDisabledOnClick(boolean enabled)</JavadocLink> 函数被禁用。

在某些情况下，单击按钮会触发长时间运行的操作。在您的应用程序处理该操作之前禁用按钮，可以防止用户多次单击按钮，尤其是在高延迟的环境中。

:::tip
单击禁用不仅有助于优化操作的处理，还防止开发人员需要自己实现此行为，因为此方法已被优化以减少往返通信。
:::

## 样式 {#styling}

### 主题 {#themes}

`Button` 组件内置了 <JavadocLink type="foundation" location="com/webforj/component/button/ButtonTheme">14 种独立主题</JavadocLink>，可实现快速样式设置而无须使用 CSS。这些主题是预定义的样式，可以应用于按钮，以改变它们的外观和视觉展示。它们提供了一种快速一致的方式，以自定义整个应用程序中按钮的外观。

虽然每种不同主题都有许多用例，但一些示例用途如下：

  - **危险**：适用于具有严重后果的操作，例如清除已填写的信息或永久删除帐户/数据。
  - **默认**：适用于整个应用程序中的不需要特殊关注和通用的操作，例如切换设置。
  - **主要**：适合作为页面上的主要“号召行动”，例如注册、保存更改或继续到另一个页面。
  - **成功**：非常适合在应用程序中可视化元素成功完成，例如表单提交或注册过程的完成。成功主题可以在成功操作完成后以编程方式应用。
  - **警告**：有助于指示用户即将执行可能存在风险的操作，例如导航离开具有未保存更改的页面。这些操作的影响通常低于使用危险主题的操作。
  - **灰色**：适合用于微小的操作，例如可以忽略的设置或者更作为页面的补充元素，而不是主要功能的一部分。
  - **信息**：适合为用户提供额外的澄清信息。

下面展示了每个支持主题应用的按钮示例： <br/>

<ComponentDemo 
path='/webforj/buttonthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonThemesView.java'
cssURL='/css/button/buttonThemes.css'
height='175px'
/>

### 尺寸 {#expanses}
以下 <JavadocLink type="foundation" location="com/webforj/component/Expanse">Expanses 值</JavadocLink> 允许快速样式设置而不使用 CSS。这使得可以操作按钮的尺寸，而无须显式使用任何样式。除了简化样式外，它还帮助创建和维护应用程序中的统一性。默认 `Button` 尺寸为 `Expanse.MEDIUM`。

不同的尺寸值通常适用于不同的用途：
  - **较大** 的尺寸值适合用于应引起注意的按钮，强调功能或是应用程序或页面核心功能的重要组成部分。
  - **中等** 尺寸按钮，即默认尺寸，应该是按钮的标准尺寸。这些按钮的功能应该与类似组件没有更重要或更少重要。
  - **较小** 尺寸值应适用于没有在应用程序中有重要行为的按钮，主要承担补充或实用角色，而不是在用户交互中发挥重要作用。这包括仅与图标一起使用的 `Button` 组件。

以下是 `Button` 组件支持的不同尺寸： <br/>

<ComponentDemo 
path='/webforj/buttonexpanses?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonExpansesView.java'
height='200px'
/>

<TableBuilder name="Button" />

## 最佳实践 {#best-practices}

为了确保在使用 `Button` 组件时提供最佳用户体验，请考虑以下最佳实践：

1. **清晰的文本**：为 `Button` 组件中的文本使用清晰简明的文字，以提供其目的的明确指示。

2. **适当的视觉样式**：考虑 `Button` 的视觉样式和主题，以确保与您应用程序的设计一致。例如：
  > - “取消”`Button` 组件应使用适当的主题或 CSS 样式进行样式设置，以确保用户确实想要取消操作
  > - “确认”`Button` 的样式应与“取消”按钮不同，但同样要突出显示，以确保用户知道这是一个特殊的操作。

3. **有效的事件处理**：高效地处理 `Button` 事件，并为用户提供适当的反馈。请参阅 [事件](../building-ui/events) 以检查有效的事件添加行为。

4. **测试与可访问性**：在不同场景下测试按钮的行为，例如当它被禁用或获取焦点时，以确保顺畅的用户体验。
遵循可访问性指导方针，以确保 `Button` 对所有用户（包括依赖辅助技术的用户）是可用的。
