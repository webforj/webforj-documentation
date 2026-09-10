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

  /** QRCode Generator using Web Awesome QR code component. */
  @BundlePackage(value = "@awesome.me/webawesome", version = "^3.12.0")
  @BundleEntry("@awesome.me/webawesome/dist/styles/themes/default.css")
  @BundleEntry("@awesome.me/webawesome/dist/components/qr-code/qr-code.js")
  @NodeName("wa-qr-code")
  public static final class QRCode extends ElementComposite {

    /** Create a new QRCode. */
    public QRCode() {
      super();
    }
  }
}
