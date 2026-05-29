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
    self.setDirection(FlexDirection.COLUMN)
        .setSpacing("var(--dwc-space-m)")
        .setPadding("var(--dwc-space-l)")
        .setMargin("0 auto")
        .setMaxWidth("700px");

    AccordionPanel panel =
        new AccordionPanel(
                "Plus Icon Panel",
                new Paragraph("This panel uses a custom expand/collapse icon via the icon slot."))
            .setIcon(FeatherIcon.PLUS.create());

    self.add(panel);
  }
}
