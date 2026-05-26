---
title: "Render a boolean column with custom icons in a Table"
description: "Replace true/false cell values with themed icons using BooleanRenderer so status columns are scannable at a glance."
tags: [table, components]
components: [Table]
difficulty: beginner
---

`BooleanRenderer` accepts two icon arguments, one for `true`, one for `false`. Pass any `Icon` instance and theme it with `setTheme` to match your design system.

```java
Table<Task> table = new Table<>();
table.addColumn("name", Task::getName);
table.addColumn("due", Task::getDueDate);

BooleanRenderer<Task> doneRenderer = new BooleanRenderer<>(
    TablerIcon.create("thumb-up").setTheme(Theme.SUCCESS),
    TablerIcon.create("thumb-down").setTheme(Theme.DANGER)
);

table.addColumn("done", Task::isDone).setRenderer(doneRenderer);
```
