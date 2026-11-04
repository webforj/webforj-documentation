---
sidebar_position: 30
title: 编辑与刷新
slug: refreshing
description: >-
  Edit Table rows by mutating the bound Repository and call commit to refresh
  the UI through RepositoryCommitEvent.
_i18n_hash: 84dec48293e6fa794713881310b6f303
---
通过与包含 `Table` 数据的 `Repository` 交互，可以编辑 `Table` 中的数据。`Repository` 充当 `Table` 与底层数据集之间的桥梁，提供数据检索、修改和刷新方法。下面的示例实现了根据所选行编辑“标题”的功能。

<ComponentDemo
path='/webforj/tableeditdata'
files={[
  'src/main/java/com/webforj/samples/views/table/TableEditDataView.java',
  'src/main/java/com/webforj/samples/views/table/TitleEditorComponent.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

在上面的示例中，`TitleEditorComponent` 类用于编辑所选 `MusicRecord` 的“标题”字段。该组件包含一个用于输入新标题的输入框，以及“保存”和“取消”按钮。

为了将编辑组件与 `Table` 连接起来，示例通过 `VoidElementRenderer` 在 `Table` 中添加了一个“编辑”按钮。点击此按钮会触发 `TitleEditorComponent` 的 `edit()` 方法，让用户修改“标题”值。

## 提交方法 {#commit-method}

用户修改标题并点击“保存”按钮后，`TitleEditorComponent` 会触发 `save()` 方法。此方法会更新相应 `MusicRecord` 的标题，并分发自定义的 `SaveEvent`。

通过 `commit()` 方法可以实时更新存储库中的数据。此方法在 `onSave` 事件监听器中调用，以确保通过编辑组件所做的更改反映到底层数据集中。

调用 `commit()` 方法会通知所有相关组件数据已发生更改。`Table` 会捕获 `RepositoryCommitEvent`，并根据新数据进行更新。

:::tip 更新和创建条目
调用 `commit()` 方法既会更新现有条目，也会**插入所有新创建的条目**。
:::
