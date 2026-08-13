package com.webforj.samples.views.elementcomposite;

import com.webforj.bundle.annotation.BundleEntry;
import com.webforj.bundle.annotation.BundlePackage;
import com.webforj.component.Composite;
import com.webforj.component.element.ElementComposite;
import com.webforj.component.element.annotation.NodeName;
import com.webforj.component.html.elements.Div;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("QR Code")
public class QRDemoView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  private final QRCode qrCode = new QRCode();

  public QRDemoView() {
    Div code = new Div();
    code.add(qrCode);

    self.setMargin("20px").add(code);
  }

  /** QRCode Generator using Shoelace QRCode component. */
  @BundlePackage(value = "@shoelace-style/shoelace", version = "^2.20.1")
  @BundleEntry("@shoelace-style/shoelace/dist/themes/light.css")
  @BundleEntry("@shoelace-style/shoelace/dist/components/qr-code/qr-code.js")
  @NodeName("sl-qr-code")
  public static final class QRCode extends ElementComposite {

    /** Create a new QRCode. */
    public QRCode() {
      super();
    }
  }
}
