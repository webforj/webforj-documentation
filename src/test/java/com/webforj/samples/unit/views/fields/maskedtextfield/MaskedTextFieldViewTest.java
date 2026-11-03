package com.webforj.samples.unit.views.fields.maskedtextfield;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;

import com.webforj.samples.views.fields.maskedtextfield.MaskedTextFieldView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class MaskedTextFieldViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new MaskedTextFieldView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }
}
