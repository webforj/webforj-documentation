package com.webforj.samples.views.javascript;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.button.Button;
import com.webforj.component.field.TextField;
import com.webforj.component.toast.Toast;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class ExecuteJavaScriptViewTest {
  @Test
  void shouldExposeTheInviteLinkAndConfirmTheCopyAction(WebforjTestbed context) {
    context.add(new ExecuteJavaScriptView());

    var link = ComponentLocator.of(TextField.class).locate();

    assertTrue(link.isReadOnly());
    assertEquals("https://webforj.com/start?invite=AB12CD", link.getValue());

    UserInteraction.click(ComponentLocator.of(Button.class).byText("Copy link").locate());
    assertEquals("Link copied", ComponentLocator.of(Toast.class).locate().getText());
  }
}
