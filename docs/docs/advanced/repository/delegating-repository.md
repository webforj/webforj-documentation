---
title: Custom data sources
sidebar_position: 4
---

<!-- vale off -->
# Custom data sources <DocChip chip='since' label='25.02' />
<!-- vale on -->

When your data lives outside your app - in a REST API, database, or external service - you need to create a custom repository implementation. The <JavadocLink type="data" location="com/webforj/data/repository/DelegatingRepository" code="true">DelegatingRepository</JavadocLink> class makes this straightforward by letting you provide functions instead of implementing a full class.

## How `DelegatingRepository` works {#how-delegatingrepository-works}

<JavadocLink type="data" location="com/webforj/data/repository/DelegatingRepository" code="true">DelegatingRepository</JavadocLink> is a concrete class that extends <JavadocLink type="data" location="com/webforj/data/repository/AbstractQueryableRepository" code="true">AbstractQueryableRepository</JavadocLink>. Instead of implementing abstract methods, you provide three functions in the constructor:

```java
DelegatingRepository<User, UserFilter> repository = new DelegatingRepository<>(
    // 1. Find function - returns filtered/sorted/paginated data
    criteria -> userService.findUsers(criteria),
    
    // 2. Count function - returns total count for the filter
    criteria -> userService.countUsers(criteria),
    
    // 3. Find by key function - returns single entity by ID
    userId -> userService.findById(userId)
);
```

Each function serves a specific purpose:

**Find function** receives a `RepositoryCriteria` object containing:
- `getFilter()` - your custom filter object (the `F` type parameter)
- `getOffset()` and `getLimit()` - for pagination
- `getOrderCriteria()` - list of sorting rules

This function must return a `Stream<T>` of entities that match the criteria. The stream can be empty if no matches are found.

**Count function** also receives the criteria but typically only uses the filter portion. It returns the total count of matching entities, ignoring pagination. This is used by UI components to show total results or calculate pages.

**Find by key function** receives an entity key (usually an ID) and returns an `Optional<T>`. Return `Optional.empty()` if the entity doesn't exist.

## REST API example {#rest-api-example}

When integrating with a REST API, you need to convert the repository criteria into HTTP request parameters. Start by defining a filter class that matches your API's query capabilities:

```java
public class UserFilter {
    private String department;
    private String status;
    // getters and setters...
}
```

This filter class represents the search parameters your API accepts. The repository will pass instances of this class to your functions when filtering is applied.

Create the repository with functions that translate criteria to API calls:

```java
DelegatingRepository<User, UserFilter> apiRepository = new DelegatingRepository<>(
    // Find users
    criteria -> {
        Map<String, String> params = buildParams(criteria);
        List<User> users = restClient.get("/users", params);
        return users.stream();
    },
    
    // Count users
    criteria -> {
        Map<String, String> filterParams = buildFilterParams(criteria.getFilter());
        return restClient.getCount("/users/count", filterParams);
    },
    
    // Find by ID
    userId -> restClient.getById("/users/" + userId)
);
```

The `buildParams()` method would extract values from the criteria and convert them to query parameters like `?department=Sales&status=active&offset=20&limit=10`. Your REST client then makes the actual HTTP request and deserializes the response.

## Database example {#database-example}

Database integration follows a similar pattern but converts criteria to SQL queries. The key difference is handling SQL generation and parameter binding:

```java
DelegatingRepository<Customer, CustomerFilter> dbRepository = new DelegatingRepository<>(
    // Query with filter, sort, pagination
    criteria -> {
        String sql = buildQuery(criteria);
        return jdbcTemplate.queryForStream(sql, rowMapper);
    },
    
    // Count matching records
    criteria -> {
        String countSql = buildCountQuery(criteria.getFilter());
        return jdbcTemplate.queryForObject(countSql, Integer.class);
    },
    
    // Find by primary key
    customerId -> {
        String sql = "SELECT * FROM customers WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, rowMapper, customerId);
    }
);
```

The `buildQuery()` method would construct SQL like:
```sql
SELECT * FROM customers 
WHERE status = ? AND region = ?
ORDER BY created_date DESC, name ASC
LIMIT ? OFFSET ?
```

Your filter object properties map to `WHERE` clause conditions, while pagination and sorting are handled through `LIMIT/OFFSET` and `ORDER BY` clauses.

## Using with UI components {#using-with-ui-components}

The beauty of the repository pattern is that UI components don't know or care where the data comes from. Whether it's an in-memory collection, REST API, or database, the usage is identical:

```java
// Create and configure repository
Repository<User> repository = createApiRepository();
UserFilter filter = new UserFilter();
filter.setDepartment("Engineering");
repository.setBaseFilter(filter);

// Attach to table
Table<User> table = new Table<>();
table.setRepository(repository);

// Table automatically displays filtered engineering users
```

When users interact with the [`Table`](../../components/table/overview) (sorting columns, changing pages), the `Table` calls your repository functions with updated criteria. Your functions translate these to API calls or SQL queries, and the table updates automatically with the results.

## When to extend `AbstractQueryableRepository` {#when-to-extend-abstractqueryablerepository}

If you need custom methods or complex initialization, extend <JavadocLink type="data" location="com/webforj/data/repository/AbstractQueryableRepository" code="true">AbstractQueryableRepository</JavadocLink> directly:

```java
public class CustomUserRepository extends AbstractQueryableRepository<User, UserFilter> {
    @Override
    public Stream<User> findBy(RepositoryCriteria<User, UserFilter> criteria) {
        // Implementation
    }
    
    @Override
    public int size(RepositoryCriteria<User, UserFilter> criteria) {
        // Implementation
    }
    
    @Override
    public Optional<User> find(Object key) {
        // Implementation
    }
    
    // Add custom methods
    public List<User> findActiveManagers() {
        // Custom query logic
    }
}
```

