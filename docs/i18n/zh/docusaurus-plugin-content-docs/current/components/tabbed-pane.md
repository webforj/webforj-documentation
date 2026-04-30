---
title: TabbedPane
slug: tabbedpane
sidebar_position: 125
sidebar_class_name: new-content
_i18n_hash: 790dce3f2bce2da54e03b7407c11204b
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-tabbed-pane" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/tabbedpane/TabbedPane" top='true'/>

多个内容部分可以在一个 `TabbedPane` 下组织，其中每个部分都与一个可单击的 `Tab` 相关联。一次只能显示一个部分，标签可以显示文本、图标或两者，以帮助用户在它们之间进行导航。

<!-- INTRO_END -->

## 用法 {#usages}

`TabbedPane` 类为开发人员提供了一个强大的工具，用于在 UI 中组织和呈现多个标签或部分。以下是一些您可能在应用程序中利用 `TabbedPane` 的典型场景：

1. **文档查看器**：实现一个文档查看器，其中每个标签代表一个不同的文档或文件。用户可以轻松在打开的文档之间切换，以提高工作效率。

2. **数据管理**：利用 `TabbedPane` 来组织数据管理任务，例如：
    >- 应用程序中显示不同的数据集
    >- 可以在单独的标签中显示各种用户配置文件
    >- 用户管理系统中的不同配置文件

3. **模块选择**：`TabbedPane` 可以表示不同的模块或部分。每个标签可以封装特定模块的功能，使用户能够集中精力处理应用程序的一个方面。

4. **任务管理**：任务管理应用程序可以使用 `TabbedPane` 来表示各种项目或任务。每个标签可以对应于一个特定项目，使用户能够单独管理和跟踪任务。

5. **程序导航**：在需要运行各种程序的应用程序中，`TabbedPane` 可以：
    >- 作为侧边栏，允许在单个应用程序内运行不同的应用程序或程序，如在 [`AppLayout`](./app-layout.md) 模板中所示
    >- 创建一个顶部条，具有类似的功能，或在已选择的应用程序内表示子应用程序。

## 标签 {#tabs}

标签是可以添加到选项卡窗格中的 UI 元素，用于组织和切换不同的内容视图。

:::important
标签并不打算作为独立组件使用。它们应该与选项卡窗格一起使用。此类不是 `Component`，不应如此使用。
:::

### 属性 {#properties}

标签由以下属性组成，这些属性在 `TabbedPane` 中添加时使用。这些属性具有获取器和设置器，以方便在 `TabbedPane` 中进行自定义。

1. **Key(`Object`)**：表示 `Tab` 的唯一标识符。

2. **Text(`String`)**：将显示为 `TabbedPane` 中 `Tab` 标题的文本。这也通过 `getTitle()` 和 `setTitle(String title)` 方法称为标题。

3. **Tooltip(`String`)**：与 `Tab` 关联的工具提示文本，当光标悬停在 `Tab` 上时将显示。

4. **Enabled(`boolean`)**：表示 `Tab` 当前是否启用。可以通过 `setEnabled(boolean enabled)` 方法进行修改。

5. **Closeable(`boolean`)**：表示 `Tab` 是否可以关闭。可以通过 `setCloseable(boolean enabled)` 方法进行修改。这将在 `Tab` 上添加一个关闭按钮，用户可以单击此按钮，并触发一个移除事件。`TabbedPane` 组件决定如何处理移除。

6. **Slot(`Component`)**： 
    插槽提供灵活的选项，以提高 `Tab` 的能力。您可以在 `Tab` 中嵌套图标、标签、加载旋转器、清除/重置能力、头像/个人资料图片及其他有益组件，以进一步向用户阐明预期含义。
    您可以在构造期间将组件添加到 `Tab` 的 `prefix` 插槽。或者，您可以使用 `setPrefixComponent()` 和 `setSuffixComponent()` 方法在 `Tab` 中显示的选项之前和之后插入各种组件。

        ```java
        TabbedPane pane = new TabbedPane();
        pane.addTab(new Tab("Documents", TablerIcon.create("files")));
        ```

## `Tab` 操作 {#tab-manipulation}

存在各种方法，允许开发人员添加、插入、移除和操作 `TabbedPane` 中 `Tab` 元素的各种属性。

### 添加一个 `Tab` {#adding-a-tab}

`addTab()` 和 `add()` 方法以不同的重载方式存在，以便允许开发人员在 `TabbedPane` 中灵活添加新标签。添加一个 `Tab` 将其放置在任何先前存在的标签之后。

1. **`addTab(String text)`** - 用指定的 `String` 作为 `Tab` 的文本，将 `Tab` 添加到 `TabbedPane` 中。
2. **`addTab(Tab tab)`** - 将作为参数提供的 `Tab` 添加到 `TabbedPane` 中。
3. **`addTab(String text, Component component)`** - 用给定的 `String` 作为 `Tab` 的文本，将一个 `Tab` 添加到 `TabbedPane` 中，并在 `TabbedPane` 的内容部分显示提供的 `Component`。
4. **`addTab(Tab tab, Component component)`** - 添加提供的 `Tab` 并在 `TabbedPane` 的内容部分显示提供的 `Component`。
5. **`add(Component... component)`** - 将一个或多个 `Component` 实例添加到 `TabbedPane` 中，为每个实例创建一个离散的 `Tab`，文本设置为 `Component` 的名称。

:::info
`add(Component... component)` 方法通过调用 `component.getName()` 来确定所传递 `Component` 的名称。
:::

### 插入一个 `Tab` {#inserting-a-tab}

除了在现有标签的末尾添加 `Tab` 外，还可以在指定位置创建一个新的 `Tab`。为此，存在多个重载版本的 `insertTab()`。

1. **`insertTab(int index, String text)`** - 在给定索引处将 `Tab` 插入 `TabbedPane`，并以指定的 `String` 作为 `Tab` 的文本。
2. **`insertTab(int index, Tab tab)`** - 在指定索引处将作为参数提供的 `Tab` 插入 `TabbedPane`。
3. **`insertTab(int index, String text, Component component)`** - 在给定索引处插入一个 `Tab`，以 `String` 作为 `Tab` 的文本，并在 `TabbedPane` 的内容部分显示提供的 `Component`。
4. **`insertTab(int index, Tab tab, Component component)`** - 插入提供的 `Tab`，并在 `TabbedPane` 的内容部分显示提供的 `Component`。

### 移除一个 `Tab` {#removing-a-tab}

要从 `TabbedPane` 中移除单个 `Tab`，请使用以下方法之一：

1. **`removeTab(Tab tab)`** - 通过传递要移除的 Tab 实例，从 `TabbedPane` 中移除一个 `Tab`。
2. **`removeTab(int index)`** - 通过指定要移除的 `Tab` 的索引，从 `TabbedPane` 中移除一个 `Tab`。

除了上述两种移除单个 `Tab` 的方法外，还可以使用 **`removeAllTabs()`** 方法清空 `TabbedPane` 中的所有标签。

:::info
`remove()` 和 `removeAll()` 方法不会移除组件内的标签。
:::

### Tab/组件关联 {#tabcomponent-association}

要更改给定 `Tab` 显示的 `Component`，请调用 `setComponentFor()` 方法，并传递该 `Tab` 的实例或该 `Tab` 在 `TabbedPane` 中的索引。

:::info
如果在已与 `Component` 关联的 `Tab` 上使用此方法，先前关联的 `Component` 将被销毁。
:::

## 配置和布局 {#configuration-and-layout}

`TabbedPane` 类有两个组成部分：在指定位置显示的 `Tab` 和要显示的组件。这可以是单个组件，或者是 [`Composite`](../building-ui/composite-components) 组件，允许在标签的内容部分显示更复杂的组件。

### 滑动 {#swiping}

`TabbedPane` 支持通过滑动在各种标签之间导航。这对于移动应用程序理想，但也可以通过内置方法配置为支持鼠标滑动。滑动和鼠标滑动默认都是禁用的，但可以分别通过 `setSwipable(boolean)` 和 `setSwipableWithMouse(boolean)` 方法启用。

### 标签位置 {#tab-placement}

`TabbedPane` 中的 `Tabs` 可以根据应用程序开发人员的喜好放置在组件的不同位置。提供的选项使用提供的枚举设置，枚举具有 `TOP`、`BOTTOM`、`LEFT`、`RIGHT` 或 `HIDDEN` 的值。默认设置为 `TOP`。

<ComponentDemo 
path='/webforj/tabbedpaneplacement?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPanePlacementView.java'
height="400px"
/>

### 对齐 {#alignment}

除了更改 `TabbedPane` 中 `Tab` 元素的放置方式外，还可以配置标签在组件内的对齐方式。默认情况下，设置为 `AUTO`，允许标签的位置决定其对齐方式。

其他选项有 `START`、`END`、`CENTER` 和 `STRETCH`。前三个描述相对于组件的位置，而 `STRETCH` 使标签填充可用空间。

<ComponentDemo 
path='/webforj/tabbedpanealignment?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneAlignmentView.java'
height="250px"
/>

### 边框和活动指示器 {#border-and-activity-indicator}

默认情况下，`TabbedPane` 将对其中的标签显示边框，位置取决于设置的 `Placement`。此边框有助于可视化选项卡窗格内各种标签所占用的空间。

当单击 `Tab` 时，默认情况下，将在该 `Tab` 附近显示活动指示器，以帮助突出显示当前选定的 `Tab`。

开发人员可以通过使用适当的设置方法更改这两个选项的值。要更改是否显示边框，可以使用 `setBorderless(boolean)` 方法，`true` 隐藏边框，而 `false`（默认值）将显示边框。

:::info
该边框不适用于整个 `TabbedPane` 组件，仅作为标签与组件内容之间的分隔符。
:::

要设置活动指示器的可见性，可以使用 `setHideActiveIndicator(boolean)` 方法。向此方法传递 `true` 将隐藏处于活动状态的 `Tab` 下的活动指示器，而 `false`（默认值）则保持指示器显示。

<ComponentDemo 
path='/webforj/tabbedpaneborder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneBorderView.java'
height="300px"
/>

### 激活模式 {#activation-modes}

为了更精细地控制 `TabbedPane` 在键盘导航时的行为，可以设置 `Activation` 模式以指定组件应如何行为。

- **`自动`**：设置为自动时，使用箭头键导航标签将立即显示相应的标签组件。

- **`手动`**：设置为手动时，标签将获得焦点，但在用户按空格或回车之前不会显示。

<ComponentDemo 
path='/webforj/tabbedpaneactivation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneActivationView.java'
height="250px"
/>

### 移除选项 {#removal-options}

可以设置单个 `Tab` 元素为可关闭。可关闭的标签将在标签上添加一个关闭按钮，点击时会触发关闭事件。`TabbedPane` 决定如何处理此行为。

- **`手动`**：默认情况下，移除设置为 `MANUAL`，意味着事件被触发，但由开发人员决定以任何方式处理该事件。

- **`自动`**：或者，可以使用 `AUTO` 来触发事件，同时也会将 `Tab` 从组件中移除，开发人员无需手动实现此行为。

### 段控制 <DocChip chip='since' label='26.00' /> {#segment-control}

通过使用 `setSegment(true)` 启用 `segment` 属性，可以将 `TabbedPane` 渲染为段控制。在此模式下，标签将以滑动药丸指示器显示，以突出显示活动选择，提供与标准标签界面紧凑的替代方案。

<ComponentDemo 
path='/webforj/tabbedpanesegment?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneSegmentView.java'
height="250px"
/>

## 样式 {#styling}

### 扩展和主题 {#expanse-and-theme}

`TabbedPane` 附带内置的 `Expanse` 和 `Theme` 选项，类似于其他 webforJ 组件。这些可以用于快速添加样式，向最终用户传达各种含义，而无需使用 CSS 对组件进行样式设置。

<ComponentDemo 
path='/webforj/tabbedpaneexpansetheme?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneExpanseThemeView.java'
height="250px"
/>

<TableBuilder name={['Tab', 'TabbedPane']} />

## 最佳实践 {#best-practices}

建议按照以下实践在应用程序中使用 `TabbedPane`：

- **逻辑分组**：使用标签将相关内容进行逻辑分组
    >- 每个标签应代表您应用程序中的一个独特类别或功能
    >- 将相似或逻辑相关的标签放近一些

- **限制标签数量**：避免用过多标签让用户感到不知所措。考虑使用层次结构或其他导航模式，以实现干净的界面

- **清晰标签**：清楚地标记您的标签，以直观使用
    >- 为每个标签提供清晰和简洁的标签
    >- 标签应反映内容或目的，便于用户理解
    >- 在适用时使用图标和不同的颜色

- **键盘导航**：使用 webforJ 的 `TabbedPane` 键盘导航支持，使用户与 `TabbedPane` 的交互更加无缝和直观

- **默认标签**：如果默认标签未放置在 `TabbedPane` 的开头，考虑将该标签设置为默认值，以获取重要或常用信息。
