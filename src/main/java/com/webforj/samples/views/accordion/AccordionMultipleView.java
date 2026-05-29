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

@Route
@FrameTitle("Accordion - Multiple Mode")
public class AccordionMultipleView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  private final Accordion accordion;

  public AccordionMultipleView() {
    self.setDirection(FlexDirection.COLUMN)
        .setSpacing("var(--dwc-space-m)")
        .setPadding("var(--dwc-space-l)")
        .setMargin("0 auto")
        .setMaxWidth("700px");

    accordion = createMultipleAccordion();

    self.add(
        new H3("Accordion Group (multiple mode)"),
        new Paragraph("Multiple panels can be open simultaneously."),
        createButtons(),
        accordion);
  }

  private Accordion createMultipleAccordion() {
    AccordionPanel panelA = new AccordionPanel("Panel A", new Paragraph("This panel is opened."));
    panelA.open();

    AccordionPanel panelB =
        new AccordionPanel(
            "Panel B",
            new Paragraph("This panel is also opened. Both can be open at the same time."));
    panelB.open();

    AccordionPanel panelC = new AccordionPanel("Panel C", new Paragraph("Content for panel C."));

    return new Accordion(panelA, panelB, panelC).setMultiple(true);
  }

  private FlexLayout createButtons() {
    Button openAll = new Button("Open All", e -> accordion.openAll());
    openAll.setTheme(ButtonTheme.PRIMARY);

    Button closeAll = new Button("Close All", e -> accordion.closeAll());

    return FlexLayout.create(openAll, closeAll)
        .horizontal()
        .build()
        .setSpacing("var(--dwc-space-s)");
  }
}
