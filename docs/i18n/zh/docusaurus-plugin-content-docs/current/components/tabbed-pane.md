---
title: TabbedPane
slug: tabbedpane
sidebar_position: 125
description: >-
  Organize content into switchable Tab sections with the TabbedPane component,
  supporting icons and customizable tab properties.
_i18n_hash: 544ab11783e8369075f1c02aba2d8dc8
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-tabbed-pane" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/tabbedpane/TabbedPane" top='true'/>

多个内容部分可以组织在一个 `TabbedPane` 下，每个部分都与一个可点击的 `Tab` 相关联。一次只能显示一个部分，标签可以显示文本、图标或两者，以帮助用户在它们之间导航。

<!-- INTRO_END -->

## 用法 {#usages}

`TabbedPane` 类为开发者提供了一个强大的工具，用于在用户界面中组织和展示多个标签或部分。以下是一些可能在应用程序中使用 `TabbedPane` 的典型场景：

1. **文档查看器**：实现一个文档查看器，每个标签代表不同的文档或文件。用户可以轻松切换打开的文档以高效 multitasking。

2. **数据管理**：使用 `TabbedPane` 来组织数据管理任务，例如：
    - 应用中显示的不同数据集
    - 各种用户个人资料可以在单独的标签中显示
    - 用户管理系统中的不同个人资料

3. **模块选择**：`TabbedPane` 可以表示不同的模块或部分。每个标签可以封装特定模块的功能，使用户能够专注于应用程序的一个方面。

4. **任务管理**：任务管理应用可以使用 `TabbedPane` 表示各种项目或任务。每个标签可以对应于一个特定项目，允许用户分别管理和跟踪任务。

5. **程序导航**：在需要运行各种程序的应用中，`TabbedPane` 可以：
    - 作为侧边栏，允许在单个应用程序内运行不同的应用或程序，如 [`AppLayout`](./app-layout.md) 模板中所示
    - 创建一个顶部栏，也可以执行类似的目的，或表示在已选应用程序内的子应用程序

## 标签 {#tabs}

标签是可以添加到选项卡面板中以组织和切换不同内容视图的用户界面元素。

:::important
标签并不是独立组件，须与选项卡面板一起使用。这个类不是 `Component`，不应作为组件使用。
:::

### 属性 {#properties}

标签由以下属性组成，用于在 `TabbedPane` 中添加它们。这些属性具有 getter 和 setter，以便在 `TabbedPane` 中进行自定义。

1. **文本(`String`)**：作为 `TabbedPane` 中 `Tab` 的标题显示的文本。这也通过 `getTitle()` 和 `setTitle(String title)` 方法被称为标题。

2. **工具提示(`String`)**：与 `Tab` 相关的工具提示文本，当光标悬停在 `Tab` 上时显示。

3. **启用(`boolean`)**：表示 `Tab` 是否启用。可以使用 `setEnabled(boolean enabled)` 方法进行修改。

4. **可关闭(`boolean`)**：表示 `Tab` 是否可以关闭。可以使用 `setClosable(boolean closable)` 方法进行修改。这将在 `Tab` 上添加一个可点击的关闭按钮，并触发移除事件。`TabbedPane` 组件决定如何处理移除。

5. **插槽(`Component`)**：
    插槽提供灵活选项以增强 `Tab` 的能力。您可以在 `Tab` 中嵌套图标、标签、加载旋转器、清除/重置功能、头像/个人资料图片和其他有益组件，以进一步澄清用户预期的含义。
    您可以在构造期间将组件添加到 `Tab` 的 `prefix` 插槽中。或者，可以使用 `setPrefixComponent()` 和 `setSuffixComponent()` 方法在 `Tab` 中显示选项的前后插入各种组件。

        ```java
        TabbedPane pane = new TabbedPane();
        pane.addTab(new Tab("Documents", TablerIcon.create("files")));
        ```

## `Tab` 操作 {#tab-manipulation}

提供多种方法，允许开发者向 `TabbedPane` 中添加、插入、移除和操作 `Tab` 元素的各种属性。

### 添加 `Tab` {#adding-a-tab}

`addTab()` 和 `add()` 方法以不同的重载形式存在，以允许开发者在 `TabbedPane` 中灵活添加新标签。添加 `Tab` 将把其放置在所有先前存在的标签之后。

1. **`addTab(String text)`**：将带有指定 `String` 作为 `Tab` 文本的 `Tab` 添加到 `TabbedPane`。
2. **`addTab(Tab tab)`**：将作为参数提供的 `Tab` 添加到 `TabbedPane`。
3. **`addTab(String text, Component component)`**：将带有给定 `String` 作为 `Tab` 文本的 `Tab` 添加，并在 `TabbedPane` 的内容部分显示提供的 `Component`。
4. **`addTab(Tab tab, Component component)`**：将提供的 `Tab` 添加，并在 `TabbedPane` 的内容部分显示提供的 `Component`。
5. **`add(Component... component)`**：向 `TabbedPane` 添加一个或多个 `Component` 实例，为每个创建一个独立的 `Tab`，文本设置为 `Component` 的名称。

:::info
`add(Component... component)` 通过调用传递参数的 `component.getName()` 来确定传递的 `Component` 的名称。
:::

### 插入 `Tab` {#inserting-a-tab}

除了在现有标签的末尾添加 `Tab`，还可以在指定位置创建新的 `Tab`。为此，存在多个重载版本的 `insertTab()`。

1. **`insertTab(int index, String text)`**：在指定索引处将带有给定 `String` 作为 `Tab` 文本的 `Tab` 插入到 `TabbedPane`。
2. **`insertTab(int index, Tab tab)`**：在指定索引处将作为参数提供的 `Tab` 插入到 `TabbedPane`。
3. **`insertTab(int index, String text, Component component)`**：在指定索引处将带有给定 `String` 作为 `Tab` 文本的 `Tab` 插入，并在 `TabbedPane` 的内容部分显示提供的 `Component`。
4. **`insertTab(int index, Tab tab, Component component)`**：插入提供的 `Tab` 并在 `TabbedPane` 的内容部分显示提供的 `Component`。

### 移除 `Tab` {#removing-a-tab}

要从 `TabbedPane` 中移除单个 `Tab`，请使用以下方法之一：

1. **`removeTab(Tab tab)`**：通过传递要移除的 Tab 实例，从 `TabbedPane` 中移除 `Tab`。
2. **`removeTab(int index)`**：通过指定要移除的 `Tab` 的索引，从 `TabbedPane` 中移除 `Tab`。

除了上面两个用于单个 `Tab` 移除的方法外，还可以使用 **`removeAllTabs()`** 方法清空 `TabbedPane` 的所有标签。

:::info
`remove()` 和 `removeAll()` 方法不会移除组件内的标签。
:::

### Tab/组件关联 {#tabcomponent-association}

要更改给定 `Tab` 显示的 `Component`，请调用 `setComponentFor()` 方法，并传递 `Tab` 的实例或该 `Tab` 在 `TabbedPane` 中的索引。

:::info
如果此方法用于已经与 `Component` 关联的 `Tab`，则之前关联的 `Component` 将被销毁。
:::

## 配置和布局 {#configuration-and-layout}

`TabbedPane` 类有两个组成部分：在指定位置显示的 `Tab` 和要显示的组件。这可以是单个组件或 [`Composite`](/docs/building-ui/composing-components) 组件，允许在选项卡内容部分中显示更复杂的组件。

### 切换 {#swiping}

`TabbedPane` 支持通过滑动在各种标签之间导航。这对于移动应用程序非常理想，但也可以通过内置方法配置以支持鼠标滑动。默认情况下，滑动和鼠标滑动都是禁用的，但可以分别通过 `setSwipeable(boolean)` 和 `setSwipeWithMouse(boolean)` 方法启用。

### 标签放置 {#tab-placement}

`TabbedPane` 中的 `Tabs` 可以依据应用开发者的偏好放置在组件的不同位置。提供的选项通过提供的枚举设置，值为 `TOP`、`BOTTOM`、`LEFT`、`RIGHT` 或 `HIDDEN`。默认设置为 `TOP`。

<ComponentDemo
path='/webforj/tabbedpaneplacement'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPanePlacementView.java']}
height='400px'
/>

### 对齐 {#alignment}

除了更改 `TabbedPane` 中 `Tab` 元素的位置外，还可以配置标签在组件中的对齐方式。默认设置为 `AUTO`，允许选项卡的位置决定其对齐方式。

其他选项为 `START`、`END`、`CENTER` 和 `STRETCH`。前三个描述相对于组件的位置，而 `STRETCH` 使标签充满可用空间。

<ComponentDemo
path='/webforj/tabbedpanealignment'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneAlignmentView.java']}
height='250px'
/>

### 边框和活动指示器 {#border-and-activity-indicator}

`TabbedPane` 默认为其中的标签显示边框，此边框根据设置的 `Placement` 确定位置。此边框有助于可视化选项卡面板中各种标签所占的空间。

当单击 `Tab` 时，默认情况下，活动指示器将显示在该 `Tab` 附近，以帮助突出显示当前选定的 `Tab`。

这两个选项都可以通过使用适当的设置方法更改布尔值来定制。要更改边框的显示与否，可以使用 `setBorderless(boolean)` 方法，传递 `true` 隐藏边框，而 `false`（默认值）则显示边框。

:::info
此边框不适用于整个 `TabbedPane` 组件，仅用于作为标签与组件内容之间的分隔符。
:::

要设置活动指示器的可见性，可以使用 `setHideActiveIndicator(boolean)` 方法。将 `true` 传递给此方法将隐藏处于活动状态的 `Tab` 下的活动指示器，而 `false`（默认值）将保持指示器的显示。

<ComponentDemo
path='/webforj/tabbedpaneborder'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneBorderView.java']}
height='300px'
/>

### 激活模式 {#activation-modes}

为了更细粒度控制 `TabbedPane` 如何与键盘导航配合使用，可以设置 `Activation` 模式以指定组件应该如何表现。

- **`自动`**：当设置为自动时，使用箭头键导航标签将立即显示相应的标签组件。

- **`手动`**：当设置为手动时，标签将获取焦点，但不会显示，直到用户按下空格或回车。

<ComponentDemo
path='/webforj/tabbedpaneactivation'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneActivationView.java']}
height='250px'
/>

### 移除选项 {#removal-options}

单个 `Tab` 元素可以设置为可关闭。可关闭的标签将在标签上添加关闭按钮，单击时触发关闭事件。`TabbedPane` 决定此行为如何处理。

- **`手动`**：默认情况下，移除设置为 `MANUAL`，这意味着事件被触发，但由开发者以任何方式处理此事件。

- **`自动`**：另外，可以使用 `AUTO`，它将触发事件，并为开发者从组件中移除 `Tab`，不需要开发者手动实现此行为。

### 分段控制 <DocChip chip='since' label='26.00' /> {#segment-control}

通过使用 `setSegment(true)` 启用 `segment` 属性，可以将 `TabbedPane` 呈现为分段控制。在此模式下，标签将显示带有滑动药丸指示器的活跃选择，提供一个紧凑的替代标准选项卡界面。

<ComponentDemo
path='/webforj/tabbedpanesegment'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneSegmentView.java']}
height='250px'
/>

## 样式 {#styling}

### 扩展和主题 {#expanse-and-theme}

`TabbedPane` 提供内置的 `Expanse` 和 `Theme` 选项，类似于其他 webforJ 组件。这些选项可用于快速添加样式，向最终用户传达多种含义，而无需使用 CSS 对组件进行样式设置。

<ComponentDemo
path='/webforj/tabbedpaneexpansetheme'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneExpanseThemeView.java']}
height='250px'
/>

<TableBuilder name={['Tab', 'TabbedPane']} />

## 最佳实践 {#best-practices}

在应用程序中使用 `TabbedPane` 时，推荐采用以下实践：

- **逻辑分组**：使用标签逻辑上分组相关内容：
    - 每个标签应表示应用程序中的不同类别或功能。
    - 将相似或逻辑上相关的标签放置在一起。

- **限制标签数量**：避免让用户感到迷惑，标签过多。考虑在适用的情况下使用层次结构或其他导航模式，以实现清晰的界面。

- **清晰标签**：清晰地标记您的标签，以便直观使用：
    - 为每个标签提供清晰简洁的标签。
    - 标签应反映内容或目的，使用户易于理解。
    - 在适用的情况下使用图标和不同颜色。

- **键盘导航**：使用 webforJ 的 `TabbedPane` 键盘导航支持，使与 `TabbedPane` 的交互对最终用户更无缝、直观。

- **默认标签**：如果默认标签没有放置在 `TabbedPane` 的开头，请考虑将此标签设置为默认，以便提供关键信息或常用信息。
