package com.webforj.samples.views.accordion;

import com.webforj.component.Composite;
import com.webforj.component.accordion.AccordionPanel;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.html.elements.Span;
import com.webforj.component.icons.FeatherIcon;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Accordion - Custom Header")
public class AccordionCustomHeaderView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public AccordionCustomHeaderView() {
    self.setDirection(FlexDirection.COLUMN)
      .setSpacing("var(--dwc-space-m)")
      .setPadding("var(--dwc-space-l)")
      .setMargin("0 auto")
      .setMaxWidth("700px")
      .add(createCustomHeaderPanel(FeatherIcon.SETTINGS, "Custom Header with Icon",
          "The header slot lets you fully customize the header content."),
        createCustomHeaderPanel(FeatherIcon.USER, "User Settings",
          "Another panel with a custom header using the user icon.")
      );
  }

  private AccordionPanel createCustomHeaderPanel(FeatherIcon icon, String title, String content) {
    FlexLayout headerContent = new FlexLayout(icon.create(), new Span(title))
      .setDirection(FlexDirection.ROW)
      .setSpacing("var(--dwc-space-s)");

    AccordionPanel panel = new AccordionPanel()
      .addToHeader(headerContent);
    panel.add(new Paragraph(content));

    return panel;
  }
}
