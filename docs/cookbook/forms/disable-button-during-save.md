---
title: "Disable a Button while a save operation runs"
description: "Prevent double-submit by disabling the button and updating its label for the duration of the operation, then restoring it when done."
tags: [forms, components]
components: [Button]
difficulty: beginner
---

Disable the button at the start of the click handler and re-enable it in a `finally` block so it's always restored, even if the operation throws.

```java
Button save = new Button("Save");

save.onClick(e -> {
  save.setEnabled(false);
  save.setText("Saving…");
  try {
    orderService.persist(currentOrder);
  } finally {
    save.setEnabled(true);
    save.setText("Save");
  }
});
```
