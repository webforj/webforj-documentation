package com.webforj.samples.views.elementcomposite;

import com.webforj.bundle.annotation.BundleEntry;
import com.webforj.bundle.annotation.BundlePackage;
import com.webforj.component.Composite;
import com.webforj.component.element.ElementComposite;
import com.webforj.component.element.PropertyDescriptor;
import com.webforj.component.element.annotation.NodeName;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import java.awt.Color;

@Route
@FrameTitle("QR Properties")
public class QRPropertiesView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  private final QRCode qrCode = new QRCode("https://www.webforj.com");

  public QRPropertiesView() {
    self.setMargin("var(--dwc-space-m)").add(qrCode);

    qrCode.setSize(200).setColor("#0059B8");
  }

  /** QRCode Generator using Shoelace QRCode component. */
  @BundlePackage(value = "@shoelace-style/shoelace", version = "^2.20.1")
  @BundleEntry("@shoelace-style/shoelace/dist/themes/light.css")
  @BundleEntry("@shoelace-style/shoelace/dist/components/qr-code/qr-code.js")
  @NodeName("sl-qr-code")
  public static final class QRCode extends ElementComposite {

    private final PropertyDescriptor<String> descValue = PropertyDescriptor.property("value", "");
    private final PropertyDescriptor<Integer> descSize = PropertyDescriptor.property("size", 128);
    private final PropertyDescriptor<String> descColor =
        PropertyDescriptor.property("fill", "#000000");

    /** Create a new QRCode. */
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
      String hex = "#%02x%02x%02x".formatted(color.getRed(), color.getGreen(), color.getBlue());
      set(descColor, hex);
      return this;
    }

    public QRCode setColor(String color) {
      set(descColor, color);
      return this;
    }
  }
}
