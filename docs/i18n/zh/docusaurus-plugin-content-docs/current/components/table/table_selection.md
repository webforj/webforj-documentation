---
sidebar_position: 10
title: Selection
slug: selection
_i18n_hash: 91df1121dac6d410883e3b43ddf767d5
---
`Table` 组件提供了多种选择功能。它可以选择单一项目、多个项目或以编程方式管理选择。

## 选择模式 {#selection-mode}

表格中的选择模式决定了用户如何选择项目。它提供了配置项目选择行为的选项。Table 类提供了一种设置选择模式的方法：

```java
setSelectionMode(SelectionMode selectionMode)
```

可用的 SelectionMode 选项包括：

>- `SINGLE` - （单一选择） 
>- `MULTI` - （多重选择）
>- `NONE` - （无选择）。

## 选择事件 {#selection-event}

`Table` 组件包会发出与行选择相关的多个事件。这些事件捕获 `Table` 行选择状态的变化。以下是关键选择事件及其描述：

>- `TableItemSelectEvent` -  当选择表格项目时发出。
>- `TableItemDeselectEvent` - 当取消选择表格项目时发出。
>- `TableItemSelectionChange` - 当表格中的整体选择发生变化，或选择额外项目时发出。

:::info
当多重选择模式处于活动状态，并且选择是通过表头复选框进行时，不会触发 `TableItemSelectEvent` 和 `TableItemDeselectEvent`。在这种情况下，应使用 `TableItemSelectionChange`。
:::

在下面的示例中，每当用户选择一行时，都会触发 `TableItemSelectEvent` 事件。可以通过使用 `onItemSelect()` 方法向表格添加监听器来处理该事件。

<ComponentDemo 
path='/webforj/tablesingleselection?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableSingleSelectionView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

## 复选框选择 {#checkbox-selection}

当选择模式为 `MULTI` 时，启用复选框选择，允许用户使用与每一行关联的复选框方便地选择一个或多个项目。此功能在用户需要对选定项目执行批量操作的场景中特别有用。Table 类提供了启用和定制复选框选择的方法。

通过使用 `setCheckboxSelection(boolean checkboxSelection)` 方法，可以配置复选框显示在每一行旁边，从而允许用户选择项目。下面的程序展示了启用多重选择和复选框选择的情况：

<ComponentDemo 
path='/webforj/tablemultiselection?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableMultiSelectionView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

## 编程选择 {#programatic-selection}

`Table` 组件提供了编程选择方法，允许您通过键或整个项目来操作选择的项目。

### 根据键选择 {#select-by-key}

`selectKey(Object... keys)` 方法使您能够通过它们的键以编程方式选择项目。您可以将一个或多个键传递给此方法，它将相应地更新选择。

### 根据索引选择 {#select-by-index}

使用 `selectIndex(int... indices)` 方法可以将一个或多个索引传递给该方法，并相应地更新所选项目。

### 选择整个项目 {#selecting-entire-items}

最后，`select(T... items)` 方法允许您通过向该方法传递一个或多个项目本身以编程方式选择项目，从而相应地更新选择。
