---
title: CheckBox
sidebar_position: 20
_i18n_hash: 0c55e1e2b7f92aa8f1f65151bfa3e096
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-checkbox" />
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/CheckBox" top='true'/>

`CheckBox` 类创建一个可以被选中或取消选中的组件，并向用户显示其状态。当点击时，复选框内会出现一个勾号，以指示肯定选择（开启）。再次点击时，勾号消失，表示否定选择（关闭）。

通过提供清晰且直接的视觉指示，复选框增强了用户交互和决策，使其成为现代用户界面中的重要元素。

## Usages {#usages}

`CheckBox` 最适合用于用户需要从选项列表中进行多重选择的场景。以下是一些使用 `CheckBox` 的示例：

1. **任务或功能选择**：当用户需要选择多个任务或功能以执行某些操作或配置时，复选框是常用的。

2. **偏好设置**：涉及偏好或设置面板的应用程序通常使用复选框，以允许用户从一组选项中选择多个选择。这最适合那些不是互斥的选项。例如：

> - 启用或禁用通知
> - 选择深色模式或浅色模式主题
> - 选择电子邮件通知偏好

3. **过滤或排序**：在需要用户选择多个过滤器或类别的应用程序中，可以使用 `CheckBox`，例如过滤搜索结果或选择多个项目以进行进一步操作。

4. **表单输入**：复选框常用于表单中，以允许用户选择多个选项或进行二进制选择。例如：
   > - 订阅新闻通讯
   > - 同意条款和条件
   > - 选择购买或预订的项目

## Text and positioning {#text-and-positioning}

复选框可以利用 <JavadocLink type="foundation" location="com/webforj/component/AbstractOptionInput" code='true' suffix='#setText(java.lang.String)'>setText(String text)</JavadocLink> 方法，该方法将根据内置的 <JavadocLink type="foundation" location="com/webforj/component/TextPosition" code='true' suffix=''>Position</JavadocLink> 靠近复选框定位。

复选框具有内置功能，可以设置文本显示在框的右侧或左侧。默认情况下，文本将显示在组件的右侧。文本的位置通过使用 <JavadocLink type="foundation" location="com/webforj/component/TextPosition" code='true' suffix=''>Position</JavadocLink> 枚举来支持。下方显示了两个设置： <br/>

<ComponentDemo 
path='/webforj/checkboxhorizontaltext?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/checkbox/CheckboxHorizontalTextView.java'
height = '200px'
/>

<br/>

## Indeterminism {#indeterminism}

`CheckBox` 组件支持不确定性，这是一种常用于表单和列表的 UI 模式，以指示一组复选框具有混合的选中和未选中状态。这种状态由第三种视觉状态表示，通常显示为复选框内的填充正方形或短划线。与不确定性相关的一些常见用例包括：

- **选择多个项目**：当用户需要从列表或选项集中选择多个项目时，不确定性非常有用。它允许用户表示他们想要选择一些但不是所有可用选项。

- **层次数据**：在复选框之间存在层次关系的场景中，可以采用不确定性。例如，在选择类别和子类别时，不确定性可以表示某些子类别被选中而其他未被选中，并且父组件处于不确定状态。

<ComponentDemo 
path='/webforj/checkboxindeterminate?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/checkbox/CheckboxIndeterminateView.java'
height = '150px'
/>

## Styling {#styling}

### Expanses {#expanses}

以下的 <JavadocLink type="foundation" location="com/webforj/component/Expanse"> Expanses 值 </JavadocLink> 允许快速样式设置，无需使用 CSS。
Expanses 通过使用 `Expanse` 枚举类来支持。以下是复选框组件支持的扩展： <br/>

<ComponentDemo 
path='/webforj/checkboxexpanse?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/checkbox/CheckboxExpanseView.java'
height = '150px'
/>

<br/>

<TableBuilder name="Checkbox" />

## Best practices {#best-practices}

为了确保在使用 `Checkbox` 组件时获得最佳用户体验，请考虑以下最佳实践：

1. **清楚标记选项**：为每个 `CheckBox` 选项提供清晰简洁的标签，以准确描述选择。标签应易于理解，并且彼此之间有明显区别。

2. **分组复选框**：将相关的复选框分组在一起，以指示它们的关联。这有助于用户理解在特定组内可以选择多个选项。

3. **提供默认选择**：如果适用，考虑为复选框提供默认选择，以指导用户在首次遇到选项时。默认选择应与最常见或首选的选择一致。

4. **不确定性**：如果父 `CheckBox` 组件有多个组件属于它，以至于一些可以选中而另一些可以取消选中，请使用不确定属性以显示并非所有 `CheckBox` 组件都已选中或未选中。
