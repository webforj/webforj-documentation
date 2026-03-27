---
title: TabbedPane
slug: tabbedpane
sidebar_position: 125
_i18n_hash: d1522c6bd692420a02df494fa0509a23
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-tabbed-pane" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/tabbedpane/TabbedPane" top='true'/>

多个内容区可以组织在一个单独的 `TabbedPane` 下，每个区域都与一个可点击的 `Tab` 关联。一次只能显示一个区域，选项卡可以显示文本、图标或两者结合，以帮助用户在它们之间导航。

<!-- INTRO_END -->

## 使用场景 {#usages}

`TabbedPane` 类为开发人员提供了一个强大的工具，用于在 UI 中组织和展示多个选项卡或区域。以下是一些你可能在应用程序中使用 `TabbedPane` 的典型场景：

1. **文档查看器**：实现一个文档查看器，其中每个选项卡代表一个不同的文档或文件。用户可以轻松切换打开的文档，以实现高效的多任务处理。

2. **数据管理**：利用 `TabbedPane` 来组织数据管理任务，例如：
    >- 应用程序中要显示的不同数据集
    >- 可以在单独的选项卡中显示不同的用户配置文件
    >- 用户管理系统中的不同配置文件

3. **模块选择**：`TabbedPane` 可以代表不同的模块或区域。每个选项卡可以封装特定模块的功能，使用户能够一次专注于应用程序的一个方面。

4. **任务管理**：任务管理应用程序可以使用 `TabbedPane` 来表示各种项目或任务。每个选项卡可以对应一个特定项目，允许用户单独管理和跟踪任务。

5. **程序导航**：在一个需要运行各种程序的应用程序中，`TabbedPane` 可以：
    >- 作为侧边栏，允许在一个应用程序内运行不同的应用程序或程序，如 [`AppLayout`](./app-layout.md) 模板中所示
    >- 创建一个顶部栏，起到类似的作用，或在已经选择的应用程序内表示子应用程序。

## 选项卡 {#tabs}

选项卡是可以添加到选项卡面板中的 UI 元素，用于组织和切换不同的内容视图。

:::important
选项卡并不是作为独立组件使用的。它们旨在与选项卡面板一起使用。此类不是 `Component` 并且不应作为此类使用。
:::

### 属性 {#properties}

选项卡由以下属性组成，当在 `TabbedPane` 中添加时使用。这些属性具有 getter 和 setter，以便于在 `TabbedPane` 中进行自定义。

1. **Key(`Object`)**：表示 `Tab` 的唯一标识符。

2. **Text(`String`)**：将在 `TabbedPane` 中显示为选项卡标题的文本。这也可以通过 `getTitle()` 和 `setTitle(String title)` 方法称为标题。

3. **Tooltip(`String`)**：与 `Tab` 关联的工具提示文本，当光标悬停在 `Tab` 上时会显示。

4. **Enabled(`boolean`)**：表示 `Tab` 当前是否启用。可以通过 `setEnabled(boolean enabled)` 方法进行修改。

5. **Closeable(`boolean`)**：表示 `Tab` 是否可以关闭。可以通过 `setCloseable(boolean enabled)` 方法进行修改。这将在 `Tab` 上添加一个关闭按钮，用户可以点击该按钮，并触发移除事件。`TabbedPane` 组件决定如何处理该移除。

6. **Slot(`Component`)**： 
   Slots 为提高选项卡的能力提供灵活的选项。您可以在 `Tab` 中嵌套图标、标签、加载旋转器、清除/重置功能、头像/个人资料图片以及其他有益的组件，以进一步明确用户的意图。
   您可以在构造期间向 `Tab` 的 `prefix` 插槽添加组件。或者，您可以使用 `setPrefixComponent()` 和 `setSuffixComponent()` 方法在 `Tab` 中显示的选项之前和之后插入各种组件。

        ```java
        TabbedPane pane = new TabbedPane();
        pane.addTab(new Tab("Documents", TablerIcon.create("files")));
        ```

## `Tab` 操作 {#tab-manipulation}

存在各种方法，允许开发人员添加、插入、移除以及操作 `TabbedPane` 中各种属性的 `Tab` 元素。

### 添加 `Tab` {#adding-a-tab}

`addTab()` 和 `add()` 方法以不同的重载形式存在，以允许开发人员灵活地向 `TabbedPane` 添加新选项卡。添加 `Tab` 将使其位于任何先前存在的选项卡之后。

1. **`addTab(String text)`** - 将 `Tab` 添加到 `TabbedPane`，并将指定的 `String` 作为 `Tab` 的文本。
2. **`addTab(Tab tab)`** - 将作为参数提供的 `Tab` 添加到 `TabbedPane`。
3. **`addTab(String text, Component component)`** - 添加一个 `Tab`，其给定的 `String` 作为 `Tab` 的文本，并在 `TabbedPane` 的内容部分显示提供的 `Component`。
4. **`addTab(Tab tab, Component component)`** - 添加提供的 `Tab`，并在 `TabbedPane` 的内容部分显示提供的 `Component`。
5. **`add(Component... component)`** - 向 `TabbedPane` 添加一个或多个 `Component` 实例，为每个实例创建一个独立的 `Tab`，文本设置为 `Component` 的名称。

:::info
`add(Component... component)` 通过对传入参数调用 `component.getName()` 来确定传入 `Component` 的名称。
:::

### 插入 `Tab` {#inserting-a-tab}

除了在现有选项卡的末尾添加 `Tab` 外，还可以在指定位置创建新选项卡。为此，使用 `insertTab()` 的多个重载版本。

1. **`insertTab(int index, String text)`** - 将 `Tab` 插入到 `TabbedPane` 中的给定索引，并将指定的 `String` 作为 `Tab` 的文本。
2. **`insertTab(int index, Tab tab)`** - 将作为参数提供的 `Tab` 插入到 `TabbedPane` 中的指定索引。
3. **`insertTab(int index, String text, Component component)`** - 将一个 `Tab` 插入到 `TabbedPane` 中，给定 `String` 作为 `Tab` 的文本，提供的 `Component` 在 `TabbedPane` 的内容部分显示。
4. **`insertTab(int index, Tab tab, Component component)`** - 插入提供的 `Tab`，并在 `TabbedPane` 的内容部分显示提供的 `Component`。

### 移除 `Tab` {#removing-a-tab}

要从 `TabbedPane` 中移除单个 `Tab`，可以使用以下方法之一：

1. **`removeTab(Tab tab)`** - 通过传递要移除的 Tab 实例，从 `TabbedPane` 中移除 `Tab`。
2. **`removeTab(int index)`** - 通过指定要移除的 `Tab` 的索引，从 `TabbedPane` 中移除 `Tab`。

除了上述两种方法外，还可以使用 **`removeAllTabs()`** 方法清除 `TabbedPane` 中的所有选项卡。

:::info
`remove()` 和 `removeAll()` 方法不会从组件中移除选项卡。
:::

### Tab/Component 关联 {#tabcomponent-association}

要更改给定 `Tab` 显示的 `Component`，请调用 `setComponentFor()` 方法，并传入 `Tab` 的实例或该 Tab 在 `TabbedPane` 中的索引。

:::info
如果在与 `Component` 已经关联的 `Tab` 上使用此方法，则之前关联的 `Component` 将被销毁。
:::

## 配置和布局 {#configuration-and-layout}

`TabbedPane` 类有两个组成部分：一个在指定位置显示的 `Tab` 和一个要显示的组件。这可以是单个组件，也可以是 [`Composite`](../building-ui/composite-components) 组件，从而允许在选项卡的内容部分显示更复杂的组件。

### 切换 {#swiping}

`TabbedPane` 支持通过切换在各种选项卡之间导航。这对于移动应用程序理想，但也可以通过内置方法配置以支持鼠标切换。切换和鼠标切换在默认情况下都是禁用的，但可以分别通过 `setSwipable(boolean)` 和 `setSwipableWithMouse(boolean)` 方法启用。

### Tab 放置 {#tab-placement}

`TabbedPane` 中的 `Tabs` 可以根据应用程序开发人员的偏好放置于组件中的不同位置。提供的选项使用提供的枚举进行设置，其值为 `TOP`、`BOTTOM`、`LEFT`、`RIGHT` 或 `HIDDEN`。默认设置为 `TOP`。

<ComponentDemo 
path='/webforj/tabbedpaneplacement?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPanePlacementView.java'
height="400px"
/>

### 对齐 {#alignment}

除了改变 `TabbedPane` 中 `Tab` 元素的放置外，还可以配置选项卡在组件中的对齐方式。默认情况下，设置为 `AUTO`，这允许选项卡的放置决定其对齐方式。

其他选项为 `START`、`END`、`CENTER` 和 `STRETCH`。前三种描述相对于组件的位置，而 `STRETCH` 使选项卡填充可用空间。

<ComponentDemo 
path='/webforj/tabbedpanealignment?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneAlignmentView.java'
height="250px"
/>

### 边框和活动指示器 {#border-and-activity-indicator}

默认情况下，`TabbedPane` 将显示用于其中选项卡的边框，边框位置取决于设置的 `Placement`。该边框有助于可视化面板内各选项卡占据的空间。

当点击 `Tab` 时，默认情况下，会在该 `Tab` 附近显示活动指示器，以帮助突出显示当前选中的 `Tab`。

开发人员可以通过使用适当的 setter 方法更改这两个选项。要更改是否显示边框，可以使用 `setBorderless(boolean)` 方法，传入 `true` 将隐藏边框，`false`（默认值）将显示边框。

:::info
此边框不适用于整个 `TabbedPane` 组件，仅仅作为选项卡与组件内容之间的分隔符。
:::

要设置活动指示器的可见性，可以使用 `setHideActiveIndicator(boolean)` 方法。传入 `true` 将隐藏活动 `Tab` 下的活动指示器，而 `false`（默认）保持指示器显示。

<ComponentDemo 
path='/webforj/tabbedpaneborder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneBorderView.java'
height="300px"
/>

### 激活模式 {#activation-modes}

为了对 `TabbedPane` 在用键盘导航时的行为进行更细粒度的控制，可以设置 `Activation` 模式以指定组件的行为方式。

- **`Auto`**：当设置为自动时，使用箭头键导航选项卡会立即显示相应的选项卡组件。

- **`Manual`**：当设置为手动时，选项卡将获得焦点，但不会显示，直到用户按下空格或回车。

<ComponentDemo 
path='/webforj/tabbedpaneactivation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneActivationView.java'
height="250px"
/>

### 移除选项 {#removal-options}

单个 `Tab` 元素可以设置为可关闭。可关闭的选项卡将在选项卡上添加关闭按钮，点击该按钮会触发关闭事件。`TabbedPane` 决定如何处理此行为。

- **`Manual`**：默认情况下，移除设置为 `MANUAL`，意味着该事件被触发，但由开发人员以任意方式处理此事件。

- **`Auto`**：或者，可以使用 `AUTO`，这将触发事件，并且还将从组件中移除 `Tab`，无需开发人员手动实现此行为。

## 样式 {#styling}

### 扩展和主题 {#expanse-and-theme}

`TabbedPane` 提供内置的 `Expanse` 和 `Theme` 选项，类似于其他 webforJ 组件。通过这些选项，可以快速添加能够传达各种含义的样式，而无需用 CSS 样式化组件。

<ComponentDemo 
path='/webforj/tabbedpaneexpansetheme?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneExpanseThemeView.java'
height="250px"
/>

<TableBuilder name="TabbedPane" />

## 最佳实践 {#best-practices}

以下实践建议在应用程序中使用 `TabbedPane`：

- **逻辑分组**：使用选项卡逻辑性地分组相关内容
    >- 每个选项卡应代表您应用程序中一个独特的类别或功能
    >- 将相似或逻辑的选项卡放在一起

- **有限的选项卡**：避免用过多的选项卡使用户感到困惑。考虑在适用时使用层级结构或其他导航模式，以实现干净的界面

- **清晰标签**：为选项卡清晰标记，以便用户直观使用
    >- 为每个选项卡提供清晰而简明的标签
    >- 标签应反映内容或目的，使用户容易理解
    >- 在适用情况下使用图标和不同颜色

- **键盘导航**：使用 webforJ 的 `TabbedPane` 键盘导航支持，使用户与 `TabbedPane` 的交互更加顺畅和直观

- **默认选项卡**：如果默认选项卡未放置在 `TabbedPane` 的开头，考虑将此选项卡设置为默认值，以供常用或重要信息使用。
