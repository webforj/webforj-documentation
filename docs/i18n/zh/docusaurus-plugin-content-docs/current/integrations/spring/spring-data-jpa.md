---
title: Spring Data JPA
sidebar_position: 20
_i18n_hash: 3fe8c744a49adaaa35e1e30c53b5c60f
---
Spring Data JPA 是 Spring 应用程序中数据访问的事实标准，提供了仓库抽象、查询方法和复杂查询的规范。webforJ 的 `SpringDataRepository` 适配器将 Spring Data 仓库与 webforJ 的 UI 组件连接起来，使您能够将 JPA 实体直接绑定到 UI 组件，实现使用 JPA 规范的动态过滤，并处理分页。

该适配器检测您的仓库实现了哪些 Spring Data 接口——无论是 `CrudRepository`、`PagingAndSortingRepository` 还是 `JpaSpecificationExecutor`——并自动在您的 UI 中提供相应的功能。这意味着您现有的 Spring Data 仓库可以与 webforJ 组件一起使用而无需修改，同时保持类型安全并使用您现有的领域模型。

:::tip[了解更多关于 Spring Data JPA 的信息]
有关 Spring Data JPA 特性和查询方法的全面理解，请参见 [Spring Data JPA 文档](https://docs.spring.io/spring-data/jpa/reference/)。
:::

## 使用 SpringDataRepository {#using-springdatarepository}

`SpringDataRepository` 类将 Spring Data JPA 仓库与 webforJ 的 Repository 接口连接，使其与 [`Table`](../../components/table/overview) 等 UI 组件兼容，同时保留所有 Spring Data 功能。

```java
// 您的 Spring Data 仓库
@Autowired
private PersonRepository personRepository;

// 用 SpringDataRepository 包装它
SpringDataRepository<Person, Long> adapter = new SpringDataRepository<>(personRepository);

// 与 webforJ Table 一起使用
Table<Person> table = new Table<>();
table.setRepository(adapter);
```

### 接口检测 {#interface-detection}

Spring Data 仓库使用接口继承来添加功能。您可以从基本的 CRUD 操作开始，然后增加用于分页或规范的接口：

```java
// 仅基本 CRUD
public interface CustomerRepository extends CrudRepository<Customer, Long> {}

// CRUD + 分页 + 排序
public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long> {}

// 完整功能仓库
public interface CustomerRepository extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {}
```

`SpringDataRepository` 会检查您的仓库实现了哪些接口并相应地调整其行为。如果您的仓库支持分页，适配器会启用分页查询。如果它实现了 `JpaSpecificationExecutor`，您可以使用规范进行动态过滤。

### 仓库能力 {#repository-capabilities}

每个 Spring Data 接口添加特定的能力，而 `SpringDataRepository` 可以利用这些能力：

- **CrudRepository** - 基本操作：`save`、`delete`、`findById`、`findAll`
- **PagingAndSortingRepository** - 添加分页查询和排序
- **JpaRepository** - 结合 CRUD 和分页/排序以及批量操作
- **JpaSpecificationExecutor** - 使用 JPA 规范进行动态查询

### 创建 Spring Data 仓库 {#creating-a-spring-data-repository}

为了最大程度地与 webforJ 组件兼容，创建同时实现 `JpaRepository` 和 `JpaSpecificationExecutor` 的仓库：

```java title="PersonRepository.java"
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository
  extends JpaRepository<Person, Long>,
      JpaSpecificationExecutor<Person> {
  // 自定义查询方法可以在此处添加
}
```

这种组合提供了：

- 按 ID 查找操作
- 最佳性能的分页
- 排序功能
- Java 持久化 API 规范过滤
- 带有和不带过滤条件的计数操作

## 使用 `Table` {#working-with-table}

以下示例使用扩展了 `JpaRepository` 和 `JpaSpecificationExecutor` 的 `PersonRepository`。这种组合使通过列标题进行排序和使用规范进行动态过滤成为可能。

```java title="TableView.java"
@Route
public class TableView extends Composite<Div> {
  private SpringDataRepository<Person, Long> repository;
  private Table<Person> table = new Table<>();

  public TableView(PersonRepository personRepository) {
    // 为 webforJ 包装 Spring Data 仓库
    repository = new SpringDataRepository<>(personRepository);
    
    // 连接到表格
    table.setRepository(repository);
    
    // 定义列
    table.addColumn("name", Person::getFullName)
          .setPropertyName("firstName"); // 按实际 JPA 属性排序
    table.addColumn("email", Person::getEmail);
    table.addColumn("age", person -> 
          person.getAge() != null ? person.getAge().toString() : "");
    table.addColumn("city", Person::getCity);
    table.addColumn("profession", Person::getProfession);
    
    // 启用排序
    table.getColumns().forEach(column -> column.setSortable(true));
  }
}
```

`setPropertyName()` 方法对于排序来说非常重要——它告诉适配器在排序该列时需要使用哪个 JPA 属性作为 `ORDER BY` 子句。如果没有它，像 `getFullName()` 这样的计算属性将无法排序。

## 使用 JPA 规范进行过滤 {#filtering-with-jpa-specifications}

`SpringDataRepository` 使用 JPA 规范进行动态查询，并将其应用于仓库的 `findBy` 和 `count` 操作。

:::tip[了解更多关于过滤的信息]
要了解使用 webforJ 仓库时过滤如何工作，包括基础过滤器和过滤器组合，请参见 [仓库文档](../../advanced/repository/overview)。
::: 

```java
// 按城市过滤
Specification<Person> cityFilter = (root, query, cb) -> 
  cb.equal(root.get("city"), "New York");
repository.setFilter(cityFilter);

// 多个条件
Specification<Person> complexFilter = (root, query, cb) -> 
  cb.and(
    cb.equal(root.get("profession"), "Engineer"),
    cb.greaterThanOrEqualTo(root.get("age"), 25)
  );
repository.setFilter(complexFilter);

// 清除过滤器
repository.setFilter(null);
```
