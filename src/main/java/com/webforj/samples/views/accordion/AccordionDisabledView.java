package com.webforj.samples.views.accordion;

import com.webforj.component.Composite;
import com.webforj.component.accordion.Accordion;
import com.webforj.component.accordion.AccordionPanel;
import com.webforj.component.html.elements.H3;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.optioninput.RadioButton;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Accordion - Disabled State")
public class AccordionDisabledView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  private final Accordion accordion;

  public AccordionDisabledView() {
    self.setDirection(FlexDirection.COLUMN)
        .setSpacing("var(--dwc-space-m)")
        .setPadding("var(--dwc-space-l)")
        .setMargin("0 auto")
        .setMaxWidth("700px");

    accordion = createAccordionGroup();

    RadioButton toggle = RadioButton.Switch("Accordion enabled").setChecked(true);
    toggle.onToggle(e -> accordion.setEnabled(e.isToggled()));

    self.add(
        new H3("Disabled Panels"),
        createDisabledPanel("This panel is disabled", false),
        createDisabledPanel("Disabled but opened", true),
        new H3("Disable entire accordion group"),
        toggle,
        accordion);
  }

  private AccordionPanel createDisabledPanel(String title, boolean initiallyOpened) {
    AccordionPanel panel =
        new AccordionPanel(title, new Paragraph("You should not be able to see this content."));
    if (initiallyOpened) {
      panel.open();
    }
    panel.setEnabled(false);
    return panel;
  }

  private Accordion createAccordionGroup() {
    AccordionPanel panel1 =
        new AccordionPanel("Panel One", new Paragraph("Content for panel one."));
    panel1.open();

    AccordionPanel panel2 =
        new AccordionPanel("Panel Two", new Paragraph("Content for panel two."));

    AccordionPanel panel3 =
        new AccordionPanel("Panel Three", new Paragraph("Content for panel three."));

    return new Accordion(panel1, panel2, panel3);
  }
}
