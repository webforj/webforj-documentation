package com.webforj.samples.views.elementcomposite;

import java.awt.Color;

import com.webforj.annotation.Attribute;
import com.webforj.annotation.JavaScript;
import com.webforj.component.Composite;
import com.webforj.component.element.ElementComposite;
import com.webforj.component.element.PropertyDescriptor;
import com.webforj.component.element.annotation.NodeName;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("QR Properties")
public class QRPropertiesView extends Composite<FlexLayout> {

  QRCode qrCode = new QRCode("https://www.webforj.com");

  public QRPropertiesView() {
    getBoundComponent().setMargin("var(--dwc-space-m)");

    getBoundComponent().add(qrCode);

    /* Setting the properties */
    qrCode.setSize(200);
    qrCode.setColor("#0059B8");
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

    /**
     * Create a new QRCode.
     */
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
  }
}