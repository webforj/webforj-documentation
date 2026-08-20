package com.webforj.samples.views.card;

import com.webforj.bundle.annotation.BundleEntry;
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.card.Card;
import com.webforj.component.googlecharts.GoogleChart;
import com.webforj.component.html.elements.H3;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.html.elements.Span;
import com.webforj.component.icons.TablerIcon;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import java.util.List;
import java.util.Map;

@Route
@FrameTitle("Monthly Report Card")
@BundleEntry("css/card/cardRegions.css")
public class CardRegionsView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public CardRegionsView() {
    self.setDirection(FlexDirection.COLUMN)
        .setJustifyContent(FlexJustifyContent.CENTER)
        .setAlignment(FlexAlignment.CENTER)
        .addClassName("card-demo");

    Card card = new Card(buildBody());
    card.addToFigure(buildChart())
        .addToIcon(TablerIcon.create("chart-bar"))
        .addToTitle(new H3("Monthly Report"))
        .addToCaption(new Paragraph("July 2026"))
        .addToHeaderActions(new Button("Share", ButtonTheme.OUTLINED_PRIMARY))
        .addToFooter(new Button("Read More"))
        .setWidth("100%")
        .setMaxWidth("380px");

    self.add(card);
  }

  private GoogleChart buildChart() {
    GoogleChart chart = new GoogleChart(GoogleChart.Type.COLUMN);
    chart.addClassName("card-regions__chart");

    chart.setOptions(
        Map.of(
            "legend", "none",
            "backgroundColor", "transparent",
            "colors", List.of("006fe6"),
            "chartArea", Map.of("width", "80%", "height", "72%"),
            "hAxis", Map.of("textStyle", Map.of("fontSize", 11)),
            "vAxis",
                Map.of(
                    "textPosition", "none",
                    "baselineColor", "transparent")));

    chart.setData(
        List.of(
            List.of("Month", "Sales"),
            List.of("Feb", 268000),
            List.of("Mar", 291000),
            List.of("Apr", 334000),
            List.of("May", 356000),
            List.of("Jun", 368000),
            List.of("Jul", 412800)));

    return chart;
  }

  private FlexLayout buildBody() {
    Span total = new Span("$412,800").addClassName("card-regions__total");
    Span change = new Span("Up 12% over June").addClassName("card-regions__change");

    FlexLayout summary =
        FlexLayout.create(total, change)
            .horizontal()
            .align()
            .baseline()
            .build()
            .setSpacing("var(--dwc-space-s");

    return FlexLayout.create(
            summary,
            new Paragraph(
                "Sales climbed across every region, with the strongest growth in the Northeast."))
        .vertical()
        .build()
        .setSpacing("var(--dwc-space-m)");
  }
}
