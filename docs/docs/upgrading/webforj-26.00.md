---
title: Upgrade to 26.00
description: Upgrade from 25.00 to 26.00
pagination_next: null
draft: true
---

I shouldn't use first person when writing in the main Webforj documentation.

This documentation serves as a guide to upgrade webforJ apps from 25.00 to 26.00.
Here are the changes needed for existing apps to continue running smoothly.
As always, see the [GitHub release overview](https://github.com/webforj/webforj/releases) for a more comprehensive list of changes between releases.

## Filters for `Repository`

The `RetrievalCriteria` interface is removed in webforJ 26.00, including the `setFilter()` method. Instead of using the generic `Repository` interface, use either `CollectionRepository` for simple filters, or [`QueryableRepository`](/docs/advanced/repository/querying-data) for more advanced filtering types, sorting, and pagination.

### Before:
```java
private String searchTerm = "";

Repository<CustomerRecord> repository = new Repository<>();

repository.setFilter((CustomerRecord r) -> {
  String title = r.getTitle();
  return title.toLowerCase().contains(this.searchTerm);
  });
```

### After:
```java {3,5}
private String searchTerm = "";

CollectionRepository<CustomerRecord> repository = new CollectionRepository<>();

repository.setBaseFilter((CustomerRecord r) -> {
  String title = r.getTitle();
  return title.toLowerCase().contains(this.searchTerm);
  });
```

