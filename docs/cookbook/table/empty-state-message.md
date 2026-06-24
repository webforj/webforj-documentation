---
title: "Show an empty-state message when a Table has no rows"
description: "Toggle a placeholder message whenever the repository backing a Table becomes empty."
tags: [table]
components: [Table]
difficulty: beginner
---

Keep the `Table`, repository, and empty-state component as fields so the helper method can access them after the constructor finishes. The `cookbook-empty-state` styling comes from a sidecar style sheet loaded with `@StyleSheet`.

```java
import com.webforj.annotation.StyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.icons.FeatherIcon;
import com.webforj.component.table.Table;
import com.webforj.data.repository.CollectionRepository;
import com.webforj.router.annotation.Route;
import java.util.List;

@Route("books")
@StyleSheet("ws://cookbook-static/empty-state.css")
public class BooksView extends Composite<Div> {
  private final Div self = getBoundComponent();
  private final CollectionRepository<Book> repository =
      new CollectionRepository<>(List.of(
          new Book("Modern Java", "A. Developer"),
          new Book("Practical UI", "B. Designer")));
  private final Table<Book> table = new Table<>();
  private final Div emptyState = new Div();

  public BooksView() {
    table.addColumn("title", Book::title).setLabel("Title");
    table.addColumn("author", Book::author).setLabel("Author");
    table.setRepository(repository);
    table.setSize("100vw", "100vh");

    emptyState.addClassName("cookbook-empty-state");
    emptyState.add(
        FeatherIcon.BOOK.create().addClassName("cookbook-empty-state__icon"),
        new Paragraph("No books found"));
    emptyState.setVisible(false);

    self.add(table, emptyState);
    refreshEmptyState();
    repository.addCommitListener(e -> refreshEmptyState());
  }

  private void refreshEmptyState() {
    boolean hasResults = repository.findAll().findAny().isPresent();
    table.setVisible(hasResults);
    emptyState.setVisible(!hasResults);
  }

  public record Book(String title, String author) {}
}
```

Place the style sheet at `src/main/resources/static/cookbook-static/empty-state.css`:

```css
.cookbook-empty-state {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: var(--dwc-space-s);
  padding: var(--dwc-space-xl);
  color: var(--dwc-color-default-text);
  background: var(--dwc-surface-2);
  border: var(--dwc-border-width) var(--dwc-border-style) var(--dwc-border-color);
  border-radius: var(--dwc-border-radius);
}

.cookbook-empty-state__icon {
  color: var(--dwc-color-default-text);
}
```
