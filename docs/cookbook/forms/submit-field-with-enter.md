---
title: "Run an action when Enter is pressed in a field"
description: "Attach a keypress listener to a TextField so Enter triggers the same action as a button click."
tags: [forms, components]
components: [TextField, Button]
difficulty: beginner
---

Attach an `onKeypress` listener to the field and compare the event's key code with `KeypressEvent.Key.ENTER`. Put the action in one method so the keyboard and button paths stay in sync.

```java
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.event.KeypressEvent;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.Route;

@Route("keyboard-search")
public class KeyboardSearchView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  private final TextField searchField = new TextField(TextField.Type.SEARCH, "Search");
  private final Button searchButton = new Button("Search", ButtonTheme.PRIMARY);
  private final Paragraph result = new Paragraph("Enter a search term.");

  public KeyboardSearchView() {
    self.setDirection(FlexDirection.COLUMN)
        .setSpacing("0.75rem")
        .setMaxWidth(420);

    searchField.setPlaceholder("Customer, order, or invoice");
    searchField.onKeypress(event -> {
      if (event.getKeyCode() == KeypressEvent.Key.ENTER) {
        runSearch();
      }
    });
    searchButton.onClick(event -> runSearch());

    self.add(searchField, searchButton, result);
  }

  private void runSearch() {
    String query = searchField.getValue().trim();
    result.setText(query.isEmpty()
        ? "Enter a search term."
        : "Searching for: " + query);
  }
}
```

The keypress listener is active while the field has focus. It doesn't create a page-wide keyboard shortcut.
