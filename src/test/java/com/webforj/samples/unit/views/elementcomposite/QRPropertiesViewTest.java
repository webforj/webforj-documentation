package com.webforj.samples.unit.views.elementcomposite;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.find.ComponentLocator;
import com.webforj.samples.views.elementcomposite.QRPropertiesView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.awt.Color;
import org.junit.jupiter.api.Test;

@WebforjTest
class QRPropertiesViewTest {
  @Test
  void shouldConfigureTheCodeValueSizeAndColor(WebforjTestbed context) {
    context.add(new QRPropertiesView());

    var qr = ComponentLocator.of(QRPropertiesView.QRCode.class).locate();

    assertEquals("https://www.webforj.com", qr.getValue());
    assertEquals(200, qr.getSize());
    assertEquals(Color.decode("#0059B8"), qr.getColor());
    assertEquals("#0059B8", qr.getStyle("color"));
  }
}
