package com.webforj.samples.unit.views.element;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.element.Element;
import com.webforj.samples.views.element.ElementMeterView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@WebforjTest
class ElementMeterViewTest {
  @Test
  void shouldConfigureStorageValueAndThresholds(WebforjTestbed context) {
    context.add(new ElementMeterView());

    var meter =
        ComponentLocator.of(Element.class).filter(e -> e.getNodeName().equals("meter")).locate();

    Map.of("min", "0", "max", "10", "low", "2", "high", "9", "optimum", "4", "value", "7.2")
        .forEach((name, value) -> assertEquals(value, meter.getAttribute(name)));
  }
}
