package com.webforj.samples.views.navigator;

import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.navigator.Navigator;
import com.webforj.component.navigator.Navigator.Layout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Navigator Pagination")
public class NavigatorPagesView extends Composite<Div> {
  private final Div self = getBoundComponent();

  public NavigatorPagesView() {
    // Create instruction text and navigator with pages layout
    Paragraph text = new Paragraph("Navigate with the buttons below");
    Navigator nav = new Navigator(100, Layout.PAGES);

    // Configure paginator and handle change events
    nav.getPaginator().setMax(5);
    nav.onChange(e -> {
      int start = e.getStartIndex();
      int end = e.getEndIndex();

      // Adjust for 1-based indexing
      if (end != 0) {
        start = start + 1;
        end = end + 1;
      }

      String formattedText = String.format("Showing %d to %d of 100", start, end);
      text.setText(formattedText);
    });

    // Add components to container
    self.setStyle("padding", "20px")
        .add(text, nav);
  }
}
