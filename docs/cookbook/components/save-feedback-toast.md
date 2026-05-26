---
title: "Show a success or error Toast after a save action"
description: "Use Toast.show with a success or danger theme to give the user immediate, non-blocking feedback after a server-side operation."
tags: [notification, components]
components: [Toast]
difficulty: beginner
---

Wrap the operation in a try/catch and call `Toast.show` with the appropriate `Theme` in each branch.

```java
Button save = new Button("Save");

save.onClick(e -> {
  try {
    service.persist(entity);
    Toast.show("Changes saved.", Theme.SUCCESS);
  } catch (Exception ex) {
    Toast.show("Save failed: " + ex.getMessage(), Theme.DANGER);
  }
});
```
