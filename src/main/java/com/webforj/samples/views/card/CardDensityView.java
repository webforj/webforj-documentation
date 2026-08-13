package com.webforj.samples.views.card;

import com.webforj.bundle.annotation.BundleEntry;
import com.webforj.component.Composite;
import com.webforj.component.Expanse;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.card.Card;
import com.webforj.component.html.elements.H3;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.html.elements.Span;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.layout.flexlayout.FlexWrap;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Invoice Summary")
@BundleEntry("css/card/cardDensity.css")
public class CardDensityView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public CardDensityView() {
    self.setMargin("var(--dwc-space-l)")
        .setSpacing("var(--dwc-space-m")
        .setJustifyContent(FlexJustifyContent.CENTER)
        .setAlignment(FlexAlignment.CENTER)
        .setWrap(FlexWrap.WRAP);

    Card invoice = buildInvoice("INV-2041", "$8,400.00");
    invoice.setDivided(true).setExpanse(Expanse.LARGE);

    Card compact = buildInvoice("INV-2041", "$8,400.00");
    compact.setDivided(true).setExpanse(Expanse.SMALL);

    self.add(invoice, compact);
  }

  private Card buildInvoice(String number, String total) {
    FlexLayout lines =
        FlexLayout.create(
                buildLine("Design retainer", "$6,000.00"),
                buildLine("Accessibility audit", "$1,800.00"),
                buildLine("Hosting", "$600.00"))
            .vertical()
            .build()
            .setSpacing("var(--dwc-space-xs)");

    Card card = new Card(lines);

    return card.addToTitle(new H3(number))
        .addToCaption(new Paragraph("Due August 30, 2026"))
        .addToFooter(
            FlexLayout.create(
                    new Span("Total " + total).addClassName("card-density__total"),
                    new Button("Pay", ButtonTheme.PRIMARY))
                .horizontal()
                .justify()
                .between()
                .align()
                .center()
                .build());
  }

  private FlexLayout buildLine(String label, String amount) {
    return FlexLayout.create(new Span(label), new Span(amount))
        .horizontal()
        .justify()
        .between()
        .build()
        .addClassName("card-density__line");
  }
}
