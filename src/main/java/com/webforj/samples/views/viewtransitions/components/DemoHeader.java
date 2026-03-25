package com.webforj.samples.views.viewtransitions.components;

import com.webforj.annotation.StyleSheet;
import com.webforj.component.Component;
import com.webforj.component.Composite;
import com.webforj.component.html.elements.H3;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.concern.HasClassName;

@StyleSheet("ws://css/viewtransitions/components/demo-header.css")
public class DemoHeader extends Composite<FlexLayout> implements HasClassName<DemoHeader> {
  private final FlexLayout self = getBoundComponent();
  private final FlexLayout actionSlot;

  public DemoHeader(String title, String description) {
    this(title, description, null);
  }

  public DemoHeader(String title, String description, String colorVar) {
    self.setDirection(FlexDirection.COLUMN)
        .setAlignment(FlexAlignment.CENTER)
        .setSpacing("var(--dwc-space-s)");

    H3 heading = new H3(title);
    heading.setStyle("margin", "0");

    Paragraph desc = new Paragraph(description);
    desc.addClassName("demo-header-desc");
    if (colorVar != null) {
      desc.setStyle("--demo-header-color", "var(" + colorVar + ")");
    }

    actionSlot = FlexLayout.create().horizontal().align().center().build();
    actionSlot.setSpacing("var(--dwc-space-m)");

    self.add(heading, desc, actionSlot);
  }

  public DemoHeader addAction(Component component) {
    actionSlot.add(component);
    return this;
  }
}
