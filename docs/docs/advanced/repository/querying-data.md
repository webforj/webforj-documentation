---
title: Querying data
sidebar_position: 3
---

<!-- vale off -->
# Querying data <DocChip chip='since' label='25.02' />
<!-- vale on -->

The <JavadocLink type="data" location="com/webforj/data/repository/QueryableRepository" code="true">QueryableRepository</JavadocLink> interface extends `Repository` with advanced querying through <JavadocLink type="data" location="com/webforj/data/repository/RepositoryCriteria" code="true">RepositoryCriteria</JavadocLink>. Unlike basic repositories that only support simple filtering, queryable repositories provide structured querying with custom filter types, sorting, and pagination.

## Understanding filter types {#understanding-filter-types}

<JavadocLink type="data" location="com/webforj/data/repository/QueryableRepository" code="true">QueryableRepository</JavadocLink> introduces a second generic parameter for the filter type: `QueryableRepository<T, F>` where `T` is your entity type and `F` is your custom filter type. 

This separation exists because different data sources speak different query languages:

```java
// Predicate filters for in-memory collections
QueryableRepository<Product, Predicate<Product>> inMemoryRepo = 
    new CollectionRepository<>(products);

// Custom filter objects for REST APIs or databases  
QueryableRepository<User, UserFilter> apiRepo = 
    new DelegatingRepository<>(/* implementation */);

// String queries for search engines
QueryableRepository<Document, String> searchRepo = 
    new DelegatingRepository<>(/* implementation */);
```

`CollectionRepository` uses `Predicate<Product>` because it filters Java objects in memory. The REST API repository uses `UserFilter` - a custom class with fields like `department` and `status` that map to query parameters. The search repository uses plain strings for full-text queries.

UI components don't care about these differences. They call `setBaseFilter()` with whatever filter type the repository expects, and the repository handles the translation.

## Building queries with repository criteria {#building-queries-with-repository-criteria}

<JavadocLink type="data" location="com/webforj/data/repository/RepositoryCriteria" code="true">RepositoryCriteria</JavadocLink> bundles all query parameters into one immutable object. Instead of calling separate methods for filter, sort, and pagination, you pass everything at once:

```java
// Complete query with all parameters
RepositoryCriteria<Product, Predicate<Product>> criteria = 
    new RepositoryCriteria<>(
        20,                                       // offset - skip first 20
        10,                                       // limit - take 10 items  
        orderCriteria,                           // sorting rules
        product -> product.getPrice() < 100.0    // filter condition
    );

// Execute the query
Stream<Product> results = repository.findBy(criteria);
int totalMatching = repository.size(criteria);
```

The `findBy()` method executes the full query - it applies the filter, sorts the results, skips the offset, and takes the limit. The `size()` method counts all items matching the filter, ignoring pagination.

You can also create criteria with just the parts you need:

```java
// Filter only
RepositoryCriteria<Product, Predicate<Product>> filterOnly = 
    new RepositoryCriteria<>(product -> product.isActive());

// Pagination only  
RepositoryCriteria<Product, Predicate<Product>> pageOnly = 
    new RepositoryCriteria<>(0, 25);
```

## Working with different filter types {#working-with-different-filter-types}

### Predicate filters {#predicate-filters}

For in-memory collections, use `Predicate<T>` to compose functional filters:

```java
CollectionRepository<Product> repository = new CollectionRepository<>(products);

// Build complex predicates
Predicate<Product> activeProducts = product -> product.isActive();
Predicate<Product> inStock = product -> product.getStock() > 0;
Predicate<Product> affordable = product -> product.getPrice() < 50.0;

// Combine conditions
repository.setBaseFilter(activeProducts.and(inStock).and(affordable));

// Dynamic filtering
Predicate<Product> filter = product -> true;
if (categoryFilter != null) {
    filter = filter.and(p -> p.getCategory().equals(categoryFilter));
}
if (maxPrice != null) {
    filter = filter.and(p -> p.getPrice() <= maxPrice);
}
repository.setBaseFilter(filter);
```


### Custom filter objects {#custom-filter-objects}

External data sources can't execute Java predicates. Instead, you create filter classes that represent what your backend can search:

```java
public class ProductFilter {
    private String category;
    private BigDecimal maxPrice;
    private Boolean inStock;
    
    // getters and setters...
}

// Use with custom repository
ProductFilter filter = new ProductFilter();
filter.setCategory("Electronics");
filter.setMaxPrice(new BigDecimal("99.99"));
filter.setInStock(true);

RepositoryCriteria<Product, ProductFilter> criteria = 
    new RepositoryCriteria<>(filter);

Stream<Product> results = customRepository.findBy(criteria);
```

Inside your custom repository's `findBy()` method, you'd translate this filter object:
- For REST APIs: Convert to query parameters like `?category=Electronics&maxPrice=99.99&inStock=true`
- For SQL: Build a where clause like `WHERE category = ? AND price <= ? AND stock > 0`
- For GraphQL: Construct a query with the appropriate field selections

The `Repository` implementation should handle this translation, keeping your UI code clean.

## Sorting data {#sorting-data}

<JavadocLink type="data" location="com/webforj/data/repository/OrderCriteria" code="true">OrderCriteria</JavadocLink> defines how to sort your data. Each `OrderCriteria` needs a value provider (how to get the value from your entity) and a direction:

```java
// Single field sorting
OrderCriteria<Employee, String> byName = 
    new OrderCriteria<>(Employee::getName, OrderCriteria.Direction.ASC);

// Multi-level sorting - department first, then salary, then name
OrderCriteriaList<Employee> sorting = new OrderCriteriaList<>();
sorting.add(new OrderCriteria<>(Employee::getDepartment, OrderCriteria.Direction.ASC));
sorting.add(new OrderCriteria<>(Employee::getSalary, OrderCriteria.Direction.DESC));  
sorting.add(new OrderCriteria<>(Employee::getName, OrderCriteria.Direction.ASC));

// Use in criteria
RepositoryCriteria<Employee, Predicate<Employee>> criteria = 
    new RepositoryCriteria<>(0, 50, sorting, employee -> employee.isActive());
```

The value provider (`Employee::getName`) works for in-memory sorting. But external data sources can't execute Java functions. For those cases, OrderCriteria accepts a property name:

```java
// For external repositories - provide both value getter and property name
OrderCriteria<Employee, String> byName = new OrderCriteria<>(
    Employee::getName,           // For in-memory sorting
    Direction.ASC,
    null,                       // Custom comparator (optional)
    "name"                      // Property name for backend sorting
);
```

`CollectionRepository` uses the value provider to sort Java objects. `DelegatingRepository` implementations can use the property name to build order clauses in SQL or `sort=name:asc` in REST APIs.

## Controlling pagination {#controlling-pagination}

Set offset and limit to control which slice of data to load:

```java
// Page-based pagination
int page = 2;          // zero-based page number
int pageSize = 20;     // items per page
int offset = page * pageSize;

RepositoryCriteria<Product, Predicate<Product>> criteria = 
    new RepositoryCriteria<>(offset, pageSize, null, yourFilter);

// Progressive loading - load more data incrementally  
int currentlyLoaded = 50;
int loadMore = 25;

repository.setOffset(0);
repository.setLimit(currentlyLoaded + loadMore);
```