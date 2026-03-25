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
@FrameTitle("Accordion - Nested")
public class AccordionNestedView extends Composite<FlexLayout> {

  private final FlexLayout self = getBoundComponent();

  public AccordionNestedView() {
    self.setDirection(FlexDirection.COLUMN)
      .setSpacing("var(--dwc-space-m)")
      .setPadding("var(--dwc-space-l)")
      .setMargin("0 auto")
      .setMaxWidth("700px")
      .add(new H3("Nested Accordions"),
        new Paragraph("Accordions can be nested inside other accordion panels."),
        createOuterPanel(), createSiblingPanel());
  }

  private AccordionPanel createOuterPanel() {
    AccordionPanel outer = new AccordionPanel("Outer Panel (contains nested accordion)");
    outer.add(createInnerAccordion());
    outer.open();
    return outer;
  }

  private AccordionPanel createSiblingPanel() {
    return new AccordionPanel("Sibling Panel",
      new Paragraph("This is a sibling of the outer panel."));
  }

  private Accordion createInnerAccordion() {
    AccordionPanel innerA = new AccordionPanel("Inner Panel A",
      new Paragraph("Nested content A"));

    AccordionPanel innerB = new AccordionPanel("Inner Panel B",
      new Paragraph("Nested content B"));

    AccordionPanel innerC = new AccordionPanel("Inner Panel C",
      new Paragraph("Nested content C"));

    return new Accordion(innerA, innerB, innerC);
  }
}
