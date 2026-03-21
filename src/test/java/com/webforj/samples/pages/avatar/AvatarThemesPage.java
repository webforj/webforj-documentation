package com.webforj.samples.pages.avatar;

import com.microsoft.playwright.Page;
import com.webforj.component.avatar.Avatar;
import com.webforj.component.avatar.AvatarTheme;
import com.webforj.samples.pages.AbstractPage;
import com.webforj.samples.utils.components.AvatarComponent;
import com.webforj.samples.views.avatar.AvatarThemesView;

public class AvatarThemesPage extends AbstractPage {

  public AvatarThemesPage(Page page) {
    super(page, AvatarThemesView.class);
  }

  public AvatarComponent getAvatar(AvatarTheme theme) {
    return new AvatarComponent(getByTheme(Avatar.class, theme));
  }

}
