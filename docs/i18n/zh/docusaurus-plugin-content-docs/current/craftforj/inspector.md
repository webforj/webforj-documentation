---
title: 检查组件
sidebar_position: 3
sidebar_class_name: new-content
description: >-
  Browse the component tree webforJ built, select components from the page, and
  change their properties while the app runs.
_i18n_hash: 585425968807283ea392911908fbd030
---
Inspector 会显示 Java 代码构建的组件树。`Composite` 会显示为你编写的类，并按 webforJ 中的顺序包含你添加的子组件，因此 craftforJ 中的结构与源代码中的结构一致。

![运行中的应用中已选中并高亮显示组件的组件树](/img/craftforj/inspector/tree-selection.png#rounded-border)

## 选择组件 {#selecting-a-component}

要从页面中选择组件，请按 <kbd>Alt</kbd> + <kbd>Shift</kbd> + <kbd>C</kbd>，然后点击该组件。craftforJ 会在树中选择对应的节点。反过来，将鼠标悬停在树中的节点上，会高亮显示页面中的对应组件，因此你可以在页面和组件树之间双向切换。

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/pick-mode.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

要搜索组件树，请按 <kbd>Cmd/Ctrl</kbd> + <kbd>F</kbd>。将搜索词放在斜杠之间可将其作为正则表达式处理。右键点击节点可打开该节点可用的操作。你可以打开其源代码，或将其交给[助手](/docs/craftforj/ai)。

## 查看和更改属性 {#reading-and-changing-properties}

选择组件后，侧边栏会显示该组件的属性，并按属性的作用分组。组件可提供哪些属性取决于组件本身，其中有些属性是只读的。对于不适合以纯文本形式显示的属性，系统会提供适合其值类型的编辑器。更改属性值后，修改会立即在运行中的应用里生效。

:::info 实时编辑不会修改你的文件
编辑属性只会更改你眼前的应用，不会更改其他内容。要将修改写入源代码，需要单独执行一个有意为之的步骤，详情请参阅[将修改写入源代码](/docs/craftforj/source-changes)。
:::

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/property-edit.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

## 查看组件的源代码 {#viewing-the-source-of-a-component}

你可以追溯任意组件，找到构建它的 Java 代码。默认情况下，源代码会以只读方式在 craftforJ 中打开，并定位到创建该组件的代码行。你也可以将 craftforJ 配置为在编辑器中打开源代码，并定位到同一行。如果无法追溯组件对应的代码行，craftforJ 会报告这一情况，而不是打开一个空白查看器。

![源代码查看器定位到创建所选组件的代码行](/img/craftforj/inspector/source-viewer.png#rounded-border)

## 移除组件 <DocChip chip='since' label='26.03' /> {#removing-a-component}

在组件树中右键点击一个组件并选择 **Remove Component**，即可通过两种方式将其移除。一种方式是将其从眼前的应用中移除，另一种则是将其从项目中移除。

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/delete-components.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

### 仅从浏览器中移除 {#removing-from-the-browser-only}

**Remove from browser only** 会将组件从运行中的应用移除，但不会更改 Java 源代码。你可以用这种方式查看移除某个组件后的布局效果，而无需立即做出任何决定。

此移除操作只是临时的。下次应用根据源代码构建此视图时（例如重新加载页面后），该组件就会重新出现。

### 从源代码中移除 {#removing-from-source}

**Remove from source** 会编辑创建该组件的 Java 文件，因此应用重建后，该组件仍会保持移除状态。移除操作会立即写入文件，而不是添加到[待处理更改](/docs/craftforj/source-changes#pending-changes)中，因此写入文件前无需审核。

写入 Java 代码会导致应用重建并重启，与[应用更改](/docs/craftforj/source-changes#after-you-apply)的效果相同。如果已配置[实时重新加载](/docs/configuration/deploy-reload/overview)，该过程会自动进行。否则，请自行重启应用。

例如，假设有一个表单，其中包含 **Save** 和 **Cancel** 按钮，而 **Save** 按钮设置了主题并注册了点击监听器：

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

选择 **Save** 并选择 **Remove from source** 后，代码会变成如下所示。变量声明、主题、监听器以及按钮在 `add()` 调用中的位置都会被移除；`ButtonTheme` 导入语句也会被移除，因为没有其他代码使用它。应用重建后，**Cancel** 按钮仍会保留：

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

移除操作只影响该组件本身及其相关内容：

- 移除容器时，其中包含的组件也会一并移除。
- 仅用于创建、配置和附加该组件的代码会被清理，包括其他地方不再使用的字段和导入语句。
- 仍在其他地方使用的代码会保留。如果类中的其他代码仍会读取该组件（例如另一个组件的监听器），craftforJ 只会将它分离，并保留创建和配置它的代码。
- 移除可复用组件的一个实例（例如你编写的 `Composite`）时，只会移除该次用法，不会删除或修改定义该组件的类。

只有在移除后的代码仍可编译时，craftforJ 才会写入文件。文件绝不会被删除。

### 无法使用移除功能的情况 {#when-removal-isnt-available}

只有在应用的[配置](/docs/craftforj/configuration#feature-flags)和 craftforJ 中的 **Java source changes** 设置都允许写入 Java 代码时，才会提供 **Remove from source**。对于以 Kotlin 构建的组件，或 craftforJ 无法追溯到源代码行的组件，不会提供此选项。

有些组件无法从源代码中移除：

- 路由：路由器会根据它们的 `@Route` 放置这些组件。
- 框架。
- 由其他组件作为自身组成部分构建的组件。请改为移除外层组件。

如果 craftforJ 找不到组件的源代码，或无法确定要移除哪些代码，也可能会拒绝移除操作。例如，如果一行代码创建了多个相同类型的组件，仅凭这一行无法确定你选择的是哪一个。

拒绝移除时，文件不会发生变化，并会显示原因：

```
Could not remove FlexLayout from source: FlexLayout is a part ContactCard builds for itself, remove that instead
```

### 撤销移除操作 {#undoing-a-removal}

要恢复从源代码中移除的组件，请在 craftforJ 中按 <kbd>Cmd/Ctrl</kbd> + <kbd>Z</kbd>。这会将文件还原到移除操作之前的状态。如果此后你已在自己的编辑器中修改文件，则撤销操作会被阻止，以避免覆盖你的修改。有关撤销的工作方式以及撤销被阻止时的处理方法，请参阅[撤销和重做](/docs/craftforj/source-changes#undoing-and-redoing)。
