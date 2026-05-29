---
title: Querying data
sidebar_position: 3
_i18n_hash: 745f13099f9adff4b07124e4c8230600
---
<!-- vale off -->
# 查询数据 <DocChip chip='since' label='25.02' />
<!-- vale on -->

<JavadocLink type="data" location="com/webforj/data/repository/QueryableRepository" code="true">QueryableRepository</JavadocLink> 接口扩展了 `Repository`，通过 <JavadocLink type="data" location="com/webforj/data/repository/RepositoryCriteria" code="true">RepositoryCriteria</JavadocLink> 提供高级查询。与仅支持简单过滤的基本存储库不同，可查询存储库提供结构化查询，支持自定义过滤类型、排序和分页。

## 理解过滤器类型 {#understanding-filter-types}

<JavadocLink type="data" location="com/webforj/data/repository/QueryableRepository" code="true">QueryableRepository</JavadocLink> 引入了第二个泛型参数作为过滤器类型：`QueryableRepository<T, F>`，其中 `T` 是你的实体类型，`F` 是你的自定义过滤器类型。

这种分离的原因在于不同的数据源使用不同的查询语言：

```java
// 针对内存集合的谓词过滤器
QueryableRepository<Product, Predicate<Product>> inMemoryRepo = 
  new CollectionRepository<>(products);

// 针对 REST API 或数据库的自定义过滤对象  
QueryableRepository<User, UserFilter> apiRepo = 
  new DelegatingRepository<>(/* implementation */);

// 针对搜索引擎的字符串查询
QueryableRepository<Document, String> searchRepo = 
  new DelegatingRepository<>(/* implementation */);
```

`CollectionRepository` 使用 `Predicate<Product>`，因为它过滤的是内存中的 Java 对象。REST API 存储库使用 `UserFilter`，这是一个具有 `department` 和 `status` 等字段的自定义类，映射到查询参数。搜索存储库使用普通字符串进行全文查询。

UI 组件不在乎这些差异。它们调用 `setBaseFilter()` 并传入存储库所期望的任何过滤器类型，存储库处理转换。

## 使用存储库标准构建查询 {#building-queries-with-repository-criteria}

<JavadocLink type="data" location="com/webforj/data/repository/RepositoryCriteria" code="true">RepositoryCriteria</JavadocLink> 将所有查询参数捆绑到一个不可变对象中。您无需为过滤、排序和分页调用单独的方法，而是一次性传递所有内容：

```java
// 包含所有参数的完整查询
RepositoryCriteria<Product, Predicate<Product>> criteria = 
  new RepositoryCriteria<>(
    20,                                       // 偏移 - 跳过前 20 个
    10,                                       // 限制 - 获取 10 个项目  
    orderCriteria,                           // 排序规则
    product -> product.getPrice() < 100.0    // 过滤条件
  );

// 执行查询
Stream<Product> results = repository.findBy(criteria);
int totalMatching = repository.size(criteria);
```

`findBy()` 方法执行完整查询 - 它应用过滤器、对结果进行排序、跳过偏移并限制条目数。`size()` 方法计算所有匹配过滤器的项目，忽略分页。

您还可以仅使用所需的部分来创建标准：

```java
// 仅过滤
RepositoryCriteria<Product, Predicate<Product>> filterOnly = 
  new RepositoryCriteria<>(product -> product.isActive());

// 仅分页  
RepositoryCriteria<Product, Predicate<Product>> pageOnly = 
  new RepositoryCriteria<>(0, 25);
```

## 使用不同过滤器类型 {#working-with-different-filter-types}

### 谓词过滤器 {#predicate-filters}

对于内存集合，使用 `Predicate<T>` 来组合功能性过滤器：

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

### 自定义过滤对象 {#custom-filter-objects}

外部数据源无法执行 Java 谓词。相反，您创建代表后端可以搜索的内容的过滤器类：

```java
public class ProductFilter {
  private String category;
  private BigDecimal maxPrice;
  private Boolean inStock;
  
  // getter 和 setter...
}

// 与自定义存储库一起使用
ProductFilter filter = new ProductFilter();
filter.setCategory("电子产品");
filter.setMaxPrice(new BigDecimal("99.99"));
filter.setInStock(true);

RepositoryCriteria<Product, ProductFilter> criteria = 
  new RepositoryCriteria<>(filter);

Stream<Product> results = customRepository.findBy(criteria);
```

在您的自定义存储库的 `findBy()` 方法中，您需要转换该过滤器对象：
- 对于 REST API：转换为查询参数，例如 `?category=Electronics&maxPrice=99.99&inStock=true`
- 对于 SQL：构建一个 WHERE 子句，例如 `WHERE category = ? AND price <= ? AND stock > 0`
- 对于 GraphQL：构建一个具有适当字段选择的查询

`Repository` 实现应处理此转换，从而保持您的 UI 代码干净。

## 排序数据 {#sorting-data}

<JavadocLink type="data" location="com/webforj/data/repository/OrderCriteria" code="true">OrderCriteria</JavadocLink> 定义了如何对数据进行排序。每个 `OrderCriteria` 需要一个值提供者（如何从实体中获取值）和一个方向：

```java
// 单字段排序
OrderCriteria<Employee, String> byName = 
  new OrderCriteria<>(Employee::getName, OrderCriteria.Direction.ASC);

// 多级排序 - 部门优先，然后是薪资，然后是姓名
OrderCriteriaList<Employee> sorting = new OrderCriteriaList<>();
sorting.add(new OrderCriteria<>(Employee::getDepartment, OrderCriteria.Direction.ASC));
sorting.add(new OrderCriteria<>(Employee::getSalary, OrderCriteria.Direction.DESC));  
sorting.add(new OrderCriteria<>(Employee::getName, OrderCriteria.Direction.ASC));

// 在标准中使用
RepositoryCriteria<Employee, Predicate<Employee>> criteria = 
  new RepositoryCriteria<>(0, 50, sorting, employee -> employee.isActive());
```

值提供者（`Employee::getName`）适用于内存排序。但外部数据源无法执行 Java 函数。在这些情况下，OrderCriteria 接受一个属性名称：

```java
// 针对外部存储库 - 同时提供值获取器和属性名称
OrderCriteria<Employee, String> byName = new OrderCriteria<>(
  Employee::getName,           // 用于内存排序
  Direction.ASC,
  null,                       // 自定义比较器（可选）
  "name"                      // 后端排序的属性名称
);
```

`CollectionRepository` 使用值提供者对 Java 对象进行排序。`DelegatingRepository` 的实现可以使用属性名称在 SQL 中构建顺序子句或在 REST API 中使用 `sort=name:asc`。

## 控制分页 {#controlling-pagination}

设置偏移和限制以控制加载的数据切片：

```java
// 基于页面的分页
int page = 2;          // 从零开始的页码
int pageSize = 20;     // 每页项目
int offset = page * pageSize;

RepositoryCriteria<Product, Predicate<Product>> criteria = 
  new RepositoryCriteria<>(offset, pageSize, null, yourFilter);

// 渐进加载 - 增量加载更多数据  
int currentlyLoaded = 50;
int loadMore = 25;

repository.setOffset(0);
repository.setLimit(currentlyLoaded + loadMore);
```
