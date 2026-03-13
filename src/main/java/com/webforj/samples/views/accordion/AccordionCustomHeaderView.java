package com.webforj.samples.views.accordion;

import com.webforj.component.Composite;
import com.webforj.component.accordion.AccordionPanel;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.html.elements.Span;
import com.webforj.component.icons.FeatherIcon;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Accordion - Custom Header")
public class AccordionCustomHeaderView extends Composite<FlexLayout> {

  private final FlexLayout self = getBoundComponent();

  public AccordionCustomHeaderView() {
    self.setDirection(FlexDirection.COLUMN);
    self.setMaxWidth("700px");
    self.setStyle("margin", "0 auto");
    self.setPadding("var(--dwc-space-l)");
    self.setSpacing("var(--dwc-space-m)");

    AccordionPanel customHeader = new AccordionPanel();
    FlexLayout headerContent = FlexLayout.create().horizontal().build()
        .setSpacing("var(--dwc-space-s)");
    headerContent.add(FeatherIcon.SETTINGS.create(), new Span("Custom Header with Icon"));
    customHeader.addToHeader(headerContent);
    customHeader.add(new Paragraph("The header slot lets you fully customize the header content."));

    AccordionPanel customHeader2 = new AccordionPanel();
    FlexLayout headerContent2 = FlexLayout.create().horizontal().build()
        .setSpacing("var(--dwc-space-s)");
    headerContent2.add(FeatherIcon.USER.create(), new Span("User Settings"));
    customHeader2.addToHeader(headerContent2);
    customHeader2.add(new Paragraph("Another panel with a custom header using the user icon."));

    self.add(customHeader, customHeader2);
  }
}
