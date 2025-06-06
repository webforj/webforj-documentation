package com.webforj.samples.views.applayout.stickytoolbar;

import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.H2;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.router.annotation.Route;
import com.webforj.samples.views.applayout.AbstractContentView;

@Route(value = "/content/:name", outlet = AppLayoutStickyToolbarView.class)
public class AppLayoutStickyToolbarContentView extends AbstractContentView {
  private final Div self = getBoundComponent();

  public AppLayoutStickyToolbarContentView() {
    for (int i = 0; i < 10; i++) {
      Div content = new Div();
      content
          .addClassName("card")
          .add(
              new H2("What is Lorem Ipsum ?"),
              new Paragraph("Lorem Ipsum is simply dummy text of the printing and typesetting " +
                  "industry. Lorem Ipsum has been the industry's standard dummy text " +
                  "ever since the 1500s when an unknown printer took a galley of type " +
                  "and scrambled it to make a type specimen book. It has survived not " +
                  "only five centuries, but also the leap into electronic " +
                  "typesetting, remaining essentially unchanged. It was popularized " +
                  "in the 1960s with the release of Letraset sheets containing Lorem " +
                  "Ipsum passages, and more recently with desktop publishing software " +
                  "like Aldus PageMaker including versions of Lorem Ipsum."));
      self.add(content);
    }

    super.contentLabel.setVisible(false);
  }
}
