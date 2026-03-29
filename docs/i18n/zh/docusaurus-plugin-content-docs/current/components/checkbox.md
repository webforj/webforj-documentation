---
title: CheckBox
sidebar_position: 20
_i18n_hash: e5ace9c598a0892cfa456f376035c87a
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-checkbox" />
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/CheckBox" top='true'/>

一个 `CheckBox` 可以被选中或取消选中，并以勾选标记的形式显示其当前状态。复选框非常适合切换单个设置或让用户从一组选项中选择多个选项。

<!-- INTRO_END -->

## 用法 {#usages}

在用户需要从选项列表中进行多项选择的场景中，`CheckBox` 是最佳选择。以下是使用 `CheckBox` 的一些示例：

1. **任务或功能选择**：当用户需要选择多个任务或功能来执行某些操作或配置时，通常会使用复选框。

2. **偏好设置**：涉及偏好或设置面板的应用程序通常使用复选框，以允许用户从一组选项中选择多个选项。这最适合非互斥的选项。例如：

> - 启用或禁用通知
> - 选择深色模式或浅色模式主题
> - 选择电子邮件通知偏好

3. **过滤或排序**：在需要用户选择多个过滤器或类别的应用程序中，可以使用 `CheckBox` ，例如过滤搜索结果或选择多个项目以进行进一步操作。

4. **表单输入**：复选框通常用于表单中，以允许用户选择多个选项或进行二元选择。例如：
   > - 订阅通讯
   > - 同意条款和条件
   > - 选择购买或预订的项目

## 文本和位置 {#text-and-positioning}

复选框可以使用 <JavadocLink type="foundation" location="com/webforj/component/AbstractOptionInput" code='true' suffix='#setText(java.lang.String)'>setText(String text)</JavadocLink> 方法，该方法将根据内置的 <JavadocLink type="foundation" location="com/webforj/component/TextPosition" code='true' suffix=''>位置</JavadocLink> 放置在复选框附近。

复选框内置功能可将文本设置为显示在框的左侧或右侧。默认情况下，文本将显示在组件的右侧。文本位置的设置由 <JavadocLink type="foundation" location="com/webforj/component/TextPosition" code='true' suffix=''>位置</JavadocLink> 枚举支持。下面是两个设置： <br/>

<ComponentDemo 
path='/webforj/checkboxhorizontaltext?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/checkbox/CheckboxHorizontalTextView.java'
height = '200px'
/>

<br/>

## 不确定性 {#indeterminism}

`CheckBox` 组件支持不确定性，这是一种在表单和列表中常用的用户界面模式，用于表示一组复选框具有混合的选中和未选中状态。此状态由第三种视觉状态表示，通常显示为填充的方形或复选框内的破折号。有几个与不确定性相关的常见用例：

- **选择多个项目**：当用户需要从列表或选项集中选择多个项目时，不确定性很有用。它允许用户表示他们想选择一些，但不是所有可用的选择。

- **层次数据**：在复选框之间存在层次关系的场景中可以使用不确定性。例如，在选择类别和子类别时，不确定性可以表示某些子类别已被选中，而另一些没有，同时父组件处于不确定状态。

<ComponentDemo 
path='/webforj/checkboxindeterminate?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/checkbox/CheckboxIndeterminateView.java'
height = '150px'
/>

## 样式 {#styling}

### 拓展 {#expanses}

以下 <JavadocLink type="foundation" location="com/webforj/component/Expanse"> 拓展值 </JavadocLink> 允许快速样式设置，而无需使用 CSS。
拓展通过使用 `Expanse` 枚举类来支持。下面是复选框组件支持的拓展： <br/>

<ComponentDemo 
path='/webforj/checkboxexpanse?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/checkbox/CheckboxExpanseView.java'
height = '150px'
/>

<br/>

<TableBuilder name="复选框" />

## 最佳实践 {#best-practices}

为了确保在使用 `Checkbox` 组件时获得最佳用户体验，请考虑以下最佳实践：

1. **清晰标记选项**：为每个 `CheckBox` 选项提供清晰简洁的标签，以准确描述选择。标签应易于理解，并且彼此之间有明显区别。

2. **分组复选框**：将相关复选框分组在一起，以指示它们之间的关联。这有助于用户理解在特定组内可以选择多个选项。

3. **提供默认选择**：如果适用，请考虑为复选框提供默认选择，以指导用户在第一次遇到选项时。默认选择应与最常见或首选的选择一致。

4. **不确定性**：如果一个父 `CheckBox` 组件下有多个组件，其中一些可以选中而另一些可以取消选择，则使用不确定属性来显示并非所有 `CheckBox` 组件都是选中或未选中的。
