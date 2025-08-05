---
title: Repository
sidebar_position: 1
sidebar_class_name: new-content
---

<!-- vale off -->
# Repository <DocChip chip='since' label='24.00' />
<!-- vale on -->


The `Repository` pattern in webforJ provides a standardized way to manage and query collections of entities. It acts as an abstraction layer between your UI components and data, making it easy to work with different data sources while maintaining consistent behavior.

## Why use repository {#why-use-repository}

`Repository` eliminates manual updates while keeping your original data intact:

```java
// Without Repository - manual updates
List<Customer> customers = loadCustomers();
Table<Customer> table = new Table<>();
table.setItems(customers);

// Adding requires full reload
customers.add(newCustomer);
table.setItems(customers); // Must reload everything
```

```java
// With Repository - automatic synchronization
List<Customer> customers = loadCustomers();
CollectionRepository<Customer> repository = new CollectionRepository<>(customers);
Table<Customer> table = new Table<>();
table.setRepository(repository);

// Adding syncs automatically
customers.add(newCustomer);
repository.commit(newCustomer); // Only updates what changed
```


## Collection repository {#collection-repository}

The <JavadocLink type="data" location="com/webforj/data/repository/CollectionRepository" code="true">CollectionRepository</JavadocLink> is the most common implementation, and wraps any Java Collection:

```java
// From ArrayList
List<Customer> customers = new ArrayList<>();
CollectionRepository<Customer> customerRepo = new CollectionRepository<>(customers);

// From HashSet  
Set<String> tags = new HashSet<>();
CollectionRepository<String> tagRepo = new CollectionRepository<>(tags);

// From any Collection
Collection<Employee> employees = getEmployeesFromHR();
CollectionRepository<Employee> employeeRepo = new CollectionRepository<>(employees);
```


## Data synchronization {#data-synchronization}

The `Repository` acts as a bridge between your data and UI components. When data changes, you notify the repository through the `commit()` method:

```java
List<Product> products = new ArrayList<>();
CollectionRepository<Product> repository = new CollectionRepository<>(products);

// Add new product
Product newProduct = new Product("P4", "Gizmo", 79.99, 15);
products.add(newProduct);
repository.commit(); // All connected components update

// Update existing product  
products.get(0).setPrice(89.99);
repository.commit(products.get(0)); // Only updates this specific row

// Remove product
products.remove(2);
repository.commit(); // Refreshes the view
```

The commit method has two signatures:
- `commit()` - Tells the repository to refresh everything. It triggers a `RepositoryCommitEvent` with all current data
- `commit(entity)` - Targets a specific entity. The repository finds this entity by its key and updates only the affected UI elements

:::important Commiting single entities
This distinction matters for performance. When you update one field in a 1000-row table, `commit(entity)` updates just that cell while `commit()` would refresh all rows.
:::

## Filtering data {#filtering-data}

The repository's filter controls what data flows to connected components. Your underlying collection stays unchanged because the filter acts as a lens:

```java
// Filter by stock availability
repository.setBaseFilter(product -> product.getStock() > 0);

// Filter by category
repository.setBaseFilter(product -> "Electronics".equals(product.getCategory()));

// Combine multiple conditions
repository.setBaseFilter(product -> 
    product.getCategory().equals("Electronics") && 
    product.getStock() > 0 && 
    product.getPrice() < 100.0
);

// Clear filter
repository.setBaseFilter(null);
```

When you set a filter, the `Repository`:
1. Applies the predicate to each item in your collection
2. Creates a filtered stream of matching items
3. Notifies connected components to update their display

The filter persists until you change it. New items added to the collection are automatically tested against the current filter.


## Working with entity keys {#working-with-entity-keys}

When your entities implement <JavadocLink type="data" location="com/webforj/data/HasEntityKey" code="true">HasEntityKey</JavadocLink>, the repository can find and update specific items by their ID:

```java
public class Customer implements HasEntityKey {
    private String customerId;
    private String name;
    private String email;
    
    @Override
    public Object getEntityKey() {
        return customerId;
    }
    
    // Constructor and getters/setters...
}

// Find by key
Optional<Customer> customer = repository.find("C001");

// Update specific customer
customer.ifPresent(c -> {
    c.setEmail("newemail@example.com");
    repository.commit(c); // Only this customer's row updates
});
```

Without `HasEntityKey`:
- `repository.find("C001")` won't find your customer because it looks for an object that equals "C001"
- `repository.commit(entity)` still works, but relies on object equality
- UI components can't select items by ID, only by object reference


## UI integration {#ui-integration}

`Repository` integrates with data-aware components:

```java
// Create repository and table
List<Customer> customers = new ArrayList<>();
CollectionRepository<Customer> repository = new CollectionRepository<>(customers);

Table<Customer> table = new Table<>();
table.setRepository(repository);
table.addColumn("ID", Customer::getId);
table.addColumn("Name", Customer::getName);
table.addColumn("Email", Customer::getEmail);

// Add data - table updates automatically
customers.add(new Customer("C001", "Alice Johnson", "alice@example.com"));
repository.commit();
```


## Next steps {#next-steps}

<DocCardList className="topics-section" />