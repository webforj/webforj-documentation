---
title: Spring Data JPA
sidebar_position: 20
---

import JavadocLink from '@site/src/components/DocsTools/JavadocLink';

Spring Data JPA is the de facto standard for data access in Spring applications, providing repository abstractions, query methods, and specifications for complex queries. The webforJ `SpringDataRepository` adapter bridges Spring Data repositories with webforJ's UI components, enabling you to bind JPA entities directly to UI components, implement dynamic filtering with JPA Specifications, and handle pagination.

The adapter detects which Spring Data interfaces your repository implements - whether it's `CrudRepository`, `PagingAndSortingRepository`, or `JpaSpecificationExecutor` - and automatically provides the corresponding features in your UI. This means your existing Spring Data repositories work with webforJ components without modification, while maintaining type safety and using your existing domain model.

:::tip[Learn more about Spring Data JPA]
For a comprehensive understanding of Spring Data JPA features and query methods, see [Spring Data JPA Reference Documentation](https://docs.spring.io/spring-data/jpa/reference/).
:::

## Using SpringDataRepository

The `SpringDataRepository` class bridges Spring Data JPA repositories with webforJ's Repository interface, making them compatible with UI components like [`Table`](../../components/table/overview) while retaining all Spring Data features.

```java
// Your Spring Data repository
@Autowired
private PersonRepository personRepository;

// Wrap it with SpringDataRepository
SpringDataRepository<Person, Long> adapter = new SpringDataRepository<>(personRepository);

// Use with webforJ Table
Table<Person> table = new Table<>();
table.setRepository(adapter);
```

### Interface detection

Spring Data repositories use interface inheritance to add capabilities. You start with basic CRUD operations and add interfaces for features like pagination or specifications:

```java
// Basic CRUD only
public interface CustomerRepository extends CrudRepository<Customer, Long> {}

// CRUD + Pagination + Sorting
public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long> {}

// Full featured repository
public interface CustomerRepository extends JpaRepository<Customer, Long>, 
                                           JpaSpecificationExecutor<Customer> {}
```

`SpringDataRepository` examines which interfaces your repository implements and adapts its behavior accordingly. If your repository supports pagination, the adapter enables paginated queries. If it implements `JpaSpecificationExecutor`, you can use dynamic filtering with specifications.

### Repository capabilities

Each Spring Data interface adds specific capabilities that `SpringDataRepository` can use:

- **CrudRepository** - Basic operations: save, delete, findById, findAll
- **PagingAndSortingRepository** - Adds paginated queries and sorting
- **JpaRepository** - Combines CRUD and paging/sorting with batch operations
- **JpaSpecificationExecutor** - Dynamic queries using JPA Specifications

### Creating a Spring Data repository

For maximum compatibility with webforJ components, create repositories that implement both `JpaRepository` and `JpaSpecificationExecutor`:

```java title="PersonRepository.java"
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository
    extends JpaRepository<Person, Long>,
            JpaSpecificationExecutor<Person> {
    // Custom query methods can go here
}
```

This combination provides:

- Find by ID operations
- Pagination with optimal performance
- Sorting capabilities
- Java Persistence API Specification filtering
- Count operations with and without filters

## Working with `Table`

The following example uses a `PersonRepository` that extends `JpaRepository` and `JpaSpecificationExecutor`. This combination enables sorting through column headers and dynamic filtering with specifications.

```java title="TableView.java"
@Route
public class TableView extends Composite<Div> {
  private SpringDataRepository<Person, Long> repository;
  private Table<Person> table = new Table<>();

  public TableView(PersonRepository personRepository) {
    // Wrap Spring Data repository for webforJ
    repository = new SpringDataRepository<>(personRepository);
    
    // Connect to table
    table.setRepository(repository);
    
    // Define columns
    table.addColumn("name", Person::getFullName)
          .setPropertyName("firstName"); // Sort by actual JPA property
    table.addColumn("email", Person::getEmail);
    table.addColumn("age", person -> 
          person.getAge() != null ? person.getAge().toString() : "");
    table.addColumn("city", Person::getCity);
    table.addColumn("profession", Person::getProfession);
    
    // Enable sorting
    table.getColumns().forEach(column -> column.setSortable(true));
  }
}
```

The `setPropertyName()` method is important for sorting - it tells the adapter which JPA property to use in the `ORDER BY` clause when sorting by that column. Without it, sorting won't work for computed properties like `getFullName()`.

## Filtering with JPA specifications  

`SpringDataRepository` uses JPA Specifications for dynamic queries an they applied to the repository `findBy`, `count` operations.

:::tip[Learn more about filtering]
To understand how filtering works with webforJ repositories, including base filters and filter composition, see the [Repository documentation](../../advanced/repository/overview).
::: 

```java
// Filter by city
Specification<Person> cityFilter = (root, query, cb) -> 
    cb.equal(root.get("city"), "New York");
repository.setFilter(cityFilter);

// Multiple conditions
Specification<Person> complexFilter = (root, query, cb) -> 
    cb.and(
        cb.equal(root.get("profession"), "Engineer"),
        cb.greaterThanOrEqualTo(root.get("age"), 25)
    );
repository.setFilter(complexFilter);

// Clear filter
repository.setFilter(null);
```
