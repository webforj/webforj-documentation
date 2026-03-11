package com.webforj.samples.views.accordion;

import com.webforj.component.Composite;
import com.webforj.component.accordion.AccordionPanel;
import com.webforj.component.html.elements.H3;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.html.elements.Span;
import com.webforj.component.icons.FeatherIcon;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route("/slots")
@FrameTitle("Accordion - Custom Slots")
public class AccordionSlotsView extends Composite<FlexLayout> {

  private final FlexLayout self = getBoundComponent();

  public AccordionSlotsView() {
    self.setDirection(FlexDirection.COLUMN);
    self.setMaxWidth("700px");
    self.setStyle("margin", "0 auto");
    self.setPadding("var(--dwc-space-l)");
    self.setSpacing("var(--dwc-space-m)");

    self.add(new H3("Custom Header Slot"));

    AccordionPanel customHeader = new AccordionPanel();
    FlexLayout headerContent = FlexLayout.create().horizontal().build()
        .setSpacing("var(--dwc-space-s)");
    headerContent.add(FeatherIcon.SETTINGS.create(), new Span("Custom Header with Icon"));
    customHeader.addToHeader(headerContent);
    customHeader.add(new Paragraph("The header slot lets you fully customize the header content."));

    AccordionPanel customHeader2 = new AccordionPanel();
    FlexLayout headerContent2 = FlexLayout.create().horizontal().build().setSpacing("var(--dwc-space-s)");
    headerContent2.add(FeatherIcon.USER.create(), new Span("User Settings"));
    customHeader2.addToHeader(headerContent2);
    customHeader2.add(new Paragraph("Another panel with a custom header using the user icon."));

    self.add(customHeader, customHeader2);

    self.add(new H3("Custom Icon Slot"));

    AccordionPanel customIcon = new AccordionPanel("Plus Icon Panel");
    customIcon.setIcon(FeatherIcon.PLUS.create());
    customIcon.add(new Paragraph("This panel uses a custom expand/collapse icon via the icon slot."));

    self.add(customIcon);
  }
}
