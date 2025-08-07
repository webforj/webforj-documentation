---
title: Repository
sidebar_position: 1
sidebar_class_name: new-content
_i18n_hash: 8dfc90f24bba893de434f1a41d5776c6
---
<!-- vale off -->
# 存储库 <DocChip chip='since' label='24.00' />
<!-- vale on -->

`Repository`模式在webforJ中提供了一种标准化的方式来管理和查询实体集合。它充当了你的UI组件和数据之间的抽象层，使得在维护一致行为的同时，能够轻松地处理不同的数据源。

## 为什么使用存储库 {#why-use-repository}

`Repository`消除了手动更新，同时保持你的原始数据不变：

```java
// 没有存储库 - 手动更新
List<Customer> customers = loadCustomers();
Table<Customer> table = new Table<>();
table.setItems(customers);

// 添加需要完全重新加载
customers.add(newCustomer);
table.setItems(customers); // 必须重新加载所有内容
```

```java
// 使用存储库 - 自动同步
List<Customer> customers = loadCustomers();
CollectionRepository<Customer> repository = new CollectionRepository<>(customers);
Table<Customer> table = new Table<>();
table.setRepository(repository);

// 添加自动同步
customers.add(newCustomer);
repository.commit(newCustomer); // 只更新更改的内容
```

## 集合存储库 {#collection-repository}

<JavadocLink type="data" location="com/webforj/data/repository/CollectionRepository" code="true">CollectionRepository</JavadocLink>是最常见的实现，封装了任何Java集合：

```java
// 从ArrayList
List<Customer> customers = new ArrayList<>();
CollectionRepository<Customer> customerRepo = new CollectionRepository<>(customers);

// 从HashSet  
Set<String> tags = new HashSet<>();
CollectionRepository<String> tagRepo = new CollectionRepository<>(tags);

// 从任何集合
Collection<Employee> employees = getEmployeesFromHR();
CollectionRepository<Employee> employeeRepo = new CollectionRepository<>(employees);
```

## 数据同步 {#data-synchronization}

`Repository`充当数据和UI组件之间的桥梁。当数据发生变化时，你通过`commit()`方法通知存储库：

```java
List<Product> products = new ArrayList<>();
CollectionRepository<Product> repository = new CollectionRepository<>(products);

// 添加新产品
Product newProduct = new Product("P4", "Gizmo", 79.99, 15);
products.add(newProduct);
repository.commit(); // 所有连接的组件更新

// 更新现有产品  
products.get(0).setPrice(89.99);
repository.commit(products.get(0)); // 只更新这一特定行

// 删除产品
products.remove(2);
repository.commit(); // 刷新视图
```

commit方法有两个签名：
- `commit()` - 告诉存储库刷新所有内容。它会触发一个`RepositoryCommitEvent`，包含所有当前数据
- `commit(entity)` - 针对特定实体。存储库通过其键找到该实体，并只更新受影响的UI元素

:::important 提交单个实体
这一区别对性能很重要。当你在一个1000行的表中更新一个字段时，`commit(entity)`只更新该单元格，而`commit()`会刷新所有行。
:::

## 过滤数据 {#filtering-data}

存储库的过滤器控制流向连接组件的数据。你的基础集合保持不变，因为过滤器充当了一个透镜：

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

当你设置过滤器时，`Repository`：
1. 对集合中的每个项目应用谓词
2. 创建一个匹配项的过滤流
3. 通知连接的组件更新其显示

过滤器在改变之前会持续存在。添加到集合中的新项目会自动通过当前过滤器进行测试。

## 处理实体键 {#working-with-entity-keys}

当你的实体实现<JavadocLink type="data" location="com/webforj/data/HasEntityKey" code="true">HasEntityKey</JavadocLink>时，存储库可以通过其ID找到并更新特定项目：

```java
public class Customer implements HasEntityKey {
    private String customerId;
    private String name;
    private String email;
    
    @Override
    public Object getEntityKey() {
        return customerId;
    }
    
    // 构造函数和getter/setter...
}

// 按键查找
Optional<Customer> customer = repository.find("C001");

// 更新特定客户
customer.ifPresent(c -> {
    c.setEmail("newemail@example.com");
    repository.commit(c); // 仅更新该客户的行
});
```

没有`HasEntityKey`：
- `repository.find("C001")`不会找到你的客户，因为它查找的是等于"C001"的对象
- `repository.commit(entity)`仍然有效，但依赖于对象平等
- UI组件不能通过ID选择项目，只能通过对象引用

## UI集成 {#ui-integration}

`Repository`与数据感知组件集成：

```java
// 创建存储库和表
List<Customer> customers = new ArrayList<>();
CollectionRepository<Customer> repository = new CollectionRepository<>(customers);

Table<Customer> table = new Table<>();
table.setRepository(repository);
table.addColumn("ID", Customer::getId);
table.addColumn("Name", Customer::getName);
table.addColumn("Email", Customer::getEmail);

// 添加数据 - 表格自动更新
customers.add(new Customer("C001", "Alice Johnson", "alice@example.com"));
repository.commit();
```

## 下一步 {#next-steps}

<DocCardList className="topics-section" />
