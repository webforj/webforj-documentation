package com.webforj.samples.views.accordion;

import com.webforj.component.Composite;
import com.webforj.component.accordion.Accordion;
import com.webforj.component.accordion.AccordionPanel;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.html.elements.H3;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route("/multiple")
@FrameTitle("Accordion - Multiple Mode")
public class AccordionMultipleView extends Composite<FlexLayout> {

  private final FlexLayout self = getBoundComponent();

  public AccordionMultipleView() {
    self.setDirection(FlexDirection.COLUMN);
    self.setMaxWidth("700px");
    self.setStyle("margin", "0 auto");
    self.setPadding("var(--dwc-space-l)");
    self.setSpacing("var(--dwc-space-m)");

    self.add(new H3("Accordion Group (multiple mode)"));
    self.add(new Paragraph("Multiple panels can be open simultaneously."));

    AccordionPanel panelA = new AccordionPanel("Panel A");
    panelA.add(new Paragraph("This panel is opened."));
    panelA.open();

    AccordionPanel panelB = new AccordionPanel("Panel B");
    panelB.add(new Paragraph("This panel is also opened. Both can be open at the same time."));
    panelB.open();

    AccordionPanel panelC = new AccordionPanel("Panel C");
    panelC.add(new Paragraph("Content for panel C."));

    Accordion accordion = new Accordion(panelA, panelB, panelC);
    accordion.setMultiple(true);

    Button openAll = new Button("Open All", e -> accordion.openAll());
    openAll.setTheme(ButtonTheme.PRIMARY);

    Button closeAll = new Button("Close All", e -> accordion.closeAll());

    FlexLayout buttons = FlexLayout.create(openAll, closeAll).build();
    buttons.setSpacing("var(--dwc-space-s)");

    self.add(buttons, accordion);
  }
}
