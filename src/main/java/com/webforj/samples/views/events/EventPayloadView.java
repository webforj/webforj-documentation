package com.webforj.samples.views.events;

import com.webforj.bundle.annotation.BundleEntry;
import com.webforj.component.Composite;
import com.webforj.component.html.elements.H2;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.list.ChoiceBox;
import com.webforj.component.list.ListItem;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import java.util.LinkedHashMap;
import java.util.Map;

@Route
@FrameTitle("Event Payload")
@BundleEntry("events/eventpayload.css")
public class EventPayloadView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  private final ChoiceBox method = new ChoiceBox("Shipping method");
  private final Map<Object, Paragraph> estimates = new LinkedHashMap<>();

  public EventPayloadView() {
    method.add("STD", "Standard");
    method.add("EXP", "Express");
    method.add("OVN", "Overnight");

    estimates.put("STD", buildEstimate("Arrives in 5 to 7 business days"));
    estimates.put("EXP", buildEstimate("Arrives in 2 business days"));
    estimates.put("OVN", buildEstimate("Arrives tomorrow"));

    FlexLayout estimateArea = new FlexLayout();
    estimateArea.setDirection(FlexDirection.COLUMN).addClassName("payload-estimates");
    estimates.values().forEach(estimateArea::add);

    method.onSelect(
        event -> {
          ListItem selected = event.getSelectedItem();
          showEstimate(selected.getKey());
        });

    FlexLayout card = new FlexLayout(new H2("Checkout"), method, estimateArea);
    card.setDirection(FlexDirection.COLUMN)
        .setSpacing("var(--dwc-space-m)")
        .addClassName("payload-card");

    self.setDirection(FlexDirection.COLUMN)
        .setAlignment(FlexAlignment.CENTER)
        .setJustifyContent(FlexJustifyContent.CENTER)
        .addClassName("payload-container");

    self.setStyle("min-height", "100vh");
    self.add(card);
  }

  private Paragraph buildEstimate(String text) {
    Paragraph p = new Paragraph(text);
    p.addClassName("payload-estimate");
    return p;
  }

  private void showEstimate(Object key) {
    estimates.forEach(
        (estimateKey, paragraph) -> {
          if (estimateKey.equals(key)) {
            paragraph.addClassName("is-active");
          } else {
            paragraph.removeClassName("is-active");
          }
        });
  }
}
