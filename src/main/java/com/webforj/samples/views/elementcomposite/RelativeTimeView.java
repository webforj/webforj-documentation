package com.webforj.samples.views.elementcomposite;

import com.webforj.bundle.annotation.BundleEntry;
import com.webforj.bundle.annotation.BundlePackage;
import com.webforj.component.Composite;
import com.webforj.component.element.ElementComposite;
import com.webforj.component.element.PropertyDescriptor;
import com.webforj.component.element.annotation.NodeName;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.html.elements.Span;
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

  public RelativeTimeView() {
    Span prefix = new Span("🚀 Rocket launching ");

    RelativeTime time = new RelativeTime();
    time.setDate(Instant.now().plus(Duration.ofDays(2)));
    time.setStyle("font-weight", "600");
    time.setStyle("color", "var(--dwc-color-primary)");

    Paragraph caption = new Paragraph(prefix, time);
    caption.setStyle("margin", "0");
    caption.setStyle("font-size", "1rem");

    FlexLayout container = new FlexLayout(caption);
    container.setStyle("background", "var(--dwc-surface-3)");
    container.setStyle("border", "thin solid var(--dwc-color-default)");
    container.setStyle("border-radius", "var(--dwc-border-radius-m)");
    container.setStyle("padding", "var(--dwc-space-m)");

    self.setJustifyContent(FlexJustifyContent.CENTER)
        .setMargin("var(--dwc-space-l)")
        .add(container);
  }

  @BundlePackage(value = "@shoelace-style/shoelace", version = "^2.20.1")
  @BundleEntry("@shoelace-style/shoelace/dist/themes/light.css")
  @BundleEntry("@shoelace-style/shoelace/dist/components/relative-time/relative-time.js")
  @NodeName("sl-relative-time")
  public static final class RelativeTime extends ElementComposite
      implements HasClassName<RelativeTime>, HasStyle<RelativeTime> {

    private final PropertyDescriptor<String> date = PropertyDescriptor.property("date", "");

    public RelativeTime setDate(Instant instant) {
      set(date, instant.toString());
      return this;
    }
  }
}
