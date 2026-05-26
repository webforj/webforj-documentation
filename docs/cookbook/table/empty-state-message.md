---
title: "Show an empty-state message when a Table has no rows"
description: "Toggle a placeholder message whenever the repository backing a Table becomes empty."
tags: [table]
components: [Table]
difficulty: beginner
---

Create a companion `Div` for the empty state, hide it initially, then toggle both elements' visibility inside a helper that runs on load and on every repository commit.

```java
Table<Book> table = new Table<>();
table.setRepository(repository);

Div emptyState = new Div();
emptyState.addClassName("empty-state");
emptyState.add(FeatherIcon.BOOK.create(), new Div("No books found"));
emptyState.setVisible(false);

refreshEmptyState();
repository.addCommitListener(e -> refreshEmptyState());

void refreshEmptyState() {
  boolean hasResults = repository.size() > 0;
  table.setVisible(hasResults);
  emptyState.setVisible(!hasResults);
}
```
