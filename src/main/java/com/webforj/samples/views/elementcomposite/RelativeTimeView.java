package com.webforj.samples.views.elementcomposite;

import com.webforj.bundle.annotation.BundleEntry;
import com.webforj.bundle.annotation.BundlePackage;
import com.webforj.component.Composite;
import com.webforj.component.card.Card;
import com.webforj.component.element.ElementComposite;
import com.webforj.component.element.PropertyDescriptor;
import com.webforj.component.element.annotation.NodeName;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.html.elements.Span;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.concern.HasClassName;
import com.webforj.concern.HasStyle;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import java.time.Duration;
import java.time.Instant;

@Route
@FrameTitle("Launch Countdown")
public class RelativeTimeView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  private final Card card = new Card();

  public RelativeTimeView() {
    Span prefix = new Span("🚀 Rocket launching ");

    RelativeTime time = new RelativeTime();
    time.setDate(Instant.now().plus(Duration.ofDays(2)));
    time.setStyle("font-weight", "600");
    time.setStyle("color", "var(--dwc-color-primary)");

    Paragraph caption = new Paragraph(prefix, time);
    caption.setStyle("margin", "0");
    caption.setStyle("font-size", "1rem");

    card.add(caption);

    self.setHeight("100vh")
        .setAlignment(FlexAlignment.CENTER)
        .setJustifyContent(FlexJustifyContent.CENTER)
        .add(card);
  }

  @BundlePackage(value = "@awesome.me/webawesome", version = "^3.12.0")
  @BundleEntry("@awesome.me/webawesome/dist/styles/themes/default.css")
  @BundleEntry("@awesome.me/webawesome/dist/components/relative-time/relative-time.js")
  @NodeName("wa-relative-time")
  public static final class RelativeTime extends ElementComposite
      implements HasClassName<RelativeTime>, HasStyle<RelativeTime> {

    private final PropertyDescriptor<String> date = PropertyDescriptor.property("date", "");

    public RelativeTime setDate(Instant instant) {
      set(date, instant.toString());
      return this;
    }
  }
}
