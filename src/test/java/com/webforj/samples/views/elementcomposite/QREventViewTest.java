package com.webforj.samples.views.elementcomposite;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@WebforjTest
class QREventViewTest {
  @Test
  void shouldReportTheClickCoordinate(WebforjTestbed context) {
    context.add(new QREventView());

    var qr = ComponentLocator.of(QREventView.QRCode.class).locate();

    UserInteraction.perform(
        qr,
        code -> UserInteraction.fireEvent(new QREventView.ClickEvent(code, Map.of("clientX", 42))));

    var dialog = context.getShownOptionDialogs().getLast();
    assertEquals("You clicked the QR code", dialog.getTitle());
    assertEquals("Client X:42", dialog.getMessage());
  }
}
