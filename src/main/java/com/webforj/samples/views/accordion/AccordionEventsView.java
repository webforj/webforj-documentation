package com.webforj.samples.views.accordion;

import com.webforj.component.Composite;
import com.webforj.component.Theme;
import com.webforj.component.accordion.AccordionPanel;
import com.webforj.component.html.elements.H3;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.toast.Toast;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route("/events")
@FrameTitle("Accordion - Events")
public class AccordionEventsView extends Composite<FlexLayout> {

  private final FlexLayout self = getBoundComponent();

  public AccordionEventsView() {
    self.setDirection(FlexDirection.COLUMN);
    self.setMaxWidth("700px");
    self.setStyle("margin", "0 auto");
    self.setPadding("var(--dwc-space-l)");
    self.setSpacing("var(--dwc-space-m)");

    self.add(new H3("Events Demo"));
    self.add(new Paragraph("Toggle, open, and close events are shown as toasts."));

    AccordionPanel panel = new AccordionPanel("Click me to see events");
    panel.add(new Paragraph("Watch for toast notifications when this panel is toggled."));

    panel.onToggle(e -> {
      String state = e.isOpened() ? "opening" : "closing";
      Toast.show("Toggle: " + state, Theme.PRIMARY);
    });

    panel.onOpen(e -> {
      Toast.show("Panel fully opened", Theme.SUCCESS);
    });

    panel.onClose(e -> {
      Toast.show("Panel fully closed", Theme.DANGER);
    });

    self.add(panel);
  }
}
