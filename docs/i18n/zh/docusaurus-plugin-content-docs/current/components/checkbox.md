---
title: CheckBox
sidebar_position: 20
_i18n_hash: 7946f6753a03194ecdcf7e20a7053012
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-checkbox" />
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/CheckBox" top='true'/>

一个 `CheckBox` 可以被选中或取消选中，并以勾选标记显示其当前状态。复选框适合于切换单个设置或让用户从一组选项中选择多个选项。

<!-- INTRO_END -->

## 用法 {#usages}

`CheckBox` 最适用于需要用户从选项列表中进行多项选择的场景。以下是使用 `CheckBox` 的几个示例：

1. **任务或功能选择**：当用户需要选择多个任务或功能以执行某些操作或配置时，通常会使用复选框。

2. **偏好设置**：涉及偏好或设置面板的应用程序通常使用复选框来允许用户从一组选项中选择多个选项。这最适合于互不排斥的选项。例如：

> - 启用或禁用通知
> - 选择深色模式或浅色模式主题
> - 选择电子邮件通知偏好

3. **过滤或排序**：`CheckBox` 可以在需要用户选择多个过滤器或类别的应用程序中使用，例如过滤搜索结果或选择多个项目以进行进一步操作。

4. **表单输入**：复选框通常用于表单中，以允许用户选择多个选项或进行二元选择。例如：
   > - 订阅通讯
   > - 同意条款和条件
   > - 选择购买或预订的商品

## 文本和定位 {#text-and-positioning}

复选框可以利用 <JavadocLink type="foundation" location="com/webforj/component/AbstractOptionInput" code='true' suffix='#setText(java.lang.String)'>setText(String text)</JavadocLink> 方法，该方法将根据内置的 <JavadocLink type="foundation" location="com/webforj/component/TextPosition" code='true' suffix=''>Position</JavadocLink> 位置于复选框附近。

复选框具有内置功能，可以在框的左侧或右侧设置要显示的文本。默认情况下，文本将显示在组件的右侧。文本的位置通过使用 <JavadocLink type="foundation" location="com/webforj/component/TextPosition" code='true' suffix=''>Position</JavadocLink> 枚举来支持。下面显示了两种设置： <br/>

<ComponentDemo
path='/webforj/checkboxhorizontaltext'
files={['src/main/java/com/webforj/samples/views/checkbox/CheckboxHorizontalTextView.java']}
height='200px'
/>

<br/>

## 不确定性 {#indeterminism}

`CheckBox` 组件支持不确定性，这是一种在表单和列表中常用的 UI 模式，用于指示一组复选框具有选中和未选中状态的混合。这种状态通常通过第三种视觉状态表示，通常在复选框内显示为填充方块或破折号。与不确定性相关的一些常见用例包括：

- **选择多个项目**：当用户需要从列表或一组选项中选择多个项目时，不确定性非常有用。它允许用户指示他们希望选择一些，但不是所有可用选项。

- **层次数据**：在复选框之间存在层次关系的场景中可以使用不确定性。例如，当选择类别和子类别时，不确定性可以表示某些子类别已被选择，而其他子类别未被选择，而父组件处于不确定状态。

<ComponentDemo
path='/webforj/checkboxindeterminate'
files={['src/main/java/com/webforj/samples/views/checkbox/CheckboxIndeterminateView.java']}
height='150px'
/>

## 样式 {#styling}

### 扩展 {#expanses}

以下 <JavadocLink type="foundation" location="com/webforj/component/Expanse">扩展值</JavadocLink> 允许在不使用 CSS 的情况下快速样式调整。
通过使用 `Expanse` 枚举类支持扩展。下面是复选框组件支持的扩展： <br/>

<ComponentDemo
path='/webforj/checkboxexpanse'
files={['src/main/java/com/webforj/samples/views/checkbox/CheckboxExpanseView.java']}
height='150px'
/>

<br/>

<TableBuilder name="Checkbox" />

## 最佳实践 {#best-practices}

为了确保在使用 `Checkbox` 组件时获得最佳用户体验，请考虑以下最佳实践：

1. **清晰标注选项**：为每个 `CheckBox` 选项提供清晰简洁的标签，以准确描述选择。标签应易于理解且能彼此区分。

2. **分组复选框**：将相关的复选框分组在一起，以指示它们之间的关联。这有助于用户理解可以在特定组内选择多个选项。

3. **提供默认选择**：如果适用，考虑为复选框提供默认选择，以指导用户首次遇到选项时的选择。默认选择应与最常见或最受欢迎的选择对齐。

4. **不确定性**：如果父 `CheckBox` 组件有多个组件属于其中特定的，可以勾选，也可以不勾选，使用不确定属性来显示并非所有 `CheckBox` 组件都被选中或未选中。
