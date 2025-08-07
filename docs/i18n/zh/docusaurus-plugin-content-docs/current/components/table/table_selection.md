---
sidebar_position: 10
title: Selection
slug: selection
_i18n_hash: c0e15da31dab7ea7701ea65b47ce67f8
---
`Table` 组件提供了各种选择功能。可以选择单个项目、多个项目或以编程方式管理选择。

## 选择模式 {#selection-mode}

表格中的选择模式决定了用户如何选择项目。它提供了配置项目选择行为的选项。Table 类提供了一个方法来设置选择模式：

```java
setSelectionMode(SelectionMode selectionMode)
```

可用的 SelectionMode 选项包括：

>- `SINGLE` - （单项选择）  
>- `MULTI` - （多项选择）  
>- `NONE` - （无选择）。

## 选择事件 {#selection-event}

`Table` 组件包会发出与行选择相关的多个事件。 这些事件捕获 `Table` 行选择状态的变化。以下是关键选择事件及其描述：

>- `TableItemSelectEvent` - 当选择一个表项时发出。  
>- `TableItemDeselectEvent` - 当取消选择一个表项时发出。  
>- `TableItemSelectionChange` - 当表中的整体选择发生变化或选择了额外项目时发出。

:::info
当处于多项选择模式并且通过表头复选框进行选择时，不会触发 `TableItemSelectEvent` 和 `TableItemDeselectEvent`。在这种情况下，应使用 `TableItemSelectionChange`。
:::

在下面的示例中，每当用户选择一行时，将触发 `TableItemSelectEvent` 事件。可以通过使用 `onItemSelect()` 方法向表添加监听器来处理该事件。

<ComponentDemo 
path='/webforj/tablesingleselection?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableSingleSelectionView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

## 复选框选择 {#checkbox-selection}

当选择模式为 `MULTI` 时，启用复选框选择，允许用户使用与每一行相关的复选框方便地选择一个或多个项目。此功能在用户需要对所选项目执行批量操作的场景中特别有用。Table 类提供了启用和自定义复选框选择的方法。

通过使用 `setCheckboxSelection(boolean checkboxSelection)` 方法，可以配置在每一行旁边显示复选框，从而允许用户选择项目。下面的程序显示了启用多项选择和复选框选择：

<ComponentDemo 
path='/webforj/tablemultiselection?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableMultiSelectionView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

## 编程选择 {#programatic-selection}

`Table` 组件提供了编程选择方法，允许您通过其键或整个项目来操作已选择的项目。

### 按键选择 {#select-by-key}

`selectKey(Object... keys)` 方法使您能够通过其键以编程方式选择项目。您可以将一个或多个键传递给此方法，它将相应地更新选择。

### 按索引选择 {#select-by-index}

使用 `selectIndex(int... indices)` 方法允许您将一个或多个索引传递给该方法，并相应地更新选定项目。

### 选择整个项目 {#selecting-entire-items}

最后，`select(T... items)` 方法通过将一个或多个项目本身传递给该方法以编程方式选择项目，以相应地更新选择。
