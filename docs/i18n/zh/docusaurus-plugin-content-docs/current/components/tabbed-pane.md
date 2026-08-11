---
title: TabbedPane
slug: tabbedpane
sidebar_position: 125
description: >-
  Organize content into switchable Tab sections with the TabbedPane component,
  supporting icons, keys, and customizable tab properties.
_i18n_hash: 563f9251b928e2e9426d69d4b5192880
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-tabbed-pane" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/tabbedpane/TabbedPane" top='true'/>

多个内容部分可以在单个 `TabbedPane` 下组织，每个部分与一个可点击的 `Tab` 相关联。一次只能显示一个部分，标签可以显示文本、图标或两者，以帮助用户在它们之间导航。

<!-- INTRO_END -->

## 用法 {#usages}

`TabbedPane` 类为开发人员提供了一个强大的工具，用于在 UI 中组织和展示多个标签或部分。以下是一些通常情况下您可能在应用程序中使用 `TabbedPane` 的场景：

1. **文档查看器**：实现一个文档查看器，其中每个标签代表一个不同的文档或文件。用户可以轻松地在打开的文档之间切换，以提高多任务处理的效率。

2. **数据管理**：利用 `TabbedPane` 组织数据管理任务，例如：
    >- 在应用程序中显示不同的数据集
    >- 可以在不同的标签中显示各种用户档案
    >- 用户管理系统中的不同档案

3. **模块选择**：`TabbedPane` 可以代表不同的模块或部分。每个标签可以封装特定模块的功能，使用户可以一次专注于应用程序的一个方面。

4. **任务管理**：任务管理应用程序可以使用 `TabbedPane` 表示各种项目或任务。每个标签可以对应一个特定项目，允许用户单独管理和跟踪任务。

5. **程序导航**：在需要运行各种程序的应用程序中，`TabbedPane` 可以：
    >- 作为侧边栏，允许在单个应用程序中运行不同的应用程序或程序，如 [`AppLayout`](./app-layout.md) 模板中所示
    >- 创建一个顶部栏，具有类似的用途，或表示在已经选定的应用程序内的子应用程序。

## 标签 {#tabs}

标签是可以添加到选项卡窗格中的 UI 元素，用于组织和切换不同的内容视图。

:::important
标签并不打算作为独立组件使用。它们应与选项卡窗格结合使用。该类不是 `Component` ，并不应作为此使用。
:::

### 属性 {#properties}

标签由以下属性组成，这些属性在将它们添加到 `TabbedPane` 时使用。这些属性具有 getter 和 setter 以便在 `TabbedPane` 内进行自定义。

1. **Key(`Object`)**：表示 `Tab` 的唯一标识符。

2. **Text(`String`)**：将在 `TabbedPane` 内作为 `Tab` 的标题显示的文本。这也被称为通过 `getTitle()` 和 `setTitle(String title)` 方法获取的标题。

3. **Tooltip(`String`)**：与 `Tab` 相关联的工具提示文本，当光标悬停在 `Tab` 上时将显示。

4. **Enabled(`boolean`)**：表示 `Tab` 是否当前可用。可以使用 `setEnabled(boolean enabled)` 方法进行修改。

5. **Closeable(`boolean`)**：表示 `Tab` 是否可以被关闭。可以使用 `setCloseable(boolean enabled)` 方法进行修改。这将会在 `Tab` 上添加一个关闭按钮，用户可以点击该按钮，并触发移除事件。`TabbedPane` 组件决定如何处理移除。

6. **Slot(`Component`)**：
   插槽提供了灵活的选项，以增强 `Tab` 的能力。您可以在 `Tab` 中嵌入图标、标签、加载指示器、清除/重置能力、头像/个人资料图片和其他有益的组件，以进一步明确对用户所表达的意图。
   您可以在构造期间向 `Tab` 的 `prefix` 插槽添加组件。或者，您可以使用 `setPrefixComponent()` 和 `setSuffixComponent()` 方法在 `Tab` 中显示选项的前后插入各种组件。

        ```java
        TabbedPane pane = new TabbedPane();
        pane.addTab(new Tab("文档", TablerIcon.create("files")));
        ```

## `Tab` 操作 {#tab-manipulation}

存在多种方法允许开发人员在 `TabbedPane` 内添加、插入、移除和操作各种 `Tab` 元素的属性。

### 添加 `Tab` {#adding-a-tab}

存在不同重载能力的 `addTab()` 和 `add()` 方法，以便开发人员在 `TabbedPane` 中灵活添加新标签。添加 `Tab` 将将其放置在任何之前存在的标签之后。

1. **`addTab(String text)`** - 使用指定的 `String` 作为 `Tab` 的文本，将 `Tab` 添加到 `TabbedPane` 中。
2. **`addTab(Tab tab)`** - 将提供的 `Tab` 作为参数添加到 `TabbedPane` 中。
3. **`addTab(String text, Component component)`** - 将一个 `Tab` 添加到 `TabbedPane`，其 `String` 作为 `Tab` 的文本，并在 `TabbedPane` 的内容部分显示提供的 `Component`。
4. **`addTab(Tab tab, Component component)`** - 添加提供的 `Tab`，并在 `TabbedPane` 的内容部分显示提供的 `Component`。
5. **`add(Component... component)`** - 向 `TabbedPane` 添加一个或多个 `Component` 实例，为每个实例创建一个离散的 `Tab`，文本设置为 `Component` 的名称。

:::info
`add(Component... component)` 通过调用 `component.getName()` 来确定传递的 `Component` 的名称。
:::

### 插入 `Tab` {#inserting-a-tab}

除了在现有标签的末尾添加 `Tab`，还可以在指定位置创建新标签。要做到这一点，多个重载版本的 `insertTab()`。

1. **`insertTab(int index, String text)`** - 在给定索引处插入一个 `Tab` ，并使用指定的 `String` 作为 `Tab` 的文本。
2. **`insertTab(int index, Tab tab)`** - 在指定索引处将作为参数提供的 `Tab` 插入到 `TabbedPane` 中。
3. **`insertTab(int index, String text, Component component)`** - 插入一个 `Tab`，其 `String` 作为 `Tab` 的文本，并在 `TabbedPane` 的内容部分显示提供的 `Component`。
4. **`insertTab(int index, Tab tab, Component component)`** - 插入提供的 `Tab`，并在 `TabbedPane` 的内容部分显示提供的 `Component`。

### 移除 `Tab` {#removing-a-tab}

要从 `TabbedPane` 中移除单个 `Tab`，请使用以下方法之一：

1. **`removeTab(Tab tab)`** - 通过传递要移除的 Tab 实例，从 `TabbedPane` 中移除一个 `Tab`。
2. **`removeTab(int index)`** - 通过指定要移除的 `Tab` 的索引，从 `TabbedPane` 中移除一个 `Tab`。

除了以上两种移除单个 `Tab` 的方法外，还可以使用 **`removeAllTabs()`** 方法清空 `TabbedPane` 中的所有标签。

:::info
`remove()` 和 `removeAll()` 方法不会移除组件中的标签。
:::

### Tab/组件关联 {#tabcomponent-association}

要更改给定 `Tab` 的显示 `Component`，请调用 `setComponentFor()` 方法，并传递 `Tab` 的实例，或在 `TabbedPane` 中该 `Tab` 的索引。

:::info
如果此方法用于已经与 `Component` 关联的 `Tab`，则先前关联的 `Component` 将被销毁。
:::

## 配置和布局 {#configuration-and-layout}

`TabbedPane` 类有两个组成部分：在指定位置显示的 `Tab` 和要显示的组件。这可以是单一组件，或一个 [`Composite`](/docs/building-ui/composing-components) 组件，允许在标签内容部分显示更复杂的组件。

### 滑动 {#swiping}

`TabbedPane` 支持通过滑动在各个标签之间导航。这对于移动应用程序非常理想，但也可以通过内置方法配置以支持鼠标滑动。滑动和鼠标滑动默认情况下均处于禁用状态，但可以通过 `setSwipable(boolean)` 和 `setSwipableWithMouse(boolean)` 方法分别启用。

### 标签位置 {#tab-placement}

`TabbedPane` 中的 `Tabs` 可以根据应用程序开发人员的偏好放置在组件的不同位置。提供的选项通过提供的枚举设置，值有 `TOP`、`BOTTOM`、`LEFT`、`RIGHT` 或 `HIDDEN`。默认设置为 `TOP`。

<ComponentDemo
path='/webforj/tabbedpaneplacement'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPanePlacementView.java']}
height='400px'
/>

### 对齐 {#alignment}

除了改变 `TabbedPane` 内 `Tab` 元素的放置外，还可以配置标签在组件内如何对齐。默认情况下，设置为 `AUTO`，这允许标签的放置决定它们的对齐。

其他选项为 `START`、`END`、`CENTER` 和 `STRETCH`。前三个描述相对于组件的位置，`STRETCH` 使标签填充可用空间。

<ComponentDemo
path='/webforj/tabbedpanealignment'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneAlignmentView.java']}
height='250px'
/>

### 边框和活动指示器 {#border-and-activity-indicator}

默认情况下，`TabbedPane` 会显示一个边框，放置在根据所设置的 `Placement`。此边框有助于可视化标签在窗格中占据的空间。

当点击 `Tab` 时，默认情况下，会在该 `Tab` 附近显示一个活动指示器，以帮助突出当前选定的 `Tab`。

开发人员可以通过使用适当的设置方法更改这两个选项的布尔值。要更改是否显示边框，可以使用 `setBorderless(boolean)` 方法，其中 `true` 隐藏边框，`false`（默认值）显示边框。

:::info
该边框不适用于整个 `TabbedPane` 组件，仅作为标签与组件内容之间的分隔符。
:::

要设置活动指示器的可见性，可以使用 `setHideActiveIndicator(boolean)` 方法。将 `true` 传递给此方法将在活动 `Tab` 下隐藏活动指示器，而 `false`（默认值）则保持指示器显示。

<ComponentDemo
path='/webforj/tabbedpaneborder'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneBorderView.java']}
height='300px'
/>

### 激活模式 {#activation-modes}

为了更精准地控制通过键盘导航时 `TabbedPane` 的行为，可以设置激活模式以指定组件应如何行为。

- **`Auto`**：设置为自动时，用箭头键导航标签将立即显示相应的标签组件。

- **`Manual`**：设置为手动时，标签将获得焦点，但不会显示，直到用户按下空格或回车。

<ComponentDemo
path='/webforj/tabbedpaneactivation'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneActivationView.java']}
height='250px'
/>

### 移除选项 {#removal-options}

单个 `Tab` 元素可以设置为可关闭。可关闭的标签将在标签上添加一个关闭按钮，单击时触发关闭事件。`TabbedPane` 决定如何处理此行为。

- **`Manual`**：默认情况下，移除设置为 `MANUAL`，这意味着触发事件，但由开发人员决定以他们选择的方式处理此事件。

- **`Auto`**：或者，可以使用 `AUTO`，这将触发事件，并且还会从组件中移除 `Tab`，开发人员无需手动实现此行为。

### 段控制 <DocChip chip='since' label='26.00' /> {#segment-control}

`TabbedPane` 可以通过使用 `setSegment(true)` 启用 `segment` 属性而呈现为段控制。在此模式下，标签将与滑动药丸指示器一起显示，该指示器突出显示活动选择，提供比标准标签界面更紧凑的替代方案。

<ComponentDemo
path='/webforj/tabbedpanesegment'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneSegmentView.java']}
height='250px'
/>

## 样式 {#styling}

### 范围和主题 {#expanse-and-theme}

`TabbedPane` 带有内置的 `Expanse` 和 `Theme` 选项，类似于其他 webforJ 组件。这些可以用来快速添加样式，使最终用户无需使用 CSS 为组件样式化。

<ComponentDemo
path='/webforj/tabbedpaneexpansetheme'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneExpanseThemeView.java']}
height='250px'
/>

<TableBuilder name={['Tab', 'TabbedPane']} />

## 最佳实践 {#best-practices}

在应用程序中使用 `TabbedPane` 时，建议遵循以下实践：

- **逻辑分组**：使用标签逻辑性地分组相关内容
    >- 每个标签应表示应用程序中的一个独特类别或功能
    >- 将相似或逻辑相关的标签放在相邻的位置

- **限制标签**：避免给用户带来过多标签的困扰。在适用的情况下考虑使用层次结构或其他导航模式以保持界面的整洁。

- **清晰标签**：清晰标记您的标签以便于直观使用
    >- 为每个标签提供清晰简洁的标签
    >- 标签应反映内容或目的，使用户能够轻松理解
    >- 在适用的情况下，利用图标和不同的颜色

- **键盘导航**：使用 webforJ 的 `TabbedPane` 键盘导航支持，使与 `TabbedPane` 的交互对最终用户更无缝和直观。

- **默认标签**：如果默认标签未放置在 `TabbedPane` 开始位置，请考虑将此标签设置为默认标签，以提供必要或常用的信息。
