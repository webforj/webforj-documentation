package com.webforj.samples.views.elementcomposite;

import com.webforj.bundle.annotation.BundleEntry;
import com.webforj.bundle.annotation.BundlePackage;
import com.webforj.component.Composite;
import com.webforj.component.Theme;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.element.ElementComposite;
import com.webforj.component.element.PropertyDescriptor;
import com.webforj.component.element.annotation.EventName;
import com.webforj.component.element.annotation.EventOptions;
import com.webforj.component.element.annotation.EventOptions.EventData;
import com.webforj.component.element.annotation.NodeName;
import com.webforj.component.event.ComponentEvent;
import com.webforj.component.html.elements.H3;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.toast.Toast;
import com.webforj.concern.HasClassName;
import com.webforj.concern.HasStyle;
import com.webforj.dispatcher.EventListener;
import com.webforj.dispatcher.ListenerRegistration;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import java.util.Map;

@Route
@FrameTitle("Product Review")
public class RatingView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  private final Rating rating = new Rating();
  private final Button submit = new Button("Submit review");

  public RatingView() {
    rating.setMax(5);
    submit.setTheme(ButtonTheme.PRIMARY);
    submit.setEnabled(false);
    submit.setStyle("width", "100%");

    rating.onChange(e -> submit.setEnabled(e.getValue() > 0));
    submit.onClick(
        e -> {
          Toast.show("Thank you for your review!", Theme.SUCCESS);
          submit.setEnabled(false);
        });

    FlexLayout container = new FlexLayout(new H3("Rate this product"), rating, submit);
    container.setDirection(FlexDirection.COLUMN);
    container.setSpacing("var(--dwc-space-m)");
    container.setStyle("background", "var(--dwc-surface-3)");
    container.setStyle("border", "thin solid var(--dwc-color-default)");
    container.setStyle("border-radius", "var(--dwc-border-radius-m)");
    container.setStyle("padding", "var(--dwc-space-m)");
    container.setStyle("width", "400px");

    self.setJustifyContent(FlexJustifyContent.CENTER)
        .setMargin("var(--dwc-space-l)")
        .add(container);
  }

  /** Wrapper for the Shoelace rating web component. */
  @BundlePackage(value = "@shoelace-style/shoelace", version = "^2.20.1")
  @BundleEntry("@shoelace-style/shoelace/dist/themes/light.css")
  @BundleEntry("@shoelace-style/shoelace/dist/components/rating/rating.js")
  @NodeName("sl-rating")
  public static final class Rating extends ElementComposite
      implements HasClassName<Rating>, HasStyle<Rating> {

    private final PropertyDescriptor<Double> value = PropertyDescriptor.property("value", 0.0);
    private final PropertyDescriptor<Integer> max = PropertyDescriptor.property("max", 5);
    private final PropertyDescriptor<Double> precision =
        PropertyDescriptor.property("precision", 1.0);

    public Rating setValue(double value) {
      if (value < 0) {
        throw new IllegalArgumentException("value must be non-negative");
      }
      set(this.value, value);
      return this;
    }

    public double getValue() {
      return get(value);
    }

    public Rating setMax(int max) {
      if (max < 1) {
        throw new IllegalArgumentException("max must be at least 1");
      }
      set(this.max, max);
      return this;
    }

    public int getMax() {
      return get(max);
    }

    public Rating setPrecision(double precision) {
      set(this.precision, precision);
      return this;
    }

    public ListenerRegistration<ChangeEvent> onChange(EventListener<ChangeEvent> listener) {
      return addEventListener(ChangeEvent.class, listener);
    }
  }

  @EventName("sl-change")
  @EventOptions(data = {@EventData(key = "value", exp = "event.target.value")})
  public static final class ChangeEvent extends ComponentEvent<Rating> {

    public ChangeEvent(Rating component, Map<String, Object> payload) {
      super(component, payload);
    }

    public double getValue() {
      Object raw = getData().get("value");
      return raw == null ? 0.0 : ((Number) raw).doubleValue();
    }
  }
}
