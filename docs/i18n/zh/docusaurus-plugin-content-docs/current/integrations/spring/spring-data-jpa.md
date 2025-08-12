---
title: Spring Data JPA
sidebar_position: 20
_i18n_hash: 5aee4b031f1464780e7fd06e71946951
---
Spring Data JPA 是 Spring 应用程序中数据访问的事实标准，提供了存储库抽象、查询方法和复杂查询的规范。webforJ 的 `SpringDataRepository` 适配器将 Spring Data 存储库与 webforJ 的 UI 组件连接起来，使您能够将 JPA 实体直接绑定到 UI 组件，实现动态过滤与 JPA 规范，并处理分页。

适配器检测您的存储库实现了哪些 Spring Data 接口——无论是 `CrudRepository`、`PagingAndSortingRepository` 还是 `JpaSpecificationExecutor`——并自动在您的 UI 中提供相应的功能。这意味着您现有的 Spring Data 存储库可以在不修改的情况下与 webforJ 组件一起使用，同时保持类型安全，并使用您现有的域模型。

:::tip[了解更多关于 Spring Data JPA]
要全面理解 Spring Data JPA 的功能和查询方法，请参阅 [Spring Data JPA 文档](https://docs.spring.io/spring-data/jpa/reference/)。
:::

## 使用 SpringDataRepository {#using-springdatarepository}

`SpringDataRepository` 类将 Spring Data JPA 存储库与 webforJ 的 Repository 接口连接起来，使它们与 [`Table`](../../components/table/overview) 等 UI 组件兼容，同时保留所有 Spring Data 特性。

```java
// 您的 Spring Data 存储库
@Autowired
private PersonRepository personRepository;

// 用 SpringDataRepository 封装它
SpringDataRepository<Person, Long> adapter = new SpringDataRepository<>(personRepository);

// 与 webforJ 表格一起使用
Table<Person> table = new Table<>();
table.setRepository(adapter);
```

### 接口检测 {#interface-detection}

Spring Data 存储库使用接口继承来添加功能。您可以从基本 CRUD 操作开始，并添加用于分页或规范的接口：

```java
// 仅基本 CRUD
public interface CustomerRepository extends CrudRepository<Customer, Long> {}

// CRUD + 分页 + 排序
public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long> {}

// 完整功能存储库
public interface CustomerRepository extends JpaRepository<Customer, Long>, 
                                           JpaSpecificationExecutor<Customer> {}
```

`SpringDataRepository` 检查您的存储库实现了哪些接口，并相应地调整其行为。如果您的存储库支持分页，适配器将启用分页查询。如果它实现了 `JpaSpecificationExecutor`，您可以使用包含规范的动态过滤。

### 存储库功能 {#repository-capabilities}

每个 Spring Data 接口添加特定功能，供 `SpringDataRepository` 使用：

- **CrudRepository** - 基本操作：`save`、`delete`、`findById`、`findAll`
- **PagingAndSortingRepository** - 添加分页查询和排序
- **JpaRepository** - 结合 CRUD 和分页/排序及批量操作
- **JpaSpecificationExecutor** - 使用 JPA 规范的动态查询

### 创建 Spring Data 存储库 {#creating-a-spring-data-repository}

为了最大限度地与 webforJ 组件兼容，创建同时实现 `JpaRepository` 和 `JpaSpecificationExecutor` 的存储库：

```java title="PersonRepository.java"
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository
    extends JpaRepository<Person, Long>,
            JpaSpecificationExecutor<Person> {
    // 自定义查询方法可以放在这里
}
```

这种组合提供：

- 按 ID 查询操作
- 高效的分页
- 排序功能
- Java 持久化 API 规范过滤
- 含过滤器和不含过滤器的计数操作

## 与 `Table` 一起工作 {#working-with-table}

以下示例使用扩展 `JpaRepository` 和 `JpaSpecificationExecutor` 的 `PersonRepository`。这种组合使得通过列头进行排序和使用规范进行动态过滤成为可能。

```java title="TableView.java"
@Route
public class TableView extends Composite<Div> {
  private SpringDataRepository<Person, Long> repository;
  private Table<Person> table = new Table<>();

  public TableView(PersonRepository personRepository) {
    // 为 webforJ 封装 Spring Data 存储库
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

`setPropertyName()` 方法对排序非常重要——它告诉适配器在按该列排序时使用 `ORDER BY` 子句中的哪个 JPA 属性。如果没有它，计算属性如 `getFullName()` 的排序将无法工作。

## 使用 JPA 规范进行过滤 {#filtering-with-jpa-specifications}

`SpringDataRepository` 使用 JPA 规范进行动态查询，并应用于存储库的 `findBy` 和 `count` 操作。

:::tip[了解更多关于过滤]
要了解如何使用 webforJ 存储库进行过滤，包括基本过滤器和过滤器组合，请参阅 [存储库文档](../../advanced/repository/overview)。
::: 

```java
// 按城市过滤
Specification<Person> cityFilter = (root, query, cb) -> 
    cb.equal(root.get("city"), "New York");
repository.setFilter(cityFilter);

// 多条件
Specification<Person> complexFilter = (root, query, cb) -> 
    cb.and(
        cb.equal(root.get("profession"), "Engineer"),
        cb.greaterThanOrEqualTo(root.get("age"), 25)
    );
repository.setFilter(complexFilter);

// 清除过滤器
repository.setFilter(null);
```
