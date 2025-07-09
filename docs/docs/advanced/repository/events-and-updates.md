---
title: Events and updates
sidebar_position: 5
---

<!-- vale off -->
# Events and updates <DocChip chip='since' label='24.00' />
<!-- vale on -->

Repository events let you react to data changes. Beyond the automatic UI updates, you can listen for changes to trigger custom logic.

## Repository event lifecycle

Every `commit()` call fires a <JavadocLink type="data" location="com/webforj/data/repository/event/RepositoryCommitEvent" code="true">RepositoryCommitEvent</JavadocLink>. This event carries information about what changed:

```java
repository.onCommit(event -> {
    // Get all committed entities
    List<Customer> commits = event.getCommits();
    
    // Check if single entity update
    if (event.isSingleCommit()) {
        Customer updated = event.getFirstCommit();
        System.out.println("Updated: " + updated.getName());
    }
});
```

The event tells you:
- `getCommits()` - List of entities that were committed
- `isSingleCommit()` - Whether this was a targeted single-entity update
- `getFirstCommit()` - Convenience method to get the first (or only) entity

For `commit()` without parameters, the event contains all entities currently in the repository after filtering.

## Update strategies

The two commit signatures serve different purposes:

```java
// Single-entity update - efficient for individual changes
Customer customer = customers.get(0);
customer.setStatus("VIP");
repository.commit(customer);

// Bulk update - efficient for multiple changes
products.clear();
products.addAll(loadProductsFromCsv());
repository.commit();
```

Single-entity commits are surgical - they tell connected components exactly which row changed. The Table can update just that row's cells without touching anything else.

Bulk commits refresh everything. Use them when:
- Multiple entities changed
- You added or removed items
- You're not sure what changed

## Reactive UI patterns

Repository events let you keep summary displays in sync with your data:

```java
// Auto-updating labels
repository.onCommit(event -> {
    double total = sales.stream().mapToDouble(Sale::getAmount).sum();
    totalLabel.setText(String.format("Total: $%.2f", total));
    countLabel.setText("Sales: " + sales.size());
});

// Live result counts
repository.onCommit(e -> {
    long count = repository.findAll().count();
    resultsLabel.setText(count + " products found");
});
```

These listeners fire on every commit, whether from user actions, data imports, or programmatic updates. The event gives you access to the committed entities, but often you'll recalculate from the source collection to include all current data.

## Memory management

Event listeners hold references to your components. If you don't remove them, the repository keeps your components in memory even after they're no longer displayed:

```java
// Keep reference to remove later
ListenerRegistration<RepositoryCommitEvent<Data>> registration = 
    repository.onCommit(event -> {
        updateDisplay(event.getCommits());
    });

// Clean up listener when component is destroyed
if (registration != null) {
    registration.remove();
}
```

The `onCommit()` method returns a ListenerRegistration. Store this reference and call `remove()` when your component is destroyed or no longer needs updates. This prevents memory leaks in long-running applications.