---
title: TabbedPane
slug: tabbedpane
sidebar_position: 125
_i18n_hash: 2e67673ef0ac49904be50764ef47ecb0
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-tabbed-pane" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/tabbedpane/TabbedPane" top='true'/>

`TabbedPane` 类提供了一种紧凑且有组织的方式来展示分为多个部分的内容，每部分都与一个 `Tab` 相关联。用户可以通过点击相应的标签在这些部分之间切换，通常标签上会标有文本和/或图标。此类简化了多层次界面的创建，其中不同的内容或表单需要可访问，但不能同时显示。

## Usages {#usages}

`TabbedPane` 类为开发人员提供了强大的工具，用于在用户界面中组织和呈现多个标签或部分。以下是一些典型场景，您可以在应用程序中使用 `TabbedPane`：

1. **文档查看器**：实现一个文档查看器，其中每个标签表示一个不同的文档或文件。用户可以轻松在打开的文档之间切换，以提高多任务处理的效率。

2. **数据管理**：使用 `TabbedPane` 来组织数据管理任务，例如：
    >- 应用程序中显示不同的数据集  
    >- 各种用户档案可以在单独的标签内显示  
    >- 用户管理系统中的不同档案  

3. **模块选择**：`TabbedPane` 可以表示不同的模块或部分。每个标签可以封装特定模块的功能，使用户能够集中于应用程序的一个方面。

4. **任务管理**：任务管理应用程序可以使用 `TabbedPane` 来表示各种项目或任务。每个标签可能对应于一个特定项目，使用户能够单独管理和跟踪任务。

5. **程序导航**：在需要运行各种程序的应用程序中，`TabbedPane` 可以：
    >- 作为一个侧边栏，允许在单个应用程序内运行不同的应用程序或程序，比如在 [`AppLayout`](./app-layout.md) 模板中所示  
    >- 创建一个顶部栏，可以起到类似的目的，或者表示在已经选择的应用程序中的子应用程序。
  
## Tabs {#tabs}

标签是可以添加到选项卡窗格中的用户界面元素，用于组织和切换不同的内容视图。

:::important
标签并不打算作为独立组件使用。它们应该与选项卡窗格结合使用。这个类不是 `Component`，不应这样使用。
:::

### Properties {#properties}

标签由以下属性组成，这些属性在将其添加到 `TabbedPane` 时使用。这些属性具有 getter 和 setter，以便在 `TabbedPane` 中自定义。

1. **Key(`Object`)**: 表示 `Tab` 的唯一标识符。

2. **Text(`String`)**: 在 `TabbedPane` 中作为 `Tab` 的标题显示的文本。这也被称为 `getTitle()` 和 `setTitle(String title)` 方法的标题。

3. **Tooltip(`String`)**: 与 `Tab` 相关联的工具提示文本，当光标悬停在 `Tab` 上时将显示。

4. **Enabled(`boolean`)**: 表示 `Tab` 当前是否处于启用状态。可以通过 `setEnabled(boolean enabled)` 方法进行修改。

5. **Closeable(`boolean`)**: 表示 `Tab` 是否可以关闭。可以通过 `setCloseable(boolean enabled)` 方法进行修改。这将在 `Tab` 上添加一个关闭按钮，用户可以点击该按钮，并触发一个移除事件。`TabbedPane` 组件决定如何处理该移除。

6. **Slot(`Component`)**:
    插槽提供灵活的选项，以改善 `Tab` 的功能。您可以在 `Tab` 内嵌入图标、标签、加载旋转器、清除/重置功能、头像/个人资料图片以及其他有益组件，以进一步向用户阐明意图。
    您可以在构造期间将组件添加到 `Tab` 的 `prefix` 插槽。或者，您可以使用 `setPrefixComponent()` 和 `setSuffixComponent()` 方法在 `Tab` 内部显示选项之前和之后插入各种组件。

        ```java
        TabbedPane pane = new TabbedPane();
        pane.addTab(new Tab("Documents", TablerIcon.create("files")));
        ```

## `Tab` manipulation {#tab-manipulation}

存在各种方法，允许开发人员在 `TabbedPane` 中添加、插入、删除和操作各种属性的 `Tab` 元素。

### Adding a `Tab` {#adding-a-tab}

`addTab()` 和 `add()` 方法存在不同的重载形式，以允许开发人员灵活地将新标签添加到 `TabbedPane`。添加 `Tab` 将使其位于任何先前存在的标签之后。

1. **`addTab(String text)`** - 使用指定的 `String` 作为 `Tab` 的文本，向 `TabbedPane` 添加一个 `Tab`。  
2. **`addTab(Tab tab)`** - 将作为参数提供的 `Tab` 添加到 `TabbedPane`。  
3. **`addTab(String text, Component component)`** - 使用给定的 `String` 作为 `Tab` 的文本，向 `TabbedPane` 添加一个标签，并在 `TabbedPane` 的内容部分显示提供的 `Component` 。  
4. **`addTab(Tab tab, Component component)`** - 添加提供的 `Tab` 并在 `TabbedPane` 的内容部分显示提供的 `Component` 。  
5. **`add(Component... component)`** - 向 `TabbedPane` 添加一个或多个 `Component` 实例，为每个实例创建一个单独的 `Tab` ，文本设置为 `Component` 的名称。

:::info
`add(Component... component)` 通过调用传递参数的 `component.getName()` 来确定传递 `Component` 的名称。
:::

### Inserting a `Tab` {#inserting-a-tab}

除了在现有标签的末尾添加 `Tab`，还可以在指定的位置创建一个新标签。为此，提供多种重载版本的 `insertTab()`。

1. **`insertTab(int index, String text)`** - 在给定索引位置将 `Tab` 插入到 `TabbedPane` ，指定的 `String` 作为 `Tab` 的文本。  
2. **`insertTab(int index, Tab tab)`** - 在指定索引位置将作为参数提供的 `Tab` 插入到 `TabbedPane` 。  
3. **`insertTab(int index, String text, Component component)`** - 在给定索引位置插入一个 `Tab` ，指定的 `String` 作为 `Tab` 的文本，并在 `TabbedPane` 的内容部分显示提供的 `Component` 。  
4. **`insertTab(int index, Tab tab, Component component)`** - 插入提供的 `Tab` 并在 `TabbedPane` 的内容部分显示提供的 `Component` 。

### Removing a `Tab` {#removing-a-tab}

要从 `TabbedPane` 中删除一个 `Tab`，请使用以下任一方法：

1. **`removeTab(Tab tab)`** - 通过传递要删除的 `Tab` 实例，从 `TabbedPane` 中移除 `Tab`。  
2. **`removeTab(int index)`** - 通过指定要删除的 `Tab` 的索引，从 `TabbedPane` 中移除 `Tab`。

除了以上两种方法以删除单个 `Tab`，还可以使用 **`removeAllTabs()`** 方法清空 `TabbedPane` 中的所有标签。

:::info
`remove()` 和 `removeAll()` 方法不会删除组件中的标签。
:::

### Tab/Component association {#tabcomponent-association}

要更改要在给定 `Tab` 上显示的 `Component`，请调用 `setComponentFor()` 方法，并传递 `Tab` 的实例或该 `TabbedPane` 中该 `Tab` 的索引。

:::info
如果在已与 `Component` 相关联的 `Tab` 上使用此方法，则先前相关的 `Component` 将被销毁。
:::

## Configuration and layout {#configuration-and-layout}

`TabbedPane` 类有两个组成部分：一个在指定位置显示的 `Tab` 和一个要显示的组件。这可以是单个组件，或者是 [`Composite`](../building-ui/composite-components) 组件，允许在标签的内容部分显示更复杂的组件。

### Swiping {#swiping}

`TabbedPane` 支持通过滑动在不同的标签之间导航。这非常适合移动应用程序，但也可以通过内置方法配置以支持鼠标滑动。滑动和鼠标滑动默认情况下均处于禁用状态，但可以分别通过 `setSwipable(boolean)` 和 `setSwipableWithMouse(boolean)` 方法启用。

### Tab placement {#tab-placement}

`TabbedPane` 中的 `Tabs` 可以根据应用程序开发人员的偏好放置在组件的不同位置。提供的选项使用提供的枚举设置，具有 `TOP`、`BOTTOM`、`LEFT`、`RIGHT` 或 `HIDDEN` 的值。默认设置为 `TOP`。

<ComponentDemo 
path='/webforj/tabbedpaneplacement?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPanePlacementView.java'
height="400px"
/>

### Alignment {#alignment}

除了改变 `TabbedPane` 中 `Tab` 元素的放置外，还可以配置标签在组件中的对齐方式。默认情况下，设置为 `AUTO`，这允许标签的放置决定其对齐。

其他选项为 `START`、`END`、`CENTER` 和 `STRETCH`。前三个描述相对于组件的位置，而 `STRETCH` 使标签填满可用空间。

<ComponentDemo 
path='/webforj/tabbedpanealignment?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneAlignmentView.java'
height="250px"
/>

### Border and activity indicator {#border-and-activity-indicator}

`TabbedPane` 默认情况下将显示标签的边框，该边框的位置取决于设置的 `Placement`。此边框有助于可视化面板内不同标签所占的空间。

当点击 `Tab` 时，默认情况下，会在该 `Tab` 附近显示活动指示器，以突出显示当前选定的 `Tab`。

开发人员可以通过使用适当的 setter 方法更改这两个选项的布尔值来自定义。要更改是否显示边框，可以使用 `setBorderless(boolean)` 方法，传递 `true` 将隐藏边框，`false`（默认值）将显示边框。

:::info
该边框不适用于整个 `TabbedPane` 组件，仅作为标签与组件内容之间的分隔符。
:::

要设置活动指示器的可见性，可以使用 `setHideActiveIndicator(boolean)` 方法。将 `true` 传递给此方法将隐藏活跃标签下的活动指示器，而 `false`（默认值）则保持指示器可见。

<ComponentDemo 
path='/webforj/tabbedpaneborder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneBorderView.java'
height="300px"
/>

### Activation modes {#activation-modes}

为了对 `TabbedPane` 在键盘导航时的行为进行更细粒度的控制，可以设置 `Activation` 模式以指定组件的行为。

- **`Auto`**: 设置为自动时，使用箭头键导航标签将立即显示相应的标签组件。

- **`Manual`**: 设置为手动时，标签将获得焦点，但不会显示，直到用户按下空格或回车。

<ComponentDemo 
path='/webforj/tabbedpaneactivation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneActivationView.java'
height="250px"
/>

### Removal options {#removal-options}

单个 `Tab` 元素可以设置为可关闭。可关闭的标签将在标签上添加一个关闭按钮，单击时触发关闭事件。`TabbedPane` 决定如何处理此行为。

- **`Manual`**: 默认情况下，移除设置为 `MANUAL`，这意味着事件会被触发，但由开发人员处理此事件的方式。

- **`Auto`**: 另外，可以使用 `AUTO`，它将触发事件，同时将 `Tab` 从组件中移除，使开发人员无需手动实现该行为。

## Styling {#styling}

### Expanse and theme {#expanse-and-theme}

`TabbedPane` 具有与其他 webforJ 组件类似的内置 `Expanse` 和 `Theme` 选项。这些可用于快速添加样式，向最终用户传达不同的含义，而无需使用 CSS 对组件进行样式设置。

<ComponentDemo 
path='/webforj/tabbedpaneexpansetheme?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneExpanseThemeView.java'
height="250px"
/>

<TableBuilder name="TabbedPane" />

## Best practices {#best-practices}

以下实践建议在应用程序中使用 `TabbedPane`：

- **逻辑分组**: 使用标签对相关内容进行逻辑分组
    >- 每个标签应代表应用程序中的一个独特类别或功能  
    >- 将相似或逻辑的标签放在相近位置  

- **限量标签**: 避免因标签过多而使用户感到困惑。考虑在适用时使用层次结构或其他导航模式，以保持界面的清晰

- **清晰标签**: 清晰标记您的标签以便直观使用
    >- 为每个标签提供清晰简洁的标签  
    >- 标签应反映内容或目的，使用户易于理解  
    >- 在适用情况下利用图标和不同的颜色  

- **键盘导航**: 使用 webforJ 的 `TabbedPane` 键盘导航支持，使与 `TabbedPane` 的交互对最终用户更加顺畅和直观

- **默认标签**: 如果默认标签不放在 `TabbedPane` 的开头，考虑将该标签设置为默认，以便于访问重要或常用的信息。
