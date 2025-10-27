---
title: TabbedPane
slug: tabbedpane
sidebar_position: 125
_i18n_hash: ebf6bff550fd69aeb6ab8e4dfefd2323
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-tabbed-pane" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/tabbedpane/TabbedPane" top='true'/>

`TabbedPane` 类提供了一种紧凑且有组织的方式来显示分为多个部分的内容，每个部分与一个 `Tab` 相关联。用户可以通过点击各自的标签来切换这些部分，通常标签会用文本和/或图标标记。此类简化了多方面接口的创建，其中不同的内容或表单需要可访问但不同时可见。

## Usages {#usages}

`TabbedPane` 类为开发者提供了一个强大的工具，以组织和展示多个标签或部分在UI中。以下是一些典型的场景，你可能会在应用中使用 `TabbedPane`：

1. **文档查看器**：实现一个文档查看器，每个标签代表一个不同的文档或文件。用户可以轻松地在打开的文档之间切换，以提高多任务处理的效率。

2. **数据管理**：利用 `TabbedPane` 组织数据管理任务，例如：
    >- 在应用程序中显示不同的数据集
    >- 各种用户个人资料可以在不同的标签中显示
    >- 用户管理系统中的不同个人资料

3. **模块选择**：`TabbedPane` 可以表示不同的模块或部分。每个标签可以封装特定模块的功能，使用户可以一次专注于应用的一个方面。

4. **任务管理**：任务管理应用可以使用 `TabbedPane` 来表示各种项目或任务。每个标签可以对应于特定项目，使用户能够单独管理和跟踪任务。

5. **程序导航**：在需要运行各种程序的应用程序中，`TabbedPane` 可以：
    >- 作为一个侧边栏，允许在单个应用程序中运行不同的应用程序或程序，例如在 [`AppLayout`](./app-layout.md) 模板中所示
    >- 创建一个顶部栏，可以起到类似的目的或在已选择应用程序中表示子应用程序。

## Tabs {#tabs}

标签是可以添加到标签页中以组织和切换不同内容视图的UI元素。

:::important
标签并不是独立的组件。它们旨在与标签页结合使用。此类不是 `Component`，并且不应这样使用。
:::

### Properties {#properties}

标签由以下属性组成，这些属性在 `TabbedPane` 中添加时使用。这些属性具有 getter 和 setter，以便在 `TabbedPane` 中进行自定义。

1. **Key(`Object`)**：表示 `Tab` 的唯一标识符。

2. **Text(`String`)**：将在 `TabbedPane` 中作为 `Tab` 的标题显示的文本。这也通过 `getTitle()` 和 `setTitle(String title)` 方法称为标题。

3. **Tooltip(`String`)**：与 `Tab` 关联的工具提示文本，将在光标悬停在 `Tab` 上时显示。

4. **Enabled(`boolean`)**：表示 `Tab` 当前是否可用。可以通过 `setEnabled(boolean enabled)` 方法修改。

5. **Closeable(`boolean`)**：表示 `Tab` 是否可以关闭。可以通过 `setCloseable(boolean enabled)` 方法修改。这将在 `Tab` 上添加一个关闭按钮，用户可以点击该按钮，并触发移除事件。`TabbedPane` 组件决定如何处理该移除。

6. **Slot(`Component`)**： 
    插槽提供了灵活的选项来提高 `Tab` 的能力。你可以在一个 `Tab` 内嵌套图标、标签、加载旋转器、清除/重置功能、头像/个人资料图片及其他有益的组件，以进一步阐明用户的意图。
    在构造期间，可以将组件添加到 `Tab` 的 `prefix` 插槽中。或者，你可以使用 `setPrefixComponent()` 和 `setSuffixComponent()` 方法在 `Tab` 中显示选项之前和之后插入各种组件。

       ```java
       TabbedPane pane = new TabbedPane();
       pane.addTab(new Tab("Documents", TablerIcon.create("files")));
       ```

## `Tab` manipulation {#tab-manipulation}

存在各种方法，允许开发者添加、插入、移除和操作 `TabbedPane` 中 `Tab` 元素的各种属性。

### Adding a `Tab` {#adding-a-tab}

`addTab()` 和 `add()` 方法以不同的重载形式存在，允许开发者在 `TabbedPane` 中灵活地添加新标签。添加 `Tab` 将把它放在任何先前存在标签之后。

1. **`addTab(String text)`** - 用指定的 `String` 作为 `Tab` 的文本，将 `Tab` 添加到 `TabbedPane`。
2. **`addTab(Tab tab)`** - 将提供的 `Tab` 参数添加到 `TabbedPane`。
3. **`addTab(String text, Component component)`** - 添加一个 `Tab`，其文本为给定的 `String`，并在 `TabbedPane` 的内容部分显示提供的 `Component`。
4. **`addTab(Tab tab, Component component)`** - 添加提供的 `Tab` 并在 `TabbedPane` 的内容部分显示提供的 `Component`。
5. **`add(Component... component)`** - 将一个或多个 `Component` 实例添加到 `TabbedPane`，为每个实例创建一个独立的 `Tab`，文本设置为 `Component` 的名称。

:::info
`add(Component... component)` 通过调用传入参数上的 `component.getName()` 来确定传入 `Component` 的名称。
:::

### Inserting a `Tab` {#inserting-a-tab}

除了在现有标签的末尾添加 `Tab` 外，还可以在指定位置创建一个新的标签。为此，提供多个重载版本的 `insertTab()`。

1. **`insertTab(int index, String text)`** - 在给定索引处向 `TabbedPane` 插入一个 `Tab`，其文本为指定的 `String`。
2. **`insertTab(int index, Tab tab)`** - 在指定索引处向 `TabbedPane` 插入提供的 `Tab`。
3. **`insertTab(int index, String text, Component component)`** - 插入一个 `Tab`，其文本为给定的 `String`，并显示在 `TabbedPane` 的内容部分提供的 `Component`。
4. **`insertTab(int index, Tab tab, Component component)`** - 插入提供的 `Tab`，并在 `TabbedPane` 的内容部分显示提供的 `Component`。

### Removing a `Tab` {#removing-a-tab}

要从 `TabbedPane` 中移除单个 `Tab`，可以使用以下方法之一：

1. **`removeTab(Tab tab)`** - 通过传递要移除的 `Tab` 实例从 `TabbedPane` 中移除一个 `Tab`。
2. **`removeTab(int index)`** - 通过指定要移除的 `Tab` 的索引，从 `TabbedPane` 中移除一个 `Tab`。

除了上述两种方法外，可以使用 **`removeAllTabs()`** 方法清除 `TabbedPane` 中的所有标签。

:::info
`remove()` 和 `removeAll()` 方法不会移除组件中的选项卡。
:::

### Tab/Component association {#tabcomponent-association}

要更改与给定 `Tab` 关联的 `Component`，可以调用 `setComponentFor()` 方法，并传递 `Tab` 的实例或该 `Tab` 在 `TabbedPane` 中的索引。

:::info
如果此方法在已经与 `Component` 关联的 `Tab` 上使用，则先前关联的 `Component` 将被销毁。
:::

## Configuration and layout {#configuration-and-layout}

`TabbedPane` 类有两个组成部分：一个在指定位置显示的 `Tab` 和一个要显示的组件。它可以是单个组件，或 [`Composite`](../building-ui/composite-components) 组件，允许在标签内容部分显示更复杂的组件。

### Swiping {#swiping}

`TabbedPane` 支持通过滑动在各个标签之间导航。这对于移动应用程序理想，但也可以通过内置方法配置以支持鼠标滑动。滑动和鼠标滑动默认是禁用的，但可以通过 `setSwipable(boolean)` 和 `setSwipableWithMouse(boolean)` 方法分别启用。

### Tab placement {#tab-placement}

`TabbedPane` 中的 `Tabs` 可以根据应用程序开发者的偏好放置在组件的不同位置。提供的选项通过提供的枚举设置，该枚举的值为 `TOP`、`BOTTOM`、`LEFT`、`RIGHT` 或 `HIDDEN`。默认设置为 `TOP`。

<ComponentDemo 
path='/webforj/tabbedpaneplacement?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPanePlacementView.java'
height="400px"
/>

### Alignment {#alignment}

除了更改 `TabbedPane` 中 `Tab` 元素的位置之外，还可以配置标签在组件内的对齐方式。默认情况下，设置为 `AUTO`，允许标签的放置决定其对齐。

其他选项为 `START`、`END`、`CENTER` 和 `STRETCH`。前三个描述相对于组件的位置，而 `STRETCH` 则使标签填充可用空间。

<ComponentDemo 
path='/webforj/tabbedpanealignment?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneAlignmentView.java'
height="250px"
/>

### Border and activity indicator {#border-and-activity-indicator}

`TabbedPane` 默认情况下会为其中的标签显示边框，具体位置取决于设置的 `Placement`。该边框有助于可视化各标签在面板中所占的空间。

点击某个 `Tab` 时，默认情况下，会在该 `Tab` 附近显示一个活动指示器，以帮助突出当前选中的 `Tab`。

这两个选项都可以通过开发者更改布尔值来进行自定义，使用适当的 setter 方法来修改是否显示边框，可以使用 `setBorderless(boolean)` 方法，传递 `true` 隐藏边框，而 `false`（默认值）则显示边框。

:::info
此边框不适用于整个 `TabbedPane` 组件，仅作为标签与组件内容之间的分隔符。
:::

要设置活动指示器的可见性，可以使用 `setHideActiveIndicator(boolean)` 方法。将 `true` 传递给该方法将在活动 `Tab` 下隐藏活动指示器，而 `false`（默认值）将保持指示器显示。

<ComponentDemo 
path='/webforj/tabbedpaneborder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneBorderView.java'
height="300px"
/>

### Activation modes {#activation-modes}

为了更细致地控制 `TabbedPane` 在通过键盘导航时的行为，`Activation` 模式可以设置为指定组件应如何行为。

- **`Auto`**：当设置为自动时，使用箭头键导航标签将立即显示相应的标签组件。

- **`Manual`**：当设置为手动时，标签将获得焦点，但不会显示，直到用户按下空格或回车。

<ComponentDemo 
path='/webforj/tabbedpaneactivation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneActivationView.java'
height="250px"
/>

### Removal options {#removal-options}

单个 `Tab` 元素可以设置为可关闭。可关闭的标签将为标签添加一个关闭按钮，点击时会触发关闭事件。`TabbedPane` 决定如何处理此行为。

- **`Manual`**：默认情况下，移除设置为 `MANUAL`，这意味着事件会被触发，但由开发者以他们选择的任何方式处理此事件。

- **`Auto`**：另外，可以使用 `AUTO`，这将触发事件，并且会将 `Tab` 从组件中移除，免去开发者手动实现此行为的需要。

## Styling {#styling}

### Expanse and theme {#expanse-and-theme}

`TabbedPane` 具有内置的 `Expanse` 和 `Theme` 选项，类似于其他 webforJ 组件。可以迅速使用这些选项添加样式，以向最终用户传达各种意义，而无需使用 CSS 来样式化组件。

<ComponentDemo 
path='/webforj/tabbedpaneexpansetheme?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneExpanseThemeView.java'
height="250px"
/>

<TableBuilder name="TabbedPane" />

## Best practices {#best-practices}

以下实践建议在应用程序中使用 `TabbedPane`：

- **逻辑分组**：使用标签来逻辑上分组相关内容
    >- 每个标签应表示您应用程序中的一个不同类别或功能
    >- 将相似或逻辑的标签相互靠近分组

- **限制标签数量**：避免用太多标签让用户感到不知所措。考虑使用分层结构或其他导航模式来保持界面的整洁

- **清晰标签**：为您的标签清晰命名以便直观使用
    >- 为每个标签提供清晰简洁的标签
    >- 标签应反映内容或目的，使用户易于理解
    >- 在适用的情况下利用图标和明显的颜色

- **键盘导航**：利用 webforJ 的 `TabbedPane` 键盘导航支持，使与 `TabbedPane` 的交互对最终用户更加无缝和直观

- **默认标签**：如果默认标签不放在 `TabbedPane` 的开头，则考虑将该标签设置为默认标签，以显示重要或常用的信息。
