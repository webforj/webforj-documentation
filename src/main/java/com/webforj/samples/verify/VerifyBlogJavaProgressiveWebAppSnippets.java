package com.webforj.samples.verify;

import com.webforj.App;
import com.webforj.ProfileDescriptor;
import com.webforj.annotation.AppProfile;
import com.webforj.annotation.AppTheme;
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;

public class VerifyBlogJavaProgressiveWebAppSnippets extends Composite<Div> {

  public VerifyBlogJavaProgressiveWebAppSnippets() {
    // Nested classes below are compiled as the snippets.
  }

  @AppProfile(name = "Field Orders", shortName = "Orders")
  public static class MinimalApplication extends App {}

  @AppTheme("dark")
  @AppProfile(
      name = "Field Orders",
      shortName = "Orders",
      description = "Review and approve orders from any device",
      themeColor = "#2563eb",
      backgroundColor = "#0f172a",
      startUrl = "/orders",
      display = ProfileDescriptor.Display.STANDALONE,
      categories = {"business", "productivity"},
      defaultIcon = @AppProfile.DefaultIcon("icons://icon.png"),
      screenshots = {
        @AppProfile.Screenshot(
            src = "ws://img/screenshots/wide.png",
            sizes = "1280x720",
            formFactor = "wide"),
        @AppProfile.Screenshot(
            src = "ws://img/screenshots/narrow.png",
            sizes = "1080x1920",
            formFactor = "narrow")
      })
  public static class FullApplication extends App {}

  public void badge() {
    App.setBadge(3);
    App.setBadge(null);
  }
}
