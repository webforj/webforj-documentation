---
title: Repository
sidebar_position: 1
sidebar_class_name: has-new-content
_i18n_hash: c285de15c19063af40c61f15cebf4dc1
---
<!-- vale off -->
# 仓库 <DocChip chip='since' label='24.00' />
<!-- vale on -->

`Repository` 模式在 webforJ 中提供了一种标准化的方式来管理和查询实体集合。它充当了 UI 组件和数据之间的抽象层，使得在保持一致行为的同时，能够轻松地处理不同的数据源。

## 为什么使用仓库 {#why-use-repository}

`Repository` 消除了手动更新，同时保持原始数据不变：

```java
// 没有仓库 - 手动更新
List<Customer> customers = loadCustomers();
Table<Customer> table = new Table<>();
table.setItems(customers);

// 添加需要完全重新加载
customers.add(newCustomer);
table.setItems(customers); // 必须重新加载所有内容
```

```java
// 使用仓库 - 自动同步
List<Customer> customers = loadCustomers();
CollectionRepository<Customer> repository = new CollectionRepository<>(customers);
Table<Customer> table = new Table<>();
table.setRepository(repository);

// 添加自动同步
customers.add(newCustomer);
repository.commit(newCustomer); // 只更新变化的部分
```

## 集合仓库 {#collection-repository}

<JavadocLink type="data" location="com/webforj/data/repository/CollectionRepository" code="true">CollectionRepository</JavadocLink> 是最常见的实现，包装任何 Java 集合：

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

`Repository` 充当数据和 UI 组件之间的桥梁。当数据更改时，您通过 `commit()` 方法通知仓库：

```java
List<Product> products = new ArrayList<>();
CollectionRepository<Product> repository = new CollectionRepository<>(products);

// 添加新产品
Product newProduct = new Product("P4", "Gizmo", 79.99, 15);
products.add(newProduct);
repository.commit(); // 所有连接的组件更新

// 更新现有产品  
products.get(0).setPrice(89.99);
repository.commit(products.get(0)); // 仅更新这一特定行

// 删除产品
products.remove(2);
repository.commit(); // 刷新视图
```

commit 方法有两种签名：
- `commit()` - 告诉仓库刷新所有内容。它触发 `RepositoryCommitEvent`，并带有所有当前数据
- `commit(entity)` - 定位特定实体。仓库通过其键找到该实体，并仅更新受影响的 UI 元素

:::important 提交单个实体
这种区分关系到性能。当您在一个 1000 行的表中更新一个字段时，`commit(entity)` 只更新该单元格，而 `commit()` 则会刷新所有行。
:::

## 过滤数据 {#filtering-data}

仓库的过滤器控制传送到连接组件的数据流。您的底层集合保持不变，因为过滤器充当透镜：

```java
// 根据库存可用性过滤
repository.setBaseFilter(product -> product.getStock() > 0);

// 根据类别过滤
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
1. 将谓词应用于集合中的每一项
2. 创建一个匹配项的过滤流
3. 通知连接组件更新其显示

过滤器会持续存在，直到您更改它。新增到集合中的项目会自动测试当前过滤器。

## 处理实体键 {#working-with-entity-keys}

仓库需要唯一识别实体，以支持 `find()` 和 `commit(entity)` 等操作。有两种方法可以定义实体的识别方式：

### 使用 `HasEntityKey` 接口 {#using-hasentitykey}

在您的实体类上实现 <JavadocLink type="data" location="com/webforj/data/HasEntityKey" code="true">HasEntityKey</JavadocLink>：

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

// 按键查找
Optional<Customer> customer = repository.find("C001");

// 更新特定客户
customer.ifPresent(c -> {
    c.setEmail("newemail@example.com");
    repository.commit(c); // 仅更新该客户的行
});
```

### 使用自定义键提供者 <DocChip chip='since' label='25.10' /> {#using-custom-key-provider}

对于那些您无法或不想实现 `HasEntityKey` 的实体（如 JPA 实体），使用 `setKeyProvider()`：

```java
@Entity
public class Product {
    @Id
    private Long id;
    private String name;
    private double price;

    // JPA 管理的实体
}

// 配置仓库以使用 getId() 方法
CollectionRepository<Product> repository = new CollectionRepository<>(products);
repository.setKeyProvider(Product::getId);

// 现在查找使用 ID
Optional<Product> product = repository.find(123L);
```

### 选择一种方法 {#choosing-approach}

这两种方法都可以使用，但在以下情况下，建议使用 `setKeyProvider()`：
- 使用具有 `@Id` 字段的 JPA 实体
- 您无法修改实体类
- 您需要不同的键策略以应对不同的仓库

使用 `HasEntityKey` 当：
- 您控制实体类
- 键提取逻辑复杂
- 您希望实体定义其自身的身份

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

// 添加数据 - 表格自动更新
customers.add(new Customer("C001", "Alice Johnson", "alice@example.com"));
repository.commit();
```

## 下一步 {#next-steps}

<DocCardList className="topics-section" />
