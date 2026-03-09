---
sidebar_position: 30
title: Editing and Refreshing
slug: refreshing
_i18n_hash: 1a8c3b1ab877d759906737431d8601e1
---
在 `Table` 中编辑数据是通过与包含 `Table` 数据的 `Repository` 交互来实现的。`Repository` 充当 `Table` 和底层数据集之间的桥梁，提供数据检索、修改和刷新的方法。以下是一个示例，实现了编辑所需行的“标题”行为。

<ComponentDemo 
path='/webforj/tableeditdata?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableEditDataView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TitleEditorComponent.java']}
height='600px'
/>

在上述示例中，`TitleEditorComponent` 类用于编辑所选 `MusicRecord` 的“标题”字段。该组件包括一个新标题的输入字段，以及“保存”和“取消”按钮。

为了将编辑组件与 `Table` 连接，通过 `VoidElementRenderer` 向 `Table` 添加了一个“编辑”按钮。点击该按钮会触发 `TitleEditorComponent` 的 `edit()` 方法，允许用户修改“标题”值。

## 提交方法 {#commit-method}

一旦用户修改标题并点击“保存”按钮，`TitleEditorComponent` 将触发 `save()` 方法。该方法更新相应 `MusicRecord` 的标题，并分发一个自定义的 `SaveEvent`。

通过 `commit()` 方法实现对仓库中数据的实时更新。该方法在 `onSave` 事件监听器中使用，确保通过编辑组件所做的更改反映在底层数据集中。

调用 `commit()` 方法以通知所有相关组件数据已发生更改。`Table` 捕获 `RepositoryCommitEvent`，并根据新数据进行更新。

:::tip 更新和创建条目
调用 `commit()` 方法既更新了现有条目，又**插入任何新创建的条目**。
:::
