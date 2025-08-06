---
title: Querying data
sidebar_position: 3
_i18n_hash: c5508e014de2ca1de7543b34e39731bc
---
<!-- vale off -->
# 查询数据 <DocChip chip='since' label='25.02' />
<!-- vale on -->

<JavadocLink type="data" location="com/webforj/data/repository/QueryableRepository" code="true">QueryableRepository</JavadocLink> 接口扩展了 `Repository`，通过 <JavadocLink type="data" location="com/webforj/data/repository/RepositoryCriteria" code="true">RepositoryCriteria</JavadocLink> 实现高级查询。与仅支持简单过滤的基本仓库不同，可查询仓库提供了结构化查询，支持自定义过滤器类型、排序和分页。

## 理解过滤器类型 {#understanding-filter-types}

<JavadocLink type="data" location="com/webforj/data/repository/QueryableRepository" code="true">QueryableRepository</JavadocLink> 介绍了第二个泛型参数用于过滤器类型：`QueryableRepository<T, F>`，其中 `T` 是您的实体类型，而 `F` 是您的自定义过滤器类型。

这种分离存在的原因是，不同的数据源使用不同的查询语言：

```java
// 内存集合的谓词过滤器
QueryableRepository<Product, Predicate<Product>> inMemoryRepo = 
    new CollectionRepository<>(products);

// REST API 或数据库的自定义过滤对象  
QueryableRepository<User, UserFilter> apiRepo = 
    new DelegatingRepository<>(/* 实现 */);

// 搜索引擎的字符串查询
QueryableRepository<Document, String> searchRepo = 
    new DelegatingRepository<>(/* 实现 */);
```

`CollectionRepository` 使用 `Predicate<Product>`，因为它在内存中过滤Java对象。REST API 仓库使用 `UserFilter` - 一个自定义类，包含像 `department` 和 `status` 这样的字段，映射到查询参数。搜索仓库使用普通字符串进行全文查询。

UI 组件不关心这些差异。它们调用 `setBaseFilter()`，使用仓库期望的任何过滤器类型，而仓库则处理翻译。

## 使用仓库标准构建查询 {#building-queries-with-repository-criteria}

<JavadocLink type="data" location="com/webforj/data/repository/RepositoryCriteria" code="true">RepositoryCriteria</JavadocLink> 将所有查询参数捆绑到一个不可变对象中。您将所有内容一次性传递，而不是调用用于过滤、排序和分页的单独方法：

```java
// 包含所有参数的完整查询
RepositoryCriteria<Product, Predicate<Product>> criteria = 
    new RepositoryCriteria<>(  
        20,                                       // 偏移量 - 跳过前20
        10,                                       // 限制 - 取10个项目  
        orderCriteria,                           // 排序规则
        product -> product.getPrice() < 100.0    // 过滤条件
    );

// 执行查询
Stream<Product> results = repository.findBy(criteria);
int totalMatching = repository.size(criteria);
```

`findBy()` 方法执行完整查询 - 应用过滤器、对结果进行排序、跳过偏移量并取限制。`size()` 方法计算所有匹配过滤器的项目，忽略分页。

您还可以仅使用所需的部分创建标准：

```java
// 仅过滤
RepositoryCriteria<Product, Predicate<Product>> filterOnly = 
    new RepositoryCriteria<>(product -> product.isActive());

// 仅分页  
RepositoryCriteria<Product, Predicate<Product>> pageOnly = 
    new RepositoryCriteria<>(0, 25);
```

## 使用不同的过滤器类型 {#working-with-different-filter-types}

### 谓词过滤器 {#predicate-filters}

对于内存集合，使用 `Predicate<T>` 来组合函数过滤器：

```java
CollectionRepository<Product> repository = new CollectionRepository<>(products);

// 构建复杂的谓词
Predicate<Product> activeProducts = product -> product.isActive();
Predicate<Product> inStock = product -> product.getStock() > 0;
Predicate<Product> affordable = product -> product.getPrice() < 50.0;

// 组合条件
repository.setBaseFilter(activeProducts.and(inStock).and(affordable));

// 动态过滤
Predicate<Product> filter = product -> true;
if (categoryFilter != null) {
    filter = filter.and(p -> p.getCategory().equals(categoryFilter));
}
if (maxPrice != null) {
    filter = filter.and(p -> p.getPrice() <= maxPrice);
}
repository.setBaseFilter(filter);
```

### 自定义过滤器对象 {#custom-filter-objects}

外部数据源无法执行Java谓词。相反，您需要创建表示后端可以搜索的内容的过滤器类：

```java
public class ProductFilter {
    private String category;
    private BigDecimal maxPrice;
    private Boolean inStock;
    
    // getters 和 setters...
}

// 与自定义仓库一起使用
ProductFilter filter = new ProductFilter();
filter.setCategory("电子产品");
filter.setMaxPrice(new BigDecimal("99.99"));
filter.setInStock(true);

RepositoryCriteria<Product, ProductFilter> criteria = 
    new RepositoryCriteria<>(filter);

Stream<Product> results = customRepository.findBy(criteria);
```

在您自定义仓库的 `findBy()` 方法中，您将翻译此过滤器对象：
- 对于 REST API：转换为查询参数，如 `?category=电子产品&maxPrice=99.99&inStock=true`
- 对于 SQL：构建一个 where 子句，如 `WHERE category = ? AND price <= ? AND stock > 0`
- 对于 GraphQL：构建一个包含适当字段选择的查询

`Repository` 的实现应处理此翻译，以保持您的 UI 代码清晰。

## 排序数据 {#sorting-data}

<JavadocLink type="data" location="com/webforj/data/repository/OrderCriteria" code="true">OrderCriteria</JavadocLink> 定义如何对数据进行排序。每个 `OrderCriteria` 需要一个值提供者（如何从实体中获取值）和一个方向：

```java
// 单字段排序
OrderCriteria<Employee, String> byName = 
    new OrderCriteria<>(Employee::getName, OrderCriteria.Direction.ASC);

// 多层次排序 - 部门优先，其次是工资，再次是名字
OrderCriteriaList<Employee> sorting = new OrderCriteriaList<>();
sorting.add(new OrderCriteria<>(Employee::getDepartment, OrderCriteria.Direction.ASC));
sorting.add(new OrderCriteria<>(Employee::getSalary, OrderCriteria.Direction.DESC));  
sorting.add(new OrderCriteria<>(Employee::getName, OrderCriteria.Direction.ASC));

// 在标准中使用
RepositoryCriteria<Employee, Predicate<Employee>> criteria = 
    new RepositoryCriteria<>(0, 50, sorting, employee -> employee.isActive());
```

值提供者（`Employee::getName`）适用于内存排序。但外部数据源无法执行 Java 函数。对于这些情况，OrderCriteria 接受属性名称：

```java
// 对于外部仓库 - 提供值获取器和属性名称
OrderCriteria<Employee, String> byName = new OrderCriteria<>(  
    Employee::getName,           // 用于内存排序
    Direction.ASC,
    null,                       // 自定义比较器（可选）
    "name"                      // 后台排序的属性名称
);
```

`CollectionRepository` 使用值提供者来对 Java 对象进行排序。`DelegatingRepository` 实现可以使用属性名称在 SQL 中建立排序子句，或在 REST API 中使用 `sort=name:asc`。

## 控制分页 {#controlling-pagination}

设置偏移量和限制以控制要加载的数据切片：

```java
// 基于页面的分页
int page = 2;          // 从零开始的页面号
int pageSize = 20;     // 每页项目
int offset = page * pageSize;

RepositoryCriteria<Product, Predicate<Product>> criteria = 
    new RepositoryCriteria<>(offset, pageSize, null, yourFilter);

// 渐进加载 - 逐步加载更多数据  
int currentlyLoaded = 50;
int loadMore = 25;

repository.setOffset(0);
repository.setLimit(currentlyLoaded + loadMore);
```
