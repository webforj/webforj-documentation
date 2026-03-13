package com.webforj.samples.views.accordion;

import com.webforj.component.Composite;
import com.webforj.component.accordion.AccordionPanel;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.icons.FeatherIcon;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Accordion - Custom Icon")
public class AccordionCustomIconView extends Composite<FlexLayout> {

  private final FlexLayout self = getBoundComponent();

  public AccordionCustomIconView() {
    self.setDirection(FlexDirection.COLUMN);
    self.setMaxWidth("700px");
    self.setStyle("margin", "0 auto");
    self.setPadding("var(--dwc-space-l)");
    self.setSpacing("var(--dwc-space-m)");

    AccordionPanel customIcon = new AccordionPanel("Plus Icon Panel");
    customIcon.setIcon(FeatherIcon.PLUS.create());
    customIcon.add(new Paragraph("This panel uses a custom expand/collapse icon via the icon slot."));

    self.add(customIcon);
  }
}
