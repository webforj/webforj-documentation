---
title: TabbedPane
slug: tabbedpane
sidebar_position: 125
sidebar_class_name: new-content
description: >-
  Organize content into switchable Tab sections with the TabbedPane component,
  supporting icons, keys, and customizable tab properties.
_i18n_hash: 9a348db865b5ea1688eb09c4f6f75a57
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-tabbed-pane" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/tabbedpane/TabbedPane" top='true'/>

多个内容部分可以组织在一个`TabbedPane`下，其中每个部分都与一个可点击的`Tab`相关联。一次只能显示一个部分，选项卡可以显示文本、图标或两者兼而有之，以帮助用户在它们之间导航。

<!-- INTRO_END -->

## 用法 {#usages}

`TabbedPane` 类为开发人员提供了一种强大的工具，用于在用户界面中组织和展示多个选项卡或部分。以下是一些典型场景，您可能会在应用程序中利用 `TabbedPane`：

1. **文档查看器**：实现一个文档查看器，其中每个选项卡代表不同的文档或文件。用户可以轻松在打开的文档之间切换，以高效地进行多任务处理。

2. **数据管理**：利用 `TabbedPane` 组织数据管理任务，例如：
    >- 在应用程序中显示不同的数据集
    >- 可以在不同的选项卡中显示各种用户配置文件
    >- 用户管理系统中的不同配置文件

3. **模块选择**：`TabbedPane` 可以表示不同的模块或部分。每个选项卡可以封装特定模块的功能，使用者可以专注于应用程序的一个方面。

4. **任务管理**：任务管理应用程序可以使用 `TabbedPane` 来表示各种项目或任务。每个选项卡可以对应于一个特定项目，使用户能够单独管理和跟踪任务。

5. **程序导航**：在需要运行各种程序的应用程序中，`TabbedPane` 可以：
    >- 作为侧边栏，允许在单个应用程序内运行不同的应用程序或程序，例如在 [`AppLayout`](./app-layout.md) 模板中显示的内容
    >- 创建一个顶部栏，可以实现类似的功能，或在已经选择的应用程序内表示子应用程序。

## 选项卡 {#tabs}

选项卡是可以添加到选项卡面板中的用户界面元素，用于组织和切换不同的内容视图。

:::important
选项卡不应单独用作组件。它们应与选项卡面板一起使用。此类不是 `Component`，不应作为这样的组件使用。
:::

### 属性 {#properties}

选项卡由以下属性组成，这些属性在添加到 `TabbedPane` 时使用。这些属性具有获取器和设置器，以便在 `TabbedPane` 中方便地进行自定义。

1. **Key(`Object`)**：表示 `Tab` 的唯一标识符。

2. **Text(`String`)**：将在 `TabbedPane` 中作为 `Tab` 的标题显示的文本。这也通过 `getTitle()` 和 `setTitle(String title)` 方法被称为标题。

3. **Tooltip(`String`)**：与 `Tab` 关联的工具提示文本，当光标悬停在 `Tab` 上时将显示该文本。

4. **Enabled(`boolean`)**：表示 `Tab` 是否当前启用。可以通过 `setEnabled(boolean enabled)` 方法修改。

5. **Closeable(`boolean`)**：表示 `Tab` 是否可以关闭。可以通过 `setCloseable(boolean enabled)` 方法修改。此操作将在 `Tab` 上添加一个可供用户点击的关闭按钮，并触发一个移除事件。`TabbedPane` 组件决定如何处理该移除。

6. **Slot(`Component`)**：
    插槽提供了灵活的选项，以提升 `Tab` 的能力。您可以在 `Tab` 中嵌入图标、标签、加载指示器、清除/重置功能、头像/个人资料图片以及其他有益的组件，以进一步向用户阐明意图。
    您可以在构造期间将组件添加到 `Tab` 的 `prefix` 插槽中。或者，您可以使用 `setPrefixComponent()` 和 `setSuffixComponent()` 方法在 `Tab` 中的显示选项前后插入各种组件。

        ```java
        TabbedPane pane = new TabbedPane();
        pane.addTab(new Tab("Documents", TablerIcon.create("files")));
        ```

## `Tab` 操作 {#tab-manipulation}

存在多种方法允许开发人员添加、插入、删除和操作 `TabbedPane` 中的 `Tab` 元素的各种属性。

### 添加 `Tab` {#adding-a-tab}

`addTab()` 和 `add()` 方法以不同的重载形式存在，允许开发人员在 `TabbedPane` 中灵活添加新选项卡。添加一个 `Tab` 将把它放置在任何先前存在的选项卡之后。

1. **`addTab(String text)`** - 使用指定的 `String` 作为 `Tab` 的文本向 `TabbedPane` 添加一个 `Tab`。
2. **`addTab(Tab tab)`** - 将参数中提供的 `Tab` 添加到 `TabbedPane`。
3. **`addTab(String text, Component component)`** - 添加一个 `Tab`，其文本为给定的 `String`，并且在 `TabbedPane` 的内容部分显示所提供的 `Component`。
4. **`addTab(Tab tab, Component component)`** - 添加提供的 `Tab`，并在 `TabbedPane` 的内容部分显示所提供的 `Component`。
5. **`add(Component... component)`** - 向 `TabbedPane` 添加一个或多个 `Component` 实例，为每个实例创建一个独立的 `Tab`，文本设置为 `Component` 的名称。

:::info
`add(Component... component)` 根据传递参数调用 `component.getName()` 来确定传递的 `Component` 的名称。
:::

### 插入 `Tab` {#inserting-a-tab}

除了在现有选项卡的末尾添加 `Tab`，还可以在指定位置创建新的选项卡。为此，使用多个重载版本的 `insertTab()`。

1. **`insertTab(int index, String text)`** - 在给定索引处将一个 `Tab` 插入到 `TabbedPane`，文本为指定的 `String`。
2. **`insertTab(int index, Tab tab)`** - 将提供的 `Tab` 插入到 `TabbedPane` 中的指定索引。
3. **`insertTab(int index, String text, Component component)`** - 插入一个 `Tab`，其文本为给定的 `String`，并在 `TabbedPane` 的内容部分显示所提供的 `Component`。
4. **`insertTab(int index, Tab tab, Component component)`** - 插入提供的 `Tab`，并显示所提供的 `Component`。

### 移除 `Tab` {#removing-a-tab}

要从 `TabbedPane` 中移除单个 `Tab`，使用以下方法之一：

1. **`removeTab(Tab tab)`** - 通过将要移除的 `Tab` 实例传递给 `TabbedPane` 移除 `Tab`。
2. **`removeTab(int index)`** - 通过指定要移除的 `Tab` 的索引，从 `TabbedPane` 中移除 `Tab`。

除了上述两种单个 `Tab` 的移除方法，使用 **`removeAllTabs()`** 方法可以清空 `TabbedPane` 中的所有选项卡。

:::info
`remove()` 和 `removeAll()` 方法不会移除组件内的选项卡。
:::

### Tab/Component 关联 {#tabcomponent-association}

要更改给定 `Tab` 要显示的 `Component`，请调用 `setComponentFor()` 方法，并传递 `Tab` 的实例或该 `Tab` 在 `TabbedPane` 中的索引。

:::info
如果在已与 `Component` 关联的 `Tab` 上使用此方法，将销毁先前关联的 `Component`。
:::

## 配置与布局 {#configuration-and-layout}

`TabbedPane` 类有两个组成部分：一个在指定位置显示的 `Tab` 和一个要显示的组件。这可以是一个单一的组件，也可以是一个 [`Composite`](/docs/building-ui/composing-components) 组件，使得在选项卡的内容部分显示更复杂的组件成为可能。

### 刷新 {#swiping}

`TabbedPane` 支持通过滑动在不同选项卡之间导航。这对于移动应用程序非常理想，但也可以通过内置方法配置以支持鼠标滑动。滑动和鼠标滑动默认情况下都是禁用的，但可以分别通过 `setSwipable(boolean)` 和 `setSwipableWithMouse(boolean)` 方法启用。

### 选项卡放置 {#tab-placement}

`TabbedPane` 内的 `Tabs` 可以根据应用程序开发人员的偏好置于组件的各个位置。提供的选项通过提供的枚举进行设置，值包括 `TOP`、`BOTTOM`、`LEFT`、`RIGHT` 或 `HIDDEN`。默认设置为 `TOP`。

<ComponentDemo
path='/webforj/tabbedpaneplacement'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPanePlacementView.java']}
height='400px'
/>

### 对齐方式 {#alignment}

除了改变 `TabbedPane` 中 `Tab` 元素的放置方式外，还可以配置选项卡在组件内的对齐方式。默认情况下，设置为 `AUTO`，允许选项卡的放置决定它们的对齐方式。

其他选项为 `START`、`END`、`CENTER` 和 `STRETCH`。前三个描述相对于组件的位置，而 `STRETCH` 使选项卡填充可用空间。

<ComponentDemo
path='/webforj/tabbedpanealignment'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneAlignmentView.java']}
height='250px'
/>

### 边框和活动指示器 {#border-and-activity-indicator}

`TabbedPane` 默认会显示选项卡的边框，边框的位置取决于已设置的 `Placement`。此边框有助于可视化面板内各个选项卡占据的空间。

当单击 `Tab` 时，默认情况下，会在该 `Tab` 附近显示一个活动指示器，以帮助突出显示当前选择的 `Tab`。

开发人员可以通过适当的设置器方法更改这两个选项的自定义状态。要更改是否显示边框，可以使用 `setBorderless(boolean)` 方法，传入 `true` 将隐藏边框，而 `false`（默认值）将显示边框。

:::info
此边框不适用于整个 `TabbedPane` 组件，仅作为选项卡和组件内容之间的分隔符。
:::

要设置活动指示器的可见性，可以使用 `setHideActiveIndicator(boolean)` 方法。将 `true` 传递给此方法将隐藏活动选项卡下的活动指示器，而 `false`（默认）保持指示器显示。

<ComponentDemo
path='/webforj/tabbedpaneborder'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneBorderView.java']}
height='300px'
/>

### 激活模式 {#activation-modes}

为了更细粒度地控制 `TabbedPane` 在通过键盘导航时的行为，可以设置激活模式以指定组件应如何工作。

- **`Auto`**：设置为自动时，使用箭头键导航选项卡将立即显示相应的选项卡组件。

- **`Manual`**：设置为手动时，选项卡将获得焦点，但在用户按下空格键或回车键之前不会显示。

<ComponentDemo
path='/webforj/tabbedpaneactivation'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneActivationView.java']}
height='250px'
/>

### 移除选项 {#removal-options}

单个 `Tab` 元素可以设置为可关闭。可关闭的选项卡将在选项卡上添加一个关闭按钮，点击时会触发关闭事件。`TabbedPane` 决定如何处理此行为。

- **`Manual`**：默认情况下，移除设置为 `MANUAL`，这意味着事件会触发，但由开发人员自行处理此事件。

- **`Auto`**：另外，可以使用 `AUTO`，这将触发事件，并将 `Tab` 从组件中移除，用户不需要手动实现此行为。

### 段控制 <DocChip chip='since' label='26.00' /> {#segment-control}

可以通过 `setSegment(true)` 启用 `segment` 属性将 `TabbedPane` 渲染为段控制。在此模式下，选项卡显示带有滑动药丸指示器的选项卡，会突出显示活动选择，提供了一种比标准选项卡界面更紧凑的替代方案。

<ComponentDemo
path='/webforj/tabbedpanesegment'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneSegmentView.java']}
height='250px'
/>

## 样式 {#styling}

### 扩展和主题 {#expanse-and-theme}

`TabbedPane` 提供了内置的 `Expanse` 和 `Theme` 选项，类似于其他 webforJ 组件。这些可以用于快速添加样式，使终端用户明白各种含义，而无需使用 CSS 对组件进行样式设置。

<ComponentDemo
path='/webforj/tabbedpaneexpansetheme'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneExpanseThemeView.java']}
height='250px'
/>

<TableBuilder name={['Tab', 'TabbedPane']} />

## 最佳实践 {#best-practices}

在应用程序中使用 `TabbedPane` 时，建议采用以下实践：

- **逻辑分组**：利用选项卡逻辑分组相关内容
    >- 每个选项卡应代表应用程序内一个独特的类别或功能
    >- 将相似或逻辑相关的选项卡放在一起

- **有限的选项卡**：避免使用过多的选项卡让用户感到不知所措。考虑在适用的情况下使用层次结构或其他导航模式来保持界面的简洁

- **清晰的标签**：清晰地给选项卡加标签以便于使用
    >- 为每个选项卡提供清晰简明的标签
    >- 标签应反映内容或用途，使用户易于理解
    >- 在适用时利用图标和不同的颜色

- **键盘导航**：利用 webforJ 的 `TabbedPane` 键盘导航支持，使用户与 `TabbedPane` 的交互更流畅、直观

- **默认选项卡**：如果默认选项卡未放置在 `TabbedPane` 的开头，考虑将此选项卡设置为默认选项，以提供必要或常用的信息。
