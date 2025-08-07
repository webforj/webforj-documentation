---
title: TabbedPane
slug: tabbedpane
sidebar_position: 125
_i18n_hash: 4f2421ca62abb88a3edd750e7887d2db
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-tabbed-pane" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/tabbedpane/TabbedPane" top='true'/>

`TabbedPane`类提供了一种紧凑而有组织的方式来显示内容，这些内容被划分为多个部分，每个部分都与一个`Tab`相关联。用户可以通过点击相应的标签在这些部分之间切换，这些标签通常带有文本和/或图标。这个类简化了多层次接口的创建，用户可以访问不同的内容或表单，而不需要同时可见。

## 用法 {#usages}

`TabbedPane`类为开发者提供了一个强大的工具，用于在用户界面中组织和呈现多个标签或部分。以下是一些您可能在应用程序中使用`TabbedPane`的典型场景：

1. **文档查看器**：实现一个文档查看器，其中每个标签代表不同的文档或文件。用户可以轻松地在打开的文档之间切换，以实现高效的多任务处理。

2. **数据管理**：利用`TabbedPane`来组织数据管理任务，例如：
    >- 在应用程序中展示不同的数据集
    >- 不同的用户资料可以在单独的标签中显示
    >- 用户管理系统中的不同档案

3. **模块选择**：`TabbedPane`可以代表不同的模块或部分。每个标签可以封装一个特定模块的功能，使用户能够一次专注于应用程序的一个方面。

4. **任务管理**：任务管理应用可以使用`TabbedPane`来表示各种项目或任务。每个标签可以对应一个特定项目，使用户可以单独管理和跟踪任务。

5. **程序导航**：在需要运行各种程序的应用程序中，`TabbedPane`可以：
    >- 作为侧边栏，允许在单个应用程序中运行不同的应用程序或程序，例如在[`AppLayout`](./app-layout.md)模板中所示
    >- 创建一个顶部栏，也可以起到类似的作用，或在已经选择的应用程序中表示子应用。

## 标签 {#tabs}

标签是可以添加到选项卡面板中的用户界面元素，以组织和切换不同的内容视图。

:::important
标签并不打算作为独立组件使用。它们旨在与选项卡面板联合使用。这个类不是`Component`，不应被单独使用。
:::

### 属性 {#properties}

标签由以下属性组成，这些属性在将它们添加到`TabbedPane`时使用。这些属性具有获取器和设置器，以便在`TabbedPane`中进行自定义。

1. **Key(`Object`)**：表示`Tab`的唯一标识符。

2. **Text(`String`)**：将在`TabbedPane`中显示为`Tab`标题的文本。这也被称为通过`getTitle()`和`setTitle(String title)`方法获取的标题。

3. **Tooltip(`String`)**：与`Tab`关联的工具提示文本，当光标悬停在`Tab`上时会显示。

4. **Enabled(`boolean`)**：表示`Tab`当前是否启用。可以通过`setEnabled(boolean enabled)`方法修改。

5. **Closeable(`boolean`)**：表示`Tab`是否可以关闭。可以通过`setCloseable(boolean enabled)`方法修改。这将在`Tab`上添加一个关闭按钮，用户可以点击，并触发移除事件。`TabbedPane`组件决定如何处理移除操作。

6. **Slot(`Component`)**：
    Slots提供了灵活的选项，以提高`Tab`的能力。您可以在`Tab`中嵌套图标、标签、加载指示器、清除/重置功能、头像/个人资料图片和其他有益组件，以进一步向用户澄清预期含义。
    您可以在构造期间将组件添加到`Tab`的`prefix`槽中。或者，您可以使用`setPrefixComponent()`和`setSuffixComponent()`方法在`Tab`中显示选项前后插入各种组件。

        ```java
        TabbedPane pane = new TabbedPane();
        pane.addTab(new Tab("Documents", TablerIcon.create("files")));
        ```

## `Tab`操作 {#tab-manipulation}

存在多种方法，允许开发者在`TabbedPane`中添加、插入、移除和操作各种`Tab`元素的属性。

### 添加`Tab` {#adding-a-tab}

`addTab()`和`add()`方法存在不同的重载版本，以允许开发者灵活地向`TabbedPane`添加新标签。添加一个`Tab`将使其位于任何之前存在的标签之后。

1. **`addTab(String text)`** - 使用指定的`String`作为`Tab`文本向`TabbedPane`添加一个`Tab`。
2. **`addTab(Tab tab)`** - 将作为参数提供的`Tab`添加到`TabbedPane`中。
3. **`addTab(String text, Component component)`** - 用给定的`String`作为`Tab`文本添加一个`Tab`，并在`TabbedPane`的内容部分显示提供的`Component`。
4. **`addTab(Tab tab, Component component)`** - 添加提供的`Tab`并在`TabbedPane`的内容部分显示提供的`Component`。
5. **`add(Component... component)`** - 将一个或多个`Component`实例添加到`TabbedPane`，为每个组件创建一个独立的`Tab`，文本设置为`Component`的名称。

:::info
`add(Component... component)`通过调用传递的参数上的`component.getName()`来确定所传递`Component`的名称。
:::

### 插入`Tab` {#inserting-a-tab}

除了在现有标签的末尾添加`Tab`，还可以在指定位置创建新的标签。为此，重载版本的`insertTab()`方法可用。

1. **`insertTab(int index, String text)`** - 在给定索引处将`Tab`插入`TabbedPane`，文本为指定的`String`。
2. **`insertTab(int index, Tab tab)`** - 在指定索引处将提供的`Tab`作为参数插入到`TabbedPane`中。
3. **`insertTab(int index, String text, Component component)`** - 在给定索引处插入具有指定`String`作为文本的`Tab`，并在`TabbedPane`的内容部分显示提供的`Component`。
4. **`insertTab(int index, Tab tab, Component component)`** - 插入提供的`Tab`并在`TabbedPane`的内容部分显示提供的`Component`。

### 移除`Tab` {#removing-a-tab}

要从`TabbedPane`中移除一个单独的`Tab`，使用以下方法之一：

1. **`removeTab(Tab tab)`** - 通过传递要移除的Tab实例，从`TabbedPane`中移除`Tab`。
2. **`removeTab(int index)`** - 通过指定要移除的`Tab`的索引，从`TabbedPane`中移除该`Tab`。

除了上述两个移除单个`Tab`的方法外，还可以使用**`removeAllTabs()`**方法清空`TabbedPane`中的所有标签。

:::info
`remove()`和`removeAll()`方法不移除组件内部的标签。
:::

### Tab/Component关联 {#tabcomponent-association}

要更改给定`Tab`要显示的`Component`，请调用`setComponentFor()`方法，并传递`Tab`的实例或该`Tab`在`TabbedPane`中的索引。

:::info
如果此方法在已与`Component`关联的`Tab`上使用，则先前关联的`Component`将被销毁。
:::

## 配置和布局 {#configuration-and-layout}

`TabbedPane`类由两部分组成：在指定位置显示的`Tab`，以及要显示的组件。这可以是单个组件，或[`Composite`](../building-ui/composite-components)组件，允许在标签的内容部分显示更复杂的组件。

### 切换 {#swiping}

`TabbedPane`支持通过滑动在各个标签之间导航。这对于移动应用程序理想，但也可以通过内置方法进行配置以支持鼠标滑动。滑动和鼠标滑动默认情况下都是禁用的，但可以通过`setSwipable(boolean)`和`setSwipableWithMouse(boolean)`方法启用。

### 标签放置 {#tab-placement}

`TabbedPane`中的`Tabs`可以根据应用程序开发者的偏好放置在组件中的不同位置。提供的选项使用提供的枚举进行设置，其值为`TOP`、`BOTTOM`、`LEFT`、`RIGHT`或`HIDDEN`。默认设置为`TOP`。

<ComponentDemo 
path='/webforj/tabbedpaneplacement?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPanePlacementView.java'
height="400px"
/>

### 对齐 {#alignment}

除了改变`TabbedPane`中`Tab`元素的放置外，还可以配置标签在组件中的对齐方式。默认情况下，设置为`AUTO`，允许标签的放置决定其对齐方式。

其他选项为`START`、`END`、`CENTER`和`STRETCH`。前三个描述相对于组件的位置，`STRETCH`使标签填充可用空间。

<ComponentDemo 
path='/webforj/tabbedpanealignment?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneAlignmentView.java'
height="250px"
/>

### 边框和活动指示器 {#border-and-activity-indicator}

默认情况下，`TabbedPane`将为其中的标签显示边框，该边框根据设置的`Placement`进行放置。此边框有助于可视化标签在窗格中占用的空间。

当单击`Tab`时，默认情况下，会在该`Tab`附近显示活动指示器，以帮助突出显示当前选定的`Tab`。

这两个选项都可以通过开发者改变布尔值来进行定制。要更改是否显示边框，可以使用`setBorderless(boolean)`方法，传入`true`将隐藏边框，默认值`false`将显示边框。

:::info
此边框不适用于整个`TabbedPane`组件，仅充当标签与组件内容之间的分隔符。
:::

要设置活动指示器的可见性，可以使用`setHideActiveIndicator(boolean)`方法。向该方法传入`true`将隐藏活动`Tab`下的活动指示器，而传入`false`（默认值）将保持指示器显示。

<ComponentDemo 
path='/webforj/tabbedpaneborder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneBorderView.java'
height="300px"
/>

### 激活模式 {#activation-modes}

为了更精细地控制`TabbedPane`在键盘导航时的行为，可以设置`Activation`模式，以指定组件应如何行为。

- **`Auto`**：设置为自动时，用箭头键导航标签会立即显示相应的标签组件。

- **`Manual`**：设置为手动时，标签将获得焦点，但在用户按下空格或回车之前不会显示。

<ComponentDemo 
path='/webforj/tabbedpaneactivation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneActivationView.java'
height="250px"
/>

### 移除选项 {#removal-options}

单个`Tab`元素可以设置为可关闭。可关闭标签将在标签上添加一个关闭按钮，当单击时会触发关闭事件。`TabbedPane`决定如何处理此行为。

- **`Manual`**：默认情况下，移除设置为`MANUAL`，这意味着会触发事件，但由开发者决定以他们选择的方式处理此事件。

- **`Auto`**：可以使用`AUTO`，这将触发事件，并在组件中删除`Tab`，消除开发者手动实现此行为的需要。

## 样式 {#styling}

### 范围和主题 {#expanse-and-theme}

`TabbedPane`带有内置的`Expanse`和`Theme`选项，类似于其他webforJ组件。这些可以用来快速添加样式，以向最终用户传达各种意义，而无需使用CSS对组件进行样式处理。

<ComponentDemo 
path='/webforj/tabbedpaneexpansetheme?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneExpanseThemeView.java'
height="250px"
/>

<TableBuilder name="TabbedPane" />

## 最佳实践 {#best-practices}

以下实践建议在应用程序中使用`TabbedPane`：

- **逻辑分组**：使用标签逻辑上分组相关内容
    >- 每个标签应表示您应用程序中的一个独特类别或功能
    >- 将相似或逻辑标签放置在一起

- **有限标签**：避免用过多的标签让用户感到不知所措。考虑在适用的情况下使用层次结构或其他导航模式以保持干净的界面

- **清晰标签**：清晰标记标签以便于直观使用
    >- 为每个标签提供清晰简洁的标签
    >- 标签应反映内容或目的，使用户容易理解 
    >- 在适用的地方利用图标和截然不同的颜色

- **键盘导航**：使用webforJ的`TabbedPane`键盘导航支持，使用户与`TabbedPane`的交互更加无缝和直观

- **默认标签**：如果默认标签未放置在`TabbedPane`的开头，考虑将此标签设置为默认标签，以显示重要或常用的信息。
