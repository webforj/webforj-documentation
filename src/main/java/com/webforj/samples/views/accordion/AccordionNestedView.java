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
    self.setDirection(FlexDirection.COLUMN);
    self.setMaxWidth("700px");
    self.setStyle("margin", "0 auto");
    self.setPadding("var(--dwc-space-l)");
    self.setSpacing("var(--dwc-space-m)");

    self.add(new H3("Nested Accordions"));
    self.add(new Paragraph("Accordions can be nested inside other accordion panels."));

    AccordionPanel innerA = new AccordionPanel("Inner Panel A");
    innerA.add(new Paragraph("Nested content A"));

    AccordionPanel innerB = new AccordionPanel("Inner Panel B");
    innerB.add(new Paragraph("Nested content B"));

    AccordionPanel innerC = new AccordionPanel("Inner Panel C");
    innerC.add(new Paragraph("Nested content C"));

    Accordion innerAccordion = new Accordion(innerA, innerB, innerC);

    AccordionPanel outer = new AccordionPanel("Outer Panel (contains nested accordion)");
    outer.add(innerAccordion);
    outer.open();

    AccordionPanel sibling = new AccordionPanel("Sibling Panel");
    sibling.add(new Paragraph("This is a sibling of the outer panel."));

    self.add(outer, sibling);
  }
}
