---
title: Events and updates
sidebar_position: 5
_i18n_hash: 45321f5a931bc6efb56376b53662be32
---
<!-- vale off -->
# 事件和更新 <DocChip chip='since' label='24.00' />
<!-- vale on -->

`Repository` 事件让您能够对数据更改做出反应。除了自动的 UI 更新，您还可以监听更改以触发自定义逻辑。

## `Repository` 事件生命周期 {#repository-event-lifecycle}

每次 `commit()` 调用都会触发一个 <JavadocLink type="data" location="com/webforj/data/repository/event/RepositoryCommitEvent" code="true">RepositoryCommitEvent</JavadocLink>。此事件携带有关更改的信息：

```java
repository.onCommit(event -> {
    // 获取所有提交的实体
    List<Customer> commits = event.getCommits();
    
    // 检查是否为单一实体更新
    if (event.isSingleCommit()) {
        Customer updated = event.getFirstCommit();
        System.out.println("已更新: " + updated.getName());
    }
});
```

该事件告诉您：
- `getCommits()` - 已提交的实体列表
- `isSingleCommit()` - 该是否为定向单实体更新
- `getFirstCommit()` - 获取第一个（或唯一）实体的便捷方法

对于没有参数的 `commit()`，该事件包含经过过滤后当前处于存储库中的所有实体。

## 更新策略 {#update-strategies}

这两种提交签名适用于不同的目的：

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

单实体提交是外科手术般的 - 它们准确告诉连接的组件哪一行发生了变化。[`Table`](../../components/table/overview) 仅能更新那一行的单元格，而不会影响其他内容。

批量提交会刷新所有内容。使用它们的时机是：
- 多个实体发生了更改
- 您添加或删除了项目
- 您不确定发生了什么更改

## 响应式 UI 模式 {#reactive-ui-patterns}

`Repository` 事件让您可以将摘要显示与数据保持同步：

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
    resultsLabel.setText(count + " 个产品找到");
});
```

这些监听器在每次提交时触发，无论是来自用户操作、数据导入还是程序化更新。该事件让您访问提交的实体，但通常您会从源集合重新计算以包括所有当前数据。

## 内存管理 {#memory-management}

事件监听器持有对您组件的引用。如果您不将其移除，`Repository` 即使在组件不再显示后也会将其保留在内存中：

```java
// 保留引用以便稍后移除
ListenerRegistration<RepositoryCommitEvent<Data>> registration = 
    repository.onCommit(event -> {
        updateDisplay(event.getCommits());
    });

// 组件销毁时清理监听器
if (registration != null) {
    registration.remove();
}
```

`onCommit()` 方法返回一个 `ListenerRegistration`。存储此引用，并在组件销毁或不再需要更新时调用 `remove()`。这可以防止长时间运行的应用程序中的内存泄漏。
