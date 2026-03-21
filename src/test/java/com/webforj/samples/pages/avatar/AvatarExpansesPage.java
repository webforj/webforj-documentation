package com.webforj.samples.pages.avatar;

import com.microsoft.playwright.Page;
import com.webforj.component.avatar.Avatar;
import com.webforj.component.avatar.AvatarExpanse;
import com.webforj.samples.pages.AbstractPage;
import com.webforj.samples.utils.components.AvatarComponent;
import com.webforj.samples.views.avatar.AvatarExpansesView;

public class AvatarExpansesPage extends AbstractPage {

  public AvatarExpansesPage(Page page) {
    super(page, AvatarExpansesView.class);
  }

  public AvatarComponent getAvatar(AvatarExpanse expanse) {
    return new AvatarComponent(getByExpanse(Avatar.class, expanse));
  }

}
