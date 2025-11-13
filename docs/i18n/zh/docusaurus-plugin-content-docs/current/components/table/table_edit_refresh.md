---
sidebar_position: 30
title: 编辑与刷新
slug: refreshing
_i18n_hash: 39816123675d62a6dda185187e8d13e2
---
在 `Table` 中编辑数据是通过与包含 `Table` 数据的 `Repository` 进行交互来实现的。`Repository` 作为 `Table` 与底层数据集之间的桥梁，提供数据检索、修改和刷新的方法。下面是一个实现了编辑所需行的 "标题" 行为的示例。

<ComponentDemo 
path='/webforj/tableeditdata?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableEditDataView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TitleEditorComponent.java']}
height='600px'
/>

在上述示例中，`TitleEditorComponent` 类为选定的 `MusicRecord` 方便地提供了编辑 "标题" 字段的功能。该组件包括一个用于输入新标题的输入字段，以及 "保存" 和 "取消" 按钮。

为了将编辑组件与 `Table` 连接，通过 `VoidElementRenderer` 向 `Table` 添加了一个 "编辑" 按钮。单击该按钮会触发 `TitleEditorComponent` 的 `edit()` 方法，使用户能够修改 "标题"。

## 提交方法 {#commit-method}

一旦用户修改了标题并单击 "保存" 按钮，`TitleEditorComponent` 会触发 `save()` 方法。该方法会更新相应 `MusicRecord` 的标题，并触发自定义的 `SaveEvent`。

通过 `commit()` 方法实现对数据的实时更新。此方法在 `onSave` 事件监听器中使用，确保通过编辑组件所做的更改反映在底层数据集中。

调用 `commit()` 方法，用于通知所有感兴趣的组件数据已被更改。`Table` 会捕获 `RepositoryCommitEvent`，并根据新数据进行更新。

:::tip 更新和创建条目
调用 `commit()` 方法既更新现有条目，也**插入任何新创建的条目**。
:::
