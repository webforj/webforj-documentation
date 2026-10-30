---
title: TabbedPane
slug: tabbedpane
sidebar_position: 125
description: >-
  Organize content into switchable Tab sections with the TabbedPane component,
  supporting icons and customizable tab properties.
_i18n_hash: 2d2e78638206283a7423cea736e3bf2c
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-tabbed-pane" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/tabbedpane/TabbedPane" top='true'/>

多个内容部分可以组织在一个 `TabbedPane` 下，其中每个部分都与一个可点击的 `Tab` 相关联。一次只能看到一个部分，选项卡可以显示文本、图标或两者的组合，以帮助用户在它们之间导航。

<!-- INTRO_END -->

## 用法 {#usages}

`TabbedPane` 类为开发者提供了一个强大的工具，用于在用户界面中组织和呈现多个选项卡或部分。以下是一些在应用中使用 `TabbedPane` 的典型场景：

1. **文档查看器**：实现一个文档查看器，其中每个选项卡代表不同的文档或文件。用户可以轻松在打开的文档之间切换，以实现高效的多任务处理。

2. **数据管理**：使用 `TabbedPane` 来组织数据管理任务，例如：
    - 在应用中显示不同的数据集
    - 通过单独的选项卡显示各种用户档案
    - 用户管理系统中的不同档案

3. **模块选择**：`TabbedPane` 可以表示不同的模块或部分。每个选项卡可以封装特定模块的功能，使用户可以专注于应用的一个方面。

4. **任务管理**：任务管理应用可以使用 `TabbedPane` 来表示各种项目或任务。每个选项卡可以对应于一个特定项目，使用户能够单独管理和跟踪任务。

5. **程序导航**：在需要运行各种程序的应用中，`TabbedPane` 可以：
    - 作为侧边栏，允许在单个应用程序中运行不同的应用或程序，如在 [`AppLayout`](./app-layout.md) 模板中所示
    - 创建一个顶部栏，具有类似的功能，或在已选择的应用中表示子应用

## 选项卡 <DocChip chip='name' label="dwc-tab" /> {#tabs}

选项卡是可以添加到选项卡窗格中以组织和切换不同内容视图的用户界面元素。

:::important
选项卡并不打算作为独立组件使用。它们旨在与选项卡窗格结合使用。此类不是 `Component`，不应被作为组件使用。
:::

### 属性 {#properties}

选项卡由以下属性组成，这些属性在 `TabbedPane` 中添加时使用。这些属性具有访问器和修改器，以便在 `TabbedPane` 中进行定制。

1. **文本(`String`)**：在 `TabbedPane` 中显示为 `Tab` 标题的文本。也通过 `getTitle()` 和 `setTitle(String title)` 方法称为标题。

2. **工具提示(`String`)**：与 `Tab` 相关联的工具提示文本，当鼠标悬停在 `Tab` 上时显示。

3. **启用(`boolean`)**：表示 `Tab` 是否启用。可以通过 `setEnabled(boolean enabled)` 方法进行修改。

4. **可关闭(`boolean`)**：表示 `Tab` 是否可以关闭。可以通过 `setClosable(boolean closable)` 方法进行修改。这将在 `Tab` 上添加一个可点击的关闭按钮，并触发移除事件。`TabbedPane` 组件决定如何处理移除。

5. **插槽(`Component`)**：
    插槽提供了灵活的选项，以改善 `Tab` 的功能。您可以在 `Tab` 中嵌入图标、标签、加载指示器、清除/重置功能、头像/个人资料图片和其他有益组件，以进一步明确用户的意图。
    在构造 `Tab` 时，可以将组件添加到 `prefix` 插槽。或者，您可以使用 `setPrefixComponent()` 和 `setSuffixComponent()` 方法在 `Tab` 中显示选项之前或之后插入各种组件。

        ```java
        TabbedPane pane = new TabbedPane();
        pane.addTab(new Tab("Documents", TablerIcon.create("files")));
        ```

## `Tab` 操作 {#tab-manipulation}

存在各种方法允许开发者添加、插入、移除和操作 `TabbedPane` 中的 `Tab` 元素的各种属性。

### 添加 `Tab` {#adding-a-tab}

`addTab()` 和 `add()` 方法以不同的重载方式存在，以允许开发者灵活地向 `TabbedPane` 添加新选项卡。添加 `Tab` 将将其放在任何先前现有选项卡之后。

1. **`addTab(String text)`**：使用指定的 `String` 作为 `Tab` 的文本，向 `TabbedPane` 添加一个 `Tab`。
2. **`addTab(Tab tab)`**：将提供的 `Tab` 作为参数添加到 `TabbedPane` 中。
3. **`addTab(String text, Component component)`**：使用给定的 `String` 作为 `Tab` 的文本，向 `TabbedPane` 添加一个选项卡，并在 `TabbedPane` 的内容部分显示提供的 `Component`。
4. **`addTab(Tab tab, Component component)`**：将提供的 `Tab` 添加并在 `TabbedPane` 的内容部分显示提供的 `Component`。
5. **`add(Component... component)`**：向 `TabbedPane` 添加一个或多个 `Component` 实例，为每个选项创建一个离散的 `Tab`，文本设置为 `Component` 的名称。

:::info
`add(Component... component)` 通过调用传递参数的 `component.getName()` 来确定传递 `Component` 的名称。
:::

### 插入 `Tab` {#inserting-a-tab}

除了在现有选项卡的末尾添加一个 `Tab`，还可以在指定位置创建一个新的选项卡。为此，多个重载版本的 `insertTab()`。

1. **`insertTab(int index, String text)`**：在给定索引处将指定的 `String` 作为 `Tab` 的文本插入到 `TabbedPane` 中。
2. **`insertTab(int index, Tab tab)`**：在指定索引处将提供的 `Tab` 作为参数插入到 `TabbedPane` 中。
3. **`insertTab(int index, String text, Component component)`**：插入一个 `Tab`，指定的 `String` 作为 `Tab` 的文本，并在 `TabbedPane` 的内容部分显示提供的 `Component`。
4. **`insertTab(int index, Tab tab, Component component)`**：插入提供的 `Tab` 并在 `TabbedPane` 的内容部分显示提供的 `Component`。

### 移除 `Tab` {#removing-a-tab}

要从 `TabbedPane` 中移除单个 `Tab`，请使用以下方法之一：

1. **`removeTab(Tab tab)`**：通过传递要移除的 `Tab` 实例从 `TabbedPane` 中移除一个 `Tab`。
2. **`removeTab(int index)`**：通过指定要移除的 `Tab` 的索引从 `TabbedPane` 中移除一个 `Tab`。

除了上述移除单个 `Tab` 的两种方法外，使用 **`removeAllTabs()`** 方法可以清空 `TabbedPane` 中的所有选项卡。

:::info
`remove()` 和 `removeAll()` 方法不会移除组件中的选项卡。
:::

### 选项卡/组件关联 {#tabcomponent-association}

要更改要为给定 `Tab` 显示的 `Component`，请调用 `setComponentFor()` 方法，并传递 `Tab` 的实例或该 `Tab` 在 `TabbedPane` 中的索引。

:::info
如果此方法用于已与 `Component` 关联的 `Tab`，则先前关联的 `Component` 将被销毁。
:::

## 配置和布局 {#configuration-and-layout}

`TabbedPane` 类由两个组成部分组成：一个在指定位置显示的 `Tab` 和一个要显示的组件。这个组件可以是一个单一组件，或者是一个 [`Composite`](/docs/building-ui/composing-components) 组件，允许在标签内容部分内显示更复杂的组件。

### 切换 {#swiping}

`TabbedPane` 支持通过滑动在不同选项卡之间导航。这对于移动应用程序是理想的，但也可以通过内置方法配置以支持鼠标滑动。滑动和鼠标滑动在默认情况下都是禁用的，但可以分别通过 `setSwipeable(boolean)` 和 `setSwipeWithMouse(boolean)` 方法启用。

### 选项卡放置 {#tab-placement}

`TabbedPane` 中的 `Tabs` 可以根据应用开发者的偏好放置在组件的不同位置。提供的选项通过提供的枚举设置，具有 `TOP`、`BOTTOM`、`LEFT`、`RIGHT` 或 `HIDDEN` 的值。默认设置为 `TOP`。

<ComponentDemo
path='/webforj/tabbedpaneplacement'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPanePlacementView.java']}
height='400px'
/>

### 对齐 {#alignment}

除了更改 `TabbedPane` 中 `Tab` 元素的放置外，还可以配置选项卡在组件内的对齐方式。默认情况下，设置为 `AUTO`，允许选项卡的放置来决定它们的对齐方式。

其他选项为 `START`、`END`、`CENTER` 和 `STRETCH`。前三个描述相对于组件的位置，`STRETCH` 使选项卡填充可用空间。

<ComponentDemo
path='/webforj/tabbedpanealignment'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneAlignmentView.java']}
height='250px'
/>

### 边框和活动指示器 {#border-and-activity-indicator}

`TabbedPane` 默认将为其显示的选项卡显示边框，放置依赖于设置的 `Placement`。这个边框有助于可视化选项卡在窗格中占据的空间。

当点击一个 `Tab` 时，默认情况下，会在该 `Tab` 附近显示一个活动指示器，以帮助突出显示当前选定的 `Tab`。

这两个选项都可以通过使用适当设置方法更改布尔值进行自定义。要更改是否显示边框，可以使用 `setBorderless(boolean)` 方法，传递 `true` 隐藏边框，`false`（默认值）显示边框。

:::info
这个边框不适用于整个 `TabbedPane` 组件，仅作为选项卡与组件内容之间的分隔符。
:::

要设置活动指示器的可见性，可以使用 `setHideActiveIndicator(boolean)` 方法。将 `true` 传递给该方法将隐藏活动 `Tab` 下的活动指示器，而 `false`（默认值）则保持指示器显示。

<ComponentDemo
path='/webforj/tabbedpaneborder'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneBorderView.java']}
height='300px'
/>

### 激活模式 {#activation-modes}

为了更细粒度地控制 `TabbedPane` 如何与键盘导航配合使用，可以设置 `Activation` 模式以指定组件应该如何行为。

- **`Auto`**：当设置为自动时，使用方向键导航选项卡将立即显示相应的选项卡组件。

- **`Manual`**：当设置为手动时，选项卡将获取焦点，但在用户按下空格或回车之前不会显示。

<ComponentDemo
path='/webforj/tabbedpaneactivation'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneActivationView.java']}
height='250px'
/>

### 移除选项 {#removal-options}

单个 `Tab` 元素可以设置为可关闭。可关闭的选项卡将在选项卡上添加一个关闭按钮，单击时会触发关闭事件。`TabbedPane` 决定如何处理此行为。

- **`Manual`**：默认情况下，移除设置为 `MANUAL`，这意味着事件被触发，但由开发者决定以何种方式处理此事件。

- **`Auto`**：可以使用 `AUTO`，这将触发事件，并从组件中移除 `Tab`，无需开发者手动实现此行为。

### 区段控制 <DocChip chip='since' label='26.00' /> {#segment-control}

`TabbedPane` 可以通过启用 `segment` 属性并使用 `setSegment(true)` 将其呈现为区段控制。在此模式下，选项卡以滑动药丸指示器显示，突出显示活动选择，为标准选项卡界面提供紧凑的替代方案。

<ComponentDemo
path='/webforj/tabbedpanesegment'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneSegmentView.java']}
height='250px'
/>

## 样式 {#styling}

### 扩展和主题 {#expanse-and-theme}

`TabbedPane` 具有内置的 `Expanse` 和 `Theme` 选项，类似于其他 webforJ 组件。这些可以用来快速添加样式，以向最终用户传达各种意义，而无需使用 CSS 样式组件。

<ComponentDemo
path='/webforj/tabbedpaneexpansetheme'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneExpanseThemeView.java']}
height='250px'
/>

<TableBuilder name={['Tab', 'TabbedPane']} />

## 最佳实践 {#best-practices}

以下实践建议在应用程序中使用 `TabbedPane` 时遵循：

- **逻辑分组**：使用选项卡逻辑分组相关内容：
    - 每个选项卡应代表应用中一个独特的类别或功能。
    - 将相似或逻辑相关的选项卡靠近放置。

- **有限选项卡**：避免给用户带来过多选项卡的困扰。考虑在可行的情况下使用层次结构或其他导航模式，以实现干净的界面。

- **清晰标签**：为您的选项卡提供清晰的标签，以便直观使用：
    - 为每个选项卡提供清晰简洁的标签。
    - 标签应反映内容或目的，使用户易于理解。
    - 在适用的情况下使用图标和不同的颜色。

- **键盘导航**：使用 webforJ 的 `TabbedPane` 键盘导航支持，使用户与 `TabbedPane` 的交互更加流畅和直观。

- **默认选项卡**：如果默认选项卡不在 `TabbedPane` 开头，请考虑将该选项卡设置为默认，以便提供重要或常用的信息。
