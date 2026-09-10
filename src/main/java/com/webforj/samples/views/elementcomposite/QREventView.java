package com.webforj.samples.views.elementcomposite;

import static com.webforj.component.optiondialog.OptionDialog.showMessageDialog;

import com.webforj.bundle.annotation.BundleEntry;
import com.webforj.bundle.annotation.BundlePackage;
import com.webforj.component.Composite;
import com.webforj.component.element.ElementComposite;
import com.webforj.component.element.PropertyDescriptor;
import com.webforj.component.element.annotation.EventName;
import com.webforj.component.element.annotation.EventOptions;
import com.webforj.component.element.annotation.NodeName;
import com.webforj.component.event.ComponentEvent;
import com.webforj.component.html.elements.Div;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.concern.HasStyle;
import com.webforj.dispatcher.EventListener;
import com.webforj.dispatcher.ListenerRegistration;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import java.awt.Color;
import java.util.Map;

@Route
@FrameTitle("QR Events")
public class QREventView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  private final Div label = new Div("Click Me!");
  private final QRCode qrCode = new QRCode("https://www.webforj.com");

  public QREventView() {
    self.setSpacing("10px")
        .setMargin("20px")
        .setWidth(200)
        .setDirection(FlexDirection.COLUMN)
        .add(label, qrCode);

    qrCode.setSize(200).setColor("#000");

    qrCode.onClick(e -> showMessageDialog("Client X:" + e.getClientX(), "You clicked the QR code"));
  }

  /** QRCode Generator using Web Awesome QR code component. */
  @BundlePackage(value = "@awesome.me/webawesome", version = "^3.12.0")
  @BundleEntry("@awesome.me/webawesome/dist/styles/themes/default.css")
  @BundleEntry("@awesome.me/webawesome/dist/components/qr-code/qr-code.js")
  @NodeName("wa-qr-code")
  public static final class QRCode extends ElementComposite implements HasStyle<QRCode> {

    private final PropertyDescriptor<String> descValue = PropertyDescriptor.property("value", "");
    private final PropertyDescriptor<Integer> descSize = PropertyDescriptor.property("size", 128);
    private String cssColor = "#000000";

    public QRCode() {
      super();
    }

    public QRCode(String value) {
      super();
      this.setValue(value);
    }

    public QRCode(String value, int size) {
      super();
      this.setValue(value);
      this.setSize(size);
    }

    public String getValue() {
      return get(descValue);
    }

    public QRCode setValue(String value) {
      set(descValue, value);
      return this;
    }

    public int getSize() {
      return get(descSize);
    }

    public QRCode setSize(int size) {
      set(descSize, size);
      return this;
    }

    public Color getColor() {
      return Color.decode(cssColor);
    }

    public QRCode setColor(Color color) {
      return setColor("#%02x%02x%02x".formatted(color.getRed(), color.getGreen(), color.getBlue()));
    }

    /**
     * Sets the code color.
     *
     * <p>The component derives the code color from the CSS {@code color} property rather than from
     * a component property, so this setter writes an inline style.
     */
    public QRCode setColor(String color) {
      this.cssColor = color;
      setStyle("color", color);
      return this;
    }

    public ListenerRegistration<ClickEvent> onClick(EventListener<ClickEvent> listener) {
      return addEventListener(ClickEvent.class, listener);
    }
  }

  // Creating a click event.
  @EventName("click")
  @EventOptions(data = {@EventOptions.EventData(key = "clientX", exp = "event.clientX")})
  public static class ClickEvent extends ComponentEvent<QRCode> {

    public ClickEvent(QRCode source, Map<String, Object> detail) {
      super(source, detail);
    }

    public int getClientX() {
      return (int) getEventMap().get("clientX");
    }
  }
}
