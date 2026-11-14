---
title: 检查组件
sidebar_position: 3
sidebar_class_name: new-content
description: >-
  Browse the component tree webforJ built, select components from the page, and
  change their properties while the app runs.
_i18n_hash: 91fbaf620dc8967a50c4a5ce6b455841
---
Inspector 会显示 Java 代码构建的组件树。`Composite` 会显示为你编写的类，并按 webforJ 保存子组件的顺序包含你添加的子组件，因此 craftforJ 中的结构与你源代码中的结构一致。

![运行中的应用中选中并高亮显示某个组件的组件树](/img/craftforj/inspector/tree-selection.png#rounded-border)

## 选择组件 {#selecting-a-component}

要从页面中选择组件，请按 <kbd>Alt</kbd> + <kbd>Shift</kbd> + <kbd>C</kbd>，然后单击该组件。craftforJ 会在树中选中对应的节点。将鼠标悬停在树中的节点上，则会反向高亮显示页面中的对应组件，因此你可以在屏幕和树之间双向切换。

<VideoPlayer video="craftforj/pick-mode" width={1920} height={992} title="Pick mode" />

要搜索组件树，请按 <kbd>Cmd/Ctrl</kbd> + <kbd>F</kbd>。将搜索词用斜杠括起来，可将其作为正则表达式处理。右键单击节点会打开该节点可用的操作。你可以打开其源代码，或将其交给[助手](/docs/craftforj/ai)。

## 查看和更改属性 {#reading-and-changing-properties}

选中组件后，侧边栏会显示该组件的属性，并按影响范围分组。组件提供哪些属性取决于组件本身，其中一些属性为只读。对于不适合以纯文本形式显示的属性，会提供与其值相适配的编辑器。更改属性值后，会立即在运行中的应用中生效。

:::info 实时编辑不会修改你的文件
属性编辑只会更改当前显示的应用，不会更改其他内容。要将其写入源代码，需要单独主动执行此步骤，详见[将更改写入源代码](/docs/craftforj/source-changes)。
:::

<VideoPlayer video="craftforj/property-edit" width={1920} height={1000} title="Property edit" />

## 查看组件的源代码 {#viewing-the-source-of-a-component}

你可以追溯任意组件，找到构建它的 Java 代码。默认情况下，源代码会以只读模式在 craftforJ 中打开，并定位到创建该组件的代码行。你也可以将 craftforJ 配置为在编辑器中打开源代码，并定位到同一行。如果组件无法追溯到某一行源代码，craftforJ 会报告这一情况，而不会打开空白的查看器。

![源代码查看器定位到创建所选组件的代码行](/img/craftforj/inspector/source-viewer.png#rounded-border)

## 移除组件 <DocChip chip='since' label='26.03' /> {#removing-a-component}

在组件树中右键单击组件并选择 **Remove Component**，可以通过两种方式将其移除。一种是从当前显示的应用中移除，另一种是从项目中移除。

<VideoPlayer video="craftforj/delete-components" width={1872} height={1080} title="Delete components" />

### 仅从浏览器中移除 {#removing-from-the-browser-only}

**Remove from browser only** 会从运行中的应用中移除组件，但不会更改 Java 源代码。你可以用它来查看布局在移除某个组件后的效果，再决定是否进行任何更改。

此移除是临时的。下次应用根据源代码构建该视图时（例如重新加载页面后），该组件会重新出现。

### 从源代码中移除 {#removing-from-source}

**Remove from source** 会编辑创建该组件的 Java 文件，因此应用重新构建后该组件仍会保持移除状态。移除操作会立即写入文件，而不是添加到[待处理更改](/docs/craftforj/source-changes#pending-changes)中，因此在写入文件前无需经过审核。

写入 Java 代码会使应用重新构建并重启，与[应用更改](/docs/craftforj/source-changes#after-you-apply)时相同。如果已配置[实时重新加载](/docs/configuration/deploy-reload/overview)，则会自动完成。否则，需要手动重启应用。

例如，有一个表单，包含 **Save** 和 **Cancel** 按钮，其中 **Save** 设置了主题并添加了点击监听器：

```java
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.layout.flexlayout.FlexLayout;

public class ContactFormView {
  public ContactFormView(FlexLayout layout) {
    layout.setSpacing("1em");
    Button save = new Button("Save");
    save.setTheme(ButtonTheme.PRIMARY);
    save.onClick(event -> {
      save.setEnabled(false);
    });
    Button cancel = new Button("Cancel");
    layout.add(save, cancel);
  }
}
```

选中 **Save** 并选择 **Remove from source** 后，代码会变成如下所示。变量声明、主题、监听器以及按钮在 `add()` 调用中的位置都会被移除；未被其他代码使用的 `ButtonTheme` 导入语句也会一并移除。应用重新构建后，**Cancel** 仍会保留：

```java
import com.webforj.component.button.Button;
import com.webforj.component.layout.flexlayout.FlexLayout;

public class ContactFormView {
  public ContactFormView(FlexLayout layout) {
    layout.setSpacing("1em");
    Button cancel = new Button("Cancel");
    layout.add(cancel);
  }
}
```

移除操作只会影响该组件本身，不会涉及其他内容：

- 移除容器时，其中包含的组件也会一并移除。
- 仅用于创建、配置和附加该组件的代码会被清理，包括其他地方不再使用的字段和导入语句。
- 其他地方仍需要的代码会保留。如果类中的其他部分仍会读取该组件（例如另一个组件的监听器），craftforJ 只会将其分离，并保留创建和配置它的代码。
- 移除可复用组件的某个实例（例如你编写的 `Composite`）时，只会移除该处用法，不会删除或更改其定义类。

只有在结果仍可编译时，craftforJ 才会写入文件。文件永远不会被删除。

### 无法移除的情况 {#when-removal-isnt-available}

只有当应用的[配置](/docs/craftforj/configuration#feature-flags)和 craftforJ 中的 **Java source changes** 设置都允许写入 Java 代码时，才会提供 **Remove from source**。对于用 Kotlin 构建的组件，或 craftforJ 无法追溯到源代码行的组件，不会提供此选项。

有些组件无法从源代码中移除：

- 路由，路由器会根据其 `@Route` 放置这些路由
- 框架
- 由其他组件作为自身组成部分构建的组件。请改为移除外层组件。

如果 craftforJ 找不到组件的源代码，或无法确定要移除哪段代码，也可能拒绝移除。例如，一行代码创建了多个同类型组件时，仅凭这一行无法确定你选择的是哪一个。

拒绝移除时，你的文件不会发生变化，并会显示原因：

```
Could not remove FlexLayout from source: FlexLayout is a part ContactCard builds for itself, remove that instead
```

### 撤销移除操作 {#undoing-a-removal}

要恢复从源代码中移除的组件，请在 craftforJ 中按 <kbd>Cmd/Ctrl</kbd> + <kbd>Z</kbd>。这会将文件恢复到移除操作之前的状态。如果此后你已在自己的编辑器中修改该文件，撤销操作会被阻止，而不会覆盖你的更改。请参阅[撤销和重做](/docs/craftforj/source-changes#undoing-and-redoing)，了解撤销操作的工作方式，以及被阻止时该如何处理。
