---
title: Events and updates
sidebar_position: 5
_i18n_hash: b2973e75abc879992ab1e235ba5d8b5e
---
<!-- vale off -->
# 事件和更新 <DocChip chip='since' label='24.00' />
<!-- vale on -->

`Repository` 事件让您能够对数据变化做出反应。在自动 UI 更新之外，您还可以监听变化以触发自定义逻辑。

## `Repository` 事件生命周期 {#repository-event-lifecycle}

每次调用 `commit()` 都会触发一个 <JavadocLink type="data" location="com/webforj/data/repository/event/RepositoryCommitEvent" code="true">RepositoryCommitEvent</JavadocLink>。该事件携带有关发生了什么变化的信息：

```java
repository.onCommit(event -> {
    // 获取所有提交的实体
    List<Customer> commits = event.getCommits();
    
    // 检查是否是单个实体更新
    if (event.isSingleCommit()) {
        Customer updated = event.getFirstCommit();
        System.out.println("已更新: " + updated.getName());
    }
});
```

事件告诉您：
- `getCommits()` - 已提交的实体列表
- `isSingleCommit()` - 是否是针对单个实体的更新
- `getFirstCommit()` - 获取第一个（或唯一）实体的便利方法

对于没有参数的 `commit()`，事件包含在过滤后当前在仓库中的所有实体。

## 更新策略 {#update-strategies}

两个提交签名服务于不同的目的：

```java
// 单个实体更新 - 适用于单独更改
Customer customer = customers.get(0);
customer.setStatus("VIP");
repository.commit(customer);

// 批量更新 - 适用于多个更改
products.clear();
products.addAll(loadProductsFromCsv());
repository.commit();
```

单个实体提交是精准的 - 它们精确地告诉连接的组件哪些行发生了变化。[`Table`](../../components/table/overview) 可以仅更新该行的单元格，而不影响其他任何内容。

批量提交会刷新所有内容。当以下情况发生时使用它们：
- 多个实体发生变化
- 您添加或移除了项目
- 您不确定发生了什么变化

## 响应式 UI 模式 {#reactive-ui-patterns}

`Repository` 事件让您能够保持摘要显示与数据同步：

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

这些监听器在每次提交时触发，无论是来自用户的操作、数据导入，还是程序化更新。事件使您能够访问提交的实体，但通常您会从源集合重新计算，以包含所有当前数据。

## 内存管理 {#memory-management}

事件监听器持有对您组件的引用。如果您不移除它们，`Repository` 会在组件不再显示后仍然将其保留在内存中：

```java
// 保留引用以便后期移除
ListenerRegistration<RepositoryCommitEvent<Data>> registration = 
    repository.onCommit(event -> {
        updateDisplay(event.getCommits());
    });

// 在组件销毁时清理监听器
if (registration != null) {
    registration.remove();
}
```

`onCommit()` 方法返回一个 `ListenerRegistration`。存储此引用并在组件销毁或不再需要更新时调用 `remove()`。这可以防止长时间运行的应用程序中的内存泄漏。
