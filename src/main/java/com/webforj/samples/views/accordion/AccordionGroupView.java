package com.webforj.samples.views.accordion;

import com.webforj.component.Composite;
import com.webforj.component.accordion.Accordion;
import com.webforj.component.accordion.AccordionPanel;
import com.webforj.component.html.elements.H3;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Accordion - Single Mode Group")
public class AccordionGroupView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public AccordionGroupView() {
    self.setDirection(FlexDirection.COLUMN)
      .setSpacing("var(--dwc-space-m)")
      .setPadding("var(--dwc-space-l)")
      .setMargin("0 auto")
      .setMaxWidth("700px")
      .add(new H3("Accordion Group (single mode)"),
        new Paragraph("Only one panel can be open at a time."),
        createAccordion());
  }

  private Accordion createAccordion() {
    AccordionPanel panel1 = new AccordionPanel("What is webforJ?",
      new Paragraph("webforJ is a Java framework for building web applications."));
    panel1.open();

    AccordionPanel panel2 = new AccordionPanel("How do grouped panels work?",
      new Paragraph("Panels inside an Accordion are coordinated. By default, expanding one collapses the others."));

    AccordionPanel panel3 = new AccordionPanel("Can I have multiple groups?",
      new Paragraph("Yes, each Accordion instance manages its own set of panels independently."));

    return new Accordion(panel1, panel2, panel3);
  }
}
