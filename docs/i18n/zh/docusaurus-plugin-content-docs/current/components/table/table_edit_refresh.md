---
sidebar_position: 30
title: Editing and Refreshing
slug: refreshing
_i18n_hash: 0c57bd3fd3a9adb9cb7275e23efa725f
---
在 `Table` 中编辑数据通过与包含该 `Table` 数据的 `Repository` 交互实现。`Repository` 作为 `Table` 与底层数据集之间的桥梁，提供数据检索、修改和刷新的方法。以下是一个示例，展示了如何实现编辑所需行的“标题”的行为。

<ComponentDemo 
path='/webforj/tableeditdata?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableEditDataView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TitleEditorComponent.java']}
height='600px'
/>

在上述示例中，`TitleEditorComponent` 类便于编辑选定 `MusicRecord` 的“标题”字段。该组件包括一个用于输入新标题的字段，以及“保存”和“取消”按钮。

为了将编辑组件与 `Table` 连接，通过 `VoidElementRenderer` 向 `Table` 添加一个“编辑”按钮。点击该按钮会触发 `TitleEditorComponent` 的 `edit()` 方法，允许用户修改“标题”。

## 提交方法 {#commit-method}

一旦用户修改了标题并点击“保存”按钮，`TitleEditorComponent` 会触发 `save()` 方法。该方法更新相应 `MusicRecord` 的标题并分发自定义的 `SaveEvent`。

通过 `commit()` 方法实现对存储库中数据的实时更新。该方法在 `onSave` 事件监听器中使用，确保通过编辑组件所做的更改反映在底层数据集中。

调用 `commit()` 方法以通知所有相关组件数据已更改。`Table` 捕捉 `RepositoryCommitEvent`，并根据新数据进行更新。

:::tip 更新和创建条目
调用 `commit()` 方法既更新现有条目，也**插入任何新创建的条目**。
:::
