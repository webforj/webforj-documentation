---
title: TabbedPane
slug: tabbedpane
sidebar_position: 125
sidebar_class_name: new-content
_i18n_hash: 0b623c02434c6f0d140de0ade3a22c5d
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-tabbed-pane" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/tabbedpane/TabbedPane" top='true'/>

多个内容部分可以组织在一个 `TabbedPane` 下，每个部分与一个可点击的 `Tab` 相关联。一次只显示一个部分，标签可以显示文本、图标或两者，以帮助用户进行导航。

<!-- INTRO_END -->

## 用法 {#usages}

`TabbedPane` 类为开发者提供了一个强大的工具，用于在 UI 中组织和展示多个选项卡或部分。以下是一些在应用程序中可能使用 `TabbedPane` 的典型场景：

1. **文档查看器**：实现文档查看器，其中每个选项卡代表不同的文档或文件。用户可以轻松地在打开的文档之间切换，以实现高效的多任务处理。

2. **数据管理**：利用 `TabbedPane` 来组织数据管理任务，例如：
    >- 在应用程序中显示不同的数据集
    >- 各种用户档案可以在单独的选项卡中显示
    >- 用户管理系统中的不同档案

3. **模块选择**：`TabbedPane` 可以表示不同的模块或部分。每个选项卡可以封装特定模块的功能，使用户能够集中在应用程序的一个方面。

4. **任务管理**：任务管理应用程序可以使用 `TabbedPane` 来表示各种项目或任务。每个选项卡可以对应于一个特定项目，使用户能够分别管理和跟踪任务。

5. **程序导航**：在需要运行各种程序的应用程序中，`TabbedPane` 可以：
    >- 作为侧边栏，允许在单个应用程序内运行不同的应用程序或程序，例如 [`AppLayout`](./app-layout.md) 模板中所示
    >- 创建一个顶部条，具有相似的目的，或表示在已选择的应用程序内的子应用程序。

## 选项卡 {#tabs}

选项卡是可以添加到选项卡面板中的 UI 元素，用于组织和切换不同的内容视图。

:::important
选项卡并不是作为独立组件使用的。它们是与选项卡面板结合使用的。这个类不是一个 `Component` ，不应如此使用。
:::

### 属性 {#properties}

选项卡由以下属性组成，这些属性在选项卡面板中添加时使用。这些属性具有获取器和设置器，以便在选项卡面板中进行自定义。

1. **Key(`Object`)**：表示 `Tab` 的唯一标识符。

2. **Text(`String`)**：将在选项卡面板中作为 `Tab` 标题显示的文本。这也通过 `getTitle()` 和 `setTitle(String title)` 方法称为标题。

3. **Tooltip(`String`)**：与 `Tab` 关联的工具提示文本，当光标悬停在 `Tab` 上时显示。

4. **Enabled(`boolean`)**：表示 `Tab` 当前是否启用。可以使用 `setEnabled(boolean enabled)` 方法修改。

5. **Closeable(`boolean`)**：表示 `Tab` 是否可以关闭。可以使用 `setCloseable(boolean enabled)` 方法修改。这将在选项卡上添加一个可单击的关闭按钮，并触发一个移除事件。`TabbedPane` 组件决定如何处理移除。

6. **Slot(`Component`)**： 
    插槽提供了灵活的选项，以提高选项卡的能力。您可以在 `Tab` 内嵌入图标、标签、加载旋转器、清除/重置功能、头像/个人资料照片及其他有益组件，以进一步明确用户的意图。
    您可以在构造时将组件添加到 `Tab` 的前缀插槽。或者，您可以使用 `setPrefixComponent()` 和 `setSuffixComponent()` 方法在选项卡内显示选项之前或之后插入各种组件。

        ```java
        TabbedPane pane = new TabbedPane();
        pane.addTab(new Tab("Documents", TablerIcon.create("files")));
        ```

## `Tab` 操作 {#tab-manipulation}

存在多种方法允许开发者在 `TabbedPane` 内部添加、插入、移除和操作各种选项卡元素的属性。

### 添加选项卡 {#adding-a-tab}

`addTab()` 和 `add()` 方法以不同的重载方式存在，以允许开发者灵活地将新的选项卡添加到 `TabbedPane` 中。添加选项卡将在任何先前存在的选项卡之后放置它。

1. **`addTab(String text)`** - 使用指定的 `String` 作为选项卡文本，将选项卡添加到选项卡面板中。
2. **`addTab(Tab tab)`** - 将提供的选项卡作为参数添加到选项卡面板中。
3. **`addTab(String text, Component component)`** - 添加一个具有指定 `String` 作为选项卡文本的选项卡，并在选项卡面板的内容部分显示提供的组件。
4. **`addTab(Tab tab, Component component)`** - 将提供的选项卡添加并在选项卡面板的内容部分显示提供的组件。
5. **`add(Component... component)`** - 将一个或多个组件实例添加到选项卡面板中，为每个组件创建一个独立的选项卡，文本设置为组件的名称。

:::info
`add(Component... component)` 通过调用传递参数的 `component.getName()` 方法来判断传递组件的名称。
:::

### 插入选项卡 {#inserting-a-tab}

除了在现有选项卡的末尾添加选项卡外，还可以在指定位置创建一个新的选项卡。为此，提供了多个重载版本的 `insertTab()`。

1. **`insertTab(int index, String text)`** - 将选项卡插入到指定索引的 `TabbedPane` 中，使用指定的 `String` 作为选项卡文本。
2. **`insertTab(int index, Tab tab)`** - 将提供的选项卡作为参数插入到指定索引的选项卡面板中。
3. **`insertTab(int index, String text, Component component)`** - 在选项卡面板的内容部分插入给定 `String` 作为选项卡文本的选项卡，以及提供的组件。
4. **`insertTab(int index, Tab tab, Component component)`** - 插入提供的选项卡，并在选项卡面板的内容部分显示提供的组件。

### 移除选项卡 {#removing-a-tab}

要从选项卡面板中移除单个选项卡，请使用以下方法之一：

1. **`removeTab(Tab tab)`** - 通过传递要移除的选项卡实例，从选项卡面板中移除一个选项卡。
2. **`removeTab(int index)`** - 通过指定要移除的选项卡的索引，从选项卡面板中移除选项卡。

除了上述两种单个选项卡移除方法外，使用 **`removeAllTabs()`** 方法可以清空选项卡面板中的所有选项卡。

:::info
`remove()` 和 `removeAll()` 方法不会在组件内部移除选项卡。
:::

### 选项卡/组件关联 {#tabcomponent-association}

要更改该选项卡中显示的组件，请调用 `setComponentFor()` 方法，并传入选项卡实例或该选项卡在 `TabbedPane` 中的索引。

:::info
如果在已经与组件关联的选项卡上使用此方法，则之前关联的组件将被销毁。
:::

## 配置和布局 {#configuration-and-layout}

`TabbedPane` 类有两个组成部分：在指定位置显示的选项卡，以及要显示的组件。这可以是单个组件，也可以是 [`Composite`](../building-ui/composing-components) 组件，允许在选项卡的内容部分显示更复杂的组件。

### 滑动 {#swiping}

`TabbedPane` 支持通过滑动导航不同的选项卡。这对于移动应用程序理想，但也可以通过内置方法配置以支持鼠标滑动。滑动和鼠标滑动默认都是禁用的，但可以使用 `setSwipable(boolean)` 和 `setSwipableWithMouse(boolean)` 方法分别启用。

### 选项卡位置 {#tab-placement}

`TabbedPane` 中的选项卡可以根据应用程序开发者的偏好放置在组件的不同位置。提供的选项使用提供的枚举进行设置，其值为 `TOP`、`BOTTOM`、`LEFT`、`RIGHT` 或 `HIDDEN`。默认设置为 `TOP`。

<ComponentDemo
path='/webforj/tabbedpaneplacement'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPanePlacementView.java']}
height='400px'
/>

### 对齐 {#alignment}

除了改变 `TabbedPane` 中选项卡元素的放置外，还可以配置选项卡在组件中如何对齐。默认设置为 `AUTO`，允许选项卡的放置决定其对齐方式。

其他选项为 `START`、`END`、`CENTER` 和 `STRETCH`。前三个描述相对于组件的位置，而 `STRETCH` 使选项卡填充可用空间。

<ComponentDemo
path='/webforj/tabbedpanealignment'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneAlignmentView.java']}
height='250px'
/>

### 边框和活动指示器 {#border-and-activity-indicator}

`TabbedPane` 默认会为其中的选项卡显示边框，放置依赖于设置的 `Placement`。此边框有助于可视化选项卡面板中多个选项卡所占用的空间。

单击选项卡时，默认情况下，在该选项卡附近会显示一个活动指示器，以帮助突出显示当前选中的选项卡。

开发者可以通过使用适当的设置方法更改这两个选项的自定义。要更改是否显示边框，可以使用 `setBorderless(boolean)` 方法，传递 `true` 将隐藏边框，而 `false`（默认值）则显示边框。

:::info
此边框不适用于整个 `TabbedPane` 组件，仅作为选项卡与组件内容之间的分隔符。
:::

要设置活动指示器的可见性，可以使用 `setHideActiveIndicator(boolean)` 方法。向此方法传递 `true` 将隐藏选项卡下的活动指示器，而传递 `false`（默认值）则保持指示器显示。

<ComponentDemo
path='/webforj/tabbedpaneborder'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneBorderView.java']}
height='300px'
/>

### 激活模式 {#activation-modes}

为了更细粒度地控制 `TabbedPane` 在键盘导航时的行为，可以设置 `Activation` 模式以指定组件应如何行为。

- **`Auto`**：设置为自动时，用箭头键导航选项卡时将立即显示相应的选项卡组件。

- **`Manual`**：设置为手动时，选项卡将获得焦点，但在用户按下空格或回车之前不会显示。

<ComponentDemo
path='/webforj/tabbedpaneactivation'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneActivationView.java']}
height='250px'
/>

### 移除选项 {#removal-options}

单个选项卡元素可以设置为可关闭。可关闭的选项卡将在选项卡上添加一个关闭按钮，点击时会触发关闭事件。`TabbedPane` 决定如何处理此行为。

- **`Manual`**：默认情况下，移除设置为 `MANUAL`，这意味着事件会被触发，但由开发者来以任意方式处理此事件。

- **`Auto`**：另外，可以使用 `AUTO`，这将触发事件，也从组件中移除选项卡，消除开发者手动实现此行为的需要。

### 片段控制 <DocChip chip='since' label='26.00' /> {#segment-control}

通过使用 `setSegment(true)` 启用 `segment` 属性，`TabbedPane` 可以呈现为片段控制。在此模式下，选项卡显示带滑动指示器的活动选择，提供了一种紧凑的替代标准选项卡界面的方式。

<ComponentDemo
path='/webforj/tabbedpanesegment'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneSegmentView.java']}
height='250px'
/>

## 样式 {#styling}

### 扩展和主题 {#expanse-and-theme}

`TabbedPane` 带有内置 `Expanse` 和 `Theme` 选项，类似于其他 webforJ 组件。可以使用这些选项快速添加样式，以传达给最终用户的各种含义，而无需使用 CSS 来样式化组件。

<ComponentDemo
path='/webforj/tabbedpaneexpansetheme'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneExpanseThemeView.java']}
height='250px'
/>

<TableBuilder name={['Tab', 'TabbedPane']} />

## 最佳实践 {#best-practices}

以下实践建议在应用程序内使用 `TabbedPane`：

- **逻辑分组**：使用选项卡来逻辑地分组相关内容
    >- 每个选项卡应代表应用程序内的一个不同类别或功能
    >- 将相似或逻辑选项卡聚集在一起

- **限制选项卡**：避免用过多的选项卡使用户不知所措。考虑在适用时使用分层结构或其他导航模式，以实现干净的界面

- **清晰标签**：为选项卡提供清晰的标签，以便于使用
    >- 为每个选项卡提供清晰简洁的标签
    >- 标签应反映内容或目的，使用户易于理解
    >- 在适用时利用图标和不同颜色

- **键盘导航**：使用 webforJ 的 `TabbedPane` 键盘导航支持，使得与 `TabbedPane` 的交互对最终用户更加流畅和直观

- **默认选项卡**：如果默认选项卡没有放置在 `TabbedPane` 的开头，考虑将此选项卡设置为默认，以便于获得基本或常用信息。
