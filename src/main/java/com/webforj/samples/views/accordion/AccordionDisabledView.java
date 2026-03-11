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

@Route("/disabled")
@FrameTitle("Accordion - Disabled State")
public class AccordionDisabledView extends Composite<FlexLayout> {

  private final FlexLayout self = getBoundComponent();

  public AccordionDisabledView() {
    self.setDirection(FlexDirection.COLUMN);
    self.setMaxWidth("700px");
    self.setStyle("margin", "0 auto");
    self.setPadding("var(--dwc-space-l)");
    self.setSpacing("var(--dwc-space-m)");

    self.add(new H3("Disabled Panels"));

    AccordionPanel disabled = new AccordionPanel("This panel is disabled");
    disabled.add(new Paragraph("You should not be able to see this content."));
    disabled.setEnabled(false);

    AccordionPanel disabledOpened = new AccordionPanel("Disabled but opened");
    disabledOpened.add(new Paragraph("This content is visible but the header cannot be clicked to collapse."));
    disabledOpened.open();
    disabledOpened.setEnabled(false);

    self.add(disabled, disabledOpened);

    self.add(new H3("Disable entire accordion group"));

    AccordionPanel panel1 = new AccordionPanel("Panel One");
    panel1.add(new Paragraph("Content for panel one."));
    panel1.open();

    AccordionPanel panel2 = new AccordionPanel("Panel Two");
    panel2.add(new Paragraph("Content for panel two."));

    AccordionPanel panel3 = new AccordionPanel("Panel Three");
    panel3.add(new Paragraph("Content for panel three."));

    Accordion accordion = new Accordion(panel1, panel2, panel3);

    Button toggle = new Button("Toggle Enabled", e -> {
      accordion.setEnabled(!accordion.isEnabled());
    });
    toggle.setTheme(ButtonTheme.PRIMARY);

    self.add(toggle, accordion);
  }
}
