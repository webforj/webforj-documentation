package com.webforj.samples.views.javascript;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.webforj.samples.pages.javascript.ExecuteJavaScriptPage;
import com.webforj.samples.views.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ExecuteJavaScriptViewIT extends BaseTest {

  private ExecuteJavaScriptPage executeJavaScriptPage;

  @BeforeEach
  public void setupExecuteJavaScript() {
    navigateToRoute(ExecuteJavaScriptPage.getRoute());
    executeJavaScriptPage = new ExecuteJavaScriptPage(page);
  }

  @Test
  public void showsToastAfterClick() {
    executeJavaScriptPage.getCopyButton().click();
    assertThat(executeJavaScriptPage.getCopiedToast()).isVisible();
  }
}
