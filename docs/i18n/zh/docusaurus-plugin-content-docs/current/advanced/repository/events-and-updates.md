---
title: Events and updates
sidebar_position: 5
description: >-
  React to repository commits with RepositoryCommitEvent listeners, choose
  single-entity or bulk updates, and manage listener registrations.
_i18n_hash: 9ce2e7d25b38cd0d04acd30c80edffb3
---
<!-- vale off -->
# 事件和更新 <DocChip chip='since' label='24.00' />
<!-- vale on -->

`Repository` 事件让您可以对数据变化做出反应。除了自动的用户界面更新，您还可以监听变化以触发自定义逻辑。

## `Repository` 事件生命周期 {#repository-event-lifecycle}

每次 `commit()` 调用都会触发一个 <JavadocLink type="data" location="com/webforj/data/repository/event/RepositoryCommitEvent" code="true">RepositoryCommitEvent</JavadocLink>。该事件包含有关变化的信息：

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
- `getCommits()` - 已提交的实体列表
- `isSingleCommit()` - 是否为目标单实体更新
- `getFirstCommit()` - 获取第一个（或唯一）实体的便利方法

对于没有参数的 `commit()`，事件包含经过过滤后当前在仓库中的所有实体。

## 更新策略 {#update-strategies}

这两个提交签名的目的不同：

```java
// 单实体更新 - 适用于个别更改
Customer customer = customers.get(0);
customer.setStatus("VIP");
repository.commit(customer);

// 批量更新 - 适用于多个更改
products.clear();
products.addAll(loadProductsFromCsv());
repository.commit();
```

单实体提交是精确的 - 它告知连接的组件确切的哪一行发生了变化。[`Table`](../../components/table/overview) 可以仅更新该行的单元格，而不影响其他内容。

批量提交会刷新所有内容。当以下情况发生时使用它们：
- 多个实体已更改
- 您添加或删除了项目
- 您不确定更改了什么 

## 响应式 UI 模式 {#reactive-ui-patterns}

`Repository` 事件让您可以使摘要显示与您的数据保持同步：

```java
// 自动更新标签
repository.onCommit(event -> {
  double total = sales.stream().mapToDouble(Sale::getAmount).sum();
  totalLabel.setText(String.format("总计: $%.2f", total));
  countLabel.setText("销售量: " + sales.size());
});

// 实时结果计数
repository.onCommit(e -> {
  long count = repository.findAll().count();
  resultsLabel.setText(count + " 件产品找到");
});
```

这些监听器会在每次提交时触发，无论是来自用户操作、数据导入还是程序更新。事件让您访问已提交的实体，但通常您会从源集合重新计算，以包括所有当前数据。

## 内存管理 {#memory-management}

事件监听器持有对您组件的引用。如果您没有将它们移除，则 `Repository` 会在内存中保持您的组件，即使它们不再显示：

```java
// 保持引用以便稍后移除
ListenerRegistration<RepositoryCommitEvent<Data>> registration =
  repository.onCommit(event -> {
    updateDisplay(event.getCommits());
  });

// 当组件被销毁时清理监听器
if (registration != null) {
  registration.remove();
}
```

`onCommit()` 方法返回一个 `ListenerRegistration`。存储此引用，并在您的组件被销毁或不再需要更新时调用 `remove()`。这可以防止在长期运行的应用程序中出现内存泄漏。
