---
title: Repository
sidebar_position: 1
sidebar_class_name: has-new-content
_i18n_hash: b9a08226a8ace111beea4ab2a03ff79f
---
<!-- vale off -->
# 仓库 <DocChip chip='since' label='24.00' />
<!-- vale on -->

`Repository`模式在webforJ中提供了一种标准化的方法来管理和查询实体集合。它充当了您的UI组件和数据之间的抽象层，使您能够轻松地处理不同的数据源，同时保持一致的行为。

## 为什么使用仓库 {#why-use-repository}

`Repository`消除了手动更新，同时保持您的原始数据完好无损：

```java
// 没有Repository - 手动更新
List<Customer> customers = loadCustomers();
Table<Customer> table = new Table<>();
table.setItems(customers);

// 添加需要完全重新加载
customers.add(newCustomer);
table.setItems(customers); // 必须重新加载所有内容
```

```java
// 使用Repository - 自动同步
List<Customer> customers = loadCustomers();
CollectionRepository<Customer> repository = new CollectionRepository<>(customers);
Table<Customer> table = new Table<>();
table.setRepository(repository);

// 添加自动同步
customers.add(newCustomer);
repository.commit(newCustomer); // 仅更新已更改的部分
```

## 集合仓库 {#collection-repository}

<JavadocLink type="data" location="com/webforj/data/repository/CollectionRepository" code="true">CollectionRepository</JavadocLink>是最常见的实现，并且包装任何Java集合：

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

`Repository`充当数据和UI组件之间的桥梁。当数据更改时，您通过`commit()`方法通知仓库：

```java
List<Product> products = new ArrayList<>();
CollectionRepository<Product> repository = new CollectionRepository<>(products);

// 添加新产品
Product newProduct = new Product("P4", "小工具", 79.99, 15);
products.add(newProduct);
repository.commit(); // 所有连接的组件更新

// 更新现有产品  
products.get(0).setPrice(89.99);
repository.commit(products.get(0)); // 仅更新该特定行

// 移除产品
products.remove(2);
repository.commit(); // 刷新视图
```

commit方法有两个签名：
- `commit()` - 告诉仓库刷新所有内容。它触发一个`RepositoryCommitEvent`，包含所有当前数据
- `commit(entity)` - 针对特定实体。仓库通过其键找到该实体并仅更新受影响的UI元素

:::important 提交单个实体
这个区别对性能很重要。当您在1000行表中更新一个字段时，`commit(entity)`只更新该单元格，而`commit()`将刷新所有行。
:::

## 筛选数据 {#filtering-data}

仓库的筛选器控制了数据流向连接的组件。您的基础集合保持不变，因为筛选器充当透镜：

```java
// 按库存可用性筛选
repository.setBaseFilter(product -> product.getStock() > 0);

// 按类别筛选
repository.setBaseFilter(product -> "电子产品".equals(product.getCategory()));

// 组合多个条件
repository.setBaseFilter(product -> 
    product.getCategory().equals("电子产品") && 
    product.getStock() > 0 && 
    product.getPrice() < 100.0
);

// 清除筛选
repository.setBaseFilter(null);
```

当您设置筛选器时，`Repository`：
1. 将谓词应用于集合中的每个项目
2. 创建匹配项的过滤流
3. 通知连接的组件更新其显示

筛选器在您更改之前会一直存在。添加到集合的新项目会自动测试当前筛选器。

## 使用实体键 {#working-with-entity-keys}

仓库需要唯一地识别实体，以支持诸如`find()`和`commit(entity)`之类的操作。有两种方式可以定义如何识别实体：

### 使用HasEntityKey接口 {#using-hasentitykey}

在您的实体类上实现<JavadocLink type="data" location="com/webforj/data/HasEntityKey" code="true">HasEntityKey</JavadocLink>：

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

### 使用自定义键提供程序<DocChip chip='since' label='25.10' /> {#using-custom-key-provider}

对于您无法或不想实现`HasEntityKey`的实体（如JPA实体），使用`setKeyProvider()`：

```java
@Entity
public class Product {
    @Id
    private Long id;
    private String name;
    private double price;

    // JPA托管实体
}

// 配置仓库以使用getId()方法
CollectionRepository<Product> repository = new CollectionRepository<>(products);
repository.setKeyProvider(Product::getId);

// 现在按ID查找有效
Optional<Product> product = repository.find(123L);
```

### 选择方法 {#choosing-approach}

两种方法都可以使用，但是当以下情况时，`setKeyProvider()`更受欢迎：
- 使用具有`@Id`字段的JPA实体
- 您无法修改实体类
- 您需要不同的键策略为不同的仓库

使用`HasEntityKey`时：
- 您控制实体类
- 键提取逻辑复杂
- 您希望实体定义自己的身份


## UI集成 {#ui-integration}

`Repository`与数据感知组件集成：

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
customers.add(new Customer("C001", "爱丽丝·约翰逊", "alice@example.com"));
repository.commit();
```

## 下一步 {#next-steps}

<DocCardList className="topics-section" />
