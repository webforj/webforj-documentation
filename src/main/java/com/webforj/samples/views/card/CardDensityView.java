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
    self.setJustifyContent(FlexJustifyContent.CENTER)
        .setAlignment(FlexAlignment.CENTER)
        .setWrap(FlexWrap.WRAP)
        .setSpacing("var(--dwc-space-l)")
        .addClassName("card-demo");

    Card invoice = buildInvoice("INV-2041", "$8,400.00", Expanse.LARGE);
    invoice.setDivided(true).addClassName("card-density__invoice");

    Card compact = buildInvoice("INV-2041", "$8,400.00", Expanse.SMALL);
    compact.setDivided(true).addClassName("card-density__invoice");

    self.add(invoice, compact);
  }

  private Card buildInvoice(String number, String total, Expanse expanse) {
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
        .addToFooter(buildFooter(total, expanse));
  }

  private FlexLayout buildFooter(String total, Expanse expanse) {
    Button pay = new Button("Pay", ButtonTheme.PRIMARY);
    pay.setExpanse(expanse);

    FlexLayout footer =
        FlexLayout.create(new Span("Total " + total).addClassName("card-density__total"), pay)
            .horizontal()
            .build();
    footer.setJustifyContent(FlexJustifyContent.END);
    footer.setAlignment(FlexAlignment.CENTER);
    footer.setSpacing("var(--dwc-space-m)");
    footer.setWidth("100%");

    return footer;
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
