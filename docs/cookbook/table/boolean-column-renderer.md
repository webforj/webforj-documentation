---
title: "Render a boolean column with custom icons in a Table"
description: "Replace true/false cell values with themed icons using BooleanRenderer so status columns are scannable at a glance."
tags: [table, components]
components: [Table]
difficulty: beginner
---

`BooleanRenderer` turns a `Boolean` cell value into an icon. Pass a true icon and a false icon to the constructor to override the defaults (a check, a cross, and a dash for null). Here `Task::isDone` provides the value, and the renderer shows a green check for finished tasks and an amber clock for pending ones.

```java
import com.webforj.component.Composite;
import com.webforj.component.Theme;
import com.webforj.component.html.elements.Div;
import com.webforj.component.icons.TablerIcon;
import com.webforj.component.table.Table;
import com.webforj.component.table.renderer.BooleanRenderer;
import com.webforj.data.repository.CollectionRepository;
import com.webforj.router.annotation.Route;
import java.util.List;

@Route("tasks")
public class TasksView extends Composite<Div> {
  private final Div self = getBoundComponent();

  public TasksView() {
    Table<Task> table = new Table<>();
    table.addColumn("title", Task::getTitle).setLabel("Task");

    BooleanRenderer<Task> doneRenderer = new BooleanRenderer<>(
        TablerIcon.create("circle-check").setTheme(Theme.SUCCESS),
        TablerIcon.create("clock").setTheme(Theme.WARNING));

    table
        .addColumn("done", Task::isDone)
        .setLabel("Done")
        .setRenderer(doneRenderer);

    table.setRepository(new CollectionRepository<>(List.of(
          new Task("Review invoice", true),
          new Task("Send reminder", false))))
        .setWidth("100vw")
        .setHeight("100vh");

    self.add(table);
  }

  public static class Task {
    private final String title;
    private final Boolean done;

    public Task(String title, Boolean done) {
      this.title = title;
      this.done = done;
    }

    public String getTitle() {
      return title;
    }

    public Boolean isDone() {
      return done;
    }
  }
}
```

To customize only one state, or to show an icon for `null` values, use `setTrueIcon`, `setFalseIcon`, `setNullIcon`, and `setShowNull(true)` instead of passing both icons to the constructor.