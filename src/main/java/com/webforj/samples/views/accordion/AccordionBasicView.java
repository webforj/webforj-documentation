package com.webforj.samples.views.accordion;

import com.webforj.component.Composite;
import com.webforj.component.accordion.AccordionPanel;
import com.webforj.component.html.elements.H3;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route("/basic")
@FrameTitle("Accordion - Standalone Panels")
public class AccordionBasicView extends Composite<FlexLayout> {

  private final FlexLayout self = getBoundComponent();

  public AccordionBasicView() {
    self.setDirection(FlexDirection.COLUMN);
    self.setMaxWidth("700px");
    self.setStyle("margin", "0 auto");
    self.setPadding("var(--dwc-space-l)");
    self.setSpacing("var(--dwc-space-m)");

    self.add(new H3("Standalone Panels"));
    self.add(new Paragraph("Each panel works independently without a group wrapper."));

    AccordionPanel panel1 = new AccordionPanel("Section One");
    panel1.add(new Paragraph("This panel starts opened. Each panel operates independently."));
    panel1.open();

    AccordionPanel panel2 = new AccordionPanel("Section Two");
    panel2.add(new Paragraph("Content for section two. Click the header to expand."));

    AccordionPanel panel3 = new AccordionPanel("Section Three");
    panel3.add(new Paragraph("Content for section three."));

    self.add(panel1, panel2, panel3);
  }
}
