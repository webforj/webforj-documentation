package com.webforj.samples.pages.avatar;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.component.avatar.Avatar;
import com.webforj.component.avatar.AvatarShape;
import com.webforj.samples.pages.AbstractPage;
import com.webforj.samples.utils.SupportedLanguage;
import com.webforj.samples.utils.WebforjLocator;
import com.webforj.samples.views.avatar.AvatarShapesView;

public class AvatarShapesPage extends AbstractPage {

  public AvatarShapesPage(Page page) {
    super(page, AvatarShapesView.class);
  }

  public WebforjLocator getAvatar(int index) {
    return getByClass(Avatar.class).nth(index);
  }
}
