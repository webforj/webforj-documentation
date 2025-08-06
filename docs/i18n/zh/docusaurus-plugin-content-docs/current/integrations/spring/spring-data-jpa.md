---
title: Spring Data JPA
sidebar_position: 20
_i18n_hash: f97a3247bcdeadf193a049bdb6d1a3bc
---
Spring Data JPA 是 Spring 应用程序中数据访问的事实标准，提供了存储库抽象、查询方法和复杂查询的规范。webforJ 的 `SpringDataRepository` 适配器将 Spring Data 存储库与 webforJ 的 UI 组件连接起来，使您能够将 JPA 实体直接绑定到 UI 组件，实现基于 JPA 规范的动态过滤，并处理分页。

适配器检测您的存储库实现了哪些 Spring Data 接口 - 无论是 `CrudRepository`、`PagingAndSortingRepository`，还是 `JpaSpecificationExecutor` - 并自动在您的 UI 中提供相应的功能。这意味着您现有的 Spring Data 存储库可以与 webforJ 组件一起使用，而无需修改，同时保持类型安全并使用您现有的领域模型。

:::tip[了解更多关于 Spring Data JPA]
有关 Spring Data JPA 功能和查询方法的全面了解，请参见 [Spring Data JPA 文档](https://docs.spring.io/spring-data/jpa/reference/)。
:::

## 使用 SpringDataRepository {#using-springdatarepository}

`SpringDataRepository` 类将 Spring Data JPA 存储库与 webforJ 的 Repository 接口连接起来，使其与像 [`Table`](../../components/table/overview) 这样的 UI 组件兼容，同时保留所有 Spring Data 功能。

```java
// 您的 Spring Data 存储库
@Autowired
private PersonRepository personRepository;

// 使用 SpringDataRepository 包装
SpringDataRepository<Person, Long> adapter = new SpringDataRepository<>(personRepository);

// 与 webforJ Table 一起使用
Table<Person> table = new Table<>();
table.setRepository(adapter);
```

### 接口检测 {#interface-detection}

Spring Data 存储库使用接口继承来添加功能。您可以从基本的 CRUD 操作开始，并添加用于分页或规范的接口：

```java
// 仅基本 CRUD
public interface CustomerRepository extends CrudRepository<Customer, Long> {}

// CRUD + 分页 + 排序
public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long> {}

// 完整功能的存储库
public interface CustomerRepository extends JpaRepository<Customer, Long>, 
                                           JpaSpecificationExecutor<Customer> {}
```

`SpringDataRepository` 检查您的存储库实现了哪些接口，并相应地调整其行为。如果您的存储库支持分页，适配器将启用分页查询。如果它实现了 `JpaSpecificationExecutor`，您可以使用规范进行动态过滤。

### 存储库功能 {#repository-capabilities}

每个 Spring Data 接口添加了 `SpringDataRepository` 可以使用的特定功能：

- **CrudRepository** - 基本操作：`save`、`delete`、`findById`、`findAll`
- **PagingAndSortingRepository** - 添加分页查询和排序
- **JpaRepository** - 将 CRUD 和分页/排序与批量操作结合起来
- **JpaSpecificationExecutor** - 使用 JPA 规范进行动态查询

### 创建一个 Spring Data 存储库 {#creating-a-spring-data-repository}

为了最大限度地兼容 webforJ 组件，创建实现 `JpaRepository` 和 `JpaSpecificationExecutor` 的存储库：

```java title="PersonRepository.java"
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository
    extends JpaRepository<Person, Long>,
            JpaSpecificationExecutor<Person> {
    // 自定义查询方法可以在这里定义
}
```

这种组合提供了：

- 通过 ID 查找操作
- 具有最佳性能的分页
- 排序能力
- Java 持久化 API 规范过滤
- 带有和不带过滤器的计数操作

## 与 `Table` 一起工作 {#working-with-table}

以下示例使用一个扩展 `JpaRepository` 和 `JpaSpecificationExecutor` 的 `PersonRepository`。这种组合使得可以通过列标题进行排序，并通过规范进行动态过滤。

```java title="TableView.java"
@Route
public class TableView extends Composite<Div> {
  private SpringDataRepository<Person, Long> repository;
  private Table<Person> table = new Table<>();

  public TableView(PersonRepository personRepository) {
    // 为 webforJ 包装 Spring Data 存储库
    repository = new SpringDataRepository<>(personRepository);
    
    // 连接到表
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

`setPropertyName()` 方法对于排序很重要 - 它告诉适配器在按该列排序时，应使用哪个 JPA 属性在 `ORDER BY` 子句中。如果没有它，像 `getFullName()` 这样的计算属性的排序将无法正常工作。

## 使用 JPA 规范进行过滤 {#filtering-with-jpa-specifications}

`SpringDataRepository` 使用 JPA 规范进行动态查询，并将其应用于存储库的 `findBy` 和 `count` 操作。

:::tip[了解更多关于过滤]
要了解 webforJ 存储库如何进行过滤，包括基本过滤器和过滤器组合，请参见 [存储库文档](../../advanced/repository/overview)。
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
