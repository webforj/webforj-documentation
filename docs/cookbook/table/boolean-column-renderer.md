---
title: "Render a boolean column with custom icons in a Table"
description: "Replace true/false cell values with themed icons using BooleanRenderer so status columns are scannable at a glance."
tags: [table, components]
components: [Table]
difficulty: beginner
---

`BooleanRenderer` expects the column's value provider to return a `Boolean`. In this example, `Task::isDone` reads the `done` property from each row and the renderer turns that value into a themed icon.

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
        TablerIcon.create("thumb-up").setTheme(Theme.SUCCESS),
        TablerIcon.create("thumb-down").setTheme(Theme.DANGER));

    table
        .addColumn("done", Task::isDone)
        .setLabel("Done")
        .setRenderer(doneRenderer);

    table.setRepository(new CollectionRepository<>(List.of(
        new Task("Review invoice", true),
        new Task("Send reminder", false))));

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
