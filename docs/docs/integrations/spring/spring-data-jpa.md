---
title: Spring Data JPA
sidebar_position: 20
---

import JavadocLink from '@site/src/components/DocsTools/JavadocLink';

This article explains how to use Spring Data JPA repositories with webforJ components through the `SpringDataRepository` adapter. This integration allows you to connect your Spring Data repositories directly to webforJ tables and other data-aware components while maintaining the full power of Java Persistence API specifications for filtering and querying.

## Understanding SpringDataRepository

The `SpringDataRepository` class bridges webforJ's repository interface with Spring Data repositories. It wraps your existing Spring Data repository and automatically detects which interfaces it implements to provide corresponding features.

### Supported interfaces

Your Spring Data repository can implement any combination of:

- **CrudRepository** - Enables basic Create, Read, Update, Delete operations like finding by ID
- **PagingAndSortingRepository** - Adds pagination and sorting capabilities
- **JpaSpecificationExecutor** - Enables Java Persistence API Specification queries with filtering

### Recommended repository setup

For all features with optimal performance, extend `JpaRepository` (which includes both CrudRepository and PagingAndSortingRepository) and add `JpaSpecificationExecutor`:

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

## Dependencies

Add Spring Data JPA to your project. This starter includes Hibernate, Spring Data, and all required transaction management:

```xml title="pom.xml"
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
</dependency>
```

For production, replace H2 with your database:

```xml title="pom.xml"
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <scope>runtime</scope>
</dependency>
```

## Entity setup

SpringDataRepository works with standard JPA entities without requiring any webforJ-specific interfaces or annotations. Your existing JPA entities integrate with webforJ components through the repository pattern.

Spring Data JPA handles entity identification through the `@Id` field, while SpringDataRepository passes entities as-is to webforJ components:

:::tip
The validation annotations provide both database constraints and UI validation when used with webforJ's data binding. See [Jakarta Validation](../../data-binding/validation/jakarta-validation) for configuring automatic UI validation with these annotations.
:::

```java title="Person.java"
@Entity
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "First name is required")
    @Column(name = "first_name", nullable = false)
    private String firstName;
    
    @NotBlank(message = "Last name is required")
    @Column(name = "last_name", nullable = false)
    private String lastName;
    
    @Email(message = "Email should be valid")
    @Column(name = "email", unique = true)
    private String email;
    
    @Min(value = 0, message = "Age should not be less than 0")
    @Max(value = 150, message = "Age should not be greater than 150")
    private Integer age;
    
    private String city;
    private String profession;
    
    // Computed properties work with table columns
    public String getFullName() {
        return firstName + " " + lastName;
    }
    
    // Standard constructors, getters and setters
}
```

## Table integration

SpringDataRepository wraps your Spring Data repository to work with webforJ components:

:::tip
For advanced table configuration options like virtualization, row selection, and custom renderers, see the [Table component](../../components/table/overview) documentation.
:::

```java title="TableView.java"
@Route
public class TableView extends Composite<Div> {
  private SpringDataRepository<Person, Long> repository;
  private Table<Person> table = new Table<>();

  public TableView(PersonRepository personRepository) {
    // Wrap Spring Data repository for webforJ
    repository = new SpringDataRepository<>(personRepository);
    
    // Connect to table - handles all data synchronization
    table.setRepository(repository);
    
    // Define columns
    table.addColumn("name", Person::getFullName)
          .setPropertyName("firstName"); // Sort by actual JPA property
    table.addColumn("email", Person::getEmail);
    table.addColumn("age", person -> 
          person.getAge() != null ? person.getAge().toString() : "");
    table.addColumn("city", Person::getCity);
    table.addColumn("profession", Person::getProfession);
    
    // Enable features
    table.setSelectionMode(Table.SelectionMode.MULTIPLE);
    table.getColumns().forEach(column -> column.setSortable(true));
  }
}
```

The repository handles pagination, sorting, and filtering automatically. Changes in the database reflect immediately in the UI through the repository's event system.

## Filtering with JPA specifications  

SpringDataRepository uses JPA Specifications for dynamic queries an they applied to the repository `findBy`, `count` operations.

:::tip
To understand how filtering works with the repository pattern in webforJ, including base filters and filter composition, see [Repository patterns](../../advanced/repository/overview).
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
