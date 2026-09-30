package com.webforj.samples.views.element;

import com.webforj.bundle.annotation.BundleEntry;
import com.webforj.component.Composite;
import com.webforj.component.Expanse;
import com.webforj.component.card.Card;
import com.webforj.component.element.Element;
import com.webforj.component.html.elements.H2;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@BundleEntry("css/element/elementfigure.css")
@FrameTitle("Element Figure")
public class ElementFigureView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  private final Card card = new Card();

  public ElementFigureView() {
    createFigure();
    setCard();

    self.setHeight("100vh")
        .setAlignment(FlexAlignment.CENTER)
        .setJustifyContent(FlexJustifyContent.CENTER)
        .add(card);
  }

  private void createFigure() {

    Element figure = new Element("figure");
    figure.addClassName("testimonial-figure");

    Element quote = new Element("blockquote");
    quote.addClassName("testimonial-quote");
    quote.setText("Building the entire UI in Java kept our team on one stack.");

    Element caption = new Element("figcaption");
    caption.addClassName("testimonial-caption");
    caption.setHtml("<strong>Dana Lee</strong>, Engineering Lead");

    figure.add(quote, caption);

    card.addToBody(figure);
  }

  private void setCard() {
    card.setWidth("100%")
        .setMaxWidth(420)
        .setExpanse(Expanse.XLARGE)
        .addToTitle(new H2("Testimonial"));
  }
}
