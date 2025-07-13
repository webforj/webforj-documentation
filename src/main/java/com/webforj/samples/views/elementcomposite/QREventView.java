package com.webforj.samples.views.elementcomposite;

import java.awt.Color;
import java.util.Map;

import static com.webforj.component.optiondialog.OptionDialog.showMessageDialog;

import com.webforj.annotation.Attribute;
import com.webforj.annotation.JavaScript;
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
import com.webforj.dispatcher.EventListener;
import com.webforj.dispatcher.ListenerRegistration;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import com.webforj.samples.config.RouteConfig;

@Route(RouteConfig.QR_CODE_EVENTS)
@FrameTitle("QR Events")
public class QREventView extends Composite<FlexLayout> {

  Div label = new Div("Click Me!");
  QRCode qrCode = new QRCode("https://www.webforj.com");

  public QREventView() {
    getBoundComponent().setSpacing("10px").setMargin("20px").setWidth(200);
    getBoundComponent().setDirection(FlexDirection.COLUMN);

    getBoundComponent().add(label, qrCode);
    qrCode.setSize(200);
    qrCode.setColor("#000");

    /* Adding an onClick event */
    qrCode.onClick(e -> showMessageDialog("Client X:" + e.getClientX(), "You clicked the QR code"));
  }

  /**
   * QRCode Generator using Shoelace QRCode component.
   */
  @JavaScript(value = "https://cdn.jsdelivr.net/npm/@shoelace-style/shoelace@2.0.0-beta.87/dist/shoelace.js", attributes = {
      @Attribute(name = "type", value = "module") })
  @NodeName("sl-qr-code")
  public final class QRCode extends ElementComposite {

    private final PropertyDescriptor<String> descValue = PropertyDescriptor.property("value", "");
    private final PropertyDescriptor<Integer> descSize = PropertyDescriptor.property("size", 128);
    private final PropertyDescriptor<String> descColor = PropertyDescriptor.property("fill", "#000000");

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
      String hex = get(descColor);
      return Color.decode(hex);
    }

    public QRCode setColor(Color color) {
      String hex = String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
      set(descColor, hex);
      return this;
    }

    public QRCode setColor(String color) {
      set(descColor, color);
      return this;
    }

    /* Adding the onClick registration method */
    public ListenerRegistration<ClickEvent> onClick(EventListener<ClickEvent> listener) {
      return addEventListener(ClickEvent.class, listener);
    }

  }

  /* Creating a click event. */
  @EventName("click")
  @EventOptions(data = { @EventOptions.EventData(key = "clientX", exp = "event.clientX") })
  public static class ClickEvent extends ComponentEvent<QRCode> {

    public ClickEvent(QRCode source, Map<String, Object> detail) {
      super(source, detail);
    }

    public int getClientX() {
      return (int) getEventMap().get("clientX");
    }
  }
}