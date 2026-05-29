---
sidebar_position: 30
title: Editing and Refreshing
slug: refreshing
_i18n_hash: 86d395b36fe0cdec90b5a29497a8b0d3
---
在 `Table` 中编辑数据通过与包含数据的 `Repository` 进行交互来实现。`Repository` 是 `Table` 和底层数据集之间的桥梁，提供数据检索、修改和刷新的方法。下面是一个实现了基于所需行的“标题”编辑行为的示例。

<ComponentDemo
path='/webforj/tableeditdata'
files={[
  'src/main/java/com/webforj/samples/views/table/TableEditDataView.java',
  'src/main/java/com/webforj/samples/views/table/TitleEditorComponent.java',
]}
height='600px'
/>

在上面的示例中，`TitleEditorComponent` 类便于编辑所选 `MusicRecord` 的“标题”字段。该组件包括一个用于新标题的输入字段，以及“保存”和“取消”按钮。

为了将编辑组件与 `Table` 连接，`Table` 通过 `VoidElementRenderer` 添加了一个“编辑”按钮。点击此按钮会触发 `TitleEditorComponent` 的 `edit()` 方法，允许用户修改“标题”值。

## 提交方法 {#commit-method}

一旦用户修改标题并点击“保存”按钮，`TitleEditorComponent` 将触发 `save()` 方法。此方法更新对应 `MusicRecord` 的标题并调度一个自定义的 `SaveEvent`。

通过 `commit()` 方法实现对数据仓库的实时更新。此方法在 `onSave` 事件监听器中被使用，以确保通过编辑组件所做的更改反映在底层数据集中。

调用 `commit()` 方法以通知所有感兴趣的组件数据已被更改。`Table` 捕获 `RepositoryCommitEvent`，并根据新数据进行更新。

:::tip 更新和创建条目
调用 `commit()` 方法既更新现有条目，也**插入任何新创建的条目**。
:::
