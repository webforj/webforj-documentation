package com.webforj.samples.pages.avatar;

import com.microsoft.playwright.Page;
import com.webforj.component.avatar.Avatar;
import com.webforj.component.avatar.AvatarExpanse;
import com.webforj.samples.pages.AbstractPage;
import com.webforj.samples.utils.WebforjLocator;
import com.webforj.samples.views.avatar.AvatarExpansesView;

public class AvatarExpansesPage extends AbstractPage {

  public AvatarExpansesPage(Page page) {
    super(page, AvatarExpansesView.class);
  }

  public WebforjLocator getAvatar(AvatarExpanse expanse) {
    return getByExpanse(Avatar.class, expanse);
  }

}
