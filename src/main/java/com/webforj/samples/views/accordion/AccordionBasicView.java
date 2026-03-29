package com.webforj.samples.views.accordion;

import com.webforj.component.Composite;
import com.webforj.component.accordion.AccordionPanel;
import com.webforj.component.html.elements.H3;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Accordion - Standalone Panels")
public class AccordionBasicView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public AccordionBasicView() {
    self.setDirection(FlexDirection.COLUMN)
      .setSpacing("var(--dwc-space-m)")
      .setPadding("var(--dwc-space-l)")
      .setMargin("0 auto")
      .setMaxWidth("700px")
      .add(
        new H3("Standalone Panels"),
        new Paragraph("Each panel works independently without a group wrapper."),
        createPanel("Section One", "This panel starts opened. Each panel operates independently.", true),
        createPanel("Section Two", "Content for section two. Click the header to expand.", false),
        createPanel("Section Three", "Content for section three.", false)
      );
  }

  private AccordionPanel createPanel(String label, String content, boolean opened) {
    AccordionPanel panel = new AccordionPanel(label, new Paragraph(content));
    if (opened) {
      panel.open();
    }
    return panel;
  }
}
