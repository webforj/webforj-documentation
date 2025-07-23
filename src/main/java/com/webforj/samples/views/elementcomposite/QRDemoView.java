package com.webforj.samples.views.elementcomposite;

import com.webforj.annotation.Attribute;

import com.webforj.annotation.JavaScript;
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

  QRCode qrCode = new QRCode();

  public QRDemoView() {
    getBoundComponent().setMargin("20px");
    Div code = new Div();
    code.add(qrCode);

    getBoundComponent().add(code);
  }

  /**
   * QRCode Generator using Shoelace QRCode component.
   */
  @JavaScript(value = "https://cdn.jsdelivr.net/npm/@shoelace-style/shoelace@2.0.0-beta.87/dist/shoelace.js", attributes = {
      @Attribute(name = "type", value = "module") })
  @NodeName("sl-qr-code")
  public final class QRCode extends ElementComposite {

    /**
     * Create a new QRCode.
     */
    public QRCode() {
      super();
    }
  }
}