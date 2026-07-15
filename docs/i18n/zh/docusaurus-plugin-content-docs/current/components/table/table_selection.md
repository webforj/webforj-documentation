---
sidebar_position: 10
title: Selection
slug: selection
description: >-
  Configure single, multi, or no-selection modes on the Table and respond to row
  selection events with appropriate listeners.
_i18n_hash: 3dc9f9e7462f97e260e1112a2966dc18
---
`Table` 组件提供了各种选择能力。可以选择单个项目、多个项目或以编程方式管理选择。

:::tip 管理和查询数据
有关如何使用 `Repository` 模式来管理和查询集合的信息，请参阅 [Repository 文章](/docs/advanced/repository/overview)。
:::

## 选择模式 {#selection-mode}

表中的选择模式决定了用户如何选择项目。它提供了配置项目选择行为的选项。Table 类提供了一个方法来设置选择模式：

```java
setSelectionMode(SelectionMode selectionMode)
```

可用的 SelectionMode 选项包括：

>- `SINGLE` - （单选）
>- `MULTI` - （多选）
>- `NONE` - （不选择）。

## 选择事件 {#selection-event}

`Table` 组件包会发出与行选择相关的多个事件。这些事件捕获 `Table` 行选择状态的变化。以下是关键选择事件及其描述：

>- `TableItemSelectEvent` - 当选择表项时发出。
>- `TableItemDeselectEvent` - 当取消选择表项时发出。
>- `TableItemSelectionChange` - 当表中的整体选择发生变化或选择了附加选项时发出。

:::info
当多选模式处于活动状态，并且通过表头复选框进行选择时，`TableItemSelectEvent` 和 `TableItemDeselectEvent` 不会被触发。在这种情况下，应使用 `TableItemSelectionChange`。
:::

在下面的例子中，每当用户选择一行时，将触发 `TableItemSelectEvent` 事件。可以通过使用 `onItemSelect()` 方法为表添加侦听器来处理该事件。

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

当选择模式为 `MULTI` 时，启用复选框选择，允许用户方便地使用与每行关联的复选框选择一个或多个项目。该功能在用户需要对选定项目执行批量操作的场景中尤为有用。Table 类提供了启用和自定义复选框选择的方法。

通过使用 `setCheckboxSelection(boolean checkboxSelection)` 方法，可以配置复选框在每行旁边显示，从而允许用户选择项目。下面的程序演示了启用多选和复选框选择：

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

`Table` 组件提供了编程选择方法，允许您通过键或整个项目来操作选定的项目。

### 通过键选择 {#select-by-key}

`selectKey(Object... keys)` 方法使您能够以编程方式使用项目的键选择项目。您可以将一个或多个键传递给该方法，它将相应地更新选择。

### 选择条目项目 {#selecting-entry-items}

最后，`select(T... items)` 方法允许您通过将一个或多个项目本身传递给该方法以编程方式选择项目，从而相应地更新选择。
