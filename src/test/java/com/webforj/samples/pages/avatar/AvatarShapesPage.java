package com.webforj.samples.pages.avatar;

import com.microsoft.playwright.Page;
import com.webforj.component.avatar.Avatar;
import com.webforj.samples.pages.AbstractPage;
import com.webforj.samples.utils.components.AvatarComponent;
import com.webforj.samples.views.avatar.AvatarShapesView;

public class AvatarShapesPage extends AbstractPage {

  public AvatarShapesPage(Page page) {
    super(page, AvatarShapesView.class);
  }

  public AvatarComponent getAvatar(int index) {
    return new AvatarComponent(getByClass(Avatar.class).nth(index));
  }
}
