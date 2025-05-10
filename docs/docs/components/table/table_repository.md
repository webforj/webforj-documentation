---
sidebar_position: 7
title: Data Repository
slug: Repository
---

<JavadocLink type="data" location="com/webforj/data/repository/Repository"  code="true">Repository</JavadocLink>

The <JavadocLink type="data" location="com/webforj/data/repository/Repository"  code="true">Repository</JavadocLink> interface in webforJ provides a standardized contract for working with collections of entities in a `Table`. It abstracts data access and allows the `Table` to efficiently interact with Java `Collection` objects and supports filtering, lookup, counting, and refresh operations.

## Overview 

A `Repository` is a data provider that wraps around a Java `Collection`. It allows the `Table` component to work with data in a uniform way, regardless of the underlying source (e.g., in-memory lists, external databases). It also supports integration with webforJ's <JavadocLink type="data" location="com/webforj/data/HasEntityKey" code="true">HasEntityKey</JavadocLink> interface to uniquely identify each entity.

```java
Table<Customer> table = new Table<>();
Repository<Customer> repo = Service.getCurrent().getCustomers(); // Returns an array of Customer objects
table.setRepository(repo); // Binds the table to that collection of objects
```

:::tip Setting a Table's `Repository`
When creating a `Table`, you can either set its associated `Repository` with the `setRepository()` method, or pass a `Collection` in the constructor, which will create a `Repository` bound to this `Collection` for you.
:::

## Core responsibilities

The `Repository` interface enables the following actions to be utilized in a webforJ app:

### Entity lookup

Entities can be retrieved by key, index, or custom criteria called <JavadocLink type="data" location="com/webforj/data/repository/RetrievalCriteria" code="true">RetrievalCriteria</JavadocLink> via various find methods. 

```java
repo.find(id); // Assuming a correct id is stored in id variable
repo.findByIndex(0);
repo.findBy(criteria); // Passing a RetrievalCriteria object
```

:::tip
The `getKey(entity)` method can help find an item by its ID, and returns the unique identifier of an entity if the entity implements `HasEntityKey`.
:::

### Collection navigation

The following methods provide direct access to the contents of the repository:

- <JavadocLink type="data" location="com/webforj/data/repository/Repository" code="true" suffix='#has(T)'>has(T entity)</JavadocLink> confirms whether or not an entity is present within the `Repository`, and returns `true` if the entity is found, and `false` if not.
- <JavadocLink type="data" location="com/webforj/data/repository/Repository" code="true" suffix='#size()'>size()</JavadocLink> returns the number of entities contained within the `Repository`.

### Refreshing data

Repositories can notify the `Table` that data has changed using `commit()`, which serves two main purposes:

1) **Updating an existing entity in the Repository**

2) **Adding a new entity to the Repository if it doesn't already exist**

There are two versions of the `commit()` method: one without parameters, and one that accepts the new or modified entity as a parameter.

#### `commit(T entity)`: Add or update an entry

When you call `commit(entity)`, webforJ checks whether the entity already exists in the `Repository`. If the entity is:

- **Found**: The `Repository` updates its internal data store with the new version of the entity and refreshes the corresponding row in the `Table`.

- **Not Found**: The entity is added to the `Repository`, and the `Table` renders a new row for it.

```java
Customer customer = new Customer("Alice", "Smith");
repository.commit(customer); // Adds a new row to the table
//...
customer.setFirstName("Hanna");
repository.commit(customer); // Updates the existing row in the table
```

:::important Using `commit()` efficiently
This method targets only the affected row, making it more efficient for updates compared to full refreshes.
:::

#### `commit()`: Refresh all entities

Calling `commit()` with no parameters triggers a **complete `Repository` refresh**. This is useful when a broad change has occurred - such as a batch update, external data reload, or initial population of the dataset.

```java
repository.commit(); // Re-evaluates and re-renders all rows
```

:::caution Committing efficiently
Use this only when necessary. It's less performant than `commit(entity)` since it may re-process and re-render the entire collection.
:::

#### Best practices for refreshing data

- Prefer `commit(entity)` when only a single item has changed.

- Use `commit()` sparingly—for example, after bulk operations or external data loads.

- Always ensure that your model class implements `HasEntityKey` to enable accurate updates via `commit()`.

### Event handling

Commit events can be listened to using `addCommitListener()` or `onCommit()`, its alias, and enables you to react to data updates and synchronize other parts of your app accordingly.

 This listener is triggered any time the repository is updated - whether by adding a new item, modifying an existing one, or refreshing the entire collection.

<!-- vale off -->
```java
repository.onCommit(event -> {
  System.out.println("Repository updated!");
});
```
<!-- vale on -->

The event handler receives a `RepositoryCommitEvent<T>` object, which provides access to the following methods:

- `getRepository()` - Returns the source repository
- `getCommits()` - Returns a list of the commits
- `getFirstCommit()` - Returns the first commit in the list
- `isSingleCommit()` - Returns a flag to indicate if the event is for a single commit.

## Interaction with `Table`

When bound to a `Table`, a `Repository` enables control over:

- Row rendering
- Sorting (single/multi)
- Filtering and pagination
- Real-time data refreshes

<!-- vale off -->
Repositories **do not provide write/update/delete methods by design**. Their role is to expose structured, queryable, read-only data to the UI layer.
<!-- vale on -->

## Interaction with the underlying data source

It’s important to understand that the `Repository` itself is a read/query abstraction layer. It doesn't own or persist the data it provides. Instead, it acts as a wrapper around a backing data source, such as a `List`, a `Map`, or even a database.

All mutations to the data (e.g., adding, modifying, or removing entities) should be performed on the underlying data structure, not directly on the `Repository`.

For example:
```java
List<Customer> customers = new ArrayList<>();
Repository<Customer> repo = new CollectionRepository<>(customers);

// Add a new customer to the backing list
Customer customer = new Customer("Alice", "Smith");
customers.add(customer);

// Notify the repository to trigger a UI refresh
repo.commit(customer);
```

Failing to update the data source before committing may result in stale or incorrect data appearing in the table.