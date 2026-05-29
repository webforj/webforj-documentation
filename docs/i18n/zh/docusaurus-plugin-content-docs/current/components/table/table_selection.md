---
sidebar_position: 10
title: Selection
slug: selection
_i18n_hash: 46e92f0b5b3f1dafbf040176711ae5ac
---
`Table` 组件提供了多种选择功能。可以选择单个项目、多个项目或以编程方式管理选择。

:::tip 管理和查询数据
有关如何使用 `Repository` 模式来管理和查询集合的信息，请参阅 [Repository 文章](/docs/advanced/repository/overview)。
:::

## 选择模式 {#selection-mode}

表格中的选择模式决定了用户如何选择项目。它提供了配置项目选择行为的选项。Table 类提供了一个方法来设置选择模式：

```java
setSelectionMode(SelectionMode selectionMode)
```

可用的 SelectionMode 选项包括：

>- `SINGLE` - （单选）
>- `MULTI` - （多选）
>- `NONE` - （不选择）。

## 选择事件 {#selection-event}

`Table` 组件包会发出与行选择相关的多个事件。这些事件捕获 `Table` 行选择状态的变化。下面是关键选择事件及其描述：

>- `TableItemSelectEvent` -  当选择一个表项时触发。
>- `TableItemDeselectEvent` - 当取消选择一个表项时触发。
>- `TableItemSelectionChange` - 当表格中的整体选择变化时触发，或者当选择一个额外的选择时触发。

:::info
当多选模式处于活动状态且通过头部复选框进行选择时，不会触发 `TableItemSelectEvent` 和 `TableItemDeselectEvent`。在这种情况下，应该使用 `TableItemSelectionChange`。
:::

在下面的示例中，每当用户选择一行时，将触发 `TableItemSelectEvent` 事件。可以通过使用 `onItemSelect()` 方法向表格添加监听器来处理该事件。

<ComponentDemo
path='/webforj/tablesingleselection'
files={[
  'src/main/java/com/webforj/samples/views/table/TableSingleSelectionView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

## 复选框选择 {#checkbox-selection}

当选择模式为 `MULTI` 时，启用复选框选择，并允许用户方便地使用与每行相关联的复选框选择一个或多个项目。此功能特别适用于用户需要对选定项目执行批量操作的场景。Table 类提供了方法来启用和自定义复选框选择。

通过使用 `setCheckboxSelection(boolean checkboxSelection)` 方法，可以配置复选框在每一行旁边显示，从而允许用户选择项目。下面的程序显示启用多重选择和复选框选择：

<ComponentDemo
path='/webforj/tablemultiselection'
files={[
  'src/main/java/com/webforj/samples/views/table/TableMultiSelectionView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

## 编程选择 {#programatic-selection}

`Table` 组件提供了编程选择方法，允许您通过其键或整个项目来操作已选择的项目。

### 按键选择 {#select-by-key}

`selectKey(Object... keys)` 方法允许您通过编程方式使用其键选择项目。您可以将一个或多个键传递给此方法，它将相应更新选择。

### 选择条目项目 {#selecting-entry-items}

最后，`select(T... items)` 方法允许您通过将一个或多个项目本身传递给此方法来以编程方式选择项目，并相应更新选择。
