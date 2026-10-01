package com.webforj.samples.views.element;

import com.webforj.component.Composite;
import com.webforj.component.Expanse;
import com.webforj.component.card.Card;
import com.webforj.component.element.Element;
import com.webforj.component.html.elements.H2;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Input Demo")
public class ElementMeterView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  private final Card card = new Card();
  private final Element meter = new Element("meter");

  public ElementMeterView() {
    createMeter();
    setCard();

    self.setHeight("100vh")
        .setAlignment(FlexAlignment.CENTER)
        .setJustifyContent(FlexJustifyContent.CENTER)
        .add(card);
  }

  private void createMeter() {
    meter.setAttribute("min", "0");
    meter.setAttribute("max", "10");
    meter.setAttribute("low", "2");
    meter.setAttribute("high", "9");
    meter.setAttribute("optimum", "4");
    meter.setAttribute("value", "7.2");
    meter.setWidth("100%");
  }

  private void setCard() {
    card.setWidth("100%")
        .setMaxWidth(420)
        .setExpanse(Expanse.XLARGE)
        .addToTitle(new H2("Storage"))
        .addToBody(meter, new Paragraph("7.2 GB of 10 GB used"));
  }
}
