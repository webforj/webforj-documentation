---
sidebar_position: 30
title: Editing and Refreshing
slug: refreshing
description: >-
  Edit Table rows by mutating the bound Repository and call commit to refresh
  the UI through RepositoryCommitEvent.
_i18n_hash: 6ecf362668be7d0633c3c13e7da068ec
---
在 `Table` 中编辑数据是通过与包含 `Table` 数据的 `Repository` 进行交互来实现的。 `Repository` 作为 `Table` 和基础数据集之间的桥梁，提供数据检索、修改和刷新的方法。以下是一个实现了编辑所需行的“标题”行为的示例。

<ComponentDemo
path='/webforj/tableeditdata'
files={[
  'src/main/java/com/webforj/samples/views/table/TableEditDataView.java',
  'src/main/java/com/webforj/samples/views/table/TitleEditorComponent.java',
]}
height='600px'
/>

在上述示例中，`TitleEditorComponent` 类便于为选定的 `MusicRecord` 编辑“标题”字段。该组件包括用于新标题的输入字段，以及“保存”和“取消”按钮。

为了将编辑组件与 `Table` 连接，在 `Table` 中通过 `VoidElementRenderer` 添加了一个“编辑”按钮。单击此按钮会触发 `TitleEditorComponent` 的 `edit()` 方法，允许用户修改“标题”值。

## 提交方法 {#commit-method}

用户修改标题并点击“保存”按钮后，`TitleEditorComponent` 会触发 `save()` 方法。该方法更新相应 `MusicRecord` 的标题，并分发自定义的 `SaveEvent`。

通过 `commit()` 方法实现对存储库中数据的实时更新。此方法在 `onSave` 事件监听器中使用，确保通过编辑组件所做的更改反映在基础数据集中。

调用 `commit()` 方法以通知所有感兴趣的组件数据已经更改。`Table` 捕获到 `RepositoryCommitEvent`，并根据新数据进行更新。

:::tip 更新和创建条目
调用 `commit()` 方法既更新现有条目，也 **插入任何已创建的新条目**。
:::
