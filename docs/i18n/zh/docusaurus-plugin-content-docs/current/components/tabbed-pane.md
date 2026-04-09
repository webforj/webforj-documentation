---
title: TabbedPane
slug: tabbedpane
sidebar_position: 125
_i18n_hash: f903eeae452aae41b3eb04c170b9e98e
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-tabbed-pane" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/tabbedpane/TabbedPane" top='true'/>

多个内容部分可以组织在一个 `TabbedPane` 下，每个部分与一个可点击的 `Tab` 相关联。一次只显示一个部分，标签可以显示文本、图标或两者结合，以帮助用户在它们之间导航。

## 用法 {#usages}

`TabbedPane` 类为开发者提供了一种强大的工具，用于在用户界面中组织和呈现多个标签或部分。以下是一些典型的场景，你可能在应用程序中利用 `TabbedPane`：

1. **文档查看器**：实现一个文档查看器，其中每个标签代表一个不同的文档或文件。用户可以轻松切换打开的文档，以便高效地进行多任务处理。

2. **数据管理**：利用 `TabbedPane` 来组织数据管理任务，例如：
    >- 在应用程序中显示不同的数据集
    >- 各种用户配置文件可以在单独的标签中显示
    >- 用户管理系统中的不同档案

3. **模块选择**：`TabbedPane` 可以表示不同的模块或部分。每个标签可以封装特定模块的功能，让用户一次专注于应用程序的一个方面。

4. **任务管理**：任务管理应用程序可以使用 `TabbedPane` 来表示各种项目或任务。每个标签可以对应一个特定项目，使用户能够单独管理和跟踪任务。

5. **程序导航**：在需要运行各种程序的应用程序中，`TabbedPane` 可以：
    >- 作为侧边栏，允许在单个应用程序内运行不同的应用程序或程序，例如在 [`AppLayout`](./app-layout.md) 模板中所示
    >- 创建一个顶栏，可以服务类似的目的，或在已选择的应用程序内表示子应用程序。

## 标签 {#tabs}

标签是可以添加到标签面板中以组织和切换不同内容视图的用户界面元素。

:::important
标签不应作为独立组件使用。它们应与标签面板结合使用。这个类不是一个 `Component`，不应如此使用。
:::

### 属性 {#properties}

标签由以下属性组成，这些属性在将它们添加到 `TabbedPane` 时使用。这些属性具有 getter 和 setter，以便在 `TabbedPane` 中实现自定义。

1. **Key(`Object`)**：表示 `Tab` 的唯一标识符。

2. **Text(`String`)**：作为 `Tab` 在 `TabbedPane` 中的标题要显示的文本。这也通过 `getTitle()` 和 `setTitle(String title)` 方法引用为标题。

3. **Tooltip(`String`)**：与 `Tab` 关联的工具提示文本，当光标悬停在 `Tab` 上时将显示。

4. **Enabled(`boolean`)**：表示 `Tab` 当前是否启用。可以通过 `setEnabled(boolean enabled)` 方法进行修改。

5. **Closeable(`boolean`)**：表示 `Tab` 是否可以关闭。可以通过 `setCloseable(boolean enabled)` 方法进行修改。这将为 `Tab` 添加一个可以供用户点击的关闭按钮，并触发移除事件。`TabbedPane` 组件决定如何处理该移除。

6. **Slot(`Component`)**：
    插槽提供了灵活的选项来改善 `Tab` 的功能。您可以在 `Tab` 中嵌套图标、标签、加载旋转器、清除/重置功能、头像/个人资料图片及其他有益组件，以进一步清晰地传达给用户。
    您可以在构建时将组件添加到 `Tab` 的 `prefix` 插槽中。或者，您可以使用 `setPrefixComponent()` 和 `setSuffixComponent()` 方法在 `Tab` 内显示的选项之前和之后插入各种组件。

        ```java
        TabbedPane pane = new TabbedPane();
        pane.addTab(new Tab("Documents", TablerIcon.create("files")));
        ```

## `Tab` 操作 {#tab-manipulation}

存在多种方法，允许开发者在 `TabbedPane` 中添加、插入、移除和操作 `Tab` 元素的各种属性。

### 添加一个 `Tab` {#adding-a-tab}

`addTab()` 和 `add()` 方法以不同的重载形式存在，以便开发者在向 `TabbedPane` 添加新标签时提供灵活性。添加一个 `Tab` 会将其放置在任何先前存在的标签之后。

1. **`addTab(String text)`** - 在 `TabbedPane` 中添加一个 `Tab`，以指定的 `String` 作为 `Tab` 的文本。
2. **`addTab(Tab tab)`** - 将作为参数提供的 `Tab` 添加到 `TabbedPane`。
3. **`addTab(String text, Component component)`** - 添加一个 `Tab`，其文本为给定的 `String`，并在 `TabbedPane` 的内容部分显示提供的 `Component`。
4. **`addTab(Tab tab, Component component)`** - 添加提供的 `Tab` 并在 `TabbedPane` 的内容部分显示提供的 `Component`。
5. **`add(Component... component)`** - 向 `TabbedPane` 添加一个或多个 `Component` 实例，为每个标签创建一个独立的 `Tab`，文本设置为 `Component` 的名称。

:::info
`add(Component... component)` 通过在传递的参数上调用 `component.getName()` 来确定所传递的 `Component` 的名称。
:::

### 插入一个 `Tab` {#inserting-a-tab}

除了在现有标签的末尾添加 `Tab` 之外，还可以在指定位置创建一个新标签。为此，提供多种重载版本的 `insertTab()`。

1. **`insertTab(int index, String text)`** - 在给定的索引处将 `Tab` 插入 `TabbedPane`，以指定的 `String` 作为标签的文本。
2. **`insertTab(int index, Tab tab)`** - 在指定的索引处将作为参数提供的 `Tab` 插入到 `TabbedPane`。
3. **`insertTab(int index, String text, Component component)`** - 插入一个 `Tab`，其文本为给定的 `String`，并在 `TabbedPane` 的内容部分显示提供的 `Component`。
4. **`insertTab(int index, Tab tab, Component component)`** - 插入提供的 `Tab` 并在 `TabbedPane` 的内容部分显示提供的 `Component`。

### 移除一个 `Tab` {#removing-a-tab}

要从 `TabbedPane` 中移除单个 `Tab`，使用以下方法之一：

1. **`removeTab(Tab tab)`** - 通过传递要移除的 `Tab` 实例来从 `TabbedPane` 移除 `Tab`。
2. **`removeTab(int index)`** - 通过指定要移除的 `Tab` 的索引从 `TabbedPane` 移除 `Tab`。

除了上述两个方法，可以使用 **`removeAllTabs()`** 方法清除 `TabbedPane` 中的所有标签。

:::info
`remove()` 和 `removeAll()` 方法不会删除组件内的标签。
:::

### 标签/组件关联 {#tabcomponent-association}

若要更改要为给定 `Tab` 显示的 `Component`，请调用 `setComponentFor()` 方法，并传递该 `Tab` 的实例或在 `TabbedPane` 中该标签的索引。

:::info
如果在已经与 `Component` 关联的 `Tab` 上使用此方法，则先前关联的 `Component` 将被销毁。
:::

## 配置和布局 {#configuration-and-layout}

`TabbedPane` 类由两个组成部分组成：在指定位置显示的 `Tab` 和要显示的组件。这可以是单个组件，也可以是 [`Composite`](../building-ui/composite-components) 组件，允许在标签的内容部分显示更复杂的组件。

### 滑动 {#swiping}

`TabbedPane` 支持通过滑动在不同标签之间导航。这对于移动应用程序非常理想，但也可以通过内置的方法配置以支持鼠标滑动。滑动和鼠标滑动默认情况下是禁用的，可以分别使用 `setSwipable(boolean)` 和 `setSwipableWithMouse(boolean)` 方法启用。

### 标签位置 {#tab-placement}

`TabbedPane` 中的 `Tabs` 可以根据应用程序开发人员的偏好放置在组件的不同位置。提供的选项使用提供的枚举设置，其值为 `TOP`、`BOTTOM`、`LEFT`、`RIGHT` 或 `HIDDEN`。默认设置为 `TOP`。

<ComponentDemo 
path='/webforj/tabbedpaneplacement?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPanePlacementView.java'
height="400px"
/>

### 对齐 {#alignment}

除了改变 `TabbedPane` 中 `Tab` 元素的位置外，还可以配置标签在组件内的对齐方式。默认情况下，设置为 `AUTO`，这允许标签的放置决定它们的对齐方式。

其他选项是 `START`、`END`、`CENTER` 和 `STRETCH`。前三种描述相对于组件的位置，`STRETCH` 使标签充满可用空间。

<ComponentDemo 
path='/webforj/tabbedpanealignment?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneAlignmentView.java'
height="250px"
/>

### 边框和活动指示器 {#border-and-activity-indicator}

默认情况下，`TabbedPane` 将为其中的标签显示边框，边框的位置取决于设置的 `Placement`。这个边框有助于可视化面板中各个标签所占的空间。

单击 `Tab` 时，默认情况下，会在该 `Tab` 附近显示一个活动指示器，以帮助突出显示当前选定的 `Tab`。

这两个选项可以通过开发者使用适当的 setter 方法进行自定义。要更改是否显示边框，可以使用 `setBorderless(boolean)` 方法，其中 `true` 隐藏边框，`false`（默认值）显示边框。

:::info
该边框不适用于整个 `TabbedPane` 组件，仅用作标签与组件内容之间的分隔符。
:::

要设置活动指示器的可见性，可以使用 `setHideActiveIndicator(boolean)` 方法。将 `true` 传递给此方法将隐藏活动 `Tab` 下的活动指示器，而 `false`（默认值）则保持指示器显示。

<ComponentDemo 
path='/webforj/tabbedpaneborder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneBorderView.java'
height="300px"
/>

### 激活模式 {#activation-modes}

为了更细粒度地控制 `TabbedPane` 在通过键盘导航时的行为，可以设置 `Activation` 模式以指定组件的行为。

- **`Auto`**：设置为自动时，用箭头键导航标签会立即显示相应的标签组件。

- **`Manual`**：设置为手动时，标签将获得焦点，但在用户按下空格或回车之前不会显示。

<ComponentDemo 
path='/webforj/tabbedpaneactivation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneActivationView.java'
height="250px"
/>

### 移除选项 {#removal-options}

单独的 `Tab` 元素可以设置为可关闭。可关闭的标签将添加一个关闭按钮，该按钮在点击时触发关闭事件。`TabbedPane` 决定如何处理这一行为。

- **`Manual`**：默认情况下，移除设置为 `MANUAL`，这意味着事件会被触发，但由开发人员自行处理该事件。

- **`Auto`**：另外，可以使用 `AUTO`，这会触发事件，并自动从组件中移除 `Tab`，不再需要开发人员手动实现此行为。

## 样式 {#styling}

### 扩展和主题 {#expanse-and-theme}

`TabbedPane` 具有内置的 `Expanse` 和 `Theme` 选项，类似于其他 webforJ 组件。这些可以快速添加样式，向最终用户传达各种含义，而无需使用 CSS 样式化组件。

<ComponentDemo 
path='/webforj/tabbedpaneexpansetheme?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneExpanseThemeView.java'
height="250px"
/>

<TableBuilder name={['Tab', 'TabbedPane']} />

## 最佳实践 {#best-practices}

在应用程序中使用 `TabbedPane` 时，建议遵循以下实践：

- **逻辑分组**：使用标签逻辑分组相关内容
    >- 每个标签应代表应用程序中一个独特的类别或功能
    >- 将相似或逻辑上的标签放在一起

- **标签数量限制**：避免用过多的标签给用户带来压力。考虑在适用时使用层次结构或其他导航模式，从而保持清晰的界面。

- **清晰的标签**：为您的标签清晰命名，以便直观使用
    >- 为每个标签提供清晰简洁的标签
    >- 标签应反映内容或目的，使用户易于理解
    >- 在适用时利用图标和不同的颜色

- **键盘导航**：使用 webforJ 的 `TabbedPane` 键盘导航支持，使最终用户与 `TabbedPane` 的交互更加顺畅和直观。

- **默认标签**：如果默认标签未放置在 `TabbedPane` 的开头，考虑将此标签设置为默认值，以便提供重要或常用的信息。
