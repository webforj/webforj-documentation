---
sidebar_position: 10
title: Selection
slug: selection
_i18n_hash: 35cb02c29edafbe9a0715b4630be56c1
---
`Table` 组件提供多种选择功能。可以选择单个项目、多个项目，或以编程方式管理选择。

:::tip 管理和查询数据
有关如何使用 `Repository` 模式管理和查询集合的信息，请参阅 [Repository 文章](/docs/advanced/repository/overview)。
:::

## 选择模式 {#selection-mode}

表格中的选择模式决定了用户如何选择项目。它提供了配置项目选择行为的选项。Table 类提供了设置选择模式的方法：

```java
setSelectionMode(SelectionMode selectionMode)
```

可用的 SelectionMode 选项包括：

>- `SINGLE` - （单个选择）
>- `MULTI` - （多个选择）
>- `NONE` - （无选择）。

## 选择事件 {#selection-event}

`Table` 组件包会发出与行选择相关的多个事件。这些事件捕获 `Table` 行的选择状态变化。以下是关键选择事件及其描述：

>- `TableItemSelectEvent` - 当选择表格项目时发出。
>- `TableItemDeselectEvent` - 当取消选择表格项目时发出。
>- `TableItemSelectionChange` - 当表格中的整体选择发生变化，或选择了额外的选择时发出。

:::info
当多项选择模式处于活动状态并通过标题复选框进行选择时，不会触发 `TableItemSelectEvent` 和 `TableItemDeselectEvent`。在这种情况下，应使用 `TableItemSelectionChange`。
:::

在下面的示例中，每当用户选择一行时，都会触发 `TableItemSelectEvent` 事件。可以通过使用 `onItemSelect()` 方法向表格添加监听器来处理此事件。

<ComponentDemo 
path='/webforj/tablesingleselection?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableSingleSelectionView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

## 复选框选择 {#checkbox-selection}

当选择模式为 `MULTI` 时，启用复选框选择，并允许用户方便地使用与每行相关的复选框选择一个或多个项目。此功能在用户需要对选定项目执行批量操作的情况下尤其有用。Table 类提供方法来启用和自定义复选框选择。

通过使用 `setCheckboxSelection(boolean checkboxSelection)` 方法，可以配置复选框以显示在每行旁边，允许用户选择项目。下面的程序显示启用了多项选择和复选框选择：

<ComponentDemo 
path='/webforj/tablemultiselection?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableMultiSelectionView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

## 编程选择 {#programatic-selection}

`Table` 组件提供编程选择方法，使您可以通过其键或整个项目操纵已选择的项目。 

### 通过键选择 {#select-by-key}

`selectKey(Object... keys)` 方法使您能够以编程方式使用它们的键选择项目。您可以将一个或多个键传递给此方法，它将相应地更新选择。

### 选择条目项目 {#selecting-entry-items}

最后，`select(T... items)` 方法允许您通过将一个或多个项目本身传递给此方法以编程方式选择项目，从而相应地更新选择。
