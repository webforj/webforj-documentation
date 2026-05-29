---
title: Events and updates
sidebar_position: 5
_i18n_hash: 0f7a5576086e2922c0549eae23e66061
---
<!-- vale off -->
# 事件和更新 <DocChip chip='since' label='24.00' />
<!-- vale on -->

`Repository` 事件让您对数据更改做出反应。除了自动的用户界面更新，您还可以监听更改以触发自定义逻辑。

## `Repository` 事件生命周期 {#repository-event-lifecycle}

每次调用 `commit()` 将触发一个 <JavadocLink type="data" location="com/webforj/data/repository/event/RepositoryCommitEvent" code="true">RepositoryCommitEvent</JavadocLink>。该事件携带有关更改的信息：

```java
repository.onCommit(event -> {
  // 获取所有已提交的实体
  List<Customer> commits = event.getCommits();
  
  // 检查是否为单个实体更新
  if (event.isSingleCommit()) {
    Customer updated = event.getFirstCommit();
    System.out.println("已更新: " + updated.getName());
  }
});
```

该事件告诉您：
- `getCommits()` - 提交的实体列表
- `isSingleCommit()` - 是否为单个实体的目标更新
- `getFirstCommit()` - 获取第一个（或唯一）实体的便捷方法

对于没有参数的 `commit()`，该事件包含经过过滤后当前存储库中的所有实体。

## 更新策略 {#update-strategies}

这两种提交签名服务于不同的目的：

```java
// 单个实体更新 - 对于单个更改来说高效
Customer customer = customers.get(0);
customer.setStatus("VIP");
repository.commit(customer);

// 批量更新 - 对于多个更改来说高效
products.clear();
products.addAll(loadProductsFromCsv());
repository.commit();
```

单个实体提交是精准的 - 它们告诉连接的组件确切更改了哪一行。[`Table`](../../components/table/overview) 可以仅更新该行的单元格而不影响其他。

批量提交会刷新所有内容。当以下情况时使用它们：
- 多个实体已更改
- 您添加或删除了项目
- 您不确定更改了什么

## 响应式用户界面模式 {#reactive-ui-patterns}

`Repository` 事件让您可以保持摘要显示与数据的同步：

```java
// 自动更新标签
repository.onCommit(event -> {
  double total = sales.stream().mapToDouble(Sale::getAmount).sum();
  totalLabel.setText(String.format("总计: $%.2f", total));
  countLabel.setText("销售: " + sales.size());
});

// 实时结果计数
repository.onCommit(e -> {
  long count = repository.findAll().count();
  resultsLabel.setText(count + " 个产品被找到");
});
```

这些监听器会在每次提交时触发，无论是来自用户操作、数据导入还是程序更新。事件使您可以访问提交的实体，但通常您会从源集合重新计算以包含所有当前数据。

## 内存管理 {#memory-management}

事件监听器持有对您的组件的引用。如果您不删除它们，`Repository` 将在您的组件不再显示后仍然保持它们在内存中：

```java
// 保持引用以便稍后删除
ListenerRegistration<RepositoryCommitEvent<Data>> registration = 
  repository.onCommit(event -> {
    updateDisplay(event.getCommits());
  });

// 当组件被销毁时清理监听器
if (registration != null) {
  registration.remove();
}
```

`onCommit()` 方法返回一个 `ListenerRegistration`。存储此引用，并在您的组件被销毁或不再需要更新时调用 `remove()`。这可以防止在长期运行的应用程序中发生内存泄漏。
