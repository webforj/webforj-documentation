package com.webforj.samples.views.refresher;

import java.util.List;
import java.util.Random;

import com.webforj.annotation.StyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.icons.FeatherIcon;
import com.webforj.component.icons.Icon;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.refresher.Refresher;
import com.webforj.router.annotation.Route;

@Route
@StyleSheet("ws://css/refresher/refresher.css")
public class RefresherView extends Composite<Div> {
  // Names for the item list
  private static final List<String> NAMES = List.of(
          "John", "Jane", "Alice", "Bob", "Charlie", "Diana",
          "Ethan", "Fiona", "George", "Hannah", "Ian", "Jill"
  );

  private final Div self = getBoundComponent();

  // Random generator for selecting names
  private final Random random = new Random();

  public RefresherView() {
    // Create canvas for displaying items
    Div canvas = new Div()
        .addClassName("is-canvas");

    // Create refresher component
    Refresher refresher = new Refresher();

    // Handle refresh events
    refresher.onRefresh(e -> {
      canvas.removeAll();
      for (int i = 0; i < 8; i++) {
        canvas.add(new Item());
      }
      refresher.finish();
    });

    // Add initial items and refresher to container
    for (int i = 0; i < 8; i++) {
      canvas.add(new Item());
    }

    self.add(canvas, refresher);
  }

  /**
   * Item component representing a single row in the refresher list.
   */
  class Item extends Composite<Div> {
    public Item() {
      Div self = getBoundComponent();

      // Select random name from the list
      String name = NAMES.get(random.nextInt(NAMES.size()));

      // Create item components with text content
      Div nameDiv = new Div(name).addClassName("item-name");
      Div excerpt = new Div("""
              Lorem ipsum dolor sit amet, consectetur adipiscing elit. \
              Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.""")
          .addClassName("item-excerpt");

      // Create arrow icon
      Icon icon = FeatherIcon.ARROW_RIGHT.create()
          .setMinSize("24px", "24px");

      // Compose layout with flexbox
      self.add(
          FlexLayout.create(
              FlexLayout.create(nameDiv, excerpt)
                  .vertical()
                  .build(),
              icon
          )
              .horizontal()
              .justify().between()
              .align().center()
              .build()
      );
    }
  }
}
