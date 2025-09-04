---
title: Repository
sidebar_position: 1
sidebar_class_name: new-content
_i18n_hash: dc6f7bbfe82d68565cbe8da6436f080c
---
<!-- vale off -->
# 仓库 <DocChip chip='since' label='24.00' />
<!-- vale on -->

`Repository` 模式在 webforJ 中提供了一种标准化的方法来管理和查询实体集合。它充当您的 UI 组件与数据之间的抽象层，使您能够轻松处理不同的数据源，同时保持一致的行为。

## 为什么使用仓库 {#why-use-repository}

`Repository` 消除了手动更新的需要，同时保持您原始数据的完整性：

```java
// 没有 Repository - 手动更新
List<Customer> customers = loadCustomers();
Table<Customer> table = new Table<>();
table.setItems(customers);

// 添加需要完全重新加载
customers.add(newCustomer);
table.setItems(customers); // 必须重新加载所有内容
```

```java
// 使用 Repository - 自动同步
List<Customer> customers = loadCustomers();
CollectionRepository<Customer> repository = new CollectionRepository<>(customers);
Table<Customer> table = new Table<>();
table.setRepository(repository);

// 添加自动同步
customers.add(newCustomer);
repository.commit(newCustomer); // 仅更新更改的部分
```

## 集合仓库 {#collection-repository}

<JavadocLink type="data" location="com/webforj/data/repository/CollectionRepository" code="true">CollectionRepository</JavadocLink> 是最常见的实现，可以包装任何 Java 集合：

```java
// 从 ArrayList
List<Customer> customers = new ArrayList<>();
CollectionRepository<Customer> customerRepo = new CollectionRepository<>(customers);

// 从 HashSet  
Set<String> tags = new HashSet<>();
CollectionRepository<String> tagRepo = new CollectionRepository<>(tags);

// 从任何集合
Collection<Employee> employees = getEmployeesFromHR();
CollectionRepository<Employee> employeeRepo = new CollectionRepository<>(employees);
```

## 数据同步 {#data-synchronization}

`Repository` 作为数据与 UI 组件之间的桥梁。当数据更改时，您通过 `commit()` 方法通知仓库：

```java
List<Product> products = new ArrayList<>();
CollectionRepository<Product> repository = new CollectionRepository<>(products);

// 添加新产品
Product newProduct = new Product("P4", "Gizmo", 79.99, 15);
products.add(newProduct);
repository.commit(); // 所有连接的组件更新

// 更新现有产品  
products.get(0).setPrice(89.99);
repository.commit(products.get(0)); // 仅更新这个特定行

// 移除产品
products.remove(2);
repository.commit(); // 刷新视图
```

commit 方法有两个签名：
- `commit()` - 告诉仓库刷新所有内容。它触发带有所有当前数据的 `RepositoryCommitEvent`
- `commit(entity)` - 针对特定实体。仓库通过其键找到此实体并仅更新受影响的 UI 元素

:::important 提交单个实体
这种区分对于性能很重要。当您在一个 1000 行的表中更新一个字段时，`commit(entity)` 仅更新该单元格，而 `commit()` 将刷新所有行。
:::

## 过滤数据 {#filtering-data}

仓库的过滤器控制连接组件接收到的数据。您的基础集合保持不变，因为过滤器充当了一个透镜：

```java
// 按库存可用性过滤
repository.setBaseFilter(product -> product.getStock() > 0);

// 按类别过滤
repository.setBaseFilter(product -> "Electronics".equals(product.getCategory()));

// 组合多个条件
repository.setBaseFilter(product -> 
    product.getCategory().equals("Electronics") && 
    product.getStock() > 0 && 
    product.getPrice() < 100.0
);

// 清除过滤器
repository.setBaseFilter(null);
```

当您设置过滤器时，`Repository`：
1. 对集合中的每个项目应用谓词
2. 创建匹配项目的过滤流
3. 通知连接的组件更新其显示

过滤器会一直存在，直到您更改它。添加到集合中的新项目会自动测试当前过滤条件。

## 使用实体键 {#working-with-entity-keys}

当您的实体实现 <JavadocLink type="data" location="com/webforj/data/HasEntityKey" code="true">HasEntityKey</JavadocLink> 时，仓库可以通过其 ID 查找和更新特定项目：

```java
public class Customer implements HasEntityKey {
    private String customerId;
    private String name;
    private String email;
    
    @Override
    public Object getEntityKey() {
        return customerId;
    }
    
    // 构造函数和 getter/setter...
}

// 通过键查找
Optional<Customer> customer = repository.find("C001");

// 更新特定客户
customer.ifPresent(c -> {
    c.setEmail("newemail@example.com");
    repository.commit(c); // 仅更新这个客户的行
});
```

没有 `HasEntityKey`：
- `repository.find("C001")` 将找不到您的客户，因为它寻找一个等于 "C001" 的对象
- `repository.commit(entity)` 仍然有效，但依赖于对象相等
- UI 组件不能通过 ID 选择项目，只能通过对象引用选择

## UI 集成 {#ui-integration}

`Repository` 与数据感知组件集成：

```java
// 创建仓库和表
List<Customer> customers = new ArrayList<>();
CollectionRepository<Customer> repository = new CollectionRepository<>(customers);

Table<Customer> table = new Table<>();
table.setRepository(repository);
table.addColumn("ID", Customer::getId);
table.addColumn("Name", Customer::getName);
table.addColumn("Email", Customer::getEmail);

// 添加数据 - 表自动更新
customers.add(new Customer("C001", "Alice Johnson", "alice@example.com"));
repository.commit();
```

## 下一步 {#next-steps}

<DocCardList className="topics-section" />
